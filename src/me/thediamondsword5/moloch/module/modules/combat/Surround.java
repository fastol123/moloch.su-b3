/*     */ package me.thediamondsword5.moloch.module.modules.combat;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.utils.BlockUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.network.play.server.SPacketBlockBreakAnim;
/*     */ import net.minecraft.network.play.server.SPacketBlockChange;
/*     */ import net.minecraft.network.play.server.SPacketMultiBlockChange;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.GameType;
/*     */ import net.minecraft.world.World;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.network.PacketEvent;
/*     */ import net.spartanb312.base.utils.CrystalUtil;
/*     */ import net.spartanb312.base.utils.EntityUtil;
/*     */ import net.spartanb312.base.utils.ItemUtils;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import net.spartanb312.base.utils.graphics.SpartanTessellator;
/*     */ import net.spartanb312.base.utils.math.Pair;
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "Surround", category = Category.COMBAT, description = "Put obsidian around your feet to protect them from crystal damage")
/*     */ public class Surround extends Module {
/*  35 */   Setting<Page> page = setting("Page", Page.Place);
/*  36 */   Setting<Integer> placeDelay = setting("PlaceDelay", 70, 0, 500).des("Delay between place attempts in milliseconds").whenAtMode(this.page, Page.Place);
/*  37 */   Setting<Integer> multiPlace = setting("MultiPlace", 4, 1, 5).des("Blocks to place at once").whenAtMode(this.page, Page.Place);
/*  38 */   Setting<Boolean> onPacket = setting("OnPacketBlockChange", true).des("Tries to place on SPacketBlockChange / SPacketMultiBlockChange").whenAtMode(this.page, Page.Place);
/*  39 */   Setting<Boolean> packetPlace = setting("PacketPlace", true).des("Uses packets to place blocks").whenAtMode(this.page, Page.Place);
/*  40 */   Setting<Boolean> antiGhostBlock = setting("AntiGhostBlock", true).des("Hits blocks after placing to remove it if its a client side only (ghost) block").whenAtMode(this.page, Page.Place);
/*  41 */   Setting<Boolean> rotate = setting("Rotate", false).des("Spoofs rotations to place blocks").whenAtMode(this.page, Page.Place);
/*  42 */   Setting<Boolean> center = setting("Center", false).des("Moves you to the center of the blockpos").whenAtMode(this.page, Page.Place);
/*  43 */   Setting<Boolean> disableOnLeaveHole = setting("DisableOnLeaveHole", true).des("Automatically disables module when you aren't in the same blockpos anymore").whenAtMode(this.page, Page.Place);
/*  44 */   Setting<Boolean> extend = setting("Extend", false).des("Extends surround if somebody tries to mine part of it to prevent being citied").whenAtMode(this.page, Page.Place);
/*  45 */   Setting<Boolean> breakCrystals = setting("BreakCrystals", true).des("Breaks crystals that are blocking surround").whenAtMode(this.page, Page.Place);
/*  46 */   Setting<Float> breakCrystalsDelay = setting("BreakCrystalsDelay", 50.0F, 0.0F, 1000.0F).des("Delay in milliseconds between attempts to break crystal").whenTrue(this.breakCrystals).whenAtMode(this.page, Page.Place);
/*  47 */   Setting<Boolean> antiSuicideCrystal = setting("AntiSuicideCrystal", true).des("Breaks crystal as long as it doesn't make you go below a certain health amount").whenTrue(this.breakCrystals).whenAtMode(this.page, Page.Place);
/*  48 */   Setting<Float> minHealthRemaining = setting("MinHealthRemain", 8.0F, 1.0F, 36.0F).des("Min health that crystal should leave you with after you break it").whenTrue(this.antiSuicideCrystal).whenTrue(this.breakCrystals).whenAtMode(this.page, Page.Place);
/*  49 */   Setting<Float> maxCrystalDamage = setting("MaxCrystalDamage", 11.0F, 0.0F, 36.0F).des("Don't break crystal if it could deal this much damage or more").whenFalse(this.antiSuicideCrystal).whenTrue(this.breakCrystals).whenAtMode(this.page, Page.Place);
/*  50 */   Setting<Boolean> onlyVisible = setting("OnlyVisible", false).des("Only tries to place on sides of blocks that you can see").whenAtMode(this.page, Page.Place);
/*  51 */   Setting<Boolean> useEnderChest = setting("UseEnderChest", false).des("Uses ender chests when you run out of obsidian").whenAtMode(this.page, Page.Place);
/*     */   
/*  53 */   Setting<Boolean> render = setting("RenderPlacePos", true).des("Render a box for positions to be placed in").whenAtMode(this.page, Page.Render);
/*  54 */   Setting<Boolean> fade = setting("Fade", false).des("Fades alpha of render after blocks are placed").whenTrue(this.render).whenAtMode(this.page, Page.Render);
/*  55 */   Setting<Float> fadeSpeed = setting("FadeSpeed", 2.0F, 0.1F, 3.0F).des("Fade speed of render").whenTrue(this.fade).whenTrue(this.render).whenAtMode(this.page, Page.Render);
/*  56 */   Setting<Boolean> solid = setting("Solid", true).whenTrue(this.render).whenAtMode(this.page, Page.Render);
/*  57 */   Setting<Color> solidColor = setting("SolidColor", new Color((new Color(100, 61, 255, 19)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 61, 255, 19)).whenTrue(this.render).whenTrue(this.solid).whenAtMode(this.page, Page.Render);
/*  58 */   Setting<Boolean> lines = setting("Lines", true).whenTrue(this.render).whenAtMode(this.page, Page.Render);
/*  59 */   Setting<Float> linesWidth = setting("LinesWidth", 1.0F, 1.0F, 5.0F).whenTrue(this.render).whenTrue(this.lines).whenAtMode(this.page, Page.Render);
/*  60 */   Setting<Color> linesColor = setting("LinesColor", new Color((new Color(100, 61, 255, 101)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 61, 255, 101)).whenTrue(this.render).whenTrue(this.lines).whenAtMode(this.page, Page.Render);
/*     */   
/*  62 */   private final Timer placeTimer = new Timer();
/*  63 */   private final Timer fadeTimer = new Timer();
/*  64 */   private final Timer breakCrystalsTimer = new Timer();
/*  65 */   private final HashMap<BlockPos, Float> toRenderPos = new HashMap<>();
/*  66 */   private final HashMap<BlockPos, Boolean> onPacketPlaceFlagMap = new HashMap<>();
/*  67 */   private BlockPos currentPlayerPos = null;
/*     */   
/*     */   private boolean centeredFlag = false;
/*     */   private boolean isTickPlacingFlag = false;
/*     */   
/*     */   public void onRenderWorld(RenderEvent event) {
/*  73 */     if (((Boolean)this.render.getValue()).booleanValue()) {
/*  74 */       int passedms = (int)this.fadeTimer.hasPassed();
/*  75 */       this.fadeTimer.reset();
/*  76 */       for (Map.Entry<BlockPos, Float> entry : (new HashMap<>(this.toRenderPos)).entrySet()) {
/*  77 */         if (((Float)entry.getValue()).floatValue() <= 0.0F) {
/*  78 */           this.toRenderPos.remove(entry.getKey());
/*     */           
/*     */           continue;
/*     */         } 
/*  82 */         if (((Boolean)this.solid.getValue()).booleanValue()) {
/*  83 */           SpartanTessellator.drawBlockFullBox(new Vec3d((Vec3i)entry.getKey()), false, 1.0F, (new Color(((Color)this.solidColor.getValue()).getColorColor().getRed(), ((Color)this.solidColor.getValue()).getColorColor().getGreen(), ((Color)this.solidColor.getValue()).getColorColor().getBlue(), (int)(((Color)this.solidColor.getValue()).getAlpha() * ((Float)entry.getValue()).floatValue() / 300.0F))).getRGB());
/*     */         }
/*     */         
/*  86 */         if (((Boolean)this.lines.getValue()).booleanValue()) {
/*  87 */           SpartanTessellator.drawBlockLineBox(new Vec3d((Vec3i)entry.getKey()), false, 1.0F, ((Float)this.linesWidth.getValue()).floatValue(), (new Color(((Color)this.linesColor.getValue()).getColorColor().getRed(), ((Color)this.linesColor.getValue()).getColorColor().getGreen(), ((Color)this.linesColor.getValue()).getColorColor().getBlue(), (int)(((Color)this.linesColor.getValue()).getAlpha() * ((Float)entry.getValue()).floatValue() / 300.0F))).getRGB());
/*     */         }
/*     */         
/*  90 */         if (((Boolean)this.fade.getValue()).booleanValue() && 
/*  91 */           passedms < 1000) {
/*  92 */           this.toRenderPos.put(entry.getKey(), Float.valueOf(((Float)entry.getValue()).floatValue() - passedms * ((Float)this.fadeSpeed.getValue()).floatValue()));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 101 */     this.onPacketPlaceFlagMap.clear();
/* 102 */     this.currentPlayerPos = null;
/* 103 */     this.centeredFlag = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 108 */     if (!this.isTickPlacingFlag && mc.field_71441_e != null && mc.field_71439_g != null) {
/* 109 */       if (((Boolean)this.extend.getValue()).booleanValue() && event.getPacket() instanceof SPacketBlockBreakAnim) {
/* 110 */         SPacketBlockBreakAnim packet = (SPacketBlockBreakAnim)event.getPacket();
/* 111 */         BlockPos playerPos = new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(Math.round(mc.field_71439_g.field_70163_u)), Math.floor(mc.field_71439_g.field_70161_v));
/*     */         
/* 113 */         if (placePoses(false).contains(packet.func_179821_b())) {
/* 114 */           BlockPos extendedPos = new BlockPos(((packet.func_179821_b()).field_177962_a * 2.0F - playerPos.field_177962_a), ((packet.func_179821_b()).field_177960_b * 2.0F - playerPos.field_177960_b), ((packet.func_179821_b()).field_177961_c * 2.0F - playerPos.field_177961_c));
/* 115 */           extendedPos = BlockUtil.extrudeBlock(extendedPos, EnumFacing.DOWN);
/*     */           
/* 117 */           if (BlockUtil.isFacePlaceble(extendedPos, EnumFacing.UP, true)) {
/*     */             
/* 119 */             if (((Boolean)this.breakCrystals.getValue()).booleanValue()) {
/* 120 */               CrystalUtil.breakBlockingCrystals(mc.field_71441_e.func_180495_p(BlockUtil.extrudeBlock(extendedPos, EnumFacing.UP)).func_185918_c((World)mc.field_71441_e, BlockUtil.extrudeBlock(extendedPos, EnumFacing.UP)), ((Boolean)this.antiSuicideCrystal.getValue()).booleanValue(), ((Float)this.minHealthRemaining.getValue()).floatValue(), ((Float)this.maxCrystalDamage.getValue()).floatValue(), ((Boolean)this.rotate.getValue()).booleanValue());
/*     */             }
/*     */             
/* 123 */             int prevSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 124 */             if (ItemUtils.isItemInHotbar(Item.func_150898_a(Blocks.field_150343_Z))) {
/* 125 */               ItemUtils.switchToSlot(ItemUtils.findBlockInHotBar(Blocks.field_150343_Z));
/*     */             }
/* 127 */             else if (((Boolean)this.useEnderChest.getValue()).booleanValue() && ItemUtils.isItemInHotbar(Item.func_150898_a(Blocks.field_150477_bB))) {
/* 128 */               ItemUtils.switchToSlot(ItemUtils.findBlockInHotBar(Blocks.field_150477_bB));
/*     */             } 
/*     */             
/* 131 */             BlockUtil.placeBlock(extendedPos, EnumFacing.UP, ((Boolean)this.packetPlace.getValue()).booleanValue(), false, ((Boolean)this.rotate.getValue()).booleanValue());
/* 132 */             if (((Boolean)this.antiGhostBlock.getValue()).booleanValue() && mc.field_71442_b.field_78779_k != GameType.CREATIVE) mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, BlockUtil.extrudeBlock(extendedPos, EnumFacing.UP), BlockUtil.getVisibleBlockSide(new Vec3d((Vec3i)BlockUtil.extrudeBlock(extendedPos, EnumFacing.UP))))); 
/* 133 */             if (((Boolean)this.render.getValue()).booleanValue()) this.toRenderPos.put(BlockUtil.extrudeBlock(extendedPos, EnumFacing.UP), Float.valueOf(300.0F));
/*     */             
/* 135 */             ItemUtils.switchToSlot(prevSlot);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 140 */       if (((Boolean)this.onPacket.getValue()).booleanValue()) {
/* 141 */         if (event.getPacket() instanceof SPacketBlockChange) {
/*     */           
/* 143 */           BlockPos playerPos = new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor((Math.round(mc.field_71439_g.field_70163_u) - 1L)), Math.floor(mc.field_71439_g.field_70161_v));
/* 144 */           if (!placePoses(true).contains(((SPacketBlockChange)event.packet).func_179827_b()))
/*     */             return; 
/* 146 */           boolean flag1 = false;
/* 147 */           for (Map.Entry<BlockPos, Boolean> entry : this.onPacketPlaceFlagMap.entrySet()) {
/* 148 */             if (BlockUtil.isSameBlockPos(entry.getKey(), ((SPacketBlockChange)event.packet).func_179827_b())) {
/* 149 */               flag1 = true;
/* 150 */               if (!((Boolean)entry.getValue()).booleanValue())
/*     */                 return; 
/* 152 */               this.onPacketPlaceFlagMap.put(entry.getKey(), Boolean.valueOf(false));
/*     */             } 
/*     */           } 
/*     */           
/* 156 */           if (!flag1) {
/*     */             return;
/*     */           }
/*     */           
/* 160 */           if (!((SPacketBlockChange)event.packet).func_180728_a().func_185904_a().func_76222_j())
/*     */             return; 
/* 162 */           Pair<BlockPos, EnumFacing> data = getPlaceData(playerPos, ((SPacketBlockChange)event.packet).func_179827_b());
/* 163 */           if (data == null)
/*     */             return; 
/* 165 */           if (!((Boolean)this.onlyVisible.getValue()).booleanValue() && data.a == playerPos && BlockUtil.isBlockPlaceable(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b), EnumFacing.UP))) {
/*     */             return;
/*     */           }
/*     */           
/* 169 */           if (mc.field_71441_e.func_180495_p(BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b)).func_185918_c((World)mc.field_71441_e, BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b))
/* 170 */             .func_72326_a(mc.field_71439_g.func_174813_aQ())) {
/*     */             return;
/*     */           }
/*     */           
/* 174 */           EntityUtil.entitiesListFlag = true;
/* 175 */           boolean flag = false;
/* 176 */           for (Entity entity : EntityUtil.entitiesList()) {
/* 177 */             if (entity == mc.field_71439_g || !(entity instanceof net.minecraft.entity.player.EntityPlayer)) {
/*     */               continue;
/*     */             }
/*     */             
/* 181 */             if (mc.field_71441_e.func_180495_p(BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b)).func_185918_c((World)mc.field_71441_e, BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b))
/* 182 */               .func_72326_a(entity.func_174813_aQ())) {
/* 183 */               flag = true;
/*     */             }
/*     */           } 
/* 186 */           EntityUtil.entitiesListFlag = false;
/* 187 */           if (flag) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 192 */           int prevSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 193 */           if (ItemUtils.isItemInHotbar(Item.func_150898_a(Blocks.field_150343_Z))) {
/* 194 */             ItemUtils.switchToSlot(ItemUtils.findBlockInHotBar(Blocks.field_150343_Z));
/*     */           }
/* 196 */           else if (((Boolean)this.useEnderChest.getValue()).booleanValue() && ItemUtils.isItemInHotbar(Item.func_150898_a(Blocks.field_150477_bB))) {
/* 197 */             ItemUtils.switchToSlot(ItemUtils.findBlockInHotBar(Blocks.field_150477_bB));
/*     */           } 
/*     */           
/* 200 */           BlockUtil.placeBlock((BlockPos)data.a, (EnumFacing)data.b, ((Boolean)this.packetPlace.getValue()).booleanValue(), false, ((Boolean)this.rotate.getValue()).booleanValue());
/* 201 */           if (((Boolean)this.antiGhostBlock.getValue()).booleanValue() && mc.field_71442_b.field_78779_k != GameType.CREATIVE) mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b), BlockUtil.getVisibleBlockSide(new Vec3d((Vec3i)BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b))))); 
/* 202 */           if (((Boolean)this.render.getValue()).booleanValue()) this.toRenderPos.put(BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b), Float.valueOf(300.0F));
/*     */           
/* 204 */           ItemUtils.switchToSlot(prevSlot);
/*     */         } 
/*     */         
/* 207 */         if (event.getPacket() instanceof SPacketMultiBlockChange) {
/* 208 */           BlockPos playerPos = new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor((Math.round(mc.field_71439_g.field_70163_u) - 1L)), Math.floor(mc.field_71439_g.field_70161_v));
/* 209 */           int index = 0;
/*     */           
/* 211 */           int prevSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 212 */           if (ItemUtils.isItemInHotbar(Item.func_150898_a(Blocks.field_150343_Z))) {
/* 213 */             ItemUtils.switchToSlot(ItemUtils.findBlockInHotBar(Blocks.field_150343_Z));
/*     */           }
/* 215 */           else if (((Boolean)this.useEnderChest.getValue()).booleanValue() && ItemUtils.isItemInHotbar(Item.func_150898_a(Blocks.field_150477_bB))) {
/* 216 */             ItemUtils.switchToSlot(ItemUtils.findBlockInHotBar(Blocks.field_150477_bB));
/*     */           } 
/*     */           
/* 219 */           for (SPacketMultiBlockChange.BlockUpdateData blockUpdateData : ((SPacketMultiBlockChange)event.getPacket()).func_179844_a()) {
/* 220 */             if (placePoses(true).contains(blockUpdateData.func_180090_a())) {
/*     */               
/* 222 */               boolean flag1 = false;
/* 223 */               boolean flag2 = false;
/* 224 */               for (Map.Entry<BlockPos, Boolean> entry : this.onPacketPlaceFlagMap.entrySet()) {
/* 225 */                 if (BlockUtil.isSameBlockPos(entry.getKey(), ((SPacketBlockChange)event.packet).func_179827_b())) {
/* 226 */                   flag2 = true;
/* 227 */                   if (!((Boolean)entry.getValue()).booleanValue()) { flag1 = true; continue; }
/*     */                   
/* 229 */                   this.onPacketPlaceFlagMap.put(entry.getKey(), Boolean.valueOf(false));
/*     */                 } 
/*     */               } 
/*     */               
/* 233 */               if (!flag1 && flag2) {
/*     */                 
/* 235 */                 if (this.onPacketPlaceFlagMap.get(blockUpdateData.func_180090_a()) == null || 
/* 236 */                   !((Boolean)this.onPacketPlaceFlagMap.get(blockUpdateData.func_180090_a())).booleanValue())
/* 237 */                   return;  this.onPacketPlaceFlagMap.put(blockUpdateData.func_180090_a(), Boolean.valueOf(false));
/*     */                 
/* 239 */                 if (blockUpdateData.func_180088_c().func_185904_a().func_76222_j())
/*     */                 
/* 241 */                 { Pair<BlockPos, EnumFacing> data = getPlaceData(playerPos, blockUpdateData.func_180090_a());
/* 242 */                   if (data != null)
/*     */                   {
/* 244 */                     if (index < ((Integer)this.multiPlace.getValue()).intValue())
/*     */                     {
/*     */ 
/*     */                       
/* 248 */                       if (((Boolean)this.onlyVisible.getValue()).booleanValue() || data.a != playerPos || !BlockUtil.isBlockPlaceable(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b), EnumFacing.UP)))
/*     */                       {
/*     */ 
/*     */ 
/*     */                         
/* 253 */                         if (!mc.field_71441_e.func_180495_p(BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b)).func_185918_c((World)mc.field_71441_e, BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b)).func_72326_a(mc.field_71439_g.func_174813_aQ()))
/*     */                         
/*     */                         { 
/*     */                           
/* 257 */                           EntityUtil.entitiesListFlag = true;
/* 258 */                           boolean flag = false;
/* 259 */                           for (Entity entity : EntityUtil.entitiesList()) {
/* 260 */                             if (entity == mc.field_71439_g || !(entity instanceof net.minecraft.entity.player.EntityPlayer)) {
/*     */                               continue;
/*     */                             }
/*     */                             
/* 264 */                             if (mc.field_71441_e.func_180495_p(BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b)).func_185918_c((World)mc.field_71441_e, BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b))
/* 265 */                               .func_72326_a(entity.func_174813_aQ())) {
/* 266 */                               flag = true;
/*     */                             }
/*     */                           } 
/* 269 */                           EntityUtil.entitiesListFlag = false;
/* 270 */                           if (!flag)
/*     */                           
/*     */                           { 
/*     */                             
/* 274 */                             BlockUtil.placeBlock((BlockPos)data.a, (EnumFacing)data.b, ((Boolean)this.packetPlace.getValue()).booleanValue(), false, ((Boolean)this.rotate.getValue()).booleanValue());
/* 275 */                             if (((Boolean)this.antiGhostBlock.getValue()).booleanValue() && mc.field_71442_b.field_78779_k != GameType.CREATIVE) mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b), BlockUtil.getVisibleBlockSide(new Vec3d((Vec3i)BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b))))); 
/* 276 */                             if (((Boolean)this.render.getValue()).booleanValue()) this.toRenderPos.put(BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b), Float.valueOf(300.0F));
/*     */                             
/* 278 */                             index++; }  }  }  }  }  } 
/*     */               } 
/*     */             } 
/* 281 */           }  ItemUtils.switchToSlot(prevSlot);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 289 */     if (mc.field_71441_e != null && mc.field_71439_g != null && ((Boolean)this.onPacket.getValue()).booleanValue()) {
/* 290 */       for (BlockPos pos : placePoses(true)) {
/* 291 */         if (!mc.field_71441_e.func_180495_p(pos).func_177230_c().func_176200_f((IBlockAccess)mc.field_71441_e, pos)) {
/* 292 */           this.onPacketPlaceFlagMap.put(pos, Boolean.valueOf(true));
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 297 */     if (mc.field_71441_e != null && mc.field_71439_g != null && this.breakCrystalsTimer.passed(((Float)this.breakCrystalsDelay.getValue()).floatValue()) && ((Boolean)this.breakCrystals.getValue()).booleanValue()) {
/* 298 */       for (BlockPos pos : placePoses(true)) {
/* 299 */         CrystalUtil.breakBlockingCrystals(mc.field_71441_e.func_180495_p(pos).func_185918_c((World)mc.field_71441_e, pos), ((Boolean)this.antiSuicideCrystal.getValue()).booleanValue(), ((Float)this.minHealthRemaining.getValue()).floatValue(), ((Float)this.maxCrystalDamage.getValue()).floatValue(), ((Boolean)this.rotate.getValue()).booleanValue());
/*     */       }
/* 301 */       this.breakCrystalsTimer.reset();
/*     */     } 
/*     */     
/* 304 */     tickPlace();
/*     */   }
/*     */   
/*     */   private void tickPlace() {
/* 308 */     if (mc.field_71441_e != null && mc.field_71439_g != null && this.placeTimer.passed(((Integer)this.placeDelay.getValue()).intValue())) {
/* 309 */       if (((Boolean)this.render.getValue()).booleanValue() && !((Boolean)this.fade.getValue()).booleanValue()) this.toRenderPos.clear();
/*     */       
/* 311 */       if (!ItemUtils.isItemInHotbar(Item.func_150898_a(Blocks.field_150343_Z)) && (!((Boolean)this.useEnderChest.getValue()).booleanValue() || !ItemUtils.isItemInHotbar(Item.func_150898_a(Blocks.field_150477_bB)))) {
/* 312 */         toggle();
/* 313 */         ChatUtil.sendNoSpamErrorMessage("No blocks to place!");
/*     */         
/*     */         return;
/*     */       } 
/* 317 */       if (((Boolean)this.center.getValue()).booleanValue() && !this.centeredFlag) {
/* 318 */         EntityUtil.setCenter();
/* 319 */         this.centeredFlag = true;
/*     */       } 
/*     */       
/* 322 */       BlockPos playerPos = new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor((Math.round(mc.field_71439_g.field_70163_u) - 1L)), Math.floor(mc.field_71439_g.field_70161_v));
/* 323 */       if (this.currentPlayerPos == null) this.currentPlayerPos = playerPos;
/*     */       
/* 325 */       if (((Boolean)this.disableOnLeaveHole.getValue()).booleanValue() && !BlockUtil.isSameBlockPos(this.currentPlayerPos, new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor((Math.round(mc.field_71439_g.field_70163_u) - 1L)), Math.floor(mc.field_71439_g.field_70161_v)))) {
/* 326 */         toggle();
/*     */         
/*     */         return;
/*     */       } 
/* 330 */       this.isTickPlacingFlag = true;
/* 331 */       List<Pair<BlockPos, EnumFacing>> list = new ArrayList<>();
/*     */       
/* 333 */       int index = 0;
/* 334 */       for (Pair<BlockPos, EnumFacing> data : ((Boolean)this.onlyVisible.getValue()).booleanValue() ? visiblePlacePos(playerPos) : placePos(playerPos)) {
/* 335 */         if (index < ((Integer)this.multiPlace.getValue()).intValue())
/*     */         {
/*     */ 
/*     */           
/* 339 */           if (((Boolean)this.onlyVisible.getValue()).booleanValue() || data.a != playerPos || !BlockUtil.isBlockPlaceable(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b), EnumFacing.UP)))
/*     */           {
/*     */ 
/*     */             
/* 343 */             if (BlockUtil.isFacePlaceble((BlockPos)data.a, (EnumFacing)data.b, false))
/*     */             {
/*     */ 
/*     */ 
/*     */               
/* 348 */               if (!mc.field_71441_e.func_180495_p(BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b)).func_185918_c((World)mc.field_71441_e, BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b)).func_72326_a(mc.field_71439_g.func_174813_aQ())) {
/*     */ 
/*     */ 
/*     */                 
/* 352 */                 EntityUtil.entitiesListFlag = true;
/* 353 */                 boolean flag = false;
/* 354 */                 for (Entity entity : EntityUtil.entitiesList()) {
/* 355 */                   if (entity == mc.field_71439_g || !(entity instanceof net.minecraft.entity.player.EntityPlayer)) {
/*     */                     continue;
/*     */                   }
/*     */                   
/* 359 */                   if (mc.field_71441_e.func_180495_p(BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b)).func_185918_c((World)mc.field_71441_e, BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b))
/* 360 */                     .func_72326_a(entity.func_174813_aQ())) {
/* 361 */                     flag = true;
/*     */                   }
/*     */                 } 
/* 364 */                 EntityUtil.entitiesListFlag = false;
/* 365 */                 if (!flag) {
/*     */ 
/*     */ 
/*     */                   
/* 369 */                   list.add(new Pair(data.a, data.b));
/*     */                   
/* 371 */                   if (((Boolean)this.render.getValue()).booleanValue()) this.toRenderPos.put(BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b), Float.valueOf(300.0F));
/*     */                   
/* 373 */                   index++;
/*     */                 } 
/*     */               }  }  }  } 
/* 376 */       }  if (!list.isEmpty()) {
/* 377 */         int prevSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 378 */         if (ItemUtils.isItemInHotbar(Item.func_150898_a(Blocks.field_150343_Z))) {
/* 379 */           ItemUtils.switchToSlot(ItemUtils.findBlockInHotBar(Blocks.field_150343_Z));
/*     */         }
/* 381 */         else if (((Boolean)this.useEnderChest.getValue()).booleanValue() && ItemUtils.isItemInHotbar(Item.func_150898_a(Blocks.field_150477_bB))) {
/* 382 */           ItemUtils.switchToSlot(ItemUtils.findBlockInHotBar(Blocks.field_150477_bB));
/*     */         } 
/*     */         
/* 385 */         list.forEach(data -> {
/*     */               BlockUtil.placeBlock((BlockPos)data.a, (EnumFacing)data.b, ((Boolean)this.packetPlace.getValue()).booleanValue(), false, ((Boolean)this.rotate.getValue()).booleanValue()); if (((Boolean)this.antiGhostBlock.getValue()).booleanValue() && mc.field_71442_b.field_78779_k != GameType.CREATIVE)
/*     */                 mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b), BlockUtil.getVisibleBlockSide(new Vec3d((Vec3i)BlockUtil.extrudeBlock((BlockPos)data.a, (EnumFacing)data.b))))); 
/*     */             });
/* 389 */         this.placeTimer.reset();
/*     */         
/* 391 */         ItemUtils.switchToSlot(prevSlot);
/*     */       } 
/* 393 */       this.isTickPlacingFlag = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Pair<BlockPos, EnumFacing>[] placePos(BlockPos pos) {
/* 398 */     return (Pair<BlockPos, EnumFacing>[])new Pair[] { new Pair(new BlockPos(pos.field_177962_a + 1, pos.field_177960_b, pos.field_177961_c), EnumFacing.UP), new Pair(new BlockPos(pos.field_177962_a - 1, pos.field_177960_b, pos.field_177961_c), EnumFacing.UP), new Pair(new BlockPos(pos.field_177962_a, pos.field_177960_b, pos.field_177961_c + 1), EnumFacing.UP), new Pair(new BlockPos(pos.field_177962_a, pos.field_177960_b, pos.field_177961_c - 1), EnumFacing.UP), new Pair(new BlockPos(pos.field_177962_a + 2, pos.field_177960_b + 1, pos.field_177961_c), EnumFacing.WEST), new Pair(new BlockPos(pos.field_177962_a - 2, pos.field_177960_b + 1, pos.field_177961_c), EnumFacing.EAST), new Pair(new BlockPos(pos.field_177962_a, pos.field_177960_b + 1, pos.field_177961_c + 2), EnumFacing.NORTH), new Pair(new BlockPos(pos.field_177962_a, pos.field_177960_b + 1, pos.field_177961_c - 2), EnumFacing.SOUTH), new Pair(pos, EnumFacing.EAST), new Pair(pos, EnumFacing.WEST), new Pair(pos, EnumFacing.SOUTH), new Pair(pos, EnumFacing.NORTH) };
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
/*     */ 
/*     */   
/*     */   private Pair<BlockPos, EnumFacing>[] visiblePlacePos(BlockPos pos) {
/* 416 */     return (Pair<BlockPos, EnumFacing>[])new Pair[] { new Pair(new BlockPos(pos.field_177962_a + 1, pos.field_177960_b, pos.field_177961_c), EnumFacing.UP), new Pair(new BlockPos(pos.field_177962_a - 1, pos.field_177960_b, pos.field_177961_c), EnumFacing.UP), new Pair(new BlockPos(pos.field_177962_a, pos.field_177960_b, pos.field_177961_c + 1), EnumFacing.UP), new Pair(new BlockPos(pos.field_177962_a, pos.field_177960_b, pos.field_177961_c - 1), EnumFacing.UP), new Pair(new BlockPos(pos.field_177962_a + 2, pos.field_177960_b + 1, pos.field_177961_c), EnumFacing.WEST), new Pair(new BlockPos(pos.field_177962_a - 2, pos.field_177960_b + 1, pos.field_177961_c), EnumFacing.EAST), new Pair(new BlockPos(pos.field_177962_a, pos.field_177960_b + 1, pos.field_177961_c + 2), EnumFacing.NORTH), new Pair(new BlockPos(pos.field_177962_a, pos.field_177960_b + 1, pos.field_177961_c - 2), EnumFacing.SOUTH) };
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
/*     */   private Pair<BlockPos, EnumFacing> getPlaceData(BlockPos playerPos, BlockPos pos) {
/* 429 */     if (!((Boolean)this.onlyVisible.getValue()).booleanValue()) {
/* 430 */       if (BlockUtil.isSameBlockPos(pos, new BlockPos(playerPos.field_177962_a + 1, playerPos.field_177960_b, playerPos.field_177961_c))) {
/* 431 */         return new Pair(playerPos, EnumFacing.WEST);
/*     */       }
/* 433 */       if (BlockUtil.isSameBlockPos(pos, new BlockPos(playerPos.field_177962_a - 1, playerPos.field_177960_b, playerPos.field_177961_c))) {
/* 434 */         return new Pair(playerPos, EnumFacing.EAST);
/*     */       }
/* 436 */       if (BlockUtil.isSameBlockPos(pos, new BlockPos(playerPos.field_177962_a, playerPos.field_177960_b, playerPos.field_177961_c + 1))) {
/* 437 */         return new Pair(playerPos, EnumFacing.NORTH);
/*     */       }
/* 439 */       if (BlockUtil.isSameBlockPos(pos, new BlockPos(playerPos.field_177962_a, playerPos.field_177960_b, playerPos.field_177961_c - 1))) {
/* 440 */         return new Pair(playerPos, EnumFacing.SOUTH);
/*     */       }
/*     */     } 
/*     */     
/* 444 */     if (BlockUtil.isSameBlockPos(pos, new BlockPos(playerPos.field_177962_a + 1, playerPos.field_177960_b + 1, playerPos.field_177961_c))) {
/* 445 */       Pair<BlockPos, EnumFacing> toPlacePos1 = new Pair(BlockUtil.extrudeBlock(playerPos, EnumFacing.EAST), EnumFacing.UP);
/* 446 */       Pair<BlockPos, EnumFacing> toPlacePos2 = new Pair(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock((BlockPos)toPlacePos1.a, EnumFacing.EAST), EnumFacing.UP), EnumFacing.WEST);
/* 447 */       if (BlockUtil.isBlockPlaceable((BlockPos)toPlacePos1.a)) {
/* 448 */         return toPlacePos1;
/*     */       }
/*     */       
/* 451 */       return toPlacePos2;
/*     */     } 
/*     */     
/* 454 */     if (BlockUtil.isSameBlockPos(pos, new BlockPos(playerPos.field_177962_a - 1, playerPos.field_177960_b + 1, playerPos.field_177961_c))) {
/* 455 */       Pair<BlockPos, EnumFacing> toPlacePos1 = new Pair(BlockUtil.extrudeBlock(playerPos, EnumFacing.WEST), EnumFacing.UP);
/* 456 */       Pair<BlockPos, EnumFacing> toPlacePos2 = new Pair(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock((BlockPos)toPlacePos1.a, EnumFacing.WEST), EnumFacing.UP), EnumFacing.EAST);
/* 457 */       if (BlockUtil.isBlockPlaceable((BlockPos)toPlacePos1.a)) {
/* 458 */         return toPlacePos1;
/*     */       }
/*     */       
/* 461 */       return toPlacePos2;
/*     */     } 
/*     */     
/* 464 */     if (BlockUtil.isSameBlockPos(pos, new BlockPos(playerPos.field_177962_a, playerPos.field_177960_b + 1, playerPos.field_177961_c + 1))) {
/* 465 */       Pair<BlockPos, EnumFacing> toPlacePos1 = new Pair(BlockUtil.extrudeBlock(playerPos, EnumFacing.SOUTH), EnumFacing.UP);
/* 466 */       Pair<BlockPos, EnumFacing> toPlacePos2 = new Pair(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock((BlockPos)toPlacePos1.a, EnumFacing.SOUTH), EnumFacing.UP), EnumFacing.NORTH);
/* 467 */       if (BlockUtil.isBlockPlaceable((BlockPos)toPlacePos1.a)) {
/* 468 */         return toPlacePos1;
/*     */       }
/*     */       
/* 471 */       return toPlacePos2;
/*     */     } 
/*     */     
/* 474 */     if (BlockUtil.isSameBlockPos(pos, new BlockPos(playerPos.field_177962_a, playerPos.field_177960_b + 1, playerPos.field_177961_c - 1))) {
/* 475 */       Pair<BlockPos, EnumFacing> toPlacePos1 = new Pair(BlockUtil.extrudeBlock(playerPos, EnumFacing.NORTH), EnumFacing.UP);
/* 476 */       Pair<BlockPos, EnumFacing> toPlacePos2 = new Pair(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock((BlockPos)toPlacePos1.a, EnumFacing.NORTH), EnumFacing.UP), EnumFacing.SOUTH);
/* 477 */       if (BlockUtil.isBlockPlaceable((BlockPos)toPlacePos1.a)) {
/* 478 */         return toPlacePos1;
/*     */       }
/*     */       
/* 481 */       return toPlacePos2;
/*     */     } 
/*     */ 
/*     */     
/* 485 */     return null;
/*     */   }
/*     */   
/*     */   private List<BlockPos> placePoses(boolean includeBottom) {
/* 489 */     List<BlockPos> list = new ArrayList<>();
/* 490 */     BlockPos pos = new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor((Math.round(mc.field_71439_g.field_70163_u) - 1L)), Math.floor(mc.field_71439_g.field_70161_v));
/*     */     
/* 492 */     if (includeBottom) {
/* 493 */       list.add(new BlockPos(pos.field_177962_a + 1, pos.field_177960_b, pos.field_177961_c));
/* 494 */       list.add(new BlockPos(pos.field_177962_a - 1, pos.field_177960_b, pos.field_177961_c));
/* 495 */       list.add(new BlockPos(pos.field_177962_a, pos.field_177960_b, pos.field_177961_c + 1));
/* 496 */       list.add(new BlockPos(pos.field_177962_a, pos.field_177960_b, pos.field_177961_c - 1));
/*     */     } 
/* 498 */     list.add(new BlockPos(pos.field_177962_a + 1, pos.field_177960_b + 1, pos.field_177961_c));
/* 499 */     list.add(new BlockPos(pos.field_177962_a - 1, pos.field_177960_b + 1, pos.field_177961_c));
/* 500 */     list.add(new BlockPos(pos.field_177962_a, pos.field_177960_b + 1, pos.field_177961_c + 1));
/* 501 */     list.add(new BlockPos(pos.field_177962_a, pos.field_177960_b + 1, pos.field_177961_c - 1));
/*     */     
/* 503 */     return list;
/*     */   }
/*     */   
/*     */   enum Page {
/* 507 */     Place,
/* 508 */     Render;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\combat\Surround.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */