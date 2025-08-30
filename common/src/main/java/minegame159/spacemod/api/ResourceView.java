package minegame159.spacemod.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public interface ResourceView<R> extends Iterable<ResourceSlot<R>> {
    @NotNull
    Iterator<ResourceSlot<R>> slots();

    @Nullable
    ResourceSlot<R> mainOutput();

    @Override
    @NotNull
    default Iterator<ResourceSlot<R>> iterator() {
        return slots();
    }
}
