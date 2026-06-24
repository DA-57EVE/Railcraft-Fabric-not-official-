package mods.railcraft.common.blocks.ore;

import java.util.Locale;

public enum OreType {
    SULFUR,
    SALTPETER,
    TIN,
    LEAD,
    SILVER,
    NICKEL,
    ZINC,
    FIRESTONE;

    public String getName() { return name().toLowerCase(Locale.ROOT); }
}
