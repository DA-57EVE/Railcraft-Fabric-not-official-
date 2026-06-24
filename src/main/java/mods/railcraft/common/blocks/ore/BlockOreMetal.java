package mods.railcraft.common.blocks.ore;

import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockOreMetal extends DropExperienceBlock {

    private final OreType oreType;

    public BlockOreMetal(OreType type, BlockBehaviour.Properties props) {
        super(props);
        this.oreType = type;
    }

    public OreType getOreType() { return oreType; }
}
