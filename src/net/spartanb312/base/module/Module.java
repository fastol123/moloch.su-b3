/*     */ package net.spartanb312.base.module;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import me.thediamondsword5.moloch.core.common.Visibility;
/*     */ import me.thediamondsword5.moloch.hud.huds.CustomHUDFont;
/*     */ import me.thediamondsword5.moloch.module.modules.client.ChatSettings;
/*     */ import me.thediamondsword5.moloch.module.modules.client.ClientInfo;
/*     */ import me.thediamondsword5.moloch.module.modules.client.CustomFont;
/*     */ import me.thediamondsword5.moloch.module.modules.client.MoreClickGUI;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.spartanb312.base.BaseCenter;
/*     */ import net.spartanb312.base.client.ModuleManager;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.common.KeyBind;
/*     */ import net.spartanb312.base.core.concurrent.task.Task;
/*     */ import net.spartanb312.base.core.concurrent.task.VoidTask;
/*     */ import net.spartanb312.base.core.config.ListenableContainer;
/*     */ import net.spartanb312.base.core.event.decentralization.DecentralizedEvent;
/*     */ import net.spartanb312.base.core.event.decentralization.EventData;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.client.InputUpdateEvent;
/*     */ import net.spartanb312.base.event.events.network.PacketEvent;
/*     */ import net.spartanb312.base.event.events.render.RenderEvent;
/*     */ import net.spartanb312.base.event.events.render.RenderOverlayEvent;
/*     */ import net.spartanb312.base.notification.NotificationManager;
/*     */ import net.spartanb312.base.utils.ChatUtil;
/*     */ 
/*     */ 
/*     */ public class Module
/*     */   extends ListenableContainer
/*     */ {
/*  37 */   public final String name = getAnnotation().name();
/*  38 */   public final Category category = getAnnotation().category();
/*  39 */   public final Parallel annotation = getClass().<Parallel>getAnnotation(Parallel.class);
/*  40 */   public final boolean parallelRunnable = (this.annotation != null && this.annotation.runnable()); public boolean enabled; private final ConcurrentHashMap<DecentralizedEvent<? extends EventData>, Task<? extends EventData>> listenerMap; public final List<KeyBind> keyBinds; protected final Setting<Boolean> enabledSetting; public final Setting<KeyBind> bindSetting;
/*  41 */   public final String description = getAnnotation().description(); public final Setting<Visibility> visibleSetting;
/*     */   public final Setting<String> displayName;
/*     */   public boolean moduleEnableFlag;
/*     */   public boolean moduleDisableFlag;
/*     */   
/*     */   public Module() {
/*  47 */     this.enabled = false;
/*  48 */     this.listenerMap = new ConcurrentHashMap<>();
/*     */     
/*  50 */     this.keyBinds = new ArrayList<>();
/*     */     
/*  52 */     this.enabledSetting = setting("Enabled", false).when(() -> false);
/*  53 */     this.bindSetting = setting("Bind", subscribeKey(new KeyBind(getAnnotation().keyCode(), this::toggle))).des("The key bind of this module");
/*  54 */     this.visibleSetting = setting("Visible", new Visibility(true)).des("Determine the visibility of the module");
/*  55 */     this.displayName = setting("DisplayName", this.name).des("Display name of module on arraylist and toggle notifications");
/*     */ 
/*     */     
/*  58 */     this.moduleEnableFlag = false;
/*  59 */     this.moduleDisableFlag = false;
/*     */     this.configFile = new File("moloch.su/config/modules/" + this.category.categoryName + "/" + this.name + ".json");
/*  61 */   } public static Minecraft mc = Minecraft.func_71410_x();
/*     */   
/*     */   public void onSave() {
/*  64 */     this.enabledSetting.setValue(Boolean.valueOf(this.enabled));
/*  65 */     saveConfig();
/*     */   }
/*     */   
/*     */   public void onLoad() {
/*  69 */     readConfig();
/*  70 */     if (((Boolean)this.enabledSetting.getValue()).booleanValue() && !this.enabled) { enable(); }
/*  71 */     else if (!((Boolean)this.enabledSetting.getValue()).booleanValue() && this.enabled) { disable(); }
/*     */     
/*  73 */     if (ModuleManager.getModule(CustomHUDFont.class).isDisabled()) {
/*  74 */       ModuleManager.getModule(CustomHUDFont.class).enable();
/*     */     }
/*  76 */     if (ModuleManager.getModule(CustomFont.class).isDisabled()) {
/*  77 */       ModuleManager.getModule(CustomFont.class).enable();
/*     */     }
/*  79 */     if (ModuleManager.getModule(ChatSettings.class).isDisabled()) {
/*  80 */       ModuleManager.getModule(ChatSettings.class).enable();
/*     */     }
/*  82 */     if (ModuleManager.getModule(MoreClickGUI.class).isDisabled()) {
/*  83 */       ModuleManager.getModule(MoreClickGUI.class).enable();
/*     */     }
/*  85 */     if (ModuleManager.getModule(ClientInfo.class).isDisabled()) {
/*  86 */       ModuleManager.getModule(ClientInfo.class).enable();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyBind subscribeKey(KeyBind keyBind) {
/*  92 */     this.keyBinds.add(keyBind);
/*  93 */     return keyBind;
/*     */   }
/*     */   
/*     */   public KeyBind unsubscribeKey(KeyBind keyBind) {
/*  97 */     this.keyBinds.remove(keyBind);
/*  98 */     return keyBind;
/*     */   }
/*     */   
/*     */   public void toggle() {
/* 102 */     if (isEnabled()) { disable(); }
/* 103 */     else { enable(); }
/*     */   
/*     */   }
/*     */   public void reload() {
/* 107 */     if (this.enabled) {
/* 108 */       this.enabled = false;
/* 109 */       BaseCenter.MODULE_BUS.unregister(this);
/* 110 */       onDisable();
/* 111 */       this.enabled = true;
/* 112 */       BaseCenter.MODULE_BUS.register(this);
/* 113 */       onEnable();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/* 118 */     return this.enabled;
/*     */   }
/*     */   
/*     */   public boolean isDisabled() {
/* 122 */     return !this.enabled;
/*     */   }
/*     */   
/*     */   public void enable() {
/* 126 */     this.enabled = true;
/* 127 */     BaseCenter.MODULE_BUS.register(this);
/* 128 */     subscribe();
/*     */     
/* 130 */     if (!((Boolean)ChatSettings.INSTANCE.invisibleToggleMessages.getValue()).booleanValue() || ((Visibility)this.visibleSetting.getValue()).getVisible()) {
/* 131 */       NotificationManager.moduleToggle(this, (String)this.displayName.getValue(), true);
/*     */     }
/*     */     
/* 134 */     onEnable();
/*     */   }
/*     */   
/*     */   public void disable() {
/* 138 */     this.enabled = false;
/* 139 */     BaseCenter.MODULE_BUS.unregister(this);
/* 140 */     unsubscribe();
/*     */     
/* 142 */     if (!((Boolean)ChatSettings.INSTANCE.invisibleToggleMessages.getValue()).booleanValue() || ((Visibility)this.visibleSetting.getValue()).getVisible()) {
/* 143 */       NotificationManager.moduleToggle(this, (String)this.displayName.getValue(), false);
/*     */     }
/*     */     
/* 146 */     onDisable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPacketReceive(PacketEvent.Receive event) {}
/*     */ 
/*     */   
/*     */   public void onPacketSend(PacketEvent.Send event) {}
/*     */ 
/*     */   
/*     */   public void onTick() {}
/*     */ 
/*     */   
/*     */   public void onRenderTick() {}
/*     */   
/*     */   public void onEnable() {
/* 162 */     this.moduleEnableFlag = true;
/*     */   }
/*     */   
/*     */   public void onDisable() {
/* 166 */     this.moduleDisableFlag = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRender(RenderOverlayEvent event) {}
/*     */ 
/*     */   
/*     */   public void onRenderWorld(RenderEvent event) {}
/*     */ 
/*     */   
/*     */   public void onInputUpdate(InputUpdateEvent event) {}
/*     */ 
/*     */   
/*     */   public void onSettingChange(Setting<?> setting) {}
/*     */   
/*     */   public Setting<VoidTask> actionListener(String name, VoidTask defaultValue) {
/* 182 */     ListenerSetting setting = new ListenerSetting(name, defaultValue);
/* 183 */     getSettings().add(setting);
/* 184 */     return setting;
/*     */   }
/*     */   
/*     */   @SafeVarargs
/*     */   public final <T> List<T> listOf(T... elements) {
/* 189 */     return Arrays.asList(elements);
/*     */   }
/*     */   
/*     */   public ModuleInfo getAnnotation() {
/* 193 */     if (getClass().isAnnotationPresent((Class)ModuleInfo.class)) {
/* 194 */       return getClass().<ModuleInfo>getAnnotation(ModuleInfo.class);
/*     */     }
/* 196 */     throw new IllegalStateException("No Annotation on class " + getClass().getCanonicalName() + "!");
/*     */   }
/*     */   
/*     */   public String getModuleInfo() {
/* 200 */     return "";
/*     */   }
/*     */   
/*     */   public String getHudSuffix() {
/* 204 */     return (String)this.displayName.getValue() + (!getModuleInfo().equals("") ? (ChatUtil.colored("7") + "[" + ChatUtil.colored("f") + getModuleInfo() + ChatUtil.colored("7") + "]") : getModuleInfo());
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\module\Module.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */