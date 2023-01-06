/*    */ package net.spartanb312.base.event.events.render;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.spartanb312.base.core.event.decentralization.EventData;
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public final class RenderOverlayEvent
/*    */   extends EventCenter implements EventData {
/*    */   private final float partialTicks;
/*    */   private final ScaledResolution scaledResolution;
/*    */   
/*    */   public RenderOverlayEvent() {
/* 14 */     this.partialTicks = Minecraft.func_71410_x().func_184121_ak();
/* 15 */     this.scaledResolution = new ScaledResolution(Minecraft.func_71410_x());
/*    */   }
/*    */   
/*    */   public RenderOverlayEvent(float partialTicks) {
/* 19 */     this.partialTicks = partialTicks;
/* 20 */     this.scaledResolution = new ScaledResolution(Minecraft.func_71410_x());
/*    */   }
/*    */   
/*    */   public final float getPartialTicks() {
/* 24 */     return this.partialTicks;
/*    */   }
/*    */   
/*    */   public final ScaledResolution getScaledResolution() {
/* 28 */     return this.scaledResolution;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\event\events\render\RenderOverlayEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */