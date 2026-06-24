package mods.railcraft.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ItemMagnifyingGlass extends Item {

    public ItemMagnifyingGlass(Item.Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (player != null && !level.isClientSide()) {
            BlockState state = level.getBlockState(pos);
            player.sendSystemMessage(Component.literal(
                    "Block: " + state.getBlock().getDescriptionId() + " @ " + pos));
        }
        return InteractionResult.SUCCESS;
    }
}
