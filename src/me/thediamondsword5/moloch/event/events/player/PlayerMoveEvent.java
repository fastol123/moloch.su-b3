/*    */ package me.thediamondsword5.moloch.event.events.player;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public class PlayerMoveEvent extends EventCenter {
/*    */   public double motionX;
/*    */   public double motionY;
/*    */   public double motionZ;
/*    */   
/*    */   public PlayerMoveEvent(EntityPlayer player) {
/* 12 */     this.motionX = player.field_70159_w;
/* 13 */     this.motionY = player.field_70181_x;
/* 14 */     this.motionZ = player.field_70179_y;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\event\events\player\PlayerMoveEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */