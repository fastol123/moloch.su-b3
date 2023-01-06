/*     */ package me.thediamondsword5.moloch.module.modules.combat;
/*     */ import java.awt.Color;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.event.events.player.DamageBlockEvent;
/*     */ import me.thediamondsword5.moloch.event.events.player.LeftClickBlockEvent;
/*     */ import me.thediamondsword5.moloch.utils.BlockUtil;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.spartanb312.base.core.event.Listener;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.render.RenderEvent;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.ColorUtil;
/*     */ import net.spartanb312.base.utils.ItemUtils;
/*     */ import net.spartanb312.base.utils.MathUtilFuckYou;
/*     */ import net.spartanb312.base.utils.RotationUtil;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import net.spartanb312.base.utils.graphics.SpartanTessellator;
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "Mine+", category = Category.COMBAT, description = "More efficient mining techniques (PacketMine && InstantMine)")
/*     */ public class MinePlus extends Module {
/*  29 */   private BlockPos miningPos = null;
/*  30 */   private EnumFacing miningFace = EnumFacing.UP;
/*  31 */   private int prevSlot = 0;
/*     */   private boolean flag = false;
/*     */   private boolean flag2 = false;
/*     */   private boolean flag3 = false;
/*     */   private boolean instantMineRotationFlag = false;
/*     */   private boolean onMined = false;
/*  37 */   private final Timer instantMineTimer = new Timer();
/*     */   
/*  39 */   Setting<Page> page = setting("Page", Page.Mine);
/*  40 */   Setting<Mode> mode = setting("Mode", Mode.PacketMine).des("On 2b2tpvp.net (and maybe other servers?) it helps to look at the block for a bit after tapping it to have it consistently work (from experience)").whenAtMode(this.page, Page.Mine);
/*     */   
/*  42 */   Setting<Swap> swap = setting("Swap", Swap.Normal).des("Ways to switch to best mining tool").whenAtMode(this.page, Page.Mine);
/*  43 */   Setting<Boolean> spamPackets = setting("SpamPackets", false).des("Spam break packets while mining (idk if this actually makes a difference, ig it makes it look more legit?)").whenAtMode(this.mode, Mode.PacketMine).whenAtMode(this.page, Page.Mine);
/*  44 */   Setting<Integer> instantMineDelay = setting("InstantMineDelay", 70, 0, 1000).des("Delay between each attempted instant mine break").whenAtMode(this.mode, Mode.InstantMine).whenAtMode(this.page, Page.Mine);
/*  45 */   Setting<Float> range = setting("Range", 8.0F, 1.0F, 10.0F).des("Range to stop mining if you get too far from the block").whenAtMode(this.page, Page.Mine);
/*  46 */   Setting<Boolean> rotate = setting("Rotate", false).des("Rotate to block on finish mining").whenAtMode(this.page, Page.Mine);
/*     */   
/*  48 */   Setting<Boolean> completedColor = setting("CompletedColor", true).des("Changes color on completion of mining").whenAtMode(this.page, Page.Render);
/*  49 */   Setting<Float> completedProgress = setting("CompletedProgress", 0.7F, 0.0F, 0.9F).des("At what fraction of progress should the color start changing").whenTrue(this.completedColor).whenAtMode(this.page, Page.Render);
/*  50 */   Setting<ScaleMode> scaleMode = setting("ScaleMode", ScaleMode.Expand).des("Changes size of render depending on mining progress").whenAtMode(this.page, Page.Render);
/*  51 */   Setting<Float> scaleFactor = setting("ScaleFactor", 0.5F, 0.1F, 1.0F).des("Steepness of mining render scale change").only(v -> (this.scaleMode.getValue() != ScaleMode.None)).whenAtMode(this.page, Page.Render);
/*  52 */   Setting<Boolean> solidBox = setting("SolidBox", true).whenAtMode(this.page, Page.Render);
/*  53 */   Setting<Color> solidBoxColor = setting("SolidBoxColor", new Color((new Color(255, 50, 50, 19)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 19)).whenTrue(this.solidBox).whenAtMode(this.page, Page.Render);
/*  54 */   Setting<Color> solidBoxCompletedColor = setting("SolidBoxCompletedColor", new Color((new Color(50, 255, 50, 19)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 19)).whenTrue(this.completedColor).whenTrue(this.solidBox).whenAtMode(this.page, Page.Render);
/*  55 */   Setting<Boolean> linesBox = setting("LinesBox", true).whenAtMode(this.page, Page.Render);
/*  56 */   Setting<Float> linesBoxWidth = setting("LinesBoxWidth", 1.0F, 1.0F, 5.0F).whenTrue(this.linesBox).whenAtMode(this.page, Page.Render);
/*  57 */   Setting<Color> linesBoxColor = setting("LinesBoxColor", new Color((new Color(255, 50, 50, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 175)).whenTrue(this.linesBox).whenAtMode(this.page, Page.Render);
/*  58 */   Setting<Color> linesBoxCompletedColor = setting("LinesBoxCompletedColor", new Color((new Color(50, 255, 50, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 175)).whenTrue(this.completedColor).whenTrue(this.linesBox).whenAtMode(this.page, Page.Render);
/*     */ 
/*     */   
/*     */   public String getModuleInfo() {
/*  62 */     return String.valueOf(this.mode.getValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  67 */     BlockUtil.packetMiningFlag = false;
/*  68 */     this.miningPos = null;
/*  69 */     this.moduleDisableFlag = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderWorld(RenderEvent event) {
/*  74 */     if (this.miningPos != null) {
/*  75 */       Color solidColor = ((Color)this.solidBoxColor.getValue()).getColorColor();
/*  76 */       Color linesColor = ((Color)this.linesBoxColor.getValue()).getColorColor();
/*  77 */       int bestSlot = ItemUtils.fastestMiningTool(mc.field_71441_e.func_180495_p(this.miningPos).func_177230_c());
/*  78 */       float progress = (float)(remainingTime() / BlockUtil.blockBrokenTime(this.miningPos, mc.field_71439_g.field_71071_by.func_70301_a(bestSlot)));
/*  79 */       float renderScale = 1.0F;
/*     */       
/*  81 */       switch ((ScaleMode)this.scaleMode.getValue()) {
/*     */         case PacketMine:
/*  83 */           renderScale = MathUtilFuckYou.interpNonLinear(1.0F, 0.0F, progress, ((Float)this.scaleFactor.getValue()).floatValue() / 15.0F);
/*     */           break;
/*     */ 
/*     */         
/*     */         case InstantMine:
/*  88 */           renderScale = MathUtilFuckYou.interpNonLinear(0.0F, 1.0F, progress, ((Float)this.scaleFactor.getValue()).floatValue() / 15.0F);
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/*  93 */       if (((Boolean)this.completedColor.getValue()).booleanValue() && MathUtilFuckYou.clamp(progress, 0.0F, 1.0F) < 1.0F - ((Float)this.completedProgress.getValue()).floatValue()) {
/*  94 */         solidColor = ColorUtil.colorShift(solidColor, ((Color)this.solidBoxCompletedColor.getValue()).getColorColor(), 300.0F * (1.0F - MathUtilFuckYou.clamp(progress, 0.0F, 1.0F) / (1.0F - ((Float)this.completedProgress.getValue()).floatValue())));
/*  95 */         linesColor = ColorUtil.colorShift(linesColor, ((Color)this.linesBoxCompletedColor.getValue()).getColorColor(), 300.0F * (1.0F - MathUtilFuckYou.clamp(progress, 0.0F, 1.0F) / (1.0F - ((Float)this.completedProgress.getValue()).floatValue())));
/*     */       } 
/*     */       
/*  98 */       if (((Boolean)this.solidBox.getValue()).booleanValue()) {
/*  99 */         SpartanTessellator.drawBlockBBFullBox(this.miningPos, renderScale, (new Color(solidColor.getRed(), solidColor.getGreen(), solidColor.getBlue(), ((Color)this.solidBoxColor.getValue()).getAlpha())).getRGB());
/*     */       }
/*     */       
/* 102 */       if (((Boolean)this.linesBox.getValue()).booleanValue()) {
/* 103 */         SpartanTessellator.drawBlockBBLineBox(this.miningPos, renderScale, ((Float)this.linesBoxWidth.getValue()).floatValue(), (new Color(linesColor.getRed(), linesColor.getGreen(), linesColor.getBlue(), ((Color)this.linesBoxColor.getValue()).getAlpha())).getRGB());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 110 */     if (((Boolean)this.spamPackets.getValue()).booleanValue() && this.miningPos != null && BlockUtil.isBlockPlaceable(this.miningPos) && this.mode.getValue() == Mode.PacketMine && BlockUtil.packetMiningFlag) {
/* 111 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.miningPos, this.miningFace));
/* 112 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.miningPos, this.miningFace));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderTick() {
/* 118 */     if (this.miningPos != null) {
/* 119 */       if (!BlockUtil.isBlockPlaceable(this.miningPos)) {
/* 120 */         BlockUtil.packetMiningFlag = false;
/* 121 */         this.miningPos = null;
/*     */         
/*     */         return;
/*     */       } 
/* 125 */       this.miningFace = BlockUtil.getVisibleBlockSide(new Vec3d(this.miningPos.field_177962_a + 0.5D, this.miningPos.field_177960_b, this.miningPos.field_177961_c + 0.5D));
/*     */ 
/*     */       
/* 128 */       if (this.miningFace != null) {
/*     */         int bestSlot;
/* 130 */         if (MathUtilFuckYou.getDistance(mc.field_71439_g.func_174791_d(), new Vec3d((Vec3i)this.miningPos)) > ((Float)this.range.getValue()).floatValue()) {
/* 131 */           this.miningPos = null;
/*     */           
/*     */           return;
/*     */         } 
/* 135 */         switch ((Mode)this.mode.getValue()) {
/*     */           case PacketMine:
/* 137 */             bestSlot = ItemUtils.fastestMiningTool(mc.field_71441_e.func_180495_p(this.miningPos).func_177230_c());
/*     */             
/* 139 */             if (remainingTime() <= 100.0D && remainingTime() >= -750.0D) {
/* 140 */               if (!this.flag) {
/* 141 */                 this.flag = true;
/* 142 */                 this.prevSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/*     */               } 
/*     */               
/* 145 */               switch ((Swap)this.swap.getValue()) {
/*     */                 case PacketMine:
/* 147 */                   ItemUtils.switchToSlot(bestSlot);
/* 148 */                   this.onMined = true;
/*     */                   break;
/*     */ 
/*     */                 
/*     */                 case InstantMine:
/* 153 */                   mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(bestSlot));
/* 154 */                   this.onMined = true;
/*     */                   break;
/*     */               } 
/*     */ 
/*     */             
/*     */             } 
/* 160 */             if (!this.flag2 && remainingTime() <= 100.0D && BlockUtil.isBlockPlaceable(this.miningPos) && this.swap.getValue() == Swap.None && mc.field_71439_g.field_71071_by.field_70461_c == bestSlot) {
/* 161 */               this.onMined = true;
/* 162 */               this.flag2 = true;
/*     */             } 
/*     */             
/* 165 */             if (this.swap.getValue() == Swap.None && !BlockUtil.isBlockPlaceable(this.miningPos)) {
/* 166 */               this.miningPos = null;
/*     */             }
/*     */             break;
/*     */ 
/*     */           
/*     */           case InstantMine:
/* 172 */             if (mc.field_71442_b != null && 
/* 173 */               remainingTime() <= 0.0D) {
/* 174 */               if (BlockUtil.isBlockPlaceable(this.miningPos) && this.instantMineTimer.passed(((Integer)this.instantMineDelay.getValue()).intValue())) {
/* 175 */                 bestSlot = ItemUtils.fastestMiningTool(mc.field_71441_e.func_180495_p(this.miningPos).func_177230_c());
/*     */                 
/* 177 */                 this.instantMineRotationFlag = true;
/*     */                 
/* 179 */                 if (!this.flag3) {
/* 180 */                   this.prevSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/*     */                 }
/*     */                 
/* 183 */                 switch ((Swap)this.swap.getValue()) {
/*     */                   case PacketMine:
/* 185 */                     ItemUtils.switchToSlot(bestSlot);
/* 186 */                     this.flag3 = true;
/*     */                     break;
/*     */ 
/*     */                   
/*     */                   case InstantMine:
/* 191 */                     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(bestSlot));
/* 192 */                     this.flag3 = true;
/*     */                     break;
/*     */                 } 
/*     */ 
/*     */                 
/* 197 */                 mc.field_71442_b.field_78778_j = false;
/* 198 */                 mc.field_71442_b.field_78781_i = 0;
/* 199 */                 mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.miningPos, this.miningFace));
/* 200 */                 this.instantMineTimer.reset(); break;
/*     */               } 
/* 202 */               if (this.flag3) {
/* 203 */                 switch ((Swap)this.swap.getValue()) {
/*     */                   case PacketMine:
/* 205 */                     ItemUtils.switchToSlot(this.prevSlot);
/*     */                     break;
/*     */ 
/*     */                   
/*     */                   case InstantMine:
/* 210 */                     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.prevSlot));
/*     */                     break;
/*     */                 } 
/*     */                 
/* 214 */                 this.flag3 = false;
/*     */               } 
/*     */             } 
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Listener
/*     */   public void onLeftClickBlock(LeftClickBlockEvent event) {
/* 227 */     if (this.miningPos != null && event.blockPos == this.miningPos) {
/* 228 */       event.cancel();
/*     */     }
/*     */     
/* 231 */     BlockPos tempMiningPos = (this.miningPos == null) ? new BlockPos(0, -99999, 0) : this.miningPos;
/*     */     
/* 233 */     if (mc.field_71441_e.func_180495_p(event.blockPos).func_177230_c() != Blocks.field_150357_h && mc.field_71441_e.func_180495_p(event.blockPos).func_177230_c() != Blocks.field_180401_cv && (remainingTime() <= 0.0D || event.blockPos.field_177962_a != tempMiningPos.field_177962_a || event.blockPos.field_177960_b != tempMiningPos.field_177960_b || event.blockPos.field_177961_c != tempMiningPos.field_177961_c)) {
/* 234 */       BlockUtil.mineBlock(event.blockPos, event.face, true);
/*     */       
/* 236 */       this.miningPos = event.blockPos;
/* 237 */       BlockUtil.packetMiningFlag = true;
/* 238 */       this.flag2 = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void onDamageBlock(DamageBlockEvent event) {
/* 244 */     if (this.mode.getValue() == Mode.PacketMine && event.blockPos == this.miningPos) {
/* 245 */       event.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void onUpdateWalkingPlayer(OnUpdateWalkingPlayerEvent event) {
/* 251 */     if (this.mode.getValue() == Mode.PacketMine && this.miningPos != null && this.miningFace != null && this.mode.getValue() == Mode.PacketMine && this.onMined) {
/* 252 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, this.miningPos, this.miningFace));
/* 253 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.miningPos, this.miningFace));
/* 254 */       mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*     */       
/* 256 */       if (((Boolean)this.rotate.getValue()).booleanValue()) {
/* 257 */         rotate();
/*     */       }
/*     */       
/* 260 */       if (this.swap.getValue() != Swap.None) {
/* 261 */         int bestSlot = ItemUtils.fastestMiningTool(mc.field_71441_e.func_180495_p(this.miningPos).func_177230_c());
/* 262 */         this.miningPos = null;
/*     */         
/* 264 */         if (this.flag) {
/* 265 */           if (this.prevSlot != bestSlot) {
/* 266 */             switch ((Swap)this.swap.getValue()) {
/*     */               case PacketMine:
/* 268 */                 ItemUtils.switchToSlot(this.prevSlot);
/*     */                 break;
/*     */ 
/*     */               
/*     */               case InstantMine:
/* 273 */                 mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.prevSlot));
/*     */                 break;
/*     */             } 
/*     */           
/*     */           }
/* 278 */           this.flag = false;
/*     */         } 
/*     */       } 
/*     */       
/* 282 */       this.onMined = false;
/*     */     } 
/*     */     
/* 285 */     if (this.instantMineRotationFlag && ((Boolean)this.rotate.getValue()).booleanValue()) {
/* 286 */       rotate();
/* 287 */       this.instantMineRotationFlag = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void rotate() {
/* 292 */     float[] rotats = RotationUtil.getRotations(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), BlockUtil.getBlockVecFaceCenter(this.miningPos, this.miningFace));
/* 293 */     RotationUtil.setYawAndPitchBlock(rotats[0], rotats[1]);
/*     */   }
/*     */   
/*     */   private double remainingTime() {
/* 297 */     if (this.miningPos == null) return 0.0D; 
/* 298 */     int bestSlot = ItemUtils.fastestMiningTool(mc.field_71441_e.func_180495_p(this.miningPos).func_177230_c());
/* 299 */     return BlockUtil.packetMineStartTime + BlockUtil.blockBrokenTime(this.miningPos, mc.field_71439_g.field_71071_by.func_70301_a(bestSlot)) - System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   enum Page {
/* 303 */     Mine,
/* 304 */     Render;
/*     */   }
/*     */   
/*     */   enum Mode {
/* 308 */     PacketMine,
/* 309 */     InstantMine;
/*     */   }
/*     */   
/*     */   enum Swap {
/* 313 */     None,
/* 314 */     Normal,
/* 315 */     Silent;
/*     */   }
/*     */   
/*     */   enum ScaleMode {
/* 319 */     Expand,
/* 320 */     Shrink,
/* 321 */     None;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\combat\MinePlus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */