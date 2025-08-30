package minegame159.spacemod.api;

import com.google.common.collect.Iterators;
import minegame159.spacemod.api.fluid.FluidResource;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class ResourceView2Storage implements Storage<FluidVariant> {
    public final ResourceView<FluidResource> resourceView;
    public final ResourceSlot2StorageView[] views;

    public ResourceView2Storage(ResourceView<FluidResource> view) {
        this.resourceView = view;

        //noinspection unchecked
        this.views = new ResourceSlot2StorageView[Iterators.size(view.slots())];

        var i = 0;

        for (var slot : view) {
            views[i] = new ResourceSlot2StorageView(slot);
            i++;
        }
    }

    @Override
    public boolean supportsInsertion() {
        for (var view : views) {
            if (view.slot.canInsert(ResourceInteraction.Automatic)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean supportsExtraction() {
        for (var view : views) {
            if (view.slot.canExtract(ResourceInteraction.Automatic)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public long insert(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        var amount = 0L;

        for (var view : views) {
            amount += view.insert(resource, maxAmount - amount, transaction);
            if (amount >= maxAmount) break;
        }

        return amount;
    }

    @Override
    public long extract(FluidVariant resource, long maxAmount, TransactionContext transaction) {
        var amount = 0L;

        for (var view : views) {
            amount += view.extract(resource, maxAmount - amount, transaction);
            if (amount >= maxAmount) break;
        }

        return amount;
    }

    @Override
    public @NotNull Iterator<StorageView<FluidVariant>> iterator() {
        return Iterators.forArray(views);
    }
}
