package minegame159.spacemod.recipe;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import minegame159.spacemod.api.fluid.FluidApi;
import minegame159.spacemod.api.fluid.FluidResource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

public record FluidIngredient(Either<TagKey<Fluid>, ResourceLocation> fluid, int amount) {
    private static final Codec<Integer> AMOUNT_CODEC = Codec.INT.xmap(
        FluidApi::mbToPlatformUnit,
        FluidApi::platformUnitToMb
    );

    public static final Codec<FluidIngredient> TAG_ID_CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.either(
                TagKey.hashedCodec(Registries.FLUID),
                ResourceLocation.CODEC
            ).fieldOf("fluid").forGetter(FluidIngredient::fluid),
            AMOUNT_CODEC.fieldOf("amount").forGetter(FluidIngredient::amount)
        ).apply(instance, FluidIngredient::new)
    );

    public static final Codec<FluidIngredient> ID_CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            ResourceLocation.CODEC.xmap(
                Either::<TagKey<Fluid>, ResourceLocation>right,
                either -> either.right().orElseThrow()
            ).fieldOf("fluid").forGetter(FluidIngredient::fluid),
            AMOUNT_CODEC.fieldOf("amount").forGetter(FluidIngredient::amount)
        ).apply(instance, FluidIngredient::new)
    );

    private static final StreamCodec<ByteBuf, TagKey<Fluid>> FLUID_TAG_STREAM_CODEC = ResourceLocation.STREAM_CODEC.map(
        id -> TagKey.create(Registries.FLUID, id),
        TagKey::location
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, FluidIngredient> TAG_ID_STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.either(
            FLUID_TAG_STREAM_CODEC,
            ResourceLocation.STREAM_CODEC
        ),
        FluidIngredient::fluid,
        ByteBufCodecs.VAR_INT,
        FluidIngredient::amount,
        FluidIngredient::new
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, FluidIngredient> ID_STREAM_CODEC = StreamCodec.composite(
        ResourceLocation.STREAM_CODEC.map(
            Either::right,
            either -> either.right().orElseThrow()
        ),
        FluidIngredient::fluid,
        ByteBufCodecs.VAR_INT,
        FluidIngredient::amount,
        FluidIngredient::new
    );

    public TagKey<Fluid> fluidTag() {
        return this.fluid.left().orElse(null);
    }

    public ResourceLocation fluidId() {
        return this.fluid.right().orElse(null);
    }

    public boolean matches(Fluid fluid) {
        if (fluidTag() != null) {
            return fluid.is(fluidTag());
        }

        return BuiltInRegistries.FLUID.getKey(fluid).equals(fluidId());
    }

    public Fluid getFluid(Level level) {
        var id = this.fluid.right().orElseThrow(() -> new IllegalStateException("FluidIngredient.getFluid() cannot be used with a tag"));
        return level.registryAccess().registryOrThrow(Registries.FLUID).get(id);
    }

    public FluidResource getFluidResource(Level level) {
        return FluidResource.of(getFluid(level));
    }
}
