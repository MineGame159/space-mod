package minegame159.spacemod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import dev.architectury.utils.GameInstance;
import minegame159.spacemod.SpaceMod;
import minegame159.spacemod.mixin.client.LevelRendererAccessor;
import minegame159.spacemod.planets.Planets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

@Environment(EnvType.CLIENT)
public final class SpaceSky {
    private SpaceSky() {}

    private static final ResourceLocation CLOUDS = SpaceMod.id("textures/planet/clouds.png");

    public static void render(Matrix4f frustumMatrix, Matrix4f projectionMatrix) {
        var poseStack = new PoseStack();

        poseStack.mulPose(frustumMatrix);
        poseStack.mulPose(Axis.YP.rotationDegrees(-90));

        FogRenderer.setupNoFog();
        RenderSystem.depthMask(false);

        renderStars(projectionMatrix, poseStack);
        renderPlanet(poseStack);

        RenderSystem.depthMask(true);
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    private static void renderStars(Matrix4f projectionMatrix, PoseStack poseStack) {
        var time = 16 * 60 * 1000;
        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(System.currentTimeMillis() % time / (float) time * 360));

        RenderSystem.setShaderColor(0.75f, 0.75f, 0.75f, 0.75f);
        FogRenderer.setupNoFog();

        var starBuffer = ((LevelRendererAccessor) GameInstance.getClient().levelRenderer).getStarBuffer();
        starBuffer.bind();
        starBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, GameRenderer.getPositionShader());

        VertexBuffer.unbind();

        poseStack.popPose();
    }

    private static void renderPlanet(PoseStack poseStack) {
        var planet = Planets.getOrThrow(SpaceMod.id("overworld"));

        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(165));
        poseStack.mulPose(Axis.ZP.rotationDegrees(15));

        var size = 40;

        // Terrain
        RenderSystem.setShaderColor(0.75f, 0.75f, 0.75f, 1);
        renderPlanetTexture(planet.texture().withPath("textures/" + planet.texture().getPath() + ".png"), poseStack, size, 0, 0);

        // Clouds
        if (planet.hasOxygen()) {
            var time = System.currentTimeMillis();

            RenderSystem.setShaderColor(0.75f, 0.75f, 0.75f, 0.5f);
            renderPlanetTexture(CLOUDS, poseStack, size + 1, time % 540000 / 54000f, time % 300000 / 30000f);
        }
    }

    private static void renderPlanetTexture(ResourceLocation texture, PoseStack poseStack, int size, float uOffset, float vOffset) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);

        var builder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        var pose = poseStack.last().pose();

        builder.addVertex(pose, -size, 100, -size).setUv(uOffset + 0, vOffset + 0);
        builder.addVertex(pose, size, 100, -size).setUv(uOffset + 1, vOffset + 0);
        builder.addVertex(pose, size, 100, size).setUv(uOffset + 1, vOffset + 1);
        builder.addVertex(pose, -size, 100, size).setUv(uOffset + 0, vOffset + 1);

        BufferUploader.drawWithShader(builder.buildOrThrow());
    }
}
