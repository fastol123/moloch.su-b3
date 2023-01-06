/*    */ package me.thediamondsword5.moloch.hud.huds;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.thediamondsword5.moloch.module.modules.client.ClientInfo;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.spartanb312.base.client.FontManager;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.engine.AsyncRenderer;
/*    */ import net.spartanb312.base.hud.HUDModule;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.utils.ChatUtil;
/*    */ import net.spartanb312.base.utils.ColorUtil;
/*    */ import net.spartanb312.base.utils.graphics.font.CFontRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ModuleInfo(name = "WaterMark", category = Category.HUD, description = "Client Name Display")
/*    */ public class WaterMark
/*    */   extends HUDModule
/*    */ {
/* 23 */   public CFontRenderer font = FontManager.fontRenderer;
/* 24 */   public Setting<Boolean> version = setting("Version", true).des("Draw Version");
/* 25 */   public Setting<Boolean> watermarkShadow = setting("Shadow", true).des("Draw Shadow Under Watermark");
/* 26 */   public Setting<Boolean> rainbow = setting("Rainbow", false).des("Rainbow color");
/* 27 */   public Setting<Float> rainbowSpeed = setting("Rainbow Speed", 1.0F, 0.0F, 30.0F).des("Rainbow color change speed").whenTrue(this.rainbow);
/* 28 */   public Setting<Float> rainbowSaturation = setting("Saturation", 0.75F, 0.0F, 1.0F).des("Rainbow color saturation").whenTrue(this.rainbow);
/* 29 */   public Setting<Float> rainbowBrightness = setting("Brightness", 0.8F, 0.0F, 1.0F).des("Rainbow color brightness").whenTrue(this.rainbow);
/* 30 */   public Setting<Integer> red = setting("Red", 100, 0, 255).des("Red").whenFalse(this.rainbow);
/* 31 */   public Setting<Integer> green = setting("Green", 61, 0, 255).des("Green").whenFalse(this.rainbow);
/* 32 */   public Setting<Integer> blue = setting("Blue", 255, 0, 255).des("Blue").whenFalse(this.rainbow);
/* 33 */   public Setting<Integer> alpha = setting("Alpha", 255, 0, 255).des("Blue");
/*    */ 
/*    */   
/*    */   public WaterMark() {
/* 37 */     this.asyncRenderer = new AsyncRenderer()
/*    */       {
/*    */         public void onUpdate(ScaledResolution resolution, int mouseX, int mouseY) {
/* 40 */           drawAsyncString((String)ClientInfo.INSTANCE.clientName.getValue() + " " + ChatUtil.SECTIONSIGN + "f" + (((Boolean)WaterMark.this.version.getValue()).booleanValue() ? (String)ClientInfo.INSTANCE.clientVersion.getValue() : ""), WaterMark.this.x, WaterMark.this.y, WaterMark.this.color(), ((Boolean)WaterMark.this.watermarkShadow.getValue()).booleanValue());
/* 41 */           WaterMark.this.width = FontManager.getWidthHUD((String)ClientInfo.INSTANCE.clientName.getValue() + " " + ChatUtil.SECTIONSIGN + "f" + (((Boolean)WaterMark.this.version.getValue()).booleanValue() ? (String)ClientInfo.INSTANCE.clientVersion.getValue() : ""));
/* 42 */           WaterMark.this.height = FontManager.getHeight();
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   public void onHUDRender(ScaledResolution resolution) {
/* 49 */     this.asyncRenderer.onRender();
/*    */   }
/*    */   
/*    */   public int color() {
/* 53 */     if (((Boolean)this.rainbow.getValue()).booleanValue()) {
/* 54 */       Color lgbtqColor = new Color(ColorUtil.getBetterRainbow(((Float)this.rainbowSpeed.getValue()).floatValue(), ((Float)this.rainbowSaturation.getValue()).floatValue(), ((Float)this.rainbowBrightness.getValue()).floatValue()));
/* 55 */       return (new Color(lgbtqColor.getRed(), lgbtqColor.getGreen(), lgbtqColor.getBlue(), ((Integer)this.alpha.getValue()).intValue())).getRGB();
/*    */     } 
/*    */     
/* 58 */     return (new Color(((Integer)this.red.getValue()).intValue(), ((Integer)this.green.getValue()).intValue(), ((Integer)this.blue.getValue()).intValue(), ((Integer)this.alpha.getValue()).intValue())).getRGB();
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\hud\huds\WaterMark.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */