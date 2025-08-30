package minegame159.spacemod.api;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public interface ResourceView<R> extends Iterable<ResourceSlot<R>> {
    @NotNull
    Iterator<ResourceSlot<R>> slots();

    @Override
    @NotNull
    default Iterator<ResourceSlot<R>> iterator() {
        return slots();
    }
}
