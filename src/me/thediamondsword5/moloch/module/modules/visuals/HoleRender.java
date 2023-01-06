/*     */ package me.thediamondsword5.moloch.module.modules.visuals;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.utils.BlockUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.render.RenderEvent;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.MathUtilFuckYou;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import net.spartanb312.base.utils.graphics.RenderHelper;
/*     */ import net.spartanb312.base.utils.graphics.SpartanTessellator;
/*     */ import net.spartanb312.base.utils.math.Pair;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "HoleRender", category = Category.VISUALS, description = "Highlights holes that are safe from crystal damage")
/*     */ public class HoleRender extends Module {
/*  33 */   Setting<Page> page = setting("Page", Page.Calc);
/*     */   
/*  35 */   Setting<Float> range = setting("Range", 7.0F, 1.0F, 20.0F).des("Range to start rendering holes in").whenAtMode(this.page, Page.Calc);
/*  36 */   Setting<Boolean> doubleHoles = setting("DoubleHoles", true).des("Render double holes").whenAtMode(this.page, Page.Calc);
/*  37 */   Setting<Boolean> miscBlocks = setting("MiscBlocks", true).des("Count other blocks that are blast resistant in hole calc").whenAtMode(this.page, Page.Calc);
/*  38 */   Setting<Boolean> advancedCheck = setting("AdvancedCheck", false).des("Checks for a 2 block space just above the hole to avoid rendering inaccessible holes (will decrease performance)").whenAtMode(this.page, Page.Calc);
/*  39 */   Setting<Integer> updateDelay = setting("UpdateDelay", 0, 0, 200).des("Delay between updating holes in MS").whenAtMode(this.page, Page.Calc);
/*     */   
/*  41 */   Setting<Boolean> rollingHeight = setting("RollingHeight", false).des("Makes height of renders go up and down on an axis").whenAtMode(this.page, Page.Render);
/*  42 */   Setting<Float> rollingSpeed = setting("RollingSpeed", 1.0F, 0.1F, 10.0F).des("How fast the renders go up and down").whenTrue(this.rollingHeight).whenAtMode(this.page, Page.Render);
/*  43 */   Setting<Float> rollingWidth = setting("RollWidth", 0.4F, 0.0F, 2.0F).des("How wide should each 'wave' be").whenTrue(this.rollingHeight).whenAtMode(this.page, Page.Render);
/*  44 */   Setting<Float> rollingHeightMax = setting("RollHeightMax", 1.0F, -2.0F, 2.0F).des("Maximum height for rolling height").whenTrue(this.rollingHeight).whenAtMode(this.page, Page.Render);
/*  45 */   Setting<Float> rollingHeightMin = setting("RollHeightMin", 0.1F, -2.0F, 2.0F).des("Minimum height for rolling height").whenTrue(this.rollingHeight).whenAtMode(this.page, Page.Render);
/*  46 */   Setting<Boolean> pyramidMode = setting("PyramidRender", false).des("Render holes in a pyramid shape instead of as a rectangle").whenAtMode(this.page, Page.Render);
/*     */   
/*  48 */   Setting<Boolean> mergeDoubleHoles = setting("MergeDoubleHoles", false).des("Render double holes as 1 rectangle instead of 2").whenTrue(this.doubleHoles).whenAtMode(this.page, Page.Render);
/*  49 */   Setting<SelfHighlightMode> selfHighlight = setting("SelfHighlight", SelfHighlightMode.None).des("Modifies the hole that you're currently in").whenAtMode(this.page, Page.Render);
/*  50 */   Setting<Float> selfHightlightAlphaFactor = setting("SelfHighlightAlphaFactor", 0.5F, 0.0F, 10.0F).des("Multiply hole alpha by this when you are in that hole").whenAtMode(this.selfHighlight, SelfHighlightMode.Alpha).whenAtMode(this.page, Page.Render);
/*  51 */   Setting<Float> selfHightlightHeight = setting("SelfHighlightHeight", 0.2F, -2.0F, 2.0F).des("Modify hole height when you are in that hole").whenAtMode(this.selfHighlight, SelfHighlightMode.Height).whenAtMode(this.page, Page.Render);
/*  52 */   Setting<Boolean> fadeIn = setting("FadeIn", false).des("Holes will have lower alpha if they are on the edge of your render range").whenAtMode(this.page, Page.Render);
/*  53 */   Setting<Float> fadeInRange = setting("FadeInRange", 1.0F, 0.1F, 10.0F).des("Distance from your rendering range in which holes will have a lower alpha").whenTrue(this.fadeIn).whenAtMode(this.page, Page.Render);
/*  54 */   Setting<Boolean> xCross = setting("BoxCross", false).des("Makes an x cross through the hole render").whenAtMode(this.page, Page.Render);
/*  55 */   Setting<Boolean> gradientXCross = setting("GradientBoxCross", false).des("Renders x cross with a gradient").whenTrue(this.xCross).whenAtMode(this.page, Page.Render);
/*  56 */   Setting<Boolean> flatXCross = setting("FlatBoxCross", false).des("Renders flat x cross").whenFalse(this.pyramidMode).whenTrue(this.xCross).whenAtMode(this.page, Page.Render);
/*  57 */   Setting<Boolean> solidBox = setting("SolidBox", true).whenAtMode(this.page, Page.Render);
/*  58 */   Setting<Boolean> gradientSolidBox = setting("GradientSolid", false).des("Renders solid box with a gradient").whenTrue(this.solidBox).whenAtMode(this.page, Page.Render);
/*  59 */   Setting<Boolean> wallSolid = setting("WallSolid", true).des("See solid render through wall").whenTrue(this.solidBox).whenAtMode(this.page, Page.Render);
/*  60 */   Setting<Boolean> sidesOnly = setting("SidesOnly", false).des("Removes the top and bottom planes from solid render when gradient to make cool effect or smt idk i thought it looked cool in xuanox's video").whenTrue(this.gradientSolidBox).whenTrue(this.solidBox).whenAtMode(this.page, Page.Render);
/*  61 */   Setting<Boolean> flatSolidBox = setting("FlatSolidBox", false).des("Renders flat solid box").whenTrue(this.solidBox).whenAtMode(this.page, Page.Render);
/*  62 */   Setting<Boolean> linesBox = setting("LinesBox", true).whenAtMode(this.page, Page.Render);
/*  63 */   Setting<Boolean> gradientLinesBox = setting("GradientLines", false).des("Renders lines box with a gradient").whenTrue(this.linesBox).whenAtMode(this.page, Page.Render);
/*  64 */   Setting<Boolean> wallLines = setting("WallLines", true).des("See lines render through wall").whenTrue(this.linesBox).whenAtMode(this.page, Page.Render);
/*  65 */   Setting<Boolean> flatLinesBox = setting("FlatLinesBox", false).des("Renders flat lines box").whenTrue(this.linesBox).whenAtMode(this.page, Page.Render);
/*  66 */   Setting<Boolean> boxOneBlockHeight = setting("OneBlockHeight", false).des("Makes hole render exactly one block tall (bc slider isn't percise enough)").whenFalse(this.rollingHeight).whenAtMode(this.page, Page.Render);
/*  67 */   Setting<Float> boxHeight = setting("Height", 1.0F, -2.0F, 2.0F).whenFalse(this.boxOneBlockHeight).whenFalse(this.rollingHeight).whenAtMode(this.page, Page.Render);
/*  68 */   Setting<Float> lineWidth = setting("LineWidth", 1.0F, 0.0F, 5.0F).only(v -> (((Boolean)this.linesBox.getValue()).booleanValue() || ((Boolean)this.xCross.getValue()).booleanValue())).whenAtMode(this.page, Page.Render);
/*     */   
/*  70 */   Setting<Color> singleSafeColorSolid = setting("SingleSafeColorSolid", new Color((new Color(50, 255, 50, 20)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 20)).whenTrue(this.solidBox).whenAtMode(this.page, Page.Color);
/*  71 */   Setting<Color> singleSafeGradientColorSolid = setting("SingleSafeGradientColorSolid", new Color((new Color(0, 0, 0, 0)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 0, 0, 0, 0)).whenTrue(this.gradientSolidBox).whenTrue(this.solidBox).whenAtMode(this.page, Page.Color);
/*  72 */   Setting<Color> singleSafeColorLines = setting("SingleSafeColorLines", new Color((new Color(50, 255, 50, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 175)).only(v -> (((Boolean)this.linesBox.getValue()).booleanValue() || ((Boolean)this.xCross.getValue()).booleanValue())).whenAtMode(this.page, Page.Color);
/*  73 */   Setting<Color> singleSafeGradientColorLines = setting("SingleSafeGradientColorLines", new Color((new Color(0, 0, 0, 0)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 0, 0, 0, 0)).only(v -> ((((Boolean)this.linesBox.getValue()).booleanValue() && ((Boolean)this.gradientLinesBox.getValue()).booleanValue()) || (((Boolean)this.gradientXCross.getValue()).booleanValue() && ((Boolean)this.xCross.getValue()).booleanValue()))).whenAtMode(this.page, Page.Color);
/*  74 */   Setting<Color> singleUnSafeColorSolid = setting("SingleUnSafeColorSolid", new Color((new Color(255, 50, 50, 20)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 20)).whenTrue(this.solidBox).whenAtMode(this.page, Page.Color);
/*  75 */   Setting<Color> singleUnSafeGradientColorSolid = setting("SingleUnSafeGradientColorSolid", new Color((new Color(0, 0, 0, 0)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 0, 0, 0, 0)).whenTrue(this.gradientSolidBox).whenTrue(this.solidBox).whenAtMode(this.page, Page.Color);
/*  76 */   Setting<Color> singleUnSafeColorLines = setting("SingleUnSafeColorLines", new Color((new Color(255, 50, 50, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 175)).only(v -> (((Boolean)this.linesBox.getValue()).booleanValue() || ((Boolean)this.xCross.getValue()).booleanValue())).whenAtMode(this.page, Page.Color);
/*  77 */   Setting<Color> singleUnSafeGradientColorLines = setting("SingleUnSafeGradientColorLines", new Color((new Color(0, 0, 0, 0)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 0, 0, 0, 0)).only(v -> ((((Boolean)this.linesBox.getValue()).booleanValue() && ((Boolean)this.gradientLinesBox.getValue()).booleanValue()) || (((Boolean)this.gradientXCross.getValue()).booleanValue() && ((Boolean)this.xCross.getValue()).booleanValue()))).whenAtMode(this.page, Page.Color);
/*  78 */   Setting<Color> doubleSafeColorSolid = setting("DoubleSafeColorSolid", new Color((new Color(255, 255, 50, 20)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 50, 20)).whenTrue(this.solidBox).whenTrue(this.doubleHoles).whenAtMode(this.page, Page.Color);
/*  79 */   Setting<Color> doubleSafeGradientColorSolid = setting("DoubleSafeGradientColorSolid", new Color((new Color(0, 0, 0, 0)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 0, 0, 0, 0)).whenTrue(this.gradientSolidBox).whenTrue(this.solidBox).whenTrue(this.doubleHoles).whenAtMode(this.page, Page.Color);
/*  80 */   Setting<Color> doubleSafeColorLines = setting("DoubleSafeColorLines", new Color((new Color(255, 255, 50, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 50, 175)).only(v -> (((Boolean)this.linesBox.getValue()).booleanValue() || ((Boolean)this.xCross.getValue()).booleanValue())).whenTrue(this.doubleHoles).whenAtMode(this.page, Page.Color);
/*  81 */   Setting<Color> doubleSafeGradientColorLines = setting("DoubleSafeGradientColorLines", new Color((new Color(0, 0, 0, 0)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 0, 0, 0, 0)).only(v -> ((((Boolean)this.linesBox.getValue()).booleanValue() && ((Boolean)this.gradientLinesBox.getValue()).booleanValue()) || (((Boolean)this.gradientXCross.getValue()).booleanValue() && ((Boolean)this.xCross.getValue()).booleanValue()))).whenTrue(this.doubleHoles).whenAtMode(this.page, Page.Color);
/*  82 */   Setting<Color> doubleUnSafeColorSolid = setting("DoubleUnSafeColorSolid", new Color((new Color(255, 129, 50, 20)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 129, 50, 20)).whenTrue(this.solidBox).whenTrue(this.doubleHoles).whenAtMode(this.page, Page.Color);
/*  83 */   Setting<Color> doubleUnSafeGradientColorSolid = setting("DoubleUnSafeGradientColorSolid", new Color((new Color(0, 0, 0, 0)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 0, 0, 0, 0)).whenTrue(this.gradientSolidBox).whenTrue(this.solidBox).whenTrue(this.doubleHoles).whenAtMode(this.page, Page.Color);
/*  84 */   Setting<Color> doubleUnSafeColorLines = setting("DoubleUnSafeColorLines", new Color((new Color(255, 129, 50, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 129, 50, 175)).only(v -> (((Boolean)this.linesBox.getValue()).booleanValue() || ((Boolean)this.xCross.getValue()).booleanValue())).whenTrue(this.doubleHoles).whenAtMode(this.page, Page.Color);
/*  85 */   Setting<Color> doubleUnSafeGradientColorLines = setting("DoubleUnSafeGradientColorLines", new Color((new Color(0, 0, 0, 0)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 0, 0, 0, 0)).only(v -> ((((Boolean)this.linesBox.getValue()).booleanValue() && ((Boolean)this.gradientLinesBox.getValue()).booleanValue()) || (((Boolean)this.gradientXCross.getValue()).booleanValue() && ((Boolean)this.xCross.getValue()).booleanValue()))).whenTrue(this.doubleHoles).whenAtMode(this.page, Page.Color);
/*     */   
/*  87 */   private final HashMap<BlockPos, Integer> toRenderPos = new HashMap<>();
/*  88 */   private final HashMap<Pair<BlockPos, BlockPos>, Integer> toRenderDoublePos = new HashMap<>();
/*  89 */   private final List<BlockPos> inRangeBlocks = new ArrayList<>();
/*  90 */   private final Timer updateTimer = new Timer();
/*     */ 
/*     */   
/*     */   public String getModuleInfo() {
/*  94 */     if (((Boolean)this.solidBox.getValue()).booleanValue() && ((Boolean)this.linesBox.getValue()).booleanValue()) return "Full"; 
/*  95 */     if (((Boolean)this.solidBox.getValue()).booleanValue()) return "Solid"; 
/*  96 */     if (((Boolean)this.linesBox.getValue()).booleanValue()) return "Outline"; 
/*  97 */     return "Ok";
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderWorld(RenderEvent event) {
/* 102 */     if (this.updateTimer.passed(((Integer)this.updateDelay.getValue()).intValue())) {
/* 103 */       updateHoles();
/* 104 */       this.updateTimer.reset();
/*     */     } 
/*     */     
/* 107 */     for (Map.Entry<BlockPos, Integer> entry : (new HashMap<>(this.toRenderPos)).entrySet()) {
/* 108 */       Color solidColor, linesColor, solidColor2, linesColor2; if (!RenderHelper.isInViewFrustrum(SpartanTessellator.getBoundingFromPos(entry.getKey()))) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 116 */       BlockPos playerPos = new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
/* 117 */       boolean isInHole = BlockUtil.isSameBlockPos(playerPos, entry.getKey());
/* 118 */       float height = ((Float)this.boxHeight.getValue()).floatValue();
/* 119 */       if (((Boolean)this.boxOneBlockHeight.getValue()).booleanValue()) height = 1.0F; 
/* 120 */       Vec3d holeVec = new Vec3d((Vec3i)entry.getKey());
/*     */       
/* 122 */       float alphaFactor = 1.0F;
/* 123 */       if (((Boolean)this.fadeIn.getValue()).booleanValue()) {
/* 124 */         alphaFactor = MathUtilFuckYou.clamp(((Float)this.range.getValue()).floatValue() - (float)MathUtilFuckYou.getDistance(mc.field_71439_g.func_174791_d(), holeVec), 0.0F, ((Float)this.fadeInRange.getValue()).floatValue()) / ((Float)this.fadeInRange.getValue()).floatValue();
/*     */       }
/*     */       
/* 127 */       if (((Boolean)this.rollingHeight.getValue()).booleanValue()) {
/* 128 */         height = getRolledHeight(((BlockPos)entry.getKey()).field_177962_a);
/*     */       }
/*     */       
/* 131 */       if (this.selfHighlight.getValue() == SelfHighlightMode.Alpha && isInHole) {
/* 132 */         alphaFactor *= ((Float)this.selfHightlightAlphaFactor.getValue()).floatValue();
/*     */       }
/*     */       
/* 135 */       if (this.selfHighlight.getValue() == SelfHighlightMode.Height && isInHole) {
/* 136 */         height = ((Float)this.selfHightlightHeight.getValue()).floatValue();
/*     */       }
/*     */       
/* 139 */       switch (((Integer)entry.getValue()).intValue()) {
/*     */         case 1:
/* 141 */           solidColor = new Color(((Color)this.singleSafeColorSolid.getValue()).getColorColor().getRed(), ((Color)this.singleSafeColorSolid.getValue()).getColorColor().getGreen(), ((Color)this.singleSafeColorSolid.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.singleSafeColorSolid.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 142 */           linesColor = new Color(((Color)this.singleSafeColorLines.getValue()).getColorColor().getRed(), ((Color)this.singleSafeColorLines.getValue()).getColorColor().getGreen(), ((Color)this.singleSafeColorLines.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.singleSafeColorLines.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 143 */           solidColor2 = new Color(((Color)this.singleSafeGradientColorSolid.getValue()).getColorColor().getRed(), ((Color)this.singleSafeGradientColorSolid.getValue()).getColorColor().getGreen(), ((Color)this.singleSafeGradientColorSolid.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.singleSafeGradientColorSolid.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 144 */           linesColor2 = new Color(((Color)this.singleSafeGradientColorLines.getValue()).getColorColor().getRed(), ((Color)this.singleSafeGradientColorLines.getValue()).getColorColor().getGreen(), ((Color)this.singleSafeGradientColorLines.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.singleSafeGradientColorLines.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/*     */           break;
/*     */ 
/*     */         
/*     */         case 2:
/* 149 */           solidColor = new Color(((Color)this.singleUnSafeColorSolid.getValue()).getColorColor().getRed(), ((Color)this.singleUnSafeColorSolid.getValue()).getColorColor().getGreen(), ((Color)this.singleUnSafeColorSolid.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.singleUnSafeColorSolid.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 150 */           linesColor = new Color(((Color)this.singleUnSafeColorLines.getValue()).getColorColor().getRed(), ((Color)this.singleUnSafeColorLines.getValue()).getColorColor().getGreen(), ((Color)this.singleUnSafeColorLines.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.singleUnSafeColorLines.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 151 */           solidColor2 = new Color(((Color)this.singleUnSafeGradientColorSolid.getValue()).getColorColor().getRed(), ((Color)this.singleUnSafeGradientColorSolid.getValue()).getColorColor().getGreen(), ((Color)this.singleUnSafeGradientColorSolid.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.singleUnSafeGradientColorSolid.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 152 */           linesColor2 = new Color(((Color)this.singleUnSafeGradientColorLines.getValue()).getColorColor().getRed(), ((Color)this.singleUnSafeGradientColorLines.getValue()).getColorColor().getGreen(), ((Color)this.singleUnSafeGradientColorLines.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.singleUnSafeGradientColorLines.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/*     */           break;
/*     */ 
/*     */         
/*     */         case 3:
/* 157 */           solidColor = new Color(((Color)this.doubleSafeColorSolid.getValue()).getColorColor().getRed(), ((Color)this.doubleSafeColorSolid.getValue()).getColorColor().getGreen(), ((Color)this.doubleSafeColorSolid.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.doubleSafeColorSolid.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 158 */           linesColor = new Color(((Color)this.doubleSafeColorLines.getValue()).getColorColor().getRed(), ((Color)this.doubleSafeColorLines.getValue()).getColorColor().getGreen(), ((Color)this.doubleSafeColorLines.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.doubleSafeColorLines.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 159 */           solidColor2 = new Color(((Color)this.doubleSafeGradientColorSolid.getValue()).getColorColor().getRed(), ((Color)this.doubleSafeGradientColorSolid.getValue()).getColorColor().getGreen(), ((Color)this.doubleSafeGradientColorSolid.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.doubleSafeGradientColorSolid.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 160 */           linesColor2 = new Color(((Color)this.doubleSafeGradientColorLines.getValue()).getColorColor().getRed(), ((Color)this.doubleSafeGradientColorLines.getValue()).getColorColor().getGreen(), ((Color)this.doubleSafeGradientColorLines.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.doubleSafeGradientColorLines.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/*     */           break;
/*     */ 
/*     */         
/*     */         default:
/* 165 */           solidColor = new Color(((Color)this.doubleUnSafeColorSolid.getValue()).getColorColor().getRed(), ((Color)this.doubleUnSafeColorSolid.getValue()).getColorColor().getGreen(), ((Color)this.doubleUnSafeColorSolid.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.doubleUnSafeColorSolid.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 166 */           linesColor = new Color(((Color)this.doubleUnSafeColorLines.getValue()).getColorColor().getRed(), ((Color)this.doubleUnSafeColorLines.getValue()).getColorColor().getGreen(), ((Color)this.doubleUnSafeColorLines.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.doubleUnSafeColorLines.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 167 */           solidColor2 = new Color(((Color)this.doubleUnSafeGradientColorSolid.getValue()).getColorColor().getRed(), ((Color)this.doubleUnSafeGradientColorSolid.getValue()).getColorColor().getGreen(), ((Color)this.doubleUnSafeGradientColorSolid.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.doubleUnSafeGradientColorSolid.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 168 */           linesColor2 = new Color(((Color)this.doubleUnSafeGradientColorLines.getValue()).getColorColor().getRed(), ((Color)this.doubleUnSafeGradientColorLines.getValue()).getColorColor().getGreen(), ((Color)this.doubleUnSafeGradientColorLines.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.doubleUnSafeGradientColorLines.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 173 */       if (((Boolean)this.solidBox.getValue()).booleanValue()) {
/* 174 */         if (!((Boolean)this.wallSolid.getValue()).booleanValue()) {
/* 175 */           GL11.glEnable(2929);
/*     */         }
/*     */         
/* 178 */         if (((Boolean)this.flatSolidBox.getValue()).booleanValue()) {
/* 179 */           SpartanTessellator.drawFlatFullBox(holeVec, !((Boolean)this.wallSolid.getValue()).booleanValue(), solidColor.getRGB());
/*     */         
/*     */         }
/* 182 */         else if (((Boolean)this.gradientSolidBox.getValue()).booleanValue()) {
/* 183 */           if (((Boolean)this.pyramidMode.getValue()).booleanValue()) {
/* 184 */             SpartanTessellator.drawGradientPyramidFullBox(holeVec, !((Boolean)this.wallSolid.getValue()).booleanValue(), height, solidColor.getRGB(), solidColor2.getRGB());
/*     */           } else {
/*     */             
/* 187 */             SpartanTessellator.drawGradientBlockFullBox(holeVec, !((Boolean)this.wallSolid.getValue()).booleanValue(), ((Boolean)this.sidesOnly.getValue()).booleanValue(), height, solidColor.getRGB(), solidColor2.getRGB());
/*     */           }
/*     */         
/*     */         }
/* 191 */         else if (((Boolean)this.pyramidMode.getValue()).booleanValue()) {
/* 192 */           SpartanTessellator.drawPyramidFullBox(holeVec, !((Boolean)this.wallSolid.getValue()).booleanValue(), height, solidColor.getRGB());
/*     */         } else {
/*     */           
/* 195 */           SpartanTessellator.drawBlockFullBox(holeVec, !((Boolean)this.wallSolid.getValue()).booleanValue(), height, solidColor.getRGB());
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 200 */         if (!((Boolean)this.wallSolid.getValue()).booleanValue()) {
/* 201 */           GL11.glDisable(2929);
/*     */         }
/*     */       } 
/*     */       
/* 205 */       if (((Boolean)this.linesBox.getValue()).booleanValue()) {
/* 206 */         if (!((Boolean)this.wallLines.getValue()).booleanValue()) {
/* 207 */           GL11.glEnable(2929);
/*     */         }
/*     */         
/* 210 */         if (((Boolean)this.flatLinesBox.getValue()).booleanValue()) {
/* 211 */           SpartanTessellator.drawFlatLineBox(holeVec, !((Boolean)this.wallLines.getValue()).booleanValue(), ((Float)this.lineWidth.getValue()).floatValue(), linesColor.getRGB());
/*     */         
/*     */         }
/* 214 */         else if (((Boolean)this.gradientLinesBox.getValue()).booleanValue()) {
/* 215 */           if (((Boolean)this.pyramidMode.getValue()).booleanValue()) {
/* 216 */             SpartanTessellator.drawGradientPyramidLineBox(holeVec, !((Boolean)this.wallLines.getValue()).booleanValue(), height, ((Float)this.lineWidth.getValue()).floatValue(), linesColor.getRGB(), linesColor2.getRGB());
/*     */           } else {
/*     */             
/* 219 */             SpartanTessellator.drawGradientBlockLineBox(holeVec, !((Boolean)this.wallLines.getValue()).booleanValue(), height, ((Float)this.lineWidth.getValue()).floatValue(), linesColor.getRGB(), linesColor2.getRGB());
/*     */           }
/*     */         
/*     */         }
/* 223 */         else if (((Boolean)this.pyramidMode.getValue()).booleanValue()) {
/* 224 */           SpartanTessellator.drawPyramidLineBox(holeVec, !((Boolean)this.wallLines.getValue()).booleanValue(), height, ((Float)this.lineWidth.getValue()).floatValue(), linesColor.getRGB());
/*     */         } else {
/*     */           
/* 227 */           SpartanTessellator.drawBlockLineBox(holeVec, !((Boolean)this.wallLines.getValue()).booleanValue(), height, ((Float)this.lineWidth.getValue()).floatValue(), linesColor.getRGB());
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 232 */         if (!((Boolean)this.wallLines.getValue()).booleanValue()) {
/* 233 */           GL11.glDisable(2929);
/*     */         }
/*     */       } 
/*     */       
/* 237 */       if (((Boolean)this.xCross.getValue()).booleanValue()) {
/* 238 */         if (!((Boolean)this.wallLines.getValue()).booleanValue()) {
/* 239 */           GL11.glEnable(2929);
/*     */         }
/*     */         
/* 242 */         if (((Boolean)this.flatXCross.getValue()).booleanValue() || ((Boolean)this.pyramidMode.getValue()).booleanValue()) {
/* 243 */           SpartanTessellator.drawFlatXCross(holeVec, ((Float)this.lineWidth.getValue()).floatValue(), linesColor.getRGB());
/*     */         
/*     */         }
/* 246 */         else if (((Boolean)this.gradientXCross.getValue()).booleanValue()) {
/* 247 */           SpartanTessellator.drawGradientXCross(holeVec, height, ((Float)this.lineWidth.getValue()).floatValue(), linesColor.getRGB(), linesColor2.getRGB());
/*     */         } else {
/*     */           
/* 250 */           SpartanTessellator.drawXCross(holeVec, height, ((Float)this.lineWidth.getValue()).floatValue(), linesColor.getRGB());
/*     */         } 
/*     */ 
/*     */         
/* 254 */         if (!((Boolean)this.wallLines.getValue()).booleanValue()) {
/* 255 */           GL11.glDisable(2929);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 260 */     if (((Boolean)this.mergeDoubleHoles.getValue()).booleanValue()) {
/* 261 */       for (Map.Entry<Pair<BlockPos, BlockPos>, Integer> entry : (new HashMap<>(this.toRenderDoublePos)).entrySet()) {
/* 262 */         Color solidColor, linesColor, solidColor2, linesColor2; if (!RenderHelper.isInViewFrustrum(SpartanTessellator.getBoundingFromPos((BlockPos)((Pair)entry.getKey()).a)) && !RenderHelper.isInViewFrustrum(SpartanTessellator.getBoundingFromPos((BlockPos)((Pair)entry.getKey()).b))) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 270 */         BlockPos playerPos = new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
/* 271 */         boolean isInHole = (BlockUtil.isSameBlockPos(playerPos, (BlockPos)((Pair)entry.getKey()).a) || BlockUtil.isSameBlockPos(playerPos, (BlockPos)((Pair)entry.getKey()).b));
/* 272 */         float height = ((Float)this.boxHeight.getValue()).floatValue();
/* 273 */         if (((Boolean)this.boxOneBlockHeight.getValue()).booleanValue()) height = 1.0F; 
/* 274 */         boolean flagx = false;
/* 275 */         boolean flagz = false;
/*     */         
/* 277 */         if (((Boolean)this.rollingHeight.getValue()).booleanValue()) {
/* 278 */           height = getRolledHeight(((BlockPos)((Pair)entry.getKey()).a).field_177962_a);
/*     */         }
/*     */         
/* 281 */         if (this.selfHighlight.getValue() == SelfHighlightMode.Height && isInHole) {
/* 282 */           height = ((Float)this.selfHightlightHeight.getValue()).floatValue();
/*     */         }
/*     */         
/* 285 */         Vec3d holeVec1 = new Vec3d(((BlockPos)((Pair)entry.getKey()).a).field_177962_a - 0.5D, ((BlockPos)((Pair)entry.getKey()).a).field_177960_b, ((BlockPos)((Pair)entry.getKey()).a).field_177961_c - 0.5D);
/* 286 */         Vec3d holeVec2 = new Vec3d(((BlockPos)((Pair)entry.getKey()).b).field_177962_a + 0.5D, (((BlockPos)((Pair)entry.getKey()).b).field_177960_b + height), ((BlockPos)((Pair)entry.getKey()).b).field_177961_c + 0.5D);
/*     */         
/* 288 */         if (((BlockPos)((Pair)entry.getKey()).a).field_177962_a == ((BlockPos)((Pair)entry.getKey()).b).field_177962_a + 1.0D) {
/* 289 */           flagx = true;
/* 290 */           holeVec1 = new Vec3d(((BlockPos)((Pair)entry.getKey()).a).field_177962_a + 0.5D, ((BlockPos)((Pair)entry.getKey()).a).field_177960_b, ((BlockPos)((Pair)entry.getKey()).a).field_177961_c - 0.5D);
/* 291 */           holeVec2 = new Vec3d(((BlockPos)((Pair)entry.getKey()).b).field_177962_a - 0.5D, (((BlockPos)((Pair)entry.getKey()).b).field_177960_b + height), ((BlockPos)((Pair)entry.getKey()).b).field_177961_c + 0.5D);
/*     */         } 
/*     */         
/* 294 */         if (((BlockPos)((Pair)entry.getKey()).a).field_177961_c == ((BlockPos)((Pair)entry.getKey()).b).field_177961_c + 1.0D) {
/* 295 */           flagz = true;
/* 296 */           holeVec1 = new Vec3d(((BlockPos)((Pair)entry.getKey()).a).field_177962_a - 0.5D, ((BlockPos)((Pair)entry.getKey()).a).field_177960_b, ((BlockPos)((Pair)entry.getKey()).a).field_177961_c + 0.5D);
/* 297 */           holeVec2 = new Vec3d(((BlockPos)((Pair)entry.getKey()).b).field_177962_a + 0.5D, (((BlockPos)((Pair)entry.getKey()).b).field_177960_b + height), ((BlockPos)((Pair)entry.getKey()).b).field_177961_c - 0.5D);
/*     */         } 
/*     */         
/* 300 */         float alphaFactor = 1.0F;
/* 301 */         if (((Boolean)this.fadeIn.getValue()).booleanValue()) {
/* 302 */           Vec3d centerVec = new Vec3d((holeVec1.field_72450_a + holeVec2.field_72450_a) / 2.0D, (holeVec1.field_72448_b + holeVec2.field_72448_b) / 2.0D, (holeVec1.field_72449_c + holeVec2.field_72449_c) / 2.0D);
/* 303 */           alphaFactor = MathUtilFuckYou.clamp(((Float)this.range.getValue()).floatValue() - (float)MathUtilFuckYou.getDistance(mc.field_71439_g.func_174791_d(), centerVec), 0.0F, ((Float)this.fadeInRange.getValue()).floatValue()) / ((Float)this.fadeInRange.getValue()).floatValue();
/*     */         } 
/*     */         
/* 306 */         if (this.selfHighlight.getValue() == SelfHighlightMode.Alpha && isInHole) {
/* 307 */           alphaFactor *= ((Float)this.selfHightlightAlphaFactor.getValue()).floatValue();
/*     */         }
/*     */         
/* 310 */         switch (((Integer)entry.getValue()).intValue()) {
/*     */           case 1:
/* 312 */             solidColor = new Color(((Color)this.singleSafeColorSolid.getValue()).getColorColor().getRed(), ((Color)this.singleSafeColorSolid.getValue()).getColorColor().getGreen(), ((Color)this.singleSafeColorSolid.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.singleSafeColorSolid.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 313 */             linesColor = new Color(((Color)this.singleSafeColorLines.getValue()).getColorColor().getRed(), ((Color)this.singleSafeColorLines.getValue()).getColorColor().getGreen(), ((Color)this.singleSafeColorLines.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.singleSafeColorLines.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 314 */             solidColor2 = new Color(((Color)this.singleSafeGradientColorSolid.getValue()).getColorColor().getRed(), ((Color)this.singleSafeGradientColorSolid.getValue()).getColorColor().getGreen(), ((Color)this.singleSafeGradientColorSolid.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.singleSafeGradientColorSolid.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 315 */             linesColor2 = new Color(((Color)this.singleSafeGradientColorLines.getValue()).getColorColor().getRed(), ((Color)this.singleSafeGradientColorLines.getValue()).getColorColor().getGreen(), ((Color)this.singleSafeGradientColorLines.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.singleSafeGradientColorLines.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/*     */             break;
/*     */ 
/*     */           
/*     */           case 2:
/* 320 */             solidColor = new Color(((Color)this.singleUnSafeColorSolid.getValue()).getColorColor().getRed(), ((Color)this.singleUnSafeColorSolid.getValue()).getColorColor().getGreen(), ((Color)this.singleUnSafeColorSolid.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.singleUnSafeColorSolid.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 321 */             linesColor = new Color(((Color)this.singleUnSafeColorLines.getValue()).getColorColor().getRed(), ((Color)this.singleUnSafeColorLines.getValue()).getColorColor().getGreen(), ((Color)this.singleUnSafeColorLines.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.singleUnSafeColorLines.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 322 */             solidColor2 = new Color(((Color)this.singleUnSafeGradientColorSolid.getValue()).getColorColor().getRed(), ((Color)this.singleUnSafeGradientColorSolid.getValue()).getColorColor().getGreen(), ((Color)this.singleUnSafeGradientColorSolid.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.singleUnSafeGradientColorSolid.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 323 */             linesColor2 = new Color(((Color)this.singleUnSafeGradientColorLines.getValue()).getColorColor().getRed(), ((Color)this.singleUnSafeGradientColorLines.getValue()).getColorColor().getGreen(), ((Color)this.singleUnSafeGradientColorLines.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.singleUnSafeGradientColorLines.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/*     */             break;
/*     */ 
/*     */           
/*     */           case 3:
/* 328 */             solidColor = new Color(((Color)this.doubleSafeColorSolid.getValue()).getColorColor().getRed(), ((Color)this.doubleSafeColorSolid.getValue()).getColorColor().getGreen(), ((Color)this.doubleSafeColorSolid.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.doubleSafeColorSolid.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 329 */             linesColor = new Color(((Color)this.doubleSafeColorLines.getValue()).getColorColor().getRed(), ((Color)this.doubleSafeColorLines.getValue()).getColorColor().getGreen(), ((Color)this.doubleSafeColorLines.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.doubleSafeColorLines.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 330 */             solidColor2 = new Color(((Color)this.doubleSafeGradientColorSolid.getValue()).getColorColor().getRed(), ((Color)this.doubleSafeGradientColorSolid.getValue()).getColorColor().getGreen(), ((Color)this.doubleSafeGradientColorSolid.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.doubleSafeGradientColorSolid.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 331 */             linesColor2 = new Color(((Color)this.doubleSafeGradientColorLines.getValue()).getColorColor().getRed(), ((Color)this.doubleSafeGradientColorLines.getValue()).getColorColor().getGreen(), ((Color)this.doubleSafeGradientColorLines.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.doubleSafeGradientColorLines.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/*     */             break;
/*     */ 
/*     */           
/*     */           default:
/* 336 */             solidColor = new Color(((Color)this.doubleUnSafeColorSolid.getValue()).getColorColor().getRed(), ((Color)this.doubleUnSafeColorSolid.getValue()).getColorColor().getGreen(), ((Color)this.doubleUnSafeColorSolid.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.doubleUnSafeColorSolid.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 337 */             linesColor = new Color(((Color)this.doubleUnSafeColorLines.getValue()).getColorColor().getRed(), ((Color)this.doubleUnSafeColorLines.getValue()).getColorColor().getGreen(), ((Color)this.doubleUnSafeColorLines.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.doubleUnSafeColorLines.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 338 */             solidColor2 = new Color(((Color)this.doubleUnSafeGradientColorSolid.getValue()).getColorColor().getRed(), ((Color)this.doubleUnSafeGradientColorSolid.getValue()).getColorColor().getGreen(), ((Color)this.doubleUnSafeGradientColorSolid.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.doubleUnSafeGradientColorSolid.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/* 339 */             linesColor2 = new Color(((Color)this.doubleUnSafeGradientColorLines.getValue()).getColorColor().getRed(), ((Color)this.doubleUnSafeGradientColorLines.getValue()).getColorColor().getGreen(), ((Color)this.doubleUnSafeGradientColorLines.getValue()).getColorColor().getBlue(), (int)MathUtilFuckYou.clamp(((Color)this.doubleUnSafeGradientColorLines.getValue()).getAlpha() * alphaFactor, 0.0F, 255.0F));
/*     */             break;
/*     */         } 
/*     */ 
/*     */         
/* 344 */         if (((Boolean)this.solidBox.getValue()).booleanValue()) {
/* 345 */           if (!((Boolean)this.wallSolid.getValue()).booleanValue()) {
/* 346 */             GL11.glEnable(2929);
/*     */           }
/*     */           
/* 349 */           if (((Boolean)this.flatSolidBox.getValue()).booleanValue()) {
/* 350 */             SpartanTessellator.drawDoubleBlockFlatFullBox(holeVec1, holeVec2, !((Boolean)this.wallSolid.getValue()).booleanValue(), solidColor.getRGB());
/*     */           
/*     */           }
/* 353 */           else if (((Boolean)this.gradientSolidBox.getValue()).booleanValue()) {
/* 354 */             if (((Boolean)this.pyramidMode.getValue()).booleanValue()) {
/* 355 */               SpartanTessellator.drawGradientDoubleBlockFullPyramid(holeVec1, holeVec2, !((Boolean)this.wallSolid.getValue()).booleanValue(), flagx, flagz, solidColor.getRGB(), solidColor2.getRGB());
/*     */             } else {
/*     */               
/* 358 */               SpartanTessellator.drawGradientDoubleBlockFullBox(holeVec1, holeVec2, !((Boolean)this.wallSolid.getValue()).booleanValue(), ((Boolean)this.sidesOnly.getValue()).booleanValue(), solidColor.getRGB(), solidColor2.getRGB());
/*     */             }
/*     */           
/*     */           }
/* 362 */           else if (((Boolean)this.pyramidMode.getValue()).booleanValue()) {
/* 363 */             SpartanTessellator.drawDoubleBlockFullPyramid(holeVec1, holeVec2, !((Boolean)this.wallSolid.getValue()).booleanValue(), flagx, flagz, solidColor.getRGB());
/*     */           } else {
/*     */             
/* 366 */             SpartanTessellator.drawDoubleBlockFullBox(holeVec1, holeVec2, !((Boolean)this.wallSolid.getValue()).booleanValue(), solidColor.getRGB());
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 371 */           if (!((Boolean)this.wallSolid.getValue()).booleanValue()) {
/* 372 */             GL11.glDisable(2929);
/*     */           }
/*     */         } 
/*     */         
/* 376 */         if (((Boolean)this.linesBox.getValue()).booleanValue()) {
/* 377 */           if (!((Boolean)this.wallLines.getValue()).booleanValue()) {
/* 378 */             GL11.glEnable(2929);
/*     */           }
/*     */           
/* 381 */           if (((Boolean)this.flatLinesBox.getValue()).booleanValue()) {
/* 382 */             SpartanTessellator.drawDoubleBlockFlatLineBox(holeVec1, holeVec2, !((Boolean)this.wallLines.getValue()).booleanValue(), ((Float)this.lineWidth.getValue()).floatValue(), linesColor.getRGB());
/*     */           
/*     */           }
/* 385 */           else if (((Boolean)this.gradientLinesBox.getValue()).booleanValue()) {
/* 386 */             if (((Boolean)this.pyramidMode.getValue()).booleanValue()) {
/* 387 */               SpartanTessellator.drawGradientDoubleBlockLinePyramid(holeVec1, holeVec2, !((Boolean)this.wallLines.getValue()).booleanValue(), ((Float)this.lineWidth.getValue()).floatValue(), flagx, flagz, linesColor.getRGB(), linesColor2.getRGB());
/*     */             } else {
/*     */               
/* 390 */               SpartanTessellator.drawGradientDoubleBlockLineBox(holeVec1, holeVec2, !((Boolean)this.wallLines.getValue()).booleanValue(), ((Float)this.lineWidth.getValue()).floatValue(), linesColor.getRGB(), linesColor2.getRGB());
/*     */             }
/*     */           
/*     */           }
/* 394 */           else if (((Boolean)this.pyramidMode.getValue()).booleanValue()) {
/* 395 */             SpartanTessellator.drawDoubleBlockLinePyramid(holeVec1, holeVec2, !((Boolean)this.wallLines.getValue()).booleanValue(), ((Float)this.lineWidth.getValue()).floatValue(), flagx, flagz, linesColor.getRGB());
/*     */           } else {
/*     */             
/* 398 */             SpartanTessellator.drawDoubleBlockLineBox(holeVec1, holeVec2, !((Boolean)this.wallLines.getValue()).booleanValue(), ((Float)this.lineWidth.getValue()).floatValue(), linesColor.getRGB());
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 403 */           if (!((Boolean)this.wallLines.getValue()).booleanValue()) {
/* 404 */             GL11.glDisable(2929);
/*     */           }
/*     */         } 
/*     */         
/* 408 */         if (((Boolean)this.xCross.getValue()).booleanValue()) {
/* 409 */           if (!((Boolean)this.wallLines.getValue()).booleanValue()) {
/* 410 */             GL11.glEnable(2929);
/*     */           }
/*     */           
/* 413 */           if (((Boolean)this.flatXCross.getValue()).booleanValue() || ((Boolean)this.pyramidMode.getValue()).booleanValue()) {
/* 414 */             SpartanTessellator.drawDoublePointFlatXCross(holeVec1, holeVec2, ((Float)this.lineWidth.getValue()).floatValue(), linesColor.getRGB());
/*     */           
/*     */           }
/* 417 */           else if (((Boolean)this.gradientXCross.getValue()).booleanValue()) {
/* 418 */             SpartanTessellator.drawGradientDoublePointXCross(holeVec1, holeVec2, ((Float)this.lineWidth.getValue()).floatValue(), linesColor.getRGB(), linesColor2.getRGB());
/*     */           } else {
/*     */             
/* 421 */             SpartanTessellator.drawDoublePointXCross(holeVec1, holeVec2, ((Float)this.lineWidth.getValue()).floatValue(), linesColor.getRGB());
/*     */           } 
/*     */ 
/*     */           
/* 425 */           if (!((Boolean)this.wallLines.getValue()).booleanValue()) {
/* 426 */             GL11.glDisable(2929);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/* 433 */   private final Set<Block> validBlocks = Sets.newHashSet((Object[])new Block[] { Blocks.field_150357_h, Blocks.field_150343_Z, Blocks.field_150467_bQ, Blocks.field_150477_bB, Blocks.field_180401_cv, Blocks.field_150381_bn });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EnumFacing[] doubleHoleDirections(EnumFacing facing) {
/* 443 */     switch (facing) {
/*     */       case EAST:
/* 445 */         return new EnumFacing[] { EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case WEST:
/* 453 */         return new EnumFacing[] { EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.WEST };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case NORTH:
/* 461 */         return new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case SOUTH:
/* 469 */         return new EnumFacing[] { EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST };
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 477 */     return new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int sort(boolean isDouble, boolean isSafe) {
/* 485 */     if (isDouble && isSafe) {
/* 486 */       return 3;
/*     */     }
/* 488 */     if (isDouble) {
/* 489 */       return 4;
/*     */     }
/*     */     
/* 492 */     if (isSafe) {
/* 493 */       return 1;
/*     */     }
/*     */     
/* 496 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateHoles() {
/* 501 */     this.inRangeBlocks.clear();
/* 502 */     this.toRenderPos.clear();
/* 503 */     if (((Boolean)this.mergeDoubleHoles.getValue()).booleanValue()) this.toRenderDoublePos.clear(); 
/* 504 */     HashMap<BlockPos, Integer> cachedDoublePos = new HashMap<>();
/*     */     
/* 506 */     int rangeMax = (int)Math.ceil((((Float)this.range.getValue()).floatValue() + 1.0F));
/* 507 */     for (int x = (int)((mc.field_71439_g.func_174791_d()).field_72450_a - rangeMax); x < (int)((mc.field_71439_g.func_174791_d()).field_72450_a + rangeMax); x++) {
/* 508 */       for (int z = (int)((mc.field_71439_g.func_174791_d()).field_72449_c - rangeMax); z < (int)((mc.field_71439_g.func_174791_d()).field_72449_c + rangeMax); z++) {
/* 509 */         for (int y = (int)((mc.field_71439_g.func_174791_d()).field_72448_b + rangeMax); y > (int)((mc.field_71439_g.func_174791_d()).field_72448_b - rangeMax); y--) {
/* 510 */           if (MathUtilFuckYou.getDistance(mc.field_71439_g.func_174791_d(), new Vec3d(x, y, z)) <= ((Float)this.range.getValue()).floatValue()) {
/* 511 */             this.inRangeBlocks.add(new BlockPos(x, y, z));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 517 */     for (BlockPos pos : this.inRangeBlocks) {
/* 518 */       if (mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a) {
/*     */         continue;
/*     */       }
/*     */       
/* 522 */       if (cachedDoublePos.containsKey(pos)) {
/*     */         continue;
/*     */       }
/*     */       
/* 526 */       Block downBlock = mc.field_71441_e.func_180495_p(BlockUtil.extrudeBlock(pos, EnumFacing.DOWN)).func_177230_c();
/*     */       
/* 528 */       if (mc.field_71441_e.func_180495_p(BlockUtil.extrudeBlock(pos, EnumFacing.UP)).func_177230_c() != Blocks.field_150350_a) {
/*     */         continue;
/*     */       }
/*     */       
/* 532 */       if (mc.field_71441_e.func_180495_p(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock(pos, EnumFacing.UP), EnumFacing.UP)).func_177230_c() != Blocks.field_150350_a) {
/*     */         continue;
/*     */       }
/*     */       
/* 536 */       if (((Boolean)this.miscBlocks.getValue()).booleanValue() ? !this.validBlocks.contains(downBlock) : (downBlock != Blocks.field_150343_Z && downBlock != Blocks.field_150357_h)) {
/*     */         continue;
/*     */       }
/*     */       
/* 540 */       boolean isDouble = false;
/* 541 */       boolean isSafe = true;
/* 542 */       boolean checkForDoubleFlag = false;
/* 543 */       EnumFacing faceToCheckDouble = EnumFacing.EAST;
/* 544 */       BlockPos extendedBlockPos = pos;
/* 545 */       int placeableIndex = 0;
/* 546 */       int validBlocksIndex = 0;
/* 547 */       int advancedCheckIndex = 0;
/* 548 */       int doubleHoleCheckIndex = 0;
/*     */       
/* 550 */       if (downBlock != Blocks.field_150357_h && downBlock != Blocks.field_180401_cv) {
/* 551 */         isSafe = false;
/*     */       }
/*     */ 
/*     */       
/* 555 */       for (EnumFacing facing : EnumFacing.field_176754_o) {
/* 556 */         extendedBlockPos = BlockUtil.extrudeBlock(pos, facing);
/* 557 */         Block block = mc.field_71441_e.func_180495_p(extendedBlockPos).func_177230_c();
/*     */         
/* 559 */         if (((Boolean)this.doubleHoles.getValue()).booleanValue()) {
/* 560 */           if (!BlockUtil.isBlockPlaceable(extendedBlockPos)) {
/* 561 */             placeableIndex++;
/* 562 */             checkForDoubleFlag = true;
/* 563 */             faceToCheckDouble = facing;
/*     */           } 
/*     */           
/* 566 */           if (placeableIndex > 1 || cachedDoublePos.containsKey(pos)) {
/* 567 */             checkForDoubleFlag = false;
/*     */           }
/*     */         } 
/*     */         
/* 571 */         if (((Boolean)this.miscBlocks.getValue()).booleanValue() ? !this.validBlocks.contains(block) : (block != Blocks.field_150343_Z && block != Blocks.field_150357_h)) {
/*     */ 
/*     */ 
/*     */           
/* 575 */           if (block != Blocks.field_150357_h && block != Blocks.field_180401_cv) {
/* 576 */             isSafe = false;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 581 */           if (((Boolean)this.advancedCheck.getValue()).booleanValue()) {
/* 582 */             boolean b = false;
/* 583 */             if (BlockUtil.isBlockPlaceable(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock(pos, EnumFacing.UP), EnumFacing.UP), EnumFacing.UP)) && (
/* 584 */               BlockUtil.isBlockPlaceable(BlockUtil.extrudeBlock(extendedBlockPos, EnumFacing.UP)) || BlockUtil.isBlockPlaceable(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock(extendedBlockPos, EnumFacing.UP), EnumFacing.UP)))) {
/* 585 */               b = true;
/*     */             }
/*     */ 
/*     */             
/* 589 */             if (BlockUtil.isBlockPlaceable(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock(pos, EnumFacing.UP), EnumFacing.UP), EnumFacing.UP), EnumFacing.UP)) && 
/* 590 */               BlockUtil.isBlockPlaceable(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock(extendedBlockPos, EnumFacing.UP), EnumFacing.UP))) {
/* 591 */               b = true;
/*     */             }
/*     */ 
/*     */             
/* 595 */             if (b) {
/* 596 */               advancedCheckIndex++;
/*     */             }
/*     */           } 
/*     */           
/* 600 */           validBlocksIndex++;
/*     */         } 
/*     */       } 
/* 603 */       if (((Boolean)this.advancedCheck.getValue()).booleanValue()) {
/* 604 */         if (advancedCheckIndex >= 4) {
/*     */           continue;
/*     */         }
/*     */         
/* 608 */         if (((Boolean)this.doubleHoles.getValue()).booleanValue() && checkForDoubleFlag && advancedCheckIndex == 3) {
/* 609 */           doubleHoleCheckIndex++;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 614 */       if (((Boolean)this.doubleHoles.getValue()).booleanValue() && checkForDoubleFlag) {
/* 615 */         extendedBlockPos = BlockUtil.extrudeBlock(pos, faceToCheckDouble);
/* 616 */         Block doubleDownBlock = mc.field_71441_e.func_180495_p(BlockUtil.extrudeBlock(extendedBlockPos, EnumFacing.DOWN)).func_177230_c();
/*     */         
/* 618 */         if (((Boolean)this.miscBlocks.getValue()).booleanValue() ? !this.validBlocks.contains(doubleDownBlock) : (doubleDownBlock != Blocks.field_150343_Z && doubleDownBlock != Blocks.field_150357_h)) {
/*     */           continue;
/*     */         }
/*     */         
/* 622 */         if (doubleDownBlock != Blocks.field_150357_h && doubleDownBlock != Blocks.field_180401_cv) {
/* 623 */           isSafe = false;
/*     */         }
/*     */         
/* 626 */         if (((Boolean)this.advancedCheck.getValue()).booleanValue()) {
/* 627 */           if (BlockUtil.isBlockPlaceable(BlockUtil.extrudeBlock(extendedBlockPos, EnumFacing.UP)) || BlockUtil.isBlockPlaceable(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock(extendedBlockPos, EnumFacing.UP), EnumFacing.UP))) {
/* 628 */             doubleHoleCheckIndex++;
/*     */           }
/*     */           
/* 631 */           if (doubleHoleCheckIndex >= 2) {
/*     */             continue;
/*     */           }
/*     */         } 
/*     */         
/* 636 */         for (EnumFacing facing : doubleHoleDirections(faceToCheckDouble)) {
/* 637 */           Block doubleBlockExtendedBlock = mc.field_71441_e.func_180495_p(BlockUtil.extrudeBlock(extendedBlockPos, facing)).func_177230_c();
/*     */           
/* 639 */           if (((Boolean)this.miscBlocks.getValue()).booleanValue() ? !this.validBlocks.contains(doubleBlockExtendedBlock) : (doubleBlockExtendedBlock != Blocks.field_150343_Z && doubleBlockExtendedBlock != Blocks.field_150357_h)) {
/*     */             break;
/*     */           }
/*     */           
/* 643 */           if (doubleBlockExtendedBlock != Blocks.field_150357_h && doubleBlockExtendedBlock != Blocks.field_180401_cv) {
/* 644 */             isSafe = false;
/*     */           }
/*     */ 
/*     */           
/* 648 */           if (((Boolean)this.advancedCheck.getValue()).booleanValue()) {
/* 649 */             BlockPos doubleExtendedBlockPos = BlockUtil.extrudeBlock(extendedBlockPos, facing);
/* 650 */             boolean b = false;
/* 651 */             if (BlockUtil.isBlockPlaceable(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock(extendedBlockPos, EnumFacing.UP), EnumFacing.UP), EnumFacing.UP)) && (
/* 652 */               BlockUtil.isBlockPlaceable(BlockUtil.extrudeBlock(doubleExtendedBlockPos, EnumFacing.UP)) || BlockUtil.isBlockPlaceable(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock(doubleExtendedBlockPos, EnumFacing.UP), EnumFacing.UP)))) {
/* 653 */               b = true;
/*     */             }
/*     */ 
/*     */             
/* 657 */             if (BlockUtil.isBlockPlaceable(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock(extendedBlockPos, EnumFacing.UP), EnumFacing.UP), EnumFacing.UP), EnumFacing.UP)) && 
/* 658 */               BlockUtil.isBlockPlaceable(BlockUtil.extrudeBlock(BlockUtil.extrudeBlock(doubleExtendedBlockPos, EnumFacing.UP), EnumFacing.UP))) {
/* 659 */               b = true;
/*     */             }
/*     */ 
/*     */             
/* 663 */             if (b) {
/* 664 */               advancedCheckIndex++;
/*     */             }
/*     */           } 
/*     */           
/* 668 */           validBlocksIndex++;
/*     */         } 
/*     */         
/* 671 */         if (((Boolean)this.advancedCheck.getValue()).booleanValue() && advancedCheckIndex >= 6) {
/*     */           continue;
/*     */         }
/*     */         
/* 675 */         if (validBlocksIndex == 6) {
/* 676 */           isDouble = true;
/*     */         } else {
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 683 */       int sort = sort(isDouble, isSafe);
/*     */       
/* 685 */       if (((Boolean)this.mergeDoubleHoles.getValue()).booleanValue() && ((Boolean)this.doubleHoles.getValue()).booleanValue() && isDouble) {
/* 686 */         this.toRenderDoublePos.put(new Pair(pos, extendedBlockPos), Integer.valueOf(sort));
/* 687 */         cachedDoublePos.put(extendedBlockPos, Integer.valueOf(sort));
/*     */         continue;
/*     */       } 
/* 690 */       if (isDouble || validBlocksIndex >= 4) {
/* 691 */         this.toRenderPos.put(pos, Integer.valueOf(sort));
/*     */       }
/*     */       
/* 694 */       if (isDouble) {
/* 695 */         cachedDoublePos.put(extendedBlockPos, Integer.valueOf(sort));
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 700 */     if (!((Boolean)this.mergeDoubleHoles.getValue()).booleanValue() && ((Boolean)this.doubleHoles.getValue()).booleanValue() && !cachedDoublePos.isEmpty()) {
/* 701 */       for (Map.Entry<BlockPos, Integer> entry : cachedDoublePos.entrySet()) {
/* 702 */         if (!this.toRenderPos.containsKey(entry.getKey())) {
/* 703 */           this.toRenderPos.put(entry.getKey(), entry.getValue());
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private float getRolledHeight(float offset) {
/* 710 */     double s = System.currentTimeMillis() * ((Float)this.rollingSpeed.getValue()).floatValue() + (offset * ((Float)this.rollingWidth.getValue()).floatValue() * 100.0F);
/* 711 */     s %= 300.0D;
/* 712 */     s = 150.0D * Math.sin((s - 75.0D) * Math.PI / 150.0D) + 150.0D;
/* 713 */     return ((Float)this.rollingHeightMax.getValue()).floatValue() + (float)s * (((Float)this.rollingHeightMin.getValue()).floatValue() - ((Float)this.rollingHeightMax.getValue()).floatValue()) / 300.0F;
/*     */   }
/*     */   
/*     */   enum Page {
/* 717 */     Calc,
/* 718 */     Render,
/* 719 */     Color;
/*     */   }
/*     */   
/*     */   enum SelfHighlightMode {
/* 723 */     Alpha,
/* 724 */     Height,
/* 725 */     None;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\visuals\HoleRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */