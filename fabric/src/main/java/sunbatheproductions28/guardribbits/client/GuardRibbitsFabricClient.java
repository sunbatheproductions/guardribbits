package sunbatheproductions28.guardribbits.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import sunbatheproductions28.guardribbits.client.render.GuardRibbitRenderer;
import sunbatheproductions28.guardribbits.module.EntityTypeModule;
import sunbatheproductions28.guardribbits.module.NetworkModuleFabric;

public class GuardRibbitsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        GuardRibbitsCommonClient.init();

        NetworkModuleFabric.registerS2CPackets();
        EntityRendererRegistry.register(EntityTypeModule.GUARD_RIBBIT.get(), GuardRibbitRenderer::new);
    }
}
