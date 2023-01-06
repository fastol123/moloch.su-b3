/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import me.thediamondsword5.moloch.event.events.render.RenderEntityEvent;
/*    */ import me.thediamondsword5.moloch.event.events.render.RenderEntityInvokeEvent;
/*    */ import me.thediamondsword5.moloch.event.events.render.RenderEntityLayersEvent;
/*    */ import me.thediamondsword5.moloch.module.modules.visuals.ESP;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin(value = {RenderLivingBase.class}, priority = 2147483596)
/*    */ public class MixinRenderLivingBase<T extends EntityLivingBase>
/*    */   extends Render<T> {
/*    */   @Shadow
/*    */   protected ModelBase field_77045_g;
/*    */   
/*    */   public MixinRenderLivingBase(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
/* 32 */     super(renderManagerIn);
/* 33 */     this.field_77045_g = modelBaseIn;
/* 34 */     this.field_76989_e = shadowSizeIn;
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderModel"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void doRender(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, CallbackInfo ci) {
/* 39 */     RenderEntityEvent event = new RenderEntityEvent(this.field_77045_g, (Entity)entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
/* 40 */     if (!func_180548_c((Entity)entityIn)) {
/*    */       return;
/*    */     }
/* 43 */     BaseCenter.EVENT_BUS.post(event);
/* 44 */     if (event.isCancelled()) {
/* 45 */       ci.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderModel"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V")}, cancellable = true)
/*    */   public void doRenderInvoke(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, CallbackInfo ci) {
/* 51 */     RenderEntityInvokeEvent event = new RenderEntityInvokeEvent(this.field_77045_g, (Entity)entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
/* 52 */     if (!func_180548_c((Entity)entityIn)) {
/*    */       return;
/*    */     }
/* 55 */     BaseCenter.EVENT_BUS.post(event);
/* 56 */     if (event.isCancelled()) {
/* 57 */       ci.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderLayers"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/LayerRenderer;doRenderLayer(Lnet/minecraft/entity/EntityLivingBase;FFFFFFF)V")}, cancellable = true)
/*    */   public void renderLayersHook(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleIn, CallbackInfo ci) {
/* 63 */     if (ModuleManager.getModule(ESP.class).isEnabled() && ESP.INSTANCE.renderOutlineFlag) ci.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderLayers"}, at = {@At("RETURN")}, cancellable = true)
/*    */   public void renderLayersAndShitINeedToSleep(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleIn, CallbackInfo ci) {
/* 68 */     RenderEntityLayersEvent event = new RenderEntityLayersEvent(RenderLivingBase.class.cast(this), this.field_77045_g, entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scaleIn);
/* 69 */     BaseCenter.EVENT_BUS.post(event);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Redirect(method = {"doRender"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;disableCull()V"))
/*    */   public void doRenderDisableCullHook(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 76 */     if (!ModuleManager.getModule(ESP.class).isEnabled() || !ESP.INSTANCE.renderOutlineFlag) GlStateManager.func_179129_p(); 
/*    */   }
/*    */   
/*    */   @Redirect(method = {"doRender"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;enableCull()V"))
/*    */   public void doRenderEnableCullHook(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 81 */     if (!ModuleManager.getModule(ESP.class).isEnabled() || !ESP.INSTANCE.renderOutlineFlag) GlStateManager.func_179089_o(); 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected ResourceLocation getEntityTexture(T entity) {
/* 86 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinRenderLivingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */