/*    */ package net.spartanb312.base.core.concurrent.repeat;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ import net.spartanb312.base.core.concurrent.ConcurrentTaskManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RepeatManager
/*    */ {
/*    */   public static RepeatManager instance;
/*    */   
/*    */   public static void init() {
/* 18 */     if (instance == null) instance = new RepeatManager(); 
/*    */   }
/*    */   
/* 21 */   public final List<RepeatUnit> repeatUnits = new CopyOnWriteArrayList<>();
/* 22 */   public final List<DelayUnit> delayUnits = new CopyOnWriteArrayList<>();
/*    */   
/*    */   public static void update() {
/* 25 */     instance.delayUnits.removeIf(DelayUnit::invoke);
/* 26 */     instance.repeatUnits.removeIf(RepeatUnit::isDead);
/* 27 */     instance.repeatUnits.forEach(it -> {
/*    */           if (it.shouldRun())
/*    */             ConcurrentTaskManager.launch(it::run); 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\concurrent\repeat\RepeatManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */