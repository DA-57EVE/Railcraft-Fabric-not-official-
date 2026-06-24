package mods.railcraft.common.sounds;

import mods.railcraft.RailcraftMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public final class RailcraftSounds {

    public static final SoundEvent LOCOMOTIVE_STEAM_WHISTLE = create("entity.locomotive.steam.whistle");
    public static final SoundEvent LOCOMOTIVE_ELECTRIC_WHISTLE = create("entity.locomotive.electric.whistle");
    public static final SoundEvent CROWBAR_LINK = create("item.crowbar.link");
    public static final SoundEvent CROWBAR_REMOVE = create("item.crowbar.remove");
    public static final SoundEvent TRACK_PLACE = create("block.track.place");
    public static final SoundEvent COKE_OVEN_WORK = create("block.coke_oven.work");
    public static final SoundEvent BOILER_BURN = create("block.boiler.burn");
    public static final SoundEvent ROLLING_MACHINE_RUNNING = create("block.rolling_machine.running");
    public static final SoundEvent ROCK_CRUSHER_RUNNING = create("block.rock_crusher.running");
    public static final SoundEvent TUNNEL_BORE_RUNNING = create("entity.tunnel_bore.running");
    public static final SoundEvent CHARGE_ZAPPED = create("block.charge.zapped");

    private static SoundEvent create(String path) {
        ResourceLocation id = new ResourceLocation(RailcraftMod.MOD_ID, path);
        return SoundEvent.createVariableRangeEvent(id);
    }

    public static void register() {
        registerSound(LOCOMOTIVE_STEAM_WHISTLE);
        registerSound(LOCOMOTIVE_ELECTRIC_WHISTLE);
        registerSound(CROWBAR_LINK);
        registerSound(CROWBAR_REMOVE);
        registerSound(TRACK_PLACE);
        registerSound(COKE_OVEN_WORK);
        registerSound(BOILER_BURN);
        registerSound(ROLLING_MACHINE_RUNNING);
        registerSound(ROCK_CRUSHER_RUNNING);
        registerSound(TUNNEL_BORE_RUNNING);
        registerSound(CHARGE_ZAPPED);
    }

    private static void registerSound(SoundEvent event) {
        Registry.register(BuiltInRegistries.SOUND_EVENT, event.getLocation(), event);
    }

    private RailcraftSounds() {}
}
