/*     */ package net.spartanb312.base.client;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.spartanb312.base.BaseCenter;
/*     */ import net.spartanb312.base.core.concurrent.ConcurrentTaskManager;
/*     */ import net.spartanb312.base.core.concurrent.blocking.BlockingContent;
/*     */ import net.spartanb312.base.core.event.Listener;
/*     */ import net.spartanb312.base.core.event.decentralization.EventData;
/*     */ import net.spartanb312.base.core.event.decentralization.ListenableImpl;
/*     */ import net.spartanb312.base.event.decentraliized.DecentralizedClientTickEvent;
/*     */ import net.spartanb312.base.event.decentraliized.DecentralizedPacketEvent;
/*     */ import net.spartanb312.base.event.decentraliized.DecentralizedRenderTickEvent;
/*     */ import net.spartanb312.base.event.decentraliized.DecentralizedRenderWorldEvent;
/*     */ import net.spartanb312.base.event.events.client.InputUpdateEvent;
/*     */ import net.spartanb312.base.event.events.client.SettingUpdateEvent;
/*     */ import net.spartanb312.base.event.events.network.PacketEvent;
/*     */ import net.spartanb312.base.event.events.render.RenderOverlayEvent;
/*     */ import net.spartanb312.base.event.events.render.RenderWorldEvent;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.notification.NotificationManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModuleBus
/*     */   extends ListenableImpl
/*     */ {
/*     */   private final List<Module> modules;
/*     */   
/*     */   public ModuleBus() {
/*  35 */     this.modules = new CopyOnWriteArrayList<>(); BaseCenter.EVENT_BUS.register(this); listener(DecentralizedClientTickEvent.class, it -> onTick()); listener(DecentralizedRenderTickEvent.class, this::onRenderTick); listener(DecentralizedRenderWorldEvent.class, this::onRenderWorld);
/*     */     listener(DecentralizedPacketEvent.Send.class, this::onPacketSend);
/*     */     listener(DecentralizedPacketEvent.Receive.class, this::onPacketReceive);
/*  38 */     subscribe(); } public synchronized void register(Module module) { this.modules.add(module);
/*  39 */     BaseCenter.EVENT_BUS.register(module); }
/*     */ 
/*     */   
/*     */   public void unregister(Module module) {
/*  43 */     this.modules.remove(module);
/*  44 */     BaseCenter.EVENT_BUS.unregister(module);
/*     */   }
/*     */   
/*     */   public boolean isRegistered(Module module) {
/*  48 */     return this.modules.contains(module);
/*     */   }
/*     */   
/*     */   public List<Module> getModules() {
/*  52 */     return this.modules;
/*     */   }
/*     */   
/*     */   @Listener(priority = 2147483647)
/*     */   public void onKey(InputUpdateEvent event) {
/*  57 */     this.modules.forEach(mod -> mod.onInputUpdate(event));
/*     */   }
/*     */   
/*     */   public void onTick() {
/*  61 */     ConcurrentTaskManager.runBlocking(it -> this.modules.forEach(()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Listener(priority = 2147483647)
/*     */   public void onRenderTick(RenderOverlayEvent event) {
/*  84 */     ConcurrentTaskManager.runBlocking(it -> this.modules.forEach(()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Listener(priority = 2147483647)
/*     */   public void onRenderWorld(RenderWorldEvent event) {
/* 113 */     WorldRenderPatcher.INSTANCE.patch(event);
/*     */   }
/*     */   
/*     */   @Listener(priority = 2147483647)
/*     */   public void onPacketSend(PacketEvent.Send event) {
/* 118 */     this.modules.forEach(module -> {
/*     */           try {
/*     */             module.onPacketSend(event);
/* 121 */           } catch (Exception exception) {
/*     */             NotificationManager.fatal("Error while running PacketSend!");
/*     */             exception.printStackTrace();
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   @Listener(priority = 2147483647)
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 130 */     this.modules.forEach(module -> {
/*     */           try {
/*     */             module.onPacketReceive(event);
/* 133 */           } catch (Exception exception) {
/*     */             NotificationManager.fatal("Error while running PacketReceive!");
/*     */             exception.printStackTrace();
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   @Listener(priority = 2147483647)
/*     */   public void onSettingChange(SettingUpdateEvent event) {
/* 142 */     this.modules.forEach(it -> {
/*     */           try {
/*     */             it.onSettingChange(event.getSetting());
/* 145 */           } catch (Exception exception) {
/*     */             NotificationManager.fatal("Error while running onSettingChange!");
/*     */             exception.printStackTrace();
/*     */           } 
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\client\ModuleBus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */