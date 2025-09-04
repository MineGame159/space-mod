package minegame159.spacemod.planets;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.serialization.JsonOps;
import minegame159.spacemod.Space;
import minegame159.spacemod.SpaceMod;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class Planets {
    private Planets() {}

    private static Map<ResourceLocation, Planet> PLANETS = new HashMap<>();

    @Nullable
    public static Planet get(ResourceLocation id) {
        return PLANETS.get(id);
    }

    public static Planet getOrThrow(ResourceLocation id) {
        var planet = PLANETS.get(id);
        if (planet == null) throw new IllegalArgumentException("Planet with the id '" + id + "' doesn't exist");

        return planet;
    }

    @Nullable
    public static Planet getForDimension(ResourceKey<Level> key) {
        for (var planet : PLANETS.values()) {
            if (planet.dimension().location().equals(key.location())) {
                return planet;
            }
        }

        return null;
    }

    public static boolean hasOxygen(Level level) {
        return hasOxygen(level.dimension());
    }

    public static boolean hasOxygen(ResourceKey<Level> key) {
        if (key.location().equals(Space.KEY.location())) {
            return false;
        }

        var planet = getForDimension(key);
        return planet == null || planet.hasOxygen();
    }

    public static class Loader extends SimplePreparableReloadListener<Map<ResourceLocation, Planet>> {
        private static final String FOLDER = "spacemod_planet";

        @Override
        protected Map<ResourceLocation, Planet> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
            var planets = new HashMap<ResourceLocation, Planet>();
            var resources = resourceManager.listResources(FOLDER, path -> path.getPath().endsWith(".json"));

            for (var path : resources.keySet()) {
                JsonElement element;

                try {
                    var reader = resources.get(path).openAsReader();
                    element = JsonParser.parseReader(reader);
                    reader.close();
                } catch (IOException e) {
                    SpaceMod.LOGGER.error("Failed to parse planet '{}'", path, e);
                    continue;
                }

                Planet.CODEC.parse(JsonOps.INSTANCE, element)
                    .resultOrPartial(s -> SpaceMod.LOGGER.error("Failed to decode planet '{}': {}", path, s))
                    .ifPresent(planet -> {
                        var id = path.withPath(path.getPath().substring(FOLDER.length() + 1, path.getPath().length() - 5));
                        planets.put(id, planet);
                    });
            }

            return planets;
        }

        @Override
        protected void apply(Map<ResourceLocation, Planet> planets, ResourceManager resourceManager, ProfilerFiller profiler) {
            PLANETS = planets;
        }
    }
}
