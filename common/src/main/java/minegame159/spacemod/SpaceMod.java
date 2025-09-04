package minegame159.spacemod;

import com.mojang.logging.LogUtils;
import dev.architectury.event.events.common.InteractionEvent;
import minegame159.spacemod.blocks.ModBlockEntities;
import minegame159.spacemod.blocks.ModBlocks;
import minegame159.spacemod.items.ModDataComponents;
import minegame159.spacemod.items.ModItems;
import minegame159.spacemod.menus.ModMenuTypes;
import minegame159.spacemod.planets.SharedPlanetBehaviour;
import minegame159.spacemod.recipe.ModRecipeSerializers;
import minegame159.spacemod.recipe.ModRecipeTypes;
import minegame159.spacemod.worldgen.feature.ModFeatures;
import minegame159.spacemod.worldgen.placement.ModPlacements;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public final class SpaceMod {
    public static final String ID = "spacemod";

    public static Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        ModFluids.init();
        ModBlocks.init();
        ModBlockEntities.init();

        ModDataComponents.init();
        ModItems.init();

        ModRecipeTypes.init();
        ModRecipeSerializers.init();

        ModPlacements.init();
        ModFeatures.init();

        ModMenuTypes.init();

        SharedPlanetBehaviour.init();
        Space.init();

        InteractionEvent.RIGHT_CLICK_BLOCK.register(BucketHandler::onRightClickBlock);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }

    public static MutableComponent translatable(String prefix, String suffix, Object... args) {
        return Component.translatable(prefix + "." + ID + "." + suffix, args);
    }
}
