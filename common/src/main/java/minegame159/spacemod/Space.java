package minegame159.spacemod;

import dev.architectury.event.events.common.TickEvent;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class Space {
    private Space() {}

    public static final ResourceKey<Level> KEY = ResourceKey.create(
        Registries.DIMENSION,
        SpaceMod.id("space")
    );

    public static void init() {
        TickEvent.SERVER_LEVEL_POST.register(Space::onLevelTickPost);
    }

    private static void onLevelTickPost(ServerLevel level) {
        if (level.isClientSide || !level.dimension().location().equals(KEY.location())) return;

        List<Player> playersToTeleport = null;

        for (var player : level.players()) {
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
