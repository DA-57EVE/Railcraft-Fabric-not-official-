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

    // Furnace panel background colour — used to paint over the unused output slot hole
    private static final int PANEL_COLOR = 0xFFC6C6C6;

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

        // Cover the arrow outline + output slot (baked into furnace texture, not used here)
        g.fill(leftPos + 74, topPos + 29, leftPos + 137, topPos + 57, PANEL_COLOR);

        // Flame indicator — draws upward (furnace UV: 176,0; 14×13)
        int flame = menu.getScaledFlame(13);
        if (flame > 0) {
            int yOff = 13 - flame;
            g.blit(BG, leftPos + 57, topPos + 36 + yOff, 176, yOff, 14, flame);
        }

        // Steam pressure bar — right section, 80×16 px
        int steamBar = menu.getScaledSteam(80);
        g.fill(leftPos + 82, topPos + 29, leftPos + 162, topPos + 45, 0xFF333333);
        if (steamBar > 0)
            g.fill(leftPos + 82, topPos + 29, leftPos + 82 + steamBar, topPos + 45, 0xFF3399FF);
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mx, int my) {
        g.drawString(font, title, (imageWidth - font.width(title)) / 2, 6, 0x404040, false);
        g.drawString(font, playerInventoryTitle, 8, imageHeight - 94, 0x404040, false);

        // Left: fuel label (slot is at 56,17)
        g.drawString(font, "Fuel:", 8, 21, 0x404040, false);

        // Right: steam label above bar (bar top = 29)
        g.drawString(font, "Steam:", 82, 19, 0x404040, false);
        // Steam numbers below bar (bar bottom = 45)
        g.drawString(font, menu.getSteam() + " / " + menu.getMaxSteam(), 82, 47, 0x3399FF, false);

        // Mode below steam numbers
        int mode = menu.getMode();
        String modeName = (mode >= 0 && mode < MODE_LABELS.length) ? MODE_LABELS[mode] : "?";
        int modeColor = (mode >= 0 && mode < MODE_COLORS.length ? MODE_COLORS[mode] : 0x888888) | 0xFF000000;
        g.drawString(font, "Mode: " + modeName, 82, 58, modeColor, false);
    }
}
