/*   */ package net.spartanb312.base.core.setting.settings;
/*   */ 
/*   */ import net.spartanb312.base.core.setting.NumberSetting;
/*   */ 
/*   */ public class FloatSetting extends NumberSetting<Float> {
/*   */   public FloatSetting(String name, float defaultValue, float min, float max) {
/* 7 */     super(name, Float.valueOf(defaultValue), Float.valueOf(min), Float.valueOf(max));
/*   */   }
/*   */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\setting\settings\FloatSetting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */