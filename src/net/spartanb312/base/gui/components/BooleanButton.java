/*     */ package net.spartanb312.base.gui.components;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.HashMap;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.module.modules.client.CustomFont;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.spartanb312.base.client.FontManager;
/*     */ import net.spartanb312.base.core.setting.Setting;
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
/*     */ public class BooleanButton
/*     */   extends Component
/*     */ {
/*     */   Setting<Boolean> setting;
/*     */   boolean isColorPanel;
/*     */   String moduleName;
/*     */   
/*     */   public BooleanButton(Setting<Boolean> setting, int width, int height, Panel father, boolean isColorPanel, Module module) {
/*  28 */     this.moduleName = module.name;
/*  29 */     this.width = width;
/*  30 */     this.height = height;
/*  31 */     this.father = father;
/*  32 */     this.setting = setting;
/*  33 */     this.isColorPanel = isColorPanel;
/*     */   }
/*     */   
/*  36 */   public static HashMap<String, Integer> storedBooleanTextLoops = new HashMap<>();
/*  37 */   public static HashMap<String, Integer> storedBooleanProgressLoops = new HashMap<>();
/*     */   
/*  39 */   public static HashMap<String, Integer> storedBooleanFullRectProgress = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY, float translateDelta, float partialTicks) {
/*  44 */     GlStateManager.func_179118_c();
/*     */     
/*  46 */     Color booleanTextColor = new Color(255, 255, 255, 255);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  52 */     int booleanTextColorDisabledColorColorDropMenuAnimateAlpha = (int)(((Color)ClickGUI.instance.booleanTextColorDisabledColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader);
/*  53 */     if (booleanTextColorDisabledColorColorDropMenuAnimateAlpha <= 4) {
/*  54 */       booleanTextColorDisabledColorColorDropMenuAnimateAlpha = 4;
/*     */     }
/*  56 */     int defaultTextColorColorDropMenuAnimateAlpha = (int)(0.85F * colorMenuToggleThreader);
/*  57 */     if (defaultTextColorColorDropMenuAnimateAlpha <= 4) {
/*  58 */       defaultTextColorColorDropMenuAnimateAlpha = 4;
/*     */     }
/*  60 */     if (this.isColorPanel) {  } else {  }  Color settingsTextColor = new Color(((Color)ClickGUI.instance.booleanTextColorDisabledColor.getValue()).getColor());
/*  61 */     int booleanColor = this.isColorPanel ? (new Color(((Color)ClickGUI.instance.booleanColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.booleanColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.booleanColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.booleanColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.booleanColor.getValue()).getAlpha())).getRGB() : ((Color)ClickGUI.instance.booleanColor.getValue()).getColor();
/*  62 */     if (this.isColorPanel) {  } else {  }  Color defaultTextColor = new Color(255, 255, 255, 255);
/*     */     
/*  64 */     storedBooleanProgressLoops.putIfAbsent(this.setting.getName() + this.moduleName, Integer.valueOf(0));
/*  65 */     if (((Boolean)this.setting.getValue()).booleanValue()) {
/*  66 */       int animateLoops = ((Integer)storedBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue();
/*  67 */       animateLoops = (int)(animateLoops + ((Float)ClickGUI.instance.booleanSmoothFactor.getValue()).floatValue() * 10.0F);
/*  68 */       if (animateLoops >= 300) {
/*  69 */         animateLoops = 300;
/*     */       }
/*  71 */       if (animateLoops <= 0) {
/*  72 */         animateLoops = 0;
/*     */       }
/*  74 */       storedBooleanProgressLoops.put(this.setting.getName() + this.moduleName, Integer.valueOf(animateLoops));
/*     */     } else {
/*     */       
/*  77 */       int animateLoops = ((Integer)storedBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue();
/*  78 */       animateLoops = (int)(animateLoops - ((Float)ClickGUI.instance.booleanSmoothFactor.getValue()).floatValue() * 10.0F);
/*  79 */       if (animateLoops >= 300) {
/*  80 */         animateLoops = 300;
/*     */       }
/*  82 */       if (animateLoops <= 0) {
/*  83 */         animateLoops = 0;
/*     */       }
/*  85 */       storedBooleanProgressLoops.put(this.setting.getName() + this.moduleName, Integer.valueOf(animateLoops));
/*     */     } 
/*     */ 
/*     */     
/*  89 */     if (((Boolean)ClickGUI.instance.booleanTextColorChange.getValue()).booleanValue()) {
/*  90 */       int booleanTextColorEnabledColorColorDropMenuAnimateAlpha = (int)(((Color)ClickGUI.instance.booleanTextColorEnabledColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader);
/*  91 */       if (booleanTextColorEnabledColorColorDropMenuAnimateAlpha <= 4) {
/*  92 */         booleanTextColorEnabledColorColorDropMenuAnimateAlpha = 4;
/*     */       }
/*  94 */       if (this.isColorPanel) {  } else {  }  booleanTextColor = new Color(((Color)ClickGUI.instance.booleanTextColorEnabledColor.getValue()).getColor());
/*  95 */       if (((Boolean)ClickGUI.instance.booleanTextColorSmooth.getValue()).booleanValue()) {
/*  96 */         storedBooleanTextLoops.putIfAbsent(this.setting.getName() + this.moduleName, Integer.valueOf(0));
/*  97 */         if (((Boolean)this.setting.getValue()).booleanValue()) {
/*  98 */           int animateLoops = ((Integer)storedBooleanTextLoops.get(this.setting.getName() + this.moduleName)).intValue();
/*     */           
/* 100 */           int red = (int)MathUtilFuckYou.linearInterp(settingsTextColor.getRed(), booleanTextColor.getRed(), animateLoops);
/* 101 */           int green = (int)MathUtilFuckYou.linearInterp(settingsTextColor.getGreen(), booleanTextColor.getGreen(), animateLoops);
/* 102 */           int blue = (int)MathUtilFuckYou.linearInterp(settingsTextColor.getBlue(), booleanTextColor.getBlue(), animateLoops);
/* 103 */           int alpha = (int)MathUtilFuckYou.linearInterp(settingsTextColor.getAlpha(), booleanTextColor.getAlpha(), animateLoops);
/*     */           
/* 105 */           booleanTextColor = new Color(red, green, blue, alpha);
/* 106 */           animateLoops = (int)(animateLoops + ((Float)ClickGUI.instance.booleanTextColorSmoothFactor.getValue()).floatValue() * 10.0F);
/*     */           
/* 108 */           if (animateLoops >= 300) {
/* 109 */             animateLoops = 300;
/*     */           }
/* 111 */           if (animateLoops <= 0) {
/* 112 */             animateLoops = 0;
/*     */           }
/* 114 */           storedBooleanTextLoops.put(this.setting.getName() + this.moduleName, Integer.valueOf(animateLoops));
/*     */         } else {
/*     */           
/* 117 */           int animateLoops = ((Integer)storedBooleanTextLoops.get(this.setting.getName() + this.moduleName)).intValue();
/*     */           
/* 119 */           int red = (int)MathUtilFuckYou.linearInterp(settingsTextColor.getRed(), booleanTextColor.getRed(), animateLoops);
/* 120 */           int green = (int)MathUtilFuckYou.linearInterp(settingsTextColor.getGreen(), booleanTextColor.getGreen(), animateLoops);
/* 121 */           int blue = (int)MathUtilFuckYou.linearInterp(settingsTextColor.getBlue(), booleanTextColor.getBlue(), animateLoops);
/* 122 */           int alpha = (int)MathUtilFuckYou.linearInterp(settingsTextColor.getAlpha(), booleanTextColor.getAlpha(), animateLoops);
/*     */           
/* 124 */           booleanTextColor = new Color(red, green, blue, alpha);
/* 125 */           animateLoops = (int)(animateLoops - ((Float)ClickGUI.instance.booleanTextColorSmoothFactor.getValue()).floatValue() * 10.0F);
/*     */           
/* 127 */           if (animateLoops >= 300) {
/* 128 */             animateLoops = 300;
/*     */           }
/* 130 */           if (animateLoops <= 0) {
/* 131 */             animateLoops = 0;
/*     */           }
/* 133 */           storedBooleanTextLoops.put(this.setting.getName() + this.moduleName, Integer.valueOf(animateLoops));
/*     */         }
/*     */       
/*     */       }
/* 137 */       else if (!((Boolean)this.setting.getValue()).booleanValue()) {
/* 138 */         booleanTextColor = settingsTextColor;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 144 */     if (((Boolean)ClickGUI.instance.booleanFullRect.getValue()).booleanValue()) {
/* 145 */       if (this.isColorPanel) {  } else {  }  Color booleanFullRectColor = new Color(((Color)ClickGUI.instance.booleanFullRectColor.getValue()).getColor());
/*     */       
/* 147 */       if (((Boolean)ClickGUI.instance.booleanFullRectSmooth.getValue()).booleanValue()) {
/* 148 */         storedBooleanFullRectProgress.putIfAbsent(this.setting.getName() + this.moduleName, Integer.valueOf(0));
/* 149 */         int animateLoops = ((Integer)storedBooleanFullRectProgress.get(this.setting.getName() + this.moduleName)).intValue();
/* 150 */         if (((Boolean)this.setting.getValue()).booleanValue()) {
/* 151 */           animateLoops = (int)(animateLoops + ((Float)ClickGUI.instance.booleanFullRectSmoothFactor.getValue()).floatValue() * 10.0F);
/*     */         } else {
/*     */           
/* 154 */           animateLoops = (int)(animateLoops - ((Float)ClickGUI.instance.booleanFullRectSmoothFactor.getValue()).floatValue() * 10.0F);
/*     */         } 
/* 156 */         if (animateLoops >= 300) {
/* 157 */           animateLoops = 300;
/*     */         }
/* 159 */         if (animateLoops <= 0) {
/* 160 */           animateLoops = 0;
/*     */         }
/* 162 */         storedBooleanFullRectProgress.put(this.setting.getName() + this.moduleName, Integer.valueOf(animateLoops));
/*     */         
/* 164 */         if (((Boolean)ClickGUI.instance.booleanFullRectSmoothAlpha.getValue()).booleanValue()) {
/* 165 */           booleanFullRectColor = new Color(booleanFullRectColor.getRed(), booleanFullRectColor.getGreen(), booleanFullRectColor.getBlue(), (int)(booleanFullRectColor.getAlpha() / 300.0F * ((Integer)storedBooleanFullRectProgress.get(this.setting.getName() + this.moduleName)).intValue()));
/*     */         }
/*     */       } 
/*     */       
/* 169 */       if (((Boolean)this.setting.getValue()).booleanValue() && !((Boolean)ClickGUI.instance.booleanFullRectSmooth.getValue()).booleanValue()) {
/* 170 */         RenderUtils2D.drawRect((this.x + 3), (this.y + 1), (this.x + this.width - 1), (this.y + this.height), booleanFullRectColor.getRGB());
/*     */       }
/* 172 */       else if (((Boolean)ClickGUI.instance.booleanFullRectSmooth.getValue()).booleanValue() && (ClickGUI.instance.booleanFullRectScaleType.getValue() != ClickGUI.BooleanFullRectScaleType.None || ((Boolean)ClickGUI.instance.booleanFullRectSmoothAlpha.getValue()).booleanValue())) {
/* 173 */         RenderUtils2D.drawRect((this.x + 3) + ((ClickGUI.instance.booleanFullRectScaleType.getValue() == ClickGUI.BooleanFullRectScaleType.Left) ? ((300.0F - ((Integer)storedBooleanFullRectProgress.get(this.setting.getName() + this.moduleName)).intValue()) * (this.width - 4) / 300.0F) : ((ClickGUI.instance.booleanFullRectScaleType.getValue() == ClickGUI.BooleanFullRectScaleType.All) ? ((300.0F - ((Integer)storedBooleanFullRectProgress.get(this.setting.getName() + this.moduleName)).intValue()) * (this.width - 4) / 2.0F / 300.0F) : 0.0F)), (this.y + 1) + ((ClickGUI.instance.booleanFullRectScaleType.getValue() == ClickGUI.BooleanFullRectScaleType.All) ? ((300.0F - ((Integer)storedBooleanFullRectProgress.get(this.setting.getName() + this.moduleName)).intValue()) * this.height / 2.0F / 300.0F) : 0.0F), (this.x + this.width - 1) - ((ClickGUI.instance.booleanFullRectScaleType.getValue() == ClickGUI.BooleanFullRectScaleType.Right) ? ((300.0F - ((Integer)storedBooleanFullRectProgress.get(this.setting.getName() + this.moduleName)).intValue()) * (this.width - 4) / 300.0F) : ((ClickGUI.instance.booleanFullRectScaleType.getValue() == ClickGUI.BooleanFullRectScaleType.All) ? ((300.0F - ((Integer)storedBooleanFullRectProgress.get(this.setting.getName() + this.moduleName)).intValue()) * (this.width - 2) / 2.0F / 300.0F) : 0.0F)), (this.y + this.height) - ((ClickGUI.instance.booleanFullRectScaleType.getValue() == ClickGUI.BooleanFullRectScaleType.All) ? ((300.0F - ((Integer)storedBooleanFullRectProgress.get(this.setting.getName() + this.moduleName)).intValue()) * this.height / 2.0F / 300.0F) : 0.0F), booleanFullRectColor.getRGB());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 178 */     if (ClickGUI.instance.booleanSwitchType.getValue() != ClickGUI.BooleanSwitchTypes.None) {
/* 179 */       Color booleanSecondaryColor; if (((Boolean)ClickGUI.instance.booleanSwitchColorChange.getValue()).booleanValue()) {
/*     */         
/* 181 */         if (this.isColorPanel) {  } else {  }  Color booleanEnabledColor = new Color(((Color)ClickGUI.instance.booleanEnabledColor.getValue()).getColor());
/* 182 */         if (this.isColorPanel) {  } else {  }  Color booleanDisabledColor = new Color(((Color)ClickGUI.instance.booleanDisabledColor.getValue()).getColor());
/*     */         
/* 184 */         if (((Boolean)ClickGUI.instance.booleanSmooth.getValue()).booleanValue()) {
/* 185 */           int red = (int)MathUtilFuckYou.linearInterp(booleanDisabledColor.getRed(), booleanEnabledColor.getRed(), ((Integer)storedBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue());
/* 186 */           int green = (int)MathUtilFuckYou.linearInterp(booleanDisabledColor.getGreen(), booleanEnabledColor.getGreen(), ((Integer)storedBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue());
/* 187 */           int blue = (int)MathUtilFuckYou.linearInterp(booleanDisabledColor.getBlue(), booleanEnabledColor.getBlue(), ((Integer)storedBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue());
/* 188 */           int alpha = (int)MathUtilFuckYou.linearInterp(booleanDisabledColor.getAlpha(), booleanEnabledColor.getAlpha(), ((Integer)storedBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue());
/* 189 */           booleanColor = (new Color(red, green, blue, alpha)).getRGB();
/*     */         
/*     */         }
/* 192 */         else if (((Boolean)this.setting.getValue()).booleanValue()) {
/* 193 */           booleanColor = booleanEnabledColor.getRGB();
/*     */         } else {
/*     */           
/* 196 */           booleanColor = booleanDisabledColor.getRGB();
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 202 */       float centerX = this.x + this.width / 5.0F * 4.0F + 18.0F * ((Float)ClickGUI.instance.booleanSwitchScale.getValue()).floatValue() + ((Float)ClickGUI.instance.booleanSwitchX.getValue()).floatValue() - 18.0F * ((Float)ClickGUI.instance.booleanSwitchScale.getValue()).floatValue() - this.height / 4.0F + (((Boolean)ClickGUI.instance.booleanSmooth.getValue()).booleanValue() ? (((Integer)storedBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue() * (18.0F * ((Float)ClickGUI.instance.booleanSwitchScale.getValue()).floatValue() - this.height / 2.0F) / 300.0F) : (((Boolean)this.setting.getValue()).booleanValue() ? (18.0F * ((Float)ClickGUI.instance.booleanSwitchScale.getValue()).floatValue() - this.height / 2.0F) : 0.0F));
/* 203 */       float centerY = this.y + this.height / 2.0F;
/* 204 */       if (ClickGUI.instance.booleanSwitchType.getValue() == ClickGUI.BooleanSwitchTypes.SliderRound) {
/* 205 */         RenderUtils2D.drawCircle(centerX, centerY, this.height / 4.0F * ((Float)ClickGUI.instance.booleanDotFillAmount.getValue()).floatValue(), booleanColor);
/* 206 */         RenderUtils2D.drawCustomRoundedRectOutline(this.x + this.width / 5.0F * 4.0F + ((Float)ClickGUI.instance.booleanSwitchX.getValue()).floatValue(), this.y + this.height / 4.0F, this.x + this.width / 5.0F * 4.0F + 18.0F * ((Float)ClickGUI.instance.booleanSwitchScale.getValue()).floatValue() + ((Float)ClickGUI.instance.booleanSwitchX.getValue()).floatValue(), this.y + this.height / 4.0F * 3.0F, 1.0F, ((Float)ClickGUI.instance.booleanSwitchLineWidth.getValue()).floatValue(), true, true, true, true, false, false, booleanColor);
/*     */       }
/* 208 */       else if (ClickGUI.instance.booleanSwitchType.getValue() == ClickGUI.BooleanSwitchTypes.SliderNonRound) {
/* 209 */         RenderUtils2D.drawRect(centerX - this.height / 4.0F * ((Float)ClickGUI.instance.booleanDotFillAmount.getValue()).floatValue(), centerY - this.height / 4.0F * ((Float)ClickGUI.instance.booleanDotFillAmount.getValue()).floatValue(), centerX + this.height / 4.0F * ((Float)ClickGUI.instance.booleanDotFillAmount.getValue()).floatValue(), centerY + this.height / 4.0F * ((Float)ClickGUI.instance.booleanDotFillAmount.getValue()).floatValue(), booleanColor);
/* 210 */         RenderUtils2D.drawRectOutline(this.x + this.width / 5.0F * 4.0F + ((Float)ClickGUI.instance.booleanSwitchX.getValue()).floatValue(), this.y + this.height / 4.0F, this.x + this.width / 5.0F * 4.0F + 18.0F * ((Float)ClickGUI.instance.booleanSwitchScale.getValue()).floatValue() + ((Float)ClickGUI.instance.booleanSwitchX.getValue()).floatValue(), this.y + this.height / 4.0F * 3.0F, ((Float)ClickGUI.instance.booleanSwitchLineWidth.getValue()).floatValue(), booleanColor, false, false);
/*     */       } 
/*     */ 
/*     */       
/* 214 */       float centerXDotMode = this.x + this.width / 5.0F * 4.0F + 7.0F + ((Float)ClickGUI.instance.booleanSwitchX.getValue()).floatValue();
/* 215 */       float centerYDotMode = this.y + this.height / 2.0F;
/* 216 */       if ((ClickGUI.instance.booleanDotMode.getValue() == ClickGUI.BooleanDotMode.Alpha || ClickGUI.instance.booleanDotMode.getValue() == ClickGUI.BooleanDotMode.Both) && (ClickGUI.instance.booleanSwitchType.getValue() == ClickGUI.BooleanSwitchTypes.Circle || ClickGUI.instance.booleanSwitchType.getValue() == ClickGUI.BooleanSwitchTypes.Square)) {
/* 217 */         booleanSecondaryColor = new Color((new Color(booleanColor)).getRed(), (new Color(booleanColor)).getGreen(), (new Color(booleanColor)).getBlue(), (int)((new Color(booleanColor)).getAlpha() / 300.0F * ((Integer)storedBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue()));
/*     */       } else {
/*     */         
/* 220 */         booleanSecondaryColor = new Color(booleanColor);
/*     */       } 
/* 222 */       if (ClickGUI.instance.booleanSwitchType.getValue() == ClickGUI.BooleanSwitchTypes.Circle) {
/* 223 */         RenderUtils2D.drawCircle(centerXDotMode, centerYDotMode, (ClickGUI.instance.booleanDotMode.getValue() == ClickGUI.BooleanDotMode.Scale || ClickGUI.instance.booleanDotMode.getValue() == ClickGUI.BooleanDotMode.Both) ? (((Boolean)ClickGUI.instance.booleanSmooth.getValue()).booleanValue() ? (((Integer)storedBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue() * this.height / 4.0F * ((Float)ClickGUI.instance.booleanDotFillAmount.getValue()).floatValue() / 300.0F) : (((Boolean)this.setting.getValue()).booleanValue() ? (this.height / 4.0F * ((Float)ClickGUI.instance.booleanDotFillAmount.getValue()).floatValue()) : 0.0F)) : (this.height / 4.0F * ((Float)ClickGUI.instance.booleanDotFillAmount.getValue()).floatValue()), booleanSecondaryColor.getRGB());
/* 224 */         RenderUtils2D.drawCircleOutline(centerXDotMode, centerYDotMode, this.height / 4.0F, ((Float)ClickGUI.instance.booleanSwitchLineWidth.getValue()).floatValue(), booleanColor);
/*     */       }
/* 226 */       else if (ClickGUI.instance.booleanSwitchType.getValue() == ClickGUI.BooleanSwitchTypes.Square) {
/* 227 */         float squareDotScale = (ClickGUI.instance.booleanDotMode.getValue() == ClickGUI.BooleanDotMode.Scale || ClickGUI.instance.booleanDotMode.getValue() == ClickGUI.BooleanDotMode.Both) ? (((Boolean)ClickGUI.instance.booleanSmooth.getValue()).booleanValue() ? (((Integer)storedBooleanProgressLoops.get(this.setting.getName() + this.moduleName)).intValue() * this.height / 4.0F * ((Float)ClickGUI.instance.booleanDotFillAmount.getValue()).floatValue() / 300.0F) : (((Boolean)this.setting.getValue()).booleanValue() ? (this.height / 4.0F * ((Float)ClickGUI.instance.booleanDotFillAmount.getValue()).floatValue()) : 0.0F)) : (this.height / 4.0F * ((Float)ClickGUI.instance.booleanDotFillAmount.getValue()).floatValue());
/* 228 */         RenderUtils2D.drawRect(centerXDotMode - squareDotScale, centerYDotMode - squareDotScale, centerXDotMode + squareDotScale, centerYDotMode + squareDotScale, booleanSecondaryColor.getRGB());
/* 229 */         RenderUtils2D.drawRectOutline(centerXDotMode - this.height / 4.0F, centerYDotMode - this.height / 4.0F, centerXDotMode + this.height / 4.0F, centerYDotMode + this.height / 4.0F, ((Float)ClickGUI.instance.booleanSwitchLineWidth.getValue()).floatValue(), booleanColor, false, false);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 234 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Minecraft) {
/* 235 */       GL11.glEnable(3553);
/* 236 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 237 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/* 239 */       this.mc.field_71466_p.func_175065_a(this.setting.getName(), (this.x + 5), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F), ((Boolean)ClickGUI.instance.booleanTextColorChange.getValue()).booleanValue() ? booleanTextColor.getRGB() : defaultTextColor.getRGB(), ((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue());
/*     */       
/* 241 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 242 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/* 243 */       GL11.glDisable(3553);
/*     */     
/*     */     }
/* 246 */     else if (((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue()) {
/* 247 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 248 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/* 250 */       FontManager.drawShadow(this.setting.getName(), (this.x + 5), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), ((Boolean)ClickGUI.instance.booleanTextColorChange.getValue()).booleanValue() ? booleanTextColor.getRGB() : defaultTextColor.getRGB());
/*     */       
/* 252 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 253 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */     } else {
/*     */       
/* 256 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 257 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/* 259 */       FontManager.draw(this.setting.getName(), (this.x + 5), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), ((Boolean)ClickGUI.instance.booleanTextColorChange.getValue()).booleanValue() ? booleanTextColor.getRGB() : defaultTextColor.getRGB());
/*     */       
/* 261 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 262 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */     } 
/*     */ 
/*     */     
/* 266 */     GlStateManager.func_179141_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public void bottomRender(int mouseX, int mouseY, boolean lastSetting, boolean firstSetting, float partialTicks) {
/* 271 */     GlStateManager.func_179118_c();
/* 272 */     drawSettingRects(lastSetting, this.isColorPanel);
/*     */     
/* 274 */     drawExtendedGradient(lastSetting, this.isColorPanel);
/* 275 */     if (this.isColorPanel) {
/* 276 */       if (((Boolean)ClickGUI.instance.colorDropMenuSideBar.getValue()).booleanValue()) {
/* 277 */         drawExtendedLineColor(lastSetting);
/*     */       }
/* 279 */       if (((Boolean)ClickGUI.instance.colorDropMenuOutline.getValue()).booleanValue()) {
/* 280 */         drawColorMenuOutline(lastSetting, firstSetting, ((Boolean)ClickGUI.instance.colorDropMenuExtensions.getValue()).booleanValue());
/*     */       }
/*     */     } else {
/*     */       
/* 284 */       drawExtendedLine(lastSetting);
/*     */     } 
/*     */     
/* 287 */     renderHoverRect(this.moduleName + this.setting.getName(), mouseX, mouseY, 2.0F, -1.0F, false);
/*     */     
/* 289 */     GlStateManager.func_179141_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHovered(int mouseX, int mouseY) {
/* 294 */     if (!anyExpanded || this.isColorPanel) {
/* 295 */       return (mouseX >= Math.min(this.x, this.x + this.width) && mouseX <= Math.max(this.x, this.x + this.width) && mouseY >= Math.min(this.y, this.y + this.height) && mouseY <= Math.max(this.y, this.y + this.height));
/*     */     }
/*     */     
/* 298 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 305 */     if (!this.setting.isVisible() || !isHovered(mouseX, mouseY))
/* 306 */       return false; 
/* 307 */     if (mouseButton == 0) {
/* 308 */       this.setting.setValue(Boolean.valueOf(!((Boolean)this.setting.getValue()).booleanValue()));
/* 309 */       SoundUtil.playButtonClick();
/*     */     } 
/* 311 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 316 */     return this.setting.isVisible();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 321 */     return this.setting.getDescription();
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\gui\components\BooleanButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */