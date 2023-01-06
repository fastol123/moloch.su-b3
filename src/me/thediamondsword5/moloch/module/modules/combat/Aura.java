/*     */ package me.thediamondsword5.moloch.module.modules.combat;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.hud.huds.DebugThing;
/*     */ import me.thediamondsword5.moloch.module.modules.other.Freecam;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.spartanb312.base.client.FriendManager;
/*     */ import net.spartanb312.base.client.ModuleManager;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.concurrent.ConcurrentTaskManager;
/*     */ import net.spartanb312.base.core.concurrent.repeat.RepeatUnit;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.render.RenderEvent;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.EntityUtil;
/*     */ import net.spartanb312.base.utils.ItemUtils;
/*     */ import net.spartanb312.base.utils.MathUtilFuckYou;
/*     */ import net.spartanb312.base.utils.RotationUtil;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import net.spartanb312.base.utils.graphics.RenderHelper;
/*     */ import net.spartanb312.base.utils.graphics.SpartanTessellator;
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
/*     */ @ModuleInfo(name = "Aura", category = Category.COMBAT, description = "Attacks entities around you")
/*     */ public class Aura
/*     */   extends Module
/*     */ {
/*  53 */   private final List<RepeatUnit> repeatUnits = new ArrayList<>();
/*  54 */   private static final HashMap<Entity, Float> targetData = new HashMap<>();
/*  55 */   public static final HashMap<Entity, Integer> moreTargetData = new HashMap<>();
/*  56 */   static final List<Entity> moreTargetDataToRemove = new ArrayList<>();
/*  57 */   private static final HashMap<Entity, Integer> lastTargetData = new HashMap<>();
/*  58 */   private static final Timer attackTimer = new Timer();
/*     */   private int prevSlot;
/*     */   public static Entity target;
/*     */   private boolean flag;
/*     */   private boolean flag2;
/*     */   private boolean attackFlag;
/*  64 */   private long lastTime = -9999L;
/*  65 */   public static final List<Vec3d> entityTriggerVecList = new ArrayList<>();
/*     */   
/*     */   public static Aura INSTANCE;
/*  68 */   Setting<Page> page = setting("Page", Page.Aura);
/*     */   
/*  70 */   Setting<Boolean> delay = setting("ModifyDelay", false).des("Don't use attack cooldown").whenAtMode(this.page, Page.Aura);
/*  71 */   Setting<Integer> attackDelay = setting("AttackDelay", 0, 0, 1000).des("Delay to attack target").whenTrue(this.delay).whenAtMode(this.page, Page.Aura);
/*  72 */   Setting<Float> range = setting("Range", 6.0F, 0.0F, 10.0F).des("Range to start attacking target").whenAtMode(this.page, Page.Aura);
/*  73 */   Setting<Boolean> targetPlayers = setting("TargetPlayers", true).des("Target players").whenAtMode(this.page, Page.Aura);
/*  74 */   Setting<Boolean> targetMobs = setting("TargetMobs", true).des("Target mobs").whenAtMode(this.page, Page.Aura);
/*  75 */   Setting<Boolean> targetAnimals = setting("TargetAnimals", false).des("Target animals").whenAtMode(this.page, Page.Aura);
/*  76 */   Setting<Boolean> targetMiscEntities = setting("TargetOtherEntities", false).des("Target other entities").whenAtMode(this.page, Page.Aura);
/*  77 */   Setting<Boolean> ignoreInvisible = setting("IgnoreInvisible", false).des("Doesn't target invisible entities").whenAtMode(this.page, Page.Aura);
/*  78 */   Setting<Boolean> legitMode = setting("LegitMode", false).des("Makes aura act more like actual player attack with left click").whenAtMode(this.page, Page.Aura);
/*  79 */   Setting<Boolean> stopSprint = setting("StopSprint", false).des("Stops sprinting during hit").whenAtMode(this.page, Page.Aura);
/*  80 */   Setting<Integer> randomClickPercent = setting("RandomClickPercent", 100, 1, 100).des("Chance of how likely aura will attack when aimed at target").whenTrue(this.legitMode).whenAtMode(this.page, Page.Aura);
/*  81 */   Setting<Boolean> checkWall = setting("CheckWall", false).des("Only attack target if they aren't behind wall").whenFalse(this.legitMode).whenAtMode(this.page, Page.Aura);
/*  82 */   Setting<Float> wallRange = setting("WallRange", 3.0F, 0.0F, 10.0F).des("Range to start attacking target when target is behind a block").whenFalse(this.legitMode).whenFalse(this.checkWall).whenAtMode(this.page, Page.Aura);
/*  83 */   Setting<Boolean> rotate = setting("Rotate", false).des("Rotate to attack target").whenFalse(this.legitMode).whenAtMode(this.page, Page.Aura);
/*  84 */   Setting<Boolean> slowRotate = setting("SlowRotate", false).des("Rotate more smoothly for strict servers").whenTrue(this.rotate).whenFalse(this.legitMode).whenAtMode(this.page, Page.Aura);
/*  85 */   Setting<Integer> yawSpeed = setting("YawSpeed", 774, 1, 2000).des("Yaw speed i think").whenTrue(this.slowRotate).whenTrue(this.rotate).whenFalse(this.legitMode).whenAtMode(this.page, Page.Aura);
/*  86 */   Setting<Float> attackYawRange = setting("YawHitRange", 11.8F, 0.0F, 90.0F).des("Yaw range in degrees to start attacking target").whenTrue(this.slowRotate).whenTrue(this.rotate).whenFalse(this.legitMode).whenAtMode(this.page, Page.Aura);
/*  87 */   Setting<Boolean> triggerMode = setting("TriggerMode", false).des("Only attack target when facing it").whenFalse(this.legitMode).whenFalse(this.rotate).whenAtMode(this.page, Page.Aura);
/*  88 */   public Setting<Boolean> autoSwitch = setting("AutoSwitch", false).des("Automatically switch to weapon").whenFalse(this.legitMode).whenAtMode(this.page, Page.Aura);
/*  89 */   public Setting<Weapon> preferredWeapon = setting("PreferredWeapon", Weapon.Sword).des("Preferred weapon to switch to").whenFalse(this.legitMode).whenAtMode(this.page, Page.Aura);
/*  90 */   Setting<Boolean> switchBack = setting("SwitchBack", false).des("Switch back to previous slot on disable or when nothing is targeted").whenFalse(this.legitMode).whenTrue(this.autoSwitch).whenAtMode(this.page, Page.Aura);
/*     */   
/*  92 */   Setting<Boolean> offhandSwing = setting("OffhandSwing", false).des("Swing with offhand to attack").whenAtMode(this.page, Page.Render);
/*  93 */   Setting<RenderType> renderType = setting("RenderType", RenderType.Box).whenAtMode(this.page, Page.Render);
/*  94 */   Setting<BoxMode> renderBoxMode = setting("BoxMode", BoxMode.Solid).des("Mode of box render").whenAtMode(this.renderType, RenderType.Box).only(v -> (this.renderType.getValue() != RenderType.None)).whenAtMode(this.page, Page.Render);
/*  95 */   Setting<Float> boxLinesWidth = setting("LineWidth", 1.0F, 1.0F, 5.0F).des("Box render lines width").only(v -> (this.renderType.getValue() == RenderType.Box && this.renderBoxMode.getValue() != BoxMode.Solid)).whenAtMode(this.page, Page.Render);
/*  96 */   Setting<Color> color = setting("Color", new Color((new Color(255, 100, 100, 125)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 100, 125)).des("Aura target render color").only(v -> (this.renderType.getValue() != RenderType.Box && this.renderType.getValue() != RenderType.None)).whenAtMode(this.page, Page.Render);
/*  97 */   Setting<Color> solidColor = setting("SolidColor", new Color((new Color(255, 100, 100, 14)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 100, 14)).des("Aura target render fill color").only(v -> (this.renderType.getValue() == RenderType.Box && this.renderBoxMode.getValue() != BoxMode.Lines)).whenAtMode(this.page, Page.Render);
/*  98 */   Setting<Color> linesColor = setting("LinesColor", new Color((new Color(255, 255, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 175)).des("Aura target render outline color").only(v -> (this.renderType.getValue() == RenderType.Box && this.renderBoxMode.getValue() != BoxMode.Solid)).whenAtMode(this.page, Page.Render);
/*  99 */   Setting<Boolean> fadeOnTargetChange = setting("FadeOnTargetChange", true).des("Fade color when target is changed to another entity or nothing").only(v -> (this.renderType.getValue() != RenderType.None)).whenAtMode(this.page, Page.Render);
/* 100 */   Setting<Integer> fadeSpeedTargetChange = setting("FadeSpeedOnChange", 25, 2, 50).des("Render target change color fade speed").only(v -> (this.renderType.getValue() != RenderType.None)).whenTrue(this.fadeOnTargetChange).whenAtMode(this.page, Page.Render);
/* 101 */   Setting<Boolean> changeColorWhenHit = setting("ChangeColorOnHit", true).des("Change and fade color on a hit").only(v -> (this.renderType.getValue() != RenderType.None)).whenAtMode(this.page, Page.Render);
/* 102 */   Setting<Integer> fadeSpeedWhenHit = setting("FadeSpeedOnHit", 25, 2, 50).des("Render hit color fade speed").only(v -> (this.renderType.getValue() != RenderType.None)).whenTrue(this.changeColorWhenHit).whenAtMode(this.page, Page.Render);
/* 103 */   Setting<Color> hitColor = setting("HitColor", new Color((new Color(255, 100, 100, 125)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 100, 125)).des("Aura target render color").only(v -> (this.renderType.getValue() != RenderType.Box && this.renderType.getValue() != RenderType.None)).whenTrue(this.changeColorWhenHit).whenAtMode(this.page, Page.Render);
/* 104 */   Setting<Color> solidHitColor = setting("SolidHitColor", new Color((new Color(255, 255, 255, 52)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 100, 52)).des("Aura target render fill color").only(v -> (this.renderType.getValue() == RenderType.Box && this.renderBoxMode.getValue() != BoxMode.Lines)).whenTrue(this.changeColorWhenHit).whenAtMode(this.page, Page.Render);
/* 105 */   Setting<Color> linesHitColor = setting("LinesHitColor", new Color((new Color(255, 255, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 175)).des("Aura target render outline color").only(v -> (this.renderType.getValue() == RenderType.Box && this.renderBoxMode.getValue() != BoxMode.Solid)).whenTrue(this.changeColorWhenHit).whenAtMode(this.page, Page.Render);
/*     */ 
/*     */ 
/*     */   
/*     */   RepeatUnit doRotate;
/*     */ 
/*     */   
/*     */   RepeatUnit updateAura;
/*     */ 
/*     */ 
/*     */   
/*     */   public Aura() {
/* 117 */     this.doRotate = new RepeatUnit(() -> 1, () -> {
/*     */           if (((Boolean)this.rotate.getValue()).booleanValue() && !((Boolean)this.legitMode.getValue()).booleanValue()) {
/*     */             if ((checkPreferredWeapons() && !((Boolean)this.autoSwitch.getValue()).booleanValue()) || (((Boolean)this.autoSwitch.getValue()).booleanValue() && this.preferredWeapon.getValue() != Weapon.None) || this.preferredWeapon.getValue() == Weapon.None) {
/*     */               if (!targetData.isEmpty() && target != null) {
/*     */                 RotationUtil.lookAtTarget(target, ((Boolean)this.slowRotate.getValue()).booleanValue(), ((Integer)this.yawSpeed.getValue()).intValue());
/*     */               } else if (mc.field_71439_g != null) {
/*     */                 RotationUtil.resetRotation(((Boolean)this.slowRotate.getValue()).booleanValue(), ((Integer)this.yawSpeed.getValue()).intValue());
/*     */               } 
/*     */             } else if (mc.field_71439_g != null) {
/*     */               RotationUtil.resetRotation(((Boolean)this.slowRotate.getValue()).booleanValue(), ((Integer)this.yawSpeed.getValue()).intValue());
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     this.updateAura = new RepeatUnit(() -> 50, () -> {
/*     */           if (mc.field_71441_e == null)
/*     */             return;  if ((checkPreferredWeapons() && !((Boolean)this.autoSwitch.getValue()).booleanValue()) || (((Boolean)this.autoSwitch.getValue()).booleanValue() && this.preferredWeapon.getValue() != Weapon.None) || this.preferredWeapon.getValue() == Weapon.None) {
/*     */             if (((Boolean)this.fadeOnTargetChange.getValue()).booleanValue())
/*     */               this.flag2 = true; 
/*     */             target = calcTarget();
/*     */             if (target == null)
/*     */               return; 
/*     */             if (((Boolean)this.fadeOnTargetChange.getValue()).booleanValue() && !target.field_70128_L)
/*     */               lastTargetData.put(target, Integer.valueOf(0)); 
/*     */             if (((Boolean)this.changeColorWhenHit.getValue()).booleanValue() && !target.field_70128_L && !moreTargetData.containsKey(target))
/*     */               moreTargetData.put(target, Integer.valueOf(300)); 
/*     */             attackTargets();
/*     */           } else if (((Boolean)this.fadeOnTargetChange.getValue()).booleanValue() && this.renderType.getValue() != RenderType.None && (!checkPreferredWeapons() || ((Boolean)this.autoSwitch.getValue()).booleanValue()) && (!((Boolean)this.autoSwitch.getValue()).booleanValue() || this.preferredWeapon.getValue() == Weapon.None) && this.preferredWeapon.getValue() != Weapon.None) {
/*     */             if (this.flag2) {
/*     */               target = calcTarget();
/*     */               if (target != null)
/*     */                 lastTargetData.put(target, Integer.valueOf(0)); 
/*     */               this.flag2 = false;
/*     */             } 
/*     */           } else if (((Boolean)this.autoSwitch.getValue()).booleanValue() && ((Boolean)this.switchBack.getValue()).booleanValue() && this.flag) {
/*     */             mc.field_71439_g.field_71071_by.field_70461_c = this.prevSlot;
/*     */             this.flag = false;
/*     */           } 
/*     */         });
/*     */     INSTANCE = this;
/*     */     this.repeatUnits.add(this.updateAura);
/*     */     this.repeatUnits.add(this.doRotate);
/*     */     this.repeatUnits.forEach(it -> {
/*     */           it.suspend();
/*     */           ConcurrentTaskManager.runRepeat(it);
/* 167 */         }); } public String getModuleInfo() { if (target != null) return target.func_70005_c_(); 
/* 168 */     return " "; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 173 */     this.repeatUnits.forEach(RepeatUnit::resume);
/* 174 */     this.moduleEnableFlag = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 179 */     this.repeatUnits.forEach(RepeatUnit::suspend);
/* 180 */     if (((Boolean)this.autoSwitch.getValue()).booleanValue() && ((Boolean)this.switchBack.getValue()).booleanValue() && this.flag) {
/* 181 */       mc.field_71439_g.field_71071_by.field_70461_c = this.prevSlot;
/*     */     }
/* 183 */     if (((Boolean)this.rotate.getValue()).booleanValue()) {
/* 184 */       RotationUtil.newYaw = mc.field_71439_g.field_70759_as;
/* 185 */       RotationUtil.shouldSpoofPacket = false;
/* 186 */       RotationUtil.flag = false;
/*     */     } 
/* 188 */     this.moduleDisableFlag = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderWorld(RenderEvent event) {
/* 193 */     if (this.renderType.getValue() == RenderType.Box) {
/* 194 */       Color theSolidColor = new Color(((Color)this.solidColor.getValue()).getColorColor().getRed(), ((Color)this.solidColor.getValue()).getColorColor().getGreen(), ((Color)this.solidColor.getValue()).getColorColor().getBlue(), ((Color)this.solidColor.getValue()).getAlpha());
/* 195 */       Color theLinesColor = new Color(((Color)this.linesColor.getValue()).getColorColor().getRed(), ((Color)this.linesColor.getValue()).getColorColor().getGreen(), ((Color)this.linesColor.getValue()).getColorColor().getBlue(), ((Color)this.linesColor.getValue()).getAlpha());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 200 */       if (((Boolean)this.changeColorWhenHit.getValue()).booleanValue() && !moreTargetData.isEmpty()) {
/* 201 */         if (!moreTargetDataToRemove.isEmpty()) {
/* 202 */           moreTargetDataToRemove.forEach(moreTargetData.keySet()::remove);
/* 203 */           moreTargetDataToRemove.clear();
/*     */         } 
/*     */         
/* 206 */         for (Map.Entry<Entity, Integer> entry : (new HashMap<>(moreTargetData)).entrySet()) {
/* 207 */           if (mc.field_71439_g.func_70032_d(entry.getKey()) > ((Float)this.range.getValue()).floatValue() && (!((Boolean)this.fadeOnTargetChange.getValue()).booleanValue() || !lastTargetData.containsKey(entry.getKey()))) {
/* 208 */             if (moreTargetData.size() == 1) {
/* 209 */               moreTargetDataToRemove.add(0, entry.getKey());
/*     */               
/*     */               break;
/*     */             } 
/* 213 */             moreTargetDataToRemove.add(0, entry.getKey());
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 218 */           int localValue = ((Integer)entry.getValue()).intValue();
/*     */           
/* 220 */           if (this.attackFlag && target != null && entry.getKey() == target) {
/* 221 */             localValue = 0;
/* 222 */             this.attackFlag = false;
/*     */           } 
/*     */           
/* 225 */           if (this.lastTime == -9999L) this.lastTime = System.currentTimeMillis() - 16L;
/*     */           
/* 227 */           for (int i = 0; i < (int)((float)(System.currentTimeMillis() - this.lastTime) / 50.0F * ((Integer)this.fadeSpeedWhenHit.getValue()).intValue()); i++) {
/* 228 */             localValue++;
/*     */           }
/* 230 */           if (localValue >= 300) {
/* 231 */             localValue = 300;
/*     */           }
/*     */           
/* 234 */           int red = (int)MathUtilFuckYou.linearInterp(((Color)this.solidHitColor.getValue()).getColorColor().getRed(), theSolidColor.getRed(), localValue);
/* 235 */           int green = (int)MathUtilFuckYou.linearInterp(((Color)this.solidHitColor.getValue()).getColorColor().getGreen(), theSolidColor.getGreen(), localValue);
/* 236 */           int blue = (int)MathUtilFuckYou.linearInterp(((Color)this.solidHitColor.getValue()).getColorColor().getBlue(), theSolidColor.getBlue(), localValue);
/* 237 */           int alpha = (int)MathUtilFuckYou.linearInterp(((Color)this.solidHitColor.getValue()).getAlpha(), ((Color)this.solidColor.getValue()).getAlpha(), localValue);
/* 238 */           Color theNewSolidColor = new Color(red, green, blue, alpha);
/*     */           
/* 240 */           int red2 = (int)MathUtilFuckYou.linearInterp(((Color)this.linesHitColor.getValue()).getColorColor().getRed(), theLinesColor.getRed(), localValue);
/* 241 */           int green2 = (int)MathUtilFuckYou.linearInterp(((Color)this.linesHitColor.getValue()).getColorColor().getGreen(), theLinesColor.getGreen(), localValue);
/* 242 */           int blue2 = (int)MathUtilFuckYou.linearInterp(((Color)this.linesHitColor.getValue()).getColorColor().getBlue(), theLinesColor.getBlue(), localValue);
/* 243 */           int alpha2 = (int)MathUtilFuckYou.linearInterp(((Color)this.linesHitColor.getValue()).getAlpha(), ((Color)this.linesColor.getValue()).getAlpha(), localValue);
/* 244 */           Color theNewLinesColor = new Color(red2, green2, blue2, alpha2);
/*     */           
/* 246 */           DebugThing.debugInt++;
/* 247 */           if (target != null && entry.getKey() == target && 
/* 248 */             RenderHelper.isInViewFrustrum(target)) {
/* 249 */             if (this.renderBoxMode.getValue() != BoxMode.Lines)
/* 250 */               SpartanTessellator.drawBBFullBox(target, theNewSolidColor.getRGB()); 
/* 251 */             if (this.renderBoxMode.getValue() != BoxMode.Solid) {
/* 252 */               SpartanTessellator.drawBBLineBox(target, ((Float)this.boxLinesWidth.getValue()).floatValue(), theNewLinesColor.getRGB());
/*     */             }
/*     */           } 
/*     */           
/* 256 */           moreTargetData.put(entry.getKey(), Integer.valueOf(localValue));
/*     */         }
/*     */       
/* 259 */       } else if (target != null && 
/* 260 */         RenderHelper.isInViewFrustrum(target)) {
/* 261 */         if (this.renderBoxMode.getValue() != BoxMode.Lines)
/* 262 */           SpartanTessellator.drawBBFullBox(target, theSolidColor.getRGB()); 
/* 263 */         if (this.renderBoxMode.getValue() != BoxMode.Solid) {
/* 264 */           SpartanTessellator.drawBBLineBox(target, ((Float)this.boxLinesWidth.getValue()).floatValue(), theLinesColor.getRGB());
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 269 */       if (((Boolean)this.fadeOnTargetChange.getValue()).booleanValue() && !lastTargetData.isEmpty()) {
/* 270 */         for (Map.Entry<Entity, Integer> entry : (new HashMap<>(lastTargetData)).entrySet()) {
/* 271 */           if (entry.getKey() != target || (((Boolean)this.fadeOnTargetChange.getValue()).booleanValue() && (!checkPreferredWeapons() || ((Boolean)this.autoSwitch.getValue()).booleanValue()) && (!((Boolean)this.autoSwitch.getValue()).booleanValue() || this.preferredWeapon.getValue() == Weapon.None) && this.preferredWeapon.getValue() != Weapon.None)) {
/* 272 */             Color theSolidColorLastTarget, theLinesColorLastTarget; int localValue = ((Integer)entry.getValue()).intValue();
/*     */             
/* 274 */             if (this.lastTime == -9999L) this.lastTime = System.currentTimeMillis() - 16L;
/*     */             
/* 276 */             for (int i = 0; i < (int)((float)(System.currentTimeMillis() - this.lastTime) / 50.0F * ((Integer)this.fadeSpeedTargetChange.getValue()).intValue()); i++) {
/* 277 */               localValue++;
/*     */             }
/*     */             
/* 280 */             if (localValue >= 300) {
/* 281 */               lastTargetData.remove(entry.getKey());
/*     */ 
/*     */               
/*     */               continue;
/*     */             } 
/*     */             
/* 287 */             if (((Boolean)this.changeColorWhenHit.getValue()).booleanValue()) {
/* 288 */               int red = (int)MathUtilFuckYou.linearInterp(((Color)this.solidHitColor.getValue()).getColorColor().getRed(), theSolidColor.getRed(), ((Integer)moreTargetData.get(entry.getKey())).intValue());
/* 289 */               int green = (int)MathUtilFuckYou.linearInterp(((Color)this.solidHitColor.getValue()).getColorColor().getGreen(), theSolidColor.getGreen(), ((Integer)moreTargetData.get(entry.getKey())).intValue());
/* 290 */               int blue = (int)MathUtilFuckYou.linearInterp(((Color)this.solidHitColor.getValue()).getColorColor().getBlue(), theSolidColor.getBlue(), ((Integer)moreTargetData.get(entry.getKey())).intValue());
/* 291 */               int alpha5 = (int)MathUtilFuckYou.linearInterp(((Color)this.solidHitColor.getValue()).getAlpha(), ((Color)this.solidColor.getValue()).getAlpha(), ((Integer)moreTargetData.get(entry.getKey())).intValue());
/* 292 */               int alpha = (int)MathUtilFuckYou.linearInterp(alpha5, 0.0F, localValue);
/* 293 */               theSolidColorLastTarget = new Color(red, green, blue, alpha);
/*     */             } else {
/*     */               
/* 296 */               int alpha = (int)MathUtilFuckYou.linearInterp(((Color)this.solidColor.getValue()).getAlpha(), 0.0F, localValue);
/* 297 */               theSolidColorLastTarget = new Color(theSolidColor.getRed(), theSolidColor.getGreen(), theSolidColor.getBlue(), alpha);
/*     */             } 
/*     */ 
/*     */             
/* 301 */             if (((Boolean)this.changeColorWhenHit.getValue()).booleanValue()) {
/* 302 */               int red2 = (int)MathUtilFuckYou.linearInterp(((Color)this.linesHitColor.getValue()).getColorColor().getRed(), theLinesColor.getRed(), ((Integer)moreTargetData.get(entry.getKey())).intValue());
/* 303 */               int green2 = (int)MathUtilFuckYou.linearInterp(((Color)this.linesHitColor.getValue()).getColorColor().getGreen(), theLinesColor.getGreen(), ((Integer)moreTargetData.get(entry.getKey())).intValue());
/* 304 */               int blue2 = (int)MathUtilFuckYou.linearInterp(((Color)this.linesHitColor.getValue()).getColorColor().getBlue(), theLinesColor.getBlue(), ((Integer)moreTargetData.get(entry.getKey())).intValue());
/* 305 */               int alpha6 = (int)MathUtilFuckYou.linearInterp(((Color)this.linesHitColor.getValue()).getAlpha(), ((Color)this.linesColor.getValue()).getAlpha(), ((Integer)moreTargetData.get(entry.getKey())).intValue());
/* 306 */               int alpha2 = (int)MathUtilFuckYou.linearInterp(alpha6, 0.0F, localValue);
/* 307 */               theLinesColorLastTarget = new Color(red2, green2, blue2, alpha2);
/*     */             } else {
/*     */               
/* 310 */               int alpha2 = (int)MathUtilFuckYou.linearInterp(((Color)this.linesColor.getValue()).getAlpha(), 0.0F, localValue);
/* 311 */               theLinesColorLastTarget = new Color(theLinesColor.getRed(), theLinesColor.getGreen(), theLinesColor.getBlue(), alpha2);
/*     */             } 
/*     */             
/* 314 */             if (entry.getKey() != null && RenderHelper.isInViewFrustrum(entry.getKey())) {
/* 315 */               if (this.renderBoxMode.getValue() != BoxMode.Lines)
/* 316 */                 SpartanTessellator.drawBBFullBox(entry.getKey(), theSolidColorLastTarget.getRGB()); 
/* 317 */               if (this.renderBoxMode.getValue() != BoxMode.Solid) {
/* 318 */                 SpartanTessellator.drawBBLineBox(entry.getKey(), ((Float)this.boxLinesWidth.getValue()).floatValue(), theLinesColorLastTarget.getRGB());
/*     */               }
/*     */             } 
/* 321 */             lastTargetData.put(entry.getKey(), Integer.valueOf(localValue));
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 326 */       if (((Boolean)this.changeColorWhenHit.getValue()).booleanValue() || ((Boolean)this.fadeOnTargetChange.getValue()).booleanValue()) this.lastTime = System.currentTimeMillis(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void attackTargets() {
/* 331 */     if (target != null) {
/* 332 */       if (target.field_70128_L) targetData.remove(target); 
/* 333 */       if (!((Boolean)this.legitMode.getValue()).booleanValue() && ((Boolean)this.rotate.getValue()).booleanValue() && ((Boolean)this.slowRotate.getValue()).booleanValue() && RotationUtil.calcNormalizedAngleDiff(RotationUtil.normalizeAngle(RotationUtil.getRotations(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), target.func_174791_d())[0]), RotationUtil.newYaw) > ((Float)this.attackYawRange.getValue()).floatValue())
/* 334 */         return;  if (((Boolean)this.autoSwitch.getValue()).booleanValue()) {
/* 335 */         if (!this.flag) {
/* 336 */           this.prevSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 337 */           this.flag = true;
/*     */         } 
/* 339 */         ItemUtils.switchToSlot(ItemUtils.findItemInHotBar(preferredWeapon()));
/*     */       } 
/* 341 */       if (!((Boolean)this.checkWall.getValue()).booleanValue() || EntityUtil.isEntityVisible(target)) doAttack(target); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void doAttack(Entity entity) {
/* 346 */     if (ModuleManager.getModule(Criticals.class).isEnabled() && !((Boolean)Criticals.INSTANCE.packetMode.getValue()).booleanValue() && mc.field_71439_g.field_70122_E && target instanceof net.minecraft.entity.EntityLivingBase && Criticals.INSTANCE
/* 347 */       .canCrit() && 
/* 348 */       !((Boolean)Criticals.INSTANCE.disableWhenAura.getValue()).booleanValue()) {
/*     */       
/* 350 */       Criticals.INSTANCE.doJumpCrit();
/*     */       
/* 352 */       if (mc.field_71439_g.func_184825_o(0.5F) > 0.9F && mc.field_71439_g.field_70143_R > 0.1D) {
/* 353 */         this.attackFlag = true;
/*     */         
/* 355 */         boolean sprinting = mc.field_71439_g.func_70051_ag();
/*     */         
/* 357 */         if (((Boolean)this.stopSprint.getValue()).booleanValue()) {
/* 358 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
/*     */         }
/*     */         
/* 361 */         mc.field_71442_b.func_78764_a((EntityPlayer)mc.field_71439_g, entity);
/* 362 */         mc.field_71439_g.func_184609_a(((Boolean)this.offhandSwing.getValue()).booleanValue() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
/* 363 */         mc.field_71439_g.func_184821_cY();
/*     */         
/* 365 */         if (((Boolean)this.stopSprint.getValue()).booleanValue() && sprinting) {
/* 366 */           mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING));
/*     */         }
/*     */         
/* 369 */         attackTimer.reset();
/*     */       }
/*     */     
/*     */     }
/* 373 */     else if (attackTimer.passed(((Boolean)this.delay.getValue()).booleanValue() ? ((Integer)this.attackDelay.getValue()).intValue() : getWeaponCooldown())) {
/*     */       
/* 375 */       this.attackFlag = true;
/*     */       
/* 377 */       boolean sprinting = mc.field_71439_g.func_70051_ag();
/*     */       
/* 379 */       if (((Boolean)this.stopSprint.getValue()).booleanValue()) {
/* 380 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SPRINTING));
/*     */       }
/*     */       
/* 383 */       if (((Boolean)this.legitMode.getValue()).booleanValue() && Math.random() <= (((Integer)this.randomClickPercent.getValue()).intValue() / 100.0F)) {
/* 384 */         mc.func_147116_af();
/*     */       } else {
/*     */         
/* 387 */         mc.field_71442_b.func_78764_a((EntityPlayer)mc.field_71439_g, entity);
/* 388 */         mc.field_71439_g.func_184609_a(((Boolean)this.offhandSwing.getValue()).booleanValue() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
/* 389 */         mc.field_71439_g.func_184821_cY();
/*     */       } 
/*     */       
/* 392 */       if (((Boolean)this.stopSprint.getValue()).booleanValue() && sprinting) {
/* 393 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SPRINTING));
/*     */       }
/*     */       
/* 396 */       attackTimer.reset();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkEntity(Entity entity) {
/* 402 */     if (entity == null || mc.field_71439_g == null) return false;
/*     */     
/* 404 */     return (!FriendManager.isFriend(entity) && entity != mc.field_71439_g && ((((Boolean)this.targetPlayers
/* 405 */       .getValue()).booleanValue() && entity instanceof EntityPlayer) || (((Boolean)this.targetMobs
/* 406 */       .getValue()).booleanValue() && (EntityUtil.isEntityMob(entity) || entity instanceof net.minecraft.entity.boss.EntityDragon)) || (((Boolean)this.targetAnimals
/* 407 */       .getValue()).booleanValue() && EntityUtil.isEntityAnimal(entity)) || (((Boolean)this.targetMiscEntities
/* 408 */       .getValue()).booleanValue() && !(entity instanceof EntityPlayer) && 
/* 409 */       !EntityUtil.isEntityMob(entity) && !(entity instanceof net.minecraft.entity.boss.EntityDragon) && !EntityUtil.isEntityAnimal(entity) && !(entity instanceof net.minecraft.entity.item.EntityItem) && !(entity instanceof net.minecraft.entity.IProjectile) && !(entity instanceof net.minecraft.entity.item.EntityXPOrb))) && mc.field_71439_g
/* 410 */       .func_70032_d(entity) < ((Float)this.range.getValue()).floatValue() && (((Boolean)this.checkWall.getValue()).booleanValue() || 
/* 411 */       EntityUtil.isEntityVisible(entity) || mc.field_71439_g
/* 412 */       .func_70032_d(entity) < ((Float)this.wallRange.getValue()).floatValue()) && !entity.field_70128_L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Entity calcTarget() {
/* 418 */     if (!((Boolean)this.rotate.getValue()).booleanValue() && ((Boolean)this.triggerMode.getValue()).booleanValue()) {
/* 419 */       Vec3d startVec = mc.field_71439_g.func_174824_e(mc.func_184121_ak());
/* 420 */       RayTraceResult ray = mc.field_71439_g.func_174822_a(6.0D, mc.func_184121_ak());
/* 421 */       if (ray == null) return null; 
/* 422 */       Vec3d raytracedVec = ray.field_72307_f;
/*     */       
/* 424 */       double[] extendVecHelper = MathUtilFuckYou.cartesianToPolar3d(raytracedVec.field_72450_a - startVec.field_72450_a, raytracedVec.field_72448_b - startVec.field_72448_b, raytracedVec.field_72449_c - startVec.field_72449_c);
/* 425 */       double[] extendVecHelper2 = MathUtilFuckYou.polarToCartesian3d(((Float)this.range.getValue()).floatValue(), extendVecHelper[1], extendVecHelper[2]);
/*     */       
/* 427 */       double extendFactorX = extendVecHelper2[0] / 200.0D;
/* 428 */       double extendFactorY = extendVecHelper2[1] / 200.0D;
/* 429 */       double extendFactorZ = extendVecHelper2[2] / 200.0D;
/*     */       
/* 431 */       entityTriggerVecList.clear();
/* 432 */       for (int i = 0; i < 200; i++) {
/* 433 */         extendFactorX += extendVecHelper2[0] / 200.0D;
/* 434 */         extendFactorY += extendVecHelper2[1] / 200.0D;
/* 435 */         extendFactorZ += extendVecHelper2[2] / 200.0D;
/* 436 */         Vec3d extendVec = new Vec3d(startVec.field_72450_a + extendFactorX, startVec.field_72448_b + extendFactorY, startVec.field_72449_c + extendFactorZ);
/* 437 */         entityTriggerVecList.add(0, extendVec);
/*     */       } 
/*     */     } 
/*     */     
/* 441 */     for (Entity entity : EntityUtil.entitiesList()) {
/* 442 */       EntityUtil.entitiesListFlag = true;
/* 443 */       if (!checkEntity(entity)) {
/* 444 */         targetData.remove(entity);
/*     */         
/*     */         continue;
/*     */       } 
/* 448 */       if (ModuleManager.getModule(Freecam.class).isEnabled() && Freecam.INSTANCE.camera == entity) {
/*     */         continue;
/*     */       }
/* 451 */       if (((Boolean)this.ignoreInvisible.getValue()).booleanValue() && entity.func_82150_aj()) {
/*     */         continue;
/*     */       }
/* 454 */       if (((Boolean)this.legitMode.getValue()).booleanValue()) {
/* 455 */         if (mc.field_71476_x.field_72308_g == entity) { targetData.put(entity, Float.valueOf(mc.field_71439_g.func_70032_d(entity))); continue; }
/* 456 */          targetData.remove(entity);
/*     */         continue;
/*     */       } 
/* 459 */       if (!((Boolean)this.rotate.getValue()).booleanValue() && ((Boolean)this.triggerMode.getValue()).booleanValue()) {
/* 460 */         if (!RenderHelper.isInViewFrustrum(entity)) {
/* 461 */           targetData.remove(entity);
/*     */           continue;
/*     */         } 
/* 464 */         double collisionBorderSize = entity.func_70111_Y();
/* 465 */         AxisAlignedBB hitbox = entity.func_174813_aQ().func_72321_a(collisionBorderSize, collisionBorderSize, collisionBorderSize);
/*     */         
/* 467 */         if (((Boolean)this.checkWall.getValue()).booleanValue() && mc.field_71476_x.field_72308_g != entity) {
/* 468 */           return null;
/*     */         }
/* 470 */         for (Vec3d vec : entityTriggerVecList) {
/* 471 */           if (hitbox.func_72318_a(vec)) {
/* 472 */             targetData.put(entity, Float.valueOf(mc.field_71439_g.func_70032_d(entity)));
/*     */             break;
/*     */           } 
/* 475 */           targetData.remove(entity);
/*     */         }  continue;
/*     */       } 
/* 478 */       targetData.put(entity, Float.valueOf(mc.field_71439_g.func_70032_d(entity)));
/*     */     } 
/*     */     
/* 481 */     EntityUtil.entitiesListFlag = false;
/*     */     
/* 483 */     if (mc.field_71441_e.field_72996_f.isEmpty() || ((!checkPreferredWeapons() || ((Boolean)this.autoSwitch.getValue()).booleanValue()) && (!((Boolean)this.autoSwitch.getValue()).booleanValue() || this.preferredWeapon.getValue() == Weapon.None) && this.preferredWeapon.getValue() != Weapon.None)) targetData.clear();
/*     */     
/* 485 */     if (!targetData.isEmpty()) {
/* 486 */       if (((Boolean)this.legitMode.getValue()).booleanValue()) {
/* 487 */         return mc.field_71476_x.field_72308_g;
/*     */       }
/*     */       
/* 490 */       Entity minKey = null;
/* 491 */       float minValue = Float.MAX_VALUE;
/* 492 */       for (Map.Entry<Entity, Float> entry : (new HashMap<>(targetData)).entrySet()) {
/* 493 */         float value = ((Float)targetData.get(entry.getKey())).floatValue();
/* 494 */         if (value < minValue) {
/* 495 */           minValue = value;
/* 496 */           minKey = entry.getKey();
/*     */         } 
/*     */       } 
/*     */       
/* 500 */       return minKey;
/*     */     } 
/*     */ 
/*     */     
/* 504 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private Item preferredWeapon() {
/* 509 */     switch ((Weapon)this.preferredWeapon.getValue()) {
/*     */       case Sword:
/* 511 */         if (ItemUtils.isItemInHotbar(Items.field_151048_u)) return Items.field_151048_u;
/*     */         
/* 513 */         if (ItemUtils.isItemInHotbar(Items.field_151040_l)) return Items.field_151040_l;
/*     */         
/* 515 */         if (ItemUtils.isItemInHotbar(Items.field_151052_q)) return Items.field_151052_q;
/*     */         
/* 517 */         if (ItemUtils.isItemInHotbar(Items.field_151041_m)) return Items.field_151041_m; 
/* 518 */         if (ItemUtils.isItemInHotbar(Items.field_151010_B)) return Items.field_151010_B;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case Axe:
/* 525 */         if (ItemUtils.isItemInHotbar(Items.field_151056_x)) return Items.field_151056_x;
/*     */         
/* 527 */         if (ItemUtils.isItemInHotbar(Items.field_151036_c)) return Items.field_151036_c;
/*     */         
/* 529 */         if (ItemUtils.isItemInHotbar(Items.field_151049_t)) return Items.field_151049_t;
/*     */         
/* 531 */         if (ItemUtils.isItemInHotbar(Items.field_151053_p)) return Items.field_151053_p; 
/* 532 */         if (ItemUtils.isItemInHotbar(Items.field_151006_E)) return Items.field_151006_E;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case PickAxe:
/* 539 */         if (ItemUtils.isItemInHotbar(Items.field_151046_w)) return Items.field_151046_w;
/*     */         
/* 541 */         if (ItemUtils.isItemInHotbar(Items.field_151035_b)) return Items.field_151035_b;
/*     */         
/* 543 */         if (ItemUtils.isItemInHotbar(Items.field_151050_s)) return Items.field_151050_s;
/*     */         
/* 545 */         if (ItemUtils.isItemInHotbar(Items.field_151039_o)) return Items.field_151053_p; 
/* 546 */         if (ItemUtils.isItemInHotbar(Items.field_151005_D)) return Items.field_151005_D;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case Shovel:
/* 553 */         if (ItemUtils.isItemInHotbar(Items.field_151047_v)) return Items.field_151047_v;
/*     */         
/* 555 */         if (ItemUtils.isItemInHotbar(Items.field_151037_a)) return Items.field_151037_a;
/*     */         
/* 557 */         if (ItemUtils.isItemInHotbar(Items.field_151051_r)) return Items.field_151051_r;
/*     */         
/* 559 */         if (ItemUtils.isItemInHotbar(Items.field_151038_n)) return Items.field_151053_p; 
/* 560 */         if (ItemUtils.isItemInHotbar(Items.field_151011_C)) return Items.field_151011_C;
/*     */         
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 566 */     return Items.field_190931_a;
/*     */   }
/*     */   
/*     */   public boolean checkPreferredWeapons() {
/* 570 */     if (mc.field_71439_g != null) {
/* 571 */       switch ((Weapon)this.preferredWeapon.getValue()) {
/*     */         case Sword:
/* 573 */           return (mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151048_u || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151040_l || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151052_q || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151041_m || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151010_B);
/*     */         
/*     */         case Axe:
/* 576 */           return (mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151056_x || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151036_c || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151049_t || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151053_p || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151006_E);
/*     */         
/*     */         case PickAxe:
/* 579 */           return (mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151046_w || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151035_b || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151050_s || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151039_o || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151005_D);
/*     */         
/*     */         case Shovel:
/* 582 */           return (mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151047_v || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151037_a || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151051_r || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151038_n || mc.field_71439_g.func_184614_ca().func_77973_b() == Items.field_151011_C);
/*     */       } 
/*     */     }
/* 585 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getWeaponCooldown() {
/* 590 */     Item item = mc.field_71439_g.func_184614_ca().func_77973_b();
/* 591 */     if (item instanceof net.minecraft.item.ItemSword) {
/* 592 */       return 600;
/*     */     }
/* 594 */     if (item instanceof net.minecraft.item.ItemPickaxe) {
/* 595 */       return 850;
/*     */     }
/* 597 */     if (item == Items.field_151036_c) {
/* 598 */       return 1100;
/*     */     }
/* 600 */     if (item == Items.field_151018_J) {
/* 601 */       return 500;
/*     */     }
/* 603 */     if (item == Items.field_151019_K) {
/* 604 */       return 350;
/*     */     }
/* 606 */     if (item == Items.field_151053_p || item == Items.field_151049_t) {
/* 607 */       return 1250;
/*     */     }
/* 609 */     if (item instanceof net.minecraft.item.ItemSpade || item == Items.field_151006_E || item == Items.field_151056_x || item == Items.field_151017_I || item == Items.field_151013_M) {
/* 610 */       return 1000;
/*     */     }
/* 612 */     return 250;
/*     */   }
/*     */   
/*     */   enum Page {
/* 616 */     Aura,
/* 617 */     Render;
/*     */   }
/*     */   
/*     */   enum Weapon {
/* 621 */     Sword,
/* 622 */     Axe,
/* 623 */     PickAxe,
/* 624 */     Shovel,
/* 625 */     None;
/*     */   }
/*     */   
/*     */   enum RenderType {
/* 629 */     Box,
/* 630 */     Chams,
/* 631 */     Circle,
/* 632 */     None;
/*     */   }
/*     */   
/*     */   enum BoxMode {
/* 636 */     Lines,
/* 637 */     Solid,
/* 638 */     Both;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\combat\Aura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */