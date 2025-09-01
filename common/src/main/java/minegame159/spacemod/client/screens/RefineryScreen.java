package minegame159.spacemod.client.screens;

import minegame159.spacemod.SpaceMod;
import minegame159.spacemod.client.screens.widgets.EnergyBar;
import minegame159.spacemod.client.screens.widgets.FluidTank;
import minegame159.spacemod.client.screens.widgets.Progress;
import minegame159.spacemod.menus.RefineryMenu;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

@Environment(EnvType.CLIENT)
public class RefineryScreen extends BaseScreen<RefineryMenu> {
    private static final ResourceLocation BACKGROUND = SpaceMod.id("textures/gui/refinery.png");

    public RefineryScreen(RefineryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();

        var x = leftPos;
        var y = topPos;

        addWidget(new EnergyBar(x + 13, y + 17, () -> menu.data.energy()));
        addWidget(new FluidTank(x + 43, y + 17, () -> menu.data.input()));
        addWidget(new FluidTank(x + 115, y + 17, () -> menu.data.output()));
        addWidget(new Progress(x + 77, y + 35, () -> menu.data.progress()));
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        var x = leftPos;
        var y = topPos;

        guiGraphics.blit(BACKGROUND, x, y, 0, 0, imageWidth, imageHeight);
    }
}
