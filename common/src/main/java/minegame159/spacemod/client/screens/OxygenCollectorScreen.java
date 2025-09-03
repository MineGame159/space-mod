package minegame159.spacemod.client.screens;

import dev.architectury.utils.GameInstance;
import minegame159.spacemod.SpaceMod;
import minegame159.spacemod.client.screens.widgets.EnergyBar;
import minegame159.spacemod.menus.OxygenCollectorMenu;
import minegame159.spacemod.planets.Planets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class OxygenCollectorScreen extends BaseScreen<OxygenCollectorMenu> {
    private static final ResourceLocation BACKGROUND = SpaceMod.id("textures/gui/oxygen_collector.png");

    public OxygenCollectorScreen(OxygenCollectorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();

        var x = leftPos;
        var y = topPos;

        var planet = Planets.getForDimension(GameInstance.getClient().player.level().dimension());

        if (planet == null || !planet.hasOxygen()) {
            var text = SpaceMod.translatable("text", "planet_without_oxygen").withStyle(ChatFormatting.RED);
            var textWidth = font.width(text);

            addRenderableWidget(new MultiLineTextWidget(
                x + 170 / 2 - textWidth / 2, y + 18,
                text, font
            ));
        }

        addWidget(new EnergyBar(x + 13, y + 17, () -> menu.data.energy()));
    }

    @Override
    protected ResourceLocation getBackground() {
        return BACKGROUND;
    }
}
