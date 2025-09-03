package minegame159.spacemod.compat.emi;

import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import minegame159.spacemod.SpaceMod;
import minegame159.spacemod.items.ModDataComponents;
import minegame159.spacemod.items.OxygenStorage;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.Item;

public class FillOxygenRecipe extends BasicEmiRecipe {
    public FillOxygenRecipe(EmiRecipeCategory category, Item item) {
        super(
            category,
            SpaceMod.id("/fill_oxygen." + item.arch$registryName().getPath()),
            10 + 18 + 10 + 24 + 10 + 18 + 10,
            10 + 18 + 10
        );

        var capacity = item.components().get(ModDataComponents.OXYGEN_STORAGE.get()).capacity();

        inputs.add(EmiStack.of(item));

        outputs.add(EmiStack.of(item, DataComponentPatch.builder()
            .set(ModDataComponents.OXYGEN_STORAGE.get(), new OxygenStorage(capacity, capacity))
            .build()
        ));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(inputs.getFirst(), 10, 10);
        widgets.addFillingArrow(10 + 18 + 10, 10, 1000);
        widgets.addSlot(outputs.getFirst(), 10 + 18 + 10 + 24 + 10, 10).recipeContext(this);
    }
}
