/*     */ package net.spartanb312.base.mixin.mixins.render;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.module.modules.visuals.Chams;
/*     */ import me.thediamondsword5.moloch.module.modules.visuals.ESP;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.RenderEnderCrystal;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.spartanb312.base.client.ModuleManager;
/*     */ import net.spartanb312.base.command.Command;
/*     */ import net.spartanb312.base.utils.EntityUtil;
/*     */ import net.spartanb312.base.utils.graphics.SpartanTessellator;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ @Mixin({RenderEnderCrystal.class})
/*     */ public class MixinRenderEnderCrystal
/*     */ {
/*     */   @Final
/*     */   @Shadow
/*     */   private ModelBase field_188316_g;
/*  32 */   private final ResourceLocation crystal = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
/*     */   
/*     */   @Inject(method = {"doRender"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void doRenderHookPre(EntityEnderCrystal entityIn, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
/*  36 */     float floatTicks = MathHelper.func_76126_a((entityIn.field_70261_a + partialTicks) * 0.2F) / 2.0F + 0.5F;
/*  37 */     floatTicks = (floatTicks * floatTicks + floatTicks) * ((ModuleManager.getModule(Chams.class).isEnabled() && ((Boolean)Chams.instance.crystals.getValue()).booleanValue() && !((Boolean)Chams.instance.fixCrystalOutlineESP.getValue()).booleanValue() && ((Boolean)Chams.instance.crystalBobModify.getValue()).booleanValue()) ? ((Float)Chams.instance.crystalBob.getValue()).floatValue() : 1.0F);
/*     */     
/*  39 */     if ((ModuleManager.getModule(Chams.class).isEnabled() && ((Boolean)Chams.instance.crystals.getValue()).booleanValue() && !((Boolean)Chams.instance.fixCrystalOutlineESP.getValue()).booleanValue()) || (((Boolean)ESP.INSTANCE.espTargetCrystals.getValue()).booleanValue() && ESP.INSTANCE.espModeCrystals.getValue() == ESP.Mode.Wireframe && ModuleManager.getModule(ESP.class).isEnabled() && (((Boolean)ESP.INSTANCE.espWireframeWallEffectCrystal.getValue()).booleanValue() || ((Boolean)ESP.INSTANCE.crystalsCancelVanillaRender.getValue()).booleanValue()) && (!((Boolean)ESP.INSTANCE.espRangeLimit.getValue()).booleanValue() || Command.mc.field_71439_g.func_70032_d((Entity)entityIn) <= ((Float)ESP.INSTANCE.espRange.getValue()).floatValue()))) {
/*     */       
/*  41 */       GL11.glPushMatrix();
/*  42 */       GL11.glTranslated(x, y + ((ModuleManager.getModule(Chams.class).isEnabled() && !((Boolean)Chams.instance.fixCrystalOutlineESP.getValue()).booleanValue() && ((Boolean)Chams.instance.crystals.getValue()).booleanValue()) ? ((Float)Chams.instance.crystalYOffset.getValue()).floatValue() : 0.0F), z);
/*  43 */       GL11.glEnable(3042);
/*  44 */       GL11.glBlendFunc(770, 771);
/*  45 */       GL11.glEnable(2848);
/*  46 */       GL11.glEnable(2881);
/*  47 */       GlStateManager.func_179118_c();
/*     */       
/*  49 */       if (((Boolean)Chams.instance.crystalScaleModify.getValue()).booleanValue() && !((Boolean)Chams.instance.fixCrystalOutlineESP.getValue()).booleanValue() && ModuleManager.getModule(Chams.class).isEnabled() && ((Boolean)Chams.instance.crystals.getValue()).booleanValue()) GL11.glScalef(((Float)Chams.instance.crystalScale.getValue()).floatValue(), ((Float)Chams.instance.crystalScale.getValue()).floatValue(), ((Float)Chams.instance.crystalScale.getValue()).floatValue());
/*     */       
/*  51 */       if (ModuleManager.getModule(Chams.class).isEnabled() && ((Boolean)Chams.instance.crystals.getValue()).booleanValue() && !((Boolean)Chams.instance.fixCrystalOutlineESP.getValue()).booleanValue()) {
/*  52 */         float alphaCrowdFactor = 1.0F;
/*  53 */         if (((Boolean)Chams.instance.crystalCrowdAlpha.getValue()).booleanValue() && EntityUtil.getInterpDistance(Command.mc.func_184121_ak(), (Entity)Command.mc.field_71439_g, (Entity)entityIn) <= ((Float)Chams.instance.crystalCrowdAlphaRadius.getValue()).floatValue()) alphaCrowdFactor = ((Float)Chams.instance.crystalCrowdEndAlpha.getValue()).floatValue() + (1.0F - ((Float)Chams.instance.crystalCrowdEndAlpha.getValue()).floatValue()) * (float)(EntityUtil.getInterpDistance(Command.mc.func_184121_ak(), (Entity)Command.mc.field_71439_g, (Entity)entityIn) / ((Float)Chams.instance.crystalCrowdAlphaRadius.getValue()).floatValue());
/*     */         
/*  55 */         Color colorChams = ((Color)Chams.instance.crystalColor.getValue()).getColorColor();
/*  56 */         int alphaChams = ((Color)Chams.instance.crystalColor.getValue()).getAlpha();
/*     */         
/*  58 */         GlStateManager.func_179132_a(((Boolean)Chams.instance.crystalDepthMask.getValue()).booleanValue());
/*  59 */         GL11.glPolygonMode(1032, 6914);
/*  60 */         if (((Boolean)Chams.instance.crystalBlend.getValue()).booleanValue()) GL11.glBlendFunc(770, 32772);
/*     */         
/*  62 */         if (((Boolean)Chams.instance.crystalCull.getValue()).booleanValue()) { GL11.glEnable(2884); }
/*  63 */         else { GL11.glDisable(2884); }
/*     */         
/*  65 */         if (((Boolean)Chams.instance.crystalWall.getValue()).booleanValue() && !((Boolean)Chams.instance.crystalWallEffect.getValue()).booleanValue()) GL11.glDepthRange(0.0D, 0.01D);
/*     */         
/*  67 */         if (((Boolean)Chams.instance.crystalLighting.getValue()).booleanValue()) { GL11.glEnable(2896); }
/*  68 */         else { GL11.glDisable(2896); }
/*     */         
/*  70 */         if (((Boolean)Chams.instance.crystalTexture.getValue()).booleanValue()) {
/*  71 */           GlStateManager.func_179141_d();
/*  72 */           GL11.glEnable(3553);
/*  73 */           Command.mc.func_110434_K().func_110577_a(this.crystal);
/*     */         } else {
/*  75 */           GL11.glDisable(3553);
/*     */         } 
/*  77 */         GL11.glColor4f(colorChams.getRed() / 255.0F, colorChams.getGreen() / 255.0F, colorChams.getBlue() / 255.0F, alphaChams / 255.0F * alphaCrowdFactor);
/*  78 */         this.field_188316_g.func_78088_a((Entity)entityIn, 0.0F, (entityIn.field_70261_a + partialTicks) * 3.0F * (((Boolean)Chams.instance.crystalSpinModify.getValue()).booleanValue() ? ((Float)Chams.instance.crystalSpinSpeed.getValue()).floatValue() : 1.0F), floatTicks * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */         
/*  80 */         if (((Boolean)Chams.instance.crystalWallEffect.getValue()).booleanValue())
/*  81 */           renderWallEffect(entityIn, alphaCrowdFactor, partialTicks, floatTicks); 
/*  82 */         if (((Boolean)Chams.instance.crystalGlint.getValue()).booleanValue()) {
/*  83 */           renderGlint(entityIn, partialTicks, alphaCrowdFactor, floatTicks, (Color)Chams.instance.crystalGlintColor.getValue());
/*     */         }
/*  85 */         if (((Boolean)Chams.instance.crystalWall.getValue()).booleanValue() && !((Boolean)Chams.instance.crystalWallEffect.getValue()).booleanValue()) GL11.glDepthRange(0.0D, 1.0D); 
/*  86 */         if (((Boolean)Chams.instance.crystalTexture.getValue()).booleanValue()) GlStateManager.func_179118_c(); 
/*  87 */         if (((Boolean)Chams.instance.crystalBlend.getValue()).booleanValue()) GL11.glBlendFunc(770, 771);
/*     */       
/*     */       } 
/*  90 */       if (((Boolean)ESP.INSTANCE.espTargetCrystals.getValue()).booleanValue() && ESP.INSTANCE.espModeCrystals.getValue() == ESP.Mode.Wireframe && ModuleManager.getModule(ESP.class).isEnabled() && (!((Boolean)ESP.INSTANCE.espRangeLimit.getValue()).booleanValue() || Command.mc.field_71439_g.func_70032_d((Entity)entityIn) <= ((Float)ESP.INSTANCE.espRange.getValue()).floatValue())) {
/*  91 */         GlStateManager.func_179132_a(false);
/*  92 */         GL11.glEnable(3008);
/*  93 */         GL11.glDisable(3553);
/*  94 */         GL11.glDisable(2896);
/*  95 */         GL11.glPolygonMode(1032, 6913);
/*  96 */         GL11.glEnable(2884);
/*  97 */         GL11.glLineWidth(((Float)ESP.INSTANCE.espCrystalWidth.getValue()).floatValue());
/*     */         
/*  99 */         if (((Boolean)ESP.INSTANCE.crystalsCancelVanillaRender.getValue()).booleanValue() || (ModuleManager.getModule(Chams.class).isEnabled() && ((Boolean)Chams.instance.crystalCancelVanillaRender.getValue()).booleanValue()) || (((Boolean)ESP.INSTANCE.crystalsCancelVanillaRender.getValue()).booleanValue() && !((Boolean)ESP.INSTANCE.espWireframeWallEffectCrystal.getValue()).booleanValue()) || (!((Boolean)ESP.INSTANCE.espWireframeOnlyWallCrystal.getValue()).booleanValue() && ((Boolean)ESP.INSTANCE.espWireframeWallEffectCrystal.getValue()).booleanValue() && (((Boolean)ESP.INSTANCE.crystalsCancelVanillaRender.getValue()).booleanValue() || (ModuleManager.getModule(Chams.class).isEnabled() && ((Boolean)Chams.instance.crystalCancelVanillaRender.getValue()).booleanValue())))) {
/* 100 */           Color color = ((Color)ESP.INSTANCE.espColorCrystals.getValue()).getColorColor();
/* 101 */           int alpha = ((Color)ESP.INSTANCE.espColorCrystals.getValue()).getAlpha();
/*     */           
/* 103 */           if (((Boolean)Chams.instance.crystalCancelVanillaRender.getValue()).booleanValue() && !((Boolean)Chams.instance.fixCrystalOutlineESP.getValue()).booleanValue() && !((Boolean)ESP.INSTANCE.espWireframeWallEffectCrystal.getValue()).booleanValue()) GlStateManager.func_179132_a(false); 
/* 104 */           if (!((Boolean)ESP.INSTANCE.espWireframeWallEffectCrystal.getValue()).booleanValue()) GL11.glDisable(2929);
/*     */ 
/*     */           
/* 107 */           GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha / 255.0F);
/* 108 */           this.field_188316_g.func_78088_a((Entity)entityIn, 0.0F, (entityIn.field_70261_a + partialTicks) * 3.0F * ((ModuleManager.getModule(Chams.class).isEnabled() && ((Boolean)Chams.instance.crystals.getValue()).booleanValue() && !((Boolean)Chams.instance.fixCrystalOutlineESP.getValue()).booleanValue() && ((Boolean)Chams.instance.crystalSpinModify.getValue()).booleanValue()) ? ((Float)Chams.instance.crystalSpinSpeed.getValue()).floatValue() : 1.0F), floatTicks * 0.2F, 0.0F, 0.0F, (((Boolean)Chams.instance.crystalScaleModify.getValue()).booleanValue() && ModuleManager.getModule(Chams.class).isEnabled()) ? (((Float)Chams.instance.crystalScale.getValue()).floatValue() * 0.0625F) : 0.0625F);
/*     */           
/* 110 */           GlStateManager.func_179132_a(false);
/*     */         } 
/*     */         
/* 113 */         if (((Boolean)ESP.INSTANCE.espWireframeWallEffectCrystal.getValue()).booleanValue()) {
/* 114 */           Color wallColor = ((Color)ESP.INSTANCE.espWireframeWallColorCrystals.getValue()).getColorColor();
/* 115 */           int wallAlpha = ((Color)ESP.INSTANCE.espWireframeWallColorCrystals.getValue()).getAlpha();
/*     */ 
/*     */           
/* 118 */           GL11.glEnable(2929);
/* 119 */           GL11.glDepthFunc(516);
/* 120 */           GL11.glColor4f(wallColor.getRed() / 255.0F, wallColor.getGreen() / 255.0F, wallColor.getBlue() / 255.0F, wallAlpha / 255.0F);
/* 121 */           this.field_188316_g.func_78088_a((Entity)entityIn, 0.0F, (entityIn.field_70261_a + partialTicks) * 3.0F * ((ModuleManager.getModule(Chams.class).isEnabled() && ((Boolean)Chams.instance.crystals.getValue()).booleanValue() && !((Boolean)Chams.instance.fixCrystalOutlineESP.getValue()).booleanValue() && ((Boolean)Chams.instance.crystalSpinModify.getValue()).booleanValue()) ? ((Float)Chams.instance.crystalSpinSpeed.getValue()).floatValue() : 1.0F), floatTicks * 0.2F, 0.0F, 0.0F, 0.0625F);
/* 122 */           GL11.glDepthFunc(513);
/* 123 */           GL11.glDisable(2929);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 128 */       if (((Boolean)Chams.instance.crystalScaleModify.getValue()).booleanValue() && !((Boolean)Chams.instance.fixCrystalOutlineESP.getValue()).booleanValue() && ModuleManager.getModule(Chams.class).isEnabled() && ((Boolean)Chams.instance.crystals.getValue()).booleanValue()) GL11.glScalef(1.0F / ((Float)Chams.instance.crystalScale.getValue()).floatValue(), 1.0F / ((Float)Chams.instance.crystalScale.getValue()).floatValue(), 1.0F / ((Float)Chams.instance.crystalScale.getValue()).floatValue());
/*     */       
/* 130 */       GL11.glPolygonMode(1032, 6914);
/* 131 */       GL11.glEnable(2896);
/* 132 */       GL11.glDepthFunc(515);
/* 133 */       SpartanTessellator.releaseGL();
/* 134 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/* 137 */     if ((ModuleManager.getModule(ESP.class).isEnabled() && ((Boolean)ESP.INSTANCE.espTargetCrystals.getValue()).booleanValue() && ((Boolean)ESP.INSTANCE.crystalsCancelVanillaRender.getValue()).booleanValue()) || (ModuleManager.getModule(Chams.class).isEnabled() && !((Boolean)Chams.instance.fixCrystalOutlineESP.getValue()).booleanValue() && ((Boolean)Chams.instance.crystals.getValue()).booleanValue() && ((Boolean)Chams.instance.crystalCancelVanillaRender.getValue()).booleanValue())) {
/* 138 */       ci.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"doRender"}, at = {@At("RETURN")})
/*     */   public void doRenderHook2(EntityEnderCrystal entityIn, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
/* 144 */     float floatTicks = MathHelper.func_76126_a((entityIn.field_70261_a + partialTicks) * 0.2F) / 2.0F + 0.5F;
/* 145 */     floatTicks = (floatTicks * floatTicks + floatTicks) * ((ModuleManager.getModule(Chams.class).isEnabled() && ((Boolean)Chams.instance.crystals.getValue()).booleanValue() && !((Boolean)Chams.instance.fixCrystalOutlineESP.getValue()).booleanValue() && ((Boolean)Chams.instance.crystalBobModify.getValue()).booleanValue()) ? ((Float)Chams.instance.crystalBob.getValue()).floatValue() : 1.0F);
/*     */     
/* 147 */     GL11.glPushMatrix();
/* 148 */     GL11.glTranslated(x, y + ((ModuleManager.getModule(Chams.class).isEnabled() && !((Boolean)Chams.instance.fixCrystalOutlineESP.getValue()).booleanValue() && ((Boolean)Chams.instance.crystals.getValue()).booleanValue()) ? ((Float)Chams.instance.crystalYOffset.getValue()).floatValue() : 0.0F), z);
/*     */     
/* 150 */     if ((!((Boolean)ESP.INSTANCE.espWireframeOnlyWallCrystal.getValue()).booleanValue() || !((Boolean)ESP.INSTANCE.espWireframeWallEffectCrystal.getValue()).booleanValue()) && ((Boolean)ESP.INSTANCE.espTargetCrystals.getValue()).booleanValue() && ESP.INSTANCE.espModeCrystals.getValue() == ESP.Mode.Wireframe && ModuleManager.getModule(ESP.class).isEnabled() && (!ModuleManager.getModule(Chams.class).isEnabled() || !((Boolean)Chams.instance.crystals.getValue()).booleanValue() || ((Boolean)Chams.instance.fixCrystalOutlineESP.getValue()).booleanValue() || !((Boolean)Chams.instance.crystalCancelVanillaRender.getValue()).booleanValue())) {
/*     */       
/* 152 */       Color color = ((Color)ESP.INSTANCE.espColorCrystals.getValue()).getColorColor();
/* 153 */       int alpha = ((Color)ESP.INSTANCE.espColorCrystals.getValue()).getAlpha();
/*     */       
/* 155 */       GL11.glEnable(3042);
/* 156 */       GL11.glBlendFunc(770, 771);
/* 157 */       GL11.glEnable(2881);
/* 158 */       GL11.glEnable(2848);
/* 159 */       GL11.glEnable(3008);
/* 160 */       GL11.glDisable(3553);
/* 161 */       GL11.glDisable(2896);
/* 162 */       GL11.glDisable(2929);
/* 163 */       GlStateManager.func_179089_o();
/* 164 */       if (((Boolean)Chams.instance.crystalScaleModify.getValue()).booleanValue() && !((Boolean)Chams.instance.fixCrystalOutlineESP.getValue()).booleanValue() && ModuleManager.getModule(Chams.class).isEnabled() && ((Boolean)Chams.instance.crystals.getValue()).booleanValue()) GL11.glScalef(((Float)Chams.instance.crystalScale.getValue()).floatValue(), ((Float)Chams.instance.crystalScale.getValue()).floatValue(), ((Float)Chams.instance.crystalScale.getValue()).floatValue());
/*     */       
/* 166 */       GL11.glLineWidth(((Float)ESP.INSTANCE.espCrystalWidth.getValue()).floatValue());
/* 167 */       GL11.glPolygonMode(1032, 6913);
/*     */       
/* 169 */       if (!((Boolean)ESP.INSTANCE.espWireframeOnlyWallCrystal.getValue()).booleanValue() && ((Boolean)ESP.INSTANCE.espWireframeWallEffectCrystal.getValue()).booleanValue() && !((Boolean)ESP.INSTANCE.crystalsCancelVanillaRender.getValue()).booleanValue() && (!ModuleManager.getModule(Chams.class).isEnabled() || !((Boolean)Chams.instance.crystalCancelVanillaRender.getValue()).booleanValue())) {
/* 170 */         GL11.glEnable(2929);
/* 171 */         GlStateManager.func_179132_a(false);
/*     */       } 
/*     */       
/* 174 */       GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha / 255.0F);
/* 175 */       this.field_188316_g.func_78088_a((Entity)entityIn, 0.0F, (entityIn.field_70261_a + partialTicks) * 3.0F * ((ModuleManager.getModule(Chams.class).isEnabled() && ((Boolean)Chams.instance.crystals.getValue()).booleanValue() && !((Boolean)Chams.instance.fixCrystalOutlineESP.getValue()).booleanValue() && ((Boolean)Chams.instance.crystalSpinModify.getValue()).booleanValue()) ? ((Float)Chams.instance.crystalSpinSpeed.getValue()).floatValue() : 1.0F), floatTicks * 0.2F, 0.0F, 0.0F, (((Boolean)Chams.instance.crystalScaleModify.getValue()).booleanValue() && ModuleManager.getModule(Chams.class).isEnabled()) ? (((Float)Chams.instance.crystalScale.getValue()).floatValue() * 0.0625F) : 0.0625F);
/*     */       
/* 177 */       if (((Boolean)Chams.instance.crystalScaleModify.getValue()).booleanValue() && !((Boolean)Chams.instance.fixCrystalOutlineESP.getValue()).booleanValue() && ModuleManager.getModule(Chams.class).isEnabled() && ((Boolean)Chams.instance.crystals.getValue()).booleanValue()) GL11.glScalef(1.0F / ((Float)Chams.instance.crystalScale.getValue()).floatValue(), 1.0F / ((Float)Chams.instance.crystalScale.getValue()).floatValue(), 1.0F / ((Float)Chams.instance.crystalScale.getValue()).floatValue());
/*     */       
/* 179 */       GL11.glPolygonMode(1032, 6914);
/* 180 */       SpartanTessellator.releaseGL();
/* 181 */       GL11.glEnable(2896);
/* 182 */       GL11.glEnable(2929);
/* 183 */       GL11.glEnable(3553);
/*     */     } 
/*     */     
/* 186 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderWallEffect(EntityEnderCrystal entityIn, float alphaCrowdFactor, float partialTicks, float floatTicks) {
/* 191 */     Color color = ((Color)Chams.instance.crystalWallColor.getValue()).getColorColor();
/* 192 */     Color color2 = (Color)Chams.instance.crystalWallColor.getValue();
/* 193 */     int alpha = ((Color)Chams.instance.crystalWallColor.getValue()).getAlpha();
/*     */     
/* 195 */     GlStateManager.func_179132_a(false);
/*     */     
/* 197 */     if (((Boolean)Chams.instance.crystalWallTexture.getValue()).booleanValue()) {
/* 198 */       GlStateManager.func_179141_d();
/* 199 */       GL11.glEnable(3553);
/* 200 */       Command.mc.func_110434_K().func_110577_a(this.crystal);
/*     */     } else {
/* 202 */       GL11.glDisable(3553);
/*     */     } 
/* 204 */     if (((Boolean)Chams.instance.crystalWallBlend.getValue()).booleanValue()) { GL11.glBlendFunc(770, 32772); }
/* 205 */     else { GL11.glBlendFunc(770, 771); }
/*     */     
/* 207 */     GL11.glEnable(2929);
/* 208 */     GL11.glDepthFunc(516);
/*     */     
/* 210 */     if (((Boolean)Chams.instance.crystalWallGlint.getValue()).booleanValue()) {
/* 211 */       renderGlint(entityIn, partialTicks, alphaCrowdFactor, floatTicks, color2);
/*     */     } else {
/* 213 */       GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha / 255.0F * alphaCrowdFactor);
/* 214 */       this.field_188316_g.func_78088_a((Entity)entityIn, 0.0F, (entityIn.field_70261_a + partialTicks) * 3.0F * (((Boolean)Chams.instance.crystalSpinModify.getValue()).booleanValue() ? ((Float)Chams.instance.crystalSpinSpeed.getValue()).floatValue() : 1.0F), floatTicks * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */     } 
/*     */     
/* 217 */     GL11.glDepthFunc(513);
/*     */   }
/*     */   
/*     */   private void renderGlint(EntityEnderCrystal entityIn, float partialTicks, float alphaCrowdFactor, float floatTicks, Color color) {
/* 221 */     ResourceLocation glintTexture = null;
/*     */     
/* 223 */     switch ((Chams.GlintMode)Chams.instance.crystalGlintMode.getValue()) {
/*     */       case LoadedPack:
/* 225 */         glintTexture = Chams.instance.loadedTexturePackGlint;
/*     */         break;
/*     */ 
/*     */       
/*     */       case Gradient:
/* 230 */         glintTexture = Chams.instance.gradientGlint;
/*     */         break;
/*     */ 
/*     */       
/*     */       case Lightning:
/* 235 */         glintTexture = Chams.instance.lightningGlint;
/*     */         break;
/*     */ 
/*     */       
/*     */       case Swirls:
/* 240 */         glintTexture = Chams.instance.swirlsGlint;
/*     */         break;
/*     */ 
/*     */       
/*     */       case Lines:
/* 245 */         glintTexture = Chams.instance.linesGlint;
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 250 */     if (glintTexture != null) Command.mc.func_110434_K().func_110577_a(glintTexture); 
/* 251 */     GL11.glEnable(3553);
/* 252 */     GL11.glEnable(3042);
/*     */ 
/*     */     
/* 255 */     float alpha = color.getAlpha() / 255.0F;
/* 256 */     GL11.glColor4f(color.getColorColor().getRed() / 255.0F * alpha * alphaCrowdFactor, color.getColorColor().getGreen() / 255.0F * alpha * alphaCrowdFactor, color.getColorColor().getBlue() / 255.0F * alpha * alphaCrowdFactor, 1.0F);
/*     */     
/* 258 */     GL11.glBlendFunc(768, 1);
/*     */     
/* 260 */     for (int i = 0; i < 2; i++) {
/* 261 */       GL11.glMatrixMode(5890);
/* 262 */       GL11.glLoadIdentity();
/* 263 */       GL11.glScalef(((Float)Chams.instance.crystalGlintScale.getValue()).floatValue(), ((Float)Chams.instance.crystalGlintScale.getValue()).floatValue(), ((Float)Chams.instance.crystalGlintScale.getValue()).floatValue());
/* 264 */       if (((Boolean)Chams.instance.crystalGlintMove.getValue()).booleanValue()) {
/* 265 */         GL11.glTranslatef(entityIn.field_70173_aa * 0.01F * ((Float)Chams.instance.crystalGlintMoveSpeed.getValue()).floatValue(), 0.0F, 0.0F);
/*     */       }
/* 267 */       GL11.glRotatef(30.0F - i * 60.0F, 0.0F, 0.0F, 1.0F);
/* 268 */       GL11.glMatrixMode(5888);
/*     */       
/* 270 */       this.field_188316_g.func_78088_a((Entity)entityIn, 0.0F, (entityIn.field_70261_a + partialTicks) * 3.0F * (((Boolean)Chams.instance.crystalSpinModify.getValue()).booleanValue() ? ((Float)Chams.instance.crystalSpinSpeed.getValue()).floatValue() : 1.0F), floatTicks * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */     } 
/*     */     
/* 273 */     GL11.glMatrixMode(5890);
/* 274 */     GL11.glLoadIdentity();
/* 275 */     GL11.glMatrixMode(5888);
/*     */     
/* 277 */     GL11.glBlendFunc(770, 771);
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinRenderEnderCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */