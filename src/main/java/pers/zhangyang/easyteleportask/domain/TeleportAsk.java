package pers.zhangyang.easyteleportask.domain;

import org.bukkit.entity.Player;
import pers.zhangyang.easyteleportask.enumeration.AskTypeEnum;

public class TeleportAsk {
    private Player sender;
    private Player target;
    private AskTypeEnum askType;
    private long time;

    public TeleportAsk(Player sender, Player target, AskTypeEnum askType, long time) {
        this.sender = sender;
        this.target = target;
        this.askType = askType;
        this.time = time;
    }


    public long getTime() {
        return time;
    }

    public Player getSender() {
        return sender;
    }

    public Player getTarget() {
        return target;
    }

    public AskTypeEnum getAskType() {
        return askType;
    }
}
