package mods.railcraft.common.items;

import java.util.Locale;

public enum Metal {
    STEEL,
    IRON,
    GOLD,
    COPPER,
    TIN,
    LEAD,
    SILVER,
    BRONZE,
    NICKEL,
    INVAR,
    ZINC,
    BRASS;

    public static final Metal[] VALUES = values();
    public static final Metal[] CLASSIC_METALS = {IRON, GOLD, COPPER, TIN, LEAD, SILVER};

    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    /** Returns true for metals that come from vanilla (always available without other mods). */
    public boolean isVanilla() {
        return this == IRON || this == GOLD || this == COPPER;
    }
}
