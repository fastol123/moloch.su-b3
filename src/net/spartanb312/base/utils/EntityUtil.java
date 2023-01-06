/*     */ package net.spartanb312.base.utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelDragon;
/*     */ import net.minecraft.client.model.ModelEnderman;
/*     */ import net.minecraft.client.model.ModelIllager;
/*     */ import net.minecraft.client.model.ModelLlama;
/*     */ import net.minecraft.client.model.ModelOcelot;
/*     */ import net.minecraft.client.model.ModelPlayer;
/*     */ import net.minecraft.client.model.ModelPolarBear;
/*     */ import net.minecraft.client.model.ModelRabbit;
/*     */ import net.minecraft.client.model.ModelSilverfish;
/*     */ import net.minecraft.client.model.ModelSkeleton;
/*     */ import net.minecraft.client.model.ModelSquid;
/*     */ import net.minecraft.client.model.ModelWither;
/*     */ import net.minecraft.client.model.ModelZombie;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.spartanb312.base.utils.graphics.RenderHelper;
/*     */ 
/*     */ public class EntityUtil {
/*  31 */   public static Minecraft mc = Minecraft.func_71410_x();
/*     */   public static boolean isEntityPlayerLoaded = false;
/*     */   public static boolean isEntityMobLoaded = false;
/*     */   public static boolean isEntityAnimalLoaded = false;
/*     */   public static boolean isEntityCrystalLoaded = false;
/*     */   public static boolean isEntityProjectileLoaded = false;
/*  37 */   public static List<Entity> entitiesList1 = new ArrayList<>();
/*     */   public static boolean entitiesListFlag = false;
/*     */   
/*     */   public static List<Entity> entitiesList() {
/*  41 */     if (!entitiesListFlag && mc.field_71441_e != null && mc.field_71441_e.field_72996_f != null && mc.field_71441_e.field_72996_f.size() != 0) {
/*  42 */       entitiesList1 = new ArrayList<>(mc.field_71441_e.field_72996_f);
/*     */     }
/*  44 */     return entitiesList1;
/*     */   }
/*     */   
/*     */   public static boolean isEntityMob(Entity entity) {
/*  48 */     return (entity instanceof net.minecraft.entity.monster.EntityMob || entity instanceof net.minecraft.entity.monster.EntityShulker || entity instanceof net.minecraft.entity.monster.EntitySlime || entity instanceof net.minecraft.entity.monster.EntityGhast);
/*     */   }
/*     */   
/*     */   public static boolean isEntityAnimal(Entity entity) {
/*  52 */     return (entity instanceof net.minecraft.entity.passive.EntityAnimal || entity instanceof net.minecraft.entity.passive.EntitySquid);
/*     */   }
/*     */   
/*     */   public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
/*  56 */     return new Vec3d((entity.field_70165_t - entity.field_70142_S) * x, (entity.field_70163_u - entity.field_70137_T) * y, (entity.field_70161_v - entity.field_70136_U) * z);
/*     */   }
/*     */   
/*     */   public static Vec3d getInterpolatedEntityPos(Entity entity, double ticks) {
/*  60 */     return new Vec3d(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * ticks, entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * ticks, entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * ticks);
/*     */   }
/*     */   
/*     */   public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
/*  64 */     return getInterpolatedAmount(entity, ticks, ticks, ticks);
/*     */   }
/*     */   
/*     */   public static boolean isPlayerInHole() {
/*  68 */     BlockPos blockPos = getLocalPlayerPosFloored();
/*     */     
/*  70 */     IBlockState blockState = mc.field_71441_e.func_180495_p(blockPos);
/*     */     
/*  72 */     if (blockState.func_177230_c() != Blocks.field_150350_a) {
/*  73 */       return false;
/*     */     }
/*  75 */     if (mc.field_71441_e.func_180495_p(blockPos.func_177984_a()).func_177230_c() != Blocks.field_150350_a) {
/*  76 */       return false;
/*     */     }
/*  78 */     if (mc.field_71441_e.func_180495_p(blockPos.func_177977_b()).func_177230_c() == Blocks.field_150350_a) {
/*  79 */       return false;
/*     */     }
/*     */     
/*  82 */     BlockPos[] touchingBlocks = { blockPos.func_177978_c(), blockPos.func_177968_d(), blockPos.func_177974_f(), blockPos.func_177976_e() };
/*     */     
/*  84 */     int validHorizontalBlocks = 0;
/*  85 */     for (BlockPos touching : touchingBlocks) {
/*  86 */       IBlockState touchingState = mc.field_71441_e.func_180495_p(touching);
/*  87 */       if (touchingState.func_177230_c() != Blocks.field_150350_a && touchingState.func_185913_b()) {
/*  88 */         validHorizontalBlocks++;
/*     */       }
/*     */     } 
/*  91 */     return (validHorizontalBlocks >= 4);
/*     */   }
/*     */   
/*     */   public static BlockPos getLocalPlayerPosFloored() {
/*  95 */     return new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
/*     */   }
/*     */   
/*     */   public static boolean isFakeLocalPlayer(Entity entity) {
/*  99 */     return (entity != null && entity.func_145782_y() == -100 && mc.field_71439_g != entity);
/*     */   }
/*     */   
/*     */   public static boolean isPassive(Entity e) {
/* 103 */     if (e instanceof EntityWolf && ((EntityWolf)e).func_70919_bu()) {
/* 104 */       return false;
/*     */     }
/* 106 */     if (e instanceof net.minecraft.entity.EntityAgeable || e instanceof net.minecraft.entity.passive.EntityAmbientCreature || e instanceof net.minecraft.entity.passive.EntitySquid) {
/* 107 */       return true;
/*     */     }
/* 109 */     return (e instanceof EntityIronGolem && ((EntityIronGolem)e).func_70643_av() == null);
/*     */   }
/*     */   
/*     */   public static boolean isLiving(Entity e) {
/* 113 */     return e instanceof net.minecraft.entity.EntityLivingBase;
/*     */   }
/*     */   
/*     */   public static float[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
/* 117 */     double dirX = me.field_70165_t - px;
/* 118 */     double dirY = me.field_70163_u - py;
/* 119 */     double dirZ = me.field_70161_v - pz;
/* 120 */     double len = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
/* 121 */     dirX /= len;
/* 122 */     dirY /= len;
/* 123 */     dirZ /= len;
/* 124 */     double pitch = Math.asin(dirY);
/* 125 */     double yaw = Math.atan2(dirZ, dirX);
/* 126 */     pitch = pitch * 180.0D / Math.PI;
/* 127 */     yaw = yaw * 180.0D / Math.PI;
/* 128 */     yaw += 90.0D;
/* 129 */     return new float[] { (float)yaw, (float)pitch };
/*     */   }
/*     */   
/*     */   public static void runEntityCheck() {
/* 133 */     isEntityPlayerLoaded = false;
/* 134 */     isEntityMobLoaded = false;
/* 135 */     isEntityAnimalLoaded = false;
/* 136 */     isEntityCrystalLoaded = false;
/* 137 */     isEntityProjectileLoaded = false;
/* 138 */     for (Entity entity : entitiesList()) {
/* 139 */       entitiesListFlag = true;
/* 140 */       if (!RenderHelper.isInViewFrustrum(entity))
/* 141 */         continue;  if (entity instanceof EntityPlayer && entity != mc.field_71439_g) isEntityPlayerLoaded = true; 
/* 142 */       if (entity instanceof net.minecraft.entity.monster.EntityMob || entity instanceof net.minecraft.entity.monster.EntitySlime || entity instanceof net.minecraft.entity.monster.EntityGhast || entity instanceof net.minecraft.entity.boss.EntityDragon) isEntityMobLoaded = true; 
/* 143 */       if (isEntityAnimal(entity)) isEntityAnimalLoaded = true; 
/* 144 */       if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal) isEntityCrystalLoaded = true; 
/* 145 */       if (entity instanceof net.minecraft.entity.IProjectile || entity instanceof net.minecraft.entity.projectile.EntityShulkerBullet || entity instanceof net.minecraft.entity.projectile.EntityFireball || entity instanceof net.minecraft.entity.item.EntityEnderEye) isEntityProjectileLoaded = true; 
/*     */     } 
/* 147 */     entitiesListFlag = false;
/*     */   }
/*     */   
/*     */   public static double calculateDistanceWithPartialTicks(double originalPos, double finalPos, float renderPartialTicks) {
/* 151 */     return finalPos + (originalPos - finalPos) * renderPartialTicks;
/*     */   }
/*     */   
/*     */   public static Vec3d interpolateEntity(Entity entity, float renderPartialTicks) {
/* 155 */     return new Vec3d(calculateDistanceWithPartialTicks(entity.field_70165_t, entity.field_70142_S, renderPartialTicks), calculateDistanceWithPartialTicks(entity.field_70163_u, entity.field_70137_T, renderPartialTicks), calculateDistanceWithPartialTicks(entity.field_70161_v, entity.field_70136_U, renderPartialTicks));
/*     */   }
/*     */   
/*     */   public static Vec3d interpolateEntityRender(Entity entity, float renderPartialTicks) {
/* 159 */     return new Vec3d(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * renderPartialTicks - (mc.func_175598_ae()).field_78725_b, entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * renderPartialTicks - (mc.func_175598_ae()).field_78726_c, entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * renderPartialTicks - (mc.func_175598_ae()).field_78723_d);
/*     */   }
/*     */   
/*     */   public static boolean isBurrowed(Entity entity) {
/* 163 */     BlockPos pos = new BlockPos(Math.floor(entity.field_70165_t), Math.floor(entity.field_70163_u + 0.2D), Math.floor(entity.field_70161_v));
/* 164 */     return (mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a && mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150355_j && mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150358_i && mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150353_l && mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150356_k);
/*     */   }
/*     */   
/*     */   public static boolean isPosPlaceable(BlockPos pos) {
/* 168 */     return (mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76222_j() && mc.field_71441_e.func_72917_a(new AxisAlignedBB(pos), (Entity)mc.field_71439_g));
/*     */   }
/*     */   
/*     */   private static void centerPlayer(double x, double y, double z) {
/* 172 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(x, y, z, true));
/* 173 */     mc.field_71439_g.func_70107_b(x, y, z);
/*     */   }
/*     */   
/*     */   private static double getDst(Vec3d vec) {
/* 177 */     return mc.field_71439_g.func_174791_d().func_72438_d(vec);
/*     */   }
/*     */   
/*     */   public static void setCenter() {
/* 181 */     BlockPos centerPos = mc.field_71439_g.func_180425_c();
/* 182 */     double y = centerPos.func_177956_o();
/* 183 */     double x = centerPos.func_177958_n();
/* 184 */     double z = centerPos.func_177952_p();
/*     */     
/* 186 */     Vec3d plusPlus = new Vec3d(x + 0.5D, y, z + 0.5D);
/* 187 */     Vec3d plusMinus = new Vec3d(x + 0.5D, y, z - 0.5D);
/* 188 */     Vec3d minusMinus = new Vec3d(x - 0.5D, y, z - 0.5D);
/* 189 */     Vec3d minusPlus = new Vec3d(x - 0.5D, y, z + 0.5D);
/*     */     
/* 191 */     if (getDst(plusPlus) < getDst(plusMinus) && getDst(plusPlus) < getDst(minusMinus) && getDst(plusPlus) < getDst(minusPlus)) {
/* 192 */       x = centerPos.func_177958_n() + 0.5D;
/* 193 */       z = centerPos.func_177952_p() + 0.5D;
/*     */     } 
/* 195 */     if (getDst(plusMinus) < getDst(plusPlus) && getDst(plusMinus) < getDst(minusMinus) && getDst(plusMinus) < getDst(minusPlus)) {
/* 196 */       x = centerPos.func_177958_n() + 0.5D;
/* 197 */       z = centerPos.func_177952_p() - 0.5D;
/*     */     } 
/* 199 */     if (getDst(minusMinus) < getDst(plusPlus) && getDst(minusMinus) < getDst(plusMinus) && getDst(minusMinus) < getDst(minusPlus)) {
/* 200 */       x = centerPos.func_177958_n() - 0.5D;
/* 201 */       z = centerPos.func_177952_p() - 0.5D;
/*     */     } 
/* 203 */     if (getDst(minusPlus) < getDst(plusPlus) && getDst(minusPlus) < getDst(plusMinus) && getDst(minusPlus) < getDst(minusMinus)) {
/* 204 */       x = centerPos.func_177958_n() - 0.5D;
/* 205 */       z = centerPos.func_177952_p() + 0.5D;
/*     */     } 
/* 207 */     centerPlayer(x, y, z);
/*     */   }
/*     */   
/*     */   public static Vec3d selfCenterPos() {
/* 211 */     BlockPos centerPos = mc.field_71439_g.func_180425_c();
/* 212 */     double y = centerPos.func_177956_o();
/* 213 */     double x = centerPos.func_177958_n();
/* 214 */     double z = centerPos.func_177952_p();
/*     */     
/* 216 */     Vec3d plusPlus = new Vec3d(x + 0.5D, y, z + 0.5D);
/* 217 */     Vec3d plusMinus = new Vec3d(x + 0.5D, y, z - 0.5D);
/* 218 */     Vec3d minusMinus = new Vec3d(x - 0.5D, y, z - 0.5D);
/* 219 */     Vec3d minusPlus = new Vec3d(x - 0.5D, y, z + 0.5D);
/*     */     
/* 221 */     if (getDst(plusPlus) < getDst(plusMinus) && getDst(plusPlus) < getDst(minusMinus) && getDst(plusPlus) < getDst(minusPlus)) {
/* 222 */       x = centerPos.func_177958_n() + 0.5D;
/* 223 */       z = centerPos.func_177952_p() + 0.5D;
/*     */     } 
/* 225 */     if (getDst(plusMinus) < getDst(plusPlus) && getDst(plusMinus) < getDst(minusMinus) && getDst(plusMinus) < getDst(minusPlus)) {
/* 226 */       x = centerPos.func_177958_n() + 0.5D;
/* 227 */       z = centerPos.func_177952_p() - 0.5D;
/*     */     } 
/* 229 */     if (getDst(minusMinus) < getDst(plusPlus) && getDst(minusMinus) < getDst(plusMinus) && getDst(minusMinus) < getDst(minusPlus)) {
/* 230 */       x = centerPos.func_177958_n() - 0.5D;
/* 231 */       z = centerPos.func_177952_p() - 0.5D;
/*     */     } 
/* 233 */     if (getDst(minusPlus) < getDst(plusPlus) && getDst(minusPlus) < getDst(plusMinus) && getDst(minusPlus) < getDst(minusMinus)) {
/* 234 */       x = centerPos.func_177958_n() - 0.5D;
/* 235 */       z = centerPos.func_177952_p() + 0.5D;
/*     */     } 
/* 237 */     return new Vec3d(x, y, z);
/*     */   }
/*     */   
/*     */   public static boolean isEntityVisible(Entity entity) {
/* 241 */     return mc.field_71439_g.func_70685_l(entity);
/*     */   }
/*     */   
/*     */   public static double getInterpDistance(float partialTicks, Entity entity, Entity entity2) {
/* 245 */     Vec3d interp = interpolateEntity(entity, partialTicks);
/* 246 */     Vec3d interp2 = interpolateEntity(entity2, partialTicks);
/*     */     
/* 248 */     double x = interp.field_72450_a - interp2.field_72450_a;
/* 249 */     double y = interp.field_72448_b - interp2.field_72448_b;
/* 250 */     double z = interp.field_72449_c - interp2.field_72449_c;
/*     */     
/* 252 */     return Math.sqrt(x * x + y * y + z * z);
/*     */   }
/*     */   
/*     */   public static ModelBase getModel(Entity entity) {
/* 256 */     if (entity instanceof EntityPlayer) return (ModelBase)new ModelPlayer(0.0F, false); 
/* 257 */     if (entity instanceof net.minecraft.entity.passive.EntityBat) return (ModelBase)new ModelBat(); 
/* 258 */     if (entity instanceof net.minecraft.entity.monster.EntityBlaze) return (ModelBase)new ModelBlaze(); 
/* 259 */     if (entity instanceof net.minecraft.entity.monster.EntitySpider) return (ModelBase)new ModelSpider(); 
/* 260 */     if (entity instanceof net.minecraft.entity.passive.EntityChicken) return (ModelBase)new ModelChicken(); 
/* 261 */     if (entity instanceof net.minecraft.entity.passive.EntityCow) return (ModelBase)new ModelCow(); 
/* 262 */     if (entity instanceof net.minecraft.entity.monster.EntityCreeper) return (ModelBase)new ModelCreeper(); 
/* 263 */     if (entity instanceof net.minecraft.entity.passive.EntityDonkey || entity instanceof net.minecraft.entity.passive.EntityHorse || entity instanceof net.minecraft.entity.passive.EntityMule || entity instanceof net.minecraft.entity.passive.EntitySkeletonHorse || entity instanceof net.minecraft.entity.passive.EntityZombieHorse) return (ModelBase)new ModelHorse(); 
/* 264 */     if (entity instanceof net.minecraft.entity.monster.EntityGuardian) return (ModelBase)new ModelGuardian(); 
/* 265 */     if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal) return (ModelBase)new ModelEnderCrystal(0.0F, false); 
/* 266 */     if (entity instanceof net.minecraft.entity.boss.EntityDragon) return (ModelBase)new ModelDragon(0.0F); 
/* 267 */     if (entity instanceof net.minecraft.entity.monster.EntityEnderman) return (ModelBase)new ModelEnderman(0.0F); 
/* 268 */     if (entity instanceof net.minecraft.entity.monster.EntityEndermite) return (ModelBase)new ModelEnderMite(); 
/* 269 */     if (entity instanceof net.minecraft.entity.monster.EntityEvoker || entity instanceof net.minecraft.entity.monster.EntityIllusionIllager || entity instanceof net.minecraft.entity.monster.EntityVindicator) return (ModelBase)new ModelIllager(0.0F, 0.0F, 64, 64); 
/* 270 */     if (entity instanceof net.minecraft.entity.monster.EntityGhast) return (ModelBase)new ModelGhast(); 
/* 271 */     if (entity instanceof net.minecraft.entity.monster.EntityZombieVillager) return (ModelBase)new ModelZombieVillager(); 
/* 272 */     if (entity instanceof net.minecraft.entity.monster.EntityGiantZombie || entity instanceof net.minecraft.entity.monster.EntityZombie) return (ModelBase)new ModelZombie(); 
/* 273 */     if (entity instanceof net.minecraft.entity.passive.EntityLlama) return (ModelBase)new ModelLlama(0.0F); 
/* 274 */     if (entity instanceof net.minecraft.entity.monster.EntityMagmaCube) return (ModelBase)new ModelMagmaCube(); 
/* 275 */     if (entity instanceof net.minecraft.entity.passive.EntityOcelot) return (ModelBase)new ModelOcelot(); 
/* 276 */     if (entity instanceof net.minecraft.entity.passive.EntityParrot) return (ModelBase)new ModelParrot(); 
/* 277 */     if (entity instanceof net.minecraft.entity.passive.EntityPig) return (ModelBase)new ModelPig(); 
/* 278 */     if (entity instanceof net.minecraft.entity.monster.EntityPolarBear) return (ModelBase)new ModelPolarBear(); 
/* 279 */     if (entity instanceof net.minecraft.entity.passive.EntityRabbit) return (ModelBase)new ModelRabbit(); 
/* 280 */     if (entity instanceof net.minecraft.entity.passive.EntitySheep) return (ModelBase)new ModelSheep2(); 
/* 281 */     if (entity instanceof net.minecraft.entity.monster.EntityShulker) return (ModelBase)new ModelShulker(); 
/* 282 */     if (entity instanceof net.minecraft.entity.monster.EntitySilverfish) return (ModelBase)new ModelSilverfish(); 
/* 283 */     if (entity instanceof net.minecraft.entity.monster.EntitySkeleton || entity instanceof net.minecraft.entity.monster.EntityStray || entity instanceof net.minecraft.entity.monster.EntityWitherSkeleton) return (ModelBase)new ModelSkeleton(); 
/* 284 */     if (entity instanceof net.minecraft.entity.monster.EntitySlime) return (ModelBase)new ModelSlime(16); 
/* 285 */     if (entity instanceof net.minecraft.entity.monster.EntitySnowman) return (ModelBase)new ModelSnowMan(); 
/* 286 */     if (entity instanceof net.minecraft.entity.passive.EntitySquid) return (ModelBase)new ModelSquid(); 
/* 287 */     if (entity instanceof net.minecraft.entity.monster.EntityVex) return (ModelBase)new ModelVex(); 
/* 288 */     if (entity instanceof net.minecraft.entity.passive.EntityVillager) return (ModelBase)new ModelVillager(0.0F); 
/* 289 */     if (entity instanceof EntityIronGolem) return (ModelBase)new ModelIronGolem(); 
/* 290 */     if (entity instanceof net.minecraft.entity.monster.EntityWitch) return (ModelBase)new ModelWitch(0.0F); 
/* 291 */     if (entity instanceof net.minecraft.entity.boss.EntityWither) return (ModelBase)new ModelWither(0.0F); 
/* 292 */     if (entity instanceof EntityWolf) return (ModelBase)new ModelWolf(); 
/* 293 */     return null;
/*     */   }
/*     */   public static AxisAlignedBB scaleBB(Vec3d vec, AxisAlignedBB bb, float scale) {
/* 296 */     Vec3d center = new Vec3d(bb.field_72340_a + (bb.field_72336_d - bb.field_72340_a) * 0.5D, bb.field_72338_b + (bb.field_72337_e - bb.field_72338_b) * 0.5D, bb.field_72339_c + (bb.field_72334_f - bb.field_72339_c) * 0.5D);
/* 297 */     double newWidth = (bb.field_72336_d - bb.field_72340_a) * 0.5D * scale;
/* 298 */     double newHeight = (bb.field_72337_e - bb.field_72338_b) * 0.5D * scale;
/* 299 */     double newLength = (bb.field_72334_f - bb.field_72339_c) * 0.5D * scale;
/* 300 */     return new AxisAlignedBB(center.field_72450_a + newWidth, center.field_72448_b + newHeight, center.field_72449_c + newLength, center.field_72450_a - newWidth, center.field_72448_b - newHeight, center.field_72449_c - newLength);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canStep() {
/* 305 */     return (mc.field_71441_e != null && mc.field_71439_g != null && !mc.field_71439_g.func_70090_H() && !mc.field_71439_g.func_180799_ab() && !mc.field_71439_g.func_70617_f_() && !mc.field_71474_y.field_74314_A.func_151470_d());
/*     */   }
/*     */   
/*     */   public static boolean isOnGround(double height) {
/* 309 */     return !mc.field_71441_e.func_184144_a((Entity)mc.field_71439_g, mc.field_71439_g.func_174813_aQ().func_72317_d(0.0D, -height, 0.0D)).isEmpty();
/*     */   }
/*     */   
/*     */   public static double[] motionPredict(float magnitude, float xOffset, float yOffset, float zOffset) {
/* 313 */     double[] d = MathUtilFuckYou.cartesianToPolar3d(xOffset, yOffset, zOffset);
/* 314 */     double[] d1 = MathUtilFuckYou.polarToCartesian3d(magnitude, d[1], d[2]);
/*     */     
/* 316 */     return new double[] { d1[0], d1[2] };
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getMovementYaw() {
/* 321 */     float forward = (mc.field_71439_g.field_71158_b.field_192832_b > 0.0F) ? 1.0F : ((mc.field_71439_g.field_71158_b.field_192832_b < 0.0F) ? -1.0F : 0.0F);
/*     */     
/* 323 */     float strafe = (mc.field_71439_g.field_71158_b.field_78902_a > 0.0F) ? 1.0F : ((mc.field_71439_g.field_71158_b.field_78902_a < 0.0F) ? -1.0F : 0.0F);
/*     */ 
/*     */     
/* 326 */     float s = 90.0F * strafe;
/* 327 */     s *= (forward != 0.0F) ? (forward * 0.5F) : 1.0F;
/* 328 */     float yaw = mc.field_71439_g.field_70177_z - s;
/* 329 */     yaw -= (forward == -1.0F) ? 180.0F : 0.0F;
/*     */     
/* 331 */     return yaw * 0.017453292519943295D;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\EntityUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */