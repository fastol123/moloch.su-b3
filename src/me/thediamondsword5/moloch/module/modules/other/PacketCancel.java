/*    */ package me.thediamondsword5.moloch.module.modules.other;
/*    */ 
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.event.events.network.PacketEvent;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ 
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "PacketCancel", category = Category.OTHER, description = "Cancels certain client sent packets")
/*    */ public class PacketCancel
/*    */   extends Module
/*    */ {
/* 16 */   Setting<Boolean> input = setting("Input", false).des("Cancels CPacketInput");
/* 17 */   Setting<Boolean> position = setting("Position", false).des("Cancels CPacketPlayer.Position");
/* 18 */   Setting<Boolean> rotate = setting("Rotation", false).des("Cancels CPacketPlayer.Rotation");
/* 19 */   Setting<Boolean> positionRotate = setting("PosRotate", false).des("Cancels CPacketPlayer.PositionRotation");
/* 20 */   Setting<Boolean> playerAbilities = setting("PlayerAbilities", false).des("Cancels CPacketPlayerAbilities");
/* 21 */   Setting<Boolean> digging = setting("Digging", false).des("Cancels CPacketPlayerDigging");
/* 22 */   Setting<Boolean> useItem = setting("UseItem", false).des("Cancels CPacketPlayerTryUseItem");
/* 23 */   Setting<Boolean> useItemOnBlock = setting("UseItemOnBlock", false).des("Cancels CPacketPlayerTryUseItemOnBlock");
/* 24 */   Setting<Boolean> useItemOnEntity = setting("UseItemOnEntity", false).des("Cancels CPacketUseEntity");
/* 25 */   Setting<Boolean> moveVehicle = setting("MoveVehicle", false).des("Cancels CPacketVehicleMove");
/* 26 */   Setting<Boolean> steerBoat = setting("SteerBoat", false).des("Cancels CPacketSteerBoat");
/* 27 */   Setting<Boolean> serverRemoveEntities = setting("ServerRemoveEntities", false).des("Cancels SPacketDestroyEntities");
/*    */   
/* 29 */   private int canceledIndex = 0;
/*    */ 
/*    */   
/*    */   public String getModuleInfo() {
/* 33 */     return this.canceledIndex + "";
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 38 */     this.moduleDisableFlag = true;
/* 39 */     this.canceledIndex = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 44 */     if ((event.getPacket() instanceof net.minecraft.network.play.client.CPacketInput && ((Boolean)this.input.getValue()).booleanValue()) || (event
/* 45 */       .getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.Position && ((Boolean)this.position.getValue()).booleanValue()) || (event
/* 46 */       .getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.Rotation && ((Boolean)this.rotate.getValue()).booleanValue()) || (event
/* 47 */       .getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer.PositionRotation && ((Boolean)this.positionRotate.getValue()).booleanValue()) || (event
/* 48 */       .getPacket() instanceof net.minecraft.network.play.client.CPacketPlayerAbilities && ((Boolean)this.playerAbilities.getValue()).booleanValue()) || (event
/* 49 */       .getPacket() instanceof net.minecraft.network.play.client.CPacketPlayerDigging && ((Boolean)this.digging.getValue()).booleanValue()) || (event
/* 50 */       .getPacket() instanceof net.minecraft.network.play.client.CPacketPlayerTryUseItem && ((Boolean)this.useItem.getValue()).booleanValue()) || (event
/* 51 */       .getPacket() instanceof net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock && ((Boolean)this.useItemOnBlock.getValue()).booleanValue()) || (event
/* 52 */       .getPacket() instanceof net.minecraft.network.play.client.CPacketUseEntity && ((Boolean)this.useItemOnEntity.getValue()).booleanValue()) || (event
/* 53 */       .getPacket() instanceof net.minecraft.network.play.client.CPacketVehicleMove && ((Boolean)this.moveVehicle.getValue()).booleanValue()) || (event
/* 54 */       .getPacket() instanceof net.minecraft.network.play.client.CPacketSteerBoat && ((Boolean)this.steerBoat.getValue()).booleanValue())) {
/* 55 */       this.canceledIndex++;
/* 56 */       event.cancel();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 62 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketDestroyEntities && ((Boolean)this.serverRemoveEntities.getValue()).booleanValue()) {
/* 63 */       this.canceledIndex++;
/* 64 */       event.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\other\PacketCancel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */