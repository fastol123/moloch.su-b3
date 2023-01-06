/*    */ package net.spartanb312.base.core.concurrent.blocking;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import net.spartanb312.base.core.concurrent.ConcurrentTaskManager;
/*    */ import net.spartanb312.base.core.concurrent.task.VoidTask;
/*    */ import net.spartanb312.base.core.concurrent.utils.Syncer;
/*    */ 
/*    */ 
/*    */ public class BlockingContent
/*    */ {
/* 13 */   private final List<BlockingUnit> tasks = new ArrayList<>();
/* 14 */   private final AtomicInteger finished = new AtomicInteger(0);
/*    */   private Syncer syncer;
/*    */   
/*    */   public void launch(VoidTask task) {
/* 18 */     BlockingUnit unit = new BlockingUnit(task, this);
/* 19 */     ConcurrentTaskManager.instance.executor.execute(unit);
/* 20 */     this.tasks.add(unit);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void count() {
/* 26 */     synchronized (this.finished) {
/* 27 */       this.finished.incrementAndGet();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void await() {
/* 32 */     if (this.tasks.size() == 0)
/*    */       return; 
/* 34 */     synchronized (this.finished) {
/* 35 */       this.syncer = new Syncer(this.tasks.size() - this.finished.get());
/*    */     } 
/* 37 */     this.syncer.await();
/*    */   }
/*    */   
/*    */   public synchronized void countDown() {
/* 41 */     if (this.syncer != null) this.syncer.countDown(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\concurrent\blocking\BlockingContent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */