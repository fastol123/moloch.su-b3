/*     */ package me.thediamondsword5.moloch.module.modules.combat;
/*     */ 
/*     */ import me.thediamondsword5.moloch.event.events.player.PlayerAttackEvent;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketAnimation;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.spartanb312.base.client.ModuleManager;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.event.Listener;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "Criticals", category = Category.COMBAT, description = "Force attacks to be criticals")
/*     */ public class Criticals
/*     */   extends Module
/*     */ {
/*     */   public boolean flag = false;
/*     */   private boolean flag2 = false;
/*     */   private Entity target;
/*     */   public static Criticals INSTANCE;
/*  29 */   public Setting<Boolean> packetMode = setting("Packet", true).des("Use packets to force attacks to be criticals instead of mini jumps");
/*  30 */   Setting<Float> jumpHeight = setting("JumpHeight", 0.3F, 0.1F, 0.5F).des("Height of jump for criticals").whenFalse(this.packetMode);
/*  31 */   Setting<Boolean> onlyWeapon = setting("OnlyWeapon", true).des("Only force criticals when holding a sword or axe");
/*  32 */   Setting<Boolean> checkRaytrace = setting("CheckRaytrace", false).des("Only force criticals when your mouse is over the target entity").whenFalse(this.packetMode);
/*  33 */   public Setting<Boolean> disableWhenAura = setting("AuraNoCrits", false).des("Disable criticals when aura is actively attacking an entity (if you want criticals but don't want it to spam jump when using aura)").whenFalse(this.packetMode);
/*     */   
/*     */   public Criticals() {
/*  36 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderTick() {
/*  41 */     if (!((Boolean)this.packetMode.getValue()).booleanValue()) {
/*  42 */       if (this.target == null)
/*     */         return; 
/*  44 */       if (this.flag && mc.field_71439_g.field_70143_R > 0.1D && canCrit() && (!((Boolean)this.checkRaytrace.getValue()).booleanValue() || mc.field_71476_x.field_72308_g == this.target)) {
/*  45 */         this.flag = false;
/*  46 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(this.target));
/*  47 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
/*  48 */         mc.field_71439_g.func_184821_cY();
/*     */       } 
/*     */       
/*  51 */       if (this.flag && mc.field_71439_g.field_70143_R > 0.0D) {
/*  52 */         this.flag2 = true;
/*     */       }
/*  54 */       if (this.flag2 && mc.field_71439_g.field_70122_E) {
/*  55 */         this.flag = false;
/*  56 */         this.flag2 = false;
/*  57 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(this.target));
/*  58 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
/*  59 */         mc.field_71439_g.func_184821_cY();
/*     */       } 
/*     */     } 
/*     */     
/*  63 */     if (((Boolean)this.packetMode.getValue()).booleanValue() || (this.flag2 && (mc.field_71439_g.field_70122_E || mc.field_71439_g.field_70134_J || mc.field_71439_g.func_70617_f_() || mc.field_71439_g.func_184218_aH() || mc.field_71439_g
/*  64 */       .func_70644_a(MobEffects.field_76440_q) || mc.field_71439_g.func_70090_H() || mc.field_71439_g.func_180799_ab()))) {
/*  65 */       this.flag = false;
/*  66 */       this.flag2 = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void onPlayerAttackPre(PlayerAttackEvent event) {
/*  72 */     if (!mc.field_71474_y.field_74314_A.func_151470_d() && canCrit() && mc.field_71439_g.field_70122_E && event.target instanceof net.minecraft.entity.EntityLivingBase && !this.flag)
/*     */     {
/*  74 */       if (((Boolean)this.packetMode.getValue()).booleanValue()) {
/*  75 */         if (mc.field_71439_g.func_184825_o(0.5F) > 0.9F) {
/*  76 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.1D, mc.field_71439_g.field_70161_v, false));
/*  77 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v, false));
/*     */         }
/*     */       
/*  80 */       } else if (!((Boolean)this.disableWhenAura.getValue()).booleanValue() || !ModuleManager.getModule(Aura.class).isEnabled() || ((
/*  81 */         !Aura.INSTANCE.checkPreferredWeapons() || ((Boolean)Aura.INSTANCE.autoSwitch.getValue()).booleanValue()) && (!((Boolean)Aura.INSTANCE.autoSwitch.getValue()).booleanValue() || Aura.INSTANCE.preferredWeapon.getValue() == Aura.Weapon.None) && Aura.INSTANCE.preferredWeapon.getValue() != Aura.Weapon.None)) {
/*  82 */         doJumpCrit();
/*  83 */         this.target = event.target;
/*  84 */         this.flag = true;
/*  85 */         event.cancel();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void doJumpCrit() {
/*  91 */     mc.field_71439_g.func_70664_aZ();
/*  92 */     mc.field_71439_g.field_70181_x = ((Float)this.jumpHeight.getValue()).floatValue();
/*     */   }
/*     */   
/*     */   public boolean canCrit() {
/*  96 */     return (!mc.field_71439_g.field_70134_J && !mc.field_71439_g.func_70617_f_() && !mc.field_71439_g.func_184218_aH() && 
/*  97 */       !mc.field_71439_g.func_70644_a(MobEffects.field_76440_q) && !mc.field_71439_g.func_70090_H() && !mc.field_71439_g.func_180799_ab() && (
/*  98 */       !((Boolean)this.onlyWeapon.getValue()).booleanValue() || isHoldingWeapon()));
/*     */   }
/*     */   
/*     */   private boolean isHoldingWeapon() {
/* 102 */     return (mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151048_u || mc.field_71439_g
/* 103 */       .func_184614_ca().func_77973_b() == Items.field_151040_l || mc.field_71439_g
/* 104 */       .func_184614_ca().func_77973_b() == Items.field_151010_B || mc.field_71439_g
/* 105 */       .func_184614_ca().func_77973_b() == Items.field_151052_q || mc.field_71439_g
/* 106 */       .func_184614_ca().func_77973_b() == Items.field_151041_m || mc.field_71439_g
/* 107 */       .func_184614_ca().func_77973_b() == Items.field_151056_x || mc.field_71439_g
/* 108 */       .func_184614_ca().func_77973_b() == Items.field_151040_l || mc.field_71439_g
/* 109 */       .func_184614_ca().func_77973_b() == Items.field_151006_E || mc.field_71439_g
/* 110 */       .func_184614_ca().func_77973_b() == Items.field_151049_t || mc.field_71439_g
/* 111 */       .func_184614_ca().func_77973_b() == Items.field_151053_p);
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\combat\Criticals.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */