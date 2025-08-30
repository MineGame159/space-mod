package minegame159.spacemod.utils;

import minegame159.spacemod.api.ResourceInteraction;
import minegame159.spacemod.api.ResourceSlot;
import minegame159.spacemod.api.ResourceView;

public final class ResourceUtils {
    private ResourceUtils() {}

    public static <R> int insert(ResourceView<R> view, R resource, int maxAmount, ResourceInteraction interaction) {
        var amount = 0;

        for (var slot : view) {
            if (slot.canInsert(interaction)) {
                amount += slot.insert(resource, maxAmount - amount, interaction);
                if (amount >= maxAmount) break;
            }
        }

        return amount;
    }

    public static <R> int extract(ResourceView<R> view, R resource, int maxAmount, ResourceInteraction interaction) {
        var amount = 0;

        for (var slot : view) {
            if (slot.canExtract(interaction)) {
                amount += slot.extract(resource, maxAmount - amount, interaction);
                if (amount >= maxAmount) break;
            }
        }

        return amount;
    }

    public static <R> int transfer(ResourceSlot<R> src, ResourceSlot<R> dst, R resource, int maxAmount, ResourceInteraction interaction) {
        if (!src.canExtract(interaction)) return 0;
        if (!dst.canInsert(interaction)) return 0;

        var canExtract = src.extract(resource, maxAmount, ResourceInteraction.Simulation);
        var inserted = dst.insert(resource, canExtract, interaction);
        return src.extract(resource, inserted, interaction);
    }
}
