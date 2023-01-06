/*    */ package me.thediamondsword5.moloch.hud.huds;
/*    */ 
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.hud.HUDModule;
/*    */ import net.spartanb312.base.module.Category;
/*    */ 
/*    */ @ModuleInfo(name = "HUDFont", category = Category.HUD, description = "HUD Font Settings")
/*    */ public class CustomHUDFont extends HUDModule {
/*    */   public static CustomHUDFont instance;
/* 12 */   public Setting<FontMode> font = setting("Font", FontMode.Comfortaa).des("Font");
/*    */   
/*    */   public CustomHUDFont() {
/* 15 */     instance = this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onHUDRender(ScaledResolution resolution) {}
/*    */ 
/*    */   
/*    */   public enum FontMode
/*    */   {
/* 24 */     Comfortaa, Arial, Objectivity, Minecraft;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 30 */     enable();
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\hud\huds\CustomHUDFont.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */