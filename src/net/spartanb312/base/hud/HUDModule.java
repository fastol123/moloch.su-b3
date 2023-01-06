/*     */ package net.spartanb312.base.hud;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.engine.AsyncRenderer;
/*     */ import net.spartanb312.base.engine.RenderEngine;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*     */ 
/*     */ public abstract class HUDModule
/*     */   extends Module
/*     */ {
/*  14 */   public static final int backgroundColor = (new Color(1, 1, 1, 128)).getRGB();
/*     */   public int x;
/*     */   public int y;
/*     */   public int width;
/*  18 */   protected AsyncRenderer asyncRenderer = null; public int height;
/*     */   boolean dragging = false;
/*  20 */   private final Setting<Integer> hud_x = setting("HUD_X", 100, -2147483648, 2147483647).when(() -> false); int x2; int y2;
/*  21 */   private final Setting<Integer> hud_y = setting("HUD_Y", 100, -2147483648, 2147483647).when(() -> false);
/*  22 */   private final Setting<Integer> hud_width = setting("HUD_WIDTH", 100, -2147483648, 2147483647).when(() -> false);
/*  23 */   private final Setting<Integer> hud_height = setting("HUD_HEIGHT", 100, -2147483648, 2147483647).when(() -> false);
/*     */ 
/*     */   
/*     */   public void onSave() {
/*  27 */     this.hud_x.setValue(Integer.valueOf(this.x));
/*  28 */     this.hud_y.setValue(Integer.valueOf(this.y));
/*  29 */     this.hud_width.setValue(Integer.valueOf(this.width));
/*  30 */     this.hud_height.setValue(Integer.valueOf(this.height));
/*  31 */     super.onSave();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onLoad() {
/*  36 */     super.onLoad();
/*  37 */     this.x = ((Integer)this.hud_x.getValue()).intValue();
/*  38 */     this.y = ((Integer)this.hud_y.getValue()).intValue();
/*  39 */     this.width = ((Integer)this.hud_width.getValue()).intValue();
/*  40 */     this.height = ((Integer)this.hud_height.getValue()).intValue();
/*     */   }
/*     */   
/*     */   public void onHUDEnable() {
/*  44 */     this.moduleEnableFlag = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onHUDDisable() {
/*  49 */     this.moduleDisableFlag = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void onEnable() {
/*  55 */     if (this.asyncRenderer != null) RenderEngine.subscribe(this.asyncRenderer); 
/*  56 */     onHUDEnable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  61 */     if (this.asyncRenderer != null) RenderEngine.unsubscribe(this.asyncRenderer); 
/*  62 */     onHUDDisable();
/*     */   }
/*     */   
/*     */   public HUDModule() {
/*  66 */     this.x = 100;
/*  67 */     this.y = 100;
/*  68 */     this.width = 50;
/*  69 */     this.height = 50;
/*     */   }
/*     */   
/*     */   public HUDModule(int x, int y) {
/*  73 */     this.x = x;
/*  74 */     this.y = y;
/*  75 */     this.width = 50;
/*  76 */     this.height = 50;
/*     */   }
/*     */   
/*     */   public HUDModule(int x, int y, int width, int height) {
/*  80 */     this.x = x;
/*  81 */     this.y = y;
/*  82 */     this.width = width;
/*  83 */     this.height = height;
/*     */   }
/*     */   
/*     */   public final void renderInHUDEditor(int mouseX, int mouseY, float partialTicks, ScaledResolution resolution) {
/*  87 */     if (this.dragging) {
/*  88 */       this.x = this.x2 + mouseX;
/*  89 */       this.y = this.y2 + mouseY;
/*     */     } 
/*  91 */     RenderUtils2D.drawRect(this.x, this.y, (this.x + this.width), (this.y + this.height), backgroundColor);
/*  92 */     onHUDRender(resolution);
/*     */   }
/*     */   
/*     */   public final boolean onMouseClicked(int mouseX, int mouseY, int button) {
/*  96 */     if (button == 0 && isHovered(mouseX, mouseY)) {
/*  97 */       this.x2 = this.x - mouseX;
/*  98 */       this.y2 = this.y - mouseY;
/*  99 */       this.dragging = true;
/* 100 */       return true;
/*     */     } 
/* 102 */     return false;
/*     */   }
/*     */   
/*     */   public final void onMouseReleased(int mouseX, int mouseY, int state) {
/* 106 */     if (state == 0) this.dragging = false; 
/*     */   }
/*     */   
/*     */   public final boolean isHovered(int mouseX, int mouseY) {
/* 110 */     return (mouseX >= Math.min(this.x, this.x + this.width) && mouseX <= Math.max(this.x, this.x + this.width) && mouseY >= Math.min(this.y, this.y + this.height) && mouseY <= Math.max(this.y, this.y + this.height));
/*     */   }
/*     */   
/*     */   public abstract void onHUDRender(ScaledResolution paramScaledResolution);
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\hud\HUDModule.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */