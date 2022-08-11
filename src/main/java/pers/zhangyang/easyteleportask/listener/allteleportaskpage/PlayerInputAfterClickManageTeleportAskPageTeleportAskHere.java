package pers.zhangyang.easyteleportask.listener.allteleportaskpage;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import pers.zhangyang.easylibrary.base.FiniteInputListenerBase;
import pers.zhangyang.easylibrary.base.GuiPage;
import pers.zhangyang.easylibrary.other.vault.Vault;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.util.PermUtil;
import pers.zhangyang.easylibrary.yaml.MessageYaml;
import pers.zhangyang.easyteleportask.domain.Gamer;
import pers.zhangyang.easyteleportask.domain.TeleportAsk;
import pers.zhangyang.easyteleportask.enumeration.AskTypeEnum;
import pers.zhangyang.easyteleportask.manager.GamerManager;
import pers.zhangyang.easyteleportask.manager.TeleportAskManager;
import pers.zhangyang.easyteleportask.yaml.SettingYaml;

import java.util.List;

public class PlayerInputAfterClickManageTeleportAskPageTeleportAskHere extends FiniteInputListenerBase {
    public PlayerInputAfterClickManageTeleportAskPageTeleportAskHere(Player player, OfflinePlayer owner, GuiPage previousPage) {
        super(player, owner, previousPage, 1);

        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.howToTeleportAskHere");

        MessageUtil.sendMessageTo(player, list);
    }

    @Override
    public void run() {


        Player onlineOwner=owner.getPlayer();
        if (onlineOwner==null){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notOnline");
            MessageUtil.sendMessageTo(player, list);
            return;
        }

        Player target= Bukkit.getPlayer(messages[0]);
        if (target==null){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notOnline");
            MessageUtil.sendMessageTo(player, list);
            return;
        }

        Gamer senderGamer= GamerManager.INSTANCE.getGamer(onlineOwner);
        Gamer targetGamer= GamerManager.INSTANCE.getGamer(target);

        List<String> worldNameBlackList=SettingYaml.INSTANCE.getStringList("setting.worldBlackList");
        if (worldNameBlackList!=null &&worldNameBlackList.contains(player.getWorld().getName())){

            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.worldBlackListWhenTeleportAskHere");
            MessageUtil.sendMessageTo(player, list);
            return;
        }



        if (!onlineOwner.isOp()) {
            Integer perm = PermUtil.getMinNumberPerm("EasyTeleportAsk.teleportAskInterval.", onlineOwner);
            if (perm == null) {
                perm = 0;
            }
            TeleportAsk latestPlayerSendTeleportAsk = TeleportAskManager.INSTANCE.getLatestPlayerSendTeleportAsk(onlineOwner);
            if (latestPlayerSendTeleportAsk != null && System.currentTimeMillis() - latestPlayerSendTeleportAsk.getTime()
                    < perm * 1000L) {

                List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.tooFastWhenTeleportAskHere");
                MessageUtil.sendMessageTo(player, list);
                return;
            }
        }



        if (Vault.hook()==null){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notHookVault");
            MessageUtil.sendMessageTo(player, list);
            return;
        }
        if (Vault.hook().getBalance(onlineOwner)<SettingYaml.INSTANCE.teleportAskCost()){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notEnoughVaultWhenTeleportAskHere");
            MessageUtil.sendMessageTo(player, list);
            return;
        }
        Vault.hook().withdrawPlayer(onlineOwner,SettingYaml.INSTANCE.teleportAskCost());

        TeleportAsk teleportAsk=new TeleportAsk(onlineOwner,target, AskTypeEnum.TELEPORT_ASK_HERE, System.currentTimeMillis());
        TeleportAskManager.INSTANCE.addTeleportAsk(teleportAsk);

        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.teleportHere");
        MessageUtil.sendMessageTo(player, list);

    }
}
