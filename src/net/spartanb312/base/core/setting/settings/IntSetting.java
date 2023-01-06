/*   */ package net.spartanb312.base.core.setting.settings;
/*   */ 
/*   */ import net.spartanb312.base.core.setting.NumberSetting;
/*   */ 
/*   */ public class IntSetting extends NumberSetting<Integer> {
/*   */   public IntSetting(String name, int defaultValue, int min, int max) {
/* 7 */     super(name, Integer.valueOf(defaultValue), Integer.valueOf(min), Integer.valueOf(max));
/*   */   }
/*   */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\setting\settings\IntSetting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */