package pers.zhangyang.easyteleportask.yaml;

import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easylibrary.base.YamlBase;

public class SettingYaml extends YamlBase {
    public static final SettingYaml INSTANCE = new SettingYaml();

    private SettingYaml() {
        super("setting.yml");
    }

    @NotNull
    public String getDisplay() {
        String display = getStringDefault("setting.display");
        if(pers.zhangyang.easylibrary.yaml.SettingYaml.class.getClassLoader().getResource("display/"+display)==null){
            display = backUpConfiguration.getString("setting.display");
        }
        assert display != null;
        return display;
    }

    public double teleportAskCost() {
        double display = getDoubleDefault("setting.teleportAskCost");
        if (display<0){
            display=0;
        }
        return display;
    }

    public int teleportAskDelay() {
        int display = getIntegerDefault("setting.teleportAskDelay");
        if (display<0){
            display=3;
        }
        return display;
    }
}
