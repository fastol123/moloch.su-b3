/*     */ package net.spartanb312.base.utils.graphics;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import me.thediamondsword5.moloch.mixinotherstuff.AccessorInterfaceShaderGroup;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.client.shader.Shader;
/*     */ import net.minecraft.client.shader.ShaderGroup;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.spartanb312.base.utils.ItemUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderUtils2D
/*     */ {
/*     */   public static Framebuffer framebuffer;
/*     */   private static ShaderGroup shaderGroup;
/*     */   private static int prevScaleFactor;
/*     */   private static int prevScaleWidth;
/*     */   private static int prevScaleHeight;
/*     */   
/*     */   public static void prepareGl() {
/*  44 */     GlStateManager.func_179090_x();
/*  45 */     GlStateManager.func_179147_l();
/*  46 */     GlStateManager.func_179103_j(7425);
/*  47 */     GL11.glEnable(2848);
/*  48 */     GL11.glHint(3154, 4354);
/*  49 */     GlStateManager.func_179129_p();
/*     */   }
/*     */   
/*     */   public static void releaseGl() {
/*  53 */     GlStateManager.func_179098_w();
/*  54 */     GlStateManager.func_179103_j(7424);
/*  55 */     GL11.glDisable(2848);
/*  56 */     GlStateManager.func_179084_k();
/*  57 */     GlStateManager.func_179089_o();
/*     */   }
/*     */   
/*     */   public static void drawRectOutline(float x, float y, float endX, float endY, int color, boolean topToggle, boolean bottomToggle) {
/*  61 */     drawCustomRectOutline(x, y, endX, endY, 1.0F, color, color, color, color, topToggle, bottomToggle);
/*     */   }
/*     */   
/*     */   public static void drawRectOutline(float x, float y, float endX, float endY, float lineWidth, int color, boolean topToggle, boolean bottomToggle) {
/*  65 */     drawCustomRectOutline(x, y, endX, endY, lineWidth, color, color, color, color, topToggle, bottomToggle);
/*     */   }
/*     */   
/*     */   public static void drawCustomRectOutline(float x, float y, float endX, float endY, int rightTop, int leftTop, int leftDown, int rightDown, boolean topToggle, boolean bottomToggle) {
/*  69 */     drawCustomRectOutline(x, y, endX, endY, 1.0F, rightTop, leftTop, leftDown, rightDown, topToggle, bottomToggle);
/*     */   }
/*     */   
/*     */   public static void drawCustomRectOutline(float x, float y, float endX, float endY, float lineWidth, int rightTop, int leftTop, int leftDown, int rightDown, boolean topToggle, boolean bottomToggle) {
/*  73 */     GL11.glLineWidth(lineWidth);
/*  74 */     GL11.glEnable(2848);
/*     */     
/*  76 */     VertexBuffer.begin(1);
/*  77 */     if (!topToggle) {
/*  78 */       VertexBuffer.put(endX, y, rightTop);
/*  79 */       VertexBuffer.put(x, y, leftTop);
/*     */     } 
/*     */     
/*  82 */     VertexBuffer.put(x, y, leftTop);
/*  83 */     VertexBuffer.put(x, endY, leftDown);
/*     */     
/*  85 */     if (!bottomToggle) {
/*  86 */       VertexBuffer.put(x, endY, leftDown);
/*  87 */       VertexBuffer.put(endX, endY, rightDown);
/*     */     } 
/*     */     
/*  90 */     VertexBuffer.put(endX, endY, rightDown);
/*  91 */     VertexBuffer.put(endX, y, rightTop);
/*  92 */     VertexBuffer.end();
/*     */     
/*  94 */     GL11.glDisable(2848);
/*     */   }
/*     */   
/*     */   public static void drawRect(float x, float y, float endX, float endY, int color) {
/*  98 */     drawCustomRect(x, y, endX, endY, color, color, color, color);
/*     */   }
/*     */   
/*     */   public static void drawCustomRect(float x, float y, float endX, float endY, int rightTop, int leftTop, int leftDown, int rightDown) {
/* 102 */     VertexBuffer.begin(7);
/* 103 */     VertexBuffer.put(endX, y, rightTop);
/* 104 */     VertexBuffer.put(x, y, leftTop);
/* 105 */     VertexBuffer.put(x, endY, leftDown);
/* 106 */     VertexBuffer.put(endX, endY, rightDown);
/* 107 */     VertexBuffer.end();
/*     */   }
/*     */   
/*     */   public static void drawCustomLine(float startX, float startY, float endX, float endY, float lineWidth, int startColor, int endColor) {
/* 111 */     GL11.glLineWidth(lineWidth);
/*     */     
/* 113 */     VertexBuffer.begin(1);
/* 114 */     VertexBuffer.put(startX, startY, startColor);
/* 115 */     VertexBuffer.put(endX, endY, endColor);
/* 116 */     VertexBuffer.end();
/*     */     
/* 118 */     GL11.glLineWidth(1.0F);
/*     */   }
/*     */   
/*     */   public static void drawRoundedRect(float x, float y, float radius, float endX, float endY, boolean onlyTall, boolean arcTopRight, boolean arcTopLeft, boolean arcDownRight, boolean arcDownLeft, int color) {
/* 122 */     drawCustomRoundedRect(x, y, radius, endX, endY, arcTopRight, arcTopLeft, arcDownRight, arcDownLeft, false, false, onlyTall, color, color, color, color, color, color, color, color, color, color, color, color, color, color, color, color);
/*     */   }
/*     */   
/*     */   public static void drawRoundedRectFade(float x, float y, float radius, boolean fadeCenterRect, boolean onlyTall, float endX, float endY, int color) {
/* 126 */     GlStateManager.func_179118_c();
/* 127 */     drawCustomRoundedRect(x, y, radius, endX, endY, true, true, true, true, true, fadeCenterRect, onlyTall, color, color, color, color, color, color, color, color, color, color, color, color, color, color, color, color);
/* 128 */     GlStateManager.func_179141_d();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawCustomRoundedRect(float x, float y, float radiusFactor, float endX, float endY, boolean arcTopRight, boolean arcTopLeft, boolean arcDownRight, boolean arcDownLeft, boolean fadeMode, boolean fadeCenterRect, boolean onlyTall, int arcColorTopRight, int arcColorTopLeft, int arcColorDownRight, int arcColorDownLeft, int rightTopLeft, int leftTopLeft, int leftDownLeft, int rightDownLeft, int rightTopMid, int leftTopMid, int leftDownMid, int rightDownMid, int rightTopRight, int leftTopRight, int leftDownRight, int rightDownRight) {
/*     */     float radiusMax;
/* 135 */     if (endX - x > endY - y) {
/* 136 */       radiusMax = onlyTall ? ((endX - x) / 2.0F) : ((endY - y) / 2.0F);
/*     */     } else {
/* 138 */       radiusMax = (endX - x) / 2.0F;
/*     */     } 
/*     */     
/* 141 */     float radius = radiusFactor * radiusMax;
/*     */ 
/*     */     
/* 144 */     VertexBuffer.begin(7);
/* 145 */     VertexBuffer.put(x + radius, y + radius, rightTopLeft);
/* 146 */     VertexBuffer.put(x, y + radius, fadeMode ? (new Color(0, 0, 0, 0)).getRGB() : leftTopLeft);
/* 147 */     VertexBuffer.put(x, endY - radius, fadeMode ? (new Color(0, 0, 0, 0)).getRGB() : leftDownLeft);
/* 148 */     VertexBuffer.put(x + radius, endY - radius, rightDownLeft);
/*     */     
/* 150 */     if (fadeMode) {
/* 151 */       if (fadeCenterRect) {
/* 152 */         VertexBuffer.put(endX - radius, y + radius, arcColorTopRight);
/* 153 */         VertexBuffer.put(x + radius, y + radius, arcColorTopLeft);
/* 154 */         VertexBuffer.put(x + radius, endY - radius, arcColorDownLeft);
/* 155 */         VertexBuffer.put(endX - radius, endY - radius, arcColorDownRight);
/*     */       } 
/*     */ 
/*     */       
/* 159 */       VertexBuffer.put(endX - radius, y, (new Color(0, 0, 0, 0)).getRGB());
/* 160 */       VertexBuffer.put(x + radius, y, (new Color(0, 0, 0, 0)).getRGB());
/* 161 */       VertexBuffer.put(x + radius, y + radius, arcColorTopLeft);
/* 162 */       VertexBuffer.put(endX - radius, y + radius, arcColorTopRight);
/*     */       
/* 164 */       VertexBuffer.put(endX - radius, endY - radius, arcColorDownRight);
/* 165 */       VertexBuffer.put(x + radius, endY - radius, arcColorDownLeft);
/* 166 */       VertexBuffer.put(x + radius, endY, (new Color(0, 0, 0, 0)).getRGB());
/* 167 */       VertexBuffer.put(endX - radius, endY, (new Color(0, 0, 0, 0)).getRGB());
/*     */     } else {
/* 169 */       VertexBuffer.put(endX - radius, y, rightTopMid);
/* 170 */       VertexBuffer.put(x + radius, y, leftTopMid);
/* 171 */       VertexBuffer.put(x + radius, endY, leftDownMid);
/* 172 */       VertexBuffer.put(endX - radius, endY, rightDownMid);
/*     */     } 
/*     */     
/* 175 */     VertexBuffer.put(endX, y + radius, fadeMode ? (new Color(0, 0, 0, 0)).getRGB() : rightTopRight);
/* 176 */     VertexBuffer.put(endX - radius, y + radius, leftTopRight);
/* 177 */     VertexBuffer.put(endX - radius, endY - radius, leftDownRight);
/* 178 */     VertexBuffer.put(endX, endY - radius, fadeMode ? (new Color(0, 0, 0, 0)).getRGB() : rightDownRight);
/* 179 */     VertexBuffer.end();
/*     */     
/* 181 */     if (arcTopRight) {
/* 182 */       VertexBuffer.begin(6);
/* 183 */       VertexBuffer.put(endX - radius, y + radius, arcColorTopRight);
/* 184 */       for (int i = 0; i <= 20; i++) {
/* 185 */         VertexBuffer.put((endX - radius) + radius * Math.cos(i * 0.07853981633974483D), (y + radius) - radius * Math.sin(i * 0.07853981633974483D), fadeMode ? (new Color(0, 0, 0, 0)).getRGB() : arcColorTopRight);
/*     */       }
/* 187 */       VertexBuffer.end();
/*     */     } else {
/* 189 */       VertexBuffer.begin(7);
/* 190 */       VertexBuffer.put(endX, y, rightTopRight);
/* 191 */       VertexBuffer.put(endX - radius, y, rightTopRight);
/* 192 */       VertexBuffer.put(endX - radius, y + radius, rightTopRight);
/* 193 */       VertexBuffer.put(endX, y + radius, rightTopRight);
/* 194 */       VertexBuffer.end();
/*     */     } 
/*     */     
/* 197 */     if (arcTopLeft) {
/* 198 */       VertexBuffer.begin(6);
/* 199 */       VertexBuffer.put(x + radius, y + radius, arcColorTopLeft);
/* 200 */       for (int i = 0; i <= 20; i++) {
/* 201 */         VertexBuffer.put((x + radius) - radius * Math.cos(i * 0.07853981633974483D), (y + radius) - radius * Math.sin(i * 0.07853981633974483D), fadeMode ? (new Color(0, 0, 0, 0)).getRGB() : arcColorTopLeft);
/*     */       }
/* 203 */       VertexBuffer.end();
/*     */     } else {
/* 205 */       VertexBuffer.begin(7);
/* 206 */       VertexBuffer.put(x + radius, y, rightTopRight);
/* 207 */       VertexBuffer.put(x, y, rightTopRight);
/* 208 */       VertexBuffer.put(x, y + radius, rightTopRight);
/* 209 */       VertexBuffer.put(x + radius, y + radius, rightTopRight);
/* 210 */       VertexBuffer.end();
/*     */     } 
/*     */     
/* 213 */     if (arcDownLeft) {
/* 214 */       VertexBuffer.begin(6);
/* 215 */       VertexBuffer.put(x + radius, endY - radius, arcColorDownLeft);
/* 216 */       for (int i = 0; i <= 20; i++) {
/* 217 */         VertexBuffer.put((x + radius) - radius * Math.cos(i * 0.07853981633974483D), (endY - radius) + radius * Math.sin(i * 0.07853981633974483D), fadeMode ? (new Color(0, 0, 0, 0)).getRGB() : arcColorDownLeft);
/*     */       }
/* 219 */       VertexBuffer.end();
/*     */     } else {
/* 221 */       VertexBuffer.begin(7);
/* 222 */       VertexBuffer.put(x + radius, endY - radius, rightTopRight);
/* 223 */       VertexBuffer.put(x, endY - radius, rightTopRight);
/* 224 */       VertexBuffer.put(x, endY, rightTopRight);
/* 225 */       VertexBuffer.put(x + radius, endY, rightTopRight);
/* 226 */       VertexBuffer.end();
/*     */     } 
/*     */     
/* 229 */     if (arcDownRight) {
/* 230 */       VertexBuffer.begin(6);
/* 231 */       VertexBuffer.put(endX - radius, endY - radius, arcColorDownRight);
/* 232 */       for (int i = 0; i <= 20; i++) {
/* 233 */         VertexBuffer.put((endX - radius) + radius * Math.cos(i * 0.07853981633974483D), (endY - radius) + radius * Math.sin(i * 0.07853981633974483D), fadeMode ? (new Color(0, 0, 0, 0)).getRGB() : arcColorDownRight);
/*     */       }
/* 235 */       VertexBuffer.end();
/*     */     } else {
/* 237 */       VertexBuffer.begin(7);
/* 238 */       VertexBuffer.put(endX, endY - radius, rightTopRight);
/* 239 */       VertexBuffer.put(endX - radius, endY - radius, rightTopRight);
/* 240 */       VertexBuffer.put(endX - radius, endY, rightTopRight);
/* 241 */       VertexBuffer.put(endX, endY, rightTopRight);
/* 242 */       VertexBuffer.end();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void drawBetterRoundRectFade(float x, float y, float endX, float endY, float sizeFactor, float sizeMax, boolean onlyTall, boolean fadeCenterRect, boolean bottomToggle, int color) {
/* 247 */     GlStateManager.func_179118_c();
/* 248 */     float size = sizeFactor * sizeMax;
/*     */     
/* 250 */     VertexBuffer.begin(7);
/* 251 */     VertexBuffer.put(x, y, color);
/* 252 */     VertexBuffer.put(x - size, y, (new Color(0, 0, 0, 0)).getRGB());
/* 253 */     VertexBuffer.put(x - size, endY, (new Color(0, 0, 0, 0)).getRGB());
/* 254 */     VertexBuffer.put(x, endY, color);
/*     */     
/* 256 */     if (fadeCenterRect) {
/* 257 */       VertexBuffer.put(endX, y, color);
/* 258 */       VertexBuffer.put(x, y, color);
/* 259 */       VertexBuffer.put(x, endY, color);
/* 260 */       VertexBuffer.put(endX, endY, color);
/*     */     } 
/*     */ 
/*     */     
/* 264 */     VertexBuffer.put(endX, y - size, (new Color(0, 0, 0, 0)).getRGB());
/* 265 */     VertexBuffer.put(x, y - size, (new Color(0, 0, 0, 0)).getRGB());
/* 266 */     VertexBuffer.put(x, y, color);
/* 267 */     VertexBuffer.put(endX, y, color);
/*     */     
/* 269 */     if (!bottomToggle) {
/* 270 */       VertexBuffer.put(endX, endY, color);
/* 271 */       VertexBuffer.put(x, endY, color);
/* 272 */       VertexBuffer.put(x, endY + size, (new Color(0, 0, 0, 0)).getRGB());
/* 273 */       VertexBuffer.put(endX, endY + size, (new Color(0, 0, 0, 0)).getRGB());
/*     */     } 
/*     */     
/* 276 */     VertexBuffer.put(endX + size, y, (new Color(0, 0, 0, 0)).getRGB());
/* 277 */     VertexBuffer.put(endX, y, color);
/* 278 */     VertexBuffer.put(endX, endY, color);
/* 279 */     VertexBuffer.put(endX + size, endY, (new Color(0, 0, 0, 0)).getRGB());
/* 280 */     VertexBuffer.end();
/*     */ 
/*     */     
/* 283 */     VertexBuffer.begin(6);
/* 284 */     VertexBuffer.put(endX, y, color); int i;
/* 285 */     for (i = 0; i <= 20; i++) {
/* 286 */       VertexBuffer.put(endX + size * Math.cos(i * 0.07853981633974483D), y - size * Math.sin(i * 0.07853981633974483D), (new Color(0, 0, 0, 0)).getRGB());
/*     */     }
/* 288 */     VertexBuffer.end();
/*     */ 
/*     */     
/* 291 */     VertexBuffer.begin(6);
/* 292 */     VertexBuffer.put(x, y, color);
/* 293 */     for (i = 0; i <= 20; i++) {
/* 294 */       VertexBuffer.put(x - size * Math.cos(i * 0.07853981633974483D), y - size * Math.sin(i * 0.07853981633974483D), (new Color(0, 0, 0, 0)).getRGB());
/*     */     }
/* 296 */     VertexBuffer.end();
/*     */ 
/*     */     
/* 299 */     VertexBuffer.begin(6);
/* 300 */     VertexBuffer.put(x, endY, color);
/* 301 */     for (i = 0; i <= 20; i++) {
/* 302 */       VertexBuffer.put(x - size * Math.cos(i * 0.07853981633974483D), endY + size * Math.sin(i * 0.07853981633974483D), (new Color(0, 0, 0, 0)).getRGB());
/*     */     }
/* 304 */     VertexBuffer.end();
/*     */ 
/*     */     
/* 307 */     VertexBuffer.begin(6);
/* 308 */     VertexBuffer.put(endX, endY, color);
/* 309 */     for (i = 0; i <= 20; i++) {
/* 310 */       VertexBuffer.put(endX + size * Math.cos(i * 0.07853981633974483D), endY + size * Math.sin(i * 0.07853981633974483D), (new Color(0, 0, 0, 0)).getRGB());
/*     */     }
/* 312 */     VertexBuffer.end();
/* 313 */     GlStateManager.func_179141_d();
/*     */   }
/*     */   
/*     */   public static void drawCustomRoundedRectModuleEnableMode(float x, float y, float endX, float endY, float radius, boolean right, int color) {
/* 317 */     drawCustomGradientRoundedRectModuleEnableMode(x, y, endX, endY, radius, right, color, color);
/*     */   }
/*     */   
/*     */   public static void drawCustomGradientRoundedRectModuleEnableMode(float x, float y, float endX, float endY, float radius, boolean right, int innerColor, int outerColor) {
/* 321 */     float radiuss = radius * (endX - x);
/*     */ 
/*     */     
/* 324 */     VertexBuffer.begin(7);
/* 325 */     VertexBuffer.put(endX, y + radiuss, right ? innerColor : outerColor);
/* 326 */     VertexBuffer.put(x, y + radiuss, right ? outerColor : innerColor);
/* 327 */     VertexBuffer.put(x, endY - radiuss, right ? outerColor : innerColor);
/* 328 */     VertexBuffer.put(endX, endY - radiuss, right ? innerColor : outerColor);
/*     */     
/* 330 */     if (right) {
/*     */       
/* 332 */       VertexBuffer.begin(7);
/* 333 */       VertexBuffer.put(endX, y, outerColor);
/* 334 */       VertexBuffer.put(x + radiuss, y, outerColor);
/* 335 */       VertexBuffer.put(x + radiuss, y + radiuss, innerColor);
/* 336 */       VertexBuffer.put(endX, y + radiuss, innerColor);
/*     */ 
/*     */       
/* 339 */       VertexBuffer.put(endX, endY - radiuss, innerColor);
/* 340 */       VertexBuffer.put(x + radiuss, endY - radiuss, innerColor);
/* 341 */       VertexBuffer.put(x + radiuss, endY, outerColor);
/* 342 */       VertexBuffer.put(endX, endY, outerColor);
/* 343 */       VertexBuffer.end();
/*     */ 
/*     */       
/* 346 */       VertexBuffer.begin(6);
/* 347 */       VertexBuffer.put(x + radiuss, y + radiuss, innerColor); int i;
/* 348 */       for (i = 0; i <= 20; i++) {
/* 349 */         VertexBuffer.put((x + radiuss) - radiuss * Math.cos(i * 0.07853981633974483D), (y + radiuss) - radiuss * Math.sin(i * 0.07853981633974483D), outerColor);
/*     */       }
/* 351 */       VertexBuffer.end();
/*     */ 
/*     */       
/* 354 */       VertexBuffer.begin(6);
/* 355 */       VertexBuffer.put(x + radiuss, endY - radiuss, innerColor);
/* 356 */       for (i = 0; i <= 20; i++) {
/* 357 */         VertexBuffer.put((x + radiuss) - radiuss * Math.cos(i * 0.07853981633974483D), (endY - radiuss) + radiuss * Math.sin(i * 0.07853981633974483D), outerColor);
/*     */       }
/* 359 */       VertexBuffer.end();
/*     */     }
/*     */     else {
/*     */       
/* 363 */       VertexBuffer.begin(7);
/* 364 */       VertexBuffer.put(endX - radiuss, y, outerColor);
/* 365 */       VertexBuffer.put(x, y, outerColor);
/* 366 */       VertexBuffer.put(x, y + radiuss, innerColor);
/* 367 */       VertexBuffer.put(endX - radiuss, y + radiuss, innerColor);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 372 */       VertexBuffer.put(endX - radiuss, endY - radiuss, innerColor);
/* 373 */       VertexBuffer.put(x, endY - radiuss, innerColor);
/* 374 */       VertexBuffer.put(x, endY, outerColor);
/* 375 */       VertexBuffer.put(endX - radiuss, endY, outerColor);
/* 376 */       VertexBuffer.end();
/*     */ 
/*     */       
/* 379 */       VertexBuffer.begin(6);
/* 380 */       VertexBuffer.put(endX - radiuss, y + radiuss, innerColor); int i;
/* 381 */       for (i = 0; i <= 20; i++) {
/* 382 */         VertexBuffer.put((endX - radiuss) + radiuss * Math.cos(i * 0.07853981633974483D), (y + radiuss) - radiuss * Math.sin(i * 0.07853981633974483D), outerColor);
/*     */       }
/* 384 */       VertexBuffer.end();
/*     */ 
/*     */       
/* 387 */       VertexBuffer.begin(6);
/* 388 */       VertexBuffer.put(endX - radiuss, endY - radiuss, innerColor);
/* 389 */       for (i = 0; i <= 20; i++) {
/* 390 */         VertexBuffer.put((endX - radiuss) + radiuss * Math.cos(i * 0.07853981633974483D), (endY - radiuss) + radiuss * Math.sin(i * 0.07853981633974483D), outerColor);
/*     */       }
/* 392 */       VertexBuffer.end();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawCustomCategoryRoundedRect(float x, float y, float endX, float endY, float radiusFactor, boolean arcTopRight, boolean arcTopLeft, boolean arcDownRight, boolean arcDownLeft, boolean fadeRight, boolean fadeLeft, float fadeSize, int color) {
/*     */     float radiusMax;
/* 400 */     if (endX - x > endY - y) {
/* 401 */       radiusMax = (endY - y) / 2.0F;
/*     */     } else {
/* 403 */       radiusMax = (endX - x) / 2.0F;
/*     */     } 
/*     */     
/* 406 */     float radius = radiusFactor * radiusMax;
/*     */ 
/*     */     
/* 409 */     VertexBuffer.begin(7);
/* 410 */     VertexBuffer.put(endX - radius, y, color);
/* 411 */     VertexBuffer.put(x + radius, y, color);
/* 412 */     VertexBuffer.put(x + radius, endY, color);
/* 413 */     VertexBuffer.put(endX - radius, endY, color);
/* 414 */     VertexBuffer.end();
/*     */ 
/*     */     
/* 417 */     if (fadeLeft) {
/* 418 */       GlStateManager.func_179118_c();
/* 419 */       VertexBuffer.begin(7);
/* 420 */       VertexBuffer.put(x + radius, y + radius, color);
/* 421 */       VertexBuffer.put(x - fadeSize, y + radius, (new Color(0, 0, 0, 0)).getRGB());
/* 422 */       VertexBuffer.put(x - fadeSize, endY - radius, (new Color(0, 0, 0, 0)).getRGB());
/* 423 */       VertexBuffer.put(x + radius, endY - radius, color);
/* 424 */       VertexBuffer.end();
/* 425 */       GlStateManager.func_179141_d();
/*     */     } else {
/* 427 */       VertexBuffer.begin(7);
/* 428 */       VertexBuffer.put(x + radius, y + radius, color);
/* 429 */       VertexBuffer.put(x, y + radius, color);
/* 430 */       VertexBuffer.put(x, endY - radius, color);
/* 431 */       VertexBuffer.put(x + radius, endY - radius, color);
/* 432 */       VertexBuffer.end();
/*     */     } 
/*     */ 
/*     */     
/* 436 */     if (fadeRight) {
/* 437 */       GlStateManager.func_179118_c();
/* 438 */       VertexBuffer.begin(7);
/* 439 */       VertexBuffer.put(endX + fadeSize, y + radius, (new Color(0, 0, 0, 0)).getRGB());
/* 440 */       VertexBuffer.put(endX - radius, y + radius, color);
/* 441 */       VertexBuffer.put(endX - radius, endY - radius, color);
/* 442 */       VertexBuffer.put(endX + fadeSize, endY - radius, (new Color(0, 0, 0, 0)).getRGB());
/* 443 */       VertexBuffer.end();
/* 444 */       GlStateManager.func_179141_d();
/*     */     } else {
/* 446 */       VertexBuffer.begin(7);
/* 447 */       VertexBuffer.put(endX, y + radius, color);
/* 448 */       VertexBuffer.put(endX - radius, y + radius, color);
/* 449 */       VertexBuffer.put(endX - radius, endY - radius, color);
/* 450 */       VertexBuffer.put(endX, endY - radius, color);
/* 451 */       VertexBuffer.end();
/*     */     } 
/*     */ 
/*     */     
/* 455 */     if (arcTopRight) {
/* 456 */       VertexBuffer.begin(6);
/* 457 */       VertexBuffer.put(endX - radius, y + radius, color);
/* 458 */       for (int i = 0; i <= 20; i++) {
/* 459 */         VertexBuffer.put((endX - radius) + radius * Math.cos(i * 0.07853981633974483D), (y + radius) - radius * Math.sin(i * 0.07853981633974483D), color);
/*     */       }
/* 461 */       VertexBuffer.end();
/*     */     }
/* 463 */     else if (fadeRight) {
/* 464 */       GlStateManager.func_179118_c();
/* 465 */       VertexBuffer.begin(7);
/* 466 */       VertexBuffer.put(endX + fadeSize, y, (new Color(0, 0, 0, 0)).getRGB());
/* 467 */       VertexBuffer.put(endX - radius, y, color);
/* 468 */       VertexBuffer.put(endX - radius, y + radius, color);
/* 469 */       VertexBuffer.put(endX + fadeSize, y + radius, (new Color(0, 0, 0, 0)).getRGB());
/* 470 */       VertexBuffer.end();
/* 471 */       GlStateManager.func_179141_d();
/*     */     } else {
/*     */       
/* 474 */       VertexBuffer.begin(7);
/* 475 */       VertexBuffer.put(endX, y, color);
/* 476 */       VertexBuffer.put(endX - radius, y, color);
/* 477 */       VertexBuffer.put(endX - radius, y + radius, color);
/* 478 */       VertexBuffer.put(endX, y + radius, color);
/* 479 */       VertexBuffer.end();
/*     */     } 
/*     */ 
/*     */     
/* 483 */     if (arcTopLeft) {
/* 484 */       VertexBuffer.begin(6);
/* 485 */       VertexBuffer.put(x + radius, y + radius, color);
/* 486 */       for (int i = 0; i <= 20; i++) {
/* 487 */         VertexBuffer.put((x + radius) - radius * Math.cos(i * 0.07853981633974483D), (y + radius) - radius * Math.sin(i * 0.07853981633974483D), color);
/*     */       }
/* 489 */       VertexBuffer.end();
/*     */     }
/* 491 */     else if (fadeLeft) {
/* 492 */       GlStateManager.func_179118_c();
/* 493 */       VertexBuffer.begin(7);
/* 494 */       VertexBuffer.put(x + radius, y, color);
/* 495 */       VertexBuffer.put(x - fadeSize, y, (new Color(0, 0, 0, 0)).getRGB());
/* 496 */       VertexBuffer.put(x - fadeSize, y + radius, (new Color(0, 0, 0, 0)).getRGB());
/* 497 */       VertexBuffer.put(x + radius, y + radius, color);
/* 498 */       VertexBuffer.end();
/* 499 */       GlStateManager.func_179141_d();
/*     */     } else {
/*     */       
/* 502 */       VertexBuffer.begin(7);
/* 503 */       VertexBuffer.put(x + radius, y, color);
/* 504 */       VertexBuffer.put(x, y, color);
/* 505 */       VertexBuffer.put(x, y + radius, color);
/* 506 */       VertexBuffer.put(x + radius, y + radius, color);
/* 507 */       VertexBuffer.end();
/*     */     } 
/*     */ 
/*     */     
/* 511 */     if (arcDownLeft) {
/* 512 */       VertexBuffer.begin(6);
/* 513 */       VertexBuffer.put(x + radius, endY - radius, color);
/* 514 */       for (int i = 0; i <= 20; i++) {
/* 515 */         VertexBuffer.put((x + radius) - radius * Math.cos(i * 0.07853981633974483D), (endY - radius) + radius * Math.sin(i * 0.07853981633974483D), color);
/*     */       }
/* 517 */       VertexBuffer.end();
/*     */     }
/* 519 */     else if (fadeLeft) {
/* 520 */       GlStateManager.func_179118_c();
/* 521 */       VertexBuffer.begin(7);
/* 522 */       VertexBuffer.put(x + radius, endY - radius, color);
/* 523 */       VertexBuffer.put(x - fadeSize, endY - radius, (new Color(0, 0, 0, 0)).getRGB());
/* 524 */       VertexBuffer.put(x - fadeSize, endY, (new Color(0, 0, 0, 0)).getRGB());
/* 525 */       VertexBuffer.put(x + radius, endY, color);
/* 526 */       VertexBuffer.end();
/* 527 */       GlStateManager.func_179141_d();
/*     */     } else {
/*     */       
/* 530 */       VertexBuffer.begin(7);
/* 531 */       VertexBuffer.put(x + radius, endY - radius, color);
/* 532 */       VertexBuffer.put(x, endY - radius, color);
/* 533 */       VertexBuffer.put(x, endY, color);
/* 534 */       VertexBuffer.put(x + radius, endY, color);
/* 535 */       VertexBuffer.end();
/*     */     } 
/*     */ 
/*     */     
/* 539 */     if (arcDownRight) {
/* 540 */       VertexBuffer.begin(6);
/* 541 */       VertexBuffer.put(endX - radius, endY - radius, color);
/* 542 */       for (int i = 0; i <= 20; i++) {
/* 543 */         VertexBuffer.put((endX - radius) + radius * Math.cos(i * 0.07853981633974483D), (endY - radius) + radius * Math.sin(i * 0.07853981633974483D), color);
/*     */       }
/* 545 */       VertexBuffer.end();
/*     */     }
/* 547 */     else if (fadeRight) {
/* 548 */       GlStateManager.func_179118_c();
/* 549 */       VertexBuffer.begin(7);
/* 550 */       VertexBuffer.put(endX + fadeSize, endY - radius, (new Color(0, 0, 0, 0)).getRGB());
/* 551 */       VertexBuffer.put(endX - radius, endY - radius, color);
/* 552 */       VertexBuffer.put(endX - radius, endY, color);
/* 553 */       VertexBuffer.put(endX + fadeSize, endY, (new Color(0, 0, 0, 0)).getRGB());
/* 554 */       VertexBuffer.end();
/* 555 */       GlStateManager.func_179141_d();
/*     */     } else {
/*     */       
/* 558 */       VertexBuffer.begin(7);
/* 559 */       VertexBuffer.put(endX, endY - radius, color);
/* 560 */       VertexBuffer.put(endX - radius, endY - radius, color);
/* 561 */       VertexBuffer.put(endX - radius, endY, color);
/* 562 */       VertexBuffer.put(endX, endY, color);
/* 563 */       VertexBuffer.end();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawCircle(float x, float y, float radius, int color) {
/* 569 */     drawCustomCircle(x, y, radius, color, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawCustomCircle(float x, float y, float radius, int innerColor, int outerColor) {
/* 574 */     VertexBuffer.begin(6);
/* 575 */     VertexBuffer.put(x, y, innerColor);
/* 576 */     for (int i = 0; i <= 40; i++) {
/* 577 */       VertexBuffer.put(x + radius * Math.cos(i * 0.15707963267948966D), y + radius * Math.sin(i * 0.15707963267948966D), outerColor);
/*     */     }
/* 579 */     VertexBuffer.end();
/*     */   }
/*     */   
/*     */   public static void drawCircleOutline(float x, float y, float radius, float lineWidth, int color) {
/* 583 */     GL11.glLineWidth(lineWidth);
/* 584 */     GL11.glEnable(2848);
/*     */     
/* 586 */     VertexBuffer.begin(3);
/* 587 */     for (int i = 0; i <= 40; i++) {
/* 588 */       VertexBuffer.put((float)(x + radius * Math.cos(i * 0.15707963267948966D)), (float)(y + radius * Math.sin(i * 0.15707963267948966D)), color);
/* 589 */       VertexBuffer.put((float)(x + radius * Math.cos((i + 1) * 0.15707963267948966D)), (float)(y + radius * Math.sin((i + 1) * 0.15707963267948966D)), color);
/*     */     } 
/* 591 */     VertexBuffer.end();
/*     */     
/* 593 */     GL11.glDisable(2848);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawCustomRoundedRectOutline(float x, float y, float endX, float endY, float radiusFactor, float lineWidth, boolean arcTopRight, boolean arcTopLeft, boolean arcDownRight, boolean arcDownLeft, boolean topToggle, boolean bottomToggle, int color) {
/*     */     float radiusMax;
/* 599 */     if (endX - x > endY - y) {
/* 600 */       radiusMax = (endY - y) / 2.0F;
/*     */     } else {
/* 602 */       radiusMax = (endX - x) / 2.0F;
/*     */     } 
/*     */     
/* 605 */     float radius = radiusFactor * radiusMax;
/*     */     
/* 607 */     GL11.glLineWidth(lineWidth);
/* 608 */     GL11.glEnable(2848);
/*     */     
/* 610 */     if (arcTopRight) {
/* 611 */       VertexBuffer.begin(3);
/* 612 */       for (int i = 0; i <= 20; i++) {
/* 613 */         VertexBuffer.put((float)((endX - radius) + radius * Math.cos(i * 0.07853981633974483D)), (float)((y + radius) - radius * Math.sin(i * 0.07853981633974483D)), color);
/*     */       }
/* 615 */       VertexBuffer.end();
/*     */     } else {
/* 617 */       VertexBuffer.begin(1);
/* 618 */       VertexBuffer.put(endX, y + radius, color);
/* 619 */       VertexBuffer.put(endX, y, color);
/* 620 */       if (!topToggle) {
/* 621 */         VertexBuffer.put(endX, y, color);
/* 622 */         VertexBuffer.put(endX - radius, y, color);
/*     */       } 
/* 624 */       VertexBuffer.end();
/*     */     } 
/*     */     
/* 627 */     if (!topToggle) {
/* 628 */       VertexBuffer.begin(1);
/* 629 */       VertexBuffer.put(endX - radius, y, color);
/* 630 */       VertexBuffer.put(x + radius, y, color);
/* 631 */       VertexBuffer.end();
/*     */     } 
/*     */     
/* 634 */     if (arcTopLeft) {
/* 635 */       VertexBuffer.begin(3);
/* 636 */       for (int i = 0; i <= 20; i++) {
/* 637 */         VertexBuffer.put((float)((x + radius) - radius * Math.cos(i * 0.07853981633974483D)), (float)((y + radius) - radius * Math.sin(i * 0.07853981633974483D)), color);
/*     */       }
/* 639 */       VertexBuffer.end();
/*     */     } else {
/* 641 */       VertexBuffer.begin(1);
/* 642 */       if (!topToggle) {
/* 643 */         VertexBuffer.put(x + radius, y, color);
/* 644 */         VertexBuffer.put(x, y, color);
/*     */       } 
/* 646 */       VertexBuffer.put(x, y, color);
/* 647 */       VertexBuffer.put(x, y + radius, color);
/* 648 */       VertexBuffer.end();
/*     */     } 
/*     */     
/* 651 */     VertexBuffer.begin(1);
/* 652 */     VertexBuffer.put(x, y + radius, color);
/* 653 */     VertexBuffer.put(x, endY - radius, color);
/* 654 */     VertexBuffer.end();
/*     */     
/* 656 */     if (arcDownLeft) {
/* 657 */       VertexBuffer.begin(3);
/* 658 */       for (int i = 0; i <= 20; i++) {
/* 659 */         VertexBuffer.put((float)((x + radius) - radius * Math.cos(i * 0.07853981633974483D)), (float)((endY - radius) + radius * Math.sin(i * 0.07853981633974483D)), color);
/*     */       }
/* 661 */       VertexBuffer.end();
/*     */     } else {
/* 663 */       VertexBuffer.begin(1);
/* 664 */       VertexBuffer.put(x, endY - radius, color);
/* 665 */       VertexBuffer.put(x, endY, color);
/* 666 */       if (!bottomToggle) {
/* 667 */         VertexBuffer.put(x, endY, color);
/* 668 */         VertexBuffer.put(x + radius, endY, color);
/*     */       } 
/* 670 */       VertexBuffer.end();
/*     */     } 
/*     */     
/* 673 */     if (!bottomToggle) {
/* 674 */       VertexBuffer.begin(1);
/* 675 */       VertexBuffer.put(x + radius, endY, color);
/* 676 */       VertexBuffer.put(endX - radius, endY, color);
/* 677 */       VertexBuffer.end();
/*     */     } 
/*     */     
/* 680 */     if (arcDownRight) {
/* 681 */       VertexBuffer.begin(3);
/* 682 */       for (int i = 0; i <= 20; i++) {
/* 683 */         VertexBuffer.put((float)((endX - radius) + radius * Math.cos(i * 0.07853981633974483D)), (float)((endY - radius) + radius * Math.sin(i * 0.07853981633974483D)), color);
/*     */       }
/* 685 */       VertexBuffer.end();
/*     */     } else {
/* 687 */       VertexBuffer.begin(1);
/* 688 */       if (!bottomToggle) {
/* 689 */         VertexBuffer.put(endX - radius, endY, color);
/* 690 */         VertexBuffer.put(endX, endY, color);
/*     */       } 
/* 692 */       VertexBuffer.put(endX, endY, color);
/* 693 */       VertexBuffer.put(endX, endY - radius, color);
/* 694 */       VertexBuffer.end();
/*     */     } 
/*     */     
/* 697 */     VertexBuffer.begin(1);
/* 698 */     VertexBuffer.put(endX, endY - radius, color);
/* 699 */     VertexBuffer.put(endX, y + radius, color);
/* 700 */     VertexBuffer.end();
/*     */     
/* 702 */     GL11.glDisable(2848);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3, int color) {
/* 707 */     drawCustomTriangle(x1, y1, x2, y2, x3, y3, color, color, color);
/*     */   }
/*     */   
/*     */   public static void drawTriangleOutline(float x1, float y1, float x2, float y2, float x3, float y3, float lineWidth, int color) {
/* 711 */     drawCustomTriangleOutline(x1, y1, x2, y2, x3, y3, lineWidth, color, color, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawCustomTriangle(float x1, float y1, float x2, float y2, float x3, float y3, int color1, int color2, int color3) {
/* 716 */     VertexBuffer.begin(4);
/* 717 */     VertexBuffer.put(x1, y1, color1);
/* 718 */     VertexBuffer.put(x2, y2, color2);
/* 719 */     VertexBuffer.put(x3, y3, color3);
/* 720 */     VertexBuffer.end();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawCustomTriangleOutline(float x1, float y1, float x2, float y2, float x3, float y3, float lineWidth, int color1, int color2, int color3) {
/* 725 */     GL11.glLineWidth(lineWidth);
/*     */     
/* 727 */     VertexBuffer.begin(3);
/* 728 */     VertexBuffer.put(x1, y1, color1);
/* 729 */     VertexBuffer.put(x2, y2, color2);
/* 730 */     VertexBuffer.put(x3, y3, color3);
/* 731 */     VertexBuffer.put(x1, y1, color1);
/* 732 */     VertexBuffer.end();
/*     */     
/* 734 */     GL11.glLineWidth(1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawEquilateralTriangle(float x, float y, boolean upsideDown, float size, int color) {
/* 739 */     drawCustomEquilateralTriangle(x, y, size, upsideDown, color, color, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawCustomEquilateralTriangle(float x, float y, float size, boolean upsideDown, int top, int left, int right) {
/* 744 */     VertexBuffer.begin(4);
/* 745 */     VertexBuffer.put(x, upsideDown ? (y + size) : (y - size), top);
/* 746 */     VertexBuffer.put(x + size * Math.cos(3.665191429188092D), upsideDown ? (y + size * Math.sin(3.665191429188092D)) : (y - size * Math.sin(3.665191429188092D)), left);
/* 747 */     VertexBuffer.put(x + size * Math.cos(-0.5235987755982988D), upsideDown ? (y + size * Math.sin(3.665191429188092D)) : (y - size * Math.sin(3.665191429188092D)), right);
/* 748 */     VertexBuffer.end();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawRhombus(float x, float y, float size, int color) {
/* 753 */     drawCustomRhombus(x, y, size, color, color, color, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawCustomRhombus(float x, float y, float size, int topColor, int bottomColor, int leftColor, int rightColor) {
/* 758 */     VertexBuffer.begin(7);
/* 759 */     VertexBuffer.put(x, y - size, topColor);
/* 760 */     VertexBuffer.put(x - size, y, leftColor);
/* 761 */     VertexBuffer.put(x, y + size, bottomColor);
/* 762 */     VertexBuffer.put(x + size, y, rightColor);
/* 763 */     VertexBuffer.end();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawBlurAreaPre(float factor, float partialTicks) {
/* 768 */     ScaledResolution scale = new ScaledResolution(ItemUtils.mc);
/*     */     
/* 770 */     int scaleFactor = scale.func_78325_e();
/* 771 */     int scaleWidth = scale.func_78326_a();
/* 772 */     int scaleHeight = scale.func_78328_b();
/*     */ 
/*     */     
/* 775 */     if (prevScaleFactor != scaleFactor || prevScaleWidth != scaleWidth || prevScaleHeight != scaleHeight || framebuffer == null || shaderGroup == null) {
/*     */       try {
/* 777 */         if (framebuffer != null) {
/* 778 */           framebuffer.func_147608_a();
/*     */         }
/*     */         
/* 781 */         shaderGroup = new ShaderGroup(ItemUtils.mc.func_110434_K(), ItemUtils.mc.func_110442_L(), ItemUtils.mc.func_147110_a(), new ResourceLocation("minecraft:shaders/post/kawase_blur_.json"));
/* 782 */         shaderGroup.func_148026_a(ItemUtils.mc.field_71443_c, ItemUtils.mc.field_71440_d);
/* 783 */         framebuffer = ((AccessorInterfaceShaderGroup)shaderGroup).getListFramebuffers().get(0);
/*     */       }
/* 785 */       catch (Exception exception) {}
/*     */     }
/*     */     
/* 788 */     prevScaleFactor = scaleFactor;
/* 789 */     prevScaleWidth = scaleWidth;
/* 790 */     prevScaleHeight = scaleHeight;
/*     */     
/* 792 */     for (Shader shader : ((AccessorInterfaceShaderGroup)shaderGroup).getListShaders()) {
/* 793 */       shader.func_148043_c().func_147991_a("multiplier").func_148090_a(factor);
/*     */     }
/*     */     
/* 796 */     shaderGroup.func_148018_a(partialTicks);
/* 797 */     ItemUtils.mc.func_147110_a().func_147610_a(true);
/*     */     
/* 799 */     GlStateManager.func_179128_n(5889);
/* 800 */     GlStateManager.func_179096_D();
/* 801 */     GlStateManager.func_179130_a(0.0D, ItemUtils.mc.field_71443_c, ItemUtils.mc.field_71440_d, 0.0D, 1000.0D, 3000.0D);
/* 802 */     GlStateManager.func_179128_n(5888);
/* 803 */     GlStateManager.func_179096_D();
/* 804 */     GlStateManager.func_179109_b(0.0F, 0.0F, -2000.0F);
/* 805 */     GlStateManager.func_179083_b(0, 0, ItemUtils.mc.field_71443_c, ItemUtils.mc.field_71440_d);
/*     */     
/* 807 */     framebuffer.func_147612_c();
/*     */   }
/*     */   
/*     */   public static void drawBlurAreaPost() {
/* 811 */     framebuffer.func_147606_d();
/* 812 */     ScaledResolution scale = new ScaledResolution(ItemUtils.mc);
/* 813 */     GL11.glScalef(scale.func_78325_e(), scale.func_78325_e(), 1.0F);
/*     */   }
/*     */   
/*     */   public static void drawBlurRect(Tessellator tessellator, BufferBuilder bufferBuilder, float x, float y, float endX, float endY) {
/* 817 */     float normalTexX = x / ItemUtils.mc.field_71443_c;
/* 818 */     float normalTexY = 1.0F - y / ItemUtils.mc.field_71440_d;
/* 819 */     float normalEndTexX = endX / ItemUtils.mc.field_71443_c;
/* 820 */     float normalEndTexY = 1.0F - endY / ItemUtils.mc.field_71440_d;
/* 821 */     float f2 = framebuffer.field_147621_c / framebuffer.field_147622_a;
/* 822 */     float f3 = framebuffer.field_147618_d / framebuffer.field_147620_b;
/*     */     
/* 824 */     bufferBuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
/* 825 */     bufferBuilder.func_181662_b(endX, endY, 0.0D).func_187315_a((f2 * normalEndTexX), (f3 * normalEndTexY)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 826 */     bufferBuilder.func_181662_b(endX, y, 0.0D).func_187315_a((f2 * normalEndTexX), (f3 * normalTexY)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 827 */     bufferBuilder.func_181662_b(x, y, 0.0D).func_187315_a((f2 * normalTexX), (f3 * normalTexY)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 828 */     bufferBuilder.func_181662_b(x, endY, 0.0D).func_187315_a((f2 * normalTexX), (f3 * normalEndTexY)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 829 */     tessellator.func_78381_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawBlurRoundedRect(Tessellator tessellator, BufferBuilder bufferBuilder, float x, float y, float radiusFactor, float endX, float endY, boolean arcTopRight, boolean arcTopLeft, boolean arcDownRight, boolean arcDownLeft, boolean onlyTall) {
/*     */     float radiusMax;
/* 835 */     if (endX - x > endY - y) {
/* 836 */       radiusMax = onlyTall ? ((endX - x) / 2.0F) : ((endY - y) / 2.0F);
/*     */     } else {
/* 838 */       radiusMax = (endX - x) / 2.0F;
/*     */     } 
/*     */     
/* 841 */     float radius = radiusFactor * radiusMax;
/*     */     
/* 843 */     float normalTexX = x / ItemUtils.mc.field_71443_c;
/* 844 */     float normalTexY = 1.0F - y / ItemUtils.mc.field_71440_d;
/* 845 */     float normalEndTexX = endX / ItemUtils.mc.field_71443_c;
/* 846 */     float normalEndTexY = 1.0F - endY / ItemUtils.mc.field_71440_d;
/* 847 */     float normalTexXPlusRad = (x + radius) / ItemUtils.mc.field_71443_c;
/* 848 */     float normalTexYPlusRad = 1.0F - (y + radius) / ItemUtils.mc.field_71440_d;
/* 849 */     float normalEndTexXMinusRad = (endX - radius) / ItemUtils.mc.field_71443_c;
/* 850 */     float normalEndTexYMinusRad = 1.0F - (endY - radius) / ItemUtils.mc.field_71440_d;
/*     */     
/* 852 */     float f2 = framebuffer.field_147621_c / framebuffer.field_147622_a;
/* 853 */     float f3 = framebuffer.field_147618_d / framebuffer.field_147620_b;
/*     */     
/* 855 */     bufferBuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
/* 856 */     bufferBuilder.func_181662_b((x + radius), (y + radius), 0.0D).func_187315_a((f2 * normalTexXPlusRad), (f3 * normalTexYPlusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 857 */     bufferBuilder.func_181662_b(x, (y + radius), 0.0D).func_187315_a((f2 * normalTexX), (f3 * normalTexYPlusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 858 */     bufferBuilder.func_181662_b(x, (endY - radius), 0.0D).func_187315_a((f2 * normalTexX), (f3 * normalEndTexYMinusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 859 */     bufferBuilder.func_181662_b((x + radius), (endY - radius), 0.0D).func_187315_a((f2 * normalTexXPlusRad), (f3 * normalEndTexYMinusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/*     */     
/* 861 */     bufferBuilder.func_181662_b((endX - radius), y, 0.0D).func_187315_a((f2 * normalEndTexXMinusRad), (f3 * normalTexY)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 862 */     bufferBuilder.func_181662_b((x + radius), y, 0.0D).func_187315_a((f2 * normalTexXPlusRad), (f3 * normalTexY)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 863 */     bufferBuilder.func_181662_b((x + radius), endY, 0.0D).func_187315_a((f2 * normalTexXPlusRad), (f3 * normalEndTexY)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 864 */     bufferBuilder.func_181662_b((endX - radius), endY, 0.0D).func_187315_a((f2 * normalEndTexXMinusRad), (f3 * normalEndTexY)).func_181669_b(255, 255, 255, 255).func_181675_d();
/*     */     
/* 866 */     bufferBuilder.func_181662_b(endX, (y + radius), 0.0D).func_187315_a((f2 * normalEndTexX), (f3 * normalTexYPlusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 867 */     bufferBuilder.func_181662_b((endX - radius), (y + radius), 0.0D).func_187315_a((f2 * normalEndTexXMinusRad), (f3 * normalTexYPlusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 868 */     bufferBuilder.func_181662_b((endX - radius), (endY - radius), 0.0D).func_187315_a((f2 * normalEndTexXMinusRad), (f3 * normalEndTexYMinusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 869 */     bufferBuilder.func_181662_b(endX, (endY - radius), 0.0D).func_187315_a((f2 * normalEndTexX), (f3 * normalEndTexYMinusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 870 */     tessellator.func_78381_a();
/*     */     
/* 872 */     if (arcTopRight) {
/* 873 */       bufferBuilder.func_181668_a(6, DefaultVertexFormats.field_181709_i);
/* 874 */       bufferBuilder.func_181662_b((endX - radius), (y + radius), 0.0D).func_187315_a((f2 * normalEndTexXMinusRad), (f3 * normalTexYPlusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 875 */       for (int i = 0; i <= 20; i++) {
/* 876 */         bufferBuilder.func_181662_b((endX - radius) + radius * Math.cos(i * 0.07853981633974483D), (y + radius) - radius * Math.sin(i * 0.07853981633974483D), 0.0D).func_187315_a(f2 * ((endX - radius) + radius * Math.cos(i * 0.07853981633974483D)) / ItemUtils.mc.field_71443_c, f3 * (1.0D - ((y + radius) - radius * Math.sin(i * 0.07853981633974483D)) / ItemUtils.mc.field_71440_d)).func_181669_b(255, 255, 255, 255).func_181675_d();
/*     */       }
/*     */     } else {
/* 879 */       bufferBuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
/* 880 */       bufferBuilder.func_181662_b(endX, y, 0.0D).func_187315_a((f2 * normalEndTexX), (f3 * normalTexY)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 881 */       bufferBuilder.func_181662_b((endX - radius), y, 0.0D).func_187315_a((f2 * normalEndTexXMinusRad), (f3 * normalTexY)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 882 */       bufferBuilder.func_181662_b((endX - radius), (y + radius), 0.0D).func_187315_a((f2 * normalEndTexXMinusRad), (f3 * normalTexYPlusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 883 */       bufferBuilder.func_181662_b(endX, (y + radius), 0.0D).func_187315_a((f2 * normalEndTexX), (f3 * normalTexYPlusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/*     */     } 
/* 885 */     tessellator.func_78381_a();
/*     */     
/* 887 */     if (arcTopLeft) {
/* 888 */       bufferBuilder.func_181668_a(6, DefaultVertexFormats.field_181709_i);
/* 889 */       bufferBuilder.func_181662_b((x + radius), (y + radius), 0.0D).func_187315_a((f2 * normalTexXPlusRad), (f3 * normalTexYPlusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 890 */       for (int i = 0; i <= 20; i++) {
/* 891 */         bufferBuilder.func_181662_b((x + radius) - radius * Math.cos((20 - i) * 0.07853981633974483D), (y + radius) - radius * Math.sin((20 - i) * 0.07853981633974483D), 0.0D).func_187315_a(f2 * ((x + radius) - radius * Math.cos((20 - i) * 0.07853981633974483D)) / ItemUtils.mc.field_71443_c, f3 * (1.0D - ((y + radius) - radius * Math.sin((20 - i) * 0.07853981633974483D)) / ItemUtils.mc.field_71440_d)).func_181669_b(255, 255, 255, 255).func_181675_d();
/*     */       }
/*     */     } else {
/* 894 */       bufferBuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
/* 895 */       bufferBuilder.func_181662_b((x + radius), y, 0.0D).func_187315_a((f2 * normalTexXPlusRad), (f3 * normalTexY)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 896 */       bufferBuilder.func_181662_b(x, y, 0.0D).func_187315_a((f2 * normalTexX), (f3 * normalTexY)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 897 */       bufferBuilder.func_181662_b(x, (y + radius), 0.0D).func_187315_a((f2 * normalTexX), (f3 * normalTexYPlusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 898 */       bufferBuilder.func_181662_b((x + radius), (y + radius), 0.0D).func_187315_a((f2 * normalTexXPlusRad), (f3 * normalTexYPlusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/*     */     } 
/* 900 */     tessellator.func_78381_a();
/*     */     
/* 902 */     if (arcDownLeft) {
/* 903 */       bufferBuilder.func_181668_a(6, DefaultVertexFormats.field_181709_i);
/* 904 */       bufferBuilder.func_181662_b((x + radius), (endY - radius), 0.0D).func_187315_a((f2 * normalTexXPlusRad), (f3 * normalEndTexYMinusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 905 */       for (int i = 0; i <= 20; i++) {
/* 906 */         bufferBuilder.func_181662_b((x + radius) - radius * Math.cos(i * 0.07853981633974483D), (endY - radius) + radius * Math.sin(i * 0.07853981633974483D), 0.0D).func_187315_a(f2 * ((x + radius) - radius * Math.cos(i * 0.07853981633974483D)) / ItemUtils.mc.field_71443_c, f3 * (1.0D - ((endY - radius) + radius * Math.sin(i * 0.07853981633974483D)) / ItemUtils.mc.field_71440_d)).func_181669_b(255, 255, 255, 255).func_181675_d();
/*     */       }
/*     */     } else {
/* 909 */       bufferBuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
/* 910 */       bufferBuilder.func_181662_b((x + radius), (endY - radius), 0.0D).func_187315_a((f2 * normalTexXPlusRad), (f3 * normalEndTexYMinusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 911 */       bufferBuilder.func_181662_b(x, (endY - radius), 0.0D).func_187315_a((f2 * normalTexX), (f3 * normalEndTexYMinusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 912 */       bufferBuilder.func_181662_b(x, endY, 0.0D).func_187315_a((f2 * normalTexX), (f3 * normalEndTexY)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 913 */       bufferBuilder.func_181662_b((x + radius), endY, 0.0D).func_187315_a((f2 * normalTexXPlusRad), (f3 * normalEndTexY)).func_181669_b(255, 255, 255, 255).func_181675_d();
/*     */     } 
/* 915 */     tessellator.func_78381_a();
/*     */     
/* 917 */     if (arcDownRight) {
/* 918 */       bufferBuilder.func_181668_a(6, DefaultVertexFormats.field_181709_i);
/* 919 */       bufferBuilder.func_181662_b((endX - radius), (endY - radius), 0.0D).func_187315_a((f2 * normalEndTexXMinusRad), (f3 * normalEndTexYMinusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 920 */       for (int i = 0; i <= 20; i++) {
/* 921 */         bufferBuilder.func_181662_b((endX - radius) + radius * Math.cos((20 - i) * 0.07853981633974483D), (endY - radius) + radius * Math.sin((20 - i) * 0.07853981633974483D), 0.0D).func_187315_a(f2 * ((endX - radius) + radius * Math.cos((20 - i) * 0.07853981633974483D)) / ItemUtils.mc.field_71443_c, f3 * (1.0D - ((endY - radius) + radius * Math.sin((20 - i) * 0.07853981633974483D)) / ItemUtils.mc.field_71440_d)).func_181669_b(255, 255, 255, 255).func_181675_d();
/*     */       }
/*     */     } else {
/* 924 */       bufferBuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
/* 925 */       bufferBuilder.func_181662_b(endX, (endY - radius), 0.0D).func_187315_a((f2 * normalEndTexX), (f3 * normalEndTexYMinusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 926 */       bufferBuilder.func_181662_b((endX - radius), (endY - radius), 0.0D).func_187315_a((f2 * normalEndTexXMinusRad), (f3 * normalEndTexYMinusRad)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 927 */       bufferBuilder.func_181662_b((endX - radius), endY, 0.0D).func_187315_a((f2 * normalEndTexXMinusRad), (f3 * normalEndTexY)).func_181669_b(255, 255, 255, 255).func_181675_d();
/* 928 */       bufferBuilder.func_181662_b(endX, endY, 0.0D).func_187315_a((f2 * normalEndTexX), (f3 * normalEndTexY)).func_181669_b(255, 255, 255, 255).func_181675_d();
/*     */     } 
/* 930 */     tessellator.func_78381_a();
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\graphics\RenderUtils2D.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */