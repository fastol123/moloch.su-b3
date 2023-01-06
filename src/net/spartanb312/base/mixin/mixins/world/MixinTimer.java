/*    */ package net.spartanb312.base.mixin.mixins.world;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.player.UpdateTimerEvent;
/*    */ import net.minecraft.util.Timer;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({Timer.class})
/*    */ public class MixinTimer
/*    */ {
/*    */   @Shadow
/*    */   public float field_194148_c;
/*    */   
/*    */   @Inject(method = {"updateTimer"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/util/Timer;elapsedPartialTicks:F", ordinal = 1)})
/*    */   private void onUpdateTimerTicks(CallbackInfo ci) {
/* 20 */     UpdateTimerEvent event = new UpdateTimerEvent(1.0F);
/* 21 */     BaseCenter.EVENT_BUS.post(event);
/* 22 */     this.field_194148_c *= event.timerSpeed;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\world\MixinTimer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */