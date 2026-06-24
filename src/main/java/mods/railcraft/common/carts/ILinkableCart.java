package mods.railcraft.common.carts;

import net.minecraft.world.entity.vehicle.AbstractMinecart;

import java.util.UUID;

public interface ILinkableCart {

    UUID getLinkA();
    UUID getLinkB();
    void setLinkA(UUID id);
    void setLinkB(UUID id);

    default float getLinkageDistance(AbstractMinecart other) {
        return LinkageManager.LINKAGE_DISTANCE;
    }

    default float getOptimalDistance(AbstractMinecart other) {
        return LinkageManager.OPTIMAL_DISTANCE;
    }

    default boolean canBeAdjusted(AbstractMinecart other) {
        return true;
    }

    default boolean hasTwoLinks() {
        return true;
    }

    default boolean isLinkable() {
        return true;
    }
}
