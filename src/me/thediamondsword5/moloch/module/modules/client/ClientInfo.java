/*    */ package me.thediamondsword5.moloch.module.modules.client;
/*    */ 
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "ClientInfo", category = Category.CLIENT, description = "Change client information")
/*    */ public class ClientInfo
/*    */   extends Module {
/*    */   public static ClientInfo INSTANCE;
/* 14 */   public final String modVersion = "b3";
/*    */   
/* 16 */   public Setting<String> clientName = setting("ClientName", "moloch.su");
/* 17 */   public Setting<String> clientVersion = setting("ClientVersion", "b3");
/* 18 */   public Setting<String> clientPrefix = setting("ClientPrefix", "+");
/*    */   
/*    */   public ClientInfo() {
/* 21 */     INSTANCE = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 26 */     enable();
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\client\ClientInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */