package mods.railcraft.common.carts;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class LinkageManager {

    public static final float LINKAGE_DISTANCE = 1.5f;  // per cart end; max = 2 * LINKAGE_DISTANCE
    public static final float OPTIMAL_DISTANCE = 1.25f; // per cart end; total gap = 2 * OPTIMAL_DISTANCE

    public static final UUID NIL = new UUID(0, 0);

    // Server-side crowbar selection state: player UUID → first selected cart UUID
    private static final Map<UUID, UUID> crowbarSelection = new HashMap<>();

    private LinkageManager() {}

    // ---- Link data access ----

    public static UUID getLinkA(AbstractMinecart cart) {
        return cart instanceof ILinkableCart lc ? lc.getLinkA() : NIL;
    }

    public static UUID getLinkB(AbstractMinecart cart) {
        return cart instanceof ILinkableCart lc ? lc.getLinkB() : NIL;
    }

    public static @Nullable AbstractMinecart getLinkedCartA(AbstractMinecart cart) {
        return resolveCart(cart, getLinkA(cart));
    }

    public static @Nullable AbstractMinecart getLinkedCartB(AbstractMinecart cart) {
        return resolveCart(cart, getLinkB(cart));
    }

    private static @Nullable AbstractMinecart resolveCart(AbstractMinecart cart, UUID id) {
        if (NIL.equals(id)) return null;
        if (cart.level().isClientSide()) return null;
        Entity e = ((ServerLevel) cart.level()).getEntity(id);
        return e instanceof AbstractMinecart mc ? mc : null;
    }

    // ---- Link state checks ----

    public static boolean isNil(UUID id) {
        return NIL.equals(id);
    }

    public static boolean hasFreeLink(AbstractMinecart cart) {
        return isNil(getLinkA(cart)) || (hasB(cart) && isNil(getLinkB(cart)));
    }

    private static boolean hasB(AbstractMinecart cart) {
        return !(cart instanceof ILinkableCart lc) || lc.hasTwoLinks();
    }

    public static boolean areLinked(AbstractMinecart cart1, AbstractMinecart cart2) {
        UUID id1 = cart1.getUUID(), id2 = cart2.getUUID();
        boolean c1 = id2.equals(getLinkA(cart1)) || id2.equals(getLinkB(cart1));
        boolean c2 = id1.equals(getLinkA(cart2)) || id1.equals(getLinkB(cart2));
        return c1 && c2;
    }

    public static boolean areInSameTrain(AbstractMinecart a, AbstractMinecart b) {
        if (a == b) return true;
        // Walk the chain from a and see if b is reachable
        return isReachable(a, b, null, 0);
    }

    private static boolean isReachable(AbstractMinecart current, AbstractMinecart target,
                                        @Nullable AbstractMinecart prev, int depth) {
        if (depth > 50) return false; // safety limit
        AbstractMinecart linkA = getLinkedCartA(current);
        AbstractMinecart linkB = getLinkedCartB(current);
        for (AbstractMinecart next : new AbstractMinecart[]{linkA, linkB}) {
            if (next == null || next == prev) continue;
            if (next == target) return true;
            if (isReachable(next, target, current, depth + 1)) return true;
        }
        return false;
    }

    // ---- Create / break links ----

    public static boolean createLink(AbstractMinecart cart1, AbstractMinecart cart2) {
        if (cart1 == cart2) return false;
        if (!hasFreeLink(cart1) || !hasFreeLink(cart2)) return false;
        if (areLinked(cart1, cart2)) return false;
        if (areInSameTrain(cart1, cart2)) return false;

        float maxDistSq = getLinkageDistanceSq(cart1, cart2);
        if (cart1.distanceToSqr(cart2) > maxDistSq) return false;

        setFreeLink(cart1, cart2.getUUID());
        setFreeLink(cart2, cart1.getUUID());
        return true;
    }

    public static void breakLink(AbstractMinecart cart1, AbstractMinecart cart2) {
        UUID id1 = cart1.getUUID(), id2 = cart2.getUUID();
        clearLink(cart1, id2);
        clearLink(cart2, id1);
    }

    public static void breakLinks(AbstractMinecart cart) {
        AbstractMinecart a = getLinkedCartA(cart);
        AbstractMinecart b = getLinkedCartB(cart);
        if (a != null) breakLink(cart, a);
        if (b != null) breakLink(cart, b);
    }

    private static void setFreeLink(AbstractMinecart cart, UUID target) {
        if (!(cart instanceof ILinkableCart lc)) return;
        if (isNil(lc.getLinkA())) {
            lc.setLinkA(target);
        } else if (lc.hasTwoLinks() && isNil(lc.getLinkB())) {
            lc.setLinkB(target);
        }
    }

    private static void clearLink(AbstractMinecart cart, UUID target) {
        if (!(cart instanceof ILinkableCart lc)) return;
        if (target.equals(lc.getLinkA())) lc.setLinkA(NIL);
        if (target.equals(lc.getLinkB())) lc.setLinkB(NIL);
    }

    private static float getLinkageDistanceSq(AbstractMinecart a, AbstractMinecart b) {
        float d = 0;
        d += a instanceof ILinkableCart lc ? lc.getLinkageDistance(b) : LINKAGE_DISTANCE;
        d += b instanceof ILinkableCart lc ? lc.getLinkageDistance(a) : LINKAGE_DISTANCE;
        return d * d;
    }

    // ---- Crowbar selection ----

    public static void setSelection(Player player, AbstractMinecart cart) {
        crowbarSelection.put(player.getUUID(), cart.getUUID());
    }

    public static @Nullable UUID getSelection(Player player) {
        return crowbarSelection.get(player.getUUID());
    }

    public static void clearSelection(Player player) {
        crowbarSelection.remove(player.getUUID());
    }
}
