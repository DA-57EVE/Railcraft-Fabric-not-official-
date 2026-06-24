package mods.railcraft.common.blocks;

import mods.railcraft.RailcraftMod;
import mods.railcraft.common.blocks.aesthetics.brick.*;
import mods.railcraft.common.blocks.aesthetics.concrete.BlockReinforcedConcrete;
import mods.railcraft.common.blocks.aesthetics.glass.BlockStrengthGlass;
import mods.railcraft.common.blocks.aesthetics.lantern.BlockLanternRailcraft;
import mods.railcraft.common.blocks.aesthetics.metals.MetalDecoration;
import mods.railcraft.common.blocks.aesthetics.post.BlockPost;
import mods.railcraft.common.blocks.machine.equipment.BlockRollingMachine;
import mods.railcraft.common.blocks.machine.equipment.TileRollingMachine;
import mods.railcraft.common.blocks.ore.BlockOreMetal;
import mods.railcraft.common.blocks.ore.OreType;
import mods.railcraft.common.blocks.structures.*;
import mods.railcraft.common.blocks.tracks.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.HashMap;
import java.util.Map;

public final class RailcraftBlocks {

    // -------------------------------------------------------------------------
    // Block entity types (registered after blocks)
    // -------------------------------------------------------------------------
    public static BlockEntityType<TileCokeOven>       COKE_OVEN_BLOCK_ENTITY;
    public static BlockEntityType<TileSteamBoiler>    STEAM_BOILER_BLOCK_ENTITY;
    public static BlockEntityType<TileRollingMachine> ROLLING_MACHINE_BLOCK_ENTITY;
    public static BlockEntityType<TileRockCrusher>    ROCK_CRUSHER_BLOCK_ENTITY;
    public static BlockEntityType<TileBlastFurnace>   BLAST_FURNACE_BLOCK_ENTITY;

    // -------------------------------------------------------------------------
    // Tracks
    // -------------------------------------------------------------------------
    public static final BlockFlexTrack TRACK_IRON             = new BlockFlexTrack(TrackTypes.IRON,             trackProps());
    public static final BlockFlexTrack TRACK_ABANDONED         = new BlockFlexTrack(TrackTypes.ABANDONED,        trackProps());
    public static final BlockFlexTrack TRACK_ELECTRIC          = new BlockFlexTrack(TrackTypes.ELECTRIC,         trackProps());
    public static final BlockFlexTrack TRACK_HIGH_SPEED        = new BlockFlexTrack(TrackTypes.HIGH_SPEED,       trackProps());
    public static final BlockFlexTrack TRACK_HS_ELECTRIC       = new BlockFlexTrack(TrackTypes.HIGH_SPEED_ELECTRIC, trackProps());
    public static final BlockFlexTrack TRACK_REINFORCED        = new BlockFlexTrack(TrackTypes.REINFORCED,       trackProps());
    public static final BlockFlexTrack TRACK_STRAP_IRON        = new BlockFlexTrack(TrackTypes.STRAP_IRON,       trackProps());
    public static final BlockElevatorTrack TRACK_ELEVATOR      = new BlockElevatorTrack(trackProps());

    // -------------------------------------------------------------------------
    // Structures / machines
    // -------------------------------------------------------------------------
    public static final BlockCokeOven      COKE_OVEN       = new BlockCokeOven(machineProps());
    public static final BlockSteamBoiler   STEAM_BOILER    = new BlockSteamBoiler(machineProps());
    public static final BlockRollingMachine ROLLING_MACHINE = new BlockRollingMachine(machineProps());
    public static final BlockRockCrusher   ROCK_CRUSHER    = new BlockRockCrusher(machineProps());
    public static final BlockBlastFurnace  BLAST_FURNACE   = new BlockBlastFurnace(machineProps());

    // -------------------------------------------------------------------------
    // Ores
    // -------------------------------------------------------------------------
    public static final BlockOreMetal ORE_SULFUR   = new BlockOreMetal(OreType.SULFUR,    oreProps());
    public static final BlockOreMetal ORE_SALTPETER = new BlockOreMetal(OreType.SALTPETER, oreProps());
    public static final BlockOreMetal ORE_TIN      = new BlockOreMetal(OreType.TIN,       oreProps());
    public static final BlockOreMetal ORE_LEAD     = new BlockOreMetal(OreType.LEAD,      oreProps());
    public static final BlockOreMetal ORE_SILVER   = new BlockOreMetal(OreType.SILVER,    oreProps());
    public static final BlockOreMetal ORE_NICKEL   = new BlockOreMetal(OreType.NICKEL,    oreProps());
    public static final BlockOreMetal ORE_ZINC     = new BlockOreMetal(OreType.ZINC,      oreProps());

    // -------------------------------------------------------------------------
    // Aesthetic - bricks
    // -------------------------------------------------------------------------
    public static final Map<String, BlockBrick> BRICKS = new HashMap<>();

    // -------------------------------------------------------------------------
    // Aesthetic - decoration blocks
    // -------------------------------------------------------------------------
    public static final Block STEEL_BLOCK           = new Block(metalProps());
    public static final Block TIN_BLOCK             = new Block(metalProps());
    public static final Block LEAD_BLOCK            = new Block(metalProps());
    public static final Block SILVER_BLOCK          = new Block(metalProps());
    public static final Block BRONZE_BLOCK          = new Block(metalProps());
    public static final Block NICKEL_BLOCK          = new Block(metalProps());
    public static final Block INVAR_BLOCK           = new Block(metalProps());
    public static final Block ZINC_BLOCK            = new Block(metalProps());
    public static final Block BRASS_BLOCK           = new Block(metalProps());
    public static final Block CREOSOTE_BLOCK        = new Block(woodProps());
    public static final BlockStrengthGlass STRENGTH_GLASS = new BlockStrengthGlass(glassProps());
    public static final BlockReinforcedConcrete REINFORCED_CONCRETE = new BlockReinforcedConcrete(stoneProps().strength(20f, 1200f));
    public static final BlockPost POST_METAL        = new BlockPost(metalProps().noOcclusion());
    public static final BlockPost POST_WOOD         = new BlockPost(woodProps().noOcclusion());
    public static final BlockLanternRailcraft LANTERN_STONE = new BlockLanternRailcraft(stoneProps());
    public static final BlockLanternRailcraft LANTERN_IRON  = new BlockLanternRailcraft(metalProps());

    // -------------------------------------------------------------------------
    // Registration
    // -------------------------------------------------------------------------
    public static void register() {
        // Tracks
        reg("track_iron",          TRACK_IRON);
        reg("track_abandoned",     TRACK_ABANDONED);
        reg("track_electric",      TRACK_ELECTRIC);
        reg("track_high_speed",    TRACK_HIGH_SPEED);
        reg("track_hs_electric",   TRACK_HS_ELECTRIC);
        reg("track_reinforced",    TRACK_REINFORCED);
        reg("track_strap_iron",    TRACK_STRAP_IRON);
        reg("track_elevator",      TRACK_ELEVATOR);

        // Structures / machines
        reg("coke_oven",       COKE_OVEN);
        reg("steam_boiler",    STEAM_BOILER);
        reg("rolling_machine", ROLLING_MACHINE);
        reg("rock_crusher",    ROCK_CRUSHER);
        reg("blast_furnace",   BLAST_FURNACE);

        // Ores
        reg("ore_sulfur",    ORE_SULFUR);
        reg("ore_saltpeter", ORE_SALTPETER);
        reg("ore_tin",       ORE_TIN);
        reg("ore_lead",      ORE_LEAD);
        reg("ore_silver",    ORE_SILVER);
        reg("ore_nickel",    ORE_NICKEL);
        reg("ore_zinc",      ORE_ZINC);

        // Metal decoration
        reg("steel_block",  STEEL_BLOCK);
        reg("tin_block",    TIN_BLOCK);
        reg("lead_block",   LEAD_BLOCK);
        reg("silver_block", SILVER_BLOCK);
        reg("bronze_block", BRONZE_BLOCK);
        reg("nickel_block", NICKEL_BLOCK);
        reg("invar_block",  INVAR_BLOCK);
        reg("zinc_block",   ZINC_BLOCK);
        reg("brass_block",  BRASS_BLOCK);
        reg("creosote_block",    CREOSOTE_BLOCK);
        reg("strength_glass",    STRENGTH_GLASS);
        reg("reinforced_concrete", REINFORCED_CONCRETE);
        reg("post_metal",        POST_METAL);
        reg("post_wood",         POST_WOOD);
        reg("lantern_stone",     LANTERN_STONE);
        reg("lantern_iron",      LANTERN_IRON);

        // Bricks
        for (BrickTheme theme : BrickTheme.values()) {
            for (BrickVariant variant : BrickVariant.values()) {
                String name = "brick_" + theme.getName() + "_" + variant.getName();
                BlockBrick brick = new BlockBrick(theme, variant, stoneProps());
                BRICKS.put(name, brick);
                reg(name, brick);
            }
        }

        // Block entities (must be registered after their blocks)
        COKE_OVEN_BLOCK_ENTITY = regBE("coke_oven",
                FabricBlockEntityTypeBuilder.create(TileCokeOven::new, COKE_OVEN).build());
        STEAM_BOILER_BLOCK_ENTITY = regBE("steam_boiler",
                FabricBlockEntityTypeBuilder.create(TileSteamBoiler::new, STEAM_BOILER).build());
        ROLLING_MACHINE_BLOCK_ENTITY = regBE("rolling_machine",
                FabricBlockEntityTypeBuilder.create(TileRollingMachine::new, ROLLING_MACHINE).build());
        ROCK_CRUSHER_BLOCK_ENTITY = regBE("rock_crusher",
                FabricBlockEntityTypeBuilder.create(TileRockCrusher::new, ROCK_CRUSHER).build());
        BLAST_FURNACE_BLOCK_ENTITY = regBE("blast_furnace",
                FabricBlockEntityTypeBuilder.create(TileBlastFurnace::new, BLAST_FURNACE).build());
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private static void reg(String name, Block block) {
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(RailcraftMod.MOD_ID, name), block);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(RailcraftMod.MOD_ID, name),
                new BlockItem(block, new Item.Properties()));
    }

    private static <T extends net.minecraft.world.level.block.entity.BlockEntity>
    BlockEntityType<T> regBE(String name, BlockEntityType<T> type) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,
                new ResourceLocation(RailcraftMod.MOD_ID, name), type);
    }

    private static BlockBehaviour.Properties trackProps() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .noCollission()
                .strength(0.7f)
                .sound(SoundType.METAL);
    }

    private static BlockBehaviour.Properties machineProps() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .requiresCorrectToolForDrops()
                .strength(5.0f, 10.0f)
                .sound(SoundType.METAL);
    }

    private static BlockBehaviour.Properties oreProps() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .requiresCorrectToolForDrops()
                .strength(3.0f, 3.0f)
                .sound(SoundType.STONE);
    }

    private static BlockBehaviour.Properties metalProps() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .requiresCorrectToolForDrops()
                .strength(5.0f, 10.0f)
                .sound(SoundType.METAL);
    }

    private static BlockBehaviour.Properties stoneProps() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.STONE)
                .requiresCorrectToolForDrops()
                .strength(2.0f, 10.0f)
                .sound(SoundType.STONE);
    }

    private static BlockBehaviour.Properties woodProps() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.WOOD)
                .strength(2.0f, 5.0f)
                .sound(SoundType.WOOD);
    }

    private static BlockBehaviour.Properties glassProps() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.NONE)
                .strength(3.0f, 1200.0f)
                .noOcclusion()
                .sound(SoundType.GLASS);
    }

    private RailcraftBlocks() {}
}
