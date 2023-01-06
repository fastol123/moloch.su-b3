/*     */ package net.spartanb312.base.mixin.mixins.render;
/*     */ 
/*     */ import me.thediamondsword5.moloch.event.decentralized.DecentralizedRenderWorldPostEvent;
/*     */ import me.thediamondsword5.moloch.event.events.player.BlockInteractionEvent;
/*     */ import me.thediamondsword5.moloch.event.events.render.FOVItemModifyEvent;
/*     */ import me.thediamondsword5.moloch.event.events.render.RenderWorldPostEventCenter;
/*     */ import me.thediamondsword5.moloch.mixinotherstuff.IEntityRenderer;
/*     */ import me.thediamondsword5.moloch.module.modules.visuals.CameraClip;
/*     */ import me.thediamondsword5.moloch.module.modules.visuals.HoveredHighlight;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.EntityRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.spartanb312.base.BaseCenter;
/*     */ import net.spartanb312.base.client.ModuleManager;
/*     */ import net.spartanb312.base.core.event.decentralization.EventData;
/*     */ import net.spartanb312.base.event.decentraliized.DecentralizedRenderWorldEvent;
/*     */ import net.spartanb312.base.event.events.render.HudOverlayEvent;
/*     */ import net.spartanb312.base.event.events.render.RenderWorldEvent;
/*     */ import net.spartanb312.base.module.modules.visuals.NoRender;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({EntityRenderer.class})
/*     */ public abstract class MixinEntityRenderer
/*     */   implements IEntityRenderer
/*     */ {
/*     */   @Shadow
/*     */   @Final
/*     */   public Minecraft field_78531_r;
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_78479_a(float paramFloat, int paramInt);
/*     */   
/*     */   @Inject(method = {"hurtCameraEffect"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void hurtCameraEffect(float partialTicks, CallbackInfo ci) {
/*  51 */     HudOverlayEvent event = new HudOverlayEvent(HudOverlayEvent.Type.HURTCAM);
/*  52 */     BaseCenter.EVENT_BUS.post(event);
/*  53 */     if (event.isCancelled())
/*  54 */       ci.cancel(); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"renderWorldPass"}, at = {@At(value = "INVOKE_STRING", target = "net/minecraft/profiler/Profiler.endStartSection(Ljava/lang/String;)V", args = {"ldc=hand"})})
/*     */   public void onStartHand(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
/*  59 */     RenderWorldEvent event = new RenderWorldEvent(partialTicks, pass);
/*  60 */     DecentralizedRenderWorldEvent.instance.post((EventData)event);
/*  61 */     BaseCenter.EVENT_BUS.post(event);
/*     */   }
/*     */   
/*     */   @Inject(method = {"renderWorldPass"}, at = {@At("TAIL")})
/*     */   public void onStartHand1(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
/*  66 */     RenderWorldPostEventCenter event = new RenderWorldPostEventCenter(partialTicks, pass);
/*  67 */     DecentralizedRenderWorldPostEvent.instance.post((EventData)event);
/*  68 */     BaseCenter.EVENT_BUS.post(event);
/*     */   }
/*     */   
/*     */   @Inject(method = {"renderWorldPass"}, at = {@At("RETURN")})
/*     */   public void renderWorldPassHookPost(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci) {
/*  73 */     GL11.glDisable(2896);
/*     */   }
/*     */   
/*     */   @Inject(method = {"hurtCameraEffect"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void hurtCameraEffectHook(float partialTicks, CallbackInfo ci) {
/*  78 */     if (ModuleManager.getModule(NoRender.class).isEnabled() && ((Boolean)NoRender.INSTANCE.hurtCam.getValue()).booleanValue()) ci.cancel(); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"setupFog"}, at = {@At("RETURN")})
/*     */   public void setupFogHook(int startCoords, float partialTicks, CallbackInfo ci) {
/*  83 */     if (ModuleManager.getModule(NoRender.class).isEnabled() && ((Boolean)NoRender.INSTANCE.fog.getValue()).booleanValue())
/*  84 */       GlStateManager.func_179106_n(); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"displayItemActivation"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void displayItemActivationHook(ItemStack stack, CallbackInfo ci) {
/*  89 */     if (ModuleManager.getModule(NoRender.class).isEnabled() && NoRender.INSTANCE.totemPop.getValue() == NoRender.TotemMode.NoRender)
/*  90 */       ci.cancel(); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"renderItemActivation"}, at = {@At("HEAD")})
/*     */   public void renderItemActivationHook(int p_190563_1_, int p_190563_2_, float p_190563_3_, CallbackInfo ci) {
/*  95 */     if (ModuleManager.getModule(NoRender.class).isEnabled() && NoRender.INSTANCE.totemPop.getValue() == NoRender.TotemMode.Scale) {
/*     */       
/*  97 */       ScaledResolution scaledResolution = new ScaledResolution(this.field_78531_r);
/*  98 */       int scaledWidth = scaledResolution.func_78326_a();
/*  99 */       int scaledHeight = scaledResolution.func_78328_b();
/*     */       
/* 101 */       GL11.glTranslatef(scaledWidth / 2.0F * (1.0F - ((Float)NoRender.INSTANCE.totemSize.getValue()).floatValue()), scaledHeight / 2.0F * (1.0F - ((Float)NoRender.INSTANCE.totemSize.getValue()).floatValue()), 0.0F);
/* 102 */       GL11.glScalef(((Float)NoRender.INSTANCE.totemSize.getValue()).floatValue(), ((Float)NoRender.INSTANCE.totemSize.getValue()).floatValue(), ((Float)NoRender.INSTANCE.totemSize.getValue()).floatValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void invokeSetupCameraTransform(float partialTicks, int pass) {
/* 108 */     func_78479_a(partialTicks, pass);
/*     */   }
/*     */   
/*     */   @Inject(method = {"getFOVModifier"}, at = {@At("RETURN")}, cancellable = true)
/*     */   public void getFOVModifierHook(float partialTicks, boolean useFOVSetting, CallbackInfoReturnable<Float> cir) {
/* 113 */     FOVItemModifyEvent event = new FOVItemModifyEvent(((Float)cir.getReturnValue()).floatValue());
/* 114 */     BaseCenter.EVENT_BUS.post(event);
/* 115 */     if (event.isCancelled())
/* 116 */       cir.setReturnValue(Float.valueOf(event.fov)); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"getMouseOver"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getPositionEyes(F)Lnet/minecraft/util/math/Vec3d;", shift = At.Shift.BEFORE)}, cancellable = true)
/*     */   public void getMouseOverHook(float partialTicks, CallbackInfo ci) {
/* 121 */     BlockInteractionEvent event = new BlockInteractionEvent();
/* 122 */     BaseCenter.EVENT_BUS.post(event);
/* 123 */     if (event.isCancelled()) {
/* 124 */       ci.cancel();
/* 125 */       this.field_78531_r.field_71424_I.func_76319_b();
/*     */     } 
/*     */   }
/*     */   
/*     */   @ModifyVariable(method = {"orientCamera"}, ordinal = 3, at = @At(value = "STORE", ordinal = 0), require = 1)
/*     */   public double orientCameraModify(double d) {
/* 131 */     if (ModuleManager.getModule(CameraClip.class).isEnabled()) {
/* 132 */       return ((Float)CameraClip.INSTANCE.cameraDistance.getValue()).floatValue();
/*     */     }
/*     */     
/* 135 */     return d;
/*     */   }
/*     */ 
/*     */   
/*     */   @ModifyVariable(method = {"orientCamera"}, ordinal = 7, at = @At(value = "STORE", ordinal = 0), require = 1)
/*     */   public double orientCameraModify2(double d) {
/* 141 */     if (ModuleManager.getModule(CameraClip.class).isEnabled()) {
/* 142 */       return ((Float)CameraClip.INSTANCE.cameraDistance.getValue()).floatValue();
/*     */     }
/*     */     
/* 145 */     return d;
/*     */   }
/*     */ 
/*     */   
/*     */   @Redirect(method = {"renderWorldPass"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawSelectionBox(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/math/RayTraceResult;IF)V"))
/*     */   public void renderWorldPassRedirect(RenderGlobal instance, EntityPlayer d4, RayTraceResult d5, int blockpos, float iblockstate) {
/* 151 */     if (ModuleManager.getModule(HoveredHighlight.class).isDisabled())
/* 152 */       instance.func_72731_b(d4, d5, blockpos, iblockstate); 
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinEntityRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */