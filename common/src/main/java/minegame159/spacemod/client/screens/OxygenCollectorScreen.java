package minegame159.spacemod.client.screens;

import minegame159.spacemod.SpaceMod;
import minegame159.spacemod.client.screens.widgets.EnergyBar;
import minegame159.spacemod.menus.OxygenCollectorMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

        addWidget(new EnergyBar(x + 13, y + 17, () -> menu.data.energy()));
    }

    @Override
    protected ResourceLocation getBackground() {
        return BACKGROUND;
    }
}
