/*     */ package me.thediamondsword5.moloch.module.modules.other;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.event.events.player.OnUpdateWalkingPlayerEvent;
/*     */ import me.thediamondsword5.moloch.utils.BlockUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.spartanb312.base.client.ModuleManager;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.concurrent.ConcurrentTaskManager;
/*     */ import net.spartanb312.base.core.concurrent.repeat.RepeatUnit;
/*     */ import net.spartanb312.base.core.event.Listener;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.network.PacketEvent;
/*     */ import net.spartanb312.base.event.events.render.RenderEvent;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.notification.NotificationManager;
/*     */ import net.spartanb312.base.utils.ItemUtils;
/*     */ import net.spartanb312.base.utils.RotationUtil;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import net.spartanb312.base.utils.graphics.SpartanTessellator;
/*     */ import net.spartanb312.base.utils.math.Pair;
/*     */ 
/*     */ @Parallel(runnable = true)
/*     */ @ModuleInfo(name = "EnderChestFarm", category = Category.OTHER, description = "Automatically places and mines ender chests for obsidian")
/*     */ public class EnderChestFarm extends Module {
/*  40 */   private final List<RepeatUnit> repeatUnits = new ArrayList<>();
/*  41 */   private final Timer timer = new Timer();
/*     */   private boolean placingUpFlag = false;
/*  43 */   private int prevSlot = 0;
/*  44 */   private int eChestPrevSlot = 0;
/*     */   public static boolean switchFlag = false;
/*  46 */   private BlockPos renderPos = null;
/*  47 */   public static final List<Pair<BlockPos, EnumFacing>> placeableSpots = new ArrayList<>();
/*  48 */   private float prevYaw = 0.0F;
/*  49 */   private float prevPitch = 0.0F;
/*     */   private boolean prevPlayerChosePlace = false;
/*  51 */   private int eChestSlot = 0;
/*     */   private boolean eChestInitSlotFlag = false;
/*  53 */   private long placeTimeStamp = 0L;
/*     */   
/*  55 */   Setting<SwapMode> swapMode = setting("SwapMode", SwapMode.FromInventory).des("Where to swap from and to ender chests to mine");
/*  56 */   Setting<Float> placeDelay = setting("PlaceDelay", 100.0F, 1.0F, 1000.0F).des("Delay for placing ender chests");
/*  57 */   Setting<Boolean> playerChosePlace = setting("PlayerChosePlace", false).des("Only replace ender chests once player puts one down instead of automatically finding a place to place ender chests").whenAtMode(this.swapMode, SwapMode.HotbarOnly);
/*  58 */   Setting<Boolean> packetPlace = setting("PacketPlace", true).des("Use packets to place ender chests");
/*  59 */   Setting<Boolean> rotate = setting("Rotate", true).des("Rotate to ender chests");
/*  60 */   Setting<Boolean> clientSideRotate = setting("ClientSideRotate", false).des("Force client to look at ender chests").whenTrue(this.rotate);
/*  61 */   Setting<Boolean> packetMine = setting("PacketMine", true).des("Uses packets to mine instead of clicking on the block").only(v -> (((Boolean)this.clientSideRotate.getValue()).booleanValue() || !((Boolean)this.rotate.getValue()).booleanValue()));
/*  62 */   Setting<Boolean> render = setting("Render", true).des("Renders a box on the location where ender chests are being placed");
/*  63 */   Setting<Boolean> renderSolid = setting("RenderSolid", true).des("Use solid box for ender chest position render").whenTrue(this.render);
/*  64 */   Setting<Color> solidColor = setting("SolidColor", new Color((new Color(255, 255, 255, 19)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 19)).whenTrue(this.renderSolid).whenTrue(this.render);
/*  65 */   Setting<Boolean> renderLines = setting("RenderLines", true).des("Use outline box for ender chest position render").whenTrue(this.render);
/*  66 */   Setting<Float> renderLinesWidth = setting("LinesWidth", 1.0F, 1.0F, 5.0F).des("Width of lines of outline box render").whenTrue(this.renderLines).whenTrue(this.render);
/*  67 */   Setting<Color> linesColor = setting("LinesColor", new Color((new Color(255, 255, 255, 101)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 101)).whenTrue(this.renderLines).whenTrue(this.render);
/*     */ 
/*     */ 
/*     */   
/*     */   RepeatUnit doRotate;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnderChestFarm() {
/*  77 */     this.doRotate = new RepeatUnit(() -> 1, () -> {
/*     */           if (((Boolean)this.rotate.getValue()).booleanValue() && ((Boolean)this.clientSideRotate.getValue()).booleanValue() && this.renderPos != null && mc.field_71439_g != null && mc.field_71441_e != null) {
/*     */             Vec3d rotatePos = BlockUtil.getBlockVecFaceCenter(BlockUtil.extrudeBlock(this.renderPos, this.placingUpFlag ? EnumFacing.UP : EnumFacing.DOWN), EnumFacing.UP);
/*     */             float[] rotations = RotationUtil.getRotations(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), new Vec3d(rotatePos.field_72450_a, rotatePos.field_72448_b, rotatePos.field_72449_c));
/*     */             mc.field_71439_g.field_70177_z = rotations[0];
/*     */             mc.field_71439_g.field_70125_A = rotations[1];
/*     */           } 
/*     */         });
/*     */     this.repeatUnits.add(this.doRotate);
/*     */     this.repeatUnits.forEach(it -> {
/*     */           it.suspend();
/*     */           ConcurrentTaskManager.runRepeat(it);
/*     */         });
/*     */   }
/*     */   public void onEnable() {
/*  92 */     this.repeatUnits.forEach(RepeatUnit::resume);
/*     */     
/*  94 */     placeableSpots.clear();
/*  95 */     this.renderPos = null;
/*     */     
/*  97 */     this.placeTimeStamp = System.currentTimeMillis();
/*     */     
/*  99 */     if (mc.field_71439_g != null) {
/* 100 */       this.prevYaw = mc.field_71439_g.field_70177_z;
/* 101 */       this.prevPitch = mc.field_71439_g.field_70125_A;
/*     */     } else {
/*     */       
/* 104 */       this.prevYaw = 0.0F;
/* 105 */       this.prevPitch = 0.0F;
/*     */     } 
/*     */     
/* 108 */     this.moduleEnableFlag = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 113 */     this.repeatUnits.forEach(RepeatUnit::suspend);
/*     */     
/* 115 */     if (switchFlag) {
/* 116 */       if (this.swapMode.getValue() == SwapMode.FromInventory && this.eChestPrevSlot != 99999) {
/* 117 */         mc.field_71442_b.func_187098_a(0, this.eChestPrevSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 118 */         mc.field_71442_b.func_187098_a(0, this.eChestSlot + 36, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 119 */         mc.field_71442_b.func_187098_a(0, this.eChestPrevSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 120 */         mc.field_71442_b.func_78765_e();
/*     */       } 
/*     */       
/* 123 */       if (this.swapMode.getValue() == SwapMode.Offhand && this.eChestPrevSlot != 99999) {
/* 124 */         mc.field_71442_b.func_187098_a(0, this.eChestPrevSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 125 */         mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 126 */         mc.field_71442_b.func_187098_a(0, this.eChestPrevSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 127 */         mc.field_71442_b.func_78765_e();
/*     */       } 
/*     */       
/* 130 */       mc.field_71439_g.field_71071_by.field_70461_c = this.prevSlot;
/* 131 */       switchFlag = false;
/*     */     } 
/*     */     
/* 134 */     if (((Boolean)this.rotate.getValue()).booleanValue() && (
/* 135 */       (Boolean)this.clientSideRotate.getValue()).booleanValue()) {
/* 136 */       mc.field_71439_g.field_70177_z = this.prevYaw;
/* 137 */       mc.field_71439_g.field_70125_A = this.prevPitch;
/*     */     } 
/*     */ 
/*     */     
/* 141 */     placeableSpots.clear();
/* 142 */     this.renderPos = null;
/*     */     
/* 144 */     this.eChestInitSlotFlag = false;
/* 145 */     this.moduleDisableFlag = true;
/* 146 */     BlockUtil.packetMiningFlag = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderWorld(RenderEvent event) {
/* 151 */     if (this.renderPos != null && ((Boolean)this.render.getValue()).booleanValue()) {
/* 152 */       if (((Boolean)this.renderSolid.getValue()).booleanValue()) {
/* 153 */         SpartanTessellator.drawBlockFullBox(new Vec3d((Vec3i)this.renderPos), false, 1.0F, ((Color)this.solidColor.getValue()).getColor());
/*     */       }
/*     */       
/* 156 */       if (((Boolean)this.renderLines.getValue()).booleanValue()) {
/* 157 */         SpartanTessellator.drawBlockLineBox(new Vec3d((Vec3i)this.renderPos), false, 1.0F, ((Float)this.renderLinesWidth.getValue()).floatValue(), ((Color)this.linesColor.getValue()).getColor());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 165 */     if (this.renderPos != null && !placeableSpots.isEmpty()) {
/* 166 */       if (BlockUtil.isBlockPlaceable(this.renderPos)) {
/*     */         
/* 168 */         ItemUtils.switchToSlot(ItemUtils.fastestMiningTool(Blocks.field_150477_bB));
/* 169 */         if ((!((Boolean)this.packetMine.getValue()).booleanValue() && (((Boolean)this.clientSideRotate.getValue()).booleanValue() || !((Boolean)this.rotate.getValue()).booleanValue())) || !BlockUtil.packetMiningFlag) {
/* 170 */           BlockUtil.mineBlock(this.renderPos, BlockUtil.getVisibleBlockSide(new Vec3d((Vec3i)this.renderPos)), ((!((Boolean)this.clientSideRotate.getValue()).booleanValue() && ((Boolean)this.rotate.getValue()).booleanValue()) || ((Boolean)this.packetMine.getValue()).booleanValue()));
/* 171 */           BlockUtil.packetMiningFlag = true;
/*     */         } 
/*     */       } 
/*     */       
/* 175 */       double remainingTime = BlockUtil.packetMineStartTime + BlockUtil.blockBrokenTime(this.renderPos, mc.field_71439_g.field_184831_bT) - System.currentTimeMillis();
/* 176 */       if (!BlockUtil.isBlockPlaceable(this.renderPos) || remainingTime < -500.0D) {
/* 177 */         BlockUtil.packetMiningFlag = false;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPacketSend(PacketEvent.Send event) {
/* 184 */     if (((Boolean)this.playerChosePlace.getValue()).booleanValue() && event.packet instanceof CPacketPlayerTryUseItemOnBlock && mc.field_71439_g.func_184614_ca().func_77973_b() == Item.func_150898_a(Blocks.field_150477_bB)) {
/* 185 */       placeableSpots.add(new Pair(((CPacketPlayerTryUseItemOnBlock)event.packet).func_187023_a(), ((CPacketPlayerTryUseItemOnBlock)event.packet)
/* 186 */             .func_187024_b()));
/*     */     }
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void onUpdateWalkingPlayer(OnUpdateWalkingPlayerEvent event) {
/* 192 */     update();
/*     */     
/* 194 */     if (this.renderPos != null && !placeableSpots.isEmpty() && BlockUtil.isBlockPlaceable(this.renderPos)) {
/* 195 */       double remainingTime = BlockUtil.packetMineStartTime + BlockUtil.blockBrokenTime(this.renderPos, mc.field_71439_g.field_184831_bT) - System.currentTimeMillis();
/* 196 */       if ((System.currentTimeMillis() - this.placeTimeStamp <= 50L || (remainingTime <= 100.0D && remainingTime >= 0.0D)) && !((Boolean)this.clientSideRotate.getValue()).booleanValue()) {
/* 197 */         float[] rotats = RotationUtil.getRotations(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), BlockUtil.getBlockVecFaceCenter(BlockUtil.extrudeBlock(this.renderPos, this.placingUpFlag ? EnumFacing.UP : EnumFacing.DOWN), EnumFacing.UP));
/* 198 */         RotationUtil.setYawAndPitchBlock(rotats[0], rotats[1]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void update() {
/* 204 */     if ((!ItemUtils.isItemInHotbar(Item.func_150898_a(Blocks.field_150477_bB)) && this.swapMode.getValue() == SwapMode.HotbarOnly) || (
/* 205 */       !ItemUtils.isItemInInventory(Item.func_150898_a(Blocks.field_150477_bB)) && (this.swapMode.getValue() == SwapMode.FromInventory || this.swapMode.getValue() == SwapMode.Offhand))) {
/* 206 */       NotificationManager.error("No ender chests to place!");
/* 207 */       ModuleManager.getModule(EnderChestFarm.class).disable();
/*     */       
/*     */       return;
/*     */     } 
/* 211 */     if (!this.eChestInitSlotFlag) {
/* 212 */       this.eChestSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 213 */       this.eChestInitSlotFlag = true;
/*     */     } 
/*     */ 
/*     */     
/* 217 */     if (this.prevPlayerChosePlace != ((Boolean)this.playerChosePlace.getValue()).booleanValue()) {
/* 218 */       placeableSpots.clear();
/* 219 */       this.renderPos = null;
/*     */     } 
/* 221 */     this.prevPlayerChosePlace = ((Boolean)this.playerChosePlace.getValue()).booleanValue();
/*     */ 
/*     */     
/* 224 */     BlockPos pos = new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
/*     */     
/* 226 */     if (this.renderPos != null) {
/* 227 */       this.placingUpFlag = (this.renderPos.field_177960_b >= pos.field_177960_b + 2);
/*     */     }
/*     */     
/* 230 */     if (!((Boolean)this.playerChosePlace.getValue()).booleanValue()) {
/* 231 */       placeableSpots.clear();
/* 232 */       findPlacements();
/*     */     } 
/*     */     
/* 235 */     if (!placeableSpots.isEmpty()) {
/* 236 */       this.renderPos = BlockUtil.extrudeBlock(new BlockPos((Vec3i)((Pair)placeableSpots.get(0)).a), this.placingUpFlag ? EnumFacing.DOWN : EnumFacing.UP);
/*     */       
/* 238 */       if (!BlockUtil.isBlockPlaceable(this.renderPos) && 
/* 239 */         this.timer.passed(((Float)this.placeDelay.getValue()).floatValue())) {
/*     */         
/* 241 */         if (!switchFlag) {
/* 242 */           this.eChestPrevSlot = ItemUtils.itemSlotIDinInventory(Item.func_150898_a(Blocks.field_150477_bB));
/* 243 */           this.prevSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 244 */           switchFlag = true;
/*     */         } 
/*     */         
/* 247 */         if (this.swapMode.getValue() == SwapMode.HotbarOnly || (this.swapMode.getValue() == SwapMode.FromInventory && ItemUtils.isItemInHotbar(Item.func_150898_a(Blocks.field_150477_bB)))) {
/* 248 */           ItemUtils.switchToSlot(ItemUtils.findBlockInHotBar(Blocks.field_150477_bB));
/*     */         }
/*     */         
/* 251 */         if (this.swapMode.getValue() == SwapMode.FromInventory && ItemUtils.isItemInInventory(Item.func_150898_a(Blocks.field_150477_bB)) && 
/* 252 */           !ItemUtils.isItemInHotbar(Item.func_150898_a(Blocks.field_150477_bB)) && !ItemUtils.isItemInHotbar(Item.func_150898_a(Blocks.field_150477_bB))) {
/* 253 */           ItemUtils.swapItemFromInvToHotBar(Item.func_150898_a(Blocks.field_150477_bB), this.eChestSlot);
/*     */         }
/*     */         
/* 256 */         if (this.swapMode.getValue() == SwapMode.Offhand && ItemUtils.isItemInInventory(Item.func_150898_a(Blocks.field_150477_bB)) && mc.field_71439_g.func_184592_cb().func_77973_b() != Item.func_150898_a(Blocks.field_150477_bB)) {
/* 257 */           int slotID = ItemUtils.itemSlotIDinInventory(Item.func_150898_a(Blocks.field_150477_bB));
/*     */           
/* 259 */           if (slotID != 99999) {
/* 260 */             mc.field_71442_b.func_187098_a(0, slotID, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 261 */             mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 262 */             mc.field_71442_b.func_187098_a(0, slotID, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 263 */             mc.field_71442_b.func_78765_e();
/*     */           } 
/*     */         } 
/*     */         
/* 267 */         this.placeTimeStamp = System.currentTimeMillis();
/*     */         
/* 269 */         BlockUtil.placeBlock((BlockPos)((Pair)placeableSpots.get(0)).a, (EnumFacing)((Pair)placeableSpots.get(0)).b, ((Boolean)this.packetPlace.getValue()).booleanValue(), (this.swapMode.getValue() == SwapMode.Offhand), (
/* 270 */             !((Boolean)this.clientSideRotate.getValue()).booleanValue() && ((Boolean)this.rotate.getValue()).booleanValue()));
/*     */         
/* 272 */         this.timer.reset();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private BlockPos[] tryToPlaceSpots(BlockPos originalPos) {
/* 279 */     return new BlockPos[] { new BlockPos(originalPos.field_177962_a + 1, originalPos.field_177960_b, originalPos.field_177961_c), new BlockPos(originalPos.field_177962_a - 1, originalPos.field_177960_b, originalPos.field_177961_c), new BlockPos(originalPos.field_177962_a, originalPos.field_177960_b, originalPos.field_177961_c + 1), new BlockPos(originalPos.field_177962_a, originalPos.field_177960_b, originalPos.field_177961_c - 1), new BlockPos(originalPos.field_177962_a + 1, originalPos.field_177960_b + 1, originalPos.field_177961_c), new BlockPos(originalPos.field_177962_a - 1, originalPos.field_177960_b + 1, originalPos.field_177961_c), new BlockPos(originalPos.field_177962_a, originalPos.field_177960_b + 1, originalPos.field_177961_c + 1), new BlockPos(originalPos.field_177962_a, originalPos.field_177960_b + 1, originalPos.field_177961_c - 1), new BlockPos(originalPos.field_177962_a, originalPos.field_177960_b + 5, originalPos.field_177961_c) };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void findPlacements() {
/* 295 */     BlockPos pos = new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
/*     */     
/* 297 */     for (BlockPos toPlacePos : tryToPlaceSpots(pos)) {
/* 298 */       for (EnumFacing facing : EnumFacing.values()) {
/* 299 */         if ((facing == EnumFacing.UP && toPlacePos.field_177960_b < pos.field_177960_b + 2) || (facing == EnumFacing.DOWN && toPlacePos.field_177960_b >= pos.field_177960_b + 2)) {
/*     */           
/* 301 */           if (toPlacePos.field_177960_b >= pos.field_177960_b + 2) {
/* 302 */             for (int i = 1; i < 6; i++) {
/* 303 */               toPlacePos = new BlockPos(pos.field_177962_a, pos.field_177960_b + i, pos.field_177961_c);
/* 304 */               BlockPos base = BlockUtil.extrudeBlock(toPlacePos, EnumFacing.UP);
/*     */               
/* 306 */               if (isFacePlacebleForEChest(base, EnumFacing.DOWN) && 
/* 307 */                 isBlockPlaceableForEChestUp(base)) {
/*     */                 break;
/*     */               }
/*     */             } 
/*     */           }
/* 312 */           BlockPos offset = BlockUtil.extrudeBlock(toPlacePos, facing.func_176734_d());
/* 313 */           if (isFacePlacebleForEChest(offset, facing) && BlockUtil.isBlockPlaceable(offset)) {
/* 314 */             placeableSpots.add(new Pair(offset, facing));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isFacePlacebleForEChest(BlockPos pos, EnumFacing facing) {
/* 322 */     BlockPos pos1 = BlockUtil.extrudeBlock(pos, facing); return (
/* 323 */       mc.field_71441_e.func_72917_a(new AxisAlignedBB(pos1), (Entity)mc.field_71439_g) && (mc.field_71441_e.func_180495_p(pos1).func_177230_c() == Blocks.field_150477_bB || mc.field_71441_e.func_180495_p(pos1).func_177230_c() == Blocks.field_150350_a || mc.field_71441_e.func_180495_p(pos1).func_177230_c() == Blocks.field_150353_l || mc.field_71441_e.func_180495_p(pos1).func_177230_c() == Blocks.field_150356_k || mc.field_71441_e.func_180495_p(pos1).func_177230_c() == Blocks.field_150355_j || mc.field_71441_e.func_180495_p(pos1).func_177230_c() == Blocks.field_150358_i));
/*     */   }
/*     */   
/*     */   private boolean isBlockPlaceableForEChestUp(BlockPos pos) {
/* 327 */     return (mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150477_bB && mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a && mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150355_j && mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150358_i && mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150353_l && mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150356_k);
/*     */   }
/*     */   
/*     */   enum SwapMode {
/* 331 */     FromInventory,
/* 332 */     HotbarOnly,
/* 333 */     Offhand;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\other\EnderChestFarm.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */