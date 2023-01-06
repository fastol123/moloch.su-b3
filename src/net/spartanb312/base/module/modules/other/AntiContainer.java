/*    */ package net.spartanb312.base.module.modules.other;
/*    */ 
/*    */ import me.thediamondsword5.moloch.utils.BlockUtil;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.event.events.network.PacketEvent;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ 
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "AntiContainer", category = Category.OTHER, description = "Avoiding opening containers")
/*    */ public class AntiContainer
/*    */   extends Module
/*    */ {
/* 20 */   Setting<Boolean> Chest = setting("Chest", true);
/* 21 */   Setting<Boolean> EnderChest = setting("EnderChest", true);
/* 22 */   Setting<Boolean> Trapped_Chest = setting("TrappedChest", true);
/* 23 */   Setting<Boolean> Hopper = setting("Hopper", true);
/* 24 */   Setting<Boolean> Dispenser = setting("Dispenser", true);
/* 25 */   Setting<Boolean> Furnace = setting("Furnace", true);
/* 26 */   Setting<Boolean> Beacon = setting("Beacon", true);
/* 27 */   Setting<Boolean> Crafting_Table = setting("CraftingTable", true);
/* 28 */   Setting<Boolean> Anvil = setting("Anvil", true);
/* 29 */   Setting<Boolean> Enchanting_table = setting("Enchantingtable", true);
/* 30 */   Setting<Boolean> Brewing_Stand = setting("BrewingStand", true);
/* 31 */   Setting<Boolean> ShulkerBox = setting("ShulkerBox", true);
/*    */ 
/*    */   
/*    */   public void onPacketSend(PacketEvent.Send packet) {
/* 35 */     if (packet.packet instanceof CPacketPlayerTryUseItemOnBlock && !BlockUtil.isPlacing) {
/* 36 */       BlockPos pos = ((CPacketPlayerTryUseItemOnBlock)packet.packet).func_187023_a();
/* 37 */       if (check(pos)) packet.cancel(); 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean check(BlockPos pos) {
/* 42 */     return ((mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150486_ae && ((Boolean)this.Chest.getValue()).booleanValue()) || (mc.field_71441_e
/* 43 */       .func_180495_p(pos).func_177230_c() == Blocks.field_150477_bB && ((Boolean)this.EnderChest.getValue()).booleanValue()) || (mc.field_71441_e
/* 44 */       .func_180495_p(pos).func_177230_c() == Blocks.field_150447_bR && ((Boolean)this.Trapped_Chest.getValue()).booleanValue()) || (mc.field_71441_e
/* 45 */       .func_180495_p(pos).func_177230_c() == Blocks.field_150438_bZ && ((Boolean)this.Hopper.getValue()).booleanValue()) || (mc.field_71441_e
/* 46 */       .func_180495_p(pos).func_177230_c() == Blocks.field_150367_z && ((Boolean)this.Dispenser.getValue()).booleanValue()) || (mc.field_71441_e
/* 47 */       .func_180495_p(pos).func_177230_c() == Blocks.field_150460_al && ((Boolean)this.Furnace.getValue()).booleanValue()) || (mc.field_71441_e
/* 48 */       .func_180495_p(pos).func_177230_c() == Blocks.field_150461_bJ && ((Boolean)this.Beacon.getValue()).booleanValue()) || (mc.field_71441_e
/* 49 */       .func_180495_p(pos).func_177230_c() == Blocks.field_150462_ai && ((Boolean)this.Crafting_Table.getValue()).booleanValue()) || (mc.field_71441_e
/* 50 */       .func_180495_p(pos).func_177230_c() == Blocks.field_150467_bQ && ((Boolean)this.Anvil.getValue()).booleanValue()) || (mc.field_71441_e
/* 51 */       .func_180495_p(pos).func_177230_c() == Blocks.field_150381_bn && ((Boolean)this.Enchanting_table.getValue()).booleanValue()) || (mc.field_71441_e
/* 52 */       .func_180495_p(pos).func_177230_c() == Blocks.field_150382_bo && ((Boolean)this.Brewing_Stand.getValue()).booleanValue()) || (mc.field_71441_e
/* 53 */       .func_180495_p(pos).func_177230_c() instanceof net.minecraft.block.BlockShulkerBox && ((Boolean)this.ShulkerBox.getValue()).booleanValue()));
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\module\modules\other\AntiContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */