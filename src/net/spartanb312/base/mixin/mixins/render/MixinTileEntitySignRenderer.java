/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ 
/*    */ import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
/*    */ import net.minecraft.tileentity.TileEntitySign;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.module.modules.visuals.NoRender;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({TileEntitySignRenderer.class})
/*    */ public class MixinTileEntitySignRenderer {
/*    */   @Inject(method = {"render(Lnet/minecraft/tileentity/TileEntitySign;DDDFIF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;glNormal3f(FFF)V")})
/*    */   public void renderHookPre(TileEntitySign te, double x, double y, double z, float partialTicks, int destroyStage, float alpha, CallbackInfo ci) {
/* 17 */     if (ModuleManager.getModule(NoRender.class).isEnabled() && ((Boolean)NoRender.INSTANCE.signText.getValue()).booleanValue())
/* 18 */       GL11.glScalef(0.0F, 0.0F, 1.0F); 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinTileEntitySignRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */