/*    */ package net.spartanb312.base.command.commands;
/*    */ 
/*    */ import net.spartanb312.base.command.Command;
/*    */ import net.spartanb312.base.common.annotations.CommandInfo;
/*    */ import net.spartanb312.base.utils.ChatUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandInfo(command = "tp", description = "Teleport you to the place you want.")
/*    */ public class TP
/*    */   extends Command
/*    */ {
/*    */   public void onCall(String s, String[] args) {
/* 16 */     if (args.length < 3) {
/* 17 */       ChatUtil.printErrorChatMessage(getSyntax());
/*    */       return;
/*    */     } 
/*    */     try {
/* 21 */       int x = Integer.parseInt(args[0]);
/* 22 */       int y = Integer.parseInt(args[1]);
/* 23 */       int z = Integer.parseInt(args[2]);
/* 24 */       mc.field_71439_g.func_70107_b(x, y, z);
/* 25 */     } catch (Exception e) {
/* 26 */       ChatUtil.printErrorChatMessage(getSyntax());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSyntax() {
/* 32 */     return "tp <x> <y> <z>";
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\command\commands\TP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */