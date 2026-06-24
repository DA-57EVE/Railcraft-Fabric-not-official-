package mods.railcraft.common.blocks.tracks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

/**
 * Vertical elevator track — teleports carts up or down to a matching elevator track.
 * Uses a triggered property (powered by redstone) to determine direction.
 */
public class BlockElevatorTrack extends Block {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public BlockElevatorTrack(BlockBehaviour.Properties props) {
        super(props);
        registerDefaultState(defaultBlockState().setValue(POWERED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos,
                                Block block, BlockPos fromPos, boolean moving) {
        boolean powered = level.hasNeighborSignal(pos);
        if (powered != state.getValue(POWERED)) {
            level.setBlock(pos, state.setValue(POWERED, powered), 3);
        }
    }

    /** Returns the nearest elevator track above/below pos in the requested direction. */
    public static BlockPos findTarget(Level level, BlockPos pos, boolean goUp) {
        Direction dir = goUp ? Direction.UP : Direction.DOWN;
        BlockPos current = pos.relative(dir);
        for (int i = 0; i < 64; i++) {
            if (level.getBlockState(current).getBlock() instanceof BlockElevatorTrack) {
                return current;
            }
            current = current.relative(dir);
        }
        return null;
    }
}
