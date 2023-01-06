/*    */ package net.spartanb312.base.core.event;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ import net.spartanb312.base.core.concurrent.ConcurrentTaskManager;
/*    */ import net.spartanb312.base.core.concurrent.blocking.BlockingContent;
/*    */ import net.spartanb312.base.core.concurrent.task.ObjectTask;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EventManager
/*    */ {
/* 19 */   private final List<SubscribedUnit> registeredUnit = new CopyOnWriteArrayList<>();
/*    */   
/*    */   public void register(Object owner) {
/* 22 */     unregister(owner);
/* 23 */     Method[] methods = owner.getClass().getDeclaredMethods();
/* 24 */     if (methods.length != 0) {
/* 25 */       for (Method method : methods) {
/* 26 */         if (method.isAnnotationPresent((Class)Listener.class)) {
/* 27 */           Listener annotation = method.<Listener>getAnnotation(Listener.class);
/* 28 */           if (method.getParameterCount() == 1) {
/* 29 */             if (!method.isAccessible()) method.setAccessible(true); 
/* 30 */             ObjectTask task = it -> {
/*    */                 try {
/*    */                   method.invoke(owner, new Object[] { it });
/* 33 */                 } catch (Exception e) {
/*    */                   e.printStackTrace();
/*    */                 } 
/*    */               };
/* 37 */             SubscribedUnit unit = new SubscribedUnit(owner, method.getParameterTypes()[0], task, annotation.priority(), annotation.parallel());
/* 38 */             this.registeredUnit.add(unit);
/*    */           } 
/*    */         } 
/*    */       } 
/* 42 */       this.registeredUnit.sort(Comparator.comparing(SubscribedUnit::getPriority));
/* 43 */       Collections.reverse(this.registeredUnit);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void unregister(Object owner) {
/* 48 */     this.registeredUnit.removeIf(it -> it.getOwner().equals(owner));
/*    */   }
/*    */   
/*    */   public void post(Object event) {
/* 52 */     Class<?> eventClass = event.getClass();
/* 53 */     ConcurrentTaskManager.runBlocking(content -> {
/*    */           for (SubscribedUnit unit : this.registeredUnit) {
/*    */             if (eventClass.equals(unit.getEventClass())) {
/*    */               if (unit.isParallel()) {
/*    */                 content.launch(());
/*    */                 continue;
/*    */               } 
/*    */               unit.getTask().invoke(event);
/*    */             } 
/*    */           } 
/*    */         }); } public boolean isRegistered(Object owner) {
/* 64 */     return this.registeredUnit.stream().anyMatch(it -> it.getOwner().equals(owner));
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\event\EventManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */