/*    */ package net.spartanb312.base.core.setting.settings;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import net.spartanb312.base.client.FontManager;
/*    */ import net.spartanb312.base.core.common.DisplayEnum;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ 
/*    */ public class EnumSetting<E extends Enum<E>>
/*    */   extends Setting<E> {
/*    */   public EnumSetting(String name, E defaultValue) {
/* 12 */     super(name, defaultValue);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setByName(String name) {
/* 17 */     for (Enum enum_ : (Enum[])((Enum<Enum>)this.value).getDeclaringClass().getEnumConstants()) {
/* 18 */       if (enum_.name().equals(name)) this.value = enum_; 
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getLongestElementLength() {
/* 23 */     ArrayList<Integer> elementLength = new ArrayList<>();
/* 24 */     for (Enum enum_ : (Enum[])((Enum<Enum>)this.value).getDeclaringClass().getEnumConstants()) {
/* 25 */       elementLength.add(Integer.valueOf(FontManager.getWidth(enum_.name())));
/*    */     }
/* 27 */     return ((Integer)Collections.<Integer>max(elementLength)).intValue();
/*    */   }
/*    */   
/*    */   public String displayValue() {
/* 31 */     if (this.value instanceof DisplayEnum)
/* 32 */       return ((DisplayEnum)this.value).displayName(); 
/* 33 */     return ((Enum)this.value).name();
/*    */   }
/*    */   
/*    */   public void forwardLoop() {
/* 37 */     if (((Enum)this.value).ordinal() == ((Enum[])((Enum)this.value).getDeclaringClass().getEnumConstants()).length - 1)
/* 38 */     { this.value = ((Enum[])((Enum)this.value).getDeclaringClass().getEnumConstants())[0]; }
/* 39 */     else { this.value = ((Enum[])((Enum)this.value).getDeclaringClass().getEnumConstants())[((Enum)this.value).ordinal() + 1]; }
/*    */   
/*    */   }
/*    */   public void backwardLoop() {
/* 43 */     if (((Enum)this.value).ordinal() == 0)
/* 44 */     { this.value = ((Enum[])((Enum)this.value).getDeclaringClass().getEnumConstants())[((Enum[])((Enum)this.value).getDeclaringClass().getEnumConstants()).length - 1]; }
/* 45 */     else { this.value = ((Enum[])((Enum)this.value).getDeclaringClass().getEnumConstants())[((Enum)this.value).ordinal() - 1]; }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\setting\settings\EnumSetting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */