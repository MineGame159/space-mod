package minegame159.spacemod.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;

import java.util.function.Supplier;

public final class SimpleFluidRecipe implements ProcessingRecipe<SimpleFluidRecipeInput> {
    private final RecipeType<SimpleFluidRecipe> type;
    private final RecipeSerializer<SimpleFluidRecipe> serializer;

    public final FluidIngredient input;
    public final int ticks;
    public final FluidIngredient output;

    public SimpleFluidRecipe(RecipeType<SimpleFluidRecipe> type, RecipeSerializer<SimpleFluidRecipe> serializer, FluidIngredient input, int ticks, FluidIngredient output) {
        this.type = type;
        this.serializer = serializer;

        this.input = input;
        this.ticks = ticks;
        this.output = output;
    }

    @Override
    public int getTicks() {
        return ticks;
    }

    @Override
    public boolean matches(SimpleFluidRecipeInput input, Level level) {
        return this.input.matches(input.input().fluid()) && input.input().amount() >= this.input.amount()
            && (input.output() == Fluids.EMPTY || this.output.matches(input.output()));
    }

    @Override
    public ItemStack assemble(SimpleFluidRecipeInput input, HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return serializer;
    }

    @Override
    public RecipeType<?> getType() {
        return type;
    }

    public FluidIngredient input() {
        return input;
    }

    public int ticks() {
        return ticks;
    }

    public FluidIngredient output() {
        return output;
    }

    @Override
    public String toString() {
        return "SimpleFluidRecipe[" +
            "input=" + input + ", " +
            "ticks=" + ticks + ", " +
            "output=" + output + ']';
    }

    public static class Serializer implements RecipeSerializer<SimpleFluidRecipe> {
        private final MapCodec<SimpleFluidRecipe> codec;

        private final StreamCodec<RegistryFriendlyByteBuf, SimpleFluidRecipe> streamCodec;

        public Serializer(Supplier<RecipeType<SimpleFluidRecipe>> type) {
            this.codec = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                    FluidIngredient.TAG_ID_CODEC.fieldOf("input").forGetter(SimpleFluidRecipe::input),
                    Codec.INT.fieldOf("ticks").forGetter(SimpleFluidRecipe::ticks),
                    FluidIngredient.ID_CODEC.fieldOf("output").forGetter(SimpleFluidRecipe::output)
                ).apply(
                    instance,
                    (input, ticks, output) -> new SimpleFluidRecipe(type.get(), this, input, ticks, output)
                )
            );

            this.streamCodec = StreamCodec.composite(
                FluidIngredient.TAG_ID_STREAM_CODEC, SimpleFluidRecipe::input,
                ByteBufCodecs.VAR_INT, SimpleFluidRecipe::ticks,
                FluidIngredient.ID_STREAM_CODEC, SimpleFluidRecipe::output,
                (input, ticks, output) -> new SimpleFluidRecipe(type.get(), this, input, ticks, output)
            );
        }

        @Override
        public MapCodec<SimpleFluidRecipe> codec() {
            return codec;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SimpleFluidRecipe> streamCodec() {
            return streamCodec;
        }
    }
}
