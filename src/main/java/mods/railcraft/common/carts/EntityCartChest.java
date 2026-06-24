package mods.railcraft.common.carts;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.vehicle.MinecartChest;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import mods.railcraft.common.items.RailcraftItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class EntityCartChest extends MinecartChest {

    public EntityCartChest(EntityType<EntityCartChest> type, Level level) {
        super(type, level);
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv) {
        return ChestMenu.threeRows(id, inv, this);
    }

    @Override
    public Item getDropItem() { return RailcraftItems.CART_CHEST; }
}
