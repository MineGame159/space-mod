package minegame159.spacemod.worldgen.fabric;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

@SuppressWarnings("unused")
public final class BiomeUtilsImpl {
    private BiomeUtilsImpl() {}

    public static TagKey<Biome> getDesertTag() {
        return ConventionalBiomeTags.IS_DESERT;
    }
}
