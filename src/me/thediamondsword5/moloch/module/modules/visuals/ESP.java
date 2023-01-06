/*     */ package me.thediamondsword5.moloch.module.modules.visuals;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import me.thediamondsword5.moloch.client.EnemyManager;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.event.events.render.RenderEntityInvokeEvent;
/*     */ import me.thediamondsword5.moloch.event.events.render.RenderEntityLayersEvent;
/*     */ import me.thediamondsword5.moloch.event.events.render.RenderWorldPostEventCenter;
/*     */ import me.thediamondsword5.moloch.module.modules.other.Freecam;
/*     */ import me.thediamondsword5.moloch.utils.graphics.shaders.sexy.Outline;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.client.shader.Shader;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.spartanb312.base.client.FriendManager;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.concurrent.ConcurrentTaskManager;
/*     */ import net.spartanb312.base.core.concurrent.repeat.RepeatUnit;
/*     */ import net.spartanb312.base.core.event.Listener;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.render.RenderEvent;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.EntityUtil;
/*     */ import net.spartanb312.base.utils.graphics.RenderHelper;
/*     */ import net.spartanb312.base.utils.graphics.SpartanTessellator;
/*     */ import org.lwjgl.opengl.EXTFramebufferObject;
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
/*     */ @ModuleInfo(name = "ESP", category = Category.VISUALS, description = "Highlights entities")
/*     */ public class ESP
/*     */   extends Module
/*     */ {
/*     */   public static ESP INSTANCE;
/*     */   public boolean renderOutlineFlag = false;
/*  63 */   private final List<RepeatUnit> repeatUnits = new ArrayList<>();
/*     */   public boolean renderProjectileFlag = false;
/*  65 */   public final HashMap<EntityPlayer, Float> skeletonFadeData = new HashMap<>();
/*  66 */   public final HashMap<EntityPlayer, float[][]> skeletonData = (HashMap)new HashMap<>();
/*     */   
/*  68 */   Setting<Page> page = setting("Page", Page.General);
/*     */   
/*  70 */   public Setting<Boolean> ignoreInvisible = setting("IgnoreInvisible", false).des("Doesn't render ESP for invisible entities").whenAtMode(this.page, Page.General);
/*  71 */   public Setting<Boolean> espCull = setting("CullFaces", false).des("Stop rendering sides not visible").whenAtMode(this.page, Page.General);
/*  72 */   public Setting<Boolean> espRangeLimit = setting("LimitRange", false).des("ESP range limit").whenAtMode(this.page, Page.General);
/*  73 */   public Setting<Float> espRange = setting("Range", 30.0F, 10.0F, 64.0F).des("ESP range").whenTrue(this.espRangeLimit).whenAtMode(this.page, Page.General);
/*  74 */   public Setting<Float> espGlowWidth = setting("GlowWidth", 1.0F, 0.1F, 10.0F).des("!! TURN OFF FAST RENDER IN OPTIFINE !!  Glow ESP width").whenAtMode(this.page, Page.General);
/*  75 */   public Setting<Float> espOutlineWidth = setting("OutlineWidth", 1.5F, 1.0F, 5.0F).des("Non shader outline ESP width").whenAtMode(this.page, Page.General);
/*  76 */   public Setting<Boolean> espShaderOutlineFastMode = setting("ShaderOutlineFast", false).des("Shader outline faster render one color").whenAtMode(this.page, Page.General);
/*  77 */   public Setting<Float> espShaderOutlineFastModeWidth = setting("ShaderOutlineFastWidth", 1.5F, 1.0F, 5.0F).des("Shader outline faster render width").whenTrue(this.espShaderOutlineFastMode).whenAtMode(this.page, Page.General);
/*  78 */   public Setting<Color> espShaderOutlineFastModeColor = setting("ShaderOutlineFastColor", new Color((new Color(255, 100, 100, 255)).getRGB(), false, true, 3.4F, 0.5F, 0.9F, 255, 100, 100, 255)).des("Shader outline faster render color").whenTrue(this.espShaderOutlineFastMode).whenAtMode(this.page, Page.General);
/*     */   
/*  80 */   public Setting<Boolean> espTargetSelf = setting("Self", false).des("ESP target self").whenAtMode(this.page, Page.Self);
/*  81 */   public Setting<ModeSelf> espModeSelf = setting("ModeSelf", ModeSelf.Outline).des("Mode of ESP for self (Turn off Fast Render In Optifine For Glow)").whenTrue(this.espTargetSelf).whenAtMode(this.page, Page.Self);
/*  82 */   Setting<Float> espSelfWidth = setting("SelfOutlineWidth", 1.5F, 0.1F, 5.0F).des("ESP self line width").only(v -> (this.espModeSelf.getValue() == ModeSelf.Wireframe)).whenTrue(this.espTargetSelf).whenAtMode(this.page, Page.Self);
/*  83 */   Setting<Boolean> selfCancelVanillaRender = setting("SelfNoVanillaRender", false).des("Cancels normal minecraft self player rendering").whenTrue(this.espTargetSelf).whenAtMode(this.page, Page.Self);
/*  84 */   public Setting<Boolean> espWireframeWallEffectSelf = setting("SelfWireframeWallEffect", false).des("ESP wireframe wall effects for self ESP").whenTrue(this.espTargetSelf).whenAtMode(this.espModeSelf, ModeSelf.Wireframe).whenAtMode(this.page, Page.Self);
/*  85 */   public Setting<Boolean> espWireframeOnlyWallSelf = setting("SelfWireframeOnlyWall", false).des("ESP wireframe only wall render for self ESP").whenTrue(this.espWireframeWallEffectSelf).whenTrue(this.espTargetSelf).whenAtMode(this.page, Page.Self);
/*  86 */   Setting<Boolean> espSkeletonSelf = setting("SelfSkeleton", false).des("Skelly rendering on yourself in 3rd person").whenTrue(this.espTargetSelf).whenAtMode(this.page, Page.Self);
/*  87 */   Setting<Float> espSkeletonSelfWidth = setting("SelfSkeletonWidth", 1.0F, 1.0F, 5.0F).des("Self skelly line width").whenTrue(this.espSkeletonSelf).whenTrue(this.espTargetSelf).whenAtMode(this.page, Page.Self);
/*  88 */   Setting<Boolean> espSkeletonGradientSelfEnds = setting("SelfSkeletonFadeLimbs", false).des("Make the limbs and head lines of self skelly fade out in a gradient").whenTrue(this.espSkeletonSelf).whenTrue(this.espTargetSelf).whenAtMode(this.page, Page.Self);
/*  89 */   Setting<Boolean> espSkeletonRollingSelfColor = setting("SSkeletonRollColor", false).des("Roll colors on player skelly from up to down").whenTrue(this.espSkeletonSelf).whenTrue(this.espTargetSelf).whenAtMode(this.page, Page.Self);
/*  90 */   Setting<Color> espSkeletonRollingSelfColor1 = setting("SSkeletonRollColor1", new Color((new Color(255, 255, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 255)).whenTrue(this.espSkeletonRollingSelfColor).whenTrue(this.espSkeletonSelf).whenTrue(this.espTargetSelf).whenAtMode(this.page, Page.Self);
/*  91 */   Setting<Color> espSkeletonRollingSelfColor2 = setting("SSkeletonRollColor2", new Color((new Color(50, 50, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 50, 50, 255)).whenTrue(this.espSkeletonRollingSelfColor).whenTrue(this.espSkeletonSelf).whenTrue(this.espTargetSelf).whenAtMode(this.page, Page.Self);
/*  92 */   public Setting<Color> espColorSelf = setting("SelfColor", new Color((new Color(255, 255, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 255)).des("Self ESP color").whenTrue(this.espTargetSelf).whenAtMode(this.page, Page.Self);
/*  93 */   Setting<Color> espWireframeWallColorSelf = setting("SWireframeWallColor", new Color((new Color(255, 255, 255, 125)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 125)).des("Self wall only ESP Wireframe color").whenTrue(this.espWireframeWallEffectSelf).only(v -> (this.espModeSelf.getValue() == ModeSelf.Wireframe)).whenTrue(this.espTargetSelf).whenAtMode(this.page, Page.Self);
/*  94 */   Setting<Color> espColorSkeletonSelf = setting("SelfSkeletonColor", new Color((new Color(255, 255, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 255)).des("Players skeleton color").whenTrue(this.espSkeletonSelf).whenTrue(this.espTargetSelf).whenAtMode(this.page, Page.Self);
/*     */   
/*  96 */   public Setting<Boolean> espTargetPlayers = setting("Players", false).des("ESP target players").whenAtMode(this.page, Page.Players);
/*  97 */   public Setting<Mode> espModePlayers = setting("ModePlayers", Mode.Outline).des("Mode of ESP for players (Turn off Fast Render In Optifine For Glow)").whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/*  98 */   Setting<Float> espPlayerWidth = setting("PlayerOutlineWidth", 1.5F, 0.1F, 5.0F).des("ESP player line width").only(v -> (this.espModePlayers.getValue() == Mode.Wireframe || this.espModePlayers.getValue() == Mode.Shader)).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/*  99 */   Setting<Boolean> playersCancelVanillaRender = setting("PNoVanillaRender", false).des("Cancels normal minecraft player rendering").whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 100 */   public Setting<Boolean> espWireframeWallEffectPlayer = setting("PlayerWireframeWallEffect", false).des("ESP wireframe wall effects for player ESP").whenTrue(this.espTargetPlayers).whenAtMode(this.espModePlayers, Mode.Wireframe).whenAtMode(this.page, Page.Players);
/* 101 */   public Setting<Boolean> espWireframeOnlyWallPlayer = setting("PlayerWireframeOnlyWall", false).des("ESP wireframe only wall render for player ESP").whenTrue(this.espWireframeWallEffectPlayer).whenTrue(this.espTargetPlayers).whenAtMode(this.espModePlayers, Mode.Wireframe).whenAtMode(this.page, Page.Players);
/* 102 */   Setting<Boolean> playerNoHurt = setting("PlayerNoHurt", true).des("Don't render hurt effect when player is damaged").whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 103 */   Setting<Boolean> espSkeletonPlayers = setting("PlayersSkeleton", false).des("Skelly rendering in players").whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 104 */   Setting<Float> espSkeletonPlayersWidth = setting("PlayersSkeletonWidth", 1.0F, 1.0F, 5.0F).des("Players skelly line width").whenTrue(this.espSkeletonPlayers).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 105 */   Setting<Boolean> espSkeletonGradientPlayerEnds = setting("PlayersSkeletonFadeLimbs", false).des("Make the limbs and head lines of player skelly fade out in a gradient").whenTrue(this.espSkeletonPlayers).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 106 */   public Setting<Boolean> espSkeletonDeathFade = setting("PlayersSkeletonDeathFade", false).des("Make skellys fade out when player dies").whenTrue(this.espSkeletonPlayers).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 107 */   public Setting<Float> espSkeletonDeathFadeFactor = setting("PSkeletonDeathFadeSpeed", 2.0F, 1.0F, 5.0F).des("Skelly death fade time").whenTrue(this.espSkeletonDeathFade).whenTrue(this.espSkeletonPlayers).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 108 */   Setting<Boolean> espSkeletonRollingPlayerColor = setting("PSkeletonRollColor", false).des("Roll colors on player skelly from up to down").whenTrue(this.espSkeletonPlayers).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 109 */   Setting<Color> espSkeletonRollingPlayerColor1 = setting("PSkeletonRollColor1", new Color((new Color(255, 100, 100, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 100, 255)).whenTrue(this.espSkeletonRollingPlayerColor).whenTrue(this.espSkeletonPlayers).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 110 */   Setting<Color> espSkeletonRollingPlayerColor2 = setting("PSkeletonRollColor2", new Color((new Color(100, 25, 25, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 25, 25, 255)).whenTrue(this.espSkeletonRollingPlayerColor).whenTrue(this.espSkeletonPlayers).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 111 */   Setting<Boolean> espSkeletonRollingFriendColor = setting("FSkeletonRollColor", false).des("Roll colors on friend skelly from up to down").whenTrue(this.espSkeletonPlayers).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 112 */   Setting<Color> espSkeletonRollingFriendColor1 = setting("FSkeletonRollColor1", new Color((new Color(100, 200, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 200, 255, 255)).whenTrue(this.espSkeletonRollingFriendColor).whenTrue(this.espSkeletonPlayers).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 113 */   Setting<Color> espSkeletonRollingFriendColor2 = setting("FSkeletonRollColor2", new Color((new Color(25, 50, 100, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 25, 50, 100, 255)).whenTrue(this.espSkeletonRollingFriendColor).whenTrue(this.espSkeletonPlayers).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 114 */   Setting<Boolean> espSkeletonRollingEnemyColor = setting("ESkeletonRollColor", false).des("Roll colors on enemy skelly from up to down").whenTrue(this.espSkeletonPlayers).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 115 */   Setting<Color> espSkeletonRollingEnemyColor1 = setting("ESkeletonRollColor1", new Color((new Color(255, 0, 0, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 0, 0, 255)).whenTrue(this.espSkeletonRollingEnemyColor).whenTrue(this.espSkeletonPlayers).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 116 */   Setting<Color> espSkeletonRollingEnemyColor2 = setting("ESkeletonRollColor2", new Color((new Color(100, 0, 0, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 0, 0, 255)).whenTrue(this.espSkeletonRollingEnemyColor).whenTrue(this.espSkeletonPlayers).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 117 */   public Setting<Float> espSkeletonRollingColorSpeed = setting("PSkeletonRollSpeed", 0.5F, 0.1F, 2.0F).des("Speed of skelly roll color").only(v -> (((Boolean)this.espSkeletonRollingPlayerColor.getValue()).booleanValue() || ((Boolean)this.espSkeletonRollingFriendColor.getValue()).booleanValue() || ((Boolean)this.espSkeletonRollingEnemyColor.getValue()).booleanValue())).whenTrue(this.espSkeletonPlayers).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 118 */   Setting<BoxMode> espBoxModePlayers = setting("PlayersBoxMode", BoxMode.Both).des("Players ESP box render mode").only(v -> (this.espModePlayers.getValue() == Mode.Box)).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 119 */   Setting<Float> espBoxLineWidthPlayers = setting("PlayersBoxLineWidth", 1.0F, 1.0F, 5.0F).des("Players ESP box line width").only(v -> (this.espModePlayers.getValue() == Mode.Box && (this.espBoxModePlayers.getValue() == BoxMode.Lines || this.espBoxModePlayers.getValue() == BoxMode.Both))).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 120 */   public Setting<Color> espColorPlayers = setting("PlayerColor", new Color((new Color(255, 100, 100, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 100, 255)).des("Player ESP color").only(v -> (this.espModePlayers.getValue() != Mode.Box)).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 121 */   Setting<Color> espColorSkeletonPlayer = setting("PlayerSkeletonColor", new Color((new Color(255, 100, 100, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 100, 255)).des("Players skeleton color").whenFalse(this.espSkeletonRollingPlayerColor).whenTrue(this.espSkeletonPlayers).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 122 */   Setting<Color> espColorPlayersSolid = setting("PlayerColorSolid", new Color((new Color(255, 100, 100, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 100, 120)).des("Players ESP box solid color").only(v -> (this.espModePlayers.getValue() == Mode.Box && (this.espBoxModePlayers.getValue() == BoxMode.Solid || this.espBoxModePlayers.getValue() == BoxMode.Both))).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 123 */   Setting<Color> espColorPlayersLines = setting("PlayerColorLines", new Color((new Color(200, 200, 200, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 200, 200, 200, 255)).des("Players ESP Box lines color").only(v -> (this.espModePlayers.getValue() == Mode.Box && (this.espBoxModePlayers.getValue() == BoxMode.Lines || this.espBoxModePlayers.getValue() == BoxMode.Both))).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 124 */   Setting<Color> espWireframeWallColorPlayers = setting("PWireframeWallColor", new Color((new Color(255, 175, 175, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 175, 175, 255)).des("Players wall only ESP Wireframe color").whenTrue(this.espWireframeWallEffectPlayer).only(v -> (this.espModePlayers.getValue() == Mode.Wireframe)).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 125 */   public Setting<Color> espColorPlayersFriend = setting("PlayerFriendColor", new Color((new Color(100, 200, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 200, 255, 255)).des("Player friend ESP color").only(v -> (this.espModePlayers.getValue() != Mode.Box)).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 126 */   Setting<Color> espColorSkeletonFriend = setting("PFriendSkeletonColor", new Color((new Color(100, 200, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 200, 255, 255)).des("Players friend skeleton color").whenFalse(this.espSkeletonRollingFriendColor).whenTrue(this.espSkeletonPlayers).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 127 */   Setting<Color> espColorPlayersSolidFriend = setting("PFriendColorSolid", new Color((new Color(100, 200, 255, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 200, 255, 120)).des("Players friend ESP box solid color").only(v -> (this.espModePlayers.getValue() == Mode.Box && (this.espBoxModePlayers.getValue() == BoxMode.Solid || this.espBoxModePlayers.getValue() == BoxMode.Both))).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 128 */   Setting<Color> espColorPlayersLinesFriend = setting("PFriendColorLines", new Color((new Color(200, 200, 200, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 200, 200, 200, 255)).des("Players friend ESP Box lines color").only(v -> (this.espModePlayers.getValue() == Mode.Box && (this.espBoxModePlayers.getValue() == BoxMode.Lines || this.espBoxModePlayers.getValue() == BoxMode.Both))).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 129 */   Setting<Color> espWireframeWallColorPlayersFriend = setting("PFWireframeWallColor", new Color((new Color(150, 190, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 150, 190, 255, 255)).des("Players friend wall only ESP wireframe color").whenTrue(this.espWireframeWallEffectPlayer).only(v -> (this.espModePlayers.getValue() == Mode.Wireframe)).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 130 */   public Setting<Color> espColorPlayersEnemy = setting("PEnemyColor", new Color((new Color(255, 0, 0, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 0, 0, 255)).des("Player enemy ESP color").only(v -> (this.espModePlayers.getValue() != Mode.Box)).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 131 */   Setting<Color> espColorSkeletonEnemy = setting("PEnemySkeletonColor", new Color((new Color(255, 0, 0, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 0, 0, 255)).des("Players enemy skeleton color").whenFalse(this.espSkeletonRollingEnemyColor).whenTrue(this.espSkeletonPlayers).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 132 */   Setting<Color> espColorPlayersSolidEnemy = setting("PEnemyColorSolid", new Color((new Color(255, 0, 0, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 0, 0, 120)).des("Players enemy ESP box solid color").only(v -> (this.espModePlayers.getValue() == Mode.Box && (this.espBoxModePlayers.getValue() == BoxMode.Solid || this.espBoxModePlayers.getValue() == BoxMode.Both))).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 133 */   Setting<Color> espColorPlayersLinesEnemy = setting("PEnemyColorLines", new Color((new Color(200, 200, 200, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 200, 200, 200, 255)).des("Players enemy ESP box lines color").only(v -> (this.espModePlayers.getValue() == Mode.Box && (this.espBoxModePlayers.getValue() == BoxMode.Lines || this.espBoxModePlayers.getValue() == BoxMode.Both))).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/* 134 */   Setting<Color> espWireframeWallColorPlayersEnemy = setting("PEWireframeWallColor", new Color((new Color(255, 100, 100, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 100, 255)).des("Players friend wall only ESP wireframe color").whenTrue(this.espWireframeWallEffectPlayer).only(v -> (this.espModePlayers.getValue() == Mode.Wireframe)).whenTrue(this.espTargetPlayers).whenAtMode(this.page, Page.Players);
/*     */   
/* 136 */   public Setting<Boolean> espTargetMobs = setting("Mobs", false).des("ESP target mobs").whenAtMode(this.page, Page.Mobs);
/* 137 */   public Setting<Mode> espModeMobs = setting("ModeMobs", Mode.Outline).des("Mode of ESP for mobs (Turn off Fast Render in Optifine for glow)").whenTrue(this.espTargetMobs).whenAtMode(this.page, Page.Mobs);
/* 138 */   Setting<Float> espMobWidth = setting("MobOutlineWidth", 1.5F, 0.1F, 5.0F).des("ESP mob line width").only(v -> (this.espModeMobs.getValue() == Mode.Wireframe || this.espModeMobs.getValue() != Mode.Shader)).whenTrue(this.espTargetMobs).whenAtMode(this.page, Page.Mobs);
/* 139 */   Setting<Boolean> mobsCancelVanillaRender = setting("MNoVanillaRender", false).des("Cancels normal minecraft mob rendering").whenTrue(this.espTargetMobs).whenAtMode(this.page, Page.Mobs);
/* 140 */   public Setting<Boolean> espWireframeWallEffectMob = setting("MobWireframeWallEffect", false).des("ESP wireframe wall effects for mob ESP").whenTrue(this.espTargetMobs).whenAtMode(this.espModeMobs, Mode.Wireframe).whenAtMode(this.page, Page.Mobs);
/* 141 */   public Setting<Boolean> espWireframeOnlyWallMob = setting("MobWireframeOnlyWall", false).des("ESP wireframe only wall render for mob ESP").whenTrue(this.espWireframeWallEffectMob).whenTrue(this.espTargetMobs).whenAtMode(this.espModeMobs, Mode.Wireframe).whenAtMode(this.page, Page.Mobs);
/* 142 */   Setting<Boolean> mobNoHurt = setting("MobNoHurt", true).des("Don't render hurt effect when mob is damaged").whenTrue(this.espTargetMobs).whenAtMode(this.page, Page.Mobs);
/* 143 */   Setting<BoxMode> espBoxModeMobs = setting("MobsBoxMode", BoxMode.Both).des("Mobs ESP box render mode").only(v -> (this.espModeMobs.getValue() == Mode.Box)).whenTrue(this.espTargetMobs).whenAtMode(this.page, Page.Mobs);
/* 144 */   Setting<Float> espBoxLineWidthMobs = setting("MobsBoxLineWidth", 1.0F, 1.0F, 5.0F).des("Mobs ESP box line width").only(v -> (this.espModeMobs.getValue() == Mode.Box && (this.espBoxModeMobs.getValue() == BoxMode.Lines || this.espBoxModeMobs.getValue() == BoxMode.Both))).whenTrue(this.espTargetMobs).whenAtMode(this.page, Page.Mobs);
/* 145 */   public Setting<Color> espColorMobs = setting("MobColor", new Color((new Color(255, 255, 100, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 100, 255)).des("Mob ESP color").only(v -> (this.espModeMobs.getValue() != Mode.Box)).whenTrue(this.espTargetMobs).whenAtMode(this.page, Page.Mobs);
/* 146 */   Setting<Color> espColorMobsSolid = setting("MobColorSolid", new Color((new Color(255, 255, 100, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 100, 120)).des("Mobs ESP box solid color").only(v -> (this.espModeMobs.getValue() == Mode.Box && (this.espBoxModeMobs.getValue() == BoxMode.Solid || this.espBoxModeMobs.getValue() == BoxMode.Both))).whenTrue(this.espTargetMobs).whenAtMode(this.page, Page.Mobs);
/* 147 */   Setting<Color> espColorMobsLines = setting("MobColorLines", new Color((new Color(200, 200, 200, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 200, 200, 200, 255)).des("Mobs ESP box lines color").only(v -> (this.espModeMobs.getValue() == Mode.Box && (this.espBoxModeMobs.getValue() == BoxMode.Lines || this.espBoxModeMobs.getValue() == BoxMode.Both))).whenTrue(this.espTargetMobs).whenAtMode(this.page, Page.Mobs);
/* 148 */   Setting<Color> espWireframeWallColorMobs = setting("MWireframeWallColor", new Color((new Color(255, 255, 175, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 175, 255)).des("Mobs wall only ESP wireframe color").whenTrue(this.espWireframeWallEffectMob).only(v -> (this.espModeMobs.getValue() == Mode.Wireframe)).whenTrue(this.espTargetMobs).whenAtMode(this.page, Page.Mobs);
/*     */   
/* 150 */   public Setting<Boolean> espTargetAnimals = setting("Animals", false).des("ESP target animals").whenAtMode(this.page, Page.Animals);
/* 151 */   public Setting<Mode> espModeAnimals = setting("ModeAnimals", Mode.Outline).des("Mode of ESP for animals (Turn off Fast Render In Optifine For Glow)").whenTrue(this.espTargetAnimals).whenAtMode(this.page, Page.Animals);
/* 152 */   Setting<Float> espAnimalWidth = setting("AnimalOutlineWidth", 1.5F, 0.1F, 5.0F).des("ESP animal line width").only(v -> (this.espModeAnimals.getValue() == Mode.Wireframe || this.espModeAnimals.getValue() == Mode.Shader)).whenTrue(this.espTargetAnimals).whenAtMode(this.page, Page.Animals);
/* 153 */   Setting<Boolean> animalsCancelVanillaRender = setting("ANoVanillaRender", false).des("Cancels normal minecraft animal rendering").whenTrue(this.espTargetAnimals).whenAtMode(this.page, Page.Animals);
/* 154 */   public Setting<Boolean> espWireframeWallEffectAnimal = setting("AnimalWireframeWallEffect", false).des("ESP wireframe wall effects for animal ESP").whenTrue(this.espTargetAnimals).whenAtMode(this.espModeAnimals, Mode.Wireframe).whenAtMode(this.page, Page.Animals);
/* 155 */   public Setting<Boolean> espWireframeOnlyWallAnimal = setting("AnimalWireframeOnlyWall", false).des("ESP wireframe only wall render for animal ESP").whenTrue(this.espWireframeWallEffectAnimal).whenTrue(this.espTargetAnimals).whenAtMode(this.espModeAnimals, Mode.Wireframe).whenAtMode(this.page, Page.Animals);
/* 156 */   Setting<Boolean> animalNoHurt = setting("AnimalNoHurt", true).des("Don't render hurt effect when animal is damaged").whenTrue(this.espTargetAnimals).whenAtMode(this.page, Page.Animals);
/* 157 */   Setting<BoxMode> espBoxModeAnimals = setting("AnimalsBoxMode", BoxMode.Both).des("Animals ESP box render mode").only(v -> (this.espModeAnimals.getValue() == Mode.Box)).whenTrue(this.espTargetAnimals).whenAtMode(this.page, Page.Animals);
/* 158 */   Setting<Float> espBoxLineWidthAnimals = setting("AnimalsBoxLineWidth", 1.0F, 1.0F, 5.0F).des("Animals ESP box line width").only(v -> (this.espModeAnimals.getValue() == Mode.Box && (this.espBoxModeAnimals.getValue() == BoxMode.Lines || this.espBoxModeAnimals.getValue() == BoxMode.Both))).whenTrue(this.espTargetAnimals).whenAtMode(this.page, Page.Animals);
/* 159 */   public Setting<Color> espColorAnimals = setting("AnimalColor", new Color((new Color(100, 255, 100, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 255, 100, 255)).des("Animal ESP color").only(v -> (this.espModeAnimals.getValue() != Mode.Box)).whenTrue(this.espTargetAnimals).whenAtMode(this.page, Page.Animals);
/* 160 */   Setting<Color> espColorAnimalsSolid = setting("AnimalColorSolid", new Color((new Color(100, 255, 100, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 255, 100, 120)).des("Animals ESP box solid color").only(v -> (this.espModeAnimals.getValue() == Mode.Box && (this.espBoxModeAnimals.getValue() == BoxMode.Solid || this.espBoxModeAnimals.getValue() == BoxMode.Both))).whenTrue(this.espTargetAnimals).whenAtMode(this.page, Page.Animals);
/* 161 */   Setting<Color> espColorAnimalsLines = setting("AnimalColorLines", new Color((new Color(200, 200, 200, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 200, 200, 200, 255)).des("Animals ESP box lines color").only(v -> (this.espModeAnimals.getValue() == Mode.Box && (this.espBoxModeAnimals.getValue() == BoxMode.Lines || this.espBoxModeAnimals.getValue() == BoxMode.Both))).whenTrue(this.espTargetAnimals).whenAtMode(this.page, Page.Animals);
/* 162 */   public Setting<Color> espWireframeWallColorAnimals = setting("AWireframeWallColor", new Color((new Color(175, 255, 175, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 175, 255, 175, 255)).des("Animals wall only ESP wireframe color").whenTrue(this.espWireframeWallEffectAnimal).only(v -> (this.espModeAnimals.getValue() == Mode.Wireframe)).whenTrue(this.espTargetAnimals).whenAtMode(this.page, Page.Animals);
/*     */   
/* 164 */   public Setting<Boolean> espTargetCrystals = setting("Crystals", false).des("ESP target crystals").whenAtMode(this.page, Page.Crystals);
/* 165 */   public Setting<Mode> espModeCrystals = setting("ModeCrystals", Mode.Outline).des("Mode of ESP for crystals (Turn off Fast Render In Optifine For Glow)").whenTrue(this.espTargetCrystals).whenAtMode(this.page, Page.Crystals);
/* 166 */   public Setting<Boolean> espWireframeWallEffectCrystal = setting("CrystalWireframeWallEffect", false).des("ESP wireframe wall effects for crystal ESP").whenTrue(this.espTargetCrystals).whenAtMode(this.espModeCrystals, Mode.Wireframe).whenAtMode(this.page, Page.Crystals);
/* 167 */   public Setting<Boolean> espWireframeOnlyWallCrystal = setting("CrystalWireframeOnlyWall", false).des("ESP wireframe only wall render for crystal ESP").whenTrue(this.espWireframeWallEffectCrystal).whenTrue(this.espTargetCrystals).whenAtMode(this.espModeCrystals, Mode.Wireframe).whenAtMode(this.page, Page.Crystals);
/* 168 */   public Setting<Float> espCrystalWidth = setting("CrystalOutlineWidth", 1.5F, 0.1F, 5.0F).des("ESP crystal line width").only(v -> (this.espModeCrystals.getValue() == Mode.Wireframe || this.espModeCrystals.getValue() == Mode.Shader)).whenTrue(this.espTargetCrystals).whenAtMode(this.page, Page.Crystals);
/* 169 */   public Setting<Boolean> crystalsCancelVanillaRender = setting("CNoVanillaRender", false).des("Cancels normal minecraft crystal rendering").whenTrue(this.espTargetCrystals).whenAtMode(this.page, Page.Crystals);
/* 170 */   Setting<BoxMode> espBoxModeCrystals = setting("CrystalsBoxMode", BoxMode.Both).des("Crystals ESP box render mode").only(v -> (this.espModeCrystals.getValue() == Mode.Box)).whenTrue(this.espTargetCrystals).whenAtMode(this.page, Page.Crystals);
/* 171 */   Setting<Float> espBoxLineWidthCrystals = setting("CrystalsBoxLineWidth", 1.0F, 1.0F, 5.0F).des("Crystals ESP box line width").only(v -> (this.espModeCrystals.getValue() == Mode.Box && (this.espBoxModeCrystals.getValue() == BoxMode.Lines || this.espBoxModeCrystals.getValue() == BoxMode.Both))).whenTrue(this.espTargetCrystals).whenAtMode(this.page, Page.Crystals);
/* 172 */   public Setting<Color> espColorCrystals = setting("CrystalColor", new Color((new Color(255, 100, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 255, 255)).des("Crystal ESP color").only(v -> (this.espModeCrystals.getValue() != Mode.Box)).whenTrue(this.espTargetCrystals).whenAtMode(this.page, Page.Crystals);
/* 173 */   Setting<Color> espColorCrystalsSolid = setting("CrystalColorSolid", new Color((new Color(255, 100, 255, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 255, 120)).des("Crystals ESP box solid color").only(v -> (this.espModeCrystals.getValue() == Mode.Box && (this.espBoxModeCrystals.getValue() == BoxMode.Solid || this.espBoxModeCrystals.getValue() == BoxMode.Both))).whenTrue(this.espTargetCrystals).whenAtMode(this.page, Page.Crystals);
/* 174 */   Setting<Color> espColorCrystalsLines = setting("CrystalColorLines", new Color((new Color(200, 200, 200, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 200, 200, 200, 255)).des("Crystals ESP box lines color").only(v -> (this.espModeCrystals.getValue() == Mode.Box && (this.espBoxModeCrystals.getValue() == BoxMode.Lines || this.espBoxModeCrystals.getValue() == BoxMode.Both))).whenTrue(this.espTargetCrystals).whenAtMode(this.page, Page.Crystals);
/* 175 */   public Setting<Color> espWireframeWallColorCrystals = setting("CWireframeWallColor", new Color((new Color(255, 175, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 175, 255, 255)).des("Crystals wall only ESP wireframe color").whenTrue(this.espWireframeWallEffectCrystal).only(v -> (this.espModeCrystals.getValue() == Mode.Wireframe)).whenTrue(this.espTargetCrystals).whenAtMode(this.page, Page.Crystals);
/*     */ 
/*     */   
/* 178 */   public Setting<Boolean> espTargetItems = setting("Items", false).des("ESP target items").whenAtMode(this.page, Page.Items);
/* 179 */   public Setting<ModeItems> espModeItems = setting("ModeItems", ModeItems.Box).des("Mode of ESP for items (Turn off Fast Render in Optifine for glow)").whenTrue(this.espTargetItems).whenAtMode(this.page, Page.Items);
/* 180 */   public Setting<Float> espItemWidth = setting("ItemOutlineWidth", 1.5F, 0.1F, 5.0F).des("ESP item line width").only(v -> (this.espModeItems.getValue() == ModeItems.Wireframe || this.espModeItems.getValue() == ModeItems.Shader)).whenTrue(this.espTargetItems).whenAtMode(this.page, Page.Items);
/* 181 */   public Setting<Boolean> espRangeLimitItems = setting("LimitRangeItems", false).des("ESP range limit items").whenTrue(this.espTargetItems).whenAtMode(this.page, Page.Items);
/* 182 */   public Setting<Float> espRangeItems = setting("RangeItems", 30.0F, 10.0F, 64.0F).des("ESP range items").whenTrue(this.espRangeLimitItems).whenTrue(this.espTargetItems).whenAtMode(this.page, Page.Items);
/* 183 */   Setting<BoxMode> espBoxModeItems = setting("ItemsBoxMode", BoxMode.Both).des("Items ESP box render mode").only(v -> (this.espModeItems.getValue() == ModeItems.Box)).whenTrue(this.espTargetItems).whenAtMode(this.page, Page.Items);
/* 184 */   Setting<Float> espBoxLineWidthItems = setting("ItemsBoxLineWidth", 1.0F, 1.0F, 5.0F).des("Items ESP box line width").only(v -> (this.espModeItems.getValue() == ModeItems.Box && (this.espBoxModeItems.getValue() == BoxMode.Lines || this.espBoxModeItems.getValue() == BoxMode.Both))).whenTrue(this.espTargetItems).whenAtMode(this.page, Page.Items);
/* 185 */   public Setting<Color> espColorItems = setting("ItemColor", new Color((new Color(100, 100, 255, 255)).getRGB(), true, false, 1.0F, 0.75F, 0.9F, 100, 100, 255, 255)).des("Items ESP color").only(v -> (this.espModeItems.getValue() != ModeItems.Box)).whenTrue(this.espTargetItems).whenAtMode(this.page, Page.Items);
/* 186 */   Setting<Color> espColorItemsSolid = setting("ItemColorSolid", new Color((new Color(100, 100, 255, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 100, 255, 120)).des("Items ESP box solid color").only(v -> (this.espModeItems.getValue() == ModeItems.Box && (this.espBoxModeItems.getValue() == BoxMode.Solid || this.espBoxModeItems.getValue() == BoxMode.Both))).whenTrue(this.espTargetItems).whenAtMode(this.page, Page.Items);
/* 187 */   Setting<Color> espColorItemsLines = setting("ItemColorLines", new Color((new Color(200, 200, 200, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 200, 200, 200, 255)).des("Items ESP box lines color").only(v -> (this.espModeItems.getValue() == ModeItems.Box && (this.espBoxModeItems.getValue() == BoxMode.Lines || this.espBoxModeItems.getValue() == BoxMode.Both))).whenTrue(this.espTargetItems).whenAtMode(this.page, Page.Items);
/*     */ 
/*     */   
/* 190 */   public Setting<Boolean> espTargetProjectiles = setting("Projectiles", false).des("ESP target projectiles").whenAtMode(this.page, Page.Projectiles);
/* 191 */   public Setting<ModeItems> espModeProjectiles = setting("ModeProjectiles", ModeItems.Box).des("Mode of ESP for projectiles (Turn off Fast Render in Optifine for glow)").whenTrue(this.espTargetProjectiles).whenAtMode(this.page, Page.Projectiles);
/* 192 */   public Setting<Float> espProjectileWidth = setting("ProjectileOutlineWidth", 1.5F, 0.1F, 5.0F).des("ESP projectile line width").only(v -> (this.espModeProjectiles.getValue() == ModeItems.Wireframe || this.espModeProjectiles.getValue() == ModeItems.Shader)).whenTrue(this.espTargetProjectiles).whenAtMode(this.page, Page.Projectiles);
/* 193 */   Setting<BoxMode> espBoxModeProjectiles = setting("ProjectilesBoxMode", BoxMode.Both).des("Projectiles ESP box render mode").only(v -> (this.espModeProjectiles.getValue() == ModeItems.Box)).whenTrue(this.espTargetProjectiles).whenAtMode(this.page, Page.Projectiles);
/* 194 */   Setting<Float> espBoxLineWidthProjectiles = setting("ProjectilesBoxLineWidth", 1.0F, 1.0F, 5.0F).des("Projectiles ESP box line width").only(v -> (this.espModeProjectiles.getValue() == ModeItems.Box && (this.espBoxModeProjectiles.getValue() == BoxMode.Lines || this.espBoxModeProjectiles.getValue() == BoxMode.Both))).whenTrue(this.espTargetProjectiles).whenAtMode(this.page, Page.Projectiles);
/* 195 */   public Setting<Color> espColorProjectiles = setting("ProjectileColor", new Color((new Color(100, 100, 255, 255)).getRGB(), true, false, 1.0F, 0.75F, 0.9F, 100, 100, 255, 255)).des("Projectiles ESP color").only(v -> (this.espModeProjectiles.getValue() != ModeItems.Box)).whenTrue(this.espTargetProjectiles).whenAtMode(this.page, Page.Projectiles);
/* 196 */   Setting<Color> espColorProjectilesSolid = setting("ProjectileColorSolid", new Color((new Color(100, 100, 255, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 100, 255, 120)).des("Projectiles ESP box solid color").only(v -> (this.espModeProjectiles.getValue() == ModeItems.Box && (this.espBoxModeProjectiles.getValue() == BoxMode.Solid || this.espBoxModeProjectiles.getValue() == BoxMode.Both))).whenTrue(this.espTargetProjectiles).whenAtMode(this.page, Page.Projectiles);
/* 197 */   Setting<Color> espColorProjectilesLines = setting("ProjectileColorLines", new Color((new Color(200, 200, 200, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 200, 200, 200, 255)).des("Projectiles ESP box lines color").only(v -> (this.espModeProjectiles.getValue() == ModeItems.Box && (this.espBoxModeProjectiles.getValue() == BoxMode.Lines || this.espBoxModeProjectiles.getValue() == BoxMode.Both))).whenTrue(this.espTargetProjectiles).whenAtMode(this.page, Page.Projectiles);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RepeatUnit noHurt;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ESP() {
/* 208 */     this.noHurt = new RepeatUnit(() -> 1, () -> {
/*     */           if (mc.field_71441_e == null)
/*     */             return;  for (Entity entity : EntityUtil.entitiesList()) {
/*     */             EntityUtil.entitiesListFlag = true; if ((entity instanceof EntityPlayer && ((Boolean)this.playerNoHurt.getValue()).booleanValue() && ((Boolean)this.espTargetPlayers.getValue()).booleanValue()) || (EntityUtil.isEntityMob(entity) && ((Boolean)this.mobNoHurt.getValue()).booleanValue() && ((Boolean)this.espTargetMobs.getValue()).booleanValue()) || (EntityUtil.isEntityAnimal(entity) && ((Boolean)this.animalNoHurt.getValue()).booleanValue() && ((Boolean)this.espTargetAnimals.getValue()).booleanValue()))
/*     */               ((EntityLivingBase)entity).field_70737_aN = 0; 
/*     */           }  EntityUtil.entitiesListFlag = false;
/*     */         });
/*     */     INSTANCE = this;
/*     */     this.repeatUnits.add(this.noHurt);
/*     */     this.repeatUnits.forEach(it -> {
/*     */           it.suspend();
/*     */           ConcurrentTaskManager.runRepeat(it);
/* 220 */         }); } public void onEnable() { this.repeatUnits.forEach(RepeatUnit::resume);
/* 221 */     this.moduleEnableFlag = true; }
/*     */ 
/*     */   
/*     */   @Listener
/*     */   public void renderEntity(RenderEntityInvokeEvent event) {
/* 226 */     if (RenderHelper.isInViewFrustrum(event.entityIn) && (!((Boolean)this.ignoreInvisible.getValue()).booleanValue() || !event.entityIn.func_82150_aj())) {
/* 227 */       if ((((Boolean)this.espTargetPlayers.getValue()).booleanValue() && ((Boolean)this.espSkeletonPlayers.getValue()).booleanValue() && event.entityIn instanceof EntityPlayer) || (((Boolean)this.espSkeletonSelf.getValue()).booleanValue() && ((Boolean)this.espTargetSelf.getValue()).booleanValue() && event.entityIn == mc.field_71439_g)) {
/* 228 */         float[][] rotations = SpartanTessellator.getRotationsFromModel((ModelBiped)event.modelBase);
/* 229 */         this.skeletonData.put((EntityPlayer)event.entityIn, rotations);
/*     */       } 
/*     */       
/* 232 */       if (((Boolean)this.espCull.getValue()).booleanValue()) GlStateManager.func_179089_o();
/*     */       
/* 234 */       if ((((Boolean)this.espWireframeWallEffectSelf.getValue()).booleanValue() || ((Boolean)this.espWireframeWallEffectPlayer.getValue()).booleanValue() || ((Boolean)this.espWireframeWallEffectMob.getValue()).booleanValue() || ((Boolean)this.espWireframeWallEffectAnimal.getValue()).booleanValue()) && (!((Boolean)this.espRangeLimit.getValue()).booleanValue() || mc.field_71439_g.func_70032_d(event.entityIn) <= ((Float)this.espRange.getValue()).floatValue())) {
/* 235 */         if (event.entityIn instanceof EntityPlayer && ((Boolean)this.espTargetPlayers.getValue()).booleanValue() && this.espModePlayers.getValue() == Mode.Wireframe && event.entityIn != mc.field_71439_g && ((Boolean)this.espWireframeWallEffectPlayer.getValue()).booleanValue())
/* 236 */           renderWireframeXQZ(event, this.espTargetPlayers, ((Float)this.espPlayerWidth.getValue()).floatValue()); 
/* 237 */         if (event.entityIn == mc.field_71439_g && ((Boolean)this.espTargetSelf.getValue()).booleanValue() && this.espModeSelf.getValue() == ModeSelf.Wireframe && ((Boolean)this.espWireframeWallEffectSelf.getValue()).booleanValue())
/* 238 */           renderWireframeXQZ(event, this.espTargetSelf, ((Float)this.espSelfWidth.getValue()).floatValue()); 
/* 239 */         if ((EntityUtil.isEntityMob(event.entityIn) || event.entityIn instanceof net.minecraft.entity.boss.EntityDragon) && ((Boolean)this.espTargetMobs.getValue()).booleanValue() && this.espModeMobs.getValue() == Mode.Wireframe && ((Boolean)this.espWireframeWallEffectMob.getValue()).booleanValue())
/* 240 */           renderWireframeXQZ(event, this.espTargetMobs, ((Float)this.espMobWidth.getValue()).floatValue()); 
/* 241 */         if (EntityUtil.isEntityAnimal(event.entityIn) && ((Boolean)this.espTargetAnimals.getValue()).booleanValue() && this.espModeAnimals.getValue() == Mode.Wireframe && ((Boolean)this.espWireframeWallEffectAnimal.getValue()).booleanValue()) {
/* 242 */           renderWireframeXQZ(event, this.espTargetAnimals, ((Float)this.espAnimalWidth.getValue()).floatValue());
/*     */         }
/*     */       } 
/* 245 */       if ((event.entityIn == mc.field_71439_g && ((Boolean)this.espTargetSelf.getValue()).booleanValue() && ((Boolean)this.selfCancelVanillaRender.getValue()).booleanValue()) || (event.entityIn instanceof EntityPlayer && ((Boolean)this.espTargetPlayers.getValue()).booleanValue() && ((Boolean)this.playersCancelVanillaRender.getValue()).booleanValue()) || ((EntityUtil.isEntityMob(event.entityIn) || event.entityIn instanceof net.minecraft.entity.boss.EntityDragon) && ((Boolean)this.espTargetMobs.getValue()).booleanValue() && ((Boolean)this.mobsCancelVanillaRender.getValue()).booleanValue()) || (EntityUtil.isEntityAnimal(event.entityIn) && ((Boolean)this.espTargetAnimals.getValue()).booleanValue() && ((Boolean)this.animalsCancelVanillaRender.getValue()).booleanValue()))
/* 246 */         event.cancel(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void renderPost(RenderEntityLayersEvent event) {
/* 252 */     if (RenderHelper.isInViewFrustrum((Entity)event.entityIn) && (!((Boolean)this.ignoreInvisible.getValue()).booleanValue() || !event.entityIn.func_82150_aj())) {
/* 253 */       if (((Boolean)this.espCull.getValue()).booleanValue()) GlStateManager.func_179089_o(); 
/* 254 */       if (mc.field_71439_g.func_70032_d((Entity)event.entityIn) > ((Float)this.espRange.getValue()).floatValue() && ((Boolean)this.espRangeLimit.getValue()).booleanValue() && ((((Boolean)this.espTargetPlayers.getValue()).booleanValue() && this.espModePlayers.getValue() == Mode.Glow) || (((Boolean)this.espTargetMobs.getValue()).booleanValue() && this.espModeMobs.getValue() == Mode.Glow) || (((Boolean)this.espTargetAnimals.getValue()).booleanValue() && this.espModeAnimals.getValue() == Mode.Glow) || (((Boolean)this.espTargetCrystals.getValue()).booleanValue() && this.espModeCrystals.getValue() == Mode.Glow) || (((Boolean)this.espTargetItems.getValue()).booleanValue() && this.espModeItems.getValue() == ModeItems.Glow))) {
/* 255 */         unGlow();
/*     */       }
/*     */       
/* 258 */       if ((!((Boolean)this.espWireframeWallEffectSelf.getValue()).booleanValue() || !((Boolean)this.espWireframeOnlyWallSelf.getValue()).booleanValue() || !((Boolean)this.espWireframeWallEffectPlayer.getValue()).booleanValue() || !((Boolean)this.espWireframeOnlyWallPlayer.getValue()).booleanValue() || !((Boolean)this.espWireframeWallEffectMob.getValue()).booleanValue() || !((Boolean)this.espWireframeOnlyWallMob.getValue()).booleanValue() || !((Boolean)this.espWireframeWallEffectAnimal.getValue()).booleanValue() || !((Boolean)this.espWireframeOnlyWallAnimal.getValue()).booleanValue()) && (!((Boolean)this.espRangeLimit.getValue()).booleanValue() || mc.field_71439_g.func_70032_d((Entity)event.entityIn) <= ((Float)this.espRange.getValue()).floatValue())) {
/* 259 */         if (event.entityIn instanceof EntityPlayer && ((Boolean)this.espTargetPlayers.getValue()).booleanValue() && this.espModePlayers.getValue() == Mode.Wireframe && event.entityIn != mc.field_71439_g && (!((Boolean)this.espWireframeWallEffectPlayer.getValue()).booleanValue() || !((Boolean)this.espWireframeOnlyWallPlayer.getValue()).booleanValue()))
/* 260 */           renderWireframe(event, this.espTargetPlayers, this.espWireframeWallEffectPlayer, ((Float)this.espPlayerWidth.getValue()).floatValue()); 
/* 261 */         if (event.entityIn == mc.field_71439_g && ((Boolean)this.espTargetSelf.getValue()).booleanValue() && this.espModeSelf.getValue() == ModeSelf.Wireframe && (!((Boolean)this.espWireframeWallEffectSelf.getValue()).booleanValue() || !((Boolean)this.espWireframeOnlyWallSelf.getValue()).booleanValue()))
/* 262 */           renderWireframe(event, this.espTargetSelf, this.espWireframeWallEffectSelf, ((Float)this.espSelfWidth.getValue()).floatValue()); 
/* 263 */         if ((EntityUtil.isEntityMob((Entity)event.entityIn) || event.entityIn instanceof net.minecraft.entity.boss.EntityDragon) && ((Boolean)this.espTargetMobs.getValue()).booleanValue() && this.espModeMobs.getValue() == Mode.Wireframe && (!((Boolean)this.espWireframeWallEffectMob.getValue()).booleanValue() || !((Boolean)this.espWireframeOnlyWallMob.getValue()).booleanValue()))
/* 264 */           renderWireframe(event, this.espTargetMobs, this.espWireframeWallEffectMob, ((Float)this.espMobWidth.getValue()).floatValue()); 
/* 265 */         if (EntityUtil.isEntityAnimal((Entity)event.entityIn) && ((Boolean)this.espTargetAnimals.getValue()).booleanValue() && this.espModeAnimals.getValue() == Mode.Wireframe && (!((Boolean)this.espWireframeWallEffectAnimal.getValue()).booleanValue() || !((Boolean)this.espWireframeOnlyWallAnimal.getValue()).booleanValue())) {
/* 266 */           renderWireframe(event, this.espTargetAnimals, this.espWireframeWallEffectAnimal, ((Float)this.espAnimalWidth.getValue()).floatValue());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onRenderWorld(RenderEvent event) {
/* 273 */     if ((((Boolean)this.espTargetPlayers.getValue()).booleanValue() && ((Boolean)this.espSkeletonPlayers.getValue()).booleanValue()) || (((Boolean)this.espTargetSelf.getValue()).booleanValue() && ((Boolean)this.espSkeletonSelf.getValue()).booleanValue())) {
/*     */       
/* 275 */       List<EntityPlayer> list = new ArrayList<>(this.skeletonData.keySet());
/* 276 */       list.removeAll(mc.field_71441_e.field_73010_i);
/* 277 */       list.forEach(this.skeletonData.keySet()::remove);
/*     */       
/* 279 */       if (((Boolean)this.espSkeletonDeathFade.getValue()).booleanValue()) {
/* 280 */         List<EntityPlayer> list2 = new ArrayList<>(this.skeletonFadeData.keySet());
/* 281 */         list2.removeAll(mc.field_71441_e.field_73010_i);
/* 282 */         list2.forEach(this.skeletonFadeData.keySet()::remove);
/*     */       } 
/*     */       
/* 285 */       for (Entity entity : EntityUtil.entitiesList()) {
/* 286 */         EntityUtil.entitiesListFlag = true;
/* 287 */         if (!(entity instanceof EntityPlayer) || ((EntityPlayer)entity).func_184613_cA() || ((EntityPlayer)entity).func_175149_v() || (
/* 288 */           mc.field_71474_y.field_74320_O == 0 && ((Boolean)this.espSkeletonSelf.getValue()).booleanValue() && ((Boolean)this.espTargetSelf.getValue()).booleanValue() && entity == mc.field_71439_g && Freecam.INSTANCE.camera == null))
/*     */           continue; 
/* 290 */         if (!RenderHelper.isInViewFrustrum(entity)) {
/*     */           continue;
/*     */         }
/* 293 */         if (!EntityUtil.entitiesList().contains(entity) || ((EntityPlayer)entity).func_70608_bn() || (entity.field_70128_L && (!((Boolean)this.espSkeletonDeathFade.getValue()).booleanValue() || (this.skeletonFadeData.get(entity) != null && ((Float)this.skeletonFadeData.get(entity)).floatValue() >= 300.0F)))) {
/* 294 */           this.skeletonData.remove(entity);
/*     */         }
/* 296 */         if (!this.skeletonData.containsKey(entity) || (
/* 297 */           ((EntityPlayer)entity).field_70725_aQ > 0 && !((Boolean)this.espSkeletonDeathFade.getValue()).booleanValue()))
/*     */           continue; 
/* 299 */         int skellyPlayerColor = ((Color)this.espColorSkeletonPlayer.getValue()).getColor();
/* 300 */         boolean skellyRollColor = ((Boolean)this.espSkeletonRollingPlayerColor.getValue()).booleanValue();
/* 301 */         Color skellyRollColor1 = ((Color)this.espSkeletonRollingPlayerColor1.getValue()).getColorColor();
/* 302 */         Color skellyRollColor2 = ((Color)this.espSkeletonRollingPlayerColor2.getValue()).getColorColor();
/*     */         
/* 304 */         if (FriendManager.isFriend(entity)) {
/* 305 */           skellyPlayerColor = ((Color)this.espColorSkeletonFriend.getValue()).getColor();
/* 306 */           skellyRollColor = ((Boolean)this.espSkeletonRollingFriendColor.getValue()).booleanValue();
/* 307 */           skellyRollColor1 = ((Color)this.espSkeletonRollingFriendColor1.getValue()).getColorColor();
/* 308 */           skellyRollColor2 = ((Color)this.espSkeletonRollingFriendColor2.getValue()).getColorColor();
/*     */         } 
/*     */         
/* 311 */         if (EnemyManager.isEnemy(entity)) {
/* 312 */           skellyPlayerColor = ((Color)this.espColorSkeletonEnemy.getValue()).getColor();
/* 313 */           skellyRollColor = ((Boolean)this.espSkeletonRollingEnemyColor.getValue()).booleanValue();
/* 314 */           skellyRollColor1 = ((Color)this.espSkeletonRollingEnemyColor1.getValue()).getColorColor();
/* 315 */           skellyRollColor2 = ((Color)this.espSkeletonRollingEnemyColor2.getValue()).getColorColor();
/*     */         } 
/*     */         
/* 318 */         if ((entity == mc.field_71439_g && ((Boolean)this.espTargetSelf.getValue()).booleanValue() && ((Boolean)this.espSkeletonSelf.getValue()).booleanValue()) || (((Boolean)this.espTargetPlayers.getValue()).booleanValue() && ((Boolean)this.espSkeletonPlayers.getValue()).booleanValue() && entity != mc.field_71439_g)) {
/* 319 */           if (entity == mc.field_71439_g) {
/* 320 */             SpartanTessellator.drawSkeleton((EntityPlayer)entity, this.skeletonData.get(entity), ((Float)this.espSkeletonSelfWidth.getValue()).floatValue(), ((Boolean)this.espSkeletonGradientSelfEnds.getValue()).booleanValue(), ((Boolean)this.espSkeletonRollingSelfColor.getValue()).booleanValue(), ((Color)this.espSkeletonRollingSelfColor1.getValue()).getColorColor(), ((Color)this.espSkeletonRollingSelfColor2.getValue()).getColorColor(), ((Color)this.espColorSkeletonSelf.getValue()).getColor());
/*     */             continue;
/*     */           } 
/* 323 */           SpartanTessellator.drawSkeleton((EntityPlayer)entity, this.skeletonData.get(entity), ((Float)this.espSkeletonPlayersWidth.getValue()).floatValue(), ((Boolean)this.espSkeletonGradientPlayerEnds.getValue()).booleanValue(), skellyRollColor, skellyRollColor1, skellyRollColor2, skellyPlayerColor);
/*     */         } 
/*     */       } 
/*     */       
/* 327 */       EntityUtil.entitiesListFlag = false;
/*     */     } 
/*     */     
/* 330 */     if (((Boolean)this.espTargetPlayers.getValue()).booleanValue() && this.espModePlayers.getValue() == Mode.Box) renderBox(this.espTargetPlayers, this.espBoxModePlayers, this.espBoxLineWidthPlayers, this.espColorPlayersLines, this.espColorPlayersSolid); 
/* 331 */     if (((Boolean)this.espTargetMobs.getValue()).booleanValue() && this.espModeMobs.getValue() == Mode.Box) renderBox(this.espTargetMobs, this.espBoxModeMobs, this.espBoxLineWidthMobs, this.espColorMobsLines, this.espColorMobsSolid); 
/* 332 */     if (((Boolean)this.espTargetAnimals.getValue()).booleanValue() && this.espModeAnimals.getValue() == Mode.Box) renderBox(this.espTargetAnimals, this.espBoxModeAnimals, this.espBoxLineWidthAnimals, this.espColorAnimalsLines, this.espColorAnimalsSolid); 
/* 333 */     if (((Boolean)this.espTargetCrystals.getValue()).booleanValue() && this.espModeCrystals.getValue() == Mode.Box) renderBox(this.espTargetCrystals, this.espBoxModeCrystals, this.espBoxLineWidthCrystals, this.espColorCrystalsLines, this.espColorCrystalsSolid); 
/* 334 */     if (((Boolean)this.espTargetProjectiles.getValue()).booleanValue() && this.espModeProjectiles.getValue() == ModeItems.Box) renderBox(this.espTargetProjectiles, this.espBoxModeProjectiles, this.espBoxLineWidthProjectiles, this.espColorProjectilesLines, this.espColorProjectilesSolid); 
/* 335 */     if (((Boolean)this.espTargetItems.getValue()).booleanValue() && this.espModeItems.getValue() == ModeItems.Box && 
/* 336 */       this.espModeItems.getValue() == ModeItems.Box) {
/* 337 */       for (Entity entity : EntityUtil.entitiesList()) {
/* 338 */         EntityUtil.entitiesListFlag = true;
/* 339 */         if (RenderHelper.isInViewFrustrum(entity) && 
/* 340 */           entity instanceof net.minecraft.entity.item.EntityItem && ((Boolean)this.espTargetItems.getValue()).booleanValue() && (!((Boolean)this.espRangeLimitItems.getValue()).booleanValue() || mc.field_71439_g.func_70032_d(entity) <= ((Float)this.espRangeItems.getValue()).floatValue())) {
/* 341 */           if (this.espBoxModeItems.getValue() == BoxMode.Solid || this.espBoxModeItems.getValue() == BoxMode.Both)
/* 342 */             SpartanTessellator.drawBBFullBox(entity, ((Color)this.espColorItemsSolid.getValue()).getColor()); 
/* 343 */           if (this.espBoxModeItems.getValue() == BoxMode.Lines || this.espBoxModeItems.getValue() == BoxMode.Both) {
/* 344 */             SpartanTessellator.drawBBLineBox(entity, ((Float)this.espBoxLineWidthItems.getValue()).floatValue(), ((Color)this.espColorItemsLines.getValue()).getColor());
/*     */           }
/*     */         } 
/*     */       } 
/* 348 */       EntityUtil.entitiesListFlag = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Listener
/*     */   public void onRenderWorld1(RenderEvent.Extra1 event) {
/* 355 */     if (((Boolean)this.espTargetProjectiles.getValue()).booleanValue() && this.espModeProjectiles.getValue() == ModeItems.Wireframe) {
/* 356 */       EntityUtil.runEntityCheck();
/*     */       
/* 358 */       if (EntityUtil.isEntityProjectileLoaded) {
/*     */         
/* 360 */         this.renderProjectileFlag = true;
/*     */         
/* 362 */         GL11.glEnable(2848);
/* 363 */         GL11.glLineWidth(((Float)this.espProjectileWidth.getValue()).floatValue());
/* 364 */         GL11.glPolygonMode(1032, 6913);
/*     */         
/* 366 */         for (Entity entity : EntityUtil.entitiesList()) {
/* 367 */           EntityUtil.entitiesListFlag = true;
/* 368 */           if (!RenderHelper.isInViewFrustrum(entity) || (!(entity instanceof net.minecraft.entity.IProjectile) && !(entity instanceof net.minecraft.entity.projectile.EntityShulkerBullet) && !(entity instanceof net.minecraft.entity.projectile.EntityFireball) && !(entity instanceof net.minecraft.entity.item.EntityEnderEye)))
/* 369 */             continue;  GL11.glColor4f(((Color)this.espColorProjectiles.getValue()).getColorColor().getRed() / 255.0F, ((Color)this.espColorProjectiles.getValue()).getColorColor().getGreen() / 255.0F, ((Color)this.espColorProjectiles.getValue()).getColorColor().getBlue() / 255.0F, ((Color)this.espColorProjectiles.getValue()).getAlpha() / 255.0F);
/* 370 */           renderEntityInWorld(entity);
/*     */         } 
/* 372 */         EntityUtil.entitiesListFlag = false;
/*     */         
/* 374 */         GL11.glPolygonMode(1032, 6914);
/* 375 */         GL11.glDisable(2848);
/* 376 */         GL11.glDisable(2896);
/* 377 */         this.renderProjectileFlag = false;
/*     */       } 
/*     */     } 
/*     */     
/* 381 */     if ((((Boolean)this.espTargetSelf.getValue()).booleanValue() && this.espModeSelf.getValue() == ModeSelf.Outline) || (((Boolean)this.espTargetPlayers.getValue()).booleanValue() && this.espModePlayers.getValue() == Mode.Outline) || (((Boolean)this.espTargetMobs.getValue()).booleanValue() && this.espModeMobs.getValue() == Mode.Outline) || (((Boolean)this.espTargetAnimals.getValue()).booleanValue() && this.espModeAnimals.getValue() == Mode.Outline) || (((Boolean)this.espTargetCrystals.getValue()).booleanValue() && this.espModeCrystals.getValue() == Mode.Outline)) {
/* 382 */       EntityUtil.runEntityCheck();
/*     */       
/* 384 */       if (EntityUtil.isEntityPlayerLoaded || EntityUtil.isEntityMobLoaded || EntityUtil.isEntityAnimalLoaded || EntityUtil.isEntityCrystalLoaded) {
/* 385 */         outline1();
/* 386 */         doRenderVanillaEntitiesAgain(false);
/* 387 */         outline2();
/* 388 */         doRenderVanillaEntitiesAgain(false);
/* 389 */         outline3();
/* 390 */         doRenderVanillaEntitiesAgain(true);
/* 391 */         outline4();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void onRenderWorldPost(RenderWorldPostEventCenter event) {
/* 398 */     if ((((Boolean)this.espTargetPlayers.getValue()).booleanValue() && this.espModePlayers.getValue() == Mode.Shader) || (((Boolean)this.espTargetMobs.getValue()).booleanValue() && this.espModeMobs.getValue() == Mode.Shader) || (((Boolean)this.espTargetAnimals.getValue()).booleanValue() && this.espModeAnimals.getValue() == Mode.Shader) || (((Boolean)this.espTargetCrystals.getValue()).booleanValue() && this.espModeCrystals.getValue() == Mode.Shader) || (((Boolean)this.espTargetItems.getValue()).booleanValue() && this.espModeItems.getValue() == ModeItems.Shader) || (((Boolean)this.espTargetProjectiles.getValue()).booleanValue() && this.espModeProjectiles.getValue() == ModeItems.Shader)) {
/* 399 */       this.renderOutlineFlag = true;
/* 400 */       renderShaderOutline();
/* 401 */       this.renderOutlineFlag = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRenderTick() {
/* 408 */     if ((!((Boolean)this.playerNoHurt.getValue()).booleanValue() || !((Boolean)this.espTargetPlayers.getValue()).booleanValue()) && (!((Boolean)this.mobNoHurt.getValue()).booleanValue() || !((Boolean)this.espTargetMobs.getValue()).booleanValue()) && (!((Boolean)this.animalNoHurt.getValue()).booleanValue() || !((Boolean)this.espTargetAnimals.getValue()).booleanValue())) {
/* 409 */       this.repeatUnits.forEach(RepeatUnit::suspend);
/*     */     } else {
/* 411 */       this.repeatUnits.forEach(RepeatUnit::resume);
/*     */     } 
/* 413 */     if ((((Boolean)this.espTargetSelf.getValue()).booleanValue() && this.espModeSelf.getValue() == ModeSelf.Glow) || (((Boolean)this.espTargetPlayers.getValue()).booleanValue() && this.espModePlayers.getValue() == Mode.Glow) || (((Boolean)this.espTargetMobs.getValue()).booleanValue() && this.espModeMobs.getValue() == Mode.Glow) || (((Boolean)this.espTargetAnimals.getValue()).booleanValue() && this.espModeAnimals.getValue() == Mode.Glow) || (((Boolean)this.espTargetCrystals.getValue()).booleanValue() && this.espModeCrystals.getValue() == Mode.Glow) || (((Boolean)this.espTargetItems.getValue()).booleanValue() && this.espModeItems.getValue() == ModeItems.Glow) || (((Boolean)this.espTargetProjectiles.getValue()).booleanValue() && this.espModeProjectiles.getValue() == ModeItems.Glow)) {
/* 414 */       for (Shader shader : mc.field_71438_f.field_174991_A.field_148031_d) {
/* 415 */         if (shader.func_148043_c().func_147991_a("Radius") != null)
/* 416 */           shader.func_148043_c().func_147991_a("Radius").func_148090_a(((Float)this.espGlowWidth.getValue()).floatValue()); 
/*     */       } 
/* 418 */       for (Entity entity : EntityUtil.entitiesList()) {
/* 419 */         EntityUtil.entitiesListFlag = true;
/* 420 */         if ((!((Boolean)this.ignoreInvisible.getValue()).booleanValue() || !entity.func_82150_aj()) && ((entity == mc.field_71439_g && ((Boolean)this.espTargetSelf.getValue()).booleanValue() && this.espModeSelf.getValue() == ModeSelf.Glow) || (entity instanceof EntityPlayer && ((Boolean)this.espTargetPlayers.getValue()).booleanValue() && this.espModePlayers.getValue() == Mode.Glow && entity != mc.field_71439_g) || ((EntityUtil.isEntityMob(entity) || entity instanceof net.minecraft.entity.boss.EntityDragon) && ((Boolean)this.espTargetMobs.getValue()).booleanValue() && this.espModeMobs.getValue() == Mode.Glow) || (EntityUtil.isEntityAnimal(entity) && ((Boolean)this.espTargetAnimals.getValue()).booleanValue() && this.espModeAnimals.getValue() == Mode.Glow) || (entity instanceof net.minecraft.entity.item.EntityEnderCrystal && ((Boolean)this.espTargetCrystals.getValue()).booleanValue() && this.espModeCrystals.getValue() == Mode.Glow) || (entity instanceof net.minecraft.entity.item.EntityItem && ((Boolean)this.espTargetItems.getValue()).booleanValue() && this.espModeItems.getValue() == ModeItems.Glow) || ((entity instanceof net.minecraft.entity.IProjectile || entity instanceof net.minecraft.entity.projectile.EntityShulkerBullet || entity instanceof net.minecraft.entity.projectile.EntityFireball || entity instanceof net.minecraft.entity.item.EntityEnderEye) && ((Boolean)this.espTargetProjectiles.getValue()).booleanValue() && this.espModeProjectiles.getValue() == ModeItems.Glow))) {
/* 421 */           entity.func_184195_f(true); continue;
/* 422 */         }  unGlow(entity);
/*     */       } 
/* 424 */       EntityUtil.entitiesListFlag = false;
/*     */     } else {
/*     */       
/* 427 */       unGlow();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 433 */     unGlow();
/* 434 */     this.repeatUnits.forEach(RepeatUnit::suspend);
/* 435 */     this.moduleDisableFlag = true;
/*     */   }
/*     */   
/*     */   private void renderEntityInWorld(Entity entity) {
/* 439 */     Vec3d renderPos = EntityUtil.interpolateEntityRender(entity, mc.func_184121_ak());
/* 440 */     Render<Entity> entityRenderObj = mc.func_175598_ae().func_78713_a(entity);
/* 441 */     if (entityRenderObj != null) entityRenderObj.func_76986_a(entity, renderPos.field_72450_a, renderPos.field_72448_b, renderPos.field_72449_c, entity.field_70177_z, mc.func_184121_ak()); 
/*     */   }
/*     */   
/*     */   private void renderShaderOutline() {
/* 445 */     if (((Boolean)this.espShaderOutlineFastMode.getValue()).booleanValue()) {
/* 446 */       boolean isEntityLoaded = false;
/* 447 */       for (Entity entity : EntityUtil.entitiesList()) {
/* 448 */         EntityUtil.entitiesListFlag = true;
/* 449 */         if (entity != null) {
/* 450 */           isEntityLoaded = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 454 */       EntityUtil.entitiesListFlag = false;
/* 455 */       if (isEntityLoaded) {
/* 456 */         Color color = new Color(((Color)this.espShaderOutlineFastModeColor.getValue()).getColorColor().getRed(), ((Color)this.espShaderOutlineFastModeColor.getValue()).getColorColor().getGreen(), ((Color)this.espShaderOutlineFastModeColor.getValue()).getColorColor().getBlue(), ((Color)this.espShaderOutlineFastModeColor.getValue()).getAlpha());
/* 457 */         int alpha = ((Color)this.espShaderOutlineFastModeColor.getValue()).getAlpha();
/* 458 */         Outline.SHADER_OUTLINE.startDraw(mc.func_184121_ak(), false);
/*     */         
/* 460 */         for (Entity entity : EntityUtil.entitiesList()) {
/* 461 */           EntityUtil.entitiesListFlag = true;
/* 462 */           if (((Boolean)this.ignoreInvisible.getValue()).booleanValue() && entity.func_82150_aj())
/* 463 */             continue;  if (!((Boolean)this.espRangeLimit.getValue()).booleanValue() || mc.field_71439_g.func_70032_d(entity) <= ((Float)this.espRange.getValue()).floatValue()) {
/* 464 */             if (entity instanceof EntityPlayer && ((Boolean)this.espTargetPlayers.getValue()).booleanValue() && this.espModePlayers.getValue() == Mode.Shader && entity != mc.field_71439_g)
/* 465 */               renderEntityInWorld(entity); 
/* 466 */             if ((EntityUtil.isEntityMob(entity) || entity instanceof net.minecraft.entity.boss.EntityDragon) && ((Boolean)this.espTargetMobs.getValue()).booleanValue() && this.espModeMobs.getValue() == Mode.Shader)
/* 467 */               renderEntityInWorld(entity); 
/* 468 */             if (EntityUtil.isEntityAnimal(entity) && ((Boolean)this.espTargetAnimals.getValue()).booleanValue() && this.espModeAnimals.getValue() == Mode.Shader)
/* 469 */               renderEntityInWorld(entity); 
/* 470 */             if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal && ((Boolean)this.espTargetCrystals.getValue()).booleanValue() && this.espModeCrystals.getValue() == Mode.Shader)
/* 471 */               renderEntityInWorld(entity); 
/* 472 */             if ((entity instanceof net.minecraft.entity.IProjectile || entity instanceof net.minecraft.entity.projectile.EntityShulkerBullet || entity instanceof net.minecraft.entity.projectile.EntityFireball || entity instanceof net.minecraft.entity.item.EntityEnderEye) && ((Boolean)this.espTargetProjectiles.getValue()).booleanValue() && this.espModeProjectiles.getValue() == ModeItems.Shader)
/* 473 */               renderEntityInWorld(entity); 
/*     */           } 
/* 475 */           if ((!((Boolean)this.espRangeLimitItems.getValue()).booleanValue() || mc.field_71439_g.func_70032_d(entity) <= ((Float)this.espRangeItems.getValue()).floatValue()) && entity instanceof net.minecraft.entity.item.EntityItem && ((Boolean)this.espTargetItems.getValue()).booleanValue() && this.espModeItems.getValue() == ModeItems.Shader)
/* 476 */             renderEntityInWorld(entity); 
/*     */         } 
/* 478 */         EntityUtil.entitiesListFlag = false;
/*     */         
/* 480 */         Outline.SHADER_OUTLINE.stopDraw(color, alpha, ((Float)this.espShaderOutlineFastModeWidth.getValue()).floatValue(), 1.0F);
/*     */       } 
/*     */     } else {
/*     */       
/* 484 */       boolean isEntityPlayerLoaded = false;
/* 485 */       boolean isEntityMobLoaded = false;
/* 486 */       boolean isEntityAnimalLoaded = false;
/* 487 */       boolean isEntityCrystalLoaded = false;
/* 488 */       boolean isEntityItemLoaded = false;
/* 489 */       boolean isEntityProjectileLoaded = false;
/* 490 */       for (Entity entity : EntityUtil.entitiesList()) {
/* 491 */         EntityUtil.entitiesListFlag = true;
/* 492 */         if (!RenderHelper.isInViewFrustrum(entity))
/* 493 */           continue;  if (entity instanceof EntityPlayer) isEntityPlayerLoaded = true; 
/* 494 */         if (EntityUtil.isEntityMob(entity) || entity instanceof net.minecraft.entity.boss.EntityDragon) isEntityMobLoaded = true; 
/* 495 */         if (EntityUtil.isEntityAnimal(entity)) isEntityAnimalLoaded = true; 
/* 496 */         if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal) isEntityCrystalLoaded = true; 
/* 497 */         if (entity instanceof net.minecraft.entity.item.EntityItem) isEntityItemLoaded = true; 
/* 498 */         if (entity instanceof net.minecraft.entity.IProjectile || entity instanceof net.minecraft.entity.projectile.EntityShulkerBullet || entity instanceof net.minecraft.entity.projectile.EntityFireball || entity instanceof net.minecraft.entity.item.EntityEnderEye) isEntityProjectileLoaded = true; 
/*     */       } 
/* 500 */       EntityUtil.entitiesListFlag = false;
/*     */       
/* 502 */       if (((Boolean)this.espTargetMobs.getValue()).booleanValue() && this.espModeMobs.getValue() == Mode.Shader && isEntityMobLoaded) {
/* 503 */         Color color1 = new Color(0, 0, 0, 0);
/* 504 */         int alpha1 = 0;
/* 505 */         Outline.SHADER_OUTLINE.startDraw(mc.func_184121_ak(), false);
/* 506 */         for (Entity entity : EntityUtil.entitiesList()) {
/* 507 */           EntityUtil.entitiesListFlag = true;
/* 508 */           if ((!((Boolean)this.ignoreInvisible.getValue()).booleanValue() || !entity.func_82150_aj()) && (
/* 509 */             EntityUtil.isEntityMob(entity) || entity instanceof net.minecraft.entity.boss.EntityDragon) && (
/* 510 */             !((Boolean)this.espRangeLimit.getValue()).booleanValue() || mc.field_71439_g.func_70032_d(entity) <= ((Float)this.espRange.getValue()).floatValue())) {
/* 511 */             color1 = ((Color)this.espColorMobs.getValue()).getColorColor();
/* 512 */             alpha1 = ((Color)this.espColorMobs.getValue()).getAlpha();
/* 513 */             renderEntityInWorld(entity);
/*     */           } 
/*     */         } 
/* 516 */         EntityUtil.entitiesListFlag = false;
/* 517 */         Outline.SHADER_OUTLINE.stopDraw(color1, alpha1, ((Float)this.espMobWidth.getValue()).floatValue(), 1.0F);
/*     */       } 
/*     */ 
/*     */       
/* 521 */       if (((Boolean)this.espTargetAnimals.getValue()).booleanValue() && this.espModeAnimals.getValue() == Mode.Shader && isEntityAnimalLoaded) {
/* 522 */         Color color2 = new Color(0, 0, 0, 0);
/* 523 */         int alpha2 = 0;
/* 524 */         Outline.SHADER_OUTLINE.startDraw(mc.func_184121_ak(), false);
/* 525 */         for (Entity entity : EntityUtil.entitiesList()) {
/* 526 */           EntityUtil.entitiesListFlag = true;
/* 527 */           if ((!((Boolean)this.ignoreInvisible.getValue()).booleanValue() || !entity.func_82150_aj()) && 
/* 528 */             EntityUtil.isEntityAnimal(entity) && (
/* 529 */             !((Boolean)this.espRangeLimit.getValue()).booleanValue() || mc.field_71439_g.func_70032_d(entity) <= ((Float)this.espRange.getValue()).floatValue())) {
/* 530 */             color2 = ((Color)this.espColorAnimals.getValue()).getColorColor();
/* 531 */             alpha2 = ((Color)this.espColorAnimals.getValue()).getAlpha();
/* 532 */             renderEntityInWorld(entity);
/*     */           } 
/*     */         } 
/* 535 */         EntityUtil.entitiesListFlag = false;
/* 536 */         Outline.SHADER_OUTLINE.stopDraw(color2, alpha2, ((Float)this.espAnimalWidth.getValue()).floatValue(), 1.0F);
/*     */       } 
/*     */ 
/*     */       
/* 540 */       if (((Boolean)this.espTargetCrystals.getValue()).booleanValue() && this.espModeCrystals.getValue() == Mode.Shader && isEntityCrystalLoaded) {
/* 541 */         Color color3 = new Color(0, 0, 0, 0);
/* 542 */         int alpha3 = 0;
/* 543 */         Outline.SHADER_OUTLINE.startDraw(mc.func_184121_ak(), false);
/* 544 */         for (Entity entity : EntityUtil.entitiesList()) {
/* 545 */           EntityUtil.entitiesListFlag = true;
/* 546 */           if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal && (
/* 547 */             !((Boolean)this.espRangeLimit.getValue()).booleanValue() || mc.field_71439_g.func_70032_d(entity) <= ((Float)this.espRange.getValue()).floatValue())) {
/* 548 */             color3 = ((Color)this.espColorCrystals.getValue()).getColorColor();
/* 549 */             alpha3 = ((Color)this.espColorCrystals.getValue()).getAlpha();
/* 550 */             renderEntityInWorld(entity);
/*     */           } 
/*     */         } 
/* 553 */         EntityUtil.entitiesListFlag = false;
/* 554 */         Outline.SHADER_OUTLINE.stopDraw(color3, alpha3, ((Float)this.espCrystalWidth.getValue()).floatValue(), 1.0F);
/*     */       } 
/*     */ 
/*     */       
/* 558 */       if (((Boolean)this.espTargetItems.getValue()).booleanValue() && this.espModeItems.getValue() == ModeItems.Shader && isEntityItemLoaded) {
/* 559 */         Color color4 = new Color(0, 0, 0, 0);
/* 560 */         int alpha4 = 0;
/* 561 */         Outline.SHADER_OUTLINE.startDraw(mc.func_184121_ak(), false);
/* 562 */         for (Entity entity : EntityUtil.entitiesList()) {
/* 563 */           EntityUtil.entitiesListFlag = true;
/* 564 */           if (entity instanceof net.minecraft.entity.item.EntityItem && (
/* 565 */             !((Boolean)this.espRangeLimitItems.getValue()).booleanValue() || mc.field_71439_g.func_70032_d(entity) <= ((Float)this.espRangeItems.getValue()).floatValue())) {
/* 566 */             color4 = ((Color)this.espColorItems.getValue()).getColorColor();
/* 567 */             alpha4 = ((Color)this.espColorItems.getValue()).getAlpha();
/* 568 */             renderEntityInWorld(entity);
/*     */           } 
/*     */         } 
/* 571 */         EntityUtil.entitiesListFlag = false;
/* 572 */         Outline.SHADER_OUTLINE.stopDraw(color4, alpha4, ((Float)this.espItemWidth.getValue()).floatValue(), 1.0F);
/*     */       } 
/*     */       
/* 575 */       if (((Boolean)this.espTargetProjectiles.getValue()).booleanValue() && this.espModeProjectiles.getValue() == ModeItems.Shader && isEntityProjectileLoaded) {
/* 576 */         Color color69 = new Color(0, 0, 0, 0);
/* 577 */         int alpha69 = 0;
/* 578 */         Outline.SHADER_OUTLINE.startDraw(mc.func_184121_ak(), false);
/* 579 */         for (Entity entity : EntityUtil.entitiesList()) {
/* 580 */           EntityUtil.entitiesListFlag = true;
/* 581 */           if ((entity instanceof net.minecraft.entity.IProjectile || entity instanceof net.minecraft.entity.projectile.EntityShulkerBullet || entity instanceof net.minecraft.entity.projectile.EntityFireball || entity instanceof net.minecraft.entity.item.EntityEnderEye) && (
/* 582 */             !((Boolean)this.espRangeLimit.getValue()).booleanValue() || mc.field_71439_g.func_70032_d(entity) <= ((Float)this.espRange.getValue()).floatValue())) {
/* 583 */             color69 = ((Color)this.espColorProjectiles.getValue()).getColorColor();
/* 584 */             alpha69 = ((Color)this.espColorProjectiles.getValue()).getAlpha();
/* 585 */             renderEntityInWorld(entity);
/*     */           } 
/*     */         } 
/* 588 */         EntityUtil.entitiesListFlag = false;
/* 589 */         Outline.SHADER_OUTLINE.stopDraw(color69, alpha69, ((Float)this.espProjectileWidth.getValue()).floatValue(), 1.0F);
/*     */       } 
/*     */ 
/*     */       
/* 593 */       if (((Boolean)this.espTargetPlayers.getValue()).booleanValue() && this.espModePlayers.getValue() == Mode.Shader && isEntityPlayerLoaded) {
/* 594 */         Color color5 = new Color(0, 0, 0, 0);
/* 595 */         int alpha5 = 0;
/* 596 */         Outline.SHADER_OUTLINE.startDraw(mc.func_184121_ak(), false);
/* 597 */         for (Entity entity : EntityUtil.entitiesList()) {
/* 598 */           EntityUtil.entitiesListFlag = true;
/* 599 */           if ((!((Boolean)this.ignoreInvisible.getValue()).booleanValue() || !entity.func_82150_aj()) && 
/* 600 */             entity instanceof EntityPlayer && 
/* 601 */             entity != mc.field_71439_g && (!((Boolean)this.espRangeLimit.getValue()).booleanValue() || mc.field_71439_g.func_70032_d(entity) <= ((Float)this.espRange.getValue()).floatValue())) {
/* 602 */             if (FriendManager.isFriend(entity)) {
/* 603 */               color5 = ((Color)this.espColorPlayersFriend.getValue()).getColorColor();
/* 604 */               alpha5 = ((Color)this.espColorPlayersFriend.getValue()).getAlpha();
/*     */             }
/* 606 */             else if (EnemyManager.isEnemy(entity)) {
/* 607 */               color5 = ((Color)this.espColorPlayersEnemy.getValue()).getColorColor();
/* 608 */               alpha5 = ((Color)this.espColorPlayersEnemy.getValue()).getAlpha();
/*     */             } else {
/*     */               
/* 611 */               color5 = ((Color)this.espColorPlayers.getValue()).getColorColor();
/* 612 */               alpha5 = ((Color)this.espColorPlayers.getValue()).getAlpha();
/*     */             } 
/* 614 */             renderEntityInWorld(entity);
/*     */           } 
/*     */         } 
/* 617 */         EntityUtil.entitiesListFlag = false;
/* 618 */         Outline.SHADER_OUTLINE.stopDraw(color5, alpha5, ((Float)this.espPlayerWidth.getValue()).floatValue(), 1.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void renderWireframeXQZ(RenderEntityInvokeEvent event, Setting<Boolean> settingTarget, float espWidth) {
/*     */     Color wallColor;
/*     */     int wallAlpha;
/* 625 */     if (settingTarget == this.espTargetPlayers)
/* 626 */     { if (FriendManager.isFriend(event.entityIn)) { wallColor = ((Color)this.espWireframeWallColorPlayersFriend.getValue()).getColorColor(); }
/* 627 */       else if (EnemyManager.isEnemy(event.entityIn)) { wallColor = ((Color)this.espWireframeWallColorPlayersEnemy.getValue()).getColorColor(); }
/* 628 */       else { wallColor = ((Color)this.espWireframeWallColorPlayers.getValue()).getColorColor(); }
/*     */        }
/* 630 */     else if (settingTarget == this.espTargetSelf) { wallColor = ((Color)this.espWireframeWallColorSelf.getValue()).getColorColor(); }
/* 631 */     else if (settingTarget == this.espTargetMobs) { wallColor = ((Color)this.espWireframeWallColorMobs.getValue()).getColorColor(); }
/* 632 */     else { wallColor = ((Color)this.espWireframeWallColorAnimals.getValue()).getColorColor(); }
/*     */ 
/*     */     
/* 635 */     if (settingTarget == this.espTargetPlayers)
/* 636 */     { if (FriendManager.isFriend(event.entityIn)) { wallAlpha = ((Color)this.espWireframeWallColorPlayersFriend.getValue()).getAlpha(); }
/* 637 */       else if (EnemyManager.isEnemy(event.entityIn)) { wallAlpha = ((Color)this.espWireframeWallColorPlayersEnemy.getValue()).getAlpha(); }
/* 638 */       else { wallAlpha = ((Color)this.espWireframeWallColorPlayers.getValue()).getAlpha(); }
/*     */        }
/* 640 */     else if (settingTarget == this.espTargetSelf) { wallAlpha = ((Color)this.espWireframeWallColorSelf.getValue()).getAlpha(); }
/* 641 */     else if (settingTarget == this.espTargetMobs) { wallAlpha = ((Color)this.espWireframeWallColorMobs.getValue()).getAlpha(); }
/* 642 */     else { wallAlpha = ((Color)this.espWireframeWallColorAnimals.getValue()).getAlpha(); }
/*     */     
/* 644 */     GL11.glEnable(2881);
/* 645 */     GL11.glEnable(2848);
/* 646 */     SpartanTessellator.prepareGL();
/* 647 */     GL11.glDisable(2896);
/*     */     
/* 649 */     GL11.glPolygonMode(1032, 6913);
/* 650 */     GL11.glLineWidth(espWidth);
/*     */     
/* 652 */     GL11.glEnable(2929);
/* 653 */     GL11.glDepthFunc(516);
/* 654 */     GL11.glColor4f(wallColor.getRed() / 255.0F, wallColor.getGreen() / 255.0F, wallColor.getBlue() / 255.0F, wallAlpha / 255.0F);
/* 655 */     event.modelBase.func_78088_a(event.entityIn, event.limbSwing, event.limbSwingAmount, event.ageInTicks, event.netHeadYaw, event.headPitch, event.scale);
/* 656 */     GL11.glDepthFunc(513);
/*     */     
/* 658 */     GL11.glPolygonMode(1032, 6914);
/* 659 */     SpartanTessellator.releaseGL();
/*     */   }
/*     */   private void renderWireframe(RenderEntityLayersEvent event, Setting<Boolean> settingTarget, Setting<Boolean> settingespWireframeWallEffect, float espWidth) {
/*     */     Color color;
/*     */     int alpha;
/* 664 */     if (settingTarget == this.espTargetPlayers)
/* 665 */     { if (FriendManager.isFriend((Entity)event.entityIn)) { color = ((Color)this.espColorPlayersFriend.getValue()).getColorColor(); }
/* 666 */       else if (EnemyManager.isEnemy((Entity)event.entityIn)) { color = ((Color)this.espColorPlayersEnemy.getValue()).getColorColor(); }
/* 667 */       else { color = ((Color)this.espColorPlayers.getValue()).getColorColor(); }
/*     */        }
/* 669 */     else if (settingTarget == this.espTargetSelf) { color = ((Color)this.espColorSelf.getValue()).getColorColor(); }
/* 670 */     else if (settingTarget == this.espTargetMobs) { color = ((Color)this.espColorMobs.getValue()).getColorColor(); }
/* 671 */     else { color = ((Color)this.espColorAnimals.getValue()).getColorColor(); }
/*     */ 
/*     */     
/* 674 */     if (settingTarget == this.espTargetPlayers)
/* 675 */     { if (FriendManager.isFriend((Entity)event.entityIn)) { alpha = ((Color)this.espColorPlayersFriend.getValue()).getAlpha(); }
/* 676 */       else if (EnemyManager.isEnemy((Entity)event.entityIn)) { alpha = ((Color)this.espColorPlayersEnemy.getValue()).getAlpha(); }
/* 677 */       else { alpha = ((Color)this.espColorPlayers.getValue()).getAlpha(); }
/*     */        }
/* 679 */     else if (settingTarget == this.espTargetSelf) { alpha = ((Color)this.espColorSelf.getValue()).getAlpha(); }
/* 680 */     else if (settingTarget == this.espTargetMobs) { alpha = ((Color)this.espColorMobs.getValue()).getAlpha(); }
/* 681 */     else { alpha = ((Color)this.espColorAnimals.getValue()).getAlpha(); }
/*     */     
/* 683 */     GL11.glEnable(2881);
/* 684 */     GL11.glEnable(2848);
/* 685 */     GlStateManager.func_179118_c();
/* 686 */     GlStateManager.func_179090_x();
/* 687 */     GlStateManager.func_179097_i();
/* 688 */     GlStateManager.func_179147_l();
/* 689 */     GL11.glBlendFunc(770, 771);
/* 690 */     GL11.glDisable(2896);
/* 691 */     GL11.glLineWidth(espWidth);
/* 692 */     GL11.glPolygonMode(1032, 6913);
/* 693 */     if (((Boolean)settingespWireframeWallEffect.getValue()).booleanValue()) {
/* 694 */       GlStateManager.func_179126_j();
/* 695 */       GlStateManager.func_179132_a(true);
/*     */     } 
/* 697 */     GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha / 255.0F);
/* 698 */     event.modelBase.func_78088_a((Entity)event.entityIn, event.limbSwing, event.limbSwingAmount, event.ageInTicks, event.netHeadYaw, event.headPitch, event.scaleIn);
/*     */     
/* 700 */     GL11.glPolygonMode(1032, 6914);
/* 701 */     SpartanTessellator.releaseGL();
/*     */   }
/*     */   
/*     */   private void unGlow() {
/* 705 */     for (Entity entity : EntityUtil.entitiesList()) {
/* 706 */       EntityUtil.entitiesListFlag = true;
/* 707 */       entity.func_184195_f(false);
/*     */     } 
/* 709 */     EntityUtil.entitiesListFlag = false;
/*     */   }
/*     */   
/*     */   private void unGlow(Entity entityIn) {
/* 713 */     for (Entity entity : EntityUtil.entitiesList()) {
/* 714 */       EntityUtil.entitiesListFlag = true;
/* 715 */       if (entity == entityIn) entity.func_184195_f(false); 
/*     */     } 
/* 717 */     EntityUtil.entitiesListFlag = false;
/*     */   }
/*     */   
/*     */   private void renderBox(Setting<Boolean> settingESPTarget, Setting<BoxMode> settingESPBoxMode, Setting<Float> settingESPBoxLineWidth, Setting<Color> settingESPColorLines, Setting<Color> settingESPColorSolid) {
/* 721 */     for (Entity entity : EntityUtil.entitiesList()) {
/* 722 */       EntityUtil.entitiesListFlag = true;
/* 723 */       if (RenderHelper.isInViewFrustrum(entity) && (!((Boolean)this.ignoreInvisible.getValue()).booleanValue() || !entity.func_82150_aj())) {
/*     */         boolean entityCheck;
/* 725 */         if (settingESPTarget == this.espTargetPlayers)
/* 726 */         { entityCheck = entity instanceof EntityPlayer;
/*     */           
/* 728 */           if (FriendManager.isFriend(entity)) { settingESPColorLines = this.espColorPlayersLinesFriend; }
/* 729 */           else if (EnemyManager.isEnemy(entity)) { settingESPColorLines = this.espColorPlayersLinesEnemy; }
/* 730 */           else { settingESPColorLines = this.espColorPlayersLines; }
/*     */           
/* 732 */           if (FriendManager.isFriend(entity)) { settingESPColorSolid = this.espColorPlayersSolidFriend; }
/* 733 */           else if (EnemyManager.isEnemy(entity)) { settingESPColorSolid = this.espColorPlayersSolidEnemy; }
/* 734 */           else { settingESPColorSolid = this.espColorPlayersSolid; }
/*     */            }
/* 736 */         else if (settingESPTarget == this.espTargetMobs) { entityCheck = (EntityUtil.isEntityMob(entity) || entity instanceof net.minecraft.entity.boss.EntityDragon); }
/* 737 */         else if (settingESPTarget == this.espTargetAnimals) { entityCheck = EntityUtil.isEntityAnimal(entity); }
/* 738 */         else if (settingESPTarget == this.espTargetCrystals) { entityCheck = entity instanceof net.minecraft.entity.item.EntityEnderCrystal; }
/* 739 */         else if (settingESPTarget == this.espTargetItems) { entityCheck = entity instanceof net.minecraft.entity.item.EntityItem; }
/* 740 */         else { entityCheck = (entity instanceof net.minecraft.entity.IProjectile || entity instanceof net.minecraft.entity.projectile.EntityShulkerBullet || entity instanceof net.minecraft.entity.projectile.EntityFireball || entity instanceof net.minecraft.entity.item.EntityEnderEye); }
/*     */ 
/*     */         
/* 743 */         if (entityCheck && ((Boolean)settingESPTarget.getValue()).booleanValue() && entity != mc.field_71439_g && (!((Boolean)this.espRangeLimit.getValue()).booleanValue() || mc.field_71439_g.func_70032_d(entity) <= ((Float)this.espRange.getValue()).floatValue())) {
/* 744 */           if (settingESPBoxMode.getValue() == BoxMode.Solid || settingESPBoxMode.getValue() == BoxMode.Both)
/* 745 */             SpartanTessellator.drawBBFullBox(entity, ((Color)settingESPColorSolid.getValue()).getColor()); 
/* 746 */           if (settingESPBoxMode.getValue() == BoxMode.Lines || settingESPBoxMode.getValue() == BoxMode.Both)
/* 747 */             SpartanTessellator.drawBBLineBox(entity, ((Float)settingESPBoxLineWidth.getValue()).floatValue(), ((Color)settingESPColorLines.getValue()).getColor()); 
/*     */         } 
/*     */       } 
/*     */     } 
/* 751 */     EntityUtil.entitiesListFlag = false;
/*     */   }
/*     */   
/*     */   private void outline1() {
/* 755 */     this.renderOutlineFlag = true;
/*     */     
/* 757 */     Framebuffer fbo = mc.field_147124_at;
/* 758 */     if (fbo != null && 
/* 759 */       fbo.field_147624_h > -1) {
/* 760 */       EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.field_147624_h);
/* 761 */       int stencilDepthBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
/* 762 */       EXTFramebufferObject.glBindRenderbufferEXT(36161, stencilDepthBufferID);
/* 763 */       EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, mc.field_71443_c, mc.field_71440_d);
/* 764 */       EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencilDepthBufferID);
/* 765 */       EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencilDepthBufferID);
/* 766 */       fbo.field_147624_h = -1;
/*     */     } 
/*     */ 
/*     */     
/* 770 */     GL11.glLineWidth(((Float)this.espOutlineWidth.getValue()).floatValue());
/* 771 */     GL11.glEnable(2848);
/* 772 */     GL11.glEnable(2960);
/* 773 */     GL11.glStencilFunc(512, 1, 15);
/* 774 */     GL11.glStencilOp(7681, 7681, 7681);
/* 775 */     GL11.glPolygonMode(1032, 6913);
/*     */   }
/*     */   
/*     */   private void outline2() {
/* 779 */     GL11.glStencilFunc(512, 0, 15);
/* 780 */     GL11.glStencilOp(7681, 7681, 7681);
/* 781 */     GL11.glPolygonMode(1032, 6914);
/*     */   }
/*     */   
/*     */   private void outline3() {
/* 785 */     GL11.glStencilFunc(514, 1, 15);
/* 786 */     GL11.glStencilOp(7680, 7680, 7680);
/* 787 */     GL11.glPolygonMode(1032, 6913);
/* 788 */     GL11.glDepthRange(0.0D, 0.001D);
/*     */   }
/*     */   
/*     */   private void outline4() {
/* 792 */     GL11.glDepthRange(0.0D, 1.0D);
/* 793 */     GL11.glDisable(2960);
/* 794 */     GL11.glDisable(2848);
/* 795 */     GL11.glPolygonMode(1032, 6914);
/* 796 */     this.renderOutlineFlag = false;
/*     */   }
/*     */   
/*     */   private void doRenderVanillaEntitiesAgain(boolean flag) {
/* 800 */     for (Entity entity : EntityUtil.entitiesList()) {
/* 801 */       EntityUtil.entitiesListFlag = true;
/* 802 */       if (RenderHelper.isInViewFrustrum(entity) && (!((Boolean)this.ignoreInvisible.getValue()).booleanValue() || !entity.func_82150_aj()) && (!((Boolean)this.espRangeLimit.getValue()).booleanValue() || mc.field_71439_g.func_70032_d(entity) <= ((Float)this.espRange.getValue()).floatValue()) && ((entity instanceof EntityPlayer && ((Boolean)this.espTargetPlayers.getValue()).booleanValue() && this.espModePlayers.getValue() == Mode.Outline && entity != mc.field_71439_g && EntityUtil.isEntityPlayerLoaded) || (entity == mc.field_71439_g && ((Boolean)this.espTargetSelf.getValue()).booleanValue() && this.espModeSelf.getValue() == ModeSelf.Outline) || ((EntityUtil.isEntityMob(entity) || entity instanceof net.minecraft.entity.boss.EntityDragon) && ((Boolean)this.espTargetMobs.getValue()).booleanValue() && this.espModeMobs.getValue() == Mode.Outline && EntityUtil.isEntityMobLoaded) || (EntityUtil.isEntityAnimal(entity) && ((Boolean)this.espTargetAnimals.getValue()).booleanValue() && this.espModeAnimals.getValue() == Mode.Outline && EntityUtil.isEntityAnimalLoaded) || (entity instanceof net.minecraft.entity.item.EntityEnderCrystal && ((Boolean)this.espTargetCrystals.getValue()).booleanValue() && this.espModeCrystals.getValue() == Mode.Outline && EntityUtil.isEntityCrystalLoaded))) {
/* 803 */         if (flag) {
/* 804 */           outLineColor(entity);
/* 805 */           GL11.glDisable(3553);
/* 806 */           GL11.glDisable(2896);
/*     */         } 
/* 808 */         renderEntityInWorld(entity);
/*     */       } 
/*     */     } 
/* 811 */     EntityUtil.entitiesListFlag = false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void outLineColor(Entity entity) {
/* 816 */     Color theColor = new Color(255, 255, 255, 255);
/* 817 */     if (entity instanceof EntityPlayer && ((Boolean)this.espTargetPlayers.getValue()).booleanValue() && entity != mc.field_71439_g)
/* 818 */       if (FriendManager.isFriend(entity)) { theColor = ((Color)this.espColorPlayersFriend.getValue()).getColorColor(); }
/* 819 */       else if (EnemyManager.isEnemy(entity)) { theColor = ((Color)this.espColorPlayersEnemy.getValue()).getColorColor(); }
/* 820 */       else { theColor = ((Color)this.espColorPlayers.getValue()).getColorColor(); }
/*     */        
/* 822 */     if (entity == mc.field_71439_g && ((Boolean)this.espTargetSelf.getValue()).booleanValue())
/* 823 */       theColor = ((Color)this.espColorSelf.getValue()).getColorColor(); 
/* 824 */     if ((EntityUtil.isEntityMob(entity) || entity instanceof net.minecraft.entity.boss.EntityDragon) && ((Boolean)this.espTargetMobs.getValue()).booleanValue())
/* 825 */       theColor = ((Color)this.espColorMobs.getValue()).getColorColor(); 
/* 826 */     if (EntityUtil.isEntityAnimal(entity) && ((Boolean)this.espTargetAnimals.getValue()).booleanValue())
/* 827 */       theColor = ((Color)this.espColorAnimals.getValue()).getColorColor(); 
/* 828 */     if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal && ((Boolean)this.espTargetCrystals.getValue()).booleanValue()) {
/* 829 */       theColor = ((Color)this.espColorCrystals.getValue()).getColorColor();
/*     */     }
/* 831 */     int theAlpha = 255;
/* 832 */     if (entity instanceof EntityPlayer && ((Boolean)this.espTargetPlayers.getValue()).booleanValue() && entity != mc.field_71439_g)
/* 833 */       if (FriendManager.isFriend(entity)) { theAlpha = ((Color)this.espColorPlayersFriend.getValue()).getAlpha(); }
/* 834 */       else if (EnemyManager.isEnemy(entity)) { theAlpha = ((Color)this.espColorPlayersEnemy.getValue()).getAlpha(); }
/* 835 */       else { theAlpha = ((Color)this.espColorPlayers.getValue()).getAlpha(); }
/*     */        
/* 837 */     if (entity == mc.field_71439_g && ((Boolean)this.espTargetSelf.getValue()).booleanValue())
/* 838 */       theAlpha = ((Color)this.espColorSelf.getValue()).getAlpha(); 
/* 839 */     if ((EntityUtil.isEntityMob(entity) || entity instanceof net.minecraft.entity.boss.EntityDragon) && ((Boolean)this.espTargetMobs.getValue()).booleanValue())
/* 840 */       theAlpha = ((Color)this.espColorMobs.getValue()).getAlpha(); 
/* 841 */     if (EntityUtil.isEntityAnimal(entity) && ((Boolean)this.espTargetAnimals.getValue()).booleanValue())
/* 842 */       theAlpha = ((Color)this.espColorAnimals.getValue()).getAlpha(); 
/* 843 */     if (entity instanceof net.minecraft.entity.item.EntityEnderCrystal && ((Boolean)this.espTargetCrystals.getValue()).booleanValue()) {
/* 844 */       theAlpha = ((Color)this.espColorCrystals.getValue()).getAlpha();
/*     */     }
/* 846 */     GL11.glColor4f(theColor.getRed() / 255.0F, theColor.getGreen() / 255.0F, theColor.getBlue() / 255.0F, theAlpha / 255.0F);
/*     */   }
/*     */   
/*     */   enum Page {
/* 850 */     General, Self, Players, Mobs, Animals, Crystals, Items, Projectiles;
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 854 */     Outline, Glow, Shader, Box, Wireframe, None;
/*     */   }
/*     */   
/*     */   public enum ModeSelf {
/* 858 */     Outline, Glow, Wireframe, None;
/*     */   }
/*     */   
/*     */   public enum ModeItems {
/* 862 */     Glow, Shader, Box, Wireframe;
/*     */   }
/*     */   
/*     */   enum BoxMode {
/* 866 */     Lines, Solid, Both;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\visuals\ESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */