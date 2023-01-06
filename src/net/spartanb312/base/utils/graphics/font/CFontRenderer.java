/*     */ package net.spartanb312.base.utils.graphics.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CFontRenderer
/*     */   extends CFont
/*     */ {
/*     */   public static CFontRenderer instance;
/*  18 */   protected CFont.CharData[] boldChars = new CFont.CharData[256];
/*  19 */   protected CFont.CharData[] italicChars = new CFont.CharData[256];
/*  20 */   protected CFont.CharData[] boldItalicChars = new CFont.CharData[256];
/*     */   protected DynamicTexture texBold;
/*     */   protected DynamicTexture texItalic;
/*     */   protected DynamicTexture texItalicBold;
/*  24 */   private final int[] colorCode = new int[32];
/*     */   
/*     */   public CFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
/*  27 */     super(font, antiAlias, fractionalMetrics);
/*  28 */     setupMinecraftColorcodes();
/*  29 */     setupBoldItalicIDs();
/*  30 */     instance = this;
/*     */   }
/*     */   
/*     */   public CFontRenderer(CFont.CustomFont font, boolean antiAlias, boolean fractionalMetrics) {
/*  34 */     super(font, antiAlias, fractionalMetrics);
/*  35 */     setupMinecraftColorcodes();
/*  36 */     setupBoldItalicIDs();
/*     */   }
/*     */   
/*     */   public void drawStringWithShadow(String text, double x, double y, int color) {
/*  40 */     float shadowWidth = drawString(text, x + 1.0D, y + 1.0D, color, true);
/*  41 */     drawString(text, x, y, color, false);
/*     */   }
/*     */   
/*     */   public float drawString(String text, float x, float y, int color) {
/*  45 */     return drawString(text, x, y, color, false);
/*     */   }
/*     */   
/*     */   public void drawCenteredString(String text, float x, float y, int color) {
/*  49 */     drawString(text, x - (getStringWidth(text) / 2), y, color);
/*     */   }
/*     */   
/*     */   public void drawCenteredStringWithShadow(String text, float x, float y, int color) {
/*  53 */     float shadowWidth = drawString(text, (x - (getStringWidth(text) / 2)) + 1.0D, y + 1.0D, color, true);
/*  54 */     drawString(text, x - (getStringWidth(text) / 2), y, color);
/*     */   }
/*     */   
/*     */   public float drawString(String text, double x, double y, int color, boolean shadow) {
/*  58 */     x--;
/*  59 */     if (text == null) {
/*  60 */       return 0.0F;
/*     */     }
/*  62 */     if (color == 553648127) {
/*  63 */       color = 16777215;
/*     */     }
/*  65 */     if ((color & 0xFC000000) == 0) {
/*  66 */       color |= 0xFF000000;
/*     */     }
/*  68 */     if (shadow) {
/*  69 */       color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
/*     */     }
/*     */     
/*  72 */     CFont.CharData[] currentData = this.charData;
/*  73 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/*  74 */     boolean bold = false;
/*  75 */     boolean italic = false;
/*  76 */     boolean strikethrough = false;
/*  77 */     boolean underline = false;
/*  78 */     boolean render = true;
/*     */     
/*  80 */     x *= 2.0D;
/*  81 */     y = (y - 3.0D) * 2.0D;
/*     */     
/*  83 */     if (render) {
/*     */       
/*  85 */       GL11.glPushMatrix();
/*  86 */       GL11.glShadeModel(7425);
/*  87 */       GL11.glScalef(0.5F, 0.5F, 1.0F);
/*  88 */       GL11.glTranslatef((float)-x * 0.11111F, (float)-y * 0.11111F, 0.0F);
/*  89 */       GL11.glScalef(1.11111F, 1.11111F, 1.0F);
/*  90 */       GlStateManager.func_179147_l();
/*  91 */       GlStateManager.func_179141_d();
/*  92 */       GlStateManager.func_179112_b(770, 771);
/*  93 */       GL11.glEnable(2848);
/*     */       
/*  95 */       GlStateManager.func_179131_c((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/*  96 */       int size = text.length();
/*  97 */       GL11.glEnable(3553);
/*  98 */       GlStateManager.func_179144_i(this.tex.func_110552_b());
/*  99 */       GL11.glBindTexture(3553, this.tex.func_110552_b());
/*     */       
/* 101 */       for (int i = 0; i < size; i++) {
/* 102 */         char character = text.charAt(i);
/* 103 */         if (character == '§') {
/* 104 */           int colorIndex = 21;
/*     */           
/*     */           try {
/* 107 */             colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
/* 108 */           } catch (Exception e) {
/* 109 */             e.printStackTrace();
/*     */           } 
/*     */           
/* 112 */           if (colorIndex < 16) {
/* 113 */             bold = false;
/* 114 */             italic = false;
/* 115 */             underline = false;
/* 116 */             strikethrough = false;
/* 117 */             GlStateManager.func_179144_i(this.tex.func_110552_b());
/* 118 */             currentData = this.charData;
/* 119 */             if (colorIndex < 0) {
/* 120 */               colorIndex = 15;
/*     */             }
/* 122 */             if (shadow) {
/* 123 */               colorIndex += 16;
/*     */             }
/* 125 */             int colorcode = this.colorCode[colorIndex];
/* 126 */             GlStateManager.func_179131_c((colorcode >> 16 & 0xFF) / 255.0F, (colorcode >> 8 & 0xFF) / 255.0F, (colorcode & 0xFF) / 255.0F, alpha);
/* 127 */           } else if (colorIndex == 17) {
/* 128 */             bold = true;
/* 129 */             if (italic) {
/* 130 */               if (this.texItalicBold != null) {
/* 131 */                 GlStateManager.func_179144_i(this.texItalicBold.func_110552_b());
/* 132 */                 currentData = this.boldItalicChars;
/*     */               }
/*     */             
/* 135 */             } else if (this.texBold != null) {
/* 136 */               GlStateManager.func_179144_i(this.texBold.func_110552_b());
/* 137 */               currentData = this.boldChars;
/*     */             }
/*     */           
/* 140 */           } else if (colorIndex == 18) {
/* 141 */             strikethrough = true;
/* 142 */           } else if (colorIndex == 19) {
/* 143 */             underline = true;
/* 144 */           } else if (colorIndex == 20) {
/* 145 */             italic = true;
/* 146 */             if (bold) {
/* 147 */               if (this.texItalicBold != null) {
/* 148 */                 GlStateManager.func_179144_i(this.texItalicBold.func_110552_b());
/* 149 */                 currentData = this.boldItalicChars;
/*     */               }
/*     */             
/* 152 */             } else if (this.texBold != null) {
/* 153 */               GlStateManager.func_179144_i(this.texItalic.func_110552_b());
/* 154 */               currentData = this.italicChars;
/*     */             }
/*     */           
/* 157 */           } else if (colorIndex == 21) {
/* 158 */             bold = false;
/* 159 */             italic = false;
/* 160 */             underline = false;
/* 161 */             strikethrough = false;
/* 162 */             GlStateManager.func_179131_c((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/* 163 */             GlStateManager.func_179144_i(this.tex.func_110552_b());
/* 164 */             currentData = this.charData;
/*     */           } 
/* 166 */           i++;
/*     */ 
/*     */         
/*     */         }
/* 170 */         else if (character < currentData.length && character >= '\000') {
/*     */ 
/*     */ 
/*     */           
/* 174 */           GL11.glBegin(4);
/* 175 */           drawChar(currentData, character, (float)x, (float)y);
/* 176 */           GL11.glEnd();
/*     */           
/* 178 */           if (strikethrough) {
/* 179 */             drawLine(x, y + ((currentData[character]).height / 2), x + (currentData[character]).width - 8.0D, y + ((currentData[character]).height / 2), 1.0F);
/*     */           }
/* 181 */           if (underline) {
/* 182 */             drawLine(x, y + (currentData[character]).height - 2.0D, x + (currentData[character]).width - 8.0D, y + (currentData[character]).height - 2.0D, 1.0F);
/*     */           }
/* 184 */           x += (((currentData[character]).width - 8 + this.charOffset) / 2.0F);
/*     */         } 
/*     */       } 
/* 187 */       GlStateManager.func_179147_l();
/* 188 */       GL11.glScalef(0.90000093F, 0.90000093F, 1.0F);
/* 189 */       GL11.glTranslatef((float)x * 0.11111F, (float)y * 0.11111F, 0.0F);
/* 190 */       GL11.glScalef(2.0F, 2.0F, 1.0F);
/* 191 */       GL11.glHint(3154, 4354);
/* 192 */       GL11.glDisable(3553);
/*     */       
/* 194 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/* 197 */     return (float)x / 2.0F;
/*     */   }
/*     */   
/*     */   public String trimCFontStringToWidthBetter(String text, float width, boolean nearestWord) {
/* 201 */     StringBuilder stringbuilder = new StringBuilder();
/* 202 */     boolean nonSpaceEndingFlag = false;
/* 203 */     int lastSpaceIndex = 0;
/* 204 */     int size = text.length();
/* 205 */     for (int i = 0; i < size; i++) {
/* 206 */       char currentChar = text.charAt(i);
/* 207 */       stringbuilder.append(currentChar);
/*     */       
/* 209 */       if (nearestWord && String.valueOf(currentChar).equals(" ")) {
/* 210 */         lastSpaceIndex = i;
/*     */       }
/* 212 */       if (getStringWidth(stringbuilder.toString()) >= width) {
/* 213 */         if (nearestWord && !String.valueOf(currentChar).equals(" ")) {
/* 214 */           nonSpaceEndingFlag = true;
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 220 */     if (nearestWord && nonSpaceEndingFlag) {
/* 221 */       stringbuilder.delete(lastSpaceIndex, stringbuilder.toString().length());
/*     */     }
/* 223 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStringWidth(String text) {
/* 228 */     if (text == null) {
/* 229 */       return 0;
/*     */     }
/* 231 */     int width = 0;
/* 232 */     CFont.CharData[] currentData = this.charData;
/* 233 */     boolean bold = false;
/* 234 */     boolean italic = false;
/* 235 */     int size = text.length();
/* 236 */     for (int i = 0; i < size; i++) {
/* 237 */       char character = text.charAt(i);
/* 238 */       if (character == '§') {
/* 239 */         int colorIndex = "0123456789abcdefklmnor".indexOf(character);
/* 240 */         if (colorIndex < 16) {
/* 241 */           bold = false;
/* 242 */           italic = false;
/* 243 */         } else if (colorIndex == 17) {
/* 244 */           bold = true;
/* 245 */           currentData = italic ? this.boldItalicChars : this.boldChars;
/* 246 */         } else if (colorIndex == 20) {
/* 247 */           italic = true;
/* 248 */           currentData = bold ? this.boldItalicChars : this.italicChars;
/* 249 */         } else if (colorIndex == 21) {
/* 250 */           bold = false;
/* 251 */           italic = false;
/* 252 */           currentData = this.charData;
/*     */         } 
/* 254 */         i++;
/*     */       
/*     */       }
/* 257 */       else if (character < currentData.length && character >= '\000') {
/* 258 */         width += (currentData[character]).width - 8 + this.charOffset;
/*     */       } 
/* 260 */     }  return (int)(width * 1.11111F / 4.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 265 */     super.setFont(font);
/* 266 */     setupBoldItalicIDs();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAntiAlias(boolean antiAlias) {
/* 271 */     super.setAntiAlias(antiAlias);
/* 272 */     setupBoldItalicIDs();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFractionalMetrics(boolean fractionalMetrics) {
/* 277 */     super.setFractionalMetrics(fractionalMetrics);
/* 278 */     setupBoldItalicIDs();
/*     */   }
/*     */   
/*     */   private void setupBoldItalicIDs() {
/* 282 */     this.texBold = setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
/* 283 */     this.texItalic = setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
/* 284 */     this.texItalicBold = setupTexture(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
/*     */   }
/*     */   
/*     */   private void drawLine(double x, double y, double x1, double y1, float width) {
/* 288 */     GL11.glDisable(3553);
/* 289 */     GL11.glEnable(2848);
/* 290 */     GL11.glLineWidth(width);
/* 291 */     GL11.glBegin(1);
/* 292 */     GL11.glVertex2d(x, y);
/* 293 */     GL11.glVertex2d(x1, y1);
/* 294 */     GL11.glEnd();
/* 295 */     GL11.glEnable(3553);
/* 296 */     GL11.glDisable(2848);
/*     */   }
/*     */   
/*     */   public List<String> wrapWords(String text, double width) {
/* 300 */     ArrayList<String> finalWords = new ArrayList<>();
/* 301 */     if (getStringWidth(text) > width) {
/* 302 */       String[] words = text.split(" ");
/* 303 */       String currentWord = "";
/* 304 */       char lastColorCode = Character.MAX_VALUE;
/* 305 */       for (String word : words) {
/* 306 */         for (int i = 0; i < (word.toCharArray()).length; i++) {
/* 307 */           char c = word.toCharArray()[i];
/* 308 */           if (c == '§' && i < (word.toCharArray()).length - 1)
/* 309 */             lastColorCode = word.toCharArray()[i + 1]; 
/*     */         } 
/* 311 */         StringBuilder stringBuilder = new StringBuilder();
/* 312 */         if (getStringWidth(stringBuilder.append(currentWord).append(word).append(" ").toString()) < width) {
/* 313 */           currentWord = currentWord + word + " ";
/*     */         } else {
/*     */           
/* 316 */           finalWords.add(currentWord);
/* 317 */           currentWord = "§" + lastColorCode + word + " ";
/*     */         } 
/* 319 */       }  if (currentWord.length() > 0) {
/* 320 */         if (getStringWidth(currentWord) < width) {
/* 321 */           finalWords.add("§" + lastColorCode + currentWord + " ");
/* 322 */           currentWord = "";
/*     */         } else {
/* 324 */           for (String s : formatString(currentWord, width)) {
/* 325 */             finalWords.add(s);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } else {
/* 330 */       finalWords.add(text);
/*     */     } 
/* 332 */     return finalWords;
/*     */   }
/*     */   
/*     */   public List<String> formatString(String string, double width) {
/* 336 */     ArrayList<String> finalWords = new ArrayList<>();
/* 337 */     String currentWord = "";
/* 338 */     char lastColorCode = Character.MAX_VALUE;
/* 339 */     char[] chars = string.toCharArray();
/* 340 */     for (int i = 0; i < chars.length; i++) {
/* 341 */       char c = chars[i];
/* 342 */       if (c == '§' && i < chars.length - 1) {
/* 343 */         lastColorCode = chars[i + 1];
/*     */       }
/* 345 */       StringBuilder stringBuilder = new StringBuilder();
/* 346 */       if (getStringWidth(stringBuilder.append(currentWord).append(c).toString()) < width) {
/* 347 */         currentWord = currentWord + c;
/*     */       } else {
/*     */         
/* 350 */         finalWords.add(currentWord);
/* 351 */         currentWord = "§" + lastColorCode + c;
/*     */       } 
/* 353 */     }  if (currentWord.length() > 0) {
/* 354 */       finalWords.add(currentWord);
/*     */     }
/* 356 */     return finalWords;
/*     */   }
/*     */   
/*     */   private void setupMinecraftColorcodes() {
/* 360 */     for (int index = 0; index < 32; index++) {
/* 361 */       int noClue = (index >> 3 & 0x1) * 85;
/* 362 */       int red = (index >> 2 & 0x1) * 170 + noClue;
/* 363 */       int green = (index >> 1 & 0x1) * 170 + noClue;
/* 364 */       int blue = (index >> 0 & 0x1) * 170 + noClue;
/* 365 */       if (index == 6) {
/* 366 */         red += 85;
/*     */       }
/* 368 */       if (index >= 16) {
/* 369 */         red /= 4;
/* 370 */         green /= 4;
/* 371 */         blue /= 4;
/*     */       } 
/* 373 */       this.colorCode[index] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\graphics\font\CFontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */