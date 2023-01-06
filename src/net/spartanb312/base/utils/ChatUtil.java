/*     */ package net.spartanb312.base.utils;
/*     */ 
/*     */ import me.thediamondsword5.moloch.module.modules.client.ChatSettings;
/*     */ import me.thediamondsword5.moloch.module.modules.client.ClientInfo;
/*     */ import net.minecraft.client.gui.GuiNewChat;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChatUtil
/*     */ {
/*     */   private static final int DeleteID = 114514;
/*  17 */   public static char SECTIONSIGN = '§';
/*     */   
/*     */   public static String colored(String code) {
/*  20 */     return SECTIONSIGN + code;
/*     */   }
/*     */   
/*     */   public static String bracketLeft(Setting<ChatSettings.Brackets> setting) {
/*  24 */     switch ((ChatSettings.Brackets)setting.getValue()) { case Black:
/*  25 */         return "<";
/*     */       case Gold:
/*  27 */         return "[";
/*     */       case Gray:
/*  29 */         return "{";
/*     */       case Blue:
/*  31 */         return "(";
/*     */       case Green:
/*  33 */         return " "; }
/*     */     
/*  35 */     return "";
/*     */   }
/*     */   
/*     */   public static String bracketRight(Setting<ChatSettings.Brackets> setting) {
/*  39 */     switch ((ChatSettings.Brackets)setting.getValue()) { case Black:
/*  40 */         return ">";
/*     */       case Gold:
/*  42 */         return "]";
/*     */       case Gray:
/*  44 */         return "}";
/*     */       case Blue:
/*  46 */         return ")";
/*     */       case Green:
/*  48 */         return " "; }
/*     */     
/*  50 */     return "";
/*     */   }
/*     */   
/*     */   public static String effectString(Setting<ChatSettings.Effects> setting) {
/*  54 */     switch ((ChatSettings.Effects)setting.getValue()) { case Black:
/*  55 */         return SECTIONSIGN + "l";
/*     */       case Gold:
/*  57 */         return SECTIONSIGN + "n";
/*     */       case Gray:
/*  59 */         return SECTIONSIGN + "o";
/*     */       case Blue:
/*  61 */         return ""; }
/*     */     
/*  63 */     return "";
/*     */   }
/*     */   
/*     */   public static String colorString(Setting<ChatSettings.StringColors> setting) {
/*  67 */     switch ((ChatSettings.StringColors)setting.getValue()) { case Black:
/*  68 */         return "0";
/*     */       case Gold:
/*  70 */         return "6";
/*     */       case Gray:
/*  72 */         return "7";
/*     */       case Blue:
/*  74 */         return "9";
/*     */       case Green:
/*  76 */         return "a";
/*     */       case Aqua:
/*  78 */         return "b";
/*     */       case Red:
/*  80 */         return "c";
/*     */       case LightPurple:
/*  82 */         return "d";
/*     */       case Yellow:
/*  84 */         return "e";
/*     */       case White:
/*  86 */         return "f";
/*     */       case DarkBlue:
/*  88 */         return "1";
/*     */       case DarkGreen:
/*  90 */         return "2";
/*     */       case DarkAqua:
/*  92 */         return "3";
/*     */       case DarkRed:
/*  94 */         return "4";
/*     */       case DarkPurple:
/*  96 */         return "5";
/*     */       case DarkGray:
/*  98 */         return "8";
/*     */       case Lgbtq:
/* 100 */         return "͏"; }
/*     */     
/* 102 */     return "";
/*     */   }
/*     */   
/*     */   public static void sendNoSpamMessage(String message, int messageID) {
/* 106 */     sendNoSpamRawChatMessage(SECTIONSIGN + "7" + bracketLeft(ChatSettings.INSTANCE.brackets) + "؜" + ((ChatSettings.INSTANCE.stringColor.getValue() == ChatSettings.StringColors.Lgbtq) ? " " : "") + SECTIONSIGN + colorString(ChatSettings.INSTANCE.stringColor) + effectString(ChatSettings.INSTANCE.effects) + (String)ClientInfo.INSTANCE.clientName.getValue() + "§r" + SECTIONSIGN + "7" + bracketRight(ChatSettings.INSTANCE.brackets) + " " + SECTIONSIGN + "r" + message, messageID);
/*     */   }
/*     */   
/*     */   public static void sendNoSpamMessage(String message) {
/* 110 */     sendNoSpamRawChatMessage(SECTIONSIGN + "7" + bracketLeft(ChatSettings.INSTANCE.brackets) + "؜" + ((ChatSettings.INSTANCE.stringColor.getValue() == ChatSettings.StringColors.Lgbtq) ? " " : "") + SECTIONSIGN + colorString(ChatSettings.INSTANCE.stringColor) + effectString(ChatSettings.INSTANCE.effects) + (String)ClientInfo.INSTANCE.clientName.getValue() + "§r" + SECTIONSIGN + "7" + bracketRight(ChatSettings.INSTANCE.brackets) + " " + SECTIONSIGN + "r" + message);
/*     */   }
/*     */   
/*     */   public static void sendNoSpamMessage(String[] messages) {
/* 114 */     sendNoSpamMessage("");
/* 115 */     for (String s : messages) sendNoSpamRawChatMessage(s); 
/*     */   }
/*     */   
/*     */   public static void sendNoSpamErrorMessage(String message) {
/* 119 */     sendNoSpamRawChatMessage(SECTIONSIGN + "7" + bracketLeft(ChatSettings.INSTANCE.brackets) + SECTIONSIGN + "4" + SECTIONSIGN + "lERROR" + SECTIONSIGN + "7" + bracketRight(ChatSettings.INSTANCE.brackets) + " " + SECTIONSIGN + "r" + message);
/*     */   }
/*     */   
/*     */   public static void sendNoSpamErrorMessage(String message, int messageID) {
/* 123 */     sendNoSpamRawChatMessage(SECTIONSIGN + "7" + bracketLeft(ChatSettings.INSTANCE.brackets) + SECTIONSIGN + "4" + SECTIONSIGN + "lERROR" + SECTIONSIGN + "7" + bracketRight(ChatSettings.INSTANCE.brackets) + " " + SECTIONSIGN + "r" + message, messageID);
/*     */   }
/*     */   
/*     */   public static void sendNoSpamRawChatMessage(String message) {
/* 127 */     sendSpamlessMessage(message);
/*     */   }
/*     */   
/*     */   public static void sendNoSpamRawChatMessage(String message, int messageID) {
/* 131 */     sendSpamlessMessage(messageID, message);
/*     */   }
/*     */   
/*     */   public static void printRawChatMessage(String message) {
/* 135 */     if (RotationUtil.mc.field_71439_g == null)
/* 136 */       return;  ChatMessage(message);
/*     */   }
/*     */   
/*     */   public static void printChatMessage(String message) {
/* 140 */     printRawChatMessage(SECTIONSIGN + "7" + bracketLeft(ChatSettings.INSTANCE.brackets) + "؜" + ((ChatSettings.INSTANCE.stringColor.getValue() == ChatSettings.StringColors.Lgbtq) ? " " : "") + SECTIONSIGN + colorString(ChatSettings.INSTANCE.stringColor) + effectString(ChatSettings.INSTANCE.effects) + (String)ClientInfo.INSTANCE.clientName.getValue() + "§r" + SECTIONSIGN + "7" + bracketRight(ChatSettings.INSTANCE.brackets) + " " + SECTIONSIGN + "r" + message);
/*     */   }
/*     */   
/*     */   public static void printErrorChatMessage(String message) {
/* 144 */     printRawChatMessage(SECTIONSIGN + "7" + bracketLeft(ChatSettings.INSTANCE.brackets) + SECTIONSIGN + "4" + SECTIONSIGN + "lERROR" + SECTIONSIGN + "7" + bracketRight(ChatSettings.INSTANCE.brackets) + " " + SECTIONSIGN + "r" + message);
/*     */   }
/*     */   
/*     */   public static void sendSpamlessMessage(String message) {
/* 148 */     if (RotationUtil.mc.field_71439_g == null)
/* 149 */       return;  GuiNewChat chat = RotationUtil.mc.field_71456_v.func_146158_b();
/* 150 */     chat.func_146234_a((ITextComponent)new TextComponentString(message), 114514);
/*     */   }
/*     */   
/*     */   public static void sendSpamlessMessage(int messageID, String message) {
/* 154 */     if (RotationUtil.mc.field_71439_g == null)
/* 155 */       return;  GuiNewChat chat = RotationUtil.mc.field_71456_v.func_146158_b();
/* 156 */     chat.func_146234_a((ITextComponent)new TextComponentString(message), messageID);
/*     */   }
/*     */   
/*     */   public static void ChatMessage(String message) {
/* 160 */     RotationUtil.mc.field_71456_v.func_146158_b().func_146227_a((ITextComponent)new TextComponentString(message));
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\ChatUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */