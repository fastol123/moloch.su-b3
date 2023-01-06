/*    */ package me.thediamondsword5.moloch.module.modules.other;
/*    */ 
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ import net.spartanb312.base.utils.ChatUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "DummyModule", category = Category.OTHER, description = "Module that you can rename to act as another module (can be used to basically rename modules from other clients by binding them to the same keys)")
/*    */ public class DummyModule
/*    */   extends Module
/*    */ {
/* 17 */   Setting<String> info = setting("Information", "");
/*    */ 
/*    */   
/*    */   public String getHudSuffix() {
/* 21 */     return (String)this.displayName.getValue() + (!((String)this.info.getValue()).equals("") ? (ChatUtil.colored("7") + "[" + ChatUtil.colored("f") + (String)this.info.getValue() + ChatUtil.colored("7") + "]") : "");
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\other\DummyModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */