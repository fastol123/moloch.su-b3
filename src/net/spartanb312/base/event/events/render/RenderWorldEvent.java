/*    */ package net.spartanb312.base.event.events.render;
/*    */ 
/*    */ import net.spartanb312.base.core.event.decentralization.EventData;
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public final class RenderWorldEvent
/*    */   extends EventCenter implements EventData {
/*    */   private final float partialTicks;
/*    */   private final Pass pass;
/*    */   
/*    */   public RenderWorldEvent(float partialTicks, int pass) {
/* 12 */     this.partialTicks = partialTicks;
/* 13 */     this.pass = Pass.values()[pass];
/*    */   }
/*    */   
/*    */   public final Pass getPass() {
/* 17 */     return this.pass;
/*    */   }
/*    */   
/*    */   public final float getPartialTicks() {
/* 21 */     return this.partialTicks;
/*    */   }
/*    */   
/*    */   public enum Pass {
/* 25 */     ANAGLYPH_CYAN, ANAGLYPH_RED, NORMAL;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\event\events\render\RenderWorldEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */