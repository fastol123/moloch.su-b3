/*    */ package me.thediamondsword5.moloch.module.modules.visuals;
/*    */ 
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "CameraClip", category = Category.VISUALS, description = "Allows your 3rd person camera to clip through blocks")
/*    */ public class CameraClip
/*    */   extends Module {
/* 13 */   public Setting<Float> cameraDistance = setting("CameraDistance", 4.0F, 0.1F, 20.0F);
/*    */   
/*    */   public static CameraClip INSTANCE;
/*    */   
/*    */   public CameraClip() {
/* 18 */     INSTANCE = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\visuals\CameraClip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */