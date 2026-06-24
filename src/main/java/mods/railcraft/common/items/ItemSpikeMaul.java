package mods.railcraft.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;

import java.util.UUID;

public class ItemSpikeMaul extends TieredItem {

    private static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A500-9C13A33DB5CF");
    private static final UUID ATTACK_SPEED_UUID  = UUID.fromString("FA233E1C-4180-4866-B01B-BCCE9785ACA3");

    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public ItemSpikeMaul(Tier tier, Item.Properties properties) {
        super(tier, properties);
        float damage = 4.0f + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                ATTACK_DAMAGE_UUID, "Weapon modifier", damage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(
                ATTACK_SPEED_UUID, "Weapon modifier", -3.2f, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? attributeModifiers : super.getDefaultAttributeModifiers(slot);
    }
}
