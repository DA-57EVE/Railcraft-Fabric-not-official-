package mods.railcraft.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import mods.railcraft.common.carts.LinkageManager;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ItemCrowbar extends TieredItem {

    private static final UUID ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    private static final UUID ATTACK_SPEED_UUID  = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    private final float attackDamage;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public ItemCrowbar(Tier tier, float attackDamage, float attackSpeed, Item.Properties properties) {
        super(tier, properties);
        this.attackDamage = attackDamage + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(
                ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? attributeModifiers : super.getDefaultAttributeModifiers(slot);
    }

    public InteractionResult onCartInteract(ItemStack stack, Player player, AbstractMinecart cart, InteractionHand hand) {
        if (player.level().isClientSide()) return InteractionResult.SUCCESS;

        if (player.isShiftKeyDown()) {
            // Shift-click: break all links on this cart
            LinkageManager.breakLinks(cart);
            LinkageManager.clearSelection(player);
            player.displayClientMessage(Component.literal("Links broken."), true);
            return InteractionResult.SUCCESS;
        }

        UUID selectedId = LinkageManager.getSelection(player);
        if (selectedId == null) {
            // First click: select this cart
            LinkageManager.setSelection(player, cart);
            player.displayClientMessage(Component.literal("Cart selected. Right-click another to link."), true);
        } else if (selectedId.equals(cart.getUUID())) {
            // Clicked the same cart again: cancel
            LinkageManager.clearSelection(player);
            player.displayClientMessage(Component.literal("Selection cancelled."), true);
        } else {
            // Second click: try to link
            AbstractMinecart first = resolveCart(cart.level(), selectedId);
            LinkageManager.clearSelection(player);
            if (first == null) {
                player.displayClientMessage(Component.literal("First cart not found."), true);
            } else if (LinkageManager.createLink(first, cart)) {
                player.displayClientMessage(Component.literal("Carts linked!"), true);
            } else if (LinkageManager.areLinked(first, cart)) {
                player.displayClientMessage(Component.literal("Already linked."), true);
            } else if (!LinkageManager.hasFreeLink(first) || !LinkageManager.hasFreeLink(cart)) {
                player.displayClientMessage(Component.literal("A cart has no free link slot."), true);
            } else {
                player.displayClientMessage(Component.literal("Too far apart to link."), true);
            }
        }

        return InteractionResult.SUCCESS;
    }

    private static @Nullable AbstractMinecart resolveCart(Level level, UUID id) {
        if (level.isClientSide()) return null;
        Entity e = ((ServerLevel) level).getEntity(id);
        return e instanceof AbstractMinecart mc ? mc : null;
    }
}
