package minegame159.spacemod.api.fluid;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public interface FluidResource {
    Codec<FluidResource> CODEC = FluidResourceHooks.getCodec();

    Fluid getFluid();

    DataComponentPatch getComponents();

    DataComponentMap getComponentMap();

    default boolean is(TagKey<Fluid> tag) {
        return getFluid().is(tag);
    }

    static FluidResource empty() {
        return FluidResourceHooks.of(Fluids.EMPTY, DataComponentPatch.EMPTY);
    }

    static FluidResource of(Fluid fluid) {
        return FluidResourceHooks.of(fluid, DataComponentPatch.EMPTY);
    }

    static FluidResource of(Fluid fluid, DataComponentPatch components) {
        return FluidResourceHooks.of(fluid, components);
    }
}
