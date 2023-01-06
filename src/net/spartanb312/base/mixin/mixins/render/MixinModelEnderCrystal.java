/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ 
/*    */ import me.thediamondsword5.moloch.module.modules.visuals.Chams;
/*    */ import net.minecraft.client.model.ModelEnderCrystal;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({ModelEnderCrystal.class})
/*    */ public abstract class MixinModelEnderCrystal {
/*    */   @Shadow
/*    */   private ModelRenderer field_78229_c;
/*    */   @Shadow
/*    */   private ModelRenderer field_78228_b;
/*    */   @Shadow
/*    */   private ModelRenderer field_78230_a;
/*    */   
/*    */   @Inject(method = {"render"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderModelHook(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo ci) {
/* 26 */     if (ModuleManager.getModule(Chams.class).isEnabled() && ((Boolean)Chams.instance.crystals.getValue()).booleanValue() && ((Boolean)Chams.instance.crystalOneGlass.getValue()).booleanValue()) {
/* 27 */       GlStateManager.func_179094_E();
/* 28 */       GlStateManager.func_179152_a(2.0F, 2.0F, 2.0F);
/* 29 */       GlStateManager.func_179109_b(0.0F, -0.5F, 0.0F);
/* 30 */       if (this.field_78229_c != null) {
/* 31 */         this.field_78229_c.func_78785_a(scale);
/*    */       }
/*    */       
/* 34 */       GlStateManager.func_179114_b(limbSwingAmount, 0.0F, 1.0F, 0.0F);
/* 35 */       GlStateManager.func_179109_b(0.0F, 0.8F + ageInTicks, 0.0F);
/* 36 */       GlStateManager.func_179114_b(60.0F, 0.7071F, 0.0F, 0.7071F);
/* 37 */       this.field_78228_b.func_78785_a(scale);
/* 38 */       GlStateManager.func_179152_a(0.875F, 0.875F, 0.875F);
/* 39 */       GlStateManager.func_179114_b(60.0F, 0.7071F, 0.0F, 0.7071F);
/* 40 */       GlStateManager.func_179114_b(limbSwingAmount, 0.0F, 1.0F, 0.0F);
/* 41 */       GlStateManager.func_179152_a(0.875F, 0.875F, 0.875F);
/* 42 */       GlStateManager.func_179114_b(60.0F, 0.7071F, 0.0F, 0.7071F);
/* 43 */       GlStateManager.func_179114_b(limbSwingAmount, 0.0F, 1.0F, 0.0F);
/* 44 */       this.field_78230_a.func_78785_a(scale);
/* 45 */       GlStateManager.func_179121_F();
/*    */       
/* 47 */       ci.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinModelEnderCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */