package minegame159.spacemod.utils;

import minegame159.spacemod.api.ResourceInteraction;
import minegame159.spacemod.api.ResourceSlot;

import java.util.function.Predicate;

public abstract class SimpleResourceSlot<R> implements ResourceSlot<R> {
    private final int capacity;

    private final ResourceInteraction.Mask insertMask;
    private final ResourceInteraction.Mask extractMask;

    private final Predicate<R> insertFilter;
    private final Runnable onUpdate;

    private R resource;
    private int amount;

    public SimpleResourceSlot(int capacity, ResourceInteraction.Mask insertMask, ResourceInteraction.Mask extractMask, Predicate<R> insertFilter, Runnable onUpdate) {
        this.capacity = capacity;

        this.insertMask = insertMask;
        this.extractMask = extractMask;

        this.insertFilter = insertFilter;
        this.onUpdate = onUpdate;

        this.resource = getEmpty();
        this.amount = 0;
    }

    protected abstract R getEmpty();

    @Override
    public R getResource() {
        return resource;
    }

    @Override
    public void setResource(R resource, int amount) {
        this.resource = resource;
        this.amount = amount;

        if (onUpdate != null) {
            onUpdate.run();
        }
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean canInsert(ResourceInteraction interaction) {
        return insertMask.allows(interaction);
    }

    @Override
    public boolean canExtract(ResourceInteraction interaction) {
        return extractMask.allows(interaction);
    }

    private int insertImpl(R resource, int maxAmount, boolean apply) {
        if (insertFilter.test(resource) && (amount == 0 || resource.equals(this.resource))) {
            var free = capacity - amount;
            var amount = Math.min(maxAmount, free);

            if (amount > 0 && apply) {
                if (resource.equals(this.resource)) {
                    this.amount += amount;
                } else {
                    this.resource = resource;
                    this.amount = amount;
                }
            }

            return amount;
        }

        return 0;
    }

    @Override
    public int insert(R resource, int maxAmount, ResourceInteraction interaction) {
        if (!canInsert(interaction)) {
            return 0;
        }

        return insertImpl(resource, maxAmount, interaction != ResourceInteraction.Simulation);
    }

    public int insertSkipMask(R resource, int maxAmount) {
        return insertImpl(resource, maxAmount, true);
    }

    private int extractImpl(R resource, int maxAmount, boolean apply) {
        if (resource.equals(this.resource)) {
            var amount = Math.min(maxAmount, this.amount);

            if (amount > 0 && apply) {
                this.amount -= amount;
            }

            return amount;
        }

        return 0;
    }

    @Override
    public int extract(R resource, int maxAmount, ResourceInteraction interaction) {
        if (!canExtract(interaction)) {
            return 0;
        }

        var apply = interaction != ResourceInteraction.Simulation;
        var amount = extractImpl(resource, maxAmount, apply);

        if (apply && this.amount == 0) {
            this.resource = getEmpty();
        }

        return amount;
    }

    public int extractSkipMask(R resource, int maxAmount) {
        return extractImpl(resource, maxAmount, true);
    }
}
