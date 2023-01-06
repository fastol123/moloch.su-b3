/*    */ package net.spartanb312.base.mixin.mixins.entity;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.entity.TurnEvent;
/*    */ import me.thediamondsword5.moloch.event.events.player.GroundedStepEvent;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.MoverType;
/*    */ import net.minecraft.network.datasync.EntityDataManager;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.module.modules.movement.Velocity;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ @Mixin({Entity.class})
/*    */ public abstract class MixinEntity
/*    */ {
/*    */   @Shadow
/*    */   public double field_70159_w;
/*    */   @Shadow
/*    */   public double field_70181_x;
/*    */   
/*    */   @Inject(method = {"addVelocity"}, at = {@At("RETURN")})
/*    */   public void entityPushHook(double x, double y, double z, CallbackInfo ci) {
/* 28 */     if (ModuleManager.getModule(Velocity.class).isEnabled() && ((Boolean)Velocity.instance.pushing.getValue()).booleanValue()) {
/* 29 */       this.field_70159_w -= x;
/* 30 */       this.field_70181_x -= y;
/* 31 */       this.field_70179_y -= z;
/*    */     }  } @Shadow
/*    */   public double field_70179_y; @Shadow
/*    */   public float field_70138_W; @Shadow
/*    */   public EntityDataManager field_70180_af; @Inject(method = {"turn"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void turn(float yaw, float pitch, CallbackInfo ci) {
/* 37 */     Entity entityTurning = (Entity)this;
/* 38 */     TurnEvent event = new TurnEvent(entityTurning, yaw, pitch);
/* 39 */     BaseCenter.EVENT_BUS.post(event);
/*    */     
/* 41 */     if (event.isCancelled())
/* 42 */       ci.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"move"}, at = {@At(value = "FIELD", target = "net/minecraft/entity/Entity.onGround:Z", ordinal = 1)})
/*    */   private void moveHook(MoverType type, double x, double y, double z, CallbackInfo ci) {
/* 47 */     if (type == MoverType.SELF) {
/* 48 */       GroundedStepEvent event = new GroundedStepEvent(this.field_70138_W);
/* 49 */       BaseCenter.EVENT_BUS.post(event);
/* 50 */       this.field_70138_W = event.height;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\entity\MixinEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */