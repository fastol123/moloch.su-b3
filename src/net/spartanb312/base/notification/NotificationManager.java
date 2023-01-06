/*    */ package net.spartanb312.base.notification;
/*    */ 
/*    */ import me.thediamondsword5.moloch.module.modules.client.ChatSettings;
/*    */ import net.spartanb312.base.module.Module;
/*    */ import net.spartanb312.base.utils.ChatUtil;
/*    */ 
/*    */ public class NotificationManager
/*    */ {
/*    */   public static void raw(String message) {
/* 10 */     ChatUtil.printChatMessage(message);
/*    */   }
/*    */   
/*    */   public static void info(String message) {
/* 14 */     raw("[Info]" + message);
/*    */   }
/*    */   
/*    */   public static void warn(String message) {
/* 18 */     raw(color("6") + "[Warning]" + color("r") + message);
/*    */   }
/*    */   
/*    */   public static void error(String message) {
/* 22 */     ChatUtil.printErrorChatMessage(color("c") + "[Error]" + color("r") + message);
/*    */   }
/*    */   
/*    */   public static void fatal(String message) {
/* 26 */     ChatUtil.printErrorChatMessage(color("4") + "[Fatal]" + color("r") + message);
/*    */   }
/*    */   
/*    */   public static void debug(String message) {
/* 30 */     raw(color("a") + "[Debug]" + color("r") + message);
/*    */   }
/*    */   
/*    */   public static void moduleToggle(Module module, String name, boolean toggled) {
/* 34 */     ChatUtil.sendNoSpamMessage(ChatUtil.effectString(ChatSettings.INSTANCE.moduleEffects) + name + "§r " + ChatUtil.SECTIONSIGN + " has been " + (toggled ? color("aEnabled") : color("cDisabled")) + color("r") + "!");
/*    */   }
/*    */   
/*    */   public static String color(String color) {
/* 38 */     return ChatUtil.SECTIONSIGN + color;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\notification\NotificationManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */