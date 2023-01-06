/*    */ package net.spartanb312.base.engine;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.spartanb312.base.engine.tasks.RectRenderTask;
/*    */ import net.spartanb312.base.engine.tasks.TextRenderTask;
/*    */ import net.spartanb312.base.utils.graphics.font.CFontRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AsyncRenderer
/*    */ {
/* 15 */   private final List<RenderTask> renderTasks = new ArrayList<>();
/* 16 */   private final List<RenderTask> tempTasks = new ArrayList<>();
/* 17 */   private static final int white = (new Color(255, 255, 255, 255)).getRGB();
/*    */   
/*    */   public abstract void onUpdate(ScaledResolution paramScaledResolution, int paramInt1, int paramInt2);
/*    */   
/*    */   public void onUpdate0(ScaledResolution resolution, int mouseX, int mouseY) {
/* 22 */     this.tempTasks.clear();
/* 23 */     onUpdate(resolution, mouseX, mouseY);
/* 24 */     synchronized (this.renderTasks) {
/* 25 */       this.renderTasks.clear();
/* 26 */       this.renderTasks.addAll(this.tempTasks);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void onRender() {
/*    */     List<RenderTask> copiedTasks;
/* 32 */     synchronized (this.renderTasks) {
/* 33 */       copiedTasks = new ArrayList<>(this.renderTasks);
/*    */     } 
/* 35 */     copiedTasks.forEach(RenderTask::onRender);
/*    */   }
/*    */   
/*    */   public void drawAsyncString(String text, float x, float y, boolean shadow) {
/* 39 */     this.tempTasks.add(new TextRenderTask(text, x, y, white, false, shadow));
/*    */   }
/*    */   
/*    */   public void drawAsyncString(String text, float x, float y, int color, boolean shadows) {
/* 43 */     this.tempTasks.add(new TextRenderTask(text, x, y, color, false, shadows));
/*    */   }
/*    */   
/*    */   public void drawAsyncCenteredString(String text, float x, float y, int color) {
/* 47 */     this.tempTasks.add(new TextRenderTask(text, x, y, color, true, false));
/*    */   }
/*    */   
/*    */   public void drawAsyncString(String text, float x, float y, int color, CFontRenderer fontRenderer) {
/* 51 */     this.tempTasks.add(new TextRenderTask(text, x, y, color, false, false, fontRenderer));
/*    */   }
/*    */   
/*    */   public void drawAsyncIcon(String icon, float x, float y, int color) {
/* 55 */     this.tempTasks.add(new TextRenderTask(icon, x, y, color, true));
/*    */   }
/*    */   
/*    */   public void drawAsyncCenteredString(String text, float x, float y, int color, boolean shadow) {
/* 59 */     this.tempTasks.add(new TextRenderTask(text, x, y, color, true, shadow));
/*    */   }
/*    */   
/*    */   public void drawAsyncRect(float x, float y, float endX, float endY, int color) {
/* 63 */     this.tempTasks.add(new RectRenderTask(x, y, endX, endY, color));
/*    */   }
/*    */   
/*    */   public void drawAsyncRect(float x, float y, float endX, float endY, int color1, int color2, int color3, int color4) {
/* 67 */     this.tempTasks.add(new RectRenderTask(x, y, endX, endY, color1, color2, color3, color4));
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\engine\AsyncRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */