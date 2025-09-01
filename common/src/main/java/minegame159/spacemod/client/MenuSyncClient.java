package minegame159.spacemod.client;

import dev.architectury.networking.NetworkManager;
import minegame159.spacemod.menus.sync.MenuDataS2CPacket;
import minegame159.spacemod.menus.sync.SyncedMenu;

public final class MenuSyncClient {
    private MenuSyncClient() {}

    public static void init() {
        NetworkManager.registerReceiver(
            NetworkManager.Side.S2C,
            MenuDataS2CPacket.TYPE,
            MenuDataS2CPacket.CODEC,
            MenuSyncClient::onPacket
        );
    }

    private static void onPacket(MenuDataS2CPacket<?> packet, NetworkManager.PacketContext context) {
        if (context.getPlayer().containerMenu instanceof SyncedMenu<?, ?> menu) {
            menu.onReceivePacket(packet);
        }
    }
}
