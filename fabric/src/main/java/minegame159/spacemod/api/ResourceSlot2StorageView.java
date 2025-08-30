package minegame159.spacemod.api;

import minegame159.spacemod.api.fluid.FluidResource;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import net.minecraft.world.level.material.Fluids;

public class ResourceSlot2StorageView extends SnapshotParticipant<ResourceSnapshot<FluidResource>> implements StorageView<FluidVariant> {
    public final ResourceSlot<FluidResource> slot;

    public ResourceSlot2StorageView(ResourceSlot<FluidResource> slot) {
        this.slot = slot;
    }

    @Override
    protected ResourceSnapshot<FluidResource> createSnapshot() {
        return new ResourceSnapshot<>(slot.getResource(), slot.getAmount());
    }

    @Override
    protected void readSnapshot(ResourceSnapshot<FluidResource> snapshot) {
        slot.setResource(snapshot.resource(), snapshot.amount());
    }

    @Override
    public long getAmount() {
        return slot.getAmount();
    }

    @Override
    public long getCapacity() {
        return slot.getCapacity();
    }

    @Override
    public boolean isResourceBlank() {
        return slot.getResource().getFluid() == Fluids.EMPTY;
    }

    @Override
    public FluidVariant getResource() {
        var resource = slot.getResource();
        return FluidVariant.of(resource.getFluid(), resource.getComponents());
    }

    public long insert(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        if (!slot.canInsert(ResourceInteraction.Automatic)) {
            return 0;
        }

        updateSnapshots(transaction);

        transaction.addCloseCallback((transaction1, result) -> {
            if (result.wasCommitted()) {
                slot.setResource(slot.getResource(), slot.getAmount());
            }
        });

        return slot.insert(FluidResource.of(resource.getFluid(), resource.getComponents()), (int) maxAmount, ResourceInteraction.Automatic);
    }

    @Override
    public long extract(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        if (!slot.canExtract(ResourceInteraction.Automatic)) {
            return 0;
        }

        updateSnapshots(transaction);

        transaction.addCloseCallback((transaction1, result) -> {
            if (result.wasCommitted()) {
                slot.setResource(slot.getResource(), slot.getAmount());
            }
        });

        return slot.extract(FluidResource.of(resource.getFluid(), resource.getComponents()), (int) maxAmount, ResourceInteraction.Automatic);
    }
}
