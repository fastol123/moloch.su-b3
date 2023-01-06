/*     */ package me.thediamondsword5.moloch.module.modules.combat;
/*     */ 
/*     */ import me.thediamondsword5.moloch.utils.BlockUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.spartanb312.base.client.ModuleManager;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.notification.NotificationManager;
/*     */ import net.spartanb312.base.utils.CrystalUtil;
/*     */ import net.spartanb312.base.utils.EntityUtil;
/*     */ import net.spartanb312.base.utils.ItemUtils;
/*     */ import net.spartanb312.base.utils.RotationUtil;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "SelfBlock", category = Category.COMBAT, description = "Fuck a block")
/*     */ public class SelfBlock
/*     */   extends Module
/*     */ {
/*  36 */   private static final Timer timer = new Timer();
/*     */   double prevPlayerPosY;
/*     */   int prevSlot;
/*     */   BlockPos pos;
/*     */   boolean flag;
/*     */   double originalPosX;
/*     */   double originalPosY;
/*     */   double originalPosZ;
/*     */   Item originalItem;
/*  45 */   int failedSelfBlockNum = 0;
/*     */   
/*  47 */   Setting<BlockMode> blockMode = setting("BlockMode", BlockMode.Obsidian);
/*  48 */   Setting<Mode> selfBlockMode = setting("SelfBlockMode", Mode.Packet).des("Type of selfblock");
/*  49 */   Setting<Boolean> switchPlus = setting("SwitchAlternate", true).des("Uses different autoswitch to switch to target block in hotbar (is supposed to bypass a cooldown or smt but it cant switch properly while youre holding a tool like pickaxes, swords, bows, etc...)");
/*  50 */   Setting<Boolean> rotate = setting("Rotate", true).des("Rotate to burrow");
/*  51 */   Setting<Boolean> spoofOnGround = setting("SpoofOnGround", true).des("Spoof being on ground").only(v -> (this.selfBlockMode.getValue() == Mode.Packet));
/*  52 */   Setting<Boolean> breakCrystals = setting("BreakCrystals", true).des("If an end crystal's hitbox is blocking your place positions, try and break it");
/*  53 */   Setting<Boolean> antiSuicideCrystal = setting("AntiSuicideCrystal", true).des("Breaks crystal as long as it doesn't make you go below a certain health amount").whenTrue(this.breakCrystals);
/*  54 */   Setting<Float> minHealthRemaining = setting("MinHealthRemain", 8.0F, 1.0F, 36.0F).des("Min health that crystal should leave you with after you break it").whenTrue(this.antiSuicideCrystal).whenTrue(this.breakCrystals);
/*  55 */   Setting<Float> maxCrystalDamage = setting("MaxCrystalDamage", 11.0F, 0.0F, 36.0F).des("Don't break a crystal if it's damage to you exceeds this amount").whenFalse(this.antiSuicideCrystal).whenTrue(this.breakCrystals);
/*  56 */   Setting<Boolean> toggle = setting("Toggle", true).des("Disable when done").only(v -> (this.selfBlockMode.getValue() != Mode.NoLag));
/*     */   
/*  58 */   Setting<Integer> delay = setting("Delay", 292, 0, 1000).des("No toggle block place delay").only(v -> (this.selfBlockMode.getValue() != Mode.NoLag)).whenFalse(this.toggle);
/*  59 */   Setting<Boolean> antiStuck = setting("AntiStuck", true).des("Stops trying to place when stuck").only(v -> (this.selfBlockMode.getValue() != Mode.NoLag)).whenFalse(this.toggle);
/*  60 */   Setting<Boolean> waitPlace = setting("WaitPlace", false).des("Waits until able to place then tries to place").only(v -> (this.selfBlockMode.getValue() != Mode.NoLag)).whenFalse(this.toggle);
/*  61 */   Setting<Integer> maxTry = setting("MaxTry", 4, 1, 20).only(v -> (this.selfBlockMode.getValue() != Mode.NoLag)).whenFalse(this.toggle).whenFalse(this.waitPlace).whenTrue(this.antiStuck);
/*  62 */   Setting<DisableMode> disableMode = setting("DisableCheckMode", DisableMode.Both).des("No toggle auto disable check mode").only(v -> (this.selfBlockMode.getValue() != Mode.NoLag)).whenFalse(this.toggle);
/*  63 */   Setting<Double> yPower = setting("YPower", 0.9D, -10.0D, 10.0D).des("Y motion").only(v -> (this.selfBlockMode.getValue() != Mode.NoLag));
/*  64 */   Setting<Boolean> center = setting("Center", false).des("Center player on burrow").only(v -> (this.selfBlockMode.getValue() != Mode.NoLag));
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  68 */     this.moduleEnableFlag = true;
/*  69 */     this.originalItem = mc.field_71439_g.func_184614_ca().func_77973_b();
/*     */     
/*  71 */     this.prevPlayerPosY = mc.field_71439_g.field_70163_u;
/*  72 */     this.pos = new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(Math.round(mc.field_71439_g.field_70163_u)), Math.floor(mc.field_71439_g.field_70161_v));
/*     */     
/*  74 */     if (this.selfBlockMode.getValue() != Mode.Packet) {
/*  75 */       mc.field_71439_g.func_70664_aZ();
/*     */     }
/*     */     
/*  78 */     this.originalPosX = mc.field_71439_g.field_70165_t;
/*  79 */     this.originalPosY = mc.field_71439_g.field_70163_u;
/*  80 */     this.originalPosZ = mc.field_71439_g.field_70161_v;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  85 */     if (!ItemUtils.isItemInHotbar((this.blockMode.getValue() == BlockMode.WitherSkull) ? Items.field_151144_bL : Item.func_150898_a(burrowBlock()))) {
/*  86 */       NotificationManager.error("No blocks to place!");
/*  87 */       ModuleManager.getModule(SelfBlock.class).disable();
/*     */       
/*     */       return;
/*     */     } 
/*  91 */     if (((Boolean)this.breakCrystals.getValue()).booleanValue()) {
/*  92 */       CrystalUtil.breakBlockingCrystals(mc.field_71441_e.func_180495_p(this.pos).func_185918_c((World)mc.field_71441_e, this.pos), ((Boolean)this.antiSuicideCrystal.getValue()).booleanValue(), ((Float)this.minHealthRemaining.getValue()).floatValue(), ((Float)this.maxCrystalDamage.getValue()).floatValue(), ((Boolean)this.rotate.getValue()).booleanValue());
/*     */     }
/*     */     
/*  95 */     switch ((Mode)this.selfBlockMode.getValue()) {
/*     */       case Obsidian:
/*  97 */         if (!((Boolean)this.toggle.getValue()).booleanValue() && this.selfBlockMode.getValue() == Mode.Packet) {
/*  98 */           if (!EntityUtil.isBurrowed((Entity)mc.field_71439_g)) {
/*     */             
/* 100 */             if (((Boolean)this.antiStuck.getValue()).booleanValue() ? (this.failedSelfBlockNum >= ((Integer)this.maxTry.getValue()).intValue()) : (this.failedSelfBlockNum == -999)) {
/* 101 */               disable();
/*     */             }
/* 103 */             else if (timer.passed(((Integer)this.delay.getValue()).intValue()) && (!((Boolean)this.waitPlace.getValue()).booleanValue() || EntityUtil.isPosPlaceable(new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(Math.round(mc.field_71439_g.field_70163_u)), Math.floor(mc.field_71439_g.field_70161_v))))) {
/*     */               
/* 105 */               if (((Boolean)this.center.getValue()).booleanValue()) EntityUtil.setCenter(); 
/* 106 */               posPacket();
/* 107 */               packetBurrow();
/* 108 */               timer.reset();
/* 109 */               if (((Boolean)this.antiStuck.getValue()).booleanValue()) {
/* 110 */                 BlockPos pos = new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u + 0.2D), Math.floor(mc.field_71439_g.field_70161_v));
/* 111 */                 if (mc.field_71441_e.func_180495_p(pos).func_177230_c() != ((this.blockMode.getValue() == BlockMode.WitherSkull) ? (Block)Blocks.field_150465_bP : burrowBlock())) {
/* 112 */                   this.failedSelfBlockNum++;
/*     */                 }
/*     */               }
/*     */             
/*     */             }
/*     */           
/* 118 */           } else if (((this.originalPosX > (EntityUtil.selfCenterPos()).field_72450_a + 0.6D || this.originalPosX < (EntityUtil.selfCenterPos()).field_72450_a - 0.6D || this.originalPosZ > (EntityUtil.selfCenterPos()).field_72449_c + 0.6D || this.originalPosZ < (EntityUtil.selfCenterPos()).field_72449_c - 0.6D) && (this.disableMode.getValue() == DisableMode.Horizontal || this.disableMode.getValue() == DisableMode.Both)) || (this.originalPosY != mc.field_71439_g.field_70163_u && (this.disableMode.getValue() == DisableMode.Vertical || this.disableMode.getValue() == DisableMode.Both))) {
/* 119 */             disable();
/*     */           } 
/*     */         }
/*     */         
/* 123 */         if (((Boolean)this.toggle.getValue()).booleanValue()) {
/* 124 */           if (((Boolean)this.center.getValue()).booleanValue()) EntityUtil.setCenter(); 
/* 125 */           if (this.blockMode.getValue() != BlockMode.WitherSkull) posPacket(); 
/* 126 */           packetBurrow();
/* 127 */           disable();
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case EnderChest:
/* 134 */         if (mc.field_71439_g.field_70122_E) {
/* 135 */           mc.field_71439_g.func_70664_aZ();
/*     */         }
/*     */         
/* 138 */         if (mc.field_71439_g.field_70163_u >= this.prevPlayerPosY + 1.04D) {
/* 139 */           this.prevSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 140 */           if (!((Boolean)this.switchPlus.getValue()).booleanValue() || (((Boolean)this.switchPlus.getValue()).booleanValue() && mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_190931_a))
/* 141 */           { if (this.blockMode.getValue() == BlockMode.WitherSkull) { ItemUtils.switchToSlot(ItemUtils.findItemInHotBar(Items.field_151144_bL)); }
/* 142 */             else { ItemUtils.switchToSlot(ItemUtils.findBlockInHotBar(burrowBlock())); }
/*     */             
/*     */              }
/* 145 */           else if (this.blockMode.getValue() == BlockMode.WitherSkull) { ItemUtils.switchToSlotButBetter(ItemUtils.findItemInHotBar(Items.field_151144_bL)); }
/* 146 */           else { ItemUtils.switchToSlotButBetter(ItemUtils.findBlockInHotBar(burrowBlock())); }
/*     */ 
/*     */           
/* 149 */           BlockUtil.placeBlock(BlockUtil.extrudeBlock(this.pos, EnumFacing.DOWN), EnumFacing.UP, true, false, ((Boolean)this.rotate.getValue()).booleanValue());
/*     */           
/* 151 */           if (!((Boolean)this.switchPlus.getValue()).booleanValue() || (((Boolean)this.switchPlus.getValue()).booleanValue() && mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_190931_a))
/* 152 */           { ItemUtils.switchToSlot(this.prevSlot);
/*     */              }
/*     */           
/* 155 */           else if (this.blockMode.getValue() == BlockMode.WitherSkull) { ItemUtils.switchToSlotButBetter(ItemUtils.findItemInHotBar(Items.field_151144_bL)); }
/* 156 */           else { ItemUtils.switchToSlotButBetter(ItemUtils.findBlockInHotBar(burrowBlock())); }
/*     */           
/* 158 */           mc.field_71439_g.field_70181_x = 0.0D;
/* 159 */           RotationUtil.resetRotationBlock();
/* 160 */           this.flag = true;
/*     */         } 
/*     */         
/* 163 */         if (!mc.field_71439_g.field_70122_E && this.flag) {
/* 164 */           this.flag = false;
/* 165 */           disable();
/*     */         } 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 173 */     this.failedSelfBlockNum = 0;
/* 174 */     this.moduleDisableFlag = true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void posPacket() {
/* 179 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.42D, mc.field_71439_g.field_70161_v, ((Boolean)this.spoofOnGround.getValue()).booleanValue()));
/* 180 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.75D, mc.field_71439_g.field_70161_v, ((Boolean)this.spoofOnGround.getValue()).booleanValue()));
/* 181 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.01D, mc.field_71439_g.field_70161_v, ((Boolean)this.spoofOnGround.getValue()).booleanValue()));
/* 182 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.16D, mc.field_71439_g.field_70161_v, ((Boolean)this.spoofOnGround.getValue()).booleanValue()));
/*     */   }
/*     */   
/*     */   private void packetBurrow() {
/* 186 */     this.prevSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 187 */     if (!((Boolean)this.switchPlus.getValue()).booleanValue() || (((Boolean)this.switchPlus.getValue()).booleanValue() && mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_190931_a))
/* 188 */     { if (this.blockMode.getValue() == BlockMode.WitherSkull) { ItemUtils.switchToSlot(ItemUtils.findItemInHotBar(Items.field_151144_bL)); }
/* 189 */       else { ItemUtils.switchToSlot(ItemUtils.findBlockInHotBar(burrowBlock())); }
/*     */       
/*     */        }
/* 192 */     else if (this.blockMode.getValue() == BlockMode.WitherSkull) { ItemUtils.switchToSlotButBetter(ItemUtils.findItemInHotBar(Items.field_151144_bL)); }
/* 193 */     else { ItemUtils.switchToSlotButBetter(ItemUtils.findBlockInHotBar(burrowBlock())); }
/*     */ 
/*     */     
/* 196 */     this.pos = new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(Math.round(mc.field_71439_g.field_70163_u)), Math.floor(mc.field_71439_g.field_70161_v));
/* 197 */     BlockUtil.placeBlock(BlockUtil.extrudeBlock(this.pos, EnumFacing.DOWN), EnumFacing.UP, true, false, ((Boolean)this.rotate.getValue()).booleanValue());
/* 198 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + ((Double)this.yPower.getValue()).doubleValue(), mc.field_71439_g.field_70161_v, false));
/*     */     
/* 200 */     if (!((Boolean)this.switchPlus.getValue()).booleanValue() || (((Boolean)this.switchPlus.getValue()).booleanValue() && this.originalItem == Items.field_190931_a))
/* 201 */     { ItemUtils.switchToSlot(this.prevSlot);
/*     */        }
/*     */     
/* 204 */     else if (this.blockMode.getValue() == BlockMode.WitherSkull) { ItemUtils.switchToSlotButBetter(ItemUtils.findItemInHotBar(Items.field_151144_bL)); }
/* 205 */     else { ItemUtils.switchToSlotButBetter(ItemUtils.findBlockInHotBar(burrowBlock())); }
/*     */   
/*     */   }
/*     */   
/*     */   private Block burrowBlock() {
/* 210 */     switch ((BlockMode)this.blockMode.getValue()) { case Obsidian:
/* 211 */         return Blocks.field_150343_Z;
/*     */       case EnderChest:
/* 213 */         return Blocks.field_150477_bB;
/*     */       case EndRod:
/* 215 */         return Blocks.field_185764_cQ; }
/*     */     
/* 217 */     return Blocks.field_150343_Z;
/*     */   }
/*     */   
/*     */   enum BlockMode {
/* 221 */     Obsidian,
/* 222 */     EnderChest,
/* 223 */     WitherSkull,
/* 224 */     EndRod;
/*     */   }
/*     */   
/*     */   enum Mode {
/* 228 */     Packet,
/* 229 */     NoLag;
/*     */   }
/*     */   
/*     */   enum DisableMode {
/* 233 */     Horizontal,
/* 234 */     Vertical,
/* 235 */     Both;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\combat\SelfBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */