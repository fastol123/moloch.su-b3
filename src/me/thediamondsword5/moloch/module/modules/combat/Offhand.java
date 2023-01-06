/*     */ package me.thediamondsword5.moloch.module.modules.combat;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.CrystalUtil;
/*     */ import net.spartanb312.base.utils.ItemUtils;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "Offhand", category = Category.COMBAT, description = "Manages items in offhand")
/*     */ public class Offhand extends Module {
/*  20 */   Setting<Boolean> crystal = setting("Crystal", true).des("Automatically switches offhand to crystals when conditions are met");
/*  21 */   Setting<Boolean> gapple = setting("Gapple", false).des("Automatically switches offhand to golden apples when conditions are met");
/*  22 */   Setting<Boolean> gappleRightClickSword = setting("GapRightClickSword", true).des("Switches offhand to gapple when right clicking with sword").whenTrue(this.gapple);
/*  23 */   Setting<Boolean> noMotion = setting("NoMotion", false).des("Stops movement while switching items");
/*  24 */   Setting<Integer> delay = setting("Delay", 50, 0, 1000).des("Delay in switching items in milliseconds");
/*  25 */   Setting<Float> totemHealth = setting("TotemHealth", 11.0F, 0.0F, 36.0F).des("Amount of health to switch back to totem from whatever else you are holding in offhand");
/*  26 */   Setting<Boolean> checkElytra = setting("CheckElytra", true).des("Switches to totem as long as you are using an elytra");
/*  27 */   Setting<Boolean> checkCrystalDamage = setting("CheckCrystalDamage", true).des("Switches to totem if you are about to take certain amount of damage from crystal");
/*  28 */   Setting<Float> maxCrystalDamage = setting("MaxCrystalDamage", 26.0F, 0.0F, 36.0F).des("Maximum amount of potential damage to switch offhand back to totem").whenTrue(this.checkCrystalDamage);
/*  29 */   Setting<Boolean> checkFallDistance = setting("CheckFallDistance", true).des("Switches to totem if fall distance is beyond a certain threshold");
/*  30 */   Setting<Float> maxFallDistance = setting("MaxFallDistance", 5.0F, 0.0F, 50.0F).des("Max amount of blocks that you can fall and not have offhand switch to totem").whenTrue(this.checkFallDistance);
/*     */   
/*  32 */   private final Timer timer = new Timer();
/*  33 */   private int toSwitchSlot = 0;
/*  34 */   private Item currentItem = Items.field_190931_a;
/*     */ 
/*     */   
/*     */   public String getModuleInfo() {
/*  38 */     return ItemUtils.getItemCount(mc.field_71439_g.func_184592_cb().func_77973_b()) + "";
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderTick() {
/*  43 */     if (this.timer.passed(((Integer)this.delay.getValue()).intValue()) && mc.field_71439_g != null) {
/*  44 */       this.toSwitchSlot = -999;
/*     */       
/*  46 */       if (ItemUtils.isItemInInventory(Items.field_190929_cY) && (mc.field_71439_g.func_110143_aJ() + mc.field_71439_g.func_110139_bj() <= ((Float)this.totemHealth.getValue()).floatValue() || (((Boolean)this.checkElytra
/*  47 */         .getValue()).booleanValue() && mc.field_71439_g.func_184582_a(EntityEquipmentSlot.CHEST).func_77973_b() == Items.field_185160_cR) || (((Boolean)this.checkCrystalDamage
/*  48 */         .getValue()).booleanValue() && CrystalUtil.getDmgSelf() > ((Float)this.maxCrystalDamage.getValue()).floatValue()) || (((Boolean)this.checkFallDistance
/*  49 */         .getValue()).booleanValue() && mc.field_71439_g.field_70143_R >= ((Float)this.maxFallDistance.getValue()).floatValue()))) {
/*     */         
/*  51 */         setOffhandItem(Items.field_190929_cY);
/*     */       }
/*  53 */       else if (ItemUtils.isItemInInventory(Items.field_151153_ao) && ((Boolean)this.gapple.getValue()).booleanValue() && 
/*  54 */         Mouse.isButtonDown(1) && (!((Boolean)this.gappleRightClickSword.getValue()).booleanValue() || mc.field_71439_g.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemSword)) {
/*     */         
/*  56 */         setOffhandItem(Items.field_151153_ao);
/*     */       }
/*  58 */       else if (ItemUtils.isItemInInventory(Items.field_185158_cP) && ((Boolean)this.crystal.getValue()).booleanValue()) {
/*     */         
/*  60 */         setOffhandItem(Items.field_185158_cP);
/*     */       
/*     */       }
/*  63 */       else if (ItemUtils.isItemInInventory(Items.field_190929_cY)) {
/*  64 */         setOffhandItem(Items.field_190929_cY);
/*  65 */       } else if (ItemUtils.isItemInInventory(Items.field_185158_cP) && ((Boolean)this.crystal.getValue()).booleanValue()) {
/*  66 */         setOffhandItem(Items.field_185158_cP);
/*  67 */       } else if (ItemUtils.isItemInInventory(Items.field_151153_ao) && ((Boolean)this.gapple.getValue()).booleanValue()) {
/*  68 */         setOffhandItem(Items.field_151153_ao);
/*     */       } 
/*     */ 
/*     */       
/*  72 */       if (mc.field_71439_g.func_184592_cb().func_77973_b() != this.currentItem && this.toSwitchSlot != -999) {
/*  73 */         double prevMotionX = mc.field_71439_g.field_70159_w;
/*  74 */         double prevMotionZ = mc.field_71439_g.field_70179_y;
/*  75 */         if (((Boolean)this.noMotion.getValue()).booleanValue()) {
/*  76 */           mc.field_71439_g.field_70159_w = 0.0D;
/*  77 */           mc.field_71439_g.field_70179_y = 0.0D;
/*  78 */           mc.field_71439_g.func_70016_h(0.0D, mc.field_71439_g.field_70181_x, 0.0D);
/*     */         } 
/*     */         
/*  81 */         boolean preSwitchIsEmpty = (mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190931_a);
/*     */         
/*  83 */         mc.field_71442_b.func_187098_a(0, this.toSwitchSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*  84 */         mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*  85 */         if (!preSwitchIsEmpty) {
/*  86 */           mc.field_71442_b.func_187098_a(0, this.toSwitchSlot, 0, ClickType.PICKUP, (EntityPlayer)mc.field_71439_g);
/*     */         }
/*  88 */         mc.field_71442_b.func_78765_e();
/*     */         
/*  90 */         if (((Boolean)this.noMotion.getValue()).booleanValue()) {
/*  91 */           mc.field_71439_g.field_70159_w = prevMotionX;
/*  92 */           mc.field_71439_g.field_70179_y = prevMotionZ;
/*  93 */           mc.field_71439_g.func_70016_h(prevMotionX, mc.field_71439_g.field_70181_x, prevMotionZ);
/*     */         } 
/*  95 */         this.timer.reset();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setOffhandItem(Item item) {
/* 101 */     this.currentItem = item;
/*     */     
/* 103 */     if (mc.field_71439_g.func_184592_cb().func_77973_b() != item) {
/* 104 */       int switchSlot = ItemUtils.findItemInInv(item);
/* 105 */       if (switchSlot != -999)
/* 106 */         this.toSwitchSlot = switchSlot; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\combat\Offhand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */