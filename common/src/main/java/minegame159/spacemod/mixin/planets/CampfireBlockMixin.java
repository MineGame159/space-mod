package minegame159.spacemod.mixin.planets;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import minegame159.spacemod.planets.Planets;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CampfireBlock.class)
public abstract class CampfireBlockMixin {
    @WrapMethod(method = "getStateForPlacement")
    private BlockState spacemod$blockFireWithoutOxygen(BlockPlaceContext context, Operation<BlockState> operation) {
        var original = operation.call(context);

        if (!Planets.hasOxygen(context.getLevel().dimension())) {
            original = original.setValue(CampfireBlock.LIT, false);
        }

        return original;
    }
}
