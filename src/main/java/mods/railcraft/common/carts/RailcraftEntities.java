package mods.railcraft.common.carts;

import mods.railcraft.RailcraftMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class RailcraftEntities {

    public static final EntityType<EntityCartBasic> CART_BASIC =
            FabricEntityTypeBuilder.<EntityCartBasic>create(MobCategory.MISC, EntityCartBasic::new)
                    .dimensions(EntityDimensions.fixed(0.98f, 0.7f))
                    .build();

    public static final EntityType<EntityCartChest> CART_CHEST =
            FabricEntityTypeBuilder.<EntityCartChest>create(MobCategory.MISC, EntityCartChest::new)
                    .dimensions(EntityDimensions.fixed(0.98f, 0.7f))
                    .build();

    public static final EntityType<EntityCartHopper> CART_HOPPER =
            FabricEntityTypeBuilder.<EntityCartHopper>create(MobCategory.MISC, EntityCartHopper::new)
                    .dimensions(EntityDimensions.fixed(0.98f, 0.7f))
                    .build();

    public static final EntityType<EntityCartTNT> CART_TNT =
            FabricEntityTypeBuilder.<EntityCartTNT>create(MobCategory.MISC, EntityCartTNT::new)
                    .dimensions(EntityDimensions.fixed(0.98f, 0.7f))
                    .build();

    public static final EntityType<EntityCartTank> CART_TANK =
            FabricEntityTypeBuilder.<EntityCartTank>create(MobCategory.MISC, EntityCartTank::new)
                    .dimensions(EntityDimensions.fixed(0.98f, 0.7f))
                    .build();

    public static final EntityType<EntityLocomotiveSteam> LOCOMOTIVE_STEAM =
            FabricEntityTypeBuilder.<EntityLocomotiveSteam>create(MobCategory.MISC, EntityLocomotiveSteam::new)
                    .dimensions(EntityDimensions.fixed(1.8f, 1.4f))
                    .build();

    public static final EntityType<EntityLocomotiveElectric> LOCOMOTIVE_ELECTRIC =
            FabricEntityTypeBuilder.<EntityLocomotiveElectric>create(MobCategory.MISC, EntityLocomotiveElectric::new)
                    .dimensions(EntityDimensions.fixed(1.8f, 1.4f))
                    .build();

    public static final EntityType<EntityTunnelBore> TUNNEL_BORE =
            FabricEntityTypeBuilder.<EntityTunnelBore>create(MobCategory.MISC, EntityTunnelBore::new)
                    .dimensions(EntityDimensions.fixed(3.2f, 2.0f))
                    .build();

    public static void register() {
        reg("cart_basic",          CART_BASIC);
        reg("cart_chest",          CART_CHEST);
        reg("cart_hopper",         CART_HOPPER);
        reg("cart_tnt",            CART_TNT);
        reg("cart_tank",           CART_TANK);
        reg("locomotive_steam",    LOCOMOTIVE_STEAM);
        reg("locomotive_electric", LOCOMOTIVE_ELECTRIC);
        reg("tunnel_bore",         TUNNEL_BORE);
    }

    private static void reg(String name, EntityType<?> type) {
        Registry.register(BuiltInRegistries.ENTITY_TYPE,
                new ResourceLocation(RailcraftMod.MOD_ID, name), type);
    }

    private RailcraftEntities() {}
}
