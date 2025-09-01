package minegame159.spacemod.recipe;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import minegame159.spacemod.SpaceMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public final class ModRecipeSerializers {
    private ModRecipeSerializers() {}

    public static void init() {
        REGISTRY.register();
    }

    // Recipe serializers

    public static final DeferredRegister<RecipeSerializer<?>> REGISTRY = DeferredRegister.create(
        SpaceMod.ID,
        Registries.RECIPE_SERIALIZER
    );

    public static final DeferredSupplier<SimpleFluidRecipe.Serializer> REFINING = REGISTRY.register(
        "refining",
        () -> new SimpleFluidRecipe.Serializer(ModRecipeTypes.REFINING)
    );
}
