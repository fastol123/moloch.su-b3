/*    */ package net.spartanb312.base.engine.tasks;
/*    */ import net.spartanb312.base.engine.RenderTask;
/*    */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*    */ 
/*    */ public class RectRenderTask implements RenderTask {
/*    */   float x;
/*    */   float y;
/*    */   float endX;
/*    */   float endY;
/*    */   
/*    */   public RectRenderTask(float x, float y, float endX, float endY, int color1, int color2, int color3, int color4) {
/* 12 */     this.x = x;
/* 13 */     this.y = y;
/* 14 */     this.endX = endX;
/* 15 */     this.endY = endY;
/* 16 */     this.color1 = color1;
/* 17 */     this.color2 = color2;
/* 18 */     this.color3 = color3;
/* 19 */     this.color4 = color4;
/*    */   }
/*    */   int color1; int color2; int color3; int color4;
/*    */   public RectRenderTask(float x, float y, float endX, float endY, int color) {
/* 23 */     this.x = x;
/* 24 */     this.y = y;
/* 25 */     this.endX = endX;
/* 26 */     this.endY = endY;
/* 27 */     this.color1 = color;
/* 28 */     this.color2 = color;
/* 29 */     this.color3 = color;
/* 30 */     this.color4 = color;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender() {
/* 35 */     RenderUtils2D.drawCustomRect(this.x, this.y, this.endX, this.endY, this.color1, this.color2, this.color3, this.color4);
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\engine\tasks\RectRenderTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */