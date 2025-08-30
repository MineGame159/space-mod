package minegame159.spacemod.api.fluid.fabric;

import com.mojang.serialization.Codec;
import minegame159.spacemod.api.fluid.FluidResource;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.impl.transfer.fluid.FluidVariantImpl;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.level.material.Fluid;

@SuppressWarnings("unused")
public final class FluidResourceHooksImpl {
    private FluidResourceHooksImpl() {}

    public static Codec<FluidResource> getCodec() {
        return FluidVariant.CODEC.xmap(
            variant -> (FluidResource) variant,
            resource -> (FluidVariant) resource
        );
    }

    public static FluidResource of(Fluid fluid, DataComponentPatch components) {
        return (FluidResource) FluidVariantImpl.of(fluid, components);
    }
}
