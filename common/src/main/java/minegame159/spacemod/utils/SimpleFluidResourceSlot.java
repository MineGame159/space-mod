package minegame159.spacemod.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import minegame159.spacemod.SpaceMod;
import minegame159.spacemod.api.ResourceInteraction;
import minegame159.spacemod.api.ResourceSnapshot;
import minegame159.spacemod.api.fluid.FluidResource;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;

import java.util.function.Predicate;

public class SimpleFluidResourceSlot extends SimpleResourceSlot<FluidResource> {
    public static final Codec<ResourceSnapshot<FluidResource>> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            FluidResource.CODEC.fieldOf("resource").forGetter(ResourceSnapshot::resource),
            Codec.INT.fieldOf("amount").forGetter(ResourceSnapshot::amount)
        ).apply(instance, ResourceSnapshot<FluidResource>::new)
    );

    public SimpleFluidResourceSlot(int capacity, ResourceInteraction.Mask insertMask, ResourceInteraction.Mask extractMask, Predicate<FluidResource> insertFilter, Runnable onUpdate) {
        super(capacity, insertMask, extractMask, insertFilter, onUpdate);
    }

    @Override
    protected FluidResource getEmpty() {
        return FluidResource.empty();
    }

    public void load(CompoundTag tag, HolderLookup.Provider registries, String name) {
        if (tag.contains(name, CompoundTag.TAG_COMPOUND)) {
            var ops = registries.createSerializationContext(NbtOps.INSTANCE);

            CODEC.parse(ops, tag.getCompound(name))
                .resultOrPartial(msg -> SpaceMod.LOGGER.error("Failed to parse fluid resource snapshot for Refinery: '{}'", msg))
                .ifPresent(snapshot -> setResource(snapshot.resource(), snapshot.amount()));
        }
    }

    public void save(CompoundTag tag, HolderLookup.Provider registries, String name) {
        var ops = registries.createSerializationContext(NbtOps.INSTANCE);

        CODEC.encodeStart(ops, new ResourceSnapshot<>(getResource(), getAmount()))
            .resultOrPartial(msg -> SpaceMod.LOGGER.error("Failed to encode fluid resource snapshot for Refinery: '{}'", msg))
            .ifPresent(snapshotTag -> tag.put(name, snapshotTag));
    }
}
