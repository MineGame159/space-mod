package minegame159.spacemod.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record OxygenStorage(int amount, int capacity) {
    public static final Codec<OxygenStorage> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.INT.fieldOf("amount").forGetter(OxygenStorage::amount),
            Codec.INT.fieldOf("capacity").forGetter(OxygenStorage::capacity)
        ).apply(instance, OxygenStorage::new)
    );

    public static final StreamCodec<ByteBuf, OxygenStorage> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, OxygenStorage::amount,
        ByteBufCodecs.VAR_INT, OxygenStorage::capacity,
        OxygenStorage::new
    );

    public int free() {
        return capacity - amount;
    }

    public float progress() {
        return (float) amount / capacity;
    }
}
