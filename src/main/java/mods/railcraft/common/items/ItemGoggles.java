package mods.railcraft.common.items;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;

public class ItemGoggles extends ArmorItem {

    public ItemGoggles(Item.Properties properties) {
        super(ArmorMaterials.IRON, Type.HELMET, properties.durability(80));
    }
}
