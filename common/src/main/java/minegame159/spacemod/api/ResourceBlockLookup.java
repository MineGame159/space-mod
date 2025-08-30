package minegame159.spacemod.api;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;

public interface ResourceBlockLookup<R, C> {
    <T extends BlockEntity> void register(BlockEntityType<T> type, Provider<T, R, C> provider);

    @Nullable
    ResourceView<R> find(Level level, BlockPos pos, C context);

    interface Provider<T extends BlockEntity, R, C> {
        @Nullable
        ResourceView<R> get(T blockEntity, C context);
    }
}
