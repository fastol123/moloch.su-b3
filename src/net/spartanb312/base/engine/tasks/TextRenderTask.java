/*    */ package net.spartanb312.base.engine.tasks;
/*    */ 
/*    */ import me.thediamondsword5.moloch.hud.huds.CustomHUDFont;
/*    */ import net.spartanb312.base.client.FontManager;
/*    */ import net.spartanb312.base.command.Command;
/*    */ import net.spartanb312.base.engine.RenderTask;
/*    */ import net.spartanb312.base.utils.graphics.font.CFontRenderer;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class TextRenderTask implements RenderTask {
/*    */   String text;
/*    */   float x;
/*    */   float y;
/*    */   int color;
/*    */   boolean centered;
/*    */   boolean shadow;
/*    */   boolean isIcon;
/*    */   CFontRenderer fontRenderer;
/*    */   FontManager fontManager;
/*    */   
/*    */   public TextRenderTask(String text, float x, float y, int color, boolean centered, boolean shadow) {
/* 22 */     this.text = text;
/* 23 */     this.x = x;
/* 24 */     this.y = y;
/* 25 */     this.color = color;
/* 26 */     this.centered = centered;
/* 27 */     this.shadow = shadow;
/* 28 */     this.fontRenderer = FontManager.fontRenderer;
/*    */   }
/*    */   
/*    */   public TextRenderTask(String text, float x, float y, int color, boolean centered, boolean shadow, CFontRenderer fontRenderer) {
/* 32 */     this.text = text;
/* 33 */     this.x = x;
/* 34 */     this.y = y;
/* 35 */     this.color = color;
/* 36 */     this.centered = centered;
/* 37 */     this.shadow = shadow;
/* 38 */     this.fontRenderer = fontRenderer;
/*    */   }
/*    */   
/*    */   public TextRenderTask(String text, float x, float y, int color, boolean isIcon) {
/* 42 */     this.text = text;
/* 43 */     this.x = x;
/* 44 */     this.y = y;
/* 45 */     this.color = color;
/* 46 */     this.isIcon = isIcon;
/* 47 */     this.fontRenderer = FontManager.iconFont;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender() {
/* 52 */     if (this.isIcon)
/* 53 */     { this.fontRenderer.drawString(this.text, this.x, this.y, this.color);
/*    */        }
/*    */     
/* 56 */     else if (CustomHUDFont.instance.font.getValue() == CustomHUDFont.FontMode.Minecraft)
/* 57 */     { GL11.glEnable(3553);
/* 58 */       if (this.centered) { this.fontManager.drawStringMcCentered(this.text, this.x, this.y, this.color, this.shadow); }
/* 59 */       else { Command.mc.field_71466_p.func_175065_a(this.text, (int)this.x, (int)this.y, this.color, this.shadow); }
/* 60 */        GL11.glDisable(3553);
/*    */        }
/*    */     
/* 63 */     else if (this.shadow)
/* 64 */     { if (this.centered) { FontManager.drawHUDShadowCentered(this.text, this.x, this.y, this.color); }
/* 65 */       else { FontManager.drawHUDShadow(this.text, this.x, this.y, this.color); }
/*    */        }
/* 67 */     else if (this.centered) { FontManager.drawHUDCentered(this.text, this.x, this.y, this.color); }
/* 68 */     else { FontManager.drawHUD(this.text, this.x, this.y, this.color); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\engine\tasks\TextRenderTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */