package minegame159.spacemod.api;

import com.google.common.collect.Iterators;
import minegame159.spacemod.api.fluid.FluidResource;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class StorageView2ResourceViewOrSlot implements ResourceView<FluidResource>, ResourceSlot<FluidResource> {
    private final Storage<FluidVariant> storage;
    private final StorageView<FluidVariant> view;

    public StorageView2ResourceViewOrSlot(Storage<FluidVariant> storage, StorageView<FluidVariant> view) {
        this.storage = storage;
        this.view = view;
    }

    public StorageView2ResourceViewOrSlot(Storage<FluidVariant> storage) {
        this(storage, storage.iterator().next());
    }

    // View

    @Override
    public @NotNull Iterator<ResourceSlot<FluidResource>> slots() {
        return Iterators.forArray(this);
    }

    @Override
    public @Nullable ResourceSlot<FluidResource> mainOutput() {
        return null;
    }


    // Slot

    @Override
    public FluidResource getResource() {
        var resource = view.getResource();
        return FluidResource.of(resource.getFluid(), resource.getComponents());
    }

    @Override
    public void setResource(FluidResource resource, int amount) {
        throw new IllegalStateException("Cannot call ResourceSlot.setResource on a StorageView2ResourceSlot");
    }

    @Override
    public int getAmount() {
        return (int) view.getAmount();
    }

    @Override
    public int getCapacity() {
        return (int) view.getCapacity();
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
    public int insert(FluidResource resource, int maxAmount, ResourceInteraction interaction) {
        try (var transaction = Transaction.openOuter()) {
            var amount = (int) storage.insert(FluidVariant.of(resource.getFluid(), resource.getComponents()), maxAmount, transaction);

            if (interaction != ResourceInteraction.Simulation) {
                transaction.commit();
            }

            return amount;
        }
    }

    @Override
    public int extract(FluidResource resource, int maxAmount, ResourceInteraction interaction) {
        try (var transaction = Transaction.openOuter()) {
            var amount = (int) view.extract(FluidVariant.of(resource.getFluid(), resource.getComponents()), maxAmount, transaction);

            if (interaction != ResourceInteraction.Simulation) {
                transaction.commit();
            }

            return amount;
        }
    }

    @Override
    public void update() {}
}
