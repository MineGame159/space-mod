package minegame159.spacemod;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
    public static void init() {
        REGISTRY.register();
    }

    // Blocks

    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(
        SpaceMod.ID,
        Registries.BLOCK
    );

    public static final RegistrySupplier<RocketSiloBlock> ROCKET_SILO = REGISTRY.register(
        "rocket_silo",
        () -> new RocketSiloBlock(BlockBehaviour.Properties.of())
    );
}
