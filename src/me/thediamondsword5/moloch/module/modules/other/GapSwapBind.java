/*    */ package me.thediamondsword5.moloch.module.modules.other;
/*    */ 
/*    */ import net.minecraft.init.Items;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.common.KeyBind;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ import net.spartanb312.base.utils.ItemUtils;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "GapSwapBind", category = Category.OTHER, description = "Bind a key to force you to hold a gap as long as it's down (this is useless for everyone except me bc the ca i use is stupid af and doesn't have a swap back option)")
/*    */ public class GapSwapBind
/*    */   extends Module {
/* 17 */   Setting<KeyBind> bind = setting("GapBind", subscribeKey(new KeyBind(0, null))).des("Key to bind to switch to gap");
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 21 */     if (Keyboard.isKeyDown(((KeyBind)this.bind.getValue()).getKeyCode()) && mc.field_71439_g.func_184614_ca().func_77973_b() != Items.field_151153_ao)
/* 22 */       ItemUtils.switchToSlot(ItemUtils.findItemInHotBar(Items.field_151153_ao)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\other\GapSwapBind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */