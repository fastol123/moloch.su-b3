/*     */ package me.thediamondsword5.moloch.gui.components;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.HashMap;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.core.common.Visibility;
/*     */ import me.thediamondsword5.moloch.core.setting.settings.VisibilitySetting;
/*     */ import me.thediamondsword5.moloch.module.modules.client.CustomFont;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.spartanb312.base.client.FontManager;
/*     */ import net.spartanb312.base.gui.Component;
/*     */ import net.spartanb312.base.gui.Panel;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*     */ import net.spartanb312.base.utils.MathUtilFuckYou;
/*     */ import net.spartanb312.base.utils.SoundUtil;
/*     */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class VisibilityButton
/*     */   extends Component
/*     */ {
/*     */   VisibilitySetting setting;
/*     */   String moduleName;
/*     */   
/*     */   public VisibilityButton(VisibilitySetting setting, int width, int height, Panel father, Module module) {
/*  28 */     this.width = width;
/*  29 */     this.height = height;
/*  30 */     this.father = father;
/*  31 */     this.setting = setting;
/*  32 */     this.moduleName = module.name;
/*     */   }
/*     */   
/*  35 */   public static HashMap<String, Integer> storedVisibilityBooleanProgressLoops = new HashMap<>();
/*  36 */   public static HashMap<String, Integer> storedVisibilityBooleanFullRectProgress = new HashMap<>();
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY, float translateDelta, float partialTicks) {
/*  40 */     GlStateManager.func_179118_c();
/*     */     
/*  42 */     Color visibleVisibleTextColor = new Color(((Color)ClickGUI.instance.visibilityVisibleTextColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.visibilityVisibleTextColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.visibilityVisibleTextColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.visibilityVisibleTextColor.getValue()).getAlpha());
/*     */     
/*  44 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Minecraft) {
/*  45 */       GL11.glEnable(3553);
/*  46 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/*  47 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/*  49 */       this.mc.field_71466_p.func_175065_a(this.setting.getName(), (this.x + 5), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F), visibleVisibleTextColor.getRGB(), ((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue());
/*     */       
/*  51 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*  52 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*  53 */       GL11.glDisable(3553);
/*     */     
/*     */     }
/*  56 */     else if (((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue()) {
/*  57 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/*  58 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/*  60 */       FontManager.drawShadow(this.setting.getName(), (this.x + 5), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), visibleVisibleTextColor.getRGB());
/*     */       
/*  62 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*  63 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */     }
/*     */     else {
/*     */       
/*  67 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/*  68 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/*  70 */       FontManager.draw(this.setting.getName(), (this.x + 5), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), visibleVisibleTextColor.getRGB());
/*     */       
/*  72 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*  73 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */     } 
/*     */ 
/*     */     
/*  77 */     if (ClickGUI.instance.visibilitySettingMode.getValue() == ClickGUI.VisibilitySettingMode.Boolean) {
/*     */       
/*  79 */       int booleanColor = ((Color)ClickGUI.instance.visibilityBooleanColor.getValue()).getColor();
/*     */ 
/*     */ 
/*     */       
/*  83 */       storedVisibilityBooleanProgressLoops.putIfAbsent(this.setting.getName() + this.moduleName, Integer.valueOf(0));
/*  84 */       if (((Visibility)this.setting.getValue()).getVisible()) {
/*  85 */         int animateLoops = ((Integer)storedVisibilityBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue();
/*  86 */         animateLoops = (int)(animateLoops + ((Float)ClickGUI.instance.visibilityBooleanSmoothFactor.getValue()).floatValue() * 10.0F);
/*  87 */         if (animateLoops >= 300) {
/*  88 */           animateLoops = 300;
/*     */         }
/*  90 */         if (animateLoops <= 0) {
/*  91 */           animateLoops = 0;
/*     */         }
/*  93 */         storedVisibilityBooleanProgressLoops.put(this.setting.getName() + this.moduleName, Integer.valueOf(animateLoops));
/*     */       } else {
/*     */         
/*  96 */         int animateLoops = ((Integer)storedVisibilityBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue();
/*  97 */         animateLoops = (int)(animateLoops - ((Float)ClickGUI.instance.visibilityBooleanSmoothFactor.getValue()).floatValue() * 10.0F);
/*  98 */         if (animateLoops >= 300) {
/*  99 */           animateLoops = 300;
/*     */         }
/* 101 */         if (animateLoops <= 0) {
/* 102 */           animateLoops = 0;
/*     */         }
/* 104 */         storedVisibilityBooleanProgressLoops.put(this.setting.getName() + this.moduleName, Integer.valueOf(animateLoops));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 110 */       if (((Boolean)ClickGUI.instance.visibilityBooleanFullRect.getValue()).booleanValue()) {
/* 111 */         Color booleanFullRectColor = new Color(((Color)ClickGUI.instance.visibilityBooleanFullRectColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.visibilityBooleanFullRectColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.visibilityBooleanFullRectColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.visibilityBooleanFullRectColor.getValue()).getAlpha());
/*     */         
/* 113 */         if (((Boolean)ClickGUI.instance.visibilityBooleanFullRectSmooth.getValue()).booleanValue()) {
/* 114 */           storedVisibilityBooleanFullRectProgress.putIfAbsent(this.setting.getName() + this.moduleName, Integer.valueOf(0));
/* 115 */           int animateLoops = ((Integer)storedVisibilityBooleanFullRectProgress.get(this.setting.getName() + this.moduleName)).intValue();
/* 116 */           if (((Visibility)this.setting.getValue()).getVisible()) {
/* 117 */             animateLoops = (int)(animateLoops + ((Float)ClickGUI.instance.visibilityBooleanFullRectSmoothFactor.getValue()).floatValue() * 10.0F);
/*     */           } else {
/*     */             
/* 120 */             animateLoops = (int)(animateLoops - ((Float)ClickGUI.instance.visibilityBooleanFullRectSmoothFactor.getValue()).floatValue() * 10.0F);
/*     */           } 
/* 122 */           if (animateLoops >= 300) {
/* 123 */             animateLoops = 300;
/*     */           }
/* 125 */           if (animateLoops <= 0) {
/* 126 */             animateLoops = 0;
/*     */           }
/* 128 */           storedVisibilityBooleanFullRectProgress.put(this.setting.getName() + this.moduleName, Integer.valueOf(animateLoops));
/*     */           
/* 130 */           if (((Boolean)ClickGUI.instance.visibilityBooleanFullRectSmoothAlpha.getValue()).booleanValue()) {
/* 131 */             booleanFullRectColor = new Color(booleanFullRectColor.getRed(), booleanFullRectColor.getGreen(), booleanFullRectColor.getBlue(), (int)(((Color)ClickGUI.instance.visibilityBooleanFullRectColor.getValue()).getAlpha() / 300.0F * ((Integer)storedVisibilityBooleanFullRectProgress.get(this.setting.getName() + this.moduleName)).intValue()));
/*     */           }
/*     */         } 
/*     */         
/* 135 */         if (((Visibility)this.setting.getValue()).getVisible() && !((Boolean)ClickGUI.instance.visibilityBooleanFullRectSmooth.getValue()).booleanValue()) {
/* 136 */           RenderUtils2D.drawRect((this.x + 3), (this.y + 1), (this.x + this.width - 1), (this.y + this.height), booleanFullRectColor.getRGB());
/*     */         }
/* 138 */         else if (((Boolean)ClickGUI.instance.visibilityBooleanFullRectSmooth.getValue()).booleanValue() && (ClickGUI.instance.visibilityBooleanFullRectScaleType.getValue() != ClickGUI.BooleanFullRectScaleType.None || ((Boolean)ClickGUI.instance.visibilityBooleanFullRectSmoothAlpha.getValue()).booleanValue())) {
/* 139 */           RenderUtils2D.drawRect((this.x + 3) + ((ClickGUI.instance.visibilityBooleanFullRectScaleType.getValue() == ClickGUI.BooleanFullRectScaleType.Left) ? ((300.0F - ((Integer)storedVisibilityBooleanFullRectProgress.get(this.setting.getName() + this.moduleName)).intValue()) * (this.width - 4) / 300.0F) : ((ClickGUI.instance.visibilityBooleanFullRectScaleType.getValue() == ClickGUI.BooleanFullRectScaleType.All) ? ((300.0F - ((Integer)storedVisibilityBooleanFullRectProgress.get(this.setting.getName() + this.moduleName)).intValue()) * (this.width - 4) / 2.0F / 300.0F) : 0.0F)), (this.y + 1) + ((ClickGUI.instance.visibilityBooleanFullRectScaleType.getValue() == ClickGUI.BooleanFullRectScaleType.All) ? ((300.0F - ((Integer)storedVisibilityBooleanFullRectProgress.get(this.setting.getName() + this.moduleName)).intValue()) * this.height / 2.0F / 300.0F) : 0.0F), (this.x + this.width - 1) - ((ClickGUI.instance.visibilityBooleanFullRectScaleType.getValue() == ClickGUI.BooleanFullRectScaleType.Right) ? ((300.0F - ((Integer)storedVisibilityBooleanFullRectProgress.get(this.setting.getName() + this.moduleName)).intValue()) * (this.width - 4) / 300.0F) : ((ClickGUI.instance.visibilityBooleanFullRectScaleType.getValue() == ClickGUI.BooleanFullRectScaleType.All) ? ((300.0F - ((Integer)storedVisibilityBooleanFullRectProgress.get(this.setting.getName() + this.moduleName)).intValue()) * (this.width - 2) / 2.0F / 300.0F) : 0.0F)), (this.y + this.height) - ((ClickGUI.instance.visibilityBooleanFullRectScaleType.getValue() == ClickGUI.BooleanFullRectScaleType.All) ? ((300.0F - ((Integer)storedVisibilityBooleanFullRectProgress.get(this.setting.getName() + this.moduleName)).intValue()) * this.height / 2.0F / 300.0F) : 0.0F), booleanFullRectColor.getRGB());
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 144 */       if (ClickGUI.instance.visibilityBooleanSwitchType.getValue() != ClickGUI.BooleanSwitchTypes.None) {
/* 145 */         Color booleanSecondaryColor; if (((Boolean)ClickGUI.instance.visibilityBooleanSwitchColorChange.getValue()).booleanValue()) {
/*     */           
/* 147 */           Color booleanEnabledColor = new Color(((Color)ClickGUI.instance.visibilityBooleanEnabledColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.visibilityBooleanEnabledColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.visibilityBooleanEnabledColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.visibilityBooleanEnabledColor.getValue()).getAlpha());
/* 148 */           Color booleanDisabledColor = new Color(((Color)ClickGUI.instance.visibilityBooleanDisabledColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.visibilityBooleanDisabledColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.visibilityBooleanDisabledColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.visibilityBooleanDisabledColor.getValue()).getAlpha());
/*     */           
/* 150 */           if (((Boolean)ClickGUI.instance.visibilityBooleanSmooth.getValue()).booleanValue()) {
/* 151 */             int red = (int)MathUtilFuckYou.linearInterp(booleanDisabledColor.getRed(), booleanEnabledColor.getRed(), ((Integer)storedVisibilityBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue());
/* 152 */             int green = (int)MathUtilFuckYou.linearInterp(booleanDisabledColor.getGreen(), booleanEnabledColor.getGreen(), ((Integer)storedVisibilityBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue());
/* 153 */             int blue = (int)MathUtilFuckYou.linearInterp(booleanDisabledColor.getBlue(), booleanEnabledColor.getBlue(), ((Integer)storedVisibilityBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue());
/* 154 */             int alpha = (int)MathUtilFuckYou.linearInterp(((Color)ClickGUI.instance.visibilityBooleanDisabledColor.getValue()).getAlpha(), ((Color)ClickGUI.instance.visibilityBooleanEnabledColor.getValue()).getAlpha(), ((Integer)storedVisibilityBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue());
/* 155 */             booleanColor = (new Color(red, green, blue, alpha)).getRGB();
/*     */           
/*     */           }
/* 158 */           else if (((Visibility)this.setting.getValue()).getVisible()) {
/* 159 */             booleanColor = booleanEnabledColor.getRGB();
/*     */           } else {
/*     */             
/* 162 */             booleanColor = booleanDisabledColor.getRGB();
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 168 */         float centerX = this.x + this.width / 5.0F * 4.0F + 18.0F * ((Float)ClickGUI.instance.visibilityBooleanSwitchScale.getValue()).floatValue() + ((Float)ClickGUI.instance.visibilityBooleanSwitchX.getValue()).floatValue() - 18.0F * ((Float)ClickGUI.instance.visibilityBooleanSwitchScale.getValue()).floatValue() - this.height / 4.0F + (((Boolean)ClickGUI.instance.visibilityBooleanSmooth.getValue()).booleanValue() ? (((Integer)storedVisibilityBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue() * (18.0F * ((Float)ClickGUI.instance.visibilityBooleanSwitchScale.getValue()).floatValue() - this.height / 2.0F) / 300.0F) : (((Visibility)this.setting.getValue()).getVisible() ? (18.0F * ((Float)ClickGUI.instance.visibilityBooleanSwitchScale.getValue()).floatValue() - this.height / 2.0F) : 0.0F));
/* 169 */         float centerY = this.y + this.height / 2.0F;
/* 170 */         if (ClickGUI.instance.visibilityBooleanSwitchType.getValue() == ClickGUI.BooleanSwitchTypes.SliderRound) {
/* 171 */           RenderUtils2D.drawCircle(centerX, centerY, this.height / 4.0F * ((Float)ClickGUI.instance.visibilityBooleanDotFillAmount.getValue()).floatValue(), booleanColor);
/* 172 */           RenderUtils2D.drawCustomRoundedRectOutline(this.x + this.width / 5.0F * 4.0F + ((Float)ClickGUI.instance.visibilityBooleanSwitchX.getValue()).floatValue(), this.y + this.height / 4.0F, this.x + this.width / 5.0F * 4.0F + 18.0F * ((Float)ClickGUI.instance.visibilityBooleanSwitchScale.getValue()).floatValue() + ((Float)ClickGUI.instance.visibilityBooleanSwitchX.getValue()).floatValue(), this.y + this.height / 4.0F * 3.0F, 1.0F, ((Float)ClickGUI.instance.visibilityBooleanSwitchLineWidth.getValue()).floatValue(), true, true, true, true, false, false, booleanColor);
/*     */         }
/* 174 */         else if (ClickGUI.instance.visibilityBooleanSwitchType.getValue() == ClickGUI.BooleanSwitchTypes.SliderNonRound) {
/* 175 */           RenderUtils2D.drawRect(centerX - this.height / 4.0F * ((Float)ClickGUI.instance.visibilityBooleanDotFillAmount.getValue()).floatValue(), centerY - this.height / 4.0F * ((Float)ClickGUI.instance.visibilityBooleanDotFillAmount.getValue()).floatValue(), centerX + this.height / 4.0F * ((Float)ClickGUI.instance.visibilityBooleanDotFillAmount.getValue()).floatValue(), centerY + this.height / 4.0F * ((Float)ClickGUI.instance.visibilityBooleanDotFillAmount.getValue()).floatValue(), booleanColor);
/* 176 */           RenderUtils2D.drawRectOutline(this.x + this.width / 5.0F * 4.0F + ((Float)ClickGUI.instance.visibilityBooleanSwitchX.getValue()).floatValue(), this.y + this.height / 4.0F, this.x + this.width / 5.0F * 4.0F + 18.0F * ((Float)ClickGUI.instance.visibilityBooleanSwitchScale.getValue()).floatValue() + ((Float)ClickGUI.instance.visibilityBooleanSwitchX.getValue()).floatValue(), this.y + this.height / 4.0F * 3.0F, ((Float)ClickGUI.instance.visibilityBooleanSwitchLineWidth.getValue()).floatValue(), booleanColor, false, false);
/*     */         } 
/*     */ 
/*     */         
/* 180 */         float centerXDotMode = this.x + this.width / 5.0F * 4.0F + 7.0F + ((Float)ClickGUI.instance.visibilityBooleanSwitchX.getValue()).floatValue();
/* 181 */         float centerYDotMode = this.y + this.height / 2.0F;
/* 182 */         if ((ClickGUI.instance.visibilityBooleanDotMode.getValue() == ClickGUI.BooleanDotMode.Alpha || ClickGUI.instance.visibilityBooleanDotMode.getValue() == ClickGUI.BooleanDotMode.Both) && (ClickGUI.instance.visibilityBooleanSwitchType.getValue() == ClickGUI.BooleanSwitchTypes.Circle || ClickGUI.instance.visibilityBooleanSwitchType.getValue() == ClickGUI.BooleanSwitchTypes.Square)) {
/* 183 */           booleanSecondaryColor = new Color((new Color(booleanColor)).getRed(), (new Color(booleanColor)).getGreen(), (new Color(booleanColor)).getBlue(), (int)((new Color(booleanColor)).getAlpha() / 300.0F * ((Integer)storedVisibilityBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue()));
/*     */         } else {
/*     */           
/* 186 */           booleanSecondaryColor = new Color(booleanColor);
/*     */         } 
/* 188 */         if (ClickGUI.instance.visibilityBooleanSwitchType.getValue() == ClickGUI.BooleanSwitchTypes.Circle) {
/* 189 */           RenderUtils2D.drawCircle(centerXDotMode, centerYDotMode, (ClickGUI.instance.visibilityBooleanDotMode.getValue() == ClickGUI.BooleanDotMode.Scale || ClickGUI.instance.visibilityBooleanDotMode.getValue() == ClickGUI.BooleanDotMode.Both) ? (((Boolean)ClickGUI.instance.visibilityBooleanSmooth.getValue()).booleanValue() ? (((Integer)storedVisibilityBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue() * this.height / 4.0F * ((Float)ClickGUI.instance.visibilityBooleanDotFillAmount.getValue()).floatValue() / 300.0F) : (((Visibility)this.setting.getValue()).getVisible() ? (this.height / 4.0F * ((Float)ClickGUI.instance.visibilityBooleanDotFillAmount.getValue()).floatValue()) : 0.0F)) : (this.height / 4.0F * ((Float)ClickGUI.instance.visibilityBooleanDotFillAmount.getValue()).floatValue()), booleanSecondaryColor.getRGB());
/* 190 */           RenderUtils2D.drawCircleOutline(centerXDotMode, centerYDotMode, this.height / 4.0F, ((Float)ClickGUI.instance.visibilityBooleanSwitchLineWidth.getValue()).floatValue(), booleanColor);
/*     */         }
/* 192 */         else if (ClickGUI.instance.visibilityBooleanSwitchType.getValue() == ClickGUI.BooleanSwitchTypes.Square) {
/* 193 */           float squareDotScale = (ClickGUI.instance.visibilityBooleanDotMode.getValue() == ClickGUI.BooleanDotMode.Scale || ClickGUI.instance.visibilityBooleanDotMode.getValue() == ClickGUI.BooleanDotMode.Both) ? (((Boolean)ClickGUI.instance.visibilityBooleanSmooth.getValue()).booleanValue() ? (((Integer)storedVisibilityBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue() * this.height / 4.0F * ((Float)ClickGUI.instance.visibilityBooleanDotFillAmount.getValue()).floatValue() / 300.0F) : (((Visibility)this.setting.getValue()).getVisible() ? (this.height / 4.0F * ((Float)ClickGUI.instance.visibilityBooleanDotFillAmount.getValue()).floatValue()) : 0.0F)) : (this.height / 4.0F * ((Float)ClickGUI.instance.visibilityBooleanDotFillAmount.getValue()).floatValue());
/* 194 */           RenderUtils2D.drawRect(centerXDotMode - squareDotScale, centerYDotMode - squareDotScale, centerXDotMode + squareDotScale, centerYDotMode + squareDotScale, booleanSecondaryColor.getRGB());
/* 195 */           RenderUtils2D.drawRectOutline(centerXDotMode - this.height / 4.0F, centerYDotMode - this.height / 4.0F, centerXDotMode + this.height / 4.0F, centerYDotMode + this.height / 4.0F, ((Float)ClickGUI.instance.visibilityBooleanSwitchLineWidth.getValue()).floatValue(), booleanColor, false, false);
/*     */         }
/*     */       
/*     */       } 
/* 199 */     } else if (ClickGUI.instance.visibilitySettingMode.getValue() == ClickGUI.VisibilitySettingMode.Text) {
/* 200 */       String theString; Color visibilityTextColor = new Color(((Color)ClickGUI.instance.visibilityTextColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.visibilityTextColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.visibilityTextColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.visibilityTextColor.getValue()).getAlpha());
/*     */       
/* 202 */       if (((Visibility)this.setting.getValue()).getVisible()) {
/* 203 */         theString = "True";
/*     */       } else {
/*     */         
/* 206 */         theString = "False";
/*     */       } 
/*     */       
/* 209 */       float currentTextWidth = this.font.getStringWidth(theString);
/* 210 */       if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Minecraft) {
/* 211 */         GL11.glTranslatef((this.x + this.width - 3 - this.font.getStringWidth(theString)) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) + (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 212 */         GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */         
/* 214 */         this.mc.field_71466_p.func_175065_a(theString, (this.x + this.width - 3 - this.font.getStringWidth(theString)), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F), visibilityTextColor.getRGB(), ((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue());
/*     */         
/* 216 */         GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 217 */         GL11.glTranslatef((this.x + this.width - 3 - this.font.getStringWidth(theString)) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F - (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */       
/*     */       }
/* 220 */       else if (((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue()) {
/*     */         
/* 222 */         GL11.glTranslatef((this.x + this.width - 3 - this.font.getStringWidth(theString)) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) + (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 223 */         GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */         
/* 225 */         FontManager.drawShadow(theString, (this.x + this.width - 3 - this.font.getStringWidth(theString)), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), visibilityTextColor.getRGB());
/*     */ 
/*     */         
/* 228 */         GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 229 */         GL11.glTranslatef((this.x + this.width - 3 - this.font.getStringWidth(theString)) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F - (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 234 */         GL11.glTranslated(((this.x + this.width - 3 - this.font.getStringWidth(theString)) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) + (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth), (((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue())), 0.0D);
/* 235 */         GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */         
/* 237 */         FontManager.draw(theString, this.x + this.width - 3 - this.font.getStringWidth(theString), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3, visibilityTextColor);
/*     */         
/* 239 */         GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 240 */         GL11.glTranslated(((this.x + this.width - 3 - this.font.getStringWidth(theString)) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F - (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth), (((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F), 0.0D);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 246 */       Color visibilityIconColor = new Color(((Color)ClickGUI.instance.visibilityIconColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.visibilityIconColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.visibilityIconColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.visibilityIconColor.getValue()).getAlpha());
/*     */       
/* 248 */       GL11.glTranslatef((this.x + this.width - 3 - FontManager.getVisibilityIconWidth()) * (1.0F - ((Float)ClickGUI.instance.visibilityIconScale.getValue()).floatValue()), (int)(this.y + this.height / 2.0F + ((Integer)ClickGUI.instance.visibilityIconYOffset.getValue()).intValue()) * (1.0F - ((Float)ClickGUI.instance.visibilityIconScale.getValue()).floatValue()), 0.0F);
/* 249 */       GL11.glScalef(((Float)ClickGUI.instance.visibilityIconScale.getValue()).floatValue(), ((Float)ClickGUI.instance.visibilityIconScale.getValue()).floatValue(), ((Float)ClickGUI.instance.visibilityIconScale.getValue()).floatValue());
/* 250 */       if (((Visibility)this.setting.getValue()).getVisible()) {
/* 251 */         FontManager.drawVisibilityIconOn(this.x + this.width - 3 - FontManager.getVisibilityIconWidth(), (int)(this.y + this.height / 2.0F + ((Integer)ClickGUI.instance.visibilityIconYOffset.getValue()).intValue()), visibilityIconColor.getRGB());
/*     */       } else {
/*     */         
/* 254 */         FontManager.drawVisibilityIconOff(this.x + this.width - 3 - FontManager.getVisibilityIconWidth(), (int)(this.y + this.height / 2.0F + ((Integer)ClickGUI.instance.visibilityIconYOffset.getValue()).intValue()), visibilityIconColor.getRGB());
/*     */       } 
/* 256 */       GL11.glScalef(1.0F / ((Float)ClickGUI.instance.visibilityIconScale.getValue()).floatValue(), 1.0F / ((Float)ClickGUI.instance.visibilityIconScale.getValue()).floatValue(), 1.0F / ((Float)ClickGUI.instance.visibilityIconScale.getValue()).floatValue());
/* 257 */       GL11.glTranslatef((this.x + this.width - 3 - FontManager.getVisibilityIconWidth()) * (1.0F - ((Float)ClickGUI.instance.visibilityIconScale.getValue()).floatValue()) * -1.0F, (int)(this.y + this.height / 2.0F + ((Integer)ClickGUI.instance.visibilityIconYOffset.getValue()).intValue()) * (1.0F - ((Float)ClickGUI.instance.visibilityIconScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */       
/* 259 */       if (((Boolean)ClickGUI.instance.visibilityIconGlow.getValue()).booleanValue() && (!((Boolean)ClickGUI.instance.visibilityIconGlowToggle.getValue()).booleanValue() || ((Visibility)this.setting.getValue()).getVisible())) {
/* 260 */         Color visibilityIconGlowColor = new Color(((Color)ClickGUI.instance.visibilityIconGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.visibilityIconGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.visibilityIconGlowColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.visibilityIconGlowColor.getValue()).getAlpha());
/*     */         
/* 262 */         GlStateManager.func_179118_c();
/* 263 */         RenderUtils2D.drawCustomCircle((this.x + this.width - 3) - FontManager.getVisibilityIconWidth() / 2.0F + ((Float)ClickGUI.instance.visibilityIconGlowX.getValue()).floatValue(), (int)(this.y + this.height / 2.0F + ((Integer)ClickGUI.instance.visibilityIconYOffset.getValue()).intValue() + FontManager.getIconHeight() / 2.0F + ((Float)ClickGUI.instance.visibilityIconGlowY.getValue()).floatValue()), ((Float)ClickGUI.instance.visibilityIconGlowSize.getValue()).floatValue(), visibilityIconGlowColor.getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/* 264 */         GlStateManager.func_179118_c();
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     GlStateManager.func_179141_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public void bottomRender(int mouseX, int mouseY, boolean lastSetting, boolean firstSetting, float partialTicks) {
/* 273 */     GlStateManager.func_179118_c();
/* 274 */     drawSettingRects(lastSetting, false);
/*     */     
/* 276 */     drawExtendedGradient(lastSetting, false);
/* 277 */     drawExtendedLine(lastSetting);
/*     */     
/* 279 */     renderHoverRect(this.moduleName + this.setting.getName(), mouseX, mouseY, 2.0F, -1.0F, false);
/*     */     
/* 281 */     GlStateManager.func_179141_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHovered(int mouseX, int mouseY) {
/* 286 */     if (!anyExpanded) {
/* 287 */       return (mouseX >= Math.min(this.x, this.x + this.width) && mouseX <= Math.max(this.x, this.x + this.width) && mouseY >= Math.min(this.y, this.y + this.height) && mouseY <= Math.max(this.y, this.y + this.height));
/*     */     }
/*     */     
/* 290 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 297 */     if (!this.setting.isVisible() || !isHovered(mouseX, mouseY))
/* 298 */       return false; 
/* 299 */     if (mouseButton == 0) {
/* 300 */       this.setting.setOpposite(!((Visibility)this.setting.getValue()).getVisible());
/* 301 */       SoundUtil.playButtonClick();
/*     */     } 
/* 303 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 308 */     return this.setting.isVisible();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 313 */     return this.setting.getDescription();
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\gui\components\VisibilityButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */