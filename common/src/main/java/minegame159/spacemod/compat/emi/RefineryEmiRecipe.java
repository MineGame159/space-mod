package minegame159.spacemod.compat.emi;

import dev.architectury.utils.GameInstance;
import dev.emi.emi.api.recipe.BasicEmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.TextureWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import minegame159.spacemod.SpaceMod;
import minegame159.spacemod.api.fluid.FluidApi;
import minegame159.spacemod.client.screens.widgets.Sprites;
import minegame159.spacemod.recipe.SimpleFluidRecipe;
import minegame159.spacemod.utils.Components;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class RefineryEmiRecipe extends BasicEmiRecipe {
    private final int ticks;

    public RefineryEmiRecipe(EmiRecipeCategory category, RecipeHolder<SimpleFluidRecipe> holder) {
        super(
            category,
            holder.id(),
            10 + Sprites.ENERGY_BACKGROUND.width() + 10 + Sprites.FLUID_TANK_BACKGROUND.width() + 10 + 24 + 10 + Sprites.FLUID_TANK_BACKGROUND.width() + 10,
            10 + Sprites.FLUID_TANK_BACKGROUND.height() + 10
        );

        var level = GameInstance.getClient().level;
        var recipe = holder.value();

        this.ticks = recipe.ticks();

        if (recipe.input().fluidTag() != null) {
            inputs.add(EmiIngredient.of(recipe.input().fluidTag(), (recipe.input().amount())));
        } else {
            inputs.add(EmiStack.of(recipe.input().getFluid(level), (recipe.input().amount())));
        }

        outputs.add(EmiStack.of(recipe.output().getFluid(level), (recipe.output().amount())));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        var input = inputs.getFirst();
        var output = outputs.getFirst();

        var capacity = Math.max(Math.max((int) input.getAmount(), (int) output.getAmount()), FluidApi.BUCKET_AMOUNT);

        var x = 10;

        energyBar(widgets, x, 10, 20, ticks);
        x += Sprites.ENERGY_BACKGROUND.width() + 10;

        tank(widgets, x, 10, input, capacity);
        x += Sprites.FLUID_TANK_BACKGROUND.width() + 10;

        progress(widgets, x, height / 2 - 17 / 2, ticks);
        x += 24 + 10;

        tank(widgets, x, 10, output, capacity).recipeContext(this);
    }

    private static void energyBar(WidgetHolder widgets, int x, int y, int consumption, int ticks) {
        texture(widgets, Sprites.ENERGY_BACKGROUND, x, y).tooltipText(List.of(
            Component.literal("Total: ").append(Components.energyAmount(consumption * ticks)),
            Component.literal("Consumption: ").append(Components.energyUsage(consumption)).withStyle(ChatFormatting.GRAY)
        ));

        texture(widgets, Sprites.ENERGY_FULL, x + 1, y + 1);
    }

    private static SlotWidget tank(WidgetHolder widgets, int x, int y, EmiIngredient stack, int capacity) {
        texture(widgets, Sprites.FLUID_TANK_BACKGROUND, x, y);
        var tank = widgets.addTank(stack, x, y, Sprites.FLUID_TANK_BACKGROUND.width(), Sprites.FLUID_TANK_BACKGROUND.height(), capacity).drawBack(false);
        texture(widgets, Sprites.FLUID_TANK_OVERLAY, x + 1, y + 1);

        return tank;
    }

    private static void progress(WidgetHolder widgets, int x, int y, int ticks) {
        widgets.addFillingArrow(x, y, 1000 / 20 * ticks)
            .tooltipText(List.of(SpaceMod.translatable("text", "tick_duration", ticks)));
    }

    private static TextureWidget texture(WidgetHolder widgets, Sprites.Sprite sprite, int x, int y) {
        var id = ResourceLocation.fromNamespaceAndPath(sprite.id().getNamespace(), "textures/gui/sprites/" + sprite.id().getPath() + ".png");
        return widgets.addTexture(id, x, y, sprite.width(), sprite.height(), 0, 0, sprite.width(), sprite.height(), sprite.width(), sprite.height());
    }
}
