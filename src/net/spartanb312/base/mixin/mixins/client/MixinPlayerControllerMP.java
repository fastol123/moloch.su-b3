/*    */ package net.spartanb312.base.mixin.mixins.client;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.player.BlockBreakDelayEvent;
/*    */ import me.thediamondsword5.moloch.event.events.player.DamageBlockEvent;
/*    */ import me.thediamondsword5.moloch.event.events.player.MultiTaskEvent;
/*    */ import me.thediamondsword5.moloch.event.events.player.PlayerAttackEvent;
/*    */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.GameType;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({PlayerControllerMP.class})
/*    */ public class MixinPlayerControllerMP
/*    */ {
/*    */   @Shadow
/*    */   public boolean field_78778_j;
/*    */   @Shadow
/*    */   public GameType field_78779_k;
/*    */   
/*    */   @Inject(method = {"attackEntity"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void attackEntityHook(EntityPlayer playerIn, Entity targetEntity, CallbackInfo ci) {
/* 34 */     if (targetEntity != null) {
/* 35 */       PlayerAttackEvent event = new PlayerAttackEvent(targetEntity);
/* 36 */       BaseCenter.EVENT_BUS.post(event);
/*    */       
/* 38 */       if (event.isCancelled())
/* 39 */         ci.cancel(); 
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"onPlayerDamageBlock"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void onPlayerDamageBlockHook(BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> cir) {
/* 45 */     DamageBlockEvent event = new DamageBlockEvent(pos, side);
/* 46 */     BaseCenter.EVENT_BUS.post(event);
/*    */     
/* 48 */     if (event.isCancelled())
/* 49 */       cir.setReturnValue(Boolean.valueOf(false)); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"getIsHittingBlock"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void getIsHittingBlockHook(CallbackInfoReturnable<Boolean> cir) {
/* 54 */     MultiTaskEvent event = new MultiTaskEvent();
/* 55 */     BaseCenter.EVENT_BUS.post(event);
/* 56 */     if (event.isCancelled()) {
/* 57 */       cir.setReturnValue(Boolean.valueOf(false));
/*    */     } else {
/* 59 */       cir.setReturnValue(Boolean.valueOf(this.field_78778_j));
/*    */     } 
/*    */   }
/*    */   @Inject(method = {"clickBlock"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;blockHitDelay:I", opcode = 181, shift = At.Shift.AFTER)})
/*    */   public void clickBlockHook(BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> cir) {
/* 64 */     BlockBreakDelayEvent event = new BlockBreakDelayEvent();
/* 65 */     BaseCenter.EVENT_BUS.post(event);
/*    */   }
/*    */   
/*    */   @Inject(method = {"onPlayerDamageBlock"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;blockHitDelay:I", opcode = 181, ordinal = 1, shift = At.Shift.AFTER)})
/*    */   public void onPlayerDamageBlockHook1(BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> cir) {
/* 70 */     BlockBreakDelayEvent event = new BlockBreakDelayEvent();
/* 71 */     BaseCenter.EVENT_BUS.post(event);
/*    */   }
/*    */   
/*    */   @Inject(method = {"onPlayerDamageBlock"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;blockHitDelay:I", opcode = 181, ordinal = 2, shift = At.Shift.AFTER)})
/*    */   public void onPlayerDamageBlockHook2(BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> cir) {
/* 76 */     BlockBreakDelayEvent event = new BlockBreakDelayEvent();
/* 77 */     BaseCenter.EVENT_BUS.post(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\client\MixinPlayerControllerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */