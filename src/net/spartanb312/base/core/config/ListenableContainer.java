/*    */ package net.spartanb312.base.core.config;
/*    */ 
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import net.spartanb312.base.core.concurrent.task.Task;
/*    */ import net.spartanb312.base.core.event.decentralization.DecentralizedEvent;
/*    */ import net.spartanb312.base.core.event.decentralization.EventData;
/*    */ import net.spartanb312.base.core.event.decentralization.Listenable;
/*    */ import net.spartanb312.base.core.event.decentralization.ListenableImpl;
/*    */ 
/*    */ public class ListenableContainer
/*    */   extends ConfigContainer
/*    */   implements Listenable {
/* 13 */   private final ConcurrentHashMap<DecentralizedEvent<? extends EventData>, Task<? extends EventData>> listenerMap = new ConcurrentHashMap<>();
/*    */ 
/*    */   
/*    */   public ConcurrentHashMap<DecentralizedEvent<? extends EventData>, Task<? extends EventData>> listenerMap() {
/* 17 */     return this.listenerMap;
/*    */   }
/*    */ 
/*    */   
/*    */   public void subscribe() {
/* 22 */     this.listenerMap.forEach(DecentralizedEvent::register);
/*    */   }
/*    */ 
/*    */   
/*    */   public void unsubscribe() {
/* 27 */     this.listenerMap.forEach(DecentralizedEvent::unregister);
/*    */   }
/*    */   
/*    */   public <T extends EventData> void listener(Class<? extends DecentralizedEvent<T>> eventClass, Task<T> action) {
/* 31 */     ListenableImpl.listener(this, eventClass, action);
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\config\ListenableContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */