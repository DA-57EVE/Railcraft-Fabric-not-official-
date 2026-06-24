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

    private static final double MAX_SPEED    = 0.6;
    private static final int    SLOT_FUEL    = 0;
    public  static final int    INV_SIZE     = 9;
    public  static final int    MAX_BURN     = 3200;
    public  static final int    MAX_STEAM_I  = 4000;

    private final NonNullList<ItemStack> inventory = NonNullList.withSize(INV_SIZE, ItemStack.EMPTY);
    private double steamAmount = 0.0;
    private int    burnTime    = 0;
    private static final double MAX_STEAM = 4000.0;

    private final ContainerData syncData = new ContainerData() {
        @Override public int get(int i) {
            return switch (i) {
                case 0 -> burnTime;
                case 1 -> MAX_BURN;
                case 2 -> (int) steamAmount;
                case 3 -> MAX_STEAM_I;
                case 4 -> getMode().ordinal();
                default -> 0;
            };
        }
        @Override public void set(int i, int v) {
            switch (i) {
                case 0 -> burnTime = v;
                case 2 -> steamAmount = v;
                case 4 -> { try { setMode(Mode.values()[v]); } catch (ArrayIndexOutOfBoundsException ignored) {} }
            }
        }
        @Override public int getCount() { return 5; }
    };

    public EntityLocomotiveSteam(EntityType<EntityLocomotiveSteam> type, Level level) {
        super(type, level);
    }

    @Override
    public double getLocomotiveMaxSpeed() { return MAX_SPEED; }

    @Override
    public Item getDropItem() { return RailcraftItems.LOCOMOTIVE_STEAM; }

    @Override
    protected void applyThrottle() {
        if (steamAmount >= 10.0) {
            steamAmount -= 10.0;
            double dx = getDeltaMovement().x;
            double dz = getDeltaMovement().z;
            double speed = Math.sqrt(dx * dx + dz * dz);
            if (speed < MAX_SPEED) {
                double boost = 0.01;
                setDeltaMovement(getDeltaMovement().add(dx * boost, 0, dz * boost));
            }
        } else {
            setMode(Mode.IDLE);
        }

        if (burnTime > 0) {
            burnTime--;
            steamAmount = Math.min(MAX_STEAM, steamAmount + 2.0);
        } else {
            ItemStack fuel = inventory.get(SLOT_FUEL);
            int fv = getFuelValue(fuel);
            if (fv > 0) {
                fuel.shrink(1);
                burnTime = fv;
            }
        }
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
        net.minecraft.world.ContainerHelper.saveAllItems(tag, inventory);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        steamAmount = tag.getDouble("steam");
        burnTime    = tag.getInt("burnTime");
        net.minecraft.world.ContainerHelper.loadAllItems(tag, inventory);
    }

    public double getSteamAmount() { return steamAmount; }
    public double getMaxSteam()    { return MAX_STEAM; }
    public int    getBurnTime()    { return burnTime; }

    // ---- Right-click to open GUI ----

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (!level().isClientSide()) {
            player.openMenu(this);
        }
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
