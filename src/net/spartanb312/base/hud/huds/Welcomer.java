/*    */ package net.spartanb312.base.hud.huds;
/*    */ 
/*    */ import me.thediamondsword5.moloch.module.modules.other.NameSpoof;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.spartanb312.base.client.FontManager;
/*    */ import net.spartanb312.base.client.GUIManager;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.engine.AsyncRenderer;
/*    */ import net.spartanb312.base.hud.HUDModule;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ 
/*    */ @ModuleInfo(name = "Welcomer", category = Category.HUD)
/*    */ public class Welcomer
/*    */   extends HUDModule {
/* 18 */   public Setting<Boolean> shadow = setting("Shadow", true).des("Draw Shadow Under Welcome Message");
/*    */   
/*    */   public Welcomer() {
/* 21 */     this.asyncRenderer = new AsyncRenderer()
/*    */       {
/*    */         public void onUpdate(ScaledResolution resolution, int mouseX, int mouseY)
/*    */         {
/* 25 */           String text = "Welcome " + (ModuleManager.getModule(NameSpoof.class).isEnabled() ? (String)NameSpoof.INSTANCE.name.getValue() : Module.mc.field_71439_g.func_70005_c_()) + "! Have a nice day :)";
/*    */           
/* 27 */           drawAsyncString(text, Welcomer.this.x, Welcomer.this.y, GUIManager.getColor3I(), ((Boolean)Welcomer.this.shadow.getValue()).booleanValue());
/* 28 */           Welcomer.this.width = FontManager.getWidthHUD(text);
/* 29 */           Welcomer.this.height = FontManager.getHeight();
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public void onHUDRender(ScaledResolution resolution) {
/* 36 */     this.asyncRenderer.onRender();
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\hud\huds\Welcomer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */