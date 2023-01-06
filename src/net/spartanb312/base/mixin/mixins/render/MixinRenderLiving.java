/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ 
/*    */ import me.thediamondsword5.moloch.module.modules.visuals.ESP;
/*    */ import net.minecraft.client.renderer.entity.RenderLiving;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({RenderLiving.class})
/*    */ public class MixinRenderLiving {
/*    */   @Inject(method = {"renderLeash"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void renderLeashHook(CallbackInfo ci) {
/* 15 */     if (ModuleManager.getModule(ESP.class).isEnabled() && ESP.INSTANCE.renderOutlineFlag) ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinRenderLiving.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */