package minegame159.spacemod.compat.emi;

import dev.emi.emi.api.EmiStackProvider;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.stack.EmiStackInteraction;
import minegame159.spacemod.client.screens.BaseScreen;
import minegame159.spacemod.client.screens.widgets.FluidTank;

public class BaseScreenProvider<T extends BaseScreen<?>> implements EmiStackProvider<T> {
    @Override
    public EmiStackInteraction getStackAt(T screen, int x, int y) {
        for (var widget : screen.widgets) {
            if (widget instanceof FluidTank fluidTank && x >= widget.x && x <= widget.x + widget.width && y >= widget.y && y <= widget.y + widget.height) {
                return new EmiStackInteraction(EmiStack.of(fluidTank.provider.get().fluid()));
            }
        }

        return EmiStackInteraction.EMPTY;
    }
}
