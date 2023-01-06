/*     */ package net.spartanb312.base.mixin.mixins.client;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import me.thediamondsword5.moloch.mixinotherstuff.IChatLine;
/*     */ import me.thediamondsword5.moloch.module.modules.client.ChatSettings;
/*     */ import me.thediamondsword5.moloch.module.modules.other.NameSpoof;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ChatLine;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiNewChat;
/*     */ import net.spartanb312.base.BaseCenter;
/*     */ import net.spartanb312.base.client.FontManager;
/*     */ import net.spartanb312.base.client.ModuleManager;
/*     */ import net.spartanb312.base.module.modules.visuals.NoRender;
/*     */ import net.spartanb312.base.utils.ChatUtil;
/*     */ import net.spartanb312.base.utils.ColorUtil;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
/*     */ 
/*     */ @Mixin({GuiNewChat.class})
/*     */ public abstract class MixinGuiNewChat
/*     */   extends Gui {
/*     */   @Shadow
/*     */   @Final
/*     */   public List<ChatLine> field_146253_i;
/*     */   @Shadow
/*     */   @Final
/*     */   private Minecraft field_146247_f;
/*  40 */   private ChatLine currentLine = null;
/*  41 */   private int intFlag = 0;
/*     */ 
/*     */   
/*     */   @Inject(method = {"drawChat"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/ChatLine;getUpdatedCounter()I")}, locals = LocalCapture.CAPTURE_FAILSOFT)
/*     */   public void grabChatLine(int updateCounter, CallbackInfo ci, int i, int j, float f, boolean flag, float f1, int k, int l, int il, ChatLine chatLine) {
/*  46 */     this.currentLine = chatLine;
/*     */   }
/*     */   @Shadow
/*     */   public abstract int func_146232_i();
/*     */   @Redirect(method = {"drawChat"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
/*     */   private int drawStringWithShadow(FontRenderer fontRenderer, String text, float x, float y, int color) {
/*  52 */     if (((Boolean)ChatSettings.INSTANCE.chatTimeStamps.getValue()).booleanValue()) {
/*     */       
/*  54 */       ChatSettings.drawnChatLines = new ArrayList<>(this.field_146253_i);
/*     */ 
/*     */       
/*     */       try {
/*  58 */         if (IChatLine.storedTime.size() > 100 || ChatSettings.drawnChatLines.size() > 100) {
/*  59 */           for (Map.Entry<ChatLine, String> entry : (new HashMap<>(IChatLine.storedTime)).entrySet()) {
/*  60 */             if (!ChatSettings.drawnChatLines.contains(entry.getKey())) IChatLine.storedTime.remove(entry.getKey());
/*     */           
/*     */           } 
/*     */         }
/*  64 */       } catch (Exception exception) {}
/*     */       
/*  66 */       text = ((ChatSettings.INSTANCE.chatTimeStampsColor.getValue() == ChatSettings.StringColors.Lgbtq) ? "؜" : "") + ChatUtil.SECTIONSIGN + ChatUtil.colorString(ChatSettings.INSTANCE.chatTimeStampsColor) + ChatUtil.bracketLeft(ChatSettings.INSTANCE.chatTimeStampBrackets) + (String)IChatLine.storedTime.get(this.currentLine) + ChatUtil.bracketRight(ChatSettings.INSTANCE.chatTimeStampBrackets) + (!text.contains("͏") ? "§r" : "") + (((Boolean)ChatSettings.INSTANCE.chatTimeStampSpace.getValue()).booleanValue() ? " " : "") + text;
/*     */     } 
/*     */     
/*  69 */     if (ModuleManager.getModule(NameSpoof.class).isEnabled()) {
/*  70 */       text = text.replaceAll(this.field_146247_f.field_71439_g.func_70005_c_(), (String)NameSpoof.INSTANCE.name.getValue());
/*     */     }
/*     */     
/*  73 */     if (text.contains("͏")) {
/*  74 */       BaseCenter.fontManager.drawLgbtqString(text, x, y, ((Boolean)ChatSettings.INSTANCE.lgbtqDynamic.getValue()).booleanValue() ? ColorUtil.rainbow(((Integer)ChatSettings.INSTANCE.lgbtqRealSpeed.getValue()).intValue(), 1.0F, 1.0F, ((Float)ChatSettings.INSTANCE.lgbtqSaturation.getValue()).floatValue(), ((Float)ChatSettings.INSTANCE.lgbtqBright.getValue()).floatValue()) : Color.HSBtoRGB(((Float)ChatSettings.INSTANCE.lgbtqStart.getValue()).floatValue(), ((Float)ChatSettings.INSTANCE.lgbtqSaturation.getValue()).floatValue(), ((Float)ChatSettings.INSTANCE.lgbtqBright.getValue()).floatValue()), ((Float)ChatSettings.INSTANCE.lgbtqSpeed.getValue()).floatValue(), true);
/*     */     } else {
/*  76 */       this.field_146247_f.field_71466_p.func_175063_a(text, x, y, color);
/*     */     } 
/*  78 */     return 0;
/*     */   }
/*     */   
/*     */   @Inject(method = {"drawChat"}, at = {@At("HEAD")})
/*     */   public void drawChatPre(int updateCounter, CallbackInfo ci) {
/*  83 */     this.intFlag = 0;
/*     */   }
/*     */   
/*     */   @Redirect(method = {"drawChat"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"))
/*     */   private void drawRectBackgroundClean(int left, int top, int right, int bottom, int color) {
/*  88 */     this.intFlag++;
/*  89 */     if (!((Boolean)NoRender.INSTANCE.chat.getValue()).booleanValue() || !NoRender.INSTANCE.isEnabled()) {
/*  90 */       Gui.func_73734_a(left, top, (((Boolean)ChatSettings.INSTANCE.chatTimeStamps.getValue()).booleanValue() && this.intFlag < func_146232_i() + 1) ? (right + FontManager.getWidth(((Boolean)ChatSettings.INSTANCE.chatTimeStamps24hr.getValue()).booleanValue() ? "<88:88>      " : "<88:88 PM>      ")) : right, bottom, color);
/*     */     }
/*     */   }
/*     */   
/*     */   @Redirect(method = {"getChatComponent"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;getStringWidth(Ljava/lang/String;)I"))
/*     */   public int getChatComponentRedirect(FontRenderer renderer, String text) {
/*  96 */     if (ModuleManager.getModule(NameSpoof.class).isEnabled()) {
/*  97 */       return renderer.func_78256_a(text.replaceAll(this.field_146247_f.field_71439_g.func_70005_c_(), (String)NameSpoof.INSTANCE.name.getValue()));
/*     */     }
/*     */     
/* 100 */     return renderer.func_78256_a(text);
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\client\MixinGuiNewChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */