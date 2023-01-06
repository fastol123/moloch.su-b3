/*    */ package me.thediamondsword5.moloch.event.events.render;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public class RenderEntityInvokeEvent
/*    */   extends EventCenter
/*    */ {
/*    */   public ModelBase modelBase;
/*    */   public Entity entityIn;
/*    */   public float limbSwing;
/*    */   public float limbSwingAmount;
/*    */   public float ageInTicks;
/*    */   public float netHeadYaw;
/*    */   public float headPitch;
/*    */   public float scale;
/*    */   
/*    */   public RenderEntityInvokeEvent(ModelBase modelBase, Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 20 */     this.modelBase = modelBase;
/* 21 */     this.entityIn = entityIn;
/* 22 */     this.limbSwing = limbSwing;
/* 23 */     this.limbSwingAmount = limbSwingAmount;
/* 24 */     this.ageInTicks = ageInTicks;
/* 25 */     this.netHeadYaw = netHeadYaw;
/* 26 */     this.headPitch = headPitch;
/* 27 */     this.scale = scale;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\event\events\render\RenderEntityInvokeEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */