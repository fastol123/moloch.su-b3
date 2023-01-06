/*     */ package net.spartanb312.base.client;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import me.thediamondsword5.moloch.hud.huds.ArmorDisplay;
/*     */ import me.thediamondsword5.moloch.hud.huds.WaterMark;
/*     */ import me.thediamondsword5.moloch.module.modules.client.ChatSettings;
/*     */ import me.thediamondsword5.moloch.module.modules.client.CustomFont;
/*     */ import me.thediamondsword5.moloch.module.modules.client.MoreClickGUI;
/*     */ import me.thediamondsword5.moloch.module.modules.client.Particles;
/*     */ import me.thediamondsword5.moloch.module.modules.combat.Criticals;
/*     */ import me.thediamondsword5.moloch.module.modules.combat.Offhand;
/*     */ import me.thediamondsword5.moloch.module.modules.combat.SelfBlock;
/*     */ import me.thediamondsword5.moloch.module.modules.movement.GUIMove;
/*     */ import me.thediamondsword5.moloch.module.modules.movement.NoHunger;
/*     */ import me.thediamondsword5.moloch.module.modules.other.GapSwapBind;
/*     */ import me.thediamondsword5.moloch.module.modules.other.NameSpoof;
/*     */ import me.thediamondsword5.moloch.module.modules.other.PacketCancel;
/*     */ import me.thediamondsword5.moloch.module.modules.visuals.CameraClip;
/*     */ import net.spartanb312.base.BaseCenter;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.common.KeyBind;
/*     */ import net.spartanb312.base.core.concurrent.ConcurrentTaskManager;
/*     */ import net.spartanb312.base.core.concurrent.blocking.BlockingContent;
/*     */ import net.spartanb312.base.core.event.Listener;
/*     */ import net.spartanb312.base.event.events.client.KeyEvent;
/*     */ import net.spartanb312.base.event.events.render.RenderOverlayEvent;
/*     */ import net.spartanb312.base.gui.renderers.HUDEditorRenderer;
/*     */ import net.spartanb312.base.hud.HUDModule;
/*     */ import net.spartanb312.base.hud.huds.CombatInfo;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*     */ import net.spartanb312.base.module.modules.client.HUDEditor;
/*     */ import net.spartanb312.base.module.modules.movement.Sprint;
/*     */ import net.spartanb312.base.module.modules.movement.Velocity;
/*     */ import net.spartanb312.base.module.modules.other.FakePlayer;
/*     */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*     */ 
/*     */ public class ModuleManager {
/*  40 */   public final Map<Class<? extends Module>, Module> moduleMap = new ConcurrentHashMap<>();
/*  41 */   public final List<Module> moduleList = new ArrayList<>();
/*  42 */   private final Set<Class<? extends Module>> classes = new HashSet<>();
/*     */   
/*     */   private static ModuleManager instance;
/*     */   
/*     */   public static List<Module> getModules() {
/*  47 */     return (getInstance()).moduleList;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {
/*  52 */     if (instance == null) instance = new ModuleManager(); 
/*  53 */     instance.moduleMap.clear();
/*     */ 
/*     */     
/*  56 */     registerNewModule((Class)ClickGUI.class);
/*  57 */     registerNewModule((Class)HUDEditor.class);
/*  58 */     registerNewModule((Class)MoreClickGUI.class);
/*  59 */     registerNewModule((Class)ChatSettings.class);
/*  60 */     registerNewModule((Class)CustomFont.class);
/*  61 */     registerNewModule((Class)Particles.class);
/*  62 */     registerNewModule((Class)Blur.class);
/*  63 */     registerNewModule((Class)ClientInfo.class);
/*     */ 
/*     */     
/*  66 */     registerNewModule((Class)SelfBlock.class);
/*  67 */     registerNewModule((Class)Aura.class);
/*  68 */     registerNewModule((Class)SilentXP.class);
/*  69 */     registerNewModule((Class)Criticals.class);
/*  70 */     registerNewModule((Class)MinePlus.class);
/*  71 */     registerNewModule((Class)MultiTask.class);
/*  72 */     registerNewModule((Class)FastUse.class);
/*  73 */     registerNewModule((Class)Surround.class);
/*  74 */     registerNewModule((Class)Offhand.class);
/*  75 */     registerNewModule((Class)SpongeCrystal.class);
/*     */ 
/*     */     
/*  78 */     registerNewModule((Class)AntiContainer.class);
/*  79 */     registerNewModule((Class)FakePlayer.class);
/*  80 */     registerNewModule((Class)Spammer.class);
/*  81 */     registerNewModule((Class)Freecam.class);
/*  82 */     registerNewModule((Class)RPC.class);
/*  83 */     registerNewModule((Class)EnderChestFarm.class);
/*  84 */     registerNewModule((Class)EntityAlerter.class);
/*  85 */     registerNewModule((Class)NoEntityBlock.class);
/*  86 */     registerNewModule((Class)DummyModule.class);
/*  87 */     registerNewModule((Class)PacketCancel.class);
/*  88 */     registerNewModule((Class)ElytraSwap.class);
/*  89 */     registerNewModule((Class)PingSpoof.class);
/*  90 */     registerNewModule((Class)GapSwapBind.class);
/*  91 */     registerNewModule((Class)NameSpoof.class);
/*     */ 
/*     */     
/*  94 */     registerNewModule((Class)Sprint.class);
/*  95 */     registerNewModule((Class)Velocity.class);
/*  96 */     registerNewModule((Class)NoHunger.class);
/*  97 */     registerNewModule((Class)NoSlow.class);
/*  98 */     registerNewModule((Class)ReverseStep.class);
/*  99 */     registerNewModule((Class)Step.class);
/* 100 */     registerNewModule((Class)Timer.class);
/* 101 */     registerNewModule((Class)GUIMove.class);
/* 102 */     registerNewModule((Class)EntityControl.class);
/*     */ 
/*     */     
/* 105 */     registerNewModule((Class)NoRender.class);
/* 106 */     registerNewModule((Class)FullBright.class);
/* 107 */     registerNewModule((Class)ESP.class);
/* 108 */     registerNewModule((Class)Chams.class);
/* 109 */     registerNewModule((Class)FOV.class);
/* 110 */     registerNewModule((Class)HoleRender.class);
/* 111 */     registerNewModule((Class)Nametags.class);
/* 112 */     registerNewModule((Class)CityRender.class);
/* 113 */     registerNewModule((Class)HoveredHighlight.class);
/* 114 */     registerNewModule((Class)EntityPointer.class);
/* 115 */     registerNewModule((Class)CameraClip.class);
/* 116 */     registerNewModule((Class)ChorusTrace.class);
/* 117 */     registerNewModule((Class)HeldModelTweaks.class);
/* 118 */     registerNewModule((Class)PopRender.class);
/*     */ 
/*     */     
/* 121 */     registerNewModule((Class)ActiveModuleList.class);
/* 122 */     registerNewModule((Class)CombatInfo.class);
/* 123 */     registerNewModule((Class)Welcomer.class);
/* 124 */     registerNewModule((Class)CustomHUDFont.class);
/* 125 */     registerNewModule((Class)WaterMark.class);
/* 126 */     registerNewModule((Class)ArmorDisplay.class);
/* 127 */     registerNewModule((Class)FPS.class);
/* 128 */     registerNewModule((Class)DebugThing.class);
/* 129 */     registerNewModule((Class)LagNotify.class);
/*     */     
/* 131 */     instance.loadModules();
/* 132 */     BaseCenter.EVENT_BUS.register(instance);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerNewModule(Class<? extends Module> clazz) {
/* 137 */     instance.classes.add(clazz);
/*     */   }
/*     */   
/*     */   @Listener(priority = 2147483647)
/*     */   public void onKey(KeyEvent event) {
/* 142 */     this.moduleList.forEach(it -> it.keyBinds.forEach(()));
/*     */   }
/*     */   
/*     */   @Listener(priority = 2147483647)
/*     */   public void onRenderHUD(RenderOverlayEvent event) {
/* 147 */     RenderUtils2D.prepareGl();
/* 148 */     for (int i = HUDEditorRenderer.instance.hudModules.size() - 1; i >= 0; i--) {
/* 149 */       HUDModule hudModule = HUDEditorRenderer.instance.hudModules.get(i);
/*     */       
/* 151 */       if (!(ItemUtils.mc.field_71462_r instanceof net.spartanb312.base.gui.HUDEditorFinal) && hudModule.isEnabled())
/* 152 */         hudModule.onHUDRender(event.getScaledResolution()); 
/*     */     } 
/* 154 */     RenderUtils2D.releaseGl();
/*     */   }
/*     */   
/*     */   public static ModuleManager getInstance() {
/* 158 */     if (instance == null) instance = new ModuleManager(); 
/* 159 */     return instance;
/*     */   }
/*     */   
/*     */   public static Module getModule(Class<? extends Module> clazz) {
/* 163 */     return (getInstance()).moduleMap.get(clazz);
/*     */   }
/*     */   
/*     */   public static Module getModuleByName(String targetName) {
/* 167 */     for (Module module : getModules()) {
/* 168 */       if (module.name.equalsIgnoreCase(targetName)) {
/* 169 */         return module;
/*     */       }
/*     */     } 
/* 172 */     BaseCenter.log.info("Module " + targetName + " is not exist.Please check twice!");
/* 173 */     return null;
/*     */   }
/*     */   
/*     */   private void loadModules() {
/* 177 */     BaseCenter.log.info("[ModuleManager]Loading modules.");
/* 178 */     ConcurrentTaskManager.runBlocking(unit -> this.classes.stream().sorted(Comparator.comparing(Class::getSimpleName)).forEach(()));
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
/* 199 */     sort();
/* 200 */     BaseCenter.log.info("[ModuleManager]Loaded " + this.moduleList.size() + " modules");
/*     */   }
/*     */   
/*     */   private synchronized void add(Module module, Class<? extends Module> clazz) {
/* 204 */     this.moduleList.add(module);
/* 205 */     this.moduleMap.put(clazz, module);
/*     */   }
/*     */   
/*     */   private void sort() {
/* 209 */     this.moduleList.sort(Comparator.comparing(it -> it.name));
/*     */   }
/*     */   
/*     */   public static String getModuleMiniIcons(Class<? extends Module> clazz) {
/* 213 */     if (clazz == Offhand.class) {
/* 214 */       return "A";
/*     */     }
/* 216 */     if (clazz == NoHunger.class) {
/* 217 */       return "B";
/*     */     }
/* 219 */     if (clazz == Sprint.class) {
/* 220 */       return "C";
/*     */     }
/* 222 */     if (clazz == Velocity.class) {
/* 223 */       return "D";
/*     */     }
/* 225 */     if (clazz == ChatSettings.class) {
/* 226 */       return "E";
/*     */     }
/* 228 */     if (clazz == ClickGUI.class) {
/* 229 */       return "F";
/*     */     }
/* 231 */     if (clazz == MoreClickGUI.class) {
/* 232 */       return "F";
/*     */     }
/* 234 */     if (clazz == CustomFont.class) {
/* 235 */       return "G";
/*     */     }
/* 237 */     if (clazz == HUDEditor.class) {
/* 238 */       return "H";
/*     */     }
/* 240 */     if (clazz == Particles.class) {
/* 241 */       return "I";
/*     */     }
/* 243 */     if (clazz == WaterMark.class) {
/* 244 */       return "J";
/*     */     }
/*     */     
/* 247 */     return "";
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\client\ModuleManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */