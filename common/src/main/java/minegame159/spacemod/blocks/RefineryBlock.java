package minegame159.spacemod.blocks;

import com.mojang.serialization.MapCodec;
import minegame159.spacemod.menus.sync.SyncedMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class RefineryBlock extends MachineBlock {
    public RefineryBlock(Properties properties) {
        super(properties, ModBlockEntities.REFINERY);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(RefineryBlock::new);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof RefineryBlockEntity provider) {
                SyncedMenu.open((ServerPlayer) player, provider);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
