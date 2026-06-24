package mods.railcraft.common.blocks.structures;

import mods.railcraft.common.gui.SteamBoilerMenu;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static mods.railcraft.common.blocks.RailcraftBlocks.STEAM_BOILER_BLOCK_ENTITY;

public class TileSteamBoiler extends BaseContainerBlockEntity {

    private static final int SLOT_FUEL = 0;
    private static final int SIZE = 2;

    private NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
    private double heat = 0.0;
    private double maxHeat = 500.0;
    private double steamAmount = 0.0;
    private static final double MAX_STEAM = 16000.0;

    private int burnTime = 0;
    private int fuelBurnTime = 0;

    private final ContainerData syncData = new ContainerData() {
        @Override public int get(int i) {
            return switch (i) {
                case 0 -> burnTime;
                case 1 -> fuelBurnTime;
                case 2 -> (int)(heat * 10);
                case 3 -> (int)(maxHeat * 10);
                case 4 -> (int)steamAmount;
                case 5 -> (int)MAX_STEAM;
                default -> 0;
            };
        }
        @Override public void set(int i, int v) {
            switch (i) {
                case 0 -> burnTime = v;
                case 1 -> fuelBurnTime = v;
                case 2 -> heat = v / 10.0;
                case 4 -> steamAmount = v;
            }
        }
        @Override public int getCount() { return 6; }
    };

    public TileSteamBoiler(BlockPos pos, BlockState state) {
        super(STEAM_BOILER_BLOCK_ENTITY, pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TileSteamBoiler tile) {
        tile.tick(level, pos, state);
    }

    private void tick(Level level, BlockPos pos, BlockState state) {
        if (burnTime > 0) {
            burnTime--;
            if (heat < maxHeat) heat += 0.5;
        } else {
            ItemStack fuel = items.get(SLOT_FUEL);
            int fuelValue = getFuelValue(fuel);
            if (fuelValue > 0 && heat < maxHeat) {
                fuel.shrink(1);
                burnTime = fuelBurnTime = fuelValue;
                setChanged();
            } else if (heat > 0) {
                heat -= 0.5;
            }
        }

        if (heat >= 100.0 && steamAmount < MAX_STEAM) {
            double steamPerTick = (heat / maxHeat) * 4.0;
            steamAmount = Math.min(MAX_STEAM, steamAmount + steamPerTick);
            setChanged();
        }
    }

    private int getFuelValue(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        if (stack.is(RailcraftItems.COKE) || stack.is(RailcraftItems.COAL_COKE)) return 3200;
        return net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
                .getFuel().getOrDefault(stack.getItem(), 0);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, items);
        tag.putDouble("heat", heat);
        tag.putDouble("steam", steamAmount);
        tag.putInt("burnTime", burnTime);
        tag.putInt("fuelBurnTime", fuelBurnTime);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items);
        heat = tag.getDouble("heat");
        steamAmount = tag.getDouble("steam");
        burnTime = tag.getInt("burnTime");
        fuelBurnTime = tag.getInt("fuelBurnTime");
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.railcraft.steam_boiler");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return new SteamBoilerMenu(id, inv, this, syncData);
    }

    @Override
    public int getContainerSize() { return SIZE; }

    @Override
    public boolean isEmpty() { return items.stream().allMatch(ItemStack::isEmpty); }

    @Override
    public ItemStack getItem(int slot)                  { return items.get(slot); }

    @Override
    public ItemStack removeItem(int slot, int count)    { return ContainerHelper.removeItem(items, slot, count); }

    @Override
    public ItemStack removeItemNoUpdate(int slot)       { return ContainerHelper.takeItem(items, slot); }

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

    public double getHeat()        { return heat; }
    public double getMaxHeat()     { return maxHeat; }
    public double getSteamAmount() { return steamAmount; }
    public int getBurnTime()       { return burnTime; }
    public int getFuelBurnTime()   { return fuelBurnTime; }

    public boolean extractSteam(double amount) {
        if (steamAmount >= amount) {
            steamAmount -= amount;
            setChanged();
            return true;
        }
        return false;
    }
}
