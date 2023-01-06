/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.thediamondsword5.moloch.core.common.Color;
/*    */ import me.thediamondsword5.moloch.module.modules.visuals.ESP;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderItem;
/*    */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ @Mixin({RenderItem.class})
/*    */ public abstract class MixinRenderItem
/*    */ {
/*    */   @Shadow
/*    */   protected abstract void func_191967_a(IBakedModel paramIBakedModel, int paramInt, ItemStack paramItemStack);
/*    */   
/*    */   @Inject(method = {"renderEffect"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderEffectHook(IBakedModel model, CallbackInfo ci) {
/* 27 */     if (ModuleManager.getModule(ESP.class).isEnabled() && ESP.INSTANCE.renderProjectileFlag) ci.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderModelHook(IBakedModel model, ItemStack stack, CallbackInfo ci) {
/* 32 */     if (ModuleManager.getModule(ESP.class).isEnabled() && ESP.INSTANCE.renderProjectileFlag) {
/* 33 */       GL11.glEnable(2848);
/* 34 */       GlStateManager.func_179090_x();
/* 35 */       GlStateManager.func_179097_i();
/* 36 */       GlStateManager.func_179147_l();
/* 37 */       GL11.glDisable(2896);
/* 38 */       GL11.glLineWidth(((Float)ESP.INSTANCE.espProjectileWidth.getValue()).floatValue());
/* 39 */       GL11.glPolygonMode(1032, 6913);
/*    */       
/* 41 */       func_191967_a(model, (new Color(((Color)ESP.INSTANCE.espColorProjectiles.getValue()).getColorColor().getRed(), ((Color)ESP.INSTANCE.espColorProjectiles.getValue()).getColorColor().getGreen(), ((Color)ESP.INSTANCE.espColorProjectiles.getValue()).getColorColor().getBlue(), ((Color)ESP.INSTANCE.espColorProjectiles.getValue()).getAlpha())).getRGB(), stack);
/*    */       
/* 43 */       GL11.glPolygonMode(1032, 6914);
/* 44 */       GlStateManager.func_179098_w();
/* 45 */       GlStateManager.func_179126_j();
/* 46 */       GlStateManager.func_179141_d();
/* 47 */       GL11.glEnable(2896);
/* 48 */       GlStateManager.func_179147_l();
/*    */       
/* 50 */       ci.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinRenderItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */