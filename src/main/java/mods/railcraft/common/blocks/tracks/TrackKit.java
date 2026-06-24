package mods.railcraft.common.blocks.tracks;

import net.minecraft.resources.ResourceLocation;

/** Describes a track kit that can be applied to a flex track to create an outfitted track. */
public record TrackKit(
        ResourceLocation id,
        boolean needsSupport,
        boolean requiresPower,
        int craftingCount) {

    public static Builder builder(ResourceLocation id) {
        return new Builder(id);
    }

    public static final class Builder {
        private final ResourceLocation id;
        private boolean needsSupport = false;
        private boolean requiresPower = false;
        private int craftingCount = 2;

        Builder(ResourceLocation id) { this.id = id; }

        public Builder needsSupport()       { this.needsSupport = true; return this; }
        public Builder requiresPower()      { this.requiresPower = true; return this; }
        public Builder craftingCount(int n) { this.craftingCount = n; return this; }

        public TrackKit build() {
            return new TrackKit(id, needsSupport, requiresPower, craftingCount);
        }
    }
}
