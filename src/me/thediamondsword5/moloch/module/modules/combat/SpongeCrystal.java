/*     */ package me.thediamondsword5.moloch.module.modules.combat;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.hud.huds.DebugThing;
/*     */ import me.thediamondsword5.moloch.utils.BlockUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.concurrent.ConcurrentTaskManager;
/*     */ import net.spartanb312.base.core.concurrent.repeat.RepeatUnit;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.render.RenderEvent;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.CrystalUtil;
/*     */ import net.spartanb312.base.utils.ItemUtils;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import net.spartanb312.base.utils.graphics.SpartanTessellator;
/*     */ import net.spartanb312.base.utils.math.Pair;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @Parallel(runnable = true)
/*     */ @ModuleInfo(name = "SpongeCrystal", category = Category.COMBAT, description = "Places sponges to allow for crystal placements in water")
/*     */ public class SpongeCrystal
/*     */   extends Module {
/*  37 */   Setting<Page> page = setting("Page", Page.General);
/*     */   
/*  39 */   Setting<Integer> targetUpdateDelay = setting("TargetUpdateDelay", 50, 1, 200).des("Milliseconds to update the target position calculations").whenAtMode(this.page, Page.General);
/*  40 */   Setting<Boolean> packetPlace = setting("PacketPlace", true).des("Uses packets to place sponge").whenAtMode(this.page, Page.General);
/*  41 */   Setting<Boolean> targetMobs = setting("TargetMobs", false).des("Target monsters").whenAtMode(this.page, Page.General);
/*  42 */   Setting<Boolean> rotate = setting("Rotate", false).des("Rotates player to face position to place sponge").whenAtMode(this.page, Page.General);
/*  43 */   Setting<Boolean> conserveSponges = setting("ConserveSponges", false).des("Only places more sponges if the previously placed one is destroyed").whenAtMode(this.page, Page.General);
/*  44 */   Setting<Float> detectionRange = setting("DetectionRange", 10.0F, 0.0F, 10.0F).des("Minimum distance from a target entity to start calculating place positions").whenAtMode(this.page, Page.General);
/*  45 */   Setting<Float> range = setting("Range", 4.5F, 0.0F, 8.0F).des("Range to begin attempting to place sponges").whenAtMode(this.page, Page.General);
/*  46 */   Setting<Float> wallRange = setting("WallRange", 3.0F, 0.0F, 5.0F).des("Range to begin attemping to place sponges when place position is behind a wall").whenAtMode(this.page, Page.General);
/*  47 */   Setting<Float> minDamage = setting("MinDamage", 4.5F, 0.0F, 36.0F).des("Minimum damage to target to place sponge").whenAtMode(this.page, Page.General);
/*  48 */   Setting<Boolean> noSuicide = setting("NoSuicide", true).des("When at low health, don't place in areas where a crystal can kill you").whenAtMode(this.page, Page.General);
/*  49 */   Setting<Boolean> lethalOverride = setting("LethalOverride", true).des("Ignores max self damage when opponent can be popped and you can have a certain amount of health remaining").whenAtMode(this.page, Page.General);
/*  50 */   Setting<Float> lethalRemainingHealth = setting("LethalRemainingHealth", 8.0F, 0.0F, 36.0F).des("Min health remaining after you can break a crystal to pop opponent").whenTrue(this.lethalOverride).whenAtMode(this.page, Page.General);
/*  51 */   Setting<Float> maxSelfDamage = setting("MaxSelfDamage", 12.0F, 0.0F, 36.0F).des("Maximum damage that a crystal placed in targeted position can do to you").whenAtMode(this.page, Page.General);
/*     */   
/*  53 */   Setting<Boolean> solid = setting("Solid", true).des("Render solid box at position to place sponge").whenAtMode(this.page, Page.Render);
/*  54 */   Setting<Color> solidColor = setting("SolidColor", new Color((new Color(100, 61, 255, 50)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 61, 255, 50)).whenAtMode(this.page, Page.Render);
/*  55 */   Setting<Boolean> lines = setting("Lines", true).des("Render wireframe box at position to place sponge").whenAtMode(this.page, Page.Render);
/*  56 */   Setting<Float> linesWidth = setting("LinesWidth", 1.0F, 1.0F, 5.0F).des("Width of wireframe box lines").whenAtMode(this.page, Page.Render);
/*  57 */   Setting<Color> linesColor = setting("LinesColor", new Color((new Color(255, 255, 255, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 120)).whenAtMode(this.page, Page.Render);
/*  58 */   Setting<Boolean> fade = setting("Fade", true).des("Fade out render on place").whenAtMode(this.page, Page.Render);
/*  59 */   Setting<Float> fadeSpeed = setting("FadeSpeed", 1.0F, 0.1F, 3.0F).des("Fade out render speed").whenTrue(this.fade).whenAtMode(this.page, Page.Render);
/*  60 */   Setting<Boolean> move = setting("Move", true).des("Move render up on place").whenTrue(this.fade).whenAtMode(this.page, Page.Render);
/*  61 */   Setting<Float> moveSpeed = setting("MoveSpeed", 1.0F, 0.1F, 3.0F).des("Move render up speed").whenTrue(this.move).whenTrue(this.fade).whenAtMode(this.page, Page.Render);
/*     */   
/*  63 */   private final List<RepeatUnit> repeatUnits = new ArrayList<>();
/*  64 */   private final HashMap<BlockPos, Float> animateMap = new HashMap<>();
/*  65 */   private final Timer timer = new Timer();
/*  66 */   private BlockPos toPlacePos = null;
/*  67 */   private BlockPos toRenderPos = null;
/*  68 */   private BlockPos prevSpongePos = null;
/*     */ 
/*     */ 
/*     */   
/*     */   RepeatUnit updateCalc;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpongeCrystal() {
/*  78 */     this.updateCalc = new RepeatUnit(() -> ((Integer)this.targetUpdateDelay.getValue()).intValue(), () -> {
/*     */           Pair<BlockPos, Entity> data = CrystalUtil.calcPlace(((Boolean)this.targetMobs.getValue()).booleanValue(), ((Float)this.detectionRange.getValue()).floatValue(), ((Float)this.range.getValue()).floatValue(), ((Float)this.wallRange.getValue()).floatValue(), ((Float)this.minDamage.getValue()).floatValue(), ((Float)this.maxSelfDamage.getValue()).floatValue(), ((Boolean)this.lethalOverride.getValue()).booleanValue(), ((Float)this.lethalRemainingHealth.getValue()).floatValue(), ((Boolean)this.noSuicide.getValue()).booleanValue(), true, false);
/*     */           this.toPlacePos = (BlockPos)data.a;
/*     */         });
/*     */     this.repeatUnits.add(this.updateCalc);
/*     */     this.repeatUnits.forEach(it -> {
/*     */           it.suspend();
/*     */           ConcurrentTaskManager.runRepeat(it);
/*     */         }); } public void onRenderWorld(RenderEvent event) {
/*  87 */     if (((Boolean)this.fade.getValue()).booleanValue()) {
/*  88 */       DebugThing.debugInt = this.animateMap.size();
/*  89 */       int passedms = (int)this.timer.hasPassed();
/*  90 */       this.timer.reset();
/*     */       
/*  92 */       for (Map.Entry<BlockPos, Float> entry : (new HashMap<>(this.animateMap)).entrySet()) {
/*  93 */         if (((Float)entry.getValue()).floatValue() <= 0.0F) {
/*  94 */           this.animateMap.remove(entry.getKey());
/*     */           
/*     */           continue;
/*     */         } 
/*  98 */         if (((Boolean)this.move.getValue()).booleanValue()) {
/*  99 */           GL11.glTranslatef(0.0F, (300.0F - ((Float)entry.getValue()).floatValue()) / 500.0F * ((Float)this.moveSpeed.getValue()).floatValue(), 0.0F);
/*     */         }
/*     */         
/* 102 */         if (((Boolean)this.solid.getValue()).booleanValue()) {
/* 103 */           SpartanTessellator.drawBlockFullBox(new Vec3d((Vec3i)entry.getKey()), false, 1.0F, (new Color(((Color)this.solidColor.getValue()).getColorColor().getRed(), ((Color)this.solidColor.getValue()).getColorColor().getGreen(), ((Color)this.solidColor.getValue()).getColorColor().getBlue(), (int)(((Color)this.solidColor.getValue()).getAlpha() * ((Float)entry.getValue()).floatValue() / 300.0F))).getRGB());
/*     */         }
/*     */         
/* 106 */         if (((Boolean)this.lines.getValue()).booleanValue()) {
/* 107 */           SpartanTessellator.drawBlockLineBox(new Vec3d((Vec3i)entry.getKey()), false, 1.0F, ((Float)this.linesWidth.getValue()).floatValue(), (new Color(((Color)this.linesColor.getValue()).getColorColor().getRed(), ((Color)this.linesColor.getValue()).getColorColor().getGreen(), ((Color)this.linesColor.getValue()).getColorColor().getBlue(), (int)(((Color)this.linesColor.getValue()).getAlpha() * ((Float)entry.getValue()).floatValue() / 300.0F))).getRGB());
/*     */         }
/*     */         
/* 110 */         if (((Boolean)this.move.getValue()).booleanValue()) {
/* 111 */           GL11.glTranslatef(0.0F, -(300.0F - ((Float)entry.getValue()).floatValue()) / 500.0F * ((Float)this.moveSpeed.getValue()).floatValue(), 0.0F);
/*     */         }
/*     */         
/* 114 */         if (passedms < 1000) {
/* 115 */           this.animateMap.put(entry.getKey(), Float.valueOf(((Float)entry.getValue()).floatValue() - passedms * ((Float)this.fadeSpeed.getValue()).floatValue() / 3.0F));
/*     */         }
/*     */       }
/*     */     
/* 119 */     } else if (this.toRenderPos != null) {
/* 120 */       if (((Boolean)this.solid.getValue()).booleanValue()) {
/* 121 */         SpartanTessellator.drawBlockFullBox(new Vec3d((Vec3i)this.toRenderPos), false, 1.0F, ((Color)this.solidColor.getValue()).getColor());
/*     */       }
/*     */       
/* 124 */       if (((Boolean)this.lines.getValue()).booleanValue()) {
/* 125 */         SpartanTessellator.drawBlockLineBox(new Vec3d((Vec3i)this.toRenderPos), false, 1.0F, ((Float)this.linesWidth.getValue()).floatValue(), ((Color)this.linesColor.getValue()).getColor());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/* 132 */     if (this.toRenderPos != null && mc.field_71441_e.func_180495_p(this.toRenderPos).func_177230_c() != Blocks.field_150360_v) {
/* 133 */       this.toRenderPos = null;
/*     */     }
/*     */     
/* 136 */     if (this.toPlacePos == null) {
/* 137 */       this.toRenderPos = null;
/*     */       
/*     */       return;
/*     */     } 
/* 141 */     if (((Boolean)this.conserveSponges.getValue()).booleanValue() && this.prevSpongePos != null) {
/* 142 */       if (mc.field_71441_e.func_180495_p(this.prevSpongePos).func_177230_c() == Blocks.field_150360_v) {
/*     */         return;
/*     */       }
/*     */       
/* 146 */       this.prevSpongePos = null;
/*     */     } 
/*     */ 
/*     */     
/* 150 */     if (mc.field_71441_e.func_180495_p(BlockUtil.extrudeBlock(this.toPlacePos, EnumFacing.UP)).func_177230_c() == Blocks.field_150355_j || mc.field_71441_e
/* 151 */       .func_180495_p(BlockUtil.extrudeBlock(this.toPlacePos, EnumFacing.UP)).func_177230_c() == Blocks.field_150358_i) {
/* 152 */       for (EnumFacing facing : EnumFacing.field_176754_o) {
/* 153 */         BlockPos pos = BlockUtil.extrudeBlock(this.toPlacePos, facing);
/* 154 */         if (BlockUtil.isFacePlaceble(pos, EnumFacing.UP, true)) {
/*     */           
/* 156 */           if (((Boolean)this.conserveSponges.getValue()).booleanValue() && this.prevSpongePos == null) {
/* 157 */             this.prevSpongePos = BlockUtil.extrudeBlock(pos, EnumFacing.UP);
/*     */           }
/*     */           
/* 160 */           if (((Boolean)this.fade.getValue()).booleanValue()) {
/* 161 */             this.animateMap.put(BlockUtil.extrudeBlock(pos, EnumFacing.UP), Float.valueOf(300.0F));
/*     */           }
/* 163 */           this.toRenderPos = BlockUtil.extrudeBlock(pos, EnumFacing.UP);
/*     */           
/* 165 */           int prevSlot = 9999;
/* 166 */           if (ItemUtils.isItemInHotbar(Item.func_150898_a(Blocks.field_150360_v))) {
/* 167 */             prevSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 168 */             ItemUtils.switchToSlot(ItemUtils.findItemInHotBar(Item.func_150898_a(Blocks.field_150360_v)));
/*     */           } 
/*     */           
/* 171 */           BlockUtil.placeBlock(pos, EnumFacing.UP, ((Boolean)this.packetPlace.getValue()).booleanValue(), false, ((Boolean)this.rotate.getValue()).booleanValue());
/*     */           
/* 173 */           if (prevSlot != 9999) {
/* 174 */             ItemUtils.switchToSlot(prevSlot);
/*     */           }
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 185 */     this.repeatUnits.forEach(RepeatUnit::resume);
/* 186 */     this.moduleEnableFlag = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 191 */     this.repeatUnits.forEach(RepeatUnit::suspend);
/* 192 */     this.moduleDisableFlag = true;
/* 193 */     this.prevSpongePos = null;
/* 194 */     this.toRenderPos = null;
/*     */   }
/*     */   
/*     */   enum Page
/*     */   {
/* 199 */     General,
/* 200 */     Render;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\combat\SpongeCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */