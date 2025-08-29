package minegame159.spacemod.worldgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record OilFieldConfiguration(IntProvider spoutHeight, IntProvider spoutDepth, IntProvider fieldXZRadius, IntProvider fieldYRadius) implements FeatureConfiguration {
    public static final Codec<OilFieldConfiguration> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            IntProvider.POSITIVE_CODEC.fieldOf("spout_height").forGetter(OilFieldConfiguration::spoutHeight),
            IntProvider.POSITIVE_CODEC.fieldOf("spout_depth").forGetter(OilFieldConfiguration::spoutDepth),
            IntProvider.POSITIVE_CODEC.fieldOf("field_xz_radius").forGetter(OilFieldConfiguration::fieldXZRadius),
            IntProvider.POSITIVE_CODEC.fieldOf("field_y_radius").forGetter(OilFieldConfiguration::fieldYRadius)
        ).apply(instance, OilFieldConfiguration::new)
    );
}
