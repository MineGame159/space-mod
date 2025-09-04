package minegame159.spacemod.blocks;

import minegame159.spacemod.SpaceMod;
import minegame159.spacemod.items.ModDataComponents;
import minegame159.spacemod.items.OxygenStorage;
import minegame159.spacemod.menus.OxygenCollectorMenu;
import minegame159.spacemod.menus.sync.SyncedMenuProvider;
import minegame159.spacemod.planets.Planets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class OxygenCollectorBlockEntity extends MachineBlockEntity implements SyncedMenuProvider<OxygenCollectorMenu.Data> {
    public final SimpleContainer container = new SimpleContainer(1);

    public OxygenCollectorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.OXYGEN_COLLECTOR.get(), pos, blockState, 40);
    }

    @Override
    protected boolean tickWork() {
        if (!Planets.hasOxygen(level.dimension())) return false;

        var stack = container.getItem(0);
        var storage = stack.get(ModDataComponents.OXYGEN_STORAGE.get());

        if (storage != null && storage.free() > 0) {
            var amount = Math.min(storage.amount() + 10, storage.capacity());
            stack.set(ModDataComponents.OXYGEN_STORAGE.get(), new OxygenStorage(amount, storage.capacity()));

            return true;
        }

        return false;
    }

    // Menu

    @Override
    public OxygenCollectorMenu.Data getMenuData() {
        return new OxygenCollectorMenu.Data(
            getEnergyMenuData()
        );
    }

    @Override
    public Component getDisplayName() {
        return SpaceMod.translatable("block", "oxygen_collector");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new OxygenCollectorMenu(id, playerInventory, this, ContainerLevelAccess.create(level, getBlockPos()));
    }

    // Data

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        ContainerHelper.loadAllItems(tag, container.getItems(), registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        ContainerHelper.saveAllItems(tag, container.getItems(), registries);
    }
}
