package sunbatheproductions28.guardribbits;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import software.bernie.geckolib.GeckoLib;
import sunbatheproductions28.guardribbits.client.render.GuardRibbitRenderer;
import sunbatheproductions28.guardribbits.module.EntityTypeModule;

public class GuardRibbitsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        GeckoLib.initialize();
        GuardRibbitsCommon.init();

        EntityRendererRegistry.register(EntityTypeModule.GUARD_RIBBIT.get(), GuardRibbitRenderer::new);
    }

}
