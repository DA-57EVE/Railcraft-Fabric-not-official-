package mods.railcraft.common.items;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

public enum RailcraftArmorMaterials implements ArmorMaterial {

    STEEL("railcraft:steel", 37, new int[]{3, 6, 8, 3}, 9,
            SoundEvents.ARMOR_EQUIP_IRON, 2.0f, 0.0f,
            Ingredient.of(RailcraftItems.STEEL_INGOT)),

    OVERALLS("railcraft:overalls", 15, new int[]{1, 3, 2, 1}, 5,
            SoundEvents.ARMOR_EQUIP_LEATHER, 0.0f, 0.0f,
            Ingredient.of(RailcraftItems.STEEL_INGOT));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantability;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Ingredient repairIngredient;

    private static final int[] BASE_DURABILITY = {11, 16, 15, 13};

    RailcraftArmorMaterials(String name, int durabilityMultiplier, int[] slotProtections,
                            int enchantability, SoundEvent sound, float toughness,
                            float knockbackResistance, Ingredient repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.slotProtections = slotProtections;
        this.enchantability = enchantability;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY[type.getSlot().getIndex()] * durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return slotProtections[type.getSlot().getIndex()];
    }

    @Override
    public int getEnchantmentValue() { return enchantability; }

    @Override
    public SoundEvent getEquipSound() { return sound; }

    @Override
    public Ingredient getRepairIngredient() { return repairIngredient; }

    @Override
    public String getName() { return name; }

    @Override
    public float getToughness() { return toughness; }

    @Override
    public float getKnockbackResistance() { return knockbackResistance; }
}
