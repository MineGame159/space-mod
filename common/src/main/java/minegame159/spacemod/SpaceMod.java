package minegame159.spacemod;

import minegame159.spacemod.worldgen.feature.ModFeatures;
import minegame159.spacemod.worldgen.placement.ModPlacements;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class SpaceMod {
    public static final String ID = "spacemod";

    public static void init() {
        ModFluids.init();
        ModBlocks.init();
        ModItems.init();

        ModPlacements.init();
        ModFeatures.init();

        Space.init();
    }

    public static MutableComponent translatable(String prefix, String suffix) {
        return Component.translatable(prefix + "." + ID + "." + suffix);
    }
}
