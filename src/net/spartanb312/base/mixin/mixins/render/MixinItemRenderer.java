/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.render.ItemModelEvent;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.ItemRenderer;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.module.modules.visuals.NoRender;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({ItemRenderer.class})
/*    */ public abstract class MixinItemRenderer
/*    */ {
/*    */   private boolean flag = true;
/*    */   
/*    */   @Shadow
/*    */   public abstract void func_187457_a(AbstractClientPlayer paramAbstractClientPlayer, float paramFloat1, float paramFloat2, EnumHand paramEnumHand, float paramFloat3, ItemStack paramItemStack, float paramFloat4);
/*    */   
/*    */   @Inject(method = {"renderWaterOverlayTexture"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderWaterOverlayTextureHook(float partialTicks, CallbackInfo ci) {
/* 32 */     if (ModuleManager.getModule(NoRender.class).isEnabled() && ((Boolean)NoRender.INSTANCE.waterOverlay.getValue()).booleanValue())
/* 33 */       ci.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderFireInFirstPerson"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderFireInFirstPersonHook(CallbackInfo ci) {
/* 38 */     if (ModuleManager.getModule(NoRender.class).isEnabled() && ((Boolean)NoRender.INSTANCE.fire.getValue()).booleanValue())
/* 39 */       ci.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderSuffocationOverlay"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void renderSuffocationOverlayHook(TextureAtlasSprite sprite, CallbackInfo ci) {
/* 44 */     if (ModuleManager.getModule(NoRender.class).isEnabled() && ((Boolean)NoRender.INSTANCE.blockOverlay.getValue()).booleanValue())
/* 45 */       ci.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderItemInFirstPersonHook(AbstractClientPlayer player, float p_187457_2_, float p_187457_3_, EnumHand hand, float p_187457_5_, ItemStack stack, float p_187457_7_, CallbackInfo ci) {
/* 50 */     ItemModelEvent.EquipProgress equipProgress = new ItemModelEvent.EquipProgress(1.0F, 1.0F, false, false);
/* 51 */     BaseCenter.EVENT_BUS.post(equipProgress);
/*    */     
/* 53 */     if (equipProgress.isCancelled() && this.flag) {
/* 54 */       ci.cancel();
/* 55 */       this.flag = false;
/* 56 */       if (hand == EnumHand.MAIN_HAND) {
/* 57 */         func_187457_a(player, p_187457_2_, p_187457_3_, hand, p_187457_5_ + (((ItemModelEvent)equipProgress).modifyMain ? ((ItemModelEvent)equipProgress).offsetMain : 1.0F), stack, p_187457_7_);
/*    */       }
/*    */       
/* 60 */       if (hand == EnumHand.OFF_HAND) {
/* 61 */         func_187457_a(player, p_187457_2_, p_187457_3_, hand, p_187457_5_ + (((ItemModelEvent)equipProgress).modifyOff ? ((ItemModelEvent)equipProgress).offsetOff : 1.0F), stack, p_187457_7_);
/*    */       }
/* 63 */       this.flag = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItemSide(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemCameraTransforms$TransformType;Z)V")})
/*    */   private void renderItemInFirstPersonHook1(AbstractClientPlayer player, float partialTicks, float pitch, EnumHand hand, float swingProgress, ItemStack stack, float equippedProgress, CallbackInfo ci) {
/* 69 */     ItemModelEvent.Normal normal = new ItemModelEvent.Normal(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, false, false, hand);
/*    */ 
/*    */     
/* 72 */     BaseCenter.EVENT_BUS.post(normal);
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinItemRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */