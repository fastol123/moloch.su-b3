/*    */ package net.spartanb312.base.command.commands;
/*    */ 
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketChatMessage;
/*    */ import net.spartanb312.base.command.Command;
/*    */ import net.spartanb312.base.common.annotations.CommandInfo;
/*    */ import net.spartanb312.base.utils.ChatUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandInfo(command = "say", description = "Send message to chat.")
/*    */ public class Send
/*    */   extends Command
/*    */ {
/*    */   public void onCall(String s, String[] args) {
/*    */     try {
/* 18 */       StringBuilder content = new StringBuilder();
/* 19 */       for (String arg : args) {
/* 20 */         content.append(" ").append(arg);
/*    */       }
/* 22 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage(content.toString()));
/* 23 */     } catch (Exception e) {
/* 24 */       ChatUtil.sendNoSpamErrorMessage(getSyntax());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSyntax() {
/* 30 */     return "say <message>";
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\command\commands\Send.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */