package mods.railcraft.common.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemPairingTool extends Item {

    private static final String TAG_PAIR_ID = "pairId";

    public ItemPairingTool(Item.Properties properties) {
        super(properties.stacksTo(1));
    }

    public static int getPairId(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null ? tag.getInt(TAG_PAIR_ID) : -1;
    }

    public static void setPairId(ItemStack stack, int id) {
        stack.getOrCreateTag().putInt(TAG_PAIR_ID, id);
    }
}
