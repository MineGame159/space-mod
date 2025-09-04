package minegame159.spacemod.mixin.space;

import minegame159.spacemod.Space;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {
    @Inject(method = "emptyContents", at = @At("HEAD"), cancellable = true)
    private void spacemod$blockFluidsInSpace(Player player, Level level, BlockPos pos, BlockHitResult result, CallbackInfoReturnable<Boolean> info) {
        if (level.dimension().location().equals(Space.KEY.location())) {
            info.setReturnValue(false);
        }
    }
}
