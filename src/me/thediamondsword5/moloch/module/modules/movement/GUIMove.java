/*    */ package me.thediamondsword5.moloch.module.modules.movement;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import me.thediamondsword5.moloch.event.events.player.KeyEvent;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.event.Listener;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.gui.Component;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ import net.spartanb312.base.utils.MathUtilFuckYou;
/*    */ import net.spartanb312.base.utils.Timer;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "GUIMove", category = Category.MOVEMENT, description = "Be able to move player while a GUI is open")
/*    */ public class GUIMove
/*    */   extends Module
/*    */ {
/* 26 */   Setting<Boolean> jump = setting("Jump", true).des("Makes player jump when spacebar is pressed while GUI screen is open");
/* 27 */   Setting<Boolean> sneak = setting("Sneak", false).des("Crouches player when sneak keybind is pressed while in GUI screen");
/* 28 */   Setting<Boolean> rotateArrows = setting("RotateArrows", false).des("Rotates player in GUI screen when arrow keys are pressed");
/* 29 */   Setting<Float> rotateArrowsSpeed = setting("RotateArrowsSpeed", 1.0F, 0.1F, 5.0F).des("Speed of rotation").whenTrue(this.rotateArrows);
/*    */   
/* 31 */   private final Timer rotateTimer = new Timer();
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 35 */     if (mc.field_71456_v.func_146158_b().func_146241_e() || mc.field_71462_r == null || mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiEditSign || mc.field_71462_r instanceof net.minecraft.client.gui.GuiScreenBook || mc.field_71462_r instanceof net.minecraft.client.gui.GuiRepair) {
/*    */       return;
/*    */     }
/*    */     
/* 39 */     if (!Component.isTyping) {
/* 40 */       List<KeyBinding> keybinds = new ArrayList<>();
/*    */       
/* 42 */       keybinds.add(mc.field_71474_y.field_74351_w);
/* 43 */       keybinds.add(mc.field_71474_y.field_74368_y);
/* 44 */       keybinds.add(mc.field_71474_y.field_74366_z);
/* 45 */       keybinds.add(mc.field_71474_y.field_74370_x);
/* 46 */       if (((Boolean)this.jump.getValue()).booleanValue()) keybinds.add(mc.field_71474_y.field_74314_A); 
/* 47 */       if (((Boolean)this.sneak.getValue()).booleanValue()) keybinds.add(mc.field_71474_y.field_74311_E);
/*    */       
/* 49 */       for (KeyBinding keyBinding : keybinds) {
/* 50 */         KeyBinding.func_74510_a(keyBinding.func_151463_i(), Keyboard.isKeyDown(keyBinding.func_151463_i()));
/*    */       }
/*    */       
/* 53 */       if (((Boolean)this.rotateArrows.getValue()).booleanValue()) {
/* 54 */         int passedms = (int)this.rotateTimer.hasPassed();
/* 55 */         this.rotateTimer.reset();
/*    */         
/* 57 */         if (passedms < 1000) {
/* 58 */           if (Keyboard.isKeyDown(200)) {
/* 59 */             mc.field_71439_g.field_70125_A -= passedms * ((Float)this.rotateArrowsSpeed.getValue()).floatValue() / 3.0F;
/* 60 */           } else if (Keyboard.isKeyDown(208)) {
/* 61 */             mc.field_71439_g.field_70125_A += passedms * ((Float)this.rotateArrowsSpeed.getValue()).floatValue() / 3.0F;
/* 62 */           } else if (Keyboard.isKeyDown(205)) {
/* 63 */             mc.field_71439_g.field_70177_z += passedms * ((Float)this.rotateArrowsSpeed.getValue()).floatValue() / 3.0F;
/* 64 */           } else if (Keyboard.isKeyDown(203)) {
/* 65 */             mc.field_71439_g.field_70177_z -= passedms * ((Float)this.rotateArrowsSpeed.getValue()).floatValue() / 3.0F;
/*    */           } 
/*    */           
/* 68 */           mc.field_71439_g.field_70125_A = MathUtilFuckYou.clamp(mc.field_71439_g.field_70125_A, -90.0F, 90.0F);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   @Listener
/*    */   public void onKeyEvent(KeyEvent event) {
/* 76 */     if (mc.field_71456_v.func_146158_b().func_146241_e() || mc.field_71462_r == null || mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiEditSign || mc.field_71462_r instanceof net.minecraft.client.gui.GuiScreenBook || mc.field_71462_r instanceof net.minecraft.client.gui.GuiRepair) {
/*    */       return;
/*    */     }
/*    */     
/* 80 */     if (!Component.isTyping)
/* 81 */       event.info = event.pressed; 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\movement\GUIMove.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */