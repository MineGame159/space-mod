package minegame159.spacemod.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import minegame159.spacemod.ModFluidTags;
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
        var pushedByCrudeOil = this.updateFluidHeightAndDoFluidPushing(ModFluidTags.CRUDE_OIL, 0.01);

        if (pushedByCrudeOil) {
            this.wasTouchingWater = true;
            this.resetFallDistance();
        }

        return pushedOriginal | pushedByCrudeOil;
    }
}
