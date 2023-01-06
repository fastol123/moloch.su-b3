/*    */ package me.thediamondsword5.moloch.event.events.render;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public class RenderEntityPreEvent extends EventCenter {
/*    */   public Entity entityIn;
/*    */   
/*    */   public RenderEntityPreEvent(Entity entityIn) {
/* 10 */     this.entityIn = entityIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\event\events\render\RenderEntityPreEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */