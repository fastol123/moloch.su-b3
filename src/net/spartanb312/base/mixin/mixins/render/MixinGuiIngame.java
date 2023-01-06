/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiIngame;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.module.modules.visuals.NoRender;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({GuiIngame.class})
/*    */ public class MixinGuiIngame {
/*    */   @Inject(method = {"renderVignette"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void renderVignetterHook(CallbackInfo ci) {
/* 16 */     if (ModuleManager.getModule(NoRender.class).isEnabled() && ((Boolean)NoRender.INSTANCE.vignette.getValue()).booleanValue())
/* 17 */       ci.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderPumpkinOverlay"}, at = {@At("HEAD")}, cancellable = true)
/*    */   protected void renderPumpkinOverlayHook(ScaledResolution scaledRes, CallbackInfo ci) {
/* 22 */     if (ModuleManager.getModule(NoRender.class).isEnabled() && ((Boolean)NoRender.INSTANCE.pumpkin.getValue()).booleanValue())
/* 23 */       ci.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderPotionEffects"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void renderPotionEffectsHook(ScaledResolution resolution, CallbackInfo ci) {
/* 28 */     if (ModuleManager.getModule(NoRender.class).isEnabled() && ((Boolean)NoRender.INSTANCE.potionIcons.getValue()).booleanValue())
/* 29 */       ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinGuiIngame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */