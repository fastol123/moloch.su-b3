/*    */ package me.thediamondsword5.moloch.module.modules.client;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.thediamondsword5.moloch.core.common.Color;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "GUIFont", category = Category.CLIENT, description = "GUI Font Settings")
/*    */ public class CustomFont extends Module {
/*    */   public static CustomFont instance;
/* 15 */   Setting<Page> page = setting("Page", Page.EverythingElse);
/*    */   
/* 17 */   public Setting<FontMode> font = setting("GeneralFont", FontMode.Comfortaa).des("General Font").whenAtMode(this.page, Page.EverythingElse);
/* 18 */   public Setting<Boolean> textShadow = setting("TextShadows", false).des("Draw Shadow Under GUI Text").whenAtMode(this.page, Page.EverythingElse);
/* 19 */   public Setting<TextPos> moduleTextPos = setting("ModuleTextPos", TextPos.Center).des("Module Text Pos").whenAtMode(this.page, Page.EverythingElse);
/* 20 */   public Setting<Integer> moduleTextOffsetX = setting("TextOffsetX", 0, 0, 50).des("Module Name X Offset").whenAtMode(this.page, Page.EverythingElse);
/* 21 */   public Setting<Integer> textOffset = setting("TextOffsetY", 0, -13, 13).des("Module Name Y Offset").whenAtMode(this.page, Page.EverythingElse);
/* 22 */   public Setting<Float> textScale = setting("TextScale", 0.8F, 0.1F, 1.5F).des("Size Of Text").whenAtMode(this.page, Page.EverythingElse);
/* 23 */   public Setting<Float> componentTextScale = setting("SettingsTextScale", 0.8F, 0.1F, 1.5F).des("Size Of Module Config Settings Text").whenAtMode(this.page, Page.EverythingElse);
/* 24 */   public Setting<Color> moduleTextColor = setting("ModuleTextColor", new Color((new Color(72, 72, 72, 206)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 72, 72, 72, 206)).des("Module Text Color").whenAtMode(this.page, Page.EverythingElse);
/*    */   
/* 26 */   public Setting<FontMode> categoryFont = setting("CategoryFont", FontMode.Objectivity).des("Category Text Font").whenAtMode(this.page, Page.CategoryText);
/* 27 */   public Setting<TextPos> categoryTextPos = setting("CategoryTextPos", TextPos.Center).des("Category Text Pos").whenAtMode(this.page, Page.CategoryText);
/* 28 */   public Setting<Float> categoryTextX = setting("CategoryTextX", 1.7F, -30.0F, 30.0F).des("Category Text X").whenAtMode(this.page, Page.CategoryText);
/* 29 */   public Setting<Float> categoryTextY = setting("CategoryTextY", 0.0F, -15.0F, 15.0F).des("Category Text Y").whenAtMode(this.page, Page.CategoryText);
/* 30 */   public Setting<Float> categoryTextScale = setting("CategoryTextScale", 1.3F, 0.1F, 2.0F).des("Size Of Category Text").whenAtMode(this.page, Page.CategoryText);
/* 31 */   public Setting<Boolean> categoryTextShadow = setting("CategoryTextShadow", false).des("Draw Shadow Under Category Text").whenAtMode(this.page, Page.CategoryText);
/* 32 */   public Setting<Boolean> categoryTextShadowGradient = setting("CTextShadowGradient", true).des("Draw Gradient Shadow Under Category Text").whenAtMode(this.page, Page.CategoryText);
/* 33 */   public Setting<Float> categoryTextShadowGradientX = setting("CTextShadowGradientX", 0.0F, -30.0F, 30.0F).des("Category Text Gradient Shadow X").whenTrue(this.categoryTextShadowGradient).whenAtMode(this.page, Page.CategoryText);
/* 34 */   public Setting<Float> categoryTextShadowGradientY = setting("CTextShadowGradientY", 0.0F, -15.0F, 15.0F).des("Category Text Gradient Shadow Y").whenTrue(this.categoryTextShadowGradient).whenAtMode(this.page, Page.CategoryText);
/* 35 */   public Setting<Float> categoryTextShadowGradientSize = setting("CTShadowGradientSize", 0.1F, 0.0F, 1.0F).des("Category Text Gradient Shadow Size").whenTrue(this.categoryTextShadowGradient).whenAtMode(this.page, Page.CategoryText);
/* 36 */   public Setting<Integer> categoryTextShadowGradientAlpha = setting("CTShadowGradientAlpha", 81, 0, 255).des("Category Text Gradient Shadow Alpha").whenTrue(this.categoryTextShadowGradient).whenAtMode(this.page, Page.CategoryText);
/* 37 */   public Setting<Color> categoryTextColor = setting("CategoryTextColor", new Color((new Color(255, 255, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 255)).des("Category Text Color").whenAtMode(this.page, Page.CategoryText);
/*    */ 
/*    */   
/*    */   public CustomFont() {
/* 41 */     instance = this;
/*    */   }
/*    */   
/*    */   enum Page {
/* 45 */     CategoryText, EverythingElse;
/*    */   }
/*    */   
/*    */   public enum FontMode {
/* 49 */     Comfortaa, Arial, Objectivity, Minecraft;
/*    */   }
/*    */   
/*    */   public enum TextPos {
/* 53 */     Left, Right, Center;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 58 */     enable();
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\client\CustomFont.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */