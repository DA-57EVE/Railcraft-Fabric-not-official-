package mods.railcraft.common.gui;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class SteamBoilerMenu extends AbstractContainerMenu {

    // Slots: 0=fuel, 1=water; 2-28=player inv; 29-37=hotbar
    // Data: 0=burnTime, 1=fuelBurnTime, 2=heat*10, 3=maxHeat*10, 4=steamAmount, 5=maxSteam
    private final Container container;
    private final ContainerData data;

    public SteamBoilerMenu(int syncId, Inventory playerInv) {
        this(syncId, playerInv, new SimpleContainer(2), new SimpleContainerData(6));
    }

    public SteamBoilerMenu(int syncId, Inventory playerInv, Container container, ContainerData data) {
        super(RailcraftMenuTypes.STEAM_BOILER, syncId);
        this.container = container;
        this.data = data;
        checkContainerSize(container, 2);
        checkContainerDataCount(data, 6);

        addSlot(new Slot(container, 0, 56, 53));  // fuel
        addSlot(new Slot(container, 1, 56, 17));  // water input

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
        int burn = data.get(0), total = data.get(1);
        return total > 0 ? burn * height / total : 0;
    }

    public int getHeatScaled(int height) {
        int heat = data.get(2), max = data.get(3);
        return max > 0 ? heat * height / max : 0;
    }

    public int getSteamScaled(int height) {
        int steam = data.get(4), max = data.get(5);
        return max > 0 ? steam * height / max : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
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
