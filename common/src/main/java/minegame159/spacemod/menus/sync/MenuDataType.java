package minegame159.spacemod.menus.sync;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;

public record MenuDataType<T>(int id, StreamCodec<RegistryFriendlyByteBuf, T> codec) {
    private static final List<MenuDataType<?>> TYPES = new ArrayList<>();

    public static <T> MenuDataType<T> create(StreamCodec<RegistryFriendlyByteBuf, T> codec) {
        var type = new MenuDataType<>(TYPES.size() + 1, codec);

        TYPES.add(type);
        return type;
    }

    @SuppressWarnings("unchecked")
    public static <T> MenuDataType<T> getById(int id) {
        return (MenuDataType<T>) TYPES.get(id - 1);
    }
}
