/*     */ package me.thediamondsword5.moloch.utils.graphics.shaders;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import me.thediamondsword5.moloch.mixinotherstuff.IEntityRenderer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public abstract class FramebufferShader
/*     */   extends Shader
/*     */ {
/*  16 */   public Minecraft mc = Minecraft.func_71410_x();
/*     */   public static Framebuffer frameBuffer;
/*     */   public boolean shadow;
/*     */   public float red;
/*     */   public float green;
/*     */   public float blue;
/*     */   public float alpha;
/*  23 */   public float radius = 2.0F;
/*  24 */   public float quality = 1.0F;
/*     */   public boolean entityShadows;
/*     */   private static int lastScale;
/*     */   private static int lastScaleWidth;
/*     */   private static int lastScaleHeight;
/*     */   
/*     */   public FramebufferShader(String fragmentShader, String vertextShader) {
/*  31 */     super(fragmentShader, vertextShader);
/*     */   }
/*     */   
/*     */   public void startDraw(float partialTicks, boolean singleFboMode) {
/*  35 */     GlStateManager.func_179141_d();
/*  36 */     GlStateManager.func_179094_E();
/*  37 */     GlStateManager.func_179123_a();
/*     */     
/*  39 */     frameBuffer = setupFrameBuffer(frameBuffer, singleFboMode);
/*  40 */     frameBuffer.func_147610_a(true);
/*  41 */     this.entityShadows = this.mc.field_71474_y.field_181151_V;
/*  42 */     this.mc.field_71474_y.field_181151_V = false;
/*  43 */     ((IEntityRenderer)this.mc.field_71460_t).invokeSetupCameraTransform(partialTicks, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopDraw(Color color, int alpha, float radius, float quality) {
/*  48 */     this.mc.field_71474_y.field_181151_V = this.entityShadows;
/*  49 */     GL11.glEnable(3042);
/*  50 */     GL11.glBlendFunc(770, 771);
/*  51 */     this.mc.func_147110_a().func_147610_a(true);
/*     */     
/*  53 */     this.red = color.getRed() / 255.0F;
/*  54 */     this.green = color.getGreen() / 255.0F;
/*  55 */     this.blue = color.getBlue() / 255.0F;
/*  56 */     this.alpha = alpha / 255.0F;
/*  57 */     this.radius = radius;
/*  58 */     this.quality = quality;
/*     */     
/*  60 */     this.mc.field_71460_t.func_175072_h();
/*  61 */     RenderHelper.func_74518_a();
/*     */     
/*  63 */     startShader();
/*  64 */     this.mc.field_71460_t.func_78478_c();
/*  65 */     drawFramebuffer(frameBuffer);
/*  66 */     stopShader();
/*     */     
/*  68 */     this.mc.field_71460_t.func_175072_h();
/*     */     
/*  70 */     GlStateManager.func_179121_F();
/*  71 */     GlStateManager.func_179099_b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Framebuffer setupFrameBuffer(Framebuffer frameBuffer, boolean singleFboMode) {
/*  79 */     if (Display.isActive() || Display.isVisible()) {
/*     */       
/*  81 */       if (frameBuffer != null) {
/*     */         
/*  83 */         if (singleFboMode) {
/*  84 */           frameBuffer.func_147614_f();
/*  85 */           ScaledResolution scale = new ScaledResolution(Minecraft.func_71410_x());
/*  86 */           int factor = scale.func_78325_e();
/*  87 */           int factor2 = scale.func_78326_a();
/*  88 */           int factor3 = scale.func_78328_b();
/*  89 */           if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3) {
/*  90 */             frameBuffer.func_147608_a();
/*  91 */             frameBuffer = new Framebuffer(this.mc.field_71443_c, this.mc.field_71440_d, true);
/*  92 */             frameBuffer.func_147614_f();
/*     */           } 
/*  94 */           lastScale = factor;
/*  95 */           lastScaleWidth = factor2;
/*  96 */           lastScaleHeight = factor3;
/*     */         } else {
/*     */           
/*  99 */           frameBuffer.func_147608_a();
/* 100 */           frameBuffer = new Framebuffer(this.mc.field_71443_c, this.mc.field_71440_d, true);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 105 */         frameBuffer = new Framebuffer(this.mc.field_71443_c, this.mc.field_71440_d, true);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 110 */     else if (frameBuffer == null) {
/*     */       
/* 112 */       frameBuffer = new Framebuffer(this.mc.field_71443_c, this.mc.field_71440_d, true);
/*     */     } 
/*     */ 
/*     */     
/* 116 */     return frameBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawFramebuffer(Framebuffer framebuffer) {
/* 125 */     ScaledResolution scaledResolution = new ScaledResolution(this.mc);
/* 126 */     GL11.glBindTexture(3553, framebuffer.field_147617_g);
/* 127 */     GL11.glBegin(7);
/* 128 */     GL11.glTexCoord2d(0.0D, 1.0D);
/* 129 */     GL11.glVertex2d(0.0D, 0.0D);
/* 130 */     GL11.glTexCoord2d(0.0D, 0.0D);
/* 131 */     GL11.glVertex2d(0.0D, scaledResolution.func_78328_b());
/* 132 */     GL11.glTexCoord2d(1.0D, 0.0D);
/* 133 */     GL11.glVertex2d(scaledResolution.func_78326_a(), scaledResolution.func_78328_b());
/* 134 */     GL11.glTexCoord2d(1.0D, 1.0D);
/* 135 */     GL11.glVertex2d(scaledResolution.func_78326_a(), 0.0D);
/* 136 */     GL11.glEnd();
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloc\\utils\graphics\shaders\FramebufferShader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */