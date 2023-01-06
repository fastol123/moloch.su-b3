/*     */ package net.spartanb312.base.gui.components;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.module.modules.client.CustomFont;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.spartanb312.base.client.FontManager;
/*     */ import net.spartanb312.base.core.setting.NumberSetting;
/*     */ import net.spartanb312.base.core.setting.settings.DoubleSetting;
/*     */ import net.spartanb312.base.core.setting.settings.FloatSetting;
/*     */ import net.spartanb312.base.core.setting.settings.IntSetting;
/*     */ import net.spartanb312.base.gui.Component;
/*     */ import net.spartanb312.base.gui.Panel;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*     */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class NumberSlider
/*     */   extends Component
/*     */ {
/*     */   public boolean sliding = false;
/*     */   NumberSetting<?> setting;
/*     */   boolean isColorPanel;
/*     */   String moduleName;
/*     */   
/*     */   public NumberSlider(NumberSetting<?> setting, int width, int height, Panel father, boolean isColorPanel, Module module) {
/*  29 */     this.moduleName = module.name;
/*  30 */     this.width = width;
/*  31 */     this.height = height + (((Boolean)ClickGUI.instance.numSliderThinMode.getValue()).booleanValue() ? 4 : 0);
/*  32 */     this.father = father;
/*  33 */     this.setting = setting;
/*  34 */     this.isColorPanel = isColorPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY, float translateDelta, float partialTicks) {
/*  40 */     GlStateManager.func_179118_c();
/*     */     
/*  42 */     if (!this.setting.isVisible()) this.sliding = false;
/*     */     
/*  44 */     String displayValue = (this.setting instanceof IntSetting) ? ((Number)this.setting.getValue()).toString() : String.format("%.1f", new Object[] { Double.valueOf(((Number)this.setting.getValue()).doubleValue()) });
/*  45 */     double percentBar = (((Number)this.setting.getValue()).doubleValue() - this.setting.getMin().doubleValue()) / (this.setting.getMax().doubleValue() - this.setting.getMin().doubleValue());
/*  46 */     double tempWidth = (this.width - 4) * percentBar;
/*  47 */     double tempWidthThin = (this.width - 8) * percentBar;
/*     */     
/*  49 */     int numSliderTextColorColorDropMenuAnimateAlpha = (int)(((Color)ClickGUI.instance.numSliderTextColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader);
/*  50 */     if (numSliderTextColorColorDropMenuAnimateAlpha <= 4) {
/*  51 */       numSliderTextColorColorDropMenuAnimateAlpha = 4;
/*     */     }
/*  53 */     int numSliderDisplayValueTextColorColorDropMenuAnimateAlpha = (int)(((Color)ClickGUI.instance.numSliderDisplayValueTextColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader);
/*  54 */     if (numSliderDisplayValueTextColorColorDropMenuAnimateAlpha <= 4) {
/*  55 */       numSliderDisplayValueTextColorColorDropMenuAnimateAlpha = 4;
/*     */     }
/*  57 */     if (this.isColorPanel) {  } else {  }  Color settingTextColor = new Color(((Color)ClickGUI.instance.numSliderTextColor.getValue()).getColor());
/*  58 */     if (this.isColorPanel) {  } else {  }  Color displayValueTextColor = new Color(((Color)ClickGUI.instance.numSliderDisplayValueTextColor.getValue()).getColor());
/*     */     
/*  60 */     if (((Boolean)ClickGUI.instance.numSliderThinMode.getValue()).booleanValue()) {
/*     */       
/*  62 */       int unSlidedColor = this.isColorPanel ? (new Color(((Color)ClickGUI.instance.numSliderThinModeUnSlidedColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.numSliderThinModeUnSlidedColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.numSliderThinModeUnSlidedColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.numSliderThinModeUnSlidedColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.numSliderThinModeUnSlidedColor.getValue()).getAlpha())).getRGB() : ((Color)ClickGUI.instance.numSliderThinModeUnSlidedColor.getValue()).getColor();
/*  63 */       if (((Boolean)ClickGUI.instance.numSliderThinModeRounded.getValue()).booleanValue()) {
/*  64 */         RenderUtils2D.drawRoundedRect((this.x + 5), this.y + this.height / 14.0F * 11.0F - ((Float)ClickGUI.instance.numSliderThinModeBarThickness.getValue()).floatValue(), ((Float)ClickGUI.instance.numSliderThinModeRoundedRadius.getValue()).floatValue(), (this.x + this.width - 4), this.y + this.height / 14.0F * 11.0F + ((Float)ClickGUI.instance.numSliderThinModeBarThickness.getValue()).floatValue(), false, true, true, true, true, unSlidedColor);
/*     */       } else {
/*     */         
/*  67 */         RenderUtils2D.drawRect((this.x + 5), this.y + this.height / 14.0F * 11.0F - ((Float)ClickGUI.instance.numSliderThinModeBarThickness.getValue()).floatValue(), (this.x + this.width - 4), this.y + this.height / 14.0F * 11.0F + ((Float)ClickGUI.instance.numSliderThinModeBarThickness.getValue()).floatValue(), unSlidedColor);
/*     */       } 
/*     */       
/*  70 */       if (((Boolean)ClickGUI.instance.numSliderGradient.getValue()).booleanValue()) {
/*  71 */         int slidedColorRight = this.isColorPanel ? (new Color(((Color)ClickGUI.instance.numSliderRightColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.numSliderRightColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.numSliderRightColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.numSliderRightColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.numSliderRightColor.getValue()).getAlpha())).getRGB() : ((Color)ClickGUI.instance.numSliderRightColor.getValue()).getColor();
/*  72 */         int slidedColorLeft = this.isColorPanel ? (new Color(((Color)ClickGUI.instance.numSliderLeftColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.numSliderLeftColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.numSliderLeftColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.numSliderLeftColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.numSliderLeftColor.getValue()).getAlpha())).getRGB() : ((Color)ClickGUI.instance.numSliderLeftColor.getValue()).getColor();
/*     */         
/*  74 */         if (((Boolean)ClickGUI.instance.numSliderThinModeRounded.getValue()).booleanValue()) {
/*  75 */           RenderUtils2D.drawCustomRoundedRect((this.x + 5), this.y + this.height / 14.0F * 11.0F - ((Float)ClickGUI.instance.numSliderThinModeBarThickness.getValue()).floatValue(), ((Float)ClickGUI.instance.numSliderThinModeRoundedRadius.getValue()).floatValue(), (this.x + 5 + (int)tempWidthThin), this.y + this.height / 14.0F * 11.0F + ((Float)ClickGUI.instance.numSliderThinModeBarThickness.getValue()).floatValue(), true, true, true, true, false, true, false, slidedColorRight, slidedColorLeft, slidedColorRight, slidedColorLeft, slidedColorLeft, slidedColorLeft, slidedColorLeft, slidedColorLeft, slidedColorRight, slidedColorLeft, slidedColorLeft, slidedColorRight, slidedColorRight, slidedColorRight, slidedColorRight, slidedColorRight);
/*     */         } else {
/*     */           
/*  78 */           RenderUtils2D.drawCustomRect((this.x + 5), this.y + this.height / 14.0F * 11.0F - ((Float)ClickGUI.instance.numSliderThinModeBarThickness.getValue()).floatValue(), (this.x + 5 + (int)tempWidthThin), this.y + this.height / 14.0F * 11.0F + ((Float)ClickGUI.instance.numSliderThinModeBarThickness.getValue()).floatValue(), slidedColorRight, slidedColorLeft, slidedColorLeft, slidedColorRight);
/*     */         }
/*     */       
/*     */       }
/*  82 */       else if (((Boolean)ClickGUI.instance.numSliderThinModeRounded.getValue()).booleanValue()) {
/*  83 */         RenderUtils2D.drawRoundedRect((this.x + 5), this.y + this.height / 14.0F * 11.0F - ((Float)ClickGUI.instance.numSliderThinModeBarThickness.getValue()).floatValue(), ((Float)ClickGUI.instance.numSliderThinModeRoundedRadius.getValue()).floatValue(), (this.x + 5 + (int)tempWidthThin), this.y + this.height / 14.0F * 11.0F + ((Float)ClickGUI.instance.numSliderThinModeBarThickness.getValue()).floatValue(), false, true, true, true, true, this.isColorPanel ? (new Color(((Color)ClickGUI.instance.numSliderColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.numSliderColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.numSliderColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.numSliderColor.getValue()).getAlpha() / 300.0F) : ((Color)ClickGUI.instance.numSliderColor.getValue()).getAlpha())).getRGB() : ((Color)ClickGUI.instance.numSliderColor.getValue()).getColor());
/*     */       } else {
/*     */         
/*  86 */         RenderUtils2D.drawRect((this.x + 5), this.y + this.height / 14.0F * 11.0F - ((Float)ClickGUI.instance.numSliderThinModeBarThickness.getValue()).floatValue(), (this.x + 5 + (int)tempWidthThin), this.y + this.height / 14.0F * 11.0F + ((Float)ClickGUI.instance.numSliderThinModeBarThickness.getValue()).floatValue(), this.isColorPanel ? (new Color(((Color)ClickGUI.instance.numSliderColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.numSliderColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.numSliderColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.numSliderColor.getValue()).getAlpha() / 300.0F) : ((Color)ClickGUI.instance.numSliderColor.getValue()).getAlpha())).getRGB() : ((Color)ClickGUI.instance.numSliderColor.getValue()).getColor());
/*     */       } 
/*     */ 
/*     */       
/*  90 */       if (((Boolean)ClickGUI.instance.numSliderThinModeSliderButton.getValue()).booleanValue()) {
/*  91 */         int buttonShadowAlpha = this.isColorPanel ? (((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Integer)ClickGUI.instance.numSliderThinModeSliderButtonShadowAlpha.getValue()).intValue() / 300.0F * colorMenuToggleThreader) : ((Integer)ClickGUI.instance.numSliderThinModeSliderButtonShadowAlpha.getValue()).intValue()) : ((Integer)ClickGUI.instance.numSliderThinModeSliderButtonShadowAlpha.getValue()).intValue();
/*     */         
/*  93 */         if (((Boolean)ClickGUI.instance.numSliderThinModeSliderButtonShadow.getValue()).booleanValue()) {
/*  94 */           if (((Boolean)ClickGUI.instance.numSliderThinModeSliderButtonShadowAlphaFadeOut.getValue()).booleanValue() && percentBar < ((Float)ClickGUI.instance.numSliderThinModeSliderButtonShadowAlphaFadeOutThreshold.getValue()).floatValue()) {
/*  95 */             buttonShadowAlpha = (int)(((Integer)ClickGUI.instance.numSliderThinModeSliderButtonShadowAlpha.getValue()).intValue() * percentBar / ((Float)ClickGUI.instance.numSliderThinModeSliderButtonShadowAlphaFadeOutThreshold.getValue()).floatValue());
/*     */             
/*  97 */             if (buttonShadowAlpha < 0) buttonShadowAlpha = 0; 
/*  98 */             if (buttonShadowAlpha > 255) buttonShadowAlpha = 255;
/*     */             
/* 100 */             int buttonHorizontalShadowAlpha = ((Integer)ClickGUI.instance.numSliderThinModeSliderButtonShadowAlpha.getValue()).intValue() - buttonShadowAlpha;
/* 101 */             int buttonHorizontalShadowColor = (new Color(0, 0, 0, buttonHorizontalShadowAlpha)).getRGB();
/* 102 */             GlStateManager.func_179118_c();
/* 103 */             RenderUtils2D.drawCustomRect((this.x + 5 + (int)tempWidthThin) + ((Float)ClickGUI.instance.numSliderThinModeSliderButtonWidth.getValue()).floatValue() / 2.0F - 2.0F, this.y + this.height / 14.0F * 11.0F - ((Float)ClickGUI.instance.numSliderThinModeBarThickness.getValue()).floatValue(), (this.x + 5 + (int)tempWidthThin) + ((Float)ClickGUI.instance.numSliderThinModeSliderButtonWidth.getValue()).floatValue() / 2.0F - 2.0F + ((Float)ClickGUI.instance.numSliderThinModeSliderButtonShadowSize.getValue()).floatValue() * 35.0F, this.y + this.height / 14.0F * 11.0F + ((Float)ClickGUI.instance.numSliderThinModeBarThickness.getValue()).floatValue(), (new Color(0, 0, 0, 0)).getRGB(), buttonHorizontalShadowColor, buttonHorizontalShadowColor, (new Color(0, 0, 0, 0)).getRGB());
/* 104 */             GlStateManager.func_179141_d();
/*     */           }
/* 106 */           else if (((Boolean)ClickGUI.instance.numSliderThinModeSliderButtonShadowAlphaFadeOut.getValue()).booleanValue() && 1.0D - percentBar < ((Float)ClickGUI.instance.numSliderThinModeSliderButtonShadowAlphaFadeOutThreshold.getValue()).floatValue()) {
/* 107 */             buttonShadowAlpha = (int)(((Integer)ClickGUI.instance.numSliderThinModeSliderButtonShadowAlpha.getValue()).intValue() * (1.0D - percentBar) / ((Float)ClickGUI.instance.numSliderThinModeSliderButtonShadowAlphaFadeOutThreshold.getValue()).floatValue());
/*     */             
/* 109 */             if (buttonShadowAlpha < 0) buttonShadowAlpha = 0; 
/* 110 */             if (buttonShadowAlpha > 255) buttonShadowAlpha = 255;
/*     */             
/* 112 */             int buttonHorizontalShadowAlpha = ((Integer)ClickGUI.instance.numSliderThinModeSliderButtonShadowAlpha.getValue()).intValue() - buttonShadowAlpha;
/* 113 */             int buttonHorizontalShadowColor = (new Color(0, 0, 0, buttonHorizontalShadowAlpha)).getRGB();
/* 114 */             GlStateManager.func_179118_c();
/* 115 */             RenderUtils2D.drawCustomRect((this.x + 5 + (int)tempWidthThin + 2) - ((Float)ClickGUI.instance.numSliderThinModeSliderButtonWidth.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.numSliderThinModeSliderButtonShadowSize.getValue()).floatValue() * 35.0F, this.y + this.height / 14.0F * 11.0F - ((Float)ClickGUI.instance.numSliderThinModeBarThickness.getValue()).floatValue(), (this.x + 5 + (int)tempWidthThin + 2) - ((Float)ClickGUI.instance.numSliderThinModeSliderButtonWidth.getValue()).floatValue() / 2.0F, this.y + this.height / 14.0F * 11.0F + ((Float)ClickGUI.instance.numSliderThinModeBarThickness.getValue()).floatValue(), buttonHorizontalShadowColor, (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), buttonHorizontalShadowColor);
/* 116 */             GlStateManager.func_179141_d();
/*     */           } 
/* 118 */           RenderUtils2D.drawBetterRoundRectFade((this.x + 5 + (int)tempWidthThin) - ((Float)ClickGUI.instance.numSliderThinModeSliderButtonWidth.getValue()).floatValue() / 2.0F, this.y + this.height / 14.0F * 11.0F - ((Float)ClickGUI.instance.numSliderThinModeSliderButtonHeight.getValue()).floatValue() / 2.0F, (this.x + 5 + (int)tempWidthThin) + ((Float)ClickGUI.instance.numSliderThinModeSliderButtonWidth.getValue()).floatValue() / 2.0F, this.y + this.height / 14.0F * 11.0F + ((Float)ClickGUI.instance.numSliderThinModeSliderButtonHeight.getValue()).floatValue() / 2.0F, ((Float)ClickGUI.instance.numSliderThinModeSliderButtonShadowSize.getValue()).floatValue(), 20.0F, false, true, false, (new Color(0, 0, 0, buttonShadowAlpha)).getRGB());
/*     */         } 
/*     */         
/* 121 */         int numSliderThinModeSliderButtonColor = this.isColorPanel ? (new Color(((Color)ClickGUI.instance.numSliderThinModeSliderButtonColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.numSliderThinModeSliderButtonColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.numSliderThinModeSliderButtonColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.numSliderThinModeSliderButtonColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.numSliderThinModeSliderButtonColor.getValue()).getAlpha())).getRGB() : ((Color)ClickGUI.instance.numSliderThinModeSliderButtonColor.getValue()).getColor();
/* 122 */         if (((Boolean)ClickGUI.instance.numSliderThinModeSliderButtonRounded.getValue()).booleanValue()) {
/* 123 */           RenderUtils2D.drawRoundedRect((this.x + 5 + (int)tempWidthThin) - ((Float)ClickGUI.instance.numSliderThinModeSliderButtonWidth.getValue()).floatValue() / 2.0F, this.y + this.height / 14.0F * 11.0F - ((Float)ClickGUI.instance.numSliderThinModeSliderButtonHeight.getValue()).floatValue() / 2.0F, ((Float)ClickGUI.instance.numSliderThinModeSliderButtonRoundedRadius.getValue()).floatValue(), (this.x + 5 + (int)tempWidthThin) + ((Float)ClickGUI.instance.numSliderThinModeSliderButtonWidth.getValue()).floatValue() / 2.0F, this.y + this.height / 14.0F * 11.0F + ((Float)ClickGUI.instance.numSliderThinModeSliderButtonHeight.getValue()).floatValue() / 2.0F, false, true, true, true, true, numSliderThinModeSliderButtonColor);
/*     */         } else {
/*     */           
/* 126 */           RenderUtils2D.drawRect((this.x + 5 + (int)tempWidthThin) - ((Float)ClickGUI.instance.numSliderThinModeSliderButtonWidth.getValue()).floatValue() / 2.0F, this.y + this.height / 14.0F * 11.0F - ((Float)ClickGUI.instance.numSliderThinModeSliderButtonHeight.getValue()).floatValue() / 2.0F, (this.x + 5 + (int)tempWidthThin) + ((Float)ClickGUI.instance.numSliderThinModeSliderButtonWidth.getValue()).floatValue() / 2.0F, this.y + this.height / 14.0F * 11.0F + ((Float)ClickGUI.instance.numSliderThinModeSliderButtonHeight.getValue()).floatValue() / 2.0F, numSliderThinModeSliderButtonColor);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 133 */     else if (((Boolean)ClickGUI.instance.numSliderGradient.getValue()).booleanValue()) {
/* 134 */       int slidedColorRight = this.isColorPanel ? (new Color(((Color)ClickGUI.instance.numSliderRightColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.numSliderRightColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.numSliderRightColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.numSliderRightColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.numSliderRightColor.getValue()).getAlpha())).getRGB() : ((Color)ClickGUI.instance.numSliderRightColor.getValue()).getColor();
/* 135 */       int slidedColorLeft = this.isColorPanel ? (new Color(((Color)ClickGUI.instance.numSliderLeftColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.numSliderLeftColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.numSliderLeftColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.numSliderLeftColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.numSliderLeftColor.getValue()).getAlpha())).getRGB() : ((Color)ClickGUI.instance.numSliderLeftColor.getValue()).getColor();
/*     */       
/* 137 */       RenderUtils2D.drawCustomRect((this.x + 3), (this.y + 2), (this.x + 3 + (int)tempWidth), (this.y + this.height), slidedColorRight, slidedColorLeft, slidedColorLeft, slidedColorRight);
/*     */     } else {
/*     */       
/* 140 */       RenderUtils2D.drawRect((this.x + 3), (this.y + 1), (this.x + 3 + (int)tempWidth), (this.y + this.height), this.isColorPanel ? (new Color(((Color)ClickGUI.instance.numSliderColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.numSliderColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.numSliderColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.numSliderColor.getValue()).getAlpha() / 300.0F) : ((Color)ClickGUI.instance.numSliderColor.getValue()).getAlpha())).getRGB() : ((Color)ClickGUI.instance.numSliderColor.getValue()).getColor());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     float currentTextWidth = this.font.getStringWidth(String.valueOf(displayValue));
/*     */     
/* 149 */     if (this.sliding) {
/* 150 */       double diff = this.setting.getMax().doubleValue() - this.setting.getMin().doubleValue();
/* 151 */       double val = this.setting.getMin().doubleValue() + MathHelper.func_151237_a((mouseX - (this.x + 3)) / (this.width - 4), 0.0D, 1.0D) * diff;
/* 152 */       if (this.setting instanceof DoubleSetting) {
/* 153 */         ((DoubleSetting)this.setting).setValue(Double.valueOf(val));
/* 154 */       } else if (this.setting instanceof FloatSetting) {
/* 155 */         ((FloatSetting)this.setting).setValue(Float.valueOf((float)val));
/* 156 */       } else if (this.setting instanceof IntSetting) {
/* 157 */         ((IntSetting)this.setting).setValue(Integer.valueOf((int)val));
/*     */       } 
/*     */     } 
/* 160 */     if (((Boolean)ClickGUI.instance.numSliderThinMode.getValue()).booleanValue()) {
/* 161 */       float settingNameX = (this.x + 5);
/* 162 */       float textY = this.y + this.font.getHeight() / 2.0F + ((Float)ClickGUI.instance.numSliderThinModeTextOffset.getValue()).floatValue();
/*     */       
/* 164 */       if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Minecraft) {
/* 165 */         float displayValueX = (this.x + this.width - 3 - this.mc.field_71466_p.func_78256_a(String.valueOf(displayValue)));
/*     */ 
/*     */         
/* 168 */         GL11.glEnable(3553);
/* 169 */         GL11.glTranslatef(settingNameX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), textY * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 170 */         GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */         
/* 172 */         this.mc.field_71466_p.func_175065_a(this.setting.getName(), settingNameX, textY, settingTextColor.getRGB(), ((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue());
/*     */         
/* 174 */         GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 175 */         GL11.glTranslatef(settingNameX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, textY * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */ 
/*     */ 
/*     */         
/* 179 */         GL11.glTranslatef(displayValueX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) + (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, textY * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 180 */         GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */         
/* 182 */         this.mc.field_71466_p.func_175065_a(String.valueOf(displayValue), displayValueX, textY, displayValueTextColor.getRGB(), ((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue());
/*     */         
/* 184 */         GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 185 */         GL11.glTranslatef(displayValueX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F - (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, textY * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/* 186 */         GL11.glDisable(3553);
/*     */       } else {
/*     */         
/* 189 */         float displayValueX = (this.x + this.width - 3 - this.font.getStringWidth(String.valueOf(displayValue)));
/* 190 */         if (((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue())
/*     */         {
/* 192 */           GL11.glTranslatef(settingNameX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), textY * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 193 */           GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */           
/* 195 */           FontManager.drawShadow(this.setting.getName(), settingNameX, textY, settingTextColor.getRGB());
/*     */           
/* 197 */           GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 198 */           GL11.glTranslatef(settingNameX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, textY * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */ 
/*     */           
/* 201 */           GL11.glTranslatef(displayValueX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) + (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, textY * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 202 */           GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */           
/* 204 */           FontManager.drawShadow(String.valueOf(displayValue), displayValueX, textY, displayValueTextColor.getRGB());
/*     */           
/* 206 */           GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 207 */           GL11.glTranslatef(displayValueX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F - (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, textY * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */         }
/*     */         else
/*     */         {
/* 211 */           GL11.glTranslatef(settingNameX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), textY * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 212 */           GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */           
/* 214 */           FontManager.draw(this.setting.getName(), settingNameX, textY, settingTextColor.getRGB());
/*     */           
/* 216 */           GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 217 */           GL11.glTranslatef(settingNameX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, textY * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */ 
/*     */ 
/*     */           
/* 221 */           GL11.glTranslatef(displayValueX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) + (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, textY * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 222 */           GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */           
/* 224 */           FontManager.draw(String.valueOf(displayValue), displayValueX, textY, displayValueTextColor.getRGB());
/*     */           
/* 226 */           GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 227 */           GL11.glTranslatef(displayValueX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F - (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, textY * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 235 */     else if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Minecraft) {
/* 236 */       GL11.glEnable(3553);
/* 237 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 238 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/* 240 */       this.mc.field_71466_p.func_175065_a(this.setting.getName(), (this.x + 5), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F), settingTextColor.getRGB(), ((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue());
/*     */       
/* 242 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 243 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */ 
/*     */ 
/*     */       
/* 247 */       float displayValueX = ((Boolean)ClickGUI.instance.numSliderValueLock.getValue()).booleanValue() ? (this.x + this.width - 3 - this.mc.field_71466_p.func_78256_a(String.valueOf(displayValue))) : ((this.x + 7) + this.mc.field_71466_p.func_78256_a(this.setting.getName()) * ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 248 */       GL11.glTranslatef(displayValueX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) + (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 249 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/* 251 */       this.mc.field_71466_p.func_175065_a(String.valueOf(displayValue), displayValueX, (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F), displayValueTextColor.getRGB(), ((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue());
/*     */       
/* 253 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 254 */       GL11.glTranslatef(displayValueX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F - (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/* 255 */       GL11.glDisable(3553);
/*     */     } else {
/*     */       
/* 258 */       float displayValueX = ((Boolean)ClickGUI.instance.numSliderValueLock.getValue()).booleanValue() ? (this.x + this.width - 3 - this.font.getStringWidth(String.valueOf(displayValue))) : ((this.x + 7) + this.font.getStringWidth(this.setting.getName()) * ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 259 */       if (((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue()) {
/* 260 */         GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 261 */         GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */         
/* 263 */         FontManager.drawShadow(this.setting.getName(), (this.x + 5), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), settingTextColor.getRGB());
/*     */         
/* 265 */         GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 266 */         GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */ 
/*     */ 
/*     */         
/* 270 */         GL11.glTranslatef(displayValueX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) + (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 271 */         GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */         
/* 273 */         FontManager.drawShadow(String.valueOf(displayValue), displayValueX, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), displayValueTextColor.getRGB());
/*     */         
/* 275 */         GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 276 */         GL11.glTranslatef(displayValueX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F - (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */       }
/*     */       else {
/*     */         
/* 280 */         GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 281 */         GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */         
/* 283 */         FontManager.draw(this.setting.getName(), (this.x + 5), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), settingTextColor.getRGB());
/*     */         
/* 285 */         GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 286 */         GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */ 
/*     */ 
/*     */         
/* 290 */         GL11.glTranslatef(displayValueX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) + (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 291 */         GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */         
/* 293 */         FontManager.draw(String.valueOf(displayValue), displayValueX, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), displayValueTextColor.getRGB());
/*     */         
/* 295 */         GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 296 */         GL11.glTranslatef(displayValueX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F - (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 303 */     GlStateManager.func_179141_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public void bottomRender(int mouseX, int mouseY, boolean lastSetting, boolean firstSetting, float partialTicks) {
/* 308 */     GlStateManager.func_179118_c();
/* 309 */     drawSettingRects(lastSetting, this.isColorPanel);
/*     */     
/* 311 */     drawExtendedGradient(lastSetting, this.isColorPanel);
/* 312 */     if (this.isColorPanel) {
/* 313 */       if (((Boolean)ClickGUI.instance.colorDropMenuSideBar.getValue()).booleanValue()) {
/* 314 */         drawExtendedLineColor(lastSetting);
/*     */       }
/* 316 */       if (((Boolean)ClickGUI.instance.colorDropMenuOutline.getValue()).booleanValue()) {
/* 317 */         drawColorMenuOutline(lastSetting, firstSetting, ((Boolean)ClickGUI.instance.colorDropMenuExtensions.getValue()).booleanValue());
/*     */       }
/*     */     } else {
/*     */       
/* 321 */       drawExtendedLine(lastSetting);
/*     */     } 
/*     */     
/* 324 */     renderHoverRect(this.moduleName + this.setting.getName(), mouseX, mouseY, 2.0F, -5.0F, false);
/*     */     
/* 326 */     GlStateManager.func_179141_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 331 */     if (!this.setting.isVisible() || !isHovered(mouseX, mouseY))
/* 332 */       return false; 
/* 333 */     if (mouseButton == 0) {
/* 334 */       this.sliding = true;
/* 335 */       return true;
/*     */     } 
/* 337 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int state) {
/* 342 */     this.sliding = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 347 */     return this.setting.isVisible();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 352 */     return this.setting.getDescription();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHovered(int mouseX, int mouseY) {
/* 357 */     if (!anyExpanded || this.isColorPanel) {
/* 358 */       return (mouseX >= Math.min(this.x, this.x + this.width) && mouseX <= Math.max(this.x, this.x + this.width) && mouseY >= Math.min(this.y, this.y + this.height) && mouseY <= Math.max(this.y, this.y + this.height));
/*     */     }
/*     */     
/* 361 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\gui\components\NumberSlider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */