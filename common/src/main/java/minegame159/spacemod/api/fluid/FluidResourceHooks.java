package minegame159.spacemod.api.fluid;

import com.mojang.serialization.Codec;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.level.material.Fluid;

final class FluidResourceHooks {
    private FluidResourceHooks() {}

    @ExpectPlatform
    public static Codec<FluidResource> getCodec() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static FluidResource of(Fluid fluid, DataComponentPatch path) {
        throw new AssertionError();
    }
}
