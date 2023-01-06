/*    */ package me.thediamondsword5.moloch.utils.graphics.shaders.sexy;
/*    */ 
/*    */ import me.thediamondsword5.moloch.utils.graphics.shaders.FramebufferShader;
/*    */ import org.lwjgl.opengl.GL20;
/*    */ 
/*    */ public class Outline extends FramebufferShader {
/*  7 */   public static final Outline SHADER_OUTLINE = new Outline();
/*    */   
/*    */   public Outline() {
/* 10 */     super("outline.fsh", "vertex.vsh");
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupUniforms() {
/* 15 */     setupUniform("texture");
/* 16 */     setupUniform("texelSize");
/* 17 */     setupUniform("color");
/* 18 */     setupUniform("alpha");
/* 19 */     setupUniform("radius");
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateUniforms() {
/* 24 */     GL20.glUniform1i(getUniform("texture"), 0);
/* 25 */     GL20.glUniform2f(getUniform("texelSize"), 1.0F / this.mc.field_71443_c * this.radius * this.quality, 1.0F / this.mc.field_71440_d * this.radius * this.quality);
/* 26 */     GL20.glUniform4f(getUniform("color"), this.red, this.green, this.blue, this.alpha);
/* 27 */     GL20.glUniform1f(getUniform("alpha"), this.alpha);
/* 28 */     GL20.glUniform1f(getUniform("radius"), this.radius);
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloc\\utils\graphics\shaders\sexy\Outline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */