/*    */ package net.spartanb312.base.mixin.mixins.entity;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.entity.EntityControlEvent;
/*    */ import net.minecraft.entity.passive.EntityPig;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({EntityPig.class})
/*    */ public class MixinEntityPig {
/*    */   @Inject(method = {"canBeSteered"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void canBeSteeredHook(CallbackInfoReturnable<Boolean> cir) {
/* 15 */     EntityControlEvent event = new EntityControlEvent();
/* 16 */     BaseCenter.EVENT_BUS.post(event);
/* 17 */     if (event.isCancelled()) {
/* 18 */       cir.setReturnValue(Boolean.valueOf(true));
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"getSaddled"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getSaddledHook(CallbackInfoReturnable<Boolean> cir) {
/* 24 */     EntityControlEvent event = new EntityControlEvent();
/* 25 */     BaseCenter.EVENT_BUS.post(event);
/* 26 */     if (event.isCancelled())
/* 27 */       cir.setReturnValue(Boolean.valueOf(true)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\entity\MixinEntityPig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */