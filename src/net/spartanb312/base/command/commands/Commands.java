/*    */ package net.spartanb312.base.command.commands;
/*    */ 
/*    */ import net.spartanb312.base.client.CommandManager;
/*    */ import net.spartanb312.base.command.Command;
/*    */ import net.spartanb312.base.common.annotations.CommandInfo;
/*    */ import net.spartanb312.base.utils.ChatUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandInfo(command = "commands", description = "Lists all commands.")
/*    */ public class Commands
/*    */   extends Command
/*    */ {
/*    */   public void onCall(String s, String[] args) {
/* 17 */     ChatUtil.printChatMessage("§bCommands:");
/*    */     try {
/* 19 */       for (Command cmd : (CommandManager.getInstance()).commands) {
/* 20 */         if (cmd == this) {
/*    */           continue;
/*    */         }
/* 23 */         ChatUtil.printChatMessage("§b" + cmd.getSyntax().replace("<", "§3<§9").replace(">", "§3>") + "§8 - " + cmd.getDescription());
/*    */       } 
/* 25 */     } catch (Exception e) {
/* 26 */       ChatUtil.sendNoSpamErrorMessage(getSyntax());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSyntax() {
/* 32 */     return "commands";
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\command\commands\Commands.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */