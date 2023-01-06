/*    */ package net.spartanb312.base.module.modules.client;
/*    */ 
/*    */ import me.thediamondsword5.moloch.module.modules.client.Particles;
/*    */ import me.thediamondsword5.moloch.utils.graphics.ParticleUtil;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.spartanb312.base.client.ConfigManager;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.gui.HUDEditorFinal;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "HUDEditor", category = Category.CLIENT, keyCode = 41, description = "Move shit around in HUD")
/*    */ public class HUDEditor extends Module {
/* 16 */   public int flag = 0;
/*    */   
/*    */   public static HUDEditor instance;
/*    */   
/*    */   public HUDEditor() {
/* 21 */     instance = this;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 27 */     if (mc.field_71439_g != null && 
/* 28 */       !(mc.field_71462_r instanceof HUDEditorFinal)) {
/* 29 */       mc.func_147108_a((GuiScreen)new HUDEditorFinal());
/* 30 */       if (((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue()) {
/* 31 */         this.flag = 1;
/*    */       }
/*    */       
/* 34 */       this.moduleEnableFlag = true;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 41 */     this.moduleDisableFlag = true;
/*    */     
/* 43 */     if (!((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue()) {
/* 44 */       if (Particles.INSTANCE.isEnabled()) {
/* 45 */         ParticleUtil.clearParticles();
/*    */       }
/* 47 */       if (mc.field_71462_r instanceof HUDEditorFinal) {
/* 48 */         mc.func_147108_a(null);
/*    */       }
/*    */     } 
/* 51 */     ConfigManager.saveAll();
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\module\modules\client\HUDEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */