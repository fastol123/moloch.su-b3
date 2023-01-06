/*    */ package me.thediamondsword5.moloch.module.modules.visuals;
/*    */ 
/*    */ import net.minecraft.init.MobEffects;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "FullBright", category = Category.VISUALS, description = "No shadows")
/*    */ public class FullBright extends Module {
/*    */   public static FullBright INSTANCE;
/* 15 */   Setting<BrightMode> brightMode = setting("BrightMode", BrightMode.Gamma);
/*    */   
/*    */   public FullBright() {
/* 18 */     INSTANCE = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 23 */     if (this.brightMode.getValue() == BrightMode.Gamma) {
/* 24 */       mc.field_71474_y.field_74333_Y = 1.0F;
/*    */     }
/* 26 */     if (this.brightMode.getValue() == BrightMode.Potion) {
/* 27 */       mc.field_71439_g.func_184596_c(MobEffects.field_76439_r);
/*    */     }
/* 29 */     this.moduleDisableFlag = true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 35 */     if (this.brightMode.getValue() == BrightMode.Potion) {
/* 36 */       mc.field_71439_g.func_70690_d(new PotionEffect(MobEffects.field_76439_r, 42069));
/*    */     }
/* 38 */     if (this.brightMode.getValue() == BrightMode.Gamma) {
/* 39 */       if (mc.field_71439_g.func_70644_a(MobEffects.field_76439_r)) {
/* 40 */         mc.field_71439_g.func_184596_c(MobEffects.field_76439_r);
/*    */       }
/* 42 */       if (mc.field_71474_y.field_74333_Y != 1000.0F)
/* 43 */         mc.field_71474_y.field_74333_Y = 1000.0F; 
/*    */     } 
/*    */   }
/*    */   
/*    */   enum BrightMode {
/* 48 */     Gamma, Potion;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\visuals\FullBright.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */