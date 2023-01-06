/*    */ package me.thediamondsword5.moloch.event.events.player;
/*    */ 
/*    */ import net.minecraft.util.MovementInput;
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public class PlayerUpdateMoveEvent extends EventCenter {
/*    */   public MovementInput movementInput;
/*    */   
/*    */   public PlayerUpdateMoveEvent(MovementInput movementInput) {
/* 10 */     this.movementInput = movementInput;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\event\events\player\PlayerUpdateMoveEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */