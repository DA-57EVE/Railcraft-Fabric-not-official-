package mods.railcraft.common.blocks.structures;

import mods.railcraft.common.gui.CokeOvenMenu;
import mods.railcraft.common.items.RailcraftItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

import static mods.railcraft.common.blocks.RailcraftBlocks.COKE_OVEN_BLOCK_ENTITY;

public class TileCokeOven extends BaseContainerBlockEntity implements WorldlyContainer {

    // Slots: 0 = coal input, 1 = coke output, 2 = creosote output (bucket)
    private static final int SLOT_COAL     = 0;
    private static final int SLOT_COKE     = 1;
    private static final int SLOT_CREOSOTE = 2;
    private static final int SIZE          = 3;

    private static final int COOK_TIME_TOTAL = 1800; // 90 seconds

    private static final int[] SLOTS_TOP   = {SLOT_COAL};
    private static final int[] SLOTS_SIDE  = {SLOT_COAL};
    private static final int[] SLOTS_BOTTOM = {SLOT_COKE, SLOT_CREOSOTE};

    private NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
    private int cookTime = 0;
    private int creosoteAmount = 0;

    private final ContainerData syncData = new ContainerData() {
        @Override public int get(int i) {
            return switch (i) { case 0 -> cookTime; case 1 -> COOK_TIME_TOTAL; case 2 -> creosoteAmount; default -> 0; };
        }
        @Override public void set(int i, int v) { if (i == 0) cookTime = v; else if (i == 2) creosoteAmount = v; }
        @Override public int getCount() { return 3; }
    };

    public TileCokeOven(BlockPos pos, BlockState state) {
        super(COKE_OVEN_BLOCK_ENTITY, pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TileCokeOven tile) {
        tile.tick(level, pos, state);
    }

    private void tick(Level level, BlockPos pos, BlockState state) {
        ItemStack coalStack = items.get(SLOT_COAL);
        boolean wasLit = state.getValue(BlockCokeOven.LIT);

        if (canSmelt()) {
            cookTime++;
            if (cookTime >= COOK_TIME_TOTAL) {
                cookTime = 0;
                smelt();
                setChanged();
            }
        } else {
            cookTime = 0;
        }

        boolean isLit = canSmelt();
        if (isLit != wasLit) {
            level.setBlock(pos, state.setValue(BlockCokeOven.LIT, isLit), 3);
        }
    }

    private boolean canSmelt() {
        ItemStack input = items.get(SLOT_COAL);
        if (input.isEmpty()) return false;
        if (!isCoal(input)) return false;

        ItemStack cokeSlot = items.get(SLOT_COKE);
        return cokeSlot.isEmpty() || (cokeSlot.is(RailcraftItems.COKE) && cokeSlot.getCount() < cokeSlot.getMaxStackSize());
    }

    private boolean isCoal(ItemStack stack) {
        return stack.is(Items.COAL);
    }

    private void smelt() {
        if (!canSmelt()) return;
        items.get(SLOT_COAL).shrink(1);

        ItemStack cokeSlot = items.get(SLOT_COKE);
        if (cokeSlot.isEmpty()) {
            items.set(SLOT_COKE, new ItemStack(RailcraftItems.COKE));
        } else {
            cokeSlot.grow(1);
        }
        creosoteAmount += 500;
    }

    public void drops(Level level, BlockPos pos) {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) {
                net.minecraft.world.Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
        }
    }

    // --- NBT ---

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, items);
        tag.putInt("cookTime", cookTime);
        tag.putInt("creosoteAmount", creosoteAmount);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items);
        cookTime = tag.getInt("cookTime");
        creosoteAmount = tag.getInt("creosoteAmount");
    }

    // --- Container ---

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.railcraft.coke_oven");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return new CokeOvenMenu(id, inv, this, syncData);
    }

    @Override
    public int getContainerSize() { return SIZE; }

    @Override
    public boolean isEmpty() { return items.stream().allMatch(ItemStack::isEmpty); }

    @Override
    public ItemStack getItem(int slot) { return items.get(slot); }

    @Override
    public ItemStack removeItem(int slot, int count) {
        return ContainerHelper.removeItem(items, slot, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) stack.setCount(getMaxStackSize());
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return level != null && level.getBlockEntity(worldPosition) == this
                && player.distanceToSqr(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5,
                worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void clearContent() { items.clear(); }

    // --- WorldlyContainer ---

    @Override
    public int[] getSlotsForFace(Direction side) {
        return switch (side) {
            case DOWN  -> SLOTS_BOTTOM;
            case UP    -> SLOTS_TOP;
            default    -> SLOTS_SIDE;
        };
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
        return slot == SLOT_COAL && isCoal(stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return slot == SLOT_COKE || slot == SLOT_CREOSOTE;
    }

    public int getCookTime()       { return cookTime; }
    public int getCookTimeTotal()  { return COOK_TIME_TOTAL; }
    public int getCreosoteAmount() { return creosoteAmount; }
}
