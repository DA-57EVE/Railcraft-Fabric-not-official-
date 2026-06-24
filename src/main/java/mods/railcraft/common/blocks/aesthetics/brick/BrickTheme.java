package mods.railcraft.common.blocks.aesthetics.brick;

import java.util.Locale;

public enum BrickTheme {
    ABYSSAL,
    BLEACHEDBONE,
    BLOODSTAINED,
    FROSTBOUND,
    INFERNAL,
    NETHER,
    QUARRIED,
    SANDY,
    OVERLAPPING,
    POROUS;

    public String getName() { return name().toLowerCase(Locale.ROOT); }
}
