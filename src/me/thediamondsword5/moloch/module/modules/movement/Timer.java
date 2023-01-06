/*    */ package me.thediamondsword5.moloch.module.modules.movement;
/*    */ 
/*    */ import me.thediamondsword5.moloch.client.ServerManager;
/*    */ import me.thediamondsword5.moloch.event.events.player.UpdateTimerEvent;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.event.Listener;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "Timer", category = Category.MOVEMENT, description = "Modifies client side tick speed")
/*    */ public class Timer
/*    */   extends Module {
/* 16 */   Setting<Boolean> tpsSync = setting("TPSSync", false).des("Syncs client side tick speed with server side tick speed");
/* 17 */   Setting<Float> speed = setting("Speed", 10.0F, 0.1F, 20.0F).des("Speed of ticks").whenFalse(this.tpsSync);
/*    */   
/*    */   @Listener
/*    */   public void onUpdateTimer(UpdateTimerEvent event) {
/* 21 */     if (((Boolean)this.tpsSync.getValue()).booleanValue()) {
/* 22 */       event.timerSpeed *= ServerManager.getTPS() / 20.0F;
/*    */     } else {
/*    */       
/* 25 */       event.timerSpeed *= ((Float)this.speed.getValue()).floatValue();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\movement\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */