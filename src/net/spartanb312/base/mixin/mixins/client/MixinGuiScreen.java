/*    */ package net.spartanb312.base.mixin.mixins.client;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.render.DrawScreenEvent;
/*    */ import me.thediamondsword5.moloch.gui.components.StringInput;
/*    */ import me.thediamondsword5.moloch.module.modules.client.Particles;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.command.Command;
/*    */ import net.spartanb312.base.module.modules.visuals.NoRender;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({GuiScreen.class})
/*    */ public class MixinGuiScreen
/*    */   extends Gui
/*    */ {
/*    */   @Inject(method = {"drawDefaultBackground"}, at = {@At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/eventhandler/EventBus;post(Lnet/minecraftforge/fml/common/eventhandler/Event;)Z")})
/*    */   private void drawDefaultBackgroundHook(CallbackInfo ci) {
/* 26 */     DrawScreenEvent.Layer1 layer1 = new DrawScreenEvent.Layer1();
/* 27 */     BaseCenter.EVENT_BUS.post(layer1);
/*    */     
/* 29 */     DrawScreenEvent.Layer2 layer2 = new DrawScreenEvent.Layer2();
/* 30 */     BaseCenter.EVENT_BUS.post(layer2);
/*    */     
/* 32 */     if (Command.mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiContainer && (
/* 33 */       !((Boolean)Particles.INSTANCE.particlesOtherGUI.getValue()).booleanValue() || !Particles.INSTANCE.isEnabled())) {
/* 34 */       GL11.glEnable(3042);
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"Lnet/minecraft/client/gui/GuiScreen;drawWorldBackground(I)V"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void drawWorldBackgroundHook(int tint, CallbackInfo ci) {
/* 40 */     if (Command.mc.field_71441_e != null && ((Boolean)NoRender.INSTANCE.backgrounds.getValue()).booleanValue() && ModuleManager.getModule(NoRender.class).isEnabled()) {
/* 41 */       ci.cancel();
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"keyTyped"}, at = {@At("HEAD")})
/*    */   public void keyTypedHook(char typedChar, int keyCode, CallbackInfo ci) {
/* 47 */     StringInput.INSTANCE.keyTyped(typedChar, keyCode);
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\client\MixinGuiScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */