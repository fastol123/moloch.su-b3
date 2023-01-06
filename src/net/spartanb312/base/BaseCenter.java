/*    */ package net.spartanb312.base;
/*    */ 
/*    */ import me.thediamondsword5.moloch.client.EnemyManager;
/*    */ import me.thediamondsword5.moloch.client.PopManager;
/*    */ import me.thediamondsword5.moloch.client.ServerManager;
/*    */ import net.minecraftforge.fml.common.Mod;
/*    */ import net.spartanb312.base.client.CommandManager;
/*    */ import net.spartanb312.base.client.ConfigManager;
/*    */ import net.spartanb312.base.client.FontManager;
/*    */ import net.spartanb312.base.client.FriendManager;
/*    */ import net.spartanb312.base.client.GUIManager;
/*    */ import net.spartanb312.base.client.ModuleBus;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.core.concurrent.ConcurrentTaskManager;
/*    */ import net.spartanb312.base.core.concurrent.blocking.BlockingContent;
/*    */ import net.spartanb312.base.core.event.EventManager;
/*    */ import net.spartanb312.base.core.event.Listener;
/*    */ import net.spartanb312.base.event.events.client.InitializationEvent;
/*    */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*    */ import net.spartanb312.base.utils.ColorUtil;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.lwjgl.opengl.Display;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mod(modid = "moloch", name = "moloch.su", version = "b3")
/*    */ public class BaseCenter
/*    */ {
/* 30 */   public static ColorUtil colorUtil = new ColorUtil();
/* 31 */   public static FontManager fontManager = new FontManager();
/*    */   
/*    */   public static final String AUTHOR = "TheDiamondSword5 && popbob && B_312";
/*    */   
/*    */   public static final String GITHUB = "base -> https://github.com/SpartanB312/Cursa";
/*    */   public static final String VERSION = "b3";
/* 37 */   public static String CHAT_SUFFIX = " ᵐᵒˡᵒᶜʰ.ˢᵘ";
/*    */   
/* 39 */   public static final Logger log = LogManager.getLogger("moloch.su");
/*    */   private static Thread mainThread;
/*    */   
/*    */   @Listener(priority = 2147483647)
/*    */   public void preInitialize(InitializationEvent.PreInitialize event) {
/* 44 */     mainThread = Thread.currentThread();
/*    */   }
/*    */   
/*    */   @Listener(priority = 2147483647)
/*    */   public void initialize(InitializationEvent.Initialize event) {
/* 49 */     long tookTime = ConcurrentTaskManager.runTiming(() -> {
/*    */           Display.setTitle("moloch.su b3");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/*    */           ConcurrentTaskManager.runBlocking(());
/*    */         });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 84 */     log.info("Launched in " + tookTime);
/*    */   }
/*    */   
/*    */   @Listener(priority = 2147483647)
/*    */   public void postInitialize(InitializationEvent.PostInitialize event) {
/* 89 */     ClickGUI.instance.disable();
/*    */   }
/*    */   
/*    */   public static boolean isMainThread(Thread thread) {
/* 93 */     return (thread == mainThread);
/*    */   }
/*    */   
/* 96 */   public static EventManager EVENT_BUS = new EventManager();
/* 97 */   public static ModuleBus MODULE_BUS = new ModuleBus();
/*    */   
/* 99 */   public static final BaseCenter instance = new BaseCenter();
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\BaseCenter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */