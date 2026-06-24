package mods.railcraft.common.fluids;

import mods.railcraft.RailcraftMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public final class RailcraftFluids {

    public static final FlowingFluid CREOSOTE_STILL   = new FluidCreosote.Still();
    public static final FlowingFluid CREOSOTE_FLOWING = new FluidCreosote.Flowing();

    public static final FlowingFluid STEAM_STILL      = new FluidSteam.Still();
    public static final FlowingFluid STEAM_FLOWING    = new FluidSteam.Flowing();

    public static void register() {
        reg("creosote",         CREOSOTE_STILL);
        reg("flowing_creosote", CREOSOTE_FLOWING);
        reg("steam",            STEAM_STILL);
        reg("flowing_steam",    STEAM_FLOWING);
    }

    private static void reg(String name, Fluid fluid) {
        Registry.register(BuiltInRegistries.FLUID, new ResourceLocation(RailcraftMod.MOD_ID, name), fluid);
    }

    private RailcraftFluids() {}
}
