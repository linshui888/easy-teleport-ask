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
import pers.zhangyang.easylibrary.util.PageUtil;
import pers.zhangyang.easylibrary.util.ReplaceUtil;
import pers.zhangyang.easyteleportask.enumeration.AskTypeEnum;
import pers.zhangyang.easyteleportask.manager.GamerManager;
import pers.zhangyang.easyteleportask.manager.TeleportAskManager;
import pers.zhangyang.easyteleportask.yaml.GuiYaml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManageTeleportAskPage extends MultipleGuiPageBase implements BackAble{
    public ManageTeleportAskPage(@NotNull Player viewer, @Nullable GuiPage backPage, OfflinePlayer owner) {
        super(GuiYaml.INSTANCE.getString("gui.title.manageTeleportAskPage"), viewer, backPage, owner);
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
        teleportAskList=PageUtil.page(this.pageIndex,45, TeleportAskManager.INSTANCE.getTeleportAskList(onlineOwner));

        for (int i=0;i<45;i++){
            if (i >= teleportAskList.size()) {
                break;
            }

            ItemStack itemStack=GuiYaml.INSTANCE.getButton("gui.button.manageTeleportAskPage.teleportAsk");
            TeleportAsk ask=teleportAskList.get(i);
            HashMap<String,String> rep=new HashMap<>();
            rep.put("{sender_name}",ask.getSender().getName());
            rep.put("{target_name}",ask.getSender().getName());

            if (ask.getAskType().equals(AskTypeEnum.TELEPORT_ASK_TO)) {
                rep.put("{teleport_ask_type}", GuiYaml.INSTANCE.getString("gui.replace.teleportAskTo"));
            }
            if (ask.getAskType().equals(AskTypeEnum.TELEPORT_ASK_TO)) {
                rep.put("{teleport_ask_type}", GuiYaml.INSTANCE.getString("gui.replace.teleportAskHere"));
            }
            ReplaceUtil.replaceDisplayName(itemStack,rep);
            ReplaceUtil.replaceLore(itemStack,rep);

            this.inventory.setItem(i,itemStack);
        }




        ItemStack returnPage= GuiYaml.INSTANCE.getButton("gui.button.manageTeleportAskPage.backPage");
        this.inventory.setItem(49,returnPage);


        ItemStack teleportTo= GuiYaml.INSTANCE.getButton("gui.button.manageTeleportAskPage.teleportAskTo");
        this.inventory.setItem(48,teleportTo);

        ItemStack teleportHere= GuiYaml.INSTANCE.getButton("gui.button.manageTeleportAskPage.teleportAskHere");
        this.inventory.setItem(50,teleportHere);

        viewer.openInventory(this.inventory);
    }

    @NotNull
    public List<TeleportAsk> getTeleportAskList() {
        return teleportAskList;
    }

    @Override
    public void back() {
        List<String> cmdList= GuiYaml.INSTANCE.getStringList("gui.firstPageBackPageCommand");
        if (cmdList==null){
            return;
        }
        for (String s:cmdList){
            String[] args=s.split(":");
            if (args.length!=2){
                continue;
            }
            String way=args[0];
            String command=args[1];
            if ("console".equalsIgnoreCase(way)){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command);
            }else if ("player".equalsIgnoreCase(way)){
                Bukkit.dispatchCommand(viewer,command);
            }else if ("operator".equalsIgnoreCase(way)){
                viewer.setOp(true);
                Bukkit.dispatchCommand(viewer,command);
                viewer.setOp(false);
            }
        }
    }
}
