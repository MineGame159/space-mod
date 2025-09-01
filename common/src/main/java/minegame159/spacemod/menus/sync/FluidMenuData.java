package minegame159.spacemod.menus.sync;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.material.Fluid;

public record FluidMenuData(Fluid fluid, int amount, int capacity) {
    public static final StreamCodec<RegistryFriendlyByteBuf, FluidMenuData> CODEC = StreamCodec.composite(
        ByteBufCodecs.registry(Registries.FLUID), FluidMenuData::fluid,
        ByteBufCodecs.VAR_INT, FluidMenuData::amount,
        ByteBufCodecs.VAR_INT, FluidMenuData::capacity,
        FluidMenuData::new
    );

    public float progress() {
        return (float) amount / capacity;
    }
}
