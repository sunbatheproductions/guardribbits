package sunbatheproductions28.guardribbits.services;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import sunbatheproductions28.guardribbits.module.EntityTypeModule;

import java.util.function.Supplier;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    @Override
    public Supplier<Item> getRibbitGuardSpawnEggItem() {
        return () -> new ForgeSpawnEggItem(() -> EntityTypeModule.GUARD_RIBBIT.get(), 0xBA852F, 0xCFAC55,
                new Item.Properties());
    }

}