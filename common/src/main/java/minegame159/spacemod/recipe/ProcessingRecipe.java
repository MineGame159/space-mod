package minegame159.spacemod.recipe;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;

public interface ProcessingRecipe<I extends RecipeInput> extends Recipe<I> {
    int getTicks();
}
