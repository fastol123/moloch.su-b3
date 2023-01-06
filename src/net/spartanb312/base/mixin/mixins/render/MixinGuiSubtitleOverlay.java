/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiSubtitleOverlay;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import net.spartanb312.base.core.event.decentralization.EventData;
/*    */ import net.spartanb312.base.event.decentraliized.DecentralizedRenderTickEvent;
/*    */ import net.spartanb312.base.event.events.render.RenderOverlayEvent;
/*    */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({GuiSubtitleOverlay.class})
/*    */ public class MixinGuiSubtitleOverlay
/*    */ {
/*    */   @Inject(method = {"renderSubtitles"}, at = {@At("HEAD")})
/*    */   public void onRender2D(ScaledResolution resolution, CallbackInfo ci) {
/* 20 */     RenderUtils2D.prepareGl();
/* 21 */     RenderOverlayEvent event = new RenderOverlayEvent();
/* 22 */     DecentralizedRenderTickEvent.instance.post((EventData)event);
/* 23 */     BaseCenter.EVENT_BUS.post(event);
/* 24 */     RenderUtils2D.releaseGl();
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinGuiSubtitleOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */