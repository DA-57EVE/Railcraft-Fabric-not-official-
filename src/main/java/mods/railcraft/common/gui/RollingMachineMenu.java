package mods.railcraft.common.gui;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class RollingMachineMenu extends AbstractContainerMenu {

    // Slots 0-8: 3×3 craft grid; slot 9: output; 10-36: player inv; 37-45: hotbar
    private final Container container;
    private final ContainerData data;

    public RollingMachineMenu(int syncId, Inventory playerInv) {
        this(syncId, playerInv, new SimpleContainer(10), new SimpleContainerData(2));
    }

    public RollingMachineMenu(int syncId, Inventory playerInv, Container container, ContainerData data) {
        super(RailcraftMenuTypes.ROLLING_MACHINE, syncId);
        this.container = container;
        this.data = data;
        checkContainerSize(container, 10);
        checkContainerDataCount(data, 2);

        // 3×3 grid (matches crafting_table.png slot positions)
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 3; col++)
                addSlot(new Slot(container, col + row * 3, 30 + col * 18, 17 + row * 18));

        // Output slot
        addSlot(new Slot(container, 9, 124, 35));

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

    public int getScaledProgress(int width) {
        int t = data.get(0), total = data.get(1);
        return total > 0 ? t * width / total : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem(), original = stack.copy();

        if (index == 9) {
            // Output -> player inventory
            if (!moveItemStackTo(stack, 10, 46, true)) return ItemStack.EMPTY;
        } else if (index >= 10) {
            // Player inventory -> craft grid
            if (!moveItemStackTo(stack, 0, 9, false)) {
                if (index < 37) { if (!moveItemStackTo(stack, 37, 46, false)) return ItemStack.EMPTY; }
                else             { if (!moveItemStackTo(stack, 10, 37, false)) return ItemStack.EMPTY; }
            }
        } else {
            // Grid slot -> player inventory
            if (!moveItemStackTo(stack, 10, 46, true)) return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();
        if (stack.getCount() == original.getCount()) return ItemStack.EMPTY;
        slot.onTake(player, stack);
        return original;
    }
}
