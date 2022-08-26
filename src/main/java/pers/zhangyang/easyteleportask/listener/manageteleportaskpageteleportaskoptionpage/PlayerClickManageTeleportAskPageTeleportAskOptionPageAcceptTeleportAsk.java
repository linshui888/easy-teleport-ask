package pers.zhangyang.easyteleportask.listener.manageteleportaskpageteleportaskoptionpage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.yaml.MessageYaml;
import pers.zhangyang.easyteleportask.domain.Gamer;
import pers.zhangyang.easyteleportask.domain.ManageTeleportAskPageTeleportAskOptionPage;
import pers.zhangyang.easyteleportask.enumeration.AskTypeEnum;
import pers.zhangyang.easyteleportask.manager.GamerManager;
import pers.zhangyang.easyteleportask.manager.TeleportAskManager;
import pers.zhangyang.easyteleportask.yaml.SettingYaml;

import java.util.List;

@EventListener
public class PlayerClickManageTeleportAskPageTeleportAskOptionPageAcceptTeleportAsk implements Listener {
    @GuiDiscreteButtonHandler(guiPage = ManageTeleportAskPageTeleportAskOptionPage.class,slot = 21,refreshGui = false,closeGui = true)
    public void on(InventoryClickEvent event){

        ManageTeleportAskPageTeleportAskOptionPage manageTeleportAskPageTeleportAskOptionPage= (ManageTeleportAskPageTeleportAskOptionPage) event.getInventory().getHolder();

        Player viewer= (Player) event.getWhoClicked();

        assert manageTeleportAskPageTeleportAskOptionPage != null;

       if (!TeleportAskManager.INSTANCE.getTeleportAskList().contains(manageTeleportAskPageTeleportAskOptionPage.getAsk())){
           List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notExistTeleportAsk");
           MessageUtil.sendMessageTo(viewer, list);
           return;
       }
        if (!manageTeleportAskPageTeleportAskOptionPage.getAsk().getTarget().equals(viewer)){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notTargetWhenAcceptTeleportAsk");
            MessageUtil.sendMessageTo(viewer, list);
            return;
        }


        Player target=manageTeleportAskPageTeleportAskOptionPage.getAsk().getTarget();
        Player sender=manageTeleportAskPageTeleportAskOptionPage.getAsk().getSender();
        List<String> worldNameBlackList= SettingYaml.INSTANCE.getStringList("setting.worldBlackList");


        if (worldNameBlackList!=null
                &&manageTeleportAskPageTeleportAskOptionPage.getAsk().getAskType().equals(AskTypeEnum.TELEPORT_ASK_TO)
                &&worldNameBlackList.contains(viewer.getWorld().getName())){

            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.worldBlackListWhenAcceptTeleportAsk");
            MessageUtil.sendMessageTo(viewer, list);
            return;
        }
        if (worldNameBlackList!=null
                &&manageTeleportAskPageTeleportAskOptionPage.getAsk().getAskType().equals(AskTypeEnum.TELEPORT_ASK_HERE)
                &&worldNameBlackList.contains(sender.getWorld().getName())){

            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.worldBlackListWhenAcceptTeleportAsk");
            MessageUtil.sendMessageTo(viewer, list);
            return;
        }



        if (manageTeleportAskPageTeleportAskOptionPage.getAsk().getAskType().equals(AskTypeEnum.TELEPORT_ASK_TO)){
            sender.teleport(viewer.getLocation());
        }
        if (manageTeleportAskPageTeleportAskOptionPage.getAsk().getAskType().equals(AskTypeEnum.TELEPORT_ASK_HERE)){
            viewer.teleport(sender.getLocation());
        }

        TeleportAskManager.INSTANCE.getTeleportAskList().remove(manageTeleportAskPageTeleportAskOptionPage.getAsk());


        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.acceptTeleportAsk");
        MessageUtil.sendMessageTo(viewer, list);



    }

}