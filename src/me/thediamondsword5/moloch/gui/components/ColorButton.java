/*     */ package me.thediamondsword5.moloch.gui.components;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.module.modules.client.CustomFont;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.spartanb312.base.client.FontManager;
/*     */ import net.spartanb312.base.core.setting.NumberSetting;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.core.setting.settings.BooleanSetting;
/*     */ import net.spartanb312.base.gui.Component;
/*     */ import net.spartanb312.base.gui.Panel;
/*     */ import net.spartanb312.base.gui.components.BooleanButton;
/*     */ import net.spartanb312.base.gui.components.NumberSlider;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*     */ import net.spartanb312.base.module.modules.client.HUDEditor;
/*     */ import net.spartanb312.base.utils.ColorUtil;
/*     */ import net.spartanb312.base.utils.SoundUtil;
/*     */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ColorButton
/*     */   extends Component {
/*     */   Setting<Color> setting;
/*     */   String moduleName;
/*     */   boolean colorExpanded = false;
/*  32 */   List<Component> visibleColorSettings = new ArrayList<>();
/*  33 */   public BooleanSetting syncGlobalSetting = new BooleanSetting("SyncGlobal", false);
/*  34 */   BooleanSetting rainbowSetting = new BooleanSetting("Rainbow", false);
/*  35 */   NumberSetting rainbowSpeedSetting = new NumberSetting("RainbowSpeed", Float.valueOf(1.0F), Float.valueOf(0.0F), Float.valueOf(4.0F));
/*  36 */   NumberSetting rainbowSaturationSetting = new NumberSetting("RainbowSaturation", Float.valueOf(0.75F), Float.valueOf(0.0F), Float.valueOf(1.0F));
/*  37 */   NumberSetting rainbowBrightnessSetting = new NumberSetting("RainbowBrightness", Float.valueOf(0.9F), Float.valueOf(0.0F), Float.valueOf(1.0F));
/*  38 */   NumberSetting redSetting = new NumberSetting("Red", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255));
/*  39 */   NumberSetting greenSetting = new NumberSetting("Green", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255));
/*  40 */   NumberSetting blueSetting = new NumberSetting("Blue", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255));
/*  41 */   NumberSetting alphaSetting = new NumberSetting("Alpha", Integer.valueOf(255), Integer.valueOf(0), Integer.valueOf(255));
/*     */   Component syncGlobalButton;
/*     */   Component rainbowButton;
/*     */   Component rainbowSpeedSlider;
/*     */   Component rainbowSaturationSlider;
/*     */   Component rainbowBrightnessSlider;
/*     */   Component redSlider;
/*     */   Component greenSlider;
/*     */   Component blueSlider;
/*     */   Component alphaSlider;
/*  51 */   public int startY = this.y;
/*     */   boolean colorSomewhereClicked;
/*     */   boolean pastRainbowSetting;
/*     */   boolean isSlidingRainbowSpeedSlider;
/*     */   boolean isSlidingRainbowSaturationSlider;
/*     */   boolean isSlidingRainbowBrightnessSlider;
/*     */   boolean isSlidingRedSlider;
/*     */   boolean isSlidingGreenSlider;
/*     */   boolean isSlidingBlueSlider;
/*     */   boolean isSlidingAlphaSlider;
/*     */   boolean reverseAnimationFlag = false;
/*  62 */   long lastTime = 0L;
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorButton(Setting<Color> setting, int width, int height, Panel father, Module module) {
/*  67 */     this.syncGlobalButton = (Component)new BooleanButton((Setting)this.syncGlobalSetting, width, height, father, true, module);
/*  68 */     this.rainbowButton = (Component)new BooleanButton((Setting)this.rainbowSetting, width, height, father, true, module);
/*  69 */     this.rainbowSpeedSlider = (Component)new NumberSlider(this.rainbowSpeedSetting, width, height, father, true, module);
/*  70 */     this.rainbowSaturationSlider = (Component)new NumberSlider(this.rainbowSaturationSetting, width, height, father, true, module);
/*  71 */     this.rainbowBrightnessSlider = (Component)new NumberSlider(this.rainbowBrightnessSetting, width, height, father, true, module);
/*  72 */     this.redSlider = (Component)new NumberSlider(this.redSetting, width, height, father, true, module);
/*  73 */     this.greenSlider = (Component)new NumberSlider(this.greenSetting, width, height, father, true, module);
/*  74 */     this.blueSlider = (Component)new NumberSlider(this.blueSetting, width, height, father, true, module);
/*  75 */     this.alphaSlider = (Component)new NumberSlider(this.alphaSetting, width, height, father, true, module);
/*     */     
/*  77 */     this.width = width;
/*  78 */     this.height = height;
/*  79 */     this.father = father;
/*  80 */     this.setting = setting;
/*  81 */     this.moduleName = module.name;
/*     */ 
/*     */     
/*  84 */     this.visibleColorSettings.add(this.syncGlobalButton);
/*  85 */     this.visibleColorSettings.add(this.rainbowButton);
/*  86 */     this.visibleColorSettings.add(this.alphaSlider);
/*  87 */     if (((Boolean)this.rainbowSetting.getValue()).booleanValue()) {
/*  88 */       this.visibleColorSettings.add(this.rainbowSpeedSlider);
/*  89 */       this.visibleColorSettings.add(this.rainbowSaturationSlider);
/*  90 */       this.visibleColorSettings.add(this.rainbowBrightnessSlider);
/*  91 */       this.visibleColorSettings.remove(this.redSlider);
/*  92 */       this.visibleColorSettings.remove(this.greenSlider);
/*  93 */       this.visibleColorSettings.remove(this.blueSlider);
/*     */     } else {
/*     */       
/*  96 */       this.visibleColorSettings.add(this.redSlider);
/*  97 */       this.visibleColorSettings.add(this.greenSlider);
/*  98 */       this.visibleColorSettings.add(this.blueSlider);
/*  99 */       this.visibleColorSettings.remove(this.rainbowSpeedSlider);
/* 100 */       this.visibleColorSettings.remove(this.rainbowSaturationSlider);
/* 101 */       this.visibleColorSettings.remove(this.rainbowBrightnessSlider);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSetting(Component setting) {
/* 106 */     if (!this.visibleColorSettings.contains(setting)) {
/* 107 */       this.visibleColorSettings.add(setting);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setSlider(NumberSetting setting, boolean isSliding, int mouseX, int theX) {
/* 112 */     if (isSliding) {
/* 113 */       double diff = setting.getMax().doubleValue() - setting.getMin().doubleValue();
/* 114 */       double val = setting.getMin().doubleValue() + MathHelper.func_151237_a((mouseX - (theX + 3)) / (this.width - 4), 0.0D, 1.0D) * diff;
/* 115 */       if (setting.equals(this.rainbowSpeedSetting))
/* 116 */         ((Color)this.setting.getValue()).setRainbowSpeed((float)val); 
/* 117 */       if (setting.equals(this.rainbowSaturationSetting))
/* 118 */         ((Color)this.setting.getValue()).setRainbowSaturation((float)val); 
/* 119 */       if (setting.equals(this.rainbowBrightnessSetting))
/* 120 */         ((Color)this.setting.getValue()).setRainbowBrightness((float)val); 
/* 121 */       if (setting.equals(this.redSetting))
/* 122 */         ((Color)this.setting.getValue()).setRed((int)val); 
/* 123 */       if (setting.equals(this.greenSetting))
/* 124 */         ((Color)this.setting.getValue()).setGreen((int)val); 
/* 125 */       if (setting.equals(this.blueSetting))
/* 126 */         ((Color)this.setting.getValue()).setBlue((int)val); 
/* 127 */       if (setting.equals(this.alphaSetting)) {
/* 128 */         ((Color)this.setting.getValue()).setAlpha((int)val);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void colorPanelGlowExtensions(Component component, boolean lastSetting, boolean firstSetting) {
/* 134 */     if (ClickGUI.instance.moduleSideGlow.getValue() != ClickGUI.ModuleSideGlow.None) {
/* 135 */       GlStateManager.func_179118_c();
/* 136 */       if (ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Left || ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Both) {
/* 137 */         if (firstSetting)
/* 138 */           RenderUtils2D.drawCustomRect(component.x, (component.y - ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue()), component.x + ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), component.y, (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getAlpha())).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getAlpha())).getRGB(), (new Color(0, 0, 0, 0)).getRGB()); 
/* 139 */         if (lastSetting)
/* 140 */           RenderUtils2D.drawCustomRect(component.x, (component.y + this.height + 4), component.x + ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), (component.y + this.height + ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue() + 4), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getAlpha())).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getAlpha())).getRGB(), (new Color(0, 0, 0, 0)).getRGB()); 
/*     */       } 
/* 142 */       if (ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Right || ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Both) {
/* 143 */         if (firstSetting)
/* 144 */           RenderUtils2D.drawCustomRect((component.x + this.width) - ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), (component.y - ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue()), (component.x + this.width), component.y, (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getAlpha())).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getAlpha())).getRGB()); 
/* 145 */         if (lastSetting)
/* 146 */           RenderUtils2D.drawCustomRect((component.x + this.width) - ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), (component.y + this.height + 4), (component.x + this.width), (component.y + this.height + ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue() + 4), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getAlpha())).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getAlpha())).getRGB()); 
/*     */       } 
/* 148 */       GlStateManager.func_179141_d();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY, float translateDelta, float partialTicks) {
/* 155 */     GlStateManager.func_179118_c();
/*     */     
/* 157 */     Color colorTextColor = new Color(((Color)ClickGUI.instance.colorNameTextColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.colorNameTextColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.colorNameTextColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.colorNameTextColor.getValue()).getAlpha());
/*     */     
/* 159 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Minecraft) {
/* 160 */       GL11.glEnable(3553);
/* 161 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 162 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/* 164 */       this.mc.field_71466_p.func_175065_a(this.setting.getName(), (this.x + 5), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F), colorTextColor.getRGB(), ((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue());
/*     */       
/* 166 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 167 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/* 168 */       GL11.glDisable(3553);
/*     */     
/*     */     }
/* 171 */     else if (((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue()) {
/* 172 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 173 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/* 175 */       FontManager.drawShadow(this.setting.getName(), (this.x + 5), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), colorTextColor.getRGB());
/*     */       
/* 177 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 178 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */     }
/*     */     else {
/*     */       
/* 182 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 183 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/* 185 */       FontManager.draw(this.setting.getName(), (this.x + 5), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), colorTextColor.getRGB());
/*     */       
/* 187 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 188 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     this.syncGlobalSetting.setValue(Boolean.valueOf(((Color)this.setting.getValue()).getSyncGlobal()));
/* 195 */     this.rainbowSetting.setValue(Boolean.valueOf(((Color)this.setting.getValue()).getRainbow()));
/* 196 */     this.alphaSetting.setValue(Integer.valueOf(((Color)this.setting.getValue()).getAlpha()));
/* 197 */     this.rainbowSpeedSetting.setValue(Float.valueOf(((Color)this.setting.getValue()).getRainbowSpeed()));
/* 198 */     this.rainbowSaturationSetting.setValue(Float.valueOf(((Color)this.setting.getValue()).getRainbowSaturation()));
/* 199 */     this.rainbowBrightnessSetting.setValue(Float.valueOf(((Color)this.setting.getValue()).getRainbowBrightness()));
/* 200 */     this.redSetting.setValue(Integer.valueOf(((Color)this.setting.getValue()).getRed()));
/* 201 */     this.greenSetting.setValue(Integer.valueOf(((Color)this.setting.getValue()).getGreen()));
/* 202 */     this.blueSetting.setValue(Integer.valueOf(((Color)this.setting.getValue()).getBlue()));
/*     */ 
/*     */     
/* 205 */     if (this.colorExpanded) {
/*     */ 
/*     */       
/* 208 */       if (((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue()) {
/* 209 */         for (int i = 0; i < (int)((float)(System.currentTimeMillis() - this.lastTime) / 50.0F * ((Integer)ClickGUI.instance.colorDropMenuAnimateSpeed.getValue()).intValue()); i++) {
/* 210 */           colorMenuToggleThreader += this.reverseAnimationFlag ? -1 : 1;
/*     */         }
/*     */         
/* 213 */         if (colorMenuToggleThreader >= 300) {
/* 214 */           colorMenuToggleThreader = 300;
/*     */         }
/* 216 */         if (colorMenuToggleThreader <= 0) {
/* 217 */           colorMenuToggleThreader = 0;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 222 */         this.reverseAnimationFlag = false;
/*     */       } 
/*     */       
/* 225 */       if (((Boolean)this.rainbowSetting.getValue()).booleanValue() != this.pastRainbowSetting) {
/* 226 */         setSetting(this.syncGlobalButton);
/* 227 */         setSetting(this.rainbowButton);
/* 228 */         setSetting(this.alphaSlider);
/* 229 */         if (((Boolean)this.rainbowSetting.getValue()).booleanValue()) {
/* 230 */           setSetting(this.rainbowSpeedSlider);
/* 231 */           setSetting(this.rainbowSaturationSlider);
/* 232 */           setSetting(this.rainbowBrightnessSlider);
/* 233 */           this.visibleColorSettings.remove(this.redSlider);
/* 234 */           this.visibleColorSettings.remove(this.greenSlider);
/* 235 */           this.visibleColorSettings.remove(this.blueSlider);
/*     */         } else {
/*     */           
/* 238 */           setSetting(this.redSlider);
/* 239 */           setSetting(this.greenSlider);
/* 240 */           setSetting(this.blueSlider);
/* 241 */           this.visibleColorSettings.remove(this.rainbowSpeedSlider);
/* 242 */           this.visibleColorSettings.remove(this.rainbowSaturationSlider);
/* 243 */           this.visibleColorSettings.remove(this.rainbowBrightnessSlider);
/*     */         } 
/* 245 */         this.pastRainbowSetting = ((Boolean)this.rainbowSetting.getValue()).booleanValue();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 250 */       int startX = this.x + this.width + ((Integer)ClickGUI.instance.colorDropMenuXOffset.getValue()).intValue();
/* 251 */       int endX = this.x + this.width * 2 + ((Integer)ClickGUI.instance.colorDropMenuXOffset.getValue()).intValue();
/*     */ 
/*     */       
/* 254 */       if (((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() && ((Boolean)ClickGUI.instance.colorDropMenuAnimateScale.getValue()).booleanValue()) {
/* 255 */         int colorMenuToggleThreaderScale = colorMenuToggleThreader;
/* 256 */         if (colorMenuToggleThreaderScale <= 1) {
/* 257 */           colorMenuToggleThreaderScale = 1;
/*     */         }
/* 259 */         GL11.glTranslatef(startX * (1.0F - colorMenuToggleThreaderScale / 300.0F), this.y * (1.0F - colorMenuToggleThreaderScale / 300.0F), 0.0F);
/* 260 */         GL11.glScalef(colorMenuToggleThreaderScale / 300.0F, colorMenuToggleThreaderScale / 300.0F, 0.0F);
/*     */       } 
/*     */       
/* 263 */       int index = 0;
/* 264 */       int heightOffset = 0;
/* 265 */       int index2 = 0;
/* 266 */       int heightOffset2 = 0;
/*     */ 
/*     */       
/* 269 */       for (Component subSetting : this.visibleColorSettings) {
/* 270 */         index2++;
/*     */         
/* 272 */         subSetting.x = startX;
/* 273 */         subSetting.y = this.y + heightOffset2;
/*     */ 
/*     */         
/* 276 */         if (((Boolean)ClickGUI.instance.colorDropMenuExtensions.getValue()).booleanValue()) {
/* 277 */           GlStateManager.func_179118_c();
/* 278 */           int extendedGradientColor = (new Color(((Color)ClickGUI.instance.extendedGradientColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.extendedGradientColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.extendedGradientColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.extendedGradientColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.extendedGradientColor.getValue()).getAlpha())).getRGB();
/* 279 */           int colorDropMenuSideBarColor = (new Color(((Color)ClickGUI.instance.colorDropMenuSideBarColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.colorDropMenuSideBarColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.colorDropMenuSideBarColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.colorDropMenuSideBarColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.colorDropMenuSideBarColor.getValue()).getAlpha())).getRGB();
/* 280 */           int colorDropMenuOutlineColorColor = (new Color(((Color)ClickGUI.instance.colorDropMenuOutlineColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.colorDropMenuOutlineColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.colorDropMenuOutlineColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.colorDropMenuOutlineColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.colorDropMenuOutlineColor.getValue()).getAlpha())).getRGB();
/*     */           
/* 282 */           if (index2 == 1) {
/* 283 */             if (!((Boolean)ClickGUI.instance.moduleSideGlowLayer.getValue()).booleanValue()) {
/* 284 */               colorPanelGlowExtensions(subSetting, false, true);
/*     */             }
/* 286 */             RenderUtils2D.drawRect(subSetting.x, (subSetting.y - ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue()), (subSetting.x + this.width), subSetting.y, (new Color(((Color)ClickGUI.instance.moduleBGColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleBGColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleBGColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.moduleBGColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.moduleBGColor.getValue()).getAlpha())).getRGB());
/* 287 */             RenderUtils2D.drawRect((subSetting.x + 1), (subSetting.y - ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue() + 1), (subSetting.x + this.width - 1), subSetting.y, (new Color(((Color)ClickGUI.instance.extendedRectColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.extendedRectColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.extendedRectColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.extendedRectColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.extendedRectColor.getValue()).getAlpha())).getRGB());
/* 288 */             if (((Boolean)ClickGUI.instance.moduleSideGlowLayer.getValue()).booleanValue()) {
/* 289 */               colorPanelGlowExtensions(subSetting, false, true);
/*     */             }
/* 291 */             if (ClickGUI.instance.moduleSideGlow.getValue() != ClickGUI.ModuleSideGlow.None) {
/* 292 */               GlStateManager.func_179118_c();
/* 293 */               if (ClickGUI.instance.moduleSideGlowDouble.getValue() == ClickGUI.ModuleSideGlowDouble.Left) {
/* 294 */                 RenderUtils2D.drawCustomRect(subSetting.x, (subSetting.y - ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue()), subSetting.x + ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), subSetting.y, (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue() / 300.0F * colorMenuToggleThreader) : ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue() / 300.0F * colorMenuToggleThreader) : ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*     */               }
/* 296 */               else if (ClickGUI.instance.moduleSideGlowDouble.getValue() == ClickGUI.ModuleSideGlowDouble.Right) {
/* 297 */                 RenderUtils2D.drawCustomRect((subSetting.x + this.width) - ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), (subSetting.y - ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue()), (subSetting.x + this.width), subSetting.y, (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue() / 300.0F * colorMenuToggleThreader) : ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue() / 300.0F * colorMenuToggleThreader) : ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB());
/*     */               } 
/* 299 */               GlStateManager.func_179141_d();
/*     */             } 
/*     */ 
/*     */             
/* 303 */             if (((Boolean)ClickGUI.instance.extendedVerticalGradient.getValue()).booleanValue()) {
/* 304 */               RenderUtils2D.drawCustomRect(subSetting.x, (subSetting.y - ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue()), subSetting.x + ((Float)ClickGUI.instance.extendedGradientWidth.getValue()).floatValue(), subSetting.y, (new Color(0, 0, 0, 0)).getRGB(), extendedGradientColor, extendedGradientColor, (new Color(0, 0, 0, 0)).getRGB());
/*     */             }
/* 306 */             if (((Boolean)ClickGUI.instance.colorDropMenuSideBar.getValue()).booleanValue()) {
/* 307 */               RenderUtils2D.drawCustomLine(subSetting.x + ((Float)ClickGUI.instance.colorDropMenuSideBarWidth.getValue()).floatValue() / 2.0F, (subSetting.y - ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue()), subSetting.x + ((Float)ClickGUI.instance.colorDropMenuSideBarWidth.getValue()).floatValue() / 2.0F, subSetting.y, ((Float)ClickGUI.instance.colorDropMenuSideBarWidth.getValue()).floatValue(), colorDropMenuSideBarColor, colorDropMenuSideBarColor);
/*     */             }
/* 309 */             if (((Boolean)ClickGUI.instance.colorDropMenuOutline.getValue()).booleanValue()) {
/* 310 */               RenderUtils2D.drawCustomLine(subSetting.x + ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue() / 2.0F, (subSetting.y - ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue()), subSetting.x + ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue() / 2.0F, subSetting.y, ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue(), colorDropMenuOutlineColorColor, colorDropMenuOutlineColorColor);
/* 311 */               RenderUtils2D.drawCustomLine((subSetting.x + this.width) - ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue() / 2.0F, (subSetting.y - ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue()), (subSetting.x + this.width) - ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue() / 2.0F, subSetting.y, ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue(), colorDropMenuOutlineColorColor, colorDropMenuOutlineColorColor);
/* 312 */               RenderUtils2D.drawCustomLine(subSetting.x + ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue() / 2.0F, (subSetting.y - ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue()), (subSetting.x + this.width) - ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue() / 2.0F, (subSetting.y - ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue()), ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue(), colorDropMenuOutlineColorColor, colorDropMenuOutlineColorColor);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 317 */           if (index2 == this.visibleColorSettings.size()) {
/* 318 */             if (!((Boolean)ClickGUI.instance.moduleSideGlowLayer.getValue()).booleanValue()) {
/* 319 */               colorPanelGlowExtensions(subSetting, true, false);
/*     */             }
/* 321 */             RenderUtils2D.drawRect(subSetting.x, (subSetting.y + this.height + 4), (subSetting.x + this.width), (subSetting.y + this.height + ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue() + 4), (new Color(((Color)ClickGUI.instance.moduleBGColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleBGColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleBGColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.moduleBGColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.moduleBGColor.getValue()).getAlpha())).getRGB());
/* 322 */             RenderUtils2D.drawRect((subSetting.x + 1), (subSetting.y + this.height + 4), (subSetting.x + this.width - 1), (subSetting.y + this.height + ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue() + 5), (new Color(((Color)ClickGUI.instance.extendedRectColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.extendedRectColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.extendedRectColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.extendedRectColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.extendedRectColor.getValue()).getAlpha())).getRGB());
/* 323 */             if (((Boolean)ClickGUI.instance.moduleSideGlowLayer.getValue()).booleanValue()) {
/* 324 */               colorPanelGlowExtensions(subSetting, true, false);
/*     */             }
/* 326 */             if (ClickGUI.instance.moduleSideGlow.getValue() != ClickGUI.ModuleSideGlow.None) {
/* 327 */               GlStateManager.func_179118_c();
/* 328 */               if (ClickGUI.instance.moduleSideGlowDouble.getValue() == ClickGUI.ModuleSideGlowDouble.Left) {
/* 329 */                 RenderUtils2D.drawCustomRect(subSetting.x, (subSetting.y + this.height + 4), subSetting.x + ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), (subSetting.y + this.height + ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue() + 4), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue() / 300.0F * colorMenuToggleThreader) : ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue() / 300.0F * colorMenuToggleThreader) : ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*     */               }
/* 331 */               else if (ClickGUI.instance.moduleSideGlowDouble.getValue() == ClickGUI.ModuleSideGlowDouble.Right) {
/* 332 */                 RenderUtils2D.drawCustomRect((subSetting.x + this.width) - ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), (subSetting.y + this.height + 4), (subSetting.x + this.width), (subSetting.y + this.height + ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue() + 4), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue() / 300.0F * colorMenuToggleThreader) : ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue() / 300.0F * colorMenuToggleThreader) : ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB());
/*     */               } 
/* 334 */               GlStateManager.func_179141_d();
/*     */             } 
/*     */             
/* 337 */             if (((Boolean)ClickGUI.instance.extendedVerticalGradient.getValue()).booleanValue()) {
/* 338 */               RenderUtils2D.drawCustomRect(subSetting.x, (subSetting.y + this.height + 4), subSetting.x + ((Float)ClickGUI.instance.extendedGradientWidth.getValue()).floatValue(), (subSetting.y + this.height + ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue() + 4), (new Color(0, 0, 0, 0)).getRGB(), extendedGradientColor, extendedGradientColor, (new Color(0, 0, 0, 0)).getRGB());
/*     */             }
/* 340 */             if (((Boolean)ClickGUI.instance.colorDropMenuSideBar.getValue()).booleanValue()) {
/* 341 */               RenderUtils2D.drawCustomLine(subSetting.x + ((Float)ClickGUI.instance.colorDropMenuSideBarWidth.getValue()).floatValue() / 2.0F, (subSetting.y + this.height + 4), subSetting.x + ((Float)ClickGUI.instance.colorDropMenuSideBarWidth.getValue()).floatValue() / 2.0F, (subSetting.y + this.height + ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue() + 4), ((Float)ClickGUI.instance.colorDropMenuSideBarWidth.getValue()).floatValue(), colorDropMenuSideBarColor, colorDropMenuSideBarColor);
/*     */             }
/* 343 */             if (((Boolean)ClickGUI.instance.colorDropMenuOutline.getValue()).booleanValue()) {
/* 344 */               RenderUtils2D.drawCustomLine(subSetting.x + ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue() / 2.0F, (subSetting.y + this.height + 4), subSetting.x + ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue() / 2.0F, (subSetting.y + this.height + ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue() + 4), ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue(), colorDropMenuOutlineColorColor, colorDropMenuOutlineColorColor);
/* 345 */               RenderUtils2D.drawCustomLine((subSetting.x + this.width) - ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue() / 2.0F, (subSetting.y + this.height + 4), (subSetting.x + this.width) - ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue() / 2.0F, (subSetting.y + this.height + ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue() + 4), ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue(), colorDropMenuOutlineColorColor, colorDropMenuOutlineColorColor);
/* 346 */               RenderUtils2D.drawCustomLine(subSetting.x + ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue() / 2.0F, (subSetting.y + this.height + ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue() + 4), (subSetting.x + this.width) - ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue() / 2.0F, (subSetting.y + this.height + ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue() + 4), ((Float)ClickGUI.instance.colorDropMenuOutlineWidth.getValue()).floatValue(), colorDropMenuOutlineColorColor, colorDropMenuOutlineColorColor);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 351 */           GlStateManager.func_179141_d();
/*     */         } 
/*     */ 
/*     */         
/* 355 */         subSetting.bottomRender(mouseX, mouseY, (index2 == this.visibleColorSettings.size()), (index2 == 1), partialTicks);
/*     */ 
/*     */         
/* 358 */         heightOffset2 += (!subSetting.equals(this.syncGlobalButton) && !subSetting.equals(this.rainbowButton)) ? (this.height + 4) : this.height;
/*     */       } 
/*     */ 
/*     */       
/* 362 */       for (Component subSetting : this.visibleColorSettings) {
/* 363 */         index++;
/*     */         
/* 365 */         subSetting.x = startX;
/* 366 */         subSetting.y = this.y + heightOffset;
/*     */ 
/*     */         
/* 369 */         int topBottomGradientsColor = (new Color(((Color)ClickGUI.instance.colorDropMenuTopBottomGradientsColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.colorDropMenuTopBottomGradientsColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.colorDropMenuTopBottomGradientsColor.getValue()).getColorColor().getBlue(), ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.colorDropMenuTopBottomGradientsColor.getValue()).getAlpha() / 300.0F * colorMenuToggleThreader) : ((Color)ClickGUI.instance.colorDropMenuTopBottomGradientsColor.getValue()).getAlpha())).getRGB();
/* 370 */         if (index == 1 && ((Boolean)ClickGUI.instance.colorDropMenuTopBottomGradients.getValue()).booleanValue()) {
/* 371 */           GlStateManager.func_179118_c();
/* 372 */           RenderUtils2D.drawCustomRect(subSetting.x, (this.y - (((Boolean)ClickGUI.instance.colorDropMenuExtensions.getValue()).booleanValue() ? ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue() : 0)), (subSetting.x + this.width), this.y + ((Float)ClickGUI.instance.colorDropMenuTopBottomGradientsHeight.getValue()).floatValue() - (((Boolean)ClickGUI.instance.colorDropMenuExtensions.getValue()).booleanValue() ? ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue() : false), topBottomGradientsColor, topBottomGradientsColor, (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/* 373 */           GlStateManager.func_179141_d();
/*     */         } 
/* 375 */         if (index == this.visibleColorSettings.size() && ((Boolean)ClickGUI.instance.colorDropMenuTopBottomGradients.getValue()).booleanValue()) {
/* 376 */           GlStateManager.func_179118_c();
/* 377 */           RenderUtils2D.drawCustomRect(subSetting.x, (subSetting.y + this.height + 4) - ((Float)ClickGUI.instance.colorDropMenuTopBottomGradientsHeight.getValue()).floatValue() + (((Boolean)ClickGUI.instance.colorDropMenuExtensions.getValue()).booleanValue() ? ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue() : false), (subSetting.x + this.width), (subSetting.y + this.height + 4 + (((Boolean)ClickGUI.instance.colorDropMenuExtensions.getValue()).booleanValue() ? ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue() : 0)), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), topBottomGradientsColor, topBottomGradientsColor);
/* 378 */           GlStateManager.func_179141_d();
/*     */         } 
/*     */         
/* 381 */         subSetting.render(mouseX, mouseY, translateDelta, partialTicks);
/*     */ 
/*     */         
/* 384 */         if (mouseX >= Math.min(subSetting.x, subSetting.x + this.width) && mouseX <= Math.max(subSetting.x, subSetting.x + this.width) && mouseY >= Math.min(subSetting.y, subSetting.y + this.height) && mouseY <= Math.max(subSetting.y, subSetting.y + this.height) && this.colorSomewhereClicked && Mouse.isButtonDown(0)) {
/* 385 */           if (subSetting.equals(this.syncGlobalButton)) {
/* 386 */             ((Color)this.setting.getValue()).setSyncGlobal(!((Color)this.setting.getValue()).getSyncGlobal());
/*     */           }
/* 388 */           if (subSetting.equals(this.rainbowButton)) {
/* 389 */             ((Color)this.setting.getValue()).setRainbow(!((Color)this.setting.getValue()).getRainbow());
/*     */           }
/*     */           
/* 392 */           this.colorSomewhereClicked = !this.colorSomewhereClicked;
/*     */         } 
/*     */         
/* 395 */         if (mouseX >= Math.min(subSetting.x, subSetting.x + this.width) && mouseX <= Math.max(subSetting.x, subSetting.x + this.width) && mouseY >= Math.min(subSetting.y, subSetting.y + this.height) && mouseY <= Math.max(subSetting.y, subSetting.y + this.height) && Mouse.isButtonDown(0)) {
/* 396 */           if (subSetting.equals(this.rainbowSpeedSlider) && !this.isSlidingRainbowSaturationSlider && !this.isSlidingRainbowBrightnessSlider && !this.isSlidingRedSlider && !this.isSlidingGreenSlider && !this.isSlidingBlueSlider && !this.isSlidingAlphaSlider) {
/* 397 */             this.isSlidingRainbowSpeedSlider = true;
/*     */           }
/* 399 */           if (subSetting.equals(this.rainbowSaturationSlider) && !this.isSlidingRainbowSpeedSlider && !this.isSlidingRainbowBrightnessSlider && !this.isSlidingRedSlider && !this.isSlidingGreenSlider && !this.isSlidingBlueSlider && !this.isSlidingAlphaSlider) {
/* 400 */             this.isSlidingRainbowSaturationSlider = true;
/*     */           }
/* 402 */           if (subSetting.equals(this.rainbowBrightnessSlider) && !this.isSlidingRainbowSpeedSlider && !this.isSlidingRainbowSaturationSlider && !this.isSlidingRedSlider && !this.isSlidingGreenSlider && !this.isSlidingBlueSlider && !this.isSlidingAlphaSlider) {
/* 403 */             this.isSlidingRainbowBrightnessSlider = true;
/*     */           }
/* 405 */           if (subSetting.equals(this.redSlider) && !this.isSlidingRainbowSpeedSlider && !this.isSlidingRainbowSaturationSlider && !this.isSlidingRainbowBrightnessSlider && !this.isSlidingGreenSlider && !this.isSlidingBlueSlider && !this.isSlidingAlphaSlider) {
/* 406 */             this.isSlidingRedSlider = true;
/*     */           }
/* 408 */           if (subSetting.equals(this.greenSlider) && !this.isSlidingRainbowSpeedSlider && !this.isSlidingRainbowSaturationSlider && !this.isSlidingRainbowBrightnessSlider && !this.isSlidingRedSlider && !this.isSlidingBlueSlider && !this.isSlidingAlphaSlider) {
/* 409 */             this.isSlidingGreenSlider = true;
/*     */           }
/* 411 */           if (subSetting.equals(this.blueSlider) && !this.isSlidingRainbowSpeedSlider && !this.isSlidingRainbowSaturationSlider && !this.isSlidingRainbowBrightnessSlider && !this.isSlidingRedSlider && !this.isSlidingGreenSlider && !this.isSlidingAlphaSlider) {
/* 412 */             this.isSlidingBlueSlider = true;
/*     */           }
/* 414 */           if (subSetting.equals(this.alphaSlider) && !this.isSlidingRainbowSpeedSlider && !this.isSlidingRainbowSaturationSlider && !this.isSlidingRainbowBrightnessSlider && !this.isSlidingRedSlider && !this.isSlidingGreenSlider && !this.isSlidingBlueSlider) {
/* 415 */             this.isSlidingAlphaSlider = true;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 420 */         if (this.isSlidingRainbowSpeedSlider && !Mouse.isButtonDown(0))
/* 421 */           this.isSlidingRainbowSpeedSlider = false; 
/* 422 */         if (this.isSlidingRainbowSaturationSlider && !Mouse.isButtonDown(0))
/* 423 */           this.isSlidingRainbowSaturationSlider = false; 
/* 424 */         if (this.isSlidingRainbowBrightnessSlider && !Mouse.isButtonDown(0))
/* 425 */           this.isSlidingRainbowBrightnessSlider = false; 
/* 426 */         if (this.isSlidingRedSlider && !Mouse.isButtonDown(0))
/* 427 */           this.isSlidingRedSlider = false; 
/* 428 */         if (this.isSlidingGreenSlider && !Mouse.isButtonDown(0))
/* 429 */           this.isSlidingGreenSlider = false; 
/* 430 */         if (this.isSlidingBlueSlider && !Mouse.isButtonDown(0))
/* 431 */           this.isSlidingBlueSlider = false; 
/* 432 */         if (this.isSlidingAlphaSlider && !Mouse.isButtonDown(0)) {
/* 433 */           this.isSlidingAlphaSlider = false;
/*     */         }
/*     */         
/* 436 */         setSlider(this.rainbowSpeedSetting, this.isSlidingRainbowSpeedSlider, mouseX, subSetting.x);
/* 437 */         setSlider(this.rainbowSaturationSetting, this.isSlidingRainbowSaturationSlider, mouseX, subSetting.x);
/* 438 */         setSlider(this.rainbowBrightnessSetting, this.isSlidingRainbowBrightnessSlider, mouseX, subSetting.x);
/* 439 */         setSlider(this.redSetting, this.isSlidingRedSlider, mouseX, subSetting.x);
/* 440 */         setSlider(this.greenSetting, this.isSlidingGreenSlider, mouseX, subSetting.x);
/* 441 */         setSlider(this.blueSetting, this.isSlidingBlueSlider, mouseX, subSetting.x);
/* 442 */         setSlider(this.alphaSetting, this.isSlidingAlphaSlider, mouseX, subSetting.x);
/*     */         
/* 444 */         heightOffset += (!subSetting.equals(this.syncGlobalButton) && !subSetting.equals(this.rainbowButton)) ? (this.height + 4) : this.height;
/*     */       } 
/*     */       
/* 447 */       if (((Boolean)ClickGUI.instance.colorDropMenuShadow.getValue()).booleanValue()) {
/* 448 */         RenderUtils2D.drawBetterRoundRectFade(startX, ((Boolean)ClickGUI.instance.colorDropMenuExtensions.getValue()).booleanValue() ? (this.y - ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue()) : this.y, endX, ((Boolean)ClickGUI.instance.colorDropMenuExtensions.getValue()).booleanValue() ? (this.y + heightOffset + ((Integer)ClickGUI.instance.colorDropMenuExtensionsHeight.getValue()).intValue()) : (this.y + heightOffset), ((Float)ClickGUI.instance.colorDropMenuShadowSize.getValue()).floatValue(), 40.0F, false, false, false, (new Color(0, 0, 0, ((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() ? (int)(((Integer)ClickGUI.instance.colorDropMenuShadowAlpha.getValue()).intValue() / 300.0F * colorMenuToggleThreader) : ((Integer)ClickGUI.instance.colorDropMenuShadowAlpha.getValue()).intValue())).getRGB());
/*     */       }
/*     */       
/* 451 */       this.startY = this.y + heightOffset;
/*     */       
/* 453 */       if (((mouseX < Math.min(startX, endX) || mouseX > Math.max(startX, endX) || mouseY < Math.min(this.y, this.y + heightOffset) || mouseY > Math.max(this.y, this.y + heightOffset)) && (mouseX < Math.min(this.x, this.x + this.width) || mouseX > Math.max(this.x, this.x + this.width) || mouseY < Math.min(this.y, this.y + this.height) || mouseY > Math.max(this.y, this.y + this.height)) && ((Mouse.getEventButton() == 0 && Mouse.isButtonDown(0)) || (Mouse.getEventButton() == 1 && Mouse.isButtonDown(1)))) || (mouseX >= Math.min(this.x, this.x + this.width) && mouseX <= Math.max(this.x, this.x + this.width) && mouseY >= Math.min(this.y, this.y + this.height) && mouseY <= Math.max(this.y, this.y + this.height) && Mouse.getEventButton() == 1 && Mouse.isButtonDown(1))) {
/* 454 */         if (((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue()) {
/* 455 */           this.reverseAnimationFlag = true;
/*     */         } else {
/*     */           
/* 458 */           this.colorExpanded = false;
/* 459 */           anyExpanded = false;
/*     */         } 
/*     */       }
/* 462 */       if (ClickGUI.instance.isDisabled() && HUDEditor.instance.isDisabled()) {
/* 463 */         if (((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue()) {
/* 464 */           colorMenuToggleThreader = 0;
/*     */         }
/* 466 */         this.colorExpanded = false;
/* 467 */         anyExpanded = false;
/*     */       } 
/*     */       
/* 470 */       if (((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() && this.reverseAnimationFlag && 
/* 471 */         colorMenuToggleThreader <= 0) {
/* 472 */         this.reverseAnimationFlag = false;
/* 473 */         this.colorExpanded = false;
/* 474 */         anyExpanded = false;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 479 */       if (((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue() && ((Boolean)ClickGUI.instance.colorDropMenuAnimateScale.getValue()).booleanValue()) {
/* 480 */         int colorMenuToggleThreaderScale = colorMenuToggleThreader;
/* 481 */         if (colorMenuToggleThreaderScale <= 1) {
/* 482 */           colorMenuToggleThreaderScale = 1;
/*     */         }
/* 484 */         GL11.glScalef(300.0F / colorMenuToggleThreaderScale, 300.0F / colorMenuToggleThreaderScale, 0.0F);
/* 485 */         GL11.glTranslatef(startX * (1.0F - colorMenuToggleThreaderScale / 300.0F) * -1.0F, this.y * (1.0F - colorMenuToggleThreaderScale / 300.0F) * -1.0F, 0.0F);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 490 */     if (((Boolean)ClickGUI.instance.colorDropMenuAnimate.getValue()).booleanValue()) {
/* 491 */       this.lastTime = System.currentTimeMillis();
/*     */     }
/*     */ 
/*     */     
/* 495 */     Color theColor = new Color(((Color)this.setting.getValue()).getColor());
/*     */     
/* 497 */     switch ((ClickGUI.ColorDisplayShape)ClickGUI.instance.colorDisplayShape.getValue()) {
/*     */       case Rect:
/* 499 */         if (((Boolean)ClickGUI.instance.colorDisplayRounded.getValue()).booleanValue()) {
/* 500 */           if (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue())
/* 501 */             RenderUtils2D.drawCustomRoundedRectOutline((this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.colorDisplayRectWidth.getValue()).floatValue(), this.y + this.height / 2.0F - ((Float)ClickGUI.instance.colorDisplayRectHeight.getValue()).floatValue() / 2.0F, (this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue(), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.colorDisplayRectHeight.getValue()).floatValue() / 2.0F, ((Float)ClickGUI.instance.colorDisplayRoundedRadius.getValue()).floatValue(), ((Float)ClickGUI.instance.colorDisplayOutlineWidth.getValue()).floatValue(), true, true, true, true, false, false, theColor.getRGB()); 
/* 502 */           RenderUtils2D.drawRoundedRect((this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.colorDisplayRectWidth.getValue()).floatValue() + (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), this.y + this.height / 2.0F - ((Float)ClickGUI.instance.colorDisplayRectHeight.getValue()).floatValue() / 2.0F + (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), ((Float)ClickGUI.instance.colorDisplayRoundedRadius.getValue()).floatValue(), (this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.colorDisplayRectHeight.getValue()).floatValue() / 2.0F - (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), false, true, true, true, true, theColor.getRGB());
/*     */           break;
/*     */         } 
/* 505 */         if (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue())
/* 506 */           RenderUtils2D.drawRectOutline((this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.colorDisplayRectWidth.getValue()).floatValue(), this.y + this.height / 2.0F - ((Float)ClickGUI.instance.colorDisplayRectHeight.getValue()).floatValue() / 2.0F, (this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue(), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.colorDisplayRectHeight.getValue()).floatValue() / 2.0F, ((Float)ClickGUI.instance.colorDisplayOutlineWidth.getValue()).floatValue(), theColor.getRGB(), false, false); 
/* 507 */         RenderUtils2D.drawRect((this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.colorDisplayRectWidth.getValue()).floatValue() + (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), this.y + this.height / 2.0F - ((Float)ClickGUI.instance.colorDisplayRectHeight.getValue()).floatValue() / 2.0F + (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), (this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.colorDisplayRectHeight.getValue()).floatValue() / 2.0F - (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), theColor.getRGB());
/*     */         break;
/*     */ 
/*     */       
/*     */       case Square:
/* 512 */         if (((Boolean)ClickGUI.instance.colorDisplayRounded.getValue()).booleanValue()) {
/* 513 */           if (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue())
/* 514 */             RenderUtils2D.drawCustomRoundedRectOutline((this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue(), this.y + this.height / 2.0F - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F, (this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue(), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F, ((Float)ClickGUI.instance.colorDisplayRoundedRadius.getValue()).floatValue(), ((Float)ClickGUI.instance.colorDisplayOutlineWidth.getValue()).floatValue(), true, true, true, true, false, false, theColor.getRGB()); 
/* 515 */           RenderUtils2D.drawRoundedRect((this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() + (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), this.y + this.height / 2.0F - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F + (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), ((Float)ClickGUI.instance.colorDisplayRoundedRadius.getValue()).floatValue(), (this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F - (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), false, true, true, true, true, theColor.getRGB());
/*     */           break;
/*     */         } 
/* 518 */         if (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue())
/* 519 */           RenderUtils2D.drawRectOutline((this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue(), this.y + this.height / 2.0F - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F, (this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue(), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F, ((Float)ClickGUI.instance.colorDisplayOutlineWidth.getValue()).floatValue(), theColor.getRGB(), false, false); 
/* 520 */         RenderUtils2D.drawRect((this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() + (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), this.y + this.height / 2.0F - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F + (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), (this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F - (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), theColor.getRGB());
/*     */         break;
/*     */ 
/*     */       
/*     */       case Diamond:
/* 525 */         GL11.glTranslatef((this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F, this.y + this.height / 2.0F, 0.0F);
/* 526 */         GL11.glRotatef(45.0F, 0.0F, 0.0F, 1.0F);
/* 527 */         GL11.glTranslatef(((this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F) * -1.0F, (this.y + this.height / 2.0F) * -1.0F, 0.0F);
/* 528 */         if (((Boolean)ClickGUI.instance.colorDisplayRounded.getValue()).booleanValue()) {
/* 529 */           if (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue())
/* 530 */             RenderUtils2D.drawCustomRoundedRectOutline((this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue(), this.y + this.height / 2.0F - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F, (this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue(), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F, ((Float)ClickGUI.instance.colorDisplayRoundedRadius.getValue()).floatValue(), ((Float)ClickGUI.instance.colorDisplayOutlineWidth.getValue()).floatValue(), true, true, true, true, false, false, theColor.getRGB()); 
/* 531 */           RenderUtils2D.drawRoundedRect((this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() + (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), this.y + this.height / 2.0F - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F + (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), ((Float)ClickGUI.instance.colorDisplayRoundedRadius.getValue()).floatValue(), (this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F - (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), false, true, true, true, true, theColor.getRGB());
/*     */         } else {
/*     */           
/* 534 */           if (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue())
/* 535 */             RenderUtils2D.drawRectOutline((this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue(), this.y + this.height / 2.0F - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F, (this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue(), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F, ((Float)ClickGUI.instance.colorDisplayOutlineWidth.getValue()).floatValue(), theColor.getRGB(), false, false); 
/* 536 */           RenderUtils2D.drawRect((this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() + (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), this.y + this.height / 2.0F - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F + (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), (this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F - (((Boolean)ClickGUI.instance.colorDisplayOutline.getValue()).booleanValue() ? ((Float)ClickGUI.instance.colorDisplayOutlineOffset.getValue()).floatValue() : 0.0F), theColor.getRGB());
/*     */         } 
/* 538 */         GL11.glTranslatef((this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F, this.y + this.height / 2.0F, 0.0F);
/* 539 */         GL11.glRotatef(-45.0F, 0.0F, 0.0F, 1.0F);
/* 540 */         GL11.glTranslatef(((this.x + this.width) - ((Float)ClickGUI.instance.colorDisplayXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.colorDisplaySize.getValue()).floatValue() / 2.0F) * -1.0F, (this.y + this.height / 2.0F) * -1.0F, 0.0F);
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 545 */     if (((Boolean)this.syncGlobalSetting.getValue()).booleanValue()) {
/* 546 */       ((Color)this.setting.getValue()).setColor(((Color)ClickGUI.instance.globalColor.getValue()).getColor());
/*     */     
/*     */     }
/* 549 */     else if (((Boolean)this.rainbowSetting.getValue()).booleanValue()) {
/* 550 */       Color lgbtq = new Color(ColorUtil.getBetterRainbow(((Float)this.rainbowSpeedSetting.getValue()).floatValue(), ((Float)this.rainbowSaturationSetting.getValue()).floatValue(), ((Float)this.rainbowBrightnessSetting.getValue()).floatValue()));
/* 551 */       ((Color)this.setting.getValue()).setColor(lgbtq.getRGB());
/*     */     } else {
/*     */       
/* 554 */       ((Color)this.setting.getValue()).setColor((new Color(((Integer)this.redSetting.getValue()).intValue(), ((Integer)this.greenSetting.getValue()).intValue(), ((Integer)this.blueSetting.getValue()).intValue(), ((Integer)this.alphaSetting.getValue()).intValue())).getRGB());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 561 */     GlStateManager.func_179141_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public void bottomRender(int mouseX, int mouseY, boolean lastSetting, boolean firstSetting, float partialTicks) {
/* 566 */     GlStateManager.func_179118_c();
/* 567 */     drawSettingRects(lastSetting, false);
/*     */     
/* 569 */     drawExtendedGradient(lastSetting, false);
/* 570 */     drawExtendedLine(lastSetting);
/*     */     
/* 572 */     renderHoverRect(this.moduleName + this.setting.getName(), mouseX, mouseY, 2.0F, -1.0F, false);
/*     */     
/* 574 */     GlStateManager.func_179141_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHovered(int mouseX, int mouseY) {
/* 579 */     if (!anyExpanded) {
/* 580 */       return (mouseX >= Math.min(this.x, this.x + this.width) && mouseX <= Math.max(this.x, this.x + this.width) && mouseY >= Math.min(this.y, this.y + this.height) && mouseY <= Math.max(this.y, this.y + this.height));
/*     */     }
/*     */     
/* 583 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 590 */     if (!this.colorExpanded) {
/* 591 */       if (!this.setting.isVisible() || !isHovered(mouseX, mouseY))
/* 592 */         return false; 
/* 593 */       if (mouseButton == 0) {
/* 594 */         anyExpanded = !anyExpanded;
/* 595 */         this.colorExpanded = !this.colorExpanded;
/* 596 */         SoundUtil.playButtonClick();
/*     */       } 
/*     */     } else {
/*     */       
/* 600 */       if (mouseButton == 0 && Mouse.isButtonDown(0)) {
/* 601 */         this.colorSomewhereClicked = !this.colorSomewhereClicked;
/*     */       }
/* 603 */       SoundUtil.playButtonClick();
/*     */     } 
/* 605 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 610 */     return this.setting.isVisible();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 615 */     return this.setting.getDescription();
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\gui\components\ColorButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */