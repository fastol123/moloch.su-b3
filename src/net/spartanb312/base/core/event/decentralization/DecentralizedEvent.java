/*    */ package net.spartanb312.base.core.event.decentralization;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ import net.spartanb312.base.core.concurrent.task.Task;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DecentralizedEvent<T extends EventData>
/*    */ {
/* 11 */   private final List<Task<T>> listeners = new CopyOnWriteArrayList<>();
/*    */   
/*    */   public void register(Task<? extends EventData> action) {
/* 14 */     if (this.listeners.stream().anyMatch(it -> it.equals(action)))
/* 15 */       return;  this.listeners.add(action);
/*    */   }
/*    */   
/*    */   public void unregister(Task<? extends EventData> action) {
/* 19 */     this.listeners.removeIf(it -> it.equals(action));
/*    */   }
/*    */   
/*    */   public void post(T data) {
/* 23 */     this.listeners.forEach(it -> it.invoke(data));
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\event\decentralization\DecentralizedEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */