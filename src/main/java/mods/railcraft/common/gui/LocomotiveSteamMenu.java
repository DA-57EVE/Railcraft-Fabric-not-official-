package mods.railcraft.common.gui;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class LocomotiveSteamMenu extends AbstractContainerMenu {

    // SyncData: 0=burnTime,1=maxBurn,2=steam,3=maxSteam,4=mode,5=speed,6=reverse
    private final Container container;
    private final ContainerData data;

    public LocomotiveSteamMenu(int syncId, Inventory playerInv) {
        this(syncId, playerInv, new SimpleContainer(9), new SimpleContainerData(7));
    }

    public LocomotiveSteamMenu(int syncId, Inventory playerInv, Container container, ContainerData data) {
        super(RailcraftMenuTypes.LOCOMOTIVE_STEAM, syncId);
        this.container = container;
        this.data = data;
        checkContainerSize(container, 9);
        checkContainerDataCount(data, 7);

        // Slot positions match the original Railcraft GUI texture layout
        addSlot(new Slot(container, 0, 116, 20));  // burn slot (currently burning)
        addSlot(new Slot(container, 1,  80, 20));  // fuel bunker A
        addSlot(new Slot(container, 2,  80, 38));  // fuel bunker B
        addSlot(new Slot(container, 3,  80, 56));  // fuel bunker C

        // Player inventory — guiHeight=205: rows at 205-82=123, hotbar at 205-26=179
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 123 + row * 18));
        for (int col = 0; col < 9; col++)
            addSlot(new Slot(playerInv, col, 8 + col * 18, 179));

        addDataSlots(data);
    }

    public Container getLocomotive() { return container; }

    @Override
    public boolean stillValid(Player player) { return container.stillValid(player); }

    public int getScaledFlame(int height) {
        int burn = data.get(0), max = data.get(1);
        return max > 0 ? burn * height / max : 0;
    }

    public int getScaledSteam(int height) {
        int steam = data.get(2), max = data.get(3);
        return max > 0 ? steam * height / max : 0;
    }

    public int getSteam()      { return data.get(2); }
    public int getMaxSteam()   { return data.get(3); }
    public int getMode()       { return data.get(4); }
    public int getSpeed()      { return data.get(5); }
    public boolean isReverse() { return data.get(6) != 0; }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        // 0-3=loco, 4-30=player inv, 31-39=hotbar
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem(), original = stack.copy();

        if (index < 4) {
            if (!moveItemStackTo(stack, 4, 40, true)) return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(stack, 0, 4, false)) {
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
