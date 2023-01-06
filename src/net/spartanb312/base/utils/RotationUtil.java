/*     */ package net.spartanb312.base.utils;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.spartanb312.base.BaseCenter;
/*     */ import net.spartanb312.base.core.config.ListenableContainer;
/*     */ import net.spartanb312.base.core.event.Listener;
/*     */ import net.spartanb312.base.event.events.network.PacketEvent;
/*     */ import net.spartanb312.base.event.events.render.RenderModelEvent;
/*     */ import net.spartanb312.base.mixin.mixins.accessor.AccessorCPacketPlayer;
/*     */ 
/*     */ public class RotationUtil extends ListenableContainer {
/*  19 */   public static Minecraft mc = Minecraft.func_71410_x();
/*     */   public static transient float yaw;
/*     */   public static transient float pitch;
/*     */   public static transient float renderPitch;
/*     */   public static transient boolean shouldSpoofPacket;
/*     */   public static float newYaw;
/*     */   public static transient float prevYaw;
/*     */   public static transient float prevPitch;
/*  27 */   public static float theInt = 0.0F;
/*     */   public static boolean flag = false;
/*  29 */   private static final Timer rotateTimer = new Timer();
/*     */   
/*     */   public static void init() {
/*  32 */     BaseCenter.EVENT_BUS.register(new RotationUtil());
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void onPacketSend(PacketEvent.Send event) {
/*  37 */     if (mc.field_71439_g == null)
/*  38 */       return;  if (event.packet instanceof CPacketPlayer) {
/*  39 */       CPacketPlayer packet = (CPacketPlayer)event.packet;
/*  40 */       if (shouldSpoofPacket) {
/*  41 */         ((AccessorCPacketPlayer)packet).setYaw(yaw);
/*  42 */         ((AccessorCPacketPlayer)packet).setPitch(pitch);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void renderModelRotation(RenderModelEvent event) {
/*  49 */     if (shouldSpoofPacket) {
/*  50 */       event.rotating = true;
/*  51 */       event.pitch = renderPitch;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static float[] getRotations(Vec3d from, Vec3d to) {
/*  56 */     double difX = to.field_72450_a - from.field_72450_a;
/*  57 */     double difY = (to.field_72448_b - from.field_72448_b) * -1.0D;
/*  58 */     double difZ = to.field_72449_c - from.field_72449_c;
/*  59 */     double dist = MathHelper.func_76133_a(difX * difX + difZ * difZ);
/*  60 */     return new float[] { (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0D), (float)MathHelper.func_76138_g(Math.toDegrees(Math.atan2(difY, dist))) };
/*     */   }
/*     */   
/*     */   public static float[] getRotationsBlock(BlockPos block, EnumFacing face, boolean Legit) {
/*  64 */     double x = block.func_177958_n() + 0.5D - mc.field_71439_g.field_70165_t + face.func_82601_c() / 2.0D;
/*  65 */     double z = block.func_177952_p() + 0.5D - mc.field_71439_g.field_70161_v + face.func_82599_e() / 2.0D;
/*  66 */     double y = block.func_177956_o() + 0.5D;
/*     */     
/*  68 */     if (Legit) {
/*  69 */       y += 0.5D;
/*     */     }
/*  71 */     double d1 = mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e() - y;
/*  72 */     double d3 = MathHelper.func_76133_a(x * x + z * z);
/*  73 */     float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
/*  74 */     float pitch = (float)(Math.atan2(d1, d3) * 180.0D / Math.PI);
/*     */     
/*  76 */     if (yaw < 0.0F) {
/*  77 */       yaw += 360.0F;
/*     */     }
/*  79 */     return new float[] { yaw, pitch };
/*     */   }
/*     */   
/*     */   public static float calcNormalizedAngleDiff(float angle1, float angle2) {
/*  83 */     float angleDiff1 = Math.abs(angle1 - angle2);
/*  84 */     if (angleDiff1 < 180.0F) return angleDiff1; 
/*  85 */     return 360.0F - Math.max(angle1, angle2) + Math.min(angle1, angle2);
/*     */   }
/*     */   
/*     */   private static int calcYawDelta(float prevYaw, float newYaw) {
/*  89 */     if (newYaw - prevYaw >= 180.0F || (newYaw - prevYaw < 0.0F && newYaw - prevYaw >= -180.0F)) return -1; 
/*  90 */     if (newYaw - prevYaw < -180.0F || (newYaw - prevYaw >= 0.0F && newYaw - prevYaw < 180.0F)) return 1; 
/*  91 */     return -69;
/*     */   }
/*     */   
/*     */   public static float normalizeAngle(float angle) {
/*  95 */     float angle2 = angle;
/*  96 */     if (angle2 < 0.0F) angle2 += 360.0F; 
/*  97 */     angle2 %= 360.0F;
/*     */     
/*  99 */     if (angle2 > 360.0F) {
/* 100 */       angle2 = 360.0F;
/*     */     }
/* 102 */     if (angle2 < 0.0D) {
/* 103 */       angle2 = 0.0F;
/*     */     }
/* 105 */     return angle2;
/*     */   }
/*     */   
/*     */   public static void resetRotation(boolean slowRotate, float degreesPerSecond) {
/* 109 */     if (shouldSpoofPacket) {
/* 110 */       if (slowRotate) {
/*     */         
/* 112 */         float normalizedOriginalYaw = normalizeAngle(mc.field_71439_g.field_70759_as);
/*     */         
/* 114 */         if (normalizedOriginalYaw == 0.0F || normalizedOriginalYaw == 360.0F) {
/* 115 */           normalizedOriginalYaw = 1.0F;
/*     */         }
/* 117 */         if ((newYaw < normalizedOriginalYaw - 2.0F || newYaw > normalizedOriginalYaw + 2.0F) && rotateTimer.passed(1.0D)) {
/* 118 */           newYaw += degreesPerSecond / 1000.0F * calcYawDelta(newYaw, normalizedOriginalYaw);
/* 119 */           newYaw = normalizeAngle(newYaw);
/* 120 */           rotateTimer.reset();
/*     */         }
/* 122 */         else if (newYaw >= normalizedOriginalYaw - 2.0F && newYaw <= normalizedOriginalYaw + 2.0F) {
/* 123 */           shouldSpoofPacket = false;
/* 124 */           flag = false;
/*     */         } 
/*     */         
/* 127 */         setYawAndPitch(newYaw, mc.field_71439_g.field_70125_A, mc.field_71439_g.field_70125_A);
/*     */       }
/*     */       else {
/*     */         
/* 131 */         yaw = mc.field_71439_g.field_70759_as;
/* 132 */         pitch = mc.field_71439_g.field_70125_A;
/* 133 */         flag = false;
/* 134 */         shouldSpoofPacket = false;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setYawAndPitch(float yaw1, float pitch1, float renderPitch1) {
/* 140 */     yaw = yaw1;
/* 141 */     pitch = pitch1;
/* 142 */     renderPitch = renderPitch1;
/* 143 */     mc.field_71439_g.field_70761_aq = yaw1;
/*     */   }
/*     */   
/*     */   public static void lookAtTarget(Entity entity, boolean slowRotate, float degreesPerSecond) {
/* 147 */     float[] v = getRotations(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), entity.func_174791_d());
/* 148 */     float[] v2 = getRotations(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), entity.func_174791_d().func_72441_c(0.0D, -0.5D, 0.0D));
/*     */     
/* 150 */     shouldSpoofPacket = true;
/*     */     
/* 152 */     if (slowRotate) {
/* 153 */       float normalizedYaw = normalizeAngle(v[0]);
/*     */       
/* 155 */       if (normalizedYaw == 0.0F || normalizedYaw == 360.0F) {
/* 156 */         normalizedYaw = 1.0F;
/*     */       }
/* 158 */       if (!flag) {
/* 159 */         newYaw = normalizeAngle(mc.field_71439_g.field_70759_as);
/* 160 */         flag = true;
/*     */       } 
/*     */       
/* 163 */       if ((newYaw < normalizedYaw - 1.0F || newYaw > normalizedYaw + 1.0F) && rotateTimer.passed(1.0D)) {
/* 164 */         newYaw += degreesPerSecond / 1000.0F * calcYawDelta(newYaw, normalizedYaw);
/* 165 */         newYaw = normalizeAngle(newYaw);
/* 166 */         rotateTimer.reset();
/*     */       } 
/*     */       
/* 169 */       setYawAndPitch(newYaw, v[1], v2[1]);
/*     */     } else {
/* 171 */       setYawAndPitch(v[0], v[1], v2[1]);
/*     */     } 
/*     */   }
/*     */   public static void lookAtVec3d(Vec3d vec3d) {
/* 175 */     float[] v = getRotations(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), vec3d);
/*     */     
/* 177 */     setYawAndPitchBlock(v[0], v[1]);
/*     */   }
/*     */   
/*     */   public static void resetRotationBlock() {
/* 181 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(prevYaw, prevPitch, mc.field_71439_g.field_70122_E));
/*     */   }
/*     */   
/*     */   public static void setYawAndPitchBlock(float yaw1, float pitch1) {
/* 185 */     prevYaw = mc.field_71439_g.field_70177_z;
/* 186 */     prevPitch = mc.field_71439_g.field_70125_A;
/* 187 */     mc.field_71439_g.field_70761_aq = yaw1;
/* 188 */     renderPitch = pitch1;
/* 189 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(yaw1, pitch1, mc.field_71439_g.field_70122_E));
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\RotationUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */