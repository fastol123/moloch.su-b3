/*    */ package me.thediamondsword5.moloch.module.modules.other;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.event.events.network.PacketEvent;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ import net.spartanb312.base.utils.Timer;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "PingBypast", category = Category.OTHER, description = "Ping bypass??????!11!!1!!!1!! awwwwwwwa")
/*    */ public class PingSpoof
/*    */   extends Module
/*    */ {
/* 22 */   Setting<Boolean> cancelTransactionPackets = setting("CancelTransactionPackets", false).des("Cancels CPacketConfirmTransaction because that bypasses some server's patches or smt");
/* 23 */   Setting<Integer> ping = setting("Ping", 666, 0, 2000).des("Ping to spoof in milliseconds");
/*    */   
/*    */   private boolean flag = true;
/* 26 */   private final Timer timer = new Timer();
/* 27 */   private final List<Packet<?>> packetList = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 31 */     if (!mc.func_71356_B() && this.timer.passed(((Integer)this.ping.getValue()).intValue())) {
/* 32 */       this.flag = false;
/* 33 */       this.packetList.stream()
/* 34 */         .filter(Objects::nonNull)
/* 35 */         .forEach(packet -> mc.field_71439_g.field_71174_a.func_147297_a(packet));
/* 36 */       this.flag = true;
/* 37 */       this.packetList.clear();
/* 38 */       this.timer.reset();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPacketSend(PacketEvent.Send event) {
/* 44 */     if ((event.getPacket() instanceof net.minecraft.network.play.client.CPacketKeepAlive || (((Boolean)this.cancelTransactionPackets.getValue()).booleanValue() && event.getPacket() instanceof net.minecraft.network.play.client.CPacketConfirmTransaction)) && this.flag && mc.field_71439_g != null && !mc.func_71356_B()) {
/* 45 */       this.packetList.add(event.getPacket());
/* 46 */       event.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\other\PingSpoof.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */