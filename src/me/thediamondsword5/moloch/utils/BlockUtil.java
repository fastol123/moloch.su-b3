/*     */ package me.thediamondsword5.moloch.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import me.thediamondsword5.moloch.client.ServerManager;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Enchantments;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ import net.spartanb312.base.utils.EntityUtil;
/*     */ import net.spartanb312.base.utils.RotationUtil;
/*     */ import net.spartanb312.base.utils.graphics.SpartanTessellator;
/*     */ 
/*     */ public class BlockUtil
/*     */ {
/*  33 */   public static long packetMineStartTime = 0L;
/*     */   
/*     */   public static boolean packetMiningFlag = false;
/*     */   public static boolean isPlacing = false;
/*     */   
/*     */   public static BlockPos extrudeBlock(BlockPos pos, EnumFacing direction) {
/*  39 */     switch (direction) { case WEST:
/*  40 */         return new BlockPos(pos.field_177962_a - 1.0D, pos.field_177960_b, pos.field_177961_c);
/*     */       case EAST:
/*  42 */         return new BlockPos(pos.field_177962_a + 1.0D, pos.field_177960_b, pos.field_177961_c);
/*     */       case NORTH:
/*  44 */         return new BlockPos(pos.field_177962_a, pos.field_177960_b, pos.field_177961_c - 1.0D);
/*     */       case SOUTH:
/*  46 */         return new BlockPos(pos.field_177962_a, pos.field_177960_b, pos.field_177961_c + 1.0D);
/*     */       case UP:
/*  48 */         return new BlockPos(pos.field_177962_a, pos.field_177960_b + 1.0D, pos.field_177961_c);
/*     */       case DOWN:
/*  50 */         return new BlockPos(pos.field_177962_a, pos.field_177960_b - 1.0D, pos.field_177961_c); }
/*     */ 
/*     */     
/*  53 */     return pos;
/*     */   }
/*     */   
/*     */   public static boolean isBlockPlaceable(BlockPos pos) {
/*  57 */     Block block = RotationUtil.mc.field_71441_e.func_180495_p(pos).func_177230_c();
/*  58 */     return (block != Blocks.field_150350_a && block != Blocks.field_150355_j && block != Blocks.field_150358_i && block != Blocks.field_150353_l && block != Blocks.field_150356_k);
/*     */   }
/*     */   
/*     */   public static boolean isFacePlaceble(BlockPos pos, EnumFacing facing, boolean checkEntity) {
/*  62 */     BlockPos pos1 = extrudeBlock(pos, facing);
/*  63 */     return (!RotationUtil.mc.field_71441_e.func_180495_p(pos).func_185904_a().func_76222_j() && RotationUtil.mc.field_71441_e.func_180495_p(pos1).func_185904_a().func_76222_j() && (!checkEntity || EntityUtil.isPosPlaceable(pos1)));
/*     */   }
/*     */   public static Vec3d getBlockVecFaceCenter(BlockPos blockPos, EnumFacing face) {
/*  66 */     BlockPos pos = new BlockPos(Math.floor(blockPos.field_177962_a), Math.floor(blockPos.field_177960_b), Math.floor(blockPos.field_177961_c));
/*  67 */     switch (face) {
/*     */       case UP:
/*  69 */         return new Vec3d(pos.field_177962_a + 0.5D, pos.field_177960_b + 1.0D, pos.field_177961_c + 0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case DOWN:
/*  77 */         return new Vec3d(pos.field_177962_a + 0.5D, pos.field_177960_b, pos.field_177961_c + 0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case EAST:
/*  85 */         return new Vec3d(pos.field_177962_a + 1.0D, pos.field_177960_b + 0.5D, pos.field_177961_c + 0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case WEST:
/*  93 */         return new Vec3d(pos.field_177962_a, pos.field_177960_b + 0.5D, pos.field_177961_c + 0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case NORTH:
/* 101 */         return new Vec3d(pos.field_177962_a + 0.5D, pos.field_177960_b + 0.5D, pos.field_177961_c + 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case SOUTH:
/* 109 */         return new Vec3d(pos.field_177962_a + 0.5D, pos.field_177960_b + 0.5D, pos.field_177961_c);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     return new Vec3d(0.0D, 0.0D, 0.0D);
/*     */   }
/*     */   
/*     */   public static EnumFacing getVisibleBlockSide(Vec3d blockVec) {
/* 121 */     Vec3d eyeVec = RotationUtil.mc.field_71439_g.func_174824_e(RotationUtil.mc.func_184121_ak()).func_178788_d(blockVec);
/* 122 */     return EnumFacing.func_176737_a((float)eyeVec.field_72450_a, (float)eyeVec.field_72448_b, (float)eyeVec.field_72449_c);
/*     */   }
/*     */   
/*     */   public static Vec3d getVec3dBlock(BlockPos blockPos, EnumFacing face) {
/* 126 */     return (new Vec3d((Vec3i)blockPos)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(face.func_176730_m())).func_186678_a(0.5D));
/*     */   }
/*     */   
/*     */   public static float blockBreakSpeed(IBlockState blockMaterial, ItemStack tool) {
/* 130 */     float mineSpeed = tool.func_150997_a(blockMaterial);
/* 131 */     int efficiencyFactor = EnchantmentHelper.func_77506_a(Enchantments.field_185305_q, tool);
/*     */     
/* 133 */     mineSpeed = (float)((mineSpeed > 1.0D && efficiencyFactor > 0) ? (((efficiencyFactor * efficiencyFactor) + mineSpeed) + 1.0D) : mineSpeed);
/*     */     
/* 135 */     if (RotationUtil.mc.field_71439_g.func_70644_a(MobEffects.field_76422_e)) {
/* 136 */       mineSpeed *= 1.0F + ((PotionEffect)Objects.<PotionEffect>requireNonNull(RotationUtil.mc.field_71439_g.func_70660_b(MobEffects.field_76422_e))).func_76458_c() * 0.2F;
/*     */     }
/*     */     
/* 139 */     if (RotationUtil.mc.field_71439_g.func_70644_a(MobEffects.field_76419_f)) {
/* 140 */       switch (((PotionEffect)Objects.requireNonNull((T)RotationUtil.mc.field_71439_g.func_70660_b(MobEffects.field_76419_f))).func_76458_c()) {
/*     */         case 0:
/* 142 */           mineSpeed *= 0.3F;
/*     */           break;
/*     */ 
/*     */         
/*     */         case 1:
/* 147 */           mineSpeed *= 0.09F;
/*     */           break;
/*     */ 
/*     */         
/*     */         case 2:
/* 152 */           mineSpeed *= 0.0027F;
/*     */           break;
/*     */ 
/*     */         
/*     */         default:
/* 157 */           mineSpeed *= 8.1E-4F;
/*     */           break;
/*     */       } 
/*     */     
/*     */     }
/* 162 */     if (!RotationUtil.mc.field_71439_g.field_70122_E || (RotationUtil.mc.field_71439_g.func_70090_H() && EnchantmentHelper.func_77506_a(Enchantments.field_185299_g, RotationUtil.mc.field_71439_g.field_71071_by.func_70440_f(0)) == 0)) {
/* 163 */       mineSpeed /= 5.0F;
/*     */     }
/*     */     
/* 166 */     return mineSpeed;
/*     */   }
/*     */   
/*     */   public static double blockBrokenTime(BlockPos pos, ItemStack tool) {
/* 170 */     IBlockState blockMaterial = RotationUtil.mc.field_71441_e.func_180495_p(pos);
/*     */     
/* 172 */     float damageTicks = blockBreakSpeed(blockMaterial, tool) / blockMaterial.func_185887_b((World)RotationUtil.mc.field_71441_e, pos) / 30.0F;
/* 173 */     return Math.ceil((1.0F / damageTicks)) * 50.0D * (20.0F / ServerManager.getTPS());
/*     */   }
/*     */   
/*     */   public static void placeBlock(BlockPos pos, EnumFacing facing, boolean packetPlace, boolean offHand, boolean spoofRotate) {
/* 177 */     isPlacing = true;
/*     */     
/* 179 */     if (!RotationUtil.mc.field_71439_g.func_70093_af()) {
/* 180 */       RotationUtil.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)RotationUtil.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
/* 181 */       RotationUtil.mc.field_71439_g.func_70095_a(true);
/*     */     } 
/*     */     
/* 184 */     Vec3d blockVec = getVec3dBlock(pos, facing);
/*     */     
/* 186 */     if (spoofRotate) {
/* 187 */       float[] r = RotationUtil.getRotationsBlock(pos, facing, true);
/* 188 */       RotationUtil.setYawAndPitchBlock(r[0], r[1]);
/*     */     } 
/*     */     
/* 191 */     if (packetPlace) {
/* 192 */       float x = (float)(blockVec.field_72450_a - pos.func_177958_n());
/* 193 */       float y = (float)(blockVec.field_72448_b - pos.func_177956_o());
/* 194 */       float z = (float)(blockVec.field_72449_c - pos.func_177952_p());
/* 195 */       RotationUtil.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(pos, facing, offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, x, y, z));
/*     */     } else {
/*     */       
/* 198 */       RotationUtil.mc.field_71442_b.func_187099_a(RotationUtil.mc.field_71439_g, RotationUtil.mc.field_71441_e, pos, facing, blockVec, offHand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
/*     */     } 
/*     */     
/* 201 */     RotationUtil.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*     */     
/* 203 */     if (spoofRotate) {
/* 204 */       RotationUtil.resetRotationBlock();
/*     */     }
/*     */     
/* 207 */     if (!RotationUtil.mc.field_71439_g.func_70093_af()) {
/* 208 */       RotationUtil.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)RotationUtil.mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
/* 209 */       RotationUtil.mc.field_71439_g.func_70095_a(true);
/* 210 */       RotationUtil.mc.field_71442_b.func_78765_e();
/* 211 */       RotationUtil.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)RotationUtil.mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
/* 212 */       RotationUtil.mc.field_71439_g.func_70095_a(false);
/* 213 */       RotationUtil.mc.field_71442_b.func_78765_e();
/*     */     } 
/*     */ 
/*     */     
/* 217 */     isPlacing = false;
/*     */   }
/*     */   
/*     */   public static void mineBlock(BlockPos pos, EnumFacing face, boolean packetMine) {
/* 221 */     if (packetMine) {
/* 222 */       packetMineStartTime = System.currentTimeMillis();
/* 223 */       RotationUtil.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, face));
/* 224 */       RotationUtil.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, face));
/* 225 */       RotationUtil.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*     */     }
/* 227 */     else if (RotationUtil.mc.field_71442_b.func_180512_c(pos, face)) {
/* 228 */       RotationUtil.mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSameBlockPos(BlockPos pos1, BlockPos pos2) {
/* 235 */     AxisAlignedBB bb1 = SpartanTessellator.getBoundingFromPos(pos1);
/* 236 */     AxisAlignedBB bb2 = SpartanTessellator.getBoundingFromPos(pos2);
/* 237 */     return (bb1.field_72336_d == bb2.field_72336_d && bb1.field_72337_e == bb2.field_72337_e && bb1.field_72334_f == bb2.field_72334_f);
/*     */   }
/*     */   
/*     */   public static List<EnumFacing> getVisibleSides(BlockPos pos) {
/* 241 */     List<EnumFacing> list = new ArrayList<>();
/* 242 */     boolean isFullBox = (!RotationUtil.mc.field_71441_e.func_180495_p(pos).func_185913_b() || !RotationUtil.mc.field_71441_e.func_175623_d(pos));
/* 243 */     Vec3d eyesVec = RotationUtil.mc.field_71439_g.func_174824_e(RotationUtil.mc.func_184121_ak());
/* 244 */     Vec3d blockVec = (new Vec3d((Vec3i)pos)).func_72441_c(0.5D, 0.5D, 0.5D);
/* 245 */     double diffX = eyesVec.field_72450_a - blockVec.field_72450_a;
/* 246 */     double diffY = eyesVec.field_72448_b - blockVec.field_72448_b;
/* 247 */     double diffZ = eyesVec.field_72449_c - blockVec.field_72449_c;
/*     */     
/* 249 */     if (diffX < -0.5D) {
/* 250 */       list.add(EnumFacing.WEST);
/* 251 */     } else if (diffX > 0.5D) {
/* 252 */       list.add(EnumFacing.EAST);
/* 253 */     } else if (isFullBox) {
/* 254 */       list.add(EnumFacing.WEST);
/* 255 */       list.add(EnumFacing.EAST);
/*     */     } 
/*     */     
/* 258 */     if (diffY < -0.5D) {
/* 259 */       list.add(EnumFacing.DOWN);
/* 260 */     } else if (diffY > 0.5D) {
/* 261 */       list.add(EnumFacing.UP);
/*     */     } else {
/* 263 */       list.add(EnumFacing.DOWN);
/* 264 */       list.add(EnumFacing.UP);
/*     */     } 
/*     */     
/* 267 */     if (diffZ < -0.5D) {
/* 268 */       list.add(EnumFacing.NORTH);
/* 269 */     } else if (diffZ > 0.5D) {
/* 270 */       list.add(EnumFacing.SOUTH);
/* 271 */     } else if (isFullBox) {
/* 272 */       list.add(EnumFacing.NORTH);
/* 273 */       list.add(EnumFacing.SOUTH);
/*     */     } 
/*     */     
/* 276 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloc\\utils\BlockUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */