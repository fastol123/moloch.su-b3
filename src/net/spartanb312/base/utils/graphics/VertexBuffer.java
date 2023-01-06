/*    */ package net.spartanb312.base.utils.graphics;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.spartanb312.base.utils.ColorUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VertexBuffer
/*    */ {
/* 16 */   private static final Tessellator tessellator = Tessellator.func_178181_a();
/* 17 */   private static final BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*    */   
/*    */   public static void begin(int mode) {
/* 20 */     bufferbuilder.func_181668_a(mode, DefaultVertexFormats.field_181706_f);
/*    */   }
/*    */   
/*    */   public static void end() {
/* 24 */     tessellator.func_78381_a();
/*    */   }
/*    */   
/*    */   public static void put(double x, double y, int color) {
/* 28 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_181669_b(ColorUtil.getRed(color), ColorUtil.getGreen(color), ColorUtil.getBlue(color), ColorUtil.getAlpha(color)).func_181675_d();
/*    */   }
/*    */   
/*    */   public static void put(float x, float y, int color) {
/* 32 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_181669_b(ColorUtil.getRed(color), ColorUtil.getGreen(color), ColorUtil.getBlue(color), ColorUtil.getAlpha(color)).func_181675_d();
/*    */   }
/*    */   
/*    */   public static void put(int x, int y, int color) {
/* 36 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_181669_b(ColorUtil.getRed(color), ColorUtil.getGreen(color), ColorUtil.getBlue(color), ColorUtil.getAlpha(color)).func_181675_d();
/*    */   }
/*    */   
/*    */   public static void put(double x, double y, Color color) {
/* 40 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_181669_b(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).func_181675_d();
/*    */   }
/*    */   
/*    */   public static void put(float x, float y, Color color) {
/* 44 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_181669_b(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).func_181675_d();
/*    */   }
/*    */   
/*    */   public static void put(int x, int y, Color color) {
/* 48 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_181669_b(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).func_181675_d();
/*    */   }
/*    */   
/*    */   public static void put(double x, double y, float red, float green, float blue, float alpha) {
/* 52 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_181666_a(red, green, blue, alpha).func_181675_d();
/*    */   }
/*    */   
/*    */   public static void put(float x, float y, float red, float green, float blue, float alpha) {
/* 56 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_181666_a(red, green, blue, alpha).func_181675_d();
/*    */   }
/*    */   
/*    */   public static void put(int x, int y, float red, float green, float blue, float alpha) {
/* 60 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_181666_a(red, green, blue, alpha).func_181675_d();
/*    */   }
/*    */   
/*    */   public static void put(double x, double y, float red, float green, float blue) {
/* 64 */     put(x, y, red, green, blue, 1.0F);
/*    */   }
/*    */   
/*    */   public static void put(float x, float y, float red, float green, float blue) {
/* 68 */     put(x, y, red, green, blue, 1.0F);
/*    */   }
/*    */   
/*    */   public static void put(int x, int y, float red, float green, float blue) {
/* 72 */     put(x, y, red, green, blue, 1.0F);
/*    */   }
/*    */   
/*    */   public static void put(double x, double y, int red, int green, int blue, int alpha) {
/* 76 */     put(x, y, red / 255.0F, green / 255.0F, blue / 255.0F, alpha / 255.0F);
/*    */   }
/*    */   
/*    */   public static void put(float x, float y, int red, int green, int blue, int alpha) {
/* 80 */     put(x, y, red / 255.0F, green / 255.0F, blue / 255.0F, alpha / 255.0F);
/*    */   }
/*    */   
/*    */   public static void put(int x, int y, int red, int green, int blue, int alpha) {
/* 84 */     put(x, y, red / 255.0F, green / 255.0F, blue / 255.0F, alpha / 255.0F);
/*    */   }
/*    */   
/*    */   public static void put(double x, double y, int red, int green, int blue) {
/* 88 */     put(x, y, red / 255.0F, green / 255.0F, blue / 255.0F, 1.0F);
/*    */   }
/*    */   
/*    */   public static void put(float x, float y, int red, int green, int blue) {
/* 92 */     put(x, y, red / 255.0F, green / 255.0F, blue / 255.0F, 1.0F);
/*    */   }
/*    */   
/*    */   public static void put(int x, int y, int red, int green, int blue) {
/* 96 */     put(x, y, red / 255.0F, green / 255.0F, blue / 255.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\graphics\VertexBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */