/*     */ package net.spartanb312.base.core.concurrent;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.IntSupplier;
/*     */ import net.spartanb312.base.core.concurrent.blocking.BlockingContent;
/*     */ import net.spartanb312.base.core.concurrent.blocking.BlockingTask;
/*     */ import net.spartanb312.base.core.concurrent.repeat.DelayUnit;
/*     */ import net.spartanb312.base.core.concurrent.repeat.RepeatManager;
/*     */ import net.spartanb312.base.core.concurrent.repeat.RepeatUnit;
/*     */ import net.spartanb312.base.core.concurrent.task.MultiParameterTask;
/*     */ import net.spartanb312.base.core.concurrent.task.Task;
/*     */ import net.spartanb312.base.core.concurrent.task.TaskRunnable;
/*     */ import net.spartanb312.base.core.concurrent.task.VoidRunnable;
/*     */ import net.spartanb312.base.core.concurrent.task.VoidTask;
/*     */ import net.spartanb312.base.core.concurrent.thread.BackgroundMainThread;
/*     */ import net.spartanb312.base.core.concurrent.utils.Syncer;
/*     */ 
/*     */ 
/*     */ public class ConcurrentTaskManager
/*     */ {
/*  25 */   public static ConcurrentTaskManager instance = new ConcurrentTaskManager();
/*     */   
/*  27 */   public static final int workingThreads = Runtime.getRuntime().availableProcessors();
/*     */   
/*  29 */   public BackgroundMainThread backgroundMainThread = new BackgroundMainThread();
/*     */   
/*  31 */   public final ThreadPoolExecutor executor = new ThreadPoolExecutor(workingThreads, 2147483647, 1000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
/*     */   
/*     */   ConcurrentTaskManager() {
/*  34 */     RepeatManager.init();
/*  35 */     this.backgroundMainThread.start();
/*     */   }
/*     */   
/*     */   public static long runTiming(VoidTask task) {
/*  39 */     long startTime = System.currentTimeMillis();
/*  40 */     task.invoke();
/*  41 */     return System.currentTimeMillis() - startTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void runBlocking(BlockingTask task) {
/*  46 */     BlockingContent content = new BlockingContent();
/*  47 */     task.invoke(content);
/*  48 */     content.await();
/*     */   }
/*     */   
/*     */   public static void runBlockingTasks(VoidTask... tasks) {
/*  52 */     runBlockingTasks(Arrays.asList(tasks));
/*     */   }
/*     */   
/*     */   public static void runBlockingTasks(List<VoidTask> tasks) {
/*  56 */     Syncer syncer = new Syncer(tasks.size());
/*  57 */     tasks.forEach(it -> instance.executor.execute((Runnable)new VoidRunnable(it, syncer)));
/*  58 */     syncer.await();
/*     */   }
/*     */   
/*     */   public static <T> void runParameterBlocking(List<MultiParameterTask<T>> tasks, T[] parameters) {
/*  62 */     Syncer syncer = new Syncer(tasks.size());
/*  63 */     tasks.forEach(it -> instance.executor.execute((Runnable)new TaskRunnable(it, syncer, parameters)));
/*  64 */     syncer.await();
/*     */   }
/*     */   
/*     */   public static void launch(VoidTask task) {
/*  68 */     instance.executor.execute((Runnable)new VoidRunnable(task));
/*     */   }
/*     */   
/*     */   public static <T> void launch(Syncer syncer, T parameters, Task<T> task) {
/*  72 */     instance.executor.execute((Runnable)new TaskRunnable(task, syncer, parameters));
/*     */   }
/*     */   
/*     */   public static <T> void launch(T parameter, Task<T> task) {
/*  76 */     instance.executor.execute((Runnable)new TaskRunnable(parameter, task));
/*     */   }
/*     */   
/*     */   public static <T> void launch(Syncer syncer, T[] parameters, MultiParameterTask<T> task) {
/*  80 */     instance.executor.execute((Runnable)new TaskRunnable(task, syncer, (Object[])parameters));
/*     */   }
/*     */   
/*     */   public static <T> void launch(T[] parameters, MultiParameterTask<T> task) {
/*  84 */     instance.executor.execute((Runnable)new TaskRunnable((Object[])parameters, task));
/*     */   }
/*     */   
/*     */   public static void launch(Syncer syncer, VoidTask task) {
/*  88 */     instance.executor.execute((Runnable)new VoidRunnable(task, syncer));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addDelayTask(int delay, VoidTask task) {
/*  93 */     RepeatManager.instance.delayUnits.add(new DelayUnit(System.currentTimeMillis() + delay, task));
/*     */   }
/*     */   
/*     */   public static void addDelayTask(DelayUnit delayUnit) {
/*  97 */     RepeatManager.instance.delayUnits.add(delayUnit);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void runRepeat(RepeatUnit unit) {
/* 102 */     registerRepeatUnit(unit);
/*     */   }
/*     */   
/*     */   public static void runRepeat(int delay, VoidTask task) {
/* 106 */     RepeatUnit unit = new RepeatUnit(delay, task);
/* 107 */     registerRepeatUnit(unit);
/*     */   }
/*     */   
/*     */   public static void runRepeat(int delay, int times, VoidTask task) {
/* 111 */     RepeatUnit unit = new RepeatUnit(delay, times, task);
/* 112 */     registerRepeatUnit(unit);
/*     */   }
/*     */   
/*     */   public static void runRepeat(IntSupplier delay, VoidTask task) {
/* 116 */     RepeatUnit unit = new RepeatUnit(delay, task);
/* 117 */     registerRepeatUnit(unit);
/*     */   }
/*     */   
/*     */   public static void runRepeat(IntSupplier delay, int times, VoidTask task) {
/* 121 */     RepeatUnit unit = new RepeatUnit(delay, times, task);
/* 122 */     registerRepeatUnit(unit);
/*     */   }
/*     */   
/*     */   public static void registerRepeatUnit(RepeatUnit repeatUnit) {
/* 126 */     RepeatManager.instance.repeatUnits.add(repeatUnit);
/*     */   }
/*     */   
/*     */   public static void unregisterRepeatUnit(RepeatUnit repeatUnit) {
/* 130 */     RepeatManager.instance.repeatUnits.remove(repeatUnit);
/*     */   }
/*     */   
/*     */   public static void repeat(int times, VoidTask task) {
/* 134 */     for (int i = 0; i < times; i++) {
/* 135 */       task.invoke();
/*     */     }
/*     */   }
/*     */   
/*     */   public void stop() {
/*     */     try {
/* 141 */       this.executor.shutdown();
/* 142 */     } catch (Exception ignore) {
/* 143 */       System.out.println("[TaskManager]TaskManager shut down!");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateBackground() {
/* 149 */     RepeatManager.update();
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\concurrent\ConcurrentTaskManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */