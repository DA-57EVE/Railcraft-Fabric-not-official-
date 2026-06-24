package mods.railcraft.common.blocks.machine.equipment;

import mods.railcraft.common.gui.RollingMachineMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

import static mods.railcraft.common.blocks.RailcraftBlocks.ROLLING_MACHINE_BLOCK_ENTITY;

public class TileRollingMachine extends BaseContainerBlockEntity {

    private static final int CRAFT_GRID_SIZE = 9;
    private static final int OUTPUT_SLOT     = 9;
    private static final int SIZE            = 10;
    private static final int ROLL_TIME_TOTAL = 100;

    private NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
    private int rollTime  = 0;
    private boolean running = false;

    private final ContainerData syncData = new ContainerData() {
        @Override public int get(int i) { return switch (i) { case 0 -> rollTime; case 1 -> ROLL_TIME_TOTAL; default -> 0; }; }
        @Override public void set(int i, int v) { if (i == 0) rollTime = v; }
        @Override public int getCount() { return 2; }
    };

    // Dummy menu needed for TransientCraftingContainer
    private static final AbstractContainerMenu DUMMY_MENU = new AbstractContainerMenu(null, -1) {
        @Override public ItemStack quickMoveStack(Player p, int i) { return ItemStack.EMPTY; }
        @Override public boolean stillValid(Player p) { return false; }
    };

    public TileRollingMachine(BlockPos pos, BlockState state) {
        super(ROLLING_MACHINE_BLOCK_ENTITY, pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TileRollingMachine tile) {
        tile.tick(level, pos, state);
    }

    private void tick(Level level, BlockPos pos, BlockState state) {
        if (canRoll(level)) {
            rollTime++;
            running = true;
            if (rollTime >= ROLL_TIME_TOTAL) {
                rollTime = 0;
                roll(level);
            }
        } else {
            rollTime  = 0;
            running   = false;
        }
        setChanged();
    }

    private boolean canRoll(Level level) {
        return findRecipe(level).isPresent() && items.get(OUTPUT_SLOT).isEmpty();
    }

    private Optional<CraftingRecipe> findRecipe(Level level) {
        TransientCraftingContainer grid = buildGrid();
        return level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, grid, level);
    }

    private void roll(Level level) {
        Optional<CraftingRecipe> recipe = findRecipe(level);
        if (recipe.isEmpty()) return;
        TransientCraftingContainer grid = buildGrid();
        ItemStack result = recipe.get().assemble(grid, level.registryAccess());
        if (result.isEmpty()) return;
        items.set(OUTPUT_SLOT, result);
        for (int i = 0; i < CRAFT_GRID_SIZE; i++) {
            ItemStack s = items.get(i);
            if (!s.isEmpty()) s.shrink(1);
        }
        setChanged();
    }

    private TransientCraftingContainer buildGrid() {
        TransientCraftingContainer grid = new TransientCraftingContainer(DUMMY_MENU, 3, 3);
        for (int i = 0; i < CRAFT_GRID_SIZE; i++) grid.setItem(i, items.get(i).copy());
        return grid;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, items);
        tag.putInt("rollTime", rollTime);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items);
        rollTime = tag.getInt("rollTime");
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.railcraft.rolling_machine");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return new RollingMachineMenu(id, inv, this, syncData);
    }

    @Override
    public int getContainerSize()                    { return SIZE; }

    @Override
    public boolean isEmpty()                         { return items.stream().allMatch(ItemStack::isEmpty); }

    @Override
    public ItemStack getItem(int slot)               { return items.get(slot); }

    @Override
    public ItemStack removeItem(int slot, int count) { return ContainerHelper.removeItem(items, slot, count); }

    @Override
    public ItemStack removeItemNoUpdate(int slot)    { return ContainerHelper.takeItem(items, slot); }

    @Override
    public void setItem(int slot, ItemStack stack)   { items.set(slot, stack); setChanged(); }

    @Override
    public boolean stillValid(Player player) {
        return level != null && level.getBlockEntity(worldPosition) == this
                && player.distanceToSqr(worldPosition.getX() + 0.5,
                worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void clearContent() { items.clear(); }

    public int getRollTime()       { return rollTime; }
    public int getRollTimeTotal()  { return ROLL_TIME_TOTAL; }
    public boolean isRunning()     { return running; }
}
