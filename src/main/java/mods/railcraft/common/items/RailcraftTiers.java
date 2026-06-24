package mods.railcraft.common.items;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public enum RailcraftTiers implements Tier {

    STEEL(3, 1250, 7.0f, 2.5f, 16, Ingredient.of(RailcraftItems.STEEL_INGOT)),
    BRONZE(2, 500, 6.0f, 2.0f, 10, Ingredient.of(Items.COPPER_INGOT));

    private final int level;
    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final int enchantmentValue;
    private final Ingredient repairIngredient;

    RailcraftTiers(int level, int uses, float speed, float attackDamage,
                   int enchantValue, Ingredient repairIngredient) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamage;
        this.enchantmentValue = enchantValue;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getUses() { return uses; }

    @Override
    public float getSpeed() { return speed; }

    @Override
    public float getAttackDamageBonus() { return attackDamageBonus; }

    @Override
    public int getLevel() { return level; }

    @Override
    public int getEnchantmentValue() { return enchantmentValue; }

    @Override
    public Ingredient getRepairIngredient() { return repairIngredient; }
}
