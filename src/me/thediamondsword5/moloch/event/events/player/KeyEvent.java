/*    */ package me.thediamondsword5.moloch.event.events.player;
/*    */ 
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public class KeyEvent extends EventCenter {
/*    */   public boolean info;
/*    */   public boolean pressed;
/*    */   
/*    */   public KeyEvent(boolean info, boolean pressed) {
/* 10 */     this.info = info;
/* 11 */     this.pressed = pressed;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\event\events\player\KeyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */