package pers.zhangyang.easyteleportask.manager;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easyteleportask.domain.TeleportAsk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TeleportAskManager {
    public static  final TeleportAskManager INSTANCE=new TeleportAskManager();
    private final List<TeleportAsk> teleportAskList=new ArrayList<>();

    @NotNull
    public List<TeleportAsk> getTeleportAskList() {
        return teleportAskList;
    }
    @NotNull
    public List<TeleportAsk> getTeleportAskList(Player player) {
        List<TeleportAsk> teleportAskList=new ArrayList<>();
        for (TeleportAsk t:this.teleportAskList){
            if (t.getSender().equals(player)||t.getTarget().equals(player)){
                teleportAskList.add(t);
            }
        }
        return teleportAskList;
    }
    @Nullable
    public TeleportAsk getLatestPlayerSendTeleportAsk(Player player){
        TeleportAsk teleportAsk=null;
        for (TeleportAsk ask:this.teleportAskList){
            if (!ask.getSender().equals(player)){
                continue;
            }
            if (teleportAsk==null){
                teleportAsk=ask;
                continue;
            }
            if (ask.getTime()>=teleportAsk.getTime()){
                teleportAsk=ask;
            }
        }
        return teleportAsk;
    }

    public void remove(Player player){
        teleportAskList.removeIf(teleportAsk -> teleportAsk.getTarget().equals(player) || teleportAsk.getSender().equals(player));
    }

    public void addTeleportAsk(TeleportAsk ask){
        this.teleportAskList.add(ask);
    }
}
