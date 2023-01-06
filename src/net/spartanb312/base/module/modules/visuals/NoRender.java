/*     */ package net.spartanb312.base.module.modules.visuals;
/*     */ 
/*     */ import me.thediamondsword5.moloch.event.events.render.RenderEntityPreEvent;
/*     */ import net.minecraft.client.tutorial.TutorialSteps;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.event.Listener;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.network.PacketEvent;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.EntityUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Parallel(runnable = true)
/*     */ @ModuleInfo(name = "NoRender", category = Category.VISUALS, description = "Stop rendering certain things")
/*     */ public class NoRender
/*     */   extends Module
/*     */ {
/*     */   public static NoRender INSTANCE;
/*  37 */   Setting<Page> page = setting("Page", Page.Overlays);
/*     */   
/*  39 */   Setting<Boolean> blindness = setting("Blindness", true).whenAtMode(this.page, Page.Overlays);
/*  40 */   Setting<Boolean> nausea = setting("Nausea", false).whenAtMode(this.page, Page.Overlays);
/*  41 */   public Setting<Boolean> netherPortal = setting("NetherPortal", false).whenAtMode(this.page, Page.Overlays);
/*  42 */   public Setting<Boolean> fire = setting("Fire", false).whenAtMode(this.page, Page.Overlays);
/*  43 */   public Setting<Boolean> blockOverlay = setting("BlockOverlay", false).whenAtMode(this.page, Page.Overlays);
/*  44 */   public Setting<BossBarMode> bossBar = setting("BossBar", BossBarMode.None).whenAtMode(this.page, Page.Overlays);
/*  45 */   public Setting<Float> bossBarSize = setting("BossBarScale", 0.5F, 0.1F, 1.0F).only(v -> (this.bossBar.getValue() == BossBarMode.Stack)).whenAtMode(this.page, Page.Overlays);
/*  46 */   public Setting<TotemMode> totemPop = setting("TotemPop", TotemMode.None).whenAtMode(this.page, Page.Overlays);
/*  47 */   public Setting<Float> totemSize = setting("TotemSize", 0.5F, 0.1F, 1.0F).whenAtMode(this.totemPop, TotemMode.Scale).whenAtMode(this.page, Page.Overlays);
/*  48 */   public Setting<Boolean> waterOverlay = setting("WaterOverlay", false).whenAtMode(this.page, Page.Overlays);
/*  49 */   Setting<Boolean> tutorial = setting("Tutorial", false).whenAtMode(this.page, Page.Overlays);
/*  50 */   public Setting<Boolean> potionIcons = setting("PotionIcons", false).whenAtMode(this.page, Page.Overlays);
/*  51 */   public Setting<Boolean> pumpkin = setting("PumpkinOverlay", false).whenAtMode(this.page, Page.Overlays);
/*  52 */   public Setting<Boolean> vignette = setting("Vignette", false).whenAtMode(this.page, Page.Overlays);
/*  53 */   public Setting<Boolean> hurtCam = setting("HurtCam", false).whenAtMode(this.page, Page.Overlays);
/*  54 */   public Setting<Boolean> chat = setting("Chat", false).whenAtMode(this.page, Page.Overlays);
/*  55 */   public Setting<Boolean> backgrounds = setting("Backgrounds", false).whenAtMode(this.page, Page.Overlays);
/*     */   
/*  57 */   public Setting<Boolean> players = setting("Players", false).whenAtMode(this.page, Page.World);
/*  58 */   public Setting<Boolean> mobs = setting("Mobs", false).whenAtMode(this.page, Page.World);
/*  59 */   public Setting<Boolean> animals = setting("Animals", false).whenAtMode(this.page, Page.World);
/*  60 */   Setting<Boolean> items = setting("Items", false).whenAtMode(this.page, Page.World);
/*  61 */   public Setting<Boolean> armor = setting("Armor", false).whenAtMode(this.page, Page.World);
/*  62 */   Setting<Boolean> projectiles = setting("Projectiles", false).whenAtMode(this.page, Page.World);
/*  63 */   Setting<Boolean> xp = setting("XP", false).whenAtMode(this.page, Page.World);
/*  64 */   Setting<Boolean> explosion = setting("Explosions", true).whenAtMode(this.page, Page.World);
/*  65 */   public Setting<Boolean> fog = setting("Fog", false).des("Also disables the orange effect inside of lava").whenAtMode(this.page, Page.World);
/*  66 */   Setting<Boolean> paint = setting("Paintings", false).whenAtMode(this.page, Page.World);
/*  67 */   public Setting<Boolean> chests = setting("Chests", false).whenAtMode(this.page, Page.World);
/*  68 */   public Setting<Boolean> enderChests = setting("EnderChests", false).whenAtMode(this.page, Page.World);
/*  69 */   public Setting<Boolean> enchantingTableBook = setting("EnchantTableBook", false).whenAtMode(this.page, Page.World);
/*  70 */   public Setting<Boolean> maps = setting("Maps", false).whenAtMode(this.page, Page.World);
/*  71 */   public Setting<Boolean> signText = setting("SignText", false).whenAtMode(this.page, Page.World);
/*  72 */   public Setting<Boolean> skyLightUpdate = setting("SkyLightUpdate", false).whenAtMode(this.page, Page.World);
/*  73 */   Setting<Boolean> fallingBlocks = setting("FallingBlocks", false).whenAtMode(this.page, Page.World);
/*     */   
/*     */   public NoRender() {
/*  76 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  81 */     if (mc.field_71439_g == null)
/*     */       return; 
/*  83 */     if (((Boolean)this.blindness.getValue()).booleanValue()) {
/*  84 */       mc.field_71439_g.func_184596_c(MobEffects.field_76440_q);
/*     */     }
/*  86 */     if (((Boolean)this.nausea.getValue()).booleanValue() || ((Boolean)this.netherPortal.getValue()).booleanValue()) {
/*  87 */       mc.field_71439_g.func_184596_c(MobEffects.field_76431_k);
/*     */     }
/*  89 */     if (((Boolean)this.tutorial.getValue()).booleanValue()) {
/*  90 */       mc.field_71474_y.field_193631_S = TutorialSteps.NONE;
/*     */     }
/*     */   }
/*     */   
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/*  95 */     Packet<?> packet = event.packet;
/*  96 */     if ((packet instanceof net.minecraft.network.play.server.SPacketSpawnExperienceOrb && ((Boolean)this.xp.getValue()).booleanValue()) || (packet instanceof net.minecraft.network.play.server.SPacketExplosion && ((Boolean)this.explosion.getValue()).booleanValue()) || (packet instanceof net.minecraft.network.play.server.SPacketSpawnPainting && ((Boolean)this.paint.getValue()).booleanValue()))
/*  97 */       event.cancel(); 
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void onRenderEntityPre(RenderEntityPreEvent event) {
/* 102 */     if ((event.entityIn instanceof net.minecraft.entity.player.EntityPlayer && ((Boolean)this.players.getValue()).booleanValue()) || ((EntityUtil.isEntityMob(event.entityIn) || event.entityIn instanceof net.minecraft.entity.boss.EntityDragon) && ((Boolean)this.mobs.getValue()).booleanValue()) || (EntityUtil.isEntityAnimal(event.entityIn) && ((Boolean)this.animals.getValue()).booleanValue()) || (event.entityIn instanceof net.minecraft.entity.item.EntityItem && ((Boolean)this.items.getValue()).booleanValue()) || ((event.entityIn instanceof net.minecraft.entity.IProjectile || event.entityIn instanceof net.minecraft.entity.projectile.EntityShulkerBullet || event.entityIn instanceof net.minecraft.entity.projectile.EntityFireball || event.entityIn instanceof net.minecraft.entity.item.EntityEnderEye) && ((Boolean)this.projectiles.getValue()).booleanValue()) || (event.entityIn instanceof net.minecraft.entity.item.EntityFallingBlock && ((Boolean)this.fallingBlocks.getValue()).booleanValue()))
/* 103 */       event.cancel(); 
/*     */   }
/*     */   
/*     */   enum Page {
/* 107 */     Overlays,
/* 108 */     World;
/*     */   }
/*     */   
/*     */   public enum BossBarMode {
/* 112 */     Stack,
/* 113 */     NoRender,
/* 114 */     None;
/*     */   }
/*     */   
/*     */   public enum TotemMode {
/* 118 */     Scale,
/* 119 */     NoRender,
/* 120 */     None;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\module\modules\visuals\NoRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */