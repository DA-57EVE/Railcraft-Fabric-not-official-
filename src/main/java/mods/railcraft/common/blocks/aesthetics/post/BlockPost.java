package mods.railcraft.common.blocks.aesthetics.post;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockPost extends Block {

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST  = BooleanProperty.create("east");
    public static final BooleanProperty WEST  = BooleanProperty.create("west");
    public static final BooleanProperty UP    = BooleanProperty.create("up");
    public static final BooleanProperty DOWN  = BooleanProperty.create("down");

    private static final VoxelShape CORE = Block.box(6, 0, 6, 10, 16, 10);

    public BlockPost(BlockBehaviour.Properties props) {
        super(props);
        registerDefaultState(defaultBlockState()
                .setValue(NORTH, false).setValue(SOUTH, false)
                .setValue(EAST, false).setValue(WEST, false)
                .setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockGetter level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        return defaultBlockState()
                .setValue(NORTH, connectsTo(level.getBlockState(pos.north())))
                .setValue(SOUTH, connectsTo(level.getBlockState(pos.south())))
                .setValue(EAST,  connectsTo(level.getBlockState(pos.east())))
                .setValue(WEST,  connectsTo(level.getBlockState(pos.west())))
                .setValue(UP,    connectsTo(level.getBlockState(pos.above())))
                .setValue(DOWN,  connectsTo(level.getBlockState(pos.below())));
    }

    private boolean connectsTo(BlockState state) {
        return state.getBlock() instanceof BlockPost;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return CORE;
    }
}
