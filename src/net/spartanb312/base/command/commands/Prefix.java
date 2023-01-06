/*    */ package net.spartanb312.base.command.commands;
/*    */ 
/*    */ import me.thediamondsword5.moloch.module.modules.client.ClientInfo;
/*    */ import net.spartanb312.base.command.Command;
/*    */ import net.spartanb312.base.common.annotations.CommandInfo;
/*    */ import net.spartanb312.base.utils.ChatUtil;
/*    */ import net.spartanb312.base.utils.SoundUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandInfo(command = "prefix", description = "Set command prefix.")
/*    */ public class Prefix
/*    */   extends Command
/*    */ {
/*    */   public void onCall(String s, String[] args) {
/* 20 */     if (args.length <= 0) {
/* 21 */       ChatUtil.sendNoSpamErrorMessage("Please specify a new prefix!");
/*    */       return;
/*    */     } 
/* 24 */     if (args[0] != null) {
/* 25 */       ClientInfo.INSTANCE.clientPrefix.setValue(args[0]);
/* 26 */       ChatUtil.sendNoSpamMessage("Prefix set to " + ChatUtil.SECTIONSIGN + "b" + args[0] + "!");
/* 27 */       SoundUtil.playButtonClick();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSyntax() {
/* 33 */     return "prefix <char>";
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\command\commands\Prefix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */