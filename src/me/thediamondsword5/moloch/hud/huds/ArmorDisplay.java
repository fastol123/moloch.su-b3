/*     */ package me.thediamondsword5.moloch.hud.huds;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.spartanb312.base.client.FontManager;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.hud.HUDModule;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.utils.ColorUtil;
/*     */ import net.spartanb312.base.utils.ItemUtils;
/*     */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ @ModuleInfo(name = "ArmorDisplay", category = Category.HUD, description = "Displays armor information on HUD")
/*     */ public class ArmorDisplay
/*     */   extends HUDModule
/*     */ {
/*     */   public static ArmorDisplay INSTANCE;
/*  25 */   Setting<Page> page = setting("Page", Page.ArmorRender);
/*     */   
/*  27 */   Setting<RenderMode> renderMode = setting("RenderMode", RenderMode.Simplified).des("How armor is rendered on HUD").whenAtMode(this.page, Page.ArmorRender);
/*  28 */   Setting<Boolean> horizontal = setting("Horizontal", true).des("Render items horizontally").whenAtMode(this.page, Page.ArmorRender);
/*  29 */   Setting<Boolean> shiftInWater = setting("ShiftInWater", true).des("Make display go up slightly to make room for bubbles while underwater").whenTrue(this.horizontal).whenAtMode(this.page, Page.ArmorRender);
/*  30 */   Setting<Integer> separationDist = setting("SeparationDist", 20, 0, 50).des("Distance between each armor piece").whenAtMode(this.page, Page.ArmorRender);
/*  31 */   Setting<Integer> rectsWidth = setting("RectsWidth", 16, 1, 30).des("Width of simplified rect render and damage bar").whenAtMode(this.page, Page.ArmorRender);
/*  32 */   Setting<Integer> rectHeight = setting("RectHeight", 7, 1, 20).only(v -> (this.renderMode.getValue() != RenderMode.Image)).whenAtMode(this.page, Page.ArmorRender);
/*  33 */   Setting<Boolean> roundedRect = setting("RoundedRect", false).des("Rounded rect for non image render mode").only(v -> (this.renderMode.getValue() != RenderMode.Image)).whenAtMode(this.page, Page.ArmorRender);
/*  34 */   Setting<Float> roundedRectRadius = setting("RoundedRectRadius", 0.6F, 0.0F, 1.0F).des("Radius of rounded rect").whenTrue(this.roundedRect).only(v -> (this.renderMode.getValue() != RenderMode.Image)).whenAtMode(this.page, Page.ArmorRender);
/*  35 */   Setting<Boolean> roundedRectTopRight = setting("RoundedRectTopRight", true).des("Rounded corner for rounded rect top right").whenTrue(this.roundedRect).only(v -> (this.renderMode.getValue() != RenderMode.Image)).whenAtMode(this.page, Page.ArmorRender);
/*  36 */   Setting<Boolean> roundedRectTopLeft = setting("RoundedRectTopLeft", true).des("Rounded corner for rounded rect top left").whenTrue(this.roundedRect).only(v -> (this.renderMode.getValue() != RenderMode.Image)).whenAtMode(this.page, Page.ArmorRender);
/*  37 */   Setting<Boolean> roundedRectDownRight = setting("RoundedRectDownRight", true).des("Rounded corner for rounded rect bottom right").whenTrue(this.roundedRect).only(v -> (this.renderMode.getValue() != RenderMode.Image)).whenAtMode(this.page, Page.ArmorRender);
/*  38 */   Setting<Boolean> roundedRectDownLeft = setting("RoundedRectDownLeft", true).des("Rounded corner for rounded rect bottom left").whenTrue(this.roundedRect).only(v -> (this.renderMode.getValue() != RenderMode.Image)).whenAtMode(this.page, Page.ArmorRender);
/*  39 */   Setting<Boolean> borderedRect = setting("BorderedRect", true).des("Bordered rect for non image render mode").only(v -> (this.renderMode.getValue() != RenderMode.Image)).whenAtMode(this.page, Page.ArmorRender);
/*  40 */   Setting<Float> borderedRectOffset = setting("BorderedRectOffset", 2.0F, 0.0F, 5.0F).des("Bordered rect outline offset").whenTrue(this.borderedRect).only(v -> (this.renderMode.getValue() != RenderMode.Image)).whenAtMode(this.page, Page.ArmorRender);
/*  41 */   Setting<Float> borderedRectWidth = setting("BorderedRectWidth", 1.2F, 1.0F, 2.0F).des("Bordered rect outline width").whenTrue(this.borderedRect).only(v -> (this.renderMode.getValue() != RenderMode.Image)).whenAtMode(this.page, Page.ArmorRender);
/*     */   
/*  43 */   Setting<Boolean> armorCount = setting("ArmorCount", false).des("Displays how many of a piece of armor you have left in your inventory").whenAtMode(this.page, Page.Text);
/*  44 */   Setting<Integer> armorCountOffsetX = setting("ArmorCountX", 14, -50, 50).whenTrue(this.armorCount).whenAtMode(this.page, Page.Text);
/*  45 */   Setting<Integer> armorCountOffsetY = setting("ArmorCountY", -2, -50, 50).whenTrue(this.armorCount).whenAtMode(this.page, Page.Text);
/*  46 */   Setting<Float> armorCountScale = setting("ArmorCountScale", 0.8F, 0.0F, 2.0F).whenTrue(this.armorCount).whenAtMode(this.page, Page.Text);
/*  47 */   Setting<Boolean> armorCountShadow = setting("ArmorCountShadow", true).des("Draw string shadow on armor count text").whenTrue(this.armorCount).whenAtMode(this.page, Page.Text);
/*  48 */   Setting<Boolean> DMGPercentText = setting("DMGPercentText", false).des("Shows how much durability is left as a number").whenAtMode(this.page, Page.Text);
/*  49 */   Setting<Boolean> DMGPercentShadow = setting("DMGPercentShadow", true).des("Draw string shadow on durability percentage text").whenTrue(this.DMGPercentText).whenAtMode(this.page, Page.Text);
/*  50 */   Setting<Integer> DMGPercentOffsetX = setting("DMGPercentX", 3, -50, 50).whenTrue(this.DMGPercentText).whenAtMode(this.page, Page.Text);
/*  51 */   Setting<Integer> DMGPercentOffsetY = setting("DMGPercentY", -7, -50, 50).whenTrue(this.DMGPercentText).whenAtMode(this.page, Page.Text);
/*  52 */   Setting<Float> DMGPercentScale = setting("DMGPercentScale", 0.7F, 0.0F, 2.0F).whenTrue(this.DMGPercentText).whenAtMode(this.page, Page.Text);
/*     */   
/*  54 */   Setting<Boolean> shadow = setting("Shadow", true).des("Draw gradient shadow behind armor").whenAtMode(this.page, Page.Shadow);
/*  55 */   Setting<Boolean> shadowIndependent = setting("ShadowIndependent", true).des("Draw different shadow rects for each piece of armor").whenTrue(this.shadow).whenAtMode(this.page, Page.Shadow);
/*  56 */   Setting<Float> shadowSize = setting("ShadowSize", 0.2F, 0.0F, 1.0F).des("Shadow size").whenTrue(this.shadow).whenAtMode(this.page, Page.Shadow);
/*  57 */   Setting<Integer> shadowOffset = setting("ShadowOffset", 1, -50, 50).whenFalse(this.shadowIndependent).whenTrue(this.shadow).whenAtMode(this.page, Page.Shadow);
/*  58 */   Setting<Integer> shadowOffsetX = setting("ShadowOffsetX", 2, -50, 50).only(v -> (((Boolean)this.shadowIndependent.getValue()).booleanValue() || ((Boolean)this.horizontal.getValue()).booleanValue())).whenTrue(this.shadow).whenAtMode(this.page, Page.Shadow);
/*  59 */   Setting<Integer> shadowOffsetY = setting("ShadowOffsetY", 2, -50, 50).only(v -> (((Boolean)this.shadowIndependent.getValue()).booleanValue() || !((Boolean)this.horizontal.getValue()).booleanValue())).whenTrue(this.shadow).whenAtMode(this.page, Page.Shadow);
/*  60 */   Setting<Float> shadowThickness = setting("ShadowThickness", 9.0F, 0.0F, 50.0F).whenFalse(this.shadowIndependent).whenTrue(this.shadow).whenAtMode(this.page, Page.Shadow);
/*  61 */   Setting<Float> shadowWidth = setting("ShadowWidth", 1.9F, 0.0F, 50.0F).whenFalse(this.shadowIndependent).whenTrue(this.shadow).whenAtMode(this.page, Page.Shadow);
/*  62 */   Setting<Float> shadowScale = setting("ShadowScale", 0.7F, 0.1F, 2.0F).whenTrue(this.shadowIndependent).whenTrue(this.shadow).whenAtMode(this.page, Page.Shadow);
/*  63 */   Setting<Integer> shadowAlpha = setting("ShadowAlpha", 144, 0, 255).des("Shadow alpha").whenTrue(this.shadow).whenAtMode(this.page, Page.Shadow);
/*     */   
/*  65 */   Setting<Boolean> damageBar = setting("DamageBar", true).des("Render a bar to show how much durability is left in a piece of armor").whenAtMode(this.page, Page.DMGBar);
/*  66 */   Setting<Boolean> damageColorShift = setting("DMGColorShift", true).des("Makes the damage color shift as a piece of armor gets mroe damaged").whenTrue(this.damageBar).whenAtMode(this.page, Page.DMGBar);
/*  67 */   Setting<Integer> DMGBarOffsetY = setting("DMGBarY", 9, -50, 50).whenTrue(this.damageBar).whenAtMode(this.page, Page.DMGBar);
/*  68 */   Setting<Float> DMGBarHeight = setting("DMGBarHeight", 2.9F, 0.1F, 20.0F).whenTrue(this.damageBar).whenAtMode(this.page, Page.DMGBar);
/*  69 */   Setting<Boolean> roundedRectDMGBar = setting("RoundedRectDMGBar", false).des("Rounded rect for non image render mode damage bar").whenTrue(this.damageBar).whenAtMode(this.page, Page.DMGBar);
/*  70 */   Setting<Float> roundedRectRadiusDMGBar = setting("RRectRadiusDMGBar", 0.5F, 0.0F, 1.0F).des("Radius of rounded rect damage bar").whenTrue(this.roundedRectDMGBar).whenTrue(this.damageBar).whenAtMode(this.page, Page.DMGBar);
/*  71 */   Setting<Boolean> borderedRectDMGBar = setting("BorderedRectDMGBar", true).des("Bordered rect for non image render mode damage bar").whenTrue(this.damageBar).whenAtMode(this.page, Page.DMGBar);
/*  72 */   Setting<Float> borderedRectOffsetDMGBar = setting("BRectOffsetDMGBar", 2.0F, 0.0F, 5.0F).des("Bordered rect outline offset damage bar").whenTrue(this.borderedRectDMGBar).whenTrue(this.damageBar).whenAtMode(this.page, Page.DMGBar);
/*  73 */   Setting<Float> borderedRectWidthDMGBar = setting("BRectWidthDMGBar", 1.0F, 1.0F, 2.0F).des("Bordered rect outline width damage bar").whenTrue(this.borderedRectDMGBar).whenTrue(this.damageBar).whenAtMode(this.page, Page.DMGBar);
/*     */   
/*  75 */   Setting<Color> rectColorElytra = setting("RectColorElytra", new Color((new Color(150, 100, 150, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 150, 100, 150, 255)).only(v -> (this.renderMode.getValue() != RenderMode.Image)).whenAtMode(this.page, Page.Colors);
/*  76 */   Setting<Color> DMGColorElytra = setting("DMGColorElytra", new Color((new Color(50, 255, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 255)).only(v -> (((Boolean)this.damageBar.getValue()).booleanValue() || ((Boolean)this.DMGPercentText.getValue()).booleanValue())).whenAtMode(this.page, Page.Colors);
/*  77 */   Setting<Color> DMGColorElytra2 = setting("DMGColorElytra2", new Color((new Color(255, 50, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 255)).only(v -> (((Boolean)this.damageBar.getValue()).booleanValue() || ((Boolean)this.DMGPercentText.getValue()).booleanValue())).whenTrue(this.damageColorShift).whenAtMode(this.page, Page.Colors);
/*  78 */   Setting<Color> rectColorDiamond = setting("RectColorDiamond", new Color((new Color(53, 200, 200, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 53, 175, 175, 255)).only(v -> (this.renderMode.getValue() != RenderMode.Image)).whenAtMode(this.page, Page.Colors);
/*  79 */   Setting<Color> DMGColorDiamond = setting("DMGColorDiamond", new Color((new Color(50, 255, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 255)).only(v -> (((Boolean)this.damageBar.getValue()).booleanValue() || ((Boolean)this.DMGPercentText.getValue()).booleanValue())).whenAtMode(this.page, Page.Colors);
/*  80 */   Setting<Color> DMGColorDiamond2 = setting("DMGColorDiamond2", new Color((new Color(255, 50, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 255)).only(v -> (((Boolean)this.damageBar.getValue()).booleanValue() || ((Boolean)this.DMGPercentText.getValue()).booleanValue())).whenTrue(this.damageColorShift).whenAtMode(this.page, Page.Colors);
/*  81 */   Setting<Color> rectColorIron = setting("RectColorIron", new Color((new Color(200, 200, 200, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 200, 200, 200, 255)).only(v -> (this.renderMode.getValue() != RenderMode.Image)).whenAtMode(this.page, Page.Colors);
/*  82 */   Setting<Color> DMGColorIron = setting("DMGColorIron", new Color((new Color(50, 255, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 255)).only(v -> (((Boolean)this.damageBar.getValue()).booleanValue() || ((Boolean)this.DMGPercentText.getValue()).booleanValue())).whenAtMode(this.page, Page.Colors);
/*  83 */   Setting<Color> DMGColorIron2 = setting("DMGColorIron2", new Color((new Color(255, 50, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 255)).only(v -> (((Boolean)this.damageBar.getValue()).booleanValue() || ((Boolean)this.DMGPercentText.getValue()).booleanValue())).whenTrue(this.damageColorShift).whenAtMode(this.page, Page.Colors);
/*  84 */   Setting<Color> rectColorGold = setting("RectColorGold", new Color((new Color(200, 200, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 200, 200, 50, 255)).only(v -> (this.renderMode.getValue() != RenderMode.Image)).whenAtMode(this.page, Page.Colors);
/*  85 */   Setting<Color> DMGColorGold = setting("DMGColorGold", new Color((new Color(50, 255, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 255)).only(v -> (((Boolean)this.damageBar.getValue()).booleanValue() || ((Boolean)this.DMGPercentText.getValue()).booleanValue())).whenAtMode(this.page, Page.Colors);
/*  86 */   Setting<Color> DMGColorGold2 = setting("DMGColorGold2", new Color((new Color(255, 50, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 255)).only(v -> (((Boolean)this.damageBar.getValue()).booleanValue() || ((Boolean)this.DMGPercentText.getValue()).booleanValue())).whenTrue(this.damageColorShift).whenAtMode(this.page, Page.Colors);
/*  87 */   Setting<Color> rectColorChainmail = setting("RectColorChainmail", new Color((new Color(150, 150, 150, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 150, 150, 150, 255)).only(v -> (this.renderMode.getValue() != RenderMode.Image)).whenAtMode(this.page, Page.Colors);
/*  88 */   Setting<Color> DMGColorChainmail = setting("DMGColorChainmail", new Color((new Color(50, 255, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 255)).only(v -> (((Boolean)this.damageBar.getValue()).booleanValue() || ((Boolean)this.DMGPercentText.getValue()).booleanValue())).whenAtMode(this.page, Page.Colors);
/*  89 */   Setting<Color> DMGColorChainmail2 = setting("DMGColorChainmail2", new Color((new Color(255, 50, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 255)).only(v -> (((Boolean)this.damageBar.getValue()).booleanValue() || ((Boolean)this.DMGPercentText.getValue()).booleanValue())).whenTrue(this.damageColorShift).whenAtMode(this.page, Page.Colors);
/*  90 */   Setting<Color> rectColorLeather = setting("RectColorLeather", new Color((new Color(110, 75, 0, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 110, 75, 0, 255)).only(v -> (this.renderMode.getValue() != RenderMode.Image)).whenAtMode(this.page, Page.Colors);
/*  91 */   Setting<Color> DMGColorLeather = setting("DMGColorLeather", new Color((new Color(50, 255, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 255)).only(v -> (((Boolean)this.damageBar.getValue()).booleanValue() || ((Boolean)this.DMGPercentText.getValue()).booleanValue())).whenAtMode(this.page, Page.Colors);
/*  92 */   Setting<Color> DMGColorLeather2 = setting("DMGColorLeather2", new Color((new Color(255, 50, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 255)).only(v -> (((Boolean)this.damageBar.getValue()).booleanValue() || ((Boolean)this.DMGPercentText.getValue()).booleanValue())).whenTrue(this.damageColorShift).whenAtMode(this.page, Page.Colors);
/*  93 */   Setting<Color> rectColorDMGBarBG = setting("RectColorDMGBarBG", new Color((new Color(20, 20, 20, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 20, 20, 20, 255)).whenTrue(this.damageBar).whenAtMode(this.page, Page.Colors);
/*     */   
/*     */   public ArmorDisplay() {
/*  96 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onHUDRender(ScaledResolution resolution) {
/* 101 */     int x = ((Boolean)this.horizontal.getValue()).booleanValue() ? (this.x + ((Integer)this.separationDist.getValue()).intValue() * 3) : this.x;
/* 102 */     int y = ((Boolean)this.horizontal.getValue()).booleanValue() ? (this.y - ((((Boolean)this.shiftInWater.getValue()).booleanValue() && mc.field_71439_g.func_70090_H() && mc.field_71442_b.func_78763_f()) ? 10 : 0)) : (this.y + ((Integer)this.separationDist.getValue()).intValue() * 3);
/* 103 */     this.width = ((Boolean)this.horizontal.getValue()).booleanValue() ? (((Integer)this.separationDist.getValue()).intValue() * 4) : (((Integer)this.rectsWidth.getValue()).intValue() + 5);
/* 104 */     this.height = ((Boolean)this.horizontal.getValue()).booleanValue() ? ((this.renderMode.getValue() == RenderMode.Simplified) ? (((Integer)this.rectHeight.getValue()).intValue() + 5) : 20) : (((Integer)this.separationDist.getValue()).intValue() * 4);
/*     */     
/* 106 */     if (((Boolean)this.shadow.getValue()).booleanValue() && !((Boolean)this.shadowIndependent.getValue()).booleanValue()) {
/* 107 */       int i = 0;
/* 108 */       for (ItemStack armorItem : mc.field_71439_g.field_71071_by.field_70460_b) {
/* 109 */         if (armorItem.func_77973_b() != Items.field_190931_a) i++;
/*     */       
/*     */       } 
/* 112 */       if (i >= 1) {
/* 113 */         RenderUtils2D.drawBetterRoundRectFade(((Boolean)this.horizontal.getValue()).booleanValue() ? ((this.x + ((Integer)this.shadowOffsetX.getValue()).intValue()) + ((Float)this.shadowWidth.getValue()).floatValue()) : (this.x + ((Integer)this.shadowOffset.getValue()).intValue()), ((Boolean)this.horizontal.getValue()).booleanValue() ? (y + ((Integer)this.shadowOffset.getValue()).intValue()) : ((y - this.height + ((Integer)this.shadowOffsetY.getValue()).intValue()) + ((Float)this.shadowWidth.getValue()).floatValue()), ((Boolean)this.horizontal.getValue()).booleanValue() ? ((this.x + this.width + ((Integer)this.shadowOffsetX.getValue()).intValue()) - ((Float)this.shadowWidth.getValue()).floatValue()) : (this.x + ((Float)this.shadowThickness.getValue()).floatValue() + ((Integer)this.shadowOffset.getValue()).intValue()), ((Boolean)this.horizontal.getValue()).booleanValue() ? (y + ((Float)this.shadowThickness.getValue()).floatValue() + ((Integer)this.shadowOffset.getValue()).intValue()) : ((y + ((Integer)this.shadowOffsetY.getValue()).intValue()) - ((Float)this.shadowWidth.getValue()).floatValue()), ((Float)this.shadowSize.getValue()).floatValue(), 30.0F, false, true, false, (new Color(0, 0, 0, ((Integer)this.shadowAlpha.getValue()).intValue())).getRGB());
/*     */       }
/*     */     } 
/*     */     
/* 117 */     for (ItemStack armorItem : mc.field_71439_g.field_71071_by.field_70460_b) {
/* 118 */       if (armorItem.func_77973_b() != Items.field_190931_a) {
/* 119 */         if (((Boolean)this.shadow.getValue()).booleanValue() && ((Boolean)this.shadowIndependent.getValue()).booleanValue()) {
/* 120 */           RenderUtils2D.drawBetterRoundRectFade((x + ((Integer)this.shadowOffsetX.getValue()).intValue()), (y + ((Integer)this.shadowOffsetY.getValue()).intValue()), (x + ((Integer)this.shadowOffsetX.getValue()).intValue()) + ((Integer)this.rectsWidth.getValue()).intValue() * ((Float)this.shadowScale.getValue()).floatValue(), (y + ((Integer)this.shadowOffsetY.getValue()).intValue()) + ((Float)this.shadowScale.getValue()).floatValue() * ((this.renderMode.getValue() == RenderMode.Simplified) ? ((Integer)this.rectHeight.getValue()).intValue() : 20.0F), ((Float)this.shadowSize.getValue()).floatValue(), 30.0F, false, true, false, (new Color(0, 0, 0, ((Integer)this.shadowAlpha.getValue()).intValue())).getRGB());
/*     */         }
/*     */         
/* 123 */         switch ((RenderMode)this.renderMode.getValue()) {
/*     */           case Image:
/* 125 */             GL11.glEnable(3553);
/* 126 */             mc.func_175599_af().func_180450_b(armorItem, x, y);
/* 127 */             GL11.glDisable(3553);
/*     */             break;
/*     */ 
/*     */           
/*     */           case Simplified:
/* 132 */             renderArmorRect(x, y, armorItem.func_77973_b());
/*     */             break;
/*     */         } 
/*     */ 
/*     */         
/* 137 */         if (((Boolean)this.damageBar.getValue()).booleanValue()) {
/* 138 */           if (((Boolean)this.borderedRectDMGBar.getValue()).booleanValue()) {
/* 139 */             if (((Boolean)this.roundedRectDMGBar.getValue()).booleanValue()) {
/* 140 */               RenderUtils2D.drawRoundedRect(x + ((Float)this.borderedRectOffsetDMGBar.getValue()).floatValue() / 2.0F, (y + ((Integer)this.DMGBarOffsetY.getValue()).intValue()) + ((Float)this.borderedRectOffsetDMGBar.getValue()).floatValue() / 2.0F, ((Float)this.roundedRectRadiusDMGBar.getValue()).floatValue(), (x + ((Integer)this.rectsWidth.getValue()).intValue()) - ((Float)this.borderedRectOffsetDMGBar.getValue()).floatValue() / 2.0F, (y + ((Integer)this.DMGBarOffsetY.getValue()).intValue()) + ((Float)this.DMGBarHeight.getValue()).floatValue() - ((Float)this.borderedRectOffsetDMGBar.getValue()).floatValue() / 2.0F, false, ((Boolean)this.roundedRectTopRight.getValue()).booleanValue(), ((Boolean)this.roundedRectTopLeft.getValue()).booleanValue(), ((Boolean)this.roundedRectDownRight.getValue()).booleanValue(), ((Boolean)this.roundedRectDownLeft.getValue()).booleanValue(), ((Color)this.rectColorDMGBarBG.getValue()).getColor());
/*     */             } else {
/*     */               
/* 143 */               RenderUtils2D.drawRect(x + ((Float)this.borderedRectOffsetDMGBar.getValue()).floatValue() / 2.0F, (y + ((Integer)this.DMGBarOffsetY.getValue()).intValue()) + ((Float)this.borderedRectOffsetDMGBar.getValue()).floatValue() / 2.0F, (x + ((Integer)this.rectsWidth.getValue()).intValue()) - ((Float)this.borderedRectOffsetDMGBar.getValue()).floatValue() / 2.0F, (y + ((Integer)this.DMGBarOffsetY.getValue()).intValue()) + ((Float)this.DMGBarHeight.getValue()).floatValue() - ((Float)this.borderedRectOffsetDMGBar.getValue()).floatValue() / 2.0F, ((Color)this.rectColorDMGBarBG.getValue()).getColor());
/*     */             }
/*     */           
/*     */           }
/* 147 */           else if (((Boolean)this.roundedRectDMGBar.getValue()).booleanValue()) {
/* 148 */             RenderUtils2D.drawRoundedRect(x, (y + ((Integer)this.DMGBarOffsetY.getValue()).intValue()), ((Float)this.roundedRectRadiusDMGBar.getValue()).floatValue(), (x + ((Integer)this.rectsWidth.getValue()).intValue()), (y + ((Integer)this.DMGBarOffsetY.getValue()).intValue()) + ((Float)this.DMGBarHeight.getValue()).floatValue(), false, ((Boolean)this.roundedRectTopRight.getValue()).booleanValue(), ((Boolean)this.roundedRectTopLeft.getValue()).booleanValue(), ((Boolean)this.roundedRectDownRight.getValue()).booleanValue(), ((Boolean)this.roundedRectDownLeft.getValue()).booleanValue(), ((Color)this.rectColorDMGBarBG.getValue()).getColor());
/*     */           } else {
/*     */             
/* 151 */             RenderUtils2D.drawRect(x, (y + ((Integer)this.DMGBarOffsetY.getValue()).intValue()), (x + ((Integer)this.rectsWidth.getValue()).intValue()), (y + ((Integer)this.DMGBarOffsetY.getValue()).intValue()) + ((Float)this.DMGBarHeight.getValue()).floatValue(), ((Color)this.rectColorDMGBarBG.getValue()).getColor());
/*     */           } 
/*     */ 
/*     */           
/* 155 */           renderDMGBar(x, y + ((Integer)this.DMGBarOffsetY.getValue()).intValue(), armorItem);
/*     */         } 
/*     */         
/* 158 */         int armorNum = ItemUtils.getItemCount(armorItem.func_77973_b()) + 1;
/*     */         
/* 160 */         if (((Boolean)this.armorCount.getValue()).booleanValue() && armorNum > 1) {
/* 161 */           GL11.glTranslatef((x + ((Integer)this.armorCountOffsetX.getValue()).intValue()) * (1.0F - ((Float)this.armorCountScale.getValue()).floatValue()), (y + ((Integer)this.armorCountOffsetY.getValue()).intValue()) * (1.0F - ((Float)this.armorCountScale.getValue()).floatValue()), 0.0F);
/* 162 */           GL11.glScalef(((Float)this.armorCountScale.getValue()).floatValue(), ((Float)this.armorCountScale.getValue()).floatValue(), 1.0F);
/*     */           
/* 164 */           FontManager.drawHUD((ItemUtils.getItemCount(armorItem.func_77973_b()) + 1) + "", (x + ((Integer)this.armorCountOffsetX.getValue()).intValue()), (y + ((Integer)this.armorCountOffsetY.getValue()).intValue()), ((Boolean)this.armorCountShadow.getValue()).booleanValue(), (new Color(255, 255, 255, 255)).getRGB());
/*     */           
/* 166 */           GL11.glScalef(1.0F / ((Float)this.armorCountScale.getValue()).floatValue(), 1.0F / ((Float)this.armorCountScale.getValue()).floatValue(), 1.0F);
/* 167 */           GL11.glTranslatef((x + ((Integer)this.armorCountOffsetX.getValue()).intValue()) * (1.0F - ((Float)this.armorCountScale.getValue()).floatValue()) * -1.0F, (y + ((Integer)this.armorCountOffsetY.getValue()).intValue()) * (1.0F - ((Float)this.armorCountScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */         } 
/*     */         
/* 170 */         if (((Boolean)this.DMGPercentText.getValue()).booleanValue()) {
/* 171 */           GL11.glTranslatef((x + ((Integer)this.DMGPercentOffsetX.getValue()).intValue()) * (1.0F - ((Float)this.DMGPercentScale.getValue()).floatValue()), (y + ((Integer)this.DMGPercentOffsetY.getValue()).intValue()) * (1.0F - ((Float)this.DMGPercentScale.getValue()).floatValue()), 0.0F);
/* 172 */           GL11.glScalef(((Float)this.DMGPercentScale.getValue()).floatValue(), ((Float)this.DMGPercentScale.getValue()).floatValue(), 1.0F);
/*     */           
/* 174 */           FontManager.drawHUD((int)(getItemDMG(armorItem) * 100.0F) + "", (x + ((Integer)this.DMGPercentOffsetX.getValue()).intValue()), (y + ((Integer)this.DMGPercentOffsetY.getValue()).intValue()), ((Boolean)this.DMGPercentShadow.getValue()).booleanValue(), ((Boolean)this.damageColorShift.getValue()).booleanValue() ? ColorUtil.colorShift(getDMGColor2(armorItem.func_77973_b()), getDMGColor(armorItem.func_77973_b()), getItemDMG(armorItem) * 300.0F).getRGB() : getDMGColor(armorItem.func_77973_b()).getRGB());
/*     */           
/* 176 */           GL11.glScalef(1.0F / ((Float)this.DMGPercentScale.getValue()).floatValue(), 1.0F / ((Float)this.DMGPercentScale.getValue()).floatValue(), 1.0F);
/* 177 */           GL11.glTranslatef((x + ((Integer)this.DMGPercentOffsetX.getValue()).intValue()) * (1.0F - ((Float)this.DMGPercentScale.getValue()).floatValue()) * -1.0F, (y + ((Integer)this.DMGPercentOffsetY.getValue()).intValue()) * (1.0F - ((Float)this.DMGPercentScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */         } 
/*     */       } 
/*     */       
/* 181 */       if (((Boolean)this.horizontal.getValue()).booleanValue()) {
/* 182 */         x -= ((Integer)this.separationDist.getValue()).intValue();
/*     */         continue;
/*     */       } 
/* 185 */       y -= ((Integer)this.separationDist.getValue()).intValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderArmorRect(int x, int y, Item item) {
/* 191 */     int color = getSimplifiedArmorColor(item, (Color)this.rectColorLeather.getValue(), (Color)this.rectColorChainmail.getValue(), (Color)this.rectColorGold.getValue(), (Color)this.rectColorIron.getValue(), (Color)this.rectColorDiamond.getValue(), (Color)this.rectColorElytra.getDefaultValue()).getRGB();
/*     */     
/* 193 */     if (((Boolean)this.borderedRect.getValue()).booleanValue()) {
/* 194 */       if (((Boolean)this.roundedRect.getValue()).booleanValue()) {
/* 195 */         RenderUtils2D.drawCustomRoundedRectOutline(x, y, (x + ((Integer)this.rectsWidth.getValue()).intValue()), (y + ((Integer)this.rectHeight.getValue()).intValue()), ((Float)this.roundedRectRadius.getValue()).floatValue(), ((Float)this.borderedRectWidth.getValue()).floatValue(), ((Boolean)this.roundedRectTopRight.getValue()).booleanValue(), ((Boolean)this.roundedRectTopLeft.getValue()).booleanValue(), ((Boolean)this.roundedRectDownRight.getValue()).booleanValue(), ((Boolean)this.roundedRectDownLeft.getValue()).booleanValue(), false, false, color);
/* 196 */         RenderUtils2D.drawRoundedRect(x + ((Float)this.borderedRectOffset.getValue()).floatValue() / 2.0F, y + ((Float)this.borderedRectOffset.getValue()).floatValue() / 2.0F, ((Float)this.roundedRectRadius.getValue()).floatValue(), (x + ((Integer)this.rectsWidth.getValue()).intValue()) - ((Float)this.borderedRectOffset.getValue()).floatValue() / 2.0F, (y + ((Integer)this.rectHeight.getValue()).intValue()) - ((Float)this.borderedRectOffset.getValue()).floatValue() / 2.0F, false, ((Boolean)this.roundedRectTopRight.getValue()).booleanValue(), ((Boolean)this.roundedRectTopLeft.getValue()).booleanValue(), ((Boolean)this.roundedRectDownRight.getValue()).booleanValue(), ((Boolean)this.roundedRectDownLeft.getValue()).booleanValue(), color);
/*     */       } else {
/*     */         
/* 199 */         RenderUtils2D.drawRectOutline(x, y, (x + ((Integer)this.rectsWidth.getValue()).intValue()), (y + ((Integer)this.rectHeight.getValue()).intValue()), ((Float)this.borderedRectWidth.getValue()).floatValue(), color, false, false);
/* 200 */         RenderUtils2D.drawRect(x + ((Float)this.borderedRectOffset.getValue()).floatValue() / 2.0F, y + ((Float)this.borderedRectOffset.getValue()).floatValue() / 2.0F, (x + ((Integer)this.rectsWidth.getValue()).intValue()) - ((Float)this.borderedRectOffset.getValue()).floatValue() / 2.0F, (y + ((Integer)this.rectHeight.getValue()).intValue()) - ((Float)this.borderedRectOffset.getValue()).floatValue() / 2.0F, color);
/*     */       }
/*     */     
/*     */     }
/* 204 */     else if (((Boolean)this.roundedRect.getValue()).booleanValue()) {
/* 205 */       RenderUtils2D.drawRoundedRect(x, y, ((Float)this.roundedRectRadius.getValue()).floatValue(), (x + ((Integer)this.rectsWidth.getValue()).intValue()), (y + ((Integer)this.rectHeight.getValue()).intValue()), false, ((Boolean)this.roundedRectTopRight.getValue()).booleanValue(), ((Boolean)this.roundedRectTopLeft.getValue()).booleanValue(), ((Boolean)this.roundedRectDownRight.getValue()).booleanValue(), ((Boolean)this.roundedRectDownLeft.getValue()).booleanValue(), color);
/*     */     } else {
/*     */       
/* 208 */       RenderUtils2D.drawRect(x, y, (x + ((Integer)this.rectsWidth.getValue()).intValue()), (y + ((Integer)this.rectHeight.getValue()).intValue()), color);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderDMGBar(int x, int y, ItemStack itemStack) {
/* 214 */     Color dmgBarColor = getDMGColor(itemStack.func_77973_b());
/*     */     
/* 216 */     if (((Boolean)this.damageColorShift.getValue()).booleanValue()) {
/* 217 */       dmgBarColor = ColorUtil.colorShift(getDMGColor2(itemStack.func_77973_b()), getDMGColor(itemStack.func_77973_b()), getItemDMG(itemStack) * 300.0F);
/*     */     }
/*     */     
/* 220 */     if (((Boolean)this.borderedRectDMGBar.getValue()).booleanValue()) {
/* 221 */       if (((Boolean)this.roundedRectDMGBar.getValue()).booleanValue()) {
/* 222 */         RenderUtils2D.drawCustomRoundedRectOutline(x, y, (x + ((Integer)this.rectsWidth.getValue()).intValue()), y + ((Float)this.DMGBarHeight.getValue()).floatValue(), ((Float)this.roundedRectRadiusDMGBar.getValue()).floatValue(), ((Float)this.borderedRectWidthDMGBar.getValue()).floatValue(), ((Boolean)this.roundedRectTopRight.getValue()).booleanValue(), ((Boolean)this.roundedRectTopLeft.getValue()).booleanValue(), ((Boolean)this.roundedRectDownRight.getValue()).booleanValue(), ((Boolean)this.roundedRectDownLeft.getValue()).booleanValue(), false, false, dmgBarColor.getRGB());
/* 223 */         RenderUtils2D.drawRoundedRect(x + ((Float)this.borderedRectOffsetDMGBar.getValue()).floatValue() / 2.0F, y + ((Float)this.borderedRectOffsetDMGBar.getValue()).floatValue() / 2.0F, ((Float)this.roundedRectRadiusDMGBar.getValue()).floatValue(), x + getItemDMG(itemStack) * ((Integer)this.rectsWidth.getValue()).intValue() - ((Float)this.borderedRectOffsetDMGBar.getValue()).floatValue() / 2.0F, y + ((Float)this.DMGBarHeight.getValue()).floatValue() - ((Float)this.borderedRectOffsetDMGBar.getValue()).floatValue() / 2.0F, false, ((Boolean)this.roundedRectTopRight.getValue()).booleanValue(), ((Boolean)this.roundedRectTopLeft.getValue()).booleanValue(), ((Boolean)this.roundedRectDownRight.getValue()).booleanValue(), ((Boolean)this.roundedRectDownLeft.getValue()).booleanValue(), dmgBarColor.getRGB());
/*     */       } else {
/*     */         
/* 226 */         RenderUtils2D.drawRectOutline(x, y, (x + ((Integer)this.rectsWidth.getValue()).intValue()), y + ((Float)this.DMGBarHeight.getValue()).floatValue(), ((Float)this.borderedRectWidthDMGBar.getValue()).floatValue(), dmgBarColor.getRGB(), false, false);
/* 227 */         RenderUtils2D.drawRect(x + ((Float)this.borderedRectOffsetDMGBar.getValue()).floatValue() / 2.0F, y + ((Float)this.borderedRectOffsetDMGBar.getValue()).floatValue() / 2.0F, x + getItemDMG(itemStack) * ((Integer)this.rectsWidth.getValue()).intValue() - ((Float)this.borderedRectOffsetDMGBar.getValue()).floatValue() / 2.0F, y + ((Float)this.DMGBarHeight.getValue()).floatValue() - ((Float)this.borderedRectOffsetDMGBar.getValue()).floatValue() / 2.0F, dmgBarColor.getRGB());
/*     */       }
/*     */     
/*     */     }
/* 231 */     else if (((Boolean)this.roundedRectDMGBar.getValue()).booleanValue()) {
/* 232 */       RenderUtils2D.drawRoundedRect(x, y, ((Float)this.roundedRectRadiusDMGBar.getValue()).floatValue(), x + getItemDMG(itemStack) * ((Integer)this.rectsWidth.getValue()).intValue(), y + ((Float)this.DMGBarHeight.getValue()).floatValue(), false, ((Boolean)this.roundedRectTopRight.getValue()).booleanValue(), ((Boolean)this.roundedRectTopLeft.getValue()).booleanValue(), ((Boolean)this.roundedRectDownRight.getValue()).booleanValue(), ((Boolean)this.roundedRectDownLeft.getValue()).booleanValue(), dmgBarColor.getRGB());
/*     */     } else {
/*     */       
/* 235 */       RenderUtils2D.drawRect(x, y, x + getItemDMG(itemStack) * ((Integer)this.rectsWidth.getValue()).intValue(), y + ((Float)this.DMGBarHeight.getValue()).floatValue(), dmgBarColor.getRGB());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getItemDMG(ItemStack itemStack) {
/* 241 */     return (itemStack.func_77958_k() - itemStack.func_77952_i()) / itemStack.func_77958_k();
/*     */   }
/*     */   
/*     */   public Color getSimplifiedArmorColor(Item item, Color leatherColor, Color chainMailColor, Color goldColor, Color ironColor, Color diamondColor, Color elytraColor) {
/* 245 */     if (item == Items.field_151021_T || item == Items.field_151026_S || item == Items.field_151027_R || item == Items.field_151024_Q) {
/* 246 */       return leatherColor.getColorColor();
/*     */     }
/*     */     
/* 249 */     if (item == Items.field_151029_X || item == Items.field_151022_W || item == Items.field_151023_V || item == Items.field_151020_U) {
/* 250 */       return chainMailColor.getColorColor();
/*     */     }
/*     */     
/* 253 */     if (item == Items.field_151151_aj || item == Items.field_151149_ai || item == Items.field_151171_ah || item == Items.field_151169_ag) {
/* 254 */       return goldColor.getColorColor();
/*     */     }
/*     */     
/* 257 */     if (item == Items.field_151167_ab || item == Items.field_151165_aa || item == Items.field_151030_Z || item == Items.field_151028_Y) {
/* 258 */       return ironColor.getColorColor();
/*     */     }
/*     */     
/* 261 */     if (item == Items.field_151175_af || item == Items.field_151173_ae || item == Items.field_151163_ad || item == Items.field_151161_ac) {
/* 262 */       return diamondColor.getColorColor();
/*     */     }
/*     */     
/* 265 */     if (item == Items.field_185160_cR) {
/* 266 */       return elytraColor.getColorColor();
/*     */     }
/*     */     
/* 269 */     return new Color(0, 0, 0, 255);
/*     */   }
/*     */   
/*     */   private Color getDMGColor(Item item) {
/* 273 */     if (item == Items.field_151021_T || item == Items.field_151026_S || item == Items.field_151027_R || item == Items.field_151024_Q) {
/* 274 */       return ((Color)this.DMGColorLeather.getValue()).getColorColor();
/*     */     }
/*     */     
/* 277 */     if (item == Items.field_151029_X || item == Items.field_151022_W || item == Items.field_151023_V || item == Items.field_151020_U) {
/* 278 */       return ((Color)this.DMGColorChainmail.getValue()).getColorColor();
/*     */     }
/*     */     
/* 281 */     if (item == Items.field_151151_aj || item == Items.field_151149_ai || item == Items.field_151171_ah || item == Items.field_151169_ag) {
/* 282 */       return ((Color)this.DMGColorGold.getValue()).getColorColor();
/*     */     }
/*     */     
/* 285 */     if (item == Items.field_151167_ab || item == Items.field_151165_aa || item == Items.field_151030_Z || item == Items.field_151028_Y) {
/* 286 */       return ((Color)this.DMGColorIron.getValue()).getColorColor();
/*     */     }
/*     */     
/* 289 */     if (item == Items.field_151175_af || item == Items.field_151173_ae || item == Items.field_151163_ad || item == Items.field_151161_ac) {
/* 290 */       return ((Color)this.DMGColorDiamond.getValue()).getColorColor();
/*     */     }
/*     */     
/* 293 */     if (item == Items.field_185160_cR) {
/* 294 */       return ((Color)this.DMGColorElytra.getValue()).getColorColor();
/*     */     }
/*     */     
/* 297 */     return new Color(0);
/*     */   }
/*     */   
/*     */   private Color getDMGColor2(Item item) {
/* 301 */     if (item == Items.field_151021_T || item == Items.field_151026_S || item == Items.field_151027_R || item == Items.field_151024_Q) {
/* 302 */       return ((Color)this.DMGColorLeather2.getValue()).getColorColor();
/*     */     }
/*     */     
/* 305 */     if (item == Items.field_151029_X || item == Items.field_151022_W || item == Items.field_151023_V || item == Items.field_151020_U) {
/* 306 */       return ((Color)this.DMGColorChainmail2.getValue()).getColorColor();
/*     */     }
/*     */     
/* 309 */     if (item == Items.field_151151_aj || item == Items.field_151149_ai || item == Items.field_151171_ah || item == Items.field_151169_ag) {
/* 310 */       return ((Color)this.DMGColorGold2.getValue()).getColorColor();
/*     */     }
/*     */     
/* 313 */     if (item == Items.field_151167_ab || item == Items.field_151165_aa || item == Items.field_151030_Z || item == Items.field_151028_Y) {
/* 314 */       return ((Color)this.DMGColorIron2.getValue()).getColorColor();
/*     */     }
/*     */     
/* 317 */     if (item == Items.field_151175_af || item == Items.field_151173_ae || item == Items.field_151163_ad || item == Items.field_151161_ac) {
/* 318 */       return ((Color)this.DMGColorDiamond2.getValue()).getColorColor();
/*     */     }
/*     */     
/* 321 */     if (item == Items.field_185160_cR) {
/* 322 */       return ((Color)this.DMGColorElytra2.getValue()).getColorColor();
/*     */     }
/*     */     
/* 325 */     return new Color(0);
/*     */   }
/*     */   
/*     */   enum Page {
/* 329 */     ArmorRender,
/* 330 */     Text,
/* 331 */     Shadow,
/* 332 */     DMGBar,
/* 333 */     Colors;
/*     */   }
/*     */   
/*     */   public enum RenderMode {
/* 337 */     Image,
/* 338 */     Simplified,
/* 339 */     None;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\hud\huds\ArmorDisplay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */