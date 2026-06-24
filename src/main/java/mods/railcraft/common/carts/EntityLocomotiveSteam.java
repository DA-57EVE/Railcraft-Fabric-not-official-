package mods.railcraft.common.carts;

import mods.railcraft.common.items.RailcraftItems;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

public class EntityLocomotiveSteam extends EntityLocomotive {

    private static final double MAX_SPEED = 0.6;
    private static final int    SLOT_FUEL = 0;
    private static final int    INV_SIZE  = 9;

    private final NonNullList<ItemStack> inventory = NonNullList.withSize(INV_SIZE, ItemStack.EMPTY);
    private double steamAmount = 0.0;
    private int    burnTime    = 0;
    private static final double MAX_STEAM = 4000.0;

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
}
