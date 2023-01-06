/*    */ package net.spartanb312.base.utils;
/*    */ 
/*    */ import java.time.Duration;
/*    */ import java.time.Instant;
/*    */ 
/*    */ public final class Timer
/*    */ {
/*    */   public static Instant now() {
/*  9 */     return Instant.now();
/*    */   }
/*    */   
/*    */   private long time;
/*    */   
/*    */   public Timer() {
/* 15 */     this.time = -1L;
/*    */   }
/*    */   
/*    */   public void resetTimeSkipTo(int ms) {
/* 19 */     this.time = System.currentTimeMillis() + ms;
/*    */   }
/*    */   
/*    */   public boolean passed(double ms) {
/* 23 */     return ((System.currentTimeMillis() - this.time) >= ms);
/*    */   }
/*    */   
/*    */   public boolean passedSec(double second) {
/* 27 */     return ((System.currentTimeMillis() - this.time) >= second * 1000.0D);
/*    */   }
/*    */   
/*    */   public boolean passedTick(double tick) {
/* 31 */     return ((System.currentTimeMillis() - this.time) >= tick * 50.0D);
/*    */   }
/*    */   
/*    */   public boolean passedMin(double minutes) {
/* 35 */     return ((System.currentTimeMillis() - this.time) >= minutes * 1000.0D * 60.0D);
/*    */   }
/*    */   
/*    */   public long getPassed() {
/* 39 */     return System.currentTimeMillis() - this.time;
/*    */   }
/*    */ 
/*    */   
/*    */   public static long getSecondsPassed(Instant start, Instant current) {
/* 44 */     return Duration.between(start, current).getSeconds();
/*    */   }
/*    */   
/*    */   public static boolean haveSecondsPassed(Instant start, Instant current, long seconds) {
/* 48 */     return (getSecondsPassed(start, current) >= seconds);
/*    */   }
/*    */   
/*    */   public static long getMilliSecondsPassed(Instant start, Instant current) {
/* 52 */     return Duration.between(start, current).toMillis();
/*    */   }
/*    */   
/*    */   public static boolean haveMilliSecondsPassed(Instant start, Instant current, long milliseconds) {
/* 56 */     return (getMilliSecondsPassed(start, current) >= milliseconds);
/*    */   }
/*    */   
/*    */   public Timer reset() {
/* 60 */     this.time = System.currentTimeMillis();
/* 61 */     return this;
/*    */   }
/*    */   
/*    */   public Timer restart() {
/* 65 */     this.time = -1L;
/* 66 */     return this;
/*    */   }
/*    */   
/*    */   public long getTime() {
/* 70 */     return this.time;
/*    */   }
/*    */   
/*    */   public void setTime(long time) {
/* 74 */     this.time = time;
/*    */   }
/*    */   
/*    */   public long hasPassed() {
/* 78 */     return System.currentTimeMillis() - this.time;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */