package sunbatheproductions28.guardribbits.services;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import sunbatheproductions28.guardribbits.module.EntityTypeModule;

import java.util.function.Supplier;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Supplier<Item> getRibbitGuardSpawnEggItem() {
        return () -> new SpawnEggItem(EntityTypeModule.GUARD_RIBBIT.get(), 0xb3c35b, 0xCFAC55,
                new Item.Properties());
    }

}
