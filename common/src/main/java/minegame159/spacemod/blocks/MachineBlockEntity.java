package minegame159.spacemod.blocks;

import minegame159.spacemod.api.ResourceInteraction;
import minegame159.spacemod.api.energy.EnergyResource;
import minegame159.spacemod.utils.SimpleEnergyResourceSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public abstract class MachineBlockEntity extends BlockEntity {
    public final SimpleEnergyResourceSlot energy = new SimpleEnergyResourceSlot(
        10000,
        ResourceInteraction.Mask.Automatic,
        ResourceInteraction.Mask.Manual,
        resource -> true,
        this::setChanged
    );

    protected final int energyConsumption;

    private List<Runnable> changeListeners;

    public MachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, int energyConsumption) {
        super(type, pos, blockState);

        this.energyConsumption = energyConsumption;
    }

    public Runnable addChangeListener(Runnable listener) {
        if (changeListeners == null) {
            changeListeners = new ArrayList<>();
        }

        changeListeners.add(listener);
        return listener;
    }

    public void removeChangeListener(Runnable listener) {
        if (changeListeners != null) {
            changeListeners.remove(listener);
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();

        if (changeListeners != null) {
            for (var listener : changeListeners) {
                listener.run();
            }
        }
    }

    protected abstract boolean tickWork();

    public void tick() {
        tryTickWork();
    }

    protected boolean tryTickWork() {
        if (energy.getAmount() >= energyConsumption) {
            if (tickWork()) {
                energy.extractSkipMask(EnergyResource.INSTANCE, energyConsumption);
            }

            return true;
        }

        return false;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        energy.load(tag, registries, "energy");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        energy.save(tag, registries, "energy");
    }
}
