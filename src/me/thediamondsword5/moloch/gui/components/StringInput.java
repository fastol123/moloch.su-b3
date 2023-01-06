/*     */ package me.thediamondsword5.moloch.gui.components;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.module.modules.client.CustomFont;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.spartanb312.base.client.FontManager;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.core.setting.settings.StringSetting;
/*     */ import net.spartanb312.base.gui.Component;
/*     */ import net.spartanb312.base.gui.Panel;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*     */ import net.spartanb312.base.utils.MathUtilFuckYou;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringInput
/*     */   extends Component
/*     */ {
/*     */   Setting<String> setting;
/*     */   String moduleName;
/*     */   boolean isOverflowing = false;
/*  29 */   Timer typeTimer = new Timer();
/*  30 */   Timer animationTimer = new Timer();
/*  31 */   Timer typingMarkerTimer = new Timer();
/*     */   boolean showTypingMarker = true;
/*  33 */   float animationAlpha = 0.0F;
/*  34 */   int typingMarkerOffset = 0;
/*  35 */   float prevTextWidth = 0.0F;
/*  36 */   float typingMarkerInterpDelta = 300.0F;
/*     */   
/*     */   boolean typingMarkerInterpFlag = false;
/*     */   public static StringInput INSTANCE;
/*     */   
/*     */   public StringInput(Setting<String> setting, int width, int height, Panel father, Module module) {
/*  42 */     this.width = width;
/*  43 */     this.height = height + 14;
/*  44 */     this.father = father;
/*  45 */     this.setting = setting;
/*  46 */     this.moduleName = module.name;
/*  47 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY, float translateDelta, float partialTicks) {
/*  52 */     int passedms = (int)this.animationTimer.hasPassed();
/*  53 */     this.animationTimer.reset();
/*  54 */     if (Keyboard.isKeyDown(1) || Keyboard.isKeyDown(28)) {
/*  55 */       ((StringSetting)this.setting).listening = false;
/*     */     }
/*  57 */     if (!((StringSetting)this.setting).listening && this.typingMarkerOffset != 0) {
/*  58 */       this.typingMarkerOffset = 0;
/*     */     }
/*  60 */     this.isOverflowing = (FontManager.getWidth((String)this.setting.getValue()) * ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue() + this.x + 5.0F > (this.x + this.width - 7));
/*     */     
/*  62 */     RenderUtils2D.drawRect((this.x + 4), this.y + this.height / 2.0F + 2.0F, (this.x + this.width - 4), (this.y + this.height - 2), ((Color)ClickGUI.instance.stringInputBoxColor.getValue()).getColor());
/*     */     
/*  64 */     float settingNameX = (this.x + 5);
/*  65 */     float textY = this.y + this.font.getHeight() / 2.0F + ((Float)ClickGUI.instance.stringInputNameOffset.getValue()).floatValue();
/*     */     
/*  67 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Minecraft) {
/*  68 */       GL11.glEnable(3553);
/*  69 */       GL11.glTranslatef(settingNameX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), textY * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/*  70 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/*  72 */       this.mc.field_71466_p.func_175065_a(this.setting.getName(), settingNameX, textY, ((Color)ClickGUI.instance.stringInputNameColor.getValue()).getColor(), ((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue());
/*     */       
/*  74 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*  75 */       GL11.glTranslatef(settingNameX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, textY * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*  76 */       GL11.glDisable(3553);
/*     */     } else {
/*     */       
/*  79 */       GL11.glTranslatef(settingNameX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), textY * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/*  80 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*  81 */       if (((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue()) {
/*  82 */         FontManager.drawShadow(this.setting.getName(), settingNameX, textY, ((Color)ClickGUI.instance.stringInputNameColor.getValue()).getColor());
/*     */       } else {
/*     */         
/*  85 */         FontManager.draw(this.setting.getName(), settingNameX, textY, ((Color)ClickGUI.instance.stringInputNameColor.getValue()).getColor());
/*     */       } 
/*  87 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*  88 */       GL11.glTranslatef(settingNameX * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, textY * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */     } 
/*     */     
/*  91 */     if (this.isOverflowing) {
/*  92 */       GL11.glTranslatef(-(FontManager.getWidth((String)this.setting.getValue()) * ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue() + this.x + 8.0F - (this.x + this.width - 4)), 0.0F, 0.0F);
/*     */     }
/*     */     
/*  95 */     ScaledResolution scaledResolution = new ScaledResolution(this.mc);
/*  96 */     GL11.glScissor((this.x + 4) * scaledResolution.func_78325_e(), (int)(this.mc.field_71440_d - (((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue() ? (this.mc.field_71440_d - translateDelta * scaledResolution.func_78325_e()) : 0.0F) - (this.y + this.height / 2.0F + 2.0F) * scaledResolution.func_78325_e() - (this.height / 2.0F - 4.0F) * scaledResolution.func_78325_e()), (this.width - 8) * scaledResolution.func_78325_e(), (int)(this.height / 2.0F - 4.0F) * scaledResolution.func_78325_e());
/*  97 */     GL11.glEnable(3089);
/*     */     
/*  99 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Minecraft) {
/* 100 */       GL11.glEnable(3553);
/* 101 */       GL11.glTranslatef((this.x + 6) * (1.0F - ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue()), (this.y + this.height * 0.75F - 2.0F) * (1.0F - ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue()), 0.0F);
/* 102 */       GL11.glScalef(((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue(), ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue(), 1.0F);
/*     */       
/* 104 */       this.mc.field_71466_p.func_175065_a((String)this.setting.getValue(), (this.x + 6), this.y + this.height * 0.75F - 2.0F, ((Color)ClickGUI.instance.stringInputValueColor.getValue()).getColor(), ((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue());
/*     */       
/* 106 */       GL11.glScalef(1.0F / ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue(), 1.0F / ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue(), 1.0F);
/* 107 */       GL11.glTranslatef((this.x + 6) * -(1.0F - ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue()), (this.y + this.height * 0.75F - 2.0F) * -(1.0F - ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue()), 0.0F);
/* 108 */       GL11.glDisable(3553);
/*     */     } else {
/*     */       
/* 111 */       GL11.glTranslatef((this.x + 6) * (1.0F - ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue()), (this.y + this.height * 0.75F) * (1.0F - ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue()), 0.0F);
/* 112 */       GL11.glScalef(((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue(), ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue(), 1.0F);
/* 113 */       if (((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue()) {
/* 114 */         FontManager.drawShadow((String)this.setting.getValue(), (this.x + 6), this.y + this.height * 0.75F, ((Color)ClickGUI.instance.stringInputValueColor.getValue()).getColor());
/*     */       } else {
/*     */         
/* 117 */         FontManager.draw((String)this.setting.getValue(), (this.x + 6), this.y + this.height * 0.75F, ((Color)ClickGUI.instance.stringInputValueColor.getValue()).getColor());
/*     */       } 
/* 119 */       GL11.glScalef(1.0F / ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue(), 1.0F / ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue(), 1.0F);
/* 120 */       GL11.glTranslatef((this.x + 6) * -(1.0F - ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue()), (this.y + this.height * 0.75F) * -(1.0F - ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue()), 0.0F);
/*     */     } 
/* 122 */     GL11.glDisable(3089);
/*     */     
/* 124 */     if (((StringSetting)this.setting).listening) {
/* 125 */       if (this.typingMarkerInterpFlag && passedms < 1000) {
/* 126 */         this.typingMarkerInterpDelta += passedms * 1.5F;
/*     */       }
/* 128 */       if (this.typingMarkerInterpDelta > 300.0F) {
/* 129 */         this.typingMarkerInterpDelta = 300.0F;
/*     */       }
/*     */       
/* 132 */       if (this.showTypingMarker) {
/* 133 */         RenderUtils2D.drawRect(MathUtilFuckYou.linearInterp(this.prevTextWidth, FontManager.getWidth(((String)this.setting.getValue()).substring(0, ((String)this.setting.getValue()).length() - this.typingMarkerOffset)), this.typingMarkerInterpDelta) * ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue() + this.x + 6.0F, this.y + this.height / 2.0F + 3.0F, MathUtilFuckYou.linearInterp(this.prevTextWidth, FontManager.getWidth(((String)this.setting.getValue()).substring(0, ((String)this.setting.getValue()).length() - this.typingMarkerOffset)), this.typingMarkerInterpDelta) * ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue() + this.x + 6.5F, (this.y + this.height - 3), ((Color)ClickGUI.instance.stringInputTypingMarkColor.getValue()).getColor());
/*     */       }
/*     */     } 
/* 136 */     isTyping = ((StringSetting)this.setting).listening;
/*     */     
/* 138 */     if (this.isOverflowing) {
/* 139 */       GL11.glTranslatef(FontManager.getWidth((String)this.setting.getValue()) * ((Float)ClickGUI.instance.stringInputValueScale.getValue()).floatValue() + this.x + 8.0F - (this.x + this.width - 4), 0.0F, 0.0F);
/*     */       
/* 141 */       if (passedms < 1000) {
/* 142 */         this.animationAlpha += passedms * 4.0F / 10.0F;
/*     */       
/*     */       }
/*     */     }
/* 146 */     else if (passedms < 1000) {
/* 147 */       this.animationAlpha -= passedms * 4.0F / 10.0F;
/*     */     } 
/*     */ 
/*     */     
/* 151 */     if (this.animationAlpha > 300.0F) {
/* 152 */       this.animationAlpha = 300.0F;
/*     */     }
/* 154 */     else if (this.animationAlpha < 0.0F) {
/* 155 */       this.animationAlpha = 0.0F;
/*     */     } 
/*     */     
/* 158 */     if (this.typingMarkerTimer.passed(500.0D)) {
/* 159 */       this.showTypingMarker = !this.showTypingMarker;
/* 160 */       this.typingMarkerTimer.reset();
/*     */     } 
/*     */     
/* 163 */     GlStateManager.func_179118_c();
/* 164 */     RenderUtils2D.drawCustomRect((this.x + 4), this.y + this.height / 2.0F + 2.0F, (this.x + 19), (this.y + this.height - 2), (new Color(0, 0, 0, 0)).getRGB(), (new Color(0, 0, 0, (int)(170.0F * this.animationAlpha / 300.0F))).getRGB(), (new Color(0, 0, 0, (int)(170.0F * this.animationAlpha / 300.0F))).getRGB(), (new Color(0, 0, 0, 0)).getRGB());
/* 165 */     if (((Boolean)ClickGUI.instance.stringInputBoxOutline.getValue()).booleanValue()) {
/* 166 */       RenderUtils2D.drawRectOutline((this.x + 4), this.y + this.height / 2.0F + 2.0F, (this.x + this.width - 4), (this.y + this.height - 2), ((Float)ClickGUI.instance.stringInputBoxOutlineWidth.getValue()).floatValue(), ((Color)ClickGUI.instance.stringInputBoxOutlineColor.getValue()).getColor(), false, false);
/*     */     }
/* 168 */     GlStateManager.func_179141_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public void bottomRender(int mouseX, int mouseY, boolean lastSetting, boolean firstSetting, float partialTicks) {
/* 173 */     GlStateManager.func_179118_c();
/* 174 */     drawSettingRects(lastSetting, false);
/*     */     
/* 176 */     drawExtendedGradient(lastSetting, false);
/* 177 */     drawExtendedLine(lastSetting);
/*     */     
/* 179 */     renderHoverRect(this.moduleName + this.setting.getName(), mouseX, mouseY, 2.0F, -13.0F, false);
/*     */     
/* 181 */     GlStateManager.func_179141_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 186 */     if (!isHovered(mouseX, mouseY) || !this.setting.isVisible()) {
/* 187 */       ((StringSetting)this.setting).listening = false;
/* 188 */       return false;
/*     */     } 
/*     */     
/* 191 */     if (mouseButton == 0) {
/* 192 */       ((StringSetting)this.setting).listening = !((StringSetting)this.setting).listening;
/* 193 */       return true;
/*     */     } 
/*     */     
/* 196 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 201 */     return this.setting.isVisible();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 206 */     return this.setting.getDescription();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHovered(int mouseX, int mouseY) {
/* 211 */     if (!anyExpanded) {
/* 212 */       return (mouseX >= Math.min(this.x, this.x + this.width) && mouseX <= Math.max(this.x, this.x + this.width) && mouseY >= Math.min(this.y, this.y + this.height) && mouseY <= Math.max(this.y, this.y + this.height));
/*     */     }
/*     */     
/* 215 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void keyTyped(char typedChar, int keyCode) {
/* 220 */     if (((StringSetting)this.setting).listening) {
/* 221 */       if (isValidInput(keyCode) && this.typeTimer.passed(50.0D)) {
/* 222 */         this.setting.setValue((new StringBuilder((String)this.setting.getValue())).insert(((String)this.setting.getValue()).length() - this.typingMarkerOffset, keyCodeToCapitalized(keyCode)).toString());
/* 223 */         this.typeTimer.reset();
/*     */       } 
/*     */       
/* 226 */       if (keyCode == 14 && ((String)this.setting.getValue()).length() >= 1) {
/* 227 */         this.setting.setValue((new StringBuilder((String)this.setting.getValue())).replace(((String)this.setting.getValue()).length() - this.typingMarkerOffset - 1, ((String)this.setting.getValue()).length() - this.typingMarkerOffset, "").toString());
/*     */       }
/*     */       
/* 230 */       if (keyCode == 203 || keyCode == 205) {
/* 231 */         this.typingMarkerInterpDelta = 0.0F;
/* 232 */         this.typingMarkerInterpFlag = true;
/* 233 */         this.prevTextWidth = FontManager.getWidth(((String)this.setting.getValue()).substring(0, ((String)this.setting.getValue()).length() - this.typingMarkerOffset));
/*     */       } 
/*     */       
/* 236 */       if (keyCode == 203) {
/* 237 */         this.typingMarkerOffset++;
/*     */       }
/* 239 */       else if (keyCode == 205) {
/* 240 */         this.typingMarkerOffset--;
/*     */       } 
/*     */       
/* 243 */       this.typingMarkerOffset = (int)MathUtilFuckYou.clamp(this.typingMarkerOffset, 0L, ((String)this.setting.getValue()).length());
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isValidInput(int keyCode) {
/* 248 */     switch (keyCode) { case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/*     */       case 11:
/*     */       case 12:
/*     */       case 13:
/*     */       case 16:
/*     */       case 17:
/*     */       case 18:
/*     */       case 19:
/*     */       case 20:
/*     */       case 21:
/*     */       case 22:
/*     */       case 23:
/*     */       case 24:
/*     */       case 25:
/*     */       case 26:
/*     */       case 27:
/*     */       case 30:
/*     */       case 31:
/*     */       case 32:
/*     */       case 33:
/*     */       case 34:
/*     */       case 35:
/*     */       case 36:
/*     */       case 37:
/*     */       case 38:
/*     */       case 39:
/*     */       case 40:
/*     */       case 41:
/*     */       case 43:
/*     */       case 44:
/*     */       case 45:
/*     */       case 46:
/*     */       case 47:
/*     */       case 48:
/*     */       case 49:
/*     */       case 50:
/*     */       case 51:
/*     */       case 52:
/*     */       case 53:
/*     */       case 57:
/* 296 */         return true; }
/*     */     
/* 298 */     return false;
/*     */   }
/*     */   
/*     */   public String keyCodeToCapitalized(int keyCode) {
/* 302 */     if (Keyboard.isKeyDown(54) || Keyboard.isKeyDown(42)) {
/* 303 */       switch (keyCode) { case 30:
/* 304 */           return "A";
/* 305 */         case 48: return "B";
/* 306 */         case 46: return "C";
/* 307 */         case 32: return "D";
/* 308 */         case 18: return "E";
/* 309 */         case 33: return "F";
/* 310 */         case 34: return "G";
/* 311 */         case 35: return "H";
/* 312 */         case 23: return "I";
/* 313 */         case 36: return "J";
/* 314 */         case 37: return "K";
/* 315 */         case 38: return "L";
/* 316 */         case 50: return "M";
/* 317 */         case 49: return "N";
/* 318 */         case 24: return "O";
/* 319 */         case 25: return "P";
/* 320 */         case 16: return "Q";
/* 321 */         case 19: return "R";
/* 322 */         case 31: return "S";
/* 323 */         case 20: return "T";
/* 324 */         case 22: return "U";
/* 325 */         case 47: return "V";
/* 326 */         case 17: return "W";
/* 327 */         case 45: return "X";
/* 328 */         case 21: return "Y";
/* 329 */         case 44: return "Z";
/* 330 */         case 11: return ")";
/* 331 */         case 2: return "!";
/* 332 */         case 3: return "@";
/* 333 */         case 4: return "#";
/* 334 */         case 5: return "$";
/* 335 */         case 6: return "%";
/* 336 */         case 7: return "^";
/* 337 */         case 8: return "&";
/* 338 */         case 9: return "*";
/* 339 */         case 10: return "(";
/* 340 */         case 12: return "_";
/* 341 */         case 13: return "+";
/* 342 */         case 26: return "{";
/* 343 */         case 27: return "}";
/* 344 */         case 53: return "?";
/* 345 */         case 43: return "|";
/* 346 */         case 41: return "~";
/* 347 */         case 39: return ":";
/* 348 */         case 40: return "\"";
/* 349 */         case 51: return "<";
/* 350 */         case 52: return ">";
/* 351 */         case 57: return " "; }
/*     */ 
/*     */     
/*     */     } else {
/* 355 */       switch (keyCode) { case 30:
/* 356 */           return "a";
/* 357 */         case 48: return "b";
/* 358 */         case 46: return "c";
/* 359 */         case 32: return "d";
/* 360 */         case 18: return "e";
/* 361 */         case 33: return "f";
/* 362 */         case 34: return "g";
/* 363 */         case 35: return "h";
/* 364 */         case 23: return "i";
/* 365 */         case 36: return "j";
/* 366 */         case 37: return "k";
/* 367 */         case 38: return "l";
/* 368 */         case 50: return "m";
/* 369 */         case 49: return "n";
/* 370 */         case 24: return "o";
/* 371 */         case 25: return "p";
/* 372 */         case 16: return "q";
/* 373 */         case 19: return "r";
/* 374 */         case 31: return "s";
/* 375 */         case 20: return "t";
/* 376 */         case 22: return "u";
/* 377 */         case 47: return "v";
/* 378 */         case 17: return "w";
/* 379 */         case 45: return "x";
/* 380 */         case 21: return "y";
/* 381 */         case 44: return "z";
/* 382 */         case 11: return "0";
/* 383 */         case 2: return "1";
/* 384 */         case 3: return "2";
/* 385 */         case 4: return "3";
/* 386 */         case 5: return "4";
/* 387 */         case 6: return "5";
/* 388 */         case 7: return "6";
/* 389 */         case 8: return "7";
/* 390 */         case 9: return "8";
/* 391 */         case 10: return "9";
/* 392 */         case 12: return "-";
/* 393 */         case 13: return "=";
/* 394 */         case 26: return "[";
/* 395 */         case 27: return "]";
/* 396 */         case 53: return "/";
/* 397 */         case 43: return "\\";
/* 398 */         case 41: return "`";
/* 399 */         case 39: return ";";
/* 400 */         case 40: return "'";
/* 401 */         case 51: return ",";
/* 402 */         case 52: return ".";
/* 403 */         case 57: return " "; }
/*     */     
/*     */     } 
/* 406 */     return "";
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\gui\components\StringInput.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */