package mods.railcraft.common.carts;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.RailShape;

public class CartItem extends Item {

    @FunctionalInterface
    public interface CartFactory<T extends AbstractMinecart> {
        T create(EntityType<T> type, Level level);
    }

    private final EntityType<? extends AbstractMinecart> entityType;
    private final CartFactory<AbstractMinecart> factory;

    @SuppressWarnings("unchecked")
    public <T extends AbstractMinecart> CartItem(EntityType<T> entityType, CartFactory<T> factory, Properties props) {
        super(props);
        this.entityType = entityType;
        this.factory = (CartFactory<AbstractMinecart>) (CartFactory<?>) factory;
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        BlockState state = level.getBlockState(pos);

        if (!state.is(BlockTags.RAILS)) {
            return InteractionResult.FAIL;
        }

        ItemStack stack = ctx.getItemInHand();
        if (!level.isClientSide) {
            RailShape shape = state.hasProperty(BlockStateProperties.RAIL_SHAPE)
                    ? state.getValue(BlockStateProperties.RAIL_SHAPE)
                    : (state.hasProperty(BlockStateProperties.RAIL_SHAPE_STRAIGHT)
                            ? state.getValue(BlockStateProperties.RAIL_SHAPE_STRAIGHT)
                            : RailShape.NORTH_SOUTH);
            double yOffset = shape.isAscending() ? 0.5 : 0.0;

            @SuppressWarnings("unchecked")
            AbstractMinecart cart = factory.create((EntityType<AbstractMinecart>) entityType, level);
            cart.setPos(pos.getX() + 0.5, pos.getY() + 0.0625 + yOffset, pos.getZ() + 0.5);
            if (stack.hasCustomHoverName()) {
                cart.setCustomName(stack.getHoverName());
            }
            level.addFreshEntity(cart);
        }

        stack.shrink(1);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
