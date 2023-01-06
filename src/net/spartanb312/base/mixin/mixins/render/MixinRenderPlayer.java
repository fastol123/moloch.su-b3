/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ 
/*    */ import me.thediamondsword5.moloch.module.modules.other.Freecam;
/*    */ import me.thediamondsword5.moloch.module.modules.visuals.Nametags;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.utils.ItemUtils;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({RenderPlayer.class})
/*    */ public abstract class MixinRenderPlayer extends RenderLivingBase<AbstractClientPlayer> {
/*    */   public MixinRenderPlayer(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/* 23 */     super(renderManagerIn, modelBaseIn, shadowSizeIn);
/*    */   }
/*    */   
/*    */   @Shadow
/*    */   protected abstract void func_177137_d(AbstractClientPlayer paramAbstractClientPlayer);
/*    */   
/*    */   @Inject(method = {"renderEntityName"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderEntityNameHook(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq, CallbackInfo ci) {
/* 31 */     if (ModuleManager.getModule(Nametags.class).isEnabled()) {
/* 32 */       ci.cancel();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"doRender"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderManager;renderViewEntity:Lnet/minecraft/entity/Entity;")})
/*    */   public void doRenderGetRenderViewEntity(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
/* 39 */     if (ModuleManager.getModule(Freecam.class).isEnabled() && ItemUtils.mc.func_175606_aa() != ItemUtils.mc.field_71439_g) {
/* 40 */       double renderY = y;
/*    */       
/* 42 */       if (entity.func_70093_af()) {
/* 43 */         renderY = y - 0.125D;
/*    */       }
/* 45 */       func_177137_d(entity);
/* 46 */       GlStateManager.func_187408_a(GlStateManager.Profile.PLAYER_SKIN);
/* 47 */       func_76986_a((EntityLivingBase)entity, x, renderY, z, entityYaw, partialTicks);
/* 48 */       GlStateManager.func_187440_b(GlStateManager.Profile.PLAYER_SKIN);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinRenderPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */