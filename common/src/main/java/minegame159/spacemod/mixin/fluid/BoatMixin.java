package minegame159.spacemod.mixin.fluid;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import minegame159.spacemod.ModFluids;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Boat.class)
public abstract class BoatMixin {
    @WrapOperation(
        method = {"getWaterLevelAbove", "checkInWater", "isUnderwater", "checkFallDamage", "canAddPassenger"},
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z")
    )
    private boolean spacemod$fluid(FluidState instance, TagKey<Fluid> tag, Operation<Boolean> original) {
        for (var info : ModFluids.INFOS) {
            if (info.providesSupport() && instance.is(info.tag())) {
                return true;
            }
        }

        return original.call(instance, tag);
    }
}
