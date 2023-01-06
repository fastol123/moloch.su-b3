/*    */ package net.spartanb312.base.command.commands;
/*    */ 
/*    */ import net.spartanb312.base.client.ConfigManager;
/*    */ import net.spartanb312.base.command.Command;
/*    */ import net.spartanb312.base.common.annotations.CommandInfo;
/*    */ import net.spartanb312.base.utils.ChatUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandInfo(command = "config", description = "Save or load config.")
/*    */ public class Config
/*    */   extends Command
/*    */ {
/*    */   public void onCall(String s, String[] args) {
/* 17 */     if (args[0] == null) {
/* 18 */       ChatUtil.sendNoSpamMessage("Missing argument &bmode&r: Choose from reload, save or path");
/*    */       return;
/*    */     } 
/* 21 */     switch (args[0].toLowerCase()) {
/*    */       case "save":
/* 23 */         save();
/*    */         break;
/*    */       case "load":
/* 26 */         load();
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSyntax() {
/* 33 */     return "config <save/load>";
/*    */   }
/*    */   
/*    */   public void load() {
/* 37 */     ConfigManager.loadAll();
/* 38 */     ChatUtil.sendNoSpamMessage("Configuration reloaded!");
/*    */   }
/*    */   
/*    */   public void save() {
/* 42 */     ConfigManager.saveAll();
/* 43 */     ChatUtil.sendNoSpamMessage("Configuration saved!");
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\command\commands\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */