package sunbatheproductions28.guardribbits.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import sunbatheproductions28.guardribbits.GuardRibbitsCommon;
import sunbatheproductions28.guardribbits.entity.GuardRibbitEntity;

public class GuardRibbitModel extends GeoModel<GuardRibbitEntity> {

    private static final ResourceLocation MODEL = new ResourceLocation(GuardRibbitsCommon.MOD_ID, "geo/guard_ribbit.geo.json");
    private static final ResourceLocation TEXTURE = new ResourceLocation(GuardRibbitsCommon.MOD_ID, "textures/entity/guard_ribbit.png");
    private static final ResourceLocation ANIMATION = new ResourceLocation(GuardRibbitsCommon.MOD_ID, "animations/guard_ribbit.animation.json");

    @Override
    public ResourceLocation getModelResource(GuardRibbitEntity guardRibbitEntity) {
        return MODEL;
        }

    @Override
    public ResourceLocation getTextureResource(GuardRibbitEntity guardRibbitEntity) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getAnimationResource(GuardRibbitEntity guardRibbitEntity) {
        return ANIMATION;
    }
}
