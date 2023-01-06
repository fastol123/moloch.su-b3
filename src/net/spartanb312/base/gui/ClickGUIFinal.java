/*     */ package net.spartanb312.base.gui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.module.modules.client.Blur;
/*     */ import me.thediamondsword5.moloch.module.modules.client.MoreClickGUI;
/*     */ import me.thediamondsword5.moloch.module.modules.client.Particles;
/*     */ import me.thediamondsword5.moloch.utils.graphics.ParticleUtil;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiUtilRenderComponents;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraftforge.client.event.GuiScreenEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.spartanb312.base.client.FontManager;
/*     */ import net.spartanb312.base.client.GUIManager;
/*     */ import net.spartanb312.base.client.ModuleManager;
/*     */ import net.spartanb312.base.gui.renderers.ClickGUIRenderer;
/*     */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*     */ import net.spartanb312.base.utils.math.Pair;
/*     */ import net.spartanb312.base.utils.math.Vec2I;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClickGUIFinal
/*     */   extends GuiScreen
/*     */ {
/*  39 */   private static Minecraft mc = Minecraft.func_71410_x();
/*  40 */   public int flag = 0;
/*     */   
/*     */   public static ClickGUIFinal instance;
/*     */   
/*  44 */   public static Pair<String, Vec2I> description = null;
/*  45 */   public static int white = (new Color(255, 255, 255, 255)).getRGB();
/*     */   public static boolean descriptionHubDragging = false;
/*     */   public static int descriptionHubHeight;
/*     */   public static int descriptionHubX;
/*     */   public static int descriptionHubY;
/*     */   public static int descriptionHubX2;
/*     */   public static int descriptionHubY2;
/*     */   static boolean descriptionBoxAnimationFlag = false;
/*  53 */   static Timer descriptionBoxAnimationTimer = new Timer();
/*  54 */   static float descriptionBoxAnimationThreader = 0.0F;
/*  55 */   static int lastIndex = 0;
/*  56 */   public static int previousIndex = 0;
/*  57 */   static Timer descriptionTextAnimationTimer = new Timer();
/*  58 */   public static float descriptionTextAnimationThreader = 0.0F;
/*     */   static boolean isTransitioningOutTextFlag = false;
/*     */   static boolean noAlphaTextFlag = false;
/*  61 */   static String lastText = "";
/*  62 */   public static String previousText = "";
/*  63 */   public static String staticString = "";
/*  64 */   Timer guiAnimateTimer = new Timer();
/*  65 */   float delta = 0.0F;
/*     */   
/*     */   static Panel panel;
/*     */ 
/*     */   
/*     */   public ClickGUIFinal() {
/*  71 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClicked(int mouseX, int mouseY, int mouseButton, int startX, int startY, int endX, int endY) {
/*  76 */     if (Mouse.getEventButton() == mouseButton && Mouse.isButtonDown(mouseButton) && mouseX >= Math.min(startX, endX) && mouseX <= Math.max(startX, endX) && mouseY >= Math.min(startY, endY) && mouseY <= Math.max(startY, endY))
/*     */     {
/*  78 */       return true;
/*     */     }
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_146281_b() {
/*  92 */     if ((Minecraft.func_71410_x()).field_71460_t.func_147706_e() != null) {
/*  93 */       (Minecraft.func_71410_x()).field_71460_t.func_147706_e().func_148021_a();
/*     */     }
/*  95 */     if (ModuleManager.getModule(ClickGUI.class).isEnabled()) {
/*  96 */       ModuleManager.getModule(ClickGUI.class).disable();
/*     */     }
/*     */   }
/*     */   
/*     */   private float alphaFactor() {
/* 101 */     float f = this.delta / mc.field_71462_r.field_146295_m;
/* 102 */     if (f > 1.0F) f = 1.0F; 
/* 103 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/* 110 */     if (ModuleManager.getModule(Blur.class).isEnabled() && OpenGlHelper.func_148822_b() && ((Boolean)Blur.INSTANCE.blurClickGUI.getValue()).booleanValue()) {
/*     */       
/* 112 */       RenderUtils2D.drawBlurAreaPre(((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue() ? (((Float)Blur.INSTANCE.blurFactor.getValue()).floatValue() * alphaFactor()) : ((Float)Blur.INSTANCE.blurFactor.getValue()).floatValue(), partialTicks);
/* 113 */       RenderUtils2D.drawBlurRect(Tessellator.func_178181_a(), Tessellator.func_178181_a().func_178180_c(), 0.0F, 0.0F, mc.field_71443_c, mc.field_71440_d);
/* 114 */       RenderUtils2D.drawBlurAreaPost();
/*     */     } 
/*     */     
/* 117 */     RenderUtils2D.prepareGl();
/* 118 */     if (((Boolean)ClickGUI.instance.backgroundColor.getValue()).booleanValue()) {
/*     */       
/* 120 */       if (((Boolean)ClickGUI.instance.gradient.getValue()).booleanValue()) {
/*     */         
/* 122 */         GlStateManager.func_179118_c();
/* 123 */         Color trColor = ((Color)ClickGUI.instance.trColor.getValue()).getColorColor();
/* 124 */         Color tlColor = ((Color)ClickGUI.instance.tlColor.getValue()).getColorColor();
/* 125 */         Color brColor = ((Color)ClickGUI.instance.brColor.getValue()).getColorColor();
/* 126 */         Color blColor = ((Color)ClickGUI.instance.blColor.getValue()).getColorColor();
/* 127 */         RenderUtils2D.drawCustomRect(0.0F, 0.0F, this.field_146294_l, this.field_146295_m, (new Color(trColor.getRed(), trColor.getGreen(), trColor.getBlue(), ((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.trColor.getValue()).getAlpha() * alphaFactor()) : ((Color)ClickGUI.instance.trColor.getValue()).getAlpha())).getRGB(), (new Color(tlColor.getRed(), tlColor.getGreen(), tlColor.getBlue(), ((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.tlColor.getValue()).getAlpha() * alphaFactor()) : ((Color)ClickGUI.instance.tlColor.getValue()).getAlpha())).getRGB(), (new Color(blColor.getRed(), blColor.getGreen(), blColor.getBlue(), ((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.blColor.getValue()).getAlpha() * alphaFactor()) : ((Color)ClickGUI.instance.blColor.getValue()).getAlpha())).getRGB(), (new Color(brColor.getRed(), brColor.getGreen(), brColor.getBlue(), ((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.brColor.getValue()).getAlpha() * alphaFactor()) : ((Color)ClickGUI.instance.brColor.getValue()).getAlpha())).getRGB());
/* 128 */         GlStateManager.func_179141_d();
/*     */       }
/*     */       else {
/*     */         
/* 132 */         Color bgColor = ((Color)ClickGUI.instance.bgColor.getValue()).getColorColor();
/* 133 */         RenderUtils2D.drawRect(0.0F, 0.0F, this.field_146294_l, this.field_146295_m, (new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), ((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.bgColor.getValue()).getAlpha() * alphaFactor()) : ((Color)ClickGUI.instance.bgColor.getValue()).getAlpha())).getRGB());
/*     */       } 
/* 135 */       MinecraftForge.EVENT_BUS.post((Event)new GuiScreenEvent.BackgroundDrawnEvent(this));
/*     */     } 
/*     */     
/* 138 */     description = null;
/*     */     
/* 140 */     if (GUIManager.isParticle()) {
/*     */       
/* 142 */       ParticleUtil.render();
/* 143 */       RenderUtils2D.prepareGl();
/* 144 */       GL11.glEnable(3042);
/*     */     } 
/*     */     
/* 147 */     if (((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue()) {
/*     */ 
/*     */       
/* 150 */       if (ModuleManager.getModule(ClickGUI.class).isDisabled()) {
/*     */         
/* 152 */         if (this.guiAnimateTimer.passed(1.0D)) {
/*     */           
/* 154 */           this.delta -= (mc.field_71462_r.field_146295_m - this.delta + 1.0F) * ((Float)ClickGUI.instance.guiMoveSpeed.getValue()).floatValue();
/* 155 */           this.guiAnimateTimer.reset();
/*     */         } 
/*     */         
/* 158 */         if (this.delta <= 0.0F) {
/* 159 */           this.delta = 0.0F;
/*     */         }
/*     */       } else {
/*     */         
/* 163 */         if (this.guiAnimateTimer.passed(1.0D)) {
/*     */           
/* 165 */           this.delta += (mc.field_71462_r.field_146295_m - this.delta) / 5.0F * ((Float)ClickGUI.instance.guiMoveSpeed.getValue()).floatValue();
/* 166 */           this.guiAnimateTimer.reset();
/*     */         } 
/*     */         
/* 169 */         if (this.delta >= mc.field_71462_r.field_146295_m)
/*     */         {
/* 171 */           this.delta = mc.field_71462_r.field_146295_m;
/*     */         }
/*     */       } 
/*     */       
/* 175 */       GL11.glTranslatef(0.0F, mc.field_71462_r.field_146295_m - this.delta, 0.0F);
/*     */     } 
/*     */     
/* 178 */     ClickGUIRenderer.instance.drawScreen(mouseX, mouseY, this.delta, partialTicks);
/*     */     
/* 180 */     this.flag = 1;
/*     */     
/* 182 */     if (description != null)
/*     */     {
/* 184 */       if (MoreClickGUI.instance.descriptionMode.getValue() == MoreClickGUI.DescriptionMode.MouseTag) {
/*     */         
/* 186 */         RenderUtils2D.drawRect((((Vec2I)description.b).x + 10), ((Vec2I)description.b).y, (((Vec2I)description.b).x + 12 + FontManager.getWidth((String)description.a)), (((Vec2I)description.b).y + FontManager.getHeight() + 4), -2063597568);
/* 187 */         RenderUtils2D.drawRectOutline((((Vec2I)description.b).x + 10), ((Vec2I)description.b).y, (((Vec2I)description.b).x + 12 + FontManager.getWidth((String)description.a)), (((Vec2I)description.b).y + FontManager.getHeight() + 4), GUIManager.getColor4I(), false, false);
/* 188 */         FontManager.draw((String)description.a, (((Vec2I)description.b).x + 11), (((Vec2I)description.b).y + 4), white);
/*     */       } 
/*     */     }
/*     */     
/* 192 */     if (MoreClickGUI.instance.descriptionMode.getValue() == MoreClickGUI.DescriptionMode.Hub)
/*     */     {
/* 194 */       drawDescriptionHub(mouseX, mouseY, descriptionHubDragging);
/*     */     }
/*     */     
/* 197 */     if (((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue()) {
/* 198 */       GL11.glTranslatef(0.0F, this.delta - mc.field_71462_r.field_146295_m, 0.0F);
/*     */     }
/* 200 */     if (GUIManager.isParticle()) {
/* 201 */       GL11.glDisable(3042);
/*     */     }
/* 203 */     if (ModuleManager.getModule(ClickGUI.class).isDisabled() && ((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue())
/*     */     {
/* 205 */       if (mc.field_71462_r instanceof ClickGUIFinal && this.delta <= 0.0F) {
/*     */         
/* 207 */         if (Particles.INSTANCE.isEnabled()) {
/* 208 */           ParticleUtil.clearParticles();
/*     */         }
/* 210 */         mc.func_147108_a(null);
/*     */       } 
/*     */     }
/* 213 */     RenderUtils2D.releaseGl();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73864_a(int mouseX, int mouseY, int mouseButton) {
/* 219 */     ClickGUIRenderer.instance.mouseClicked(mouseX, mouseY, mouseButton);
/* 220 */     if (mouseX >= Math.min(descriptionHubX, descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubLength.getValue()).intValue()) && mouseX <= Math.max(descriptionHubX, descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubLength.getValue()).intValue()) && mouseY >= Math.min(descriptionHubY, descriptionHubY + descriptionHubHeight) && mouseY <= Math.max(descriptionHubY, descriptionHubY + descriptionHubHeight)) {
/*     */       
/* 222 */       descriptionHubDragging = true;
/* 223 */       descriptionHubX2 = descriptionHubX - mouseX;
/* 224 */       descriptionHubY2 = descriptionHubY - mouseY;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73869_a(char typedChar, int keyCode) {
/* 231 */     ClickGUIRenderer.instance.keyTyped(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_146286_b(int mouseX, int mouseY, int state) {
/* 237 */     ClickGUIRenderer.instance.mouseReleased(mouseX, mouseY, state);
/* 238 */     descriptionHubDragging = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawDescriptionHub(int mouseX, int mouseY, boolean isDescriptionHubDragging) {
/* 243 */     descriptionHubHeight = ((Integer)MoreClickGUI.instance.descriptionModeHubInitialHeight.getValue()).intValue();
/* 244 */     Color descriptionHubColor = new Color(((Color)MoreClickGUI.instance.descriptionModeHubColor.getValue()).getColorColor().getRed(), ((Color)MoreClickGUI.instance.descriptionModeHubColor.getValue()).getColorColor().getGreen(), ((Color)MoreClickGUI.instance.descriptionModeHubColor.getValue()).getColorColor().getBlue(), ((Color)MoreClickGUI.instance.descriptionModeHubColor.getValue()).getAlpha());
/*     */     
/* 246 */     if (isDescriptionHubDragging) {
/*     */       
/* 248 */       descriptionHubX = descriptionHubX2 + mouseX;
/* 249 */       descriptionHubY = descriptionHubY2 + mouseY;
/*     */     } 
/*     */     
/* 252 */     float extraRectHeight = 0.0F;
/* 253 */     ArrayList<ITextComponent> lineList = new ArrayList<>();
/* 254 */     int index = 0;
/*     */     
/* 256 */     if (((Boolean)MoreClickGUI.instance.descriptionModeHubDescriptionAnimation.getValue()).booleanValue()) {
/*     */       
/* 258 */       String currentDescription = (description != null) ? (String)description.a : "";
/*     */       
/* 260 */       if (lastText != currentDescription) {
/*     */         
/* 262 */         if (!isTransitioningOutTextFlag) {
/* 263 */           previousText = lastText;
/*     */         }
/* 265 */         isTransitioningOutTextFlag = true;
/*     */       } 
/*     */ 
/*     */       
/* 269 */       if (description != null) {
/* 270 */         lastText = (String)description.a;
/* 271 */       } else if (descriptionTextAnimationThreader > 0.0F) {
/* 272 */         isTransitioningOutTextFlag = true;
/*     */       } 
/*     */     } 
/* 275 */     if (descriptionTextAnimationThreader <= 0.0F) {
/* 276 */       isTransitioningOutTextFlag = false;
/*     */     }
/* 278 */     TextComponentString textComponentString = new TextComponentString(""), textComponentString = textComponentString;
/*     */     
/* 280 */     if (isTransitioningOutTextFlag && ((Boolean)MoreClickGUI.instance.descriptionModeHubDescriptionAnimation.getValue()).booleanValue()) {
/* 281 */       textComponentString = new TextComponentString(previousText);
/*     */     }
/* 283 */     if (description != null && (!isTransitioningOutTextFlag || !((Boolean)MoreClickGUI.instance.descriptionModeHubDescriptionAnimation.getValue()).booleanValue())) {
/* 284 */       textComponentString = new TextComponentString((String)description.a);
/*     */     }
/* 286 */     if (MoreClickGUI.instance.descriptionModeHubDescriptionFont.getValue() != MoreClickGUI.DescriptionModeHubDesTextFont.Minecraft) {
/* 287 */       lineList = (ArrayList<ITextComponent>)FontManager.splitTextCFont((ITextComponent)textComponentString, (int)((((Integer)MoreClickGUI.instance.descriptionModeHubLength.getValue()).intValue() - ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionXOffset.getValue()).intValue() - ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionXBoundingOffset.getValue()).intValue()) / ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue()), FontManager.descriptionHubDesTextFontRenderer());
/*     */     } else {
/* 289 */       lineList = (ArrayList<ITextComponent>)GuiUtilRenderComponents.func_178908_a((ITextComponent)textComponentString, (int)((((Integer)MoreClickGUI.instance.descriptionModeHubLength.getValue()).intValue() - ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionXOffset.getValue()).intValue() - ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionXBoundingOffset.getValue()).intValue()) / ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue()), (Minecraft.func_71410_x()).field_71466_p, false, false);
/*     */     } 
/*     */     
/* 292 */     index = lineList.size();
/*     */     
/* 294 */     if (((Boolean)MoreClickGUI.instance.descriptionModeHubAnimation.getValue()).booleanValue()) {
/*     */       
/* 296 */       if (lastIndex != index) previousIndex = lastIndex;
/*     */       
/* 298 */       if (description != null) lastIndex = index;
/*     */       
/* 300 */       int passedms = (int)descriptionBoxAnimationTimer.hasPassed();
/* 301 */       if (passedms < 1000)
/*     */       {
/* 303 */         for (int i = 0; i <= passedms; i++) {
/*     */           
/* 305 */           descriptionBoxAnimationThreader += (((description == null && (!((Boolean)MoreClickGUI.instance.descriptionModeHubDescriptionAnimation.getValue()).booleanValue() || descriptionTextAnimationThreader <= 0.0F)) || lastIndex < previousIndex) ? -1.0F : 1.0F) * ((Float)MoreClickGUI.instance.descriptionModeHubAnimationSpeed.getValue()).floatValue() / 200.0F;
/*     */           
/* 307 */           if ((descriptionBoxAnimationThreader >= FontManager.getHeightDescriptionHubDesText() * ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue() * (lastIndex - 1) && lastIndex >= previousIndex) || (descriptionBoxAnimationThreader <= 
/* 308 */             FontManager.getHeightDescriptionHubDesText() * ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue() * (lastIndex - 1) && lastIndex < previousIndex)) {
/* 309 */             descriptionBoxAnimationThreader = FontManager.getHeightDescriptionHubDesText() * ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue() * (lastIndex - 1);
/*     */           }
/* 311 */           if (descriptionBoxAnimationThreader <= 0.0F)
/* 312 */             descriptionBoxAnimationThreader = 0.0F; 
/*     */         } 
/*     */       }
/* 315 */       descriptionBoxAnimationTimer.reset();
/*     */       
/* 317 */       extraRectHeight = descriptionBoxAnimationThreader;
/*     */     }
/* 319 */     else if (description != null || !((Boolean)MoreClickGUI.instance.descriptionModeHubDescriptionAnimation.getValue()).booleanValue() || descriptionTextAnimationThreader >= 0.0F) {
/* 320 */       extraRectHeight = FontManager.getHeightDescriptionHubDesText() * ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue() * (index - 1);
/*     */     } 
/*     */     
/* 323 */     if (((Boolean)MoreClickGUI.instance.descriptionModeHubShadow.getValue()).booleanValue()) {
/* 324 */       RenderUtils2D.drawBetterRoundRectFade(descriptionHubX, descriptionHubY - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F), (descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubLength.getValue()).intValue()), (descriptionHubY + descriptionHubHeight) + ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue() * ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue() + (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? 0.0F : extraRectHeight), ((Float)MoreClickGUI.instance.descriptionModeHubShadowSize.getValue()).floatValue(), 70.0F, false, ((Boolean)MoreClickGUI.instance.descriptionModeHubShadowCenterRect.getValue()).booleanValue(), false, (new Color(0, 0, 0, ((Integer)MoreClickGUI.instance.descriptionModeHubShadowAlpha.getValue()).intValue())).getRGB());
/*     */     }
/* 326 */     if (((Boolean)MoreClickGUI.instance.descriptionModeHubRounded.getValue()).booleanValue()) {
/* 327 */       RenderUtils2D.drawRoundedRect(descriptionHubX, descriptionHubY - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F), ((Float)MoreClickGUI.instance.descriptionModeHubRoundedRadius.getValue()).floatValue(), (descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubLength.getValue()).intValue()), (descriptionHubY + descriptionHubHeight) + ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue() * ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue() + (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? 0.0F : extraRectHeight), false, ((Boolean)MoreClickGUI.instance.descriptionModeHubRoundedTopRight.getValue()).booleanValue(), ((Boolean)MoreClickGUI.instance.descriptionModeHubRoundedTopLeft.getValue()).booleanValue(), ((Boolean)MoreClickGUI.instance.descriptionModeHubRoundedBottomRight.getValue()).booleanValue(), ((Boolean)MoreClickGUI.instance.descriptionModeHubRoundedBottomLeft.getValue()).booleanValue(), descriptionHubColor.getRGB());
/*     */     } else {
/* 329 */       RenderUtils2D.drawRect(descriptionHubX, descriptionHubY - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F), (descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubLength.getValue()).intValue()), (descriptionHubY + descriptionHubHeight) + ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue() * ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue() + (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? 0.0F : extraRectHeight), descriptionHubColor.getRGB());
/*     */     } 
/* 331 */     if (((Boolean)MoreClickGUI.instance.descriptionModeHubOutline.getValue()).booleanValue())
/*     */     {
/* 333 */       if (((Boolean)MoreClickGUI.instance.descriptionModeHubRounded.getValue()).booleanValue()) {
/* 334 */         RenderUtils2D.drawCustomRoundedRectOutline(descriptionHubX, descriptionHubY - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F), (descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubLength.getValue()).intValue()), (descriptionHubY + descriptionHubHeight) + ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue() * ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue() + (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? 0.0F : extraRectHeight), ((Float)MoreClickGUI.instance.descriptionModeHubRoundedRadius.getValue()).floatValue(), ((Float)MoreClickGUI.instance.descriptionModeHubOutlineWidth.getValue()).floatValue(), ((Boolean)MoreClickGUI.instance.descriptionModeHubRoundedTopRight.getValue()).booleanValue(), ((Boolean)MoreClickGUI.instance.descriptionModeHubRoundedTopLeft.getValue()).booleanValue(), ((Boolean)MoreClickGUI.instance.descriptionModeHubRoundedBottomRight.getValue()).booleanValue(), ((Boolean)MoreClickGUI.instance.descriptionModeHubRoundedBottomLeft.getValue()).booleanValue(), false, false, ((Color)MoreClickGUI.instance.descriptionModeHubOutlineColor.getValue()).getColor());
/*     */       } else {
/* 336 */         RenderUtils2D.drawRectOutline(descriptionHubX, descriptionHubY - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F), (descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubLength.getValue()).intValue()), (descriptionHubY + descriptionHubHeight) + ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue() * ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue() + (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? 0.0F : extraRectHeight), ((Float)MoreClickGUI.instance.descriptionModeHubOutlineWidth.getValue()).floatValue(), ((Color)MoreClickGUI.instance.descriptionModeHubOutlineColor.getValue()).getColor(), false, false);
/*     */       } 
/*     */     }
/* 339 */     if (((Boolean)MoreClickGUI.instance.descriptionModeHubBar.getValue()).booleanValue()) {
/*     */       
/* 341 */       if (((Boolean)MoreClickGUI.instance.descriptionModeHubBarRound.getValue()).booleanValue()) {
/* 342 */         RenderUtils2D.drawRoundedRect(descriptionHubX, descriptionHubY - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F), ((Float)MoreClickGUI.instance.descriptionModeHubBarRoundRadius.getValue()).floatValue(), (descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubLength.getValue()).intValue()), descriptionHubY + ((Float)MoreClickGUI.instance.descriptionModeHubBarHeight.getValue()).floatValue() - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F), false, ((Boolean)MoreClickGUI.instance.descriptionModeHubBarRoundTopRight.getValue()).booleanValue(), ((Boolean)MoreClickGUI.instance.descriptionModeHubBarRoundTopLeft.getValue()).booleanValue(), ((Boolean)MoreClickGUI.instance.descriptionModeHubBarRoundBottomRight.getValue()).booleanValue(), ((Boolean)MoreClickGUI.instance.descriptionModeHubBarRoundBottomLeft.getValue()).booleanValue(), ((Color)MoreClickGUI.instance.descriptionModeHubBarColor.getValue()).getColor());
/*     */       } else {
/* 344 */         RenderUtils2D.drawRect(descriptionHubX, descriptionHubY - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F), (descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubLength.getValue()).intValue()), descriptionHubY + ((Float)MoreClickGUI.instance.descriptionModeHubBarHeight.getValue()).floatValue() - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F), ((Color)MoreClickGUI.instance.descriptionModeHubBarColor.getValue()).getColor());
/*     */       } 
/* 346 */       if (((Boolean)MoreClickGUI.instance.descriptionModeHubBarGlow.getValue()).booleanValue()) {
/* 347 */         RenderUtils2D.drawRoundedRectFade(descriptionHubX + ((Float)MoreClickGUI.instance.descriptionModeHubBarGlowXOffset.getValue()).floatValue() - ((Float)MoreClickGUI.instance.descriptionModeHubBarGlowWidth.getValue()).floatValue() / 2.0F, descriptionHubY - ((Float)MoreClickGUI.instance.descriptionModeHubBarGlowHeight.getValue()).floatValue() / 2.0F + ((Float)MoreClickGUI.instance.descriptionModeHubBarGlowYOffset.getValue()).floatValue() - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F), 1.0F, true, false, (descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubLength.getValue()).intValue()) + ((Float)MoreClickGUI.instance.descriptionModeHubBarGlowWidth.getValue()).floatValue() / 2.0F + ((Float)MoreClickGUI.instance.descriptionModeHubBarGlowXOffset.getValue()).floatValue(), descriptionHubY + ((Float)MoreClickGUI.instance.descriptionModeHubBarHeight.getValue()).floatValue() + ((Float)MoreClickGUI.instance.descriptionModeHubBarGlowHeight.getValue()).floatValue() / 2.0F + ((Float)MoreClickGUI.instance.descriptionModeHubBarGlowYOffset.getValue()).floatValue() - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F), ((Color)MoreClickGUI.instance.descriptionModeHubBarGlowColor.getValue()).getColor());
/*     */       }
/*     */     } 
/*     */     
/* 351 */     int desHubTextAlpha = ((Color)MoreClickGUI.instance.descriptionModeHubDescriptionColor.getValue()).getAlpha();
/*     */     
/* 353 */     if (((Boolean)MoreClickGUI.instance.descriptionModeHubDescriptionAnimation.getValue()).booleanValue()) {
/*     */ 
/*     */       
/* 356 */       int passedms = (int)descriptionTextAnimationTimer.hasPassed();
/* 357 */       if (passedms < 1000)
/*     */       {
/* 359 */         for (int i = 0; i <= passedms; i++) {
/*     */           
/* 361 */           descriptionTextAnimationThreader += ((description == null || isTransitioningOutTextFlag || extraRectHeight < FontManager.getHeightDescriptionHubDesText() * ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue() * (index - 1)) ? -1.0F : 1.0F) * ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionAnimationSpeed.getValue()).floatValue() / 4.0F;
/*     */           
/* 363 */           if (descriptionTextAnimationThreader >= 300.0F) {
/* 364 */             descriptionTextAnimationThreader = 300.0F;
/*     */           }
/* 366 */           if (descriptionTextAnimationThreader <= 0.0F) {
/*     */             
/* 368 */             isTransitioningOutTextFlag = false;
/* 369 */             descriptionTextAnimationThreader = 0.0F;
/*     */           } 
/*     */         } 
/*     */       }
/* 373 */       descriptionTextAnimationTimer.reset();
/*     */       
/* 375 */       desHubTextAlpha = (int)(desHubTextAlpha / 300.0F * descriptionTextAnimationThreader);
/*     */       
/* 377 */       if (desHubTextAlpha <= 4) {
/* 378 */         desHubTextAlpha = 4;
/*     */       }
/*     */     } 
/*     */     
/* 382 */     int desHubTextColor = (new Color(((Color)MoreClickGUI.instance.descriptionModeHubDescriptionColor.getValue()).getColorColor().getRed(), ((Color)MoreClickGUI.instance.descriptionModeHubDescriptionColor.getValue()).getColorColor().getGreen(), ((Color)MoreClickGUI.instance.descriptionModeHubDescriptionColor.getValue()).getColorColor().getBlue(), desHubTextAlpha)).getRGB();
/*     */ 
/*     */     
/* 385 */     if (MoreClickGUI.instance.descriptionModeHubDescriptionFont.getValue() != MoreClickGUI.DescriptionModeHubDesTextFont.Minecraft) {
/* 386 */       lineList = (ArrayList<ITextComponent>)FontManager.splitTextCFont((ITextComponent)textComponentString, (int)((((Integer)MoreClickGUI.instance.descriptionModeHubLength.getValue()).intValue() - ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionXOffset.getValue()).intValue() - ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionXBoundingOffset.getValue()).intValue()) / ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue()), FontManager.descriptionHubDesTextFontRenderer());
/*     */     } else {
/* 388 */       lineList = (ArrayList<ITextComponent>)GuiUtilRenderComponents.func_178908_a((ITextComponent)textComponentString, (int)((((Integer)MoreClickGUI.instance.descriptionModeHubLength.getValue()).intValue() - ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionXOffset.getValue()).intValue() - ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionXBoundingOffset.getValue()).intValue()) / ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue()), (Minecraft.func_71410_x()).field_71466_p, false, false);
/*     */     } 
/* 390 */     int index2 = -1;
/*     */ 
/*     */     
/* 393 */     if (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue()) {
/* 394 */       GL11.glTranslatef(0.0F, FontManager.getHeightDescriptionHubDesText() * ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue() * (lineList.size() - 1) * -1.0F, 0.0F);
/*     */     }
/* 396 */     GL11.glTranslatef((descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionXOffset.getValue()).intValue()) * (1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue()), (descriptionHubY + FontManager.getHeightDescriptionHubDesText() - ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextYOffset.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionYOffset.getValue()).intValue() + FontManager.getHeightDescriptionHubDesText() - ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue()) * (1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue()), 0.0F);
/* 397 */     GL11.glScalef(((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue(), ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue(), 1.0F);
/*     */     
/* 399 */     for (ITextComponent lines : lineList) {
/*     */       
/* 401 */       index2++;
/*     */       
/* 403 */       String currentLineStr = lines.func_150254_d();
/* 404 */       if (currentLineStr != null && currentLineStr.length() > 0)
/*     */       {
/* 406 */         if (String.valueOf(currentLineStr.charAt(0)).equals(" ")) {
/* 407 */           currentLineStr = currentLineStr.substring(1);
/*     */         }
/*     */       }
/* 410 */       if (MoreClickGUI.instance.descriptionModeHubDescriptionFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Minecraft) {
/*     */         
/* 412 */         GL11.glEnable(3553);
/* 413 */         mc.field_71466_p.func_175065_a(currentLineStr, (descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionXOffset.getValue()).intValue()), (descriptionHubY + FontManager.getHeightDescriptionHubDesText() - ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextYOffset.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionYOffset.getValue()).intValue() + FontManager.getHeightDescriptionHubDesText() - ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue() + FontManager.getHeightDescriptionHubDesText() * index2), desHubTextColor, ((Boolean)MoreClickGUI.instance.descriptionModeHubDescriptionShadow.getValue()).booleanValue());
/* 414 */         GL11.glDisable(3553);
/*     */         continue;
/*     */       } 
/* 417 */       if (((Boolean)MoreClickGUI.instance.descriptionModeHubDescriptionShadow.getValue()).booleanValue()) {
/* 418 */         FontManager.drawDesTextShadow(currentLineStr, (descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionXOffset.getValue()).intValue()), (descriptionHubY + FontManager.getHeightDescriptionHubDesText() - ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextYOffset.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionYOffset.getValue()).intValue() + FontManager.getHeightDescriptionHubDesText() - ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue() + FontManager.getHeightDescriptionHubDesText() * index2), desHubTextColor); continue;
/*     */       } 
/* 420 */       FontManager.drawDesText(currentLineStr, (descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionXOffset.getValue()).intValue()), (descriptionHubY + FontManager.getHeightDescriptionHubDesText() - ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextYOffset.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionYOffset.getValue()).intValue() + FontManager.getHeightDescriptionHubDesText() - ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue() + FontManager.getHeightDescriptionHubDesText() * index2), desHubTextColor);
/*     */     } 
/*     */ 
/*     */     
/* 424 */     GL11.glScalef(1.0F / ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue(), 1.0F / ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue(), 1.0F);
/* 425 */     GL11.glTranslatef((descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionXOffset.getValue()).intValue()) * (1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue()) * -1.0F, (descriptionHubY + FontManager.getHeightDescriptionHubDesText() - ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextYOffset.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubDescriptionYOffset.getValue()).intValue() + FontManager.getHeightDescriptionHubDesText() - ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue()) * (1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */     
/* 427 */     if (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue()) {
/* 428 */       GL11.glTranslatef(0.0F, FontManager.getHeightDescriptionHubDesText() * ((Float)MoreClickGUI.instance.descriptionModeHubDescriptionSize.getValue()).floatValue() * (lineList.size() - 1), 0.0F);
/*     */     }
/* 430 */     if (((Boolean)MoreClickGUI.instance.descriptionModeHubBarIconBG.getValue()).booleanValue()) {
/*     */       
/* 432 */       float endX = descriptionHubX + 10.0F + ((Float)MoreClickGUI.instance.descriptionModeHubBarIconBGSideX.getValue()).floatValue();
/*     */       
/* 434 */       GL11.glTranslatef((1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubBarIconBGScaleOutside.getValue()).floatValue()) * (endX - descriptionHubX) / 2.0F, (1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubBarIconBGScaleOutside.getValue()).floatValue()) * ((Float)MoreClickGUI.instance.descriptionModeHubBarHeight.getValue()).floatValue() / 2.0F, 0.0F);
/*     */       
/* 436 */       GL11.glTranslatef(descriptionHubX * (1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubBarIconBGScaleOutside.getValue()).floatValue()), (descriptionHubY - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F)) * (1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubBarIconBGScaleOutside.getValue()).floatValue()), 0.0F);
/* 437 */       GL11.glScalef(((Float)MoreClickGUI.instance.descriptionModeHubBarIconBGScaleOutside.getValue()).floatValue(), ((Float)MoreClickGUI.instance.descriptionModeHubBarIconBGScaleOutside.getValue()).floatValue(), ((Float)MoreClickGUI.instance.descriptionModeHubBarIconBGScaleOutside.getValue()).floatValue());
/*     */       
/* 439 */       RenderUtils2D.drawCustomCategoryRoundedRect(descriptionHubX, descriptionHubY - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F), endX, descriptionHubY + ((Float)MoreClickGUI.instance.descriptionModeHubBarHeight.getValue()).floatValue() - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F), ((Float)MoreClickGUI.instance.descriptionModeHubBarRoundRadius.getValue()).floatValue(), false, ((Boolean)MoreClickGUI.instance.descriptionModeHubBarRound.getValue()).booleanValue() ? ((Boolean)MoreClickGUI.instance.descriptionModeHubBarRoundTopLeft.getValue()).booleanValue() : false, false, ((Boolean)MoreClickGUI.instance.descriptionModeHubBarRound.getValue()).booleanValue() ? ((Boolean)MoreClickGUI.instance.descriptionModeHubBarRoundBottomLeft.getValue()).booleanValue() : false, ((Boolean)MoreClickGUI.instance.descriptionModeHubBarIconBGFade.getValue()).booleanValue(), false, ((Float)MoreClickGUI.instance.descriptionModeHubBarIconBGFadeSize.getValue()).floatValue(), ((Color)MoreClickGUI.instance.descriptionModeHubBarIconBGColor.getValue()).getColor());
/*     */       
/* 441 */       GL11.glScalef(1.0F / ((Float)MoreClickGUI.instance.descriptionModeHubBarIconBGScaleOutside.getValue()).floatValue(), 1.0F / ((Float)MoreClickGUI.instance.descriptionModeHubBarIconBGScaleOutside.getValue()).floatValue(), 1.0F / ((Float)MoreClickGUI.instance.descriptionModeHubBarIconBGScaleOutside.getValue()).floatValue());
/* 442 */       GL11.glTranslatef(descriptionHubX * (1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubBarIconBGScaleOutside.getValue()).floatValue()) * -1.0F, (descriptionHubY - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F)) * (1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubBarIconBGScaleOutside.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */       
/* 444 */       GL11.glTranslatef((1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubBarIconBGScaleOutside.getValue()).floatValue()) * (endX - descriptionHubX) / 2.0F * -1.0F, (1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubBarIconBGScaleOutside.getValue()).floatValue()) * ((Float)MoreClickGUI.instance.descriptionModeHubBarHeight.getValue()).floatValue() / 2.0F * -1.0F, 0.0F);
/*     */     } 
/*     */     
/* 447 */     if (((Boolean)MoreClickGUI.instance.descriptionModeHubHeaderText.getValue()).booleanValue()) {
/*     */       
/* 449 */       GL11.glTranslatef((descriptionHubX + FontManager.getIconWidth() * ((Float)MoreClickGUI.instance.descriptionModeHubIconSize.getValue()).floatValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubIconXOffset.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextXOffset.getValue()).intValue()) * (1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubHeaderTextSize.getValue()).floatValue()), ((descriptionHubY + FontManager.getHeightDescriptionHubHeaderText() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextYOffset.getValue()).intValue()) - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F)) * (1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubHeaderTextSize.getValue()).floatValue()), 0.0F);
/* 450 */       GL11.glScalef(((Float)MoreClickGUI.instance.descriptionModeHubHeaderTextSize.getValue()).floatValue(), ((Float)MoreClickGUI.instance.descriptionModeHubHeaderTextSize.getValue()).floatValue(), 1.0F);
/*     */       
/* 452 */       int desHubHeaderTextColor = ((Color)MoreClickGUI.instance.descriptionModeHubHeaderTextColor.getValue()).getColor();
/* 453 */       if (MoreClickGUI.instance.descriptionModeHubDesTextFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Minecraft) {
/*     */         
/* 455 */         if (((Boolean)MoreClickGUI.instance.descriptionModeHubBarTextShadow.getValue()).booleanValue())
/*     */         {
/* 457 */           RenderUtils2D.drawBetterRoundRectFade((float)((descriptionHubX + FontManager.getIconWidth() * ((Float)MoreClickGUI.instance.descriptionModeHubIconSize.getValue()).floatValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubIconXOffset.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextXOffset.getValue()).intValue()) - ((Float)MoreClickGUI.instance.descriptionModeHubBarTextShadowWidth.getValue()).floatValue() / 2.0D), (float)(((descriptionHubY + FontManager.getHeightDescriptionHubHeaderText() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextYOffset.getValue()).intValue()) - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F)) - ((Float)MoreClickGUI.instance.descriptionModeHubBarTextShadowHeight.getValue()).floatValue() / 2.0D), (float)((descriptionHubX + FontManager.getIconWidth() * ((Float)MoreClickGUI.instance.descriptionModeHubIconSize.getValue()).floatValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubIconXOffset.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextXOffset.getValue()).intValue() + mc.field_71466_p.func_78256_a("Description")) + ((Float)MoreClickGUI.instance.descriptionModeHubBarTextShadowWidth.getValue()).floatValue() / 2.0D), (float)(((descriptionHubY + FontManager.getHeightDescriptionHubHeaderText() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextYOffset.getValue()).intValue()) - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F)) + ((Float)MoreClickGUI.instance.descriptionModeHubBarTextShadowHeight.getValue()).floatValue() / 2.0D), ((Float)MoreClickGUI.instance.descriptionModeHubBarTextShadowSize.getValue()).floatValue(), 70.0F, false, true, false, (new Color(0, 0, 0, ((Integer)MoreClickGUI.instance.descriptionModeHubBarTextShadowAlpha.getValue()).intValue())).getRGB());
/*     */         }
/*     */         
/* 460 */         (Minecraft.func_71410_x()).field_71466_p.func_175065_a("Description", descriptionHubX + FontManager.getIconWidth() * ((Float)MoreClickGUI.instance.descriptionModeHubIconSize.getValue()).floatValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubIconXOffset.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextXOffset.getValue()).intValue(), (descriptionHubY + FontManager.getHeightDescriptionHubHeaderText() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextYOffset.getValue()).intValue()) - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F), desHubHeaderTextColor, ((Boolean)MoreClickGUI.instance.descriptionModeHubDesTextShadow.getValue()).booleanValue());
/*     */       }
/*     */       else {
/*     */         
/* 464 */         if (((Boolean)MoreClickGUI.instance.descriptionModeHubBarTextShadow.getValue()).booleanValue())
/*     */         {
/* 466 */           RenderUtils2D.drawBetterRoundRectFade((float)((descriptionHubX + FontManager.getIconWidth() * ((Float)MoreClickGUI.instance.descriptionModeHubIconSize.getValue()).floatValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubIconXOffset.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextXOffset.getValue()).intValue()) - ((Float)MoreClickGUI.instance.descriptionModeHubBarTextShadowWidth.getValue()).floatValue() / 2.0D), (float)(((descriptionHubY + FontManager.getHeightDescriptionHubHeaderText() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextYOffset.getValue()).intValue()) - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F)) - ((Float)MoreClickGUI.instance.descriptionModeHubBarTextShadowHeight.getValue()).floatValue() / 2.0D), (float)((descriptionHubX + FontManager.getIconWidth() * ((Float)MoreClickGUI.instance.descriptionModeHubIconSize.getValue()).floatValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubIconXOffset.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextXOffset.getValue()).intValue() + FontManager.fontRenderer.getStringWidth("Description")) + ((Float)MoreClickGUI.instance.descriptionModeHubBarTextShadowWidth.getValue()).floatValue() / 2.0D), (float)(((descriptionHubY + FontManager.getHeightDescriptionHubHeaderText() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextYOffset.getValue()).intValue()) - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F)) + ((Float)MoreClickGUI.instance.descriptionModeHubBarTextShadowHeight.getValue()).floatValue() / 2.0D), ((Float)MoreClickGUI.instance.descriptionModeHubBarTextShadowSize.getValue()).floatValue(), 70.0F, false, true, false, (new Color(0, 0, 0, ((Integer)MoreClickGUI.instance.descriptionModeHubBarTextShadowAlpha.getValue()).intValue())).getRGB());
/*     */         }
/*     */         
/* 469 */         if (((Boolean)MoreClickGUI.instance.descriptionModeHubDesTextShadow.getValue()).booleanValue()) {
/* 470 */           FontManager.drawHeaderTextShadow("Description", descriptionHubX + FontManager.getIconWidth() * ((Float)MoreClickGUI.instance.descriptionModeHubIconSize.getValue()).floatValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubIconXOffset.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextXOffset.getValue()).intValue(), (descriptionHubY + FontManager.getHeightDescriptionHubHeaderText() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextYOffset.getValue()).intValue()) - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F), desHubHeaderTextColor);
/*     */         } else {
/* 472 */           FontManager.drawHeaderText("Description", descriptionHubX + FontManager.getIconWidth() * ((Float)MoreClickGUI.instance.descriptionModeHubIconSize.getValue()).floatValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubIconXOffset.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextXOffset.getValue()).intValue(), (descriptionHubY + FontManager.getHeightDescriptionHubHeaderText() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextYOffset.getValue()).intValue()) - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F), desHubHeaderTextColor);
/*     */         } 
/*     */       } 
/* 475 */       GL11.glScalef(1.0F / ((Float)MoreClickGUI.instance.descriptionModeHubHeaderTextSize.getValue()).floatValue(), 1.0F / ((Float)MoreClickGUI.instance.descriptionModeHubHeaderTextSize.getValue()).floatValue(), 1.0F);
/* 476 */       GL11.glTranslatef((descriptionHubX + FontManager.getIconWidth() * ((Float)MoreClickGUI.instance.descriptionModeHubIconSize.getValue()).floatValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubIconXOffset.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextXOffset.getValue()).intValue()) * (1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubHeaderTextSize.getValue()).floatValue()) * -1.0F, ((descriptionHubY + FontManager.getHeightDescriptionHubHeaderText() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextYOffset.getValue()).intValue()) - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? extraRectHeight : 0.0F)) * (1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubHeaderTextSize.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */     } 
/*     */     
/* 479 */     if (((Boolean)MoreClickGUI.instance.descriptionModeHubIcon.getValue()).booleanValue()) {
/*     */       
/* 481 */       GL11.glTranslatef((descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubIconXOffset.getValue()).intValue()) * (1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubIconSize.getValue()).floatValue()), (descriptionHubY + FontManager.getHeightDescriptionHubHeaderText() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextYOffset.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubIconYOffset.getValue()).intValue() - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? (int)extraRectHeight : 0)) * (1.0F - ((Float)MoreClickGUI.instance.descriptionModeHubIconSize.getValue()).floatValue()), 0.0F);
/* 482 */       GL11.glScalef(((Float)MoreClickGUI.instance.descriptionModeHubIconSize.getValue()).floatValue(), ((Float)MoreClickGUI.instance.descriptionModeHubIconSize.getValue()).floatValue(), 1.0F);
/*     */       
/* 484 */       Color desHubIconColor = new Color(((Color)MoreClickGUI.instance.descriptionModeHubIconColor.getValue()).getColorColor().getRed(), ((Color)MoreClickGUI.instance.descriptionModeHubIconColor.getValue()).getColorColor().getGreen(), ((Color)MoreClickGUI.instance.descriptionModeHubIconColor.getValue()).getColorColor().getBlue(), ((Color)MoreClickGUI.instance.descriptionModeHubIconColor.getValue()).getAlpha());
/* 485 */       FontManager.drawModuleMiniIcon("6", descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubIconXOffset.getValue()).intValue(), descriptionHubY + FontManager.getHeightDescriptionHubHeaderText() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeaderTextYOffset.getValue()).intValue() + ((Integer)MoreClickGUI.instance.descriptionModeHubIconYOffset.getValue()).intValue() - (((Boolean)MoreClickGUI.instance.descriptionModeHubExpandUp.getValue()).booleanValue() ? (int)extraRectHeight : 0), desHubIconColor);
/*     */       
/* 487 */       GL11.glScalef(1.0F / ((Float)MoreClickGUI.instance.descriptionModeHubIconSize.getValue()).floatValue(), 1.0F / ((Float)MoreClickGUI.instance.descriptionModeHubIconSize.getValue()).floatValue(), 1.0F);
/* 488 */       GL11.glScalef(((Float)MoreClickGUI.instance.descriptionModeHubIconSize.getValue()).floatValue() * -1.0F, ((Float)MoreClickGUI.instance.descriptionModeHubIconSize.getValue()).floatValue() * -1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\gui\ClickGUIFinal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */