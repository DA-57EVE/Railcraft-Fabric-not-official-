package mods.railcraft.common.blocks.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import static mods.railcraft.common.blocks.RailcraftBlocks.STEAM_BOILER_BLOCK_ENTITY;

/**
 * Solid-fuel steam boiler firebox — forms a multiblock with TileBoilerTank blocks stacked above.
 */
public class BlockSteamBoiler extends BaseEntityBlock {

    public BlockSteamBoiler(BlockBehaviour.Properties props) {
        super(props);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) { return RenderShape.MODEL; }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileSteamBoiler(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, BlockState state, BlockEntityType<T> type) {
        return !level.isClientSide()
                ? createTickerHelper(type, STEAM_BOILER_BLOCK_ENTITY, TileSteamBoiler::serverTick)
                : null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof TileSteamBoiler tile) {
                player.openMenu(tile);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
