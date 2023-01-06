/*    */ package net.spartanb312.base.core.event;
/*    */ 
/*    */ import net.spartanb312.base.core.concurrent.task.ObjectTask;
/*    */ 
/*    */ public class SubscribedUnit
/*    */ {
/*    */   private final Object owner;
/*    */   private final Class<?> eventClass;
/*    */   private final ObjectTask task;
/*    */   private final int priority;
/*    */   private final boolean parallel;
/*    */   
/*    */   public SubscribedUnit(Object owner, Class<?> eventClass, ObjectTask task, int priority, boolean parallel) {
/* 14 */     this.owner = owner;
/* 15 */     this.eventClass = eventClass;
/* 16 */     this.task = task;
/* 17 */     this.priority = priority;
/* 18 */     this.parallel = parallel;
/*    */   }
/*    */   
/*    */   public ObjectTask getTask() {
/* 22 */     return this.task;
/*    */   }
/*    */   
/*    */   public Class<?> getEventClass() {
/* 26 */     return this.eventClass;
/*    */   }
/*    */   
/*    */   public Object getOwner() {
/* 30 */     return this.owner;
/*    */   }
/*    */   
/*    */   public int getPriority() {
/* 34 */     return this.priority;
/*    */   }
/*    */   
/*    */   public boolean isParallel() {
/* 38 */     return this.parallel;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\event\SubscribedUnit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */