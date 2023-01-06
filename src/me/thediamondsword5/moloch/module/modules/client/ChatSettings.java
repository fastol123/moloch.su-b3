/*     */ package me.thediamondsword5.moloch.module.modules.client;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.ChatLine;
/*     */ import net.minecraft.network.play.client.CPacketChatMessage;
/*     */ import net.spartanb312.base.BaseCenter;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.network.PacketEvent;
/*     */ import net.spartanb312.base.mixin.mixins.accessor.AccessorCPacketChatMessage;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "ChatSetting", category = Category.CLIENT, description = "Modify Other Chat")
/*     */ public class ChatSettings
/*     */   extends Module
/*     */ {
/*     */   public static ChatSettings INSTANCE;
/*  22 */   public static List<ChatLine> drawnChatLines = Lists.newArrayList();
/*     */   
/*  24 */   public Setting<Boolean> invisibleToggleMessages = setting("InvisibleToggleMessages", false).des("Modules that aren't visible will not send a chat notification on toggle");
/*     */   
/*  26 */   public Setting<Boolean> popNotifications = setting("PopNotifications", false).des("Puts client side notifications in chat whenever someone pops");
/*  27 */   public Setting<Boolean> popNotificationsMarked = setting("PopNotificationsMarked", true).des("Put client name in front of pop notification messages").whenTrue(this.popNotifications);
/*  28 */   public Setting<Effects> popNotificationsEffect = setting("PopNotificationsEffect", Effects.None).des("Effects for pop notification message").whenTrue(this.popNotifications);
/*  29 */   public Setting<StringColorsNoRainbow> popNotificationsColor = setting("PopNotificationsColor", StringColorsNoRainbow.DarkPurple).des("Color of pop notification message").whenTrue(this.popNotifications);
/*  30 */   public Setting<StringColorsNoRainbow> popNotificationsPopNumColor = setting("PopNotifPopNumColor", StringColorsNoRainbow.White).des("Color of pop notification popped totems number").whenTrue(this.popNotifications);
/*  31 */   public Setting<Effects> popNotificationsDeathEffect = setting("PopNotifDeathEffect", Effects.Bold).des("Effects for pop notification death message").whenTrue(this.popNotifications);
/*  32 */   public Setting<StringColorsNoRainbow> popNotificationsDeathColor = setting("PopNotifDeathColor", StringColorsNoRainbow.Red).des("Color of pop notification death message").whenTrue(this.popNotifications);
/*  33 */   public Setting<Boolean> chatTimeStamps = setting("ChatTimeStamps", false).des("Puts Time In Front Of Chat Messages");
/*  34 */   public Setting<Boolean> chatTimeStamps24hr = setting("ChatTimeStamps24hr", true).des("Chat TimeStamps In 24 Hours Format").whenTrue(this.chatTimeStamps);
/*  35 */   public Setting<StringColors> chatTimeStampsColor = setting("ChatTimeStampsColor", StringColors.Blue).des("Color For Chat TimeStamps").whenTrue(this.chatTimeStamps);
/*  36 */   public Setting<Brackets> chatTimeStampBrackets = setting("ChatTimeStampsBrackets", Brackets.Chevron).des("Brackets For Chat TimeStamps").whenTrue(this.chatTimeStamps);
/*  37 */   public Setting<Boolean> chatTimeStampSpace = setting("ChatTimStampsSpace", false).des("Space After Chat TimeStamps").whenTrue(this.chatTimeStamps);
/*  38 */   public Setting<Boolean> chatSuffix = setting("ChatSuffix", false).des("Appends Client Name To Message");
/*  39 */   public Setting<Brackets> brackets = setting("Brackets", Brackets.Chevron).des("Command Prefix Frame Brackets");
/*  40 */   public Setting<Effects> effects = setting("Effects", Effects.None).des("Command Prefix Effect");
/*  41 */   public Setting<Effects> moduleEffects = setting("ModuleEffects", Effects.Bold).des("Command Module Effect");
/*  42 */   public Setting<StringColors> stringColor = setting("ChatColor", StringColors.DarkPurple).des("Color For Client Chat Stuff");
/*  43 */   public Setting<Boolean> lgbtqDynamic = setting("RainbowMove", false).only(v -> (this.stringColor.getValue() == StringColors.Lgbtq || (this.chatTimeStampsColor.getValue() == StringColors.Lgbtq && ((Boolean)this.chatTimeStamps.getValue()).booleanValue()))).des("Rainbow Move");
/*  44 */   public Setting<Integer> lgbtqRealSpeed = setting("Speed", 1, 0, 20).whenTrue(this.lgbtqDynamic).only(v -> (this.stringColor.getValue() == StringColors.Lgbtq || (this.chatTimeStampsColor.getValue() == StringColors.Lgbtq && ((Boolean)this.chatTimeStamps.getValue()).booleanValue())));
/*  45 */   public Setting<Float> lgbtqStart = setting("Hue", 0.0F, 0.0F, 360.0F).only(v -> (this.stringColor.getValue() == StringColors.Lgbtq || (this.chatTimeStampsColor.getValue() == StringColors.Lgbtq && ((Boolean)this.chatTimeStamps.getValue()).booleanValue()))).des("Rainbow Hue");
/*  46 */   public Setting<Float> lgbtqSpeed = setting("ColorSize", 100.0F, 0.0F, 100.0F).whenFalse(this.lgbtqDynamic).only(v -> (this.stringColor.getValue() == StringColors.Lgbtq || (this.chatTimeStampsColor.getValue() == StringColors.Lgbtq && ((Boolean)this.chatTimeStamps.getValue()).booleanValue()))).des("Rainbow sSze");
/*  47 */   public Setting<Float> lgbtqBright = setting("Brightness", 1.0F, 0.0F, 1.0F).only(v -> (this.stringColor.getValue() == StringColors.Lgbtq || (this.chatTimeStampsColor.getValue() == StringColors.Lgbtq && ((Boolean)this.chatTimeStamps.getValue()).booleanValue()))).des("Rainbow Brightness");
/*  48 */   public Setting<Float> lgbtqSaturation = setting("Saturation", 1.0F, 0.0F, 1.0F).only(v -> (this.stringColor.getValue() == StringColors.Lgbtq || (this.chatTimeStampsColor.getValue() == StringColors.Lgbtq && ((Boolean)this.chatTimeStamps.getValue()).booleanValue()))).des("Rainbow Saturation");
/*     */   
/*     */   public ChatSettings() {
/*  51 */     INSTANCE = this;
/*     */   }
/*     */   
/*     */   public enum Brackets {
/*  55 */     Chevron, Box, Curly, Round, None;
/*     */   }
/*     */   
/*     */   public enum Effects {
/*  59 */     Bold, Underline, Italic, None;
/*     */   }
/*     */   
/*     */   public enum StringColors {
/*  63 */     Black, Gold, Gray, Blue, Green, Aqua, Red, LightPurple, Yellow, White, DarkBlue, DarkGreen, DarkAqua, DarkRed, DarkPurple, DarkGray, Lgbtq;
/*     */   }
/*     */   
/*     */   public String colorString(Setting<StringColorsNoRainbow> setting) {
/*  67 */     switch ((StringColorsNoRainbow)setting.getValue()) { case Black:
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
/*  98 */         return "8"; }
/*     */     
/* 100 */     return "";
/*     */   }
/*     */   
/*     */   public enum StringColorsNoRainbow {
/* 104 */     Black, Gold, Gray, Blue, Green, Aqua, Red, LightPurple, Yellow, White, DarkBlue, DarkGreen, DarkAqua, DarkRed, DarkPurple, DarkGray;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPacketSend(PacketEvent.Send event) {
/* 109 */     if (event.packet instanceof CPacketChatMessage && ((Boolean)this.chatSuffix.getValue()).booleanValue()) {
/* 110 */       String s = ((CPacketChatMessage)event.getPacket()).func_149439_c();
/* 111 */       if (s.startsWith("/") || s.startsWith("+") || s.startsWith(".") || s.startsWith("#") || s.startsWith(";") || s.endsWith(BaseCenter.CHAT_SUFFIX))
/* 112 */         return;  s = s + BaseCenter.CHAT_SUFFIX;
/* 113 */       if (s.length() >= 256) s = s.substring(0, 256); 
/* 114 */       ((AccessorCPacketChatMessage)event.getPacket()).setMessage(s);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 121 */     enable();
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\client\ChatSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */