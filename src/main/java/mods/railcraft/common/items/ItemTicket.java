package mods.railcraft.common.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemTicket extends Item {

    private static final String TAG_DEST = "dest";

    public ItemTicket(Item.Properties properties) {
        super(properties.stacksTo(16));
    }

    public static void setDestination(ItemStack stack, String dest) {
        stack.getOrCreateTag().putString(TAG_DEST, dest);
    }

    public static String getDestination(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null ? tag.getString(TAG_DEST) : "";
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip,
                                TooltipFlag flag) {
        String dest = getDestination(stack);
        if (!dest.isEmpty()) {
            tooltip.add(Component.translatable("item.railcraft.ticket.dest", dest));
        }
    }
}
