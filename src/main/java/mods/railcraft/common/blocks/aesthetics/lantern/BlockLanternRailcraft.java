package mods.railcraft.common.blocks.aesthetics.lantern;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class BlockLanternRailcraft extends LanternBlock {

    public BlockLanternRailcraft(BlockBehaviour.Properties props) {
        super(props.lightLevel(s -> 15).noOcclusion());
    }
}
