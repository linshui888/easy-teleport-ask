package pers.zhangyang.easyteleportask;

import org.bstats.bukkit.Metrics;
import pers.zhangyang.easylibrary.EasyPlugin;

public class EasyTeleportAsk extends EasyPlugin {
    @Override
    public void onOpen() {

        // bStats统计信息
        new Metrics(EasyTeleportAsk.instance,16083);
    }

    @Override
    public void onClose() {

    }
}
