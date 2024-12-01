package sunbatheproductions28.guardribbits.services;

import sunbatheproductions28.guardribbits.module.NetworkModuleFabric;

public class FabricModulesLoader implements IModulesLoader {
    @Override
    public void loadCommonModules() {
        IModulesLoader.super.loadCommonModules(); // Load common modules
        NetworkModuleFabric.registerC2SPackets();
    }
}