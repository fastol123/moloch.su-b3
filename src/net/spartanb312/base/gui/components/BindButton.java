/*     */ package net.spartanb312.base.gui.components;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.HashMap;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.module.modules.client.CustomFont;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.spartanb312.base.client.FontManager;
/*     */ import net.spartanb312.base.core.common.KeyBind;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.gui.Component;
/*     */ import net.spartanb312.base.gui.Panel;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*     */ import net.spartanb312.base.utils.ColorUtil;
/*     */ import net.spartanb312.base.utils.SoundUtil;
/*     */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class BindButton
/*     */   extends Component
/*     */ {
/*     */   Setting<KeyBind> setting;
/*     */   boolean accepting = false;
/*     */   String moduleName;
/*     */   
/*     */   public BindButton(Setting<KeyBind> setting, int width, int height, Panel father, Module module) {
/*  29 */     this.setting = setting;
/*  30 */     this.width = width;
/*  31 */     this.height = height;
/*  32 */     this.father = father;
/*  33 */     this.moduleName = module.name;
/*     */   }
/*     */   
/*  36 */   public static HashMap<String, Integer> storedBindWaitingLoops = new HashMap<>();
/*  37 */   public static String staticString = "";
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY, float translateDelta, float partialTicks) {
/*  41 */     GlStateManager.func_179118_c();
/*     */     
/*  43 */     Color textBindColor = new Color(((Color)ClickGUI.instance.bindButtonTextColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.bindButtonTextColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.bindButtonTextColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.bindButtonTextColor.getValue()).getAlpha());
/*     */     
/*  45 */     if (((Boolean)ClickGUI.instance.bindButtonFancy.getValue()).booleanValue()) {
/*  46 */       float rectWidth; Color keyRectColor = new Color(((Color)ClickGUI.instance.bindButtonKeyColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.bindButtonKeyColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.bindButtonKeyColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.bindButtonTextColor.getValue()).getAlpha());
/*  47 */       Color keyStrColor = new Color(((Color)ClickGUI.instance.bindButtonKeyStringColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.bindButtonKeyStringColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.bindButtonKeyStringColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.bindButtonKeyStringColor.getValue()).getAlpha());
/*     */       
/*  49 */       Color keyRectWaitingColor = new Color(((Color)ClickGUI.instance.bindButtonFancyWaitingRectColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.bindButtonFancyWaitingRectColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.bindButtonFancyWaitingRectColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.bindButtonFancyWaitingRectColor.getValue()).getAlpha());
/*     */ 
/*     */       
/*  52 */       if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Minecraft) {
/*  53 */         GL11.glEnable(3553);
/*  54 */         GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/*  55 */         GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */         
/*  57 */         this.mc.field_71466_p.func_175065_a(this.setting.getName(), (this.x + 5), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F), textBindColor.getRGB(), ((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue());
/*     */         
/*  59 */         GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*  60 */         GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*  61 */         GL11.glDisable(3553);
/*     */       } else {
/*     */         
/*  64 */         GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/*  65 */         GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */         
/*  67 */         if (((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue()) {
/*  68 */           FontManager.drawShadow(this.setting.getName(), (this.x + 5), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), textBindColor.getRGB());
/*     */         } else {
/*     */           
/*  71 */           FontManager.draw(this.setting.getName(), (this.x + 5), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), textBindColor.getRGB());
/*     */         } 
/*     */         
/*  74 */         GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*  75 */         GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/*  80 */       if (((KeyBind)this.setting.getValue()).getKeyCode() == 54 || ((KeyBind)this.setting.getValue()).getKeyCode() == 42 || ((KeyBind)this.setting.getValue()).getKeyCode() == 28 || ((KeyBind)this.setting.getValue()).getKeyCode() == 57 || ((KeyBind)this.setting.getValue()).getKeyCode() == 58 || ((KeyBind)this.setting.getValue()).getKeyCode() == 15 || ((KeyBind)this.setting.getValue()).getKeyCode() == 41 || ((KeyBind)this.setting.getValue()).getKeyCode() == 210 || ((KeyBind)this.setting.getValue()).getKeyCode() == 27 || ((KeyBind)this.setting.getValue()).getKeyCode() == 26 || ((KeyBind)this.setting.getValue()).getKeyCode() == 39 || ((KeyBind)this.setting.getValue()).getKeyCode() == 146 || ((KeyBind)this.setting.getValue()).getKeyCode() == 1 || ((KeyBind)this.setting.getValue()).getKeyCode() == 29 || ((KeyBind)this.setting.getValue()).getKeyCode() == 157 || ((KeyBind)this.setting.getValue()).getKeyCode() == 12 || ((KeyBind)this.setting.getValue()).getKeyCode() == 13 || ((KeyBind)this.setting.getValue()).getKeyCode() == 56 || ((KeyBind)this.setting.getValue()).getKeyCode() == 184 || ((KeyBind)this.setting.getValue()).getKeyCode() == 69 || ((KeyBind)this.setting.getValue()).getKeyCode() == 82 || ((KeyBind)this.setting.getValue()).getKeyCode() == 79 || ((KeyBind)this.setting.getValue()).getKeyCode() == 80 || ((KeyBind)this.setting.getValue()).getKeyCode() == 81 || ((KeyBind)this.setting.getValue()).getKeyCode() == 75 || ((KeyBind)this.setting.getValue()).getKeyCode() == 76 || ((KeyBind)this.setting.getValue()).getKeyCode() == 77 || ((KeyBind)this.setting.getValue()).getKeyCode() == 71 || ((KeyBind)this.setting.getValue()).getKeyCode() == 72 || ((KeyBind)this.setting.getValue()).getKeyCode() == 73 || ((KeyBind)this.setting.getValue()).getKeyCode() == 52 || ((KeyBind)this.setting.getValue()).getKeyCode() == 181 || ((KeyBind)this.setting.getValue()).getKeyCode() == 51 || ((KeyBind)this.setting.getValue()).getKeyCode() == 53 || ((KeyBind)this.setting.getValue()).getKeyCode() == 43 || ((KeyBind)this.setting.getValue()).getKeyCode() == 78 || ((KeyBind)this.setting.getValue()).getKeyCode() == 74 || ((KeyBind)this.setting.getValue()).getKeyCode() == 55 || ((KeyBind)this.setting.getValue()).getKeyCode() == 199 || ((KeyBind)this.setting.getValue()).getKeyCode() == 207 || ((KeyBind)this.setting.getValue()).getKeyCode() == 201 || ((KeyBind)this.setting.getValue()).getKeyCode() == 209 || ((KeyBind)this.setting.getValue()).getKeyCode() == 183) {
/*  81 */         rectWidth = (FontManager.getKeyBindWidth(Keyboard.getKeyName(((KeyBind)this.setting.getValue()).getKeyCode())) + 2);
/*     */       } else {
/*     */         
/*  84 */         rectWidth = 9.0F;
/*     */       } 
/*  86 */       if (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue()) {
/*  87 */         if (((Boolean)ClickGUI.instance.bindButtonFancyRounded.getValue()).booleanValue()) {
/*  88 */           RenderUtils2D.drawCustomRoundedRectOutline((this.x + this.width - 3) - rectWidth, this.y + this.height / 2.0F - 4.5F, (this.x + this.width - 3), this.y + this.height / 2.0F + 4.5F, ((Float)ClickGUI.instance.bindButtonFancyRoundedRadius.getValue()).floatValue(), ((Float)ClickGUI.instance.bindButtonFancyOutlineWidth.getValue()).floatValue(), true, true, true, true, false, false, keyRectColor.getRGB());
/*  89 */           RenderUtils2D.drawRoundedRect((this.x + this.width - 3) - rectWidth + ((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F, this.y + this.height / 2.0F - 4.5F + ((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F, ((Float)ClickGUI.instance.bindButtonFancyRoundedRadius.getValue()).floatValue(), (this.x + this.width - 3) - ((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F, this.y + this.height / 2.0F + 4.5F - ((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F, false, true, true, true, true, keyRectColor.getRGB());
/*     */         } else {
/*     */           
/*  92 */           RenderUtils2D.drawRectOutline((this.x + this.width - 3) - rectWidth, this.y + this.height / 2.0F - 4.5F, (this.x + this.width - 3), this.y + this.height / 2.0F + 4.5F, ((Float)ClickGUI.instance.bindButtonFancyOutlineWidth.getValue()).floatValue(), keyRectColor.getRGB(), false, false);
/*  93 */           RenderUtils2D.drawRect((this.x + this.width - 3) - rectWidth + ((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F, this.y + this.height / 2.0F - 4.5F + ((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F, (this.x + this.width - 3) - ((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F, this.y + this.height / 2.0F + 4.5F - ((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F, keyRectColor.getRGB());
/*     */         }
/*     */       
/*     */       }
/*  97 */       else if (((Boolean)ClickGUI.instance.bindButtonFancyRounded.getValue()).booleanValue()) {
/*  98 */         RenderUtils2D.drawRoundedRect((this.x + this.width - 3) - rectWidth, this.y + this.height / 2.0F - 4.5F, ((Float)ClickGUI.instance.bindButtonFancyRoundedRadius.getValue()).floatValue(), (this.x + this.width - 3), this.y + this.height / 2.0F + 4.5F, false, true, true, true, true, keyRectColor.getRGB());
/*     */       } else {
/*     */         
/* 101 */         RenderUtils2D.drawRect((this.x + this.width - 3) - rectWidth, this.y + this.height / 2.0F - 4.5F, (this.x + this.width - 3), this.y + this.height / 2.0F + 4.5F, keyRectColor.getRGB());
/*     */       } 
/*     */ 
/*     */       
/* 105 */       if ((((Boolean)ClickGUI.instance.bindButtonFancyWaitingAnimate.getValue()).booleanValue() && ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() != ClickGUI.BindButtonColoredRectAnimateMode.None) || (((Boolean)ClickGUI.instance.bindButtonFancyWaitingDots.getValue()).booleanValue() && ((Boolean)ClickGUI.instance.bindButtonFancyWaitingAnimate.getValue()).booleanValue())) {
/* 106 */         storedBindWaitingLoops.putIfAbsent(this.setting.getName() + this.moduleName, Integer.valueOf(0));
/* 107 */         int animateLoops = ((Integer)storedBindWaitingLoops.get(this.setting.getName() + this.moduleName)).intValue();
/*     */         
/* 109 */         if (this.accepting) {
/* 110 */           animateLoops = (int)(animateLoops + ((Float)ClickGUI.instance.bindButtonFancyWaitingAnimateFactor.getValue()).floatValue() * 10.0F);
/*     */         } else {
/*     */           
/* 113 */           animateLoops = (int)(animateLoops - ((Float)ClickGUI.instance.bindButtonFancyWaitingAnimateFactor.getValue()).floatValue() * 10.0F);
/*     */         } 
/*     */         
/* 116 */         if (animateLoops >= 300) {
/* 117 */           animateLoops = 300;
/*     */         }
/* 119 */         if (animateLoops <= 0) {
/* 120 */           animateLoops = 0;
/*     */         }
/* 122 */         storedBindWaitingLoops.put(this.setting.getName() + this.moduleName, Integer.valueOf(animateLoops));
/*     */       } 
/*     */ 
/*     */       
/* 126 */       if (((Boolean)ClickGUI.instance.bindButtonFancyWaitingRect.getValue()).booleanValue()) {
/* 127 */         if (((Boolean)ClickGUI.instance.bindButtonFancyWaitingAnimate.getValue()).booleanValue() && ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() != ClickGUI.BindButtonColoredRectAnimateMode.None) {
/*     */           
/* 129 */           if (ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Alpha || ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Both) {
/* 130 */             keyRectWaitingColor = new Color(keyRectWaitingColor.getRed(), keyRectWaitingColor.getGreen(), keyRectWaitingColor.getBlue(), (int)(((Color)ClickGUI.instance.bindButtonFancyWaitingRectColor.getValue()).getAlpha() / 300.0F * ((Integer)storedBindWaitingLoops.get(this.setting.getName() + this.moduleName)).intValue()));
/*     */           }
/*     */           
/* 133 */           float rightX = (this.x + this.width - 3) - (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F);
/* 134 */           float leftX = (this.x + this.width - 3) + (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F);
/* 135 */           if (((Boolean)ClickGUI.instance.bindButtonFancyRounded.getValue()).booleanValue()) {
/* 136 */             RenderUtils2D.drawRoundedRect((ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Scale || ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Both) ? (leftX - rectWidth / 2.0F - ((Integer)storedBindWaitingLoops.get(this.setting.getName() + this.moduleName)).intValue() * rectWidth / 2.0F / 300.0F) : (leftX - rectWidth), (ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Scale || ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Both) ? (this.y + this.height / 2.0F - ((Integer)storedBindWaitingLoops.get(this.setting.getName() + this.moduleName)).intValue() * (4.5F - (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F)) / 300.0F) : (this.y + this.height / 2.0F - 4.5F + (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F)), ((Float)ClickGUI.instance.bindButtonFancyRoundedRadius.getValue()).floatValue(), (ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Scale || ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Both) ? (rightX - rectWidth / 2.0F + ((Integer)storedBindWaitingLoops.get(this.setting.getName() + this.moduleName)).intValue() * rectWidth / 2.0F / 300.0F) : rightX, (ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Scale || ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Both) ? (this.y + this.height / 2.0F + ((Integer)storedBindWaitingLoops.get(this.setting.getName() + this.moduleName)).intValue() * (4.5F - (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F)) / 300.0F) : (this.y + this.height / 2.0F + 4.5F - (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F)), false, true, true, true, true, keyRectWaitingColor.getRGB());
/*     */           } else {
/*     */             
/* 139 */             RenderUtils2D.drawRect((ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Scale || ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Both) ? (leftX - rectWidth / 2.0F - ((Integer)storedBindWaitingLoops.get(this.setting.getName() + this.moduleName)).intValue() * rectWidth / 2.0F / 300.0F) : (leftX - rectWidth), (ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Scale || ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Both) ? (this.y + this.height / 2.0F - ((Integer)storedBindWaitingLoops.get(this.setting.getName() + this.moduleName)).intValue() * (4.5F - (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F)) / 300.0F) : (this.y + this.height / 2.0F - 4.5F + (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F)), (ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Scale || ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Both) ? (rightX - rectWidth / 2.0F + ((Integer)storedBindWaitingLoops.get(this.setting.getName() + this.moduleName)).intValue() * rectWidth / 2.0F / 300.0F) : rightX, (ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Scale || ClickGUI.instance.bindButtonColoredRectAnimateMode.getValue() == ClickGUI.BindButtonColoredRectAnimateMode.Both) ? (this.y + this.height / 2.0F + ((Integer)storedBindWaitingLoops.get(this.setting.getName() + this.moduleName)).intValue() * (4.5F - (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F)) / 300.0F) : (this.y + this.height / 2.0F + 4.5F - (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F)), keyRectWaitingColor.getRGB());
/*     */           }
/*     */         
/*     */         }
/* 143 */         else if (!((Boolean)ClickGUI.instance.bindButtonFancyWaitingAnimate.getValue()).booleanValue() && this.accepting) {
/* 144 */           if (((Boolean)ClickGUI.instance.bindButtonFancyRounded.getValue()).booleanValue()) {
/* 145 */             RenderUtils2D.drawRoundedRect((this.x + this.width - 3) - rectWidth + (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F), this.y + this.height / 2.0F - 4.5F + (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F), ((Float)ClickGUI.instance.bindButtonFancyRoundedRadius.getValue()).floatValue(), (this.x + this.width - 3) - (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F), this.y + this.height / 2.0F + 4.5F - (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F), false, true, true, true, true, keyRectWaitingColor.getRGB());
/*     */           } else {
/*     */             
/* 148 */             RenderUtils2D.drawRect((this.x + this.width - 3) - rectWidth + (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F), this.y + this.height / 2.0F - 4.5F + (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F), (this.x + this.width - 3) - (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F), this.y + this.height / 2.0F + 4.5F - (((Boolean)ClickGUI.instance.bindButtonFancyOutline.getValue()).booleanValue() ? (((Float)ClickGUI.instance.bindButtonFancyOutlineOffset.getValue()).floatValue() / 2.0F) : 0.0F), keyRectWaitingColor.getRGB());
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 154 */       if (((Boolean)ClickGUI.instance.bindButtonFancyWaitingDots.getValue()).booleanValue()) {
/* 155 */         Color waitingDotsColor1 = ((Color)ClickGUI.instance.bindButtonFancyWaitingDotsColor.getValue()).getColorColor();
/* 156 */         Color waitingDotsColor = new Color(waitingDotsColor1.getRed(), waitingDotsColor1.getGreen(), waitingDotsColor1.getBlue(), ((Color)ClickGUI.instance.bindButtonFancyWaitingDotsColor.getValue()).getAlpha());
/*     */ 
/*     */         
/* 159 */         if (((Boolean)ClickGUI.instance.bindButtonFancyWaitingAnimate.getValue()).booleanValue()) {
/* 160 */           waitingDotsColor = new Color(waitingDotsColor.getRed(), waitingDotsColor.getGreen(), waitingDotsColor.getBlue(), (int)(((Color)ClickGUI.instance.bindButtonFancyWaitingDotsColor.getValue()).getAlpha() / 300.0F * ((Integer)storedBindWaitingLoops.get(this.setting.getName() + this.moduleName)).intValue()));
/* 161 */           Color waitingDotsColorAlphaSave = waitingDotsColor;
/*     */           
/* 163 */           if (((Boolean)ClickGUI.instance.bindButtonFancyWaitingDotsRollingBrightnessAnimate.getValue()).booleanValue()) {
/* 164 */             waitingDotsColor = ColorUtil.rolledBrightness(waitingDotsColor, ((Float)ClickGUI.instance.bindButtonFancyWaitingAnimateDotsRollingBrightnessAnimateMaxBright.getValue()).floatValue(), ((Float)ClickGUI.instance.bindButtonFancyWaitingAnimateDotsRollingBrightnessAnimateMinBright.getValue()).floatValue(), ((Float)ClickGUI.instance.bindButtonFancyWaitingAnimateDotsRollingBrightnessAnimateFactor.getValue()).floatValue(), (this.x + this.width - 3) + ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsRadius.getValue()).floatValue() + ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsGap.getValue()).floatValue() - rectWidth / 2.0F, ((Float)ClickGUI.instance.bindButtonFancyWaitingAnimateDotsRollingBrightnessAnimateRollLength.getValue()).floatValue() / 2.0F, (ClickGUI.instance.bindButtonFancyWaitingDotsRollingBrightnessRollDirection.getValue() == ClickGUI.BindButtonWaitingDotsRolledBrightnessDirection.Right), false);
/*     */           }
/*     */           
/* 167 */           RenderUtils2D.drawCircle((this.x + this.width - 3) + ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsRadius.getValue()).floatValue() + ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsGap.getValue()).floatValue() - rectWidth / 2.0F, this.y + this.height / 2.0F + 2.0F, ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsRadius.getValue()).floatValue(), (new Color(waitingDotsColor.getRed(), waitingDotsColor.getGreen(), waitingDotsColor.getBlue(), waitingDotsColorAlphaSave.getAlpha())).getRGB());
/*     */           
/* 169 */           if (((Boolean)ClickGUI.instance.bindButtonFancyWaitingDotsRollingBrightnessAnimate.getValue()).booleanValue()) {
/* 170 */             waitingDotsColor = ColorUtil.rolledBrightness(waitingDotsColor, ((Float)ClickGUI.instance.bindButtonFancyWaitingAnimateDotsRollingBrightnessAnimateMaxBright.getValue()).floatValue(), ((Float)ClickGUI.instance.bindButtonFancyWaitingAnimateDotsRollingBrightnessAnimateMinBright.getValue()).floatValue(), ((Float)ClickGUI.instance.bindButtonFancyWaitingAnimateDotsRollingBrightnessAnimateFactor.getValue()).floatValue(), (this.x + this.width - 3) - rectWidth / 2.0F, ((Float)ClickGUI.instance.bindButtonFancyWaitingAnimateDotsRollingBrightnessAnimateRollLength.getValue()).floatValue() / 2.0F, (ClickGUI.instance.bindButtonFancyWaitingDotsRollingBrightnessRollDirection.getValue() == ClickGUI.BindButtonWaitingDotsRolledBrightnessDirection.Right), false);
/*     */           }
/*     */           
/* 173 */           RenderUtils2D.drawCircle((this.x + this.width - 3) - rectWidth / 2.0F, this.y + this.height / 2.0F + 2.0F, ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsRadius.getValue()).floatValue(), (new Color(waitingDotsColor.getRed(), waitingDotsColor.getGreen(), waitingDotsColor.getBlue(), waitingDotsColorAlphaSave.getAlpha())).getRGB());
/*     */           
/* 175 */           if (((Boolean)ClickGUI.instance.bindButtonFancyWaitingDotsRollingBrightnessAnimate.getValue()).booleanValue()) {
/* 176 */             waitingDotsColor = ColorUtil.rolledBrightness(waitingDotsColor, ((Float)ClickGUI.instance.bindButtonFancyWaitingAnimateDotsRollingBrightnessAnimateMaxBright.getValue()).floatValue(), ((Float)ClickGUI.instance.bindButtonFancyWaitingAnimateDotsRollingBrightnessAnimateMinBright.getValue()).floatValue(), ((Float)ClickGUI.instance.bindButtonFancyWaitingAnimateDotsRollingBrightnessAnimateFactor.getValue()).floatValue(), (this.x + this.width - 3) - ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsRadius.getValue()).floatValue() + ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsGap.getValue()).floatValue() - rectWidth / 2.0F, ((Float)ClickGUI.instance.bindButtonFancyWaitingAnimateDotsRollingBrightnessAnimateRollLength.getValue()).floatValue() / 2.0F, (ClickGUI.instance.bindButtonFancyWaitingDotsRollingBrightnessRollDirection.getValue() == ClickGUI.BindButtonWaitingDotsRolledBrightnessDirection.Right), false);
/*     */           }
/*     */           
/* 179 */           RenderUtils2D.drawCircle((this.x + this.width - 3) - ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsRadius.getValue()).floatValue() + ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsGap.getValue()).floatValue() - rectWidth / 2.0F, this.y + this.height / 2.0F + 2.0F, ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsRadius.getValue()).floatValue(), (new Color(waitingDotsColor.getRed(), waitingDotsColor.getGreen(), waitingDotsColor.getBlue(), waitingDotsColorAlphaSave.getAlpha())).getRGB());
/*     */         
/*     */         }
/* 182 */         else if (!((Boolean)ClickGUI.instance.bindButtonFancyWaitingAnimate.getValue()).booleanValue() && this.accepting) {
/*     */           
/* 184 */           RenderUtils2D.drawCircle((this.x + this.width - 3) + ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsRadius.getValue()).floatValue() + ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsGap.getValue()).floatValue() - rectWidth / 2.0F, this.y + this.height / 2.0F + 2.0F, ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsRadius.getValue()).floatValue(), waitingDotsColor.getRGB());
/* 185 */           RenderUtils2D.drawCircle((this.x + this.width - 3) - rectWidth / 2.0F, this.y + this.height / 2.0F + 2.0F, ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsRadius.getValue()).floatValue(), waitingDotsColor.getRGB());
/* 186 */           RenderUtils2D.drawCircle((this.x + this.width - 3) - ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsRadius.getValue()).floatValue() + ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsGap.getValue()).floatValue() - rectWidth / 2.0F, this.y + this.height / 2.0F + 2.0F, ((Float)ClickGUI.instance.bindButtonFancyWaitingDotsRadius.getValue()).floatValue(), waitingDotsColor.getRGB());
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 192 */       if (((KeyBind)this.setting.getValue()).getKeyCode() != 0 && !this.accepting) {
/* 193 */         GL11.glTranslatef(((this.x + this.width - 3) - rectWidth + 2.0F + ((Float)ClickGUI.instance.bindButtonKeyStrX.getValue()).floatValue()) * (1.0F - ((Float)ClickGUI.instance.bindButtonKeyStrScale.getValue()).floatValue()), (this.y + this.height / 2.0F + ((Float)ClickGUI.instance.bindButtonKeyStrY.getValue()).floatValue()) * (1.0F - ((Float)ClickGUI.instance.bindButtonKeyStrScale.getValue()).floatValue()), 0.0F);
/* 194 */         GL11.glScalef(((Float)ClickGUI.instance.bindButtonKeyStrScale.getValue()).floatValue(), ((Float)ClickGUI.instance.bindButtonKeyStrScale.getValue()).floatValue(), ((Float)ClickGUI.instance.bindButtonKeyStrScale.getValue()).floatValue());
/* 195 */         if (ClickGUI.instance.bindButtonKeyStrFont.getValue() == ClickGUI.KeyBindFancyFont.Minecraft) {
/* 196 */           GL11.glEnable(3553);
/* 197 */           this.mc.field_71466_p.func_175065_a(Keyboard.getKeyName(((KeyBind)this.setting.getValue()).getKeyCode()), (this.x + this.width - 3) - rectWidth + 2.0F + ((Float)ClickGUI.instance.bindButtonKeyStrX.getValue()).floatValue(), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.bindButtonKeyStrY.getValue()).floatValue(), keyStrColor.getRGB(), false);
/* 198 */           GL11.glDisable(3553);
/*     */         } else {
/*     */           
/* 201 */           FontManager.drawKeyBind(Keyboard.getKeyName(((KeyBind)this.setting.getValue()).getKeyCode()), (this.x + this.width - 3) - rectWidth + 2.0F + ((Float)ClickGUI.instance.bindButtonKeyStrX.getValue()).floatValue(), this.y + this.height / 2.0F + ((Float)ClickGUI.instance.bindButtonKeyStrY.getValue()).floatValue(), keyStrColor.getRGB());
/*     */         } 
/* 203 */         GL11.glScalef(1.0F / ((Float)ClickGUI.instance.bindButtonKeyStrScale.getValue()).floatValue(), 1.0F / ((Float)ClickGUI.instance.bindButtonKeyStrScale.getValue()).floatValue(), 1.0F / ((Float)ClickGUI.instance.bindButtonKeyStrScale.getValue()).floatValue());
/* 204 */         GL11.glTranslatef(((this.x + this.width - 3) - rectWidth + 2.0F + ((Float)ClickGUI.instance.bindButtonKeyStrX.getValue()).floatValue()) * (1.0F - ((Float)ClickGUI.instance.bindButtonKeyStrScale.getValue()).floatValue()) * -1.0F, (this.y + this.height / 2.0F + ((Float)ClickGUI.instance.bindButtonKeyStrY.getValue()).floatValue()) * (1.0F - ((Float)ClickGUI.instance.bindButtonKeyStrScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 210 */     else if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Minecraft) {
/* 211 */       GL11.glEnable(3553);
/* 212 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 213 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/* 215 */       this.mc.field_71466_p.func_175065_a(this.accepting ? (this.setting.getName() + " | ...") : (this.setting.getName() + " | " + ((((KeyBind)this.setting.getValue()).getKeyCode() == 0) ? "NONE" : Keyboard.getKeyName(((KeyBind)this.setting.getValue()).getKeyCode()))), (this.x + 5), 
/* 216 */           (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F), textBindColor.getRGB(), ((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue());
/*     */       
/* 218 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 219 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/* 220 */       GL11.glDisable(3553);
/*     */     } else {
/*     */       
/* 223 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/* 224 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/* 226 */       if (((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue()) {
/* 227 */         FontManager.drawShadow(this.accepting ? (this.setting.getName() + " | ...") : (this.setting.getName() + " | " + ((((KeyBind)this.setting.getValue()).getKeyCode() == 0) ? "NONE" : Keyboard.getKeyName(((KeyBind)this.setting.getValue()).getKeyCode()))), (this.x + 5), (
/* 228 */             (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), textBindColor.getRGB());
/*     */       } else {
/*     */         
/* 231 */         FontManager.draw(this.accepting ? (this.setting.getName() + " | ...") : (this.setting.getName() + " | " + ((((KeyBind)this.setting.getValue()).getKeyCode() == 0) ? "NONE" : Keyboard.getKeyName(((KeyBind)this.setting.getValue()).getKeyCode()))), (this.x + 5), (
/* 232 */             (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), textBindColor.getRGB());
/*     */       } 
/*     */       
/* 235 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/* 236 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 241 */     GlStateManager.func_179141_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public void bottomRender(int mouseX, int mouseY, boolean lastSetting, boolean firstSetting, float partialTicks) {
/* 246 */     GlStateManager.func_179118_c();
/* 247 */     drawSettingRects(lastSetting, false);
/*     */ 
/*     */     
/* 250 */     if (((Boolean)ClickGUI.instance.extendedCategoryBar.getValue()).booleanValue() && ((Boolean)ClickGUI.instance.extendedTopBars.getValue()).booleanValue() && firstSetting) {
/* 251 */       RenderUtils2D.drawRect(this.x - ((Float)ClickGUI.instance.extendedCategoryBarXScale.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.extendedCategoryBarX.getValue()).floatValue(), this.y - ((Float)ClickGUI.instance.extendedCategoryBarYScale.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.extendedCategoryBarY.getValue()).floatValue(), (this.x + this.width) + ((Float)ClickGUI.instance.extendedCategoryBarXScale.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.extendedCategoryBarX.getValue()).floatValue(), this.y + ((Float)ClickGUI.instance.extendedCategoryBarYScale.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.extendedCategoryBarY.getValue()).floatValue(), ((Color)ClickGUI.instance.extendedBarColor.getValue()).getColor());
/*     */     }
/*     */     
/* 254 */     drawExtendedGradient(lastSetting, false);
/* 255 */     drawExtendedLine(lastSetting);
/*     */     
/* 257 */     renderHoverRect(this.moduleName + this.setting.getName(), mouseX, mouseY, 2.0F, -1.0F, false);
/*     */     
/* 259 */     GlStateManager.func_179141_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public void keyTyped(char typedChar, int keyCode) {
/* 264 */     if (this.accepting) {
/* 265 */       if (keyCode == 14 || keyCode == 211) {
/* 266 */         ((KeyBind)this.setting.getValue()).setKeyCode(0);
/*     */       } else {
/* 268 */         ((KeyBind)this.setting.getValue()).setKeyCode(keyCode);
/*     */       } 
/* 270 */       this.accepting = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHovered(int mouseX, int mouseY) {
/* 276 */     if (!anyExpanded) {
/* 277 */       return (mouseX >= Math.min(this.x, this.x + this.width) && mouseX <= Math.max(this.x, this.x + this.width) && mouseY >= Math.min(this.y, this.y + this.height) && mouseY <= Math.max(this.y, this.y + this.height));
/*     */     }
/*     */     
/* 280 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 287 */     if (!isHovered(mouseX, mouseY)) {
/* 288 */       return false;
/*     */     }
/* 290 */     if (mouseButton == 0) {
/* 291 */       this.accepting = true;
/* 292 */       SoundUtil.playButtonClick();
/*     */     } 
/* 294 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 300 */     return this.setting.getDescription();
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\gui\components\BindButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */