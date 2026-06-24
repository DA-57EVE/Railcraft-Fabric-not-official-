package mods.railcraft.common.carts;

import mods.railcraft.common.gui.LocomotiveSteamMenu;
import mods.railcraft.common.items.RailcraftItems;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

public class EntityLocomotiveSteam extends EntityLocomotive implements Container, MenuProvider {

    public  static final int    INV_SIZE     = 9;
    private static final int    SLOT_BURN    = 0;  // actively burning slot
    private static final int    SLOT_FUEL_A  = 1;  // fuel bunker A
    private static final int    SLOT_FUEL_B  = 2;  // fuel bunker B
    private static final int    SLOT_FUEL_C  = 3;  // fuel bunker C
    public  static final int    MAX_BURN     = 3200;
    public  static final int    MAX_STEAM_I  = 4000;
    private static final double MAX_STEAM    = 4000.0;

    // Speed → max velocity limits (blocks/tick)
    private static final double[] SPEED_LIMITS = { 0.1, 0.2, 0.3, 0.4 };
    // Speed → force added per tick (must overcome 3% drag from applyNaturalSlowdown)
    private static final double[] SPEED_BOOSTS = { 0.04, 0.07, 0.10, 0.14 };
    // Steam cost per tick when running (scales with speed)
    private static final double[] STEAM_COST   = { 4.0, 6.0, 8.0, 10.0 };

    private final NonNullList<ItemStack> inventory = NonNullList.withSize(INV_SIZE, ItemStack.EMPTY);
    private double steamAmount = 0.0;
    private int    burnTime    = 0;

    // SyncData indices: 0=burnTime, 1=maxBurn, 2=steam, 3=maxSteam, 4=mode, 5=speed, 6=reverse
    private final ContainerData syncData = new ContainerData() {
        @Override public int get(int i) {
            return switch (i) {
                case 0 -> burnTime;
                case 1 -> MAX_BURN;
                case 2 -> (int) steamAmount;
                case 3 -> MAX_STEAM_I;
                case 4 -> getMode().ordinal();
                case 5 -> getSpeed().ordinal();
                case 6 -> isReverse() ? 1 : 0;
                default -> 0;
            };
        }
        @Override public void set(int i, int v) {
            switch (i) {
                case 0 -> burnTime = v;
                case 2 -> steamAmount = v;
                case 4 -> { if (v >= 0 && v < LocoMode.VALUES.length)  setMode(LocoMode.VALUES[v]); }
                case 5 -> { if (v >= 0 && v < LocoSpeed.VALUES.length) setSpeed(LocoSpeed.VALUES[v]); }
                case 6 -> setReverse(v != 0);
            }
        }
        @Override public int getCount() { return 7; }
    };

    public EntityLocomotiveSteam(EntityType<EntityLocomotiveSteam> type, Level level) {
        super(type, level);
        // Default to SHUTDOWN — player must explicitly set a speed to start
    }

    @Override
    public double getLocomotiveMaxSpeed() { return SPEED_LIMITS[LocoSpeed.MAX.ordinal()]; }

    @Override
    public Item getDropItem() { return RailcraftItems.LOCOMOTIVE_STEAM; }

    @Override
    public void tick() {
        super.tick();  // calls applyThrottle() when RUNNING
        if (!level().isClientSide()) burnFuel();
    }

    private void burnFuel() {
        if (isShutdown()) return;
        if (burnTime > 0) {
            burnTime--;
            steamAmount = Math.min(MAX_STEAM, steamAmount + 2.0);
        } else {
            // Pull fuel: burn slot first, then bunker A→C
            for (int i = SLOT_BURN; i <= SLOT_FUEL_C; i++) {
                ItemStack fuel = inventory.get(i);
                int fv = getFuelValue(fuel);
                if (fv > 0) {
                    fuel.shrink(1);
                    burnTime = fv;
                    break;
                }
            }
        }
    }

    @Override
    protected void applyThrottle() {
        int si = getSpeed().ordinal();
        if (steamAmount < STEAM_COST[si]) { setMode(LocoMode.IDLE); return; }
        steamAmount -= STEAM_COST[si];

        // In 1.20.1 the cart yaw is derived from atan2(old_z - new_z, old_x - new_x),
        // so forward is (-cos(yaw), -sin(yaw)), opposite of 1.12.2 convention.
        double yawRad = getYRot() * Math.PI / 180.0;
        double dir    = isReverse() ? 1.0 : -1.0;  // flipped sign vs original
        double boost  = SPEED_BOOSTS[si];
        double maxSpd = SPEED_LIMITS[si];

        double dx = getDeltaMovement().x + Math.cos(yawRad) * boost * dir;
        double dz = getDeltaMovement().z + Math.sin(yawRad) * boost * dir;
        double spd = Math.sqrt(dx * dx + dz * dz);
        if (spd > maxSpd) { dx = dx * maxSpd / spd; dz = dz * maxSpd / spd; }
        setDeltaMovement(dx, getDeltaMovement().y, dz);
    }

    private int getFuelValue(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        if (stack.is(RailcraftItems.COKE) || stack.is(RailcraftItems.COAL_COKE)) return 3200;
        return AbstractFurnaceBlockEntity.getFuel().getOrDefault(stack.getItem(), 0);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putDouble("steam", steamAmount);
        tag.putInt("burnTime", burnTime);
        ContainerHelper.saveAllItems(tag, inventory);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        steamAmount = tag.getDouble("steam");
        burnTime    = tag.getInt("burnTime");
        ContainerHelper.loadAllItems(tag, inventory);
    }

    public double getSteamAmount() { return steamAmount; }
    public double getMaxSteam()    { return MAX_STEAM; }
    public int    getBurnTime()    { return burnTime; }

    // ---- Right-click to open GUI ----

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (!level().isClientSide()) player.openMenu(this);
        return InteractionResult.sidedSuccess(level().isClientSide());
    }

    // ---- MenuProvider ----

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.railcraft.locomotive_steam");
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new LocomotiveSteamMenu(syncId, inv, this, syncData);
    }

    // ---- Container ----

    @Override public int getContainerSize()                    { return INV_SIZE; }
    @Override public boolean isEmpty()                         { return inventory.stream().allMatch(ItemStack::isEmpty); }
    @Override public ItemStack getItem(int slot)               { return inventory.get(slot); }
    @Override public ItemStack removeItem(int slot, int count) { return ContainerHelper.removeItem(inventory, slot, count); }
    @Override public ItemStack removeItemNoUpdate(int slot)    { return ContainerHelper.takeItem(inventory, slot); }
    @Override public void setItem(int slot, ItemStack stack)   { inventory.set(slot, stack); }
    @Override public void setChanged()                         {}
    @Override public void clearContent()                       { inventory.clear(); }
    @Override public boolean stillValid(Player player)         { return player.distanceToSqr(this) < 64.0; }
}
