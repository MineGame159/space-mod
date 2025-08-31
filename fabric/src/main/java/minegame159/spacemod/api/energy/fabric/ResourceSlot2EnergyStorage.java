package minegame159.spacemod.api.energy.fabric;

import minegame159.spacemod.api.ResourceInteraction;
import minegame159.spacemod.api.ResourceSlot;
import minegame159.spacemod.api.ResourceSnapshot;
import minegame159.spacemod.api.energy.EnergyResource;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import team.reborn.energy.api.EnergyStorage;

public class ResourceSlot2EnergyStorage extends SnapshotParticipant<ResourceSnapshot<EnergyResource>> implements EnergyStorage {
    public final ResourceSlot<EnergyResource> slot;

    public ResourceSlot2EnergyStorage(ResourceSlot<EnergyResource> slot) {
        this.slot = slot;
    }

    @Override
    protected ResourceSnapshot<EnergyResource> createSnapshot() {
        return new ResourceSnapshot<>(slot.getResource(), slot.getAmount());
    }

    @Override
    protected void readSnapshot(ResourceSnapshot<EnergyResource> snapshot) {
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
    public boolean supportsInsertion() {
        return slot.canInsert(ResourceInteraction.Automatic);
    }

    @Override
    public boolean supportsExtraction() {
        return slot.canExtract(ResourceInteraction.Automatic);
    }

    @Override
    public long insert(long maxAmount, TransactionContext transaction) {
        if (!slot.canInsert(ResourceInteraction.Automatic)) {
            return 0;
        }

        updateSnapshots(transaction);

        transaction.addCloseCallback((transaction1, result) -> {
            if (result.wasCommitted()) {
                slot.setResource(slot.getResource(), slot.getAmount());
            }
        });

        return slot.insert(EnergyResource.INSTANCE, (int) maxAmount, ResourceInteraction.Automatic);
    }

    @Override
    public long extract(long maxAmount, TransactionContext transaction) {
        if (!slot.canExtract(ResourceInteraction.Automatic)) {
            return 0;
        }

        updateSnapshots(transaction);

        transaction.addCloseCallback((transaction1, result) -> {
            if (result.wasCommitted()) {
                slot.setResource(slot.getResource(), slot.getAmount());
            }
        });

        return slot.insert(EnergyResource.INSTANCE, (int) maxAmount, ResourceInteraction.Automatic);
    }
}
