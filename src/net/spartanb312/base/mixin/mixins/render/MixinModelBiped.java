/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import net.spartanb312.base.event.events.render.RenderModelEvent;
/*    */ import net.spartanb312.base.utils.ItemUtils;
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
/*    */ @Mixin({ModelBiped.class})
/*    */ public class MixinModelBiped
/*    */ {
/*    */   @Shadow
/*    */   public ModelRenderer field_178723_h;
/*    */   public int heldItemRight;
/*    */   @Shadow
/*    */   public ModelRenderer field_78116_c;
/*    */   
/*    */   @Inject(method = {"setRotationAngles"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/model/ModelBiped;swingProgress:F")})
/*    */   private void revertSwordAnimation(float p_setRotationAngles_1_, float p_setRotationAngles_2_, float p_setRotationAngles_3_, float p_setRotationAngles_4_, float p_setRotationAngles_5_, float p_setRotationAngles_6_, Entity p_setRotationAngles_7_, CallbackInfo callbackInfo) {
/* 30 */     if (this.heldItemRight == 3) {
/* 31 */       this.field_178723_h.field_78796_g = 0.0F;
/*    */     }
/* 33 */     if (p_setRotationAngles_7_ instanceof net.minecraft.entity.player.EntityPlayer && p_setRotationAngles_7_.equals(ItemUtils.mc.field_71439_g)) {
/* 34 */       RenderModelEvent event = new RenderModelEvent();
/* 35 */       BaseCenter.EVENT_BUS.post(event);
/* 36 */       if (event.rotating) this.field_78116_c.field_78795_f = event.pitch / 57.295776F; 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinModelBiped.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */