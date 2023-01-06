/*    */ package me.thediamondsword5.moloch.event.events.entity;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public class TurnEvent extends EventCenter {
/*    */   public Entity entity;
/*    */   public float yaw;
/*    */   public float pitch;
/*    */   
/*    */   public TurnEvent(Entity entity, float yaw, float pitch) {
/* 12 */     this.entity = entity;
/* 13 */     this.yaw = yaw;
/* 14 */     this.pitch = pitch;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\event\events\entity\TurnEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */