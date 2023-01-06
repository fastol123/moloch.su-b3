/*      */ package net.spartanb312.base.gui;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.stream.Collectors;
/*      */ import me.thediamondsword5.moloch.core.common.Color;
/*      */ import me.thediamondsword5.moloch.module.modules.client.CustomFont;
/*      */ import net.minecraft.client.gui.Gui;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.spartanb312.base.client.FontManager;
/*      */ import net.spartanb312.base.client.ModuleManager;
/*      */ import net.spartanb312.base.command.Command;
/*      */ import net.spartanb312.base.gui.components.ModuleButton;
/*      */ import net.spartanb312.base.gui.renderers.ClickGUIRenderer;
/*      */ import net.spartanb312.base.gui.renderers.HUDEditorRenderer;
/*      */ import net.spartanb312.base.module.Category;
/*      */ import net.spartanb312.base.module.Module;
/*      */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*      */ import net.spartanb312.base.utils.ColorUtil;
/*      */ import net.spartanb312.base.utils.MathUtilFuckYou;
/*      */ import net.spartanb312.base.utils.Timer;
/*      */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*      */ import net.spartanb312.base.utils.graphics.font.CFontRenderer;
/*      */ import net.spartanb312.base.utils.math.Pair;
/*      */ import net.spartanb312.base.utils.math.Vec2I;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.util.vector.Vector2f;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Panel
/*      */ {
/*      */   public int x;
/*      */   public int y;
/*      */   public int width;
/*      */   public int height;
/*      */   public Category category;
/*      */   public boolean extended;
/*      */   boolean dragging;
/*      */   int x2;
/*      */   int y2;
/*      */   public static int staticY;
/*      */   public static Panel instance;
/*      */   CFontRenderer font;
/*      */   FontManager fontManager;
/*      */   public int startY;
/*   54 */   public List<ModuleButton> elements = new ArrayList<>();
/*      */   
/*      */   static boolean firstModuleButton = false;
/*      */   
/*      */   static boolean lastModuleButton = false;
/*   59 */   public static HashMap<Integer, Vector2f> categoryRectHoverParticlesList = new HashMap<>();
/*   60 */   public static HashMap<Integer, Float> categoryRectHoverParticlesOriginalYs = new HashMap<>();
/*   61 */   public static HashMap<Integer, Float> categoryRectHoverParticlesSpeed = new HashMap<>();
/*   62 */   public static HashMap<Integer, Float> categoryRectHoverParticlesTriAngle = new HashMap<>();
/*   63 */   public static HashMap<Integer, Float> categoryRectHoverParticlesTriSpinSpeed = new HashMap<>();
/*   64 */   public static HashMap<Integer, Float> categoryRectHoverParticlesSize = new HashMap<>();
/*   65 */   static int categoryRectHoverParticlesId = 0;
/*   66 */   static int renderLoopsCategoryRectHoverParticles = 0;
/*      */   
/*   68 */   public static HashMap<String, Integer> storedCategoryHoverLoops = new HashMap<>();
/*   69 */   public static HashMap<String, Float> storedCategoryTextScaleLoops = new HashMap<>();
/*   70 */   public static HashMap<String, Integer> storedCategoryShadowGradientHoverLoops = new HashMap<>();
/*   71 */   public static HashMap<String, Integer> storedHornsHoverLoops = new HashMap<>();
/*      */   
/*   73 */   public static ResourceLocation HORN = new ResourceLocation("moloch:textures/horn.png");
/*      */   
/*   75 */   static int preExtendStartY = 0;
/*      */ 
/*      */   
/*   78 */   static String staticString = "";
/*   79 */   static int staticInteger = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Timer panelTimer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Panel(Category category, int x, int y, int width, int height) {
/*   94 */     this.panelTimer = new Timer(); this.x = x; this.y = y; this.width = width; this.height = height; this.extended = true; this.dragging = false; this.category = category;
/*      */     this.font = FontManager.fontRenderer;
/*      */     instance = this;
/*   97 */     setup(); } public void setup() { for (Module m : ModuleManager.getModules()) {
/*   98 */       if (m.category == this.category) {
/*   99 */         this.elements.add(new ModuleButton(m, this.width - 10, this.height - 2, this));
/*      */       }
/*      */     }  }
/*      */ 
/*      */   
/*      */   private int outlineTopColor() {
/*  105 */     if (((Boolean)ClickGUI.instance.outlineColorGradient.getValue()).booleanValue()) {
/*  106 */       return ((Color)ClickGUI.instance.outlineTopColor.getValue()).getColor();
/*      */     }
/*  108 */     if (!((Boolean)ClickGUI.instance.outlineColorGradient.getValue()).booleanValue()) {
/*  109 */       return ((Color)ClickGUI.instance.outlineColor.getValue()).getColor();
/*      */     }
/*  111 */     return 0;
/*      */   }
/*      */   
/*      */   private int outlineDownColor() {
/*  115 */     if (((Boolean)ClickGUI.instance.outlineColorGradient.getValue()).booleanValue()) {
/*  116 */       return ((Color)ClickGUI.instance.outlineDownColor.getValue()).getColor();
/*      */     }
/*  118 */     if (!((Boolean)ClickGUI.instance.outlineColorGradient.getValue()).booleanValue()) {
/*  119 */       return ((Color)ClickGUI.instance.outlineColor.getValue()).getColor();
/*      */     }
/*  121 */     return 0;
/*      */   }
/*      */   
/*      */   private void moduleSeparators(ModuleButton button) {
/*  125 */     GlStateManager.func_179118_c();
/*      */     
/*  127 */     if (ClickGUI.instance.moduleSeparatorFadeMode.getValue() == ClickGUI.ModuleSeparatorFadeMode.Left) {
/*  128 */       if (((Boolean)ClickGUI.instance.moduleSeparatorGlow.getValue()).booleanValue()) {
/*  129 */         GlStateManager.func_179118_c();
/*  130 */         RenderUtils2D.drawCustomRect((button.x + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()) - ((Float)ClickGUI.instance.moduleSeparatorGlowHeight.getValue()).floatValue(), button.x + (Component.instance.width - 1) * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB());
/*  131 */         RenderUtils2D.drawCustomRect(button.x + (Component.instance.width - 1) * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()) - ((Float)ClickGUI.instance.moduleSeparatorGlowHeight.getValue()).floatValue(), (button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue() - ((Integer)ClickGUI.instance.moduleSeparatorWidth.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*      */         
/*  133 */         RenderUtils2D.drawCustomRect((button.x + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), button.x + (Component.instance.width - 1) * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()) + ((Float)ClickGUI.instance.moduleSeparatorGlowHeight.getValue()).floatValue(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*  134 */         RenderUtils2D.drawCustomRect(button.x + (Component.instance.width - 1) * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue() - ((Integer)ClickGUI.instance.moduleSeparatorWidth.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()) + ((Float)ClickGUI.instance.moduleSeparatorGlowHeight.getValue()).floatValue(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*  135 */         GlStateManager.func_179141_d();
/*      */       } 
/*  137 */       RenderUtils2D.drawCustomLine((button.x + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), button.x + (Component.instance.width - 1) * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), ((Integer)ClickGUI.instance.moduleSeparatorHeight.getValue()).intValue(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColor(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColor());
/*  138 */       RenderUtils2D.drawCustomLine(button.x + (Component.instance.width - 1) * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue() - ((Integer)ClickGUI.instance.moduleSeparatorWidth.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), ((Integer)ClickGUI.instance.moduleSeparatorHeight.getValue()).intValue(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColor(), (new Color(0, 0, 0, 0)).getRGB());
/*      */     }
/*  140 */     else if (ClickGUI.instance.moduleSeparatorFadeMode.getValue() == ClickGUI.ModuleSeparatorFadeMode.Right) {
/*  141 */       if (((Boolean)ClickGUI.instance.moduleSeparatorGlow.getValue()).booleanValue()) {
/*  142 */         GlStateManager.func_179118_c();
/*  143 */         RenderUtils2D.drawCustomRect((button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()) - ((Float)ClickGUI.instance.moduleSeparatorGlowHeight.getValue()).floatValue(), (button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue()) - (Component.instance.width - 1) * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB());
/*  144 */         RenderUtils2D.drawCustomRect((button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue()) - (Component.instance.width - 1) * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()) - ((Float)ClickGUI.instance.moduleSeparatorGlowHeight.getValue()).floatValue(), (button.x + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue() + ((Integer)ClickGUI.instance.moduleSeparatorWidth.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*      */         
/*  146 */         RenderUtils2D.drawCustomRect((button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue()) - (Component.instance.width - 1) * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()) + ((Float)ClickGUI.instance.moduleSeparatorGlowHeight.getValue()).floatValue(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*  147 */         RenderUtils2D.drawCustomRect((button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue()) - (Component.instance.width - 1) * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (button.x + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue() + ((Integer)ClickGUI.instance.moduleSeparatorWidth.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()) + ((Float)ClickGUI.instance.moduleSeparatorGlowHeight.getValue()).floatValue(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*  148 */         GlStateManager.func_179141_d();
/*      */       } 
/*  150 */       RenderUtils2D.drawCustomLine((button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue()) - (Component.instance.width - 1) * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), ((Integer)ClickGUI.instance.moduleSeparatorHeight.getValue()).intValue(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColor(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColor());
/*  151 */       RenderUtils2D.drawCustomLine((button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue()) - (Component.instance.width - 1) * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (button.x + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue() + ((Integer)ClickGUI.instance.moduleSeparatorWidth.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), ((Integer)ClickGUI.instance.moduleSeparatorHeight.getValue()).intValue(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColor(), (new Color(0, 0, 0, 0)).getRGB());
/*      */     }
/*  153 */     else if (ClickGUI.instance.moduleSeparatorFadeMode.getValue() == ClickGUI.ModuleSeparatorFadeMode.Both) {
/*  154 */       if (((Boolean)ClickGUI.instance.moduleSeparatorGlow.getValue()).booleanValue()) {
/*  155 */         GlStateManager.func_179118_c();
/*  156 */         RenderUtils2D.drawCustomRect((button.x + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue() + ((Integer)ClickGUI.instance.moduleSeparatorWidth.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()) - ((Float)ClickGUI.instance.moduleSeparatorGlowHeight.getValue()).floatValue(), button.x + (Component.instance.width - 1) / 2.0F * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB());
/*  157 */         RenderUtils2D.drawCustomRect((button.x + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue() + ((Integer)ClickGUI.instance.moduleSeparatorWidth.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), button.x + (Component.instance.width - 1) / 2.0F * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()) + ((Float)ClickGUI.instance.moduleSeparatorGlowHeight.getValue()).floatValue(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*      */         
/*  159 */         RenderUtils2D.drawCustomRect(button.x + (Component.instance.width - 1) / 2.0F * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()) - ((Float)ClickGUI.instance.moduleSeparatorGlowHeight.getValue()).floatValue(), (button.x + Component.instance.width - 1) - (Component.instance.width - 1) / 2.0F * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB());
/*  160 */         RenderUtils2D.drawCustomRect(button.x + (Component.instance.width - 1) / 2.0F * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (button.x + Component.instance.width - 1) - (Component.instance.width - 1) / 2.0F * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()) + ((Float)ClickGUI.instance.moduleSeparatorGlowHeight.getValue()).floatValue(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*      */         
/*  162 */         RenderUtils2D.drawCustomRect((button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue() - ((Integer)ClickGUI.instance.moduleSeparatorWidth.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()) - ((Float)ClickGUI.instance.moduleSeparatorGlowHeight.getValue()).floatValue(), (button.x + Component.instance.width - 1) - (Component.instance.width - 1) / 2.0F * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB());
/*  163 */         RenderUtils2D.drawCustomRect((button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue() - ((Integer)ClickGUI.instance.moduleSeparatorWidth.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (button.x + Component.instance.width - 1) - (Component.instance.width - 1) / 2.0F * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()) + ((Float)ClickGUI.instance.moduleSeparatorGlowHeight.getValue()).floatValue(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*  164 */         GlStateManager.func_179141_d();
/*      */       } 
/*  166 */       RenderUtils2D.drawCustomLine((button.x + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue() + ((Integer)ClickGUI.instance.moduleSeparatorWidth.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), button.x + (Component.instance.width - 1) / 2.0F * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), ((Integer)ClickGUI.instance.moduleSeparatorHeight.getValue()).intValue(), (new Color(0, 0, 0, 0)).getRGB(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColor());
/*  167 */       RenderUtils2D.drawCustomLine(button.x + (Component.instance.width - 1) / 2.0F * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (button.x + Component.instance.width - 1) - (Component.instance.width - 1) / 2.0F * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), ((Integer)ClickGUI.instance.moduleSeparatorHeight.getValue()).intValue(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColor(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColor());
/*  168 */       RenderUtils2D.drawCustomLine((button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue() - ((Integer)ClickGUI.instance.moduleSeparatorWidth.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (button.x + Component.instance.width - 1) - (Component.instance.width - 1) / 2.0F * ((Float)ClickGUI.instance.moduleSeparatorFadeLength.getValue()).floatValue() + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue(), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), ((Integer)ClickGUI.instance.moduleSeparatorHeight.getValue()).intValue(), (new Color(0, 0, 0, 0)).getRGB(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColor());
/*      */     } 
/*  170 */     GlStateManager.func_179141_d();
/*  171 */     if (ClickGUI.instance.moduleSeparatorFadeMode.getValue() == ClickGUI.ModuleSeparatorFadeMode.None) {
/*  172 */       if (((Boolean)ClickGUI.instance.moduleSeparatorGlow.getValue()).booleanValue()) {
/*  173 */         GlStateManager.func_179118_c();
/*  174 */         RenderUtils2D.drawCustomRect(button.x, (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()) - ((Float)ClickGUI.instance.moduleSeparatorGlowHeight.getValue()).floatValue(), (button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB());
/*  175 */         RenderUtils2D.drawCustomRect(button.x, (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()) + ((Float)ClickGUI.instance.moduleSeparatorGlowHeight.getValue()).floatValue(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSeparatorGlowAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*  176 */         GlStateManager.func_179141_d();
/*      */       } 
/*  178 */       RenderUtils2D.drawCustomLine(button.x, (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), (button.x + Component.instance.width - 1 + ((Integer)ClickGUI.instance.moduleSeparatorX.getValue()).intValue()), (button.y + Component.instance.height + ((Integer)ClickGUI.instance.moduleSeparatorY.getValue()).intValue()), ((Integer)ClickGUI.instance.moduleSeparatorHeight.getValue()).intValue(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColor(), ((Color)ClickGUI.instance.moduleSeparatorColor.getValue()).getColor());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void panelSideGlow(ModuleButton button) {
/*  183 */     if (ClickGUI.instance.moduleSideGlow.getValue() != ClickGUI.ModuleSideGlow.None) {
/*  184 */       GlStateManager.func_179118_c();
/*  185 */       if (ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Left || ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Both) {
/*  186 */         RenderUtils2D.drawCustomRect(button.x, (button.y - 1), button.x + ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), (button.y + Component.instance.height + 1), (new Color(0, 0, 0, 0)).getRGB(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), (new Color(0, 0, 0, 0)).getRGB());
/*      */       }
/*  188 */       if (ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Right || ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Both) {
/*  189 */         RenderUtils2D.drawCustomRect((button.x + Component.instance.width) - ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), (button.y - 1), (button.x + Component.instance.width), (button.y + Component.instance.height + 1), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor());
/*      */       }
/*  191 */       GlStateManager.func_179141_d();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void panelSideGlowBottomExtensions() {
/*  196 */     if (ClickGUI.instance.moduleSideGlow.getValue() != ClickGUI.ModuleSideGlow.None) {
/*  197 */       GlStateManager.func_179118_c();
/*  198 */       if (ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Left || ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Both) {
/*  199 */         RenderUtils2D.drawCustomRect((this.x + 5), (this.startY - Component.instance.height - 1), (this.x + 5) + ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), (this.startY + 1 + ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() - 13), (new Color(0, 0, 0, 0)).getRGB(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), (new Color(0, 0, 0, 0)).getRGB());
/*      */       }
/*  201 */       if (ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Right || ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Both) {
/*  202 */         RenderUtils2D.drawCustomRect((this.x + 5 + Component.instance.width) - ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), (this.startY - Component.instance.height - 1), (this.x + 5 + Component.instance.width), (this.startY + 1 + ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() - 13), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor());
/*      */       }
/*  204 */       GlStateManager.func_179141_d();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void calcHeight() {
/*  209 */     int index = 0;
/*  210 */     if (!this.elements.isEmpty()) {
/*  211 */       this.startY = this.y + this.height + 2;
/*  212 */       int step = 0;
/*  213 */       for (ModuleButton button : this.elements) {
/*  214 */         index++;
/*      */ 
/*      */         
/*  217 */         if (this.extended ? 
/*  218 */           !this.panelTimer.passed(((index * 25) / ((Float)ClickGUI.instance.panelOpenSpeed.getValue()).floatValue())) : 
/*      */ 
/*      */           
/*  221 */           this.panelTimer.passed((((this.elements.size() - index) * 25) / ((Float)ClickGUI.instance.panelOpenSpeed.getValue()).floatValue()))) {
/*      */           continue;
/*      */         }
/*  224 */         firstModuleButton = (index >= 1);
/*  225 */         lastModuleButton = (index == this.elements.size());
/*      */         
/*  227 */         if (step == 0 && firstModuleButton) {
/*  228 */           this.startY += (ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Top || ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Both) ? (((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() + 2) : 0;
/*  229 */           step++;
/*      */         } 
/*      */         
/*  232 */         button.y = this.startY;
/*      */ 
/*      */         
/*  235 */         this.startY += this.height - 1;
/*      */         
/*  237 */         List<Component> visibleSettings = (List<Component>)button.settings.stream().filter(Component::isVisible).collect(Collectors.toList());
/*  238 */         int settingIndex = -1;
/*  239 */         for (Component component : visibleSettings) {
/*  240 */           settingIndex++;
/*  241 */           if (button.isExtended ? 
/*  242 */             !button.buttonTimer.passed(((settingIndex * 25) / ((Float)ClickGUI.instance.moduleOpenSpeed.getValue()).floatValue())) : 
/*      */             
/*  244 */             button.buttonTimer.passed((((visibleSettings.size() - settingIndex) * 25) / ((Float)ClickGUI.instance.moduleOpenSpeed.getValue()).floatValue())))
/*      */             continue; 
/*  246 */           component.y = this.startY;
/*  247 */           if (settingIndex == visibleSettings.size() - 1 && ((Boolean)ClickGUI.instance.extendedBottomExtensions.getValue()).booleanValue()) {
/*  248 */             this.startY = (int)(this.startY + ((Float)ClickGUI.instance.extendedBottomExtensionsHeight.getValue()).floatValue());
/*      */           }
/*      */           
/*  251 */           this.startY += component.height;
/*      */         } 
/*      */         
/*  254 */         this.startY++;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void basePatternTrianglesDouble(int red, int green, int blue, int alpha, boolean outlineMode, float size) {
/*  264 */     Color color = new Color(red, green, blue, alpha);
/*      */     
/*  266 */     for (int i = 0; i <= ((Integer)ClickGUI.instance.baseRectPatternAmount.getValue()).intValue(); i++) {
/*  267 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  268 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*  270 */       RenderUtils2D.drawEquilateralTriangle(this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, !((Boolean)ClickGUI.instance.baseRectPatternReflect.getValue()).booleanValue(), size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*  271 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  272 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*      */       
/*  275 */       RenderUtils2D.drawEquilateralTriangle(this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, ((Boolean)ClickGUI.instance.baseRectPatternReflect.getValue()).booleanValue(), size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*  276 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  277 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*      */       
/*  280 */       RenderUtils2D.drawEquilateralTriangle(this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, !((Boolean)ClickGUI.instance.baseRectPatternReflect.getValue()).booleanValue(), size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*  281 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  282 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*      */       
/*  285 */       RenderUtils2D.drawEquilateralTriangle(this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, ((Boolean)ClickGUI.instance.baseRectPatternReflect.getValue()).booleanValue(), size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void basePatternTrianglesSingle(int rotationMode, int red, int green, int blue, int alpha, float size, boolean outlineMode) {
/*  296 */     Color color = new Color(red, green, blue, alpha);
/*      */ 
/*      */     
/*  299 */     for (int i = 0; i <= ((Integer)ClickGUI.instance.baseRectPatternAmount.getValue()).intValue(); i++) {
/*      */       
/*  301 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  302 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*      */       
/*  305 */       if (rotationMode == 2) {
/*  306 */         GL11.glTranslatef(this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, 0.0F);
/*  307 */         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/*  308 */         GL11.glTranslatef((this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F) * -1.0F, (this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F) * -1.0F, 0.0F);
/*      */       }
/*  310 */       else if (rotationMode == 3) {
/*  311 */         GL11.glTranslatef(this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, 0.0F);
/*  312 */         GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
/*  313 */         GL11.glTranslatef((this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F) * -1.0F, (this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F) * -1.0F, 0.0F);
/*      */       
/*      */       }
/*  316 */       else if (rotationMode == 4) {
/*  317 */         GL11.glTranslatef(this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, 0.0F);
/*  318 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/*  319 */         GL11.glTranslatef((this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F) * -1.0F, (this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F) * -1.0F, 0.0F);
/*      */       } 
/*      */       
/*  322 */       RenderUtils2D.drawEquilateralTriangle(this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, false, size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*      */       
/*  324 */       if (rotationMode == 2) {
/*  325 */         GL11.glTranslatef(this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, 0.0F);
/*  326 */         GL11.glRotatef(-180.0F, 0.0F, 0.0F, 1.0F);
/*  327 */         GL11.glTranslatef((this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F) * -1.0F, (this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F) * -1.0F, 0.0F);
/*      */       }
/*  329 */       else if (rotationMode == 3) {
/*  330 */         GL11.glTranslatef(this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, 0.0F);
/*  331 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/*  332 */         GL11.glTranslatef((this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F) * -1.0F, (this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F) * -1.0F, 0.0F);
/*      */       }
/*  334 */       else if (rotationMode == 4) {
/*  335 */         GL11.glTranslatef(this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, 0.0F);
/*  336 */         GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
/*  337 */         GL11.glTranslatef((this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F) * -1.0F, (this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F) * -1.0F, 0.0F);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  342 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  343 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*      */       
/*  346 */       if (rotationMode == 2) {
/*  347 */         GL11.glTranslatef(this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, 0.0F);
/*  348 */         GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/*  349 */         GL11.glTranslatef((this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F) * -1.0F, (this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F) * -1.0F, 0.0F);
/*      */       }
/*  351 */       else if (rotationMode == 3) {
/*  352 */         GL11.glTranslatef(this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, 0.0F);
/*  353 */         GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
/*  354 */         GL11.glTranslatef((this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F) * -1.0F, (this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F) * -1.0F, 0.0F);
/*      */       }
/*  356 */       else if (rotationMode == 4) {
/*  357 */         GL11.glTranslatef(this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, 0.0F);
/*  358 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/*  359 */         GL11.glTranslatef((this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F) * -1.0F, (this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F) * -1.0F, 0.0F);
/*      */       } 
/*      */       
/*  362 */       RenderUtils2D.drawEquilateralTriangle(this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, false, size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*      */       
/*  364 */       if (rotationMode == 2) {
/*  365 */         GL11.glTranslatef(this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, 0.0F);
/*  366 */         GL11.glRotatef(-180.0F, 0.0F, 0.0F, 0.0F);
/*  367 */         GL11.glTranslatef((this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F) * -1.0F, (this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F) * -1.0F, 0.0F);
/*      */       }
/*  369 */       else if (rotationMode == 3) {
/*  370 */         GL11.glTranslatef(this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, 0.0F);
/*  371 */         GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/*  372 */         GL11.glTranslatef((this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F) * -1.0F, (this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F) * -1.0F, 0.0F);
/*      */       }
/*  374 */       else if (rotationMode == 4) {
/*  375 */         GL11.glTranslatef(this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, 0.0F);
/*  376 */         GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
/*  377 */         GL11.glTranslatef((this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F) * -1.0F, (this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F) * -1.0F, 0.0F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void basePatternCirclesSingle(int red, int green, int blue, int alpha, float size, boolean outlineMode) {
/*  384 */     Color color = new Color(red, green, blue, alpha);
/*      */     
/*  386 */     for (int i = 0; i <= ((Integer)ClickGUI.instance.baseRectPatternAmount.getValue()).intValue(); i++) {
/*  387 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  388 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*      */       
/*  391 */       RenderUtils2D.drawCircle(this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*      */       
/*  393 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  394 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*      */       
/*  397 */       RenderUtils2D.drawCircle(this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void basePatternCirclesDouble(int red, int green, int blue, int alpha, float size, boolean outlineMode) {
/*  402 */     Color color = new Color(red, green, blue, alpha);
/*      */     
/*  404 */     if (((Boolean)ClickGUI.instance.baseRectPatternReflect.getValue()).booleanValue()) {
/*  405 */       GL11.glTranslatef(this.x + this.width / 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue(), 0.0F);
/*  406 */       GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/*  407 */       GL11.glTranslatef((this.x + this.width / 2.0F) * -1.0F, (this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue()) * -1.0F, 0.0F);
/*      */     } 
/*  409 */     for (int i = 0; i <= ((Integer)ClickGUI.instance.baseRectPatternAmount.getValue()).intValue(); i++) {
/*  410 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  411 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*      */       
/*  414 */       RenderUtils2D.drawCircle(this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*      */       
/*  416 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  417 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*      */       
/*  420 */       RenderUtils2D.drawCircle(this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*      */       
/*  422 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  423 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*      */       
/*  426 */       RenderUtils2D.drawCircle(this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*      */       
/*  428 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  429 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*      */       
/*  432 */       RenderUtils2D.drawCircle(this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*      */     } 
/*  434 */     if (((Boolean)ClickGUI.instance.baseRectPatternReflect.getValue()).booleanValue()) {
/*  435 */       GL11.glTranslatef(this.x + this.width / 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue(), 0.0F);
/*  436 */       GL11.glRotatef(-180.0F, 0.0F, 0.0F, 1.0F);
/*  437 */       GL11.glTranslatef((this.x + this.width / 2.0F) * -1.0F, (this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue()) * -1.0F, 0.0F);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void basePatternDiamondsSingle(int red, int green, int blue, int alpha, float size, boolean outlineMode) {
/*  442 */     Color color = new Color(red, green, blue, alpha);
/*      */     
/*  444 */     for (int i = 0; i <= ((Integer)ClickGUI.instance.baseRectPatternAmount.getValue()).intValue(); i++) {
/*  445 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  446 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*      */       
/*  449 */       RenderUtils2D.drawRhombus(this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*      */       
/*  451 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  452 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*      */       
/*  455 */       RenderUtils2D.drawRhombus(this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*      */     } 
/*      */   }
/*      */   
/*      */   private void basePatternDiamondsDouble(int red, int green, int blue, int alpha, float size, boolean outlineMode) {
/*  460 */     Color color = new Color(red, green, blue, alpha);
/*      */     
/*  462 */     if (((Boolean)ClickGUI.instance.baseRectPatternReflect.getValue()).booleanValue()) {
/*  463 */       GL11.glTranslatef(this.x + this.width / 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue(), 0.0F);
/*  464 */       GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
/*  465 */       GL11.glTranslatef((this.x + this.width / 2.0F) * -1.0F, (this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue()) * -1.0F, 0.0F);
/*      */     } 
/*  467 */     for (int i = 0; i <= ((Integer)ClickGUI.instance.baseRectPatternAmount.getValue()).intValue(); i++) {
/*  468 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  469 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*      */       
/*  472 */       RenderUtils2D.drawRhombus(this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*      */       
/*  474 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  475 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*      */       
/*  478 */       RenderUtils2D.drawRhombus(this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*      */       
/*  480 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  481 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*      */       
/*  484 */       RenderUtils2D.drawRhombus(this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*      */       
/*  486 */       if (outlineMode ? ((Boolean)ClickGUI.instance.baseRectPatternOutlineBrightnessRoll.getValue()).booleanValue() : ((Boolean)ClickGUI.instance.baseRectPatternBrightnessRoll.getValue()).booleanValue()) {
/*  487 */         color = ColorUtil.rolledBrightness(color, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMaxBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMaxBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollMinBright.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollMinBright.getValue()).floatValue(), outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollSpeed.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollSpeed.getValue()).floatValue(), this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, outlineMode ? ((Float)ClickGUI.instance.baseRectPatternOutlineBrightnessRollLength.getValue()).floatValue() : ((Float)ClickGUI.instance.baseRectPatternBrightnessRollLength.getValue()).floatValue(), outlineMode ? ((ClickGUI.instance.baseRectPatternOutlineBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternOutlineBrightnessRollDirection.Right)) : ((ClickGUI.instance.baseRectPatternBrightnessRollDirection.getValue() == ClickGUI.BaseRectPatternBrightnessRollDirection.Right)), false);
/*      */       }
/*      */       
/*  490 */       RenderUtils2D.drawRhombus(this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() - ((Float)ClickGUI.instance.baseRectPatternGap.getValue()).floatValue() * i * 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.baseRectPatternDoubleYGap.getValue()).floatValue() / 2.0F, size, (new Color(color.getRed(), color.getGreen(), color.getBlue(), outlineMode ? ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha() : ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha())).getRGB());
/*      */     } 
/*  492 */     if (((Boolean)ClickGUI.instance.baseRectPatternReflect.getValue()).booleanValue()) {
/*  493 */       GL11.glTranslatef(this.x + this.width / 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue(), 0.0F);
/*  494 */       GL11.glRotatef(-180.0F, 0.0F, 0.0F, 1.0F);
/*  495 */       GL11.glTranslatef((this.x + this.width / 2.0F) * -1.0F, (this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue()) * -1.0F, 0.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawScreen(int mouseX, int mouseY, float translateDelta, float partialTicks) {
/*  501 */     if (Command.mc.field_71462_r != null) {
/*      */       
/*  503 */       if (this.dragging) {
/*  504 */         this.x = this.x2 + mouseX;
/*  505 */         this.y = this.y2 + mouseY;
/*      */       } 
/*      */       
/*  508 */       int moduleRectColor = ((Color)ClickGUI.instance.moduleColor.getValue()).getColor();
/*      */ 
/*      */       
/*  511 */       if (((Boolean)ClickGUI.instance.rectHorns.getValue()).booleanValue()) {
/*  512 */         Color hornColor = new Color(((Color)ClickGUI.instance.rectHornsColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.rectHornsColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.rectHornsColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.rectHornsColor.getValue()).getAlpha());
/*      */ 
/*      */         
/*  515 */         if (((Boolean)ClickGUI.instance.rectHornsHoverDifColor.getValue()).booleanValue()) {
/*  516 */           if (isHovered(mouseX, mouseY))
/*      */           {
/*  518 */             if (!((Boolean)ClickGUI.instance.categoryRectHoverColorSmooth.getValue()).booleanValue()) {
/*  519 */               hornColor = new Color(((Color)ClickGUI.instance.rectHornsHoverColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.rectHornsHoverColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.rectHornsHoverColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.rectHornsHoverColor.getValue()).getAlpha());
/*      */             } else {
/*      */               
/*  522 */               storedHornsHoverLoops.putIfAbsent(this.category.categoryName, Integer.valueOf(0));
/*  523 */               int hoverLoops = ((Integer)storedHornsHoverLoops.get(this.category.categoryName)).intValue();
/*  524 */               if (hoverLoops >= 300) {
/*  525 */                 hoverLoops = 300;
/*      */               }
/*  527 */               if (hoverLoops <= 0) {
/*  528 */                 hoverLoops = 0;
/*      */               }
/*  530 */               int nonHoveredToHoveredRed = (int)MathUtilFuckYou.linearInterp(hornColor.getRed(), ((Color)ClickGUI.instance.rectHornsHoverColor.getValue()).getColorColor().getRed(), hoverLoops);
/*  531 */               int nonHoveredToHoveredGreen = (int)MathUtilFuckYou.linearInterp(hornColor.getGreen(), ((Color)ClickGUI.instance.rectHornsHoverColor.getValue()).getColorColor().getGreen(), hoverLoops);
/*  532 */               int nonHoveredToHoveredBlue = (int)MathUtilFuckYou.linearInterp(hornColor.getBlue(), ((Color)ClickGUI.instance.rectHornsHoverColor.getValue()).getColorColor().getBlue(), hoverLoops);
/*  533 */               int nonHoveredToHoveredAlpha = (int)MathUtilFuckYou.linearInterp(hornColor.getAlpha(), ((Color)ClickGUI.instance.rectHornsHoverColor.getValue()).getAlpha(), hoverLoops);
/*      */               
/*  535 */               hornColor = new Color(nonHoveredToHoveredRed, nonHoveredToHoveredGreen, nonHoveredToHoveredBlue, nonHoveredToHoveredAlpha);
/*  536 */               hoverLoops = (int)(hoverLoops + ((Float)ClickGUI.instance.rectHornsHoverColorSmoothFactorIn.getValue()).floatValue() * 10.0F);
/*  537 */               storedHornsHoverLoops.put(this.category.categoryName, Integer.valueOf(hoverLoops));
/*      */             } 
/*      */           }
/*      */           
/*  541 */           if (((Boolean)ClickGUI.instance.rectHornsHoverColorSmooth.getValue()).booleanValue() && storedHornsHoverLoops.containsKey(this.category.categoryName) && !isHovered(mouseX, mouseY)) {
/*      */             
/*  543 */             int hoverLoops = ((Integer)storedHornsHoverLoops.get(this.category.categoryName)).intValue();
/*  544 */             if (hoverLoops <= 0) {
/*  545 */               hoverLoops = 0;
/*      */             }
/*  547 */             if (hoverLoops >= 300) {
/*  548 */               hoverLoops = 300;
/*      */             }
/*  550 */             int nonHoveredToHoveredRed = (int)MathUtilFuckYou.linearInterp(hornColor.getRed(), ((Color)ClickGUI.instance.rectHornsHoverColor.getValue()).getColorColor().getRed(), hoverLoops);
/*  551 */             int nonHoveredToHoveredGreen = (int)MathUtilFuckYou.linearInterp(hornColor.getGreen(), ((Color)ClickGUI.instance.rectHornsHoverColor.getValue()).getColorColor().getGreen(), hoverLoops);
/*  552 */             int nonHoveredToHoveredBlue = (int)MathUtilFuckYou.linearInterp(hornColor.getBlue(), ((Color)ClickGUI.instance.rectHornsHoverColor.getValue()).getColorColor().getBlue(), hoverLoops);
/*  553 */             int nonHoveredToHoveredAlpha = (int)MathUtilFuckYou.linearInterp(hornColor.getAlpha(), ((Color)ClickGUI.instance.rectHornsHoverColor.getValue()).getAlpha(), hoverLoops);
/*      */             
/*  555 */             hornColor = new Color(nonHoveredToHoveredRed, nonHoveredToHoveredGreen, nonHoveredToHoveredBlue, nonHoveredToHoveredAlpha);
/*  556 */             hoverLoops = (int)(hoverLoops - ((Float)ClickGUI.instance.rectHornsHoverColorSmoothFactorOut.getValue()).floatValue() * 10.0F);
/*  557 */             storedHornsHoverLoops.put(this.category.categoryName, Integer.valueOf(hoverLoops));
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  562 */         GL11.glEnable(3553);
/*      */         
/*  564 */         GL11.glColor4f(hornColor.getRed() / 255.0F, hornColor.getGreen() / 255.0F, hornColor.getBlue() / 255.0F, hornColor.getAlpha() / 255.0F);
/*      */ 
/*      */         
/*  567 */         Command.mc.func_110434_K().func_110577_a(HORN);
/*  568 */         Gui.func_152125_a((int)(this.x + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() - ((Float)ClickGUI.instance.rectWidth.getValue()).floatValue() / 2.0F + ((Integer)ClickGUI.instance.rectHornsX.getValue()).intValue() - ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue()), (int)(this.y - ((Float)ClickGUI.instance.rectHeight.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.rectY.getValue()).floatValue() + ((Integer)ClickGUI.instance.rectHornsY.getValue()).intValue() - ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue()), 0.0F, 0.0F, ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue(), ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue(), ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue(), ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue(), ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue(), ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue());
/*      */ 
/*      */         
/*  571 */         Command.mc.func_110434_K().func_110577_a(HORN);
/*  572 */         Gui.func_152125_a((int)(this.x + this.width * ((Float)ClickGUI.instance.rectHornsGap.getValue()).floatValue() + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() + ((Float)ClickGUI.instance.rectWidth.getValue()).floatValue() / 2.0F + ((Integer)ClickGUI.instance.rectHornsX.getValue()).intValue() - ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue()), (int)(this.y - ((Float)ClickGUI.instance.rectHeight.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.rectY.getValue()).floatValue() + ((Integer)ClickGUI.instance.rectHornsY.getValue()).intValue() - ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue()), 0.0F, 0.0F, ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue() * -1, ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue(), ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue(), ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue(), ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue(), ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue());
/*      */         
/*  574 */         GL11.glDisable(3553);
/*  575 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */       } 
/*      */ 
/*      */       
/*  579 */       int index = 0;
/*  580 */       if (!this.elements.isEmpty()) {
/*  581 */         this.startY = this.y + this.height + 2;
/*  582 */         int step = 0;
/*  583 */         int step2 = 0;
/*  584 */         int step3 = 0;
/*  585 */         for (ModuleButton button : this.elements) {
/*  586 */           index++;
/*      */           
/*  588 */           if (this.extended ? 
/*  589 */             !this.panelTimer.passed(((index * 25) / ((Float)ClickGUI.instance.panelOpenSpeed.getValue()).floatValue())) : 
/*      */ 
/*      */             
/*  592 */             this.panelTimer.passed((((this.elements.size() - index) * 25) / ((Float)ClickGUI.instance.panelOpenSpeed.getValue()).floatValue()))) {
/*      */             continue;
/*      */           }
/*  595 */           firstModuleButton = (index >= 1);
/*  596 */           lastModuleButton = (index == this.elements.size());
/*      */           
/*  598 */           if (step2 == 0 && firstModuleButton) {
/*  599 */             this.startY += (ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Top || ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Both) ? (((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() + 2) : 0;
/*  600 */             step2++;
/*      */           } 
/*      */           
/*  603 */           button.solvePos(true);
/*  604 */           button.y = this.startY;
/*      */           
/*  606 */           this.startY += this.height - 1;
/*      */           
/*  608 */           if (button.isHovered(mouseX, mouseY) && !button.getDescription().equals("")) ClickGUIFinal.description = new Pair(button.getDescription(), new Vec2I(mouseX, mouseY));
/*      */           
/*  610 */           int settingIndex = -1;
/*  611 */           int settingIndex2 = -1;
/*  612 */           List<Component> visibleSettings = (List<Component>)button.settings.stream().filter(Component::isVisible).collect(Collectors.toList());
/*      */ 
/*      */           
/*  615 */           if (!((Boolean)ClickGUI.instance.moduleSideGlowLayer.getValue()).booleanValue()) {
/*  616 */             panelSideGlow(button);
/*      */           }
/*      */ 
/*      */           
/*  620 */           if (((Boolean)ClickGUI.instance.moduleSeparators.getValue()).booleanValue() && !((Boolean)ClickGUI.instance.moduleSeparatorsOnTop.getValue()).booleanValue()) {
/*  621 */             moduleSeparators(button);
/*      */           }
/*      */ 
/*      */           
/*  625 */           if (step == 0 && firstModuleButton) {
/*  626 */             if (ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Top || ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Both) {
/*  627 */               if (!((Boolean)ClickGUI.instance.moduleSideGlowLayer.getValue()).booleanValue()) {
/*  628 */                 GL11.glTranslatef(0.0F, -((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue(), 0.0F);
/*      */                 
/*  630 */                 if (ClickGUI.instance.moduleSideGlow.getValue() != ClickGUI.ModuleSideGlow.None) {
/*  631 */                   GlStateManager.func_179118_c();
/*  632 */                   if (ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Left || ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Both) {
/*  633 */                     RenderUtils2D.drawCustomRect(button.x, (button.y - 1), button.x + ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), (button.y - 1 + ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue()), (new Color(0, 0, 0, 0)).getRGB(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), (new Color(0, 0, 0, 0)).getRGB());
/*      */                   }
/*  635 */                   if (ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Right || ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Both) {
/*  636 */                     RenderUtils2D.drawCustomRect((button.x + Component.instance.width) - ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), (button.y - 1), (button.x + Component.instance.width), (button.y - 1 + ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue()), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor());
/*      */                   }
/*  638 */                   GlStateManager.func_179141_d();
/*      */                 } 
/*      */                 
/*  641 */                 GL11.glTranslatef(0.0F, ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue(), 0.0F);
/*      */               } 
/*  643 */               if (((Boolean)ClickGUI.instance.moduleSeparators.getValue()).booleanValue() && !((Boolean)ClickGUI.instance.moduleSeparatorsOnTop.getValue()).booleanValue()) {
/*  644 */                 GL11.glTranslatef(0.0F, -((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() - 2.0F, 0.0F);
/*  645 */                 moduleSeparators(button);
/*  646 */                 GL11.glTranslatef(0.0F, ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() + 2.0F, 0.0F);
/*      */               } 
/*      */               
/*  649 */               RenderUtils2D.drawRect(button.x, (this.y + this.height - 2), (button.x + Component.instance.width), (this.y + this.height + Component.instance.height + 1) - 12.0F + ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue(), ((Color)ClickGUI.instance.moduleBGColor.getValue()).getColor());
/*  650 */               RenderUtils2D.drawRect((button.x + 1), (this.y + this.height - 2), (button.x + Component.instance.width - 1), (this.y + this.height + Component.instance.height - ((Integer)ClickGUI.instance.moduleGap.getValue()).intValue()) - 12.0F + ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue(), moduleRectColor);
/*      */               
/*  652 */               if (((Boolean)ClickGUI.instance.moduleSeparators.getValue()).booleanValue() && ((Boolean)ClickGUI.instance.moduleSeparatorsOnTop.getValue()).booleanValue()) {
/*  653 */                 GL11.glTranslatef(0.0F, -((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() - 2.0F, 0.0F);
/*  654 */                 moduleSeparators(button);
/*  655 */                 GL11.glTranslatef(0.0F, ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() + 2.0F, 0.0F);
/*      */               } 
/*  657 */               if (((Boolean)ClickGUI.instance.moduleSideGlowLayer.getValue()).booleanValue()) {
/*  658 */                 GL11.glTranslatef(0.0F, -((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue(), 0.0F);
/*      */                 
/*  660 */                 if (ClickGUI.instance.moduleSideGlow.getValue() != ClickGUI.ModuleSideGlow.None) {
/*  661 */                   GlStateManager.func_179118_c();
/*  662 */                   if (ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Left || ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Both) {
/*  663 */                     RenderUtils2D.drawCustomRect(button.x, (button.y - 1), button.x + ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), (button.y - 1 + ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue()), (new Color(0, 0, 0, 0)).getRGB(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), (new Color(0, 0, 0, 0)).getRGB());
/*      */                   }
/*  665 */                   if (ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Right || ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Both) {
/*  666 */                     RenderUtils2D.drawCustomRect((button.x + Component.instance.width) - ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), (button.y - 1), (button.x + Component.instance.width), (button.y - 1 + ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue()), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor());
/*      */                   }
/*  668 */                   GlStateManager.func_179141_d();
/*      */                 } 
/*      */                 
/*  671 */                 GL11.glTranslatef(0.0F, ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue(), 0.0F);
/*      */               } 
/*  673 */               if (ClickGUI.instance.moduleSideGlow.getValue() != ClickGUI.ModuleSideGlow.None) {
/*  674 */                 GL11.glTranslatef(0.0F, -((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() - 2.0F, 0.0F);
/*  675 */                 GlStateManager.func_179118_c();
/*  676 */                 if (ClickGUI.instance.moduleSideGlowDouble.getValue() == ClickGUI.ModuleSideGlowDouble.Left) {
/*  677 */                   RenderUtils2D.drawCustomRect(button.x, (button.y - 1), button.x + ((Float)ClickGUI.instance.moduleSideGlowDoubleWidth.getValue()).floatValue(), (button.y + Component.instance.height), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*      */                 }
/*  679 */                 else if (ClickGUI.instance.moduleSideGlowDouble.getValue() == ClickGUI.ModuleSideGlowDouble.Right) {
/*  680 */                   RenderUtils2D.drawCustomRect((button.x + Component.instance.width) - ((Float)ClickGUI.instance.moduleSideGlowDoubleWidth.getValue()).floatValue(), (button.y - 1), (button.x + Component.instance.width), (button.y + Component.instance.height + 1), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB());
/*      */                 } 
/*  682 */                 GlStateManager.func_179141_d();
/*  683 */                 GL11.glTranslatef(0.0F, ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() + 2.0F, 0.0F);
/*      */               } 
/*      */             } 
/*  686 */             step++;
/*      */           } 
/*      */ 
/*      */           
/*  690 */           RenderUtils2D.drawRect(button.x, (button.y - 1), (button.x + Component.instance.width), (button.y + Component.instance.height + 1), ((Color)ClickGUI.instance.moduleBGColor.getValue()).getColor());
/*  691 */           if (((Boolean)ClickGUI.instance.moduleRectRounded.getValue()).booleanValue()) {
/*  692 */             RenderUtils2D.drawRoundedRect((button.x + 1), (button.y - 1), ((Float)ClickGUI.instance.moduleRectRoundedRadius.getValue()).floatValue(), (button.x + Component.instance.width - 1), (button.y + Component.instance.height - ((Integer)ClickGUI.instance.moduleGap.getValue()).intValue()), false, ((Boolean)ClickGUI.instance.moduleRoundedTopRight.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.moduleRoundedTopLeft.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.moduleRoundedBottomRight.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.moduleRoundedBottomLeft.getValue()).booleanValue(), moduleRectColor);
/*      */           } else {
/*      */             
/*  695 */             RenderUtils2D.drawRect((button.x + 1), (button.y - 1), (button.x + Component.instance.width - 1), (button.y + Component.instance.height - ((Integer)ClickGUI.instance.moduleGap.getValue()).intValue()), moduleRectColor);
/*      */           } 
/*      */ 
/*      */           
/*  699 */           preExtendStartY = this.startY;
/*  700 */           for (Component component : visibleSettings) {
/*  701 */             settingIndex2++;
/*  702 */             if (button.isExtended ? 
/*  703 */               !button.buttonTimer.passed(((settingIndex2 * 25) / ((Float)ClickGUI.instance.moduleOpenSpeed.getValue()).floatValue())) : 
/*      */               
/*  705 */               button.buttonTimer.passed((((visibleSettings.size() - settingIndex2) * 25) / ((Float)ClickGUI.instance.moduleOpenSpeed.getValue()).floatValue())))
/*      */               continue; 
/*  707 */             component.solvePos(true);
/*  708 */             component.y = preExtendStartY;
/*  709 */             component.bottomRender(mouseX, mouseY, (settingIndex2 == visibleSettings.size() - 1), (settingIndex2 == 1), partialTicks);
/*      */             
/*  711 */             if (settingIndex2 == visibleSettings.size() - 1 && ((Boolean)ClickGUI.instance.extendedBottomExtensions.getValue()).booleanValue()) {
/*  712 */               float theY = (component.y + component.height);
/*      */               
/*  714 */               if (!((Boolean)ClickGUI.instance.moduleSideGlowLayer.getValue()).booleanValue() && 
/*  715 */                 ClickGUI.instance.moduleSideGlow.getValue() != ClickGUI.ModuleSideGlow.None) {
/*  716 */                 GlStateManager.func_179118_c();
/*  717 */                 if (ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Left || ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Both) {
/*  718 */                   RenderUtils2D.drawCustomRect(component.x, theY, component.x + ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), theY + ((Float)ClickGUI.instance.extendedBottomExtensionsHeight.getValue()).floatValue(), (new Color(0, 0, 0, 0)).getRGB(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), (new Color(0, 0, 0, 0)).getRGB());
/*      */                 }
/*  720 */                 if (ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Right || ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Both) {
/*  721 */                   RenderUtils2D.drawCustomRect((component.x + component.width) - ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), theY, (component.x + component.width), theY + ((Float)ClickGUI.instance.extendedBottomExtensionsHeight.getValue()).floatValue(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor());
/*      */                 }
/*  723 */                 GlStateManager.func_179141_d();
/*      */               } 
/*      */ 
/*      */               
/*  727 */               RenderUtils2D.drawRect(component.x, theY, (component.x + component.width), theY + ((Float)ClickGUI.instance.extendedBottomExtensionsHeight.getValue()).floatValue(), ((Color)ClickGUI.instance.moduleBGColor.getValue()).getColor());
/*  728 */               RenderUtils2D.drawRect((component.x + 1), theY, (component.x + component.width - 1 - ((Integer)ClickGUI.instance.extendedRectGap.getValue()).intValue()), theY + ((Float)ClickGUI.instance.extendedBottomExtensionsHeight.getValue()).floatValue() - 1.0F, ((Color)ClickGUI.instance.extendedRectColor.getValue()).getColor());
/*      */ 
/*      */               
/*  731 */               if (((Boolean)ClickGUI.instance.moduleSideGlowLayer.getValue()).booleanValue() && 
/*  732 */                 ClickGUI.instance.moduleSideGlow.getValue() != ClickGUI.ModuleSideGlow.None) {
/*  733 */                 GlStateManager.func_179118_c();
/*  734 */                 if (ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Left || ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Both) {
/*  735 */                   RenderUtils2D.drawCustomRect(component.x, theY, component.x + ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), theY + ((Float)ClickGUI.instance.extendedBottomExtensionsHeight.getValue()).floatValue(), (new Color(0, 0, 0, 0)).getRGB(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), (new Color(0, 0, 0, 0)).getRGB());
/*      */                 }
/*  737 */                 if (ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Right || ClickGUI.instance.moduleSideGlow.getValue() == ClickGUI.ModuleSideGlow.Both) {
/*  738 */                   RenderUtils2D.drawCustomRect((component.x + component.width) - ((Float)ClickGUI.instance.moduleSideGlowWidth.getValue()).floatValue(), theY, (component.x + component.width), theY + ((Float)ClickGUI.instance.extendedBottomExtensionsHeight.getValue()).floatValue(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColor());
/*      */                 }
/*  740 */                 GlStateManager.func_179141_d();
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/*  745 */               if (ClickGUI.instance.moduleSideGlow.getValue() != ClickGUI.ModuleSideGlow.None) {
/*  746 */                 GlStateManager.func_179118_c();
/*  747 */                 if (ClickGUI.instance.moduleSideGlowDouble.getValue() == ClickGUI.ModuleSideGlowDouble.Left) {
/*  748 */                   RenderUtils2D.drawCustomRect(component.x, theY, component.x + ((Float)ClickGUI.instance.moduleSideGlowDoubleWidth.getValue()).floatValue(), theY + ((Float)ClickGUI.instance.extendedBottomExtensionsHeight.getValue()).floatValue(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*      */                 }
/*  750 */                 else if (ClickGUI.instance.moduleSideGlowDouble.getValue() == ClickGUI.ModuleSideGlowDouble.Right) {
/*  751 */                   RenderUtils2D.drawCustomRect((component.x + component.width) - ((Float)ClickGUI.instance.moduleSideGlowDoubleWidth.getValue()).floatValue(), theY, (component.x + component.width), theY + ((Float)ClickGUI.instance.extendedBottomExtensionsHeight.getValue()).floatValue(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB());
/*      */                 } 
/*  753 */                 GlStateManager.func_179141_d();
/*      */               } 
/*      */ 
/*      */               
/*  757 */               int extendedGradientColor = ((Color)ClickGUI.instance.extendedGradientColor.getValue()).getColor();
/*  758 */               if (((Boolean)ClickGUI.instance.extendedVerticalGradient.getValue()).booleanValue()) {
/*  759 */                 GlStateManager.func_179118_c();
/*  760 */                 RenderUtils2D.drawCustomRect(component.x, theY, component.x + ((Float)ClickGUI.instance.extendedGradientWidth.getValue()).floatValue(), theY + ((Float)ClickGUI.instance.extendedBottomExtensionsHeight.getValue()).floatValue() - 1.0F, (new Color(0, 0, 0, 0)).getRGB(), extendedGradientColor, extendedGradientColor, (new Color(0, 0, 0, 0)).getRGB());
/*  761 */                 GlStateManager.func_179141_d();
/*      */               } 
/*      */ 
/*      */               
/*  765 */               RenderUtils2D.drawCustomLine(component.x + ((Float)ClickGUI.instance.extendedWidth.getValue()).floatValue() / 2.0F, theY, component.x + ((Float)ClickGUI.instance.extendedWidth.getValue()).floatValue() / 2.0F, theY + ((Float)ClickGUI.instance.extendedBottomExtensionsHeight.getValue()).floatValue() - 1.0F, ((Float)ClickGUI.instance.extendedWidth.getValue()).floatValue(), ((Color)ClickGUI.instance.extendedColor.getValue()).getColor(), ((Color)ClickGUI.instance.extendedColor.getValue()).getColor());
/*      */               
/*  767 */               preExtendStartY = (int)(preExtendStartY + ((Float)ClickGUI.instance.extendedBottomExtensionsHeight.getValue()).floatValue());
/*      */             } 
/*      */             
/*  770 */             preExtendStartY += component.height;
/*      */           } 
/*      */ 
/*      */           
/*  774 */           for (Component component : visibleSettings) {
/*  775 */             settingIndex++;
/*  776 */             if (button.isExtended ? 
/*  777 */               !button.buttonTimer.passed(((settingIndex * 25) / ((Float)ClickGUI.instance.moduleOpenSpeed.getValue()).floatValue())) : 
/*      */               
/*  779 */               button.buttonTimer.passed((((visibleSettings.size() - settingIndex) * 25) / ((Float)ClickGUI.instance.moduleOpenSpeed.getValue()).floatValue())))
/*      */               continue; 
/*  781 */             component.solvePos(true);
/*  782 */             component.y = this.startY;
/*      */             
/*  784 */             int extendedTopDownGradientColor = ((Color)ClickGUI.instance.extendedTopDownGradientColor.getValue()).getColor();
/*      */             
/*  786 */             if (settingIndex == 0 && ((Boolean)ClickGUI.instance.extendedCategoryGradient.getValue()).booleanValue() && ((Boolean)ClickGUI.instance.extendedTopBars.getValue()).booleanValue()) {
/*  787 */               GlStateManager.func_179118_c();
/*  788 */               RenderUtils2D.drawCustomRect(component.x, component.y, (component.x + component.width), component.y + ((Float)ClickGUI.instance.extendedCategoryGradientHeight.getValue()).floatValue(), extendedTopDownGradientColor, extendedTopDownGradientColor, (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*  789 */               GlStateManager.func_179141_d();
/*      */             } 
/*  791 */             if (settingIndex == visibleSettings.size() - 1 && ((Boolean)ClickGUI.instance.extendedGradientBottom.getValue()).booleanValue()) {
/*  792 */               GlStateManager.func_179118_c();
/*  793 */               RenderUtils2D.drawCustomRect(component.x, (component.y + component.height - 1) + (((Boolean)ClickGUI.instance.extendedBottomExtensions.getValue()).booleanValue() ? ((Float)ClickGUI.instance.extendedBottomExtensionsHeight.getValue()).floatValue() : 0.0F) - ((Float)ClickGUI.instance.extendedGradientBottomHeight.getValue()).floatValue(), (component.x + component.width), (component.y + component.height - 1) + (((Boolean)ClickGUI.instance.extendedBottomExtensions.getValue()).booleanValue() ? ((Float)ClickGUI.instance.extendedBottomExtensionsHeight.getValue()).floatValue() : 0.0F), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), extendedTopDownGradientColor, extendedTopDownGradientColor);
/*  794 */               GlStateManager.func_179141_d();
/*      */             } 
/*      */             
/*  797 */             component.render(mouseX, mouseY, translateDelta, partialTicks);
/*  798 */             if (component.isHovered(mouseX, mouseY) && !component.getDescription().equals("")) {
/*  799 */               ClickGUIFinal.description = new Pair(component.getDescription(), new Vec2I(mouseX, mouseY));
/*      */             }
/*  801 */             if (settingIndex == visibleSettings.size() - 1 && ((Boolean)ClickGUI.instance.extendedBottomExtensions.getValue()).booleanValue()) {
/*  802 */               this.startY = (int)(this.startY + ((Float)ClickGUI.instance.extendedBottomExtensionsHeight.getValue()).floatValue());
/*      */             }
/*      */             
/*  805 */             this.startY += component.height;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  810 */           int moduleRectOutlineColor = ((Color)ClickGUI.instance.moduleRectOutlineColor.getValue()).getColor();
/*  811 */           if (((Boolean)ClickGUI.instance.moduleRectOutline.getValue()).booleanValue() && ((Boolean)ClickGUI.instance.moduleRectRounded.getValue()).booleanValue()) {
/*  812 */             RenderUtils2D.drawCustomRoundedRectOutline((button.x + 1), (button.y - 1), (button.x + Component.instance.width - 1), (button.y + Component.instance.height - ((Integer)ClickGUI.instance.moduleGap.getValue()).intValue()), ((Float)ClickGUI.instance.moduleRectRoundedRadius.getValue()).floatValue(), ((Float)ClickGUI.instance.moduleRectOutlineLineWidth.getValue()).floatValue(), ((Boolean)ClickGUI.instance.moduleRoundedTopRight.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.moduleRoundedTopLeft.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.moduleRoundedBottomRight.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.moduleRoundedBottomLeft.getValue()).booleanValue(), false, false, moduleRectOutlineColor);
/*      */           }
/*  814 */           else if (((Boolean)ClickGUI.instance.moduleRectOutline.getValue()).booleanValue()) {
/*  815 */             RenderUtils2D.drawRectOutline((button.x + 1), (button.y - 1), (button.x + Component.instance.width - 1), (button.y + Component.instance.height - ((Integer)ClickGUI.instance.moduleGap.getValue()).intValue()), ((Float)ClickGUI.instance.moduleRectOutlineLineWidth.getValue()).floatValue(), moduleRectOutlineColor, false, false);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  820 */           if (lastModuleButton && this.extended && (
/*  821 */             ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Bottom || ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Both)) {
/*  822 */             GL11.glTranslatef(0.0F, 15.0F, 0.0F);
/*      */             
/*  824 */             if (!((Boolean)ClickGUI.instance.moduleSideGlowLayer.getValue()).booleanValue()) {
/*  825 */               panelSideGlowBottomExtensions();
/*      */             }
/*      */             
/*  828 */             RenderUtils2D.drawRect((this.x + 5), (this.startY - Component.instance.height - 1), (this.x + this.width - 5), (this.startY + 1 + ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() - 13), ((Color)ClickGUI.instance.moduleBGColor.getValue()).getColor());
/*  829 */             RenderUtils2D.drawRect((this.x + 6), (this.startY - Component.instance.height - 1), (this.x + this.width - 6), (this.startY - ((Integer)ClickGUI.instance.moduleGap.getValue()).intValue() + ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() - 13), moduleRectColor);
/*      */             
/*  831 */             if (((Boolean)ClickGUI.instance.moduleSideGlowLayer.getValue()).booleanValue()) {
/*  832 */               panelSideGlowBottomExtensions();
/*      */             }
/*  834 */             if (ClickGUI.instance.moduleSideGlow.getValue() != ClickGUI.ModuleSideGlow.None) {
/*  835 */               GlStateManager.func_179118_c();
/*  836 */               if (ClickGUI.instance.moduleSideGlowDouble.getValue() == ClickGUI.ModuleSideGlowDouble.Left) {
/*  837 */                 RenderUtils2D.drawCustomRect((this.x + 5), (this.startY - Component.instance.height - 1), (this.x + 5) + ((Float)ClickGUI.instance.moduleSideGlowDoubleWidth.getValue()).floatValue(), (this.startY + 1 + ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() - 13), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*      */               }
/*  839 */               else if (ClickGUI.instance.moduleSideGlowDouble.getValue() == ClickGUI.ModuleSideGlowDouble.Right) {
/*  840 */                 RenderUtils2D.drawCustomRect((this.x + 5 + Component.instance.width) - ((Float)ClickGUI.instance.moduleSideGlowDoubleWidth.getValue()).floatValue(), (this.startY - Component.instance.height - 1), (this.x + 5 + Component.instance.width), (this.startY + 1 + ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() - 13), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB());
/*      */               } 
/*  842 */               GlStateManager.func_179141_d();
/*      */             } 
/*      */             
/*  845 */             GL11.glTranslatef(0.0F, -15.0F, 0.0F);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  850 */           if (((Boolean)ClickGUI.instance.moduleSideGlowLayer.getValue()).booleanValue()) {
/*  851 */             panelSideGlow(button);
/*      */           }
/*      */ 
/*      */           
/*  855 */           if (ClickGUI.instance.moduleSideGlow.getValue() != ClickGUI.ModuleSideGlow.None) {
/*  856 */             GlStateManager.func_179118_c();
/*  857 */             if (ClickGUI.instance.moduleSideGlowDouble.getValue() == ClickGUI.ModuleSideGlowDouble.Left) {
/*  858 */               RenderUtils2D.drawCustomRect(button.x, (button.y - 1), button.x + ((Float)ClickGUI.instance.moduleSideGlowDoubleWidth.getValue()).floatValue(), (button.y + Component.instance.height), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*      */             }
/*  860 */             else if (ClickGUI.instance.moduleSideGlowDouble.getValue() == ClickGUI.ModuleSideGlowDouble.Right) {
/*  861 */               RenderUtils2D.drawCustomRect((button.x + Component.instance.width) - ((Float)ClickGUI.instance.moduleSideGlowDoubleWidth.getValue()).floatValue(), (button.y - 1), (button.x + Component.instance.width), (button.y + Component.instance.height + 1), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.moduleSideGlowColor.getValue()).getColorColor().getBlue(), ((Integer)ClickGUI.instance.moduleSideGlowDoubleAlpha.getValue()).intValue())).getRGB());
/*      */             } 
/*  863 */             GlStateManager.func_179141_d();
/*      */           } 
/*      */ 
/*      */           
/*  867 */           if (((Boolean)ClickGUI.instance.categoryGradient.getValue()).booleanValue() && step3 == 0 && firstModuleButton) {
/*  868 */             int categoryGradientColor = ((Color)ClickGUI.instance.gradientBarColor.getValue()).getColor();
/*  869 */             GlStateManager.func_179118_c();
/*  870 */             RenderUtils2D.drawCustomRect((this.x + 4) - ((Float)ClickGUI.instance.categoryGradientXScale.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.categoryGradientX.getValue()).floatValue(), (this.y + this.height) - ((Float)ClickGUI.instance.categoryGradientYScale.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.categoryGradientY.getValue()).floatValue(), (this.x + this.width - 4) + ((Float)ClickGUI.instance.categoryGradientXScale.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.categoryGradientX.getValue()).floatValue(), (this.y + this.height + 1) + ((Float)ClickGUI.instance.categoryGradientYScale.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.categoryGradientY.getValue()).floatValue(), categoryGradientColor, categoryGradientColor, (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*  871 */             GlStateManager.func_179141_d();
/*  872 */             step3++;
/*      */           } 
/*      */ 
/*      */           
/*  876 */           if (((Boolean)ClickGUI.instance.bottomGradient.getValue()).booleanValue() && lastModuleButton) {
/*  877 */             if (ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Bottom || ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Both) {
/*  878 */               GL11.glTranslatef(0.0F, ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() + 4.0F, 0.0F);
/*      */             }
/*  880 */             int panelBottomGradientColor = ((Color)ClickGUI.instance.panelBottomGradientColor.getValue()).getColor();
/*  881 */             GlStateManager.func_179118_c();
/*  882 */             RenderUtils2D.drawCustomRect((this.x + 5), (this.startY + 1) - ((Float)ClickGUI.instance.bottomGradientWidth.getValue()).floatValue(), (this.x + this.width - 5), (this.startY - 1), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB(), panelBottomGradientColor, panelBottomGradientColor);
/*  883 */             GlStateManager.func_179141_d();
/*  884 */             if (ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Bottom || ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Both) {
/*  885 */               GL11.glTranslatef(0.0F, (((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() + 4.0F) * -1.0F, 0.0F);
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/*  890 */           this.startY++;
/*      */         } 
/*      */ 
/*      */         
/*  894 */         if (lastModuleButton && this.extended && (ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Bottom || ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Both)) {
/*  895 */           this.startY += ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() + 4;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  900 */         int index2 = 0;
/*  901 */         for (ModuleButton button : this.elements) {
/*  902 */           index2++;
/*  903 */           if (this.extended ? 
/*  904 */             !this.panelTimer.passed(((index2 * 25) / ((Float)ClickGUI.instance.panelOpenSpeed.getValue()).floatValue())) : 
/*      */             
/*  906 */             this.panelTimer.passed((((this.elements.size() - index2) * 25) / ((Float)ClickGUI.instance.panelOpenSpeed.getValue()).floatValue())))
/*      */             continue; 
/*  908 */           button.render(mouseX, mouseY, translateDelta, partialTicks);
/*      */ 
/*      */           
/*  911 */           if (((Boolean)ClickGUI.instance.moduleSeparators.getValue()).booleanValue() && ((Boolean)ClickGUI.instance.moduleSeparatorsOnTop.getValue()).booleanValue()) {
/*  912 */             moduleSeparators(button);
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  918 */         if (((Boolean)ClickGUI.instance.outline.getValue()).booleanValue()) {
/*  919 */           RenderUtils2D.drawCustomLine((this.x + 4), (this.y + this.height), (this.x + 4), this.startY, ((Float)ClickGUI.instance.outlineWidth.getValue()).floatValue(), outlineTopColor(), outlineDownColor());
/*  920 */           RenderUtils2D.drawCustomLine((this.x + this.width - 4), (this.y + this.height), (this.x + this.width - 4), this.startY, ((Float)ClickGUI.instance.outlineWidth.getValue()).floatValue(), outlineTopColor(), outlineDownColor());
/*  921 */           if (!((Boolean)ClickGUI.instance.outlineDownToggle.getValue()).booleanValue()) {
/*  922 */             RenderUtils2D.drawCustomLine((this.x + 4), this.startY, (this.x + this.width - 4), this.startY, ((Float)ClickGUI.instance.outlineWidth.getValue()).floatValue(), outlineDownColor(), outlineDownColor());
/*      */           }
/*      */         } 
/*      */ 
/*      */         
/*  927 */         if (((Boolean)ClickGUI.instance.guiCategoryPanelFadeDownExtend.getValue()).booleanValue()) {
/*  928 */           GlStateManager.func_179118_c();
/*  929 */           RenderUtils2D.drawCustomRect((this.x + 5), (this.startY - 2), (this.x + this.width - 5), (this.startY - 1) + ((Float)ClickGUI.instance.panelFadeDownExtendHeight.getValue()).floatValue(), ((Color)ClickGUI.instance.moduleBGColor.getValue()).getColor(), ((Color)ClickGUI.instance.moduleBGColor.getValue()).getColor(), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/*  930 */           GlStateManager.func_179141_d();
/*  931 */           if (((Boolean)ClickGUI.instance.panelFadeDownExtendOutline.getValue()).booleanValue()) {
/*  932 */             RenderUtils2D.drawCustomLine((this.x + 4), this.startY, (this.x + 4), (this.startY - 1) + ((Float)ClickGUI.instance.panelFadeDownExtendHeight.getValue()).floatValue(), ((Float)ClickGUI.instance.outlineWidth.getValue()).floatValue(), outlineDownColor(), (new Color(0, 0, 0, 0)).getRGB());
/*  933 */             RenderUtils2D.drawCustomLine((this.x + this.width - 4), this.startY, (this.x + this.width - 4), (this.startY - 1) + ((Float)ClickGUI.instance.panelFadeDownExtendHeight.getValue()).floatValue(), ((Float)ClickGUI.instance.outlineWidth.getValue()).floatValue(), outlineDownColor(), (new Color(0, 0, 0, 0)).getRGB());
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  938 */         if (((Boolean)ClickGUI.instance.guiCategoryBase.getValue()).booleanValue()) {
/*  939 */           Color baseColor = new Color(outlineDownColor());
/*  940 */           if (((Boolean)ClickGUI.instance.guiCategoryBaseRound.getValue()).booleanValue()) {
/*  941 */             RenderUtils2D.drawRoundedRect((this.x + 4) - ((Float)ClickGUI.instance.widthBase.getValue()).floatValue() / 2.0F, (this.startY - 1), ((Float)ClickGUI.instance.radiusBase.getValue()).floatValue(), (this.x + this.width - 4) + ((Float)ClickGUI.instance.widthBase.getValue()).floatValue() / 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue(), false, ((Boolean)ClickGUI.instance.arcTopRightBase.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.arcTopLeftBase.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.arcDownRightBase.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.arcDownLeftBase.getValue()).booleanValue(), (new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), ((Integer)ClickGUI.instance.baseAlpha.getValue()).intValue())).getRGB());
/*      */           } else {
/*      */             
/*  944 */             RenderUtils2D.drawRect((this.x + 4) - ((Float)ClickGUI.instance.widthBase.getValue()).floatValue() / 2.0F, (this.startY - 1), (this.x + this.width - 4) + ((Float)ClickGUI.instance.widthBase.getValue()).floatValue() / 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue(), (new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), ((Integer)ClickGUI.instance.baseAlpha.getValue()).intValue())).getRGB());
/*      */           } 
/*      */ 
/*      */           
/*  948 */           if (((Boolean)ClickGUI.instance.baseOutline.getValue()).booleanValue()) {
/*  949 */             if (((Boolean)ClickGUI.instance.guiCategoryBaseRound.getValue()).booleanValue()) {
/*  950 */               RenderUtils2D.drawCustomRoundedRectOutline((this.x + 4) - ((Float)ClickGUI.instance.widthBase.getValue()).floatValue() / 2.0F, (this.startY - 1), (this.x + this.width - 4) + ((Float)ClickGUI.instance.widthBase.getValue()).floatValue() / 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue(), ((Float)ClickGUI.instance.radiusBase.getValue()).floatValue(), ((Float)ClickGUI.instance.baseOutlineWidth.getValue()).floatValue(), ((Boolean)ClickGUI.instance.arcTopRightBase.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.arcTopLeftBase.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.arcDownRightBase.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.arcDownLeftBase.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.baseOutlineTopToggle.getValue()).booleanValue(), false, ((Color)ClickGUI.instance.baseOutlineColor.getValue()).getColor());
/*      */             } else {
/*      */               
/*  953 */               RenderUtils2D.drawRectOutline((this.x + 4) - ((Float)ClickGUI.instance.widthBase.getValue()).floatValue() / 2.0F, (this.startY - 1), (this.x + this.width - 4) + ((Float)ClickGUI.instance.widthBase.getValue()).floatValue() / 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue(), ((Color)ClickGUI.instance.baseOutlineColor.getValue()).getColor(), ((Boolean)ClickGUI.instance.baseOutlineTopToggle.getValue()).booleanValue(), false);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*  958 */           if (((Boolean)ClickGUI.instance.baseGlow.getValue()).booleanValue()) {
/*  959 */             RenderUtils2D.drawRoundedRectFade((this.x + 4) - ((Float)ClickGUI.instance.widthBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseGlowWidth.getValue()).floatValue() / 2.0F, (this.startY - 1) - ((Float)ClickGUI.instance.baseGlowHeight.getValue()).floatValue() / 2.0F, 1.0F, true, false, (this.x + this.width - 4) + ((Float)ClickGUI.instance.widthBase.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.baseGlowWidth.getValue()).floatValue() / 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() + ((Float)ClickGUI.instance.baseGlowHeight.getValue()).floatValue() / 2.0F, ((Color)ClickGUI.instance.baseGlowColor.getValue()).getColor());
/*      */           }
/*      */ 
/*      */           
/*  963 */           if (ClickGUI.instance.baseRectPattern.getValue() != ClickGUI.BaseRectPattern.None) {
/*  964 */             Color basePatternColor = new Color(((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha());
/*  965 */             Color basePatternOutlineColor = new Color(((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha());
/*      */             
/*  967 */             if (((Boolean)ClickGUI.instance.baseRectPatternShadow.getValue()).booleanValue()) {
/*  968 */               RenderUtils2D.drawRoundedRectFade(this.x + this.width / 2.0F - ((Float)ClickGUI.instance.baseRectPatternShadowWidth.getValue()).floatValue() / 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.baseRectPatternShadowHeight.getValue()).floatValue() / 2.0F + ((ClickGUI.instance.baseRectPatternExtra.getValue() == ClickGUI.BaseRectPatternExtra.Single) ? ((Float)ClickGUI.instance.baseRectPatternShadowY.getValue()).floatValue() : 0.0F), ((Float)ClickGUI.instance.baseRectPatternShadowRadius.getValue()).floatValue(), true, false, this.x + this.width / 2.0F + ((Float)ClickGUI.instance.baseRectPatternShadowWidth.getValue()).floatValue() / 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.baseRectPatternYOffset.getValue()).floatValue() + ((Float)ClickGUI.instance.baseRectPatternShadowHeight.getValue()).floatValue() / 2.0F + ((ClickGUI.instance.baseRectPatternExtra.getValue() == ClickGUI.BaseRectPatternExtra.Single) ? ((Float)ClickGUI.instance.baseRectPatternShadowY.getValue()).floatValue() : 0.0F), (new Color(0, 0, 0, ((Integer)ClickGUI.instance.baseRectPatternShadowAlpha.getValue()).intValue())).getRGB());
/*      */             }
/*      */             
/*  971 */             if (ClickGUI.instance.baseRectPattern.getValue() == ClickGUI.BaseRectPattern.Triangles) {
/*  972 */               if (ClickGUI.instance.baseRectPatternExtra.getValue() == ClickGUI.BaseRectPatternExtra.Single) {
/*  973 */                 if (ClickGUI.instance.baseRectPatternSingleTrianglesExtra.getValue() == ClickGUI.BaseRectPatternTrianglesSingleExtra.Up) {
/*  974 */                   if (((Boolean)ClickGUI.instance.baseRectPatternOutline.getValue()).booleanValue()) {
/*  975 */                     basePatternTrianglesSingle(1, basePatternOutlineColor.getRed(), basePatternOutlineColor.getGreen(), basePatternOutlineColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha(), ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue(), true);
/*      */                   }
/*  977 */                   basePatternTrianglesSingle(1, basePatternColor.getRed(), basePatternColor.getGreen(), basePatternColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha(), ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue() * (1.0F - ((Float)ClickGUI.instance.baseRectPatternOutlineWidth.getValue()).floatValue()), false);
/*      */                 
/*      */                 }
/*  980 */                 else if (ClickGUI.instance.baseRectPatternSingleTrianglesExtra.getValue() == ClickGUI.BaseRectPatternTrianglesSingleExtra.Down) {
/*  981 */                   if (((Boolean)ClickGUI.instance.baseRectPatternOutline.getValue()).booleanValue()) {
/*  982 */                     basePatternTrianglesSingle(2, basePatternOutlineColor.getRed(), basePatternOutlineColor.getGreen(), basePatternOutlineColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha(), ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue(), true);
/*      */                   }
/*  984 */                   basePatternTrianglesSingle(2, basePatternColor.getRed(), basePatternColor.getGreen(), basePatternColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha(), ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue() * (1.0F - ((Float)ClickGUI.instance.baseRectPatternOutlineWidth.getValue()).floatValue()), false);
/*      */                 
/*      */                 }
/*  987 */                 else if (ClickGUI.instance.baseRectPatternSingleTrianglesExtra.getValue() == ClickGUI.BaseRectPatternTrianglesSingleExtra.Left) {
/*  988 */                   if (((Boolean)ClickGUI.instance.baseRectPatternOutline.getValue()).booleanValue()) {
/*  989 */                     basePatternTrianglesSingle(3, basePatternOutlineColor.getRed(), basePatternOutlineColor.getGreen(), basePatternOutlineColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha(), ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue(), true);
/*      */                   }
/*  991 */                   basePatternTrianglesSingle(3, basePatternColor.getRed(), basePatternColor.getGreen(), basePatternColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha(), ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue() * (1.0F - ((Float)ClickGUI.instance.baseRectPatternOutlineWidth.getValue()).floatValue()), false);
/*      */                 }
/*      */                 else {
/*      */                   
/*  995 */                   if (((Boolean)ClickGUI.instance.baseRectPatternOutline.getValue()).booleanValue()) {
/*  996 */                     basePatternTrianglesSingle(4, basePatternOutlineColor.getRed(), basePatternOutlineColor.getGreen(), basePatternOutlineColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha(), ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue(), true);
/*      */                   }
/*  998 */                   basePatternTrianglesSingle(4, basePatternColor.getRed(), basePatternColor.getGreen(), basePatternColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha(), ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue() * (1.0F - ((Float)ClickGUI.instance.baseRectPatternOutlineWidth.getValue()).floatValue()), false);
/*      */                 }
/*      */               
/* 1001 */               } else if (ClickGUI.instance.baseRectPatternExtra.getValue() == ClickGUI.BaseRectPatternExtra.Double) {
/* 1002 */                 if (((Boolean)ClickGUI.instance.baseRectPatternOutline.getValue()).booleanValue()) {
/* 1003 */                   basePatternTrianglesDouble(basePatternOutlineColor.getRed(), basePatternOutlineColor.getGreen(), basePatternOutlineColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha(), true, ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue());
/*      */                 }
/* 1005 */                 basePatternTrianglesDouble(basePatternColor.getRed(), basePatternColor.getGreen(), basePatternColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha(), false, ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue() * (1.0F - ((Float)ClickGUI.instance.baseRectPatternOutlineWidth.getValue()).floatValue()));
/*      */               }
/*      */             
/*      */             }
/* 1009 */             else if (ClickGUI.instance.baseRectPattern.getValue() == ClickGUI.BaseRectPattern.Circles) {
/* 1010 */               if (ClickGUI.instance.baseRectPatternExtra.getValue() == ClickGUI.BaseRectPatternExtra.Single) {
/* 1011 */                 if (((Boolean)ClickGUI.instance.baseRectPatternOutline.getValue()).booleanValue()) {
/* 1012 */                   basePatternCirclesSingle(basePatternOutlineColor.getRed(), basePatternOutlineColor.getGreen(), basePatternOutlineColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha(), ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue(), true);
/*      */                 }
/* 1014 */                 basePatternCirclesSingle(basePatternColor.getRed(), basePatternColor.getGreen(), basePatternColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha(), ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue() * (1.0F - ((Float)ClickGUI.instance.baseRectPatternOutlineWidth.getValue()).floatValue()), false);
/*      */               }
/* 1016 */               else if (ClickGUI.instance.baseRectPatternExtra.getValue() == ClickGUI.BaseRectPatternExtra.Double) {
/* 1017 */                 if (((Boolean)ClickGUI.instance.baseRectPatternOutline.getValue()).booleanValue()) {
/* 1018 */                   basePatternCirclesDouble(basePatternOutlineColor.getRed(), basePatternOutlineColor.getGreen(), basePatternOutlineColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha(), ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue(), true);
/*      */                 }
/* 1020 */                 basePatternCirclesDouble(basePatternColor.getRed(), basePatternColor.getGreen(), basePatternColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha(), ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue() * (1.0F - ((Float)ClickGUI.instance.baseRectPatternOutlineWidth.getValue()).floatValue()), false);
/*      */               }
/*      */             
/*      */             }
/* 1024 */             else if (ClickGUI.instance.baseRectPattern.getValue() == ClickGUI.BaseRectPattern.Diamonds) {
/* 1025 */               if (ClickGUI.instance.baseRectPatternExtra.getValue() == ClickGUI.BaseRectPatternExtra.Single) {
/* 1026 */                 if (((Boolean)ClickGUI.instance.baseRectPatternOutline.getValue()).booleanValue()) {
/* 1027 */                   basePatternDiamondsSingle(basePatternOutlineColor.getRed(), basePatternOutlineColor.getGreen(), basePatternOutlineColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha(), ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue(), true);
/*      */                 }
/* 1029 */                 basePatternDiamondsSingle(basePatternColor.getRed(), basePatternColor.getGreen(), basePatternColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha(), ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue() * (1.0F - ((Float)ClickGUI.instance.baseRectPatternOutlineWidth.getValue()).floatValue()), false);
/*      */               }
/* 1031 */               else if (ClickGUI.instance.baseRectPatternExtra.getValue() == ClickGUI.BaseRectPatternExtra.Double) {
/* 1032 */                 if (((Boolean)ClickGUI.instance.baseRectPatternOutline.getValue()).booleanValue()) {
/* 1033 */                   basePatternDiamondsDouble(basePatternOutlineColor.getRed(), basePatternOutlineColor.getGreen(), basePatternOutlineColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternOutlineColor.getValue()).getAlpha(), ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue(), true);
/*      */                 }
/* 1035 */                 basePatternDiamondsDouble(basePatternColor.getRed(), basePatternColor.getGreen(), basePatternColor.getBlue(), ((Color)ClickGUI.instance.baseRectPatternColor.getValue()).getAlpha(), ((Float)ClickGUI.instance.baseRectPatternSize.getValue()).floatValue() * (1.0F - ((Float)ClickGUI.instance.baseRectPatternOutlineWidth.getValue()).floatValue()), false);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1043 */         if (lastModuleButton && this.extended && (ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Bottom || ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Both)) {
/* 1044 */           this.startY -= ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() + 4;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1049 */       float categoryRectStartX = this.x + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() - ((Float)ClickGUI.instance.rectWidth.getValue()).floatValue() / 2.0F;
/* 1050 */       float categoryRectEndX = (this.x + this.width) + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() + ((Float)ClickGUI.instance.rectWidth.getValue()).floatValue() / 2.0F;
/*      */       
/* 1052 */       float categoryRectStartY = this.y - ((Float)ClickGUI.instance.rectHeight.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.rectY.getValue()).floatValue();
/* 1053 */       float categoryRectEndY = this.y + this.height + ((Float)ClickGUI.instance.rectHeight.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.rectY.getValue()).floatValue();
/*      */       
/* 1055 */       Color categoryRectColor = new Color(((Color)ClickGUI.instance.categoryRectColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.categoryRectColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.categoryRectColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.categoryRectColor.getValue()).getAlpha());
/*      */ 
/*      */       
/* 1058 */       if (isHovered(mouseX, mouseY) && ((Boolean)ClickGUI.instance.categoryRectHoverDifColor.getValue()).booleanValue())
/*      */       {
/* 1060 */         if (!((Boolean)ClickGUI.instance.categoryRectHoverColorSmooth.getValue()).booleanValue()) {
/* 1061 */           categoryRectColor = new Color(((Color)ClickGUI.instance.categoryRectHoverColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.categoryRectHoverColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.categoryRectHoverColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.categoryRectHoverColor.getValue()).getAlpha());
/*      */         } else {
/*      */           
/* 1064 */           storedCategoryHoverLoops.putIfAbsent(this.category.categoryName, Integer.valueOf(0));
/* 1065 */           int hoverLoops = ((Integer)storedCategoryHoverLoops.get(this.category.categoryName)).intValue();
/* 1066 */           if (hoverLoops >= 300) {
/* 1067 */             hoverLoops = 300;
/*      */           }
/* 1069 */           if (hoverLoops <= 0) {
/* 1070 */             hoverLoops = 0;
/*      */           }
/* 1072 */           int nonHoveredToHoveredRed = (int)MathUtilFuckYou.linearInterp(categoryRectColor.getRed(), ((Color)ClickGUI.instance.categoryRectHoverColor.getValue()).getColorColor().getRed(), hoverLoops);
/* 1073 */           int nonHoveredToHoveredGreen = (int)MathUtilFuckYou.linearInterp(categoryRectColor.getGreen(), ((Color)ClickGUI.instance.categoryRectHoverColor.getValue()).getColorColor().getGreen(), hoverLoops);
/* 1074 */           int nonHoveredToHoveredBlue = (int)MathUtilFuckYou.linearInterp(categoryRectColor.getBlue(), ((Color)ClickGUI.instance.categoryRectHoverColor.getValue()).getColorColor().getBlue(), hoverLoops);
/* 1075 */           int nonHoveredToHoveredAlpha = (int)MathUtilFuckYou.linearInterp(categoryRectColor.getAlpha(), ((Color)ClickGUI.instance.categoryRectHoverColor.getValue()).getAlpha(), hoverLoops);
/*      */           
/* 1077 */           categoryRectColor = new Color(nonHoveredToHoveredRed, nonHoveredToHoveredGreen, nonHoveredToHoveredBlue, nonHoveredToHoveredAlpha);
/* 1078 */           hoverLoops = (int)(hoverLoops + ((Float)ClickGUI.instance.categoryRectHoverColorSmoothFactorIn.getValue()).floatValue() * 10.0F);
/* 1079 */           storedCategoryHoverLoops.put(this.category.categoryName, Integer.valueOf(hoverLoops));
/*      */         } 
/*      */       }
/*      */       
/* 1083 */       if (((Boolean)ClickGUI.instance.categoryRectHoverDifColor.getValue()).booleanValue() && ((Boolean)ClickGUI.instance.categoryRectHoverColorSmooth.getValue()).booleanValue() && storedCategoryHoverLoops.containsKey(this.category.categoryName) && !isHovered(mouseX, mouseY)) {
/*      */         
/* 1085 */         int hoverLoops = ((Integer)storedCategoryHoverLoops.get(this.category.categoryName)).intValue();
/* 1086 */         if (hoverLoops <= 0) {
/* 1087 */           hoverLoops = 0;
/*      */         }
/* 1089 */         if (hoverLoops >= 300) {
/* 1090 */           hoverLoops = 300;
/*      */         }
/* 1092 */         int nonHoveredToHoveredRed = (int)MathUtilFuckYou.linearInterp(categoryRectColor.getRed(), ((Color)ClickGUI.instance.categoryRectHoverColor.getValue()).getColorColor().getRed(), hoverLoops);
/* 1093 */         int nonHoveredToHoveredGreen = (int)MathUtilFuckYou.linearInterp(categoryRectColor.getGreen(), ((Color)ClickGUI.instance.categoryRectHoverColor.getValue()).getColorColor().getGreen(), hoverLoops);
/* 1094 */         int nonHoveredToHoveredBlue = (int)MathUtilFuckYou.linearInterp(categoryRectColor.getBlue(), ((Color)ClickGUI.instance.categoryRectHoverColor.getValue()).getColorColor().getBlue(), hoverLoops);
/* 1095 */         int nonHoveredToHoveredAlpha = (int)MathUtilFuckYou.linearInterp(categoryRectColor.getAlpha(), ((Color)ClickGUI.instance.categoryRectHoverColor.getValue()).getAlpha(), hoverLoops);
/*      */         
/* 1097 */         categoryRectColor = new Color(nonHoveredToHoveredRed, nonHoveredToHoveredGreen, nonHoveredToHoveredBlue, nonHoveredToHoveredAlpha);
/* 1098 */         hoverLoops = (int)(hoverLoops - ((Float)ClickGUI.instance.categoryRectHoverColorSmoothFactorOut.getValue()).floatValue() * 10.0F);
/* 1099 */         storedCategoryHoverLoops.put(this.category.categoryName, Integer.valueOf(hoverLoops));
/*      */       } 
/*      */ 
/*      */       
/* 1103 */       if (((Boolean)ClickGUI.instance.guiRoundRect.getValue()).booleanValue()) {
/* 1104 */         RenderUtils2D.drawRoundedRect(categoryRectStartX, categoryRectStartY, ((Float)ClickGUI.instance.radius.getValue()).floatValue(), categoryRectEndX, categoryRectEndY, false, ((Boolean)ClickGUI.instance.arcTopRight.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.arcTopLeft.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.arcDownRight.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.arcDownLeft.getValue()).booleanValue(), categoryRectColor.getRGB());
/*      */       } else {
/*      */         
/* 1107 */         RenderUtils2D.drawRect(categoryRectStartX, categoryRectStartY, categoryRectEndX, categoryRectEndY, categoryRectColor.getRGB());
/*      */       } 
/*      */ 
/*      */       
/* 1111 */       if (((Boolean)ClickGUI.instance.categoryBar.getValue()).booleanValue()) {
/* 1112 */         RenderUtils2D.drawRect((this.x + 4) - ((Float)ClickGUI.instance.categoryBarXScale.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.categoryBarX.getValue()).floatValue(), (this.y + this.height) - ((Float)ClickGUI.instance.categoryBarYScale.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.categoryBarY.getValue()).floatValue(), (this.x + this.width - 4) + ((Float)ClickGUI.instance.categoryBarXScale.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.categoryBarX.getValue()).floatValue(), (this.y + this.height + 1) + ((Float)ClickGUI.instance.categoryBarYScale.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.categoryBarY.getValue()).floatValue(), ((Color)ClickGUI.instance.barColor.getValue()).getColor());
/*      */       }
/*      */ 
/*      */       
/* 1116 */       if (((Boolean)ClickGUI.instance.categoryGlow.getValue()).booleanValue()) {
/* 1117 */         RenderUtils2D.drawRoundedRectFade(this.x + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() - ((Float)ClickGUI.instance.categoryGlowWidth.getValue()).floatValue() / 2.0F, this.y - ((Float)ClickGUI.instance.categoryGlowHeight.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.rectY.getValue()).floatValue(), 1.0F, true, false, (this.x + this.width) + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() + ((Float)ClickGUI.instance.categoryGlowWidth.getValue()).floatValue() / 2.0F, this.y + this.height + ((Float)ClickGUI.instance.categoryGlowHeight.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.rectY.getValue()).floatValue(), ((Color)ClickGUI.instance.categoryGlowColor.getValue()).getColor());
/*      */       }
/*      */ 
/*      */       
/* 1121 */       if (((Boolean)ClickGUI.instance.categoryIcons.getValue()).booleanValue()) {
/* 1122 */         if (((Boolean)ClickGUI.instance.categoryIconsBG.getValue()).booleanValue()) {
/*      */           
/* 1124 */           int categoryIconsBGColor = ((Color)ClickGUI.instance.categoryIconsBGColor.getValue()).getColor();
/*      */           
/* 1126 */           if (ClickGUI.instance.categoryIconsSide.getValue() == ClickGUI.CategoryIconsSides.Left) {
/* 1127 */             float endX = categoryRectStartX + 10.0F + ((Float)ClickGUI.instance.categoryIconsBGSideX.getValue()).floatValue();
/*      */             
/* 1129 */             GL11.glTranslatef((1.0F - ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue()) * (endX - categoryRectStartX) / 2.0F, (1.0F - ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue()) * (categoryRectEndY - categoryRectStartY) / 2.0F, 0.0F);
/*      */             
/* 1131 */             GL11.glTranslatef(categoryRectStartX * (1.0F - ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue()), categoryRectStartY * (1.0F - ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue()), 0.0F);
/* 1132 */             GL11.glScalef(((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue(), ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue(), ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue());
/*      */             
/* 1134 */             RenderUtils2D.drawCustomCategoryRoundedRect(categoryRectStartX, categoryRectStartY, endX, categoryRectEndY, ((Float)ClickGUI.instance.radius.getValue()).floatValue(), false, ((Boolean)ClickGUI.instance.guiRoundRect.getValue()).booleanValue() ? ((Boolean)ClickGUI.instance.arcTopLeft.getValue()).booleanValue() : false, false, ((Boolean)ClickGUI.instance.guiRoundRect.getValue()).booleanValue() ? ((Boolean)ClickGUI.instance.arcDownLeft.getValue()).booleanValue() : false, ((Boolean)ClickGUI.instance.categoryIconsBGSideFade.getValue()).booleanValue(), false, ((Float)ClickGUI.instance.categoryIconsBGSideFadeSize.getValue()).floatValue(), categoryIconsBGColor);
/*      */             
/* 1136 */             GL11.glScalef(1.0F / ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue(), 1.0F / ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue(), 1.0F / ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue());
/* 1137 */             GL11.glTranslatef(categoryRectStartX * (1.0F - ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue()) * -1.0F, categoryRectStartY * (1.0F - ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue()) * -1.0F, 0.0F);
/*      */             
/* 1139 */             GL11.glTranslatef((1.0F - ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue()) * (endX - categoryRectStartX) / 2.0F * -1.0F, (1.0F - ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue()) * (categoryRectEndY - categoryRectStartY) / 2.0F * -1.0F, 0.0F);
/*      */           } else {
/*      */             
/* 1142 */             float startX = categoryRectStartX - 10.0F - ((Float)ClickGUI.instance.categoryIconsBGSideX.getValue()).floatValue();
/*      */             
/* 1144 */             GL11.glTranslatef((1.0F - ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue()) * (categoryRectEndX - startX) / 2.0F, (1.0F - ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue()) * (categoryRectEndY - categoryRectStartY) / 2.0F, 0.0F);
/*      */             
/* 1146 */             GL11.glTranslatef(startX * (1.0F - ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue()), categoryRectStartY * (1.0F - ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue()), 0.0F);
/* 1147 */             GL11.glScalef(((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue(), ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue(), ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue());
/*      */             
/* 1149 */             RenderUtils2D.drawCustomCategoryRoundedRect(startX, categoryRectStartY, categoryRectEndX, categoryRectEndY, ((Float)ClickGUI.instance.radius.getValue()).floatValue(), ((Boolean)ClickGUI.instance.guiRoundRect.getValue()).booleanValue() ? ((Boolean)ClickGUI.instance.arcTopRight.getValue()).booleanValue() : false, false, ((Boolean)ClickGUI.instance.guiRoundRect.getValue()).booleanValue() ? ((Boolean)ClickGUI.instance.arcDownRight.getValue()).booleanValue() : false, false, false, ((Boolean)ClickGUI.instance.categoryIconsBGSideFade.getValue()).booleanValue(), ((Float)ClickGUI.instance.categoryIconsBGSideFadeSize.getValue()).floatValue(), categoryIconsBGColor);
/*      */             
/* 1151 */             GL11.glScalef(1.0F / ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue(), 1.0F / ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue(), 1.0F / ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue());
/* 1152 */             GL11.glTranslatef(startX * (1.0F - ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue()) * -1.0F, categoryRectStartY * (1.0F - ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue()) * -1.0F, 0.0F);
/*      */             
/* 1154 */             GL11.glTranslatef((1.0F - ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue()) * (categoryRectEndX - startX) / 2.0F * -1.0F, (1.0F - ((Float)ClickGUI.instance.categoryIconsBGScaleOutside.getValue()).floatValue()) * (categoryRectEndY - categoryRectStartY) / 2.0F * -1.0F, 0.0F);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1159 */         String categoryIconString = "";
/* 1160 */         if (this.category.categoryName == "Client") {
/* 1161 */           categoryIconString = "0";
/*      */         }
/* 1163 */         else if (this.category.categoryName == "Visuals") {
/* 1164 */           categoryIconString = "1";
/*      */         }
/* 1166 */         else if (this.category.categoryName == "Movement") {
/* 1167 */           categoryIconString = "2";
/*      */         }
/* 1169 */         else if (this.category.categoryName == "Other") {
/* 1170 */           categoryIconString = "3";
/*      */         }
/* 1172 */         else if (this.category.categoryName == "Combat") {
/* 1173 */           categoryIconString = "4";
/*      */         }
/* 1175 */         else if (this.category.categoryName == "HUD") {
/* 1176 */           categoryIconString = "5";
/*      */         } 
/*      */         
/* 1179 */         Color categoryIconsColor = new Color(((Color)ClickGUI.instance.categoryIconsColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.categoryIconsColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.categoryIconsColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.categoryIconsColor.getValue()).getAlpha());
/*      */         
/* 1181 */         if (ClickGUI.instance.categoryIconsSide.getValue() == ClickGUI.CategoryIconsSides.Left) {
/* 1182 */           GL11.glTranslatef(((this.x + 3) + ((Float)ClickGUI.instance.categoryIconsX.getValue()).floatValue()) * (1.0F - ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue()), (this.y + ((Float)ClickGUI.instance.rectY.getValue()).floatValue() + this.height / 2.0F + 2.0F + ((Float)ClickGUI.instance.categoryIconsY.getValue()).floatValue()) * (1.0F - ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue()), 0.0F);
/* 1183 */           GL11.glScalef(((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue(), ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue(), ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue());
/*      */           
/* 1185 */           FontManager.drawModuleMiniIcon(categoryIconString, (int)((this.x + 3) + ((Float)ClickGUI.instance.categoryIconsX.getValue()).floatValue()), (int)(this.y + ((Float)ClickGUI.instance.rectY.getValue()).floatValue() + this.height / 2.0F + 2.0F + ((Float)ClickGUI.instance.categoryIconsY.getValue()).floatValue()), categoryIconsColor);
/*      */           
/* 1187 */           GL11.glScalef(1.0F / ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue(), 1.0F / ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue(), 1.0F / ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue());
/* 1188 */           GL11.glTranslatef(((this.x + 3) + ((Float)ClickGUI.instance.categoryIconsX.getValue()).floatValue()) * (1.0F - ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue()) * -1.0F, (this.y + ((Float)ClickGUI.instance.rectY.getValue()).floatValue() + this.height / 2.0F + 2.0F + ((Float)ClickGUI.instance.categoryIconsY.getValue()).floatValue()) * (1.0F - ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*      */         } else {
/*      */           
/* 1191 */           GL11.glTranslatef(((this.x + this.width - 13) - ((Float)ClickGUI.instance.categoryIconsX.getValue()).floatValue()) * (1.0F - ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue()), (this.y + ((Float)ClickGUI.instance.rectY.getValue()).floatValue() + this.height / 2.0F + 2.0F + ((Float)ClickGUI.instance.categoryIconsY.getValue()).floatValue()) * (1.0F - ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue()), 0.0F);
/* 1192 */           GL11.glScalef(((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue(), ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue(), ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue());
/*      */           
/* 1194 */           FontManager.drawModuleMiniIcon(categoryIconString, (int)((this.x + this.width - 13) - ((Float)ClickGUI.instance.categoryIconsX.getValue()).floatValue()), (int)(this.y + ((Float)ClickGUI.instance.rectY.getValue()).floatValue() + this.height / 2.0F + 2.0F + ((Float)ClickGUI.instance.categoryIconsY.getValue()).floatValue()), categoryIconsColor);
/*      */           
/* 1196 */           GL11.glScalef(1.0F / ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue(), 1.0F / ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue(), 1.0F / ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue());
/* 1197 */           GL11.glTranslatef(((this.x + this.width - 13) - ((Float)ClickGUI.instance.categoryIconsX.getValue()).floatValue()) * (1.0F - ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue()) * -1.0F, (this.y + ((Float)ClickGUI.instance.rectY.getValue()).floatValue() + this.height / 2.0F + 2.0F + ((Float)ClickGUI.instance.categoryIconsY.getValue()).floatValue()) * (1.0F - ((Float)ClickGUI.instance.categoryIconsScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*      */         } 
/*      */ 
/*      */         
/* 1201 */         if (((Boolean)ClickGUI.instance.categoryIconsGlow.getValue()).booleanValue()) {
/* 1202 */           int categoryIconsGlowColor = ((Color)ClickGUI.instance.categoryIconsGlowColor.getValue()).getColor();
/*      */           
/* 1204 */           GlStateManager.func_179118_c();
/* 1205 */           if (ClickGUI.instance.categoryIconsSide.getValue() == ClickGUI.CategoryIconsSides.Left) {
/* 1206 */             RenderUtils2D.drawCustomCircle((this.x + 3) + ((Float)ClickGUI.instance.categoryIconsX.getValue()).floatValue() + FontManager.getIconWidth() / 2.0F, this.y + ((Float)ClickGUI.instance.rectY.getValue()).floatValue() + this.height / 2.0F + 2.0F + ((Float)ClickGUI.instance.categoryIconsY.getValue()).floatValue(), ((Float)ClickGUI.instance.categoryIconsGlowSize.getValue()).floatValue(), categoryIconsGlowColor, (new Color(0, 0, 0, 0)).getRGB());
/*      */           } else {
/*      */             
/* 1209 */             RenderUtils2D.drawCustomCircle((this.x + this.width - 13) - ((Float)ClickGUI.instance.categoryIconsX.getValue()).floatValue() + FontManager.getIconWidth() / 2.0F, this.y + ((Float)ClickGUI.instance.rectY.getValue()).floatValue() + this.height / 2.0F + 2.0F + ((Float)ClickGUI.instance.categoryIconsY.getValue()).floatValue(), ((Float)ClickGUI.instance.categoryIconsGlowSize.getValue()).floatValue(), categoryIconsGlowColor, (new Color(0, 0, 0, 0)).getRGB());
/*      */           } 
/* 1211 */           GlStateManager.func_179141_d();
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1217 */       if (((Boolean)ClickGUI.instance.categoryRectOutline.getValue()).booleanValue()) {
/* 1218 */         int categoryRectOutlineColor = ((Color)ClickGUI.instance.categoryRectOutlineColor.getValue()).getColor();
/*      */         
/* 1220 */         if (((Boolean)ClickGUI.instance.guiRoundRect.getValue()).booleanValue()) {
/* 1221 */           RenderUtils2D.drawCustomRoundedRectOutline(categoryRectStartX, categoryRectStartY, categoryRectEndX, categoryRectEndY, ((Float)ClickGUI.instance.radius.getValue()).floatValue(), ((Float)ClickGUI.instance.categoryRectOutlineWidth.getValue()).floatValue(), ((Boolean)ClickGUI.instance.arcTopRight.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.arcTopLeft.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.arcDownRight.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.arcDownLeft.getValue()).booleanValue(), false, ((Boolean)ClickGUI.instance.categoryRectOutlineBottomLineToggle.getValue()).booleanValue(), categoryRectOutlineColor);
/*      */         } else {
/*      */           
/* 1224 */           RenderUtils2D.drawRectOutline(categoryRectStartX, categoryRectStartY, categoryRectEndX, categoryRectEndY, ((Float)ClickGUI.instance.categoryRectOutlineWidth.getValue()).floatValue(), categoryRectOutlineColor, false, ((Boolean)ClickGUI.instance.categoryRectOutlineBottomLineToggle.getValue()).booleanValue());
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1229 */       float categoryNameTextX = (CustomFont.instance.categoryTextPos.getValue() == CustomFont.TextPos.Center) ? (this.x + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() + this.width / 2.0F - Command.mc.field_71466_p.func_78256_a(this.category.categoryName) / 2.0F) : ((CustomFont.instance.categoryTextPos.getValue() == CustomFont.TextPos.Left) ? (this.x + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() + ((ClickGUI.instance.categoryIconsSide.getValue() == ClickGUI.CategoryIconsSides.Left) ? (((Float)ClickGUI.instance.rectWidth.getValue()).floatValue() / 2.0F * -1.0F + 10.0F + ((Float)ClickGUI.instance.categoryIconsBGSideX.getValue()).floatValue()) : 0.0F)) : (this.x + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() + this.width - Command.mc.field_71466_p.func_78256_a(this.category.categoryName) + ((ClickGUI.instance.categoryIconsSide.getValue() == ClickGUI.CategoryIconsSides.Right) ? (((Float)ClickGUI.instance.rectWidth.getValue()).floatValue() / 2.0F - 10.0F - ((Float)ClickGUI.instance.categoryIconsBGSideX.getValue()).floatValue()) : 0.0F)));
/* 1230 */       float categoryNameTextY = this.y + ((Float)ClickGUI.instance.rectY.getValue()).floatValue() + this.height / 2.0F - this.font.getHeight() / 2.0F - 1.0F;
/*      */       
/* 1232 */       float categoryNameCustomTextX = (CustomFont.instance.categoryTextPos.getValue() == CustomFont.TextPos.Center) ? (this.x + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() + this.width / 2.0F - FontManager.fontRenderer.getStringWidth(this.category.categoryName) / 2.0F) : ((CustomFont.instance.categoryTextPos.getValue() == CustomFont.TextPos.Left) ? (this.x + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() + ((ClickGUI.instance.categoryIconsSide.getValue() == ClickGUI.CategoryIconsSides.Left) ? (((Float)ClickGUI.instance.rectWidth.getValue()).floatValue() / 2.0F * -1.0F + 10.0F + ((Float)ClickGUI.instance.categoryIconsBGSideX.getValue()).floatValue()) : 0.0F)) : (this.x + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() + this.width - this.font.getStringWidth(this.category.categoryName) + ((ClickGUI.instance.categoryIconsSide.getValue() == ClickGUI.CategoryIconsSides.Right) ? (((Float)ClickGUI.instance.rectWidth.getValue()).floatValue() / 2.0F - 10.0F - ((Float)ClickGUI.instance.categoryIconsBGSideX.getValue()).floatValue()) : 0.0F)));
/* 1233 */       float categoryNameCustomTextY = this.y + ((Float)ClickGUI.instance.rectY.getValue()).floatValue() + this.height / 2.0F - this.font.getHeight() / 2.0F + 2.0F;
/*      */       
/* 1235 */       int categoryTextColor = ((Color)CustomFont.instance.categoryTextColor.getValue()).getColor();
/*      */ 
/*      */       
/* 1238 */       Color categoryShadowGradientColor = new Color(0, 0, 0, ((Integer)CustomFont.instance.categoryTextShadowGradientAlpha.getValue()).intValue());
/* 1239 */       if (isHovered(mouseX, mouseY) && ((Boolean)ClickGUI.instance.categoryRectHoverShadowGradientAlpha.getValue()).booleanValue()) {
/* 1240 */         if (!((Boolean)ClickGUI.instance.categoryRectHoverShadowGradientAlphaSmooth.getValue()).booleanValue()) {
/* 1241 */           categoryShadowGradientColor = new Color(0, 0, 0, ((Integer)ClickGUI.instance.categoryRectHoverShadowGradientNewAlpha.getValue()).intValue());
/*      */         } else {
/*      */           
/* 1244 */           storedCategoryShadowGradientHoverLoops.putIfAbsent(this.category.categoryName, Integer.valueOf(0));
/* 1245 */           int hoverLoops = ((Integer)storedCategoryShadowGradientHoverLoops.get(this.category.categoryName)).intValue();
/* 1246 */           if (hoverLoops >= 300) {
/* 1247 */             hoverLoops = 300;
/*      */           }
/* 1249 */           if (hoverLoops <= 0) {
/* 1250 */             hoverLoops = 0;
/*      */           }
/*      */           
/* 1253 */           int nonHoveredToHoveredAlpha = (int)MathUtilFuckYou.linearInterp(categoryShadowGradientColor.getAlpha(), ((Integer)ClickGUI.instance.categoryRectHoverShadowGradientNewAlpha.getValue()).intValue(), hoverLoops);
/*      */           
/* 1255 */           categoryShadowGradientColor = new Color(0, 0, 0, nonHoveredToHoveredAlpha);
/* 1256 */           hoverLoops = (int)(hoverLoops + ((Float)ClickGUI.instance.categoryRectHoverShadowGradientFactorIn.getValue()).floatValue() * 10.0F);
/* 1257 */           storedCategoryShadowGradientHoverLoops.put(this.category.categoryName, Integer.valueOf(hoverLoops));
/*      */         } 
/*      */       }
/*      */       
/* 1261 */       if (((Boolean)ClickGUI.instance.categoryRectHoverShadowGradientAlpha.getValue()).booleanValue() && ((Boolean)ClickGUI.instance.categoryRectHoverColorSmooth.getValue()).booleanValue() && storedCategoryShadowGradientHoverLoops.containsKey(this.category.categoryName) && !isHovered(mouseX, mouseY)) {
/* 1262 */         int hoverLoops = ((Integer)storedCategoryShadowGradientHoverLoops.get(this.category.categoryName)).intValue();
/* 1263 */         if (hoverLoops <= 0) {
/* 1264 */           hoverLoops = 0;
/*      */         }
/* 1266 */         if (hoverLoops >= 300) {
/* 1267 */           hoverLoops = 300;
/*      */         }
/* 1269 */         int nonHoveredToHoveredAlpha = (int)MathUtilFuckYou.linearInterp(categoryShadowGradientColor.getAlpha(), ((Integer)ClickGUI.instance.categoryRectHoverShadowGradientNewAlpha.getValue()).intValue(), hoverLoops);
/*      */         
/* 1271 */         categoryShadowGradientColor = new Color(0, 0, 0, nonHoveredToHoveredAlpha);
/* 1272 */         hoverLoops = (int)(hoverLoops - ((Float)ClickGUI.instance.categoryRectHoverShadowGradientFactorIn.getValue()).floatValue() * 10.0F);
/* 1273 */         storedCategoryShadowGradientHoverLoops.put(this.category.categoryName, Integer.valueOf(hoverLoops));
/*      */       } 
/*      */ 
/*      */       
/* 1277 */       if (CustomFont.instance.categoryFont.getValue() == CustomFont.FontMode.Minecraft) {
/*      */         
/* 1279 */         if (((Boolean)CustomFont.instance.categoryTextShadowGradient.getValue()).booleanValue()) {
/* 1280 */           RenderUtils2D.drawBetterRoundRectFade(categoryNameTextX + 3.0F + ((Float)CustomFont.instance.categoryTextShadowGradientX.getValue()).floatValue() + ((Float)CustomFont.instance.categoryTextX.getValue()).floatValue(), categoryNameTextY + this.font.getHeight() / 2.0F + ((Float)CustomFont.instance.categoryTextShadowGradientY.getValue()).floatValue() + ((Float)CustomFont.instance.categoryTextY.getValue()).floatValue(), categoryNameTextX + Command.mc.field_71466_p.func_78256_a(this.category.categoryName) + 3.0F + ((Float)CustomFont.instance.categoryTextShadowGradientX.getValue()).floatValue() + ((Float)CustomFont.instance.categoryTextX.getValue()).floatValue(), categoryNameTextY + this.font.getHeight() / 2.0F + ((Float)CustomFont.instance.categoryTextShadowGradientY.getValue()).floatValue() + ((Float)CustomFont.instance.categoryTextY.getValue()).floatValue(), ((Float)CustomFont.instance.categoryTextShadowGradientSize.getValue()).floatValue(), 70.0F, false, true, false, categoryShadowGradientColor.getRGB());
/*      */         }
/*      */ 
/*      */         
/* 1284 */         if (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue()) {
/* 1285 */           storedCategoryTextScaleLoops.putIfAbsent(this.category.categoryName, CustomFont.instance.categoryTextScale.getValue());
/* 1286 */           if (isHovered(mouseX, mouseY)) {
/* 1287 */             float hoverCategoryTextScaleLoops = ((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue();
/* 1288 */             hoverCategoryTextScaleLoops += 0.1F * ((Float)ClickGUI.instance.categoryRectHoverShadowGradientFactorOut.getValue()).floatValue();
/* 1289 */             storedCategoryTextScaleLoops.put(this.category.categoryName, Float.valueOf(hoverCategoryTextScaleLoops));
/*      */           } 
/* 1291 */           if (storedCategoryTextScaleLoops.containsKey(this.category.categoryName)) {
/* 1292 */             float hoverCategoryTextScaleLoops = ((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue();
/* 1293 */             if (hoverCategoryTextScaleLoops <= ((Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()) {
/* 1294 */               hoverCategoryTextScaleLoops = ((Float)CustomFont.instance.categoryTextScale.getValue()).floatValue();
/*      */             }
/* 1296 */             if (hoverCategoryTextScaleLoops >= ((Float)ClickGUI.instance.categoryRectHoverTextScaleNewScale.getValue()).floatValue() + 1.0F - ((Float)ClickGUI.instance.categoryRectHoverTextScaleNewScale.getValue()).floatValue() / 2.0F) {
/* 1297 */               hoverCategoryTextScaleLoops = ((Float)ClickGUI.instance.categoryRectHoverTextScaleNewScale.getValue()).floatValue() + 1.0F - ((Float)ClickGUI.instance.categoryRectHoverTextScaleNewScale.getValue()).floatValue() / 2.0F;
/*      */             }
/* 1299 */             storedCategoryTextScaleLoops.put(this.category.categoryName, Float.valueOf(hoverCategoryTextScaleLoops));
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1304 */         if (CustomFont.instance.categoryTextPos.getValue() != CustomFont.TextPos.Left) {
/* 1305 */           if (CustomFont.instance.categoryTextPos.getValue() == CustomFont.TextPos.Center) {
/* 1306 */             GL11.glTranslatef((1.0F - (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() ? storedCategoryTextScaleLoops.get(this.category.categoryName) : (Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()) * Command.mc.field_71466_p.func_78256_a(this.category.categoryName) / 2.0F, 0.0F, 0.0F);
/*      */           }
/* 1308 */           else if (CustomFont.instance.categoryTextPos.getValue() == CustomFont.TextPos.Right) {
/* 1309 */             GL11.glTranslatef((1.0F - (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() ? storedCategoryTextScaleLoops.get(this.category.categoryName) : (Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()) * Command.mc.field_71466_p.func_78256_a(this.category.categoryName), 0.0F, 0.0F);
/*      */           } 
/*      */         }
/*      */         
/* 1313 */         GL11.glTranslatef(categoryNameTextX * (1.0F - (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() ? storedCategoryTextScaleLoops.get(this.category.categoryName) : (Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()), categoryNameTextY * (1.0F - (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() ? storedCategoryTextScaleLoops.get(this.category.categoryName) : (Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()), 0.0F);
/* 1314 */         if (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() && storedCategoryTextScaleLoops.containsKey(this.category.categoryName)) {
/* 1315 */           GL11.glScalef(((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue(), ((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue(), ((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue());
/*      */         } else {
/*      */           
/* 1318 */           GL11.glScalef(((Float)CustomFont.instance.categoryTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.categoryTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.categoryTextScale.getValue()).floatValue());
/*      */         } 
/*      */         
/* 1321 */         GL11.glEnable(3553);
/* 1322 */         Command.mc.field_71466_p.func_175065_a(this.category.categoryName, categoryNameTextX + ((Float)CustomFont.instance.categoryTextX.getValue()).floatValue(), categoryNameTextY + ((Float)CustomFont.instance.categoryTextY.getValue()).floatValue(), categoryTextColor, ((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue());
/* 1323 */         GL11.glDisable(3553);
/*      */         
/* 1325 */         if (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue()) {
/* 1326 */           GL11.glScalef(1.0F / ((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue(), 1.0F / ((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue(), 1.0F / ((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue());
/*      */         } else {
/*      */           
/* 1329 */           GL11.glScalef(1.0F / ((Float)CustomFont.instance.categoryTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.categoryTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.categoryTextScale.getValue()).floatValue());
/*      */         } 
/* 1331 */         GL11.glTranslatef(categoryNameTextX * (1.0F - (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() ? storedCategoryTextScaleLoops.get(this.category.categoryName) : (Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()) * -1.0F, categoryNameTextY * (1.0F - (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() ? storedCategoryTextScaleLoops.get(this.category.categoryName) : (Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*      */         
/* 1333 */         if (CustomFont.instance.categoryTextPos.getValue() != CustomFont.TextPos.Left) {
/* 1334 */           if (CustomFont.instance.categoryTextPos.getValue() == CustomFont.TextPos.Center) {
/* 1335 */             GL11.glTranslatef((1.0F - (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() ? storedCategoryTextScaleLoops.get(this.category.categoryName) : (Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()) * Command.mc.field_71466_p.func_78256_a(this.category.categoryName) / 2.0F * -1.0F, 0.0F, 0.0F);
/*      */           }
/* 1337 */           else if (CustomFont.instance.categoryTextPos.getValue() == CustomFont.TextPos.Right) {
/* 1338 */             GL11.glTranslatef((1.0F - (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() ? storedCategoryTextScaleLoops.get(this.category.categoryName) : (Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()) * Command.mc.field_71466_p.func_78256_a(this.category.categoryName) * -1.0F, 0.0F, 0.0F);
/*      */           } 
/*      */         }
/*      */         
/* 1342 */         if (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() && !isHovered(mouseX, mouseY)) {
/* 1343 */           float hoverCategoryTextScaleLoops = ((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue();
/* 1344 */           hoverCategoryTextScaleLoops -= 0.1F * ((Float)ClickGUI.instance.categoryRectHoverTextScaleFactorOut.getValue()).floatValue();
/* 1345 */           storedCategoryTextScaleLoops.put(this.category.categoryName, Float.valueOf(hoverCategoryTextScaleLoops));
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1350 */         if (((Boolean)CustomFont.instance.categoryTextShadowGradient.getValue()).booleanValue()) {
/* 1351 */           RenderUtils2D.drawBetterRoundRectFade(categoryNameTextX + 3.0F + ((Float)CustomFont.instance.categoryTextShadowGradientX.getValue()).floatValue() + ((Float)CustomFont.instance.categoryTextX.getValue()).floatValue(), categoryNameTextY + this.font.getHeight() / 2.0F + ((Float)CustomFont.instance.categoryTextShadowGradientY.getValue()).floatValue() + ((Float)CustomFont.instance.categoryTextY.getValue()).floatValue(), categoryNameTextX + this.font.getStringWidth(this.category.categoryName) + 3.0F + ((Float)CustomFont.instance.categoryTextShadowGradientX.getValue()).floatValue() + ((Float)CustomFont.instance.categoryTextX.getValue()).floatValue(), categoryNameTextY + this.font.getHeight() / 2.0F + ((Float)CustomFont.instance.categoryTextShadowGradientY.getValue()).floatValue() + ((Float)CustomFont.instance.categoryTextY.getValue()).floatValue(), ((Float)CustomFont.instance.categoryTextShadowGradientSize.getValue()).floatValue(), 70.0F, false, true, false, categoryShadowGradientColor.getRGB());
/*      */         }
/*      */ 
/*      */         
/* 1355 */         if (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue()) {
/* 1356 */           storedCategoryTextScaleLoops.putIfAbsent(this.category.categoryName, CustomFont.instance.categoryTextScale.getValue());
/* 1357 */           if (isHovered(mouseX, mouseY)) {
/* 1358 */             float hoverCategoryTextScaleLoops = ((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue();
/* 1359 */             hoverCategoryTextScaleLoops += 0.1F * ((Float)ClickGUI.instance.categoryRectHoverShadowGradientFactorOut.getValue()).floatValue();
/* 1360 */             storedCategoryTextScaleLoops.put(this.category.categoryName, Float.valueOf(hoverCategoryTextScaleLoops));
/*      */           } 
/* 1362 */           if (storedCategoryTextScaleLoops.containsKey(this.category.categoryName)) {
/* 1363 */             float hoverCategoryTextScaleLoops = ((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue();
/* 1364 */             if (hoverCategoryTextScaleLoops <= ((Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()) {
/* 1365 */               hoverCategoryTextScaleLoops = ((Float)CustomFont.instance.categoryTextScale.getValue()).floatValue();
/*      */             }
/* 1367 */             if (hoverCategoryTextScaleLoops >= ((Float)ClickGUI.instance.categoryRectHoverTextScaleNewScale.getValue()).floatValue() + 1.0F - ((Float)ClickGUI.instance.categoryRectHoverTextScaleNewScale.getValue()).floatValue() / 2.0F) {
/* 1368 */               hoverCategoryTextScaleLoops = ((Float)ClickGUI.instance.categoryRectHoverTextScaleNewScale.getValue()).floatValue() + 1.0F - ((Float)ClickGUI.instance.categoryRectHoverTextScaleNewScale.getValue()).floatValue() / 2.0F;
/*      */             }
/* 1370 */             storedCategoryTextScaleLoops.put(this.category.categoryName, Float.valueOf(hoverCategoryTextScaleLoops));
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1375 */         if (CustomFont.instance.categoryTextPos.getValue() != CustomFont.TextPos.Left) {
/* 1376 */           if (CustomFont.instance.categoryTextPos.getValue() == CustomFont.TextPos.Center) {
/* 1377 */             GL11.glTranslatef((1.0F - (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() ? storedCategoryTextScaleLoops.get(this.category.categoryName) : (Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()) * FontManager.getWidth(this.category.categoryName) / 2.0F, 0.0F, 0.0F);
/*      */           }
/* 1379 */           else if (CustomFont.instance.categoryTextPos.getValue() == CustomFont.TextPos.Right) {
/* 1380 */             GL11.glTranslatef((1.0F - (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() ? storedCategoryTextScaleLoops.get(this.category.categoryName) : (Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()) * FontManager.getWidth(this.category.categoryName), 0.0F, 0.0F);
/*      */           } 
/*      */         }
/*      */         
/* 1384 */         GL11.glTranslatef(categoryNameCustomTextX * (1.0F - (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() ? storedCategoryTextScaleLoops.get(this.category.categoryName) : (Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()), categoryNameCustomTextY * (1.0F - (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() ? storedCategoryTextScaleLoops.get(this.category.categoryName) : (Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()), 0.0F);
/* 1385 */         if (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() && storedCategoryTextScaleLoops.containsKey(this.category.categoryName)) {
/* 1386 */           GL11.glScalef(((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue(), ((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue(), ((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue());
/*      */         } else {
/*      */           
/* 1389 */           GL11.glScalef(((Float)CustomFont.instance.categoryTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.categoryTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.categoryTextScale.getValue()).floatValue());
/*      */         } 
/*      */         
/* 1392 */         if (((Boolean)CustomFont.instance.categoryTextShadow.getValue()).booleanValue()) {
/* 1393 */           FontManager.drawShadowCategory(this.category.categoryName, categoryNameCustomTextX + ((Float)CustomFont.instance.categoryTextX.getValue()).floatValue(), categoryNameCustomTextY + ((Float)CustomFont.instance.categoryTextY.getValue()).floatValue(), categoryTextColor);
/*      */         } else {
/*      */           
/* 1396 */           FontManager.drawCategory(this.category.categoryName, categoryNameCustomTextX + ((Float)CustomFont.instance.categoryTextX.getValue()).floatValue(), categoryNameCustomTextY + ((Float)CustomFont.instance.categoryTextY.getValue()).floatValue(), categoryTextColor);
/*      */         } 
/*      */         
/* 1399 */         if (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue()) {
/* 1400 */           GL11.glScalef(1.0F / ((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue(), 1.0F / ((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue(), 1.0F / ((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue());
/*      */         } else {
/*      */           
/* 1403 */           GL11.glScalef(1.0F / ((Float)CustomFont.instance.categoryTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.categoryTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.categoryTextScale.getValue()).floatValue());
/*      */         } 
/* 1405 */         GL11.glTranslatef(categoryNameCustomTextX * (1.0F - (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() ? storedCategoryTextScaleLoops.get(this.category.categoryName) : (Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()) * -1.0F, categoryNameCustomTextY * (1.0F - (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() ? storedCategoryTextScaleLoops.get(this.category.categoryName) : (Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*      */         
/* 1407 */         if (CustomFont.instance.categoryTextPos.getValue() != CustomFont.TextPos.Left) {
/* 1408 */           if (CustomFont.instance.categoryTextPos.getValue() == CustomFont.TextPos.Center) {
/* 1409 */             GL11.glTranslatef((1.0F - (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() ? storedCategoryTextScaleLoops.get(this.category.categoryName) : (Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()) * FontManager.getWidth(this.category.categoryName) / 2.0F * -1.0F, 0.0F, 0.0F);
/*      */           }
/* 1411 */           else if (CustomFont.instance.categoryTextPos.getValue() == CustomFont.TextPos.Right) {
/* 1412 */             GL11.glTranslatef((1.0F - (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() ? storedCategoryTextScaleLoops.get(this.category.categoryName) : (Float)CustomFont.instance.categoryTextScale.getValue()).floatValue()) * FontManager.getWidth(this.category.categoryName) * -1.0F, 0.0F, 0.0F);
/*      */           } 
/*      */         }
/*      */         
/* 1416 */         if (((Boolean)ClickGUI.instance.categoryRectHoverTextScale.getValue()).booleanValue() && !isHovered(mouseX, mouseY)) {
/* 1417 */           float hoverCategoryTextScaleLoops = ((Float)storedCategoryTextScaleLoops.get(this.category.categoryName)).floatValue();
/* 1418 */           hoverCategoryTextScaleLoops -= 0.1F * ((Float)ClickGUI.instance.categoryRectHoverTextScaleFactorOut.getValue()).floatValue();
/* 1419 */           storedCategoryTextScaleLoops.put(this.category.categoryName, Float.valueOf(hoverCategoryTextScaleLoops));
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1424 */       staticY = this.y;
/*      */       
/* 1426 */       if (staticY < 0)
/* 1427 */         staticY = 500; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void drawShadowsAndGlow(int mouseX, int mouseY) {
/* 1432 */     if (this.dragging) {
/* 1433 */       this.x = this.x2 + mouseX;
/* 1434 */       this.y = this.y2 + mouseY;
/*      */     } 
/*      */     
/* 1437 */     calcHeight();
/*      */ 
/*      */     
/* 1440 */     if (((Boolean)ClickGUI.instance.guiCategoryShadow.getValue()).booleanValue()) {
/* 1441 */       RenderUtils2D.drawRoundedRectFade(this.x - (this.width * ((Float)ClickGUI.instance.shadowSizeFactorX.getValue()).floatValue() - this.width) / 2.0F, this.y - (this.height * ((Float)ClickGUI.instance.shadowSizeFactorY.getValue()).floatValue() - this.height) / 2.0F, ((Float)ClickGUI.instance.shadowRadiusCategory.getValue()).floatValue(), true, false, this.x + this.width * ((Float)ClickGUI.instance.shadowSizeFactorX.getValue()).floatValue() - (this.width * ((Float)ClickGUI.instance.shadowSizeFactorX.getValue()).floatValue() - this.width) / 2.0F, this.y + this.height * ((Float)ClickGUI.instance.shadowSizeFactorY.getValue()).floatValue() - (this.height * ((Float)ClickGUI.instance.shadowSizeFactorY.getValue()).floatValue() - this.height) / 2.0F, (new Color(0, 0, 0, ((Integer)ClickGUI.instance.shadowAlpha.getValue()).intValue())).getRGB());
/*      */     }
/*      */     
/* 1444 */     if (lastModuleButton && this.extended && (ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Bottom || ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Both)) {
/* 1445 */       this.startY += ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() + 2;
/*      */     }
/*      */     
/* 1448 */     if (((Boolean)ClickGUI.instance.guiModuleShadow.getValue()).booleanValue()) {
/* 1449 */       RenderUtils2D.drawBetterRoundRectFade((this.x + 4), (this.y + this.height), (this.x + this.width - 4), (this.startY - 2), ((Float)ClickGUI.instance.moduleShadowSizeFactor.getValue()).floatValue(), 70.0F, true, ((Boolean)ClickGUI.instance.guiModuleShadowFilled.getValue()).booleanValue(), ((Boolean)ClickGUI.instance.guiCategoryPanelFadeDownExtend.getValue()).booleanValue(), (new Color(0, 0, 0, ((Integer)ClickGUI.instance.shadowAlphaModules.getValue()).intValue())).getRGB());
/*      */     }
/*      */     
/* 1452 */     if (((Boolean)ClickGUI.instance.panelBaseShadow.getValue()).booleanValue() && ((Boolean)ClickGUI.instance.guiCategoryBase.getValue()).booleanValue()) {
/* 1453 */       RenderUtils2D.drawRoundedRectFade((this.x + 4) - ((Float)ClickGUI.instance.outlineWidth.getValue()).floatValue() / 3.0F - ((Float)ClickGUI.instance.widthBase.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.panelBaseShadowWidth.getValue()).floatValue() / 2.0F, (this.startY - 1) - ((Float)ClickGUI.instance.panelBaseShadowHeight.getValue()).floatValue() / 2.0F, ((Float)ClickGUI.instance.panelBaseShadowRadius.getValue()).floatValue(), ((Boolean)ClickGUI.instance.panelBaseShadowFilled.getValue()).booleanValue(), false, (this.x + this.width - 4) + ((Float)ClickGUI.instance.outlineWidth.getValue()).floatValue() / 3.0F + ((Float)ClickGUI.instance.widthBase.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.panelBaseShadowWidth.getValue()).floatValue() / 2.0F, this.startY + ((Float)ClickGUI.instance.heightBase.getValue()).floatValue() + ((Float)ClickGUI.instance.panelBaseShadowHeight.getValue()).floatValue() / 2.0F, (new Color(0, 0, 0, ((Integer)ClickGUI.instance.panelBaseShadowAlpha.getValue()).intValue())).getRGB());
/*      */     }
/*      */     
/* 1456 */     if (lastModuleButton && this.extended && (ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Bottom || ClickGUI.instance.panelExtensions.getValue() == ClickGUI.PanelExtensions.Both)) {
/* 1457 */       this.startY -= ((Integer)ClickGUI.instance.panelExtensionsHeight.getValue()).intValue() + 2;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1462 */     if (((Boolean)ClickGUI.instance.categoryRectHoverEffect.getValue()).booleanValue() && 
/* 1463 */       ClickGUI.instance.categoryRectHoverParticlesMode.getValue() != ClickGUI.CategoryRectHoverParticlesMode.None) {
/*      */       
/* 1465 */       if (renderLoopsCategoryRectHoverParticles >= 1000) {
/* 1466 */         renderLoopsCategoryRectHoverParticles = 0;
/*      */       }
/*      */       
/* 1469 */       if (renderLoopsCategoryRectHoverParticles % (int)(1.0F / ((Float)ClickGUI.instance.categoryRectHoverParticlesGenerateRate.getValue()).floatValue() / 3.0F) == 0 && isHovered(mouseX, mouseY)) {
/* 1470 */         float triAngle, triSpinSpeed; categoryRectHoverParticlesList.put(Integer.valueOf(categoryRectHoverParticlesId), new Vector2f((float)((this.x + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() - ((Float)ClickGUI.instance.rectWidth.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.categoryRectHoverParticlesStartXOffset.getValue()).floatValue()) + (this.width + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() + ((Float)ClickGUI.instance.rectWidth.getValue()).floatValue() / 2.0F - ((Float)ClickGUI.instance.categoryRectHoverParticlesEndXOffset.getValue()).floatValue() - ((Float)ClickGUI.instance.categoryRectHoverParticlesStartXOffset.getValue()).floatValue()) * Math.random()), this.y - ((Float)ClickGUI.instance.rectHeight.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.rectY.getValue()).floatValue()));
/* 1471 */         categoryRectHoverParticlesOriginalYs.put(Integer.valueOf(categoryRectHoverParticlesId), Float.valueOf(this.y - ((Float)ClickGUI.instance.rectHeight.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.rectY.getValue()).floatValue()));
/* 1472 */         float randomSpeed = 0.0F;
/* 1473 */         if (((Boolean)ClickGUI.instance.categoryRectHoverParticlesRiseSpeedRandom.getValue()).booleanValue()) {
/* 1474 */           randomSpeed = (float)(Math.random() * ((Float)ClickGUI.instance.categoryRectHoverParticlesRiseSpeedRandomMax.getValue()).floatValue());
/* 1475 */           if (randomSpeed < ((Float)ClickGUI.instance.categoryRectHoverParticlesRiseSpeedRandomMin.getValue()).floatValue()) {
/* 1476 */             randomSpeed = ((Float)ClickGUI.instance.categoryRectHoverParticlesRiseSpeedRandomMin.getValue()).floatValue();
/*      */           }
/*      */         } 
/* 1479 */         categoryRectHoverParticlesSpeed.put(Integer.valueOf(categoryRectHoverParticlesId), ((Boolean)ClickGUI.instance.categoryRectHoverParticlesRiseSpeedRandom.getValue()).booleanValue() ? Float.valueOf(randomSpeed) : (Float)ClickGUI.instance.categoryRectHoverParticlesRiseSpeed.getValue());
/*      */         
/* 1481 */         float randomSize = 0.0F;
/* 1482 */         if (((Boolean)ClickGUI.instance.categoryRectHoverParticlesRandomSize.getValue()).booleanValue()) {
/* 1483 */           randomSize = (float)(Math.random() * ((Float)ClickGUI.instance.categoryRectHoverParticlesRandomSizeMax.getValue()).floatValue());
/* 1484 */           if (randomSize < ((Float)ClickGUI.instance.categoryRectHoverParticlesRandomSizeMin.getValue()).floatValue()) {
/* 1485 */             randomSize = ((Float)ClickGUI.instance.categoryRectHoverParticlesRandomSizeMin.getValue()).floatValue();
/*      */           }
/*      */         } 
/* 1488 */         categoryRectHoverParticlesSize.put(Integer.valueOf(categoryRectHoverParticlesId), ((Boolean)ClickGUI.instance.categoryRectHoverParticlesRandomSize.getValue()).booleanValue() ? Float.valueOf(randomSize) : (Float)ClickGUI.instance.categoryRectHoverParticlesSize.getValue());
/*      */ 
/*      */         
/* 1491 */         if (((Boolean)ClickGUI.instance.categoryRectHoverParticlesTriangleRandomAngle.getValue()).booleanValue()) {
/* 1492 */           triAngle = (float)(Math.random() * 360.0D);
/*      */         } else {
/*      */           
/* 1495 */           triAngle = ((Float)ClickGUI.instance.categoryRectHoverParticlesTrianglesAngle.getValue()).floatValue();
/*      */         } 
/* 1497 */         categoryRectHoverParticlesTriAngle.put(Integer.valueOf(categoryRectHoverParticlesId), Float.valueOf(triAngle));
/*      */         
/* 1499 */         if (((Boolean)ClickGUI.instance.categoryRectHoverParticlesRandomTriangleSpinSpeed.getValue()).booleanValue()) {
/* 1500 */           triSpinSpeed = (float)(Math.random() * ((Float)ClickGUI.instance.categoryRectHoverParticlesRandomTriangleSpinSpeedMax.getValue()).floatValue());
/* 1501 */           if (triSpinSpeed < ((Float)ClickGUI.instance.categoryRectHoverParticlesRandomTriangleSpinSpeedMin.getValue()).floatValue()) {
/* 1502 */             triSpinSpeed = ((Float)ClickGUI.instance.categoryRectHoverParticlesRandomTriangleSpinSpeedMin.getValue()).floatValue();
/*      */           }
/*      */         } else {
/*      */           
/* 1506 */           triSpinSpeed = ((Float)ClickGUI.instance.categoryRectHoverParticlesTrianglesSpinSpeed.getValue()).floatValue();
/*      */         } 
/* 1508 */         categoryRectHoverParticlesTriSpinSpeed.put(Integer.valueOf(categoryRectHoverParticlesId), Float.valueOf(triSpinSpeed));
/* 1509 */         categoryRectHoverParticlesId++;
/* 1510 */         if (categoryRectHoverParticlesId >= 500) {
/* 1511 */           categoryRectHoverParticlesId = 0;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1516 */       for (Map.Entry<Integer, Vector2f> entry : (new HashMap<>(categoryRectHoverParticlesList)).entrySet()) {
/*      */         
/* 1518 */         float threader = (((Float)categoryRectHoverParticlesOriginalYs.get(entry.getKey())).floatValue() - ((Vector2f)entry.getValue()).y) / (((Float)categoryRectHoverParticlesOriginalYs.get(entry.getKey())).floatValue() - ((Float)categoryRectHoverParticlesOriginalYs.get(entry.getKey())).floatValue() - ((Float)ClickGUI.instance.categoryRectHoverParticlesHeightCap.getValue()).floatValue());
/* 1519 */         int theAlpha = (ClickGUI.instance.categoryRectHoverParticlesScaleFadeMode.getValue() == ClickGUI.CategoryRectHoverParticlesScaleFadeMode.Alpha || ClickGUI.instance.categoryRectHoverParticlesScaleFadeMode.getValue() == ClickGUI.CategoryRectHoverParticlesScaleFadeMode.Both) ? (int)((((Color)ClickGUI.instance.categoryRectHoverParticlesColor.getValue()).getAlpha() * -1.0F) * Math.pow(threader, (((Float)ClickGUI.instance.categoryRectHoverParticlesAlphaFadeFactor.getValue()).floatValue() / 10.0F)) + ((Color)ClickGUI.instance.categoryRectHoverParticlesColor.getValue()).getAlpha()) : ((Color)ClickGUI.instance.categoryRectHoverParticlesColor.getValue()).getAlpha();
/* 1520 */         if (theAlpha > ((Color)ClickGUI.instance.categoryRectHoverParticlesColor.getValue()).getAlpha()) {
/* 1521 */           theAlpha = ((Color)ClickGUI.instance.categoryRectHoverParticlesColor.getValue()).getAlpha();
/*      */         }
/* 1523 */         if (theAlpha <= 0) {
/* 1524 */           theAlpha = 0;
/*      */         }
/*      */         
/* 1527 */         float theScale = (ClickGUI.instance.categoryRectHoverParticlesScaleFadeMode.getValue() == ClickGUI.CategoryRectHoverParticlesScaleFadeMode.Scale || ClickGUI.instance.categoryRectHoverParticlesScaleFadeMode.getValue() == ClickGUI.CategoryRectHoverParticlesScaleFadeMode.Both) ? (float)((((Float)categoryRectHoverParticlesSize.get(entry.getKey())).floatValue() * -1.0F) * Math.pow(threader, (((Float)ClickGUI.instance.categoryRectHoverParticlesScaleFadeFactor.getValue()).floatValue() / 10.0F)) + ((Float)categoryRectHoverParticlesSize.get(entry.getKey())).floatValue()) : ((Float)categoryRectHoverParticlesSize.get(entry.getKey())).floatValue();
/* 1528 */         if (theScale > ((Float)categoryRectHoverParticlesSize.get(entry.getKey())).floatValue()) {
/* 1529 */           theScale = ((Float)categoryRectHoverParticlesSize.get(entry.getKey())).floatValue();
/*      */         }
/* 1531 */         if (theScale <= 0.0F) {
/* 1532 */           theScale = 0.0F;
/*      */         }
/*      */         
/* 1535 */         GlStateManager.func_179118_c();
/* 1536 */         Color hoverParticleColor = new Color(((Color)ClickGUI.instance.categoryRectHoverParticlesColor.getValue()).getColorColor().getRed(), ((Color)ClickGUI.instance.categoryRectHoverParticlesColor.getValue()).getColorColor().getGreen(), ((Color)ClickGUI.instance.categoryRectHoverParticlesColor.getValue()).getColorColor().getBlue(), ((Color)ClickGUI.instance.categoryRectHoverParticlesColor.getValue()).getAlpha());
/* 1537 */         int realHoverParticleColor = (new Color(hoverParticleColor.getRed(), hoverParticleColor.getGreen(), hoverParticleColor.getBlue(), theAlpha)).getRGB();
/* 1538 */         if (ClickGUI.instance.categoryRectHoverParticlesMode.getValue() == ClickGUI.CategoryRectHoverParticlesMode.Circles) {
/* 1539 */           RenderUtils2D.drawCircle(((Vector2f)entry.getValue()).x, ((Vector2f)entry.getValue()).y, theScale, realHoverParticleColor);
/*      */         }
/* 1541 */         else if (ClickGUI.instance.categoryRectHoverParticlesMode.getValue() == ClickGUI.CategoryRectHoverParticlesMode.Diamonds) {
/* 1542 */           RenderUtils2D.drawRhombus(((Vector2f)entry.getValue()).x, ((Vector2f)entry.getValue()).y, theScale, realHoverParticleColor);
/*      */         }
/* 1544 */         else if (ClickGUI.instance.categoryRectHoverParticlesMode.getValue() == ClickGUI.CategoryRectHoverParticlesMode.Triangles) {
/* 1545 */           float theTriAngle = ((Float)categoryRectHoverParticlesTriAngle.get(entry.getKey())).floatValue();
/* 1546 */           if (((Boolean)ClickGUI.instance.categoryRectHoverParticlesTrianglesSpin.getValue()).booleanValue()) theTriAngle += ((Float)categoryRectHoverParticlesTriSpinSpeed.get(entry.getKey())).floatValue(); 
/* 1547 */           if (theTriAngle >= 360.0F) theTriAngle = 0.0F;
/*      */           
/* 1549 */           GL11.glTranslatef(((Vector2f)entry.getValue()).x, ((Vector2f)entry.getValue()).y, 0.0F);
/* 1550 */           GL11.glRotatef(theTriAngle, 0.0F, 0.0F, 1.0F);
/* 1551 */           GL11.glTranslatef(((Vector2f)entry.getValue()).x * -1.0F, ((Vector2f)entry.getValue()).y * -1.0F, 0.0F);
/*      */           
/* 1553 */           RenderUtils2D.drawEquilateralTriangle(((Vector2f)entry.getValue()).x, ((Vector2f)entry.getValue()).y, false, theScale, realHoverParticleColor);
/*      */           
/* 1555 */           GL11.glTranslatef(((Vector2f)entry.getValue()).x, ((Vector2f)entry.getValue()).y, 0.0F);
/* 1556 */           GL11.glRotatef(theTriAngle * -1.0F, 0.0F, 0.0F, 1.0F);
/* 1557 */           GL11.glTranslatef(((Vector2f)entry.getValue()).x * -1.0F, ((Vector2f)entry.getValue()).y * -1.0F, 0.0F);
/*      */           
/* 1559 */           if (((Boolean)ClickGUI.instance.categoryRectHoverParticlesTrianglesSpin.getValue()).booleanValue()) categoryRectHoverParticlesTriAngle.put(entry.getKey(), Float.valueOf(theTriAngle)); 
/*      */         } 
/* 1561 */         GlStateManager.func_179141_d();
/*      */         
/* 1563 */         ((Vector2f)entry.getValue()).y -= ((Float)categoryRectHoverParticlesSpeed.get(entry.getKey())).floatValue();
/*      */         
/* 1565 */         if (((Vector2f)entry.getValue()).y <= ((Float)categoryRectHoverParticlesOriginalYs.get(entry.getKey())).floatValue() - ((Float)ClickGUI.instance.categoryRectHoverParticlesHeightCap.getValue()).floatValue()) {
/* 1566 */           categoryRectHoverParticlesList.remove(entry.getKey());
/* 1567 */           categoryRectHoverParticlesOriginalYs.remove(entry.getKey());
/* 1568 */           categoryRectHoverParticlesSpeed.remove(entry.getKey());
/* 1569 */           categoryRectHoverParticlesSize.remove(entry.getKey());
/* 1570 */           categoryRectHoverParticlesTriAngle.remove(entry.getKey());
/* 1571 */           categoryRectHoverParticlesTriSpinSpeed.remove(entry.getKey());
/*      */         } 
/*      */       } 
/*      */       
/* 1575 */       renderLoopsCategoryRectHoverParticles++;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1580 */     if (((Boolean)ClickGUI.instance.rectHornsShadow.getValue()).booleanValue() && ((Boolean)ClickGUI.instance.rectHorns.getValue()).booleanValue()) {
/* 1581 */       GlStateManager.func_179118_c();
/* 1582 */       RenderUtils2D.drawCustomCircle(this.x + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() - ((Float)ClickGUI.instance.rectWidth.getValue()).floatValue() / 2.0F + ((Integer)ClickGUI.instance.rectHornsX.getValue()).intValue() + ((Float)ClickGUI.instance.rectHornsShadowXOffsetLeft.getValue()).floatValue() - ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue() / 2.0F, this.y - ((Float)ClickGUI.instance.rectHeight.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.rectY.getValue()).floatValue() + ((Integer)ClickGUI.instance.rectHornsY.getValue()).intValue() + ((Float)ClickGUI.instance.rectHornsSHadowsYOffset.getValue()).floatValue() - ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue() / 2.0F, ((Float)ClickGUI.instance.rectHornsShadowSize.getValue()).floatValue() * 3.0F, (new Color(0, 0, 0, ((Integer)ClickGUI.instance.rectHornsShadowAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/* 1583 */       RenderUtils2D.drawCustomCircle((this.x + this.width) + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() - ((Float)ClickGUI.instance.rectWidth.getValue()).floatValue() / 2.0F + ((Integer)ClickGUI.instance.rectHornsX.getValue()).intValue() - ((Float)ClickGUI.instance.rectHornsShadowXOffsetRight.getValue()).floatValue() - ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue() / 2.0F, this.y - ((Float)ClickGUI.instance.rectHeight.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.rectY.getValue()).floatValue() + ((Integer)ClickGUI.instance.rectHornsY.getValue()).intValue() + ((Float)ClickGUI.instance.rectHornsSHadowsYOffset.getValue()).floatValue() - ((Integer)ClickGUI.instance.rectHornsScale.getValue()).intValue() / 2.0F, ((Float)ClickGUI.instance.rectHornsShadowSize.getValue()).floatValue() * 3.0F, (new Color(0, 0, 0, ((Integer)ClickGUI.instance.rectHornsShadowAlpha.getValue()).intValue())).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/* 1584 */       GlStateManager.func_179141_d();
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 1589 */     if (mouseButton == 0 && isHovered(mouseX, mouseY)) {
/* 1590 */       this.x2 = this.x - mouseX;
/* 1591 */       this.y2 = this.y - mouseY;
/* 1592 */       this.dragging = true;
/* 1593 */       if (this.category.isHUD)
/* 1594 */       { Collections.swap(HUDEditorRenderer.instance.panels, 0, HUDEditorRenderer.instance.panels.indexOf(this)); }
/* 1595 */       else { Collections.swap(ClickGUIRenderer.instance.panels, 0, ClickGUIRenderer.instance.panels.indexOf(this)); }
/* 1596 */        return true;
/*      */     } 
/* 1598 */     if (mouseButton == 1 && isHovered(mouseX, mouseY)) {
/* 1599 */       this.extended = !this.extended;
/* 1600 */       this.panelTimer.reset();
/* 1601 */       return true;
/*      */     } 
/* 1603 */     return false;
/*      */   }
/*      */   
/*      */   public void mouseReleased(int mouseX, int mouseY, int state) {
/* 1607 */     if (state == 0) {
/* 1608 */       this.dragging = false;
/*      */     }
/* 1610 */     for (Component part : this.elements) {
/* 1611 */       part.mouseReleased(mouseX, mouseY, state);
/*      */     }
/*      */   }
/*      */   
/*      */   public void keyTyped(char typedChar, int keyCode) {
/* 1616 */     for (Component part : this.elements) {
/* 1617 */       part.keyTyped(typedChar, keyCode);
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isHovered(int mouseX, int mouseY) {
/* 1622 */     if (!Component.anyExpanded) {
/* 1623 */       int startX = (int)(this.x + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() - ((Float)ClickGUI.instance.rectWidth.getValue()).floatValue() / 2.0F);
/* 1624 */       int endX = (int)((this.x + this.width) + ((Float)ClickGUI.instance.rectX.getValue()).floatValue() + ((Float)ClickGUI.instance.rectWidth.getValue()).floatValue() / 2.0F);
/* 1625 */       int startY = (int)(this.y - ((Float)ClickGUI.instance.rectHeight.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.rectY.getValue()).floatValue());
/* 1626 */       int endY = (int)(this.y + this.height + ((Float)ClickGUI.instance.rectHeight.getValue()).floatValue() / 2.0F + ((Float)ClickGUI.instance.rectY.getValue()).floatValue());
/* 1627 */       return (mouseX >= Math.min(startX, endX) && mouseX <= Math.max(startX, endX) && mouseY >= Math.min(startY, endY) && mouseY <= Math.max(startY, endY));
/*      */     } 
/*      */     
/* 1630 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\gui\Panel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */