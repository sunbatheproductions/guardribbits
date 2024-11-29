package sunbatheproductions28.guardribbits;

import com.yungnickyoung.minecraft.ribbits.module.ConfigModule;
import com.yungnickyoung.minecraft.yungsapi.api.YungAutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterCreativeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sunbatheproductions28.guardribbits.module.ItemModule;
import sunbatheproductions28.guardribbits.services.Services;

@AutoRegister(GuardRibbitsCommon.MOD_ID)
public class GuardRibbitsCommon {
    public static final String MOD_ID = "guardribbits";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final ConfigModule CONFIG = new ConfigModule();

    // TODO - change this whenever updating to a new Minecraft version
    public static final String MC_VERSION_STRING = "1_20_1";

    @AutoRegister("general")
    public static AutoRegisterCreativeTab TAB = AutoRegisterCreativeTab.builder()
            .title(Component.translatable("itemGroup.guardribbits.general"))
            .iconItem(() -> new ItemStack(ItemModule.RIBBIT_GUARD_SPAWN_EGG.get()))
            .entries((params, output) -> {
                output.accept(ItemModule.RIBBIT_GUARD_SPAWN_EGG.get());
            })
            .build();

    public static void init() {
        YungAutoRegister.scanPackageForAnnotations("sunbatheproductions28.guardribbits");
        Services.MODULES.loadCommonModules();
        LOGGER.info("Initializing the Ribbit Guardians!");
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}
