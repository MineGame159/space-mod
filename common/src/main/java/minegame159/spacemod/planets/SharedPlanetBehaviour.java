package minegame159.spacemod.planets;

import dev.architectury.event.events.common.TickEvent;
import minegame159.spacemod.SpaceMod;
import minegame159.spacemod.blocks.ModBlocks;
import minegame159.spacemod.blocks.UnlitTorchBlock;
import minegame159.spacemod.blocks.UnlitWallTorchBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BaseTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

public final class SharedPlanetBehaviour {
    private SharedPlanetBehaviour() {}

    public static final ResourceKey<DamageType> SUFFOCATION_DAMAGE = ResourceKey.create(
        Registries.DAMAGE_TYPE,
        SpaceMod.id("space_suffocation")
    );

    private static DamageSource suffocationDamage;

    public static void init() {
        TickEvent.SERVER_LEVEL_POST.register(SharedPlanetBehaviour::onLevelTickPost);
    }

    private static void onLevelTickPost(ServerLevel level) {
        if (level.isClientSide || Planets.hasOxygen(level)) return;

        if (suffocationDamage == null) {
            suffocationDamage = new DamageSource(level.registryAccess()
                .registry(Registries.DAMAGE_TYPE)
                .orElseThrow().getHolderOrThrow(SUFFOCATION_DAMAGE)
            );
        }

        for (var player : level.players()) {
            if (!player.isInvulnerable()) {
                player.hurt(suffocationDamage, 1);
            }
        }
    }

    public static BlockState getPlacementStateWithoutOxygen(BlockPlaceContext context, BlockState original) {
        var block = original.getBlock();

        if (block instanceof BaseTorchBlock && !(block instanceof UnlitTorchBlock)) {
            if (original.hasProperty(UnlitWallTorchBlock.FACING)) {
                var unlit = ModBlocks.UNLIT_WALL_TORCH.get().defaultBlockState();
                return unlit.setValue(UnlitWallTorchBlock.FACING, original.getValue(UnlitWallTorchBlock.FACING));
            }

            return ModBlocks.UNLIT_TORCH.get().defaultBlockState();
        }

        return original;
    }
}
