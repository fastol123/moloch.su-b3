/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ 
/*    */ import me.thediamondsword5.moloch.module.modules.visuals.Chams;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerCape;
/*    */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.utils.ItemUtils;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Mutable;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({LayerCape.class})
/*    */ public class MixinLayerCape
/*    */ {
/*    */   @Mutable
/*    */   @Final
/*    */   @Shadow
/*    */   private final RenderPlayer field_177167_a;
/*    */   
/*    */   public MixinLayerCape(RenderPlayer playerRendererIn) {
/* 37 */     this.field_177167_a = playerRendererIn;
/*    */   }
/*    */   
/*    */   @Inject(method = {"doRenderLayer(Lnet/minecraft/client/entity/AbstractClientPlayer;FFFFFFF)V"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void doRenderLayerHook(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo ci) {
/* 42 */     if (ModuleManager.getModule(Chams.class).isEnabled() && ((((Boolean)Chams.instance.playerChangeCapeAlpha.getValue()).booleanValue() && ((Boolean)Chams.instance.self.getValue()).booleanValue() && ((Boolean)Chams.instance.selfChangeCapeAlpha.getValue()).booleanValue() && entitylivingbaseIn == ItemUtils.mc.field_71439_g) || (((Boolean)Chams.instance.playerChangeCapeAlpha.getValue()).booleanValue() && ((Boolean)Chams.instance.otherPlayers.getValue()).booleanValue() && entitylivingbaseIn != ItemUtils.mc.field_71439_g))) {
/* 43 */       doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
/* 44 */       ci.cancel();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 50 */     if (entitylivingbaseIn.func_152122_n() && !entitylivingbaseIn.func_82150_aj() && entitylivingbaseIn.func_175148_a(EnumPlayerModelParts.CAPE) && entitylivingbaseIn.func_110303_q() != null) {
/*    */       
/* 52 */       ItemStack itemstack = entitylivingbaseIn.func_184582_a(EntityEquipmentSlot.CHEST);
/*    */       
/* 54 */       if (itemstack.func_77973_b() != Items.field_185160_cR) {
/*    */         
/* 56 */         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, ((Integer)Chams.instance.playerCapeAlpha.getValue()).intValue() / 255.0F);
/* 57 */         this.field_177167_a.func_110776_a(entitylivingbaseIn.func_110303_q());
/* 58 */         GlStateManager.func_179094_E();
/* 59 */         GlStateManager.func_179109_b(0.0F, 0.0F, 0.125F);
/* 60 */         double d0 = entitylivingbaseIn.field_71091_bM + (entitylivingbaseIn.field_71094_bP - entitylivingbaseIn.field_71091_bM) * partialTicks - entitylivingbaseIn.field_70169_q + (entitylivingbaseIn.field_70165_t - entitylivingbaseIn.field_70169_q) * partialTicks;
/* 61 */         double d1 = entitylivingbaseIn.field_71096_bN + (entitylivingbaseIn.field_71095_bQ - entitylivingbaseIn.field_71096_bN) * partialTicks - entitylivingbaseIn.field_70167_r + (entitylivingbaseIn.field_70163_u - entitylivingbaseIn.field_70167_r) * partialTicks;
/* 62 */         double d2 = entitylivingbaseIn.field_71097_bO + (entitylivingbaseIn.field_71085_bR - entitylivingbaseIn.field_71097_bO) * partialTicks - entitylivingbaseIn.field_70166_s + (entitylivingbaseIn.field_70161_v - entitylivingbaseIn.field_70166_s) * partialTicks;
/* 63 */         float f = entitylivingbaseIn.field_70760_ar + (entitylivingbaseIn.field_70761_aq - entitylivingbaseIn.field_70760_ar) * partialTicks;
/* 64 */         double d3 = MathHelper.func_76126_a(f * 0.017453292F);
/* 65 */         double d4 = -MathHelper.func_76134_b(f * 0.017453292F);
/* 66 */         float f1 = (float)d1 * 10.0F;
/* 67 */         f1 = MathHelper.func_76131_a(f1, -6.0F, 32.0F);
/* 68 */         float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
/* 69 */         float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
/*    */         
/* 71 */         if (f2 < 0.0F)
/*    */         {
/* 73 */           f2 = 0.0F;
/*    */         }
/*    */         
/* 76 */         float f4 = entitylivingbaseIn.field_71107_bF + (entitylivingbaseIn.field_71109_bG - entitylivingbaseIn.field_71107_bF) * partialTicks;
/* 77 */         f1 += MathHelper.func_76126_a((entitylivingbaseIn.field_70141_P + (entitylivingbaseIn.field_70140_Q - entitylivingbaseIn.field_70141_P) * partialTicks) * 6.0F) * 32.0F * f4;
/*    */         
/* 79 */         if (entitylivingbaseIn.func_70093_af())
/*    */         {
/* 81 */           f1 += 25.0F;
/*    */         }
/*    */         
/* 84 */         GlStateManager.func_179114_b(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
/* 85 */         GlStateManager.func_179114_b(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
/* 86 */         GlStateManager.func_179114_b(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
/* 87 */         GlStateManager.func_179114_b(180.0F, 0.0F, 1.0F, 0.0F);
/*    */         
/* 89 */         GlStateManager.func_179147_l();
/* 90 */         GL11.glBlendFunc(770, 771);
/*    */         
/* 92 */         this.field_177167_a.func_177087_b().func_178728_c(0.0625F);
/* 93 */         GlStateManager.func_179121_F();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinLayerCape.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */