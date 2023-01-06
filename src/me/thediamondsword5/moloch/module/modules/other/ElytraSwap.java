/*    */ package me.thediamondsword5.moloch.module.modules.other;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.ClickType;
/*    */ import net.minecraft.inventory.Slot;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.module.Module;
/*    */ import net.spartanb312.base.utils.ItemUtils;
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "ElytraSwap", category = Category.OTHER, description = "If currently wearing chestplate, replaces it with elytra. If currently wearing elytra, replaces it with chestplate")
/*    */ public class ElytraSwap extends Module {
/* 16 */   Setting<Boolean> spoofNoMove = setting("SpoofNoMotion", true).des("Spoof 0 movement while switching items");
/*    */   
/*    */   private double prevMotionX;
/*    */   private double prevMotionY;
/*    */   private double prevMotionZ;
/*    */   
/*    */   public void onRenderTick() {
/*    */     int slotID;
/* 24 */     if (mc.field_71439_g == null || 
/* 25 */       !ItemUtils.isItemInInventory(Items.field_185160_cR) || (!ItemUtils.isItemInInventory((Item)Items.field_151163_ad) && !ItemUtils.isItemInInventory((Item)Items.field_151030_Z) && !ItemUtils.isItemInInventory((Item)Items.field_151171_ah) && !ItemUtils.isItemInInventory((Item)Items.field_151023_V) && !ItemUtils.isItemInInventory((Item)Items.field_151027_R))) {
/*    */       return;
/*    */     }
/*    */ 
/*    */     
/* 30 */     if (((ItemStack)mc.field_71439_g.field_71071_by.field_70460_b.get(2)).func_77973_b() == Items.field_151163_ad || ((ItemStack)mc.field_71439_g.field_71071_by.field_70460_b
/* 31 */       .get(2)).func_77973_b() == Items.field_151030_Z || ((ItemStack)mc.field_71439_g.field_71071_by.field_70460_b
/* 32 */       .get(2)).func_77973_b() == Items.field_151171_ah || ((ItemStack)mc.field_71439_g.field_71071_by.field_70460_b
/* 33 */       .get(2)).func_77973_b() == Items.field_151023_V || ((ItemStack)mc.field_71439_g.field_71071_by.field_70460_b
/* 34 */       .get(2)).func_77973_b() == Items.field_151027_R) {
/* 35 */       slotID = ItemUtils.itemSlotIDinInventory(Items.field_185160_cR);
/*    */     } else {
/*    */       
/* 38 */       slotID = findArmorSlotInInv();
/*    */     } 
/*    */     
/* 41 */     if (slotID != 99999) {
/* 42 */       if (((Boolean)this.spoofNoMove.getValue()).booleanValue()) {
/* 43 */         this.prevMotionX = mc.field_71439_g.field_70159_w;
/* 44 */         this.prevMotionY = mc.field_71439_g.field_70181_x;
/* 45 */         this.prevMotionZ = mc.field_71439_g.field_70179_y;
/* 46 */         mc.field_71439_g.field_70159_w = 0.0D;
/* 47 */         mc.field_71439_g.field_70181_x = 0.0D;
/* 48 */         mc.field_71439_g.field_70179_y = 0.0D;
/*    */       } 
/*    */       
/* 51 */       mc.field_71442_b.func_187098_a(0, slotID, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 52 */       mc.field_71442_b.func_187098_a(0, 6, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 53 */       mc.field_71442_b.func_187098_a(0, slotID, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 54 */       mc.field_71442_b.func_78765_e();
/*    */       
/* 56 */       if (((Boolean)this.spoofNoMove.getValue()).booleanValue()) {
/* 57 */         mc.field_71439_g.field_70159_w = this.prevMotionX;
/* 58 */         mc.field_71439_g.field_70181_x = this.prevMotionY;
/* 59 */         mc.field_71439_g.field_70179_y = this.prevMotionZ;
/*    */       } 
/*    */     } 
/*    */     
/* 63 */     toggle();
/*    */   }
/*    */   
/*    */   private int findArmorSlotInInv() {
/* 67 */     for (int i = 0; i < 45; i++) {
/* 68 */       if (((Slot)mc.field_71439_g.field_71069_bz.field_75151_b.get(i)).func_75211_c().func_77973_b() == Items.field_151163_ad || ((Slot)mc.field_71439_g.field_71069_bz.field_75151_b
/* 69 */         .get(i)).func_75211_c().func_77973_b() == Items.field_151030_Z || ((Slot)mc.field_71439_g.field_71069_bz.field_75151_b
/* 70 */         .get(i)).func_75211_c().func_77973_b() == Items.field_151171_ah || ((Slot)mc.field_71439_g.field_71069_bz.field_75151_b
/* 71 */         .get(i)).func_75211_c().func_77973_b() == Items.field_151023_V || ((Slot)mc.field_71439_g.field_71069_bz.field_75151_b
/* 72 */         .get(i)).func_75211_c().func_77973_b() == Items.field_151027_R)
/* 73 */         return i; 
/*    */     } 
/* 75 */     return 99999;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\other\ElytraSwap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */