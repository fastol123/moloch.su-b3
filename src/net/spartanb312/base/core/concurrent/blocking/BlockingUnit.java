/*    */ package net.spartanb312.base.core.concurrent.blocking;
/*    */ 
/*    */ import net.spartanb312.base.core.concurrent.task.VoidTask;
/*    */ 
/*    */ public class BlockingUnit
/*    */   implements Runnable {
/*    */   private final VoidTask task;
/*    */   private final BlockingContent content;
/*    */   
/*    */   public BlockingUnit(VoidTask task, BlockingContent content) {
/* 11 */     this.task = task;
/* 12 */     this.content = content;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 18 */       this.task.invoke();
/* 19 */     } catch (Exception exception) {
/* 20 */       exception.printStackTrace();
/*    */     } 
/* 22 */     this.content.count();
/* 23 */     this.content.countDown();
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\concurrent\blocking\BlockingUnit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */