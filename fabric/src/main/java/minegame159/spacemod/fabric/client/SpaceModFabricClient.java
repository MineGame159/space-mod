package minegame159.spacemod.fabric.client;

import minegame159.spacemod.client.SpaceModClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class SpaceModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SpaceModClient.init();
    }
}
