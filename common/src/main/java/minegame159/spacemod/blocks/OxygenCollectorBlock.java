package minegame159.spacemod.blocks;

import com.mojang.serialization.MapCodec;
import minegame159.spacemod.menus.sync.SyncedMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class OxygenCollectorBlock extends MachineBlock {
    protected OxygenCollectorBlock(Properties properties) {
        super(properties, ModBlockEntities.OXYGEN_COLLECTOR);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(OxygenCollectorBlock::new);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof OxygenCollectorBlockEntity provider) {
                SyncedMenu.open((ServerPlayer) player, provider);
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (state.getBlock() == newState.getBlock()) return;

        if (level.getBlockEntity(pos) instanceof OxygenCollectorBlockEntity blockEntity) {
            if (!level.isClientSide) {
                Containers.dropContents(level, pos, blockEntity.container);
            }

            level.updateNeighbourForOutputSignal(pos, this);
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof OxygenCollectorBlockEntity blockEntity) {
            return AbstractContainerMenu.getRedstoneSignalFromContainer(blockEntity.container);
        }

        return 0;
    }
}
