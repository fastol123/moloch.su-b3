/*    */ package net.spartanb312.base.core.concurrent.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Timer
/*    */ {
/* 11 */   private long time = -1L;
/*    */ 
/*    */   
/*    */   public boolean passed(int ms) {
/* 15 */     return (System.currentTimeMillis() - this.time >= ms);
/*    */   }
/*    */   
/*    */   public void reset() {
/* 19 */     this.time = System.currentTimeMillis();
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\concurren\\utils\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */