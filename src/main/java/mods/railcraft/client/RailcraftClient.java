package mods.railcraft.client;

import mods.railcraft.RailcraftMod;
import mods.railcraft.client.gui.*;
import mods.railcraft.common.blocks.RailcraftBlocks;
import mods.railcraft.common.carts.*;
import mods.railcraft.common.gui.RailcraftMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

@Environment(EnvType.CLIENT)
public class RailcraftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RailcraftMod.LOGGER.info("Initializing Railcraft client.");

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
                RailcraftBlocks.TRACK_IRON,
                RailcraftBlocks.TRACK_ABANDONED,
                RailcraftBlocks.TRACK_ELECTRIC,
                RailcraftBlocks.TRACK_HIGH_SPEED,
                RailcraftBlocks.TRACK_HS_ELECTRIC,
                RailcraftBlocks.TRACK_REINFORCED,
                RailcraftBlocks.TRACK_STRAP_IRON,
                RailcraftBlocks.TRACK_ELEVATOR);

        MenuScreens.register(RailcraftMenuTypes.COKE_OVEN,      CokeOvenScreen::new);
        MenuScreens.register(RailcraftMenuTypes.BLAST_FURNACE,  BlastFurnaceScreen::new);
        MenuScreens.register(RailcraftMenuTypes.ROLLING_MACHINE, RollingMachineScreen::new);
        MenuScreens.register(RailcraftMenuTypes.STEAM_BOILER,   SteamBoilerScreen::new);
        MenuScreens.register(RailcraftMenuTypes.ROCK_CRUSHER,   RockCrusherScreen::new);

        EntityRendererRegistry.register(RailcraftEntities.CART_BASIC,          ctx -> new MinecartRenderer<EntityCartBasic>(ctx, ModelLayers.MINECART));
        EntityRendererRegistry.register(RailcraftEntities.CART_CHEST,          ctx -> new MinecartRenderer<EntityCartChest>(ctx, ModelLayers.MINECART));
        EntityRendererRegistry.register(RailcraftEntities.CART_HOPPER,         ctx -> new MinecartRenderer<EntityCartHopper>(ctx, ModelLayers.MINECART));
        EntityRendererRegistry.register(RailcraftEntities.CART_TNT,            ctx -> new MinecartRenderer<EntityCartTNT>(ctx, ModelLayers.MINECART));
        EntityRendererRegistry.register(RailcraftEntities.CART_TANK,           ctx -> new MinecartRenderer<EntityCartTank>(ctx, ModelLayers.MINECART));
        EntityRendererRegistry.register(RailcraftEntities.LOCOMOTIVE_STEAM,    ctx -> new MinecartRenderer<EntityLocomotiveSteam>(ctx, ModelLayers.MINECART));
        EntityRendererRegistry.register(RailcraftEntities.LOCOMOTIVE_ELECTRIC, ctx -> new MinecartRenderer<EntityLocomotiveElectric>(ctx, ModelLayers.MINECART));
        EntityRendererRegistry.register(RailcraftEntities.TUNNEL_BORE,         ctx -> new MinecartRenderer<EntityTunnelBore>(ctx, ModelLayers.MINECART));

        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            Item item = stack.getItem();
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
            if (id == null || !RailcraftMod.MOD_ID.equals(id.getNamespace())) return;

            String tooltipKey = stack.getDescriptionId() + ".tooltip";
            if (I18n.exists(tooltipKey)) {
                lines.add(Component.translatable(tooltipKey).withStyle(ChatFormatting.GRAY));
            }
        });
    }
}
