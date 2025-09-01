package minegame159.spacemod.compat.jade;

import minegame159.spacemod.SpaceMod;
import minegame159.spacemod.blocks.ProcessingMachineBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import snownee.jade.api.Accessor;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.view.*;

import java.util.List;

public enum ProcessingMachineBlockProgressProvider implements IServerExtensionProvider<CompoundTag>, IClientExtensionProvider<CompoundTag, ProgressView> {
    INSTANCE;

    public static final ResourceLocation ID = SpaceMod.id("processing_machine");

    @Override
    public List<ClientViewGroup<ProgressView>> getClientGroups(Accessor<?> accessor, List<ViewGroup<CompoundTag>> groups) {
        return ClientViewGroup.map(groups, ProgressView::read, (group, clientGroup) -> {
            var view = clientGroup.views.getFirst();
            view.style.color(FastColor.ARGB32.color(229, 229, 51));
        });
    }

    @Override
    public List<ViewGroup<CompoundTag>> getGroups(Accessor<?> accessor) {
        if (accessor instanceof BlockAccessor blockAccessor) {
            if (blockAccessor.getBlockEntity() instanceof ProcessingMachineBlockEntity<?, ?> blockEntity) {
                var progress = blockEntity.getProgress();

                if (progress.isPresent()) {
                    return List.of(new ViewGroup<>(List.of(
                        ProgressView.create(progress.get())
                    )));
                }
            }
        }

        return List.of();
    }

    @Override
    public boolean shouldRequestData(Accessor<?> accessor) {
        if (accessor instanceof BlockAccessor blockAccessor) {
            return blockAccessor.getBlockEntity() instanceof ProcessingMachineBlockEntity<?, ?>;
        }

        return false;
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }
}
