package minegame159.spacemod;

import dev.architectury.event.events.common.TickEvent;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class Space {
    private Space() {}

    public static final ResourceKey<Level> KEY = ResourceKey.create(
        Registries.DIMENSION,
        ResourceLocation.fromNamespaceAndPath(SpaceMod.ID, "space")
    );

    public static final ResourceKey<DamageType> SUFFOCATION_DAMAGE = ResourceKey.create(
        Registries.DAMAGE_TYPE,
        ResourceLocation.fromNamespaceAndPath(SpaceMod.ID, "space_suffocation")
    );

    private static DamageSource suffocationDamage;

    public static void init() {
        TickEvent.SERVER_LEVEL_POST.register(Space::onLevelTickPost);
    }

    private static void onLevelTickPost(ServerLevel level) {
        if (!level.isClientSide && level.dimension().location().equals(KEY.location())) {
            List<Player> playersToTeleport = null;

            if (suffocationDamage == null) {
                suffocationDamage = new DamageSource(level.registryAccess()
                    .registry(Registries.DAMAGE_TYPE)
                    .orElseThrow().getHolderOrThrow(SUFFOCATION_DAMAGE)
                );
            }

            for (var player : level.players()) {
                if (!player.isInvulnerable())
                    player.hurt(suffocationDamage, 1);

                if (player.getY() < 0) {
                    if (playersToTeleport == null) {
                        playersToTeleport = new ArrayList<>();
                    }

                    playersToTeleport.add(player);
                }
            }

            if (playersToTeleport != null) {
                var overworld = level.getServer().overworld();

                for (var player : playersToTeleport) {
                    player.teleportTo(
                        overworld,
                        player.getX(), overworld.getHeight(), player.getZ(),
                        Set.of(),
                        player.getYRot(), player.getXRot()
                    );
                }
            }
        }
    }
}
