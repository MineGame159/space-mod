package minegame159.spacemod.items;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import minegame159.spacemod.SpaceMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

public final class ModDataComponents {
    private ModDataComponents() {}

    public static void init() {
        REGISTRY.register();
    }

    // Data components

    public static final DeferredRegister<DataComponentType<?>> REGISTRY = DeferredRegister.create(
        SpaceMod.ID,
        Registries.DATA_COMPONENT_TYPE
    );

    public static final DeferredSupplier<DataComponentType<OxygenStorage>> OXYGEN_STORAGE = REGISTRY.register(
        "oxygen_storage",
        () -> DataComponentType.<OxygenStorage>builder()
            .persistent(OxygenStorage.CODEC)
            .networkSynchronized(OxygenStorage.STREAM_CODEC)
            .build()
    );
}
