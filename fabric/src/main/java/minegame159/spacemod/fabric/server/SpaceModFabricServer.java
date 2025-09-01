package minegame159.spacemod.fabric.server;

import minegame159.spacemod.server.SpaceModServer;
import net.fabricmc.api.DedicatedServerModInitializer;

public class SpaceModFabricServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        SpaceModServer.init();
    }
}
