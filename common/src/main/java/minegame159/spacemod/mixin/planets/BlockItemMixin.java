package minegame159.spacemod.mixin.planets;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import minegame159.spacemod.planets.Planets;
import minegame159.spacemod.planets.SharedPlanetBehaviour;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {
    @WrapOperation(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BlockItem;getPlacementState(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;"))
    private BlockState spacemod$getPlacementStateWithoutOxygen(BlockItem instance, BlockPlaceContext context, Operation<BlockState> operation) {
        var original = operation.call(instance, context);

        if (!Planets.hasOxygen(context.getLevel())) {
            original = SharedPlanetBehaviour.getPlacementStateWithoutOxygen(context, original);
        }

        return original;
    }
}
