package mods.railcraft.client.gui;

import mods.railcraft.common.gui.BlastFurnaceMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class BlastFurnaceScreen extends AbstractContainerScreen<BlastFurnaceMenu> {

    private static final ResourceLocation BG =
            new ResourceLocation("minecraft", "textures/gui/container/furnace.png");

    public BlastFurnaceScreen(BlastFurnaceMenu menu, Inventory inv, Component title) {
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

        // Fuel flame indicator (furnace flame UV: 176,0; 14×14 — animate bottom-up)
        int flame = menu.getScaledFlame(13);
        if (flame > 0)
            g.blit(BG, leftPos + 56, topPos + 36 + 13 - flame, 176, 13 - flame, 14, flame + 1);

        // Progress arrow
        int progress = menu.getScaledArrow(24);
        if (progress > 0)
            g.blit(BG, leftPos + 79, topPos + 34, 176, 14, progress, 16);
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mx, int my) {
        g.drawString(font, title, (imageWidth - font.width(title)) / 2, 6, 0x404040, false);
        g.drawString(font, playerInventoryTitle, 8, imageHeight - 94, 0x404040, false);
    }
}
