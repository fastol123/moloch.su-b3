/*    */ package net.spartanb312.base.event.events.client;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public class GuiScreenEvent
/*    */   extends EventCenter {
/*    */   private final GuiScreen screen;
/*    */   
/*    */   public GuiScreenEvent(GuiScreen screen) {
/* 11 */     this.screen = screen;
/*    */   }
/*    */   
/*    */   public GuiScreen getScreen() {
/* 15 */     return this.screen;
/*    */   }
/*    */   
/*    */   public static class Displayed extends GuiScreenEvent {
/*    */     public Displayed(GuiScreen screen) {
/* 20 */       super(screen);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Closed extends GuiScreenEvent {
/*    */     public Closed(GuiScreen screen) {
/* 26 */       super(screen);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\event\events\client\GuiScreenEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */