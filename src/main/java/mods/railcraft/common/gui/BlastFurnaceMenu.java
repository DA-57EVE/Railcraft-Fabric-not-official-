package mods.railcraft.common.gui;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class BlastFurnaceMenu extends AbstractContainerMenu {

    // Slots: 0=iron, 1=coke fuel, 2=steel out, 3=slag out; 4-30=player inv; 31-39=hotbar
    private final Container container;
    private final ContainerData data;

    public BlastFurnaceMenu(int syncId, Inventory playerInv) {
        this(syncId, playerInv, new SimpleContainer(4), new SimpleContainerData(4));
    }

    public BlastFurnaceMenu(int syncId, Inventory playerInv, Container container, ContainerData data) {
        super(RailcraftMenuTypes.BLAST_FURNACE, syncId);
        this.container = container;
        this.data = data;
        checkContainerSize(container, 4);
        checkContainerDataCount(data, 4);

        addSlot(new Slot(container, 0, 56, 17));   // iron input
        addSlot(new Slot(container, 1, 56, 53));   // coke fuel
        addSlot(new Slot(container, 2, 116, 35));  // steel output
        addSlot(new Slot(container, 3, 116, 53));  // slag output

        addPlayerInventory(playerInv);
        addDataSlots(data);
    }

    private void addPlayerInventory(Inventory inv) {
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
        for (int col = 0; col < 9; col++)
            addSlot(new Slot(inv, col, 8 + col * 18, 142));
    }

    @Override
    public boolean stillValid(Player player) { return container.stillValid(player); }

    public int getScaledArrow(int width) {
        int t = data.get(0), total = data.get(1);
        return total > 0 ? t * width / total : 0;
    }

    public int getScaledFlame(int height) {
        int burn = data.get(2), total = data.get(3);
        return total > 0 ? burn * height / total : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem(), original = stack.copy();

        if (index < 4) {
            if (!moveItemStackTo(stack, 4, 40, true)) return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(stack, 0, 2, false)) {
                if (index < 31) { if (!moveItemStackTo(stack, 31, 40, false)) return ItemStack.EMPTY; }
                else             { if (!moveItemStackTo(stack,  4, 31, false)) return ItemStack.EMPTY; }
            }
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();
        if (stack.getCount() == original.getCount()) return ItemStack.EMPTY;
        slot.onTake(player, stack);
        return original;
    }
}
