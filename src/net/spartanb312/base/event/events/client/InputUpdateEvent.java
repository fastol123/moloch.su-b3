/*    */ package net.spartanb312.base.event.events.client;
/*    */ 
/*    */ public final class InputUpdateEvent
/*    */ {
/*    */   private final int key;
/*    */   private final char character;
/*    */   
/*    */   public InputUpdateEvent(int key, char character) {
/*  9 */     this.key = key;
/* 10 */     this.character = character;
/*    */   }
/*    */   
/*    */   public final int getKey() {
/* 14 */     return this.key;
/*    */   }
/*    */   
/*    */   public final char getCharacter() {
/* 18 */     return this.character;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\event\events\client\InputUpdateEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */