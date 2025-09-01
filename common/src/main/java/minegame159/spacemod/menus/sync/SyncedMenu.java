package minegame159.spacemod.menus.sync;

import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.menu.MenuRegistry;
import minegame159.spacemod.blocks.MachineBlockEntity;
import minegame159.spacemod.blocks.ModBlocks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

public abstract class SyncedMenu<D extends MenuData<D>, B extends MachineBlockEntity & SyncedMenuProvider<D>> extends AbstractContainerMenu {
    private final ServerPlayer player;

    private final B blockEntity;
    private final Runnable changeListener;

    private final ContainerLevelAccess access;

    public D data;

    @SuppressWarnings("unchecked")
    protected SyncedMenu(int id, MenuType<?> menuType, Inventory playerInventory, FriendlyByteBuf buffer) {
        super(menuType, id);

        this.player = null;

        this.blockEntity = null;
        this.changeListener = null;

        this.access = null;

        init(playerInventory);

        // Read initial data
        var dataType = MenuDataType.getById(buffer.readVarInt());
        data = (D) dataType.codec().decode(new RegistryFriendlyByteBuf(buffer, playerInventory.player.registryAccess()));
    }

    protected SyncedMenu(int id, MenuType<?> menuType, Inventory playerInventory, B blockEntity, ContainerLevelAccess access) {
        super(menuType, id);

        this.player = (ServerPlayer) playerInventory.player;

        this.blockEntity = blockEntity;
        this.changeListener = blockEntity.addChangeListener(this::onBlockEntityChanged);

        this.access = access;

        init(playerInventory);
    }

    private void init(Inventory playerInventory) {
        // Player inventory
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        // Player hotbar
        for (int x = 0; x < 9; ++x) {
            this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 142));
        }
    }

    public static <D extends MenuData<D>> void open(ServerPlayer player, SyncedMenuProvider<D> provider) {
        MenuRegistry.openExtendedMenu(player, provider, buffer -> {
            var data = provider.getMenuData();

            buffer.writeVarInt(data.type().id());
            data.type().codec().encode(new RegistryFriendlyByteBuf(buffer, player.registryAccess()), data);
        });
    }

    @SuppressWarnings("unchecked")
    public void onReceivePacket(MenuDataS2CPacket<?> packet) {
        if (packet.containerId() == containerId) {
            data = (D) packet.data();
        }
    }

    private void onBlockEntityChanged() {
        var data = blockEntity.getMenuData();

        NetworkManager.sendToPlayer(player, new MenuDataS2CPacket<>(
            containerId,
            data.type(),
            data
        ));
    }

    @Override
    public void removed(Player player) {
        super.removed(player);

        if (blockEntity != null) {
            blockEntity.removeChangeListener(changeListener);
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, ModBlocks.REFINERY.get());
    }
}
