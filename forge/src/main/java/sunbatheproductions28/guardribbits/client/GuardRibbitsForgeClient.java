package sunbatheproductions28.guardribbits.client;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import sunbatheproductions28.guardribbits.client.render.GuardRibbitRenderer;
import sunbatheproductions28.guardribbits.module.EntityTypeModule;

public class GuardRibbitsForgeClient {
    public static void init() {
        GuardRibbitsCommonClient.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(GuardRibbitsForgeClient::registerRenderers);
    }

    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeModule.GUARD_RIBBIT.get(), GuardRibbitRenderer::new);
    }

}
