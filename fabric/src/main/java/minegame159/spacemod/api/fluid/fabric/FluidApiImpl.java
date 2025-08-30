package minegame159.spacemod.api.fluid.fabric;

import minegame159.spacemod.api.ResourceBlockLookup;
import minegame159.spacemod.api.ResourceView;
import minegame159.spacemod.api.ResourceView2Storage;
import minegame159.spacemod.api.fluid.FluidResource;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class FluidApiImpl implements ResourceBlockLookup<FluidResource, Direction> {
    @Override
    public <T extends BlockEntity> void register(BlockEntityType<T> type, Provider<T, FluidResource, Direction> provider) {
        FluidStorage.SIDED.registerForBlockEntity(
            (blockEntity, direction) -> {
                var view = provider.get(blockEntity, direction);
                if (view == null) return null;

                return new ResourceView2Storage(view);
            },
            type
        );
    }

    @Override
    public @Nullable ResourceView<FluidResource> find(Level level, BlockPos pos, Direction context) {
        var storage = FluidStorage.SIDED.find(level, pos, context);
        if (storage == null) return null;

        if (storage instanceof ResourceView2Storage wrapper) {
            return wrapper.resourceView;
        }

        return null;
    }

    public static ResourceBlockLookup<FluidResource, @Nullable Direction> createSided() {
        return new FluidApiImpl();
    }
}
