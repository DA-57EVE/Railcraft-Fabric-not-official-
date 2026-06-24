package mods.railcraft.common.blocks.aesthetics.metals;

import java.util.Locale;

public enum MetalDecoration {
    STEEL_BLOCK,
    STEEL_PLATE_BLOCK,
    STEEL_PILLAR,
    TIN_BLOCK,
    LEAD_BLOCK,
    SILVER_BLOCK,
    BRONZE_BLOCK,
    NICKEL_BLOCK,
    INVAR_BLOCK,
    ZINC_BLOCK,
    BRASS_BLOCK;

    public String getName() { return name().toLowerCase(Locale.ROOT); }
}
