package mods.railcraft.common.blocks.tracks;

import net.minecraft.resources.ResourceLocation;

/** Describes the material composition of a flex track. */
public record TrackType(
        ResourceLocation id,
        float maxSpeed,
        boolean electric,
        boolean highSpeed,
        int maxSupportDistance) {

    public static final float SPEED_IRON        = 0.4f;
    public static final float SPEED_REINFORCED  = 0.5f;
    public static final float SPEED_HS          = 1.0f;
    public static final float SPEED_ABANDONED   = 0.25f;
    public static final float SPEED_STRAP_IRON  = 0.15f;

    public static Builder builder(ResourceLocation id) {
        return new Builder(id);
    }

    public static final class Builder {
        private final ResourceLocation id;
        private float maxSpeed = SPEED_IRON;
        private boolean electric = false;
        private boolean highSpeed = false;
        private int maxSupportDistance = 2;

        Builder(ResourceLocation id) { this.id = id; }

        public Builder maxSpeed(float s)           { this.maxSpeed = s; return this; }
        public Builder electric()                  { this.electric = true; return this; }
        public Builder highSpeed()                 { this.highSpeed = true; return this; }
        public Builder maxSupportDistance(int d)   { this.maxSupportDistance = d; return this; }

        public TrackType build() {
            return new TrackType(id, maxSpeed, electric, highSpeed, maxSupportDistance);
        }
    }
}
