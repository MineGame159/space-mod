package minegame159.spacemod.utils;

import com.google.common.collect.Iterators;
import minegame159.spacemod.api.ResourceSlot;
import minegame159.spacemod.api.ResourceView;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class SimpleResourceView<R> implements ResourceView<R> {
    private final ResourceSlot<R>[] slots;

    @SafeVarargs
    public SimpleResourceView(ResourceSlot<R>... slots) {
        this.slots = slots;
    }

    @Override
    public @NotNull Iterator<ResourceSlot<R>> slots() {
        return Iterators.forArray(slots);
    }
}
