package minegame159.spacemod.client;

import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import minegame159.spacemod.ModFluids;
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
    }
}
