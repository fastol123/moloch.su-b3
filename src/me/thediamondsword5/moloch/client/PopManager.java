/*    */ package me.thediamondsword5.moloch.client;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import me.thediamondsword5.moloch.event.events.entity.DeathEvent;
/*    */ import me.thediamondsword5.moloch.module.modules.client.ChatSettings;
/*    */ import me.thediamondsword5.moloch.module.modules.visuals.Nametags;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.network.FMLNetworkEvent;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import net.spartanb312.base.core.config.ListenableContainer;
/*    */ import net.spartanb312.base.core.event.Listener;
/*    */ import net.spartanb312.base.event.events.network.PacketEvent;
/*    */ import net.spartanb312.base.utils.ChatUtil;
/*    */ import net.spartanb312.base.utils.ItemUtils;
/*    */ 
/*    */ public class PopManager
/*    */   extends ListenableContainer
/*    */ {
/* 23 */   public static final HashMap<Entity, Integer> popMap = new HashMap<>();
/* 24 */   public static final HashMap<Entity, Integer> deathPopMap = new HashMap<>();
/*    */   
/*    */   public static void init() {
/* 27 */     BaseCenter.EVENT_BUS.register(new PopManager());
/* 28 */     MinecraftForge.EVENT_BUS.register(new PopManager());
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
/* 33 */     popMap.clear();
/* 34 */     deathPopMap.clear();
/*    */   }
/*    */   
/*    */   @Listener
/*    */   public void onDeath(DeathEvent event) {
/* 39 */     if (event.entity instanceof net.minecraft.entity.player.EntityPlayer && (Nametags.INSTANCE.popCount.getValue() != Nametags.TextMode.None || ((Boolean)ChatSettings.INSTANCE.popNotifications.getValue()).booleanValue())) {
/* 40 */       if (((Boolean)ChatSettings.INSTANCE.popNotifications.getValue()).booleanValue()) {
/* 41 */         if (((Boolean)ChatSettings.INSTANCE.popNotificationsMarked.getValue()).booleanValue()) {
/* 42 */           ChatUtil.printChatMessage(ChatUtil.SECTIONSIGN + ChatSettings.INSTANCE.colorString(ChatSettings.INSTANCE.popNotificationsDeathColor) + ChatUtil.effectString(ChatSettings.INSTANCE.popNotificationsDeathEffect) + event.entity.func_70005_c_() + " just fucking died after popping " + ((popMap.get(event.entity) == null) ? 0 : ((Integer)popMap.get(event.entity)).intValue()) + " totems!");
/*    */         } else {
/*    */           
/* 45 */           ChatUtil.printRawChatMessage(ChatUtil.SECTIONSIGN + ChatSettings.INSTANCE.colorString(ChatSettings.INSTANCE.popNotificationsDeathColor) + ChatUtil.effectString(ChatSettings.INSTANCE.popNotificationsDeathEffect) + event.entity.func_70005_c_() + " just fucking died after popping " + ((popMap.get(event.entity) == null) ? 0 : ((Integer)popMap.get(event.entity)).intValue()) + " totems!");
/*    */         } 
/*    */       }
/*    */       
/* 49 */       deathPopMap.put(event.entity, Integer.valueOf((popMap.get(event.entity) == null) ? 0 : ((Integer)popMap.get(event.entity)).intValue()));
/* 50 */       popMap.put(event.entity, Integer.valueOf(0));
/*    */     } 
/*    */   }
/*    */   
/*    */   @Listener
/*    */   public void onPacketReceive(PacketEvent.Receive event) {
/* 56 */     if ((Nametags.INSTANCE.popCount.getValue() != Nametags.TextMode.None || ((Boolean)ChatSettings.INSTANCE.popNotifications.getValue()).booleanValue()) && event
/* 57 */       .getPacket() instanceof SPacketEntityStatus && ((SPacketEntityStatus)event
/* 58 */       .getPacket()).func_149160_c() == 35) {
/* 59 */       Entity entity = ((SPacketEntityStatus)event.getPacket()).func_149161_a((World)ItemUtils.mc.field_71441_e);
/*    */       
/* 61 */       if (!(entity instanceof net.minecraft.entity.player.EntityPlayer))
/*    */         return; 
/* 63 */       Integer currentPops = popMap.get(entity);
/* 64 */       popMap.put(entity, Integer.valueOf((currentPops == null) ? 1 : (currentPops.intValue() + 1)));
/*    */       
/* 66 */       if (((Boolean)ChatSettings.INSTANCE.popNotifications.getValue()).booleanValue())
/* 67 */         if (((Boolean)ChatSettings.INSTANCE.popNotificationsMarked.getValue()).booleanValue()) {
/* 68 */           ChatUtil.printChatMessage(ChatUtil.SECTIONSIGN + ChatSettings.INSTANCE.colorString(ChatSettings.INSTANCE.popNotificationsColor) + ChatUtil.effectString(ChatSettings.INSTANCE.popNotificationsEffect) + entity.func_70005_c_() + " popped " + ChatUtil.SECTIONSIGN + ChatSettings.INSTANCE.colorString(ChatSettings.INSTANCE.popNotificationsPopNumColor) + popMap.get(entity) + ChatUtil.SECTIONSIGN + ChatSettings.INSTANCE.colorString(ChatSettings.INSTANCE.popNotificationsColor) + " time" + ((((Integer)popMap.get(entity)).intValue() > 1) ? "s" : "") + "!");
/*    */         } else {
/*    */           
/* 71 */           ChatUtil.printRawChatMessage(ChatUtil.SECTIONSIGN + ChatSettings.INSTANCE.colorString(ChatSettings.INSTANCE.popNotificationsColor) + ChatUtil.effectString(ChatSettings.INSTANCE.popNotificationsEffect) + entity.func_70005_c_() + " popped " + ChatUtil.SECTIONSIGN + ChatSettings.INSTANCE.colorString(ChatSettings.INSTANCE.popNotificationsPopNumColor) + popMap.get(entity) + ChatUtil.SECTIONSIGN + ChatSettings.INSTANCE.colorString(ChatSettings.INSTANCE.popNotificationsColor) + " time" + ((((Integer)popMap.get(entity)).intValue() > 1) ? "s" : "") + "!");
/*    */         }  
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\client\PopManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */