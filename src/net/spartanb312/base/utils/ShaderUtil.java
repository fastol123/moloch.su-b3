/*    */ package net.spartanb312.base.utils;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.client.shader.Framebuffer;
/*    */ import org.lwjgl.opengl.Display;
/*    */ 
/*    */ 
/*    */ public class ShaderUtil
/*    */ {
/*    */   private static int lastScale;
/*    */   private static int lastScaleWidth;
/*    */   private static int lastScaleHeight;
/*    */   public static ShaderUtil instance;
/*    */   
/*    */   public ShaderUtil() {
/* 17 */     instance = this;
/*    */   }
/*    */   
/*    */   public Framebuffer setupFrameBuffer(Framebuffer frameBuffer) {
/* 21 */     if (Display.isActive() || Display.isVisible()) {
/*    */       
/* 23 */       if (frameBuffer != null)
/*    */       {
/* 25 */         frameBuffer.func_147614_f();
/* 26 */         ScaledResolution scale = new ScaledResolution(Minecraft.func_71410_x());
/* 27 */         int factor = scale.func_78325_e();
/* 28 */         int factor2 = scale.func_78326_a();
/* 29 */         int factor3 = scale.func_78328_b();
/* 30 */         if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3) {
/* 31 */           frameBuffer.func_147608_a();
/* 32 */           frameBuffer = new Framebuffer(ItemUtils.mc.field_71443_c, ItemUtils.mc.field_71440_d, true);
/* 33 */           frameBuffer.func_147614_f();
/*    */         } 
/* 35 */         lastScale = factor;
/* 36 */         lastScaleWidth = factor2;
/* 37 */         lastScaleHeight = factor3;
/*    */       }
/*    */       else
/*    */       {
/* 41 */         frameBuffer = new Framebuffer(ItemUtils.mc.field_71443_c, ItemUtils.mc.field_71440_d, true);
/*    */       
/*    */       }
/*    */     
/*    */     }
/* 46 */     else if (frameBuffer == null) {
/*    */       
/* 48 */       frameBuffer = new Framebuffer(ItemUtils.mc.field_71443_c, ItemUtils.mc.field_71440_d, true);
/*    */     } 
/*    */ 
/*    */     
/* 52 */     return frameBuffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\ShaderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */