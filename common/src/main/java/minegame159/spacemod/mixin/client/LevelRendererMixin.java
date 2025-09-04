package minegame159.spacemod.mixin.client;

import minegame159.spacemod.Space;
import minegame159.spacemod.client.SpaceSky;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    @Shadow
    private @Nullable ClientLevel level;

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    private void spacemod$renderSky(Matrix4f frustumMatrix, Matrix4f projectionMatrix, float partialTick, Camera camera, boolean isFoggy, Runnable skyFogSetup, CallbackInfo info) {
        if (level.dimension().location().equals(Space.KEY.location())) {
            SpaceSky.render(frustumMatrix, projectionMatrix);
            info.cancel();
        }
    }
}
