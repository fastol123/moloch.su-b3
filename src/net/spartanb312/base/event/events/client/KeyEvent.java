/*    */ package net.spartanb312.base.event.events.client;
/*    */ 
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public final class KeyEvent
/*    */   extends EventCenter {
/*    */   private final int key;
/*    */   private final char character;
/*    */   
/*    */   public KeyEvent(int key, char character) {
/* 11 */     this.key = key;
/* 12 */     this.character = character;
/*    */   }
/*    */   
/*    */   public final int getKey() {
/* 16 */     return this.key;
/*    */   }
/*    */   
/*    */   public final char getCharacter() {
/* 20 */     return this.character;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\event\events\client\KeyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */