/*    */ package me.thediamondsword5.moloch.event.events.player;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public class PlayerAttackEvent extends EventCenter {
/*    */   public Entity target;
/*    */   
/*    */   public PlayerAttackEvent(Entity target) {
/* 10 */     this.target = target;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\event\events\player\PlayerAttackEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */