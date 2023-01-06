/*    */ package net.spartanb312.base.mixin.mixins.network;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.util.ITickable;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import net.spartanb312.base.core.event.decentralization.EventData;
/*    */ import net.spartanb312.base.event.decentraliized.DecentralizedPacketEvent;
/*    */ import net.spartanb312.base.event.events.network.PacketEvent;
/*    */ import net.spartanb312.base.utils.ItemUtils;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Overwrite;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin(value = {NetworkManager.class}, priority = 312312)
/*    */ public abstract class MixinNetWork
/*    */ {
/*    */   @Shadow
/*    */   public INetHandler field_150744_m;
/*    */   @Shadow
/*    */   public Channel field_150746_k;
/*    */   
/*    */   @Shadow
/*    */   protected abstract void func_150733_h();
/*    */   
/*    */   @Inject(method = {"channelRead0"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void packetReceived(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo) {
/* 41 */     if (ItemUtils.mc.field_71439_g != null && ItemUtils.mc.field_71441_e != null) {
/* 42 */       PacketEvent.Receive event = new PacketEvent.Receive(packet);
/* 43 */       DecentralizedPacketEvent.Receive.instance.post((EventData)event);
/* 44 */       BaseCenter.EVENT_BUS.post(event);
/* 45 */       if (event.isCancelled() && callbackInfo.isCancellable()) {
/* 46 */         callbackInfo.cancel();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"sendPacket(Lnet/minecraft/network/Packet;)V"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void sendPacket(Packet<?> packetIn, CallbackInfo callbackInfo) {
/* 53 */     if (ItemUtils.mc.field_71439_g != null && ItemUtils.mc.field_71441_e != null) {
/* 54 */       PacketEvent.Send event = new PacketEvent.Send(packetIn);
/* 55 */       DecentralizedPacketEvent.Send.instance.post((EventData)event);
/* 56 */       BaseCenter.EVENT_BUS.post(event);
/* 57 */       if (event.isCancelled() && callbackInfo.isCancellable()) {
/* 58 */         callbackInfo.cancel();
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Overwrite
/*    */   public void func_74428_b() {
/*    */     try {
/* 67 */       func_150733_h();
/*    */       
/* 69 */       if (this.field_150744_m instanceof ITickable) {
/* 70 */         ((ITickable)this.field_150744_m).func_73660_a();
/*    */       }
/*    */       
/* 73 */       if (this.field_150746_k != null) {
/* 74 */         this.field_150746_k.flush();
/*    */       }
/*    */     }
/* 77 */     catch (Exception exception) {}
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\network\MixinNetWork.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */