package minegame159.spacemod.recipe;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import minegame159.spacemod.SpaceMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;

public final class ModRecipeTypes {
    private ModRecipeTypes() {}

    public static void init() {
        REGISTRY.register();
    }

    // Recipe types

    public static final DeferredRegister<RecipeType<?>> REGISTRY = DeferredRegister.create(
        SpaceMod.ID,
        Registries.RECIPE_TYPE
    );

    private static <R extends Recipe<I>, I extends RecipeInput> DeferredSupplier<RecipeType<R>> register(String id) {
        return REGISTRY.register(
            id,
            () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return id;
                }
            }
        );
    }

    public static final DeferredSupplier<RecipeType<SimpleFluidRecipe>> REFINING = register("refining");
}
