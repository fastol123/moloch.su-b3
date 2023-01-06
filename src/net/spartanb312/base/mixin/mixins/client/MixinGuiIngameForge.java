/*    */ package net.spartanb312.base.mixin.mixins.client;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.render.DrawScreenEvent;
/*    */ import net.minecraftforge.client.GuiIngameForge;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({GuiIngameForge.class})
/*    */ public class MixinGuiIngameForge {
/*    */   @Inject(method = {"renderGameOverlay"}, at = {@At(value = "INVOKE", target = "Lnet/minecraftforge/client/GuiIngameForge;renderChat(II)V")})
/*    */   public void renderChatHook(float partialTicks, CallbackInfo ci) {
/* 15 */     DrawScreenEvent.Chat chat = new DrawScreenEvent.Chat();
/* 16 */     BaseCenter.EVENT_BUS.post(chat);
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\client\MixinGuiIngameForge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */