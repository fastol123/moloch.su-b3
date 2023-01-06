/*    */ package net.spartanb312.base.module.modules.movement;
/*    */ 
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ 
/*    */ @Parallel(runnable = true)
/*    */ @ModuleInfo(name = "Sprint", category = Category.MOVEMENT, description = "Automatically sprint")
/*    */ public class Sprint
/*    */   extends Module {
/* 13 */   Setting<Boolean> allDirections = setting("MultiDirectional", true).des("Sprint in all directions");
/* 14 */   Setting<Boolean> collideStop = setting("CollideStop", true).des("Stops sprinting when you are against a block");
/*    */ 
/*    */   
/*    */   public void onRenderTick() {
/* 18 */     if (mc.field_71439_g == null)
/* 19 */       return;  mc.field_71439_g.func_70031_b(((((Boolean)this.allDirections.getValue()).booleanValue() ? (mc.field_71439_g.field_70702_br != 0.0F || mc.field_71439_g.field_191988_bg != 0.0F) : (mc.field_71439_g.field_191988_bg != 0.0F)) && !mc.field_71439_g.func_70093_af() && (!((Boolean)this.collideStop.getValue()).booleanValue() || !mc.field_71439_g.field_70123_F) && mc.field_71439_g.func_71024_bL().func_75116_a() > 6.0F));
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\module\modules\movement\Sprint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */