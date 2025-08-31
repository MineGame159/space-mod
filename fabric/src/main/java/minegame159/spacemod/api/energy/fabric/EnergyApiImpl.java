package minegame159.spacemod.api.energy.fabric;

import minegame159.spacemod.api.ResourceBlockLookup;
import minegame159.spacemod.api.ResourceSlot;
import minegame159.spacemod.api.energy.EnergyResource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;

@SuppressWarnings("unused")
public class EnergyApiImpl implements ResourceBlockLookup<ResourceSlot<EnergyResource>, @Nullable Direction> {
    @Override
    public <T extends BlockEntity> void register(BlockEntityType<T> type, Provider<T, ResourceSlot<EnergyResource>, @Nullable Direction> provider) {
        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> {
            var slot = provider.get(blockEntity, direction);
            if (slot == null) return null;

            return new ResourceSlot2EnergyStorage(slot);
        }, type);
    }

    @Override
    public @Nullable ResourceSlot<EnergyResource> find(Level level, BlockPos pos, @Nullable Direction context) {
        var storage = EnergyStorage.SIDED.find(level, pos, context);
        if (storage == null) return null;

        if (storage instanceof ResourceSlot2EnergyStorage wrapper) {
            return wrapper.slot;
        }

        return new EnergyStorage2ResourceSlot(storage);
    }

    public static ResourceBlockLookup<ResourceSlot<EnergyResource>, @Nullable Direction> createSided() {
        return new EnergyApiImpl();
    }
}
