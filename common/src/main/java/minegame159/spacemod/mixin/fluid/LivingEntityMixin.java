package minegame159.spacemod.mixin.fluid;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import minegame159.spacemod.ModFluids;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @WrapOperation(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getFluidHeight(Lnet/minecraft/tags/TagKey;)D", ordinal = 1))
    private double spacemod$fluidJump(LivingEntity entity, TagKey<Fluid> tag, Operation<Double> original) {
        for (var info : ModFluids.INFOS) {
            var height = entity.getFluidHeight(info.tag());
            if (height > 0) return height;
        }

        return original.call(entity, tag);
    }

    @WrapOperation(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"))
    private boolean spacemod$fluidBreath(LivingEntity instance, TagKey<Fluid> tag, Operation<Boolean> original) {
        for (var info : ModFluids.INFOS) {
            if (instance.isEyeInFluid(info.tag())) {
                return true;
            }
        }

        return original.call(instance, tag);
    }
}
