package minegame159.spacemod.client.screens.widgets;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public abstract class BaseWidget implements Renderable {
    public final int x;
    public final int y;

    public final int width;
    public final int height;

    public BaseWidget(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void renderHover(GuiGraphics guiGraphics, Font font, int mouseX, int mouseY) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            renderTooltip(guiGraphics, font, mouseX, mouseY);
        }
    }

    public List<Component> getTooltip() {
        return List.of();
    }

    protected void renderTooltip(GuiGraphics guiGraphics, Font font, int mouseX, int mouseY) {
        guiGraphics.renderTooltip(font, getTooltip(), Optional.empty(), mouseX, mouseY);
    }
}
