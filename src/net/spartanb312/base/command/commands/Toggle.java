/*    */ package net.spartanb312.base.command.commands;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.command.Command;
/*    */ import net.spartanb312.base.common.annotations.CommandInfo;
/*    */ import net.spartanb312.base.module.Module;
/*    */ import net.spartanb312.base.utils.ChatUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandInfo(command = "toggle", description = "Toggle selected module or HUD.")
/*    */ public class Toggle
/*    */   extends Command
/*    */ {
/*    */   public void onCall(String s, String[] args) {
/*    */     try {
/* 20 */       ((Module)Objects.<Module>requireNonNull(ModuleManager.getModuleByName(args[0]))).toggle();
/* 21 */     } catch (Exception e) {
/* 22 */       ChatUtil.sendNoSpamErrorMessage(getSyntax());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSyntax() {
/* 28 */     return "toggle <modulename>";
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\command\commands\Toggle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */