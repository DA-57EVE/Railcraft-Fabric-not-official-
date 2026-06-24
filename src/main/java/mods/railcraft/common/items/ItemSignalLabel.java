package mods.railcraft.common.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemSignalLabel extends Item {

    private static final String TAG_LABEL = "label";

    public ItemSignalLabel(Item.Properties properties) {
        super(properties.stacksTo(16));
    }

    public static void setLabel(ItemStack stack, String label) {
        stack.getOrCreateTag().putString(TAG_LABEL, label);
    }

    public static String getLabel(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null ? tag.getString(TAG_LABEL) : "";
    }
}
