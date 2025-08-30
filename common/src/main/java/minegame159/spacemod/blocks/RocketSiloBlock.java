package minegame159.spacemod.blocks;

import minegame159.spacemod.Space;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Set;

public class RocketSiloBlock extends Block {
    public RocketSiloBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            var space = level.getServer().getLevel(Space.KEY);

            player.teleportTo(
                space,
                0.5, 128, 0.5,
                Set.of(),
                player.getYRot(), player.getXRot()
            );
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
