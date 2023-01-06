/*    */ package net.spartanb312.base.mixin.mixins.world;
/*    */ 
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.EnumSkyBlock;
/*    */ import net.minecraft.world.World;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.module.modules.movement.Velocity;
/*    */ import net.spartanb312.base.module.modules.visuals.NoRender;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({World.class})
/*    */ public class MixinWorld
/*    */ {
/*    */   @Inject(method = {"handleMaterialAcceleration"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isPushedByWater()Z")}, cancellable = true)
/*    */   public void pushedByWaterHook(AxisAlignedBB bb, Material materialIn, Entity entityIn, CallbackInfoReturnable<Boolean> ci) {
/* 28 */     if (ModuleManager.getModule(Velocity.class).isEnabled() && ((Boolean)Velocity.instance.liquid.getValue()).booleanValue())
/* 29 */       ci.setReturnValue(Boolean.valueOf(false)); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"checkLightFor"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void checkLightForHook(EnumSkyBlock lightType, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
/* 34 */     if (ModuleManager.getModule(NoRender.class).isEnabled() && ((Boolean)NoRender.INSTANCE.skyLightUpdate.getValue()).booleanValue())
/* 35 */       cir.setReturnValue(Boolean.valueOf(false)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\world\MixinWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */