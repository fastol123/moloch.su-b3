/*    */ package me.thediamondsword5.moloch.utils.graphics;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.image.BufferedImage;
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
/*    */ public class ColorUtil
/*    */ {
/*    */   public static Color averageColor(BufferedImage bi, int width, int height, int pixelStep) {
/* 18 */     int[] color = new int[3]; int x;
/* 19 */     for (x = 0; x < width; x += pixelStep) {
/* 20 */       int y; for (y = 0; y < height; y += pixelStep) {
/* 21 */         Color pixel = new Color(bi.getRGB(x, y));
/* 22 */         color[0] = color[0] + pixel.getRed();
/* 23 */         color[1] = color[1] + pixel.getGreen();
/* 24 */         color[2] = color[2] + pixel.getBlue();
/*    */       } 
/*    */     } 
/* 27 */     int num = width * height;
/* 28 */     return new Color(color[0] / num, color[1] / num, color[2] / num);
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloc\\utils\graphics\ColorUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */