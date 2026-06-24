package mods.railcraft.common.worldgen;

import mods.railcraft.common.blocks.RailcraftBlocks;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class RailcraftWorldGen {

    public static void register() {
        // Ore generation disabled until placed_feature JSON datapacks are created.
        // addOre("sulfur_ore");
        // addOre("saltpeter_ore");
        // addOre("tin_ore");
        // addOre("lead_ore");
        // addOre("silver_ore");
        // addOre("nickel_ore");
        // addOre("zinc_ore");
    }

    private static void addOre(String featureId) {
        ResourceKey<PlacedFeature> key = ResourceKey.create(Registries.PLACED_FEATURE,
                new ResourceLocation("railcraft", "ore/" + featureId));
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                key);
    }

    private RailcraftWorldGen() {}
}
