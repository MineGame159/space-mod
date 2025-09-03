package minegame159.spacemod.menus;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import minegame159.spacemod.SpaceMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

public final class ModMenuTypes {
    private ModMenuTypes() {}

    public static void init() {
        REGISTRY.register();
    }

    // Menu types

    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(
        SpaceMod.ID,
        Registries.MENU
    );

    public static final DeferredSupplier<MenuType<RefineryMenu>> REFINERY = REGISTRY.register(
        "refinery",
        () -> MenuRegistry.ofExtended(RefineryMenu::new)
    );

    public static final DeferredSupplier<MenuType<OxygenCollectorMenu>> OXYGEN_COLLECTOR = REGISTRY.register(
        "oxygen_collector",
        () -> MenuRegistry.ofExtended(OxygenCollectorMenu::new)
    );
}
