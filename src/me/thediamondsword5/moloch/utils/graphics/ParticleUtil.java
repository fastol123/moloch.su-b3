/*     */ package me.thediamondsword5.moloch.utils.graphics;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.module.modules.client.Particles;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.ColorUtil;
/*     */ import net.spartanb312.base.utils.MathUtilFuckYou;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.vector.Vector2f;
/*     */ import org.lwjgl.util.vector.Vector4f;
/*     */ 
/*     */ 
/*     */ public class ParticleUtil
/*     */ {
/*  26 */   private static final HashMap<Integer, Vector2f> particlesList = new HashMap<>();
/*  27 */   private static final HashMap<Integer, Vector2f> particlesSpeed = new HashMap<>();
/*  28 */   private static final HashMap<Integer, Float> particlesSize = new HashMap<>();
/*  29 */   private static final HashMap<Integer, Vector2f> particlesSpinSpeed = new HashMap<>();
/*  30 */   private static final HashMap<Integer, Float> particlesSpeedAlpha = new HashMap<>();
/*  31 */   private static final HashMap<Integer, Float> particlesSpeedFactor = new HashMap<>();
/*  32 */   private static final List<Vector2f> cornerList = new ArrayList<>();
/*  33 */   public static int particlesId = 0;
/*     */   public static boolean particlesClearedFlag = true;
/*  35 */   private static final Timer particlesTimer = new Timer();
/*  36 */   private static float alphaThreader = 0.0F;
/*     */ 
/*     */   
/*     */   public static void render() {
/*  40 */     particlesClearedFlag = false;
/*     */     
/*  42 */     Color particleColor = ((Color)Particles.INSTANCE.particleColor.getValue()).getColorColor();
/*     */     
/*  44 */     if (Particles.INSTANCE.particlesShape.getValue() == Particles.ParticlesShape.Circle) {
/*     */       
/*  46 */       GL11.glPushMatrix();
/*  47 */       GL11.glEnable(3042);
/*  48 */       GL11.glDisable(3553);
/*  49 */       GL11.glBlendFunc(770, 771);
/*  50 */       GL11.glDisable(2929);
/*  51 */       GL11.glDepthMask(false);
/*     */     } 
/*     */     
/*  54 */     GlStateManager.func_179118_c();
/*     */     
/*  56 */     if (Module.mc.field_71462_r == null) {
/*     */       return;
/*     */     }
/*  59 */     if (particlesId >= 2147479647) {
/*  60 */       particlesId = 0;
/*     */     }
/*  62 */     if (((Integer)Particles.INSTANCE.particleAmount.getValue()).intValue() - particlesList.size() > 0) {
/*  63 */       genParticles();
/*     */     }
/*  65 */     if (Particles.INSTANCE.particlesShape.getValue() == Particles.ParticlesShape.Circle) {
/*     */       
/*  67 */       GL11.glEnable(2832);
/*  68 */       GL11.glHint(3153, 4354);
/*     */     } 
/*     */     
/*  71 */     if (Particles.INSTANCE.particlesShape.getValue() != Particles.ParticlesShape.Circle) {
/*     */       
/*  73 */       GL11.glPushMatrix();
/*  74 */       GL11.glEnable(3042);
/*  75 */       GL11.glDisable(3553);
/*  76 */       GL11.glBlendFunc(770, 771);
/*  77 */       GL11.glDisable(2929);
/*  78 */       GL11.glDepthMask(false);
/*  79 */       GL11.glHint(3153, 4354);
/*     */     } 
/*     */     
/*  82 */     for (Map.Entry<Integer, Vector2f> entry : (new HashMap<>(particlesList)).entrySet()) {
/*     */       
/*  84 */       if (((Boolean)Particles.INSTANCE.mouseInteract.getValue()).booleanValue() && ((Boolean)Particles.INSTANCE.mouseInteractBounce.getValue()).booleanValue()) {
/*     */         
/*  86 */         Vector2f inVec = new Vector2f(((Vector2f)particlesSpeed.get(entry.getKey())).x, ((Vector2f)particlesSpeed.get(entry.getKey())).y);
/*  87 */         Vector2f normalVec = new Vector2f(((Vector2f)entry.getValue()).x - (Mouse.getEventX() * Module.mc.field_71462_r.field_146294_l / Module.mc.field_71443_c), ((Vector2f)entry.getValue()).y - ((Minecraft.func_71410_x()).field_71462_r.field_146295_m - Mouse.getEventY() * Module.mc.field_71462_r.field_146295_m / Module.mc.field_71440_d - 1));
/*     */         
/*  89 */         if (MathUtilFuckYou.dotProduct(inVec, normalVec) < 0.0F && getParticleDist(new Vector2f((Mouse.getEventX() * Module.mc.field_71462_r.field_146294_l / Module.mc.field_71443_c), ((Minecraft.func_71410_x()).field_71462_r.field_146295_m - Mouse.getEventY() * Module.mc.field_71462_r.field_146295_m / Module.mc.field_71440_d - 1)), entry.getValue()) < ((Float)Particles.INSTANCE.mouseInteractRange.getValue()).floatValue()) {
/*     */           
/*  91 */           Vector2f reflectVec = MathUtilFuckYou.reflectVector2f(inVec, normalVec);
/*  92 */           particlesSpeed.put(entry.getKey(), new Vector2f(reflectVec.x, reflectVec.y));
/*     */         } 
/*     */       } 
/*     */       
/*  96 */       if (((Boolean)Particles.INSTANCE.mouseInteract.getValue()).booleanValue() && ((Boolean)Particles.INSTANCE.mouseInterectPlowSpeedReduce.getValue()).booleanValue() && getParticleDist(new Vector2f((Mouse.getEventX() * Module.mc.field_71462_r.field_146294_l / Module.mc.field_71443_c), ((Minecraft.func_71410_x()).field_71462_r.field_146295_m - Mouse.getEventY() * Module.mc.field_71462_r.field_146295_m / Module.mc.field_71440_d - 1)), entry.getValue()) < ((Float)Particles.INSTANCE.mouseInteractRange.getValue()).floatValue())
/*     */       {
/*  98 */         particlesSpeedFactor.put(entry.getKey(), Particles.INSTANCE.mouseInteractPlowFractionOfSpeed.getValue());
/*     */       }
/*     */       
/* 101 */       drawLines(entry, 0.0033333334F * alphaThreader);
/*     */     } 
/*     */     
/* 104 */     if (Particles.INSTANCE.particlesShape.getValue() != Particles.ParticlesShape.Circle) {
/*     */       
/* 106 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 107 */       GL11.glDepthMask(true);
/* 108 */       GL11.glEnable(2884);
/* 109 */       GL11.glEnable(3553);
/* 110 */       GL11.glEnable(2929);
/* 111 */       GlStateManager.func_179141_d();
/* 112 */       GL11.glPopMatrix();
/*     */     } 
/*     */     
/* 115 */     for (Map.Entry<Integer, Vector2f> entry : (new HashMap<>(particlesList)).entrySet()) {
/*     */       
/* 117 */       float alpha = ((Color)Particles.INSTANCE.particleColor.getValue()).getAlpha() / 300.0F * alphaThreader;
/*     */       
/* 119 */       if (((Boolean)Particles.INSTANCE.particleRainbowRoll.getValue()).booleanValue() && !((Boolean)Particles.INSTANCE.particleRollColor.getValue()).booleanValue() && ((Color)Particles.INSTANCE.particleColor.getValue()).getRainbow())
/*     */       {
/* 121 */         particleColor = new Color(ColorUtil.rainbow((int)((Vector2f)entry.getValue()).x, ((Color)Particles.INSTANCE.particleColor.getValue()).getRainbowSpeed(), ((Float)Particles.INSTANCE.particleRainbowRollSize.getValue()).floatValue() / 10.0F, ((Color)Particles.INSTANCE.particleColor.getValue()).getRainbowSaturation(), ((Color)Particles.INSTANCE.particleColor.getValue()).getRainbowBrightness()));
/*     */       }
/*     */       
/* 124 */       if (((Boolean)Particles.INSTANCE.particleRollColor.getValue()).booleanValue()) {
/*     */         
/* 126 */         particleColor = ColorUtil.rolledColor(((Color)Particles.INSTANCE.particleRollColor1.getValue()).getColorColor(), ((Color)Particles.INSTANCE.particleRollColor2.getValue()).getColorColor(), (int)((Vector2f)entry.getValue()).x, ((Float)Particles.INSTANCE.particleRollColorSpeed.getValue()).floatValue(), ((Float)Particles.INSTANCE.particleRollColorSize.getValue()).floatValue() / 10.0F);
/* 127 */         alpha = MathUtilFuckYou.rolledLinearInterp((int)(((Color)Particles.INSTANCE.particleRollColor1.getValue()).getAlpha() / 300.0F * alphaThreader), (int)(((Color)Particles.INSTANCE.particleRollColor2.getValue()).getAlpha() / 300.0F * alphaThreader), (int)((Vector2f)entry.getValue()).x, ((Float)Particles.INSTANCE.particleRollColorSpeed.getValue()).floatValue(), ((Float)Particles.INSTANCE.particleRollColorSize.getValue()).floatValue() / 10.0F);
/*     */       } 
/*     */       
/* 130 */       if (((Boolean)Particles.INSTANCE.particleSpeedAlpha.getValue()).booleanValue())
/*     */       {
/* 132 */         alpha *= ((Float)particlesSpeedAlpha.get(entry.getKey())).floatValue();
/*     */       }
/*     */       
/* 135 */       if (Particles.INSTANCE.particlesShape.getValue() == Particles.ParticlesShape.Circle) {
/*     */         
/* 137 */         GL11.glColor4f(particleColor.getRed() / 255.0F, particleColor.getGreen() / 255.0F, particleColor.getBlue() / 255.0F, alpha / 255.0F);
/* 138 */         GL11.glPointSize(((Float)particlesSize.get(entry.getKey())).floatValue() * 3.0F);
/* 139 */         GL11.glBegin(0);
/* 140 */         GL11.glVertex2f(((Vector2f)entry.getValue()).x, ((Vector2f)entry.getValue()).y);
/* 141 */         GL11.glEnd();
/*     */       }
/*     */       else {
/*     */         
/* 145 */         RenderUtils2D.prepareGl();
/* 146 */         GL11.glTranslatef(((Vector2f)entry.getValue()).x, ((Vector2f)entry.getValue()).y, 0.0F);
/* 147 */         GL11.glRotatef(((Vector2f)particlesSpinSpeed.get(entry.getKey())).y, 0.0F, 0.0F, 1.0F);
/* 148 */         GL11.glTranslatef(((Vector2f)entry.getValue()).x * -1.0F, ((Vector2f)entry.getValue()).y * -1.0F, 0.0F);
/*     */         
/* 150 */         if (Particles.INSTANCE.particlesShape.getValue() == Particles.ParticlesShape.Triangle) {
/* 151 */           RenderUtils2D.drawEquilateralTriangle(((Vector2f)entry.getValue()).x, ((Vector2f)entry.getValue()).y, false, ((Float)particlesSize.get(entry.getKey())).floatValue(), (new Color(particleColor.getRed(), particleColor.getGreen(), particleColor.getBlue(), (int)alpha)).getRGB());
/*     */         }
/* 153 */         if (Particles.INSTANCE.particlesShape.getValue() == Particles.ParticlesShape.Square) {
/* 154 */           RenderUtils2D.drawRhombus(((Vector2f)entry.getValue()).x, ((Vector2f)entry.getValue()).y, ((Float)particlesSize.get(entry.getKey())).floatValue(), (new Color(particleColor.getRed(), particleColor.getGreen(), particleColor.getBlue(), (int)alpha)).getRGB());
/*     */         }
/* 156 */         GL11.glTranslatef(((Vector2f)entry.getValue()).x, ((Vector2f)entry.getValue()).y, 0.0F);
/* 157 */         GL11.glRotatef(((Vector2f)particlesSpinSpeed.get(entry.getKey())).y * -1.0F, 0.0F, 0.0F, 1.0F);
/* 158 */         GL11.glTranslatef(((Vector2f)entry.getValue()).x * -1.0F, ((Vector2f)entry.getValue()).y * -1.0F, 0.0F);
/* 159 */         RenderUtils2D.releaseGl();
/*     */       } 
/*     */       
/* 162 */       if (((Vector2f)entry.getValue()).x < 0.0F || ((Vector2f)entry.getValue()).x > Module.mc.field_71462_r.field_146294_l || ((Vector2f)entry.getValue()).y < 0.0F || ((Vector2f)entry.getValue()).y > Module.mc.field_71462_r.field_146295_m) {
/*     */         
/* 164 */         particlesList.remove(entry.getKey());
/* 165 */         particlesSpeed.remove(entry.getKey());
/* 166 */         particlesSize.remove(entry.getKey());
/* 167 */         particlesSpinSpeed.remove(entry.getKey());
/* 168 */         particlesSpeedAlpha.remove(entry.getKey());
/* 169 */         particlesSpeedFactor.remove(entry.getKey());
/*     */       } 
/*     */     } 
/*     */     
/* 173 */     if (Particles.INSTANCE.particlesShape.getValue() == Particles.ParticlesShape.Circle) {
/* 174 */       GL11.glDisable(2832);
/*     */     }
/*     */     
/* 177 */     int passedms = (int)particlesTimer.hasPassed();
/* 178 */     if (passedms < 1000)
/*     */     {
/* 180 */       for (int i = 0; i <= passedms; i++) {
/*     */         
/* 182 */         alphaThreader += ((Float)Particles.INSTANCE.fadeInSpeed.getValue()).floatValue() / 10.0F;
/*     */         
/* 184 */         if (alphaThreader >= 300.0F) {
/* 185 */           alphaThreader = 300.0F;
/*     */         }
/* 187 */         for (Map.Entry<Integer, Vector2f> entry : (new HashMap<>(particlesList)).entrySet()) {
/*     */           
/* 189 */           ((Vector2f)particlesSpinSpeed.get(entry.getKey())).y += ((Vector2f)particlesSpinSpeed.get(entry.getKey())).x / 10.0F;
/*     */           
/* 191 */           if (((Boolean)Particles.INSTANCE.mouseInteract.getValue()).booleanValue()) {
/*     */             
/* 193 */             if (getParticleDist(new Vector2f((Mouse.getEventX() * Module.mc.field_71462_r.field_146294_l / Module.mc.field_71443_c), ((Minecraft.func_71410_x()).field_71462_r.field_146295_m - Mouse.getEventY() * Module.mc.field_71462_r.field_146295_m / Module.mc.field_71440_d - 1)), entry.getValue()) < ((Float)Particles.INSTANCE.mouseInteractRange.getValue()).floatValue()) {
/*     */               
/* 195 */               ((Vector2f)entry.getValue()).x += ((Float)Particles.INSTANCE.mouseInteractPlowStrength.getValue()).floatValue() / 10.0F * ((((Vector2f)entry.getValue()).x - (Mouse.getEventX() * Module.mc.field_71462_r.field_146294_l / Module.mc.field_71443_c) < 0.0F) ? -1.0F : 1.0F);
/* 196 */               ((Vector2f)entry.getValue()).y += ((Float)Particles.INSTANCE.mouseInteractPlowStrength.getValue()).floatValue() / 10.0F * ((((Vector2f)entry.getValue()).y - ((Minecraft.func_71410_x()).field_71462_r.field_146295_m - Mouse.getEventY() * Module.mc.field_71462_r.field_146295_m / Module.mc.field_71440_d - 1) < 0.0F) ? -1.0F : 1.0F);
/*     */             } 
/*     */             
/* 199 */             if (((Boolean)Particles.INSTANCE.mouseInterectPlowSpeedReduce.getValue()).booleanValue()) {
/*     */               
/* 201 */               float speedFactor = ((Float)particlesSpeedFactor.get(entry.getKey())).floatValue();
/* 202 */               ((Vector2f)entry.getValue()).x += ((Vector2f)particlesSpeed.get(entry.getKey())).x * speedFactor / 10.0F;
/* 203 */               ((Vector2f)entry.getValue()).y += ((Vector2f)particlesSpeed.get(entry.getKey())).y * speedFactor / 10.0F;
/* 204 */               speedFactor += 0.001F * ((Float)Particles.INSTANCE.mouseInteractPlowSpeedRegenFactor.getValue()).floatValue();
/*     */               
/* 206 */               if (speedFactor >= 1.0F)
/*     */               {
/* 208 */                 speedFactor = 1.0F;
/*     */               }
/*     */               
/* 211 */               particlesSpeedFactor.put(entry.getKey(), Float.valueOf(speedFactor));
/*     */               
/*     */               continue;
/*     */             } 
/* 215 */             ((Vector2f)entry.getValue()).x += ((Vector2f)particlesSpeed.get(entry.getKey())).x / 10.0F;
/* 216 */             ((Vector2f)entry.getValue()).y += ((Vector2f)particlesSpeed.get(entry.getKey())).y / 10.0F;
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 221 */           ((Vector2f)entry.getValue()).x += ((Vector2f)particlesSpeed.get(entry.getKey())).x / 10.0F;
/* 222 */           ((Vector2f)entry.getValue()).y += ((Vector2f)particlesSpeed.get(entry.getKey())).y / 10.0F;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 227 */     particlesTimer.reset();
/*     */     
/* 229 */     if (Particles.INSTANCE.particlesShape.getValue() == Particles.ParticlesShape.Circle) {
/*     */       
/* 231 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 232 */       GL11.glDepthMask(true);
/* 233 */       GL11.glDisable(2884);
/* 234 */       GL11.glDisable(3553);
/* 235 */       GL11.glDisable(2929);
/* 236 */       GlStateManager.func_179141_d();
/* 237 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void genParticles() {
/* 243 */     if (Module.mc.field_71462_r != null)
/*     */     {
/* 245 */       for (int i = 0; i < ((Integer)Particles.INSTANCE.particleAmount.getValue()).intValue() - particlesList.size(); i++) {
/*     */         float size;
/* 247 */         Vector2f spawnPoint = new Vector2f(0.0F, 0.0F);
/* 248 */         Vector4f speed = new Vector4f(0.0F, 0.0F, 0.0F, 0.0F);
/*     */         
/* 250 */         float alpha = 1.0F;
/*     */         
/* 252 */         float spinSpeed = (float)(Math.random() * ((Float)Particles.INSTANCE.maxParticleSpinSpeed.getValue()).floatValue());
/* 253 */         if (spinSpeed <= ((Float)Particles.INSTANCE.minParticleSpinSpeed.getValue()).floatValue())
/*     */         {
/* 255 */           spinSpeed = ((Float)Particles.INSTANCE.minParticleSpinSpeed.getValue()).floatValue();
/*     */         }
/*     */         
/* 258 */         speed.x = (float)(Math.random() * ((Float)Particles.INSTANCE.maxParticleSpeed.getValue()).floatValue());
/* 259 */         speed.y = (float)(Math.random() * ((Float)Particles.INSTANCE.maxParticleSpeed.getValue()).floatValue());
/* 260 */         if (speed.x <= ((Float)Particles.INSTANCE.minParticleSpeed.getValue()).floatValue())
/*     */         {
/* 262 */           speed.x = ((Float)Particles.INSTANCE.minParticleSpeed.getValue()).floatValue();
/*     */         }
/*     */         
/* 265 */         if (speed.y <= ((Float)Particles.INSTANCE.minParticleSpeed.getValue()).floatValue())
/*     */         {
/* 267 */           speed.y = ((Float)Particles.INSTANCE.minParticleSpeed.getValue()).floatValue();
/*     */         }
/*     */         
/* 270 */         if (Particles.INSTANCE.particlesSpawnMode.getValue() == Particles.ParticlesSpawnMode.Sides) {
/*     */           
/* 272 */           switch ((Particles.ParticlesSpawnSideMode)Particles.INSTANCE.particlesSpawnSideMode.getValue()) {
/*     */ 
/*     */             
/*     */             case Horizontal:
/* 276 */               speed.w = coinFlip() ? -1.0F : 1.0F;
/* 277 */               if (coinFlip()) {
/*     */                 
/* 279 */                 spawnPoint = new Vector2f(0.0F, (float)(Math.random() * Module.mc.field_71462_r.field_146295_m));
/* 280 */                 speed.z = 1.0F;
/*     */                 
/*     */                 break;
/*     */               } 
/* 284 */               spawnPoint = new Vector2f(Module.mc.field_71462_r.field_146294_l, (float)(Math.random() * Module.mc.field_71462_r.field_146295_m));
/* 285 */               speed.z = -1.0F;
/*     */               break;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             case Vertical:
/* 292 */               speed.z = coinFlip() ? -1.0F : 1.0F;
/* 293 */               if (coinFlip()) {
/*     */                 
/* 295 */                 spawnPoint = new Vector2f((float)(Math.random() * Module.mc.field_71462_r.field_146294_l), 0.0F);
/* 296 */                 speed.w = 1.0F;
/*     */                 
/*     */                 break;
/*     */               } 
/* 300 */               spawnPoint = new Vector2f((float)(Math.random() * Module.mc.field_71462_r.field_146294_l), Module.mc.field_71462_r.field_146295_m);
/* 301 */               speed.w = -1.0F;
/*     */               break;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             case Both:
/* 308 */               if (coinFlip()) {
/*     */                 
/* 310 */                 speed.w = coinFlip() ? -1.0F : 1.0F;
/* 311 */                 if (coinFlip()) {
/*     */                   
/* 313 */                   spawnPoint = new Vector2f(0.0F, (float)(Math.random() * Module.mc.field_71462_r.field_146295_m));
/* 314 */                   speed.z = 1.0F;
/*     */                   
/*     */                   break;
/*     */                 } 
/* 318 */                 spawnPoint = new Vector2f(Module.mc.field_71462_r.field_146294_l, (float)(Math.random() * Module.mc.field_71462_r.field_146295_m));
/* 319 */                 speed.z = -1.0F;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */               
/* 324 */               speed.z = coinFlip() ? -1.0F : 1.0F;
/* 325 */               if (coinFlip()) {
/*     */                 
/* 327 */                 spawnPoint = new Vector2f((float)(Math.random() * Module.mc.field_71462_r.field_146294_l), 0.0F);
/* 328 */                 speed.w = 1.0F;
/*     */                 
/*     */                 break;
/*     */               } 
/* 332 */               spawnPoint = new Vector2f((float)(Math.random() * Module.mc.field_71462_r.field_146294_l), Module.mc.field_71462_r.field_146295_m);
/* 333 */               speed.w = -1.0F;
/*     */               break;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         } else {
/* 343 */           if (((Boolean)Particles.INSTANCE.particlesSpawnUpLeftCorner.getValue()).booleanValue()) {
/* 344 */             cornerList.add(new Vector2f(0.0F, 0.0F));
/*     */           }
/* 346 */           if (((Boolean)Particles.INSTANCE.particlesSpawnDownLeftCorner.getValue()).booleanValue()) {
/* 347 */             cornerList.add(new Vector2f(0.0F, Module.mc.field_71462_r.field_146295_m));
/*     */           }
/* 349 */           if (((Boolean)Particles.INSTANCE.particlesSpawnUpRightCorner.getValue()).booleanValue()) {
/* 350 */             cornerList.add(new Vector2f(Module.mc.field_71462_r.field_146294_l, 0.0F));
/*     */           }
/* 352 */           if (((Boolean)Particles.INSTANCE.particlesSpawnDownRightCorner.getValue()).booleanValue()) {
/* 353 */             cornerList.add(new Vector2f(Module.mc.field_71462_r.field_146294_l, Module.mc.field_71462_r.field_146295_m));
/*     */           }
/* 355 */           spawnPoint = cornerList.get((int)(Math.random() * cornerList.size()));
/*     */           
/* 357 */           if (Objects.equals(spawnPoint, new Vector2f(0.0F, 0.0F))) {
/*     */             
/* 359 */             speed.z = 1.0F;
/* 360 */             speed.w = 1.0F;
/*     */           } 
/*     */           
/* 363 */           if (Objects.equals(spawnPoint, new Vector2f(0.0F, Module.mc.field_71462_r.field_146295_m))) {
/*     */             
/* 365 */             speed.z = 1.0F;
/* 366 */             speed.w = -1.0F;
/*     */           } 
/*     */           
/* 369 */           if (Objects.equals(spawnPoint, new Vector2f(Module.mc.field_71462_r.field_146294_l, 0.0F))) {
/*     */             
/* 371 */             speed.z = -1.0F;
/* 372 */             speed.w = 1.0F;
/*     */           } 
/*     */           
/* 375 */           if (Objects.equals(spawnPoint, new Vector2f(Module.mc.field_71462_r.field_146294_l, Module.mc.field_71462_r.field_146295_m))) {
/*     */             
/* 377 */             speed.z = -1.0F;
/* 378 */             speed.w = -1.0F;
/*     */           } 
/*     */           
/* 381 */           cornerList.clear();
/*     */         } 
/*     */ 
/*     */         
/* 385 */         if (((Boolean)Particles.INSTANCE.randomParticleSize.getValue()).booleanValue()) {
/*     */           
/* 387 */           size = (float)(Math.random() * ((Float)Particles.INSTANCE.maxParticleSize.getValue()).floatValue());
/* 388 */           if (size <= ((Float)Particles.INSTANCE.minParticleSize.getValue()).floatValue())
/*     */           {
/* 390 */             size = ((Float)Particles.INSTANCE.minParticleSize.getValue()).floatValue();
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 395 */           size = ((Float)Particles.INSTANCE.particleSize.getValue()).floatValue();
/*     */         } 
/*     */         
/* 398 */         if (((Boolean)Particles.INSTANCE.particleSpeedAlpha.getValue()).booleanValue()) {
/*     */           
/* 400 */           alpha = (float)(alpha * (Math.sqrt((speed.x * speed.x + speed.y * speed.y)) - Math.sqrt((((Float)Particles.INSTANCE.minParticleSpeed.getValue()).floatValue() * ((Float)Particles.INSTANCE.minParticleSpeed.getValue()).floatValue() * 2.0F))) / (Math.sqrt((((Float)Particles.INSTANCE.maxParticleSpeed.getValue()).floatValue() * ((Float)Particles.INSTANCE.maxParticleSpeed.getValue()).floatValue() * 2.0F)) - Math.sqrt((((Float)Particles.INSTANCE.minParticleSpeed.getValue()).floatValue() * ((Float)Particles.INSTANCE.minParticleSpeed.getValue()).floatValue() * 2.0F))));
/*     */           
/* 402 */           if (alpha >= 0.5F) {
/*     */             
/* 404 */             alpha *= ((Float)Particles.INSTANCE.particlesSpeedAlphaFactor.getValue()).floatValue();
/*     */           }
/*     */           else {
/*     */             
/* 408 */             alpha /= ((Float)Particles.INSTANCE.particlesSpeedAlphaFactor.getValue()).floatValue();
/*     */           } 
/*     */           
/* 411 */           if (alpha >= 1.0F) {
/* 412 */             alpha = 1.0F;
/*     */           }
/*     */         } 
/* 415 */         particlesList.put(Integer.valueOf(particlesId), spawnPoint);
/* 416 */         particlesSpeed.put(Integer.valueOf(particlesId), new Vector2f(speed.x * speed.z, speed.y * speed.w));
/* 417 */         particlesSize.put(Integer.valueOf(particlesId), Float.valueOf(size));
/* 418 */         particlesSpinSpeed.put(Integer.valueOf(particlesId), new Vector2f(spinSpeed, (float)(Math.random() * 360.0D)));
/* 419 */         particlesSpeedAlpha.put(Integer.valueOf(particlesId), Float.valueOf(alpha));
/* 420 */         particlesSpeedFactor.put(Integer.valueOf(particlesId), Float.valueOf(1.0F));
/*     */         
/* 422 */         particlesId++;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void drawLines(Map.Entry<Integer, Vector2f> particle, float alphaFactor) {
/* 429 */     Color lineColor = ((Color)Particles.INSTANCE.lineColor.getValue()).getColorColor();
/*     */ 
/*     */     
/* 432 */     GL11.glEnable(2848);
/* 433 */     GL11.glHint(3154, 4354);
/* 434 */     GL11.glLineWidth(((Float)Particles.INSTANCE.linesWidth.getValue()).floatValue());
/* 435 */     for (Map.Entry<Integer, Vector2f> entry : (new HashMap<>(particlesList)).entrySet()) {
/*     */       
/* 437 */       int alpha = ((Color)Particles.INSTANCE.lineColor.getValue()).getAlpha();
/*     */       
/* 439 */       if (Objects.equals(entry.getKey(), particle.getKey())) {
/*     */         continue;
/*     */       }
/* 442 */       if (((Boolean)Particles.INSTANCE.restrictToAroundMouseLines.getValue()).booleanValue() && getParticleDist(new Vector2f((Mouse.getEventX() * Module.mc.field_71462_r.field_146294_l / Module.mc.field_71443_c), ((Minecraft.func_71410_x()).field_71462_r.field_146295_m - Mouse.getEventY() * Module.mc.field_71462_r.field_146295_m / Module.mc.field_71440_d - 1)), entry.getValue()) > ((Float)Particles.INSTANCE.restrictToAroundMouseLinesRange.getValue()).floatValue()) {
/*     */         continue;
/*     */       }
/* 445 */       if (getParticleDist(particle.getValue(), entry.getValue()) <= ((Integer)Particles.INSTANCE.connectRange.getValue()).intValue()) {
/*     */         
/* 447 */         if (((Boolean)Particles.INSTANCE.lineRainbowRoll.getValue()).booleanValue() && !((Boolean)Particles.INSTANCE.lineRollColor.getValue()).booleanValue() && ((Color)Particles.INSTANCE.lineColor.getValue()).getRainbow())
/*     */         {
/* 449 */           lineColor = new Color(ColorUtil.rainbow((int)((Vector2f)entry.getValue()).x, ((Color)Particles.INSTANCE.lineColor.getValue()).getRainbowSpeed(), ((Float)Particles.INSTANCE.lineRainbowRollSize.getValue()).floatValue() / 10.0F, ((Color)Particles.INSTANCE.lineColor.getValue()).getRainbowSaturation(), ((Color)Particles.INSTANCE.lineColor.getValue()).getRainbowBrightness()));
/*     */         }
/*     */         
/* 452 */         if (((Boolean)Particles.INSTANCE.lineRollColor.getValue()).booleanValue()) {
/*     */           
/* 454 */           lineColor = ColorUtil.rolledColor(((Color)Particles.INSTANCE.lineRollColor1.getValue()).getColorColor(), ((Color)Particles.INSTANCE.lineRollColor2.getValue()).getColorColor(), (int)((Vector2f)entry.getValue()).x, ((Float)Particles.INSTANCE.lineRollColorSpeed.getValue()).floatValue(), ((Float)Particles.INSTANCE.lineRollColorSize.getValue()).floatValue() / 10.0F);
/* 455 */           alpha = (int)MathUtilFuckYou.rolledLinearInterp(((Color)Particles.INSTANCE.lineRollColor1.getValue()).getAlpha(), ((Color)Particles.INSTANCE.lineRollColor2.getValue()).getAlpha(), (int)((Vector2f)entry.getValue()).x, ((Float)Particles.INSTANCE.lineRollColorSpeed.getValue()).floatValue(), ((Float)Particles.INSTANCE.lineRollColorSize.getValue()).floatValue() / 10.0F);
/*     */         } 
/*     */         
/* 458 */         GL11.glColor4f(lineColor.getRed() / 255.0F, lineColor.getGreen() / 255.0F, lineColor.getBlue() / 255.0F, alpha / 255.0F * alphaFactor * (((Boolean)Particles.INSTANCE.linesFadeIn.getValue()).booleanValue() ? ((((Integer)Particles.INSTANCE.connectRange.getValue()).intValue() - getParticleDist(particle.getValue(), entry.getValue())) / ((Integer)Particles.INSTANCE.connectRange.getValue()).intValue() / ((Float)Particles.INSTANCE.linesFadeInFactor.getValue()).floatValue()) : 1.0F));
/*     */         
/* 460 */         if (((Boolean)Particles.INSTANCE.onlyConnectOne.getValue()).booleanValue()) {
/*     */           
/* 462 */           GL11.glBegin(1);
/* 463 */           GL11.glVertex2f(((Vector2f)particle.getValue()).x, ((Vector2f)particle.getValue()).y);
/* 464 */           GL11.glVertex2f(((Vector2f)entry.getValue()).x, ((Vector2f)entry.getValue()).y);
/* 465 */           GL11.glEnd();
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 470 */         GL11.glBegin(1);
/* 471 */         GL11.glVertex2f(((Vector2f)particle.getValue()).x, ((Vector2f)particle.getValue()).y);
/* 472 */         GL11.glVertex2f(((Vector2f)entry.getValue()).x, ((Vector2f)entry.getValue()).y);
/* 473 */         GL11.glEnd();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 478 */     GL11.glDisable(2848);
/*     */   }
/*     */ 
/*     */   
/*     */   private static float getParticleDist(Vector2f particle1, Vector2f particle2) {
/* 483 */     float x = particle1.x - particle2.x;
/* 484 */     float y = particle1.y - particle2.y;
/* 485 */     return (float)Math.sqrt((x * x + y * y));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clearParticles() {
/* 490 */     particlesList.clear();
/* 491 */     particlesSpeed.clear();
/* 492 */     particlesSize.clear();
/* 493 */     particlesSpinSpeed.clear();
/* 494 */     particlesSpeedAlpha.clear();
/* 495 */     particlesSpeedFactor.clear();
/* 496 */     particlesId = 0;
/* 497 */     alphaThreader = 0.0F;
/* 498 */     particlesClearedFlag = true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean coinFlip() {
/* 503 */     return (Math.random() > 0.5D);
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloc\\utils\graphics\ParticleUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */