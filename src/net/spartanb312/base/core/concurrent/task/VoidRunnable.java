/*    */ package net.spartanb312.base.core.concurrent.task;
/*    */ 
/*    */ import net.spartanb312.base.core.concurrent.utils.Syncer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VoidRunnable
/*    */   extends Syncable
/*    */ {
/*    */   private final VoidTask task;
/*    */   private final Syncer syncer;
/*    */   
/*    */   public VoidRunnable(VoidTask task) {
/* 14 */     this.task = task;
/* 15 */     this.syncer = null;
/*    */   }
/*    */   
/*    */   public VoidRunnable(VoidTask task, Syncer syncer) {
/* 19 */     this.task = task;
/* 20 */     this.syncer = syncer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 26 */       this.task.invoke();
/* 27 */     } catch (Exception exception) {
/* 28 */       exception.printStackTrace();
/*    */     } 
/* 30 */     if (this.syncer != null) this.syncer.countDown(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\concurrent\task\VoidRunnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */