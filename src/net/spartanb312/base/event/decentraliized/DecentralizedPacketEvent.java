/*    */ package net.spartanb312.base.event.decentraliized;
/*    */ 
/*    */ import net.spartanb312.base.core.event.decentralization.DecentralizedEvent;
/*    */ import net.spartanb312.base.event.events.network.PacketEvent;
/*    */ 
/*    */ public class DecentralizedPacketEvent {
/*    */   public static class Send extends DecentralizedEvent<PacketEvent.Send> {
/*  8 */     public static Send instance = new Send();
/*    */   }
/*    */   
/*    */   public static class Receive extends DecentralizedEvent<PacketEvent.Receive> {
/* 12 */     public static Receive instance = new Receive();
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\event\decentraliized\DecentralizedPacketEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */