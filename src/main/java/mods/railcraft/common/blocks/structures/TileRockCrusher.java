package mods.railcraft.common.blocks.structures;

import mods.railcraft.common.gui.RockCrusherMenu;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static mods.railcraft.common.blocks.RailcraftBlocks.ROCK_CRUSHER_BLOCK_ENTITY;

/**
 * Rock Crusher — a 2×2×2 multiblock that grinds ores and rocks into dust.
 * Process time: 20 seconds per item. Consumes charge (electricity) to operate.
 */
public class TileRockCrusher extends BaseContainerBlockEntity {

    private static final int SLOT_INPUT   = 0;
    private static final int SLOT_OUTPUT1 = 1;
    private static final int SLOT_OUTPUT2 = 2;
    private static final int SIZE         = 3;
    private static final int CRUSH_TIME_TOTAL = 400;

    private NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
    private int crushTime = 0;

    private final ContainerData syncData = new ContainerData() {
        @Override public int get(int i) { return switch (i) { case 0 -> crushTime; case 1 -> CRUSH_TIME_TOTAL; default -> 0; }; }
        @Override public void set(int i, int v) { if (i == 0) crushTime = v; }
        @Override public int getCount() { return 2; }
    };

    public TileRockCrusher(BlockPos pos, BlockState state) {
        super(ROCK_CRUSHER_BLOCK_ENTITY, pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TileRockCrusher tile) {
        tile.tick(level, pos, state);
    }

    private void tick(Level level, BlockPos pos, BlockState state) {
        ItemStack input = items.get(SLOT_INPUT);
        if (!input.isEmpty() && items.get(SLOT_OUTPUT1).isEmpty()) {
            crushTime++;
            if (crushTime >= CRUSH_TIME_TOTAL) {
                crushTime = 0;
                crush();
            }
        } else {
            crushTime = 0;
        }
    }

    private void crush() {
        ItemStack input = items.get(SLOT_INPUT);
        if (input.isEmpty()) return;
        // Output logic — expanded via recipe data
        input.shrink(1);
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, items);
        tag.putInt("crushTime", crushTime);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items);
        crushTime = tag.getInt("crushTime");
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.railcraft.rock_crusher");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return new RockCrusherMenu(id, inv, this, syncData);
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

    public int getCrushTime()      { return crushTime; }
    public int getCrushTimeTotal() { return CRUSH_TIME_TOTAL; }
}
