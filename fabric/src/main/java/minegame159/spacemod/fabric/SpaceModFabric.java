package minegame159.spacemod.fabric;

import minegame159.spacemod.SpaceMod;
import net.fabricmc.api.ModInitializer;

public final class SpaceModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        SpaceMod.init();
    }
}
