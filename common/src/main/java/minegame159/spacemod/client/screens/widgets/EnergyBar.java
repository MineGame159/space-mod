package minegame159.spacemod.client.screens.widgets;

import minegame159.spacemod.menus.sync.EnergyMenuData;
import minegame159.spacemod.utils.Components;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class EnergyBar extends BaseWidget {
    public static final int WIDTH = Sprites.ENERGY_FULL.width() + 2;
    public static final int HEIGHT = Sprites.ENERGY_FULL.height() + 2;

    public final Supplier<EnergyMenuData> provider;

    public EnergyBar(int x, int y, Supplier<EnergyMenuData> provider) {
        super(x, y, WIDTH, HEIGHT);

        this.provider = provider;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        var progress = provider.get().progress();

        var uvHeight = (int) (progress * (height - 2));
        var invUvHeight = (int) ((1 - progress) * (height - 2));

        if (uvHeight > 0) {
            guiGraphics.blitSprite(
                Sprites.ENERGY_FULL.id(),
                width - 2, (height - 2),
                0, invUvHeight,
                x + 1, y + 1 + invUvHeight,
                4, uvHeight
            );
        }
    }

    @Override
    public List<Component> getTooltip() {
        var data = provider.get();
        var amount = Components.energyAmount(data.amount(), data.capacity());

        if (data.usage() == 0) {
            return List.of(amount);
        }

        return List.of(
            amount,
            Component.literal("Consumption: ").append(Components.energyUsage(-data.usage())).withStyle(ChatFormatting.GRAY)
        );
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, Font font, int mouseX, int mouseY) {
        guiGraphics.fill(RenderType.guiOverlay(), x + 1, y + 1, x + width - 1, y + height - 1, 0, -2130706433);

        super.renderTooltip(guiGraphics, font, mouseX, mouseY);
    }
}
