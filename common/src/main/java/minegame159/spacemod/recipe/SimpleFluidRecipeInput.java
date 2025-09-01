package minegame159.spacemod.recipe;

import minegame159.spacemod.utils.FluidAmount;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.material.Fluid;

public record SimpleFluidRecipeInput(FluidAmount input, Fluid output) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return input.amount() == 0;
    }
}
