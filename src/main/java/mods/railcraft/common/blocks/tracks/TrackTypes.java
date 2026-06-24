package mods.railcraft.common.blocks.tracks;

import mods.railcraft.RailcraftMod;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;
import java.util.Map;

/** Registry of all track types by resource-location key. */
public final class TrackTypes {

    private static final Map<ResourceLocation, TrackType> REGISTRY = new LinkedHashMap<>();

    public static final TrackType IRON = register(
            TrackType.builder(new ResourceLocation("minecraft", "iron"))
                    .maxSpeed(TrackType.SPEED_IRON)
                    .build());

    public static final TrackType ABANDONED = register(
            TrackType.builder(rc("abandoned"))
                    .maxSpeed(TrackType.SPEED_ABANDONED)
                    .maxSupportDistance(3)
                    .build());

    public static final TrackType ELECTRIC = register(
            TrackType.builder(rc("electric"))
                    .maxSpeed(TrackType.SPEED_IRON)
                    .electric()
                    .build());

    public static final TrackType HIGH_SPEED = register(
            TrackType.builder(rc("high_speed"))
                    .maxSpeed(TrackType.SPEED_HS)
                    .highSpeed()
                    .build());

    public static final TrackType HIGH_SPEED_ELECTRIC = register(
            TrackType.builder(rc("high_speed_electric"))
                    .maxSpeed(TrackType.SPEED_HS)
                    .highSpeed()
                    .electric()
                    .build());

    public static final TrackType REINFORCED = register(
            TrackType.builder(rc("reinforced"))
                    .maxSpeed(TrackType.SPEED_REINFORCED)
                    .build());

    public static final TrackType STRAP_IRON = register(
            TrackType.builder(rc("strap_iron"))
                    .maxSpeed(TrackType.SPEED_STRAP_IRON)
                    .build());

    private static TrackType register(TrackType t) {
        REGISTRY.put(t.id(), t);
        return t;
    }

    public static TrackType get(ResourceLocation id) {
        return REGISTRY.getOrDefault(id, IRON);
    }

    public static Map<ResourceLocation, TrackType> all() {
        return Map.copyOf(REGISTRY);
    }

    private static ResourceLocation rc(String path) {
        return new ResourceLocation(RailcraftMod.MOD_ID, path);
    }

    private TrackTypes() {}
}
