/*    */ package net.spartanb312.base.core.concurrent.repeat;
/*    */ 
/*    */ import net.spartanb312.base.core.concurrent.task.VoidTask;
/*    */ 
/*    */ public class DelayUnit
/*    */ {
/*    */   private final VoidTask task;
/*    */   private final long startTime;
/*    */   
/*    */   public DelayUnit(long startTime, VoidTask task) {
/* 11 */     this.task = task;
/* 12 */     this.startTime = startTime;
/*    */   }
/*    */   
/*    */   public boolean invoke() {
/* 16 */     if (System.currentTimeMillis() >= this.startTime) {
/* 17 */       this.task.invoke();
/* 18 */       return true;
/*    */     } 
/* 20 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\concurrent\repeat\DelayUnit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */