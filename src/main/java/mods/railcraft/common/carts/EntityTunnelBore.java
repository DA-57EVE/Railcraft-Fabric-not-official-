package mods.railcraft.common.carts;

import mods.railcraft.common.items.ItemBoreHead;
import mods.railcraft.common.items.RailcraftItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class EntityTunnelBore extends CartBase {

    private static final int DIG_INTERVAL = 4;
    private static final int RANGE        = 2;

    private int digTimer      = 0;
    private boolean boring    = false;
    private Direction digDirection = Direction.NORTH;
    private ItemStack boreHead = ItemStack.EMPTY;

    public EntityTunnelBore(EntityType<EntityTunnelBore> type, Level level) {
        super(type, level);
    }

    @Override
    public Item getDropItem() { return RailcraftItems.TUNNEL_BORE_ITEM; }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide() && boring && !boreHead.isEmpty()) {
            digTimer++;
            if (digTimer >= DIG_INTERVAL) {
                digTimer = 0;
                digForward();
            }
        }
    }

    private void digForward() {
        Level world = level();
        BlockPos front = blockPosition().relative(digDirection);
        for (int y = -1; y <= 1; y++) {
            for (int side = -RANGE; side <= RANGE; side++) {
                BlockPos target = front.offset(
                        digDirection.getAxis() == Direction.Axis.X ? 0 : side,
                        y,
                        digDirection.getAxis() == Direction.Axis.Z ? 0 : side);
                BlockState bs = world.getBlockState(target);
                if (!bs.isAir() && bs.getFluidState().isEmpty() && bs.getDestroySpeed(world, target) >= 0) {
                    world.destroyBlock(target, true, this);
                    damageBoreHead();
                }
            }
        }
    }

    private void damageBoreHead() {
        if (boreHead.getItem() instanceof ItemBoreHead) {
            int damage = boreHead.getDamageValue() + 1;
            boreHead.setDamageValue(damage);
            if (damage >= boreHead.getMaxDamage()) {
                boring = false;
                boreHead = ItemStack.EMPTY;
            }
        }
    }

    public boolean isBoring()                   { return boring; }
    public void    setBoring(boolean b)          { this.boring = b; }
    public ItemStack getBoreHead()              { return boreHead; }
    public void      setBoreHead(ItemStack s)   { this.boreHead = s; }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("boring", boring);
        tag.putString("digDir", digDirection.getName());
        if (!boreHead.isEmpty()) tag.put("boreHead", boreHead.save(new CompoundTag()));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        boring = tag.getBoolean("boring");
        digDirection = Direction.byName(tag.getString("digDir"));
        if (digDirection == null) digDirection = Direction.NORTH;
        if (tag.contains("boreHead")) boreHead = ItemStack.of(tag.getCompound("boreHead"));
    }
}
