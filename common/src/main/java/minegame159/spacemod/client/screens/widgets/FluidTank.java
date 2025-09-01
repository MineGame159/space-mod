package minegame159.spacemod.client.screens.widgets;

import minegame159.spacemod.api.fluid.FluidApi;
import minegame159.spacemod.menus.sync.FluidMenuData;
import minegame159.spacemod.utils.Components;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.material.Fluids;

import java.util.List;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class FluidTank extends BaseWidget {
    public static final int WIDTH = Sprites.FLUID_TANK_BACKGROUND.width();
    public static final int HEIGHT = Sprites.FLUID_TANK_BACKGROUND.height();

    public final Supplier<FluidMenuData> provider;

    public FluidTank(int x, int y, Supplier<FluidMenuData> provider) {
        super(x, y, WIDTH, HEIGHT);

        this.provider = provider;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        var data = provider.get();

        if (data.fluid() != Fluids.EMPTY) {
            var sprite = FluidApi.getSprite(data.fluid());

            if (sprite != null) {
                var remaining = (int) (data.progress() * (height - 2));
                var color = FluidApi.getColor(data.fluid());

                var y = this.y + 1 + (height - 2);

                while (remaining > 0) {
                    var height = Math.min(remaining, 16);

                    guiGraphics.innerBlit(
                        sprite.atlasLocation(),
                        x + 1, x + 1 + 16,
                        y - height, y - height + height,
                        0,
                        sprite.getU0(), sprite.getU1(),
                        sprite.getV0(), sprite.getV0() + (sprite.getV1() - sprite.getV0()) * (height / 16f),
                        FastColor.ARGB32.red(color) / 255f,
                        FastColor.ARGB32.green(color) / 255f,
                        FastColor.ARGB32.blue(color) / 255f,
                        FastColor.ARGB32.alpha(color) / 255f
                    );

                    remaining -= height;
                    y -= height;
                }
            }
        }

        guiGraphics.blitSprite(Sprites.FLUID_TANK_OVERLAY.id(), x + 1, y + 1, 16, 50);
    }

    @Override
    public List<Component> getTooltip() {
        var data = provider.get();
        if (data.fluid() == Fluids.EMPTY) return List.of();

        return List.of(
            Components.fluidName(data.fluid()),
            Components.fluidAmount(data.amount(), data.capacity()).withStyle(ChatFormatting.GRAY)
        );
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, Font font, int mouseX, int mouseY) {
        guiGraphics.fill(RenderType.guiOverlay(), x + 1, y + 1, x + width - 1, y + height - 1, 0, -2130706433);

        super.renderTooltip(guiGraphics, font, mouseX, mouseY);
    }
}
