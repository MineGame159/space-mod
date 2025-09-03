package minegame159.spacemod.compat.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import minegame159.spacemod.SpaceMod;
import minegame159.spacemod.blocks.ModBlocks;
import minegame159.spacemod.client.screens.RefineryScreen;
import minegame159.spacemod.items.ModDataComponents;
import minegame159.spacemod.items.ModItems;
import minegame159.spacemod.recipe.ModRecipeTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

import java.util.function.BiFunction;

@EmiEntrypoint
public class SpaceModEmiPlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        addRecipes(registry, ModRecipeTypes.REFINING.get(), ModBlocks.REFINERY.get(), RefineryEmiRecipe::new);
        registry.addStackProvider(RefineryScreen.class, new BaseScreenProvider<>());

        addFillOxygenRecipes(registry);
    }

    private <I extends RecipeInput, R extends Recipe<I>> void addRecipes(EmiRegistry registry, RecipeType<R> type, Block block, BiFunction<EmiRecipeCategory, RecipeHolder<R>, EmiRecipe> factory) {
        var stack = EmiStack.of(block);

        var category = new EmiRecipeCategory(
            SpaceMod.id(type.toString()),
            stack
        );

        registry.addCategory(category);
        registry.addWorkstation(category, stack);

        for (var holder : registry.getRecipeManager().getAllRecipesFor(type)) {
            registry.addRecipe(factory.apply(category, holder));
        }
    }

    private void addFillOxygenRecipes(EmiRegistry registry) {
        var category = new EmiRecipeCategory(
            SpaceMod.id("fill_oxygen"),
            EmiStack.of(ModItems.OXYGEN_TANK.get())
        );

        registry.addCategory(category);
        registry.addWorkstation(category, EmiStack.of(ModBlocks.OXYGEN_COLLECTOR.get()));

        for (var item : BuiltInRegistries.ITEM) {
            if (item.components().has(ModDataComponents.OXYGEN_STORAGE.get())) {
                registry.addRecipe(new FillOxygenRecipe(category, item));
            }
        }
    }
}
