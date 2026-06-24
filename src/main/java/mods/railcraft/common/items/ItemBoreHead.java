package mods.railcraft.common.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;

public class ItemBoreHead extends TieredItem {

    private final int miningLevel;

    public ItemBoreHead(Tier tier, Item.Properties properties) {
        super(tier, properties.stacksTo(1).durability(tier.getUses()));
        this.miningLevel = tier.getLevel();
    }

    public int getMiningLevel() { return miningLevel; }

    public boolean isDamaged(ItemStack stack) { return stack.getDamageValue() > 0; }
}
