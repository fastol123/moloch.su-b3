/*    */ package net.spartanb312.base.utils.graphics.font;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RootFontRenderer
/*    */   implements FontRenderer
/*    */ {
/*    */   private final float fontsize;
/* 14 */   private final FontRenderer fontRenderer = (Minecraft.func_71410_x()).field_71466_p;
/*    */   
/*    */   public RootFontRenderer(float fontsize) {
/* 17 */     this.fontsize = fontsize;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFontHeight() {
/* 22 */     return (int)((Minecraft.func_71410_x()).field_71466_p.field_78288_b * this.fontsize);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getStringHeight(String text) {
/* 27 */     return getFontHeight();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getStringWidth(String text) {
/* 32 */     return (int)(this.fontRenderer.func_78256_a(text) * this.fontsize);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawString(int x, int y, String text) {
/* 37 */     drawString(x, y, 255, 255, 255, text);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawString(int x, int y, int r, int g, int b, String text) {
/* 42 */     drawString(x, y, 0xFF000000 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF, text);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawString(int x, int y, Color color, String text) {
/* 47 */     drawString(x, y, color.getRGB(), text);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawString(int x, int y, int colour, String text) {
/* 52 */     drawString(x, y, colour, text, true);
/*    */   }
/*    */   
/*    */   public void drawString(int x, int y, int colour, String text, boolean shadow) {
/* 56 */     prepare(x, y);
/* 57 */     (Minecraft.func_71410_x()).field_71466_p.func_175065_a(text, 0.0F, 0.0F, colour, shadow);
/* 58 */     pop(x, y);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawStringWithShadow(int x, int y, int r, int g, int b, String text) {
/* 63 */     drawString(x, y, 0xFF000000 | (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF, text, true);
/*    */   }
/*    */   
/*    */   private void prepare(int x, int y) {
/* 67 */     GL11.glEnable(3553);
/* 68 */     GL11.glEnable(3042);
/* 69 */     GL11.glTranslatef(x, y, 0.0F);
/* 70 */     GL11.glScalef(this.fontsize, this.fontsize, 1.0F);
/* 71 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */   
/*    */   private void pop(int x, int y) {
/* 75 */     GL11.glScalef(1.0F / this.fontsize, 1.0F / this.fontsize, 1.0F);
/* 76 */     GL11.glTranslatef(-x, -y, 0.0F);
/* 77 */     GL11.glDisable(3553);
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\graphics\font\RootFontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */