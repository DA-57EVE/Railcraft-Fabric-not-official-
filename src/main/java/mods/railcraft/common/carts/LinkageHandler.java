package mods.railcraft.common.carts;

import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.phys.Vec3;

public final class LinkageHandler {

    public static final double LINK_DRAG   = 0.95;
    public static final float  MAX_DISTANCE = 8F;
    private static final float STIFFNESS   = 0.7F;
    private static final float DAMPING     = 0.4F;
    private static final float FORCE_LIMIT = 6F;

    private LinkageHandler() {}

    public static void adjustCart(AbstractMinecart cart) {
        if (cart.level().isClientSide()) return;

        boolean linkedA = adjustLinkedCart(cart, true);
        boolean linkedB = adjustLinkedCart(cart, false);

        if (linkedA || linkedB) {
            Vec3 m = cart.getDeltaMovement();
            cart.setDeltaMovement(m.x * LINK_DRAG, m.y, m.z * LINK_DRAG);
        }
    }

    private static boolean adjustLinkedCart(AbstractMinecart cart, boolean useA) {
        AbstractMinecart linked = useA
                ? LinkageManager.getLinkedCartA(cart)
                : LinkageManager.getLinkedCartB(cart);
        if (linked == null) return false;

        // Sanity check — repair asymmetric links
        if (!LinkageManager.areLinked(cart, linked)) {
            LinkageManager.breakLink(cart, linked);
            return false;
        }

        adjustVelocity(cart, linked);
        return true;
    }

    private static void adjustVelocity(AbstractMinecart cart1, AbstractMinecart cart2) {
        double dist = cart1.distanceTo(cart2);
        if (dist > MAX_DISTANCE) {
            LinkageManager.breakLink(cart1, cart2);
            return;
        }

        // Unit vector from cart1 toward cart2
        double dx = cart2.getX() - cart1.getX();
        double dz = cart2.getZ() - cart1.getZ();
        double len = Math.sqrt(dx * dx + dz * dz);
        if (len < 0.001) return;
        double ux = dx / len;
        double uz = dz / len;

        double optDist = getOptimalDistance(cart1, cart2);
        double stretch = dist - optDist;

        // Spring: pull/push to maintain optimal distance
        double springX = cap(STIFFNESS * stretch * ux);
        double springZ = cap(STIFFNESS * stretch * uz);

        boolean adj1 = canAdjust(cart1, cart2);
        boolean adj2 = canAdjust(cart2, cart1);

        if (adj1) {
            Vec3 m = cart1.getDeltaMovement();
            cart1.setDeltaMovement(m.x + springX, m.y, m.z + springZ);
        }
        if (adj2) {
            Vec3 m = cart2.getDeltaMovement();
            cart2.setDeltaMovement(m.x - springX, m.y, m.z - springZ);
        }

        // Damping: reduce relative velocity along the link axis
        Vec3 v1 = cart1.getDeltaMovement();
        Vec3 v2 = cart2.getDeltaMovement();
        double relX = v2.x - v1.x;
        double relZ = v2.z - v1.z;
        double dot   = relX * ux + relZ * uz;

        double dampX = cap(DAMPING * dot * ux);
        double dampZ = cap(DAMPING * dot * uz);

        if (adj1) {
            Vec3 m = cart1.getDeltaMovement();
            cart1.setDeltaMovement(m.x + dampX, m.y, m.z + dampZ);
        }
        if (adj2) {
            Vec3 m = cart2.getDeltaMovement();
            cart2.setDeltaMovement(m.x - dampX, m.y, m.z - dampZ);
        }
    }

    private static double getOptimalDistance(AbstractMinecart a, AbstractMinecart b) {
        double d = 0;
        d += a instanceof ILinkableCart lc ? lc.getOptimalDistance(b) : LinkageManager.OPTIMAL_DISTANCE;
        d += b instanceof ILinkableCart lc ? lc.getOptimalDistance(a) : LinkageManager.OPTIMAL_DISTANCE;
        return d;
    }

    private static boolean canAdjust(AbstractMinecart cart, AbstractMinecart other) {
        return !(cart instanceof ILinkableCart lc) || lc.canBeAdjusted(other);
    }

    private static double cap(double force) {
        return Math.copySign(Math.min(Math.abs(force), FORCE_LIMIT), force);
    }
}
