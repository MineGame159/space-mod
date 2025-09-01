package minegame159.spacemod.blocks;

import minegame159.spacemod.ModFluidTags;
import minegame159.spacemod.SpaceMod;
import minegame159.spacemod.api.ResourceInteraction;
import minegame159.spacemod.api.fluid.FluidApi;
import minegame159.spacemod.menus.RefineryMenu;
import minegame159.spacemod.menus.sync.SyncedMenuProvider;
import minegame159.spacemod.recipe.ModRecipeTypes;
import minegame159.spacemod.recipe.SimpleFluidRecipe;
import minegame159.spacemod.recipe.SimpleFluidRecipeInput;
import minegame159.spacemod.utils.FluidAmount;
import minegame159.spacemod.utils.SimpleFluidResourceSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RefineryBlockEntity extends ProcessingMachineBlockEntity<SimpleFluidRecipe, SimpleFluidRecipeInput> implements SyncedMenuProvider<RefineryMenu.Data> {
    public final SimpleFluidResourceSlot input = new SimpleFluidResourceSlot(
        FluidApi.BUCKET_AMOUNT * 8,
        ResourceInteraction.Mask.Both,
        ResourceInteraction.Mask.Manual,
        resource -> resource.is(ModFluidTags.CRUDE_OIL),
        this::setChanged
    );

    public final SimpleFluidResourceSlot output = new SimpleFluidResourceSlot(
        FluidApi.BUCKET_AMOUNT * 8,
        ResourceInteraction.Mask.None,
        ResourceInteraction.Mask.Both,
        resource -> true,
        this::setChanged
    );

    public RefineryBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.REFINERY.get(), pos, blockState, 20, ModRecipeTypes.REFINING);
    }

    @Override
    protected SimpleFluidRecipeInput getCurrentInput() {
        return new SimpleFluidRecipeInput(
            FluidAmount.of(input),
            output.getResource().getFluid()
        );
    }

    @Override
    protected void onRecipeDone(SimpleFluidRecipe recipe) {
        input.extractSkipMask(input.getResource(), recipe.input().amount());
        output.insertSkipMask(recipe.output().getFluidResource(level), recipe.output().amount());
    }

    // Menu

    @Override
    public RefineryMenu.Data getMenuData() {
        return new RefineryMenu.Data(
            energy.getMenuData(getCurrentEnergyUsage()),
            input.getMenuData(),
            output.getMenuData(),
            getProgress()
        );
    }

    @Override
    public Component getDisplayName() {
        return SpaceMod.translatable("block", "refinery");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new RefineryMenu(id, playerInventory, this, ContainerLevelAccess.create(level, getBlockPos()));
    }

    // Data

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        input.load(tag, registries, "input");
        output.load(tag, registries, "output");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        input.save(tag, registries, "input");
        output.save(tag, registries, "output");
    }
}
