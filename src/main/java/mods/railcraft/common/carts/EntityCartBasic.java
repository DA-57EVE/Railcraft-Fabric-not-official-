package mods.railcraft.common.carts;

import net.minecraft.world.entity.EntityType;
import mods.railcraft.common.items.RailcraftItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class EntityCartBasic extends CartBase {

    public EntityCartBasic(EntityType<EntityCartBasic> type, Level level) {
        super(type, level);
    }

    @Override
    public Item getDropItem() { return RailcraftItems.CART_BASIC; }
}
