package minegame159.spacemod.worldgen;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class BiomeUtils {
    private BiomeUtils() {}

    @ExpectPlatform
    public static TagKey<Biome> getDesertTag() {
        throw new AssertionError();
    }
}
