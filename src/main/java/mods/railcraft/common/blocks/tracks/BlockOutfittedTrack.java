package mods.railcraft.common.blocks.tracks;

import mods.railcraft.common.charge.ChargeNetwork;
import mods.railcraft.common.charge.IChargeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.BlockHitResult;

public class BlockOutfittedTrack extends BaseRailBlock implements IChargeBlock {

    public static final EnumProperty<RailShape> SHAPE = BlockStateProperties.RAIL_SHAPE_STRAIGHT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final TrackType trackType;
    private final TrackKit trackKit;

    public BlockOutfittedTrack(TrackType type, TrackKit kit, BlockBehaviour.Properties props) {
        super(type.highSpeed(), props);
        this.trackType = type;
        this.trackKit = kit;
        registerDefaultState(defaultBlockState()
                .setValue(SHAPE, RailShape.NORTH_SOUTH)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(SHAPE, WATERLOGGED);
    }

    @Override
    public Property<RailShape> getShapeProperty() {
        return SHAPE;
    }

    public TrackType getTrackType() { return trackType; }
    public TrackKit getTrackKit()   { return trackKit; }

    @Override
    public ChargeSpec getChargeSpec(BlockState state, Level level, BlockPos pos) {
        return trackType.electric()
                ? new ChargeSpec(ChargeSpec.ConnectType.TRACK, 0.01)
                : null;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moved) {
        super.onPlace(state, level, pos, oldState, moved);
        if (!level.isClientSide() && trackType.electric()) {
            ChargeSpec spec = getChargeSpec(state, level, pos);
            if (spec != null) ChargeNetwork.get(level).addNode(pos, spec);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        super.onRemove(state, level, pos, newState, moved);
        if (!level.isClientSide() && trackType.electric()) {
            ChargeNetwork.get(level).removeNode(pos);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {
        return InteractionResult.PASS;
    }

    public float getMotionModifier() {
        if (trackKit == TrackKits.BOOSTER) return 1.5f;
        if (trackKit == TrackKits.SLOW)    return 0.5f;
        return 1.0f;
    }

    public boolean doesActivateCart()    { return trackKit == TrackKits.ACTIVATOR; }
    public boolean isPoweredByRedstone() { return trackKit.requiresPower(); }
}
