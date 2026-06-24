package mods.railcraft.common.creativetab;

import mods.railcraft.RailcraftMod;
import mods.railcraft.common.items.RailcraftItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class RailcraftCreativeTab {

    public static final ResourceKey<CreativeModeTab> KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(),
            new ResourceLocation(RailcraftMod.MOD_ID, RailcraftMod.MOD_ID));

    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, KEY,
                FabricItemGroup.builder()
                        .title(Component.translatable("itemGroup.railcraft.main"))
                        .icon(() -> new ItemStack(RailcraftItems.STEEL_INGOT))
                        .displayItems((params, output) ->
                                BuiltInRegistries.ITEM.entrySet().stream()
                                        .filter(e -> e.getKey().location().getNamespace().equals(RailcraftMod.MOD_ID))
                                        .forEach(e -> output.accept(e.getValue())))
                        .build());
    }

    private RailcraftCreativeTab() {}
}
