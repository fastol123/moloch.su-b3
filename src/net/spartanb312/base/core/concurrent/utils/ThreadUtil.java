/*    */ package net.spartanb312.base.core.concurrent.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThreadUtil
/*    */ {
/*    */   public static void delay() {
/*    */     try {
/* 13 */       Thread.sleep(1L);
/* 14 */     } catch (InterruptedException e) {
/* 15 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\concurren\\utils\ThreadUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */