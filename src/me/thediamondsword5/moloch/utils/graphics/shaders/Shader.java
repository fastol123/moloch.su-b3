/*     */ package me.thediamondsword5.moloch.utils.graphics.shaders;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.lwjgl.opengl.ARBShaderObjects;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ public abstract class Shader
/*     */ {
/*     */   private int program;
/*     */   private Map<String, Integer> uniformsMap;
/*     */   
/*     */   public Shader(String fragmentShader, String vertexShader) {
/*     */     int fragmentShaderID, vertexShaderID;
/*     */     try {
/*  20 */       InputStream vertexStream = getClass().getResourceAsStream("/assets/moloch/shaders/" + vertexShader);
/*  21 */       vertexShaderID = createShader(IOUtils.toString(vertexStream, Charset.defaultCharset()), 35633);
/*  22 */       IOUtils.closeQuietly(vertexStream);
/*     */       
/*  24 */       InputStream fragmentStream = getClass().getResourceAsStream("/assets/moloch/shaders/" + fragmentShader);
/*  25 */       fragmentShaderID = createShader(IOUtils.toString(fragmentStream, Charset.defaultCharset()), 35632);
/*  26 */       IOUtils.closeQuietly(fragmentStream);
/*     */     }
/*  28 */     catch (Exception e) {
/*  29 */       e.printStackTrace();
/*     */       return;
/*     */     } 
/*  32 */     if (vertexShaderID == 0 || fragmentShaderID == 0)
/*  33 */       return;  this.program = ARBShaderObjects.glCreateProgramObjectARB();
/*  34 */     if (this.program == 0)
/*     */       return; 
/*  36 */     ARBShaderObjects.glAttachObjectARB(this.program, vertexShaderID);
/*  37 */     ARBShaderObjects.glAttachObjectARB(this.program, fragmentShaderID);
/*  38 */     ARBShaderObjects.glLinkProgramARB(this.program);
/*  39 */     ARBShaderObjects.glValidateProgramARB(this.program);
/*     */   }
/*     */   
/*     */   public void startShader() {
/*  43 */     GL11.glPushMatrix();
/*  44 */     GL20.glUseProgram(this.program);
/*  45 */     if (this.uniformsMap == null) {
/*  46 */       this.uniformsMap = new HashMap<>();
/*  47 */       setupUniforms();
/*     */     } 
/*  49 */     updateUniforms();
/*     */   }
/*     */   
/*     */   public void stopShader() {
/*  53 */     GL20.glUseProgram(0);
/*  54 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public abstract void setupUniforms();
/*     */   
/*     */   public abstract void updateUniforms();
/*     */   
/*     */   private int createShader(String shaderSource, int shaderType) {
/*  62 */     int shader = 0;
/*     */     
/*     */     try {
/*  65 */       shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
/*  66 */       if (shader == 0) return 0;
/*     */       
/*  68 */       ARBShaderObjects.glShaderSourceARB(shader, shaderSource);
/*  69 */       ARBShaderObjects.glCompileShaderARB(shader);
/*     */       
/*  71 */       if (ARBShaderObjects.glGetObjectParameteriARB(shader, 35713) == 0) {
/*  72 */         throw new RuntimeException("Error creating shader: " + getLogInfo(shader));
/*     */       }
/*  74 */       return shader;
/*     */     }
/*  76 */     catch (Exception e) {
/*  77 */       ARBShaderObjects.glDeleteObjectARB(shader);
/*  78 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String getLogInfo(int i) {
/*  84 */     return ARBShaderObjects.glGetInfoLogARB(i, ARBShaderObjects.glGetObjectParameteriARB(i, 35716));
/*     */   }
/*     */   
/*     */   public void setUniform(String uniformName, int location) {
/*  88 */     this.uniformsMap.put(uniformName, Integer.valueOf(location));
/*     */   }
/*     */   
/*     */   public void setupUniform(String uniformName) {
/*  92 */     setUniform(uniformName, GL20.glGetUniformLocation(this.program, uniformName));
/*     */   }
/*     */   
/*     */   public int getUniform(String uniformName) {
/*  96 */     return ((Integer)this.uniformsMap.get(uniformName)).intValue();
/*     */   }
/*     */   
/*     */   public int getProgramId() {
/* 100 */     return this.program;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloc\\utils\graphics\shaders\Shader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */