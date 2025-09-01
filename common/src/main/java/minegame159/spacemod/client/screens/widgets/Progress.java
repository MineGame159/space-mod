package minegame159.spacemod.client.screens.widgets;

import minegame159.spacemod.utils.Components;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class Progress extends BaseWidget {
    public static final int WIDTH = Sprites.ARROW_FULL.width();
    public static final int HEIGHT = Sprites.ARROW_FULL.height();

    public final Supplier<Optional<Float>> provider;

    public Progress(int x, int y, Supplier<Optional<Float>> provider) {
        super(x, y, WIDTH, HEIGHT);

        this.provider = provider;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        var progress = provider.get().orElse(0f);

        var uvWidth = (int) (progress * width);

        if (uvWidth > 0) {
            guiGraphics.blitSprite(
                Sprites.ARROW_FULL.id(),
                22, 16,
                0, 0,
                x, y,
                uvWidth, 16
            );
        }
    }

    @Override
    public List<Component> getTooltip() {
        var data = provider.get();
        if (data.isEmpty()) return List.of();

        return List.of(Components.progress(data.orElseThrow()));
    }
}
