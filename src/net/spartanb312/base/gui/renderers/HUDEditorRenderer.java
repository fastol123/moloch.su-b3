/*     */ package net.spartanb312.base.gui.renderers;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.spartanb312.base.client.ModuleManager;
/*     */ import net.spartanb312.base.gui.Component;
/*     */ import net.spartanb312.base.gui.Panel;
/*     */ import net.spartanb312.base.gui.components.ModuleButton;
/*     */ import net.spartanb312.base.hud.HUDModule;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*     */ import net.spartanb312.base.module.modules.client.HUDEditor;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HUDEditorRenderer
/*     */ {
/*  24 */   public ArrayList<Panel> panels = new ArrayList<>();
/*  25 */   public final ArrayList<HUDModule> hudModules = new ArrayList<>();
/*  26 */   public static HUDEditorRenderer instance = new HUDEditorRenderer();
/*  27 */   public static float scrolledY = 0.0F;
/*     */   
/*     */   public HUDEditorRenderer() {
/*  30 */     int startX = 5;
/*  31 */     for (Category category : Category.values()) {
/*  32 */       if (category != Category.HIDDEN && category.isHUD) {
/*  33 */         this.panels.add(new Panel(category, startX, 20, 120, 16));
/*  34 */         startX += 124;
/*     */       } 
/*  36 */     }  this.panels.forEach(it -> it.elements.forEach(()));
/*     */   }
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float translateDelta, float partialTicks) {
/*  40 */     scrollGUI();
/*     */     int i;
/*  42 */     for (i = this.panels.size() - 1; i >= 0; i--) {
/*  43 */       ((Panel)this.panels.get(i)).drawShadowsAndGlow(mouseX, mouseY);
/*     */     }
/*     */     
/*  46 */     for (i = this.panels.size() - 1; i >= 0; i--) {
/*  47 */       ((Panel)this.panels.get(i)).drawScreen(mouseX, mouseY, translateDelta, partialTicks);
/*     */     }
/*     */   }
/*     */   
/*     */   public void drawHUDElements(int mouseX, int mouseY, float partialTicks) {
/*  52 */     ScaledResolution resolution = new ScaledResolution(Minecraft.func_71410_x());
/*     */     
/*  54 */     for (int i = this.hudModules.size() - 1; i >= 0; i--) {
/*  55 */       HUDModule hudModule = this.hudModules.get(i);
/*  56 */       if (hudModule.x < 0) hudModule.x++; 
/*  57 */       if (hudModule.y < 0) hudModule.y++; 
/*  58 */       if (hudModule.x + hudModule.width > resolution.func_78326_a()) hudModule.x--; 
/*  59 */       if (hudModule.y + hudModule.height > resolution.func_78328_b()) hudModule.y--; 
/*  60 */       hudModule.renderInHUDEditor(mouseX, mouseY, partialTicks, resolution);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Panel getPanelByName(String name) {
/*  65 */     Panel getPane = null;
/*  66 */     if (this.panels != null)
/*  67 */       for (Panel panel : this.panels) {
/*  68 */         if (!panel.category.categoryName.equals(name)) {
/*     */           continue;
/*     */         }
/*  71 */         getPane = panel;
/*     */       }  
/*  73 */     return getPane;
/*     */   }
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/*  77 */     for (Panel panel : this.panels) {
/*  78 */       if (panel.mouseClicked(mouseX, mouseY, mouseButton))
/*  79 */         return;  if (!panel.extended)
/*  80 */         continue;  for (ModuleButton part : panel.elements) {
/*  81 */         if (part.mouseClicked(mouseX, mouseY, mouseButton))
/*  82 */           return;  if (!part.isExtended)
/*  83 */           continue;  for (Component component : part.settings) {
/*  84 */           if (component.isVisible() && 
/*  85 */             component.mouseClicked(mouseX, mouseY, mouseButton))
/*     */             return; 
/*     */         } 
/*     */       } 
/*  89 */     }  for (HUDModule hudModule : this.hudModules) {
/*  90 */       if (hudModule.onMouseClicked(mouseX, mouseY, mouseButton)) {
/*  91 */         Collections.swap(this.hudModules, 0, this.hudModules.indexOf(hudModule));
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void keyTyped(char typedChar, int keyCode) {
/*  98 */     if (keyCode == 1) {
/*  99 */       ModuleManager.getModule(HUDEditor.class).disable();
/*     */     }
/* 101 */     for (Panel panel : this.panels) {
/* 102 */       panel.keyTyped(typedChar, keyCode);
/*     */     }
/*     */   }
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int state) {
/* 107 */     for (Panel panel : this.panels) {
/* 108 */       panel.mouseReleased(mouseX, mouseY, state);
/*     */     }
/* 110 */     for (HUDModule hudModule : this.hudModules) {
/* 111 */       hudModule.onMouseReleased(mouseX, mouseY, state);
/*     */     }
/*     */   }
/*     */   
/*     */   public void scrollGUI() {
/* 116 */     int dWheel = Mouse.getDWheel();
/* 117 */     if (dWheel < 0 || (((Boolean)ClickGUI.instance.arrowScroll.getValue()).booleanValue() && Keyboard.isKeyDown(200))) {
/* 118 */       this.panels.forEach(component -> component.y -= ((Integer)ClickGUI.instance.scrollSpeed.getValue()).intValue());
/* 119 */       scrolledY += ((Integer)ClickGUI.instance.scrollSpeed.getValue()).intValue();
/*     */     } 
/*     */     
/* 122 */     if (dWheel > 0 || (((Boolean)ClickGUI.instance.arrowScroll.getValue()).booleanValue() && Keyboard.isKeyDown(208))) {
/* 123 */       this.panels.forEach(component -> component.y += ((Integer)ClickGUI.instance.scrollSpeed.getValue()).intValue());
/* 124 */       scrolledY -= ((Integer)ClickGUI.instance.scrollSpeed.getValue()).intValue();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\gui\renderers\HUDEditorRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */