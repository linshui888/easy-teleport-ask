package pers.zhangyang.easytemplate;

import org.bstats.bukkit.Metrics;
import pers.zhangyang.easylibrary.EasyPlugin;

public class EasyTemplate extends EasyPlugin {
    @Override
    public void onOpen() {

        // bStats统计信息
        new Metrics(EasyTemplate.instance,8888);
    }

    @Override
    public void onClose() {

    }
}
