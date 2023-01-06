/*    */ package net.spartanb312.base.core.setting;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.BooleanSupplier;
/*    */ import java.util.function.Predicate;
/*    */ 
/*    */ public class Setting<T>
/*    */ {
/*    */   private final String name;
/*    */   private final T defaultValue;
/*    */   protected T value;
/* 13 */   private final List<BooleanSupplier> visibilities = new ArrayList<>();
/* 14 */   private String description = "";
/*    */   private Predicate<T> visible;
/*    */   
/*    */   public Setting(String name, T defaultValue) {
/* 18 */     this.name = name;
/* 19 */     this.defaultValue = defaultValue;
/* 20 */     this.value = defaultValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public T getDefaultValue() {
/* 25 */     return this.defaultValue;
/*    */   }
/*    */   
/*    */   public T getValue() {
/* 29 */     return this.value;
/*    */   }
/*    */   
/*    */   public void reset() {
/* 33 */     this.value = this.defaultValue;
/*    */   }
/*    */   
/*    */   public void setValue(T valueIn) {
/* 37 */     this.value = valueIn;
/*    */   }
/*    */   
/*    */   public Setting<T> when(BooleanSupplier booleanSupplier) {
/* 41 */     this.visibilities.add(booleanSupplier);
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public <E extends Enum<E>> Setting<T> whenAtMode(Setting<E> enumSetting, E mode) {
/* 47 */     return when(() -> ((Enum)enumSetting.getValue()).equals(mode));
/*    */   }
/*    */   
/*    */   public Setting<T> whenFalse(Setting<Boolean> booleanSetting) {
/* 51 */     return when(() -> !((Boolean)booleanSetting.getValue()).booleanValue());
/*    */   }
/*    */   
/*    */   public Setting<T> only(Predicate<T> visible) {
/* 55 */     if (visible != null) {
/* 56 */       return when(() -> visible.test(getValue()));
/*    */     }
/*    */     
/* 59 */     return when(() -> true);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Setting<T> whenTrue(Setting<Boolean> booleanSetting) {
/* 65 */     return when(booleanSetting::getValue);
/*    */   }
/*    */   
/*    */   public boolean isVisible() {
/* 69 */     for (BooleanSupplier booleanSupplier : this.visibilities) {
/* 70 */       if (!booleanSupplier.getAsBoolean()) return false; 
/*    */     } 
/* 72 */     return true;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 76 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 80 */     return this.description;
/*    */   }
/*    */   
/*    */   public Setting<T> des(String description) {
/* 84 */     this.description = description;
/* 85 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\setting\Setting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */