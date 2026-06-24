package mods.railcraft.common.gui;

import mods.railcraft.RailcraftMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class RailcraftMenuTypes {

    public static MenuType<CokeOvenMenu>       COKE_OVEN;
    public static MenuType<BlastFurnaceMenu>   BLAST_FURNACE;
    public static MenuType<RollingMachineMenu> ROLLING_MACHINE;
    public static MenuType<SteamBoilerMenu>    STEAM_BOILER;
    public static MenuType<RockCrusherMenu>    ROCK_CRUSHER;

    public static void register() {
        COKE_OVEN = Registry.register(BuiltInRegistries.MENU,
                new ResourceLocation(RailcraftMod.MOD_ID, "coke_oven"),
                new MenuType<>(CokeOvenMenu::new, FeatureFlags.DEFAULT_FLAGS));
        BLAST_FURNACE = Registry.register(BuiltInRegistries.MENU,
                new ResourceLocation(RailcraftMod.MOD_ID, "blast_furnace"),
                new MenuType<>(BlastFurnaceMenu::new, FeatureFlags.DEFAULT_FLAGS));
        ROLLING_MACHINE = Registry.register(BuiltInRegistries.MENU,
                new ResourceLocation(RailcraftMod.MOD_ID, "rolling_machine"),
                new MenuType<>(RollingMachineMenu::new, FeatureFlags.DEFAULT_FLAGS));
        STEAM_BOILER = Registry.register(BuiltInRegistries.MENU,
                new ResourceLocation(RailcraftMod.MOD_ID, "steam_boiler"),
                new MenuType<>(SteamBoilerMenu::new, FeatureFlags.DEFAULT_FLAGS));
        ROCK_CRUSHER = Registry.register(BuiltInRegistries.MENU,
                new ResourceLocation(RailcraftMod.MOD_ID, "rock_crusher"),
                new MenuType<>(RockCrusherMenu::new, FeatureFlags.DEFAULT_FLAGS));
    }
}
