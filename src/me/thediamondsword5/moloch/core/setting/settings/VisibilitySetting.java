/*    */ package me.thediamondsword5.moloch.core.setting.settings;
/*    */ 
/*    */ import me.thediamondsword5.moloch.core.common.Visibility;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ 
/*    */ public class VisibilitySetting extends Setting<Visibility> {
/*    */   public VisibilitySetting(String name, Visibility defaultValue) {
/*  8 */     super(name, defaultValue);
/*    */   }
/*    */   
/*    */   public void setOpposite(boolean visible) {
/* 12 */     ((Visibility)this.value).setVisible(visible);
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\core\setting\settings\VisibilitySetting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */