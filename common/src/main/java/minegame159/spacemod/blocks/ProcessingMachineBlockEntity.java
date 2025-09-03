package minegame159.spacemod.blocks;

import minegame159.spacemod.recipe.ProcessingRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class ProcessingMachineBlockEntity<R extends ProcessingRecipe<I>, I extends RecipeInput> extends MachineBlockEntity {
    private final RecipeManager.CachedCheck<I, R> recipeLookup;

    private R recipe;
    private int progress;

    public ProcessingMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, int energyConsumption, Supplier<RecipeType<R>> recipeType) {
        super(type, pos, blockState, energyConsumption);

        this.recipeLookup = RecipeManager.createCheck(recipeType.get());
    }

    public Optional<Float> getProgress() {
        if (recipe == null) {
            return Optional.empty();
        }

        return Optional.of((float) (progress + 1) / recipe.getTicks());
    }

    protected abstract I getCurrentInput();

    protected abstract void onRecipeDone(R recipe);

    @Override
    protected boolean tryTickWork() {
        if (!super.tryTickWork()) {
            if (recipe != null && !recipe.matches(getCurrentInput(), level)) {
                recipe = null;
                progress = 0;

                setChanged();
            }

            return false;
        }

        return true;
    }

    @Override
    protected boolean tickWork() {
        if (recipe == null) {
            recipe = recipeLookup.getRecipeFor(getCurrentInput(), level).map(RecipeHolder::value).orElse(null);

            if (recipe != null) {
                return checkProgress();
            }

            return false;
        } else {
            if (recipe.matches(getCurrentInput(), level)) {
                progress++;

                if (!checkProgress()) {
                    setChanged();
                }

                return true;
            } else {
                recipe = null;
                progress = 0;

                setChanged();
                return false;
            }
        }
    }

    private boolean checkProgress() {
        if (progress >= recipe.getTicks()) {
            onRecipeDone(recipe);

            recipe = null;
            progress = 0;

            return true;
        }

        return false;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        progress = tag.getInt("progress");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.putInt("progress", progress);
    }
}
