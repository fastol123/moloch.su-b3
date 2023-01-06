/*    */ package net.spartanb312.base.event.events.client;
/*    */ 
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public class ChatEvent
/*    */   extends EventCenter {
/*    */   protected String message;
/*    */   
/*    */   public ChatEvent(String message) {
/* 10 */     this.message = message;
/*    */   }
/*    */   
/*    */   public void setMessage(String message) {
/* 14 */     this.message = message;
/*    */   }
/*    */   
/*    */   public final String getMessage() {
/* 18 */     return this.message;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\event\events\client\ChatEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */