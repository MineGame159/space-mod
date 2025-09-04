package minegame159.spacemod.mixin.planets;

import minegame159.spacemod.planets.Planets;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlintAndSteelItem.class)
public abstract class FlintAndSteelItemMixin {
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void spacemod$blockFireWithoutOxygen(UseOnContext context, CallbackInfoReturnable<InteractionResult> info) {
        if (!Planets.hasOxygen(context.getLevel())) {
            info.setReturnValue(InteractionResult.FAIL);
        }
    }
}
