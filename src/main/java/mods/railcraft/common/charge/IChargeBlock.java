package mods.railcraft.common.charge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/** Implemented by blocks that participate in the Charge electrical network. */
public interface IChargeBlock {

    ChargeSpec getChargeSpec(BlockState state, Level level, BlockPos pos);

    record ChargeSpec(ConnectType connectType, double internalResistance) {

        public enum ConnectType {
            TRACK,
            WIRE,
            BLOCK,
            SLAB
        }
    }
}
