/*    */ package me.thediamondsword5.moloch.module.modules.visuals;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.render.FOVItemModifyEvent;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.common.KeyBind;
/*    */ import net.spartanb312.base.core.event.Listener;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "FOV", category = Category.VISUALS, description = "Change FOV beyond what minecraft normally allows")
/*    */ public class FOV
/*    */   extends Module {
/* 17 */   Setting<Boolean> modifyItemFOV = setting("ModifyItemFOV", false).des("Modifies your heldmodel with your FOV");
/* 18 */   Setting<KeyBind> zoomBind = setting("ZoomBind", subscribeKey(new KeyBind(0, null))).des("Keybind for FOV zoom since the item FOV overrides the zoom fov").whenTrue(this.modifyItemFOV);
/* 19 */   Setting<Float> fov = setting("FOV", 120.0F, 0.0F, 180.0F);
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 23 */     mc.field_71474_y.field_74334_X = 100.0F;
/* 24 */     this.moduleDisableFlag = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 29 */     if (mc.field_71474_y.field_74334_X != ((Float)this.fov.getValue()).floatValue()) {
/* 30 */       mc.field_71474_y.field_74334_X = ((Float)this.fov.getValue()).floatValue();
/*    */     }
/*    */   }
/*    */   
/*    */   @Listener
/*    */   public void onFOVModifyItems(FOVItemModifyEvent event) {
/* 36 */     if (!((Boolean)this.modifyItemFOV.getValue()).booleanValue() || Keyboard.isKeyDown(((KeyBind)this.zoomBind.getValue()).getKeyCode())) {
/*    */       return;
/*    */     }
/*    */     
/* 40 */     event.cancel();
/* 41 */     event.fov = ((Float)this.fov.getValue()).floatValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\visuals\FOV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */