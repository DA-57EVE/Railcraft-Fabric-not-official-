package mods.railcraft.common.items;

import mods.railcraft.RailcraftMod;
import mods.railcraft.common.carts.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

public final class RailcraftItems {

    // -------------------------------------------------------------------------
    // Carts & Locomotives
    // -------------------------------------------------------------------------
    public static final Item CART_BASIC          = new CartItem(RailcraftEntities.CART_BASIC,          EntityCartBasic::new,          props().stacksTo(1));
    public static final Item CART_CHEST          = new CartItem(RailcraftEntities.CART_CHEST,          EntityCartChest::new,          props().stacksTo(1));
    public static final Item CART_HOPPER         = new CartItem(RailcraftEntities.CART_HOPPER,         EntityCartHopper::new,         props().stacksTo(1));
    public static final Item CART_TNT            = new CartItem(RailcraftEntities.CART_TNT,            EntityCartTNT::new,            props().stacksTo(1));
    public static final Item CART_TANK           = new CartItem(RailcraftEntities.CART_TANK,           EntityCartTank::new,           props().stacksTo(1));
    public static final Item LOCOMOTIVE_STEAM    = new CartItem(RailcraftEntities.LOCOMOTIVE_STEAM,    EntityLocomotiveSteam::new,    props().stacksTo(1));
    public static final Item LOCOMOTIVE_ELECTRIC = new CartItem(RailcraftEntities.LOCOMOTIVE_ELECTRIC, EntityLocomotiveElectric::new, props().stacksTo(1));
    public static final Item TUNNEL_BORE_ITEM    = new CartItem(RailcraftEntities.TUNNEL_BORE,         EntityTunnelBore::new,         props().stacksTo(1));

    // -------------------------------------------------------------------------
    // Metals
    // -------------------------------------------------------------------------
    public static final Item STEEL_INGOT        = new Item(props());
    public static final Item STEEL_NUGGET       = new Item(props());
    public static final Item STEEL_PLATE        = new Item(props());
    public static final Item STEEL_GEAR         = new Item(props());
    public static final Item TIN_INGOT          = new Item(props());
    public static final Item TIN_NUGGET         = new Item(props());
    public static final Item TIN_PLATE          = new Item(props());
    public static final Item TIN_GEAR           = new Item(props());
    public static final Item LEAD_INGOT         = new Item(props());
    public static final Item LEAD_NUGGET        = new Item(props());
    public static final Item LEAD_PLATE         = new Item(props());
    public static final Item LEAD_GEAR          = new Item(props());
    public static final Item SILVER_INGOT       = new Item(props());
    public static final Item SILVER_NUGGET      = new Item(props());
    public static final Item SILVER_PLATE       = new Item(props());
    public static final Item SILVER_GEAR        = new Item(props());
    public static final Item BRONZE_INGOT       = new Item(props());
    public static final Item BRONZE_NUGGET      = new Item(props());
    public static final Item BRONZE_PLATE       = new Item(props());
    public static final Item BRONZE_GEAR        = new Item(props());
    public static final Item NICKEL_INGOT       = new Item(props());
    public static final Item NICKEL_NUGGET      = new Item(props());
    public static final Item NICKEL_PLATE       = new Item(props());
    public static final Item NICKEL_GEAR        = new Item(props());
    public static final Item INVAR_INGOT        = new Item(props());
    public static final Item INVAR_NUGGET       = new Item(props());
    public static final Item INVAR_PLATE        = new Item(props());
    public static final Item INVAR_GEAR         = new Item(props());
    public static final Item ZINC_INGOT         = new Item(props());
    public static final Item ZINC_NUGGET        = new Item(props());
    public static final Item ZINC_PLATE         = new Item(props());
    public static final Item ZINC_GEAR          = new Item(props());
    public static final Item BRASS_INGOT        = new Item(props());
    public static final Item BRASS_NUGGET       = new Item(props());
    public static final Item BRASS_PLATE        = new Item(props());
    public static final Item BRASS_GEAR         = new Item(props());
    public static final Item IRON_PLATE         = new Item(props());
    public static final Item IRON_GEAR          = new Item(props());
    public static final Item GOLD_PLATE         = new Item(props());
    public static final Item GOLD_GEAR          = new Item(props());
    public static final Item COPPER_PLATE       = new Item(props());
    public static final Item COPPER_GEAR        = new Item(props());

    // -------------------------------------------------------------------------
    // Dusts
    // -------------------------------------------------------------------------
    public static final Item DUST_SULFUR        = new Item(props());
    public static final Item DUST_SALTPETER     = new Item(props());
    public static final Item DUST_COAL          = new Item(props());
    public static final Item DUST_CHARCOAL      = new Item(props());
    public static final Item DUST_OBSIDIAN      = new Item(props());

    // -------------------------------------------------------------------------
    // Track components
    // -------------------------------------------------------------------------
    public static final Item RAIL_STANDARD      = new Item(props().stacksTo(64));
    public static final Item RAIL_ADVANCED      = new Item(props().stacksTo(64));
    public static final Item RAIL_WOODEN        = new Item(props().stacksTo(64));
    public static final Item RAIL_SPEED         = new Item(props().stacksTo(64));
    public static final Item RAIL_REINFORCED    = new Item(props().stacksTo(64));
    public static final Item RAIL_ELECTRIC      = new Item(props().stacksTo(64));
    public static final Item TIE_WOOD           = new Item(props().stacksTo(64));
    public static final Item TIE_STONE          = new Item(props().stacksTo(64));
    public static final Item RAILBED_WOOD       = new Item(props().stacksTo(16));
    public static final Item RAILBED_STONE      = new Item(props().stacksTo(16));
    public static final Item TRACK_PARTS        = new Item(props().stacksTo(64));

    // -------------------------------------------------------------------------
    // Track kits
    // -------------------------------------------------------------------------
    public static final Item TRACK_KIT_ACTIVATOR       = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_BOOSTER         = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_BUFFER_STOP     = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_CONTROL         = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_COUPLER         = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_DETECTOR        = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_DISEMBARKING    = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_DUMPING         = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_EMBARKING       = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_GATED           = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_GATED_ONE_WAY   = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_JUNCTION        = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_LOCKING         = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_LOCOMOTIVE      = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_ONE_WAY         = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_PRIMING         = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_ROUTING         = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_SLOW            = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_SUSPENDED       = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_TNT             = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_TURNOUT         = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_WHISTLE         = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_WORLDSPIKE      = new Item(props().stacksTo(16));
    public static final Item TRACK_KIT_THROTTLE        = new Item(props().stacksTo(16));

    // -------------------------------------------------------------------------
    // Tools & Equipment
    // -------------------------------------------------------------------------
    public static final Item CROWBAR_IRON       = new ItemCrowbar(Tiers.IRON,           3.0f, -2.8f, props().durability(250));
    public static final Item CROWBAR_STEEL      = new ItemCrowbar(RailcraftTiers.STEEL, 3.0f, -2.8f, props().durability(1250));
    public static final Item CROWBAR_DIAMOND    = new ItemCrowbar(Tiers.DIAMOND,        3.0f, -2.8f, props().durability(1561));
    public static final Item CROWBAR_SEASONS    = new ItemCrowbar(RailcraftTiers.STEEL, 3.0f, -2.8f, props().durability(1250));

    public static final Item SPIKE_MAUL_IRON    = new ItemSpikeMaul(Tiers.IRON,         props().durability(250));
    public static final Item SPIKE_MAUL_STEEL   = new ItemSpikeMaul(RailcraftTiers.STEEL, props().durability(1250));

    public static final Item GOGGLES            = new ItemGoggles(props());
    public static final Item MAGNIFYING_GLASS   = new ItemMagnifyingGlass(props());
    public static final Item NOTEPAD            = new ItemNotepad(props());
    public static final Item OVERALLS           = new ItemOveralls(props());
    public static final Item SIGNAL_TUNER       = new ItemSignalTuner(props());
    public static final Item SIGNAL_LABEL       = new ItemSignalLabel(props());
    public static final Item PAIRING_TOOL       = new ItemPairingTool(props());
    public static final Item ROUTING_TABLE      = new ItemRoutingTable(props());
    public static final Item CHARGE_METER       = new Item(props().stacksTo(1));
    public static final Item SIGNAL_LAMP        = new Item(props());
    public static final Item SIGNAL_BLOCK_SURVEYOR = new Item(props().stacksTo(1));

    // -------------------------------------------------------------------------
    // Steel tools & weapons
    // -------------------------------------------------------------------------
    public static final Item STEEL_SWORD        = new SwordItem(RailcraftTiers.STEEL,    4, -2.4f, props());
    public static final Item STEEL_SHOVEL       = new ShovelItem(RailcraftTiers.STEEL,   1.5f, -3.0f, props());
    public static final Item STEEL_PICKAXE      = new PickaxeItem(RailcraftTiers.STEEL,  1, -2.8f, props());
    public static final Item STEEL_AXE          = new AxeItem(RailcraftTiers.STEEL,      6.0f, -3.1f, props());
    public static final Item STEEL_HOE          = new HoeItem(RailcraftTiers.STEEL,      -1, 0.0f, props());
    public static final Item STEEL_SHEARS       = new ShearsItem(props().durability(500));

    // -------------------------------------------------------------------------
    // Steel armor
    // -------------------------------------------------------------------------
    public static final Item STEEL_HELMET       = new ItemRailcraftArmor(RailcraftArmorMaterials.STEEL, ArmorItem.Type.HELMET,     props());
    public static final Item STEEL_CHESTPLATE   = new ItemRailcraftArmor(RailcraftArmorMaterials.STEEL, ArmorItem.Type.CHESTPLATE, props());
    public static final Item STEEL_LEGGINGS     = new ItemRailcraftArmor(RailcraftArmorMaterials.STEEL, ArmorItem.Type.LEGGINGS,   props());
    public static final Item STEEL_BOOTS        = new ItemRailcraftArmor(RailcraftArmorMaterials.STEEL, ArmorItem.Type.BOOTS,      props());

    // -------------------------------------------------------------------------
    // Fuel & consumables
    // -------------------------------------------------------------------------
    public static final Item COKE               = new ItemCoke(props());
    public static final Item COAL_COKE          = new ItemCoke(props());
    public static final Item CREOSOTE_BUCKET    = new Item(props().stacksTo(1));

    // -------------------------------------------------------------------------
    // Tickets
    // -------------------------------------------------------------------------
    public static final Item TICKET             = new ItemTicket(props());
    public static final Item TICKET_GOLD        = new ItemTicket(props().rarity(Rarity.UNCOMMON));

    // -------------------------------------------------------------------------
    // Signal items
    // -------------------------------------------------------------------------
    public static final Item CIRCUIT_RECEIVER   = new Item(props());
    public static final Item CIRCUIT_CONTROLLER = new Item(props());
    public static final Item CIRCUIT_SIGNAL     = new Item(props());

    // -------------------------------------------------------------------------
    // Bore heads
    // -------------------------------------------------------------------------
    public static final Item BORE_HEAD_IRON     = new ItemBoreHead(Tiers.IRON,         props());
    public static final Item BORE_HEAD_STEEL    = new ItemBoreHead(RailcraftTiers.STEEL, props());
    public static final Item BORE_HEAD_BRONZE   = new ItemBoreHead(RailcraftTiers.BRONZE, props());
    public static final Item BORE_HEAD_DIAMOND  = new ItemBoreHead(Tiers.DIAMOND,      props());

    // -------------------------------------------------------------------------
    // Miscellaneous
    // -------------------------------------------------------------------------
    public static final Item REBAR              = new Item(props());
    public static final Item CONCRETE_CHUNK     = new Item(props());
    public static final Item TURBINE_BLADE      = new Item(props().stacksTo(1));
    public static final Item TURBINE_DISK       = new Item(props().stacksTo(1));
    public static final Item TURBINE_ROTOR      = new Item(props().stacksTo(1).durability(1200));
    public static final Item FIRESTONE_RAW      = new Item(props().stacksTo(1));
    public static final Item FIRESTONE_CUT      = new Item(props().stacksTo(1));
    public static final Item FIRESTONE_CRACKED  = new Item(props().stacksTo(1));
    public static final Item STONE_CARVER       = new Item(props().stacksTo(1));

    // -------------------------------------------------------------------------
    // Registration
    // -------------------------------------------------------------------------
    public static void register() {
        reg("cart_basic",           CART_BASIC);
        reg("cart_chest",           CART_CHEST);
        reg("cart_hopper",          CART_HOPPER);
        reg("cart_tnt",             CART_TNT);
        reg("cart_tank",            CART_TANK);
        reg("locomotive_steam",     LOCOMOTIVE_STEAM);
        reg("locomotive_electric",  LOCOMOTIVE_ELECTRIC);
        reg("tunnel_bore_item",     TUNNEL_BORE_ITEM);

        reg("steel_ingot",          STEEL_INGOT);
        reg("steel_nugget",         STEEL_NUGGET);
        reg("steel_plate",          STEEL_PLATE);
        reg("steel_gear",           STEEL_GEAR);
        reg("tin_ingot",            TIN_INGOT);
        reg("tin_nugget",           TIN_NUGGET);
        reg("tin_plate",            TIN_PLATE);
        reg("tin_gear",             TIN_GEAR);
        reg("lead_ingot",           LEAD_INGOT);
        reg("lead_nugget",          LEAD_NUGGET);
        reg("lead_plate",           LEAD_PLATE);
        reg("lead_gear",            LEAD_GEAR);
        reg("silver_ingot",         SILVER_INGOT);
        reg("silver_nugget",        SILVER_NUGGET);
        reg("silver_plate",         SILVER_PLATE);
        reg("silver_gear",          SILVER_GEAR);
        reg("bronze_ingot",         BRONZE_INGOT);
        reg("bronze_nugget",        BRONZE_NUGGET);
        reg("bronze_plate",         BRONZE_PLATE);
        reg("bronze_gear",          BRONZE_GEAR);
        reg("nickel_ingot",         NICKEL_INGOT);
        reg("nickel_nugget",        NICKEL_NUGGET);
        reg("nickel_plate",         NICKEL_PLATE);
        reg("nickel_gear",          NICKEL_GEAR);
        reg("invar_ingot",          INVAR_INGOT);
        reg("invar_nugget",         INVAR_NUGGET);
        reg("invar_plate",          INVAR_PLATE);
        reg("invar_gear",           INVAR_GEAR);
        reg("zinc_ingot",           ZINC_INGOT);
        reg("zinc_nugget",          ZINC_NUGGET);
        reg("zinc_plate",           ZINC_PLATE);
        reg("zinc_gear",            ZINC_GEAR);
        reg("brass_ingot",          BRASS_INGOT);
        reg("brass_nugget",         BRASS_NUGGET);
        reg("brass_plate",          BRASS_PLATE);
        reg("brass_gear",           BRASS_GEAR);
        reg("iron_plate",           IRON_PLATE);
        reg("iron_gear",            IRON_GEAR);
        reg("gold_plate",           GOLD_PLATE);
        reg("gold_gear",            GOLD_GEAR);
        reg("copper_plate",         COPPER_PLATE);
        reg("copper_gear",          COPPER_GEAR);

        reg("dust_sulfur",          DUST_SULFUR);
        reg("dust_saltpeter",       DUST_SALTPETER);
        reg("dust_coal",            DUST_COAL);
        reg("dust_charcoal",        DUST_CHARCOAL);
        reg("dust_obsidian",        DUST_OBSIDIAN);

        reg("rail_standard",        RAIL_STANDARD);
        reg("rail_advanced",        RAIL_ADVANCED);
        reg("rail_wooden",          RAIL_WOODEN);
        reg("rail_speed",           RAIL_SPEED);
        reg("rail_reinforced",      RAIL_REINFORCED);
        reg("rail_electric",        RAIL_ELECTRIC);
        reg("tie_wood",             TIE_WOOD);
        reg("tie_stone",            TIE_STONE);
        reg("railbed_wood",         RAILBED_WOOD);
        reg("railbed_stone",        RAILBED_STONE);
        reg("track_parts",          TRACK_PARTS);

        reg("track_kit_activator",      TRACK_KIT_ACTIVATOR);
        reg("track_kit_booster",        TRACK_KIT_BOOSTER);
        reg("track_kit_buffer_stop",    TRACK_KIT_BUFFER_STOP);
        reg("track_kit_control",        TRACK_KIT_CONTROL);
        reg("track_kit_coupler",        TRACK_KIT_COUPLER);
        reg("track_kit_detector",       TRACK_KIT_DETECTOR);
        reg("track_kit_disembarking",   TRACK_KIT_DISEMBARKING);
        reg("track_kit_dumping",        TRACK_KIT_DUMPING);
        reg("track_kit_embarking",      TRACK_KIT_EMBARKING);
        reg("track_kit_gated",          TRACK_KIT_GATED);
        reg("track_kit_gated_one_way",  TRACK_KIT_GATED_ONE_WAY);
        reg("track_kit_junction",       TRACK_KIT_JUNCTION);
        reg("track_kit_locking",        TRACK_KIT_LOCKING);
        reg("track_kit_locomotive",     TRACK_KIT_LOCOMOTIVE);
        reg("track_kit_one_way",        TRACK_KIT_ONE_WAY);
        reg("track_kit_priming",        TRACK_KIT_PRIMING);
        reg("track_kit_routing",        TRACK_KIT_ROUTING);
        reg("track_kit_slow",           TRACK_KIT_SLOW);
        reg("track_kit_suspended",      TRACK_KIT_SUSPENDED);
        reg("track_kit_tnt",            TRACK_KIT_TNT);
        reg("track_kit_turnout",        TRACK_KIT_TURNOUT);
        reg("track_kit_whistle",        TRACK_KIT_WHISTLE);
        reg("track_kit_worldspike",     TRACK_KIT_WORLDSPIKE);
        reg("track_kit_throttle",       TRACK_KIT_THROTTLE);

        reg("crowbar_iron",         CROWBAR_IRON);
        reg("crowbar_steel",        CROWBAR_STEEL);
        reg("crowbar_diamond",      CROWBAR_DIAMOND);
        reg("crowbar_seasons",      CROWBAR_SEASONS);
        reg("spike_maul_iron",      SPIKE_MAUL_IRON);
        reg("spike_maul_steel",     SPIKE_MAUL_STEEL);
        reg("goggles",              GOGGLES);
        reg("magnifying_glass",     MAGNIFYING_GLASS);
        reg("notepad",              NOTEPAD);
        reg("overalls",             OVERALLS);
        reg("signal_tuner",         SIGNAL_TUNER);
        reg("signal_label",         SIGNAL_LABEL);
        reg("pairing_tool",         PAIRING_TOOL);
        reg("routing_table",        ROUTING_TABLE);
        reg("charge_meter",         CHARGE_METER);
        reg("signal_lamp",          SIGNAL_LAMP);
        reg("signal_block_surveyor", SIGNAL_BLOCK_SURVEYOR);

        reg("steel_sword",          STEEL_SWORD);
        reg("steel_shovel",         STEEL_SHOVEL);
        reg("steel_pickaxe",        STEEL_PICKAXE);
        reg("steel_axe",            STEEL_AXE);
        reg("steel_hoe",            STEEL_HOE);
        reg("steel_shears",         STEEL_SHEARS);
        reg("steel_helmet",         STEEL_HELMET);
        reg("steel_chestplate",     STEEL_CHESTPLATE);
        reg("steel_leggings",       STEEL_LEGGINGS);
        reg("steel_boots",          STEEL_BOOTS);

        reg("coke",                 COKE);
        reg("coal_coke",            COAL_COKE);
        reg("creosote_bucket",      CREOSOTE_BUCKET);

        reg("ticket",               TICKET);
        reg("ticket_gold",          TICKET_GOLD);

        reg("circuit_receiver",     CIRCUIT_RECEIVER);
        reg("circuit_controller",   CIRCUIT_CONTROLLER);
        reg("circuit_signal",       CIRCUIT_SIGNAL);

        reg("bore_head_iron",       BORE_HEAD_IRON);
        reg("bore_head_steel",      BORE_HEAD_STEEL);
        reg("bore_head_bronze",     BORE_HEAD_BRONZE);
        reg("bore_head_diamond",    BORE_HEAD_DIAMOND);

        reg("rebar",                REBAR);
        reg("concrete_chunk",       CONCRETE_CHUNK);
        reg("turbine_blade",        TURBINE_BLADE);
        reg("turbine_disk",         TURBINE_DISK);
        reg("turbine_rotor",        TURBINE_ROTOR);
        reg("firestone_raw",        FIRESTONE_RAW);
        reg("firestone_cut",        FIRESTONE_CUT);
        reg("firestone_cracked",    FIRESTONE_CRACKED);
        reg("stone_carver",         STONE_CARVER);
    }

    private static void reg(String name, Item item) {
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(RailcraftMod.MOD_ID, name), item);
    }

    private static Item.Properties props() {
        return new Item.Properties();
    }

    private RailcraftItems() {}
}
