/*    */ package me.thediamondsword5.moloch.module.modules.other;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.player.BlockInteractionEvent;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.event.Listener;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ 
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "NoEntityBlock", category = Category.OTHER, description = "Allows you to interact with blocks through entities")
/*    */ public class NoEntityBlock
/*    */   extends Module
/*    */ {
/* 20 */   Setting<Boolean> onlyBlocks = setting("OnlyBlocks", true).des("Only interact with blocks through entities when you can hit a block");
/* 21 */   Setting<Boolean> everything = setting("Everything", false).des("Interact with blocks through entities while anything is in your hands");
/* 22 */   Setting<Boolean> pickaxe = setting("Pickaxe", true).des("Interact with blocks through entities while holding pickaxe").whenFalse(this.everything);
/* 23 */   Setting<Boolean> sword = setting("Sword", false).des("Interact with blocks through entities while holding sword").whenFalse(this.everything);
/* 24 */   Setting<Boolean> gapple = setting("Gapple", false).des("Interact with blocks through entities while holding gapple").whenFalse(this.everything);
/* 25 */   Setting<Boolean> crystal = setting("Crystal", false).des("Interact with blocks through entities while holding crystal").whenFalse(this.everything);
/*    */   
/*    */   @Listener
/*    */   public void onBlockInteract(BlockInteractionEvent event) {
/* 29 */     RayTraceResult mouseObject = mc.field_71476_x;
/* 30 */     Item heldItem = mc.field_71439_g.func_184614_ca().func_77973_b();
/*    */     
/* 32 */     if ((!((Boolean)this.onlyBlocks.getValue()).booleanValue() || (mouseObject != null && mouseObject.field_72313_a == RayTraceResult.Type.BLOCK)) && ((
/* 33 */       (Boolean)this.everything.getValue()).booleanValue() || (((Boolean)this.pickaxe.getValue()).booleanValue() && heldItem instanceof net.minecraft.item.ItemPickaxe) || (((Boolean)this.sword.getValue()).booleanValue() && heldItem instanceof net.minecraft.item.ItemSword) || (((Boolean)this.gapple.getValue()).booleanValue() && heldItem == Items.field_151153_ao) || (((Boolean)this.crystal.getValue()).booleanValue() && heldItem == Items.field_185158_cP)))
/* 34 */       event.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\other\NoEntityBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */