package minegame159.spacemod;

import com.mojang.logging.LogUtils;
import minegame159.spacemod.blocks.ModBlocks;
import minegame159.spacemod.worldgen.feature.ModFeatures;
import minegame159.spacemod.worldgen.placement.ModPlacements;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.slf4j.Logger;

public final class SpaceMod {
    public static final String ID = "spacemod";

    public static Logger LOGGER = LogUtils.getLogger();

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
