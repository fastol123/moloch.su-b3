/*     */ package me.thediamondsword5.moloch.module.modules.movement;
/*     */ 
/*     */ import me.thediamondsword5.moloch.event.events.player.GroundedStepEvent;
/*     */ import me.thediamondsword5.moloch.event.events.player.PlayerUpdateMoveEvent;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.spartanb312.base.client.ModuleManager;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.event.Listener;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.EntityUtil;
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "Step", category = Category.MOVEMENT, description = "Allows you go up blocks")
/*     */ public class Step
/*     */   extends Module {
/*  21 */   public Setting<Boolean> vanilla = setting("VanillaMode", true).des("Way to step over blocks");
/*  22 */   Setting<Boolean> entityStep = setting("EntityStep", false).des("Modifies entities' step height");
/*  23 */   Setting<Float> entityStepHeight = setting("EntityStepHeight", 100.0F, 1.0F, 256.0F).des("Max entity step height").whenTrue(this.entityStep);
/*  24 */   Setting<Float> height = setting("Height", 2.0F, 1.0F, 2.5F).des("Max height to be able to step over");
/*  25 */   Setting<Boolean> toggle = setting("Toggle", false).des("Automatically disables module when you've stepped over blocks once");
/*     */   
/*  27 */   private final double[] offsetsOne = new double[] { 0.42D, 0.753D };
/*  28 */   private final double[] offsetsOneAndHalf = new double[] { 0.42D, 0.75D, 1.0D, 1.16D, 1.23D, 1.2D };
/*  29 */   private final double[] offsetsTwo = new double[] { 0.42D, 0.78D, 0.63D, 0.51D, 0.9D, 1.21D, 1.45D, 1.43D };
/*  30 */   private final double[] offsetsTwoAndHalf = new double[] { 0.425D, 0.821D, 0.699D, 0.599D, 1.022D, 1.372D, 1.652D, 1.869D, 2.019D, 1.907D };
/*     */ 
/*     */   
/*     */   public String getModuleInfo() {
/*  34 */     if (((Boolean)this.vanilla.getValue()).booleanValue()) {
/*  35 */       return "Vanilla";
/*     */     }
/*     */     
/*  38 */     return "Packet";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  44 */     this.moduleDisableFlag = true;
/*  45 */     mc.field_71439_g.field_70138_W = 0.6F;
/*  46 */     if (mc.field_71439_g.field_184239_as instanceof net.minecraft.entity.passive.AbstractHorse || mc.field_71439_g.field_184239_as instanceof net.minecraft.entity.passive.EntityPig) { mc.field_71439_g.field_184239_as.field_70138_W = 1.0F; }
/*  47 */     else if (mc.field_71439_g.field_184239_as != null) { mc.field_71439_g.field_184239_as.field_70138_W = 0.0F; }
/*     */   
/*     */   }
/*     */   
/*     */   public void onRenderTick() {
/*  52 */     if (!((Boolean)this.vanilla.getValue()).booleanValue() && EntityUtil.canStep() && mc.field_71439_g.field_70122_E) {
/*  53 */       packetStep();
/*     */     }
/*     */     
/*  56 */     if (((Boolean)this.entityStep.getValue()).booleanValue() && mc.field_71439_g.field_184239_as != null) {
/*  57 */       mc.field_71439_g.field_184239_as.field_70138_W = ((Float)this.entityStepHeight.getValue()).floatValue();
/*     */     }
/*     */     
/*  60 */     if ((((Boolean)this.vanilla.getValue()).booleanValue() || ((Boolean)this.entityStep.getValue()).booleanValue()) && ((Boolean)this.toggle.getValue()).booleanValue() && mc.field_71439_g.field_70163_u - mc.field_71439_g.field_70137_T >= 1.0D) {
/*  61 */       toggle();
/*     */     }
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void onUpdateMove(PlayerUpdateMoveEvent event) {
/*  67 */     if (((Boolean)this.vanilla.getValue()).booleanValue() && EntityUtil.canStep()) {
/*  68 */       mc.field_71439_g.field_70138_W = ((Float)this.height.getValue()).floatValue();
/*     */     } else {
/*     */       
/*  71 */       mc.field_71439_g.field_70138_W = 0.6F;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void onGroundedStep(GroundedStepEvent event) {
/*  77 */     if (mc.field_71439_g.field_184239_as instanceof net.minecraft.entity.passive.AbstractHorse || mc.field_71439_g.field_184239_as instanceof net.minecraft.entity.passive.EntityPig) { mc.field_71439_g.field_184239_as.field_70138_W = ((Boolean)this.entityStep.getValue()).booleanValue() ? ((Float)this.entityStepHeight.getValue()).floatValue() : 1.0F; }
/*  78 */     else if (mc.field_71439_g.field_184239_as != null) { mc.field_71439_g.field_184239_as.field_70138_W = ((Boolean)this.entityStep.getValue()).booleanValue() ? ((Float)this.entityStepHeight.getValue()).floatValue() : 0.0F; }
/*     */   
/*     */   }
/*     */   private void packetStep() {
/*  82 */     double[] extension = extend();
/*  83 */     float steppableHeight = 0.0F;
/*  84 */     float[] offsets = { 2.5F, 2.0F, 1.5F, 1.0F };
/*     */     
/*  86 */     for (float offset : offsets) {
/*  87 */       if (mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(extension[0], offset + 0.1D, extension[1])).isEmpty() && 
/*  88 */         !mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(extension[0], offset - 0.1D, extension[1])).isEmpty()) {
/*  89 */         steppableHeight = offset;
/*     */       }
/*     */     } 
/*     */     
/*  93 */     for (float offset : offsets) {
/*  94 */       if (((Float)this.height.getValue()).floatValue() >= offset && steppableHeight == offset) {
/*  95 */         if (offset == 1.0F) {
/*  96 */           for (double d : this.offsetsOne) {
/*  97 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + d, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70122_E));
/*     */           }
/*  99 */         } else if (offset == 1.5F) {
/* 100 */           for (double d : this.offsetsOneAndHalf) {
/* 101 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + d, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70122_E));
/*     */           }
/* 103 */         } else if (offset == 2.0F) {
/* 104 */           for (double d : this.offsetsTwo) {
/* 105 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + d, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70122_E));
/*     */           }
/*     */         } else {
/* 108 */           for (double d : this.offsetsTwoAndHalf) {
/* 109 */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + d, mc.field_71439_g.field_70161_v, mc.field_71439_g.field_70122_E));
/*     */           }
/*     */         } 
/*     */         
/* 113 */         mc.field_71439_g.func_70107_b(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + offset, mc.field_71439_g.field_70161_v);
/*     */         
/* 115 */         if (((Boolean)this.toggle.getValue()).booleanValue()) ModuleManager.getModule(Step.class).disable();
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static double[] extend() {
/* 122 */     float forward = mc.field_71439_g.field_71158_b.field_192832_b;
/* 123 */     float side = mc.field_71439_g.field_71158_b.field_78902_a;
/* 124 */     float yaw = mc.field_71439_g.field_70177_z;
/* 125 */     if (forward != 0.0F) {
/* 126 */       if (side > 0.0F) {
/* 127 */         yaw += (forward > 0.0F) ? -45.0F : 45.0F;
/* 128 */       } else if (side < 0.0F) {
/* 129 */         yaw += (forward > 0.0F) ? 45.0F : -45.0F;
/*     */       } 
/*     */       
/* 132 */       if (forward > 0.0F) {
/* 133 */         forward = 1.0F;
/* 134 */       } else if (forward < 0.0F) {
/* 135 */         forward = -1.0F;
/*     */       } 
/*     */     } 
/* 138 */     double sin = Math.sin((yaw + 90.0F) * 0.017453292519943295D);
/* 139 */     double cos = Math.cos((yaw + 90.0F) * 0.017453292519943295D);
/* 140 */     double posX = forward * 0.1D * cos + ((forward != 0.0F) ? 0.0F : side) * 0.1D * sin;
/* 141 */     double posZ = forward * 0.1D * sin - ((forward != 0.0F) ? 0.0F : side) * 0.1D * cos;
/* 142 */     return new double[] { posX, posZ };
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\movement\Step.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */