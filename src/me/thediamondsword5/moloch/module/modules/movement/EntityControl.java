/*    */ package me.thediamondsword5.moloch.module.modules.movement;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.entity.EntityControlEvent;
/*    */ import me.thediamondsword5.moloch.event.events.player.TravelEvent;
/*    */ import net.minecraft.network.play.client.CPacketUseEntity;
/*    */ import net.minecraft.world.World;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.event.Listener;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.event.events.network.PacketEvent;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ import net.spartanb312.base.utils.EntityUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "EntityControl", category = Category.MOVEMENT, description = "Instantly ride any ridable animal and control it's movements easier")
/*    */ public class EntityControl
/*    */   extends Module
/*    */ {
/* 24 */   Setting<Boolean> mountBypass = setting("MountBypass", false).des("Allows you to ride chested animals on servers that don't allow it (maybe)");
/* 25 */   Setting<Boolean> speedModify = setting("SpeedModify", true).des("Allows you to go faster than entity");
/* 26 */   Setting<Float> speed = setting("Speed", 2.0F, 0.0F, 10.0F).des("Speed of ridden entities").whenTrue(this.speedModify);
/*    */ 
/*    */   
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 30 */     if (((Boolean)this.mountBypass.getValue()).booleanValue() && event.getPacket() instanceof CPacketUseEntity && ((CPacketUseEntity)event.getPacket()).func_149565_c() != CPacketUseEntity.Action.INTERACT_AT && ((CPacketUseEntity)event
/* 31 */       .getPacket()).func_149564_a((World)mc.field_71441_e) instanceof net.minecraft.entity.passive.AbstractChestHorse) {
/* 32 */       event.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Listener
/*    */   public void entityControlSet(EntityControlEvent event) {
/* 38 */     event.cancel();
/*    */   }
/*    */   
/*    */   @Listener
/*    */   public void onTravel(TravelEvent event) {
/* 43 */     if (mc.field_71441_e != null && mc.field_71439_g != null && mc.field_71439_g.field_184239_as != null && (
/* 44 */       mc.field_71439_g.field_184239_as instanceof net.minecraft.entity.passive.EntityPig || mc.field_71439_g.field_184239_as instanceof net.minecraft.entity.passive.AbstractHorse || mc.field_71439_g.field_184239_as instanceof net.minecraft.entity.item.EntityBoat) && mc.field_71439_g.field_184239_as
/* 45 */       .func_184179_bs() == mc.field_71439_g) {
/*    */       
/* 47 */       double motionX = -Math.sin(EntityUtil.getMovementYaw()) * ((Float)this.speed.getValue()).floatValue();
/* 48 */       double motionZ = Math.cos(EntityUtil.getMovementYaw()) * ((Float)this.speed.getValue()).floatValue();
/*    */       
/* 50 */       if ((mc.field_71439_g.field_71158_b.field_192832_b != 0.0F || mc.field_71439_g.field_71158_b.field_78902_a != 0.0F) && mc.field_71441_e
/* 51 */         .func_72964_e((int)(mc.field_71439_g.field_184239_as.field_70165_t + motionX), (int)(mc.field_71439_g.field_184239_as.field_70161_v + motionZ)) instanceof net.minecraft.world.chunk.EmptyChunk) {
/* 52 */         mc.field_71439_g.field_184239_as.field_70159_w = motionX;
/* 53 */         mc.field_71439_g.field_184239_as.field_70179_y = motionZ;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\movement\EntityControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */