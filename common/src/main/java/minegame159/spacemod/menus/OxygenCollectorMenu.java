package minegame159.spacemod.menus;

import minegame159.spacemod.blocks.OxygenCollectorBlockEntity;
import minegame159.spacemod.items.ModDataComponents;
import minegame159.spacemod.menus.sync.EnergyMenuData;
import minegame159.spacemod.menus.sync.MenuData;
import minegame159.spacemod.menus.sync.MenuDataType;
import minegame159.spacemod.menus.sync.SyncedMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class OxygenCollectorMenu extends SyncedMenu<OxygenCollectorMenu.Data, OxygenCollectorBlockEntity> {
    public OxygenCollectorMenu(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
        super(id, ModMenuTypes.OXYGEN_COLLECTOR.get(), playerInventory, new SimpleContainer(1), buffer);
    }

    public OxygenCollectorMenu(int id, Inventory playerInventory, OxygenCollectorBlockEntity blockEntity, ContainerLevelAccess access) {
        super(id, ModMenuTypes.OXYGEN_COLLECTOR.get(), playerInventory, blockEntity.container, blockEntity, access);
    }

    @Override
    protected void init(Inventory playerInventory) {
        addSlot(new Slot(container, 0, 80, 33) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.has(ModDataComponents.OXYGEN_STORAGE.get());
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });

        super.init(playerInventory);
    }

    public record Data(EnergyMenuData energy) implements MenuData<Data> {
        public static final MenuDataType<Data> TYPE = MenuDataType.create(StreamCodec.composite(
            EnergyMenuData.CODEC, Data::energy,
            Data::new
        ));

        @Override
        public MenuDataType<Data> type() {
            return TYPE;
        }
    }
}
