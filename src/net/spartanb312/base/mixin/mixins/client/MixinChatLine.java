/*    */ package net.spartanb312.base.mixin.mixins.client;
/*    */ 
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import me.thediamondsword5.moloch.mixinotherstuff.IChatLine;
/*    */ import me.thediamondsword5.moloch.module.modules.client.ChatSettings;
/*    */ import net.minecraft.client.gui.ChatLine;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({ChatLine.class})
/*    */ public abstract class MixinChatLine
/*    */   implements IChatLine {
/* 17 */   private String time = "";
/*    */ 
/*    */   
/*    */   public String getTime() {
/* 21 */     return this.time;
/*    */   }
/*    */   
/*    */   @Inject(method = {"<init>"}, at = {@At("RETURN")})
/*    */   public void getTime(int p_i45000_1_, ITextComponent p_i45000_2_, int p_i45000_3_, CallbackInfo ci) {
/* 26 */     this.time = (new SimpleDateFormat(((Boolean)ChatSettings.INSTANCE.chatTimeStamps24hr.getValue()).booleanValue() ? "k:mm" : "h:mm aa")).format(new Date());
/* 27 */     storedTime.put(ChatLine.class.cast(this), this.time);
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\client\MixinChatLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */