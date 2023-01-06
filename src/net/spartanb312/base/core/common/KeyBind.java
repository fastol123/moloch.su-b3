/*    */ package net.spartanb312.base.core.common;
/*    */ 
/*    */ import net.spartanb312.base.core.concurrent.task.VoidTask;
/*    */ 
/*    */ public class KeyBind
/*    */ {
/*    */   private int keyCode;
/*    */   private final VoidTask action;
/*    */   
/*    */   public KeyBind(int keyCode, VoidTask action) {
/* 11 */     this.keyCode = keyCode;
/* 12 */     this.action = action;
/*    */   }
/*    */   
/*    */   public void test(int keyCode) {
/* 16 */     if (this.keyCode == keyCode && this.action != null) this.action.invoke(); 
/*    */   }
/*    */   
/*    */   public void setKeyCode(int keyCode) {
/* 20 */     this.keyCode = keyCode;
/*    */   }
/*    */   
/*    */   public int getKeyCode() {
/* 24 */     return this.keyCode;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\common\KeyBind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */