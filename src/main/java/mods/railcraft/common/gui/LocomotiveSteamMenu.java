package mods.railcraft.common.gui;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class LocomotiveSteamMenu extends AbstractContainerMenu {

    // Slots 0-8: locomotive inventory; 9-35: player inv; 36-44: hotbar
    // SyncData: 0=burnTime, 1=maxBurn, 2=steam, 3=maxSteam, 4=mode
    private final Container container;
    private final ContainerData data;

    public LocomotiveSteamMenu(int syncId, Inventory playerInv) {
        this(syncId, playerInv, new SimpleContainer(9), new SimpleContainerData(5));
    }

    public LocomotiveSteamMenu(int syncId, Inventory playerInv, Container container, ContainerData data) {
        super(RailcraftMenuTypes.LOCOMOTIVE_STEAM, syncId);
        this.container = container;
        this.data = data;
        checkContainerSize(container, 9);
        checkContainerDataCount(data, 5);

        // Two fuel slots reusing the furnace texture's baked-in slot holes
        addSlot(new Slot(container, 0, 56, 17));  // fuel — top slot (furnace input position)
        addSlot(new Slot(container, 1, 56, 53));  // fuel — bottom slot (furnace fuel position)

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

    public int getScaledFlame(int height) {
        int burn = data.get(0), max = data.get(1);
        return max > 0 ? burn * height / max : 0;
    }

    public int getScaledSteam(int width) {
        int steam = data.get(2), max = data.get(3);
        return max > 0 ? steam * width / max : 0;
    }

    public int getSteam()    { return data.get(2); }
    public int getMaxSteam() { return data.get(3); }
    public int getMode()     { return data.get(4); }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        // Slots: 0-1=fuel, 2-28=player inv, 29-37=hotbar
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem(), original = stack.copy();

        if (index < 2) {
            if (!moveItemStackTo(stack, 2, 38, true)) return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(stack, 0, 2, false)) {
                if (index < 29) { if (!moveItemStackTo(stack, 29, 38, false)) return ItemStack.EMPTY; }
                else             { if (!moveItemStackTo(stack,  2, 29, false)) return ItemStack.EMPTY; }
            }
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();
        if (stack.getCount() == original.getCount()) return ItemStack.EMPTY;
        slot.onTake(player, stack);
        return original;
    }
}
