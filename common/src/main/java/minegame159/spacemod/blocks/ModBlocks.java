package minegame159.spacemod.blocks;

import dev.architectury.core.block.ArchitecturyLiquidBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import minegame159.spacemod.ModFluids;
import minegame159.spacemod.SpaceMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public final class ModBlocks {
    private ModBlocks() {}

    public static void init() {
        REGISTRY.register();
    }

    // Blocks

    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(
        SpaceMod.ID,
        Registries.BLOCK
    );

    public static final RegistrySupplier<LiquidBlock> CRUDE_OIL = REGISTRY.register(
        "crude_oil",
        () -> new ArchitecturyLiquidBlock(ModFluids.CRUDE_OIL_FLOWING, BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BLACK)
            .replaceable()
            .noCollission()
            .strength(100.0F)
            .pushReaction(PushReaction.DESTROY)
            .noLootTable()
            .liquid()
            .sound(SoundType.EMPTY)
        )
    );

    public static final RegistrySupplier<LiquidBlock> ROCKET_FUEL = REGISTRY.register(
        "rocket_fuel",
        () -> new ArchitecturyLiquidBlock(ModFluids.ROCKET_FUEL_FLOWING, BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_YELLOW)
            .replaceable()
            .noCollission()
            .strength(100.0F)
            .pushReaction(PushReaction.DESTROY)
            .noLootTable()
            .liquid()
            .sound(SoundType.EMPTY)
        )
    );

    public static final RegistrySupplier<RefineryBlock> REFINERY = REGISTRY.register(
        "refinery",
        () -> new RefineryBlock(BlockBehaviour.Properties.of()
            .mapColor(DyeColor.GRAY)
            .strength(3.5f)
            .requiresCorrectToolForDrops()
        )
    );

    public static final RegistrySupplier<RocketSiloBlock> ROCKET_SILO = REGISTRY.register(
        "rocket_silo",
        () -> new RocketSiloBlock(BlockBehaviour.Properties.of())
    );
}
