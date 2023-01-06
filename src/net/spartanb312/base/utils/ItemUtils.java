/*     */ package net.spartanb312.base.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import me.thediamondsword5.moloch.utils.BlockUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.spartanb312.base.utils.math.Pair;
/*     */ 
/*     */ 
/*     */ public class ItemUtils
/*     */ {
/*  20 */   public static Minecraft mc = Minecraft.func_71410_x();
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getItemCount(Item item) {
/*  25 */     int count = mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(itemStack -> (itemStack.func_77973_b() == item)).mapToInt(ItemStack::func_190916_E).sum();
/*  26 */     if (mc.field_71439_g.func_184592_cb().func_77973_b() == item) {
/*  27 */       count++;
/*     */     }
/*  29 */     return count;
/*     */   }
/*     */   
/*     */   public static int findItemInHotBar(Item item) {
/*  33 */     for (int i = 0; i < 9; i++) {
/*  34 */       ItemStack itemStack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/*  35 */       if (itemStack.func_77973_b() == item) {
/*  36 */         return i;
/*     */       }
/*     */     } 
/*  39 */     return -1;
/*     */   }
/*     */   
/*     */   public static void switchToSlot(int slot) {
/*  43 */     if (mc.field_71439_g.field_71071_by.field_70461_c == slot || slot == -1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  48 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
/*  49 */     mc.field_71439_g.field_71071_by.field_70461_c = slot;
/*  50 */     mc.field_71442_b.func_78765_e();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void switchToSlotButBetter(int slot) {
/*  55 */     if (mc.field_71439_g.field_71071_by.field_70461_c == slot)
/*  56 */       return;  mc.field_71442_b.func_187100_a(slot);
/*     */   }
/*     */   
/*     */   public static int findBlockInHotBar(Block block) {
/*  60 */     return findItemInHotBar(Item.func_150898_a(block));
/*     */   }
/*     */   
/*     */   public static boolean isItemInHotbar(Item item) {
/*  64 */     boolean isItemPresent = false;
/*  65 */     for (int i = 0; i < 9; i++) {
/*  66 */       ItemStack itemStack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/*  67 */       if (itemStack.func_77973_b() == item) {
/*  68 */         isItemPresent = true;
/*     */       }
/*     */     } 
/*  71 */     return isItemPresent;
/*     */   }
/*     */   
/*     */   public static int fastestMiningTool(Block toMineBlockMaterial) {
/*  75 */     float fastestSpeed = 1.0F;
/*  76 */     int theSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/*     */     
/*  78 */     for (int i = 0; i < 9; i++) {
/*  79 */       ItemStack itemStack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/*     */       
/*  81 */       if (!itemStack.field_190928_g && (itemStack.func_77973_b() instanceof net.minecraft.item.ItemTool || itemStack.func_77973_b() instanceof net.minecraft.item.ItemSword || itemStack.func_77973_b() instanceof net.minecraft.item.ItemHoe || itemStack.func_77973_b() instanceof net.minecraft.item.ItemShears)) {
/*     */ 
/*     */         
/*  84 */         float mineSpeed = BlockUtil.blockBreakSpeed(toMineBlockMaterial.func_176223_P(), itemStack);
/*     */         
/*  86 */         if (mineSpeed > fastestSpeed) {
/*  87 */           fastestSpeed = mineSpeed;
/*  88 */           theSlot = i;
/*     */         } 
/*     */       } 
/*     */     } 
/*  92 */     return theSlot;
/*     */   }
/*     */   
/*     */   public static boolean isItemInInventory(Item item) {
/*  96 */     for (Slot slot : mc.field_71439_g.field_71069_bz.field_75151_b) {
/*  97 */       if (slot.func_75211_c().func_77973_b() == item)
/*  98 */         return true; 
/*     */     } 
/* 100 */     return false;
/*     */   }
/*     */   
/*     */   public static int itemSlotIDinInventory(Item item) {
/* 104 */     for (int i = 0; i < 45; i++) {
/* 105 */       if (((Slot)mc.field_71439_g.field_71069_bz.field_75151_b.get(i)).func_75211_c().func_77973_b() == item)
/* 106 */         return i; 
/*     */     } 
/* 108 */     return 99999;
/*     */   }
/*     */   
/*     */   public static void swapItemFromInvToHotBar(Item item, int hotBarSlot) {
/* 112 */     int slotID = itemSlotIDinInventory(item);
/*     */     
/* 114 */     if (slotID != 99999) {
/* 115 */       mc.field_71442_b.func_187098_a(0, slotID, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 116 */       mc.field_71442_b.func_187098_a(0, hotBarSlot + 36, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 117 */       mc.field_71442_b.func_187098_a(0, slotID, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/* 118 */       mc.field_71442_b.func_78765_e();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int findItemInInv(Item item) {
/* 123 */     for (Pair<Integer, ItemStack> data : getInventoryAndHotbarSlots()) {
/* 124 */       if (((ItemStack)data.b).func_77973_b() == item) {
/* 125 */         return ((Integer)data.a).intValue();
/*     */       }
/*     */     } 
/* 128 */     return -999;
/*     */   }
/*     */   
/*     */   public static List<Pair<Integer, ItemStack>> getInventoryAndHotbarSlots() {
/* 132 */     return getInventorySlots(9, 44);
/*     */   }
/*     */   
/*     */   private static List<Pair<Integer, ItemStack>> getInventorySlots(int current, int last) {
/* 136 */     List<Pair<Integer, ItemStack>> invSlots = new ArrayList<>();
/* 137 */     while (current <= last) {
/* 138 */       invSlots.add(new Pair(Integer.valueOf(current), mc.field_71439_g.field_71069_bz.func_75138_a().get(current)));
/* 139 */       current++;
/*     */     } 
/* 141 */     return invSlots;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\ItemUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */