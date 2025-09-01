package minegame159.spacemod.menus.sync;

import minegame159.spacemod.SpaceMod;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record MenuDataS2CPacket<T>(int containerId, MenuDataType<T> dataType, T data) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MenuDataS2CPacket<?>> TYPE = new Type<>(SpaceMod.id("menu_data"));

    public static final StreamCodec<RegistryFriendlyByteBuf, MenuDataS2CPacket<?>> CODEC = StreamCodec.ofMember(
        MenuDataS2CPacket::write,
        MenuDataS2CPacket::read
    );

    private void write(RegistryFriendlyByteBuf buffer) {
        buffer.writeVarInt(containerId);
        buffer.writeVarInt(dataType.id());
        dataType.codec().encode(buffer, data);
    }

    private static MenuDataS2CPacket<?> read(RegistryFriendlyByteBuf buffer) {
        var containerId = buffer.readVarInt();
        var dataType = MenuDataType.getById(buffer.readVarInt());
        var data = dataType.codec().decode(buffer);

        return new MenuDataS2CPacket<>(containerId, dataType, data);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
