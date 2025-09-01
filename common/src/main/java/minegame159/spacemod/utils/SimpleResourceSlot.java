package minegame159.spacemod.utils;

import com.mojang.serialization.Codec;
import minegame159.spacemod.SpaceMod;
import minegame159.spacemod.api.ResourceInteraction;
import minegame159.spacemod.api.ResourceSlot;
import minegame159.spacemod.api.ResourceSnapshot;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;

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

    protected abstract Codec<ResourceSnapshot<R>> getCodec();

    @Override
    public R getResource() {
        return resource;
    }

    @Override
    public void setResource(R resource, int amount) {
        if (amount == 0) {
            resource = getEmpty();
        }

        if (!this.resource.equals(resource) || this.amount != amount) {
            this.resource = resource;
            this.amount = amount;

            update();
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

                if (this.amount == 0) {
                    this.resource = getEmpty();
                }
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

        return extractImpl(resource, maxAmount, interaction != ResourceInteraction.Simulation);
    }

    public int extractSkipMask(R resource, int maxAmount) {
        return extractImpl(resource, maxAmount, true);
    }

    @Override
    public void update() {
        if (onUpdate != null) {
            onUpdate.run();
        }
    }

    public void load(CompoundTag tag, HolderLookup.Provider registries, String name) {
        if (tag.contains(name, CompoundTag.TAG_COMPOUND)) {
            var ops = registries.createSerializationContext(NbtOps.INSTANCE);

            getCodec().parse(ops, tag.getCompound(name))
                .resultOrPartial(msg -> SpaceMod.LOGGER.error("Failed to parse resource snapshot: '{}'", msg))
                .ifPresent(snapshot -> setResource(snapshot.resource(), snapshot.amount()));
        }
    }

    public void save(CompoundTag tag, HolderLookup.Provider registries, String name) {
        var ops = registries.createSerializationContext(NbtOps.INSTANCE);

        getCodec().encodeStart(ops, new ResourceSnapshot<>(getResource(), getAmount()))
            .resultOrPartial(msg -> SpaceMod.LOGGER.error("Failed to encode resource snapshot: '{}'", msg))
            .ifPresent(snapshotTag -> tag.put(name, snapshotTag));
    }
}
