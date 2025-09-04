package minegame159.spacemod.mixin.planets;

import minegame159.spacemod.planets.Planets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin {
    @Inject(method = "canBePlacedAt", at = @At("HEAD"), cancellable = true)
    private static void spacemod$blockFireWithoutOxygen(Level level, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> info) {
        if (!Planets.hasOxygen(level)) {
            info.setReturnValue(false);
        }
    }
}
