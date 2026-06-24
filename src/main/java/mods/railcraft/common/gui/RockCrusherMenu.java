package mods.railcraft.common.gui;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class RockCrusherMenu extends AbstractContainerMenu {

    // Slots: 0=input, 1=output1, 2=output2; 3-29=player inv; 30-38=hotbar
    private final Container container;
    private final ContainerData data;

    public RockCrusherMenu(int syncId, Inventory playerInv) {
        this(syncId, playerInv, new SimpleContainer(3), new SimpleContainerData(2));
    }

    public RockCrusherMenu(int syncId, Inventory playerInv, Container container, ContainerData data) {
        super(RailcraftMenuTypes.ROCK_CRUSHER, syncId);
        this.container = container;
        this.data = data;
        checkContainerSize(container, 3);
        checkContainerDataCount(data, 2);

        addSlot(new Slot(container, 0, 56, 17));   // input
        addSlot(new Slot(container, 1, 116, 35));  // output 1
        addSlot(new Slot(container, 2, 116, 53));  // output 2

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

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem(), original = stack.copy();

        if (index == 1 || index == 2) {
            if (!moveItemStackTo(stack, 3, 39, true)) return ItemStack.EMPTY;
        } else if (index >= 3) {
            if (!moveItemStackTo(stack, 0, 1, false)) {
                if (index < 30) { if (!moveItemStackTo(stack, 30, 39, false)) return ItemStack.EMPTY; }
                else             { if (!moveItemStackTo(stack,  3, 30, false)) return ItemStack.EMPTY; }
            }
        } else {
            if (!moveItemStackTo(stack, 3, 39, true)) return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();
        if (stack.getCount() == original.getCount()) return ItemStack.EMPTY;
        slot.onTake(player, stack);
        return original;
    }
}
