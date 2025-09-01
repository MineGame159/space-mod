package minegame159.spacemod.server;

import dev.architectury.networking.NetworkManager;
import minegame159.spacemod.menus.sync.MenuDataS2CPacket;

public final class SpaceModServer {
    private SpaceModServer() {}

    public static void init() {
        NetworkManager.registerS2CPayloadType(MenuDataS2CPacket.TYPE, MenuDataS2CPacket.CODEC);
    }
}
