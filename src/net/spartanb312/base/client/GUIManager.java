/*    */ package net.spartanb312.base.client;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.thediamondsword5.moloch.core.common.Color;
/*    */ import me.thediamondsword5.moloch.module.modules.client.Particles;
/*    */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*    */ import net.spartanb312.base.utils.ColorUtil;
/*    */ 
/*    */ public class GUIManager
/*    */ {
/*    */   public static ClickGUI ClickGUI;
/*    */   
/*    */   public static void init() {
/* 14 */     ClickGUI = (ClickGUI)ModuleManager.getModule((Class)ClickGUI.class);
/*    */   }
/*    */   
/*    */   public static boolean isParticle() {
/* 18 */     return ModuleManager.getModule((Class)Particles.class).isEnabled();
/*    */   }
/*    */   
/*    */   public static boolean isRainbow() {
/* 22 */     return ((Color)ClickGUI.globalColor.getValue()).getRainbow();
/*    */   }
/*    */   
/*    */   public static int getRed() {
/* 26 */     return ColorUtil.getRed(getColor3I());
/*    */   }
/*    */   
/*    */   public static int getGreen() {
/* 30 */     return ColorUtil.getGreen(getColor3I());
/*    */   }
/*    */   
/*    */   public static int getBlue() {
/* 34 */     return ColorUtil.getBlue(getColor3I());
/*    */   }
/*    */   
/*    */   public static int getAlpha() {
/* 38 */     return (new Color(((Color)ClickGUI.globalColor.getValue()).getColor())).getAlpha();
/*    */   }
/*    */   
/*    */   public static int getColor3I() {
/* 42 */     if (((Color)ClickGUI.globalColor.getValue()).getRainbow()) {
/* 43 */       return getRainbowColor();
/*    */     }
/* 45 */     return ((Color)ClickGUI.globalColor.getValue()).getColor();
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getColor4I() {
/* 50 */     if (((Color)ClickGUI.globalColor.getValue()).getRainbow()) {
/* 51 */       int colorHex = getRainbowColor();
/* 52 */       return (new Color(ColorUtil.getRed(colorHex), ColorUtil.getGreen(colorHex), ColorUtil.getBlue(colorHex), (new Color(((Color)ClickGUI.globalColor.getValue()).getColor())).getAlpha())).getRGB();
/*    */     } 
/* 54 */     return ((Color)ClickGUI.globalColor.getValue()).getColor();
/*    */   }
/*    */ 
/*    */   
/*    */   public static int getRainbowColor() {
/* 59 */     float[] hue = { (float)(System.currentTimeMillis() % 11520L) / 11520.0F * ((Color)ClickGUI.globalColor.getValue()).getRainbowSpeed() };
/* 60 */     return Color.HSBtoRGB(hue[0], ((Color)ClickGUI.globalColor.getValue()).getRainbowSaturation(), ((Color)ClickGUI.globalColor.getValue()).getRainbowBrightness());
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\client\GUIManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */