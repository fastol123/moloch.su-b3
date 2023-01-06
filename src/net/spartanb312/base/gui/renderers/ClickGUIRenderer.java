/*    */ package net.spartanb312.base.gui.renderers;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.gui.Component;
/*    */ import net.spartanb312.base.gui.Panel;
/*    */ import net.spartanb312.base.gui.components.ModuleButton;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ import org.lwjgl.input.Mouse;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClickGUIRenderer
/*    */ {
/* 18 */   public ArrayList<Panel> panels = new ArrayList<>();
/* 19 */   public static ClickGUIRenderer instance = new ClickGUIRenderer();
/* 20 */   public static float scrolledY = 0.0F;
/*    */   
/*    */   public ClickGUIRenderer() {
/* 23 */     int startX = 5;
/* 24 */     for (Category category : Category.values()) {
/* 25 */       if (category != Category.HIDDEN && !category.isHUD) {
/* 26 */         this.panels.add(new Panel(category, startX, 20, 120, 16));
/* 27 */         startX += 133;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   public void drawScreen(int mouseX, int mouseY, float translateDelta, float partialTicks) {
/* 32 */     scrollGUI();
/*    */     int i;
/* 34 */     for (i = this.panels.size() - 1; i >= 0; i--) {
/* 35 */       ((Panel)this.panels.get(i)).drawShadowsAndGlow(mouseX, mouseY);
/*    */     }
/*    */     
/* 38 */     for (i = this.panels.size() - 1; i >= 0; i--) {
/* 39 */       ((Panel)this.panels.get(i)).drawScreen(mouseX, mouseY, translateDelta, partialTicks);
/*    */     }
/*    */   }
/*    */   
/*    */   public Panel getPanelByName(String name) {
/* 44 */     Panel getPane = null;
/* 45 */     if (this.panels != null)
/* 46 */       for (Panel panel : this.panels) {
/* 47 */         if (!panel.category.categoryName.equals(name)) {
/*    */           continue;
/*    */         }
/* 50 */         getPane = panel;
/*    */       }  
/* 52 */     return getPane;
/*    */   }
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 56 */     for (Panel panel : this.panels) {
/* 57 */       if (panel.mouseClicked(mouseX, mouseY, mouseButton))
/* 58 */         return;  if (!panel.extended)
/* 59 */         continue;  for (ModuleButton part : panel.elements) {
/* 60 */         if (part.mouseClicked(mouseX, mouseY, mouseButton))
/* 61 */           return;  if (!part.isExtended)
/* 62 */           continue;  for (Component component : part.settings) {
/* 63 */           if (component.isVisible() && 
/* 64 */             component.mouseClicked(mouseX, mouseY, mouseButton))
/*    */             return; 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   public void keyTyped(char typedChar, int keyCode) {
/* 71 */     if (keyCode == 1) {
/* 72 */       ModuleManager.getModule(ClickGUI.class).disable();
/*    */     }
/* 74 */     for (Panel panel : this.panels) {
/* 75 */       panel.keyTyped(typedChar, keyCode);
/*    */     }
/*    */   }
/*    */   
/*    */   public void mouseReleased(int mouseX, int mouseY, int state) {
/* 80 */     for (Panel panel : this.panels) {
/* 81 */       panel.mouseReleased(mouseX, mouseY, state);
/*    */     }
/*    */   }
/*    */   
/*    */   public void scrollGUI() {
/* 86 */     int dWheel = Mouse.getDWheel();
/* 87 */     if (dWheel < 0 || (((Boolean)ClickGUI.instance.arrowScroll.getValue()).booleanValue() && Keyboard.isKeyDown(200))) {
/* 88 */       this.panels.forEach(component -> component.y -= ((Integer)ClickGUI.instance.scrollSpeed.getValue()).intValue());
/* 89 */       scrolledY += ((Integer)ClickGUI.instance.scrollSpeed.getValue()).intValue();
/*    */     } 
/*    */     
/* 92 */     if (dWheel > 0 || (((Boolean)ClickGUI.instance.arrowScroll.getValue()).booleanValue() && Keyboard.isKeyDown(208))) {
/* 93 */       this.panels.forEach(component -> component.y += ((Integer)ClickGUI.instance.scrollSpeed.getValue()).intValue());
/* 94 */       scrolledY -= ((Integer)ClickGUI.instance.scrollSpeed.getValue()).intValue();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\gui\renderers\ClickGUIRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */