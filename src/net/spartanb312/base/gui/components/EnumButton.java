/*     */ package net.spartanb312.base.gui.components;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.module.modules.client.CustomFont;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.spartanb312.base.client.FontManager;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.core.setting.settings.EnumSetting;
/*     */ import net.spartanb312.base.gui.Component;
/*     */ import net.spartanb312.base.gui.Panel;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*     */ import net.spartanb312.base.module.modules.client.HUDEditor;
/*     */ import net.spartanb312.base.utils.MathUtilFuckYou;
/*     */ import net.spartanb312.base.utils.SoundUtil;
/*     */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ public class EnumButton
/*     */   extends Component
/*     */ {
/*     */   EnumSetting<?> setting;
/*     */   public static EnumButton instance;
/*     */   public Enum<?> lastElement;
/*     */   boolean flag = false;
/*     */   String moduleName;
/*  44 */   public static HashMap<String, Integer> storedArrowAnimationLoopsRight = new HashMap<>();
/*  45 */   public static HashMap<String, Integer> storedArrowAnimationLoopsLeft = new HashMap<>();
/*  46 */   public static HashMap<String, Integer> storedEnumDropMenuSelectLoops = new HashMap<>();
/*  47 */   public static HashMap<String, Integer> storedEnumDropMenuSelectSideRectLoops = new HashMap<>();
/*  48 */   public static HashMap<String, Integer> storedEnumDropMenuSelectSideGlowLoops = new HashMap<>();
/*  49 */   public static HashMap<String, Integer> storedEnumDropMenuSelectTextLoops = new HashMap<>();
/*  50 */   public static HashMap<String, Integer> storedEnumDropMenuExpandRectLoops = new HashMap<>();
/*  51 */   public static HashMap<String, Integer> storedEnumIconExpandedLoops = new HashMap<>();
/*  52 */   public static HashMap<String, Integer> storedEnumDropMenuOpenCloseAlphaLayerLoops = new HashMap<>();
/*  53 */   public static HashMap<String, Integer> storedEnumDropMenuOpenCloseTextAlphaLayerLoops = new HashMap<>();
/*  54 */   public static int staticInt = 0;
/*  55 */   public static float lastSelectedRectStartY = -999.0F; boolean flag1; boolean flag2; boolean flag4; boolean reverseAlphaLayer2Flag;
/*  56 */   public static float lastSelectedRectEndY = -999.0F; boolean reverseAlphaLayerAnimationFlag; boolean alphaLayerAnimationReverseDoneFlag; float animatedScale; int animateTextAlpha; public EnumButton(Setting<? extends Enum<?>> setting, int width, int height, Panel father, Module module) {
/*  57 */     this.flag1 = true;
/*  58 */     this.flag2 = true;
/*  59 */     this.flag4 = true;
/*  60 */     this.reverseAlphaLayer2Flag = false;
/*  61 */     this.reverseAlphaLayerAnimationFlag = false;
/*  62 */     this.alphaLayerAnimationReverseDoneFlag = false;
/*  63 */     this.animatedScale = 0.0F;
/*  64 */     this.animateTextAlpha = 0; this.moduleName = module.name; this.width = width;
/*     */     this.height = height;
/*     */     this.father = father;
/*     */     this.setting = (EnumSetting)setting;
/*  68 */     instance = this; } public void render(int mouseX, int mouseY, float translateDelta, float partialTicks) { GlStateManager.func_179118_c();
/*     */     
/*  70 */     Color displayTextColor = new Color(((Color)ClickGUI.instance.enumDisplayTextColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.enumDisplayTextColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.enumDisplayTextColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.enumDisplayTextColor.getValue()).getAlpha());
/*  71 */     Color nameTextColor = new Color(((Color)ClickGUI.instance.enumNameColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.enumNameColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.enumNameColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.enumNameColor.getValue()).getAlpha());
/*     */     
/*  73 */     float currentTextWidth = this.font.getStringWidth(this.setting.displayValue());
/*  74 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Minecraft) {
/*  75 */       GL11.glEnable(3553);
/*  76 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/*  77 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/*  79 */       this.mc.field_71466_p.func_175065_a(this.setting.getName(), (this.x + 5), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F), nameTextColor.getRGB(), ((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue());
/*     */       
/*  81 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*  82 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */ 
/*     */       
/*  85 */       GL11.glTranslatef((this.x + this.width - 3 - this.font.getStringWidth(this.setting.displayValue()) - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue()) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) + (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/*  86 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/*  88 */       this.mc.field_71466_p.func_175065_a(this.setting.displayValue(), (this.x + this.width - 3 - this.font
/*  89 */           .getStringWidth(this.setting.displayValue()) - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue()), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F), displayTextColor
/*  90 */           .getRGB(), ((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue());
/*     */       
/*  92 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*  93 */       GL11.glTranslatef((this.x + this.width - 3 - this.font.getStringWidth(this.setting.displayValue()) - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue()) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F - (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*  94 */       GL11.glDisable(3553);
/*     */     
/*     */     }
/*  97 */     else if (((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue()) {
/*  98 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/*  99 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/* 101 */       FontManager.drawShadow(this.setting.getName(), (this.x + 5), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), nameTextColor.getRGB());
/*     */       
/* 103 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 104 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */ 
/*     */       
/* 107 */       GL11.glTranslatef((this.x + this.width - 3 - this.font.getStringWidth(this.setting.displayValue()) - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue()) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) + (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 108 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/* 110 */       FontManager.drawShadow(this.setting.displayValue(), (this.x + this.width - 3 - this.font
/* 111 */           .getStringWidth(this.setting.displayValue()) - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue()), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), displayTextColor
/* 112 */           .getRGB());
/*     */ 
/*     */       
/* 115 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 116 */       GL11.glTranslatef((this.x + this.width - 3 - this.font.getStringWidth(this.setting.displayValue()) - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue()) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F - (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */     }
/*     */     else {
/*     */       
/* 120 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 121 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/* 123 */       FontManager.draw(this.setting.getName(), (this.x + 5), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), nameTextColor.getRGB());
/*     */       
/* 125 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 126 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */ 
/*     */       
/* 129 */       GL11.glTranslated(((this.x + this.width - 3 - this.font.getStringWidth(this.setting.displayValue()) - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue()) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) + (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth), (((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue())), 0.0D);
/* 130 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/* 132 */       FontManager.draw(this.setting.displayValue(), (this.x + this.width - 3 - this.font
/* 133 */           .getStringWidth(this.setting.displayValue()) - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue()), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), displayTextColor
/* 134 */           .getRGB());
/*     */       
/* 136 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 137 */       GL11.glTranslated(((this.x + this.width - 3 - this.font.getStringWidth(this.setting.displayValue()) - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue()) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F - (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * currentTextWidth), (((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F), 0.0D);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     if (((Boolean)ClickGUI.instance.enumDropMenu.getValue()).booleanValue()) {
/*     */       
/* 145 */       if (((Boolean)ClickGUI.instance.enumDropMenuIcon.getValue()).booleanValue()) {
/* 146 */         Color enumIconColor = new Color(((Color)ClickGUI.instance.enumDropMenuIconColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.enumDropMenuIconColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.enumDropMenuIconColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.enumDropMenuIconColor.getValue()).getAlpha());
/*     */         
/* 148 */         if (((Boolean)ClickGUI.instance.enumDropMenuIconExpandedChange.getValue()).booleanValue()) {
/* 149 */           Color enumIconExpandedColor = new Color(((Color)ClickGUI.instance.enumDropMenuIconExpandedChangedColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.enumDropMenuIconExpandedChangedColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.enumDropMenuIconExpandedChangedColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.enumDropMenuIconExpandedChangedColor.getValue()).getAlpha());
/*     */           
/* 151 */           if (((Boolean)ClickGUI.instance.enumDropMenuIconExpandedChangeAnimation.getValue()).booleanValue()) {
/*     */             
/* 153 */             storedEnumIconExpandedLoops.putIfAbsent(this.setting.getName(), Integer.valueOf(0));
/* 154 */             int animateLoops = ((Integer)storedEnumIconExpandedLoops.get(this.setting.getName())).intValue();
/*     */             
/* 156 */             int red = (int)MathUtilFuckYou.linearInterp(enumIconColor.getRed(), enumIconExpandedColor.getRed(), animateLoops);
/* 157 */             int green = (int)MathUtilFuckYou.linearInterp(enumIconColor.getGreen(), enumIconExpandedColor.getGreen(), animateLoops);
/* 158 */             int blue = (int)MathUtilFuckYou.linearInterp(enumIconColor.getBlue(), enumIconExpandedColor.getBlue(), animateLoops);
/* 159 */             int alpha = (int)MathUtilFuckYou.linearInterp(((Color)ClickGUI.instance.enumDropMenuIconColor.getValue()).getAlpha(), ((Color)ClickGUI.instance.enumDropMenuIconExpandedChangedColor.getValue()).getAlpha(), animateLoops);
/*     */ 
/*     */             
/* 162 */             enumIconColor = new Color(red, green, blue, alpha);
/*     */             
/* 164 */             if (this.expanded) {
/* 165 */               animateLoops = (int)(animateLoops + ((Float)ClickGUI.instance.enumDropMenuIconExpandedChangeAnimationSpeed.getValue()).floatValue() * 10.0F);
/*     */             } else {
/*     */               
/* 168 */               animateLoops = (int)(animateLoops - ((Float)ClickGUI.instance.enumDropMenuIconExpandedChangeAnimationSpeed.getValue()).floatValue() * 10.0F);
/*     */             } 
/*     */ 
/*     */             
/* 172 */             if (animateLoops >= 300) {
/* 173 */               animateLoops = 300;
/*     */             }
/* 175 */             if (animateLoops <= 0) {
/* 176 */               animateLoops = 0;
/*     */             }
/*     */             
/* 179 */             storedEnumIconExpandedLoops.put(this.setting.getName(), Integer.valueOf(animateLoops));
/*     */           
/*     */           }
/* 182 */           else if (this.expanded) {
/* 183 */             enumIconColor = enumIconExpandedColor;
/*     */           } 
/*     */ 
/*     */           
/* 187 */           if (((Boolean)ClickGUI.instance.enumDropMenuIconExpandedGlow.getValue()).booleanValue()) {
/* 188 */             GlStateManager.func_179118_c();
/* 189 */             RenderUtils2D.drawCustomCircle((this.x + this.width - 3) - FontManager.getEnumIconWidth() / 2.0F - ((Integer)ClickGUI.instance.enumDropMenuIconXOffset.getValue()).intValue(), this.y + this.height / 2.0F, ((Float)ClickGUI.instance.enumDropMenuIconExpandedGlowSize.getValue()).floatValue(), (new Color(enumIconExpandedColor.getRed(), enumIconExpandedColor.getGreen(), enumIconExpandedColor.getBlue(), ((Boolean)ClickGUI.instance.enumDropMenuIconExpandedChangeAnimation.getValue()).booleanValue() ? (int)(((Integer)ClickGUI.instance.enumDropMenuIconExpandedGlowAlpha.getValue()).intValue() / 300.0F * ((Integer)storedEnumIconExpandedLoops.get(this.setting.getName())).intValue()) : ((Integer)ClickGUI.instance.enumDropMenuIconExpandedGlowAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/* 190 */             GlStateManager.func_179141_d();
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 195 */         GL11.glTranslatef(((this.x + this.width - 3) - FontManager.getEnumIconWidth() / 2.0F - ((Integer)ClickGUI.instance.enumDropMenuIconXOffset.getValue()).intValue()) * (1.0F - ((Float)ClickGUI.instance.enumDropMenuIconScale.getValue()).floatValue()), (this.y + this.height / 2.0F) * (1.0F - ((Float)ClickGUI.instance.enumDropMenuIconScale.getValue()).floatValue()), 0.0F);
/* 196 */         GL11.glScalef(((Float)ClickGUI.instance.enumDropMenuIconScale.getValue()).floatValue(), ((Float)ClickGUI.instance.enumDropMenuIconScale.getValue()).floatValue(), 0.0F);
/*     */         
/* 198 */         FontManager.drawEnumIcon(this.x + this.width - 3 - FontManager.getEnumIconWidth() - ((Integer)ClickGUI.instance.enumDropMenuIconXOffset.getValue()).intValue(), (int)(this.y + this.height / 2.0F - FontManager.getIconHeight() / 4.0F), enumIconColor.getRGB());
/*     */         
/* 200 */         GL11.glScalef(1.0F / ((Float)ClickGUI.instance.enumDropMenuIconScale.getValue()).floatValue(), 1.0F / ((Float)ClickGUI.instance.enumDropMenuIconScale.getValue()).floatValue(), 0.0F);
/* 201 */         GL11.glTranslatef(((this.x + this.width - 3) - FontManager.getEnumIconWidth() / 2.0F - ((Integer)ClickGUI.instance.enumDropMenuIconXOffset.getValue()).intValue()) * (1.0F - ((Float)ClickGUI.instance.enumDropMenuIconScale.getValue()).floatValue()) * -1.0F, (this.y + this.height / 2.0F) * (1.0F - ((Float)ClickGUI.instance.enumDropMenuIconScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */       } 
/*     */       
/* 204 */       if (ClickGUI.instance.isDisabled() && HUDEditor.instance.isDisabled()) {
/* 205 */         if (((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue()) {
/* 206 */           storedEnumDropMenuOpenCloseAlphaLayerLoops.put(this.setting.getName(), Integer.valueOf(0));
/*     */         }
/* 208 */         this.animatedScale = 1.0F;
/* 209 */         this.expanded = false;
/* 210 */         anyExpanded = false;
/*     */       } 
/*     */       
/* 213 */       if (this.expanded) {
/* 214 */         anyExpanded = true;
/* 215 */         if (this.flag1) {
/* 216 */           this.lastElement = (Enum)this.setting.getValue();
/* 217 */           this.flag1 = false;
/*     */         } 
/*     */         
/* 220 */         if (((Boolean)ClickGUI.instance.enumDropMenuExpandAnimateScale.getValue()).booleanValue() && ((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue()) {
/* 221 */           this.animatedScale += this.reverseAlphaLayerAnimationFlag ? (((Float)ClickGUI.instance.enumDropMenuExpandAnimateScaleSpeed.getValue()).floatValue() * 10.0F * -1.0F) : (((Float)ClickGUI.instance.enumDropMenuExpandAnimateScaleSpeed.getValue()).floatValue() * 10.0F);
/*     */           
/* 223 */           if (this.animatedScale >= 300.0F) {
/* 224 */             this.animatedScale = 300.0F;
/*     */           }
/* 226 */           if (this.animatedScale <= 1.0F) {
/* 227 */             this.animatedScale = 1.0F;
/*     */           }
/*     */           
/* 230 */           GL11.glTranslatef((this.x + this.width + ((Integer)ClickGUI.instance.enumDropMenuXOffset.getValue()).intValue()) * (1.0F - this.animatedScale / 300.0F), this.y * (1.0F - this.animatedScale / 300.0F), 0.0F);
/* 231 */           GL11.glScalef(this.animatedScale / 300.0F, this.animatedScale / 300.0F, 0.0F);
/*     */         } 
/*     */         
/* 234 */         storedEnumDropMenuSelectLoops.putIfAbsent(this.setting.getName(), Integer.valueOf(0));
/* 235 */         if (ClickGUI.instance.enumDropMenuSelectedRectAnimation.getValue() != ClickGUI.EnumDropMenuSelectedRectAnimation.None) {
/* 236 */           int animateLoops = ((Integer)storedEnumDropMenuSelectLoops.get(this.setting.getName())).intValue();
/*     */           
/* 238 */           animateLoops = (int)(animateLoops + ((Float)ClickGUI.instance.enumDropMenuSelectedRectAnimationSpeed.getValue()).floatValue() * 10.0F);
/*     */           
/* 240 */           if (animateLoops >= 300) {
/* 241 */             animateLoops = 300;
/*     */           }
/* 243 */           storedEnumDropMenuSelectLoops.put(this.setting.getName(), Integer.valueOf(animateLoops));
/*     */         } 
/*     */         
/* 246 */         storedEnumDropMenuOpenCloseAlphaLayerLoops.putIfAbsent(this.setting.getName(), Integer.valueOf(0));
/* 247 */         if (((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue()) {
/* 248 */           int animateLoops = ((Integer)storedEnumDropMenuOpenCloseAlphaLayerLoops.get(this.setting.getName())).intValue();
/*     */           
/* 250 */           if (this.reverseAlphaLayerAnimationFlag) {
/* 251 */             animateLoops = (int)(animateLoops - ((Float)ClickGUI.instance.enumDropMenuExpandAnimateSpeed.getValue()).floatValue() * 10.0F);
/*     */           } else {
/*     */             
/* 254 */             animateLoops = (int)(animateLoops + ((Float)ClickGUI.instance.enumDropMenuExpandAnimateSpeed.getValue()).floatValue() * 10.0F);
/*     */           } 
/*     */ 
/*     */           
/* 258 */           if (animateLoops >= 300) {
/* 259 */             animateLoops = 300;
/*     */           }
/* 261 */           if (animateLoops <= 4) {
/* 262 */             animateLoops = 4;
/*     */           }
/*     */           
/* 265 */           if (((Boolean)ClickGUI.instance.enumDropMenuExpandAnimateScale.getValue()).booleanValue()) {
/* 266 */             if (this.reverseAlphaLayerAnimationFlag && animateLoops <= 5 && this.animatedScale <= 1.0F) {
/* 267 */               this.alphaLayerAnimationReverseDoneFlag = true;
/*     */             
/*     */             }
/*     */           }
/* 271 */           else if (this.reverseAlphaLayerAnimationFlag && animateLoops <= 5) {
/* 272 */             this.alphaLayerAnimationReverseDoneFlag = true;
/*     */           } 
/*     */ 
/*     */           
/* 276 */           storedEnumDropMenuOpenCloseAlphaLayerLoops.put(this.setting.getName(), Integer.valueOf(animateLoops));
/*     */         } 
/*     */         
/* 279 */         if (((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue()) {
/*     */           
/* 281 */           if (this.reverseAlphaLayerAnimationFlag) {
/* 282 */             this.animateTextAlpha = (int)(this.animateTextAlpha - ((Float)ClickGUI.instance.enumDropMenuExpandAnimateSpeed.getValue()).floatValue() * 10.0F);
/*     */           } else {
/*     */             
/* 285 */             this.animateTextAlpha = (int)(this.animateTextAlpha + ((Float)ClickGUI.instance.enumDropMenuExpandAnimateSpeed.getValue()).floatValue() * 10.0F);
/*     */           } 
/*     */           
/* 288 */           if (this.animateTextAlpha >= 300) {
/* 289 */             this.animateTextAlpha = 300;
/*     */           }
/* 291 */           if (this.animateTextAlpha <= 0) {
/* 292 */             this.animateTextAlpha = 0;
/*     */           }
/*     */           
/* 295 */           storedEnumDropMenuOpenCloseTextAlphaLayerLoops.put(this.setting.getName(), Integer.valueOf(this.animateTextAlpha));
/*     */         } 
/*     */         
/* 298 */         float selectedRectStartY = 0.0F;
/* 299 */         float selectedRectEndY = 0.0F;
/* 300 */         int counter = 0;
/* 301 */         float heightOffset = 0.0F;
/* 302 */         float startX = (this.x + this.width + ((Integer)ClickGUI.instance.enumDropMenuXOffset.getValue()).intValue());
/* 303 */         float endX = (this.x + this.width + ((Integer)ClickGUI.instance.enumDropMenuWidthFactor.getValue()).intValue() * 2 + ((Integer)ClickGUI.instance.enumDropMenuXOffset.getValue()).intValue() + this.setting.getLongestElementLength());
/* 304 */         for (Enum<?> element : (Enum[])((Enum<Enum>)this.setting.getValue()).getDeclaringClass().getEnumConstants()) {
/* 305 */           counter++;
/*     */ 
/*     */ 
/*     */           
/* 309 */           Color rectBGColor = new Color(((Color)ClickGUI.instance.enumDropMenuRectBGColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.enumDropMenuRectBGColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.enumDropMenuRectBGColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.enumDropMenuRectBGColor.getValue()).getAlpha() / 300.0F * ((Integer)storedEnumDropMenuOpenCloseAlphaLayerLoops.get(this.setting.getName())).intValue()) : ((Color)ClickGUI.instance.enumDropMenuRectBGColor.getValue()).getAlpha());
/* 310 */           Color rectColor = new Color(((Color)ClickGUI.instance.enumDropMenuRectColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.enumDropMenuRectColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.enumDropMenuRectColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.enumDropMenuRectColor.getValue()).getAlpha() / 300.0F * ((Integer)storedEnumDropMenuOpenCloseAlphaLayerLoops.get(this.setting.getName())).intValue()) : ((Color)ClickGUI.instance.enumDropMenuRectColor.getValue()).getAlpha());
/*     */           
/* 312 */           Color outlineColor = new Color(((Color)ClickGUI.instance.enumDropMenuOutlineColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.enumDropMenuOutlineColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.enumDropMenuOutlineColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.enumDropMenuOutlineColor.getValue()).getAlpha() / 300.0F * ((Integer)storedEnumDropMenuOpenCloseAlphaLayerLoops.get(this.setting.getName())).intValue()) : ((Color)ClickGUI.instance.enumDropMenuOutlineColor.getValue()).getAlpha());
/*     */ 
/*     */           
/* 315 */           float startY = this.y + heightOffset;
/* 316 */           float endY = (this.y + this.height) + heightOffset;
/*     */ 
/*     */           
/* 319 */           if (element == this.setting.getValue()) {
/* 320 */             selectedRectStartY = startY + 1.0F;
/* 321 */             selectedRectEndY = (counter == Arrays.stream(((Enum<Object>)this.setting.getValue()).getDeclaringClass().getEnumConstants()).count()) ? (endY - 1.0F - ((Integer)ClickGUI.instance.enumDropMenuRectGap.getValue()).intValue()) : (endY - ((Integer)ClickGUI.instance.enumDropMenuRectGap.getValue()).intValue());
/*     */           } 
/*     */           
/* 324 */           if (((Boolean)ClickGUI.instance.enumDropMenuShadow.getValue()).booleanValue() && counter == Arrays.stream(((Enum<Object>)this.setting.getValue()).getDeclaringClass().getEnumConstants()).count()) {
/* 325 */             RenderUtils2D.drawBetterRoundRectFade(startX, ((Boolean)ClickGUI.instance.enumDropMenuExtensions.getValue()).booleanValue() ? (this.y - ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue()) : this.y, endX, ((Boolean)ClickGUI.instance.enumDropMenuExtensions.getValue()).booleanValue() ? (endY + ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue()) : endY, ((Float)ClickGUI.instance.enumDropMenuShadowSize.getValue()).floatValue(), 40.0F, false, false, false, (new Color(0, 0, 0, ((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue() ? (int)(((Integer)ClickGUI.instance.enumDropMenuShadowAlpha.getValue()).intValue() / 300.0F * ((Integer)storedEnumDropMenuOpenCloseAlphaLayerLoops.get(this.setting.getName())).intValue()) : ((Integer)ClickGUI.instance.enumDropMenuShadowAlpha.getValue()).intValue())).getRGB());
/*     */           }
/*     */ 
/*     */           
/* 329 */           if (((Boolean)ClickGUI.instance.enumDropMenuExtensions.getValue()).booleanValue()) {
/* 330 */             if (counter == 1) {
/* 331 */               RenderUtils2D.drawRect(startX, startY - ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), endX, startY, rectBGColor.getRGB());
/* 332 */               RenderUtils2D.drawRect(startX + 1.0F, startY - ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue() + 1.0F, endX - 1.0F, startY, rectColor.getRGB());
/*     */             } 
/* 334 */             if (counter == Arrays.stream(((Enum<Object>)this.setting.getValue()).getDeclaringClass().getEnumConstants()).count()) {
/* 335 */               RenderUtils2D.drawRect(startX, endY, endX, endY + ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), rectBGColor.getRGB());
/* 336 */               RenderUtils2D.drawRect(startX + 1.0F, endY, endX - 1.0F, endY + ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue() - 1.0F, rectColor.getRGB());
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 341 */           RenderUtils2D.drawRect(startX, startY, endX, endY, rectBGColor.getRGB());
/*     */ 
/*     */           
/* 344 */           if (ClickGUI.instance.enumDropMenuOtherSideGlow.getValue() != ClickGUI.EnumDropMenuOtherSideGlowMode.None) {
/* 345 */             Color otherSideGlowColor = new Color(((Color)ClickGUI.instance.enumDropMenuOtherSideGlowColor.getValue()).getRed(), ((Color)ClickGUI.instance.enumDropMenuOtherSideGlowColor.getValue()).getGreen(), ((Color)ClickGUI.instance.enumDropMenuOtherSideGlowColor.getValue()).getBlue(), ((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.enumDropMenuOtherSideGlowColor.getValue()).getAlpha() / 300.0F * ((Integer)storedEnumDropMenuOpenCloseAlphaLayerLoops.get(this.setting.getName())).intValue()) : ((Color)ClickGUI.instance.enumDropMenuOtherSideGlowColor.getValue()).getAlpha());
/*     */             
/* 347 */             GlStateManager.func_179118_c();
/* 348 */             if (ClickGUI.instance.enumDropMenuOtherSideGlow.getValue() == ClickGUI.EnumDropMenuOtherSideGlowMode.Right || ClickGUI.instance.enumDropMenuOtherSideGlow.getValue() == ClickGUI.EnumDropMenuOtherSideGlowMode.Both) {
/* 349 */               if (((Boolean)ClickGUI.instance.enumDropMenuExtensions.getValue()).booleanValue()) {
/* 350 */                 if (counter == 1) {
/* 351 */                   RenderUtils2D.drawCustomRect(endX - ((Float)ClickGUI.instance.enumDropMenuOtherSideGlowWidth.getValue()).floatValue(), startY - ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), endX, startY, otherSideGlowColor.getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), otherSideGlowColor.getRGB());
/*     */                 }
/* 353 */                 if (counter == Arrays.stream(((Enum<Object>)this.setting.getValue()).getDeclaringClass().getEnumConstants()).count()) {
/* 354 */                   RenderUtils2D.drawCustomRect(endX - ((Float)ClickGUI.instance.enumDropMenuOtherSideGlowWidth.getValue()).floatValue(), endY, endX, endY + ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), otherSideGlowColor.getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), otherSideGlowColor.getRGB());
/*     */                 }
/*     */               } 
/* 357 */               RenderUtils2D.drawCustomRect(endX - ((Float)ClickGUI.instance.enumDropMenuOtherSideGlowWidth.getValue()).floatValue(), startY, endX, endY, otherSideGlowColor.getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), otherSideGlowColor.getRGB());
/*     */             } 
/* 359 */             if (ClickGUI.instance.enumDropMenuOtherSideGlow.getValue() == ClickGUI.EnumDropMenuOtherSideGlowMode.Left || ClickGUI.instance.enumDropMenuOtherSideGlow.getValue() == ClickGUI.EnumDropMenuOtherSideGlowMode.Both) {
/* 360 */               if (((Boolean)ClickGUI.instance.enumDropMenuExtensions.getValue()).booleanValue()) {
/* 361 */                 if (counter == 1) {
/* 362 */                   RenderUtils2D.drawCustomRect(startX, startY - ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), startX + ((Float)ClickGUI.instance.enumDropMenuOtherSideGlowWidth.getValue()).floatValue(), startY, (new Color(0, 0, 0, 0)).getRGB(), otherSideGlowColor.getRGB(), otherSideGlowColor.getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*     */                 }
/* 364 */                 if (counter == Arrays.stream(((Enum<Object>)this.setting.getValue()).getDeclaringClass().getEnumConstants()).count()) {
/* 365 */                   RenderUtils2D.drawCustomRect(startX, endY, startX + ((Float)ClickGUI.instance.enumDropMenuOtherSideGlowWidth.getValue()).floatValue(), endY + ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), (new Color(0, 0, 0, 0)).getRGB(), otherSideGlowColor.getRGB(), otherSideGlowColor.getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*     */                 }
/*     */               } 
/* 368 */               RenderUtils2D.drawCustomRect(startX, startY, startX + ((Float)ClickGUI.instance.enumDropMenuOtherSideGlowWidth.getValue()).floatValue(), endY, (new Color(0, 0, 0, 0)).getRGB(), otherSideGlowColor.getRGB(), otherSideGlowColor.getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*     */             } 
/* 370 */             GlStateManager.func_179141_d();
/*     */           } 
/*     */ 
/*     */           
/* 374 */           RenderUtils2D.drawRect(startX + 1.0F, startY + 1.0F, endX - 1.0F, (counter == Arrays.stream(((Enum<Object>)this.setting.getValue()).getDeclaringClass().getEnumConstants()).count()) ? (endY - 1.0F - ((Integer)ClickGUI.instance.enumDropMenuRectGap.getValue()).intValue()) : (endY - ((Integer)ClickGUI.instance.enumDropMenuRectGap.getValue()).intValue()), rectColor.getRGB());
/*     */ 
/*     */ 
/*     */           
/* 378 */           if (((Boolean)ClickGUI.instance.enumDropMenuSideBar.getValue()).booleanValue()) {
/* 379 */             if (((Boolean)ClickGUI.instance.enumDropMenuExtensions.getValue()).booleanValue()) {
/* 380 */               if (counter == 1) {
/* 381 */                 RenderUtils2D.drawCustomLine(startX + ((Float)ClickGUI.instance.enumDropMenuSideBarWidth.getValue()).floatValue() / 4.0F, startY - ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), startX + ((Float)ClickGUI.instance.enumDropMenuSideBarWidth.getValue()).floatValue() / 4.0F, startY, ((Float)ClickGUI.instance.enumDropMenuSideBarWidth.getValue()).floatValue(), outlineColor.getRGB(), outlineColor.getRGB());
/*     */               }
/* 383 */               if (counter == Arrays.stream(((Enum<Object>)this.setting.getValue()).getDeclaringClass().getEnumConstants()).count()) {
/* 384 */                 RenderUtils2D.drawCustomLine(startX + ((Float)ClickGUI.instance.enumDropMenuSideBarWidth.getValue()).floatValue() / 4.0F, endY, startX + ((Float)ClickGUI.instance.enumDropMenuSideBarWidth.getValue()).floatValue() / 4.0F, endY + ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), ((Float)ClickGUI.instance.enumDropMenuSideBarWidth.getValue()).floatValue(), outlineColor.getRGB(), outlineColor.getRGB());
/*     */               }
/*     */             } 
/* 387 */             RenderUtils2D.drawCustomLine(startX + ((Float)ClickGUI.instance.enumDropMenuSideBarWidth.getValue()).floatValue() / 4.0F, startY, startX + ((Float)ClickGUI.instance.enumDropMenuSideBarWidth.getValue()).floatValue() / 4.0F, endY, ((Float)ClickGUI.instance.enumDropMenuSideBarWidth.getValue()).floatValue(), outlineColor.getRGB(), outlineColor.getRGB());
/*     */           } 
/*     */ 
/*     */           
/* 391 */           if (((Boolean)ClickGUI.instance.enumDropMenuSideGlow.getValue()).booleanValue()) {
/* 392 */             Color sideGlowColor = new Color(((Color)ClickGUI.instance.enumDropMenuSideGlowColor.getValue()).getRed(), ((Color)ClickGUI.instance.enumDropMenuSideGlowColor.getValue()).getGreen(), ((Color)ClickGUI.instance.enumDropMenuSideGlowColor.getValue()).getBlue(), ((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.enumDropMenuSideGlowColor.getValue()).getAlpha() / 300.0F * ((Integer)storedEnumDropMenuOpenCloseAlphaLayerLoops.get(this.setting.getName())).intValue()) : ((Color)ClickGUI.instance.enumDropMenuSideGlowColor.getValue()).getAlpha());
/*     */             
/* 394 */             GlStateManager.func_179118_c();
/* 395 */             if (((Boolean)ClickGUI.instance.enumDropMenuExtensions.getValue()).booleanValue()) {
/* 396 */               if (counter == 1) {
/* 397 */                 RenderUtils2D.drawCustomRect(startX, startY - ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), startX + ((Float)ClickGUI.instance.enumDropMenuSideGlowWidth.getValue()).floatValue(), startY, (new Color(0, 0, 0, 0)).getRGB(), sideGlowColor.getRGB(), sideGlowColor.getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*     */               }
/* 399 */               if (counter == Arrays.stream(((Enum<Object>)this.setting.getValue()).getDeclaringClass().getEnumConstants()).count()) {
/* 400 */                 RenderUtils2D.drawCustomRect(startX, endY, startX + ((Float)ClickGUI.instance.enumDropMenuSideGlowWidth.getValue()).floatValue(), endY + ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), (new Color(0, 0, 0, 0)).getRGB(), sideGlowColor.getRGB(), sideGlowColor.getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*     */               }
/*     */             } 
/* 403 */             RenderUtils2D.drawCustomRect(startX, startY, startX + ((Float)ClickGUI.instance.enumDropMenuSideGlowWidth.getValue()).floatValue(), endY, (new Color(0, 0, 0, 0)).getRGB(), sideGlowColor.getRGB(), sideGlowColor.getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/* 404 */             GlStateManager.func_179141_d();
/*     */           } 
/*     */ 
/*     */           
/* 408 */           if (((Boolean)ClickGUI.instance.enumDropMenuTopBottomGradients.getValue()).booleanValue()) {
/* 409 */             Color topBottomGradientColor = new Color(((Color)ClickGUI.instance.enumDropMenuTopBottomGradientsColor.getValue()).getRed(), ((Color)ClickGUI.instance.enumDropMenuTopBottomGradientsColor.getValue()).getGreen(), ((Color)ClickGUI.instance.enumDropMenuTopBottomGradientsColor.getValue()).getBlue(), ((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.enumDropMenuTopBottomGradientsColor.getValue()).getAlpha() / 300.0F * ((Integer)storedEnumDropMenuOpenCloseAlphaLayerLoops.get(this.setting.getName())).intValue()) : ((Color)ClickGUI.instance.enumDropMenuTopBottomGradientsColor.getValue()).getAlpha());
/*     */             
/* 411 */             GlStateManager.func_179118_c();
/* 412 */             if (((Boolean)ClickGUI.instance.enumDropMenuExtensions.getValue()).booleanValue()) {
/* 413 */               if (counter == 1) {
/* 414 */                 RenderUtils2D.drawCustomRect(startX, startY - ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), endX, startY - ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue() + ((Float)ClickGUI.instance.enumDropMenuTopBottomGradientsHeight.getValue()).floatValue(), topBottomGradientColor.getRGB(), topBottomGradientColor.getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*     */               }
/* 416 */               if (counter == Arrays.stream(((Enum<Object>)this.setting.getValue()).getDeclaringClass().getEnumConstants()).count()) {
/* 417 */                 RenderUtils2D.drawCustomRect(startX, endY + ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue() - ((Float)ClickGUI.instance.enumDropMenuTopBottomGradientsHeight.getValue()).floatValue(), endX, endY + ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), topBottomGradientColor.getRGB(), topBottomGradientColor.getRGB());
/*     */               }
/*     */             } else {
/*     */               
/* 421 */               if (counter == 1) {
/* 422 */                 RenderUtils2D.drawCustomRect(startX, startY, endX, startY + ((Float)ClickGUI.instance.enumDropMenuTopBottomGradientsHeight.getValue()).floatValue(), topBottomGradientColor.getRGB(), topBottomGradientColor.getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*     */               }
/* 424 */               if (counter == Arrays.stream(((Enum<Object>)this.setting.getValue()).getDeclaringClass().getEnumConstants()).count()) {
/* 425 */                 RenderUtils2D.drawCustomRect(startX, endY - ((Float)ClickGUI.instance.enumDropMenuTopBottomGradientsHeight.getValue()).floatValue(), endX, endY, (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), topBottomGradientColor.getRGB(), topBottomGradientColor.getRGB());
/*     */               }
/*     */             } 
/* 428 */             GlStateManager.func_179141_d();
/*     */           } 
/*     */ 
/*     */           
/* 432 */           if (((Boolean)ClickGUI.instance.enumDropMenuOutline.getValue()).booleanValue()) {
/* 433 */             if (((Boolean)ClickGUI.instance.enumDropMenuExtensions.getValue()).booleanValue()) {
/* 434 */               if (counter == 1) {
/* 435 */                 RenderUtils2D.drawCustomLine(startX, startY - ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), endX, startY - ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue(), outlineColor.getRGB(), outlineColor.getRGB());
/* 436 */                 RenderUtils2D.drawCustomLine(startX, startY, startX, startY - ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue(), outlineColor.getRGB(), outlineColor.getRGB());
/* 437 */                 RenderUtils2D.drawCustomLine(endX, startY, endX, startY - ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue(), outlineColor.getRGB(), outlineColor.getRGB());
/*     */               } 
/* 439 */               if (counter == Arrays.stream(((Enum<Object>)this.setting.getValue()).getDeclaringClass().getEnumConstants()).count()) {
/* 440 */                 RenderUtils2D.drawCustomLine(startX, endY + ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), endX, endY + ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue(), outlineColor.getRGB(), outlineColor.getRGB());
/* 441 */                 RenderUtils2D.drawCustomLine(startX, startY + this.height, startX, endY + ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue(), outlineColor.getRGB(), outlineColor.getRGB());
/* 442 */                 RenderUtils2D.drawCustomLine(endX, startY + this.height, endX, endY + ((Float)ClickGUI.instance.enumDropMenuExtensionsHeight.getValue()).floatValue(), ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue(), outlineColor.getRGB(), outlineColor.getRGB());
/*     */               } 
/*     */             } else {
/*     */               
/* 446 */               if (counter == 1) {
/* 447 */                 RenderUtils2D.drawCustomLine(startX, startY, endX, startY, ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue(), outlineColor.getRGB(), outlineColor.getRGB());
/*     */               }
/* 449 */               if (counter == Arrays.stream(((Enum<Object>)this.setting.getValue()).getDeclaringClass().getEnumConstants()).count()) {
/* 450 */                 RenderUtils2D.drawCustomLine(startX, endY, endX, endY, ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue(), outlineColor.getRGB(), outlineColor.getRGB());
/*     */               }
/*     */             } 
/* 453 */             RenderUtils2D.drawCustomLine(startX, startY, startX, endY, ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue(), outlineColor.getRGB(), outlineColor.getRGB());
/* 454 */             RenderUtils2D.drawCustomLine(endX, startY, endX, endY, ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue(), outlineColor.getRGB(), outlineColor.getRGB());
/*     */           } 
/*     */ 
/*     */           
/* 458 */           if (mouseX >= Math.min(startX, endX) && mouseX <= Math.max(startX, endX) && mouseY >= Math.min(startY, endY - (((Integer)ClickGUI.instance.enumDropMenuRectGap.getValue()).intValue() + 1)) && mouseY <= Math.max(startY, endY - (((Integer)ClickGUI.instance.enumDropMenuRectGap.getValue()).intValue() + 1)) && Mouse.getEventButton() == 0 && Mouse.isButtonDown(0)) {
/* 459 */             this.setting.setByName(element.name());
/*     */           }
/*     */ 
/*     */           
/* 463 */           if (this.lastElement != this.setting.getValue()) {
/* 464 */             if (!this.flag) {
/* 465 */               storedEnumDropMenuSelectLoops.put(this.setting.getName(), Integer.valueOf(0));
/* 466 */               storedEnumDropMenuExpandRectLoops.put(this.setting.getName() + element.name(), Integer.valueOf(1));
/* 467 */               this.flag = true;
/*     */             } 
/*     */ 
/*     */             
/* 471 */             if (element == this.lastElement) {
/* 472 */               lastSelectedRectStartY = startY - 1.0F;
/* 473 */               lastSelectedRectEndY = (counter == Arrays.stream(((Enum<Object>)this.setting.getValue()).getDeclaringClass().getEnumConstants()).count()) ? (endY - 1.0F - ((Integer)ClickGUI.instance.enumDropMenuRectGap.getValue()).intValue()) : (endY - ((Integer)ClickGUI.instance.enumDropMenuRectGap.getValue()).intValue());
/* 474 */               this.lastElement = (Enum)this.setting.getValue();
/*     */               
/* 476 */               this.flag = false;
/*     */             } else {
/*     */               
/* 479 */               lastSelectedRectStartY = selectedRectStartY;
/* 480 */               lastSelectedRectEndY = selectedRectEndY;
/*     */             } 
/*     */           } 
/*     */           
/* 484 */           heightOffset += this.height;
/*     */         } 
/*     */ 
/*     */         
/* 488 */         if (((Boolean)ClickGUI.instance.enumDropMenuSelectedRect.getValue()).booleanValue()) {
/* 489 */           Color selectedRectColor = new Color(((Color)ClickGUI.instance.enumDropMenuSelectedRectColor.getValue()).getRed(), ((Color)ClickGUI.instance.enumDropMenuSelectedRectColor.getValue()).getGreen(), ((Color)ClickGUI.instance.enumDropMenuSelectedRectColor.getValue()).getBlue(), ((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.enumDropMenuSelectedRectColor.getValue()).getAlpha() / 300.0F * ((Integer)storedEnumDropMenuOpenCloseAlphaLayerLoops.get(this.setting.getName())).intValue()) : ((Color)ClickGUI.instance.enumDropMenuSelectedRectColor.getValue()).getAlpha());
/*     */ 
/*     */           
/* 492 */           if (ClickGUI.instance.enumDropMenuSelectedRectAnimation.getValue() == ClickGUI.EnumDropMenuSelectedRectAnimation.Slide) {
/* 493 */             if (lastSelectedRectStartY == -999.0F || (lastSelectedRectStartY != selectedRectEndY && this.flag4)) {
/* 494 */               lastSelectedRectStartY = selectedRectStartY;
/* 495 */               this.flag4 = false;
/*     */             } 
/* 497 */             if (lastSelectedRectEndY == -999.0F || (lastSelectedRectEndY != selectedRectEndY && this.flag2)) {
/* 498 */               lastSelectedRectEndY = selectedRectEndY;
/* 499 */               this.flag2 = false;
/*     */             } 
/*     */ 
/*     */             
/* 503 */             float tempStartY = lastSelectedRectStartY + ((Integer)storedEnumDropMenuSelectLoops.get(this.setting.getName())).intValue() * (selectedRectStartY - lastSelectedRectStartY) / 300.0F;
/* 504 */             float tempEndY = lastSelectedRectEndY + ((Integer)storedEnumDropMenuSelectLoops.get(this.setting.getName())).intValue() * (selectedRectEndY - lastSelectedRectEndY) / 300.0F;
/*     */             
/* 506 */             if (((Boolean)ClickGUI.instance.enumDropMenuSelectedRectRounded.getValue()).booleanValue()) {
/* 507 */               RenderUtils2D.drawRoundedRect(startX + (((Boolean)ClickGUI.instance.enumDropMenuSideBar.getValue()).booleanValue() ? (1.0F + ((Float)ClickGUI.instance.enumDropMenuSideBarWidth.getValue()).floatValue() / 2.0F + 0.5F) : (((Boolean)ClickGUI.instance.enumDropMenuOutline.getValue()).booleanValue() ? (1.0F + ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue() / 2.0F + 0.5F) : 1.0F)), tempStartY, ((Float)ClickGUI.instance.enumDropMenuSelectedRectRoundedRadius.getValue()).floatValue(), endX - (((Boolean)ClickGUI.instance.enumDropMenuOutline.getValue()).booleanValue() ? (1.0F + ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue() / 2.0F + 0.5F) : 1.0F), tempEndY, false, true, true, true, true, selectedRectColor.getRGB());
/*     */             } else {
/*     */               
/* 510 */               RenderUtils2D.drawRect(startX + (((Boolean)ClickGUI.instance.enumDropMenuSideBar.getValue()).booleanValue() ? (1.0F + ((Float)ClickGUI.instance.enumDropMenuSideBarWidth.getValue()).floatValue() / 2.0F + 0.5F) : (((Boolean)ClickGUI.instance.enumDropMenuOutline.getValue()).booleanValue() ? (1.0F + ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue() / 2.0F + 0.5F) : 1.0F)), tempStartY, endX - (((Boolean)ClickGUI.instance.enumDropMenuOutline.getValue()).booleanValue() ? (1.0F + ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue() / 2.0F + 0.5F) : 1.0F), tempEndY, selectedRectColor.getRGB());
/*     */             
/*     */             }
/*     */ 
/*     */           
/*     */           }
/* 516 */           else if (ClickGUI.instance.enumDropMenuSelectedRectAnimation.getValue() != ClickGUI.EnumDropMenuSelectedRectAnimation.Slide && ClickGUI.instance.enumDropMenuSelectedRectAnimation.getValue() != ClickGUI.EnumDropMenuSelectedRectAnimation.None) {
/* 517 */             float heightOffset2 = 0.0F;
/* 518 */             int counter2 = 0;
/* 519 */             for (Enum<?> element : (Enum[])((Enum<Enum>)this.setting.getValue()).getDeclaringClass().getEnumConstants()) {
/* 520 */               counter2++;
/* 521 */               float startY = this.y + heightOffset2;
/* 522 */               float endY = (this.y + this.height) + heightOffset2;
/*     */ 
/*     */               
/* 525 */               storedEnumDropMenuSelectLoops.putIfAbsent(this.setting.getName() + element.name(), Integer.valueOf(0));
/*     */               
/* 527 */               int animateLoops = ((Integer)storedEnumDropMenuSelectLoops.get(this.setting.getName() + element.name())).intValue();
/*     */               
/* 529 */               animateLoops = (int)(animateLoops + ((element == this.setting.getValue()) ? (((Float)ClickGUI.instance.enumDropMenuSelectedRectAnimationSpeed.getValue()).floatValue() * 10.0F) : (((Float)ClickGUI.instance.enumDropMenuSelectedRectAnimationSpeed.getValue()).floatValue() * 10.0F * -1.0F)));
/*     */               
/* 531 */               if (animateLoops >= 300) {
/* 532 */                 animateLoops = 300;
/*     */               }
/* 534 */               if (animateLoops <= 0) {
/* 535 */                 animateLoops = 0;
/*     */               }
/* 537 */               storedEnumDropMenuSelectLoops.put(this.setting.getName() + element.name(), Integer.valueOf(animateLoops));
/*     */               
/* 539 */               if (ClickGUI.instance.enumDropMenuSelectedRectAnimation.getValue() == ClickGUI.EnumDropMenuSelectedRectAnimation.Alpha || ClickGUI.instance.enumDropMenuSelectedRectAnimation.getValue() == ClickGUI.EnumDropMenuSelectedRectAnimation.AlpScale) {
/* 540 */                 selectedRectColor = new Color(selectedRectColor.getRed(), selectedRectColor.getGreen(), selectedRectColor.getBlue(), (int)((((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.enumDropMenuSelectedRectColor.getValue()).getAlpha() / 300.0F * ((Integer)storedEnumDropMenuOpenCloseAlphaLayerLoops.get(this.setting.getName())).intValue()) : ((Color)ClickGUI.instance.enumDropMenuSelectedRectColor.getValue()).getAlpha()) / 300.0F * ((Integer)storedEnumDropMenuSelectLoops.get(this.setting.getName() + element.name())).intValue()));
/*     */               }
/*     */               
/* 543 */               float animatedSelectedRectStartX = startX + (((Boolean)ClickGUI.instance.enumDropMenuSideBar.getValue()).booleanValue() ? (1.0F + ((Float)ClickGUI.instance.enumDropMenuSideBarWidth.getValue()).floatValue() / 2.0F + 0.5F) : (((Boolean)ClickGUI.instance.enumDropMenuOutline.getValue()).booleanValue() ? (1.0F + ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue() / 2.0F + 0.5F) : 1.0F));
/* 544 */               float animatedSelectedRectEndX = endX - (((Boolean)ClickGUI.instance.enumDropMenuOutline.getValue()).booleanValue() ? (1.0F + ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue() / 2.0F + 0.5F) : 1.0F);
/*     */               
/* 546 */               float animatedSelectedRectStartY = startY + 1.0F;
/* 547 */               float animatedSelectedRectEndY = (counter2 == Arrays.stream(((Enum<Object>)this.setting.getValue()).getDeclaringClass().getEnumConstants()).count()) ? (endY - 1.0F - ((Integer)ClickGUI.instance.enumDropMenuRectGap.getValue()).intValue()) : (endY - ((Integer)ClickGUI.instance.enumDropMenuRectGap.getValue()).intValue());
/*     */ 
/*     */               
/* 550 */               animatedSelectedRectStartX = animatedSelectedRectStartX + (endX - startX) / 2.0F - (endX - startX) / 2.0F / 300.0F * ((Integer)storedEnumDropMenuSelectLoops.get(this.setting.getName() + element.name())).intValue();
/* 551 */               animatedSelectedRectEndX = animatedSelectedRectEndX - (endX - startX) / 2.0F + (endX - startX) / 2.0F / 300.0F * ((Integer)storedEnumDropMenuSelectLoops.get(this.setting.getName() + element.name())).intValue();
/* 552 */               animatedSelectedRectStartY = animatedSelectedRectStartY + (animatedSelectedRectEndY - animatedSelectedRectStartY) / 2.0F - (animatedSelectedRectEndY - animatedSelectedRectStartY) / 2.0F / 300.0F * ((Integer)storedEnumDropMenuSelectLoops.get(this.setting.getName() + element.name())).intValue();
/* 553 */               animatedSelectedRectEndY = animatedSelectedRectEndY - (animatedSelectedRectEndY - animatedSelectedRectStartY) / 2.0F + (animatedSelectedRectEndY - animatedSelectedRectStartY) / 2.0F / 300.0F * ((Integer)storedEnumDropMenuSelectLoops.get(this.setting.getName() + element.name())).intValue();
/*     */               
/* 555 */               if ((ClickGUI.instance.enumDropMenuSelectedRectAnimation.getValue() == ClickGUI.EnumDropMenuSelectedRectAnimation.Scale || ClickGUI.instance.enumDropMenuSelectedRectAnimation.getValue() == ClickGUI.EnumDropMenuSelectedRectAnimation.AlpScale) && ((Integer)storedEnumDropMenuSelectLoops.get(this.setting.getName() + element.name())).intValue() == 0) {
/* 556 */                 animatedSelectedRectStartX += (animatedSelectedRectEndX - animatedSelectedRectStartX) / 2.0F;
/* 557 */                 animatedSelectedRectEndX -= (animatedSelectedRectEndX - animatedSelectedRectStartX) / 2.0F;
/* 558 */                 animatedSelectedRectStartY += (animatedSelectedRectEndY - animatedSelectedRectStartY) / 2.0F;
/* 559 */                 animatedSelectedRectEndY -= (animatedSelectedRectEndY - animatedSelectedRectStartY) / 2.0F;
/*     */               } 
/*     */ 
/*     */               
/* 563 */               GlStateManager.func_179118_c();
/* 564 */               if (((Boolean)ClickGUI.instance.enumDropMenuSelectedRectRounded.getValue()).booleanValue()) {
/* 565 */                 RenderUtils2D.drawRoundedRect(animatedSelectedRectStartX, animatedSelectedRectStartY, ((Float)ClickGUI.instance.enumDropMenuSelectedRectRoundedRadius.getValue()).floatValue(), animatedSelectedRectEndX, animatedSelectedRectEndY, false, true, true, true, true, selectedRectColor.getRGB());
/*     */               } else {
/*     */                 
/* 568 */                 RenderUtils2D.drawRect(animatedSelectedRectStartX, animatedSelectedRectStartY, animatedSelectedRectEndX, animatedSelectedRectEndY, selectedRectColor.getRGB());
/*     */               } 
/* 570 */               GlStateManager.func_179141_d();
/*     */               
/* 572 */               heightOffset2 += this.height;
/*     */             }
/*     */           
/* 575 */           } else if (ClickGUI.instance.enumDropMenuSelectedRectAnimation.getValue() == ClickGUI.EnumDropMenuSelectedRectAnimation.None) {
/*     */ 
/*     */             
/* 578 */             if (((Boolean)ClickGUI.instance.enumDropMenuSelectedRectRounded.getValue()).booleanValue()) {
/* 579 */               RenderUtils2D.drawRoundedRect(startX + (((Boolean)ClickGUI.instance.enumDropMenuSideBar.getValue()).booleanValue() ? (1.0F + ((Float)ClickGUI.instance.enumDropMenuSideBarWidth.getValue()).floatValue() / 2.0F + 0.5F) : (((Boolean)ClickGUI.instance.enumDropMenuOutline.getValue()).booleanValue() ? (1.0F + ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue() / 2.0F + 0.5F) : 1.0F)), selectedRectStartY, ((Float)ClickGUI.instance.enumDropMenuSelectedRectRoundedRadius.getValue()).floatValue(), endX - (((Boolean)ClickGUI.instance.enumDropMenuOutline.getValue()).booleanValue() ? (1.0F + ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue() / 2.0F + 0.5F) : 1.0F), selectedRectEndY, false, true, true, true, true, selectedRectColor.getRGB());
/*     */             } else {
/*     */               
/* 582 */               RenderUtils2D.drawRect(startX + (((Boolean)ClickGUI.instance.enumDropMenuSideBar.getValue()).booleanValue() ? (1.0F + ((Float)ClickGUI.instance.enumDropMenuSideBarWidth.getValue()).floatValue() / 2.0F + 0.5F) : (((Boolean)ClickGUI.instance.enumDropMenuOutline.getValue()).booleanValue() ? (1.0F + ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue() / 2.0F + 0.5F) : 1.0F)), selectedRectStartY, endX - (((Boolean)ClickGUI.instance.enumDropMenuOutline.getValue()).booleanValue() ? (1.0F + ((Float)ClickGUI.instance.enumDropMenuOutlineWidth.getValue()).floatValue() / 2.0F + 0.5F) : 1.0F), selectedRectEndY, selectedRectColor.getRGB());
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 591 */         if (((Boolean)ClickGUI.instance.enumDropMenuSelectedSideRect.getValue()).booleanValue()) {
/* 592 */           Color selectedSideRectColor = new Color(((Color)ClickGUI.instance.enumDropMenuSelectedSideRectColor.getValue()).getRed(), ((Color)ClickGUI.instance.enumDropMenuSelectedSideRectColor.getValue()).getGreen(), ((Color)ClickGUI.instance.enumDropMenuSelectedSideRectColor.getValue()).getBlue(), ((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.enumDropMenuSelectedSideRectColor.getValue()).getAlpha() / 300.0F * ((Integer)storedEnumDropMenuOpenCloseAlphaLayerLoops.get(this.setting.getName())).intValue()) : ((Color)ClickGUI.instance.enumDropMenuSelectedSideRectColor.getValue()).getAlpha());
/*     */           
/* 594 */           float f = 0.0F;
/* 595 */           int counter3 = 0;
/* 596 */           for (Enum<?> element : (Enum[])((Enum<Enum>)this.setting.getValue()).getDeclaringClass().getEnumConstants()) {
/* 597 */             float f1, f2; counter3++;
/* 598 */             float startY = this.y + f;
/* 599 */             float endY = (this.y + this.height) + f;
/*     */             
/* 601 */             storedEnumDropMenuSelectSideRectLoops.putIfAbsent(this.setting.getName() + element.name(), Integer.valueOf(0));
/*     */             
/* 603 */             int animateLoops = ((Integer)storedEnumDropMenuSelectSideRectLoops.get(this.setting.getName() + element.name())).intValue();
/*     */             
/* 605 */             animateLoops = (int)(animateLoops + ((element == this.setting.getValue()) ? (((Float)ClickGUI.instance.enumDropMenuSelectedSideRectAnimationSpeed.getValue()).floatValue() * 10.0F) : (((Float)ClickGUI.instance.enumDropMenuSelectedSideRectAnimationSpeed.getValue()).floatValue() * 10.0F * -1.0F)));
/*     */             
/* 607 */             if (animateLoops >= 300) {
/* 608 */               animateLoops = 300;
/*     */             }
/* 610 */             if (animateLoops <= 0) {
/* 611 */               animateLoops = 0;
/*     */             }
/* 613 */             storedEnumDropMenuSelectSideRectLoops.put(this.setting.getName() + element.name(), Integer.valueOf(animateLoops));
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 618 */             float selectedSideRectStartY = startY + (endY - startY) / 2.0F - ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectHeight.getValue()).floatValue() / 2.0F;
/* 619 */             float selectedSideRectEndY = endY - (endY - startY) / 2.0F + ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectHeight.getValue()).floatValue() / 2.0F;
/*     */             
/* 621 */             if (ClickGUI.instance.enumDropMenuSelectedSideRectSide.getValue() == ClickGUI.EnumDropMenuSelectedSideRectSide.Right) {
/* 622 */               f1 = endX - ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectWidth.getValue()).floatValue() - ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectXOffset.getValue()).floatValue();
/* 623 */               f2 = endX - ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectXOffset.getValue()).floatValue();
/*     */             } else {
/*     */               
/* 626 */               f1 = startX + ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectXOffset.getValue()).floatValue();
/* 627 */               f2 = startX + ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectWidth.getValue()).floatValue() + ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectXOffset.getValue()).floatValue();
/*     */             } 
/*     */             
/* 630 */             if (((Boolean)ClickGUI.instance.enumDropMenuSelectedSideRectAnimation.getValue()).booleanValue()) {
/* 631 */               selectedSideRectStartY = startY + (endY - startY) / 2.0F - ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectHeight.getValue()).floatValue() / 2.0F / 300.0F * ((Integer)storedEnumDropMenuSelectSideRectLoops.get(this.setting.getName() + element.name())).intValue();
/* 632 */               selectedSideRectEndY = endY - (endY - startY) / 2.0F + ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectHeight.getValue()).floatValue() / 2.0F / 300.0F * ((Integer)storedEnumDropMenuSelectSideRectLoops.get(this.setting.getName() + element.name())).intValue();
/* 633 */               if (ClickGUI.instance.enumDropMenuSelectedSideRectSide.getValue() == ClickGUI.EnumDropMenuSelectedSideRectSide.Right) {
/* 634 */                 f1 = endX - ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectWidth.getValue()).floatValue() / 300.0F * ((Integer)storedEnumDropMenuSelectSideRectLoops.get(this.setting.getName() + element.name())).intValue();
/*     */               } else {
/*     */                 
/* 637 */                 f2 = startX + ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectXOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectWidth.getValue()).floatValue() / 300.0F * ((Integer)storedEnumDropMenuSelectSideRectLoops.get(this.setting.getName() + element.name())).intValue();
/*     */               } 
/*     */               
/* 640 */               if (((Boolean)ClickGUI.instance.enumDropMenuSelectedSideRectRounded.getValue()).booleanValue()) {
/* 641 */                 if (((Boolean)ClickGUI.instance.enumDropMenuSelectSideRectFull.getValue()).booleanValue()) {
/* 642 */                   RenderUtils2D.drawRoundedRect(f1, selectedSideRectStartY, ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectRoundedRadius.getValue()).floatValue(), f2, selectedSideRectEndY, false, true, true, true, true, selectedSideRectColor.getRGB());
/*     */                 } else {
/*     */                   
/* 645 */                   RenderUtils2D.drawCustomGradientRoundedRectModuleEnableMode(f1, selectedSideRectStartY, f2, selectedSideRectEndY, ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectRoundedRadius.getValue()).floatValue(), (ClickGUI.instance.enumDropMenuSelectedSideRectSide.getValue() == ClickGUI.EnumDropMenuSelectedSideRectSide.Right), selectedSideRectColor.getRGB(), selectedSideRectColor.getRGB());
/*     */                 } 
/*     */               } else {
/*     */                 
/* 649 */                 RenderUtils2D.drawRect(f1, selectedSideRectStartY, f2, selectedSideRectEndY, selectedSideRectColor.getRGB());
/*     */               
/*     */               }
/*     */             
/*     */             }
/* 654 */             else if (element == this.setting.getValue()) {
/* 655 */               if (((Boolean)ClickGUI.instance.enumDropMenuSelectedSideRectRounded.getValue()).booleanValue()) {
/* 656 */                 if (((Boolean)ClickGUI.instance.enumDropMenuSelectSideRectFull.getValue()).booleanValue()) {
/* 657 */                   RenderUtils2D.drawRoundedRect(f1, selectedSideRectStartY, ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectRoundedRadius.getValue()).floatValue(), f2, selectedSideRectEndY, false, true, true, true, true, selectedSideRectColor.getRGB());
/*     */                 } else {
/*     */                   
/* 660 */                   RenderUtils2D.drawCustomGradientRoundedRectModuleEnableMode(f1, selectedSideRectStartY, f2, selectedSideRectEndY, ((Float)ClickGUI.instance.enumDropMenuSelectedSideRectRoundedRadius.getValue()).floatValue(), (ClickGUI.instance.enumDropMenuSelectedSideRectSide.getValue() == ClickGUI.EnumDropMenuSelectedSideRectSide.Right), selectedSideRectColor.getRGB(), selectedSideRectColor.getRGB());
/*     */                 } 
/*     */               } else {
/*     */                 
/* 664 */                 RenderUtils2D.drawRect(f1, selectedSideRectStartY, f2, selectedSideRectEndY, selectedSideRectColor.getRGB());
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/* 669 */             f += this.height;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 674 */         if (((Boolean)ClickGUI.instance.enumDropMenuSelectedSideGlow.getValue()).booleanValue()) {
/* 675 */           Color selectedSideSideGlowColor = new Color(((Color)ClickGUI.instance.enumDropMenuSelectedSideGlowColor.getValue()).getRed(), ((Color)ClickGUI.instance.enumDropMenuSelectedSideGlowColor.getValue()).getGreen(), ((Color)ClickGUI.instance.enumDropMenuSelectedSideGlowColor.getValue()).getBlue(), ((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.enumDropMenuSelectedSideGlowColor.getValue()).getAlpha() / 300.0F * ((Integer)storedEnumDropMenuOpenCloseAlphaLayerLoops.get(this.setting.getName())).intValue()) : ((Color)ClickGUI.instance.enumDropMenuSelectedSideGlowColor.getValue()).getAlpha());
/*     */           
/* 677 */           if (((Boolean)ClickGUI.instance.enumDropMenuSelectedSideGlowAnimate.getValue()).booleanValue()) {
/* 678 */             float heightOffset4 = 0.0F;
/* 679 */             int counter4 = 0;
/* 680 */             for (Enum<?> element : (Enum[])((Enum<Enum>)this.setting.getValue()).getDeclaringClass().getEnumConstants()) {
/* 681 */               counter4++;
/* 682 */               float startY = this.y + heightOffset4;
/* 683 */               float endY = (this.y + this.height) + heightOffset4;
/*     */               
/* 685 */               storedEnumDropMenuSelectSideGlowLoops.putIfAbsent(this.setting.getName() + element.name(), Integer.valueOf(0));
/*     */               
/* 687 */               int animateLoops = ((Integer)storedEnumDropMenuSelectSideGlowLoops.get(this.setting.getName() + element.name())).intValue();
/*     */               
/* 689 */               animateLoops = (int)(animateLoops + ((element == this.setting.getValue()) ? (((Float)ClickGUI.instance.enumDropMenuSelectedSideGlowAnimateSpeed.getValue()).floatValue() * 10.0F) : (((Float)ClickGUI.instance.enumDropMenuSelectedSideGlowAnimateSpeed.getValue()).floatValue() * 10.0F * -1.0F)));
/*     */               
/* 691 */               if (animateLoops >= 300) {
/* 692 */                 animateLoops = 300;
/*     */               }
/* 694 */               if (animateLoops <= 0) {
/* 695 */                 animateLoops = 0;
/*     */               }
/* 697 */               storedEnumDropMenuSelectSideGlowLoops.put(this.setting.getName() + element.name(), Integer.valueOf(animateLoops));
/*     */               
/* 699 */               selectedSideSideGlowColor = new Color(selectedSideSideGlowColor.getRed(), selectedSideSideGlowColor.getGreen(), selectedSideSideGlowColor.getBlue(), (int)((((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.enumDropMenuSelectedSideGlowColor.getValue()).getAlpha() / 300.0F * ((Integer)storedEnumDropMenuOpenCloseAlphaLayerLoops.get(this.setting.getName())).intValue()) : ((Color)ClickGUI.instance.enumDropMenuSelectedSideGlowColor.getValue()).getAlpha()) / 300.0F * ((Integer)storedEnumDropMenuSelectSideGlowLoops.get(this.setting.getName() + element.name())).intValue()));
/*     */               
/* 701 */               GlStateManager.func_179118_c();
/* 702 */               if (ClickGUI.instance.enumDropMenuSelectedSideGlowSide.getValue() == ClickGUI.EnumDropMenuSelectedSideGlowSide.Right) {
/* 703 */                 RenderUtils2D.drawCustomRect(endX - ((Float)ClickGUI.instance.enumDropMenuSelectedSideGlowWidth.getValue()).floatValue(), startY + 1.0F, endX, (counter4 == Arrays.stream(((Enum<Object>)this.setting.getValue()).getDeclaringClass().getEnumConstants()).count()) ? (endY - 1.0F - ((Integer)ClickGUI.instance.enumDropMenuRectGap.getValue()).intValue()) : (endY - ((Integer)ClickGUI.instance.enumDropMenuRectGap.getValue()).intValue()), selectedSideSideGlowColor.getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), selectedSideSideGlowColor.getRGB());
/*     */               } else {
/*     */                 
/* 706 */                 RenderUtils2D.drawCustomRect(startX, startY + 1.0F, startX + ((Float)ClickGUI.instance.enumDropMenuSelectedSideGlowWidth.getValue()).floatValue(), (counter4 == Arrays.stream(((Enum<Object>)this.setting.getValue()).getDeclaringClass().getEnumConstants()).count()) ? (endY - 1.0F - ((Integer)ClickGUI.instance.enumDropMenuRectGap.getValue()).intValue()) : (endY - ((Integer)ClickGUI.instance.enumDropMenuRectGap.getValue()).intValue()), (new Color(0, 0, 0, 0)).getRGB(), selectedSideSideGlowColor.getRGB(), selectedSideSideGlowColor.getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*     */               } 
/* 708 */               GlStateManager.func_179141_d();
/*     */ 
/*     */               
/* 711 */               heightOffset4 += this.height;
/*     */             } 
/*     */           } else {
/*     */             
/* 715 */             GlStateManager.func_179118_c();
/* 716 */             if (ClickGUI.instance.enumDropMenuSelectedSideGlowSide.getValue() == ClickGUI.EnumDropMenuSelectedSideGlowSide.Right) {
/* 717 */               RenderUtils2D.drawCustomRect(endX - ((Float)ClickGUI.instance.enumDropMenuSelectedSideGlowWidth.getValue()).floatValue(), selectedRectStartY, endX, selectedRectEndY, selectedSideSideGlowColor.getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), selectedSideSideGlowColor.getRGB());
/*     */             } else {
/*     */               
/* 720 */               RenderUtils2D.drawCustomRect(startX, selectedRectStartY, startX + ((Float)ClickGUI.instance.enumDropMenuSelectedSideGlowWidth.getValue()).floatValue(), selectedRectEndY, (new Color(0, 0, 0, 0)).getRGB(), selectedSideSideGlowColor.getRGB(), selectedSideSideGlowColor.getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*     */             } 
/* 722 */             GlStateManager.func_179141_d();
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 732 */         float heightOffset3 = 0.0F;
/*     */         
/* 734 */         int selectedTextAlphaAnimation = (int)(((Color)ClickGUI.instance.enumDropMenuSelectedTextColor.getValue()).getAlpha() / 300.0F * this.animateTextAlpha);
/* 735 */         if (selectedTextAlphaAnimation <= 4) {
/* 736 */           selectedTextAlphaAnimation = 4;
/*     */         }
/*     */         
/* 739 */         int textAlphaAnimation = (int)(((Color)ClickGUI.instance.enumDropMenuTextColor.getValue()).getAlpha() / 300.0F * this.animateTextAlpha);
/* 740 */         if (textAlphaAnimation <= 4) {
/* 741 */           textAlphaAnimation = 4;
/*     */         }
/*     */         
/* 744 */         Color selectedTextColor = new Color(((Color)ClickGUI.instance.enumDropMenuSelectedTextColor.getValue()).getRed(), ((Color)ClickGUI.instance.enumDropMenuSelectedTextColor.getValue()).getGreen(), ((Color)ClickGUI.instance.enumDropMenuSelectedTextColor.getValue()).getBlue(), ((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue() ? selectedTextAlphaAnimation : ((Color)ClickGUI.instance.enumDropMenuSelectedTextColor.getValue()).getAlpha());
/*     */         
/* 746 */         Color newTextColor = new Color(255, 255, 255, ((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue() ? 0 : 255);
/*     */         
/* 748 */         for (Enum<?> element : (Enum[])((Enum<Enum>)this.setting.getValue()).getDeclaringClass().getEnumConstants()) {
/*     */           
/* 750 */           Color textColor = new Color(((Color)ClickGUI.instance.enumDropMenuTextColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.enumDropMenuTextColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.enumDropMenuTextColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue() ? textAlphaAnimation : ((Color)ClickGUI.instance.enumDropMenuTextColor.getValue()).getAlpha());
/*     */ 
/*     */           
/* 753 */           if (((Boolean)ClickGUI.instance.enumDropMenuSelectedTextColorAnimation.getValue()).booleanValue() && ((Boolean)ClickGUI.instance.enumDropMenuSelectedTextDifColor.getValue()).booleanValue()) {
/* 754 */             storedEnumDropMenuSelectTextLoops.putIfAbsent(this.setting.getName() + element.name(), Integer.valueOf(0));
/* 755 */             int animateLoops = ((Integer)storedEnumDropMenuSelectTextLoops.get(this.setting.getName() + element.name())).intValue();
/*     */             
/* 757 */             int red = (int)MathUtilFuckYou.linearInterp(textColor.getRed(), selectedTextColor.getRed(), animateLoops);
/* 758 */             int green = (int)MathUtilFuckYou.linearInterp(textColor.getGreen(), selectedTextColor.getGreen(), animateLoops);
/* 759 */             int blue = (int)MathUtilFuckYou.linearInterp(textColor.getBlue(), selectedTextColor.getBlue(), animateLoops);
/* 760 */             int alpha = (int)MathUtilFuckYou.linearInterp(((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue() ? textAlphaAnimation : ((Color)ClickGUI.instance.enumDropMenuTextColor.getValue()).getAlpha(), ((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue() ? selectedTextAlphaAnimation : ((Color)ClickGUI.instance.enumDropMenuSelectedTextColor.getValue()).getAlpha(), animateLoops);
/*     */ 
/*     */             
/* 763 */             newTextColor = new Color(red, green, blue, alpha);
/*     */ 
/*     */             
/* 766 */             animateLoops = (int)(animateLoops + ((element == this.setting.getValue()) ? (((Float)ClickGUI.instance.enumDropMenuSelectedTextColorAnimationSpeed.getValue()).floatValue() * 10.0F) : (((Float)ClickGUI.instance.enumDropMenuSelectedTextColorAnimationSpeed.getValue()).floatValue() * 10.0F * -1.0F)));
/*     */             
/* 768 */             if (animateLoops >= 300) {
/* 769 */               animateLoops = 300;
/*     */             }
/* 771 */             if (animateLoops <= 0) {
/* 772 */               animateLoops = 0;
/*     */             }
/* 774 */             storedEnumDropMenuSelectTextLoops.put(this.setting.getName() + element.name(), Integer.valueOf(animateLoops));
/*     */           } 
/*     */ 
/*     */           
/* 778 */           GL11.glTranslatef(((this.x + this.width + ((Integer)ClickGUI.instance.enumDropMenuXOffset.getValue()).intValue()) + (((Integer)ClickGUI.instance.enumDropMenuWidthFactor.getValue()).intValue() * 2 + this.setting.getLongestElementLength()) / 2.0F) * (1.0F - ((Float)ClickGUI.instance.enumDropMenuTextScale.getValue()).floatValue()), (this.y + this.height / 2.0F + heightOffset3) * (1.0F - ((Float)ClickGUI.instance.enumDropMenuTextScale.getValue()).floatValue()), 0.0F);
/* 779 */           GL11.glScalef(((Float)ClickGUI.instance.enumDropMenuTextScale.getValue()).floatValue(), ((Float)ClickGUI.instance.enumDropMenuTextScale.getValue()).floatValue(), 0.0F);
/*     */           
/* 781 */           GlStateManager.func_179118_c();
/* 782 */           if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Minecraft) {
/* 783 */             GL11.glEnable(3553);
/* 784 */             this.mc.field_71466_p.func_78276_b(element.name(), (int)((this.x + this.width + ((Integer)ClickGUI.instance.enumDropMenuXOffset.getValue()).intValue()) + (((Integer)ClickGUI.instance.enumDropMenuWidthFactor.getValue()).intValue() * 2 + this.setting.getLongestElementLength()) / 2.0F - FontManager.getWidth(element.name()) / 2.0F), (int)(this.y + this.height / 2.0F + heightOffset3), ((Boolean)ClickGUI.instance.enumDropMenuSelectedTextDifColor.getValue()).booleanValue() ? (((Boolean)ClickGUI.instance.enumDropMenuSelectedTextColorAnimation.getValue()).booleanValue() ? newTextColor.getRGB() : ((element == this.setting.getValue()) ? selectedTextColor.getRGB() : textColor.getRGB())) : textColor.getRGB());
/* 785 */             GL11.glDisable(3553);
/*     */           } else {
/*     */             
/* 788 */             FontManager.draw(element.name(), (this.x + this.width + ((Integer)ClickGUI.instance.enumDropMenuXOffset.getValue()).intValue()) + (((Integer)ClickGUI.instance.enumDropMenuWidthFactor.getValue()).intValue() * 2 + this.setting.getLongestElementLength()) / 2.0F - FontManager.getWidth(element.name()) / 2.0F, this.y + this.height / 2.0F + heightOffset3, ((Boolean)ClickGUI.instance.enumDropMenuSelectedTextDifColor.getValue()).booleanValue() ? (((Boolean)ClickGUI.instance.enumDropMenuSelectedTextColorAnimation.getValue()).booleanValue() ? newTextColor.getRGB() : ((element == this.setting.getValue()) ? selectedTextColor.getRGB() : textColor.getRGB())) : textColor.getRGB());
/*     */           } 
/* 790 */           GlStateManager.func_179141_d();
/*     */           
/* 792 */           GL11.glScalef(1.0F / ((Float)ClickGUI.instance.enumDropMenuTextScale.getValue()).floatValue(), 1.0F / ((Float)ClickGUI.instance.enumDropMenuTextScale.getValue()).floatValue(), 0.0F);
/* 793 */           GL11.glTranslatef(((this.x + this.width + ((Integer)ClickGUI.instance.enumDropMenuXOffset.getValue()).intValue()) + (((Integer)ClickGUI.instance.enumDropMenuWidthFactor.getValue()).intValue() * 2 + this.setting.getLongestElementLength()) / 2.0F) * (1.0F - ((Float)ClickGUI.instance.enumDropMenuTextScale.getValue()).floatValue()) * -1.0F, (this.y + this.height / 2.0F + heightOffset3) * (1.0F - ((Float)ClickGUI.instance.enumDropMenuTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */           
/* 795 */           heightOffset3 += this.height;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 801 */         if (((Boolean)ClickGUI.instance.enumDropMenuSelectedRectScaleOut.getValue()).booleanValue()) {
/* 802 */           Color expandedRectColor = new Color(((Color)ClickGUI.instance.enumDropMenuSelectedRectScaleOutColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.enumDropMenuSelectedRectScaleOutColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.enumDropMenuSelectedRectScaleOutColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.enumDropMenuSelectedRectScaleOutColor.getValue()).getAlpha());
/*     */           
/* 804 */           float heightOffset5 = 0.0F;
/* 805 */           for (Enum<?> element : (Enum[])((Enum<Enum>)this.setting.getValue()).getDeclaringClass().getEnumConstants()) {
/* 806 */             float startY = this.y + heightOffset5;
/* 807 */             float endY = (this.y + this.height) + heightOffset5;
/*     */ 
/*     */             
/* 810 */             storedEnumDropMenuExpandRectLoops.putIfAbsent(this.setting.getName() + element.name(), Integer.valueOf(300));
/*     */             
/* 812 */             expandedRectColor = new Color(expandedRectColor.getRed(), expandedRectColor.getGreen(), expandedRectColor.getBlue(), (int)(((Color)ClickGUI.instance.enumDropMenuSelectedRectScaleOutColor.getValue()).getAlpha() / 300.0F * ((Integer)storedEnumDropMenuExpandRectLoops.get(this.setting.getName() + element.name())).intValue() * -1.0F) + ((Color)ClickGUI.instance.enumDropMenuSelectedRectScaleOutColor.getValue()).getAlpha());
/*     */             
/* 814 */             int animateLoops = ((Integer)storedEnumDropMenuExpandRectLoops.get(this.setting.getName() + element.name())).intValue();
/*     */             
/* 816 */             animateLoops = (int)(animateLoops + ((Float)ClickGUI.instance.enumDropMenuSelectedRectScaleOutFactor.getValue()).floatValue() * 10.0F);
/*     */             
/* 818 */             if (animateLoops >= 300) {
/* 819 */               animateLoops = 300;
/*     */             }
/*     */             
/* 822 */             storedEnumDropMenuExpandRectLoops.put(this.setting.getName() + element.name(), Integer.valueOf(animateLoops));
/*     */             
/* 824 */             GlStateManager.func_179118_c();
/* 825 */             RenderUtils2D.drawRect(startX + 1.0F - ((Float)ClickGUI.instance.enumDropMenuSelectedRectScaleMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedEnumDropMenuExpandRectLoops.get(this.setting.getName() + element.name())).intValue(), startY + 1.0F - ((Float)ClickGUI.instance.enumDropMenuSelectedRectScaleMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedEnumDropMenuExpandRectLoops.get(this.setting.getName() + element.name())).intValue(), endX - 1.0F + ((Float)ClickGUI.instance.enumDropMenuSelectedRectScaleMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedEnumDropMenuExpandRectLoops.get(this.setting.getName() + element.name())).intValue(), endY + ((Float)ClickGUI.instance.enumDropMenuSelectedRectScaleMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedEnumDropMenuExpandRectLoops.get(this.setting.getName() + element.name())).intValue(), expandedRectColor.getRGB());
/* 826 */             GlStateManager.func_179141_d();
/*     */             
/* 828 */             heightOffset5 += this.height;
/*     */           } 
/*     */         } 
/*     */         
/* 832 */         if (this.alphaLayerAnimationReverseDoneFlag) {
/* 833 */           this.alphaLayerAnimationReverseDoneFlag = false;
/* 834 */           this.expanded = false;
/* 835 */           anyExpanded = false;
/* 836 */           this.reverseAlphaLayerAnimationFlag = false;
/* 837 */           this.reverseAlphaLayer2Flag = false;
/*     */         } 
/*     */         
/* 840 */         if (((mouseX < Math.min(startX, endX) || mouseX > Math.max(startX, endX) || mouseY < Math.min(this.y, (this.y + this.height) + heightOffset) || mouseY > Math.max(this.y, (this.y + this.height) + heightOffset)) && (mouseX < Math.min(this.x, this.x + this.width) || mouseX > Math.max(this.x, this.x + this.width) || mouseY < Math.min(this.y, this.y + this.height) || mouseY > Math.max(this.y, this.y + this.height)) && ((Mouse.getEventButton() == 0 && Mouse.isButtonDown(0)) || (Mouse.getEventButton() == 1 && Mouse.isButtonDown(1)))) || (mouseX >= Math.min(this.x, this.x + this.width) && mouseX <= Math.max(this.x, this.x + this.width) && mouseY >= Math.min(this.y, this.y + this.height) && mouseY <= Math.max(this.y, this.y + this.height) && Mouse.getEventButton() == 1 && Mouse.isButtonDown(1)))
/*     */         {
/* 842 */           if (((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue()) {
/* 843 */             this.reverseAlphaLayerAnimationFlag = true;
/*     */           } else {
/*     */             
/* 846 */             this.expanded = false;
/* 847 */             anyExpanded = false;
/*     */           } 
/*     */         }
/*     */         
/* 851 */         if (((Boolean)ClickGUI.instance.enumDropMenuExpandAnimateScale.getValue()).booleanValue() && ((Boolean)ClickGUI.instance.enumDropMenuExpandAnimate.getValue()).booleanValue()) {
/* 852 */           GL11.glScalef(300.0F / this.animatedScale, 300.0F / this.animatedScale, 0.0F);
/* 853 */           GL11.glTranslatef((this.x + this.width + ((Integer)ClickGUI.instance.enumDropMenuXOffset.getValue()).intValue()) * -1.0F * (1.0F - this.animatedScale / 300.0F), this.y * -1.0F * (1.0F - this.animatedScale / 300.0F), 0.0F);
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 863 */     else if (((Boolean)ClickGUI.instance.enumLoopModeArrows.getValue()).booleanValue()) {
/* 864 */       Color arrowsColor = new Color(((Color)ClickGUI.instance.enumArrowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.enumArrowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.enumArrowColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.enumArrowColor.getValue()).getAlpha());
/*     */ 
/*     */       
/* 867 */       RenderUtils2D.drawTriangle((this.x + this.width - 7) - this.font.getStringWidth(this.setting.displayValue()) * ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue() - ((Float)ClickGUI.instance.enumLoopModeArrowsXOffset.getValue()).floatValue() - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue(), this.y + this.height / 2.0F - ((Float)ClickGUI.instance.enumLoopModeArrowsScaleY.getValue()).floatValue() / 2.0F, (this.x + this.width - 7) - this.font.getStringWidth(this.setting.displayValue()) * ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue() - ((Float)ClickGUI.instance.enumLoopModeArrowsXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.enumLoopModeArrowsScaleX.getValue()).floatValue() - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue(), this.y + this.height / 2.0F, (this.x + this.width - 7) - this.font.getStringWidth(this.setting.displayValue()) * ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue() - ((Float)ClickGUI.instance.enumLoopModeArrowsXOffset.getValue()).floatValue() - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue(), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.enumLoopModeArrowsScaleY.getValue()).floatValue() / 2.0F, arrowsColor.getRGB());
/*     */       
/* 869 */       RenderUtils2D.drawTriangle((this.x + this.width) + ((Float)ClickGUI.instance.enumLoopModeArrowsXOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.enumLoopModeArrowsScaleX.getValue()).floatValue() - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue(), this.y + this.height / 2.0F, (this.x + this.width) + ((Float)ClickGUI.instance.enumLoopModeArrowsXOffset.getValue()).floatValue() - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue(), this.y + this.height / 2.0F - ((Float)ClickGUI.instance.enumLoopModeArrowsScaleY.getValue()).floatValue() / 2.0F, (this.x + this.width) + ((Float)ClickGUI.instance.enumLoopModeArrowsXOffset.getValue()).floatValue() - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue(), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.enumLoopModeArrowsScaleY.getValue()).floatValue() / 2.0F, arrowsColor.getRGB());
/*     */       
/* 871 */       if (ClickGUI.instance.enumArrowClickAnimationMode.getValue() != ClickGUI.EnumArrowClickAnimationMode.None) {
/*     */         
/* 873 */         storedArrowAnimationLoopsRight.putIfAbsent(this.setting.getName(), Integer.valueOf(0));
/* 874 */         int animateLoops = ((Integer)storedArrowAnimationLoopsRight.get(this.setting.getName())).intValue();
/*     */         
/* 876 */         if (isHovered(mouseX, mouseY) && Mouse.getEventButton() == 0 && Mouse.isButtonDown(0)) {
/* 877 */           animateLoops = 0;
/*     */         }
/*     */         
/* 880 */         animateLoops = (int)(animateLoops + ((Float)ClickGUI.instance.enumArrowClickAnimationFactor.getValue()).floatValue() * 10.0F);
/*     */         
/* 882 */         if (animateLoops >= 300) {
/* 883 */           animateLoops = 300;
/*     */         }
/* 885 */         if (animateLoops <= 0) {
/* 886 */           animateLoops = 0;
/*     */         }
/* 888 */         storedArrowAnimationLoopsRight.put(this.setting.getName(), Integer.valueOf(animateLoops));
/*     */ 
/*     */         
/* 891 */         storedArrowAnimationLoopsLeft.putIfAbsent(this.setting.getName(), Integer.valueOf(0));
/* 892 */         int animateLoops2 = ((Integer)storedArrowAnimationLoopsLeft.get(this.setting.getName())).intValue();
/*     */         
/* 894 */         if (isHovered(mouseX, mouseY) && Mouse.getEventButton() == 1 && Mouse.isButtonDown(1)) {
/* 895 */           animateLoops2 = 0;
/*     */         }
/*     */         
/* 898 */         animateLoops2 = (int)(animateLoops2 + ((Float)ClickGUI.instance.enumArrowClickAnimationFactor.getValue()).floatValue() * 10.0F);
/*     */         
/* 900 */         if (animateLoops2 >= 300) {
/* 901 */           animateLoops2 = 300;
/*     */         }
/* 903 */         if (animateLoops2 <= 0) {
/* 904 */           animateLoops2 = 0;
/*     */         }
/* 906 */         storedArrowAnimationLoopsLeft.put(this.setting.getName(), Integer.valueOf(animateLoops2));
/*     */         
/* 908 */         Color arrowsColorLeft = new Color(arrowsColor.getRed(), arrowsColor.getGreen(), arrowsColor.getBlue(), ((Integer)ClickGUI.instance.enumArrowClickAnimationMaxAlpha.getValue()).intValue() - (int)(((Integer)ClickGUI.instance.enumArrowClickAnimationMaxAlpha.getValue()).intValue() / 300.0F * ((Integer)storedArrowAnimationLoopsLeft.get(this.setting.getName())).intValue()));
/* 909 */         Color arrowsColorRight = new Color(arrowsColor.getRed(), arrowsColor.getGreen(), arrowsColor.getBlue(), ((Integer)ClickGUI.instance.enumArrowClickAnimationMaxAlpha.getValue()).intValue() - (int)(((Integer)ClickGUI.instance.enumArrowClickAnimationMaxAlpha.getValue()).intValue() / 300.0F * ((Integer)storedArrowAnimationLoopsRight.get(this.setting.getName())).intValue()));
/*     */ 
/*     */         
/* 912 */         if (ClickGUI.instance.enumArrowClickAnimationMode.getValue() == ClickGUI.EnumArrowClickAnimationMode.Scale) {
/* 913 */           GL11.glTranslatef(((this.x + this.width) + ((Float)ClickGUI.instance.enumLoopModeArrowsXOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.enumLoopModeArrowsScaleX.getValue()).floatValue() / 2.0F - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue()) * (1.0F - ((Float)ClickGUI.instance.enumArrowClickAnimationMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedArrowAnimationLoopsRight.get(this.setting.getName())).intValue()), (this.y + this.height / 2.0F) * (1.0F - ((Float)ClickGUI.instance.enumArrowClickAnimationMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedArrowAnimationLoopsRight.get(this.setting.getName())).intValue()), 0.0F);
/* 914 */           GL11.glScalef(((Float)ClickGUI.instance.enumArrowClickAnimationMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedArrowAnimationLoopsRight.get(this.setting.getName())).intValue(), ((Float)ClickGUI.instance.enumArrowClickAnimationMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedArrowAnimationLoopsRight.get(this.setting.getName())).intValue(), 0.0F);
/*     */         } 
/*     */         
/* 917 */         RenderUtils2D.drawTriangle((this.x + this.width) + ((Float)ClickGUI.instance.enumLoopModeArrowsXOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.enumLoopModeArrowsScaleX.getValue()).floatValue() - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue(), this.y + this.height / 2.0F, (this.x + this.width) + ((Float)ClickGUI.instance.enumLoopModeArrowsXOffset.getValue()).floatValue() - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue(), this.y + this.height / 2.0F - ((Float)ClickGUI.instance.enumLoopModeArrowsScaleY.getValue()).floatValue() / 2.0F, (this.x + this.width) + ((Float)ClickGUI.instance.enumLoopModeArrowsXOffset.getValue()).floatValue() - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue(), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.enumLoopModeArrowsScaleY.getValue()).floatValue() / 2.0F, arrowsColorRight.getRGB());
/*     */         
/* 919 */         if (ClickGUI.instance.enumArrowClickAnimationMode.getValue() == ClickGUI.EnumArrowClickAnimationMode.Scale) {
/* 920 */           GL11.glScalef(1.0F / ((Float)ClickGUI.instance.enumArrowClickAnimationMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedArrowAnimationLoopsRight.get(this.setting.getName())).intValue(), 1.0F / ((Float)ClickGUI.instance.enumArrowClickAnimationMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedArrowAnimationLoopsRight.get(this.setting.getName())).intValue(), 0.0F);
/* 921 */           GL11.glTranslatef(((this.x + this.width) + ((Float)ClickGUI.instance.enumLoopModeArrowsXOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.enumLoopModeArrowsScaleX.getValue()).floatValue() / 2.0F - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue()) * (1.0F - ((Float)ClickGUI.instance.enumArrowClickAnimationMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedArrowAnimationLoopsRight.get(this.setting.getName())).intValue()) * -1.0F, (this.y + this.height / 2.0F) * (1.0F - ((Float)ClickGUI.instance.enumArrowClickAnimationMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedArrowAnimationLoopsRight.get(this.setting.getName())).intValue()) * -1.0F, 0.0F);
/*     */         } 
/*     */ 
/*     */         
/* 925 */         if (ClickGUI.instance.enumArrowClickAnimationMode.getValue() == ClickGUI.EnumArrowClickAnimationMode.Scale) {
/* 926 */           GL11.glTranslatef(((this.x + this.width - 7) - this.font.getStringWidth(this.setting.displayValue()) * ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue() - ((Float)ClickGUI.instance.enumLoopModeArrowsXOffset.getValue()).floatValue() - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue() - ((Float)ClickGUI.instance.enumLoopModeArrowsScaleX.getValue()).floatValue() / 2.0F) * (1.0F - ((Float)ClickGUI.instance.enumArrowClickAnimationMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedArrowAnimationLoopsLeft.get(this.setting.getName())).intValue()), (this.y + this.height / 2.0F) * (1.0F - ((Float)ClickGUI.instance.enumArrowClickAnimationMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedArrowAnimationLoopsLeft.get(this.setting.getName())).intValue()), 0.0F);
/* 927 */           GL11.glScalef(((Float)ClickGUI.instance.enumArrowClickAnimationMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedArrowAnimationLoopsLeft.get(this.setting.getName())).intValue(), ((Float)ClickGUI.instance.enumArrowClickAnimationMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedArrowAnimationLoopsLeft.get(this.setting.getName())).intValue(), 0.0F);
/*     */         } 
/*     */         
/* 930 */         RenderUtils2D.drawTriangle((this.x + this.width - 7) - this.font.getStringWidth(this.setting.displayValue()) * ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue() - ((Float)ClickGUI.instance.enumLoopModeArrowsXOffset.getValue()).floatValue() - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue(), this.y + this.height / 2.0F - ((Float)ClickGUI.instance.enumLoopModeArrowsScaleY.getValue()).floatValue() / 2.0F, (this.x + this.width - 7) - this.font.getStringWidth(this.setting.displayValue()) * ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue() - ((Float)ClickGUI.instance.enumLoopModeArrowsXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.enumLoopModeArrowsScaleX.getValue()).floatValue() - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue(), this.y + this.height / 2.0F, (this.x + this.width - 7) - this.font.getStringWidth(this.setting.displayValue()) * ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue() - ((Float)ClickGUI.instance.enumLoopModeArrowsXOffset.getValue()).floatValue() - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue(), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.enumLoopModeArrowsScaleY.getValue()).floatValue() / 2.0F, arrowsColorLeft.getRGB());
/*     */         
/* 932 */         if (ClickGUI.instance.enumArrowClickAnimationMode.getValue() == ClickGUI.EnumArrowClickAnimationMode.Scale) {
/* 933 */           GL11.glScalef(1.0F / ((Float)ClickGUI.instance.enumArrowClickAnimationMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedArrowAnimationLoopsLeft.get(this.setting.getName())).intValue(), 1.0F / ((Float)ClickGUI.instance.enumArrowClickAnimationMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedArrowAnimationLoopsLeft.get(this.setting.getName())).intValue(), 0.0F);
/* 934 */           GL11.glTranslatef(((this.x + this.width - 7) - this.font.getStringWidth(this.setting.displayValue()) * ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue() - ((Float)ClickGUI.instance.enumLoopModeArrowsXOffset.getValue()).floatValue() - ((Integer)ClickGUI.instance.enumLoopModeTextXOffset.getValue()).intValue() - ((Float)ClickGUI.instance.enumLoopModeArrowsScaleX.getValue()).floatValue() / 2.0F) * (1.0F - ((Float)ClickGUI.instance.enumArrowClickAnimationMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedArrowAnimationLoopsLeft.get(this.setting.getName())).intValue()) * -1.0F, (this.y + this.height / 2.0F) * (1.0F - ((Float)ClickGUI.instance.enumArrowClickAnimationMaxScale.getValue()).floatValue() / 300.0F * ((Integer)storedArrowAnimationLoopsLeft.get(this.setting.getName())).intValue()) * -1.0F, 0.0F);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 943 */     GlStateManager.func_179141_d(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bottomRender(int mouseX, int mouseY, boolean lastSetting, boolean firstSetting, float partialTicks) {
/* 949 */     GlStateManager.func_179118_c();
/* 950 */     drawSettingRects(lastSetting, false);
/*     */     
/* 952 */     drawExtendedGradient(lastSetting, false);
/* 953 */     drawExtendedLine(lastSetting);
/*     */     
/* 955 */     renderHoverRect(this.moduleName + this.setting.getName(), mouseX, mouseY, 2.0F, -1.0F, false);
/*     */     
/* 957 */     GlStateManager.func_179141_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHovered(int mouseX, int mouseY) {
/* 962 */     if (!anyExpanded) {
/* 963 */       return (mouseX >= Math.min(this.x, this.x + this.width) && mouseX <= Math.max(this.x, this.x + this.width) && mouseY >= Math.min(this.y, this.y + this.height) && mouseY <= Math.max(this.y, this.y + this.height));
/*     */     }
/*     */     
/* 966 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 972 */     if (!isHovered(mouseX, mouseY) || !this.setting.isVisible()) return false; 
/* 973 */     if (((Boolean)ClickGUI.instance.enumDropMenu.getValue()).booleanValue()) {
/* 974 */       if (mouseButton == 0) {
/* 975 */         this.expanded = !this.expanded;
/*     */       
/*     */       }
/*     */     }
/* 979 */     else if (mouseButton == 0) {
/* 980 */       this.setting.forwardLoop();
/* 981 */       SoundUtil.playButtonClick();
/*     */     }
/* 983 */     else if (mouseButton == 1) {
/* 984 */       this.setting.backwardLoop();
/* 985 */       SoundUtil.playButtonClick();
/*     */     } 
/*     */     
/* 988 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 993 */     return this.setting.isVisible();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 998 */     return this.setting.getDescription();
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\gui\components\EnumButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */