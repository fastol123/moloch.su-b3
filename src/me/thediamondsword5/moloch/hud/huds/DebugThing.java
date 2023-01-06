/*    */ package me.thediamondsword5.moloch.hud.huds;
/*    */ 
/*    */ import me.thediamondsword5.moloch.core.common.Color;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.spartanb312.base.client.FontManager;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.engine.AsyncRenderer;
/*    */ import net.spartanb312.base.hud.HUDModule;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "DebugThing", category = Category.HUD, description = "For developer debug stuff")
/*    */ public class DebugThing
/*    */   extends HUDModule
/*    */ {
/*    */   public static float debugInt;
/*    */   
/*    */   public void onHUDRender(ScaledResolution resolution) {
/* 28 */     this.asyncRenderer.onRender();
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\hud\huds\DebugThing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */