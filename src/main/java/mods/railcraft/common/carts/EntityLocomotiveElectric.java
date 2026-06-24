package mods.railcraft.common.carts;

import mods.railcraft.common.charge.ChargeNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import mods.railcraft.common.items.RailcraftItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class EntityLocomotiveElectric extends EntityLocomotive {

    private static final double MAX_SPEED    = 0.8;
    private static final double CHARGE_DRAW  = 5.0;

    private double chargeBuffer = 0.0;

    public EntityLocomotiveElectric(EntityType<EntityLocomotiveElectric> type, Level level) {
        super(type, level);
    }

    @Override
    public double getLocomotiveMaxSpeed() { return MAX_SPEED; }

    @Override
    public Item getDropItem() { return RailcraftItems.LOCOMOTIVE_ELECTRIC; }

    @Override
    protected void applyThrottle() {
        Level level = level();
        BlockPos trackPos = blockPosition().below();
        try {
            double drawn = ChargeNetwork.get(level).draw(trackPos, CHARGE_DRAW);
            chargeBuffer += drawn;
        } catch (IllegalStateException e) {
            // client side
        }

        if (chargeBuffer >= CHARGE_DRAW) {
            chargeBuffer -= CHARGE_DRAW;
            double dx = getDeltaMovement().x;
            double dz = getDeltaMovement().z;
            double speed = Math.sqrt(dx * dx + dz * dz);
            if (speed < MAX_SPEED) {
                double boost = 0.012;
                setDeltaMovement(getDeltaMovement().add(dx * boost, 0, dz * boost));
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putDouble("chargeBuffer", chargeBuffer);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        chargeBuffer = tag.getDouble("chargeBuffer");
    }
}
