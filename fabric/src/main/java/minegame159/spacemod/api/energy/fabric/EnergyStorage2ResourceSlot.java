package minegame159.spacemod.api.energy.fabric;

import minegame159.spacemod.api.ResourceInteraction;
import minegame159.spacemod.api.ResourceSlot;
import minegame159.spacemod.api.energy.EnergyResource;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import team.reborn.energy.api.EnergyStorage;

public class EnergyStorage2ResourceSlot implements ResourceSlot<EnergyResource> {
    private final EnergyStorage storage;

    public EnergyStorage2ResourceSlot(EnergyStorage storage) {
        this.storage = storage;
    }

    @Override
    public EnergyResource getResource() {
        return EnergyResource.INSTANCE;
    }

    @Override
    public void setResource(EnergyResource resource, int amount) {
        throw new IllegalStateException("Cannot call ResourceSlot.setResource on a EnergyStorage2ResourceSlot");
    }

    @Override
    public int getAmount() {
        return (int) storage.getAmount();
    }

    @Override
    public int getCapacity() {
        return (int) storage.getCapacity();
    }

    @Override
    public boolean canInsert(ResourceInteraction interaction) {
        return switch (interaction) {
            case Automatic, Manual -> storage.supportsInsertion();
            case Simulation -> true;
        };
    }

    @Override
    public boolean canExtract(ResourceInteraction interaction) {
        return switch (interaction) {
            case Automatic, Manual -> storage.supportsExtraction();
            case Simulation -> true;
        };
    }

    @Override
    public int insert(EnergyResource resource, int maxAmount, ResourceInteraction interaction) {
        try (var transaction = Transaction.openOuter()) {
            var amount = (int) storage.insert(maxAmount, transaction);

            if (interaction != ResourceInteraction.Simulation) {
                transaction.commit();
            }

            return amount;
        }
    }

    @Override
    public int extract(EnergyResource resource, int maxAmount, ResourceInteraction interaction) {
        try (var transaction = Transaction.openOuter()) {
            var amount = (int) storage.extract(maxAmount, transaction);

            if (interaction != ResourceInteraction.Simulation) {
                transaction.commit();
            }

            return amount;
        }
    }
}
