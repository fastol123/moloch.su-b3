/*    */ package me.thediamondsword5.moloch.module.modules.combat;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.player.BlockBreakDelayEvent;
/*    */ import me.thediamondsword5.moloch.event.events.player.RightClickDelayEvent;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.event.Listener;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.mixin.mixins.accessor.AccessorMinecraft;
/*    */ import net.spartanb312.base.mixin.mixins.accessor.AccessorPlayerControllerMP;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ 
/*    */ @Parallel(runnable = true)
/*    */ @ModuleInfo(name = "FastUse", category = Category.COMBAT, description = "Allows you to remove delay from using some items")
/*    */ public class FastUse extends Module {
/* 24 */   Setting<Boolean> fastPlace = setting("FastPlace", false).des("Remove block placing delay");
/* 25 */   Setting<Boolean> fastBreak = setting("FastBreak", false).des("Remove block breaking delay");
/* 26 */   Setting<Boolean> crystals = setting("Crystals", false).des("Remove delay when placing end crystals");
/* 27 */   Setting<Boolean> fireworks = setting("Fireworks", false).des("Remove delay when using fireworks");
/* 28 */   Setting<Boolean> bow = setting("Bow", false).des("Modify auto release charge amt for bows");
/* 29 */   Setting<Integer> bowChargeThreshold = setting("BowChargeThreshold", 1, 0, 20).des("Amount of bow charged to auto release").whenTrue(this.bow);
/* 30 */   Setting<Boolean> xp = setting("Xp", false).des("Modify throwing experience bottle delay");
/* 31 */   Setting<Integer> xpDelay = setting("XpDelay", 0, 0, 10).des("Delay of throwing experience bottle").whenTrue(this.xp);
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 35 */     if (((Boolean)this.bow.getValue()).booleanValue() && mc.field_71439_g.func_184587_cr() && mc.field_71439_g.func_184607_cu().func_77973_b() == Items.field_151031_f && mc.field_71439_g.func_184612_cw() >= ((Integer)this.bowChargeThreshold.getValue()).intValue()) {
/* 36 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, mc.field_71439_g.func_174811_aO()));
/* 37 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
/* 38 */       mc.field_71439_g.func_184597_cx();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Listener
/*    */   public void blockBreakDelaySet(BlockBreakDelayEvent event) {
/* 44 */     if (((Boolean)this.fastBreak.getValue()).booleanValue()) {
/* 45 */       ((AccessorPlayerControllerMP)mc.field_71442_b).setBlockHitDelay(0);
/*    */     }
/*    */   }
/*    */   
/*    */   @Listener
/*    */   public void rightClickDelaySet(RightClickDelayEvent event) {
/* 51 */     Item heldItem = mc.field_71439_g.func_184614_ca().func_77973_b();
/* 52 */     if (heldItem instanceof net.minecraft.item.ItemBlock && ((Boolean)this.fastPlace.getValue()).booleanValue()) {
/* 53 */       ((AccessorMinecraft)mc).setRightClickDelayTimer(0);
/* 54 */     } else if (heldItem instanceof net.minecraft.item.ItemEndCrystal && ((Boolean)this.crystals.getValue()).booleanValue()) {
/* 55 */       ((AccessorMinecraft)mc).setRightClickDelayTimer(0);
/* 56 */     } else if (heldItem instanceof net.minecraft.item.ItemFirework && ((Boolean)this.fireworks.getValue()).booleanValue()) {
/* 57 */       ((AccessorMinecraft)mc).setRightClickDelayTimer(0);
/* 58 */     } else if (heldItem instanceof net.minecraft.item.ItemExpBottle && ((Boolean)this.xp.getValue()).booleanValue()) {
/* 59 */       ((AccessorMinecraft)mc).setRightClickDelayTimer(((Integer)this.xpDelay.getValue()).intValue());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\combat\FastUse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */