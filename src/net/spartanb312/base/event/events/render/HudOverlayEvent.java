/*    */ package net.spartanb312.base.event.events.render;
/*    */ 
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public final class HudOverlayEvent
/*    */   extends EventCenter {
/*    */   private final Type type;
/*    */   
/*    */   public HudOverlayEvent(Type type) {
/* 10 */     this.type = type;
/*    */   }
/*    */   
/*    */   public final Type getType() {
/* 14 */     return this.type;
/*    */   }
/*    */   
/*    */   public enum Type {
/* 18 */     WATER,
/* 19 */     LAVA,
/* 20 */     PUMPKIN,
/* 21 */     HURTCAM,
/* 22 */     SCOREBOARD,
/* 23 */     FIRE,
/* 24 */     STAT_ALL,
/* 25 */     STAT_HEALTH,
/* 26 */     STAT_FOOD,
/* 27 */     STAT_ARMOR,
/* 28 */     STAT_AIR,
/* 29 */     BOSS_BAR,
/* 30 */     EXP_BAR,
/* 31 */     VIGNETTE,
/* 32 */     CROSSHAIR,
/* 33 */     ATTACK_INDICATOR,
/* 34 */     JUMP_BAR,
/* 35 */     MOUNT_HEALTH,
/* 36 */     PORTAL,
/* 37 */     SELECTED_ITEM_TOOLTIP,
/* 38 */     POTION_EFFECTS;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\event\events\render\HudOverlayEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */