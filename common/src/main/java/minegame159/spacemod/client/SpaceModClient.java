package minegame159.spacemod.client;

import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import minegame159.spacemod.ModFluids;
import minegame159.spacemod.client.screens.RefineryScreen;
import minegame159.spacemod.menus.ModMenuTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class SpaceModClient {
    public static void init() {
        RenderTypeRegistry.register(
            RenderType.translucent(),
            ModFluids.CRUDE_OIL_FLOWING.get(), ModFluids.CRUDE_OIL.get()
        );

        MenuRegistry.registerScreenFactory(ModMenuTypes.REFINERY.get(), RefineryScreen::new);

        MenuSyncClient.init();
    }
}
