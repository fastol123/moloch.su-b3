/*     */ package net.spartanb312.base.client;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.util.List;
/*     */ import me.thediamondsword5.moloch.hud.huds.CustomHUDFont;
/*     */ import me.thediamondsword5.moloch.module.modules.client.ChatSettings;
/*     */ import me.thediamondsword5.moloch.module.modules.client.CustomFont;
/*     */ import me.thediamondsword5.moloch.module.modules.client.MoreClickGUI;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.spartanb312.base.command.Command;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*     */ import net.spartanb312.base.utils.ChatUtil;
/*     */ import net.spartanb312.base.utils.MathUtilFuckYou;
/*     */ import net.spartanb312.base.utils.graphics.font.CFont;
/*     */ import net.spartanb312.base.utils.graphics.font.CFontRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FontManager
/*     */ {
/*     */   public static CFontRenderer iconFontPlus;
/*     */   public static CFontRenderer iconFontMiniIcon;
/*     */   public static CFontRenderer iconFont;
/*     */   public static CFontRenderer fontRenderer;
/*     */   public static CFontRenderer hudFontRenderer;
/*     */   public static CFontRenderer fontArialRenderer;
/*     */   public static CFontRenderer hudFontArialRenderer;
/*     */   public static CFontRenderer hudFontObjectivityRenderer;
/*     */   public static CFontRenderer fontObjectivityRenderer;
/*     */   
/*     */   public static void init() {
/*  36 */     iconFont = new CFontRenderer(new CFont.CustomFont("/assets/moloch/fonts/Symbols.ttf", 19.0F, 0), true, false);
/*  37 */     iconFontPlus = new CFontRenderer(new CFont.CustomFont("/assets/moloch/fonts/Symbols.ttf", 14.0F, 0), true, false);
/*  38 */     iconFontMiniIcon = new CFontRenderer(new CFont.CustomFont("/assets/moloch/fonts/Symbols.ttf", 18.0F, 0), true, false);
/*     */     
/*  40 */     hudFontArialRenderer = new CFontRenderer(new Font("Arial", 0, 36), true, false);
/*  41 */     fontArialRenderer = new CFontRenderer(new Font("Arial", 0, 36), true, false);
/*     */     
/*  43 */     hudFontRenderer = new CFontRenderer(new CFont.CustomFont("/assets/moloch/fonts/Comfortaa-Bold.ttf", 18.0F, 0), true, false);
/*  44 */     fontRenderer = new CFontRenderer(new CFont.CustomFont("/assets/moloch/fonts/Comfortaa-Bold.ttf", 14.0F, 0), true, false);
/*     */     
/*  46 */     hudFontObjectivityRenderer = new CFontRenderer(new CFont.CustomFont("/assets/moloch/fonts/objectivity.bold.ttf", 18.0F, 0), true, false);
/*  47 */     fontObjectivityRenderer = new CFontRenderer(new CFont.CustomFont("/assets/moloch/fonts/objectivity.bold.ttf", 14.0F, 0), true, false);
/*     */   }
/*     */   
/*     */   public static int getWidth(String str) {
/*  51 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Comfortaa) {
/*  52 */       return fontRenderer.getStringWidth(str);
/*     */     }
/*  54 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Arial) {
/*  55 */       return fontArialRenderer.getStringWidth(str);
/*     */     }
/*  57 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Objectivity) {
/*  58 */       return fontObjectivityRenderer.getStringWidth(str);
/*     */     }
/*     */     
/*  61 */     return Command.mc.field_71466_p.func_78256_a(str);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getHeight() {
/*  67 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Comfortaa) {
/*  68 */       return fontRenderer.getHeight() + 2;
/*     */     }
/*  70 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Arial) {
/*  71 */       return fontArialRenderer.getHeight() + 2;
/*     */     }
/*  73 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Objectivity) {
/*  74 */       return fontObjectivityRenderer.getHeight() + 2;
/*     */     }
/*     */     
/*  77 */     return fontRenderer.getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public static CFontRenderer descriptionHubDesTextFontRenderer() {
/*  82 */     switch ((MoreClickGUI.DescriptionModeHubDesTextFont)MoreClickGUI.instance.descriptionModeHubDescriptionFont.getValue()) { case Black:
/*  83 */         return fontRenderer;
/*     */       case Gold:
/*  85 */         return fontArialRenderer;
/*     */       case Gray:
/*  87 */         return fontObjectivityRenderer; }
/*     */     
/*  89 */     return null;
/*     */   }
/*     */   
/*     */   public static int getWidthDescriptionHubDesText(String text) {
/*  93 */     switch ((MoreClickGUI.DescriptionModeHubDesTextFont)MoreClickGUI.instance.descriptionModeHubDescriptionFont.getValue()) { case Black:
/*  94 */         return fontRenderer.getStringWidth(text);
/*     */       case Gold:
/*  96 */         return fontArialRenderer.getStringWidth(text);
/*     */       case Gray:
/*  98 */         return fontObjectivityRenderer.getStringWidth(text);
/*     */       case Blue:
/* 100 */         return Command.mc.field_71466_p.func_78256_a(text); }
/*     */     
/* 102 */     return 0;
/*     */   }
/*     */   
/*     */   public static int getHeightDescriptionHubHeaderText() {
/* 106 */     if (MoreClickGUI.instance.descriptionModeHubDesTextFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Comfortaa) {
/* 107 */       return fontRenderer.getHeight() + 2;
/*     */     }
/* 109 */     if (MoreClickGUI.instance.descriptionModeHubDesTextFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Arial) {
/* 110 */       return fontArialRenderer.getHeight() + 2;
/*     */     }
/* 112 */     if (MoreClickGUI.instance.descriptionModeHubDesTextFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Objectivity) {
/* 113 */       return fontObjectivityRenderer.getHeight() + 2;
/*     */     }
/*     */     
/* 116 */     return fontRenderer.getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getHeightDescriptionHubDesText() {
/* 121 */     if (MoreClickGUI.instance.descriptionModeHubDescriptionFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Comfortaa) {
/* 122 */       return fontRenderer.getHeight() + 2 + ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue();
/*     */     }
/* 124 */     if (MoreClickGUI.instance.descriptionModeHubDescriptionFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Arial) {
/* 125 */       return fontArialRenderer.getHeight() + 2 + ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue();
/*     */     }
/* 127 */     if (MoreClickGUI.instance.descriptionModeHubDescriptionFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Objectivity) {
/* 128 */       return fontObjectivityRenderer.getHeight() + 2 + ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue();
/*     */     }
/*     */     
/* 131 */     return fontRenderer.getHeight() + ((Integer)MoreClickGUI.instance.descriptionModeHubHeightBetweenRowsOfText.getValue()).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getWidthCategory(String str) {
/* 136 */     if (CustomFont.instance.categoryFont.getValue() == CustomFont.FontMode.Comfortaa) {
/* 137 */       return fontRenderer.getStringWidth(str);
/*     */     }
/* 139 */     if (CustomFont.instance.categoryFont.getValue() == CustomFont.FontMode.Arial) {
/* 140 */       return fontArialRenderer.getStringWidth(str);
/*     */     }
/* 142 */     if (CustomFont.instance.categoryFont.getValue() == CustomFont.FontMode.Objectivity) {
/* 143 */       return fontObjectivityRenderer.getStringWidth(str);
/*     */     }
/*     */     
/* 146 */     return Command.mc.field_71466_p.func_78256_a(str);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getHeightCategory() {
/* 151 */     if (CustomFont.instance.categoryFont.getValue() == CustomFont.FontMode.Comfortaa) {
/* 152 */       return hudFontRenderer.getHeight() + 2;
/*     */     }
/* 154 */     if (CustomFont.instance.categoryFont.getValue() == CustomFont.FontMode.Arial) {
/* 155 */       return hudFontArialRenderer.getHeight() + 2;
/*     */     }
/* 157 */     if (CustomFont.instance.categoryFont.getValue() == CustomFont.FontMode.Objectivity) {
/* 158 */       return hudFontObjectivityRenderer.getHeight() + 2;
/*     */     }
/*     */     
/* 161 */     return fontRenderer.getHeight() + 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getWidthHUD(String str) {
/* 166 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Comfortaa) {
/* 167 */       return hudFontRenderer.getStringWidth(str);
/*     */     }
/* 169 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Arial) {
/* 170 */       return hudFontArialRenderer.getStringWidth(str);
/*     */     }
/* 172 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Objectivity) {
/* 173 */       return hudFontObjectivityRenderer.getStringWidth(str);
/*     */     }
/*     */     
/* 176 */     return Command.mc.field_71466_p.func_78256_a(str);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getHeightHUD() {
/* 181 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Comfortaa) {
/* 182 */       return hudFontRenderer.getHeight() + 2;
/*     */     }
/* 184 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Arial) {
/* 185 */       return hudFontArialRenderer.getHeight() + 2;
/*     */     }
/* 187 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Objectivity) {
/* 188 */       return hudFontObjectivityRenderer.getHeight() + 2;
/*     */     }
/*     */     
/* 191 */     return fontRenderer.getHeight() + 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void draw(String str, float x, float y, int color) {
/* 196 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Comfortaa) {
/* 197 */       fontRenderer.drawString(str, x, y, color);
/*     */     }
/* 199 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Arial) {
/* 200 */       fontArialRenderer.drawString(str, x, y - 2.0F, color);
/*     */     }
/* 202 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Objectivity) {
/* 203 */       fontObjectivityRenderer.drawString(str, x, y, color);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawShadow(String str, float x, float y, int color) {
/* 208 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Comfortaa) {
/* 209 */       fontRenderer.drawStringWithShadow(str, x, y, color);
/*     */     }
/* 211 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Arial) {
/* 212 */       fontArialRenderer.drawStringWithShadow(str, x, (y - 2.0F), color);
/*     */     }
/* 214 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Objectivity) {
/* 215 */       fontObjectivityRenderer.drawStringWithShadow(str, x, y, color);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawCategory(String str, float x, float y, int color) {
/* 221 */     if (CustomFont.instance.categoryFont.getValue() == CustomFont.FontMode.Comfortaa) {
/* 222 */       fontRenderer.drawString(str, x, y, color);
/*     */     }
/* 224 */     if (CustomFont.instance.categoryFont.getValue() == CustomFont.FontMode.Arial) {
/* 225 */       fontArialRenderer.drawString(str, x, y - 2.0F, color);
/*     */     }
/* 227 */     if (CustomFont.instance.categoryFont.getValue() == CustomFont.FontMode.Objectivity) {
/* 228 */       fontObjectivityRenderer.drawString(str, x, y, color);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawShadowCategory(String str, float x, float y, int color) {
/* 233 */     if (CustomFont.instance.categoryFont.getValue() == CustomFont.FontMode.Comfortaa) {
/* 234 */       fontRenderer.drawStringWithShadow(str, x, y, color);
/*     */     }
/* 236 */     if (CustomFont.instance.categoryFont.getValue() == CustomFont.FontMode.Arial) {
/* 237 */       fontArialRenderer.drawStringWithShadow(str, x, (y - 2.0F), color);
/*     */     }
/* 239 */     if (CustomFont.instance.categoryFont.getValue() == CustomFont.FontMode.Objectivity) {
/* 240 */       fontObjectivityRenderer.drawStringWithShadow(str, x, y, color);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawHeaderText(String str, float x, float y, int color) {
/* 245 */     if (MoreClickGUI.instance.descriptionModeHubDesTextFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Comfortaa) {
/* 246 */       fontRenderer.drawString(str, x, y, color);
/*     */     }
/* 248 */     if (MoreClickGUI.instance.descriptionModeHubDesTextFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Arial) {
/* 249 */       fontArialRenderer.drawString(str, x, y - 2.0F, color);
/*     */     }
/* 251 */     if (MoreClickGUI.instance.descriptionModeHubDesTextFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Objectivity) {
/* 252 */       fontObjectivityRenderer.drawString(str, x, y, color);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawDesText(String str, float x, float y, int color) {
/* 257 */     if (MoreClickGUI.instance.descriptionModeHubDescriptionFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Comfortaa) {
/* 258 */       fontRenderer.drawString(str, x, y, color);
/*     */     }
/* 260 */     if (MoreClickGUI.instance.descriptionModeHubDescriptionFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Arial) {
/* 261 */       fontArialRenderer.drawString(str, x, y - 2.0F, color);
/*     */     }
/* 263 */     if (MoreClickGUI.instance.descriptionModeHubDescriptionFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Objectivity) {
/* 264 */       fontObjectivityRenderer.drawString(str, x, y, color);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawHeaderTextShadow(String str, float x, float y, int color) {
/* 269 */     if (MoreClickGUI.instance.descriptionModeHubDesTextFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Comfortaa) {
/* 270 */       fontRenderer.drawString(str, x, y, color);
/*     */     }
/* 272 */     if (MoreClickGUI.instance.descriptionModeHubDesTextFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Arial) {
/* 273 */       fontArialRenderer.drawString(str, x, y - 2.0F, color);
/*     */     }
/* 275 */     if (MoreClickGUI.instance.descriptionModeHubDesTextFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Objectivity) {
/* 276 */       fontObjectivityRenderer.drawString(str, x, y, color);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawDesTextShadow(String str, float x, float y, int color) {
/* 281 */     if (MoreClickGUI.instance.descriptionModeHubDescriptionFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Comfortaa) {
/* 282 */       fontRenderer.drawStringWithShadow(str, x, y, color);
/*     */     }
/* 284 */     if (MoreClickGUI.instance.descriptionModeHubDescriptionFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Arial) {
/* 285 */       fontArialRenderer.drawStringWithShadow(str, x, (y - 2.0F), color);
/*     */     }
/* 287 */     if (MoreClickGUI.instance.descriptionModeHubDescriptionFont.getValue() == MoreClickGUI.DescriptionModeHubDesTextFont.Objectivity) {
/* 288 */       fontObjectivityRenderer.drawStringWithShadow(str, x, y, color);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawHUD(String str, float x, float y, boolean shadow, int color) {
/* 293 */     if (shadow) {
/* 294 */       if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Comfortaa) {
/* 295 */         hudFontRenderer.drawStringWithShadow(str, x, y, color);
/*     */       }
/* 297 */       if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Arial) {
/* 298 */         hudFontArialRenderer.drawStringWithShadow(str, x, (y - 2.0F), color);
/*     */       }
/* 300 */       if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Objectivity) {
/* 301 */         hudFontObjectivityRenderer.drawStringWithShadow(str, x, y, color);
/*     */       }
/*     */     } else {
/*     */       
/* 305 */       if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Comfortaa) {
/* 306 */         hudFontRenderer.drawString(str, x, y, color);
/*     */       }
/* 308 */       if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Arial) {
/* 309 */         hudFontArialRenderer.drawString(str, x, y - 2.0F, color);
/*     */       }
/* 311 */       if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Objectivity) {
/* 312 */         hudFontObjectivityRenderer.drawString(str, x, y, color);
/*     */       }
/*     */     } 
/* 315 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Minecraft) {
/* 316 */       Command.mc.field_71466_p.func_175065_a(str, x, y, color, shadow);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawHUD(String str, float x, float y, int color) {
/* 321 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Comfortaa) {
/* 322 */       hudFontRenderer.drawString(str, x, y, color);
/*     */     }
/* 324 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Arial) {
/* 325 */       hudFontArialRenderer.drawString(str, x, y - 2.0F, color);
/*     */     }
/* 327 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Objectivity) {
/* 328 */       hudFontObjectivityRenderer.drawString(str, x, y, color);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawHUDShadow(String str, float x, float y, int color) {
/* 333 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Comfortaa) {
/* 334 */       hudFontRenderer.drawStringWithShadow(str, x, y, color);
/*     */     }
/* 336 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Arial) {
/* 337 */       hudFontArialRenderer.drawStringWithShadow(str, x, (y - 2.0F), color);
/*     */     }
/* 339 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Objectivity) {
/* 340 */       hudFontObjectivityRenderer.drawStringWithShadow(str, x, y, color);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawKeyBind(String str, float x, float y, int color) {
/* 345 */     if (ClickGUI.instance.bindButtonKeyStrFont.getValue() == ClickGUI.KeyBindFancyFont.Comfortaa) {
/* 346 */       fontRenderer.drawString(str, x, y, color);
/*     */     }
/* 348 */     if (ClickGUI.instance.bindButtonKeyStrFont.getValue() == ClickGUI.KeyBindFancyFont.Arial) {
/* 349 */       fontArialRenderer.drawString(str, x, y - 2.0F, color);
/*     */     }
/* 351 */     if (ClickGUI.instance.bindButtonKeyStrFont.getValue() == ClickGUI.KeyBindFancyFont.Objectivity) {
/* 352 */       fontObjectivityRenderer.drawString(str, x, y, color);
/*     */     }
/*     */   }
/*     */   
/*     */   public static int getKeyBindWidth(String str) {
/* 357 */     if (ClickGUI.instance.bindButtonKeyStrFont.getValue() == ClickGUI.KeyBindFancyFont.Comfortaa) {
/* 358 */       return fontRenderer.getStringWidth(str);
/*     */     }
/* 360 */     if (ClickGUI.instance.bindButtonKeyStrFont.getValue() == ClickGUI.KeyBindFancyFont.Arial) {
/* 361 */       return fontArialRenderer.getStringWidth(str);
/*     */     }
/* 363 */     if (ClickGUI.instance.bindButtonKeyStrFont.getValue() == ClickGUI.KeyBindFancyFont.Objectivity) {
/* 364 */       return fontObjectivityRenderer.getStringWidth(str);
/*     */     }
/*     */     
/* 367 */     return Command.mc.field_71466_p.func_78256_a(str);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawCentered(String str, float x, float y, int color) {
/* 373 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Comfortaa) {
/* 374 */       fontRenderer.drawCenteredString(str, x, y, color);
/*     */     }
/* 376 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Arial) {
/* 377 */       fontArialRenderer.drawCenteredString(str, x, y - 2.0F, color);
/*     */     }
/* 379 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Objectivity) {
/* 380 */       fontObjectivityRenderer.drawCenteredString(str, x, y, color);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawShadowCentered(String str, float x, float y, int color) {
/* 385 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Comfortaa) {
/* 386 */       fontRenderer.drawCenteredStringWithShadow(str, x, y, color);
/*     */     }
/* 388 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Arial) {
/* 389 */       fontArialRenderer.drawCenteredStringWithShadow(str, x, y - 2.0F, color);
/*     */     }
/* 391 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Objectivity) {
/* 392 */       fontObjectivityRenderer.drawCenteredStringWithShadow(str, x, y, color);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawHUDCentered(String str, float x, float y, int color) {
/* 398 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Comfortaa) {
/* 399 */       hudFontRenderer.drawCenteredString(str, x, y, color);
/*     */     }
/* 401 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Arial) {
/* 402 */       hudFontArialRenderer.drawCenteredString(str, x, y - 2.0F, color);
/*     */     }
/* 404 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Objectivity) {
/* 405 */       hudFontObjectivityRenderer.drawCenteredString(str, x, y, color);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawHUDShadowCentered(String str, float x, float y, int color) {
/* 410 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Comfortaa) {
/* 411 */       hudFontRenderer.drawCenteredStringWithShadow(str, x, y, color);
/*     */     }
/* 413 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Arial) {
/* 414 */       hudFontArialRenderer.drawCenteredStringWithShadow(str, x, y - 2.0F, color);
/*     */     }
/* 416 */     if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Objectivity) {
/* 417 */       hudFontObjectivityRenderer.drawCenteredStringWithShadow(str, x, y, color);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void draw(String str, int x, int y, Color color) {
/* 423 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Comfortaa) {
/* 424 */       fontRenderer.drawString(str, x, y, color.getRGB());
/*     */     }
/* 426 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Arial) {
/* 427 */       fontArialRenderer.drawString(str, x, y - 2.0F, color.getRGB());
/*     */     }
/* 429 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Objectivity) {
/* 430 */       fontObjectivityRenderer.drawString(str, x, y, color.getRGB());
/*     */     }
/*     */   }
/*     */   
/*     */   public static int getIconWidth() {
/* 435 */     return iconFont.getStringWidth("b");
/*     */   }
/*     */   
/*     */   public static int getIconHeight() {
/* 439 */     return iconFont.getHeight();
/*     */   }
/*     */   
/*     */   public static String iconType() {
/* 443 */     if (ClickGUI.instance.sideIconMode.getValue() == ClickGUI.SideIconMode.Dots) {
/* 444 */       return "b";
/*     */     }
/* 446 */     if (ClickGUI.instance.sideIconMode.getValue() == ClickGUI.SideIconMode.Arrow) {
/* 447 */       return "e";
/*     */     }
/* 449 */     if (ClickGUI.instance.sideIconMode.getValue() == ClickGUI.SideIconMode.Future) {
/* 450 */       return "g";
/*     */     }
/* 452 */     if (ClickGUI.instance.sideIconMode.getValue() == ClickGUI.SideIconMode.None) {
/* 453 */       return "";
/*     */     }
/* 455 */     return "";
/*     */   }
/*     */   
/*     */   public static String iconTypeExtended() {
/* 459 */     if (ClickGUI.instance.sideIconMode.getValue() == ClickGUI.SideIconMode.Dots) {
/* 460 */       return "a";
/*     */     }
/* 462 */     if (ClickGUI.instance.sideIconMode.getValue() == ClickGUI.SideIconMode.Arrow) {
/* 463 */       return "f";
/*     */     }
/* 465 */     if (ClickGUI.instance.sideIconMode.getValue() == ClickGUI.SideIconMode.Future) {
/* 466 */       return "g";
/*     */     }
/* 468 */     if (ClickGUI.instance.sideIconMode.getValue() == ClickGUI.SideIconMode.None) {
/* 469 */       return "";
/*     */     }
/* 471 */     return "";
/*     */   }
/*     */   
/*     */   public static int getVisibilityIconWidth() {
/* 475 */     return iconFont.getStringWidth("(");
/*     */   }
/*     */   
/*     */   public static void drawVisibilityIconOn(int x, int y, int color) {
/* 479 */     iconFont.drawString("(", x, y, color);
/*     */   }
/*     */   
/*     */   public static void drawVisibilityIconOff(int x, int y, int color) {
/* 483 */     iconFont.drawString(")", x, y, color);
/*     */   }
/*     */   
/*     */   public static int getEnumIconWidth() {
/* 487 */     return iconFont.getStringWidth("@");
/*     */   }
/*     */   
/*     */   public static void drawEnumIcon(int x, int y, int color) {
/* 491 */     iconFont.drawString("@", x, y, color);
/*     */   }
/*     */   
/*     */   public static void drawIcon(int x, int y, int color) {
/* 495 */     if (ClickGUI.instance.sideIconMode.getValue() == ClickGUI.SideIconMode.Plus) {
/* 496 */       iconFontPlus.drawString("c", (x - 2), (y + 1), color);
/*     */     }
/* 498 */     else if (ClickGUI.instance.sideIconMode.getValue() == ClickGUI.SideIconMode.Future) {
/* 499 */       iconFontPlus.drawString("g", (x - 4), y, color);
/*     */     } else {
/*     */       
/* 502 */       iconFont.drawString(iconType(), x, y, color);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void drawIcon(int x, int y, Color color) {
/* 507 */     if (ClickGUI.instance.sideIconMode.getValue() == ClickGUI.SideIconMode.Plus) {
/* 508 */       iconFontPlus.drawString("c", (x - 2), (y + 1), color.getRGB());
/*     */     } else {
/*     */       
/* 511 */       iconFont.drawString(iconType(), x, y, color.getRGB());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void drawIconExtended(int x, int y, Color color) {
/* 516 */     if (ClickGUI.instance.sideIconMode.getValue() == ClickGUI.SideIconMode.Plus) {
/* 517 */       iconFontPlus.drawString("d", (x - 2), (y + 1), color.getRGB());
/*     */     } else {
/*     */       
/* 520 */       iconFont.drawString(iconTypeExtended(), x, y, color.getRGB());
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void drawModuleMiniIcon(String icon, int x, int y, Color color) {
/* 525 */     iconFontMiniIcon.drawString(icon, x, y, color.getRGB());
/*     */   }
/*     */   
/*     */   public int drawStringMc(String text, float x, float y, int color, boolean shadow) {
/* 529 */     return Command.mc.field_71466_p.func_175065_a(text, x, y, color, shadow);
/*     */   }
/*     */   
/*     */   public int drawStringMcCentered(String text, float x, float y, int color, boolean shadow) {
/* 533 */     return Command.mc.field_71466_p.func_175065_a(text, x - (getStringWidth(text) / 2), y, color, shadow);
/*     */   }
/*     */   
/*     */   public static List<ITextComponent> splitTextCFont(ITextComponent iTextComponent, int maxTextLength, CFontRenderer fontRendererIn) {
/* 537 */     String localStr = iTextComponent.func_150254_d();
/* 538 */     List<ITextComponent> localList = Lists.newArrayList((Iterable)new TextComponentString(fontRendererIn.trimCFontStringToWidthBetter(localStr, maxTextLength, true)));
/*     */     
/* 540 */     while (fontRendererIn.getStringWidth(localStr) > 0.0F) {
/* 541 */       localStr = localStr.substring(fontRendererIn.trimCFontStringToWidthBetter(localStr, maxTextLength, true).length());
/* 542 */       localList.add(new TextComponentString(fontRendererIn.trimCFontStringToWidthBetter(localStr, maxTextLength, true)));
/*     */     } 
/*     */     
/* 545 */     return localList;
/*     */   }
/*     */   
/*     */   public int colorStringToColor(Setting<ChatSettings.StringColors> setting, int rgbColor) {
/* 549 */     switch ((ChatSettings.StringColors)setting.getValue()) { case Black:
/* 550 */         return Color.BLACK.getRGB();
/*     */       case Gold:
/* 552 */         return (new Color(255, 170, 0)).getRGB();
/*     */       case Gray:
/* 554 */         return (new Color(170, 170, 170)).getRGB();
/*     */       case Blue:
/* 556 */         return (new Color(85, 85, 255)).getRGB();
/*     */       case Green:
/* 558 */         return (new Color(85, 255, 85)).getRGB();
/*     */       case Aqua:
/* 560 */         return (new Color(85, 255, 255)).getRGB();
/*     */       case Red:
/* 562 */         return (new Color(255, 85, 85)).getRGB();
/*     */       case LightPurple:
/* 564 */         return (new Color(255, 85, 255)).getRGB();
/*     */       case Yellow:
/* 566 */         return (new Color(255, 255, 85)).getRGB();
/*     */       case White:
/* 568 */         return Color.WHITE.getRGB();
/*     */       case DarkBlue:
/* 570 */         return (new Color(0, 0, 170)).getRGB();
/*     */       case DarkGreen:
/* 572 */         return (new Color(0, 170, 0)).getRGB();
/*     */       case DarkAqua:
/* 574 */         return (new Color(0, 170, 170)).getRGB();
/*     */       case DarkRed:
/* 576 */         return (new Color(170, 0, 0)).getRGB();
/*     */       case DarkPurple:
/* 578 */         return (new Color(170, 0, 170)).getRGB();
/*     */       case DarkGray:
/* 580 */         return (new Color(85, 85, 85)).getRGB();
/*     */       case Lgbtq:
/* 582 */         return rgbColor; }
/*     */     
/* 584 */     return 0;
/*     */   }
/*     */   
/*     */   public int getStringWidth(String text) {
/* 588 */     return Command.mc.field_71466_p.func_78256_a(text);
/*     */   }
/*     */   
/*     */   public void drawLgbtqString(String text, float x, float y, int startColor, float factor, boolean shadow) {
/* 592 */     Color currentColor = new Color(startColor);
/* 593 */     float hueIncrement = 1.0F / factor;
/* 594 */     float currentHue = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[0];
/* 595 */     float saturation = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[1];
/* 596 */     float brightness = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[2];
/* 597 */     int currentWidth = 0;
/* 598 */     boolean shouldRainbow = false;
/* 599 */     boolean shouldContinue = false;
/* 600 */     boolean flag = false;
/* 601 */     for (int i = 0; i < text.length(); i++) {
/* 602 */       char currentChar = text.charAt(i);
/* 603 */       char nextChar = text.charAt((int)MathUtilFuckYou.clamp((i + 1), 0L, (text.length() - 1)));
/* 604 */       boolean equals = (String.valueOf(currentChar) + nextChar).equals("§r");
/* 605 */       if (equals) {
/* 606 */         shouldRainbow = false;
/*     */       }
/* 608 */       if (shouldContinue) {
/* 609 */         shouldContinue = false;
/*     */       } else {
/*     */         
/* 612 */         if (equals) {
/* 613 */           String escapeString = text.substring(i);
/* 614 */           drawStringMc(escapeString, x + currentWidth, y, Color.WHITE.getRGB(), shadow);
/*     */           break;
/*     */         } 
/* 617 */         if (String.valueOf(currentChar).equals("؜")) {
/* 618 */           shouldRainbow = true;
/*     */         }
/* 620 */         drawStringMc(String.valueOf(currentChar).equals("§") ? "" : String.valueOf(currentChar), x + currentWidth, y, shouldRainbow ? currentColor.getRGB() : ((((Boolean)ChatSettings.INSTANCE.chatTimeStamps.getValue()).booleanValue() && ChatSettings.INSTANCE.chatTimeStampsColor.getValue() != ChatSettings.StringColors.Lgbtq) ? colorStringToColor(ChatSettings.INSTANCE.chatTimeStampsColor, currentColor.getRGB()) : Color.WHITE.getRGB()), shadow);
/* 621 */         if (String.valueOf(currentChar).equals(ChatUtil.bracketLeft(ChatSettings.INSTANCE.brackets))) {
/* 622 */           drawStringMc(ChatUtil.bracketLeft(ChatSettings.INSTANCE.brackets), x + currentWidth, y, Color.GRAY.getRGB(), shadow);
/* 623 */           if (((Boolean)ChatSettings.INSTANCE.chatTimeStamps.getValue()).booleanValue() && !flag) {
/* 624 */             drawStringMc(ChatUtil.bracketLeft(ChatSettings.INSTANCE.brackets), x + currentWidth, y, colorStringToColor(ChatSettings.INSTANCE.chatTimeStampsColor, currentColor.getRGB()), shadow);
/* 625 */             flag = true;
/*     */           } 
/*     */         } 
/* 628 */         if (String.valueOf(currentChar).equals("§")) {
/* 629 */           shouldContinue = true;
/*     */         }
/* 631 */         currentWidth += getStringWidth(String.valueOf(currentChar));
/* 632 */         if (!String.valueOf(currentChar).equals(" ")) {
/* 633 */           currentColor = new Color(Color.HSBtoRGB(currentHue, saturation, brightness));
/* 634 */           currentHue += hueIncrement;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\client\FontManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */