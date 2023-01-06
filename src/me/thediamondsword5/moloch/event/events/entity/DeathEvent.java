/*    */ package me.thediamondsword5.moloch.event.events.entity;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public class DeathEvent extends EventCenter {
/*    */   public Entity entity;
/*    */   
/*    */   public DeathEvent(Entity entity) {
/* 10 */     this.entity = entity;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\event\events\entity\DeathEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */