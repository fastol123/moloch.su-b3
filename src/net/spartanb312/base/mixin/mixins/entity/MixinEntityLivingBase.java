/*    */ package net.spartanb312.base.mixin.mixins.entity;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.entity.DeathEvent;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.network.datasync.DataParameter;
/*    */ import net.minecraft.world.World;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import net.spartanb312.base.utils.RotationUtil;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({EntityLivingBase.class})
/*    */ public abstract class MixinEntityLivingBase
/*    */   extends Entity
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   private static DataParameter<Float> field_184632_c;
/*    */   
/*    */   public MixinEntityLivingBase(World worldIn) {
/* 27 */     super(worldIn);
/*    */   }
/*    */   
/*    */   @Inject(method = {"notifyDataManagerChange"}, at = {@At("RETURN")})
/*    */   public void notifyDataManagerChangeHook(DataParameter<?> key, CallbackInfo ci) {
/* 32 */     if (key.equals(field_184632_c) && ((Float)this.field_70180_af.func_187225_a(field_184632_c)).floatValue() <= 0.0D && RotationUtil.mc.field_71441_e != null && RotationUtil.mc.field_71441_e.field_72995_K) {
/* 33 */       DeathEvent event = new DeathEvent(this);
/* 34 */       BaseCenter.EVENT_BUS.post(event);
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"attemptTeleport"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;setPositionAndUpdate(DDD)V")})
/*    */   public void attemptTeleportHook(double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {}
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\entity\MixinEntityLivingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */