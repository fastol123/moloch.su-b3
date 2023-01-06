/*     */ package net.spartanb312.base.utils;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import me.thediamondsword5.moloch.utils.BlockUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketAnimation;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.util.CombatRules;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.World;
/*     */ import net.spartanb312.base.mixin.mixins.accessor.AccessorCPacketUseEntity;
/*     */ import net.spartanb312.base.mixin.mixins.accessor.AccessorRenderManager;
/*     */ import net.spartanb312.base.utils.math.Pair;
/*     */ 
/*     */ public class CrystalUtil {
/*  37 */   public static Minecraft mc = Minecraft.func_71410_x();
/*     */   public static double getRange(Vec3d a, double x, double y, double z) {
/*  39 */     double xl = a.field_72450_a - x;
/*  40 */     double yl = a.field_72448_b - y;
/*  41 */     double zl = a.field_72449_c - z;
/*  42 */     return Math.sqrt(xl * xl + yl * yl + zl * zl);
/*     */   }
/*     */   
/*     */   public static boolean isReplaceable(Block block, boolean includeWater) {
/*  46 */     return (block == Blocks.field_150480_ab || block == Blocks.field_150398_cm || block == Blocks.field_150395_bd || block == Blocks.field_150350_a || !includeWater || block == Blocks.field_150355_j || block == Blocks.field_150358_i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void breakCrystal(EntityEnderCrystal entity) {
/*  54 */     CPacketUseEntity packet = new CPacketUseEntity((Entity)entity);
/*  55 */     ((AccessorCPacketUseEntity)packet).setId(((AccessorCPacketUseEntity)packet).getId());
/*  56 */     ((AccessorCPacketUseEntity)packet).setAction(CPacketUseEntity.Action.ATTACK);
/*  57 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)packet);
/*  58 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
/*     */   }
/*     */   
/*     */   public static float calculateDamage(double posX, double posY, double posZ, Entity entity, Vec3d vec) {
/*  62 */     float doubleExplosionSize = 12.0F;
/*  63 */     double distanceSize = getRange(vec, posX, posY, posZ) / doubleExplosionSize;
/*  64 */     Vec3d vec3d = new Vec3d(posX, posY, posZ);
/*     */     
/*  66 */     double blockDensity = entity.field_70170_p.func_72842_a(vec3d, entity.func_174813_aQ());
/*     */     
/*  68 */     double v = (1.0D - distanceSize) * blockDensity;
/*  69 */     float damage = (int)((v * v + v) / 2.0D * 7.0D * doubleExplosionSize + 1.0D);
/*  70 */     double finalValue = 1.0D;
/*     */     
/*  72 */     if (entity instanceof EntityLivingBase)
/*     */     {
/*  74 */       finalValue = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)mc.field_71441_e, null, posX, posY, posZ, 6.0F, false, true));
/*     */     }
/*  76 */     return (float)finalValue;
/*     */   }
/*     */   
/*     */   public static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
/*  80 */     Vec3d offset = new Vec3d(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
/*  81 */     return calculateDamage(posX, posY, posZ, entity, offset);
/*     */   }
/*     */   
/*     */   private static float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
/*  85 */     if (entity instanceof EntityPlayer) {
/*  86 */       EntityPlayer ep = (EntityPlayer)entity;
/*  87 */       DamageSource ds = DamageSource.func_94539_a(explosion);
/*  88 */       damage = CombatRules.func_189427_a(damage, ep.func_70658_aO(), (float)ep.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
/*     */       
/*  90 */       int k = EnchantmentHelper.func_77508_a(ep.func_184193_aE(), ds);
/*  91 */       float f = MathHelper.func_76131_a(k, 0.0F, 20.0F);
/*  92 */       damage *= 1.0F - f / 25.0F;
/*     */       
/*  94 */       if (entity.func_70644_a(MobEffects.field_76429_m)) {
/*  95 */         damage -= damage / 5.0F;
/*     */       }
/*     */       
/*  98 */       damage = Math.max(damage, 0.0F);
/*  99 */       return damage;
/*     */     } 
/* 101 */     damage = CombatRules.func_189427_a(damage, entity.func_70658_aO(), (float)entity.func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
/* 102 */     return damage;
/*     */   }
/*     */   
/*     */   private static float getDamageMultiplied(float damage) {
/* 106 */     int diff = mc.field_71441_e.func_175659_aa().func_151525_a();
/* 107 */     return damage * ((diff == 0) ? 0.0F : ((diff == 2) ? 1.0F : ((diff == 1) ? 0.5F : 1.5F)));
/*     */   }
/*     */   
/*     */   public static EnumFacing enumFacing(BlockPos blockPos) {
/* 111 */     for (EnumFacing enumFacing : EnumFacing.field_82609_l) {
/* 112 */       Vec3d vec3d = new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v);
/* 113 */       Vec3d vec3d2 = new Vec3d((blockPos.func_177958_n() + enumFacing.func_176730_m().func_177958_n()), (blockPos.func_177956_o() + enumFacing.func_176730_m().func_177956_o()), (blockPos.func_177952_p() + enumFacing.func_176730_m().func_177952_p()));
/*     */       RayTraceResult rayTraceBlocks;
/* 115 */       if ((rayTraceBlocks = mc.field_71441_e.func_147447_a(vec3d, vec3d2, false, true, false)) != null && rayTraceBlocks.field_72313_a
/* 116 */         .equals(RayTraceResult.Type.BLOCK) && rayTraceBlocks.func_178782_a().equals(blockPos)) {
/* 117 */         return enumFacing;
/*     */       }
/*     */     } 
/* 120 */     if (blockPos.func_177956_o() > mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e()) {
/* 121 */       return EnumFacing.DOWN;
/*     */     }
/* 123 */     return EnumFacing.UP;
/*     */   }
/*     */   
/*     */   public static boolean isEating() {
/* 127 */     return (mc.field_71439_g != null && (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemFood || mc.field_71439_g.func_184592_cb().func_77973_b() instanceof net.minecraft.item.ItemFood) && mc.field_71439_g.func_184587_cr());
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canSeeBlock(BlockPos p_Pos) {
/* 132 */     return (mc.field_71439_g == null || mc.field_71441_e.func_147447_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d(p_Pos.func_177958_n(), p_Pos.func_177956_o(), p_Pos.func_177952_p()), false, true, false) != null);
/*     */   }
/*     */   
/*     */   public static BlockPos getPlayerPos() {
/* 136 */     return new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
/*     */   }
/*     */   
/*     */   public static double getVecDistance(BlockPos a, double posX, double posY, double posZ) {
/* 140 */     double x1 = a.func_177958_n() - posX;
/* 141 */     double y1 = a.func_177956_o() - posY;
/* 142 */     double z1 = a.func_177952_p() - posZ;
/* 143 */     return Math.sqrt(x1 * x1 + y1 * y1 + z1 * z1);
/*     */   }
/*     */   
/*     */   public static double getVecDistance(BlockPos pos, Entity entity) {
/* 147 */     return getVecDistance(pos, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
/*     */   }
/*     */   
/*     */   public static void glBillboard(float x, float y, float z) {
/* 151 */     float scale = 0.02666667F;
/* 152 */     GlStateManager.func_179137_b(x - ((AccessorRenderManager)mc.func_175598_ae()).getRenderPosX(), y - ((AccessorRenderManager)mc.func_175598_ae()).getRenderPosY(), z - ((AccessorRenderManager)mc
/* 153 */         .func_175598_ae()).getRenderPosZ());
/* 154 */     GlStateManager.func_179114_b(-(mc.func_175598_ae()).field_78735_i, 0.0F, 1.0F, 0.0F);
/* 155 */     GlStateManager.func_179114_b((mc.func_175598_ae()).field_78732_j, (mc.field_71474_y.field_74320_O == 2) ? -1.0F : 1.0F, 0.0F, 0.0F);
/* 156 */     GlStateManager.func_179152_a(-scale, -scale, -scale);
/*     */   }
/*     */   public static boolean isCityable(BlockPos blockPos, EnumFacing relativeFacing, boolean diagonalCheck, boolean oneBlockCrystalMode) {
/*     */     EnumFacing toCheckDirect1, toCheckDirect2;
/* 160 */     if (mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150350_a || mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150357_h || mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_180401_cv) {
/* 161 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     switch (relativeFacing) {
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/* 171 */         toCheckDirect1 = EnumFacing.EAST;
/* 172 */         toCheckDirect2 = EnumFacing.WEST;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 177 */         toCheckDirect1 = EnumFacing.NORTH;
/* 178 */         toCheckDirect2 = EnumFacing.SOUTH;
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 183 */     BlockPos outOne = BlockUtil.extrudeBlock(blockPos, relativeFacing);
/* 184 */     BlockPos outOneDownOne = BlockUtil.extrudeBlock(outOne, EnumFacing.DOWN);
/* 185 */     BlockPos outOneUpOne = BlockUtil.extrudeBlock(outOne, EnumFacing.UP);
/*     */     
/* 187 */     BlockPos diagonalPos1Partial = BlockUtil.extrudeBlock(blockPos, toCheckDirect1);
/* 188 */     BlockPos diagonalPos1 = BlockUtil.extrudeBlock(diagonalPos1Partial, relativeFacing);
/* 189 */     BlockPos diagonalPos1DownOne = BlockUtil.extrudeBlock(diagonalPos1, EnumFacing.DOWN);
/* 190 */     BlockPos diagonalPos1UpOne = BlockUtil.extrudeBlock(diagonalPos1, EnumFacing.UP);
/* 191 */     BlockPos diagonalPos2Partial = BlockUtil.extrudeBlock(blockPos, toCheckDirect2);
/* 192 */     BlockPos diagonalPos2 = BlockUtil.extrudeBlock(diagonalPos2Partial, relativeFacing);
/* 193 */     BlockPos diagonalPos2DownOne = BlockUtil.extrudeBlock(diagonalPos2, EnumFacing.DOWN);
/* 194 */     BlockPos diagonalPos2UpOne = BlockUtil.extrudeBlock(diagonalPos2, EnumFacing.UP);
/*     */     
/* 196 */     if (oneBlockCrystalMode) {
/* 197 */       return ((diagonalCheck && ((mc.field_71441_e.func_180495_p(diagonalPos1Partial).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_180495_p(diagonalPos1).func_177230_c() == Blocks.field_150350_a && (mc.field_71441_e.func_180495_p(diagonalPos1DownOne).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(diagonalPos1DownOne).func_177230_c() == Blocks.field_150357_h)) || (mc.field_71441_e
/* 198 */         .func_180495_p(diagonalPos2Partial).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_180495_p(diagonalPos2).func_177230_c() == Blocks.field_150350_a && (mc.field_71441_e.func_180495_p(diagonalPos2DownOne).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(diagonalPos2DownOne).func_177230_c() == Blocks.field_150357_h)))) || (mc.field_71441_e
/* 199 */         .func_180495_p(outOne).func_177230_c() == Blocks.field_150350_a && (mc.field_71441_e.func_180495_p(outOneDownOne).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(outOneDownOne).func_177230_c() == Blocks.field_150357_h)));
/*     */     }
/*     */ 
/*     */     
/* 203 */     return ((diagonalCheck && ((mc.field_71441_e.func_180495_p(diagonalPos1Partial).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_180495_p(diagonalPos1).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_180495_p(diagonalPos1UpOne).func_177230_c() == Blocks.field_150350_a && (mc.field_71441_e.func_180495_p(diagonalPos1DownOne).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(diagonalPos1DownOne).func_177230_c() == Blocks.field_150357_h)) || (mc.field_71441_e
/* 204 */       .func_180495_p(diagonalPos2Partial).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_180495_p(diagonalPos2).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_180495_p(diagonalPos2UpOne).func_177230_c() == Blocks.field_150350_a && (mc.field_71441_e.func_180495_p(diagonalPos2DownOne).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(diagonalPos2DownOne).func_177230_c() == Blocks.field_150357_h)))) || (mc.field_71441_e
/* 205 */       .func_180495_p(outOne).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_180495_p(outOneUpOne).func_177230_c() == Blocks.field_150350_a && (mc.field_71441_e.func_180495_p(outOneDownOne).func_177230_c() == Blocks.field_150343_Z || mc.field_71441_e.func_180495_p(outOneDownOne).func_177230_c() == Blocks.field_150357_h)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void breakBlockingCrystals(AxisAlignedBB bb, boolean antiSuicideMode, float minHealthRemaining, float maxDamage, boolean rotate) {
/* 210 */     if (antiSuicideMode) {
/* 211 */       if (mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj() - getDmgSelf() < minHealthRemaining) {
/*     */         return;
/*     */       }
/* 214 */     } else if (getDmgSelf() >= maxDamage) {
/*     */       return;
/*     */     } 
/* 217 */     EntityUtil.entitiesListFlag = true;
/* 218 */     for (Entity entity : mc.field_71441_e.func_72872_a(Entity.class, bb)) {
/* 219 */       if (!(entity instanceof EntityEnderCrystal) || 
/* 220 */         !entity.field_70156_m || 
/* 221 */         !entity.func_70089_S())
/*     */         continue; 
/* 223 */       boolean sprinting = mc.field_71439_g.func_70051_ag();
/* 224 */       if (sprinting) {
/* 225 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
/*     */       }
/*     */       
/* 228 */       if (rotate) RotationUtil.lookAtTarget(entity, false, 100.0F); 
/* 229 */       breakCrystal((EntityEnderCrystal)entity);
/*     */       
/* 231 */       if (sprinting) {
/* 232 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING));
/*     */       }
/*     */     } 
/*     */     
/* 236 */     EntityUtil.entitiesListFlag = false;
/*     */   }
/*     */   
/*     */   public static float getDmgSelf() {
/* 240 */     float dmg = 0.0F;
/* 241 */     EntityUtil.entitiesListFlag = true;
/* 242 */     for (Entity entity : EntityUtil.entitiesList()) {
/* 243 */       if (!(entity instanceof EntityEnderCrystal) || mc.field_71439_g.func_70032_d(entity) > 12.0F) {
/*     */         continue;
/*     */       }
/*     */       
/* 247 */       float crystalDmg = calculateDamage(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, (Entity)mc.field_71439_g);
/* 248 */       if (crystalDmg > dmg) {
/* 249 */         dmg = crystalDmg;
/*     */       }
/*     */     } 
/* 252 */     EntityUtil.entitiesListFlag = false;
/*     */     
/* 254 */     return dmg;
/*     */   }
/*     */   
/*     */   public static boolean crystalPlaceable(BlockPos pos, boolean allowWater, boolean oneBlockCrystalMode) {
/* 258 */     if (mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150357_h && mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150343_Z) {
/* 259 */       return false;
/*     */     }
/*     */     
/* 262 */     if (!isReplaceable(mc.field_71441_e.func_180495_p(BlockUtil.extrudeBlock(pos, EnumFacing.UP)).func_177230_c(), allowWater) || 
/* 263 */       !isReplaceable(mc.field_71441_e.func_180495_p(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock(pos, EnumFacing.UP), EnumFacing.UP)).func_177230_c(), allowWater)) {
/* 264 */       return false;
/*     */     }
/*     */     
/* 267 */     EntityUtil.entitiesListFlag = true;
/* 268 */     for (Entity entity : EntityUtil.entitiesList()) {
/* 269 */       if (entity.func_174813_aQ().func_72326_a(new AxisAlignedBB(pos.field_177962_a, pos.field_177960_b + 1.0D, pos.field_177961_c, pos.field_177962_a + 1.0D, pos.field_177960_b + 3.0D, pos.field_177961_c + 1.0D)))
/*     */       {
/* 271 */         return false;
/*     */       }
/*     */     } 
/* 274 */     EntityUtil.entitiesListFlag = false;
/*     */     
/* 276 */     return true;
/*     */   }
/*     */   
/*     */   public static Pair<BlockPos, Entity> calcPlace(boolean targetMobs, float detectRange, float range, float wallRange, float minDamage, float maxSelfDamage, boolean lethalOverride, float lethalRemainingHealth, boolean noSuicide, boolean allowWater, boolean oneBlockCrystalPlace) {
/* 280 */     BlockPos toPlacePos = null;
/* 281 */     Entity target = null;
/* 282 */     float highestDamage = 0.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 287 */     List<BlockPos> placeablePos = (List<BlockPos>)BlockInteractionHelper.getSphere(getPlayerPos(), range, (int)range, false, true, 0).stream().filter(pos -> (pos.func_185332_f((int)mc.field_71439_g.field_70165_t, (int)mc.field_71439_g.field_70163_u, (int)mc.field_71439_g.field_70161_v) <= range)).filter(pos -> crystalPlaceable(pos, oneBlockCrystalPlace, allowWater)).collect(Collectors.toList());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 295 */     List<Entity> entities = (List<Entity>)EntityUtil.entitiesList().stream().filter(entity -> entity instanceof EntityLivingBase).filter(entity -> (!targetMobs || EntityUtil.isEntityMob(entity))).filter(entity -> (entity != mc.field_175622_Z)).filter(entity -> (((EntityLivingBase)entity).func_110143_aJ() > 0.0F)).filter(entity -> (entity.func_70011_f(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v) <= detectRange)).collect(Collectors.toList());
/*     */     
/* 297 */     for (Entity entity : entities) {
/* 298 */       for (BlockPos pos : placeablePos) {
/*     */         
/* 300 */         if (!canSeeBlock(pos) && MathUtilFuckYou.getDistance(new Vec3d((Vec3i)pos), mc.field_71439_g.func_174791_d()) > wallRange)
/* 301 */           continue;  float health = mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj();
/* 302 */         float selfDamage = calculateDamage(pos.func_177958_n() + 0.5D, (pos.func_177956_o() + 1), pos.func_177952_p() + 0.5D, (Entity)mc.field_71439_g);
/* 303 */         float targetDamage = calculateDamage(pos.func_177958_n() + 0.5D, (pos.func_177956_o() + 1), pos.func_177952_p() + 0.5D, entity, entity.func_174791_d());
/* 304 */         if (targetDamage < highestDamage || 
/* 305 */           targetDamage < minDamage || (
/* 306 */           noSuicide && selfDamage > health) || (
/* 307 */           lethalOverride ? (
/* 308 */           health - selfDamage < lethalRemainingHealth || targetDamage < ((EntityLivingBase)entity).func_110143_aJ() + ((EntityLivingBase)entity).func_110139_bj()) : (
/*     */ 
/*     */ 
/*     */           
/* 312 */           selfDamage > maxSelfDamage))) {
/*     */           continue;
/*     */         }
/*     */         
/* 316 */         toPlacePos = pos;
/* 317 */         target = entity;
/* 318 */         highestDamage = targetDamage;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 323 */     return new Pair(toPlacePos, target);
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\CrystalUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */