package sunbatheproductions28.guardribbits.services;


import sunbatheproductions28.guardribbits.module.NetworkModuleForge;

public class ForgeModulesLoader implements IModulesLoader {
    @Override
    public void loadCommonModules() {
        IModulesLoader.super.loadCommonModules(); // Load common modules
        NetworkModuleForge.init();
    }
}
