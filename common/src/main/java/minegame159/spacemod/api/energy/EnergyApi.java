package minegame159.spacemod.api.energy;

import dev.architectury.injectables.annotations.ExpectPlatform;
import minegame159.spacemod.api.ResourceBlockLookup;
import minegame159.spacemod.api.ResourceSlot;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public final class EnergyApi {
    private EnergyApi() {}

    public static final ResourceBlockLookup<ResourceSlot<EnergyResource>, @Nullable Direction> SIDED = createSided();

    @ExpectPlatform
    private static ResourceBlockLookup<ResourceSlot<EnergyResource>, @Nullable Direction> createSided() {
        throw new AssertionError();
    }
}
