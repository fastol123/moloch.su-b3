/*     */ package me.thediamondsword5.moloch.module.modules.visuals;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import me.thediamondsword5.moloch.client.EnemyManager;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.event.events.render.RenderEntityEvent;
/*     */ import me.thediamondsword5.moloch.event.events.render.RenderEntityInvokeEvent;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.spartanb312.base.client.FriendManager;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.concurrent.ConcurrentTaskManager;
/*     */ import net.spartanb312.base.core.concurrent.repeat.RepeatUnit;
/*     */ import net.spartanb312.base.core.event.Listener;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.EntityUtil;
/*     */ import net.spartanb312.base.utils.graphics.RenderHelper;
/*     */ import net.spartanb312.base.utils.graphics.SpartanTessellator;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ @ModuleInfo(name = "Chams", category = Category.VISUALS, description = "Do weird stuff with entity rendering")
/*     */ public class Chams
/*     */   extends Module
/*     */ {
/*     */   public static Chams instance;
/*  42 */   public final ResourceLocation loadedTexturePackGlint = new ResourceLocation("textures/misc/enchanted_item_glint.png");
/*  43 */   public final ResourceLocation gradientGlint = new ResourceLocation("moloch:textures/glints/gradient.png");
/*  44 */   public final ResourceLocation lightningGlint = new ResourceLocation("moloch:textures/glints/lightning.png");
/*  45 */   public final ResourceLocation linesGlint = new ResourceLocation("moloch:textures/glints/lines.png");
/*  46 */   public final ResourceLocation swirlsGlint = new ResourceLocation("moloch:textures/glints/swirls.png");
/*  47 */   private final List<RepeatUnit> repeatUnits = new ArrayList<>();
/*     */   
/*  49 */   Setting<Page> page = setting("Page", Page.Players);
/*     */   
/*  51 */   public Setting<Boolean> ignoreInvisible = setting("IgnoreInvisible", false).des("Doesn't render chams for invisible entities").only(v -> (this.page.getValue() == Page.Players || this.page.getValue() == Page.Mobs || this.page.getValue() == Page.Animals));
/*  52 */   public Setting<Boolean> players = setting("Players", true).des("Render player chams").whenAtMode(this.page, Page.Players);
/*  53 */   public Setting<Boolean> otherPlayers = setting("OtherPlayers", true).des("Render player chams for other players").whenTrue(this.players).whenAtMode(this.page, Page.Players);
/*  54 */   public Setting<Boolean> self = setting("Self", true).des("Render self chams").whenTrue(this.players).whenAtMode(this.page, Page.Players);
/*  55 */   public Setting<Boolean> fixPlayerOutlineESP = setting("PlayerFixOutlineESP", false).des("Renders the most basic player chams so outline ESP doesn't break").whenTrue(this.players).whenAtMode(this.page, Page.Players);
/*  56 */   public Setting<Boolean> playerWall = setting("PlayerWalls", true).des("Render players through wall").whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  57 */   Setting<Boolean> playerCancelVanillaRender = setting("PlayerNoVanillaRender", true).des("Cancels normal minecraft player rendering").whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  58 */   Setting<Boolean> playerTexture = setting("PlayerTexture", true).des("Render player texture on chams").whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  59 */   Setting<Boolean> selfTexture = setting("SelfTexture", false).des("Render self texture on chams").whenTrue(this.self).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  60 */   Setting<Boolean> playerDepthMask = setting("PlayerDepthMask", true).des("Enable depth mask for players (stops entity layers and tile entities from rendering through chams)").whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  61 */   Setting<Boolean> playerLighting = setting("PlayerLighting", false).des("Render player chams with lighting").whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  62 */   Setting<Boolean> playerCull = setting("PlayerCull", true).des("Don't render sides of player chams that you can't see").whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  63 */   public Setting<Boolean> playerNoHurt = setting("PlayerNoHurt", true).des("Don't render hurt effect when player is damaged").whenTrue(this.players).whenAtMode(this.page, Page.Players);
/*  64 */   Setting<Boolean> playerBlend = setting("PlayerBlend", false).des("Use additive blending on player chams").whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  65 */   Setting<Boolean> playerCrowdAlpha = setting("PlayerCrowdAlpha", true).des("Reduce alpha of player chams when close to you").whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  66 */   Setting<Float> playerCrowdAlphaRadius = setting("PlayerCrowdAlphaDist", 2.0F, 0.5F, 4.0F).des("Distance to start reducing alpha of player chams close to you").whenTrue(this.playerCrowdAlpha).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  67 */   Setting<Float> playerCrowdEndAlpha = setting("PlayerCrowdEndAlpha", 0.3F, 0.0F, 1.0F).des("Percentage of alpha when player chams are close to you").whenTrue(this.playerCrowdAlpha).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  68 */   public Setting<Boolean> playerChangeCapeAlpha = setting("PChangeCapeAlpha", false).des("Change cape alpha of players").whenTrue(this.players).whenAtMode(this.page, Page.Players);
/*  69 */   public Setting<Boolean> selfChangeCapeAlpha = setting("SelfChangeCapeAlpha", false).des("Change your own cape alpha").whenTrue(this.playerChangeCapeAlpha).whenTrue(this.players).whenAtMode(this.page, Page.Players);
/*  70 */   public Setting<Integer> playerCapeAlpha = setting("PlayerCapeAlpha", 129, 0, 255).des("Alpha of player capes").whenTrue(this.playerChangeCapeAlpha).whenTrue(this.players).whenAtMode(this.page, Page.Players);
/*  71 */   public Setting<Boolean> playerChangeArmorAlpha = setting("PChangeArmorAlpha", false).des("Change armor alpha of players").whenTrue(this.players).whenAtMode(this.page, Page.Players);
/*  72 */   public Setting<Boolean> selfChangeArmorAlpha = setting("SelfChangeArmorAlpha", true).des("Change your own armor alpha").whenTrue(this.playerChangeArmorAlpha).whenTrue(this.players).whenAtMode(this.page, Page.Players);
/*  73 */   public Setting<Integer> playerArmorAlpha = setting("PlayerArmorAlpha", 150, 0, 255).des("Alpha of player armor").whenTrue(this.playerChangeArmorAlpha).whenTrue(this.players).whenAtMode(this.page, Page.Players);
/*  74 */   public Setting<Boolean> playerWallEffect = setting("PlayerWallEffect", false).des("Render different chams when player is blocked by a wall").whenTrue(this.players).whenTrue(this.playerWall).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  75 */   Setting<Boolean> playerWallTexture = setting("PlayerWallTexture", false).des("Render texture on player chams behind walls").whenTrue(this.playerWallEffect).whenTrue(this.players).whenTrue(this.playerWall).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  76 */   Setting<Boolean> playerWallBlend = setting("PlayerWallBlend", false).des("Use additive blending for player chams behind walls").whenTrue(this.playerWallEffect).whenTrue(this.players).whenTrue(this.playerWall).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  77 */   Setting<Boolean> playerWallGlint = setting("PlayerWallGlint", false).des("Render glint texture on player chams behind walls").whenTrue(this.playerWallEffect).whenTrue(this.players).whenTrue(this.playerWall).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  78 */   Setting<Color> playerWallColor = setting("PlayerWallColor", new Color((new Color(255, 100, 100, 113)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 100, 113)).des("Player chams color behind walls").whenTrue(this.playerWallEffect).whenTrue(this.playerWall).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  79 */   Setting<Color> friendWallColor = setting("FriendWallColor", new Color((new Color(50, 100, 255, 100)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 100, 255, 100)).des("Friend chams color behind walls").whenTrue(this.playerWallEffect).whenTrue(this.playerWall).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  80 */   Setting<Color> enemyWallColor = setting("EnemyWallColor", new Color((new Color(255, 100, 50, 100)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 50, 100)).des("Enemy chams color behind walls").whenTrue(this.playerWallEffect).whenTrue(this.playerWall).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  81 */   Setting<Boolean> playerGlint = setting("PlayerGlint", false).des("Render glint texture on player chams").whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  82 */   Setting<Boolean> selfGlint = setting("SelfGlint", false).des("Render glint texture on yourself").whenTrue(this.self).whenTrue(this.playerGlint).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  83 */   Setting<GlintMode> playerGlintMode = setting("PlayerGlintMode", GlintMode.Swirls).des("Texture of player chams glint").only(v -> (((Boolean)this.playerGlint.getValue()).booleanValue() || ((Boolean)this.playerWallGlint.getValue()).booleanValue())).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  84 */   Setting<Boolean> playerGlintMove = setting("PlayerGlintMove", true).des("Player chams glint move").only(v -> (((Boolean)this.playerGlint.getValue()).booleanValue() || ((Boolean)this.playerWallGlint.getValue()).booleanValue())).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  85 */   Setting<Float> playerGlintMoveSpeed = setting("PlayerGlintMoveSpeed", 0.4F, 0.1F, 1.0F).des("Player chams glint move speed").only(v -> (((Boolean)this.playerGlint.getValue()).booleanValue() || ((Boolean)this.playerWallGlint.getValue()).booleanValue())).whenTrue(this.playerGlint).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  86 */   Setting<Float> playerGlintScale = setting("PlayerGlintScale", 4.0F, 0.1F, 4.0F).des("Size of player chams glint texture").only(v -> (((Boolean)this.playerGlint.getValue()).booleanValue() || ((Boolean)this.playerWallGlint.getValue()).booleanValue())).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  87 */   Setting<Color> playerGlintColor = setting("PlayerGlintColor", new Color((new Color(125, 40, 255, 144)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 125, 40, 255, 144)).des("Player chams glint color").only(v -> (((Boolean)this.playerGlint.getValue()).booleanValue() || ((Boolean)this.playerWallGlint.getValue()).booleanValue())).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  88 */   Setting<Color> friendGlintColor = setting("FriendGlintColor", new Color((new Color(50, 200, 255, 100)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 200, 255, 100)).des("Friend chams glint color").only(v -> (((Boolean)this.playerGlint.getValue()).booleanValue() || ((Boolean)this.playerWallGlint.getValue()).booleanValue())).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  89 */   Setting<Color> enemyGlintColor = setting("EnemyGlintColor", new Color((new Color(255, 50, 50, 100)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 100)).des("Enemy chams glint color").only(v -> (((Boolean)this.playerGlint.getValue()).booleanValue() || ((Boolean)this.playerWallGlint.getValue()).booleanValue())).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  90 */   Setting<Color> selfGlintColor = setting("SelfGlintColor", new Color((new Color(125, 50, 255, 84)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 125, 50, 255, 84)).des("Self chams glint color").whenTrue(this.self).whenTrue(this.selfGlint).whenTrue(this.playerGlint).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  91 */   public Setting<Boolean> playerBypassArmor = setting("PlayerThroughArmor", true).des("Render player chams through armor").whenTrue(this.playerWall).whenFalse(this.playerWallEffect).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  92 */   public Setting<Boolean> playerBypassArmorWall = setting("PlayerThroughArmorWall", false).des("Render player chams through armor only through wall").whenTrue(this.playerWall).whenFalse(this.playerWallEffect).whenTrue(this.playerBypassArmor).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  93 */   Setting<Color> playerColor = setting("PlayerColor", new Color((new Color(255, 255, 255, 153)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 153)).des("Player chams color").whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  94 */   Setting<Color> friendColor = setting("FriendColor", new Color((new Color(100, 200, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 255)).des("Friend chams color").whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  95 */   Setting<Color> enemyColor = setting("EnemyColor", new Color((new Color(255, 100, 100, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 255)).des("Enemy chams color").whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*  96 */   Setting<Color> selfColor = setting("SelfColor", new Color((new Color(255, 255, 255, 156)).getRGB(), false, true, 3.4F, 0.51F, 0.9F, 255, 255, 255, 156)).des("Self chams color").whenTrue(this.self).whenTrue(this.players).whenFalse(this.fixPlayerOutlineESP).whenAtMode(this.page, Page.Players);
/*     */   
/*  98 */   public Setting<Boolean> mobs = setting("Mobs", false).des("Render mob chams").whenAtMode(this.page, Page.Mobs);
/*  99 */   public Setting<Boolean> fixMobOutlineESP = setting("MobFixOutlineESP", false).des("Renders the most basic mob chams so outline ESP doesn't break").whenTrue(this.mobs).whenAtMode(this.page, Page.Mobs);
/* 100 */   public Setting<Boolean> mobWall = setting("MobWalls", true).des("Render mobs through wall").whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 101 */   Setting<Boolean> mobCancelVanillaRender = setting("MobNoVanillaRender", false).des("Cancels normal minecraft mob rendering").whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 102 */   Setting<Boolean> mobTexture = setting("MobTexture", false).des("Render mob texture on chams").whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 103 */   Setting<Boolean> mobDepthMask = setting("MobDepthMask", false).des("Enable depth mask for mobs (stops entity layers and tile entities from rendering through chams)").whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 104 */   Setting<Boolean> mobLighting = setting("MobLighting", false).des("Render mob chams with lighting").whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 105 */   Setting<Boolean> mobCull = setting("MobCull", true).des("Don't render sides of mob chams that you can't see").whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 106 */   public Setting<Boolean> mobNoHurt = setting("MobNoHurt", true).des("Don't render hurt effect when mob is damaged").whenTrue(this.mobs).whenAtMode(this.page, Page.Mobs);
/* 107 */   Setting<Boolean> mobBlend = setting("MobBlend", false).des("Use additive blending on mob chams").whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 108 */   Setting<Boolean> mobCrowdAlpha = setting("MobCrowdAlpha", false).des("Reduce alpha of mob chams when close to you").whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 109 */   public Setting<Boolean> mobWallEffect = setting("MobWallEffect", false).des("Render different chams when mob is blocked by a wall").whenTrue(this.mobs).whenTrue(this.mobWall).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 110 */   Setting<Boolean> mobWallTexture = setting("MobWallTexture", false).des("Render texture on mob chams behind walls").whenTrue(this.mobWallEffect).whenTrue(this.mobs).whenTrue(this.mobWall).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 111 */   Setting<Boolean> mobWallBlend = setting("MobWallBlend", false).des("Use additive blending for mob chams behind walls").whenTrue(this.mobWallEffect).whenTrue(this.mobs).whenTrue(this.mobWall).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 112 */   Setting<Boolean> mobWallGlint = setting("MobWallGlint", false).des("Render glint texture on mob chams behind walls").whenTrue(this.mobWallEffect).whenTrue(this.mobs).whenTrue(this.mobWall).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 113 */   Setting<Color> mobWallColor = setting("MobWallColor", new Color((new Color(100, 100, 100, 100)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 100, 100, 100)).des("Mob chams color behind walls").whenTrue(this.mobWallEffect).whenTrue(this.mobWall).whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 114 */   Setting<Float> mobCrowdAlphaRadius = setting("MobCrowdAlphaDist", 1.0F, 0.5F, 4.0F).des("Distance to start reducing alpha of mob chams close to you").whenTrue(this.mobCrowdAlpha).whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 115 */   Setting<Float> mobCrowdEndAlpha = setting("MobCrowdEndAlpha", 0.5F, 0.0F, 1.0F).des("Percentage of alpha when mob chams are close to you").whenTrue(this.mobCrowdAlpha).whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 116 */   public Setting<Boolean> mobChangeArmorAlpha = setting("MChangeArmorAlpha", false).des("Change armor alpha of mobs").whenTrue(this.mobs).whenAtMode(this.page, Page.Mobs);
/* 117 */   public Setting<Integer> mobArmorAlpha = setting("MobArmorAlpha", 150, 0, 255).des("Alpha of mob armor").whenTrue(this.mobChangeArmorAlpha).whenTrue(this.mobs).whenAtMode(this.page, Page.Mobs);
/* 118 */   Setting<Boolean> mobGlint = setting("MobGlint", false).des("Render glint texture on mob chams").whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 119 */   Setting<GlintMode> mobGlintMode = setting("MobGlintMode", GlintMode.Gradient).des("Texture of mob chams glint").only(v -> (((Boolean)this.mobGlint.getValue()).booleanValue() || ((Boolean)this.mobWallGlint.getValue()).booleanValue())).whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 120 */   Setting<Boolean> mobGlintMove = setting("MobGlintMove", false).des("Mob chams glint move").only(v -> (((Boolean)this.mobGlint.getValue()).booleanValue() || ((Boolean)this.mobWallGlint.getValue()).booleanValue())).whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 121 */   Setting<Float> mobGlintMoveSpeed = setting("MobGlintMoveSpeed", 0.4F, 0.1F, 1.0F).des("Mob chams glint move speed").whenTrue(this.mobGlintMove).only(v -> (((Boolean)this.mobGlint.getValue()).booleanValue() || ((Boolean)this.mobWallGlint.getValue()).booleanValue())).whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 122 */   Setting<Float> mobGlintScale = setting("MobGlintScale", 1.0F, 0.1F, 4.0F).des("Size of mob chams glint texture").only(v -> (((Boolean)this.mobGlint.getValue()).booleanValue() || ((Boolean)this.mobWallGlint.getValue()).booleanValue())).whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 123 */   Setting<Color> mobGlintColor = setting("MobGlintColor", new Color((new Color(125, 50, 255, 100)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 125, 50, 255, 100)).des("Mob chams glint color").only(v -> (((Boolean)this.mobGlint.getValue()).booleanValue() || ((Boolean)this.mobWallGlint.getValue()).booleanValue())).whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 124 */   public Setting<Boolean> mobBypassArmor = setting("MobThroughArmor", false).des("Render mob chams through armor").whenTrue(this.mobWall).whenFalse(this.mobWallEffect).whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 125 */   public Setting<Boolean> mobBypassArmorWall = setting("MobThroughArmorWall", false).des("Render mob chams through armor only through wall").whenTrue(this.mobWall).whenFalse(this.mobWallEffect).whenTrue(this.mobBypassArmor).whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/* 126 */   Setting<Color> mobColor = setting("MobColor", new Color((new Color(255, 255, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 255)).des("Mob chams color").whenTrue(this.mobs).whenFalse(this.fixMobOutlineESP).whenAtMode(this.page, Page.Mobs);
/*     */   
/* 128 */   public Setting<Boolean> animals = setting("Animals", false).des("Render animal chams").whenAtMode(this.page, Page.Animals);
/* 129 */   public Setting<Boolean> fixAnimalOutlineESP = setting("AnimalFixOutlineESP", false).des("Renders the most basic animal chams so outline ESP doesn't break").whenTrue(this.animals).whenAtMode(this.page, Page.Animals);
/* 130 */   public Setting<Boolean> animalWall = setting("AnimalWalls", true).des("Render animals through wall").whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 131 */   Setting<Boolean> animalCancelVanillaRender = setting("AnimalNoVanillaRender", false).des("Cancels normal minecraft animal rendering").whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 132 */   Setting<Boolean> animalTexture = setting("AnimalTexture", false).des("Render animal texture on chams").whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 133 */   Setting<Boolean> animalDepthMask = setting("AnimalDepthMask", false).des("Enable depth mask for animals (stops entity layers and tile entities from rendering through chams)").whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 134 */   Setting<Boolean> animalLighting = setting("AnimalLighting", false).des("Render animal chams with lighting").whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 135 */   Setting<Boolean> animalCull = setting("AnimalCull", true).des("Don't render sides of animal chams that you can't see").whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 136 */   public Setting<Boolean> animalNoHurt = setting("AnimalNoHurt", true).des("Don't render hurt effect when animal is damaged").whenTrue(this.animals).whenAtMode(this.page, Page.Animals);
/* 137 */   Setting<Boolean> animalBlend = setting("AnimalBlend", false).des("Use additive blending on animal chams").whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 138 */   public Setting<Boolean> animalWallEffect = setting("AnimalWallEffect", false).des("Render different chams when animal is blocked by a wall").whenTrue(this.animals).whenTrue(this.animalWall).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 139 */   Setting<Boolean> animalWallTexture = setting("AnimalWallTexture", false).des("Render texture on animal chams behind walls").whenTrue(this.animalWallEffect).whenTrue(this.animals).whenTrue(this.animalWall).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 140 */   Setting<Boolean> animalWallBlend = setting("AnimalWallBlend", false).des("Use additive blending for animal chams behind walls").whenTrue(this.animalWallEffect).whenTrue(this.animals).whenTrue(this.animalWall).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 141 */   Setting<Boolean> animalWallGlint = setting("AnimalWallGlint", false).des("Render glint texture on animal chams behind walls").whenTrue(this.animalWallEffect).whenTrue(this.animals).whenTrue(this.animalWall).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 142 */   Setting<Color> animalWallColor = setting("AnimalWallColor", new Color((new Color(100, 100, 100, 100)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 100, 100, 100)).des("Animal chams color behind walls").whenTrue(this.animalWallEffect).whenTrue(this.animalWall).whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 143 */   Setting<Boolean> animalCrowdAlpha = setting("AnimalCrowdAlpha", false).des("Reduce alpha of animal chams when close to you").whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 144 */   Setting<Float> animalCrowdAlphaRadius = setting("AnimalCrowdAlphaDist", 1.0F, 0.5F, 4.0F).des("Distance to start reducing alpha of animal chams close to you").whenTrue(this.animalCrowdAlpha).whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 145 */   Setting<Float> animalCrowdEndAlpha = setting("AnimalCrowdEndAlpha", 0.5F, 0.0F, 1.0F).des("Percentage of alpha when animal chams are close to you").whenTrue(this.animalCrowdAlpha).whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 146 */   Setting<Boolean> animalGlint = setting("AnimalGlint", false).des("Render glint texture on animal chams").whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 147 */   Setting<GlintMode> animalGlintMode = setting("AnimalGlintMode", GlintMode.Gradient).des("Texture of animal chams glint").only(v -> (((Boolean)this.animalGlint.getValue()).booleanValue() || ((Boolean)this.animalWallGlint.getValue()).booleanValue())).whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 148 */   Setting<Boolean> animalGlintMove = setting("AnimalGlintMove", false).des("Animal chams glint move").only(v -> (((Boolean)this.animalGlint.getValue()).booleanValue() || ((Boolean)this.animalWallGlint.getValue()).booleanValue())).whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 149 */   Setting<Float> animalGlintMoveSpeed = setting("AnimalGlintMoveSpeed", 0.4F, 0.1F, 1.0F).des("Animal chams glint move speed").whenTrue(this.animalGlintMove).only(v -> (((Boolean)this.animalGlint.getValue()).booleanValue() || ((Boolean)this.animalWallGlint.getValue()).booleanValue())).whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 150 */   Setting<Float> animalGlintScale = setting("AnimalGlintScale", 1.0F, 0.1F, 4.0F).des("Size of animal chams glint texture").only(v -> (((Boolean)this.animalGlint.getValue()).booleanValue() || ((Boolean)this.animalWallGlint.getValue()).booleanValue())).whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 151 */   Setting<Color> animalGlintColor = setting("AnimalGlintColor", new Color((new Color(125, 50, 255, 100)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 125, 50, 255, 100)).des("Animal chams glint color").only(v -> (((Boolean)this.animalGlint.getValue()).booleanValue() || ((Boolean)this.animalWallGlint.getValue()).booleanValue())).whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/* 152 */   Setting<Color> animalColor = setting("AnimalColor", new Color((new Color(255, 255, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 255)).des("Animal chams color").whenTrue(this.animals).whenFalse(this.fixAnimalOutlineESP).whenAtMode(this.page, Page.Animals);
/*     */   
/* 154 */   public Setting<Boolean> crystals = setting("Crystals", false).des("Render crystal chams").whenAtMode(this.page, Page.Crystals);
/* 155 */   public Setting<Boolean> fixCrystalOutlineESP = setting("CrystalFixOutlineESP", false).des("Renders the most basic crystal chams so outline ESP doesn't break").whenTrue(this.crystals).whenAtMode(this.page, Page.Crystals);
/* 156 */   public Setting<Boolean> crystalWall = setting("CrystalWalls", true).des("Render crystals through wall").whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 157 */   public Setting<Boolean> crystalCancelVanillaRender = setting("CrystalNoVanillaRender", false).des("Cancels normal minecraft crystal rendering").whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 158 */   public Setting<Boolean> crystalTexture = setting("CrystalTexture", false).des("Render crystal texture on chams").whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 159 */   public Setting<Boolean> crystalDepthMask = setting("CrystalDepthMask", false).des("Enable depth mask for crystals (stops entity layers and tile entities from rendering through chams)").whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 160 */   public Setting<Boolean> crystalLighting = setting("CrystalLighting", false).des("Render crystal chams with lighting").whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 161 */   public Setting<Boolean> crystalCull = setting("CrystalCull", true).des("Don't render sides of crystal chams that you can't see").whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 162 */   public Setting<Boolean> crystalBlend = setting("CrystalBlend", false).des("Use additive blending on crystal chams").whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 163 */   public Setting<Boolean> crystalWallEffect = setting("CrystalWallEffect", false).des("Render different chams when crystal is blocked by a wall").whenTrue(this.crystals).whenTrue(this.crystalWall).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 164 */   public Setting<Boolean> crystalWallTexture = setting("CrystalWallTexture", false).des("Render texture on crystal chams behind walls").whenTrue(this.crystalWallEffect).whenTrue(this.crystals).whenTrue(this.crystalWall).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 165 */   public Setting<Boolean> crystalWallBlend = setting("CrystalWallBlend", false).des("Use additive blending for crystal chams behind walls").whenTrue(this.crystalWallEffect).whenTrue(this.crystals).whenTrue(this.crystalWall).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 166 */   public Setting<Boolean> crystalWallGlint = setting("CrystalWallGlint", false).des("Render glint texture on crystal chams behind walls").whenTrue(this.crystalWallEffect).whenTrue(this.crystals).whenTrue(this.crystalWall).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 167 */   public Setting<Color> crystalWallColor = setting("CrystalWallColor", new Color((new Color(100, 100, 100, 100)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 100, 100, 100)).des("Crystal chams color behind walls").whenTrue(this.crystalWallEffect).whenTrue(this.crystalWall).whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 168 */   public Setting<Boolean> crystalCrowdAlpha = setting("CrystalCrowdAlpha", false).des("Reduce alpha of crystal chams when close to you").whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 169 */   public Setting<Float> crystalCrowdAlphaRadius = setting("CrystalCrowdAlphaDist", 1.0F, 0.5F, 4.0F).des("Distance to start reducing alpha of crystal chams close to you").whenTrue(this.crystalCrowdAlpha).whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 170 */   public Setting<Float> crystalCrowdEndAlpha = setting("CrystalCrowdEndAlpha", 0.5F, 0.0F, 1.0F).des("Percentage of alpha when crystal chams are close to you").whenTrue(this.crystalCrowdAlpha).whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 171 */   public Setting<Boolean> crystalGlint = setting("CrystalGlint", false).des("Render glint texture on crystal chams").whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 172 */   public Setting<GlintMode> crystalGlintMode = setting("CrystalGlintMode", GlintMode.Gradient).des("Texture of crystal chams glint").only(v -> (((Boolean)this.crystalGlint.getValue()).booleanValue() || ((Boolean)this.crystalWallGlint.getValue()).booleanValue())).whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 173 */   public Setting<Boolean> crystalGlintMove = setting("CrystalGlintMove", false).des("Crystal chams glint move").only(v -> (((Boolean)this.crystalGlint.getValue()).booleanValue() || ((Boolean)this.crystalWallGlint.getValue()).booleanValue())).whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 174 */   public Setting<Float> crystalGlintMoveSpeed = setting("CrystalGlintMoveSpeed", 0.4F, 0.1F, 1.0F).des("Crystal chams glint move speed").whenTrue(this.crystalGlintMove).only(v -> (((Boolean)this.crystalGlint.getValue()).booleanValue() || ((Boolean)this.crystalWallGlint.getValue()).booleanValue())).whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 175 */   public Setting<Float> crystalGlintScale = setting("CrystalGlintScale", 1.0F, 0.1F, 4.0F).des("Size of crystal chams glint texture").only(v -> (((Boolean)this.crystalGlint.getValue()).booleanValue() || ((Boolean)this.crystalWallGlint.getValue()).booleanValue())).whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 176 */   public Setting<Color> crystalGlintColor = setting("CrystalGlintColor", new Color((new Color(125, 50, 255, 100)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 125, 50, 255, 100)).des("Crystal chams glint color").only(v -> (((Boolean)this.crystalGlint.getValue()).booleanValue() || ((Boolean)this.crystalWallGlint.getValue()).booleanValue())).whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 177 */   public Setting<Boolean> crystalOneGlass = setting("CrystalOneGlass", false).des("Only render one glass cube around crystal").whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 178 */   public Setting<Float> crystalYOffset = setting("CrystalYOffset", 0.0F, 0.0F, 5.0F).des("Y offset of crystal chams").whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 179 */   public Setting<Boolean> crystalBobModify = setting("CrystalBobModify", false).des("Modify bob height of crystal chams").whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 180 */   public Setting<Float> crystalBob = setting("CrystalBob", 1.0F, 0.0F, 2.0F).des("Bob height of crystal chams").whenTrue(this.crystalBobModify).whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 181 */   public Setting<Boolean> crystalSpinModify = setting("CrystalSpinModify", false).des("Modify spin speed of crystal chams").whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 182 */   public Setting<Float> crystalSpinSpeed = setting("CrystalSpinSpeed", 1.0F, 0.0F, 4.0F).des("Speed of crystal chams spin").whenTrue(this.crystalSpinModify).whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 183 */   public Setting<Boolean> crystalScaleModify = setting("CrystalScaleModify", false).des("Modify size of crystal chams").whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 184 */   public Setting<Float> crystalScale = setting("CrystalScale", 1.0F, 0.0F, 2.0F).des("Size of crystal chams").whenTrue(this.crystalScaleModify).whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/* 185 */   public Setting<Color> crystalColor = setting("CrystalColor", new Color((new Color(255, 255, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 255)).des("Crystal chams color").whenTrue(this.crystals).whenFalse(this.fixCrystalOutlineESP).whenAtMode(this.page, Page.Crystals);
/*     */ 
/*     */   
/* 188 */   public Setting<Boolean> items = setting("Items", false).des("Render item chams").whenAtMode(this.page, Page.Items);
/* 189 */   public Setting<Boolean> itemsRangeLimit = setting("ItemsRangeLimit", false).des("Item chams limit range").whenTrue(this.items).whenAtMode(this.page, Page.Items);
/* 190 */   public Setting<Float> itemsRange = setting("ItemsRange", 30.0F, 10.0F, 64.0F).des("Item chams range").whenTrue(this.itemsRangeLimit).whenTrue(this.items).whenAtMode(this.page, Page.Items);
/* 191 */   public Setting<Boolean> itemTexture = setting("ItemTexture", false).des("Render item texture on chams").whenTrue(this.items).whenAtMode(this.page, Page.Items);
/* 192 */   public Setting<Boolean> itemLighting = setting("ItemLighting", false).des("Render item chams with lighting").whenTrue(this.items).whenAtMode(this.page, Page.Items);
/* 193 */   public Setting<Boolean> itemBlend = setting("ItemBlend", false).des("Use additive blending on item chams").whenTrue(this.items).whenAtMode(this.page, Page.Items);
/* 194 */   public Setting<Color> itemColor = setting("ItemColor", new Color((new Color(255, 255, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 255)).des("Item chams color").whenTrue(this.items).whenAtMode(this.page, Page.Items);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RepeatUnit noHurt;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chams() {
/* 207 */     this.noHurt = new RepeatUnit(() -> 1, () -> {
/*     */           if (mc.field_71441_e == null)
/*     */             return;  EntityUtil.entitiesListFlag = true; for (Entity entity : EntityUtil.entitiesList()) {
/*     */             if ((entity instanceof net.minecraft.entity.player.EntityPlayer && ((Boolean)this.playerNoHurt.getValue()).booleanValue() && ((Boolean)this.players.getValue()).booleanValue()) || (EntityUtil.isEntityMob(entity) && ((Boolean)this.mobNoHurt.getValue()).booleanValue() && ((Boolean)this.mobs.getValue()).booleanValue()) || (EntityUtil.isEntityAnimal(entity) && ((Boolean)this.animalNoHurt.getValue()).booleanValue() && ((Boolean)this.animals.getValue()).booleanValue()))
/*     */               ((EntityLivingBase)entity).field_70737_aN = 0; 
/*     */           }  EntityUtil.entitiesListFlag = false;
/*     */         });
/*     */     instance = this;
/*     */     this.repeatUnits.add(this.noHurt);
/*     */     this.repeatUnits.forEach(it -> {
/*     */           it.suspend();
/*     */           ConcurrentTaskManager.runRepeat(it);
/* 219 */         }); } public void onRenderTick() { if ((!((Boolean)this.playerNoHurt.getValue()).booleanValue() || !((Boolean)this.players.getValue()).booleanValue()) && (!((Boolean)this.mobNoHurt.getValue()).booleanValue() || !((Boolean)this.mobs.getValue()).booleanValue()) && (!((Boolean)this.animalNoHurt.getValue()).booleanValue() || !((Boolean)this.animals.getValue()).booleanValue())) {
/* 220 */       this.repeatUnits.forEach(RepeatUnit::suspend);
/*     */     } else {
/* 222 */       this.repeatUnits.forEach(RepeatUnit::resume);
/*     */     }  }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/* 227 */     this.repeatUnits.forEach(RepeatUnit::resume);
/* 228 */     this.moduleEnableFlag = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 233 */     this.repeatUnits.forEach(RepeatUnit::suspend);
/* 234 */     this.moduleDisableFlag = true;
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void renderEntity(RenderEntityEvent event) {
/* 239 */     if (RenderHelper.isInViewFrustrum(event.entityIn) && (!((Boolean)this.ignoreInvisible.getValue()).booleanValue() || !event.entityIn.func_82150_aj())) {
/*     */       
/* 241 */       if (event.entityIn instanceof net.minecraft.entity.player.EntityPlayer && ((Boolean)this.players.getValue()).booleanValue()) {
/*     */         
/* 243 */         if (event.entityIn == mc.field_71439_g && !((Boolean)this.self.getValue()).booleanValue())
/* 244 */           return;  if (event.entityIn != mc.field_71439_g && !((Boolean)this.otherPlayers.getValue()).booleanValue())
/*     */           return; 
/* 246 */         if (((Boolean)this.playerGlint.getValue()).booleanValue()) {
/*     */           Color glintColor;
/*     */           
/* 249 */           if (event.entityIn == mc.field_71439_g)
/* 250 */           { glintColor = (Color)this.selfGlintColor.getValue(); }
/*     */           
/* 252 */           else if (FriendManager.isFriend(event.entityIn)) { glintColor = (Color)this.friendGlintColor.getValue(); }
/* 253 */           else if (EnemyManager.isEnemy(event.entityIn)) { glintColor = (Color)this.enemyGlintColor.getValue(); }
/* 254 */           else { glintColor = (Color)this.playerGlintColor.getValue(); }
/*     */ 
/*     */           
/* 257 */           renderChams(event, this.players, this.playerTexture, this.playerLighting, this.playerWall, this.playerCull, this.playerBlend, this.playerCrowdAlpha, this.playerCrowdAlphaRadius, this.playerCrowdEndAlpha, this.fixPlayerOutlineESP, this.playerWallEffect, this.playerWallTexture, this.playerWallBlend, this.playerWallGlint, ((Boolean)this.playerBypassArmor.getValue()).booleanValue(), ((Boolean)this.playerBypassArmorWall.getValue()).booleanValue(), ((Boolean)this.playerDepthMask.getValue()).booleanValue(), ((Boolean)this.playerGlint.getValue()).booleanValue(), (GlintMode)this.playerGlintMode.getValue(), ((Boolean)this.playerGlintMove.getValue()).booleanValue(), ((Float)this.playerGlintMoveSpeed.getValue()).floatValue(), ((Float)this.playerGlintScale.getValue()).floatValue(), glintColor);
/*     */         } else {
/*     */           
/* 260 */           renderChams(event, this.players, this.playerTexture, this.playerLighting, this.playerWall, this.playerCull, this.playerBlend, this.playerCrowdAlpha, this.playerCrowdAlphaRadius, this.playerCrowdEndAlpha, this.fixPlayerOutlineESP, this.playerWallEffect, this.playerWallTexture, this.playerWallBlend, this.playerWallGlint, ((Boolean)this.playerBypassArmor.getValue()).booleanValue(), ((Boolean)this.playerBypassArmorWall.getValue()).booleanValue(), ((Boolean)this.playerDepthMask.getValue()).booleanValue(), ((Boolean)this.playerGlint.getValue()).booleanValue(), (GlintMode)this.playerGlintMode.getValue(), ((Boolean)this.playerGlintMove.getValue()).booleanValue(), ((Float)this.playerGlintMoveSpeed.getValue()).floatValue(), ((Float)this.playerGlintScale.getValue()).floatValue(), new Color(0, false, false, 1.0F, 0.75F, 0.9F, 0, 0, 0, 0));
/*     */         } 
/*     */       } 
/* 263 */       if (EntityUtil.isEntityMob(event.entityIn) && ((Boolean)this.mobs.getValue()).booleanValue())
/* 264 */         renderChams(event, this.mobs, this.mobTexture, this.mobLighting, this.mobWall, this.mobCull, this.mobBlend, this.mobCrowdAlpha, this.mobCrowdAlphaRadius, this.mobCrowdEndAlpha, this.fixMobOutlineESP, this.mobWallEffect, this.mobWallTexture, this.mobWallBlend, this.mobWallGlint, ((Boolean)this.mobBypassArmor.getValue()).booleanValue(), ((Boolean)this.mobBypassArmorWall.getValue()).booleanValue(), ((Boolean)this.mobDepthMask.getValue()).booleanValue(), ((Boolean)this.mobGlint.getValue()).booleanValue(), (GlintMode)this.mobGlintMode.getValue(), ((Boolean)this.mobGlintMove.getValue()).booleanValue(), ((Float)this.mobGlintMoveSpeed.getValue()).floatValue(), ((Float)this.mobGlintScale.getValue()).floatValue(), (Color)this.mobGlintColor.getValue()); 
/* 265 */       if (EntityUtil.isEntityAnimal(event.entityIn) && ((Boolean)this.animals.getValue()).booleanValue())
/* 266 */         renderChams(event, this.animals, this.animalTexture, this.animalLighting, this.animalWall, this.animalCull, this.animalBlend, this.animalCrowdAlpha, this.animalCrowdAlphaRadius, this.animalCrowdEndAlpha, this.fixAnimalOutlineESP, this.animalWallEffect, this.animalWallTexture, this.animalWallBlend, this.animalWallGlint, true, false, ((Boolean)this.animalDepthMask.getValue()).booleanValue(), ((Boolean)this.animalGlint.getValue()).booleanValue(), (GlintMode)this.animalGlintMode.getValue(), ((Boolean)this.animalGlintMove.getValue()).booleanValue(), ((Float)this.animalGlintMoveSpeed.getValue()).floatValue(), ((Float)this.animalGlintScale.getValue()).floatValue(), (Color)this.animalGlintColor.getValue()); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void cancelEntity(RenderEntityInvokeEvent event) {
/* 272 */     if (RenderHelper.isInViewFrustrum(event.entityIn)) {
/* 273 */       if (event.entityIn instanceof net.minecraft.entity.player.EntityPlayer && ((Boolean)this.players.getValue()).booleanValue()) {
/*     */         
/* 275 */         if (event.entityIn == mc.field_71439_g && !((Boolean)this.self.getValue()).booleanValue())
/* 276 */           return;  if (event.entityIn != mc.field_71439_g && !((Boolean)this.otherPlayers.getValue()).booleanValue())
/*     */           return; 
/* 278 */         chamsCancelRender(event, this.playerCancelVanillaRender, this.fixPlayerOutlineESP);
/*     */       } 
/* 280 */       if (EntityUtil.isEntityMob(event.entityIn) && ((Boolean)this.mobs.getValue()).booleanValue())
/* 281 */         chamsCancelRender(event, this.mobCancelVanillaRender, this.fixMobOutlineESP); 
/* 282 */       if (EntityUtil.isEntityAnimal(event.entityIn) && ((Boolean)this.animals.getValue()).booleanValue())
/* 283 */         chamsCancelRender(event, this.animalCancelVanillaRender, this.fixAnimalOutlineESP); 
/*     */     } 
/*     */   }
/*     */   private void renderChams(RenderEntityEvent event, Setting<Boolean> settingTarget, Setting<Boolean> settingTexture, Setting<Boolean> settingLighting, Setting<Boolean> settingWalls, Setting<Boolean> settingCull, Setting<Boolean> settingBlend, Setting<Boolean> settingCrowdAlpha, Setting<Float> settingCrowdAlphaStartRadius, Setting<Float> settingCrowdEndAlpha, Setting<Boolean> settingFixOutlineEsp, Setting<Boolean> settingWallTarget, Setting<Boolean> settingWallTexture, Setting<Boolean> settingWallBlend, Setting<Boolean> settingWallGlint, boolean throughArmor, boolean throughArmorWall, boolean depthMask, boolean glint, GlintMode glintMode, boolean glintMove, float glintMoveSpeed, float glintScale, Color glintColor) {
/*     */     Color color;
/*     */     int alpha;
/* 289 */     if (((Boolean)settingFixOutlineEsp.getValue()).booleanValue())
/*     */       return; 
/* 291 */     float alphaCrowdFactor = 1.0F;
/* 292 */     if (((Boolean)settingCrowdAlpha.getValue()).booleanValue() && event.entityIn != mc.field_71439_g && EntityUtil.getInterpDistance(mc.func_184121_ak(), (Entity)mc.field_71439_g, event.entityIn) <= ((Float)settingCrowdAlphaStartRadius.getValue()).floatValue()) alphaCrowdFactor = ((Float)settingCrowdEndAlpha.getValue()).floatValue() + (1.0F - ((Float)settingCrowdEndAlpha.getValue()).floatValue()) * (float)(EntityUtil.getInterpDistance(mc.func_184121_ak(), (Entity)mc.field_71439_g, event.entityIn) / ((Float)settingCrowdAlphaStartRadius.getValue()).floatValue());
/*     */ 
/*     */     
/* 295 */     if (settingTarget == this.players)
/* 296 */     { if (event.entityIn == mc.field_71439_g) { color = ((Color)this.selfColor.getValue()).getColorColor(); }
/*     */       
/* 298 */       else if (FriendManager.isFriend(event.entityIn)) { color = ((Color)this.friendColor.getValue()).getColorColor(); }
/* 299 */       else if (EnemyManager.isEnemy(event.entityIn)) { color = ((Color)this.enemyColor.getValue()).getColorColor(); }
/* 300 */       else { color = ((Color)this.playerColor.getValue()).getColorColor(); }
/*     */       
/*     */        }
/* 303 */     else if (settingTarget == this.mobs) { color = ((Color)this.mobColor.getValue()).getColorColor(); }
/* 304 */     else if (settingTarget == this.animals) { color = ((Color)this.animalColor.getValue()).getColorColor(); }
/* 305 */     else { color = ((Color)this.itemColor.getValue()).getColorColor(); }
/*     */ 
/*     */ 
/*     */     
/* 309 */     if (settingTarget == this.players)
/* 310 */     { if (event.entityIn == mc.field_71439_g) { alpha = ((Color)this.selfColor.getValue()).getAlpha(); }
/*     */       
/* 312 */       else if (FriendManager.isFriend(event.entityIn)) { alpha = ((Color)this.friendColor.getValue()).getAlpha(); }
/* 313 */       else if (EnemyManager.isEnemy(event.entityIn)) { alpha = ((Color)this.enemyColor.getValue()).getAlpha(); }
/* 314 */       else { alpha = ((Color)this.playerColor.getValue()).getAlpha(); }
/*     */       
/*     */        }
/* 317 */     else if (settingTarget == this.mobs) { alpha = ((Color)this.mobColor.getValue()).getAlpha(); }
/* 318 */     else if (settingTarget == this.animals) { alpha = ((Color)this.animalColor.getValue()).getAlpha(); }
/* 319 */     else { alpha = ((Color)this.itemColor.getValue()).getAlpha(); }
/*     */     
/* 321 */     GL11.glEnable(2848);
/* 322 */     GL11.glEnable(2881);
/* 323 */     GL11.glEnable(3042);
/*     */     
/* 325 */     if (((Boolean)settingBlend.getValue()).booleanValue()) { GL11.glBlendFunc(770, 32772); }
/* 326 */     else { GL11.glBlendFunc(770, 771); }
/*     */     
/* 328 */     if (((Boolean)settingCull.getValue()).booleanValue()) { GlStateManager.func_179089_o(); }
/* 329 */     else { GlStateManager.func_179129_p(); }
/*     */     
/* 331 */     GlStateManager.func_179118_c();
/* 332 */     GlStateManager.func_179132_a(depthMask);
/*     */     
/* 334 */     if (((Boolean)settingWalls.getValue()).booleanValue() && throughArmor && !((Boolean)settingWallTarget.getValue()).booleanValue()) {
/* 335 */       GL11.glDepthRange(0.0D, 0.01D);
/*     */       
/* 337 */       if (throughArmorWall) {
/* 338 */         GlStateManager.func_179132_a(false);
/*     */       }
/*     */     } 
/* 341 */     if (((Boolean)settingLighting.getValue()).booleanValue()) { GL11.glEnable(2896); }
/* 342 */     else { GL11.glDisable(2896); }
/* 343 */      GL11.glPolygonMode(1032, 6914);
/*     */     
/* 345 */     if (((Boolean)settingTexture.getValue()).booleanValue()) { GL11.glEnable(3553); }
/* 346 */     else { GL11.glDisable(3553); }
/*     */     
/* 348 */     if (event.entityIn == mc.field_71439_g) {
/* 349 */       if (((Boolean)this.selfTexture.getValue()).booleanValue()) { GL11.glEnable(3553); }
/* 350 */       else { GL11.glDisable(3553); }
/*     */     
/*     */     }
/* 353 */     GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha / 255.0F * alphaCrowdFactor);
/* 354 */     event.modelBase.func_78088_a(event.entityIn, event.limbSwing, event.limbSwingAmount, event.ageInTicks, event.netHeadYaw, event.headPitch, event.scale);
/*     */ 
/*     */     
/* 357 */     if (((Boolean)settingWalls.getValue()).booleanValue() && throughArmor && throughArmorWall && !((Boolean)settingWallTarget.getValue()).booleanValue()) {
/* 358 */       GlStateManager.func_179132_a(true);
/* 359 */       GL11.glDepthRange(0.0D, 1.0D);
/* 360 */       event.modelBase.func_78088_a(event.entityIn, event.limbSwing, event.limbSwingAmount, event.ageInTicks, event.netHeadYaw, event.headPitch, event.scale);
/*     */     } 
/*     */     
/* 363 */     if (((Boolean)settingWallTarget.getValue()).booleanValue() && ((Boolean)settingWalls.getValue()).booleanValue()) {
/* 364 */       renderWallEffect(event, alphaCrowdFactor, settingWallTarget, settingWallTexture, settingWallBlend, settingWallGlint, glintMode, glintMove, glintMoveSpeed, glintScale);
/*     */     }
/* 366 */     if (glint) {
/* 367 */       if (((Boolean)settingWallTarget.getValue()).booleanValue())
/* 368 */         GlStateManager.func_179132_a(true); 
/* 369 */       renderGlint(event, ((Boolean)settingWalls.getValue()).booleanValue(), alphaCrowdFactor, throughArmor, throughArmorWall, glintMode, glintMove, glintMoveSpeed, glintScale, glintColor, settingWallTarget);
/*     */     } 
/*     */     
/* 372 */     if (((Boolean)settingWalls.getValue()).booleanValue() && throughArmor && !((Boolean)settingWallTarget.getValue()).booleanValue()) GL11.glDepthRange(0.0D, 1.0D);
/*     */     
/* 374 */     if (((Boolean)settingBlend.getValue()).booleanValue()) GL11.glBlendFunc(770, 771);
/*     */     
/* 376 */     SpartanTessellator.releaseGL();
/* 377 */     GL11.glEnable(3042);
/* 378 */     GL11.glEnable(2896);
/*     */     
/* 380 */     if (((Boolean)settingWallTarget.getValue()).booleanValue() && ((Boolean)settingWalls.getValue()).booleanValue()) {
/* 381 */       GL11.glDepthFunc(515);
/*     */     }
/* 383 */     GL11.glEnable(3553);
/*     */   } private void renderWallEffect(RenderEntityEvent event, float alphaCrowdFactor, Setting<Boolean> settingWallTarget, Setting<Boolean> settingWallTexture, Setting<Boolean> settingWallBlend, Setting<Boolean> settingWallGlint, GlintMode glintMode, boolean glintMove, float glintMoveSpeed, float glintScale) {
/*     */     Color color;
/*     */     Color color2;
/*     */     int alpha;
/* 388 */     if (settingWallTarget == this.playerWallEffect)
/* 389 */     { if (FriendManager.isFriend(event.entityIn)) { color = ((Color)this.friendWallColor.getValue()).getColorColor(); }
/* 390 */       else if (EnemyManager.isEnemy(event.entityIn)) { color = ((Color)this.enemyWallColor.getValue()).getColorColor(); }
/* 391 */       else { color = ((Color)this.playerWallColor.getValue()).getColorColor(); }
/*     */        }
/* 393 */     else if (settingWallTarget == this.mobWallEffect) { color = ((Color)this.mobWallColor.getValue()).getColorColor(); }
/* 394 */     else if (settingWallTarget == this.animalWallEffect) { color = ((Color)this.animalWallColor.getValue()).getColorColor(); }
/* 395 */     else { color = ((Color)this.itemColor.getValue()).getColorColor(); }
/*     */ 
/*     */     
/* 398 */     if (settingWallTarget == this.playerWallEffect)
/* 399 */     { if (FriendManager.isFriend(event.entityIn)) { color2 = (Color)this.friendWallColor.getValue(); }
/* 400 */       else if (EnemyManager.isEnemy(event.entityIn)) { color2 = (Color)this.enemyWallColor.getValue(); }
/* 401 */       else { color2 = (Color)this.playerWallColor.getValue(); }
/*     */        }
/* 403 */     else if (settingWallTarget == this.mobWallEffect) { color2 = (Color)this.mobWallColor.getValue(); }
/* 404 */     else if (settingWallTarget == this.animalWallEffect) { color2 = (Color)this.animalWallColor.getValue(); }
/* 405 */     else { color2 = (Color)this.itemColor.getValue(); }
/*     */ 
/*     */ 
/*     */     
/* 409 */     if (settingWallTarget == this.playerWallEffect)
/* 410 */     { if (FriendManager.isFriend(event.entityIn)) { alpha = ((Color)this.friendWallColor.getValue()).getAlpha(); }
/* 411 */       else if (EnemyManager.isEnemy(event.entityIn)) { alpha = ((Color)this.enemyWallColor.getValue()).getAlpha(); }
/* 412 */       else { alpha = ((Color)this.playerWallColor.getValue()).getAlpha(); }
/*     */        }
/* 414 */     else if (settingWallTarget == this.mobWallEffect) { alpha = ((Color)this.mobWallColor.getValue()).getAlpha(); }
/* 415 */     else if (settingWallTarget == this.animalWallEffect) { alpha = ((Color)this.animalWallColor.getValue()).getAlpha(); }
/* 416 */     else { alpha = ((Color)this.itemColor.getValue()).getAlpha(); }
/*     */     
/* 418 */     GlStateManager.func_179132_a(false);
/*     */     
/* 420 */     if (((Boolean)settingWallTexture.getValue()).booleanValue()) { GL11.glEnable(3553); }
/* 421 */     else { GL11.glDisable(3553); }
/*     */     
/* 423 */     if (((Boolean)settingWallBlend.getValue()).booleanValue()) { GL11.glBlendFunc(770, 32772); }
/* 424 */     else { GL11.glBlendFunc(770, 771); }
/*     */     
/* 426 */     GL11.glEnable(2929);
/* 427 */     GL11.glDepthFunc(516);
/*     */     
/* 429 */     if (((Boolean)settingWallGlint.getValue()).booleanValue()) {
/* 430 */       renderGlint(event, false, alphaCrowdFactor, false, false, glintMode, glintMove, glintMoveSpeed, glintScale, color2, settingWallTarget);
/*     */     } else {
/* 432 */       GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha / 255.0F * alphaCrowdFactor);
/* 433 */       event.modelBase.func_78088_a(event.entityIn, event.limbSwing, event.limbSwingAmount, event.ageInTicks, event.netHeadYaw, event.headPitch, event.scale);
/*     */     } 
/*     */     
/* 436 */     GL11.glDepthFunc(514);
/*     */   }
/*     */   
/*     */   private void renderGlint(RenderEntityEvent event, boolean walls, float alphaCrowdFactor, boolean throughArmor, boolean throughArmorWall, GlintMode glintMode, boolean glintMove, float glintMoveSpeed, float glintScale, Color glintColor, Setting<Boolean> settingWallTarget) {
/* 440 */     if (event.entityIn == mc.field_71439_g && ((Boolean)this.self.getValue()).booleanValue() && !((Boolean)this.selfGlint.getValue()).booleanValue())
/*     */       return; 
/* 442 */     ResourceLocation glintTexture = null;
/*     */     
/* 444 */     switch (glintMode) {
/*     */       case LoadedPack:
/* 446 */         glintTexture = this.loadedTexturePackGlint;
/*     */         break;
/*     */ 
/*     */       
/*     */       case Gradient:
/* 451 */         glintTexture = this.gradientGlint;
/*     */         break;
/*     */ 
/*     */       
/*     */       case Lightning:
/* 456 */         glintTexture = this.lightningGlint;
/*     */         break;
/*     */ 
/*     */       
/*     */       case Swirls:
/* 461 */         glintTexture = this.swirlsGlint;
/*     */         break;
/*     */ 
/*     */       
/*     */       case Lines:
/* 466 */         glintTexture = this.linesGlint;
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 471 */     if (glintTexture != null) mc.func_110434_K().func_110577_a(glintTexture); 
/* 472 */     GL11.glEnable(3553);
/* 473 */     GL11.glDisable(2896);
/* 474 */     GL11.glEnable(3042);
/*     */ 
/*     */     
/* 477 */     float alpha = glintColor.getAlpha() / 255.0F * alphaCrowdFactor;
/* 478 */     GL11.glColor4f(glintColor.getColorColor().getRed() / 255.0F * alpha, glintColor.getColorColor().getGreen() / 255.0F * alpha, glintColor.getColorColor().getBlue() / 255.0F * alpha, 1.0F);
/*     */     
/* 480 */     GL11.glBlendFunc(768, 1);
/*     */     
/* 482 */     if (walls && throughArmor && throughArmorWall && !((Boolean)settingWallTarget.getValue()).booleanValue()) {
/* 483 */       GL11.glDepthRange(0.0D, 0.01D);
/* 484 */       GlStateManager.func_179132_a(false);
/*     */     } 
/*     */     
/* 487 */     doRenderGlint(event, glintMove, glintMoveSpeed, glintScale);
/*     */     
/* 489 */     if (walls && throughArmor && throughArmorWall && !((Boolean)settingWallTarget.getValue()).booleanValue()) {
/* 490 */       GlStateManager.func_179132_a(true);
/* 491 */       GL11.glDepthRange(0.0D, 1.0D);
/*     */       
/* 493 */       doRenderGlint(event, glintMove, glintMoveSpeed, glintScale);
/*     */     } 
/*     */     
/* 496 */     GL11.glBlendFunc(770, 771);
/*     */   }
/*     */   
/*     */   private void doRenderGlint(RenderEntityEvent event, boolean glintMove, float glintMoveSpeed, float glintScale) {
/* 500 */     for (int i = 0; i < 2; i++) {
/* 501 */       GL11.glMatrixMode(5890);
/* 502 */       GL11.glLoadIdentity();
/* 503 */       GL11.glScalef(glintScale, glintScale, glintScale);
/* 504 */       if (glintMove) {
/* 505 */         GL11.glTranslatef(event.entityIn.field_70173_aa * 0.01F * glintMoveSpeed, 0.0F, 0.0F);
/*     */       }
/* 507 */       GL11.glRotatef(30.0F - i * 60.0F, 0.0F, 0.0F, 1.0F);
/* 508 */       GL11.glMatrixMode(5888);
/*     */       
/* 510 */       event.modelBase.func_78088_a(event.entityIn, event.limbSwing, event.limbSwingAmount, event.ageInTicks, event.netHeadYaw, event.headPitch, event.scale);
/*     */     } 
/* 512 */     GL11.glMatrixMode(5890);
/* 513 */     GL11.glLoadIdentity();
/* 514 */     GL11.glMatrixMode(5888);
/*     */   }
/*     */   
/*     */   private void chamsCancelRender(RenderEntityInvokeEvent event, Setting<Boolean> settingCancel, Setting<Boolean> settingFixOutlineEsp) {
/* 518 */     if (((Boolean)settingFixOutlineEsp.getValue()).booleanValue())
/* 519 */       return;  if (((Boolean)settingCancel.getValue()).booleanValue()) event.cancel(); 
/*     */   }
/*     */   
/*     */   enum Page {
/* 523 */     Players,
/* 524 */     Mobs,
/* 525 */     Animals,
/* 526 */     Crystals,
/* 527 */     Items;
/*     */   }
/*     */   
/*     */   public enum GlintMode {
/* 531 */     LoadedPack,
/* 532 */     Gradient,
/* 533 */     Lightning,
/* 534 */     Swirls,
/* 535 */     Lines;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\visuals\Chams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */