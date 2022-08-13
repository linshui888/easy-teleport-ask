package pers.zhangyang.easyteleportask.domain;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easylibrary.base.BackAble;
import pers.zhangyang.easylibrary.base.GuiPage;
import pers.zhangyang.easylibrary.base.SingleGuiPageBase;
import pers.zhangyang.easylibrary.util.ReplaceUtil;
import pers.zhangyang.easylibrary.util.TimeUtil;
import pers.zhangyang.easyteleportask.enumeration.AskTypeEnum;
import pers.zhangyang.easyteleportask.manager.TeleportAskManager;
import pers.zhangyang.easyteleportask.yaml.GuiYaml;

import java.util.HashMap;

public class ManageTeleportAskPageTeleportAskOptionPage extends SingleGuiPageBase implements BackAble {
    private final TeleportAsk ask;
    public ManageTeleportAskPageTeleportAskOptionPage(TeleportAsk ask, Player viewer, GuiPage backPage, OfflinePlayer owner) {

        super(GuiYaml.INSTANCE.getString("gui.title.manageTeleportAskPageTeleportAskOptionPage"), viewer, backPage, owner);
        this.ask=ask;
    }


    @Override
    public void refresh() {

        this.inventory.clear();
        if (!TeleportAskManager.INSTANCE.getTeleportAskList().contains(ask)){
            backPage.refresh();
            return;
        }

        ItemStack backPage= GuiYaml.INSTANCE.getButtonDefault("gui.button.manageTeleportAskPageTeleportAskOptionPage.back");
        this.inventory.setItem(49,backPage);

        if (owner.getUniqueId().equals(ask.getTarget().getUniqueId())) {
            ItemStack acceptTeleportAsk = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageTeleportAskPageTeleportAskOptionPage.acceptTeleportAsk");
            this.inventory.setItem(21, acceptTeleportAsk);

            ItemStack denyTeleportAsk = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageTeleportAskPageTeleportAskOptionPage.denyTeleportAsk");
            this.inventory.setItem(23, denyTeleportAsk);
        }


        if (owner.getUniqueId().equals(ask.getSender().getUniqueId())) {
            ItemStack cancelTeleportAsk = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageTeleportAskPageTeleportAskOptionPage.cancelTeleportAsk");
            this.inventory.setItem(22, cancelTeleportAsk);
        }

        ItemStack teleportAskInformation= GuiYaml.INSTANCE.getButtonDefault("gui.button.manageTeleportAskPageTeleportAskOptionPage.teleportAskInformation");
        HashMap<String,String> rep=new HashMap<>();
        rep.put("{sender_name}",ask.getSender().getName());
        rep.put("{target_name}",ask.getTarget().getName());
        rep.put("{sender_x}", String.valueOf(ask.getSender().getLocation().getX()));
        rep.put("{sender_y}", String.valueOf(ask.getSender().getLocation().getY()));
        rep.put("{sender_z}", String.valueOf(ask.getSender().getLocation().getZ()));

        rep.put("{create_time}", TimeUtil.getTimeFromTimeMill(ask.getTime()));
        rep.put("{sender_world}", ask.getSender().getWorld().getName());

        if (ask.getAskType().equals(AskTypeEnum.TELEPORT_ASK_TO)) {
            rep.put("{teleport_ask_type}", GuiYaml.INSTANCE.getStringDefault("gui.replace.teleportAskTo"));
        }
        if (ask.getAskType().equals(AskTypeEnum.TELEPORT_ASK_TO)) {
            rep.put("{teleport_ask_type}", GuiYaml.INSTANCE.getStringDefault("gui.replace.teleportAskHere"));
        }
        ReplaceUtil.replaceDisplayName(teleportAskInformation,rep);
        ReplaceUtil.replaceLore(teleportAskInformation,rep);

        this.inventory.setItem(13,teleportAskInformation);



        viewer.openInventory(this.inventory);
    }
    public TeleportAsk getAsk() {
        return ask;
    }

    @Override
    public void back() {
        backPage.refresh();
    }
}
