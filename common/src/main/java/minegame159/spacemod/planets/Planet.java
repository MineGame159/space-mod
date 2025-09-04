package minegame159.spacemod.planets;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public record Planet(ResourceKey<Level> dimension, ResourceLocation texture, boolean hasOxygen) {
    public static final Codec<Planet> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(Planet::dimension),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(Planet::texture),
            Codec.BOOL.fieldOf("has_oxygen").forGetter(Planet::hasOxygen)
        ).apply(instance, Planet::new)
    );
}
