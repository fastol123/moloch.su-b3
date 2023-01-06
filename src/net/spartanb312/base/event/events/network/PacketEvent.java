/*    */ package net.spartanb312.base.event.events.network;
/*    */ 
/*    */ import net.minecraft.network.Packet;
/*    */ import net.spartanb312.base.core.event.decentralization.EventData;
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public class PacketEvent
/*    */   extends EventCenter implements EventData {
/*    */   public final Packet<?> packet;
/*    */   
/*    */   public PacketEvent(Packet<?> packet) {
/* 12 */     this.packet = packet;
/*    */   }
/*    */   
/*    */   public static class Receive extends PacketEvent {
/*    */     public Receive(Packet<?> packet) {
/* 17 */       super(packet);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Send extends PacketEvent {
/*    */     public Send(Packet<?> packet) {
/* 23 */       super(packet);
/*    */     }
/*    */   }
/*    */   
/*    */   public Packet<?> getPacket() {
/* 28 */     return this.packet;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\event\events\network\PacketEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */