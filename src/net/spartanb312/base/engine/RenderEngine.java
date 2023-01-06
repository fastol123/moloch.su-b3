/*    */ package net.spartanb312.base.engine;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.spartanb312.base.core.concurrent.ConcurrentTaskManager;
/*    */ import net.spartanb312.base.core.concurrent.blocking.BlockingContent;
/*    */ import net.spartanb312.base.core.concurrent.utils.ThreadUtil;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ public class RenderEngine
/*    */ {
/* 14 */   public static RenderEngine INSTANCE = new RenderEngine();
/* 15 */   private long lastUpdateTime = System.currentTimeMillis();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final List<AsyncRenderer> subscribedAsyncRenderers;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RenderEngine() {
/* 26 */     this.subscribedAsyncRenderers = new ArrayList<>(); (new Thread(() -> { while (true) { if ((Minecraft.func_71410_x()).field_71439_g != null)
/*    */               onUpdate();  ThreadUtil.delay(); }
/*    */         
/* 29 */         })).start(); } public static void subscribe(AsyncRenderer asyncRenderer) { synchronized (INSTANCE.subscribedAsyncRenderers) {
/* 30 */       if (!INSTANCE.subscribedAsyncRenderers.contains(asyncRenderer))
/* 31 */         INSTANCE.subscribedAsyncRenderers.add(asyncRenderer); 
/*    */     }  }
/*    */ 
/*    */   
/*    */   public static void unsubscribe(AsyncRenderer asyncRenderer) {
/* 36 */     synchronized (INSTANCE.subscribedAsyncRenderers) {
/* 37 */       INSTANCE.subscribedAsyncRenderers.remove(asyncRenderer);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void onUpdate() {
/* 42 */     if (System.currentTimeMillis() - this.lastUpdateTime >= 15L) {
/* 43 */       this.lastUpdateTime = System.currentTimeMillis();
/* 44 */       ScaledResolution resolution = new ScaledResolution(Minecraft.func_71410_x());
/* 45 */       int mouseX = Mouse.getX();
/* 46 */       int mouseY = Mouse.getY();
/* 47 */       synchronized (this.subscribedAsyncRenderers) {
/* 48 */         ConcurrentTaskManager.runBlocking(content -> this.subscribedAsyncRenderers.forEach(()));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\engine\RenderEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */