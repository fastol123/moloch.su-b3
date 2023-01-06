/*    */ package net.spartanb312.base.core.concurrent.utils;
/*    */ 
/*    */ import java.util.concurrent.CountDownLatch;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Syncer
/*    */ {
/*    */   private final CountDownLatch latch;
/*    */   
/*    */   public Syncer(int size) {
/* 13 */     this.latch = new CountDownLatch(size);
/*    */   }
/*    */   
/*    */   public Syncer() {
/* 17 */     this.latch = new CountDownLatch(1);
/*    */   }
/*    */   
/*    */   public CountDownLatch getLatch() {
/* 21 */     return this.latch;
/*    */   }
/*    */   
/*    */   public void countDown() {
/* 25 */     this.latch.countDown();
/*    */   }
/*    */   
/*    */   public void await() {
/*    */     try {
/* 30 */       this.latch.await();
/* 31 */     } catch (InterruptedException e) {
/* 32 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\concurren\\utils\Syncer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */