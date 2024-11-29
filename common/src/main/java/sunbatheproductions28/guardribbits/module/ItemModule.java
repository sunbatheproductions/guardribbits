package sunbatheproductions28.guardribbits.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterItem;
import sunbatheproductions28.guardribbits.GuardRibbitsCommon;
import sunbatheproductions28.guardribbits.services.Services;

@AutoRegister(GuardRibbitsCommon.MOD_ID)
public class ItemModule {

    @AutoRegister("ribbit_guard_spawn_egg")
    public static final AutoRegisterItem RIBBIT_GUARD_SPAWN_EGG = AutoRegisterItem.of(Services.PLATFORM.getRibbitGuardSpawnEggItem());

}