package sunbatheproductions28.guardribbits.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import sunbatheproductions28.guardribbits.client.model.GuardRibbitModel;
import sunbatheproductions28.guardribbits.entity.GuardRibbitEntity;

import javax.annotation.Nullable;

public class GuardRibbitRenderer extends GeoEntityRenderer<GuardRibbitEntity> {

    public GuardRibbitRenderer(EntityRendererProvider.Context context) {
        super(context, new GuardRibbitModel());
    }

    @Override
    public void renderRecursively(PoseStack poseStack, GuardRibbitEntity animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public RenderType getRenderType(GuardRibbitEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(texture);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull GuardRibbitEntity entity) {
        return super.getTextureLocation(entity);
    }

    @Override
    public float getMotionAnimThreshold(GuardRibbitEntity animatable) {
        return 0.0005f;
    }
}
