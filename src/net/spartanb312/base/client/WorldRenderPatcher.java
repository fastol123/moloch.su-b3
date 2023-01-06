/*    */ package net.spartanb312.base.client;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import net.spartanb312.base.event.events.render.RenderEvent;
/*    */ import net.spartanb312.base.event.events.render.RenderWorldEvent;
/*    */ import net.spartanb312.base.module.Module;
/*    */ import net.spartanb312.base.notification.NotificationManager;
/*    */ import net.spartanb312.base.utils.EntityUtil;
/*    */ import net.spartanb312.base.utils.ItemUtils;
/*    */ import net.spartanb312.base.utils.graphics.SpartanTessellator;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldRenderPatcher
/*    */ {
/* 22 */   public static WorldRenderPatcher INSTANCE = new WorldRenderPatcher();
/*    */   
/*    */   public void patch(RenderWorldEvent event) {
/* 25 */     ItemUtils.mc.field_71424_I.func_76320_a("moloch.su");
/*    */     
/* 27 */     ItemUtils.mc.field_71424_I.func_76320_a("setup");
/*    */     
/* 29 */     SpartanTessellator.prepareGL();
/* 30 */     GlStateManager.func_179103_j(7425);
/* 31 */     GL11.glDisable(2896);
/* 32 */     GL11.glEnable(2884);
/*    */     
/* 34 */     Vec3d renderPos = getInterpolatedPos(Objects.<Entity>requireNonNull(ItemUtils.mc.func_175606_aa()), event.getPartialTicks());
/*    */     
/* 36 */     RenderEvent e = new RenderEvent((Tessellator)SpartanTessellator.INSTANCE, renderPos);
/* 37 */     e.resetTranslation();
/*    */     
/* 39 */     RenderEvent.Extra1 e1 = new RenderEvent.Extra1((Tessellator)SpartanTessellator.INSTANCE, renderPos);
/* 40 */     e1.resetTranslation();
/*    */     
/* 42 */     ItemUtils.mc.field_71424_I.func_76319_b();
/*    */     
/* 44 */     BaseCenter.MODULE_BUS.getModules().forEach(it -> {
/*    */           
/*    */           try {
/*    */             it.onRenderWorld(e);
/* 48 */           } catch (Exception exception) {
/*    */             NotificationManager.fatal("Error while running onRenderWorld!");
/*    */             
/*    */             exception.printStackTrace();
/*    */           } 
/*    */         });
/* 54 */     BaseCenter.EVENT_BUS.post(e1);
/*    */     
/* 56 */     ItemUtils.mc.field_71424_I.func_76320_a("release");
/* 57 */     GlStateManager.func_187441_d(1.0F);
/* 58 */     GlStateManager.func_179103_j(7424);
/* 59 */     SpartanTessellator.releaseGL();
/* 60 */     GL11.glEnable(2896);
/*    */     
/* 62 */     ItemUtils.mc.field_71424_I.func_76319_b();
/*    */   }
/*    */   
/*    */   public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
/* 66 */     return (new Vec3d(entity.field_70142_S, entity.field_70137_T, entity.field_70136_U)).func_178787_e(EntityUtil.getInterpolatedAmount(entity, ticks));
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\client\WorldRenderPatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */