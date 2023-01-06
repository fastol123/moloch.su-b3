/*    */ package me.thediamondsword5.moloch.event.events.render;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public class RenderViewEntityGuiEvent extends EventCenter {
/*    */   public EntityPlayer entityPlayer;
/*    */   
/*    */   public RenderViewEntityGuiEvent(EntityPlayer entityPlayer) {
/* 10 */     this.entityPlayer = entityPlayer;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\event\events\render\RenderViewEntityGuiEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */