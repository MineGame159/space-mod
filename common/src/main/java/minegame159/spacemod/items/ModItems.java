package minegame159.spacemod.items;

import dev.architectury.core.item.ArchitecturyBucketItem;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import minegame159.spacemod.ModFluids;
import minegame159.spacemod.SpaceMod;
import minegame159.spacemod.blocks.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;

public final class ModItems {
    private ModItems() {}

    public static void init() {
        TABS_REGISTRY.register();
        REGISTRY.register();
    }

    // Tab

    private static final DeferredRegister<CreativeModeTab> TABS_REGISTRY = DeferredRegister.create(
        SpaceMod.ID,
        Registries.CREATIVE_MODE_TAB
    );

    public static final RegistrySupplier<CreativeModeTab> TAB = TABS_REGISTRY.register(
        "tab",
        () -> CreativeTabRegistry.create(
            SpaceMod.translatable("itemGroup", "tab"),
            () -> ModItems.ROCKET_SILO.get().getDefaultInstance()
        )
    );

    // Items

    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(
        SpaceMod.ID,
        Registries.ITEM
    );

    public static final RegistrySupplier<Item> CRUDE_OIL_BUCKET = REGISTRY.register(
        "crude_oil_bucket",
        () -> new ArchitecturyBucketItem(ModFluids.CRUDE_OIL, new Item.Properties().arch$tab(TAB))
    );

    public static final RegistrySupplier<Item> ROCKET_FUEL_BUCKET = REGISTRY.register(
        "rocket_fuel_bucket",
        () -> new ArchitecturyBucketItem(ModFluids.ROCKET_FUEL, new Item.Properties().arch$tab(TAB))
    );

    public static final RegistrySupplier<BlockItem> REFINERY = REGISTRY.register(
        "refinery",
        () -> new BlockItem(ModBlocks.REFINERY.get(), new Item.Properties().arch$tab(TAB))
    );

    public static final RegistrySupplier<BlockItem> OXYGEN_COLLECTOR = REGISTRY.register(
        "oxygen_collector",
        () -> new BlockItem(ModBlocks.OXYGEN_COLLECTOR.get(), new Item.Properties().arch$tab(TAB))
    );

    public static final RegistrySupplier<BlockItem> ROCKET_SILO = REGISTRY.register(
        "rocket_silo",
        () -> new BlockItem(ModBlocks.ROCKET_SILO.get(), new Item.Properties().arch$tab(TAB))
    );

    public static final RegistrySupplier<OxygenTankItem> OXYGEN_TANK = REGISTRY.register(
        "oxygen_tank",
        () -> new OxygenTankItem(new Item.Properties()
            .stacksTo(1)
            .component(ModDataComponents.OXYGEN_STORAGE.get(), new OxygenStorage(0, 1000))
            .arch$tab(TAB)
        )
    );

    public static final RegistrySupplier<StandingAndWallBlockItem> UNLIT_TORCH = REGISTRY.register(
        "unlit_torch",
        () -> new StandingAndWallBlockItem(
            ModBlocks.UNLIT_TORCH.get(),
            ModBlocks.UNLIT_WALL_TORCH.get(),
            new Item.Properties().arch$tab(TAB),
            Direction.DOWN
        )
    );
}
