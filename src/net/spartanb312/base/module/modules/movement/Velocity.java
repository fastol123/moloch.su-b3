/*    */ package net.spartanb312.base.module.modules.movement;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityFishHook;
/*    */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*    */ import net.minecraft.network.play.server.SPacketEntityVelocity;
/*    */ import net.minecraft.network.play.server.SPacketExplosion;
/*    */ import net.minecraft.world.World;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.event.events.network.PacketEvent;
/*    */ import net.spartanb312.base.mixin.mixins.accessor.AccessorSPacketEntityVelocity;
/*    */ import net.spartanb312.base.mixin.mixins.accessor.AccessorSPacketExplosion;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "Velocity", category = Category.MOVEMENT, description = "Adjust entity velocity")
/*    */ public class Velocity
/*    */   extends Module
/*    */ {
/*    */   public static Velocity instance;
/* 25 */   Setting<Integer> horizontal_vel = setting("Horizontal", 0, 0, 100);
/* 26 */   Setting<Integer> vertical_vel = setting("Vertical", 0, 0, 100);
/* 27 */   Setting<Boolean> explosions = setting("Explosions", true);
/* 28 */   Setting<Boolean> bobbers = setting("Bobbers", true);
/* 29 */   public Setting<Boolean> liquid = setting("LiquidMove", false);
/* 30 */   public Setting<Boolean> pushing = setting("Pushing", true);
/*    */   
/* 32 */   public final Minecraft mc = Minecraft.func_71410_x();
/*    */   
/*    */   public Velocity() {
/* 35 */     instance = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getModuleInfo() {
/* 40 */     return "H: " + this.horizontal_vel.getValue() + "%, V: " + this.vertical_vel.getValue() + "%";
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 45 */     if (this.mc.field_71439_g == null)
/* 46 */       return;  if (event.packet instanceof SPacketEntityStatus && ((Boolean)this.bobbers.getValue()).booleanValue()) {
/* 47 */       SPacketEntityStatus packet = (SPacketEntityStatus)event.packet;
/* 48 */       if (packet.func_149160_c() == 31) {
/* 49 */         Entity entity = packet.func_149161_a((World)this.mc.field_71441_e);
/* 50 */         if (entity instanceof EntityFishHook) {
/* 51 */           EntityFishHook fishHook = (EntityFishHook)entity;
/* 52 */           if (fishHook.field_146043_c == this.mc.field_71439_g) {
/* 53 */             event.cancel();
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/* 58 */     if (event.packet instanceof SPacketEntityVelocity) {
/* 59 */       SPacketEntityVelocity packet = (SPacketEntityVelocity)event.packet;
/* 60 */       if (packet.func_149412_c() == this.mc.field_71439_g.func_145782_y()) {
/* 61 */         if (((Integer)this.horizontal_vel.getValue()).intValue() == 0 && ((Integer)this.vertical_vel.getValue()).intValue() == 0) {
/* 62 */           event.cancel();
/*    */           
/*    */           return;
/*    */         } 
/* 66 */         if (((Integer)this.horizontal_vel.getValue()).intValue() != 100) {
/* 67 */           ((AccessorSPacketEntityVelocity)packet).setMotionX(packet.func_149411_d() / 100 * ((Integer)this.horizontal_vel.getValue()).intValue());
/* 68 */           ((AccessorSPacketEntityVelocity)packet).setMotionZ(packet.func_149409_f() / 100 * ((Integer)this.horizontal_vel.getValue()).intValue());
/*    */         } 
/*    */         
/* 71 */         if (((Integer)this.vertical_vel.getValue()).intValue() != 100) {
/* 72 */           ((AccessorSPacketEntityVelocity)packet).setMotionY(packet.func_149410_e() / 100 * ((Integer)this.vertical_vel.getValue()).intValue());
/*    */         }
/*    */       } 
/*    */     } 
/* 76 */     if (event.packet instanceof SPacketExplosion && ((Boolean)this.explosions.getValue()).booleanValue()) {
/* 77 */       SPacketExplosion packet = (SPacketExplosion)event.packet;
/*    */       
/* 79 */       if (((Integer)this.horizontal_vel.getValue()).intValue() == 0 && ((Integer)this.vertical_vel.getValue()).intValue() == 0) {
/* 80 */         event.cancel();
/*    */         
/*    */         return;
/*    */       } 
/* 84 */       if (((Integer)this.horizontal_vel.getValue()).intValue() != 100) {
/* 85 */         ((AccessorSPacketExplosion)packet).setMotionX(packet.func_149149_c() / 100.0F * ((Integer)this.horizontal_vel.getValue()).intValue());
/* 86 */         ((AccessorSPacketExplosion)packet).setMotionZ(packet.func_149147_e() / 100.0F * ((Integer)this.horizontal_vel.getValue()).intValue());
/*    */       } 
/*    */       
/* 89 */       if (((Integer)this.vertical_vel.getValue()).intValue() != 100)
/* 90 */         ((AccessorSPacketExplosion)packet).setMotionY(packet.func_149144_d() / 100.0F * ((Integer)this.vertical_vel.getValue()).intValue()); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\module\modules\movement\Velocity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */