package pers.zhangyang.easyteleportask.domain;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Gamer {
    private Player player;
    private List<Ask> askList=new ArrayList<>();

    public Gamer(Player player){
        this.player=player;
    }
}
