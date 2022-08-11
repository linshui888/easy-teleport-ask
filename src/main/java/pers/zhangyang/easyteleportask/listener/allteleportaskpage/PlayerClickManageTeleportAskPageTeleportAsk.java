package pers.zhangyang.easyteleportask.listener.allteleportaskpage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easylibrary.annotation.GuiSerialButtonHandler;
import pers.zhangyang.easyteleportask.domain.ManageTeleportAskPage;
import pers.zhangyang.easyteleportask.domain.ManageTeleportAskPageTeleportAskOptionPage;
import pers.zhangyang.easyteleportask.domain.TeleportAsk;

@EventListener
public class PlayerClickManageTeleportAskPageTeleportAsk implements Listener {
    @GuiSerialButtonHandler(guiPage = ManageTeleportAskPage.class,from = 0,to = 44)
    public void on(InventoryClickEvent event){

        int slot=event.getRawSlot();
        Player player= (Player) event.getWhoClicked();

        ManageTeleportAskPage manageTeleportAskPage= (ManageTeleportAskPage) event.getInventory().getHolder();

        assert manageTeleportAskPage != null;
        TeleportAsk teleportAsk=manageTeleportAskPage.getTeleportAskList().get(slot);
        new ManageTeleportAskPageTeleportAskOptionPage(teleportAsk,player,manageTeleportAskPage,manageTeleportAskPage.getOwner()).send();


    }
}
