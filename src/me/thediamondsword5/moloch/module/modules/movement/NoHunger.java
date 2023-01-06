/*    */ package me.thediamondsword5.moloch.module.modules.movement;
/*    */ 
/*    */ import net.minecraft.network.play.client.CPacketEntityAction;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.event.events.network.PacketEvent;
/*    */ import net.spartanb312.base.mixin.mixins.accessor.AccessorCPacketPlayer;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ 
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "NoHunger", category = Category.MOVEMENT, description = "Stop being hungry")
/*    */ public class NoHunger
/*    */   extends Module
/*    */ {
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 18 */     if (mc.field_71439_g != null) {
/* 19 */       if (event.packet instanceof CPacketEntityAction && (((CPacketEntityAction)event.packet).func_180764_b().equals(CPacketEntityAction.Action.START_SPRINTING) || ((CPacketEntityAction)event.packet).func_180764_b().equals(CPacketEntityAction.Action.STOP_SPRINTING))) {
/* 20 */         event.cancel();
/*    */       }
/* 22 */       if (event.packet instanceof net.minecraft.network.play.client.CPacketPlayer)
/* 23 */         ((AccessorCPacketPlayer)event.packet).setOnGround(((mc.field_71439_g.field_70143_R <= 0.0F || mc.field_71442_b.func_181040_m()) && mc.field_71439_g.func_184613_cA())); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\movement\NoHunger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */