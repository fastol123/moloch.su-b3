/*     */ package net.spartanb312.base.utils;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import net.spartanb312.base.gui.renderers.ClickGUIRenderer;
/*     */ import net.spartanb312.base.gui.renderers.HUDEditorRenderer;
/*     */ import net.spartanb312.base.module.modules.client.HUDEditor;
/*     */ 
/*     */ public class ColorUtil
/*     */ {
/*     */   public static Color getColor(int hex) {
/*  11 */     return new Color(hex);
/*     */   }
/*     */   
/*     */   public static int getAlpha(int hex) {
/*  15 */     return hex >> 24 & 0xFF;
/*     */   }
/*     */   
/*     */   public static int getRed(int hex) {
/*  19 */     return hex >> 16 & 0xFF;
/*     */   }
/*     */   
/*     */   public static int getGreen(int hex) {
/*  23 */     return hex >> 8 & 0xFF;
/*     */   }
/*     */   
/*     */   public static int getBlue(int hex) {
/*  27 */     return hex & 0xFF;
/*     */   }
/*     */   
/*     */   public static int getHoovered(int color, boolean isHoovered) {
/*  31 */     return isHoovered ? ((color & 0x7F7F7F) << 1) : color;
/*     */   }
/*     */   
/*     */   public static int getBetterRainbow(float rainbowSpeed, float saturation, float brightness) {
/*  35 */     double rainbowState = Math.ceil((System.currentTimeMillis() + 100L) / 20.0D) * rainbowSpeed;
/*  36 */     rainbowState %= 360.0D;
/*  37 */     return Color.getHSBColor((float)(rainbowState / 360.0D), saturation, brightness).getRGB();
/*     */   }
/*     */   
/*     */   public static int rainbow(int delay, float speed, float size, float saturation, float brightness) {
/*  41 */     double rainbowState = Math.ceil(System.currentTimeMillis() * speed / 20.0D + delay / 20.0D) / size;
/*  42 */     rainbowState %= 360.0D;
/*  43 */     return Color.getHSBColor((float)(rainbowState / 360.0D), saturation, brightness).getRGB();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color colorShift(Color startColor, Color endColor, float progressCounter) {
/*  50 */     int red = (int)MathUtilFuckYou.linearInterp(startColor.getRed(), endColor.getRed(), progressCounter);
/*  51 */     int green = (int)MathUtilFuckYou.linearInterp(startColor.getGreen(), endColor.getGreen(), progressCounter);
/*  52 */     int blue = (int)MathUtilFuckYou.linearInterp(startColor.getBlue(), endColor.getBlue(), progressCounter);
/*     */     
/*  54 */     return new Color(red, green, blue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color colorHSBChange(Color colorIn, float value, ColorHSBMode mode) {
/*  61 */     value = MathUtilFuckYou.clamp(value, 0.0F, 1.0F);
/*  62 */     float[] hsbVals = new float[3];
/*  63 */     Color.RGBtoHSB(colorIn.getRed(), colorIn.getGreen(), colorIn.getBlue(), hsbVals);
/*     */     
/*  65 */     if (mode == ColorHSBMode.Saturation) {
/*  66 */       return Color.getHSBColor(hsbVals[0], value, hsbVals[2]);
/*     */     }
/*     */     
/*  69 */     return Color.getHSBColor(hsbVals[0], hsbVals[1], value);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Color rolledBrightness(Color color, float maxBrightness, float minBrightness, float speed, float offset, float size, boolean rightFlow, boolean useScrollCorrection) {
/*  74 */     float[] hsbVals = new float[3];
/*  75 */     Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsbVals);
/*     */     
/*  77 */     float scrollCorrection = HUDEditor.instance.isEnabled() ? HUDEditorRenderer.scrolledY : ClickGUIRenderer.scrolledY;
/*  78 */     double brightnessState = System.currentTimeMillis() * speed / 37.0D + ((offset + (useScrollCorrection ? scrollCorrection : 0.0F)) * (rightFlow ? 1.0F : -1.0F));
/*  79 */     brightnessState %= 300.0D * size;
/*  80 */     brightnessState = 150.0D * Math.sin((brightnessState - (75.0F * size)) * Math.PI / (150.0F * size)) + 150.0D;
/*  81 */     float brightness = maxBrightness + (float)brightnessState * (minBrightness - maxBrightness) / 300.0F;
/*     */     
/*  83 */     return Color.getHSBColor(hsbVals[0], hsbVals[1], brightness);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Color rolledColor(Color color1, Color color2, int offset, float speed, float size) {
/*  88 */     double colorState = Math.ceil((System.currentTimeMillis() * speed + offset) / 20.0D) / size;
/*  89 */     colorState %= 300.0D;
/*  90 */     colorState = (float)(150.0D * Math.sin((colorState - 75.0D) * Math.PI / 150.0D) + 150.0D);
/*  91 */     return colorShift(color1, color2, (float)colorState);
/*     */   }
/*     */   
/*     */   public static int rolledRainbowCircular(int angle, float speed, float saturation, float brightness) {
/*  95 */     double colorState = System.currentTimeMillis() * speed % 360.0D + angle;
/*  96 */     colorState %= 360.0D;
/*  97 */     return Color.getHSBColor((float)colorState / 360.0F, saturation, brightness).getRGB();
/*     */   }
/*     */   
/*     */   public enum ColorHSBMode {
/* 101 */     Saturation,
/* 102 */     Brightness;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\ColorUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */