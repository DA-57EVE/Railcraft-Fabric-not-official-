package mods.railcraft.common.charge;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.*;

/**
 * Manages the Charge electrical network.
 * Charge propagates through track and wire blocks; batteries supply charge;
 * powered blocks consume it.
 */
public class ChargeNetwork {

    private static final Map<ServerLevel, ChargeNetwork> NETWORKS = new WeakHashMap<>();

    private final ServerLevel level;
    private final Map<BlockPos, ChargeNode> nodes = new HashMap<>();

    private ChargeNetwork(ServerLevel level) {
        this.level = level;
    }

    public static ChargeNetwork get(Level level) {
        if (level instanceof ServerLevel serverLevel) {
            return NETWORKS.computeIfAbsent(serverLevel, ChargeNetwork::new);
        }
        throw new IllegalStateException("ChargeNetwork accessed on client side");
    }

    public void addNode(BlockPos pos, IChargeBlock.ChargeSpec spec) {
        nodes.put(pos, new ChargeNode(pos, spec));
    }

    public void removeNode(BlockPos pos) {
        nodes.remove(pos);
    }

    /** Attempt to draw the given amount of charge from the network at pos. Returns actual drawn. */
    public double draw(BlockPos pos, double amount) {
        ChargeNode node = nodes.get(pos);
        if (node == null) return 0.0;
        return node.draw(amount);
    }

    /** Supply charge into the network at pos. */
    public void supply(BlockPos pos, double amount) {
        ChargeNode node = nodes.get(pos);
        if (node != null) node.supply(amount);
    }

    public static class ChargeNode {

        private final BlockPos pos;
        private final IChargeBlock.ChargeSpec spec;
        private double charge = 0.0;

        ChargeNode(BlockPos pos, IChargeBlock.ChargeSpec spec) {
            this.pos = pos;
            this.spec = spec;
        }

        public double draw(double amount) {
            double drawn = Math.min(charge, amount);
            charge -= drawn;
            return drawn;
        }

        public void supply(double amount) {
            charge += amount;
        }

        public double getCharge() { return charge; }
    }
}
