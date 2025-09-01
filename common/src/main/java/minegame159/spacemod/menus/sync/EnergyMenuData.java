package minegame159.spacemod.menus.sync;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record EnergyMenuData(int amount, int capacity, int usage) {
    public static final StreamCodec<ByteBuf, EnergyMenuData> CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, EnergyMenuData::amount,
        ByteBufCodecs.VAR_INT, EnergyMenuData::capacity,
        ByteBufCodecs.VAR_INT, EnergyMenuData::usage,
        EnergyMenuData::new
    );

    public float progress() {
        return (float) amount / capacity;
    }
}
