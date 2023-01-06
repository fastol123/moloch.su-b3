/*     */ package me.thediamondsword5.moloch.core.common;
/*     */ 
/*     */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*     */ import net.spartanb312.base.utils.ColorUtil;
/*     */ 
/*     */ 
/*     */ public class Color
/*     */ {
/*     */   private int color;
/*     */   private int red;
/*     */   private int green;
/*     */   private int blue;
/*     */   private int alpha;
/*     */   private boolean syncGlobal;
/*     */   private boolean rainbow;
/*     */   private float rainbowSpeed;
/*     */   private float rainbowSaturation;
/*     */   private float rainbowBrightness;
/*     */   
/*     */   public Color(int color, boolean syncGlobal, boolean rainbow, float rainbowSpeed, float rainbowSaturation, float rainbowBrightness, int red, int green, int blue, int alpha) {
/*  21 */     this.color = color;
/*  22 */     this.syncGlobal = syncGlobal;
/*  23 */     this.rainbow = rainbow;
/*  24 */     this.rainbowSpeed = rainbowSpeed;
/*  25 */     this.rainbowSaturation = rainbowSaturation;
/*  26 */     this.rainbowBrightness = rainbowBrightness;
/*  27 */     this.red = red;
/*  28 */     this.green = green;
/*  29 */     this.blue = blue;
/*  30 */     this.alpha = alpha;
/*     */   }
/*     */   
/*     */   private void updateColor() {
/*  34 */     if (this.syncGlobal) {
/*  35 */       setColor(((Color)ClickGUI.instance.globalColor.getValue()).getColor());
/*     */     
/*     */     }
/*  38 */     else if (this.rainbow) {
/*  39 */       java.awt.Color lgbtq = new java.awt.Color(ColorUtil.getBetterRainbow(this.rainbowSpeed, this.rainbowSaturation, this.rainbowBrightness));
/*  40 */       setColor((new java.awt.Color(lgbtq.getRed(), lgbtq.getGreen(), lgbtq.getBlue(), this.alpha)).getRGB());
/*     */     } else {
/*     */       
/*  43 */       setColor((new java.awt.Color(this.red, this.green, this.blue, this.alpha)).getRGB());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setColor(int color) {
/*  49 */     this.color = color;
/*     */   }
/*     */   
/*     */   public int getColor() {
/*  53 */     updateColor();
/*  54 */     return this.color;
/*     */   }
/*     */   
/*     */   public java.awt.Color getColorColor() {
/*  58 */     updateColor();
/*  59 */     return new java.awt.Color(this.color);
/*     */   }
/*     */   
/*     */   public void setSyncGlobal(boolean syncGlobal) {
/*  63 */     this.syncGlobal = syncGlobal;
/*     */   }
/*     */   
/*     */   public boolean getSyncGlobal() {
/*  67 */     return this.syncGlobal;
/*     */   }
/*     */   
/*     */   public void setRainbow(boolean rainbow) {
/*  71 */     this.rainbow = rainbow;
/*     */   }
/*     */   
/*     */   public boolean getRainbow() {
/*  75 */     return this.rainbow;
/*     */   }
/*     */   
/*     */   public void setRainbowSpeed(float rainbowSpeed) {
/*  79 */     this.rainbowSpeed = rainbowSpeed;
/*     */   }
/*     */   
/*     */   public float getRainbowSpeed() {
/*  83 */     return this.rainbowSpeed;
/*     */   }
/*     */   
/*     */   public void setRainbowSaturation(float rainbowSaturation) {
/*  87 */     this.rainbowSaturation = rainbowSaturation;
/*     */   }
/*     */   
/*     */   public float getRainbowSaturation() {
/*  91 */     return this.rainbowSaturation;
/*     */   }
/*     */   
/*     */   public void setRainbowBrightness(float rainbowBrightness) {
/*  95 */     this.rainbowBrightness = rainbowBrightness;
/*     */   }
/*     */   
/*     */   public float getRainbowBrightness() {
/*  99 */     return this.rainbowBrightness;
/*     */   }
/*     */   
/*     */   public void setRed(int red) {
/* 103 */     this.red = red;
/*     */   }
/*     */   
/*     */   public int getRed() {
/* 107 */     updateColor();
/* 108 */     return this.red;
/*     */   }
/*     */   
/*     */   public void setGreen(int green) {
/* 112 */     this.green = green;
/*     */   }
/*     */   
/*     */   public int getGreen() {
/* 116 */     updateColor();
/* 117 */     return this.green;
/*     */   }
/*     */   
/*     */   public void setBlue(int blue) {
/* 121 */     this.blue = blue;
/*     */   }
/*     */   
/*     */   public int getBlue() {
/* 125 */     updateColor();
/* 126 */     return this.blue;
/*     */   }
/*     */   
/*     */   public void setAlpha(int alpha) {
/* 130 */     this.alpha = alpha;
/*     */   }
/*     */   
/*     */   public int getAlpha() {
/* 134 */     return this.alpha;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\core\common\Color.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */