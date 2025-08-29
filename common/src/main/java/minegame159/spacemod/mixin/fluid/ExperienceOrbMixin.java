package minegame159.spacemod.mixin.fluid;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import minegame159.spacemod.ModFluids;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ExperienceOrb.class)
public abstract class ExperienceOrbMixin extends Entity {
    @Shadow
    protected abstract void setUnderwaterMovement();

    public ExperienceOrbMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;applyGravity()V"))
    private boolean spacemod$fluidMovement(ExperienceOrb instance) {
        if (this.isInWater()) {
            for (var info : ModFluids.INFOS) {
                if (this.getFluidHeight(info.tag()) > 0.1) {
                    if (!info.providesSupport()) this.spacemod$applyGravity(0.5);
                    else this.setUnderwaterMovement();

                    return false;
                }
            }
        }

        return true;
    }

    @Unique
    private void spacemod$applyGravity(double scale) {
        var d = this.getGravity();

        if (d != 0.0) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -d * scale, 0.0));
        }
    }
}
