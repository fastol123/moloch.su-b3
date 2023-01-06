/*    */ package net.spartanb312.base.mixin.mixins.client;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.player.KeyEvent;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({KeyBinding.class})
/*    */ public class MixinKeyBinding {
/*    */   @Shadow
/*    */   private boolean field_74513_e;
/*    */   
/*    */   @Inject(method = {"isKeyDown"}, at = {@At("RETURN")}, cancellable = true)
/*    */   private void isKeyDownHook(CallbackInfoReturnable<Boolean> cir) {
/* 19 */     KeyEvent event = new KeyEvent(((Boolean)cir.getReturnValue()).booleanValue(), this.field_74513_e);
/* 20 */     BaseCenter.EVENT_BUS.post(event);
/* 21 */     cir.setReturnValue(Boolean.valueOf(event.info));
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\client\MixinKeyBinding.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */