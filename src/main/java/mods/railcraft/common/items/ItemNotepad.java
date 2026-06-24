package mods.railcraft.common.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemNotepad extends Item {

    private static final String TAG_CONTENT = "content";

    public ItemNotepad(Item.Properties properties) {
        super(properties.stacksTo(1));
    }

    public static void setContent(ItemStack stack, CompoundTag content) {
        stack.getOrCreateTag().put(TAG_CONTENT, content);
    }

    public static CompoundTag getContent(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null ? tag.getCompound(TAG_CONTENT) : new CompoundTag();
    }
}
