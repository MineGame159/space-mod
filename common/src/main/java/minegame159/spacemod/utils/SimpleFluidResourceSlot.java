package minegame159.spacemod.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import minegame159.spacemod.api.ResourceInteraction;
import minegame159.spacemod.api.ResourceSnapshot;
import minegame159.spacemod.api.fluid.FluidResource;
import minegame159.spacemod.menus.sync.FluidMenuData;

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

    @Override
    protected Codec<ResourceSnapshot<FluidResource>> getCodec() {
        return CODEC;
    }

    public FluidMenuData getMenuData() {
        return new FluidMenuData(getResource().getFluid(), getAmount(), getCapacity());
    }
}
