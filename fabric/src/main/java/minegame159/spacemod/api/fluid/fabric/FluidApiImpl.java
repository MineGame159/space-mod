package minegame159.spacemod.api.fluid.fabric;

import com.google.common.collect.Iterators;
import minegame159.spacemod.api.*;
import minegame159.spacemod.api.fluid.FluidResource;
import minegame159.spacemod.utils.SimpleResourceView;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.SlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class FluidApiImpl implements ResourceBlockLookup<ResourceView<FluidResource>, @Nullable Direction> {
    @Override
    public <T extends BlockEntity> void register(BlockEntityType<T> type, Provider<T, ResourceView<FluidResource>, Direction> provider) {
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

        return wrap(storage);
    }

    public static ResourceBlockLookup<ResourceView<FluidResource>, @Nullable Direction> createSided() {
        return new FluidApiImpl();
    }

    @SuppressWarnings("unchecked")
    public static ResourceView<FluidResource> wrap(Storage<FluidVariant> storage) {
        // Slotted storage
        if (storage instanceof SlottedStorage<FluidVariant> slotted) {
            if (slotted.getSlotCount() == 1) {
                return new StorageView2ResourceViewOrSlot(slotted, slotted.getSlot(0));
            }

            var slots = new ResourceSlot[slotted.getSlotCount()];

            for (int i = 0; i < slots.length; i++) {
                slots[i] = new StorageView2ResourceViewOrSlot(slotted, slotted.getSlot(i));
            }

            return SimpleResourceView.with(slots);
        }

        // Generic fallback
        var count = Iterators.size(storage.iterator());

        if (count == 1) {
            return new StorageView2ResourceViewOrSlot(storage);
        }

        var slots = new ResourceSlot[count];
        var i = 0;

        for (var view : storage) {
            slots[i] = new StorageView2ResourceViewOrSlot(storage, view);
            i++;
        }

        return SimpleResourceView.with(slots);
    }
}
