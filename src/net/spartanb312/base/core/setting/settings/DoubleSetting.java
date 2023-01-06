/*   */ package net.spartanb312.base.core.setting.settings;
/*   */ 
/*   */ import net.spartanb312.base.core.setting.NumberSetting;
/*   */ 
/*   */ public class DoubleSetting extends NumberSetting<Double> {
/*   */   public DoubleSetting(String name, double defaultValue, double min, double max) {
/* 7 */     super(name, Double.valueOf(defaultValue), Double.valueOf(min), Double.valueOf(max));
/*   */   }
/*   */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\setting\settings\DoubleSetting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */