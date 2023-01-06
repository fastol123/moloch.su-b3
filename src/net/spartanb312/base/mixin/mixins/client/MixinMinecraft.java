/*     */ package net.spartanb312.base.mixin.mixins.client;
/*     */ 
/*     */ import me.thediamondsword5.moloch.event.events.player.LeftClickBlockEvent;
/*     */ import me.thediamondsword5.moloch.event.events.player.MultiTaskEvent;
/*     */ import me.thediamondsword5.moloch.event.events.player.PlayerAttackEvent;
/*     */ import me.thediamondsword5.moloch.event.events.player.RightClickDelayEvent;
/*     */ import me.thediamondsword5.moloch.module.modules.combat.SelfBlock;
/*     */ import me.thediamondsword5.moloch.module.modules.other.Freecam;
/*     */ import me.thediamondsword5.moloch.utils.BlockUtil;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.FMLLog;
/*     */ import net.spartanb312.base.BaseCenter;
/*     */ import net.spartanb312.base.client.ConfigManager;
/*     */ import net.spartanb312.base.client.ModuleManager;
/*     */ import net.spartanb312.base.event.decentraliized.DecentralizedClientTickEvent;
/*     */ import net.spartanb312.base.event.events.client.GameLoopEvent;
/*     */ import net.spartanb312.base.event.events.client.GuiScreenEvent;
/*     */ import net.spartanb312.base.event.events.client.InitializationEvent;
/*     */ import net.spartanb312.base.event.events.client.InputUpdateEvent;
/*     */ import net.spartanb312.base.event.events.client.KeyEvent;
/*     */ import net.spartanb312.base.event.events.client.TickEvent;
/*     */ import net.spartanb312.base.utils.ItemUtils;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ @Mixin({Minecraft.class})
/*     */ public class MixinMinecraft
/*     */ {
/*     */   @Shadow
/*     */   public static Minecraft field_71432_P;
/*     */   
/*     */   @Inject(method = {"displayGuiScreen"}, at = {@At("HEAD")})
/*     */   public void displayGuiScreen(GuiScreen guiScreenIn, CallbackInfo info) {
/*  43 */     if (ItemUtils.mc.field_71462_r != null) {
/*  44 */       GuiScreenEvent.Closed screenEvent = new GuiScreenEvent.Closed(ItemUtils.mc.field_71462_r);
/*  45 */       BaseCenter.EVENT_BUS.post(screenEvent);
/*  46 */       GuiScreenEvent.Displayed screenEvent1 = new GuiScreenEvent.Displayed(guiScreenIn);
/*  47 */       BaseCenter.EVENT_BUS.post(screenEvent1);
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"runGameLoop"}, at = {@At("HEAD")})
/*     */   public void runGameLoop(CallbackInfo ci) {
/*  53 */     BaseCenter.EVENT_BUS.post(new GameLoopEvent());
/*     */   }
/*     */   
/*     */   @Inject(method = {"runTickKeyboard"}, at = {@At(value = "INVOKE_ASSIGN", target = "org/lwjgl/input/Keyboard.getEventKeyState()Z", remap = false)})
/*     */   private void onKeyEvent(CallbackInfo ci) {
/*  58 */     if (ItemUtils.mc.field_71462_r != null) {
/*     */       return;
/*     */     }
/*  61 */     boolean down = Keyboard.getEventKeyState();
/*  62 */     int key = Keyboard.getEventKey();
/*  63 */     char ch = Keyboard.getEventCharacter();
/*     */ 
/*     */     
/*  66 */     if (key != 0)
/*  67 */       BaseCenter.EVENT_BUS.post(down ? new KeyEvent(key, ch) : new InputUpdateEvent(key, ch)); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"runTick"}, at = {@At("RETURN")})
/*     */   public void onTick(CallbackInfo ci) {
/*  72 */     if (ItemUtils.mc.field_71439_g != null) {
/*  73 */       DecentralizedClientTickEvent.instance.post(null);
/*  74 */       BaseCenter.EVENT_BUS.post(new TickEvent());
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"init"}, at = {@At("HEAD")})
/*     */   public void onInitMinecraft(CallbackInfo ci) {
/*  80 */     BaseCenter.EVENT_BUS.register(BaseCenter.instance);
/*     */   }
/*     */   
/*     */   @Inject(method = {"init"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V", ordinal = 0, shift = At.Shift.BEFORE)})
/*     */   public void onPreInit(CallbackInfo callbackInfo) {
/*  85 */     BaseCenter.EVENT_BUS.post(new InitializationEvent.PreInitialize());
/*     */   }
/*     */   
/*     */   @Inject(method = {"init"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;checkGLError(Ljava/lang/String;)V", ordinal = 2, shift = At.Shift.AFTER)})
/*     */   public void onInit(CallbackInfo ci) {
/*  90 */     FMLLog.log.fatal("Loading moloch.su");
/*  91 */     BaseCenter.EVENT_BUS.post(new InitializationEvent.Initialize());
/*     */   }
/*     */   
/*     */   @Inject(method = {"init"}, at = {@At("RETURN")})
/*     */   public void onPostInit(CallbackInfo ci) {
/*  96 */     BaseCenter.EVENT_BUS.post(new InitializationEvent.PostInitialize());
/*     */   }
/*     */   
/*     */   @Redirect(method = {"run"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayCrashReport(Lnet/minecraft/crash/CrashReport;)V"))
/*     */   public void displayCrashReport(Minecraft minecraft, CrashReport crashReport) {
/* 101 */     save();
/*     */   }
/*     */   
/*     */   @Inject(method = {"shutdown"}, at = {@At("HEAD")})
/*     */   public void shutdown(CallbackInfo info) {
/* 106 */     save();
/*     */   }
/*     */   
/*     */   @Inject(method = {"clickMouse"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void clickMouseHook(CallbackInfo ci) {
/* 111 */     if (ItemUtils.mc.field_71476_x != null)
/* 112 */       if (ItemUtils.mc.field_71476_x.field_72308_g != null) {
/* 113 */         PlayerAttackEvent event = new PlayerAttackEvent(ItemUtils.mc.field_71476_x.field_72308_g);
/* 114 */         BaseCenter.EVENT_BUS.post(event);
/*     */         
/* 116 */         if (event.isCancelled()) {
/* 117 */           ci.cancel();
/*     */         }
/* 119 */       } else if (BlockUtil.isBlockPlaceable(ItemUtils.mc.field_71476_x.func_178782_a())) {
/* 120 */         LeftClickBlockEvent event = new LeftClickBlockEvent(ItemUtils.mc.field_71476_x.func_178782_a(), ItemUtils.mc.field_71476_x.field_178784_b);
/* 121 */         BaseCenter.EVENT_BUS.post(event);
/*     */         
/* 123 */         if (event.isCancelled()) {
/* 124 */           ci.cancel();
/*     */         }
/*     */       }  
/*     */   }
/*     */   
/*     */   @Redirect(method = {"sendClickBlockToController"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
/*     */   private boolean isHandActiveRedirect(EntityPlayerSP entityPlayerSP) {
/* 131 */     MultiTaskEvent event = new MultiTaskEvent();
/* 132 */     BaseCenter.EVENT_BUS.post(event);
/* 133 */     if (event.isCancelled()) {
/* 134 */       return false;
/*     */     }
/* 136 */     return entityPlayerSP.func_184587_cr();
/*     */   }
/*     */   
/*     */   @Inject(method = {"rightClickMouse"}, at = {@At("HEAD")})
/*     */   private void rightClickMouseHook(CallbackInfo ci) {
/* 141 */     MultiTaskEvent event = new MultiTaskEvent();
/* 142 */     BaseCenter.EVENT_BUS.post(event);
/* 143 */     if (event.isCancelled()) {
/* 144 */       ItemUtils.mc.field_71442_b.field_78778_j = false;
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"rightClickMouse"}, at = {@At(value = "RETURN", ordinal = 0)})
/*     */   public void rightClickMouseHook1(CallbackInfo ci) {
/* 150 */     RightClickDelayEvent event = new RightClickDelayEvent();
/* 151 */     BaseCenter.EVENT_BUS.post(event);
/*     */   }
/*     */   
/*     */   @Inject(method = {"rightClickMouse"}, at = {@At(value = "RETURN", ordinal = 1)})
/*     */   public void rightClickMouseHook2(CallbackInfo ci) {
/* 156 */     RightClickDelayEvent event = new RightClickDelayEvent();
/* 157 */     BaseCenter.EVENT_BUS.post(event);
/*     */   }
/*     */   
/*     */   @Inject(method = {"rightClickMouse"}, at = {@At(value = "RETURN", ordinal = 2)})
/*     */   public void rightClickMouseHook3(CallbackInfo ci) {
/* 162 */     RightClickDelayEvent event = new RightClickDelayEvent();
/* 163 */     BaseCenter.EVENT_BUS.post(event);
/*     */   }
/*     */   
/*     */   @Inject(method = {"rightClickMouse"}, at = {@At(value = "RETURN", ordinal = 3)})
/*     */   public void rightClickMouseHook4(CallbackInfo ci) {
/* 168 */     RightClickDelayEvent event = new RightClickDelayEvent();
/* 169 */     BaseCenter.EVENT_BUS.post(event);
/*     */   }
/*     */   
/*     */   private void save() {
/* 173 */     if (ModuleManager.getModule(SelfBlock.class).isEnabled()) {
/* 174 */       ModuleManager.getModule(SelfBlock.class).disable();
/*     */     }
/* 176 */     if (ModuleManager.getModule(Freecam.class).isEnabled()) {
/* 177 */       MinecraftForge.EVENT_BUS.unregister(this);
/* 178 */       Freecam.INSTANCE.resetFreecam();
/* 179 */       ModuleManager.getModule(Freecam.class).disable();
/*     */     } 
/*     */     
/* 182 */     System.out.println("Shutting down: saving moloch.su configuration");
/* 183 */     ConfigManager.saveAll();
/* 184 */     System.out.println("Configuration saved.");
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\client\MixinMinecraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */