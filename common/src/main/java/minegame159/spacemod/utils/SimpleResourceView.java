package minegame159.spacemod.utils;

import com.google.common.collect.Iterators;
import minegame159.spacemod.api.ResourceSlot;
import minegame159.spacemod.api.ResourceView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class SimpleResourceView<R> implements ResourceView<R> {
    private final ResourceSlot<R>[] slots;
    private final boolean hasMainOutput;

    private SimpleResourceView(ResourceSlot<R>[] slots, boolean hasMainOutput) {
        this.slots = slots;
        this.hasMainOutput = hasMainOutput;
    }

    @SafeVarargs
    public static <R> SimpleResourceView<R> with(ResourceSlot<R>... slots) {
        return new SimpleResourceView<>(slots, false);
    }

    @SafeVarargs
    public static <R> SimpleResourceView<R> withMainOutput(ResourceSlot<R>... slots) {
        return new SimpleResourceView<>(slots, true);
    }

    @Override
    public @NotNull Iterator<ResourceSlot<R>> slots() {
        return Iterators.forArray(slots);
    }

    @Override
    public @Nullable ResourceSlot<R> mainOutput() {
        if (hasMainOutput && slots.length > 0) {
            return slots[slots.length - 1];
        }

        return null;
    }
}
