/*    */ package net.spartanb312.base.core.event.decentralization;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Modifier;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ import net.spartanb312.base.core.concurrent.task.Task;
/*    */ 
/*    */ public class ListenableImpl
/*    */   implements Listenable
/*    */ {
/* 11 */   private final ConcurrentHashMap<DecentralizedEvent<? extends EventData>, Task<? extends EventData>> listenerMap = new ConcurrentHashMap<>();
/*    */ 
/*    */   
/*    */   public ConcurrentHashMap<DecentralizedEvent<? extends EventData>, Task<? extends EventData>> listenerMap() {
/* 15 */     return this.listenerMap;
/*    */   }
/*    */ 
/*    */   
/*    */   public void subscribe() {
/* 20 */     this.listenerMap.forEach(DecentralizedEvent::register);
/*    */   }
/*    */ 
/*    */   
/*    */   public void unsubscribe() {
/* 25 */     this.listenerMap.forEach(DecentralizedEvent::unregister);
/*    */   }
/*    */   
/*    */   public <T extends EventData> void listener(Class<? extends DecentralizedEvent<T>> eventClass, Task<T> action) {
/* 29 */     listener(this, eventClass, action);
/*    */   }
/*    */   
/*    */   public static <T extends EventData> void listener(Listenable listenable, Class<? extends DecentralizedEvent<T>> eventClass, Task<T> action) {
/*    */     try {
/* 34 */       DecentralizedEvent<? extends EventData> instance = tryGetInstance(eventClass);
/* 35 */       if (instance != null) {
/* 36 */         listenable.listenerMap().put(instance, action);
/*    */         return;
/*    */       } 
/* 39 */       throw new NoSuchFieldException("Can't find instant static field in the DecentralizedEvent class : " + eventClass.getName());
/* 40 */     } catch (Exception e) {
/* 41 */       e.printStackTrace();
/*    */       return;
/*    */     } 
/*    */   }
/*    */   public static <T extends EventData> DecentralizedEvent<? extends EventData> tryGetInstance(Class<? extends DecentralizedEvent<T>> eventClass) {
/*    */     try {
/* 47 */       for (Field field : eventClass.getDeclaredFields()) {
/* 48 */         if (field.getType().equals(eventClass) && Modifier.isStatic(field.getModifiers())) {
/* 49 */           return (DecentralizedEvent<? extends EventData>)field.get(null);
/*    */         }
/*    */       } 
/* 52 */     } catch (IllegalAccessException illegalAccessException) {}
/*    */     
/* 54 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\event\decentralization\ListenableImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */