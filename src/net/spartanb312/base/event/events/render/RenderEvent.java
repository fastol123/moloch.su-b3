/*    */ package net.spartanb312.base.event.events.render;
/*    */ 
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public class RenderEvent
/*    */   extends EventCenter
/*    */ {
/*    */   private final Tessellator tessellator;
/*    */   public final Vec3d renderPos;
/*    */   
/*    */   public RenderEvent(Tessellator tessellator, Vec3d renderPos) {
/* 15 */     this.tessellator = tessellator;
/* 16 */     this.renderPos = renderPos;
/*    */   }
/*    */   
/*    */   public Tessellator getTessellator() {
/* 20 */     return this.tessellator;
/*    */   }
/*    */   
/*    */   public BufferBuilder getBuffer() {
/* 24 */     return this.tessellator.func_178180_c();
/*    */   }
/*    */   
/*    */   public Vec3d getRenderPos() {
/* 28 */     return this.renderPos;
/*    */   }
/*    */   
/*    */   public void setTranslation(Vec3d translation) {
/* 32 */     getBuffer().func_178969_c(-translation.field_72450_a, -translation.field_72448_b, -translation.field_72449_c);
/*    */   }
/*    */   
/*    */   public void resetTranslation() {
/* 36 */     setTranslation(this.renderPos);
/*    */   }
/*    */   
/*    */   public static class Extra1 extends RenderEvent {
/*    */     public Extra1(Tessellator tessellator, Vec3d renderPos) {
/* 41 */       super(tessellator, renderPos);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\event\events\render\RenderEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */