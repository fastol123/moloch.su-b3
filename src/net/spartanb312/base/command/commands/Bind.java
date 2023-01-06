/*    */ package net.spartanb312.base.command.commands;
/*    */ 
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.command.Command;
/*    */ import net.spartanb312.base.common.annotations.CommandInfo;
/*    */ import net.spartanb312.base.core.common.KeyBind;
/*    */ import net.spartanb312.base.module.Module;
/*    */ import net.spartanb312.base.utils.ChatUtil;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ @CommandInfo(command = "bind", description = "Set module bind key.")
/*    */ public class Bind
/*    */   extends Command
/*    */ {
/*    */   public void onCall(String s, String[] args) {
/* 16 */     if (args.length == 1) {
/* 17 */       ChatUtil.sendNoSpamMessage("Please specify a module.");
/*    */       
/*    */       return;
/*    */     } 
/*    */     try {
/* 22 */       String module = args[0];
/* 23 */       String rKey = args[1];
/*    */       
/* 25 */       Module m = ModuleManager.getModuleByName(module);
/*    */       
/* 27 */       if (m == null) {
/* 28 */         ChatUtil.sendNoSpamMessage("Unknown module '" + module + "'!");
/*    */         
/*    */         return;
/*    */       } 
/* 32 */       if (rKey == null) {
/* 33 */         ChatUtil.sendNoSpamMessage(m.name + " is bound to " + ChatUtil.SECTIONSIGN + "b" + Keyboard.getKeyName(((KeyBind)m.bindSetting.getValue()).getKeyCode()));
/*    */         
/*    */         return;
/*    */       } 
/* 37 */       int key = Keyboard.getKeyIndex(rKey);
/* 38 */       boolean isNone = false;
/*    */       
/* 40 */       if (rKey.equalsIgnoreCase("none")) {
/* 41 */         key = 0;
/* 42 */         isNone = true;
/*    */       } 
/*    */       
/* 45 */       if (key == 0 && !isNone) {
/* 46 */         ChatUtil.sendNoSpamMessage("Unknown key '" + rKey + "'!");
/*    */         
/*    */         return;
/*    */       } 
/* 50 */       ((KeyBind)m.bindSetting.getValue()).setKeyCode(key);
/* 51 */       ChatUtil.sendNoSpamMessage("Bind for " + ChatUtil.SECTIONSIGN + "b" + m.name + ChatUtil.SECTIONSIGN + "r set to " + ChatUtil.SECTIONSIGN + "b" + rKey.toUpperCase());
/*    */     }
/* 53 */     catch (Exception e) {
/* 54 */       ChatUtil.sendNoSpamErrorMessage(getSyntax());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSyntax() {
/* 61 */     return "bind <module> <bind>";
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\command\commands\Bind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */