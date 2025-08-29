package minegame159.spacemod.mixin.fluid;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import minegame159.spacemod.ModFluids;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract boolean updateFluidHeightAndDoFluidPushing(TagKey<Fluid> fluidTag, double motionScale);

    @Shadow
    protected boolean wasTouchingWater;

    @Shadow
    public abstract void resetFallDistance();

    @ModifyReturnValue(method = "updateInWaterStateAndDoFluidPushing", at = @At("RETURN"))
    private boolean spacemod$fluidPush(boolean pushedOriginal) {
        for (var info : ModFluids.INFOS) {
            if (this.updateFluidHeightAndDoFluidPushing(info.tag(), info.motionScale())) {
                this.wasTouchingWater = true;
                this.resetFallDistance();

                return true;
            }
        }

        return pushedOriginal;
    }
}
