/*    */ package me.thediamondsword5.moloch.module.modules.combat;
/*    */ 
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*    */ import net.minecraft.network.play.client.CPacketPlayer;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ import net.spartanb312.base.utils.ItemUtils;
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "SilentXP", category = Category.COMBAT, description = "Uses experience through packets")
/*    */ public class SilentXP
/*    */   extends Module {
/* 20 */   public Setting<Integer> lookPitch = setting("Look Pitch", 90, 0, 100).des("How much you look down");
/* 21 */   public Setting<Boolean> silentRotate = setting("Silent Rotate", false);
/* 22 */   public Setting<Integer> delay = setting("Delay", 0, 0, 5);
/*    */   
/*    */   private int delay_count;
/*    */   
/*    */   int prvSlot;
/*    */   
/*    */   public void onEnable() {
/* 29 */     this.delay_count = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 34 */     int oldPitch = (int)mc.field_71439_g.field_70125_A;
/* 35 */     this.prvSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 36 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(ItemUtils.findItemInHotBar(Items.field_151062_by)));
/* 37 */     if (!((Boolean)this.silentRotate.getValue()).booleanValue()) {
/* 38 */       mc.field_71439_g.field_70125_A = ((Integer)this.lookPitch.getValue()).intValue();
/*    */     }
/* 40 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(mc.field_71439_g.field_70177_z, ((Integer)this.lookPitch.getValue()).intValue(), true));
/* 41 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/* 42 */     if (!((Boolean)this.silentRotate.getValue()).booleanValue()) {
/* 43 */       mc.field_71439_g.field_70125_A = oldPitch;
/*    */     }
/* 45 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(mc.field_71439_g.field_70177_z, oldPitch, true));
/* 46 */     mc.field_71439_g.field_71071_by.field_70461_c = this.prvSlot;
/* 47 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.prvSlot));
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\combat\SilentXP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */