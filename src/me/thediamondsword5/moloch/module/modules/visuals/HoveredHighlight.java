/*     */ package me.thediamondsword5.moloch.module.modules.visuals;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.utils.BlockUtil;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.render.RenderEvent;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.MathUtilFuckYou;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import net.spartanb312.base.utils.graphics.SpartanTessellator;
/*     */ 
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "HoveredHighlight", category = Category.VISUALS, description = "Renders something to highlight the block that you are currently looking at")
/*     */ public class HoveredHighlight
/*     */   extends Module
/*     */ {
/*  28 */   Setting<Boolean> faceRender = setting("FaceRender", true).des("Renders the face of a block hovered instead of the entire block");
/*  29 */   Setting<Boolean> fade = setting("Fade", true).des("Fades in and out block currently hovered over");
/*  30 */   Setting<Float> fadeSpeed = setting("FadeSpeed", 1.0F, 0.1F, 5.0F).des("Speed that the render fades out").whenTrue(this.fade);
/*  31 */   Setting<Boolean> solid = setting("Solid", true);
/*  32 */   Setting<Color> solidColor = setting("SolidColor", new Color((new Color(255, 255, 255, 20)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 20)).whenTrue(this.solid);
/*  33 */   Setting<Boolean> lines = setting("Lines", true);
/*  34 */   Setting<Float> linesWidth = setting("LinesWidth", 1.0F, 1.0F, 5.0F).whenTrue(this.lines);
/*  35 */   Setting<Color> linesColor = setting("LinesColor", new Color((new Color(255, 255, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 175)).whenTrue(this.lines);
/*     */   
/*  37 */   private final HashMap<BlockPos, Integer> fadeMap = new HashMap<>();
/*  38 */   private final HashMap<Map.Entry<BlockPos, EnumFacing>, Integer> fadeFaceMap = new HashMap<>();
/*  39 */   private final Timer fadeTimer = new Timer();
/*     */ 
/*     */   
/*     */   public void onRenderWorld(RenderEvent event) {
/*  43 */     int passedms = (int)this.fadeTimer.hasPassed();
/*  44 */     this.fadeTimer.reset();
/*     */     
/*  46 */     if (mc.field_71476_x != null && mc.field_71476_x.field_72313_a == RayTraceResult.Type.BLOCK) {
/*  47 */       if (((Boolean)this.fade.getValue()).booleanValue()) {
/*  48 */         if (((Boolean)this.faceRender.getValue()).booleanValue()) {
/*  49 */           Map.Entry<BlockPos, EnumFacing> highlightData = new AbstractMap.SimpleEntry<>(mc.field_71476_x.func_178782_a(), mc.field_71476_x.field_178784_b);
/*     */           
/*  51 */           this.fadeFaceMap.putIfAbsent(highlightData, Integer.valueOf(0));
/*     */           
/*  53 */           if (passedms < 1000) {
/*  54 */             this.fadeFaceMap.put(highlightData, Integer.valueOf((int)(((Integer)this.fadeFaceMap.get(highlightData)).intValue() + ((Float)this.fadeSpeed.getValue()).floatValue() * passedms)));
/*     */           }
/*     */         } else {
/*     */           
/*  58 */           this.fadeMap.putIfAbsent(mc.field_71476_x.func_178782_a(), Integer.valueOf(0));
/*     */           
/*  60 */           if (passedms < 1000) {
/*  61 */             this.fadeMap.put(mc.field_71476_x.func_178782_a(), Integer.valueOf((int)(((Integer)this.fadeMap.get(mc.field_71476_x.func_178782_a())).intValue() + ((Float)this.fadeSpeed.getValue()).floatValue() * passedms)));
/*     */           }
/*     */         } 
/*     */       } else {
/*     */         
/*  66 */         doRender(mc.field_71476_x.func_178782_a(), mc.field_71476_x.field_178784_b, 1.0F);
/*     */       } 
/*     */     }
/*     */     
/*  70 */     if (((Boolean)this.fade.getValue()).booleanValue()) {
/*  71 */       if (((Boolean)this.faceRender.getValue()).booleanValue()) {
/*  72 */         for (Map.Entry<Map.Entry<BlockPos, EnumFacing>, Integer> entry : (new HashMap<>(this.fadeFaceMap)).entrySet()) {
/*  73 */           if (((Integer)entry.getValue()).intValue() > 300) {
/*  74 */             this.fadeFaceMap.put(entry.getKey(), Integer.valueOf(300));
/*     */           }
/*     */           
/*  77 */           if (((Integer)entry.getValue()).intValue() < 0) {
/*  78 */             this.fadeFaceMap.put(entry.getKey(), Integer.valueOf(0));
/*     */           }
/*     */           
/*  81 */           float alphaFactor = ((Integer)entry.getValue()).intValue() / 300.0F;
/*  82 */           doRender((BlockPos)((Map.Entry)entry.getKey()).getKey(), (EnumFacing)((Map.Entry)entry.getKey()).getValue(), MathUtilFuckYou.clamp(alphaFactor, 0.0F, 1.0F));
/*     */         } 
/*     */       } else {
/*     */         
/*  86 */         for (Map.Entry<BlockPos, Integer> entry : (new HashMap<>(this.fadeMap)).entrySet()) {
/*  87 */           if (((Integer)entry.getValue()).intValue() > 300) {
/*  88 */             this.fadeMap.put(entry.getKey(), Integer.valueOf(300));
/*     */           }
/*     */           
/*  91 */           if (((Integer)entry.getValue()).intValue() < 0) {
/*  92 */             this.fadeMap.put(entry.getKey(), Integer.valueOf(0));
/*     */           }
/*     */           
/*  95 */           float alphaFactor = ((Integer)entry.getValue()).intValue() / 300.0F;
/*  96 */           doRender(entry.getKey(), EnumFacing.UP, MathUtilFuckYou.clamp(alphaFactor, 0.0F, 1.0F));
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 101 */     if (((Boolean)this.fade.getValue()).booleanValue() && passedms < 1000) {
/* 102 */       if (((Boolean)this.faceRender.getValue()).booleanValue()) {
/* 103 */         for (Map.Entry<Map.Entry<BlockPos, EnumFacing>, Integer> entry : (new HashMap<>(this.fadeFaceMap)).entrySet()) {
/* 104 */           if (mc.field_71476_x == null || mc.field_71476_x.field_72313_a != RayTraceResult.Type.BLOCK || !BlockUtil.isSameBlockPos((BlockPos)((Map.Entry)entry.getKey()).getKey(), mc.field_71476_x.func_178782_a()) || ((Map.Entry)entry.getKey()).getValue() != mc.field_71476_x.field_178784_b) {
/* 105 */             this.fadeFaceMap.put(entry.getKey(), Integer.valueOf((int)(((Integer)this.fadeFaceMap.get(entry.getKey())).intValue() - ((Float)this.fadeSpeed.getValue()).floatValue() * passedms)));
/*     */           }
/*     */           
/* 108 */           if (((Integer)entry.getValue()).intValue() <= 0) {
/* 109 */             this.fadeFaceMap.remove(entry.getKey());
/*     */           }
/*     */         } 
/*     */       } else {
/*     */         
/* 114 */         for (Map.Entry<BlockPos, Integer> entry : (new HashMap<>(this.fadeMap)).entrySet()) {
/* 115 */           if (mc.field_71476_x == null || mc.field_71476_x.field_72313_a != RayTraceResult.Type.BLOCK || !BlockUtil.isSameBlockPos(entry.getKey(), mc.field_71476_x.func_178782_a())) {
/* 116 */             this.fadeMap.put(entry.getKey(), Integer.valueOf((int)(((Integer)this.fadeMap.get(entry.getKey())).intValue() - ((Float)this.fadeSpeed.getValue()).floatValue() * passedms)));
/*     */           }
/*     */           
/* 119 */           if (((Integer)entry.getValue()).intValue() <= 0) {
/* 120 */             this.fadeMap.remove(entry.getKey());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void doRender(BlockPos pos, EnumFacing face, float alphaFactor) {
/* 128 */     if (((Boolean)this.faceRender.getValue()).booleanValue()) {
/* 129 */       if (((Boolean)this.solid.getValue()).booleanValue()) {
/* 130 */         SpartanTessellator.drawBlockFaceFilledBB(pos, face, (new Color(((Color)this.solidColor.getValue()).getColorColor().getRed(), ((Color)this.solidColor.getValue()).getColorColor().getGreen(), ((Color)this.solidColor.getValue()).getColorColor().getBlue(), (int)(((Color)this.solidColor.getValue()).getAlpha() * alphaFactor))).getRGB());
/*     */       }
/*     */       
/* 133 */       if (((Boolean)this.lines.getValue()).booleanValue()) {
/* 134 */         SpartanTessellator.drawBlockFaceLinesBB(pos, face, ((Float)this.linesWidth.getValue()).floatValue(), (new Color(((Color)this.linesColor.getValue()).getColorColor().getRed(), ((Color)this.linesColor.getValue()).getColorColor().getGreen(), ((Color)this.linesColor.getValue()).getColorColor().getBlue(), (int)(((Color)this.linesColor.getValue()).getAlpha() * alphaFactor))).getRGB());
/*     */       }
/*     */     } else {
/*     */       
/* 138 */       if (((Boolean)this.solid.getValue()).booleanValue()) {
/* 139 */         SpartanTessellator.drawBlockBBFullBox(pos, 1.0F, (new Color(((Color)this.solidColor.getValue()).getColorColor().getRed(), ((Color)this.solidColor.getValue()).getColorColor().getGreen(), ((Color)this.solidColor.getValue()).getColorColor().getBlue(), (int)(((Color)this.solidColor.getValue()).getAlpha() * alphaFactor))).getRGB());
/*     */       }
/*     */       
/* 142 */       if (((Boolean)this.lines.getValue()).booleanValue())
/* 143 */         SpartanTessellator.drawBlockBBLineBox(pos, 1.0F, ((Float)this.linesWidth.getValue()).floatValue(), (new Color(((Color)this.linesColor.getValue()).getColorColor().getRed(), ((Color)this.linesColor.getValue()).getColorColor().getGreen(), ((Color)this.linesColor.getValue()).getColorColor().getBlue(), (int)(((Color)this.linesColor.getValue()).getAlpha() * alphaFactor))).getRGB()); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\visuals\HoveredHighlight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */