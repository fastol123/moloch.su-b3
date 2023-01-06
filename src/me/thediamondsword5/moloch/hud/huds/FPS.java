/*    */ package me.thediamondsword5.moloch.hud.huds;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.thediamondsword5.moloch.core.common.Color;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.spartanb312.base.client.FontManager;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.engine.AsyncRenderer;
/*    */ import net.spartanb312.base.hud.HUDModule;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.utils.ChatUtil;
/*    */ import net.spartanb312.base.utils.graphics.font.CFontRenderer;
/*    */ 
/*    */ @ModuleInfo(name = "FPS", category = Category.HUD, description = "Displays Frames Per Second")
/*    */ public class FPS
/*    */   extends HUDModule {
/* 19 */   public CFontRenderer font = FontManager.fontRenderer;
/* 20 */   Setting<Boolean> fpsShadow = setting("Shadow", true).des("Draw Shadow Under FPS");
/* 21 */   Setting<Color> color = setting("Color", new Color((new Color(100, 61, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 61, 255, 255));
/*    */   
/*    */   public FPS() {
/* 24 */     this.asyncRenderer = new AsyncRenderer()
/*    */       {
/*    */         public void onUpdate(ScaledResolution resolution, int mouseX, int mouseY) {
/* 27 */           drawAsyncString("FPS " + Minecraft.func_175610_ah() + " " + ChatUtil.SECTIONSIGN + "f", FPS.this.x, FPS.this.y, ((Color)FPS.this.color.getValue()).getColor(), ((Boolean)FPS.this.fpsShadow.getValue()).booleanValue());
/* 28 */           FPS.this.width = FontManager.getWidthHUD("FPS " + Minecraft.func_175610_ah() + " " + ChatUtil.SECTIONSIGN + "f");
/* 29 */           FPS.this.height = FontManager.getHeight();
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public void onHUDRender(ScaledResolution resolution) {
/* 36 */     this.asyncRenderer.onRender();
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\hud\huds\FPS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */