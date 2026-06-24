package mods.railcraft.client.gui;

import mods.railcraft.common.gui.SteamBoilerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SteamBoilerScreen extends AbstractContainerScreen<SteamBoilerMenu> {

    private static final ResourceLocation BG =
            new ResourceLocation("minecraft", "textures/gui/container/furnace.png");

    public SteamBoilerScreen(SteamBoilerMenu menu, Inventory inv, Component title) {
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

        // Fuel flame indicator
        int flame = menu.getScaledFlame(13);
        if (flame > 0)
            g.blit(BG, leftPos + 56, topPos + 36 + 13 - flame, 176, 13 - flame, 14, flame + 1);

        // Heat bar — orange fill in the right-side area (79,17 → 79+20,17+50)
        int heatBar = menu.getHeatScaled(50);
        if (heatBar > 0)
            g.fill(leftPos + 96, topPos + 17 + 50 - heatBar, leftPos + 108, topPos + 67, 0xFFFF6600);

        // Steam bar — blue fill beside heat bar
        int steamBar = menu.getSteamScaled(50);
        if (steamBar > 0)
            g.fill(leftPos + 112, topPos + 17 + 50 - steamBar, leftPos + 124, topPos + 67, 0xFF4488FF);
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mx, int my) {
        g.drawString(font, title, (imageWidth - font.width(title)) / 2, 6, 0x404040, false);
        g.drawString(font, playerInventoryTitle, 8, imageHeight - 94, 0x404040, false);
        g.drawString(font, "Heat", 93, 6, 0xFF6600, false);
        g.drawString(font, "Steam", 109, 6, 0x4488FF, false);
    }
}
