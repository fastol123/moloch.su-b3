/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ 
/*    */ import net.minecraft.client.gui.MapItemRenderer;
/*    */ import net.minecraft.world.storage.MapData;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.module.modules.visuals.NoRender;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({MapItemRenderer.class})
/*    */ public class MixinMapItemRenderer {
/*    */   @Inject(method = {"renderMap"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderMapHook(MapData mapdataIn, boolean noOverlayRendering, CallbackInfo ci) {
/* 16 */     if (ModuleManager.getModule(NoRender.class).isEnabled() && ((Boolean)NoRender.INSTANCE.maps.getValue()).booleanValue())
/* 17 */       ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinMapItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */