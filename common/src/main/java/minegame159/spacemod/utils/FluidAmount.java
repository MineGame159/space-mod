package minegame159.spacemod.utils;

import minegame159.spacemod.api.ResourceSlot;
import minegame159.spacemod.api.fluid.FluidResource;
import net.minecraft.world.level.material.Fluid;

public record FluidAmount(Fluid fluid, int amount) {
    public static FluidAmount of(ResourceSlot<FluidResource> slot) {
        return new FluidAmount(slot.getResource().getFluid(), slot.getAmount());
    }
}
