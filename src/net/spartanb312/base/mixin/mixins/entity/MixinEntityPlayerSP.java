/*    */ package net.spartanb312.base.mixin.mixins.entity;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import me.thediamondsword5.moloch.event.events.player.OnUpdateWalkingPlayerEvent;
/*    */ import me.thediamondsword5.moloch.event.events.player.PlayerMoveEvent;
/*    */ import me.thediamondsword5.moloch.event.events.player.PlayerUpdateMoveEvent;
/*    */ import me.thediamondsword5.moloch.module.modules.other.Freecam;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.entity.MoverType;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.MovementInput;
/*    */ import net.minecraft.world.World;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.event.events.client.ChatEvent;
/*    */ import net.spartanb312.base.module.modules.movement.Velocity;
/*    */ import net.spartanb312.base.utils.ItemUtils;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({EntityPlayerSP.class})
/*    */ public abstract class MixinEntityPlayerSP extends EntityPlayer {
/*    */   @Shadow
/*    */   public MovementInput field_71158_b;
/*    */   
/*    */   public MixinEntityPlayerSP(World worldIn, GameProfile gameProfileIn) {
/* 31 */     super(worldIn, gameProfileIn);
/*    */   }
/*    */   
/*    */   @Shadow
/*    */   protected abstract void func_189810_i(float paramFloat1, float paramFloat2);
/*    */   
/*    */   @Inject(method = {"sendChatMessage"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void sendChatPacket(String message, CallbackInfo ci) {
/* 39 */     ChatEvent event = new ChatEvent(message);
/* 40 */     BaseCenter.EVENT_BUS.post(event);
/* 41 */     if (event.isCancelled()) ci.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"pushOutOfBlocks"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void pushHook(double x, double y, double z, CallbackInfoReturnable<Boolean> ci) {
/* 46 */     if (ModuleManager.getModule(Velocity.class).isEnabled() && ((Boolean)Velocity.instance.pushing.getValue()).booleanValue())
/* 47 */       ci.setReturnValue(Boolean.valueOf(false)); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"onLivingUpdate"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/util/MovementInput;updatePlayerMoveState()V")}, cancellable = true)
/*    */   private void onMoveStateUpdate(CallbackInfo ci) {
/* 52 */     PlayerUpdateMoveEvent event = new PlayerUpdateMoveEvent(this.field_71158_b);
/* 53 */     BaseCenter.EVENT_BUS.post(event);
/* 54 */     if (event.isCancelled()) ci.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"isCurrentViewEntity"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void isCurrentViewEntityHook(CallbackInfoReturnable<Boolean> cir) {
/* 59 */     if (ModuleManager.getModule(Freecam.class).isEnabled() && Freecam.INSTANCE.camera != null)
/* 60 */       cir.setReturnValue(Boolean.valueOf((ItemUtils.mc.func_175606_aa() == Freecam.INSTANCE.camera))); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"onUpdateWalkingPlayer"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onUpdateWalkingPlayerHook(CallbackInfo ci) {
/* 65 */     OnUpdateWalkingPlayerEvent event = new OnUpdateWalkingPlayerEvent(this.field_70177_z, this.field_70125_A);
/* 66 */     BaseCenter.EVENT_BUS.post(event);
/* 67 */     if (event.isCancelled()) ci.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"move"}, at = {@At("TAIL")}, cancellable = true)
/*    */   public void moveHook(MoverType type, double x, double y, double z, CallbackInfo ci) {
/* 72 */     if (type == MoverType.SELF && ItemUtils.mc.field_71439_g != null) {
/* 73 */       PlayerMoveEvent event = new PlayerMoveEvent((EntityPlayer)ItemUtils.mc.field_71439_g);
/* 74 */       BaseCenter.EVENT_BUS.post(event);
/*    */       
/* 76 */       if (event.isCancelled()) {
/* 77 */         double prevX = this.field_70165_t;
/* 78 */         double prevZ = this.field_70161_v;
/*    */         
/* 80 */         func_70091_d(type, event.motionX, event.motionY, event.motionZ);
/* 81 */         func_189810_i((float)(this.field_70165_t - prevX), (float)(this.field_70161_v - prevZ));
/* 82 */         ci.cancel();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\entity\MixinEntityPlayerSP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */