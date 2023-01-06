/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.render.RenderViewEntityGuiEvent;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraftforge.client.GuiIngameForge;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*    */ 
/*    */ @Mixin(value = {GuiIngameForge.class}, remap = false)
/*    */ public class MixinGuiIngameForge {
/*    */   @ModifyVariable(method = {"renderAir"}, at = @At(value = "STORE", ordinal = 0))
/*    */   private EntityPlayer renderAirModify(EntityPlayer renderViewEntity) {
/* 15 */     RenderViewEntityGuiEvent event = new RenderViewEntityGuiEvent(renderViewEntity);
/* 16 */     BaseCenter.EVENT_BUS.post(event);
/*    */     
/* 18 */     return event.entityPlayer;
/*    */   }
/*    */   
/*    */   @ModifyVariable(method = {"renderHealth"}, at = @At(value = "STORE", ordinal = 0))
/*    */   private EntityPlayer renderHealthModify(EntityPlayer renderViewEntity) {
/* 23 */     RenderViewEntityGuiEvent event = new RenderViewEntityGuiEvent(renderViewEntity);
/* 24 */     BaseCenter.EVENT_BUS.post(event);
/*    */     
/* 26 */     return event.entityPlayer;
/*    */   }
/*    */   
/*    */   @ModifyVariable(method = {"renderFood"}, at = @At(value = "STORE", ordinal = 0))
/*    */   private EntityPlayer renderFoodModify(EntityPlayer renderViewEntity) {
/* 31 */     RenderViewEntityGuiEvent event = new RenderViewEntityGuiEvent(renderViewEntity);
/* 32 */     BaseCenter.EVENT_BUS.post(event);
/*    */     
/* 34 */     return event.entityPlayer;
/*    */   }
/*    */   
/*    */   @ModifyVariable(method = {"renderHealthMount"}, at = @At(value = "STORE", ordinal = 0))
/*    */   private EntityPlayer renderHealthMountModify(EntityPlayer renderViewEntity) {
/* 39 */     RenderViewEntityGuiEvent event = new RenderViewEntityGuiEvent(renderViewEntity);
/* 40 */     BaseCenter.EVENT_BUS.post(event);
/*    */     
/* 42 */     return event.entityPlayer;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinGuiIngameForge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */