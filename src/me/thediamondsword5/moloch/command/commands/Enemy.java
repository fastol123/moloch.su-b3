/*    */ package me.thediamondsword5.moloch.command.commands;
/*    */ 
/*    */ import me.thediamondsword5.moloch.client.EnemyManager;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.spartanb312.base.command.Command;
/*    */ import net.spartanb312.base.common.annotations.CommandInfo;
/*    */ import net.spartanb312.base.utils.ChatUtil;
/*    */ import net.spartanb312.base.utils.EntityUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @CommandInfo(command = "enemy", description = "enemy command.")
/*    */ public class Enemy
/*    */   extends Command
/*    */ {
/*    */   public void onCall(String s, String[] args) {
/*    */     try {
/* 21 */       if (args[0].equalsIgnoreCase("all")) {
/* 22 */         for (EntityPlayer player : (Minecraft.func_71410_x()).field_71441_e.field_73010_i) {
/* 23 */           if (EntityUtil.isFakeLocalPlayer((Entity)player)) {
/*    */             continue;
/*    */           }
/* 26 */           if (!player.func_82150_aj()) {
/* 27 */             EnemyManager.add((Entity)player);
/*    */           }
/*    */         } 
/* 30 */       } else if (args[0].equalsIgnoreCase("get")) {
/* 31 */         ChatUtil.sendNoSpamMessage((EnemyManager.getInstance()).enemies.toString());
/* 32 */       } else if (args[0].equalsIgnoreCase("add")) {
/* 33 */         EnemyManager.add(args[1]);
/* 34 */         ChatUtil.printChatMessage("Added enemy : " + args[1]);
/* 35 */       } else if (args[0].equalsIgnoreCase("remove")) {
/* 36 */         EnemyManager.remove(args[1]);
/* 37 */         ChatUtil.printChatMessage("Removed enemy : " + args[1]);
/*    */       } else {
/* 39 */         ChatUtil.sendNoSpamErrorMessage(getSyntax());
/*    */       }
/*    */     
/* 42 */     } catch (Exception e) {
/* 43 */       ChatUtil.sendNoSpamErrorMessage(getSyntax());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSyntax() {
/* 49 */     return "enemy <add/all/get/remove>";
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\command\commands\Enemy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */