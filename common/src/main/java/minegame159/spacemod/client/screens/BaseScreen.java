package minegame159.spacemod.client.screens;

import minegame159.spacemod.client.screens.widgets.BaseWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public abstract class BaseScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    public final List<BaseWidget> widgets = new ArrayList<>();

    public BaseScreen(T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    protected void addWidget(BaseWidget widget) {
        addRenderableOnly(widget);
        widgets.add(widget);
    }

    @Override
    protected void rebuildWidgets() {
        widgets.clear();

        super.rebuildWidgets();
    }

    protected abstract ResourceLocation getBackground();

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        var x = leftPos;
        var y = topPos;

        guiGraphics.blit(getBackground(), x, y, 0, 0, imageWidth, imageHeight);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderTooltip(guiGraphics, mouseX, mouseY);

        for (var widget : widgets) {
            widget.renderHover(guiGraphics, font, mouseX, mouseY);
        }
    }
}
