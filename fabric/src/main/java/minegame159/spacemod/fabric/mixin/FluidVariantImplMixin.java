package minegame159.spacemod.fabric.mixin;

import minegame159.spacemod.api.fluid.FluidResource;
import net.fabricmc.fabric.impl.transfer.fluid.FluidVariantImpl;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = FluidVariantImpl.class, remap = false)
public abstract class FluidVariantImplMixin implements FluidResource {
    @Shadow
    @Final
    private Fluid fluid;

    @Shadow
    @Final
    private DataComponentPatch components;

    @Shadow
    @Final
    private DataComponentMap componentMap;

    @Override
    public Fluid getFluid() {
        return fluid;
    }

    @Override
    public DataComponentPatch getComponents() {
        return components;
    }

    @Override
    public DataComponentMap getComponentMap() {
        return componentMap;
    }
}
