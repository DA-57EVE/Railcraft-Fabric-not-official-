package mods.railcraft.common.blocks.structures;

import mods.railcraft.common.gui.BlastFurnaceMenu;
import mods.railcraft.common.items.RailcraftItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static mods.railcraft.common.blocks.RailcraftBlocks.BLAST_FURNACE_BLOCK_ENTITY;

/**
 * The blast furnace converts iron + coke into steel ingots.
 * Smelt time: 600 ticks (30 s) per iron ingot → 1 steel ingot + slag.
 */
public class TileBlastFurnace extends BaseContainerBlockEntity {

    private static final int SLOT_IRON  = 0;
    private static final int SLOT_COKE  = 1;
    private static final int SLOT_STEEL = 2;
    private static final int SLOT_SLAG  = 3;
    private static final int SIZE       = 4;
    private static final int SMELT_TIME = 600;

    private NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
    private int smeltTime = 0;
    private int burnTime  = 0;
    private int currentFuelBurnTime = 0;

    private final ContainerData syncData = new ContainerData() {
        @Override public int get(int i) {
            return switch (i) { case 0 -> smeltTime; case 1 -> SMELT_TIME; case 2 -> burnTime; case 3 -> currentFuelBurnTime; default -> 0; };
        }
        @Override public void set(int i, int v) {
            switch (i) { case 0 -> smeltTime = v; case 2 -> burnTime = v; case 3 -> currentFuelBurnTime = v; }
        }
        @Override public int getCount() { return 4; }
    };

    public TileBlastFurnace(BlockPos pos, BlockState state) {
        super(BLAST_FURNACE_BLOCK_ENTITY, pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TileBlastFurnace tile) {
        tile.tick(level, pos, state);
    }

    private void tick(Level level, BlockPos pos, BlockState state) {
        boolean wasLit = state.getValue(BlockBlastFurnace.LIT);

        if (burnTime > 0) {
            burnTime--;
        } else {
            ItemStack coke = items.get(SLOT_COKE);
            if (!coke.isEmpty() && (coke.is(RailcraftItems.COKE) || coke.is(RailcraftItems.COAL_COKE))) {
                currentFuelBurnTime = burnTime = 3200;
                coke.shrink(1);
                setChanged();
            }
        }

        boolean canSmelt = canSmelt();
        if (burnTime > 0 && canSmelt) {
            smeltTime++;
            if (smeltTime >= SMELT_TIME) {
                smeltTime = 0;
                smelt();
            }
        } else if (!canSmelt) {
            smeltTime = 0;
        }

        boolean isLit = burnTime > 0 && canSmelt;
        if (isLit != wasLit) {
            level.setBlock(pos, state.setValue(BlockBlastFurnace.LIT, isLit), 3);
        }
    }

    private boolean canSmelt() {
        ItemStack iron = items.get(SLOT_IRON);
        if (iron.isEmpty() || !iron.is(Items.IRON_INGOT)) return false;
        ItemStack steel = items.get(SLOT_STEEL);
        return steel.isEmpty() || (steel.is(RailcraftItems.STEEL_INGOT) && steel.getCount() < steel.getMaxStackSize());
    }

    private void smelt() {
        if (!canSmelt()) return;
        items.get(SLOT_IRON).shrink(1);
        ItemStack steelOut = items.get(SLOT_STEEL);
        if (steelOut.isEmpty()) {
            items.set(SLOT_STEEL, new ItemStack(RailcraftItems.STEEL_INGOT));
        } else {
            steelOut.grow(1);
        }
        // Produce slag (use gravel as placeholder until slag item exists)
        ItemStack slagOut = items.get(SLOT_SLAG);
        if (slagOut.isEmpty()) {
            items.set(SLOT_SLAG, new ItemStack(Items.GRAVEL));
        } else if (slagOut.is(Items.GRAVEL) && slagOut.getCount() < slagOut.getMaxStackSize()) {
            slagOut.grow(1);
        }
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, items);
        tag.putInt("smeltTime", smeltTime);
        tag.putInt("burnTime", burnTime);
        tag.putInt("currentFuelBurnTime", currentFuelBurnTime);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items);
        smeltTime = tag.getInt("smeltTime");
        burnTime  = tag.getInt("burnTime");
        currentFuelBurnTime = tag.getInt("currentFuelBurnTime");
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.railcraft.blast_furnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return new BlastFurnaceMenu(id, inv, this, syncData);
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
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return level != null && level.getBlockEntity(worldPosition) == this
                && player.distanceToSqr(worldPosition.getX() + 0.5,
                worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void clearContent() { items.clear(); }

    public int getSmeltTime()              { return smeltTime; }
    public int getSmeltTimeTotal()         { return SMELT_TIME; }
    public int getBurnTime()               { return burnTime; }
    public int getCurrentFuelBurnTime()    { return currentFuelBurnTime; }
}
