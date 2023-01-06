/*    */ package me.thediamondsword5.moloch.module.modules.other;
/*    */ 
/*    */ import me.thediamondsword5.moloch.DiscordPresence;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "RPC", category = Category.OTHER, description = "Show people how cool you are B)")
/*    */ public class RPC
/*    */   extends Module
/*    */ {
/*    */   public static RPC INSTANCE;
/* 16 */   public Setting<Boolean> showIP = setting("ShowIP", true).des("Shows the server IP in your Discord RPC");
/* 17 */   public Setting<String> state = setting("State", "Moloch.su b3").des("ets the state of the Discord RPC");
/*    */   public RPC() {
/* 19 */     INSTANCE = this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 25 */     DiscordPresence.start();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 30 */     DiscordPresence.stop();
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\other\RPC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */