/*     */ package me.thediamondsword5.moloch.module.modules.visuals;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import me.thediamondsword5.moloch.client.EnemyManager;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.spartanb312.base.client.FriendManager;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.network.PacketEvent;
/*     */ import net.spartanb312.base.event.events.render.RenderEvent;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.EntityUtil;
/*     */ import net.spartanb312.base.utils.MathUtilFuckYou;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import net.spartanb312.base.utils.graphics.SpartanTessellator;
/*     */ import net.spartanb312.base.utils.math.Pair;
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "ChorusTrace", category = Category.VISUALS, description = "Renders direction of player chorus teleporting")
/*     */ public class ChorusTrace extends Module {
/*  32 */   Setting<Float> width = setting("Width", 2.5F, 1.0F, 5.0F).des("Width of line");
/*  33 */   Setting<Float> fadeSpeed = setting("FadeSpeed", 2.0F, 0.1F, 3.0F).des("Speed of how fast the line fades");
/*  34 */   Setting<Color> color = setting("Color", new Color((new Color(255, 255, 255, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 120));
/*  35 */   Setting<Color> friendColor = setting("FriendColor", new Color((new Color(50, 255, 255, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 255, 120));
/*  36 */   Setting<Color> enemyColor = setting("EnemyColor", new Color((new Color(255, 50, 50, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 120));
/*     */   
/*  38 */   private final HashMap<EntityPlayer, Vec3d> startChorusMap = new HashMap<>();
/*  39 */   private final HashMap<Long, EntityPlayer> endChorusMap = new HashMap<>();
/*  40 */   private final HashMap<Pair<Vec3d, Vec3d>, Pair<EntityPlayer, Float>> alphaMap = new HashMap<>();
/*  41 */   private final HashMap<Pair<Vec3d, Vec3d>, EntityPlayer> cachedChorusMap = new HashMap<>();
/*  42 */   private final Timer timer = new Timer();
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  46 */     for (EntityPlayer entityPlayer : (new HashMap<>(this.startChorusMap)).keySet()) {
/*  47 */       if (!EntityUtil.entitiesList().contains(entityPlayer)) {
/*  48 */         this.startChorusMap.remove(entityPlayer);
/*     */       }
/*     */     } 
/*     */     
/*  52 */     EntityUtil.entitiesListFlag = true;
/*  53 */     for (Entity entity : EntityUtil.entitiesList()) {
/*  54 */       if (!(entity instanceof EntityPlayer)) {
/*     */         continue;
/*     */       }
/*  57 */       if (((EntityPlayer)entity).func_184607_cu().func_77973_b() != Items.field_185161_cS) {
/*     */         continue;
/*     */       }
/*     */       
/*  61 */       this.startChorusMap.put((EntityPlayer)entity, entity.func_174791_d());
/*     */     } 
/*  63 */     EntityUtil.entitiesListFlag = false;
/*     */     
/*  65 */     for (Map.Entry<Long, EntityPlayer> entry : (new HashMap<>(this.endChorusMap)).entrySet()) {
/*  66 */       if (System.currentTimeMillis() - ((Long)entry.getKey()).longValue() >= 100L) {
/*  67 */         this.cachedChorusMap.put(new Pair(this.startChorusMap.get(entry.getValue()), ((EntityPlayer)entry.getValue()).func_174791_d()), entry.getValue());
/*  68 */         this.endChorusMap.remove(entry.getKey());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/*  75 */     if (event.getPacket() instanceof SPacketSoundEffect && ((
/*  76 */       (SPacketSoundEffect)event.getPacket()).func_186978_a() == SoundEvents.field_187544_ad || ((SPacketSoundEffect)event.getPacket()).func_186978_a() == SoundEvents.field_187534_aX)) {
/*  77 */       EntityPlayer entityPlayer; ((SPacketSoundEffect)event.getPacket()).field_149216_e = 100.0F;
/*  78 */       Vec3d soundVec = new Vec3d(((SPacketSoundEffect)event.getPacket()).field_149217_b, ((SPacketSoundEffect)event.getPacket()).field_149218_c, ((SPacketSoundEffect)event.getPacket()).field_149215_d);
/*  79 */       EntityPlayerSP entityPlayerSP = mc.field_71439_g;
/*  80 */       double lowestDist = 99999.0D;
/*     */       
/*  82 */       for (EntityPlayer player : this.startChorusMap.keySet()) {
/*  83 */         if (MathUtilFuckYou.getDistance(soundVec, player.func_174791_d()) < lowestDist) {
/*  84 */           lowestDist = MathUtilFuckYou.getDistance(soundVec, player.func_174791_d());
/*  85 */           entityPlayer = player;
/*     */         } 
/*     */       } 
/*     */       
/*  89 */       if (this.startChorusMap.containsKey(entityPlayer)) {
/*  90 */         this.endChorusMap.put(Long.valueOf(System.currentTimeMillis()), entityPlayer);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRenderWorld(RenderEvent event) {
/*  98 */     for (Map.Entry<Pair<Vec3d, Vec3d>, EntityPlayer> entry : (new HashMap<>(this.cachedChorusMap)).entrySet()) {
/*     */       Color color;
/*     */       
/*     */       int alpha;
/* 102 */       if (FriendManager.isFriend((Entity)entry.getValue())) {
/* 103 */         color = ((Color)this.friendColor.getValue()).getColorColor();
/* 104 */         alpha = ((Color)this.friendColor.getValue()).getAlpha();
/* 105 */       } else if (EnemyManager.isEnemy((Entity)entry.getValue())) {
/* 106 */         color = ((Color)this.enemyColor.getValue()).getColorColor();
/* 107 */         alpha = ((Color)this.enemyColor.getValue()).getAlpha();
/*     */       } else {
/* 109 */         color = ((Color)this.color.getValue()).getColorColor();
/* 110 */         alpha = ((Color)this.color.getValue()).getAlpha();
/*     */       } 
/*     */       
/* 113 */       this.alphaMap.putIfAbsent(entry.getKey(), new Pair(entry.getValue(), Float.valueOf(300.0F)));
/*     */       
/* 115 */       SpartanTessellator.drawLineToVec((Vec3d)((Pair)entry.getKey()).a, (Vec3d)((Pair)entry.getKey()).b, ((Float)this.width.getValue()).floatValue(), (new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(((Float)((Pair)this.alphaMap.get(entry.getKey())).b).floatValue() / 300.0F * alpha))).getRGB());
/*     */     } 
/*     */     
/* 118 */     int passedms = (int)this.timer.hasPassed();
/* 119 */     this.timer.reset();
/* 120 */     if (passedms < 1000)
/* 121 */       for (Map.Entry<Pair<Vec3d, Vec3d>, Pair<EntityPlayer, Float>> entry : (new HashMap<>(this.alphaMap)).entrySet()) {
/* 122 */         if (((Float)((Pair)entry.getValue()).b).floatValue() - passedms * ((Float)this.fadeSpeed.getValue()).floatValue() <= 0.0F) {
/* 123 */           this.alphaMap.remove(entry.getKey());
/* 124 */           this.cachedChorusMap.remove(entry.getKey());
/*     */           
/*     */           continue;
/*     */         } 
/* 128 */         this.alphaMap.put(entry.getKey(), new Pair(((Pair)entry.getValue()).a, Float.valueOf(((Float)((Pair)entry.getValue()).b).floatValue() - passedms * ((Float)this.fadeSpeed.getValue()).floatValue() / 10.0F)));
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\visuals\ChorusTrace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */