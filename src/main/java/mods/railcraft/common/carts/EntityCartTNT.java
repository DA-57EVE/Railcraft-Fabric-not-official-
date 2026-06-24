package mods.railcraft.common.carts;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import mods.railcraft.common.items.RailcraftItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class EntityCartTNT extends MinecartTNT {

    public EntityCartTNT(EntityType<EntityCartTNT> type, Level level) {
        super(type, level);
    }

    @Override
    public Item getDropItem() { return RailcraftItems.CART_TNT; }
}
