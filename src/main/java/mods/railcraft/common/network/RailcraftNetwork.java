package mods.railcraft.common.network;

import mods.railcraft.RailcraftMod;
import mods.railcraft.common.carts.EntityLocomotive;
import mods.railcraft.common.gui.LocomotiveSteamMenu;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;

public final class RailcraftNetwork {

    public static final ResourceLocation LOCO_CONTROL =
            new ResourceLocation(RailcraftMod.MOD_ID, "loco_control");

    public static void registerServerReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(LOCO_CONTROL, (server, player, handler, buf, sender) -> {
            int modeOrd    = buf.readInt();
            int speedOrd   = buf.readInt();
            boolean reverse = buf.readBoolean();
            server.execute(() -> {
                if (!(player.containerMenu instanceof LocomotiveSteamMenu menu)) return;
                if (!(menu.getLocomotive() instanceof EntityLocomotive loco)) return;
                if (loco.distanceToSqr(player) > 64.0) return;

                EntityLocomotive.LocoMode[]  modes  = EntityLocomotive.LocoMode.VALUES;
                EntityLocomotive.LocoSpeed[] speeds = EntityLocomotive.LocoSpeed.VALUES;
                if (modeOrd  >= 0 && modeOrd  < modes.length)  loco.setMode(modes[modeOrd]);
                if (speedOrd >= 0 && speedOrd < speeds.length) loco.setSpeed(speeds[speedOrd]);
                loco.setReverse(reverse);
            });
        });
    }

    private RailcraftNetwork() {}
}
