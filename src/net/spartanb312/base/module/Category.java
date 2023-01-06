/*    */ package net.spartanb312.base.module;
/*    */ 
/*    */ public enum Category
/*    */ {
/*  5 */   COMBAT("Combat", true, false),
/*  6 */   OTHER("Other", true, false),
/*  7 */   MOVEMENT("Movement", true, false),
/*  8 */   VISUALS("Visuals", true, false),
/*  9 */   CLIENT("Client", true, false),
/*    */   
/* 11 */   HUD("HUD", true, true),
/*    */   
/* 13 */   HIDDEN("Hidden", false, false);
/*    */   
/*    */   public String categoryName;
/*    */   public boolean visible;
/*    */   public boolean isHUD;
/*    */   
/*    */   Category(String categoryName, boolean visible, boolean isHUD) {
/* 20 */     this.categoryName = categoryName;
/* 21 */     this.visible = visible;
/* 22 */     this.isHUD = isHUD;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\module\Category.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */