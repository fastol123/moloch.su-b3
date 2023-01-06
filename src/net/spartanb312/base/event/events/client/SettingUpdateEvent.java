/*    */ package net.spartanb312.base.event.events.client;
/*    */ 
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public class SettingUpdateEvent
/*    */   extends EventCenter {
/*    */   private final Setting<?> setting;
/*    */   
/*    */   public SettingUpdateEvent(Setting<?> setting) {
/* 11 */     this.setting = setting;
/*    */   }
/*    */   
/*    */   public Setting<?> getSetting() {
/* 15 */     return this.setting;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\event\events\client\SettingUpdateEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */