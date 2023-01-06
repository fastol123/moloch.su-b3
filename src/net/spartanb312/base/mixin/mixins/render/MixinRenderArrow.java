/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ import me.thediamondsword5.moloch.core.common.Color;
/*    */ import me.thediamondsword5.moloch.module.modules.visuals.ESP;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderArrow;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.projectile.EntityArrow;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ 
/*    */ @Mixin({RenderArrow.class})
/*    */ public class MixinRenderArrow<T extends EntityArrow> extends Render<T> {
/*    */   protected MixinRenderArrow(RenderManager renderManager) {
/* 20 */     super(renderManager);
/*    */   }
/*    */   
/*    */   @Redirect(method = {"doRender"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
/*    */   public void doRenderHook(float colorRed, float colorGreen, float colorBlue, float colorAlpha) {
/* 25 */     if (ModuleManager.getModule(ESP.class).isEnabled() && ESP.INSTANCE.renderProjectileFlag) {
/* 26 */       GL11.glColor4f(((Color)ESP.INSTANCE.espColorProjectiles.getValue()).getColorColor().getRed() / 255.0F, ((Color)ESP.INSTANCE.espColorProjectiles.getValue()).getColorColor().getGreen() / 255.0F, ((Color)ESP.INSTANCE.espColorProjectiles.getValue()).getColorColor().getBlue() / 255.0F, ((Color)ESP.INSTANCE.espColorProjectiles.getValue()).getAlpha() / 255.0F);
/*    */     }
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected ResourceLocation getEntityTexture(T entity) {
/* 32 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinRenderArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */