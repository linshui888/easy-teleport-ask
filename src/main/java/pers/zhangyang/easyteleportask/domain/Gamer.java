package pers.zhangyang.easyteleportask.domain;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Gamer {
    private Player player;

    public Gamer(Player player){
        this.player=player;
    }

    public Player getPlayer() {
        return player;
    }




}
