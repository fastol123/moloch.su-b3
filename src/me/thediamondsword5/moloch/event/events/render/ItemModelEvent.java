/*    */ package me.thediamondsword5.moloch.event.events.render;
/*    */ import net.minecraft.util.EnumHand;
/*    */ 
/*    */ public class ItemModelEvent extends EventCenter {
/*    */   public float offsetMain;
/*    */   public float offsetOff;
/*    */   public boolean modifyMain;
/*    */   public boolean modifyOff;
/*    */   public EnumHand hand;
/*    */   public float mainX;
/*    */   public float mainY;
/*    */   public float mainZ;
/*    */   public float mainRotateX;
/*    */   public float mainRotateY;
/*    */   public float mainRotateZ;
/*    */   
/*    */   public ItemModelEvent(float mainX, float mainY, float mainZ, float mainRotateX, float mainRotateY, float mainRotateZ, float mainScaleX, float mainScaleY, float mainScaleZ, float offX, float offY, float offZ, float offRotateX, float offRotateY, float offRotateZ, float offScaleX, float offScaleY, float offScaleZ, boolean modifyMain, boolean modifyOff, EnumHand hand) {
/* 18 */     this.mainX = mainX;
/* 19 */     this.mainY = mainY;
/* 20 */     this.mainZ = mainZ;
/* 21 */     this.mainRotateX = mainRotateX;
/* 22 */     this.mainRotateY = mainRotateY;
/* 23 */     this.mainRotateZ = mainRotateZ;
/* 24 */     this.mainScaleX = mainScaleX;
/* 25 */     this.mainScaleY = mainScaleY;
/* 26 */     this.mainScaleZ = mainScaleZ;
/* 27 */     this.offX = offX;
/* 28 */     this.offY = offY;
/* 29 */     this.offZ = offZ;
/* 30 */     this.offRotateX = offRotateX;
/* 31 */     this.offRotateY = offRotateY;
/* 32 */     this.offRotateZ = offRotateZ;
/* 33 */     this.offScaleX = offScaleX;
/* 34 */     this.offScaleY = offScaleY;
/* 35 */     this.offScaleZ = offScaleZ;
/* 36 */     this.modifyMain = modifyMain;
/* 37 */     this.modifyOff = modifyOff;
/* 38 */     this.hand = hand;
/*    */   }
/*    */   public float mainScaleX; public float mainScaleY; public float mainScaleZ; public float offX; public float offY; public float offZ; public float offRotateX; public float offRotateY; public float offRotateZ; public float offScaleX; public float offScaleY; public float offScaleZ;
/*    */   public ItemModelEvent(float offsetMain, float offsetOff, boolean modifyMain, boolean modifyOff) {
/* 42 */     this.offsetMain = offsetMain;
/* 43 */     this.offsetOff = offsetOff;
/* 44 */     this.modifyMain = modifyMain;
/* 45 */     this.modifyOff = modifyOff;
/*    */   }
/*    */   
/*    */   public static class Normal
/*    */     extends ItemModelEvent
/*    */   {
/*    */     public Normal(float mainX, float mainY, float mainZ, float mainRotateX, float mainRotateY, float mainRotateZ, float mainScaleX, float mainScaleY, float mainScaleZ, float offX, float offY, float offZ, float offRotateX, float offRotateY, float offRotateZ, float offScaleX, float offScaleY, float offScaleZ, boolean modifyMain, boolean modifyOff, EnumHand hand) {
/* 52 */       super(mainX, mainY, mainZ, mainRotateX, mainRotateY, mainRotateZ, mainScaleX, mainScaleY, mainScaleZ, offX, offY, offZ, offRotateX, offRotateY, offRotateZ, offScaleX, offScaleY, offScaleZ, modifyMain, modifyOff, hand);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class EquipProgress
/*    */     extends ItemModelEvent
/*    */   {
/*    */     public EquipProgress(float offsetMain, float offsetOff, boolean modifyMain, boolean modifyOff) {
/* 60 */       super(offsetMain, offsetOff, modifyMain, modifyOff);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\event\events\render\ItemModelEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */