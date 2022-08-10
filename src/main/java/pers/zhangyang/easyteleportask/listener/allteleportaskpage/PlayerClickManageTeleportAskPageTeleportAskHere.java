package pers.zhangyang.easyteleportask.listener.allteleportaskpage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.yaml.MessageYaml;
import pers.zhangyang.easyteleportask.domain.ManageTeleportAskPage;

import java.util.List;

@EventListener
public class PlayerClickManageTeleportAskPageTeleportAskHere implements Listener {
    @GuiDiscreteButtonHandler(guiPage = ManageTeleportAskPage.class,slot = {50})
    public void on(InventoryClickEvent event){

        Player player= (Player) event.getWhoClicked();

        ManageTeleportAskPage manageTeleportAskPage= (ManageTeleportAskPage) event.getInventory().getHolder();

        Player onlineOwner=manageTeleportAskPage.getOwner().getPlayer();
        if (onlineOwner==null){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notOnline");
            MessageUtil.sendMessageTo(player, list);
            return;
        }

        new PlayerInputAfterClickManageTeleportAskPageTeleportAskHere(player,onlineOwner,manageTeleportAskPage);


    }
}
