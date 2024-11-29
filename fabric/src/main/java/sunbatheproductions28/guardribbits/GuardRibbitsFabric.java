package sunbatheproductions28.guardribbits;

import net.fabricmc.api.ModInitializer;
import software.bernie.geckolib.GeckoLib;

public class GuardRibbitsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        GeckoLib.initialize();
        GuardRibbitsCommon.init();
    }

}
