package pers.zhangyang.easyteleportask.domain;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easylibrary.base.BackAble;
import pers.zhangyang.easylibrary.base.GuiPage;
import pers.zhangyang.easylibrary.base.MultipleGuiPageBase;
import pers.zhangyang.easylibrary.util.CommandUtil;
import pers.zhangyang.easylibrary.util.PageUtil;
import pers.zhangyang.easylibrary.util.ReplaceUtil;
import pers.zhangyang.easylibrary.util.TimeUtil;
import pers.zhangyang.easyteleportask.enumeration.AskTypeEnum;
import pers.zhangyang.easyteleportask.manager.GamerManager;
import pers.zhangyang.easyteleportask.manager.TeleportAskManager;
import pers.zhangyang.easyteleportask.yaml.GuiYaml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ManageTeleportAskPage extends MultipleGuiPageBase implements BackAble{
    public ManageTeleportAskPage(@NotNull Player viewer, @Nullable GuiPage backPage, OfflinePlayer owner) {
        super(GuiYaml.INSTANCE.getString("gui.title.manageTeleportAskPage"), viewer, backPage, owner,54);
    }

    private List<TeleportAsk> teleportAskList;


    @Override
    public void send() {
        this.pageIndex=0;
        refresh();
    }

    @Override
    public void refresh() {

        Player onlineOwner=owner.getPlayer();
        if (onlineOwner==null){
            back();
            return;
        }

        this.inventory.clear();
        List<TeleportAsk> teleportAskList=new ArrayList<>(TeleportAskManager.INSTANCE.getTeleportAskList(onlineOwner));
        Collections.reverse(teleportAskList);
        this.teleportAskList=PageUtil.page(this.pageIndex,45,teleportAskList);

        for (int i=0;i<45;i++){
            if (i >= teleportAskList.size()) {
                break;
            }

            ItemStack itemStack=GuiYaml.INSTANCE.getButtonDefault("gui.button.manageTeleportAskPage.manageTeleportAskPageTeleportAskOptionPage");
            TeleportAsk ask=teleportAskList.get(i);
            HashMap<String,String> rep=new HashMap<>();
            rep.put("{sender_name}",ask.getSender().getName());
            rep.put("{target_name}",ask.getTarget().getName());

            rep.put("{create_time}", TimeUtil.getTimeFromTimeMill(ask.getTime()));
            if (ask.getAskType().equals(AskTypeEnum.TELEPORT_ASK_TO)) {
                rep.put("{teleport_ask_type}", GuiYaml.INSTANCE.getStringDefault("gui.replace.teleportAskTo"));
            }
            if (ask.getAskType().equals(AskTypeEnum.TELEPORT_ASK_TO)) {
                rep.put("{teleport_ask_type}", GuiYaml.INSTANCE.getStringDefault("gui.replace.teleportAskHere"));
            }
            ReplaceUtil.replaceDisplayName(itemStack,rep);
            ReplaceUtil.replaceLore(itemStack,rep);

            this.inventory.setItem(i,itemStack);
        }




        ItemStack returnPage= GuiYaml.INSTANCE.getButtonDefault("gui.button.manageTeleportAskPage.back");
        this.inventory.setItem(49,returnPage);


        ItemStack teleportTo= GuiYaml.INSTANCE.getButtonDefault("gui.button.manageTeleportAskPage.teleportAskTo");
        this.inventory.setItem(48,teleportTo);

        ItemStack teleportHere= GuiYaml.INSTANCE.getButtonDefault("gui.button.manageTeleportAskPage.teleportAskHere");
        this.inventory.setItem(50,teleportHere);





        if (pageIndex > 0) {
            ItemStack previous = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageTeleportAskPage.previousPage");
            inventory.setItem(45, previous);
        }
        int maxIndex = PageUtil.computeMaxPageIndex(TeleportAskManager.INSTANCE.getTeleportAskList(onlineOwner).size(), 45);
        if (pageIndex > maxIndex) {
            this.pageIndex = maxIndex;
        }
        if (pageIndex < maxIndex) {
            ItemStack next = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageTeleportAskPage.nextPage");
            inventory.setItem(53, next);
        }

        viewer.openInventory(this.inventory);
    }

    @Override
    public int getPreviousPageSlot() {
        return 45;
    }

    @Override
    public int getNextPageSlot() {
        return 53;
    }

    @NotNull
    public List<TeleportAsk> getTeleportAskList() {
        return teleportAskList;
    }

    @Override
    public void back() {
        List<String> cmdList= GuiYaml.INSTANCE.getStringList("gui.firstPageBackCommand");
        if (cmdList==null){
            return;
        }
        CommandUtil.dispatchCommandList(viewer,cmdList);
    }

    @Override
    public int getBackSlot() {
        return 49;
    }
}
