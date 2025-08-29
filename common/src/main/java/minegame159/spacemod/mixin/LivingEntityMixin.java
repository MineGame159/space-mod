package minegame159.spacemod.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import minegame159.spacemod.ModFluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @WrapOperation(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getFluidHeight(Lnet/minecraft/tags/TagKey;)D", ordinal = 1))
    private double spacemod$fluidJump(LivingEntity entity, TagKey<Fluid> tag, Operation<Double> original) {
        var crudeOilHeight = entity.getFluidHeight(ModFluidTags.CRUDE_OIL);
        if (crudeOilHeight > 0) return crudeOilHeight;

        return original.call(entity, tag);
    }

    @WrapOperation(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean spacemod$fluidBreathing(LivingEntity instance, TagKey<Fluid> tag, Operation<Boolean> original) {
        if (instance.isEyeInFluid(ModFluidTags.CRUDE_OIL)) {
            return true;
        }

        return original.call(instance, tag);
    }
}
