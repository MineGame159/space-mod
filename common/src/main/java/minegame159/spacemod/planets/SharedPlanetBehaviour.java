package minegame159.spacemod.planets;

import minegame159.spacemod.blocks.ModBlocks;
import minegame159.spacemod.blocks.UnlitTorchBlock;
import minegame159.spacemod.blocks.UnlitWallTorchBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BaseTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

public final class SharedPlanetBehaviour {
    private SharedPlanetBehaviour() {}

    public static BlockState getPlacementStateWithoutOxygen(BlockPlaceContext context, BlockState original) {
        var block = original.getBlock();

        if (block instanceof BaseTorchBlock && !(block instanceof UnlitTorchBlock)) {
            if (original.hasProperty(UnlitWallTorchBlock.FACING)) {
                var unlit = ModBlocks.UNLIT_WALL_TORCH.get().defaultBlockState();
                return unlit.setValue(UnlitWallTorchBlock.FACING, original.getValue(UnlitWallTorchBlock.FACING));
            }

            return ModBlocks.UNLIT_TORCH.get().defaultBlockState();
        }

        return original;
    }
}
