/*    */ package net.spartanb312.base.core.concurrent.task;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.spartanb312.base.core.concurrent.utils.Syncer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TaskRunnable<T>
/*    */   extends Syncable
/*    */ {
/*    */   private final MultiParameterTask<T> multiParameterTask;
/* 15 */   private final List<T> parameters = new ArrayList<>();
/*    */   
/*    */   private final Task<T> task;
/* 18 */   private T eventParameter = null;
/*    */   
/*    */   public TaskRunnable(Task<T> task) {
/* 21 */     this.task = task;
/* 22 */     this.multiParameterTask = null;
/* 23 */     this.syncer = null;
/*    */   }
/*    */   
/*    */   public TaskRunnable(Task<T> task, Syncer syncer) {
/* 27 */     this.task = task;
/* 28 */     this.syncer = syncer;
/* 29 */     this.multiParameterTask = null;
/* 30 */     this.syncer = null;
/*    */   }
/*    */   
/*    */   public TaskRunnable(T eventParameter, Task<T> task) {
/* 34 */     this.task = task;
/* 35 */     this.eventParameter = eventParameter;
/* 36 */     this.multiParameterTask = null;
/* 37 */     this.syncer = null;
/*    */   }
/*    */   
/*    */   public TaskRunnable(Task<T> task, Syncer syncer, T eventParameter) {
/* 41 */     this.task = task;
/* 42 */     this.eventParameter = eventParameter;
/* 43 */     this.syncer = syncer;
/* 44 */     this.multiParameterTask = null;
/* 45 */     this.syncer = null;
/*    */   }
/*    */   
/*    */   public TaskRunnable(MultiParameterTask<T> task) {
/* 49 */     this.task = null;
/* 50 */     this.multiParameterTask = task;
/* 51 */     this.syncer = null;
/*    */   }
/*    */   
/*    */   public TaskRunnable(MultiParameterTask<T> task, Syncer syncer) {
/* 55 */     this.task = null;
/* 56 */     this.multiParameterTask = task;
/* 57 */     this.syncer = syncer;
/*    */   }
/*    */   
/*    */   public TaskRunnable(T parameter, MultiParameterTask<T> task) {
/* 61 */     this.task = null;
/* 62 */     this.multiParameterTask = task;
/* 63 */     this.syncer = null;
/* 64 */     this.parameters.add(parameter);
/*    */   }
/*    */   
/*    */   public TaskRunnable(MultiParameterTask<T> task, Syncer syncer, T parameter) {
/* 68 */     this.task = null;
/* 69 */     this.multiParameterTask = task;
/* 70 */     this.syncer = syncer;
/* 71 */     this.parameters.add(parameter);
/*    */   }
/*    */   
/*    */   public TaskRunnable(T[] parameters, MultiParameterTask<T> task) {
/* 75 */     this.task = null;
/* 76 */     this.multiParameterTask = task;
/* 77 */     this.syncer = null;
/* 78 */     this.parameters.addAll(Arrays.asList(parameters));
/*    */   }
/*    */   
/*    */   public TaskRunnable(MultiParameterTask<T> task, Syncer syncer, T[] parameters) {
/* 82 */     this.task = null;
/* 83 */     this.multiParameterTask = task;
/* 84 */     this.syncer = syncer;
/* 85 */     this.parameters.addAll(Arrays.asList(parameters));
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 91 */       if (this.task != null) { this.task.invoke(this.eventParameter); }
/* 92 */       else if (this.multiParameterTask != null) { this.multiParameterTask.invoke(this.parameters); } 
/* 93 */     } catch (Exception exception) {
/* 94 */       exception.printStackTrace();
/*    */     } 
/* 96 */     if (this.syncer != null) this.syncer.countDown(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\concurrent\task\TaskRunnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */