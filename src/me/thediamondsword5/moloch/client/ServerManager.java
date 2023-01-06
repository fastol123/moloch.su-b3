/*    */ package me.thediamondsword5.moloch.client;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import net.spartanb312.base.core.config.ListenableContainer;
/*    */ import net.spartanb312.base.core.event.Listener;
/*    */ import net.spartanb312.base.event.events.network.PacketEvent;
/*    */ 
/*    */ 
/*    */ public class ServerManager
/*    */   extends ListenableContainer
/*    */ {
/*    */   private static long prevTime;
/*    */   private static int currentTick;
/* 16 */   private static final float[] ticks = new float[20];
/*    */   
/*    */   public static void init() {
/* 19 */     prevTime = -1L;
/* 20 */     Arrays.fill(ticks, 0.0F);
/* 21 */     BaseCenter.EVENT_BUS.register(new ServerManager());
/*    */   }
/*    */   
/*    */   @Listener
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 26 */     if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketTimeUpdate) {
/* 27 */       if (prevTime != -1L) {
/* 28 */         ticks[currentTick % ticks.length] = MathHelper.func_76131_a(20.0F / (float)(System.currentTimeMillis() - prevTime) / 1000.0F, 0.0F, 20.0F);
/* 29 */         currentTick = (int)(currentTick + 1.0F);
/*    */       } 
/*    */       
/* 32 */       prevTime = System.currentTimeMillis();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static float getTPS() {
/* 37 */     int numTicks = 0;
/* 38 */     float tickRate = 0.0F;
/*    */     
/* 40 */     for (float tick : ticks) {
/* 41 */       if (tick > 0.0F) {
/* 42 */         tickRate += tick;
/* 43 */         numTicks++;
/*    */       } 
/*    */     } 
/*    */     
/* 47 */     return MathHelper.func_76131_a(tickRate / numTicks, 0.0F, 20.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\client\ServerManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */