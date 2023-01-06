/*     */ package net.spartanb312.base.core.concurrent.repeat;
/*     */ 
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.function.IntSupplier;
/*     */ import net.spartanb312.base.core.concurrent.task.Task;
/*     */ import net.spartanb312.base.core.concurrent.task.VoidTask;
/*     */ import net.spartanb312.base.core.concurrent.utils.Timer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RepeatUnit
/*     */ {
/*     */   VoidTask task;
/*  17 */   private final AtomicBoolean runnable = new AtomicBoolean(true);
/*  18 */   private final AtomicBoolean isRunning = new AtomicBoolean(true);
/*  19 */   private final AtomicBoolean isDead = new AtomicBoolean(false);
/*  20 */   public IntSupplier delaySupplier = null;
/*  21 */   public int delay = -1;
/*  22 */   private final Timer timer = new Timer();
/*  23 */   private VoidTask timeOutOperation = null;
/*  24 */   private int times = 0;
/*  25 */   private AfterTimeOutOperation afterTimeOut = AfterTimeOutOperation.NONE;
/*     */   
/*     */   public enum AfterTimeOutOperation {
/*  28 */     NONE,
/*  29 */     SUSPEND,
/*  30 */     STOP;
/*     */   }
/*     */   
/*     */   public RepeatUnit(int delay, VoidTask task) {
/*  34 */     this.task = task;
/*  35 */     this.delay = delay;
/*  36 */     this.times += Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */   public RepeatUnit(int delay, int times, VoidTask task) {
/*  40 */     this.task = task;
/*  41 */     this.delay = delay;
/*  42 */     this.times += times;
/*     */   }
/*     */   
/*     */   public RepeatUnit(IntSupplier delay, VoidTask task) {
/*  46 */     this.task = task;
/*  47 */     this.delaySupplier = delay;
/*  48 */     this.times += Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */   public RepeatUnit(IntSupplier delay, int times, VoidTask task) {
/*  52 */     this.task = task;
/*  53 */     this.delaySupplier = delay;
/*  54 */     this.times += times;
/*     */   }
/*     */   
/*     */   public RepeatUnit(int delay, VoidTask timeOutOperation, VoidTask task) {
/*  58 */     this.task = task;
/*  59 */     this.delay = delay;
/*  60 */     this.timeOutOperation = timeOutOperation;
/*  61 */     this.times += Integer.MAX_VALUE;
/*     */   }
/*     */   
/*     */   public RepeatUnit(int delay, int times, VoidTask timeOutOperation, VoidTask task) {
/*  65 */     this.task = task;
/*  66 */     this.delay = delay;
/*  67 */     this.timeOutOperation = timeOutOperation;
/*  68 */     this.times += times;
/*     */   }
/*     */   
/*     */   public RepeatUnit(IntSupplier delay, int times, VoidTask timeOutOperation, VoidTask task) {
/*  72 */     this.task = task;
/*  73 */     this.delaySupplier = delay;
/*  74 */     this.timeOutOperation = timeOutOperation;
/*  75 */     this.times += times;
/*     */   }
/*     */   
/*     */   public void run() {
/*  79 */     if (!this.runnable.get())
/*  80 */       return;  if (this.isRunning.get() && !this.isDead.get() && this.task != null) {
/*  81 */       if (this.times > 0) {
/*  82 */         this.runnable.set(false);
/*     */         try {
/*  84 */           this.task.invoke();
/*  85 */         } catch (Exception exception) {
/*  86 */           exception.printStackTrace();
/*     */         } 
/*  88 */         this.runnable.set(true);
/*  89 */         if (this.timeOutOperation != null && this.timer.passed(getDelay())) {
/*  90 */           this.timeOutOperation.invoke();
/*  91 */           switch (this.afterTimeOut) {
/*     */             case SUSPEND:
/*  93 */               suspend();
/*     */               break;
/*     */             
/*     */             case STOP:
/*  97 */               stop();
/*     */               break;
/*     */           } 
/*     */         } 
/* 101 */         this.times--;
/*     */       } else {
/* 103 */         stop();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public int getDelay() {
/* 109 */     return (this.delaySupplier != null) ? this.delaySupplier.getAsInt() : this.delay;
/*     */   }
/*     */   
/*     */   public RepeatUnit afterTimeOutOperation(AfterTimeOutOperation atop) {
/* 113 */     this.afterTimeOut = atop;
/* 114 */     return this;
/*     */   }
/*     */   
/*     */   public RepeatUnit timeOut(Task<RepeatUnit> task) {
/* 118 */     this.timeOutOperation = (() -> task.invoke(this));
/*     */ 
/*     */     
/* 121 */     return this;
/*     */   }
/*     */   
/*     */   public RepeatUnit timeOut(AfterTimeOutOperation atop, Task<RepeatUnit> task) {
/* 125 */     this.timeOutOperation = (() -> task.invoke(this));
/*     */ 
/*     */     
/* 128 */     this.afterTimeOut = atop;
/* 129 */     return this;
/*     */   }
/*     */   
/*     */   public boolean shouldRun() {
/* 133 */     if (this.timer.passed(getDelay())) {
/* 134 */       this.timer.reset();
/* 135 */       return true;
/* 136 */     }  return false;
/*     */   }
/*     */   
/*     */   public boolean isRunning() {
/* 140 */     return this.isRunning.get();
/*     */   }
/*     */   
/*     */   public RepeatUnit suspend() {
/* 144 */     this.isRunning.set(false);
/* 145 */     return this;
/*     */   }
/*     */   
/*     */   public void resume() {
/* 149 */     this.isRunning.set(true);
/*     */   }
/*     */   
/*     */   public synchronized void stop() {
/* 153 */     this.isDead.set(true);
/* 154 */     this.task = null;
/* 155 */     this.isRunning.set(false);
/*     */   }
/*     */   
/*     */   public boolean isDead() {
/* 159 */     return this.isDead.get();
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\concurrent\repeat\RepeatUnit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */