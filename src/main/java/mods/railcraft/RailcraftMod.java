package mods.railcraft;

import mods.railcraft.common.blocks.RailcraftBlocks;
import mods.railcraft.common.carts.RailcraftEntities;
import mods.railcraft.common.creativetab.RailcraftCreativeTab;
import mods.railcraft.common.fluids.RailcraftFluids;
import mods.railcraft.common.gui.RailcraftMenuTypes;
import mods.railcraft.common.items.RailcraftItems;
import mods.railcraft.common.sounds.RailcraftSounds;
import mods.railcraft.common.worldgen.RailcraftWorldGen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RailcraftMod implements ModInitializer {

    public static final String MOD_ID = "railcraft";
    public static final String MOD_NAME = "Railcraft";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing {}", MOD_NAME);

        RailcraftSounds.register();
        RailcraftFluids.register();
        RailcraftBlocks.register();
        RailcraftItems.register();
        RailcraftEntities.register();
        RailcraftMenuTypes.register();
        RailcraftWorldGen.register();
        RailcraftCreativeTab.register();

        // Coke burns twice as long as coal (3200 ticks vs 1600)
        FuelRegistry registry = FuelRegistry.INSTANCE;
        registry.add(RailcraftItems.COKE,      3200);
        registry.add(RailcraftItems.COAL_COKE, 3200);
        registry.add(RailcraftItems.CREOSOTE_BUCKET, 2400);

        LOGGER.info("{} initialized.", MOD_NAME);
    }
}
