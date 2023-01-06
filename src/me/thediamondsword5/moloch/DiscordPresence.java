/*    */ package me.thediamondsword5.moloch;
/*    */ 
/*    */ import club.minnced.discord.rpc.DiscordEventHandlers;
/*    */ import club.minnced.discord.rpc.DiscordRPC;
/*    */ import club.minnced.discord.rpc.DiscordRichPresence;
/*    */ import me.thediamondsword5.moloch.module.modules.other.RPC;
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DiscordPresence
/*    */ {
/*    */   private static final DiscordRPC rpc;
/*    */   public static DiscordRichPresence presence;
/*    */   private static Thread thread;
/* 17 */   private static int index = 1; static {
/* 18 */     rpc = DiscordRPC.INSTANCE;
/* 19 */     presence = new DiscordRichPresence();
/*    */   }
/*    */   
/*    */   public static void start() {
/* 23 */     DiscordEventHandlers handlers = new DiscordEventHandlers();
/* 24 */     rpc.Discord_Initialize("998599684924915813", handlers, true, "");
/* 25 */     presence.startTimestamp = System.currentTimeMillis() / 1000L;
/* 26 */     presence.details = ((Minecraft.func_71410_x()).field_71462_r instanceof net.minecraft.client.gui.GuiMainMenu) ? "In the main menu." : ("Playing " + (((Minecraft.func_71410_x()).field_71422_O != null) ? (((Boolean)RPC.INSTANCE.showIP.getValue()).booleanValue() ? ("on " + (Minecraft.func_71410_x()).field_71422_O.field_78845_b + ".") : " multiplayer.") : " singleplayer."));
/* 27 */     presence.state = (String)RPC.INSTANCE.state.getValue();
/* 28 */     presence.largeImageKey = "logo";
/* 29 */     presence.largeImageText = "Moloch.su";
/* 30 */     rpc.Discord_UpdatePresence(presence);
/* 31 */     thread = new Thread(() -> {
/*    */           while (!Thread.currentThread().isInterrupted()) {
/*    */             rpc.Discord_RunCallbacks();
/*    */             presence.details = ((Minecraft.func_71410_x()).field_71462_r instanceof net.minecraft.client.gui.GuiMainMenu) ? "In the main menu." : ("Playing " + (((Minecraft.func_71410_x()).field_71422_O != null) ? (((Boolean)RPC.INSTANCE.showIP.getValue()).booleanValue() ? ("on " + (Minecraft.func_71410_x()).field_71422_O.field_78845_b + ".") : " multiplayer.") : " singleplayer."));
/*    */             presence.state = (String)RPC.INSTANCE.state.getValue();
/*    */             rpc.Discord_UpdatePresence(presence);
/*    */             try {
/*    */               Thread.sleep(2000L);
/* 39 */             } catch (InterruptedException e) {
/*    */               throw new RuntimeException(e);
/*    */             } 
/*    */           } 
/*    */         }"RPC-Callback-Handler");
/* 44 */     thread.start();
/*    */   }
/*    */   
/*    */   public static void stop() {
/* 48 */     if (thread != null && !thread.isInterrupted()) {
/* 49 */       thread.interrupt();
/*    */     }
/* 51 */     rpc.Discord_Shutdown();
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\DiscordPresence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */