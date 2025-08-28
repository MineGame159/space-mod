package minegame159.spacemod;

import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public final class ModFluids {
    private ModFluids() {}

    public static void init() {
        REGISTRY.register();
    }

    // Fluids

    public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(
        SpaceMod.ID,
        Registries.FLUID
    );

    public static final ArchitecturyFluidAttributes CRUDE_OIL_ATTRIBUTES = SimpleArchitecturyFluidAttributes.ofSupplier(
        () -> ModFluids.CRUDE_OIL_FLOWING,
        () -> ModFluids.CRUDE_OIL
    )
        .flowingTexture(ResourceLocation.fromNamespaceAndPath(SpaceMod.ID, "block/crude_oil_flow"))
        .sourceTexture(ResourceLocation.fromNamespaceAndPath(SpaceMod.ID, "block/crude_oil_still"))
        .color(FastColor.ARGB32.color(50, 50, 50))
        .block(ModBlocks.CRUDE_OIL)
        .bucketItem(ModItems.CRUDE_OIL_BUCKET)
        .density(2000)
        .viscosity(2000)
        .dropOff(2)
        .tickDelay(15);

    public static final DeferredSupplier<Fluid> CRUDE_OIL = REGISTRY.register(
        "crude_oil",
        () -> new ArchitecturyFlowingFluid.Source(CRUDE_OIL_ATTRIBUTES)
    );

    public static final DeferredSupplier<FlowingFluid> CRUDE_OIL_FLOWING = REGISTRY.register(
        "crude_oil_flowing",
        () -> new ArchitecturyFlowingFluid.Flowing(CRUDE_OIL_ATTRIBUTES)
    );
}
