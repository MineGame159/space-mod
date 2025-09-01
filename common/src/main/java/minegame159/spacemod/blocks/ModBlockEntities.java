package minegame159.spacemod.blocks;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import minegame159.spacemod.SpaceMod;
import minegame159.spacemod.api.energy.EnergyApi;
import minegame159.spacemod.api.fluid.FluidApi;
import minegame159.spacemod.utils.SimpleResourceView;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class ModBlockEntities {
    private ModBlockEntities() {}

    public static void init() {
        REGISTRY.register();

        EnergyApi.SIDED.register(
            REFINERY.get(),
            (blockEntity, context) -> blockEntity.energy
        );

        FluidApi.SIDED.register(
            REFINERY.get(),
            (blockEntity, context) -> SimpleResourceView.withMainOutput(blockEntity.input, blockEntity.output)
        );
    }

    // Block entities

    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(
        SpaceMod.ID,
        Registries.BLOCK_ENTITY_TYPE
    );

    public static final DeferredSupplier<BlockEntityType<RefineryBlockEntity>> REFINERY = REGISTRY.register(
        "refinery",
        () -> BlockEntityType.Builder.of(RefineryBlockEntity::new, ModBlocks.REFINERY.get())
            .build(Util.fetchChoiceType(References.BLOCK_ENTITY, "refinery"))
    );
}
