package minegame159.spacemod.worldgen.feature;

import minegame159.spacemod.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluids;

public class OilFieldFeature extends Feature<OilFieldConfiguration> {
    public OilFieldFeature() {
        super(OilFieldConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<OilFieldConfiguration> context) {
        var origin = context.origin();
        var level = context.level();

        var spoutHeight = context.config().spoutHeight().sample(context.random());
        var spoutDepth = context.config().spoutDepth().sample(context.random());
        var fieldXZRadius = context.config().fieldXZRadius().sample(context.random());
        var fieldYRadius = context.config().fieldYRadius().sample(context.random());

        var waterDepth = getWaterDepth(level, origin.below());

        placeSpout(level, origin, spoutHeight);

        if (waterDepth > 0) {
            placeSpout(level, origin.below(waterDepth), waterDepth);
        }

        placeSpout(level, origin.below(waterDepth + spoutDepth), spoutDepth);
        placeField(level, origin.below(waterDepth + spoutDepth + fieldYRadius - 1), fieldXZRadius, fieldYRadius);

        return true;
    }

    private int getWaterDepth(WorldGenLevel level, BlockPos origin) {
        var depth = 0;
        var pos = origin.mutable();

        while (level.getFluidState(pos).is(Fluids.WATER)) {
            depth++;
            pos.setY(pos.getY() - 1);
        }

        return depth;
    }

    private void placeSpout(WorldGenLevel level, BlockPos origin, int height) {
        var crudeOil = ModBlocks.CRUDE_OIL.get().defaultBlockState();
        var air = Blocks.AIR.defaultBlockState();

        var pos = origin.mutable();
        placeColumn(level, pos, height, crudeOil, true);

        pos.setWithOffset(origin, 1, 0, 0);
        placeColumn(level, pos, height, air, false);

        pos.setWithOffset(origin, -1, 0, 0);
        placeColumn(level, pos, height, air, false);

        pos.setWithOffset(origin, 0, 0, 1);
        placeColumn(level, pos, height, air, false);

        pos.setWithOffset(origin, 0, 0, -1);
        placeColumn(level, pos, height, air, false);
    }

    private void placeColumn(WorldGenLevel level, BlockPos.MutableBlockPos pos, int height, BlockState state, boolean tick) {
        for (int i = 0; i < height; i++) {
            setBlock(level, pos, state);
            if (tick) level.scheduleTick(pos, state.getFluidState().getType(), 0);

            pos.setY(pos.getY() + 1);
        }
    }

    private void placeField(WorldGenLevel level, BlockPos origin, int xzRadius, int yRadius) {
        var crudeOil = ModBlocks.CRUDE_OIL.get().defaultBlockState();
        var pos = origin.mutable();

        for (int x = -xzRadius; x <= xzRadius; x++) {
            for (int z = -xzRadius; z <= xzRadius; z++) {
                for (int y = -yRadius; y <= yRadius; y++) {
                    var dx = (float) x / xzRadius;
                    var dy = (float) y / yRadius;
                    var dz = (float) z / xzRadius;

                    if (dx * dx + dy * dy + dz * dz > 1) {
                        continue;
                    }

                    pos.setWithOffset(origin, x, y, z);
                    setBlock(level, pos, crudeOil);
                }
            }
        }
    }
}
