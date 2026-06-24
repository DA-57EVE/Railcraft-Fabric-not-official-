package mods.railcraft.common.blocks.aesthetics.brick;

import java.util.Locale;

public enum BrickVariant {
    COBBLE,
    FITTED,
    BRICK,
    ORNATE;

    public String getName() { return name().toLowerCase(Locale.ROOT); }
}
