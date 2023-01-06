/*     */ package me.thediamondsword5.moloch.module.modules.visuals;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import me.thediamondsword5.moloch.client.EnemyManager;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.spartanb312.base.client.FriendManager;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.render.RenderEvent;
/*     */ import net.spartanb312.base.event.events.render.RenderOverlayEvent;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.ColorUtil;
/*     */ import net.spartanb312.base.utils.EntityUtil;
/*     */ import net.spartanb312.base.utils.MathUtilFuckYou;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import net.spartanb312.base.utils.graphics.RenderHelper;
/*     */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*     */ import net.spartanb312.base.utils.graphics.SpartanTessellator;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "EntityPointer", category = Category.VISUALS, description = "Draws stuff to point at where entities are")
/*     */ public class EntityPointer
/*     */   extends Module {
/*  32 */   Setting<Page> page = setting("Page", Page.Tracers);
/*  33 */   Setting<Float> range = setting("Range", 256.0F, 1.0F, 256.0F).des("Distance to start drawing tracers");
/*  34 */   Setting<Boolean> tracers = setting("Tracers", true).des("Draw lines to entities").whenAtMode(this.page, Page.Tracers);
/*  35 */   Setting<Float> lineWidth = setting("LineWidth", 1.0F, 1.0F, 5.0F).des("Width of tracer lines").whenTrue(this.tracers).whenAtMode(this.page, Page.Tracers);
/*  36 */   Setting<Boolean> spine = setting("Spine", true).des("Draw a line going up the entity's bounding box").whenTrue(this.tracers).whenAtMode(this.page, Page.Tracers);
/*     */   
/*  38 */   Setting<Boolean> arrows = setting("Arrows", false).des("Draw arrows around crosshairs to point at entities").whenAtMode(this.page, Page.Arrows);
/*  39 */   Setting<Float> arrowOffset = setting("ArrowOffset", 15.0F, 1.0F, 100.0F).des("Distance from crosshairs that the arrows should render").whenTrue(this.arrows).whenAtMode(this.page, Page.Arrows);
/*  40 */   Setting<Boolean> offscreenOnly = setting("OffscreenOnly", false).des("Only draw arrows to entities that are offscreen").whenTrue(this.arrows).whenAtMode(this.page, Page.Arrows);
/*  41 */   Setting<Boolean> offscreenFade = setting("OffscreenFade", false).des("Fade arrows when an entity comes onto screen or goes offscreen").whenTrue(this.offscreenOnly).whenTrue(this.arrows).whenAtMode(this.page, Page.Arrows);
/*  42 */   Setting<Float> offscreenFadeFactor = setting("OffscreenFadeFactor", 1.0F, 0.1F, 10.0F).des("Speed of arrows fading").whenTrue(this.offscreenFade).whenTrue(this.offscreenOnly).whenTrue(this.arrows).whenAtMode(this.page, Page.Arrows);
/*  43 */   Setting<Float> arrowWidth = setting("ArrowWidth", 5.0F, 0.0F, 20.0F).des("Width of arrow").whenTrue(this.arrows).whenAtMode(this.page, Page.Arrows);
/*  44 */   Setting<Float> arrowHeight = setting("ArrowHeight", 5.0F, 0.0F, 20.0F).des("Height of arrow").whenTrue(this.arrows).whenAtMode(this.page, Page.Arrows);
/*  45 */   Setting<Boolean> arrowLines = setting("ArrowLines", true).des("Draw an outline on arrows").whenTrue(this.arrows).whenAtMode(this.page, Page.Arrows);
/*  46 */   Setting<Float> arrowLinesWidth = setting("ArrowLinesWidth", 1.0F, 1.0F, 5.0F).des("Width of arrows outline").whenTrue(this.arrowLines).whenTrue(this.arrows).whenAtMode(this.page, Page.Arrows);
/*     */   
/*  48 */   Setting<Boolean> players = setting("Players", true).des("Draw stuff to point at players").whenAtMode(this.page, Page.Entities);
/*  49 */   Setting<Boolean> mobs = setting("Mobs", false).des("Draw stuff to point at mobs").whenAtMode(this.page, Page.Entities);
/*  50 */   Setting<Boolean> animals = setting("Animals", false).des("Draw stuff to point at animals").whenAtMode(this.page, Page.Entities);
/*  51 */   Setting<Boolean> items = setting("Items", false).des("Draw stuff to point at dropped items").whenAtMode(this.page, Page.Entities);
/*  52 */   Setting<Boolean> pearls = setting("Pearls", false).des("Draw stuff to point at pearls").whenAtMode(this.page, Page.Entities);
/*     */   
/*  54 */   Setting<Boolean> playerDistanceColor = setting("PlayerDistColor", false).des("Change color depending on distance").whenAtMode(this.page, Page.Colors);
/*  55 */   Setting<Color> playerColor = setting("PlayerColor", new Color((new Color(255, 255, 50, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 50, 175)).whenAtMode(this.page, Page.Colors);
/*  56 */   Setting<Color> playerColorFar = setting("PlayerColorFar", new Color((new Color(255, 255, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 175)).whenTrue(this.playerDistanceColor).whenAtMode(this.page, Page.Colors);
/*  57 */   Setting<Boolean> playerArrowRainbowWheel = setting("PlayerArrowRainbowWheel", false).des("Colors arrows in a circular rainbow").whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  58 */   Setting<Boolean> playerArrowLinesRainbowWheel = setting("PArrowLinesRainbowWheel", false).des("Colors arrows outline in a circular rainbow").whenTrue(this.arrows).whenTrue(this.arrowLines).whenAtMode(this.page, Page.Colors);
/*  59 */   Setting<Float> playerArrowRainbowWheelSpeed = setting("PArrowRainbowWheelSpeed", 0.5F, 0.1F, 3.0F).des("Speed of circular rainbow wave").only(v -> (((Boolean)this.playerArrowRainbowWheel.getValue()).booleanValue() || ((Boolean)this.playerArrowLinesRainbowWheel.getValue()).booleanValue())).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  60 */   Setting<Float> playerArrowRainbowWheelSaturation = setting("PArrowRainbowWheelSaturation", 0.75F, 0.0F, 1.0F).des("Saturation of circular rainbow wave").only(v -> (((Boolean)this.playerArrowRainbowWheel.getValue()).booleanValue() || ((Boolean)this.playerArrowLinesRainbowWheel.getValue()).booleanValue())).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  61 */   Setting<Float> playerArrowRainbowWheelBrightness = setting("PArrowRainbowWheelBrightness", 0.9F, 0.0F, 1.0F).des("Brightness of circular rainbow wave").only(v -> (((Boolean)this.playerArrowRainbowWheel.getValue()).booleanValue() || ((Boolean)this.playerArrowLinesRainbowWheel.getValue()).booleanValue())).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  62 */   Setting<Color> playerColorArrowLines = setting("PlayerColorArrowLines", new Color((new Color(255, 255, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 175)).whenFalse(this.playerArrowLinesRainbowWheel).whenTrue(this.arrowLines).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  63 */   Setting<Color> friendColor = setting("FriendColor", new Color((new Color(50, 255, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 255, 175)).whenAtMode(this.page, Page.Colors);
/*  64 */   Setting<Color> friendColorFar = setting("FriendColorFar", new Color((new Color(100, 100, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 100, 255, 175)).whenTrue(this.playerDistanceColor).whenAtMode(this.page, Page.Colors);
/*  65 */   Setting<Color> friendColorArrowLines = setting("FriendColorArrowLines", new Color((new Color(255, 255, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 175)).whenTrue(this.arrowLines).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  66 */   Setting<Color> enemyColor = setting("EnemyColor", new Color((new Color(255, 0, 0, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 0, 0, 175)).whenAtMode(this.page, Page.Colors);
/*  67 */   Setting<Color> enemyColorFar = setting("EnemyColorFar", new Color((new Color(255, 100, 100, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 100, 175)).whenTrue(this.playerDistanceColor).whenAtMode(this.page, Page.Colors);
/*  68 */   Setting<Color> enemyColorArrowLines = setting("EnemyColorArrowLines", new Color((new Color(255, 255, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 175)).whenTrue(this.arrowLines).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  69 */   Setting<Boolean> mobDistanceColor = setting("MobDistColor", false).des("Change color depending on distance").whenAtMode(this.page, Page.Colors);
/*  70 */   Setting<Color> mobColor = setting("MobColor", new Color((new Color(255, 170, 50, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 170, 50, 175)).whenAtMode(this.page, Page.Colors);
/*  71 */   Setting<Color> mobColorFar = setting("MobColorFar", new Color((new Color(255, 255, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 175)).whenTrue(this.mobDistanceColor).whenAtMode(this.page, Page.Colors);
/*  72 */   Setting<Boolean> mobArrowRainbowWheel = setting("MobArrowRainbowWheel", false).des("Colors arrows in a circular rainbow").whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  73 */   Setting<Boolean> mobArrowLinesRainbowWheel = setting("MArrowLinesRainbowWheel", false).des("Colors arrows outline in a circular rainbow").whenTrue(this.arrows).whenTrue(this.arrowLines).whenAtMode(this.page, Page.Colors);
/*  74 */   Setting<Float> mobArrowRainbowWheelSpeed = setting("MArrowRainbowWheelSpeed", 0.5F, 0.1F, 3.0F).des("Speed of circular rainbow wave").only(v -> (((Boolean)this.mobArrowRainbowWheel.getValue()).booleanValue() || ((Boolean)this.mobArrowLinesRainbowWheel.getValue()).booleanValue())).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  75 */   Setting<Float> mobArrowRainbowWheelSaturation = setting("MArrowRainbowWheelSaturation", 0.75F, 0.0F, 1.0F).des("Saturation of circular rainbow wave").only(v -> (((Boolean)this.mobArrowRainbowWheel.getValue()).booleanValue() || ((Boolean)this.mobArrowLinesRainbowWheel.getValue()).booleanValue())).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  76 */   Setting<Float> mobArrowRainbowWheelBrightness = setting("MArrowRainbowWheelBrightness", 0.9F, 0.0F, 1.0F).des("Brightness of circular rainbow wave").only(v -> (((Boolean)this.mobArrowRainbowWheel.getValue()).booleanValue() || ((Boolean)this.mobArrowLinesRainbowWheel.getValue()).booleanValue())).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  77 */   Setting<Color> mobColorArrowLines = setting("MobColorArrowLines", new Color((new Color(255, 255, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 175)).whenFalse(this.mobArrowLinesRainbowWheel).whenTrue(this.arrowLines).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  78 */   Setting<Boolean> animalDistanceColor = setting("AnimalDistColor", false).des("Change color depending on distance").whenAtMode(this.page, Page.Colors);
/*  79 */   Setting<Color> animalColor = setting("AnimalColor", new Color((new Color(50, 170, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 170, 255, 175)).whenAtMode(this.page, Page.Colors);
/*  80 */   Setting<Color> animalColorFar = setting("AnimalColorFar", new Color((new Color(50, 50, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 50, 255, 175)).whenTrue(this.animalDistanceColor).whenAtMode(this.page, Page.Colors);
/*  81 */   Setting<Boolean> animalArrowRainbowWheel = setting("AnimalArrowRainbowWheel", false).des("Colors arrows in a circular rainbow").whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  82 */   Setting<Boolean> animalArrowLinesRainbowWheel = setting("AArrowLinesRainbowWheel", false).des("Colors arrows outline in a circular rainbow").whenTrue(this.arrows).whenTrue(this.arrowLines).whenAtMode(this.page, Page.Colors);
/*  83 */   Setting<Float> animalArrowRainbowWheelSpeed = setting("AArrowRainbowWheelSpeed", 0.5F, 0.1F, 3.0F).des("Speed of circular rainbow wave").only(v -> (((Boolean)this.animalArrowRainbowWheel.getValue()).booleanValue() || ((Boolean)this.animalArrowLinesRainbowWheel.getValue()).booleanValue())).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  84 */   Setting<Float> animalArrowRainbowWheelSaturation = setting("AArrowRainbowWheelSaturation", 0.75F, 0.0F, 1.0F).des("Saturation of circular rainbow wave").only(v -> (((Boolean)this.animalArrowRainbowWheel.getValue()).booleanValue() || ((Boolean)this.animalArrowLinesRainbowWheel.getValue()).booleanValue())).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  85 */   Setting<Float> animalArrowRainbowWheelBrightness = setting("AArrowRainbowWheelBrightness", 0.9F, 0.0F, 1.0F).des("Brightness of circular rainbow wave").only(v -> (((Boolean)this.animalArrowRainbowWheel.getValue()).booleanValue() || ((Boolean)this.animalArrowLinesRainbowWheel.getValue()).booleanValue())).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  86 */   Setting<Color> animalColorArrowLines = setting("AnimalColorArrowLines", new Color((new Color(255, 255, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 175)).whenFalse(this.animalArrowLinesRainbowWheel).whenTrue(this.arrowLines).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  87 */   Setting<Boolean> itemDistanceColor = setting("ItemDistColor", false).des("Change color depending on distance").whenAtMode(this.page, Page.Colors);
/*  88 */   Setting<Color> itemColor = setting("ItemColor", new Color((new Color(255, 50, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 255, 175)).whenAtMode(this.page, Page.Colors);
/*  89 */   Setting<Color> itemColorFar = setting("ItemColorFar", new Color((new Color(255, 255, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 175)).whenTrue(this.itemDistanceColor).whenAtMode(this.page, Page.Colors);
/*  90 */   Setting<Boolean> itemArrowRainbowWheel = setting("ItemArrowRainbowWheel", false).des("Colors arrows in a circular rainbow").whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  91 */   Setting<Boolean> itemArrowLinesRainbowWheel = setting("IArrowLinesRainbowWheel", false).des("Colors arrows outline in a circular rainbow").whenTrue(this.arrows).whenTrue(this.arrowLines).whenAtMode(this.page, Page.Colors);
/*  92 */   Setting<Float> itemArrowRainbowWheelSpeed = setting("IArrowRainbowWheelSpeed", 0.5F, 0.1F, 3.0F).des("Speed of circular rainbow wave").only(v -> (((Boolean)this.itemArrowRainbowWheel.getValue()).booleanValue() || ((Boolean)this.itemArrowLinesRainbowWheel.getValue()).booleanValue())).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  93 */   Setting<Float> itemArrowRainbowWheelSaturation = setting("IArrowRainbowWheelSaturation", 0.75F, 0.0F, 1.0F).des("Saturation of circular rainbow wave").only(v -> (((Boolean)this.itemArrowRainbowWheel.getValue()).booleanValue() || ((Boolean)this.itemArrowLinesRainbowWheel.getValue()).booleanValue())).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  94 */   Setting<Float> itemArrowRainbowWheelBrightness = setting("IArrowRainbowWheelBrightness", 0.9F, 0.0F, 1.0F).des("Brightness of circular rainbow wave").only(v -> (((Boolean)this.itemArrowRainbowWheel.getValue()).booleanValue() || ((Boolean)this.itemArrowLinesRainbowWheel.getValue()).booleanValue())).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  95 */   Setting<Color> itemColorArrowLines = setting("ItemColorArrowLines", new Color((new Color(255, 255, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 175)).whenFalse(this.itemArrowLinesRainbowWheel).whenTrue(this.arrowLines).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/*  96 */   Setting<Boolean> pearlDistanceColor = setting("PearlDistColor", false).des("Change color depending on distance").whenAtMode(this.page, Page.Colors);
/*  97 */   Setting<Color> pearlColor = setting("PearlColor", new Color((new Color(50, 255, 50, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 175)).whenAtMode(this.page, Page.Colors);
/*  98 */   Setting<Color> pearlColorFar = setting("PearlColorFar", new Color((new Color(255, 255, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 175)).whenTrue(this.pearlDistanceColor).whenAtMode(this.page, Page.Colors);
/*  99 */   Setting<Color> pearlColorArrowLines = setting("PearlColorArrowLines", new Color((new Color(255, 255, 255, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 175)).whenTrue(this.arrowLines).whenTrue(this.arrows).whenAtMode(this.page, Page.Colors);
/* 100 */   Setting<Float> distanceFactor = setting("DistanceFactor", 0.5F, 0.1F, 1.0F).des("Fraction of range to have entity be considered at the farthest range").only(v -> (((Boolean)this.playerDistanceColor.getValue()).booleanValue() || ((Boolean)this.mobDistanceColor.getValue()).booleanValue() || ((Boolean)this.animalDistanceColor.getValue()).booleanValue() || ((Boolean)this.itemDistanceColor.getValue()).booleanValue() || ((Boolean)this.pearlDistanceColor.getValue()).booleanValue())).whenAtMode(this.page, Page.Colors);
/*     */   
/* 102 */   private final HashMap<Entity, Float> arrowFadeMap = new HashMap<>();
/* 103 */   private final Timer arrowTimer = new Timer();
/*     */ 
/*     */   
/*     */   public void onRenderWorld(RenderEvent event) {
/* 107 */     if (((Boolean)this.tracers.getValue()).booleanValue()) {
/* 108 */       GL11.glPushMatrix();
/* 109 */       EntityUtil.entitiesList().stream()
/* 110 */         .filter(e -> (e != mc.field_175622_Z))
/* 111 */         .filter(e -> (EntityUtil.getInterpDistance(mc.func_184121_ak(), (Entity)mc.field_71439_g, e) <= ((Float)this.range.getValue()).floatValue()))
/* 112 */         .forEach(entity -> {
/*     */             float distanceFactor = 300.0F * MathUtilFuckYou.clamp((float)EntityUtil.getInterpDistance(mc.func_184121_ak(), (Entity)mc.field_71439_g, entity) / ((Float)this.range.getValue()).floatValue() * ((Float)this.distanceFactor.getValue()).floatValue(), 0.0F, 1.0F);
/*     */             
/*     */             if (((Boolean)this.players.getValue()).booleanValue() && entity instanceof net.minecraft.entity.player.EntityPlayer) {
/*     */               int color;
/*     */               
/*     */               if (FriendManager.isFriend(entity)) {
/*     */                 color = ((Boolean)this.playerDistanceColor.getValue()).booleanValue() ? ColorUtil.colorShift(((Color)this.friendColor.getValue()).getColorColor(), ((Color)this.friendColorFar.getValue()).getColorColor(), distanceFactor).getRGB() : ((Color)this.friendColor.getValue()).getColor();
/*     */               } else if (EnemyManager.isEnemy(entity)) {
/*     */                 color = ((Boolean)this.playerDistanceColor.getValue()).booleanValue() ? ColorUtil.colorShift(((Color)this.enemyColor.getValue()).getColorColor(), ((Color)this.enemyColorFar.getValue()).getColorColor(), distanceFactor).getRGB() : ((Color)this.enemyColor.getValue()).getColor();
/*     */               } else {
/*     */                 color = ((Boolean)this.playerDistanceColor.getValue()).booleanValue() ? ColorUtil.colorShift(((Color)this.playerColor.getValue()).getColorColor(), ((Color)this.playerColorFar.getValue()).getColorColor(), distanceFactor).getRGB() : ((Color)this.playerColor.getValue()).getColor();
/*     */               } 
/*     */               
/*     */               SpartanTessellator.drawTracer(entity, ((Float)this.lineWidth.getValue()).floatValue(), ((Boolean)this.spine.getValue()).booleanValue(), color);
/*     */             } 
/*     */             
/*     */             if (((Boolean)this.mobs.getValue()).booleanValue() && EntityUtil.isEntityMob(entity)) {
/*     */               SpartanTessellator.drawTracer(entity, ((Float)this.lineWidth.getValue()).floatValue(), ((Boolean)this.spine.getValue()).booleanValue(), ((Boolean)this.mobDistanceColor.getValue()).booleanValue() ? ColorUtil.colorShift(((Color)this.mobColor.getValue()).getColorColor(), ((Color)this.mobColorFar.getValue()).getColorColor(), distanceFactor).getRGB() : ((Color)this.mobColor.getValue()).getColor());
/*     */             }
/*     */             
/*     */             if (((Boolean)this.animals.getValue()).booleanValue() && EntityUtil.isEntityAnimal(entity)) {
/*     */               SpartanTessellator.drawTracer(entity, ((Float)this.lineWidth.getValue()).floatValue(), ((Boolean)this.spine.getValue()).booleanValue(), ((Boolean)this.animalDistanceColor.getValue()).booleanValue() ? ColorUtil.colorShift(((Color)this.animalColor.getValue()).getColorColor(), ((Color)this.animalColorFar.getValue()).getColorColor(), distanceFactor).getRGB() : ((Color)this.animalColor.getValue()).getColor());
/*     */             }
/*     */             
/*     */             if (((Boolean)this.items.getValue()).booleanValue() && entity instanceof net.minecraft.entity.item.EntityItem) {
/*     */               SpartanTessellator.drawTracer(entity, ((Float)this.lineWidth.getValue()).floatValue(), ((Boolean)this.spine.getValue()).booleanValue(), ((Boolean)this.itemDistanceColor.getValue()).booleanValue() ? ColorUtil.colorShift(((Color)this.itemColor.getValue()).getColorColor(), ((Color)this.itemColorFar.getValue()).getColorColor(), distanceFactor).getRGB() : ((Color)this.itemColor.getValue()).getColor());
/*     */             }
/*     */             
/*     */             if (((Boolean)this.pearls.getValue()).booleanValue() && entity instanceof net.minecraft.entity.item.EntityEnderPearl) {
/*     */               SpartanTessellator.drawTracer(entity, ((Float)this.lineWidth.getValue()).floatValue(), ((Boolean)this.spine.getValue()).booleanValue(), ((Boolean)this.pearlDistanceColor.getValue()).booleanValue() ? ColorUtil.colorShift(((Color)this.pearlColor.getValue()).getColorColor(), ((Color)this.pearlColorFar.getValue()).getColorColor(), distanceFactor).getRGB() : ((Color)this.pearlColor.getValue()).getColor());
/*     */             }
/*     */           });
/* 145 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRender(RenderOverlayEvent event) {
/* 151 */     if (((Boolean)this.arrows.getValue()).booleanValue()) {
/* 152 */       List<Entity> entities; int passedms = (int)this.arrowTimer.hasPassed();
/* 153 */       this.arrowTimer.reset();
/*     */ 
/*     */ 
/*     */       
/* 157 */       if (((Boolean)this.offscreenFade.getValue()).booleanValue()) {
/* 158 */         EntityUtil.entitiesListFlag = true;
/* 159 */         for (Entity entity : EntityUtil.entitiesList()) {
/* 160 */           this.arrowFadeMap.putIfAbsent(entity, Float.valueOf(0.0F));
/*     */         }
/* 162 */         EntityUtil.entitiesListFlag = false;
/* 163 */         entities = new ArrayList<>(this.arrowFadeMap.keySet());
/*     */       } else {
/*     */         
/* 166 */         entities = EntityUtil.entitiesList();
/*     */       } 
/*     */       
/* 169 */       RenderUtils2D.prepareGl();
/* 170 */       EntityUtil.entitiesListFlag = true;
/* 171 */       entities.stream()
/* 172 */         .filter(e -> !e.func_70005_c_().equals(mc.field_175622_Z.func_70005_c_()))
/* 173 */         .filter(e -> !e.func_70005_c_().equals(mc.field_71439_g.func_70005_c_()))
/* 174 */         .filter(e -> (EntityUtil.getInterpDistance(mc.func_184121_ak(), (Entity)mc.field_71439_g, e) <= ((Float)this.range.getValue()).floatValue()))
/* 175 */         .filter(e -> (!((Boolean)this.offscreenOnly.getValue()).booleanValue() || !((Boolean)this.offscreenFade.getValue()).booleanValue() || this.arrowFadeMap.containsKey(e)))
/* 176 */         .forEach(entity -> {
/*     */             float distanceFactor = 300.0F * MathUtilFuckYou.clamp((float)EntityUtil.getInterpDistance(mc.func_184121_ak(), (Entity)mc.field_71439_g, entity) / ((Float)this.range.getValue()).floatValue() * ((Float)this.distanceFactor.getValue()).floatValue(), 0.0F, 1.0F);
/*     */             
/*     */             float alphaFactor = 300.0F;
/*     */             
/*     */             if (((Boolean)this.offscreenOnly.getValue()).booleanValue() && ((Boolean)this.offscreenFade.getValue()).booleanValue()) {
/*     */               alphaFactor = ((Float)this.arrowFadeMap.get(entity)).floatValue();
/*     */               
/*     */               if (passedms < 1000) {
/*     */                 if (!EntityUtil.entitiesList().contains(entity) || RenderHelper.isInViewFrustrum(entity)) {
/*     */                   alphaFactor -= ((Float)this.offscreenFadeFactor.getValue()).floatValue() / 10.0F * passedms;
/*     */                 } else {
/*     */                   alphaFactor += ((Float)this.offscreenFadeFactor.getValue()).floatValue() / 10.0F * passedms;
/*     */                 } 
/*     */               }
/*     */               
/*     */               alphaFactor = MathUtilFuckYou.clamp(alphaFactor, 0.0F, 300.0F);
/*     */               
/*     */               this.arrowFadeMap.put(entity, Float.valueOf(alphaFactor));
/*     */               
/*     */               if (alphaFactor < 0.0F) {
/*     */                 this.arrowFadeMap.remove(entity);
/*     */               }
/*     */             } 
/*     */             
/*     */             if (alphaFactor > 0.0F) {
/*     */               float rotation = getYawToEntity(entity) - mc.field_175622_Z.field_70177_z + ((mc.field_71474_y.field_74320_O == 2) ? 0.0F : 180.0F);
/*     */               
/*     */               if (((Boolean)this.players.getValue()).booleanValue() && entity instanceof net.minecraft.entity.player.EntityPlayer) {
/*     */                 int color;
/*     */                 
/*     */                 int linesColor;
/*     */                 
/*     */                 if (FriendManager.isFriend(entity)) {
/*     */                   color = ((Boolean)this.playerDistanceColor.getValue()).booleanValue() ? ColorUtil.colorShift(((Color)this.friendColor.getValue()).getColorColor(), ((Color)this.friendColorFar.getValue()).getColorColor(), distanceFactor).getRGB() : ((Color)this.friendColor.getValue()).getColor();
/*     */                   
/*     */                   linesColor = ((Color)this.friendColorArrowLines.getValue()).getColor();
/*     */                 } else if (EnemyManager.isEnemy(entity)) {
/*     */                   color = ((Boolean)this.playerDistanceColor.getValue()).booleanValue() ? ColorUtil.colorShift(((Color)this.enemyColor.getValue()).getColorColor(), ((Color)this.enemyColorFar.getValue()).getColorColor(), distanceFactor).getRGB() : ((Color)this.enemyColor.getValue()).getColor();
/*     */                   
/*     */                   linesColor = ((Color)this.enemyColorArrowLines.getValue()).getColor();
/*     */                 } else {
/*     */                   color = ((Boolean)this.playerDistanceColor.getValue()).booleanValue() ? ColorUtil.colorShift(((Color)this.playerColor.getValue()).getColorColor(), ((Color)this.playerColorFar.getValue()).getColorColor(), distanceFactor).getRGB() : ((Color)this.playerColor.getValue()).getColor();
/*     */                   
/*     */                   linesColor = ((Color)this.playerColorArrowLines.getValue()).getColor();
/*     */                   
/*     */                   if (((Boolean)this.playerArrowRainbowWheel.getValue()).booleanValue()) {
/*     */                     color = ColorUtil.rolledRainbowCircular((int)rotation, ((Float)this.playerArrowRainbowWheelSpeed.getValue()).floatValue() / 10.0F, ((Float)this.playerArrowRainbowWheelSaturation.getValue()).floatValue(), ((Float)this.playerArrowRainbowWheelBrightness.getValue()).floatValue());
/*     */                   }
/*     */                   
/*     */                   if (((Boolean)this.playerArrowLinesRainbowWheel.getValue()).booleanValue()) {
/*     */                     linesColor = ColorUtil.rolledRainbowCircular((int)rotation, ((Float)this.playerArrowRainbowWheelSpeed.getValue()).floatValue() / 10.0F, ((Float)this.playerArrowRainbowWheelSaturation.getValue()).floatValue(), ((Float)this.playerArrowRainbowWheelBrightness.getValue()).floatValue());
/*     */                   }
/*     */                 } 
/*     */                 
/*     */                 drawArrow(entity, color, linesColor, alphaFactor / 300.0F);
/*     */               } 
/*     */               
/*     */               if (((Boolean)this.mobs.getValue()).booleanValue() && EntityUtil.isEntityMob(entity)) {
/*     */                 int color = ((Boolean)this.mobDistanceColor.getValue()).booleanValue() ? ColorUtil.colorShift(((Color)this.mobColor.getValue()).getColorColor(), ((Color)this.mobColorFar.getValue()).getColorColor(), distanceFactor).getRGB() : ((Color)this.mobColor.getValue()).getColor();
/*     */                 
/*     */                 int linesColor = ((Color)this.mobColorArrowLines.getValue()).getColor();
/*     */                 
/*     */                 if (((Boolean)this.mobArrowRainbowWheel.getValue()).booleanValue()) {
/*     */                   color = ColorUtil.rolledRainbowCircular((int)rotation, ((Float)this.mobArrowRainbowWheelSpeed.getValue()).floatValue() / 10.0F, ((Float)this.mobArrowRainbowWheelSaturation.getValue()).floatValue(), ((Float)this.mobArrowRainbowWheelBrightness.getValue()).floatValue());
/*     */                 }
/*     */                 
/*     */                 if (((Boolean)this.mobArrowLinesRainbowWheel.getValue()).booleanValue()) {
/*     */                   linesColor = ColorUtil.rolledRainbowCircular((int)rotation, ((Float)this.mobArrowRainbowWheelSpeed.getValue()).floatValue() / 10.0F, ((Float)this.mobArrowRainbowWheelSaturation.getValue()).floatValue(), ((Float)this.mobArrowRainbowWheelBrightness.getValue()).floatValue());
/*     */                 }
/*     */                 
/*     */                 drawArrow(entity, color, linesColor, alphaFactor / 300.0F);
/*     */               } 
/*     */               
/*     */               if (((Boolean)this.animals.getValue()).booleanValue() && EntityUtil.isEntityAnimal(entity)) {
/*     */                 int color = ((Boolean)this.animalDistanceColor.getValue()).booleanValue() ? ColorUtil.colorShift(((Color)this.animalColor.getValue()).getColorColor(), ((Color)this.animalColorFar.getValue()).getColorColor(), distanceFactor).getRGB() : ((Color)this.animalColor.getValue()).getColor();
/*     */                 
/*     */                 int linesColor = ((Color)this.animalColorArrowLines.getValue()).getColor();
/*     */                 if (((Boolean)this.animalArrowRainbowWheel.getValue()).booleanValue()) {
/*     */                   color = ColorUtil.rolledRainbowCircular((int)rotation, ((Float)this.animalArrowRainbowWheelSpeed.getValue()).floatValue() / 10.0F, ((Float)this.animalArrowRainbowWheelSaturation.getValue()).floatValue(), ((Float)this.animalArrowRainbowWheelBrightness.getValue()).floatValue());
/*     */                 }
/*     */                 if (((Boolean)this.animalArrowLinesRainbowWheel.getValue()).booleanValue()) {
/*     */                   linesColor = ColorUtil.rolledRainbowCircular((int)rotation, ((Float)this.animalArrowRainbowWheelSpeed.getValue()).floatValue() / 10.0F, ((Float)this.animalArrowRainbowWheelSaturation.getValue()).floatValue(), ((Float)this.animalArrowRainbowWheelBrightness.getValue()).floatValue());
/*     */                 }
/*     */                 drawArrow(entity, color, linesColor, alphaFactor / 300.0F);
/*     */               } 
/*     */               if (((Boolean)this.items.getValue()).booleanValue() && entity instanceof net.minecraft.entity.item.EntityItem) {
/*     */                 int color = ((Boolean)this.itemDistanceColor.getValue()).booleanValue() ? ColorUtil.colorShift(((Color)this.itemColor.getValue()).getColorColor(), ((Color)this.itemColorFar.getValue()).getColorColor(), distanceFactor).getRGB() : ((Color)this.itemColor.getValue()).getColor();
/*     */                 int linesColor = ((Color)this.itemColorArrowLines.getValue()).getColor();
/*     */                 if (((Boolean)this.itemArrowRainbowWheel.getValue()).booleanValue()) {
/*     */                   color = ColorUtil.rolledRainbowCircular((int)rotation, ((Float)this.itemArrowRainbowWheelSpeed.getValue()).floatValue() / 10.0F, ((Float)this.itemArrowRainbowWheelSaturation.getValue()).floatValue(), ((Float)this.itemArrowRainbowWheelBrightness.getValue()).floatValue());
/*     */                 }
/*     */                 if (((Boolean)this.itemArrowLinesRainbowWheel.getValue()).booleanValue()) {
/*     */                   linesColor = ColorUtil.rolledRainbowCircular((int)rotation, ((Float)this.itemArrowRainbowWheelSpeed.getValue()).floatValue() / 10.0F, ((Float)this.itemArrowRainbowWheelSaturation.getValue()).floatValue(), ((Float)this.itemArrowRainbowWheelBrightness.getValue()).floatValue());
/*     */                 }
/*     */                 drawArrow(entity, color, linesColor, alphaFactor / 300.0F);
/*     */               } 
/*     */               if (((Boolean)this.pearls.getValue()).booleanValue() && entity instanceof net.minecraft.entity.item.EntityEnderPearl) {
/*     */                 drawArrow(entity, ((Boolean)this.pearlDistanceColor.getValue()).booleanValue() ? ColorUtil.colorShift(((Color)this.pearlColor.getValue()).getColorColor(), ((Color)this.pearlColorFar.getValue()).getColorColor(), distanceFactor).getRGB() : ((Color)this.pearlColor.getValue()).getColor(), ((Color)this.pearlColorArrowLines.getValue()).getColor(), alphaFactor / 300.0F);
/*     */               }
/*     */             } 
/*     */           });
/* 278 */       RenderUtils2D.releaseGl();
/* 279 */       EntityUtil.entitiesListFlag = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawArrow(Entity entity, int color, int linesColor, float alphaFactor) {
/* 284 */     float rotation = getYawToEntity(entity) - mc.field_175622_Z.field_70177_z + ((mc.field_71474_y.field_74320_O == 2) ? 0.0F : 180.0F);
/* 285 */     ScaledResolution scaledResolution = new ScaledResolution(mc);
/* 286 */     int a = color >>> 24 & 0xFF;
/* 287 */     int r = color >>> 16 & 0xFF;
/* 288 */     int g = color >>> 8 & 0xFF;
/* 289 */     int b = color & 0xFF;
/*     */     
/* 291 */     int la = linesColor >>> 24 & 0xFF;
/* 292 */     int lr = linesColor >>> 16 & 0xFF;
/* 293 */     int lg = linesColor >>> 8 & 0xFF;
/* 294 */     int lb = linesColor & 0xFF;
/*     */     
/* 296 */     GL11.glTranslatef(scaledResolution.func_78326_a() / 2.0F, scaledResolution.func_78328_b() / 2.0F, 0.0F);
/* 297 */     GL11.glRotatef(rotation, 0.0F, 0.0F, 1.0F);
/* 298 */     GL11.glTranslatef(-scaledResolution.func_78326_a() / 2.0F, -scaledResolution.func_78328_b() / 2.0F, 0.0F);
/*     */     
/* 300 */     GL11.glTranslatef(scaledResolution.func_78326_a() / 2.0F, scaledResolution.func_78328_b() / 2.0F, 0.0F);
/* 301 */     RenderUtils2D.drawTriangle(0.0F, ((Float)this.arrowOffset.getValue()).floatValue() + ((Float)this.arrowHeight.getValue()).floatValue() / 2.0F, ((Float)this.arrowWidth.getValue()).floatValue() / 2.0F, ((Float)this.arrowOffset.getValue()).floatValue() - ((Float)this.arrowHeight.getValue()).floatValue() / 2.0F, -((Float)this.arrowWidth.getValue()).floatValue() / 2.0F, ((Float)this.arrowOffset.getValue()).floatValue() - ((Float)this.arrowHeight.getValue()).floatValue() / 2.0F, (new Color(r, g, b, (int)(a * alphaFactor)))
/* 302 */         .getRGB());
/* 303 */     if (((Boolean)this.arrowLines.getValue()).booleanValue()) {
/* 304 */       RenderUtils2D.drawTriangleOutline(0.0F, ((Float)this.arrowOffset.getValue()).floatValue() + ((Float)this.arrowHeight.getValue()).floatValue() / 2.0F, ((Float)this.arrowWidth.getValue()).floatValue() / 2.0F, ((Float)this.arrowOffset.getValue()).floatValue() - ((Float)this.arrowHeight.getValue()).floatValue() / 2.0F, -((Float)this.arrowWidth.getValue()).floatValue() / 2.0F, ((Float)this.arrowOffset.getValue()).floatValue() - ((Float)this.arrowHeight.getValue()).floatValue() / 2.0F, ((Float)this.arrowLinesWidth
/* 305 */           .getValue()).floatValue(), (new Color(lr, lg, lb, (int)(la * alphaFactor))).getRGB());
/*     */     }
/* 307 */     GL11.glTranslatef(-scaledResolution.func_78326_a() / 2.0F, -scaledResolution.func_78328_b() / 2.0F, 0.0F);
/*     */     
/* 309 */     GL11.glTranslatef(scaledResolution.func_78326_a() / 2.0F, scaledResolution.func_78328_b() / 2.0F, 0.0F);
/* 310 */     GL11.glRotatef(-rotation, 0.0F, 0.0F, 1.0F);
/* 311 */     GL11.glTranslatef(-scaledResolution.func_78326_a() / 2.0F, -scaledResolution.func_78328_b() / 2.0F, 0.0F);
/*     */   }
/*     */   
/*     */   private float getYawToEntity(Entity entity) {
/* 315 */     double x = entity.field_70165_t - mc.field_175622_Z.field_70165_t;
/* 316 */     double z = entity.field_70161_v - mc.field_175622_Z.field_70161_v;
/* 317 */     return (float)-(Math.atan2(x, z) * 57.29577951308232D);
/*     */   }
/*     */   
/*     */   enum Page {
/* 321 */     Tracers,
/* 322 */     Arrows,
/* 323 */     Entities,
/* 324 */     Colors;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\visuals\EntityPointer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */