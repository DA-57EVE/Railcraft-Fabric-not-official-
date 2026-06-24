package mods.railcraft.common.carts;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.MinecartHopper;
import mods.railcraft.common.items.RailcraftItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class EntityCartHopper extends MinecartHopper {

    public EntityCartHopper(EntityType<EntityCartHopper> type, Level level) {
        super(type, level);
    }

    @Override
    public Item getDropItem() { return RailcraftItems.CART_HOPPER; }
}
