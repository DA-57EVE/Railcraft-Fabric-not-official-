package mods.railcraft.client.gui;

import mods.railcraft.common.gui.LocomotiveSteamMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LocomotiveSteamScreen extends AbstractContainerScreen<LocomotiveSteamMenu> {

    private static final ResourceLocation BG =
            new ResourceLocation("minecraft", "textures/gui/container/furnace.png");

    private static final String[] MODE_LABELS = { "Running", "Idle", "Shutdown" };
    private static final int[]    MODE_COLORS  = { 0x00AA00, 0xAAAA00, 0xAA0000 };

    public LocomotiveSteamScreen(LocomotiveSteamMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
    }

    @Override
    public void render(GuiGraphics g, int mx, int my, float dt) {
        renderBackground(g);
        super.render(g, mx, my, dt);
        renderTooltip(g, mx, my);
    }

    @Override
    protected void renderBg(GuiGraphics g, float dt, int mx, int my) {
        g.blit(BG, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        // Flame indicator — draws upward (furnace UV: 176,0; 14×13)
        int flame = menu.getScaledFlame(13);
        if (flame > 0) {
            int yOff = 13 - flame;
            g.blit(BG, leftPos + 57, topPos + 36 + yOff, 176, yOff, 14, flame);
        }

        // Steam as the furnace arrow (UV: 176,14; 24×16) — scaled by steam level
        int steamArrow = menu.getScaledSteam(24);
        if (steamArrow > 0) {
            g.blit(BG, leftPos + 79, topPos + 34, 176, 14, steamArrow, 16);
        }

        // Wide steam bar in gap between machine area and player inventory (y=56..64)
        int steamBar = menu.getScaledSteam(152);
        g.fill(leftPos + 8, topPos + 56, leftPos + 160, topPos + 64, 0xFF222222);
        if (steamBar > 0)
            g.fill(leftPos + 8, topPos + 56, leftPos + 8 + steamBar, topPos + 64, 0xFF3399FF);
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mx, int my) {
        g.drawString(font, title, (imageWidth - font.width(title)) / 2, 6, 0x404040, false);
        g.drawString(font, playerInventoryTitle, 8, imageHeight - 94, 0x404040, false);

        // Mode — right of the arrow area (x=110, y=17), above the unused output slot
        int mode = menu.getMode();
        String modeName = (mode >= 0 && mode < MODE_LABELS.length) ? MODE_LABELS[mode] : "?";
        int modeColor = (mode >= 0 && mode < MODE_COLORS.length ? MODE_COLORS[mode] : 0x888888) | 0xFF000000;
        g.drawString(font, "Mode: " + modeName, 110, 17, modeColor, false);

        // Steam label above the bar
        g.drawString(font, "Steam: " + menu.getSteam() + " / " + menu.getMaxSteam(),
                8, 47, 0x4488FF, false);
    }
}
