package minegame159.spacemod.client.screens.widgets;

import minegame159.spacemod.SpaceMod;
import net.minecraft.resources.ResourceLocation;

public final class Sprites {
    private Sprites() {}

    public static final Sprite ENERGY_BACKGROUND = create("energy_background", 6, 52);
    public static final Sprite ENERGY_FULL = create("energy_full", 4, 50);

    public static final Sprite ARROW_FULL = create("arrow_full", 22, 16);

    public static final Sprite FLUID_TANK_BACKGROUND = create("fluid_tank_background", 18, 52);
    public static final Sprite FLUID_TANK_OVERLAY = create("tank_overlay", 16, 50);

    private static Sprite create(String name, int width, int height) {
        return new Sprite(SpaceMod.id(name), width, height);
    }

    public record Sprite(ResourceLocation id, int width, int height) {}
}
