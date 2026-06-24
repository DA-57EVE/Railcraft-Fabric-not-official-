package mods.railcraft.common.blocks.aesthetics.brick;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockBrick extends Block {

    private final BrickTheme theme;
    private final BrickVariant variant;

    public BlockBrick(BrickTheme theme, BrickVariant variant, BlockBehaviour.Properties props) {
        super(props);
        this.theme = theme;
        this.variant = variant;
    }

    public BrickTheme getBrickTheme()     { return theme; }
    public BrickVariant getBrickVariant() { return variant; }
}
