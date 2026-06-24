package mods.railcraft.client.gui;

import mods.railcraft.RailcraftMod;
import mods.railcraft.common.carts.EntityLocomotive.LocoMode;
import mods.railcraft.common.carts.EntityLocomotive.LocoSpeed;
import mods.railcraft.common.gui.LocomotiveSteamMenu;
import mods.railcraft.common.network.RailcraftNetwork;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LocomotiveSteamScreen extends AbstractContainerScreen<LocomotiveSteamMenu> {

    private static final ResourceLocation BG =
            new ResourceLocation(RailcraftMod.MOD_ID, "textures/gui/gui_locomotive_steam.png");

    // GUI height matches original (205 = 166 + 39 for two button rows + spacing)
    private static final int GUI_HEIGHT = 205;

    // Button row Y positions match original GuiLocomotive: guiHeight - 129 and guiHeight - 112
    private static final int MODE_BTN_Y  = GUI_HEIGHT - 129;  // = 76
    private static final int SPEED_BTN_Y = GUI_HEIGHT - 112;  // = 93
    private static final int BTN_H = 14;

    // Mode buttons: 3 × 55px wide starting at x=3
    private static final int[] MODE_WIDTHS = { 55, 55, 55 };
    private static final String[] MODE_LABELS = { "Shutdown", "Idle", "Running" };

    // Speed buttons: Rev(14) + four arrow speeds matching original widths (7 + level*5)
    private static final int[] SPEED_WIDTHS = { 14, 12, 17, 22, 27 };
    private static final String[] SPEED_LABELS = { "R", ">", ">>", ">>>", ">>>>" };

    private Button[] modeButtons  = new Button[3];
    private Button[] speedButtons = new Button[5];

    private LocoMode  curMode    = LocoMode.SHUTDOWN;
    private LocoSpeed curSpeed   = LocoSpeed.MAX;
    private boolean   curReverse = false;

    public LocomotiveSteamScreen(LocomotiveSteamMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        imageHeight = GUI_HEIGHT;
    }

    @Override
    protected void init() {
        super.init();
        syncFromMenu();

        // Mode buttons row
        int x = leftPos + 3;
        for (int i = 0; i < 3; i++) {
            final int idx = i;
            modeButtons[i] = Button.builder(Component.literal(MODE_LABELS[i]), btn -> onModeBtn(idx))
                    .pos(x + i * (55 + 2), topPos + MODE_BTN_Y)
                    .size(MODE_WIDTHS[i], BTN_H)
                    .build();
            addRenderableWidget(modeButtons[i]);
        }

        // Speed buttons row: Rev then four arrow speeds
        int sx = leftPos + 8;
        for (int i = 0; i < 5; i++) {
            final int idx = i;
            speedButtons[i] = Button.builder(Component.literal(SPEED_LABELS[i]), btn -> onSpeedBtn(idx))
                    .pos(sx, topPos + SPEED_BTN_Y)
                    .size(SPEED_WIDTHS[i], BTN_H)
                    .build();
            addRenderableWidget(speedButtons[i]);
            sx += SPEED_WIDTHS[i] + 3;
        }

        updateButtonLabels();
    }

    private void onModeBtn(int idx) {
        curMode = LocoMode.VALUES[idx];  // 0=SHUTDOWN, 1=IDLE, 2=RUNNING
        sendPacket();
        updateButtonLabels();
    }

    private void onSpeedBtn(int idx) {
        if (idx == 0) {
            curReverse = !curReverse;  // Rev toggles
        } else {
            curSpeed = LocoSpeed.VALUES[idx - 1];  // 1→SLOWEST, 2→SLOWER, 3→NORMAL, 4→MAX
            if (curMode != LocoMode.RUNNING) curMode = LocoMode.RUNNING;
        }
        sendPacket();
        updateButtonLabels();
    }

    private void sendPacket() {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeInt(curMode.ordinal());
        buf.writeInt(curSpeed.ordinal());
        buf.writeBoolean(curReverse);
        ClientPlayNetworking.send(RailcraftNetwork.LOCO_CONTROL, buf);
    }

    private void syncFromMenu() {
        int m = menu.getMode(), s = menu.getSpeed();
        curMode    = (m >= 0 && m < LocoMode.VALUES.length)  ? LocoMode.VALUES[m]  : LocoMode.SHUTDOWN;
        curSpeed   = (s >= 0 && s < LocoSpeed.VALUES.length) ? LocoSpeed.VALUES[s] : LocoSpeed.MAX;
        curReverse = menu.isReverse();
    }

    private void updateButtonLabels() {
        if (modeButtons[0] == null) return;

        // Mode buttons — bracket active one
        for (int i = 0; i < 3; i++) {
            boolean active = LocoMode.VALUES[i] == curMode;
            modeButtons[i].setMessage(Component.literal(active ? "[" + MODE_LABELS[i] + "]" : MODE_LABELS[i]));
        }

        // Reverse button
        speedButtons[0].setMessage(Component.literal(curReverse ? "[R]" : "R"));

        // Speed buttons — active when RUNNING at that speed
        for (int i = 1; i < 5; i++) {
            boolean active = curMode == LocoMode.RUNNING && LocoSpeed.VALUES[i - 1] == curSpeed;
            speedButtons[i].setMessage(Component.literal(active ? "[" + SPEED_LABELS[i] + "]" : SPEED_LABELS[i]));
        }
    }

    @Override
    public void render(GuiGraphics g, int mx, int my, float dt) {
        syncFromMenu();
        updateButtonLabels();
        renderBackground(g);
        super.render(g, mx, my, dt);
        renderTooltip(g, mx, my);
    }

    @Override
    protected void renderBg(GuiGraphics g, float dt, int mx, int my) {
        g.blit(BG, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        // Burn indicator at original position (x+99, y+33, UV 176,59, 14×scale)
        int flame = menu.getScaledFlame(12);
        if (flame > 0) {
            g.blit(BG, leftPos + 99, topPos + 33 - flame, 176, 59 - flame, 14, flame + 2);
        }

        // Steam gauge — vertical fill at original fluid gauge position (x=53, y=23, 16×47)
        int steamH = menu.getScaledSteam(47);
        if (steamH > 0) {
            g.blit(BG, leftPos + 53, topPos + 23 + (47 - steamH), 176, 47 - steamH, 16, steamH);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics g, int mx, int my) {
        g.drawString(font, title, (imageWidth - font.width(title)) / 2, 6, 0x404040, false);
        // Player inventory title at standard offset above the inventory rows (y=123 in absolute → 111 relative)
        g.drawString(font, playerInventoryTitle, 8, imageHeight - 94, 0x404040, false);
    }
}
