/*     */ package me.thediamondsword5.moloch.module.modules.movement;
/*     */ 
/*     */ import me.thediamondsword5.moloch.event.events.player.PlayerUpdateMoveEvent;
/*     */ import me.thediamondsword5.moloch.event.events.player.UpdateTimerEvent;
/*     */ import me.thediamondsword5.moloch.utils.BlockUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraftforge.client.event.InputUpdateEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.event.Listener;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.network.PacketEvent;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "NoSlow", category = Category.MOVEMENT, description = "Prevent slowing down")
/*     */ public class NoSlow
/*     */   extends Module {
/*     */   public static NoSlow instance;
/*     */   boolean sneakingFlag;
/*  31 */   Setting<ItemMode> itemMode = setting("ItemMode", ItemMode.Normal).des("No slow mode for items");
/*  32 */   Setting<Boolean> items = setting("Items", true).des("No slowing down on item use");
/*  33 */   public Setting<Boolean> soulSand = setting("SoulSand", false).des("No slowing down on soul sand");
/*     */   
/*  35 */   Setting<Boolean> slime = setting("Slime", false).des("No slowing down on slime blocks");
/*  36 */   public Setting<Boolean> cobweb = setting("Cobwebs", false).des("No slowing down on cobwebs");
/*     */   
/*  38 */   public Setting<Boolean> cobwebTimer = setting("CobwebTimer", false).des("Uses timer to boost you through webs").whenTrue(this.cobweb);
/*  39 */   Setting<Float> cobwebTimerSpeed = setting("CobwebTimerSpeed", 10.0F, 1.0F, 15.0F).des("Speed of timer in cobweb").whenTrue(this.cobweb).whenTrue(this.cobwebTimer);
/*  40 */   Setting<Boolean> sneak = setting("Sneak", false).des("No slowing down on sneaking");
/*     */   
/*     */   public NoSlow() {
/*  43 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderTick() {
/*  48 */     if (((Boolean)this.slime.getValue()).booleanValue()) { Blocks.field_180399_cE.setDefaultSlipperiness(0.4945F); }
/*  49 */     else { Blocks.field_180399_cE.setDefaultSlipperiness(0.8F); }
/*     */   
/*     */   }
/*     */   @Listener
/*     */   public void onUpdateMove(PlayerUpdateMoveEvent event) {
/*  54 */     if (this.itemMode.getValue() == ItemMode._2B2TSneak && !mc.field_71439_g.func_70093_af() && !mc.field_71439_g.func_184218_aH()) {
/*  55 */       if (mc.field_71439_g.func_184587_cr()) {
/*  56 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
/*  57 */         this.sneakingFlag = true;
/*     */       }
/*  59 */       else if (this.sneakingFlag) {
/*  60 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
/*  61 */         this.sneakingFlag = false;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void onUpdateTimer(UpdateTimerEvent event) {
/*  68 */     if (mc.field_71441_e == null || mc.field_71439_g == null)
/*     */       return; 
/*  70 */     BlockPos playerPos = new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v));
/*  71 */     if (((Boolean)this.cobweb.getValue()).booleanValue() && ((Boolean)this.cobwebTimer.getValue()).booleanValue() && (mc.field_71441_e.func_180495_p(BlockUtil.extrudeBlock(playerPos, EnumFacing.UP)).func_177230_c() == Blocks.field_150321_G || mc.field_71441_e.func_180495_p(playerPos).func_177230_c() == Blocks.field_150321_G || mc.field_71441_e.func_180495_p(BlockUtil.extrudeBlock(playerPos, EnumFacing.DOWN)).func_177230_c() == Blocks.field_150321_G)) {
/*  72 */       event.timerSpeed = ((Float)this.cobwebTimerSpeed.getValue()).floatValue();
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onInput(InputUpdateEvent event) {
/*  78 */     if ((((Boolean)this.items.getValue()).booleanValue() && mc.field_71439_g.func_184587_cr() && !mc.field_71439_g.func_184218_aH()) || (((Boolean)this.sneak.getValue()).booleanValue() && mc.field_71439_g.func_70093_af())) {
/*  79 */       (event.getMovementInput()).field_192832_b /= 0.2F;
/*  80 */       (event.getMovementInput()).field_78902_a /= 0.2F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  86 */     MinecraftForge.EVENT_BUS.register(this);
/*  87 */     this.moduleEnableFlag = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  92 */     MinecraftForge.EVENT_BUS.unregister(this);
/*  93 */     this.moduleDisableFlag = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPacketSend(PacketEvent.Send event) {
/*  98 */     if (event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer && ((Boolean)this.items.getValue()).booleanValue() && mc.field_71439_g.func_184587_cr() && !mc.field_71439_g.func_184218_aH()) {
/*  99 */       switch ((ItemMode)this.itemMode.getValue()) {
/*     */         case NCPStrict:
/* 101 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, new BlockPos(Math.floor(mc.field_71439_g.field_70165_t), Math.floor(mc.field_71439_g.field_70163_u), Math.floor(mc.field_71439_g.field_70161_v)), EnumFacing.DOWN));
/*     */           break;
/*     */ 
/*     */         
/*     */         case _2B2TBypass:
/* 106 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(mc.field_71439_g.field_71071_by.field_70461_c));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   enum ItemMode
/*     */   {
/* 115 */     Normal,
/* 116 */     NCPStrict,
/* 117 */     _2B2TSneak,
/* 118 */     _2B2TBypass;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\movement\NoSlow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */