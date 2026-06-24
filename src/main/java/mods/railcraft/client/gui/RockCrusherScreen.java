package mods.railcraft.client.gui;

import mods.railcraft.common.gui.RockCrusherMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class RockCrusherScreen extends AbstractContainerScreen<RockCrusherMenu> {

    private static final ResourceLocation BG =
            new ResourceLocation("minecraft", "textures/gui/container/furnace.png");

    public RockCrusherScreen(RockCrusherMenu menu, Inventory inv, Component title) {
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
