package sunbatheproductions28.guardribbits.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterEntityType;
import net.minecraft.world.entity.MobCategory;
import sunbatheproductions28.guardribbits.GuardRibbitsCommon;
import sunbatheproductions28.guardribbits.entity.GuardRibbitEntity;

@AutoRegister(GuardRibbitsCommon.MOD_ID)
public class EntityTypeModule {
    @AutoRegister("guard_ribbit")
    public static final AutoRegisterEntityType<GuardRibbitEntity> GUARD_RIBBIT = AutoRegisterEntityType.of(() ->
                    AutoRegisterEntityType.Builder
                            .of(GuardRibbitEntity::new, MobCategory.CREATURE)
                            .sized(0.5f, 0.75f)
                            .build())
            .attributes(GuardRibbitEntity::createRibbitAttributes);

}