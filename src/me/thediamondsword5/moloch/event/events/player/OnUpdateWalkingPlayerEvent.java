/*    */ package me.thediamondsword5.moloch.event.events.player;
/*    */ 
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public class OnUpdateWalkingPlayerEvent extends EventCenter {
/*    */   public float yaw;
/*    */   public float pitch;
/*    */   
/*    */   public OnUpdateWalkingPlayerEvent(float yaw, float pitch) {
/* 10 */     this.yaw = yaw;
/* 11 */     this.pitch = pitch;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\event\events\player\OnUpdateWalkingPlayerEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */