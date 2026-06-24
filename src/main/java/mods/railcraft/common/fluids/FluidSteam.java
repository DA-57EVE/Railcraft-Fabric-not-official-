package mods.railcraft.common.fluids;

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

public abstract class FluidSteam extends FlowingFluid {

    @Override
    public Fluid getFlowing() { return RailcraftFluids.STEAM_FLOWING; }

    @Override
    public Fluid getSource() { return RailcraftFluids.STEAM_STILL; }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == RailcraftFluids.STEAM_STILL || fluid == RailcraftFluids.STEAM_FLOWING;
    }

    @Override
    protected int getDropOff(LevelReader level) { return 2; }

    @Override
    public int getTickDelay(LevelReader level) { return 5; }

    @Override
    protected float getExplosionResistance() { return 0.0f; }

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
    public net.minecraft.world.item.Item getBucket() { return net.minecraft.world.item.Items.AIR; }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return net.minecraft.world.level.block.Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean isSource(FluidState state) { return false; }

    @Override
    public int getAmount(FluidState state) { return 0; }

    public static class Still extends FluidSteam {
        @Override
        public boolean isSource(FluidState state) { return true; }

        @Override
        public int getAmount(FluidState state) { return 8; }
    }

    public static class Flowing extends FluidSteam {
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
