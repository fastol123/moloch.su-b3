/*    */ package net.spartanb312.base.core.concurrent.thread;
/*    */ 
/*    */ import net.spartanb312.base.core.concurrent.ConcurrentTaskManager;
/*    */ import net.spartanb312.base.core.concurrent.utils.ThreadUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BackgroundMainThread
/*    */   extends Thread
/*    */ {
/*    */   public void run() {
/*    */     while (true) {
/*    */       try {
/* 16 */         ConcurrentTaskManager.updateBackground();
/* 17 */       } catch (Exception exception) {
/* 18 */         System.out.println("[TaskManager-Background]Running an unsafe task in background main thread!Please check twice!You'd better run an unsafe task by launching a new task or surrounding with try catch instead of running directly in background main thread!");
/*    */ 
/*    */         
/* 21 */         exception.printStackTrace();
/*    */       } 
/* 23 */       ThreadUtil.delay();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\concurrent\thread\BackgroundMainThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */