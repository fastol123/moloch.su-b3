/*    */ package net.spartanb312.base.mixin.mixins.entity;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.player.TravelEvent;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.MoverType;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.world.World;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({EntityPlayer.class})
/*    */ public abstract class MixinEntityPlayer
/*    */   extends EntityLivingBase {
/*    */   public MixinEntityPlayer(World worldIn) {
/* 18 */     super(worldIn);
/*    */   }
/*    */   
/*    */   @Inject(method = {"travel"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void travelHook(float strafe, float vertical, float forward, CallbackInfo ci) {
/* 23 */     TravelEvent event = new TravelEvent();
/* 24 */     BaseCenter.EVENT_BUS.post(event);
/* 25 */     if (event.isCancelled()) {
/* 26 */       func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/* 27 */       ci.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\entity\MixinEntityPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */