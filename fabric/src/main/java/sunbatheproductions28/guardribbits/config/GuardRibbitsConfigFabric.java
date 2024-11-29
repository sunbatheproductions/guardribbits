package sunbatheproductions28.guardribbits.config;

import com.yungnickyoung.minecraft.ribbits.RibbitsCommon;
import com.yungnickyoung.minecraft.ribbits.config.ConfigGeneralFabric;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name="ribbits-fabric-" + RibbitsCommon.MC_VERSION_STRING)
public class GuardRibbitsConfigFabric implements ConfigData {
    @ConfigEntry.Category("Guard Ribbits")
    @ConfigEntry.Gui.TransitiveObject
    public ConfigGeneralFabric general = new ConfigGeneralFabric();
}