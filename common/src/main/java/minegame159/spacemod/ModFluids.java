package minegame159.spacemod;

import dev.architectury.core.fluid.ArchitecturyFlowingFluid;
import dev.architectury.core.fluid.ArchitecturyFluidAttributes;
import dev.architectury.core.fluid.SimpleArchitecturyFluidAttributes;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import minegame159.spacemod.blocks.ModBlocks;
import minegame159.spacemod.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import java.util.List;

public final class ModFluids {
    public record Info(TagKey<Fluid> tag, double motionScale, boolean providesSupport) {}

    public static final List<Info> INFOS = List.of(
        new Info(ModFluidTags.CRUDE_OIL, 0.01, false),
        new Info(ModFluidTags.ROCKET_FUEL, 0.014, true)
    );

    private ModFluids() {}

    public static void init() {
        REGISTRY.register();
    }

    public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(
        SpaceMod.ID,
        Registries.FLUID
    );

    // Crude Oil

    public static final ArchitecturyFluidAttributes CRUDE_OIL_ATTRIBUTES = SimpleArchitecturyFluidAttributes.ofSupplier(
        () -> ModFluids.CRUDE_OIL_FLOWING,
        () -> ModFluids.CRUDE_OIL
    )
        .flowingTexture(SpaceMod.id("block/crude_oil_flow"))
        .sourceTexture(SpaceMod.id("block/crude_oil_still"))
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

    // Rocket Fuel

    public static final ArchitecturyFluidAttributes ROCKET_FUEL_ATTRIBUTES = SimpleArchitecturyFluidAttributes.ofSupplier(
            () -> ModFluids.ROCKET_FUEL_FLOWING,
            () -> ModFluids.ROCKET_FUEL
        )
        .flowingTexture(ResourceLocation.withDefaultNamespace("block/water_flow"))
        .sourceTexture(ResourceLocation.withDefaultNamespace("block/water_still"))
        .color(FastColor.ARGB32.color(229, 229, 51))
        .block(ModBlocks.ROCKET_FUEL)
        .bucketItem(ModItems.ROCKET_FUEL_BUCKET);

    public static final DeferredSupplier<Fluid> ROCKET_FUEL = REGISTRY.register(
        "rocket_fuel",
        () -> new ArchitecturyFlowingFluid.Source(ROCKET_FUEL_ATTRIBUTES)
    );

    public static final DeferredSupplier<FlowingFluid> ROCKET_FUEL_FLOWING = REGISTRY.register(
        "rocket_fuel_flowing",
        () -> new ArchitecturyFlowingFluid.Flowing(ROCKET_FUEL_ATTRIBUTES)
    );
}
