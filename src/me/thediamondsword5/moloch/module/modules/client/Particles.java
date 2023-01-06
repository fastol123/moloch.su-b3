/*     */ package me.thediamondsword5.moloch.module.modules.client;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.event.events.render.DrawScreenEvent;
/*     */ import me.thediamondsword5.moloch.utils.graphics.ParticleUtil;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.event.Listener;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "Particles", category = Category.CLIENT, description = "Display particles and lines on background")
/*     */ public class Particles
/*     */   extends Module
/*     */ {
/*     */   public static Particles INSTANCE;
/*  23 */   public Setting<Boolean> particlesChatGUI = setting("ChatEffect", true).des("Render particles in chat");
/*  24 */   public Setting<Boolean> particlesOtherGUI = setting("GUIEffect", true).des("Render particles in GUIs");
/*  25 */   public Setting<Float> fadeInSpeed = setting("FadeInSpeed", 1.0F, 0.1F, 5.0F).des("Particles fade in speed");
/*     */   
/*  27 */   public Setting<Page> page = setting("Page", Page.Particles);
/*  28 */   public Setting<ParticlesPage> particlesPage = setting("ParticlesPage", ParticlesPage.Particles).whenAtMode(this.page, Page.Particles);
/*     */   
/*  30 */   public Setting<ParticlesSpawnMode> particlesSpawnMode = setting("ParticlesSpawnMode", ParticlesSpawnMode.Corners).des("Where to spawn particles").whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Spawn);
/*  31 */   public Setting<ParticlesSpawnSideMode> particlesSpawnSideMode = setting("ParticlesSpawnSide", ParticlesSpawnSideMode.Both).des("What sides of screen particles spawn in").whenAtMode(this.particlesSpawnMode, ParticlesSpawnMode.Sides).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Spawn);
/*  32 */   public Setting<Boolean> particlesSpawnUpLeftCorner = setting("ParticlesSpawnUpLeft", true).des("Particles spawn in top left corner of screen").whenAtMode(this.particlesSpawnMode, ParticlesSpawnMode.Corners).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Spawn);
/*  33 */   public Setting<Boolean> particlesSpawnDownLeftCorner = setting("ParticlesSpawnDownLeft", true).des("Particles spawn in bottom left corner of screen").whenAtMode(this.particlesSpawnMode, ParticlesSpawnMode.Corners).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Spawn);
/*  34 */   public Setting<Boolean> particlesSpawnUpRightCorner = setting("ParticlesSpawnUpRight", true).des("Particles spawn in top right corner of screen").whenAtMode(this.particlesSpawnMode, ParticlesSpawnMode.Corners).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Spawn);
/*  35 */   public Setting<Boolean> particlesSpawnDownRightCorner = setting("ParticlesSpawnDownRight", true).des("Particles spawn in bottom right corner of screen").whenAtMode(this.particlesSpawnMode, ParticlesSpawnMode.Corners).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Spawn);
/*     */   
/*  37 */   public Setting<ParticlesShape> particlesShape = setting("ParticlesShape", ParticlesShape.Triangle).des("Shape of particles").whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  38 */   public Setting<Float> maxParticleSpinSpeed = setting("MaxSpinSpeed", 3.5F, 0.0F, 5.0F).des("Max particles spin speed").only(v -> (this.particlesShape.getValue() != ParticlesShape.Circle)).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  39 */   public Setting<Float> minParticleSpinSpeed = setting("MinSpinSpeed", 1.0F, 0.0F, 5.0F).des("Min particles spin speed").only(v -> (this.particlesShape.getValue() != ParticlesShape.Circle)).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  40 */   public Setting<Boolean> particleSpeedAlpha = setting("SpeedAlpha", true).des("Lowers alpha the faster a particle is going").whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  41 */   public Setting<Float> particlesSpeedAlphaFactor = setting("SpeedAlphaFactor", 1.0F, 1.0F, 2.0F).des("Multiply final speed alpha to make alpha difference more noticeable").whenTrue(this.particleSpeedAlpha).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  42 */   public Setting<Boolean> randomParticleSize = setting("RandomSize", true).des("Randomizes particle size").whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  43 */   public Setting<Float> particleSize = setting("Size", 5.0F, 0.0F, 5.0F).des("Particle size").whenFalse(this.randomParticleSize).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  44 */   public Setting<Float> maxParticleSize = setting("MaxSize", 3.0F, 0.0F, 5.0F).des("Max particle random size").whenTrue(this.randomParticleSize).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  45 */   public Setting<Float> minParticleSize = setting("MinSize", 2.0F, 0.0F, 5.0F).des("Min particle random size").whenTrue(this.randomParticleSize).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  46 */   public Setting<Float> maxParticleSpeed = setting("MaxSpeed", 1.0F, 0.0F, 5.0F).des("Max particles speed").whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  47 */   public Setting<Float> minParticleSpeed = setting("MinSpeed", 0.2F, 0.0F, 5.0F).des("Min particles speed").whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  48 */   public Setting<Integer> particleAmount = setting("Amount", 75, 0, 400).des("Amount of particles").whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  49 */   public Setting<Boolean> particleRollColor = setting("ParticleRollColor", true).des("Particles will roll between 2 colors").whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  50 */   public Setting<Color> particleRollColor1 = setting("ParticleRollColor1", new Color((new Color(81, 43, 170, 204)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 81, 43, 170, 204)).whenTrue(this.particleRollColor).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  51 */   public Setting<Color> particleRollColor2 = setting("ParticleRollColor2", new Color((new Color(216, 180, 255, 204)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 216, 180, 255, 204)).whenTrue(this.particleRollColor).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  52 */   public Setting<Float> particleRollColorSpeed = setting("ParticleRollColorSpeed", 0.5F, 0.1F, 3.0F).whenTrue(this.particleRollColor).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  53 */   public Setting<Float> particleRollColorSize = setting("ParticleRollColorSize", 1.5F, 0.1F, 2.0F).whenTrue(this.particleRollColor).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  54 */   public Setting<Color> particleColor = setting("ParticleColor", new Color((new Color(255, 255, 255, 199)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 199)).des("Particle Color").whenFalse(this.particleRollColor).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  55 */   public Setting<Boolean> particleRainbowRoll = setting("ParticleRainbowRoll", true).des("Rolling particles rainbow").whenFalse(this.particleRollColor).only(v -> ((Color)this.particleColor.getValue()).getRainbow()).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*  56 */   public Setting<Float> particleRainbowRollSize = setting("PRainbowRollSize", 1.0F, 0.1F, 2.0F).des("Size of particle rainbow roll").whenTrue(this.particleRainbowRoll).whenFalse(this.particleRollColor).only(v -> ((Color)this.particleColor.getValue()).getRainbow()).whenAtMode(this.page, Page.Particles).whenAtMode(this.particlesPage, ParticlesPage.Particles);
/*     */   
/*  58 */   public Setting<Integer> connectRange = setting("LineRange", 92, 0, 700).des("Range when particles will connect").whenAtMode(this.page, Page.Lines);
/*  59 */   public Setting<Boolean> onlyConnectOne = setting("ConnectOne", false).des("One line to closest point").whenAtMode(this.page, Page.Lines);
/*  60 */   public Setting<Boolean> restrictToAroundMouseLines = setting("LinesAroundMouse", false).des("Only lines around mouse").whenAtMode(this.page, Page.Lines);
/*  61 */   public Setting<Float> restrictToAroundMouseLinesRange = setting("LinesAroundMouseRange", 114.0F, 1.0F, 500.0F).des("Distance from mouse to connect lines with particles").whenTrue(this.restrictToAroundMouseLines).whenAtMode(this.page, Page.Lines);
/*  62 */   public Setting<Boolean> linesFadeIn = setting("LinesFadeIn", true).des("Fade in lines as points get closer").whenAtMode(this.page, Page.Lines);
/*  63 */   public Setting<Float> linesFadeInFactor = setting("LinesFadeInFactor", 1.0F, 0.1F, 1.0F).des("Fraction of range to stop fading in").whenTrue(this.linesFadeIn).whenAtMode(this.page, Page.Lines);
/*  64 */   public Setting<Float> linesWidth = setting("LinesWidth", 1.5F, 1.0F, 3.0F).des("Lines Width").whenAtMode(this.page, Page.Lines);
/*  65 */   public Setting<Boolean> lineRollColor = setting("LineRollColor", true).des("Lines will roll between 2 colors").whenAtMode(this.page, Page.Lines);
/*  66 */   public Setting<Color> lineRollColor1 = setting("LineRollColor1", new Color((new Color(81, 43, 170, 40)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 81, 43, 170, 40)).whenTrue(this.lineRollColor).whenAtMode(this.page, Page.Lines);
/*  67 */   public Setting<Color> lineRollColor2 = setting("LineRollColor2", new Color((new Color(216, 180, 255, 40)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 216, 180, 255, 40)).whenTrue(this.lineRollColor).whenAtMode(this.page, Page.Lines);
/*  68 */   public Setting<Float> lineRollColorSpeed = setting("LineRollColorSpeed", 0.5F, 0.1F, 3.0F).whenTrue(this.lineRollColor).whenAtMode(this.page, Page.Lines);
/*  69 */   public Setting<Float> lineRollColorSize = setting("LineRollColorSize", 1.4F, 0.1F, 2.0F).whenTrue(this.lineRollColor).whenAtMode(this.page, Page.Lines);
/*  70 */   public Setting<Color> lineColor = setting("LineColor", new Color((new Color(255, 255, 255, 40)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 40)).des("Lines Color").whenFalse(this.lineRollColor).whenAtMode(this.page, Page.Lines);
/*  71 */   public Setting<Boolean> lineRainbowRoll = setting("LineRainbowRoll", true).des("Rolling lines rainbow").whenFalse(this.lineRollColor).only(v -> ((Color)this.lineColor.getValue()).getRainbow()).whenAtMode(this.page, Page.Lines);
/*  72 */   public Setting<Float> lineRainbowRollSize = setting("LRainbowRollSize", 1.0F, 0.1F, 2.0F).des("Size of line rainbow roll").whenTrue(this.lineRainbowRoll).whenFalse(this.lineRollColor).only(v -> ((Color)this.lineColor.getValue()).getRainbow()).whenAtMode(this.page, Page.Lines);
/*     */   
/*  74 */   public Setting<Boolean> mouseInteract = setting("MouseInteract", true).des("Do stuff with particles when mouse gets near them").whenAtMode(this.page, Page.MouseInteract);
/*  75 */   public Setting<Boolean> mouseInteractBounce = setting("MouseInteractBounce", true).des("Bounce particles off of mouse range").whenTrue(this.mouseInteract).whenAtMode(this.page, Page.MouseInteract);
/*  76 */   public Setting<Float> mouseInteractPlowStrength = setting("ParticlePlowStrength", 3.0F, 0.1F, 3.0F).whenTrue(this.mouseInteract).whenAtMode(this.page, Page.MouseInteract);
/*  77 */   public Setting<Boolean> mouseInterectPlowSpeedReduce = setting("ParticleSpeedReduce", true).des("Particles slow down on nearing mouse").whenTrue(this.mouseInteract).whenAtMode(this.page, Page.MouseInteract);
/*  78 */   public Setting<Float> mouseInteractPlowFractionOfSpeed = setting("ParticleFractionOfSpeed", 0.3F, 0.1F, 1.0F).whenTrue(this.mouseInterectPlowSpeedReduce).whenTrue(this.mouseInteract).whenAtMode(this.page, Page.MouseInteract);
/*  79 */   public Setting<Float> mouseInteractPlowSpeedRegenFactor = setting("ParticlesSpeedRegenFactor", 0.5F, 0.1F, 3.0F).whenTrue(this.mouseInterectPlowSpeedReduce).whenTrue(this.mouseInteract).whenAtMode(this.page, Page.MouseInteract);
/*  80 */   public Setting<Float> mouseInteractRange = setting("MouseInteractRange", 66.9F, 1.0F, 500.0F).des("Distance from mouse for particles to start interacting with mouse").whenTrue(this.mouseInteract).whenAtMode(this.page, Page.MouseInteract);
/*     */   
/*     */   public Particles() {
/*  83 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderTick() {
/*  88 */     doParticles(true);
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void onDrawScreenChat(DrawScreenEvent.Layer2 event) {
/*  93 */     doParticles(false);
/*     */   }
/*     */   
/*     */   private void doParticles(boolean chatMode) {
/*  97 */     if (!(mc.field_71462_r instanceof net.spartanb312.base.gui.ClickGUIFinal) && !(mc.field_71462_r instanceof net.spartanb312.base.gui.HUDEditorFinal)) {
/*  98 */       if ((isOtherGUIOpen() && !mc.field_71456_v.func_146158_b().func_146241_e() && ((Boolean)this.particlesOtherGUI.getValue()).booleanValue() && !chatMode) || (chatMode && ((Boolean)this.particlesChatGUI.getValue()).booleanValue() && mc.field_71456_v.func_146158_b().func_146241_e())) {
/*  99 */         ParticleUtil.render();
/*     */       }
/*     */       
/* 102 */       if (!isOtherGUIOpen() && !mc.field_71456_v.func_146158_b().func_146241_e() && !ParticleUtil.particlesClearedFlag) {
/* 103 */         ParticleUtil.clearParticles();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isOtherGUIOpen() {
/* 109 */     return (mc.field_71462_r instanceof net.minecraft.client.gui.inventory.GuiContainer || mc.field_71462_r instanceof net.minecraft.client.gui.GuiIngameMenu);
/*     */   }
/*     */   
/*     */   enum Page {
/* 113 */     Particles,
/* 114 */     Lines,
/* 115 */     MouseInteract;
/*     */   }
/*     */   
/*     */   enum ParticlesPage {
/* 119 */     Particles,
/* 120 */     Spawn;
/*     */   }
/*     */   
/*     */   public enum ParticlesShape {
/* 124 */     Circle,
/* 125 */     Triangle,
/* 126 */     Square;
/*     */   }
/*     */   
/*     */   public enum ParticlesSpawnMode {
/* 130 */     Sides,
/* 131 */     Corners;
/*     */   }
/*     */   
/*     */   public enum ParticlesSpawnSideMode {
/* 135 */     Vertical,
/* 136 */     Horizontal,
/* 137 */     Both,
/* 138 */     None;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\client\Particles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */