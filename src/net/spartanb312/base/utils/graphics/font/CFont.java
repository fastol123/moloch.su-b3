/*     */ package net.spartanb312.base.utils.graphics.font;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.InputStream;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class CFont {
/*  14 */   private final float imgSize = 512.0F;
/*  15 */   protected CharData[] charData = new CharData[256];
/*     */   protected Font font;
/*     */   protected boolean antiAlias;
/*     */   protected boolean fractionalMetrics;
/*  19 */   protected int fontHeight = -1;
/*  20 */   protected int charOffset = 0;
/*     */   protected DynamicTexture tex;
/*     */   
/*     */   public CFont(Font font, boolean antiAlias, boolean fractionalMetrics) {
/*  24 */     this.font = font;
/*  25 */     this.antiAlias = antiAlias;
/*  26 */     this.fractionalMetrics = fractionalMetrics;
/*  27 */     this.tex = setupTexture(font, antiAlias, fractionalMetrics, this.charData);
/*     */   }
/*     */ 
/*     */   
/*     */   public CFont(CustomFont font, boolean antiAlias, boolean fractionalMetrics) {
/*     */     try {
/*  33 */       Font inputFont = Font.createFont(0, Objects.<InputStream>requireNonNull(CFont.class.getResourceAsStream(font.getFile()))).deriveFont(font.getSize() * 1.8F).deriveFont(font.getType());
/*  34 */       this.font = inputFont;
/*  35 */       this.antiAlias = antiAlias;
/*  36 */       this.fractionalMetrics = fractionalMetrics;
/*  37 */       this.tex = setupTexture(inputFont, antiAlias, fractionalMetrics, this.charData);
/*  38 */     } catch (IOException|java.awt.FontFormatException iOException) {}
/*     */   }
/*     */   
/*     */   public static class CustomFont
/*     */   {
/*     */     float size;
/*     */     String file;
/*     */     int style;
/*     */     
/*     */     public CustomFont(String file, float size, int style) {
/*  48 */       this.file = file;
/*  49 */       this.size = size;
/*  50 */       this.style = style;
/*     */     }
/*     */     public float getSize() {
/*  53 */       return this.size;
/*     */     }
/*     */     
/*     */     public String getFile() {
/*  57 */       return this.file;
/*     */     }
/*     */     
/*     */     public int getType() {
/*  61 */       if (this.style > 3) {
/*  62 */         return 3;
/*     */       }
/*  64 */       return Math.max(this.style, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   protected DynamicTexture setupTexture(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
/*  69 */     BufferedImage img = generateFontImage(font, antiAlias, fractionalMetrics, chars);
/*     */     try {
/*  71 */       return new DynamicTexture(img);
/*  72 */     } catch (Exception e) {
/*  73 */       e.printStackTrace();
/*     */       
/*  75 */       return null;
/*     */     } 
/*     */   }
/*     */   protected BufferedImage generateFontImage(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
/*  79 */     getClass(); int imgSize = 512;
/*  80 */     BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, 2);
/*  81 */     Graphics2D g = (Graphics2D)bufferedImage.getGraphics();
/*  82 */     g.setFont(font);
/*  83 */     g.setColor(new Color(255, 255, 255, 0));
/*  84 */     g.fillRect(0, 0, imgSize, imgSize);
/*  85 */     g.setColor(Color.WHITE);
/*  86 */     g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
/*  87 */     g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
/*  88 */     g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
/*  89 */     FontMetrics fontMetrics = g.getFontMetrics();
/*  90 */     int charHeight = 0;
/*  91 */     int positionX = 0;
/*  92 */     int positionY = 2;
/*  93 */     for (int i = 0; i < chars.length; i++) {
/*  94 */       char ch = (char)i;
/*  95 */       CharData charData = new CharData();
/*  96 */       Rectangle2D dimensions = fontMetrics.getStringBounds(String.valueOf(ch), g);
/*  97 */       charData.width = (dimensions.getBounds()).width + 8;
/*  98 */       charData.height = (dimensions.getBounds()).height;
/*  99 */       if (positionX + charData.width >= imgSize) {
/* 100 */         positionX = 0;
/* 101 */         positionY += charHeight;
/* 102 */         charHeight = 0;
/*     */       } 
/* 104 */       if (charData.height > charHeight) {
/* 105 */         charHeight = charData.height;
/*     */       }
/* 107 */       charData.storedX = positionX;
/* 108 */       charData.storedY = positionY;
/* 109 */       if (charData.height > this.fontHeight) {
/* 110 */         this.fontHeight = charData.height;
/*     */       }
/* 112 */       chars[i] = charData;
/* 113 */       g.drawString(String.valueOf(ch), positionX + 2, positionY + fontMetrics.getAscent());
/* 114 */       positionX += charData.width;
/*     */     } 
/* 116 */     return bufferedImage;
/*     */   }
/*     */   
/*     */   public void drawChar(CharData[] chars, char c, float x, float y) throws ArrayIndexOutOfBoundsException {
/*     */     try {
/* 121 */       drawQuad(x, y, (chars[c]).width / 2.0F, (chars[c]).height / 2.0F, (chars[c]).storedX, (chars[c]).storedY, (chars[c]).width, (chars[c]).height);
/* 122 */     } catch (Exception e) {
/* 123 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawQuad(float x, float y, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
/* 128 */     float renderSRCX = srcX / 512.0F;
/* 129 */     float renderSRCY = srcY / 512.0F;
/* 130 */     float renderSRCWidth = srcWidth / 512.0F;
/* 131 */     float renderSRCHeight = (srcHeight - 2.0F) / 512.0F;
/* 132 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
/* 133 */     GL11.glVertex2d((x + width), y);
/* 134 */     GL11.glTexCoord2f(renderSRCX, renderSRCY);
/* 135 */     GL11.glVertex2d(x, y);
/* 136 */     GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
/* 137 */     GL11.glVertex2d(x, (y + height));
/* 138 */     GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
/* 139 */     GL11.glVertex2d(x, (y + height));
/* 140 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
/* 141 */     GL11.glVertex2d((x + width), (y + height));
/* 142 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
/* 143 */     GL11.glVertex2d((x + width), y);
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 147 */     return (this.fontHeight - 8) / 2 / 2;
/*     */   }
/*     */   
/*     */   public int getStringWidth(String text) {
/* 151 */     int width = 0;
/* 152 */     for (char c : text.toCharArray()) {
/* 153 */       if (c < this.charData.length && c >= '\000') width += (this.charData[c]).width - 8 + this.charOffset; 
/*     */     } 
/* 155 */     return width / 2;
/*     */   }
/*     */   
/*     */   public boolean isAntiAlias() {
/* 159 */     return this.antiAlias;
/*     */   }
/*     */   
/*     */   public void setAntiAlias(boolean antiAlias) {
/* 163 */     if (this.antiAlias != antiAlias) {
/* 164 */       this.antiAlias = antiAlias;
/* 165 */       this.tex = setupTexture(this.font, antiAlias, this.fractionalMetrics, this.charData);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isFractionalMetrics() {
/* 170 */     return this.fractionalMetrics;
/*     */   }
/*     */   
/*     */   public void setFractionalMetrics(boolean fractionalMetrics) {
/* 174 */     if (this.fractionalMetrics != fractionalMetrics) {
/* 175 */       this.fractionalMetrics = fractionalMetrics;
/* 176 */       this.tex = setupTexture(this.font, this.antiAlias, fractionalMetrics, this.charData);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Font getFont() {
/* 181 */     return this.font;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 186 */     this.font = font;
/* 187 */     this.tex = setupTexture(font, this.antiAlias, this.fractionalMetrics, this.charData);
/*     */   }
/*     */   
/*     */   protected static class CharData {
/*     */     public int width;
/*     */     public int height;
/*     */     public int storedX;
/*     */     public int storedY;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\graphics\font\CFont.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */