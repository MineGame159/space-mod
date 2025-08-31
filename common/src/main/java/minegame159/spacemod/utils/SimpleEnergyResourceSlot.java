package minegame159.spacemod.utils;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import minegame159.spacemod.api.ResourceInteraction;
import minegame159.spacemod.api.ResourceSnapshot;
import minegame159.spacemod.api.energy.EnergyResource;

import java.util.function.Predicate;

public class SimpleEnergyResourceSlot extends SimpleResourceSlot<EnergyResource> {
    public static final Codec<ResourceSnapshot<EnergyResource>> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.INT.fieldOf("amount").forGetter(ResourceSnapshot::amount)
        ).apply(instance, amount -> new ResourceSnapshot<>(EnergyResource.INSTANCE, amount))
    );

    public SimpleEnergyResourceSlot(int capacity, ResourceInteraction.Mask insertMask, ResourceInteraction.Mask extractMask, Predicate<EnergyResource> insertFilter, Runnable onUpdate) {
        super(capacity, insertMask, extractMask, insertFilter, onUpdate);
    }

    @Override
    protected EnergyResource getEmpty() {
        return EnergyResource.INSTANCE;
    }

    @Override
    protected Codec<ResourceSnapshot<EnergyResource>> getCodec() {
        return CODEC;
    }
}
