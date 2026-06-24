package mods.railcraft.common.items;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemRoutingTable extends Item {

    private static final String TAG_ROUTING = "routing";
    private static final String TAG_RULES = "rules";

    public ItemRoutingTable(Item.Properties properties) {
        super(properties.stacksTo(1));
    }

    public static List<String> getRules(ItemStack stack) {
        List<String> rules = new ArrayList<>();
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(TAG_RULES, Tag.TAG_LIST)) {
            ListTag list = tag.getList(TAG_RULES, Tag.TAG_STRING);
            for (int i = 0; i < list.size(); i++) {
                rules.add(list.getString(i));
            }
        }
        return rules;
    }

    public static void setRules(ItemStack stack, List<String> rules) {
        CompoundTag tag = stack.getOrCreateTag();
        ListTag list = new ListTag();
        for (String rule : rules) {
            list.add(StringTag.valueOf(rule));
        }
        tag.put(TAG_RULES, list);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        List<String> rules = getRules(stack);
        if (!rules.isEmpty()) {
            tooltip.add(Component.translatable("item.railcraft.routing_table.rules", rules.size()));
        }
    }
}
