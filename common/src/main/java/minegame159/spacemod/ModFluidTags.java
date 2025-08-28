package minegame159.spacemod;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public final class ModFluidTags {
    private ModFluidTags() {}

    public static final TagKey<Fluid> CRUDE_OIL = create("crude_oil");

    private static TagKey<Fluid> create(String name) {
        return TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(SpaceMod.ID, name));
    }
}
