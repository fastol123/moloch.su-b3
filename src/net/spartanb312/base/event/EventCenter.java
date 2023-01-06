/*    */ package net.spartanb312.base.event;
/*    */ 
/*    */ public class EventCenter
/*    */ {
/*    */   private volatile boolean cancelled = false;
/*    */   
/*    */   public boolean isCancelled() {
/*  8 */     return this.cancelled;
/*    */   }
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 12 */     this.cancelled = cancelled;
/*    */   }
/*    */   
/*    */   public void cancel() {
/* 16 */     this.cancelled = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\event\EventCenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */