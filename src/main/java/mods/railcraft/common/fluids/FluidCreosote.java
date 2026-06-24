package mods.railcraft.common.fluids;

import mods.railcraft.RailcraftMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public abstract class FluidCreosote extends FlowingFluid {

    @Override
    public Fluid getFlowing() { return RailcraftFluids.CREOSOTE_FLOWING; }

    @Override
    public Fluid getSource() { return RailcraftFluids.CREOSOTE_STILL; }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == RailcraftFluids.CREOSOTE_STILL || fluid == RailcraftFluids.CREOSOTE_FLOWING;
    }

    @Override
    protected int getDropOff(LevelReader level) { return 2; }

    @Override
    public int getTickDelay(LevelReader level) { return 20; }

    @Override
    protected float getExplosionResistance() { return 1.0f; }

    @Override
    protected int getSlopeFindDistance(net.minecraft.world.level.LevelReader level) { return 4; }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {}

    @Override
    protected boolean canConvertToSource(Level level) { return false; }

    @Override
    public boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid fluid, Direction dir) {
        return false;
    }

    @Override
    public net.minecraft.world.item.Item getBucket() {
        return mods.railcraft.common.items.RailcraftItems.CREOSOTE_BUCKET;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return net.minecraft.world.level.block.Blocks.WATER.defaultBlockState();
    }

    @Override
    public boolean isSource(FluidState state) { return false; }

    @Override
    public int getAmount(FluidState state) { return 0; }

    public static class Still extends FluidCreosote {
        @Override
        public boolean isSource(FluidState state) { return true; }

        @Override
        public int getAmount(FluidState state) { return 8; }
    }

    public static class Flowing extends FluidCreosote {
        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state) { return false; }

        @Override
        public int getAmount(FluidState state) { return state.getValue(LEVEL); }
    }
}
