/*    */ package me.thediamondsword5.moloch.module.modules.movement;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.player.UpdateTimerEvent;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.event.Listener;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ import net.spartanb312.base.utils.EntityUtil;
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "ReverseStep", category = Category.MOVEMENT, description = "Allows you fall down blocks fast")
/*    */ public class ReverseStep
/*    */   extends Module {
/* 16 */   Setting<Boolean> timerModify = setting("Timer", true).des("Use timer to boost client side tick speed while falling (bypasses 2b2t anti reversestep i think, but will rubberband u if u go down blocks too quickly, so don't use on stairs)");
/* 17 */   Setting<Float> timerSpeed = setting("TimerSpeed", 2.0F, 1.0F, 5.0F).des("Timer speed as you are falling").whenTrue(this.timerModify);
/* 18 */   Setting<Float> speed = setting("Speed", 5.0F, 0.0F, 5.0F).des("Speed that you fall in");
/* 19 */   Setting<Float> height = setting("Height", 2.5F, 1.0F, 2.5F).des("Max height that you will fall fast");
/*    */   
/*    */   private double prevXMotion;
/*    */   
/*    */   private double prevZMotion;
/*    */   private boolean continueMotionFlag = false;
/*    */   private boolean doReverseStepFlag = true;
/*    */   
/*    */   public void onTick() {
/* 28 */     if (mc.field_71439_g.field_70181_x > 0.0D) {
/* 29 */       this.doReverseStepFlag = false;
/*    */     }
/* 31 */     if (mc.field_71439_g.field_70122_E) {
/* 32 */       this.doReverseStepFlag = true;
/*    */     }
/*    */     
/* 35 */     if (!EntityUtil.canStep() || !mc.field_71439_g.field_70122_E)
/*    */       return; 
/* 37 */     for (double d = 0.0D; d < ((Float)this.height.getValue()).floatValue() + 0.5D; d += 0.01D) {
/* 38 */       if (EntityUtil.isOnGround(d)) {
/* 39 */         mc.field_71439_g.field_70181_x = -((Float)this.speed.getValue()).floatValue();
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getModuleInfo() {
/* 47 */     if (((Boolean)this.timerModify.getValue()).booleanValue()) {
/* 48 */       return "Timer";
/*    */     }
/*    */     
/* 51 */     return "Normal";
/*    */   }
/*    */ 
/*    */   
/*    */   @Listener
/*    */   public void onUpdateTimer(UpdateTimerEvent event) {
/* 57 */     if (!((Boolean)this.timerModify.getValue()).booleanValue())
/*    */       return; 
/* 59 */     if (EntityUtil.canStep())
/* 60 */       if (!mc.field_71439_g.field_70122_E && this.doReverseStepFlag) {
/* 61 */         for (double d = 0.0D; d < ((Float)this.height.getValue()).floatValue() + 0.5D; d += 0.01D) {
/* 62 */           if (EntityUtil.isOnGround(d) && mc.field_71439_g.field_70181_x < 0.0D) {
/* 63 */             this.prevXMotion = mc.field_71439_g.field_70159_w;
/* 64 */             this.prevZMotion = mc.field_71439_g.field_70179_y;
/* 65 */             this.continueMotionFlag = true;
/* 66 */             mc.field_71439_g.field_70159_w = 0.0D;
/* 67 */             mc.field_71439_g.field_70179_y = 0.0D;
/* 68 */             mc.field_71439_g.func_70016_h(0.0D, mc.field_71439_g.field_70181_x, 0.0D);
/* 69 */             event.timerSpeed = ((Float)this.timerSpeed.getValue()).floatValue();
/*    */             
/*    */             break;
/*    */           } 
/*    */         } 
/* 74 */       } else if (this.continueMotionFlag) {
/* 75 */         mc.field_71439_g.field_70159_w = this.prevXMotion;
/* 76 */         mc.field_71439_g.field_70179_y = this.prevZMotion;
/* 77 */         mc.field_71439_g.func_70016_h(this.prevXMotion, mc.field_71439_g.field_70181_x, this.prevZMotion);
/* 78 */         this.continueMotionFlag = false;
/*    */       }  
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\movement\ReverseStep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */