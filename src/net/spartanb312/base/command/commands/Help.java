/*    */ package net.spartanb312.base.command.commands;
/*    */ 
/*    */ import me.thediamondsword5.moloch.module.modules.client.ClientInfo;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.command.Command;
/*    */ import net.spartanb312.base.common.annotations.CommandInfo;
/*    */ import net.spartanb312.base.core.common.KeyBind;
/*    */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*    */ import net.spartanb312.base.utils.ChatUtil;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandInfo(command = "help", description = "Get helps.")
/*    */ public class Help
/*    */   extends Command
/*    */ {
/*    */   public void onCall(String s, String[] args) {
/* 21 */     ChatUtil.printChatMessage("§b" + (String)ClientInfo.INSTANCE.clientName.getValue() + " §a" + (String)ClientInfo.INSTANCE.clientVersion.getValue());
/* 22 */     ChatUtil.printChatMessage("§cMade by: TheDiamondSword5 && popbob && B_312");
/* 23 */     ChatUtil.printChatMessage("§cGithub: base -> https://github.com/SpartanB312/Cursa");
/* 24 */     ChatUtil.printChatMessage("§3Press §c" + Keyboard.getKeyName(((KeyBind)(ModuleManager.getModule(ClickGUI.class)).bindSetting.getValue()).getKeyCode()) + "§3 to open ClickGUI");
/* 25 */     ChatUtil.printChatMessage("§3Use command: §9" + (String)ClientInfo.INSTANCE.clientPrefix.getValue() + "prefix <target prefix>§3 to set command prefix");
/* 26 */     ChatUtil.printChatMessage("§3List all available commands: §9" + (String)ClientInfo.INSTANCE.clientPrefix.getValue() + "commands");
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSyntax() {
/* 31 */     return "help";
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\command\commands\Help.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */