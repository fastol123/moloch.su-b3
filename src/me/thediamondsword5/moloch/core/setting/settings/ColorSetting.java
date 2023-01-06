/*    */ package me.thediamondsword5.moloch.core.setting.settings;
/*    */ 
/*    */ import me.thediamondsword5.moloch.core.common.Color;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ 
/*    */ public class ColorSetting extends Setting<Color> {
/*    */   public ColorSetting(String name, Color defaultValue) {
/*  8 */     super(name, defaultValue);
/*    */   }
/*    */   
/*    */   public void setColor(int color) {
/* 12 */     ((Color)this.value).setColor(color);
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\core\setting\settings\ColorSetting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */