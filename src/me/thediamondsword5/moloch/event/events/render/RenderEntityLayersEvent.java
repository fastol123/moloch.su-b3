/*    */ package me.thediamondsword5.moloch.event.events.render;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public class RenderEntityLayersEvent
/*    */   extends EventCenter
/*    */ {
/*    */   public RenderLivingBase renderLivingBase;
/*    */   public ModelBase modelBase;
/*    */   public EntityLivingBase entityIn;
/*    */   public float limbSwing;
/*    */   public float limbSwingAmount;
/*    */   public float partialTicks;
/*    */   public float ageInTicks;
/*    */   public float netHeadYaw;
/*    */   public float headPitch;
/*    */   public float scaleIn;
/*    */   
/*    */   public RenderEntityLayersEvent(RenderLivingBase renderLivingBase, ModelBase modelBase, EntityLivingBase entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleIn) {
/* 23 */     this.renderLivingBase = renderLivingBase;
/* 24 */     this.modelBase = modelBase;
/* 25 */     this.entityIn = entityIn;
/* 26 */     this.limbSwing = limbSwing;
/* 27 */     this.limbSwingAmount = limbSwingAmount;
/* 28 */     this.partialTicks = partialTicks;
/* 29 */     this.ageInTicks = ageInTicks;
/* 30 */     this.netHeadYaw = netHeadYaw;
/* 31 */     this.headPitch = headPitch;
/* 32 */     this.scaleIn = scaleIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\event\events\render\RenderEntityLayersEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */