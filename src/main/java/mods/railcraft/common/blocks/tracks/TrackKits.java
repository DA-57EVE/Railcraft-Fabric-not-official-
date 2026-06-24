package mods.railcraft.common.blocks.tracks;

import mods.railcraft.RailcraftMod;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;

public final class TrackKits {

    private static final Map<ResourceLocation, TrackKit> REGISTRY = new LinkedHashMap<>();

    public static final TrackKit ACTIVATOR     = reg("activator",     TrackKit.builder(rc("activator")).build());
    public static final TrackKit BOOSTER       = reg("booster",       TrackKit.builder(rc("booster")).build());
    public static final TrackKit BUFFER_STOP   = reg("buffer_stop",   TrackKit.builder(rc("buffer_stop")).needsSupport().build());
    public static final TrackKit CONTROL       = reg("control",       TrackKit.builder(rc("control")).requiresPower().build());
    public static final TrackKit COUPLER       = reg("coupler",       TrackKit.builder(rc("coupler")).build());
    public static final TrackKit DETECTOR      = reg("detector",      TrackKit.builder(rc("detector")).build());
    public static final TrackKit DISEMBARKING  = reg("disembarking",  TrackKit.builder(rc("disembarking")).build());
    public static final TrackKit DUMPING       = reg("dumping",       TrackKit.builder(rc("dumping")).build());
    public static final TrackKit EMBARKING     = reg("embarking",     TrackKit.builder(rc("embarking")).build());
    public static final TrackKit GATED         = reg("gated",         TrackKit.builder(rc("gated")).requiresPower().build());
    public static final TrackKit GATED_ONE_WAY = reg("gated_one_way", TrackKit.builder(rc("gated_one_way")).requiresPower().build());
    public static final TrackKit JUNCTION      = reg("junction",      TrackKit.builder(rc("junction")).build());
    public static final TrackKit LOCKING       = reg("locking",       TrackKit.builder(rc("locking")).requiresPower().build());
    public static final TrackKit LOCOMOTIVE    = reg("locomotive",    TrackKit.builder(rc("locomotive")).build());
    public static final TrackKit ONE_WAY       = reg("one_way",       TrackKit.builder(rc("one_way")).build());
    public static final TrackKit PRIMING       = reg("priming",       TrackKit.builder(rc("priming")).build());
    public static final TrackKit ROUTING       = reg("routing",       TrackKit.builder(rc("routing")).requiresPower().build());
    public static final TrackKit SLOW          = reg("slow",          TrackKit.builder(rc("slow")).build());
    public static final TrackKit SUSPENDED     = reg("suspended",     TrackKit.builder(rc("suspended")).build());
    public static final TrackKit TNT           = reg("tnt",           TrackKit.builder(rc("tnt")).build());
    public static final TrackKit TURNOUT       = reg("turnout",       TrackKit.builder(rc("turnout")).requiresPower().build());
    public static final TrackKit WHISTLE       = reg("whistle",       TrackKit.builder(rc("whistle")).build());
    public static final TrackKit WORLDSPIKE    = reg("worldspike",    TrackKit.builder(rc("worldspike")).build());
    public static final TrackKit THROTTLE      = reg("throttle",      TrackKit.builder(rc("throttle")).requiresPower().build());

    private static TrackKit reg(String name, TrackKit kit) {
        REGISTRY.put(kit.id(), kit);
        return kit;
    }

    public static TrackKit get(ResourceLocation id) { return REGISTRY.get(id); }

    public static Map<ResourceLocation, TrackKit> all() { return Map.copyOf(REGISTRY); }

    private static ResourceLocation rc(String p) { return new ResourceLocation(RailcraftMod.MOD_ID, p); }

    private TrackKits() {}
}
