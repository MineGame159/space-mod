package minegame159.spacemod.api.fluid;

import dev.architectury.hooks.fluid.FluidStackHooks;
import dev.architectury.injectables.annotations.ExpectPlatform;
import minegame159.spacemod.api.ResourceBlockLookup;
import minegame159.spacemod.api.ResourceView;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public final class FluidApi {
    private FluidApi() {}

    public static final int BUCKET_AMOUNT = (int) FluidStackHooks.bucketAmount();

    public static final ResourceBlockLookup<ResourceView<FluidResource>, @Nullable Direction> SIDED = createSided();

    @ExpectPlatform
    public static int mbToPlatformUnit(int mb) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static int platformUnitToMb(int unit) {
        throw new AssertionError();
    }

    @ExpectPlatform
    private static ResourceBlockLookup<ResourceView<FluidResource>, @Nullable Direction> createSided() {
        throw new AssertionError();
    }
}
