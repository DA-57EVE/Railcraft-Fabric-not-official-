package mods.railcraft.common.carts;

import mods.railcraft.common.items.ItemCrowbar;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public abstract class CartBase extends AbstractMinecart implements ILinkableCart {

    private UUID linkA = LinkageManager.NIL;
    private UUID linkB = LinkageManager.NIL;

    protected CartBase(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override public UUID getLinkA()          { return linkA; }
    @Override public UUID getLinkB()          { return linkB; }
    @Override public void setLinkA(UUID id)   { linkA = id; }
    @Override public void setLinkB(UUID id)   { linkB = id; }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() instanceof ItemCrowbar crowbar) {
            return crowbar.onCartInteract(stack, player, this, hand);
        }
        return super.interact(player, hand);
    }

    @Override
    public void tick() {
        super.tick();
        LinkageHandler.adjustCart(this);
    }

    @Override
    public AbstractMinecart.Type getMinecartType() {
        return AbstractMinecart.Type.RIDEABLE;
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(getDropItem());
    }

    @Override
    public abstract Item getDropItem();

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (!LinkageManager.isNil(linkA)) {
            tag.putLong("rcLinkAHigh", linkA.getMostSignificantBits());
            tag.putLong("rcLinkALow",  linkA.getLeastSignificantBits());
        }
        if (!LinkageManager.isNil(linkB)) {
            tag.putLong("rcLinkBHigh", linkB.getMostSignificantBits());
            tag.putLong("rcLinkBLow",  linkB.getLeastSignificantBits());
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("rcLinkAHigh")) {
            linkA = new UUID(tag.getLong("rcLinkAHigh"), tag.getLong("rcLinkALow"));
        }
        if (tag.contains("rcLinkBHigh")) {
            linkB = new UUID(tag.getLong("rcLinkBHigh"), tag.getLong("rcLinkBLow"));
        }
    }
}
