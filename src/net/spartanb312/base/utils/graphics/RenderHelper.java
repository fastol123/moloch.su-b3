/*    */ package net.spartanb312.base.utils.graphics;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.client.renderer.culling.Frustum;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.spartanb312.base.hud.huds.ActiveModuleList;
/*    */ import net.spartanb312.base.utils.math.Vec2I;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderHelper
/*    */ {
/* 17 */   private static final Frustum frustrum = new Frustum();
/*    */   
/*    */   public static Vec2I getStart(ScaledResolution scaledResolution, ActiveModuleList.ListPos caseIn) {
/* 20 */     switch (caseIn) {
/*    */       case RightDown:
/* 22 */         return new Vec2I(scaledResolution.func_78326_a(), scaledResolution.func_78328_b());
/*    */       
/*    */       case LeftTop:
/* 25 */         return new Vec2I(0, 0);
/*    */       
/*    */       case LeftDown:
/* 28 */         return new Vec2I(0, scaledResolution.func_78328_b());
/*    */     } 
/*    */     
/* 31 */     return new Vec2I(scaledResolution.func_78326_a(), 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isInViewFrustrum(Entity entity) {
/* 37 */     return (isInViewFrustrum(entity.func_174813_aQ()) || entity.field_70158_ak);
/*    */   }
/*    */   
/*    */   public static boolean isInViewFrustrum(AxisAlignedBB bb) {
/* 41 */     Entity current = Minecraft.func_71410_x().func_175606_aa();
/* 42 */     frustrum.func_78547_a(current.field_70165_t, current.field_70163_u, current.field_70161_v);
/* 43 */     return frustrum.func_78546_a(bb);
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\graphics\RenderHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */