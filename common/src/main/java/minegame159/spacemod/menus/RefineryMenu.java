package minegame159.spacemod.menus;

import minegame159.spacemod.blocks.RefineryBlockEntity;
import minegame159.spacemod.menus.sync.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;

import java.util.Optional;

public class RefineryMenu extends SyncedMenu<RefineryMenu.Data, RefineryBlockEntity> {
    public RefineryMenu(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
        super(id, ModMenuTypes.REFINERY.get(), playerInventory, null, buffer);

        init(playerInventory);
    }

    public RefineryMenu(int id, Inventory playerInventory, RefineryBlockEntity blockEntity, ContainerLevelAccess access) {
        super(id, ModMenuTypes.REFINERY.get(), playerInventory, null, blockEntity, access);

        init(playerInventory);
    }

    public record Data(EnergyMenuData energy, FluidMenuData input, FluidMenuData output, Optional<Float> progress) implements MenuData<Data> {
        public static final MenuDataType<Data> TYPE = MenuDataType.create(StreamCodec.composite(
            EnergyMenuData.CODEC, Data::energy,
            FluidMenuData.CODEC, Data::input,
            FluidMenuData.CODEC, Data::output,
            ByteBufCodecs.optional(ByteBufCodecs.FLOAT), Data::progress,
            Data::new
        ));

        @Override
        public MenuDataType<Data> type() {
            return TYPE;
        }
    }
}
