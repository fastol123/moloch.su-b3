/*     */ package net.spartanb312.base.utils;
/*     */ 
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import org.lwjgl.util.vector.Vector2f;
/*     */ 
/*     */ public class MathUtilFuckYou
/*     */ {
/*     */   public static float clamp(float val, float min, float max) {
/*   9 */     if (val <= min) {
/*  10 */       val = min;
/*     */     }
/*  12 */     if (val >= max) {
/*  13 */       val = max;
/*     */     }
/*  15 */     return val;
/*     */   }
/*     */ 
/*     */   
/*     */   public static long clamp(long val, long min, long max) {
/*  20 */     if (val <= min) {
/*  21 */       val = min;
/*     */     }
/*  23 */     if (val >= max) {
/*  24 */       val = max;
/*     */     }
/*  26 */     return val;
/*     */   }
/*     */   
/*     */   public static double getDistance(Vec3d start, Vec3d end) {
/*  30 */     double x = end.field_72450_a - start.field_72450_a;
/*  31 */     double y = end.field_72448_b - start.field_72448_b;
/*  32 */     double z = end.field_72449_c - start.field_72449_c;
/*  33 */     return Math.sqrt(x * x + y * y + z * z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] rotationAroundAxis3d(double x, double y, double z, double theta, String axis) {
/*  39 */     if (theta == 0.0D) {
/*  40 */       return new double[] { x, y, z };
/*     */     }
/*  42 */     switch (axis) {
/*     */       case "x":
/*  44 */         return new double[] { x, y * Math.cos(theta) - z * Math.sin(theta), y * Math.sin(theta) + z * Math.cos(theta) };
/*     */ 
/*     */       
/*     */       case "y":
/*  48 */         return new double[] { x * Math.cos(theta) + z * Math.sin(theta), y, z * Math.cos(theta) - x * Math.sin(theta) };
/*     */ 
/*     */       
/*     */       case "z":
/*  52 */         return new double[] { x * Math.cos(theta) - y * Math.sin(theta), x * Math.sin(theta) + y * Math.cos(theta), z };
/*     */     } 
/*     */     
/*  55 */     return new double[] { 0.0D, 0.0D, 0.0D };
/*     */   }
/*     */   
/*     */   public static double[] cartesianToPolar2d(double x, double y) {
/*  59 */     return new double[] { Math.sqrt(x * x + y * y), Math.atan(y / x) };
/*     */   }
/*     */   
/*     */   public static double[] polarToCartesian2d(double magnitude, double theta) {
/*  63 */     return new double[] { magnitude * Math.cos(theta), magnitude * Math.sin(theta) };
/*     */   }
/*     */   
/*     */   public static double[] cartesianToPolar3d(double x, double y, double z) {
/*  67 */     double magnitude = Math.sqrt(x * x + y * y + z * z);
/*  68 */     return new double[] { magnitude, Math.acos(x / Math.sqrt(x * x + y * y)) * ((y < 0.0D) ? -1 : true), Math.acos(z / magnitude) };
/*     */   }
/*     */   
/*     */   public static double[] polarToCartesian3d(double magnitude, double theta, double phi) {
/*  72 */     return new double[] { magnitude * Math.sin(phi) * Math.cos(theta), magnitude * Math.sin(phi) * Math.sin(theta), magnitude * Math.cos(phi) };
/*     */   }
/*     */   
/*     */   public static float dotProduct(Vector2f in, Vector2f normal) {
/*  76 */     float normalVecLength = (float)Math.sqrt((normal.x * normal.x + normal.y * normal.y));
/*  77 */     Vector2f normalizedVec = new Vector2f(normal.x / normalVecLength, normal.y / normalVecLength);
/*  78 */     return in.x * normalizedVec.x + in.y * normalizedVec.y;
/*     */   }
/*     */   
/*     */   public static float dotProductNormalized(Vector2f in, Vector2f normalizedVec) {
/*  82 */     return in.x * normalizedVec.x + in.y * normalizedVec.y;
/*     */   }
/*     */   
/*     */   public static Vector2f reflectVector2f(Vector2f in, Vector2f normal) {
/*  86 */     float normalVecLength = (float)Math.sqrt((normal.x * normal.x + normal.y * normal.y));
/*  87 */     Vector2f normalizedVec = new Vector2f(normal.x / normalVecLength, normal.y / normalVecLength);
/*  88 */     float dotProduct = 2.0F * dotProductNormalized(in, normalizedVec);
/*  89 */     return new Vector2f(in.x - normalizedVec.x * dotProduct, in.y - normalizedVec.y * dotProduct);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float interpNonLinear(float start, float end, float progress, float factor) {
/*  96 */     progress = clamp(progress, 0.0F, 1.0F);
/*  97 */     float remaining = end - start + progress * (end - start);
/*  98 */     return end - remaining / (factor * progress * 300.0F + 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float linearInterp(float start, float end, float progressCounter) {
/* 105 */     return start + progressCounter * (end - start) / 300.0F;
/*     */   }
/*     */   
/*     */   public static float rolledLinearInterp(int component1, int component2, int offset, float speed, float size) {
/* 109 */     double componentState = Math.ceil((System.currentTimeMillis() * speed + offset) / 20.0D) / size;
/* 110 */     componentState %= 300.0D;
/* 111 */     componentState = (float)(150.0D * Math.sin((componentState - 75.0D) * Math.PI / 150.0D) + 150.0D);
/* 112 */     return linearInterp(component1, component2, (float)componentState);
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\MathUtilFuckYou.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */