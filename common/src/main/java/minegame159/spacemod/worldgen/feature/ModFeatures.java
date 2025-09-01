package minegame159.spacemod.worldgen.feature;

import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import minegame159.spacemod.SpaceMod;
import minegame159.spacemod.worldgen.BiomeUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;

public final class ModFeatures {
    private ModFeatures() {}

    public static void init() {
        REGISTRY.register();

        BiomeModifications.addProperties(
            context -> context.hasTag(BiomeUtils.getDesertTag()) || context.hasTag(BiomeTags.IS_BADLANDS),
            (context, properties) -> properties.getGenerationProperties().addFeature(
                GenerationStep.Decoration.FLUID_SPRINGS,
                ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    SpaceMod.id("small_oil_field")
                )
            )
        );

        BiomeModifications.addProperties(
            context -> context.hasTag(BiomeTags.IS_DEEP_OCEAN),
            (context, properties) -> properties.getGenerationProperties().addFeature(
                GenerationStep.Decoration.FLUID_SPRINGS,
                ResourceKey.create(
                    Registries.PLACED_FEATURE,
                    SpaceMod.id("large_oil_field")
                )
            )
        );
    }

    // Features

    public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(
        SpaceMod.ID,
        Registries.FEATURE
    );

    public static final DeferredSupplier<OilFieldFeature> OIL_FIELD = REGISTRY.register(
        "oil_field",
        OilFieldFeature::new
    );
}
