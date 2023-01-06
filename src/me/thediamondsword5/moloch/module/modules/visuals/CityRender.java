/*     */ package me.thediamondsword5.moloch.module.modules.visuals;
/*     */ import java.awt.Color;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.utils.BlockUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.spartanb312.base.client.FriendManager;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.render.RenderEvent;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.utils.CrystalUtil;
/*     */ import net.spartanb312.base.utils.EntityUtil;
/*     */ import net.spartanb312.base.utils.MathUtilFuckYou;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import net.spartanb312.base.utils.graphics.SpartanTessellator;
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "CityRender", category = Category.VISUALS, description = "Renders stuff to indicate blocks that can be used to city someone (mined out to crystal them)")
/*     */ public class CityRender extends Module {
/*  26 */   Setting<Float> range = setting("Range", 6.0F, 0.0F, 15.0F).des("Range to start checking players for cityable blocks");
/*  27 */   Setting<Boolean> checkDiagonalCity = setting("DiagonalCity", true).des("Checks if a player can be citied diagonally instead of just directly next to the player");
/*  28 */   Setting<Boolean> oneBlockCrystalMode = setting("1.13+", false).des("Uses 1.13+ crystal placements to find cityable blocks where crystals can be placed in one block spaces");
/*  29 */   Setting<Boolean> self = setting("Self", true).des("Render cityable blocks for yourself");
/*  30 */   Setting<Boolean> ignoreFriends = setting("IgnoreFriends", true).des("Dont render cityable blocks for friends");
/*  31 */   Setting<Boolean> fade = setting("Fade", true).des("Fade renders in and out when cityable blocks are mined or when the player moves");
/*  32 */   Setting<Float> fadeSpeed = setting("FadeSpeed", 2.0F, 0.1F, 3.0F).des("Speed of how fast renders fade").whenTrue(this.fade);
/*  33 */   Setting<RenderMode> renderMode = setting("RenderMode", RenderMode.Box);
/*  34 */   Setting<Float> boxHeight = setting("BoxHeight", 1.0F, 0.0F, 1.0F).only(v -> (this.renderMode.getValue() != RenderMode.Flat));
/*  35 */   Setting<Boolean> solid = setting("Solid", true).des("Solid render");
/*  36 */   Setting<Color> solidColor = setting("SolidColor", new Color((new Color(255, 50, 50, 19)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 19)).whenTrue(this.solid);
/*  37 */   Setting<Color> selfSolidColor = setting("SelfSolidColor", new Color((new Color(50, 255, 50, 19)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 19)).whenTrue(this.self).whenTrue(this.solid);
/*  38 */   Setting<Boolean> lines = setting("Lines", true).des("Lines render");
/*  39 */   Setting<Float> linesWidth = setting("LinesWidth", 1.0F, 1.0F, 5.0F).whenTrue(this.lines);
/*  40 */   Setting<Color> linesColor = setting("LinesColor", new Color((new Color(255, 50, 50, 101)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 101)).whenTrue(this.solid);
/*  41 */   Setting<Color> selfLinesColor = setting("SelfLinesColor", new Color((new Color(50, 255, 50, 101)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 101)).whenTrue(this.self).whenTrue(this.lines);
/*     */   
/*  43 */   private final HashMap<BlockPos, Float> toRenderEnemyPos = new HashMap<>();
/*  44 */   private final HashMap<BlockPos, Float> toRenderEnemyPos2 = new HashMap<>();
/*  45 */   private final HashMap<BlockPos, Float> toRenderSelfPos = new HashMap<>();
/*  46 */   private final HashMap<BlockPos, Float> toRenderSelfPos2 = new HashMap<>();
/*  47 */   private final Timer timer = new Timer();
/*     */ 
/*     */   
/*     */   public void onRenderWorld(RenderEvent event) {
/*  51 */     int passedms = (int)this.timer.hasPassed();
/*  52 */     this.timer.reset();
/*  53 */     if (((Boolean)this.self.getValue()).booleanValue()) {
/*  54 */       this.toRenderSelfPos2.clear();
/*  55 */       BlockPos playerPos = new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
/*     */       
/*  57 */       for (EnumFacing facing : EnumFacing.field_176754_o) {
/*  58 */         BlockPos pos = BlockUtil.extrudeBlock(playerPos, facing);
/*     */         
/*  60 */         if (CrystalUtil.isCityable(pos, facing, ((Boolean)this.checkDiagonalCity.getValue()).booleanValue(), ((Boolean)this.oneBlockCrystalMode.getValue()).booleanValue())) {
/*  61 */           this.toRenderSelfPos2.put(pos, Float.valueOf(0.0F));
/*  62 */           if (((Boolean)this.fade.getValue()).booleanValue()) {
/*  63 */             if (passedms < 1000) {
/*  64 */               this.toRenderSelfPos.putIfAbsent(pos, Float.valueOf(0.0F));
/*  65 */               this.toRenderSelfPos.put(pos, Float.valueOf(((Float)this.toRenderSelfPos.get(pos)).floatValue() + ((Float)this.fadeSpeed.getValue()).floatValue() * passedms));
/*     */               
/*  67 */               if (((Float)this.toRenderSelfPos.get(pos)).floatValue() > 300.0F) {
/*  68 */                 this.toRenderSelfPos.put(pos, Float.valueOf(300.0F));
/*     */               }
/*     */             } 
/*     */           } else {
/*     */             
/*  73 */             this.toRenderSelfPos.put(pos, Float.valueOf(300.0F));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  78 */       for (Map.Entry<BlockPos, Float> entry : (new HashMap<>(this.toRenderSelfPos)).entrySet()) {
/*  79 */         if (!this.toRenderSelfPos2.containsKey(entry.getKey())) {
/*  80 */           if (((Boolean)this.fade.getValue()).booleanValue()) {
/*  81 */             if (passedms < 1000) {
/*  82 */               this.toRenderSelfPos.put(entry.getKey(), Float.valueOf(((Float)this.toRenderSelfPos.get(entry.getKey())).floatValue() - ((Float)this.fadeSpeed.getValue()).floatValue() * passedms));
/*     */               
/*  84 */               if (((Float)this.toRenderSelfPos.get(entry.getKey())).floatValue() <= 0.0F) {
/*  85 */                 this.toRenderSelfPos.remove(entry.getKey());
/*     */                 
/*     */                 continue;
/*     */               } 
/*     */             } 
/*     */           } else {
/*  91 */             this.toRenderSelfPos.remove(entry.getKey());
/*     */           } 
/*     */         }
/*     */         
/*  95 */         renderStuff(entry.getKey(), (Color)this.selfSolidColor.getValue(), (Color)this.selfLinesColor.getValue(), ((Boolean)this.fade.getValue()).booleanValue() ? (((Float)entry.getValue()).floatValue() / 300.0F) : 1.0F);
/*     */       } 
/*     */     } 
/*     */     
/*  99 */     EntityUtil.entitiesListFlag = true;
/* 100 */     this.toRenderEnemyPos2.clear();
/* 101 */     mc.field_71441_e.field_72996_f.stream()
/* 102 */       .filter(e -> e instanceof net.minecraft.entity.player.EntityPlayer)
/* 103 */       .filter(e -> (e != mc.field_71439_g))
/* 104 */       .filter(e -> (!((Boolean)this.ignoreFriends.getValue()).booleanValue() || !FriendManager.isFriend(e)))
/* 105 */       .filter(e -> (EntityUtil.getInterpDistance(mc.func_184121_ak(), e, (Entity)mc.field_71439_g) <= ((Float)this.range.getValue()).floatValue()))
/* 106 */       .forEach(e -> {
/*     */           BlockPos playerPos = new BlockPos(Math.floor(e.field_70165_t), Math.floor(e.field_70163_u), Math.floor(e.field_70161_v));
/*     */           
/*     */           for (EnumFacing facing : EnumFacing.field_176754_o) {
/*     */             BlockPos pos = BlockUtil.extrudeBlock(playerPos, facing);
/*     */             
/*     */             if (CrystalUtil.isCityable(pos, facing, ((Boolean)this.checkDiagonalCity.getValue()).booleanValue(), ((Boolean)this.oneBlockCrystalMode.getValue()).booleanValue())) {
/*     */               this.toRenderEnemyPos2.put(pos, Float.valueOf(0.0F));
/*     */               
/*     */               if (((Boolean)this.fade.getValue()).booleanValue()) {
/*     */                 if (passedms < 1000) {
/*     */                   this.toRenderEnemyPos.putIfAbsent(pos, Float.valueOf(0.0F));
/*     */                   
/*     */                   this.toRenderEnemyPos.put(pos, Float.valueOf(((Float)this.toRenderEnemyPos.get(pos)).floatValue() + ((Float)this.fadeSpeed.getValue()).floatValue() * passedms));
/*     */                   if (((Float)this.toRenderEnemyPos.get(pos)).floatValue() > 300.0F) {
/*     */                     this.toRenderEnemyPos.put(pos, Float.valueOf(300.0F));
/*     */                   }
/*     */                 } 
/*     */               } else {
/*     */                 this.toRenderEnemyPos.put(pos, Float.valueOf(300.0F));
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         });
/* 130 */     EntityUtil.entitiesListFlag = false;
/*     */     
/* 132 */     for (Map.Entry<BlockPos, Float> entry : (new HashMap<>(this.toRenderEnemyPos)).entrySet()) {
/* 133 */       if (!this.toRenderEnemyPos2.containsKey(entry.getKey())) {
/* 134 */         if (((Boolean)this.fade.getValue()).booleanValue()) {
/* 135 */           if (passedms < 1000) {
/* 136 */             this.toRenderEnemyPos.put(entry.getKey(), Float.valueOf(((Float)this.toRenderEnemyPos.get(entry.getKey())).floatValue() - ((Float)this.fadeSpeed.getValue()).floatValue() * passedms));
/*     */             
/* 138 */             if (((Float)this.toRenderEnemyPos.get(entry.getKey())).floatValue() <= 0.0F) {
/* 139 */               this.toRenderEnemyPos.remove(entry.getKey());
/*     */               
/*     */               continue;
/*     */             } 
/*     */           } 
/*     */         } else {
/* 145 */           this.toRenderEnemyPos.remove(entry.getKey());
/*     */         } 
/*     */       }
/*     */       
/* 149 */       renderStuff(entry.getKey(), (Color)this.solidColor.getValue(), (Color)this.linesColor.getValue(), ((Boolean)this.fade.getValue()).booleanValue() ? (((Float)entry.getValue()).floatValue() / 300.0F) : 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderStuff(BlockPos pos, Color solidColor, Color linesColor, float alphaFactor) {
/* 154 */     alphaFactor = MathUtilFuckYou.clamp(alphaFactor, 0.0F, 1.0F);
/* 155 */     switch ((RenderMode)this.renderMode.getValue()) {
/*     */       case Box:
/* 157 */         if (((Boolean)this.solid.getValue()).booleanValue()) {
/* 158 */           SpartanTessellator.drawBlockFullBox(new Vec3d((Vec3i)pos), false, ((Float)this.boxHeight.getValue()).floatValue(), (new Color(solidColor.getColorColor().getRed(), solidColor.getColorColor().getGreen(), solidColor.getColorColor().getBlue(), (int)(solidColor.getAlpha() * alphaFactor))).getRGB());
/*     */         }
/*     */         
/* 161 */         if (((Boolean)this.lines.getValue()).booleanValue()) {
/* 162 */           SpartanTessellator.drawBlockLineBox(new Vec3d((Vec3i)pos), false, ((Float)this.boxHeight.getValue()).floatValue(), ((Float)this.linesWidth.getValue()).floatValue(), (new Color(linesColor.getColorColor().getRed(), linesColor.getColorColor().getGreen(), linesColor.getColorColor().getBlue(), (int)(linesColor.getAlpha() * alphaFactor))).getRGB());
/*     */         }
/*     */         break;
/*     */ 
/*     */       
/*     */       case Flat:
/* 168 */         if (((Boolean)this.solid.getValue()).booleanValue()) {
/* 169 */           SpartanTessellator.drawFlatFullBox(new Vec3d((Vec3i)pos), false, (new Color(solidColor.getColorColor().getRed(), solidColor.getColorColor().getGreen(), solidColor.getColorColor().getBlue(), (int)(solidColor.getAlpha() * alphaFactor))).getRGB());
/*     */         }
/*     */         
/* 172 */         if (((Boolean)this.lines.getValue()).booleanValue()) {
/* 173 */           SpartanTessellator.drawFlatLineBox(new Vec3d((Vec3i)pos), false, ((Float)this.linesWidth.getValue()).floatValue(), (new Color(linesColor.getColorColor().getRed(), linesColor.getColorColor().getGreen(), linesColor.getColorColor().getBlue(), (int)(linesColor.getAlpha() * alphaFactor))).getRGB());
/*     */         }
/*     */         break;
/*     */ 
/*     */       
/*     */       case Pyramid:
/* 179 */         if (((Boolean)this.solid.getValue()).booleanValue()) {
/* 180 */           SpartanTessellator.drawPyramidFullBox(new Vec3d((Vec3i)pos), false, ((Float)this.boxHeight.getValue()).floatValue(), (new Color(solidColor.getColorColor().getRed(), solidColor.getColorColor().getGreen(), solidColor.getColorColor().getBlue(), (int)(solidColor.getAlpha() * alphaFactor))).getRGB());
/*     */         }
/*     */         
/* 183 */         if (((Boolean)this.lines.getValue()).booleanValue()) {
/* 184 */           SpartanTessellator.drawPyramidLineBox(new Vec3d((Vec3i)pos), false, ((Float)this.boxHeight.getValue()).floatValue(), ((Float)this.linesWidth.getValue()).floatValue(), (new Color(linesColor.getColorColor().getRed(), linesColor.getColorColor().getGreen(), linesColor.getColorColor().getBlue(), (int)(linesColor.getAlpha() * alphaFactor))).getRGB());
/*     */         }
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   enum RenderMode
/*     */   {
/* 192 */     Box,
/* 193 */     Flat,
/* 194 */     Pyramid;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\visuals\CityRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */