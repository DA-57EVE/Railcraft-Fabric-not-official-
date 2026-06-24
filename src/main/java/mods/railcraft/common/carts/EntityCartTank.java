package mods.railcraft.common.carts;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import mods.railcraft.common.items.RailcraftItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class EntityCartTank extends CartBase {

    private static final int CAPACITY = 32000;

    private Fluid fluid = Fluids.EMPTY;
    private int fluidAmount = 0;

    public EntityCartTank(EntityType<EntityCartTank> type, Level level) {
        super(type, level);
    }

    @Override
    public Item getDropItem() { return RailcraftItems.CART_TANK; }

    public boolean fill(Fluid fluid, int amount) {
        if (this.fluid != Fluids.EMPTY && this.fluid != fluid) return false;
        if (fluidAmount + amount > CAPACITY) return false;
        this.fluid = fluid;
        this.fluidAmount += amount;
        return true;
    }

    public int drain(int maxAmount) {
        int drained = Math.min(fluidAmount, maxAmount);
        fluidAmount -= drained;
        if (fluidAmount == 0) fluid = Fluids.EMPTY;
        return drained;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("fluidId", net.minecraft.core.registries.BuiltInRegistries.FLUID
                .getKey(fluid).toString());
        tag.putInt("fluidAmount", fluidAmount);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        fluid = net.minecraft.core.registries.BuiltInRegistries.FLUID.get(
                new net.minecraft.resources.ResourceLocation(tag.getString("fluidId")));
        if (fluid == null) fluid = Fluids.EMPTY;
        fluidAmount = tag.getInt("fluidAmount");
    }

    public Fluid getFluid()     { return fluid; }
    public int getFluidAmount() { return fluidAmount; }
    public int getCapacity()    { return CAPACITY; }
}
