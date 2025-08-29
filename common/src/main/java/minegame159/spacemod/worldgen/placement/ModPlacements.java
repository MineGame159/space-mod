package minegame159.spacemod.worldgen.placement;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import minegame159.spacemod.SpaceMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public final class ModPlacements {
    private ModPlacements() {}

    public static void init() {
        REGISTRY.register();
    }

    // Placements

    public static final DeferredRegister<PlacementModifierType<?>> REGISTRY = DeferredRegister.create(
        SpaceMod.ID,
        Registries.PLACEMENT_MODIFIER_TYPE
    );

    public static final DeferredSupplier<PlacementModifierType<NoiseThresholdFilter>> NOISE_THRESHOLD_TYPE = REGISTRY.register(
        "noise_threshold",
       () -> () -> NoiseThresholdFilter.CODEC
    );
}
