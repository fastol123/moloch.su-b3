/*    */ package net.spartanb312.base.core.setting;
/*    */ 
/*    */ public class NumberSetting<T extends Number>
/*    */   extends Setting<T> {
/*    */   private final T min;
/*    */   private final T max;
/*    */   
/*    */   public NumberSetting(String name, T defaultValue, T min, T max) {
/*  9 */     super(name, defaultValue);
/* 10 */     this.min = min;
/* 11 */     this.max = max;
/*    */   }
/*    */   
/*    */   public T getMin() {
/* 15 */     return this.min;
/*    */   }
/*    */   
/*    */   public T getMax() {
/* 19 */     return this.max;
/*    */   }
/*    */   
/*    */   public boolean isInRange(Number valueIn) {
/* 23 */     return (valueIn.doubleValue() <= this.max.doubleValue() && valueIn.doubleValue() >= this.min.doubleValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\setting\NumberSetting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */