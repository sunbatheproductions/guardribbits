package sunbatheproductions28.guardribbits;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import sunbatheproductions28.guardribbits.client.GuardRibbitsForgeClient;
import sunbatheproductions28.guardribbits.client.render.GuardRibbitRenderer;

@Mod(GuardRibbitsCommon.MOD_ID)
public class GuardRibbitsForge {
    
    public GuardRibbitsForge() {
        GuardRibbitsCommon.init();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> GuardRibbitsForgeClient::init);
    }

}