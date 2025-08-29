package minegame159.spacemod.worldgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class NoiseThresholdFilter extends PlacementFilter {
    public static final MapCodec<NoiseThresholdFilter> CODEC = RecordCodecBuilder.mapCodec(
        instance -> instance.group(
            Codec.DOUBLE.fieldOf("factor").forGetter(placement -> placement.factor)
        ).apply(instance, NoiseThresholdFilter::new)
    );

    private final double factor;

    public NoiseThresholdFilter(double factor) {
        this.factor = factor;
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        @SuppressWarnings("removal")
        var v = Biome.BIOME_INFO_NOISE.getValue(pos.getX() / factor, pos.getZ() / factor, false);

        return v > 0;
    }

    @Override
    public PlacementModifierType<?> type() {
        return ModPlacements.NOISE_THRESHOLD_TYPE.get();
    }
}
