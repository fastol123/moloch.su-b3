/*    */ package net.spartanb312.base.hud.huds;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import me.thediamondsword5.moloch.core.common.Color;
/*    */ import me.thediamondsword5.moloch.core.common.Visibility;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import net.spartanb312.base.client.FontManager;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.engine.AsyncRenderer;
/*    */ import net.spartanb312.base.hud.HUDModule;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ import net.spartanb312.base.utils.ColorUtil;
/*    */ 
/*    */ @ModuleInfo(name = "ActiveModuleList", category = Category.HUD)
/*    */ public class ActiveModuleList extends HUDModule {
/* 22 */   public Setting<Boolean> shadow = setting("Shadow", true).des("Draw Text Shadow Under Module List");
/* 23 */   Setting<ListPos> listPos = setting("ListPos", ListPos.RightTop).des("The position of list");
/* 24 */   Setting<Boolean> potionMove = setting("MoveOnPotion", true).whenAtMode(this.listPos, ListPos.RightTop).des("Move List When Potions Are Active");
/* 25 */   Setting<Color> listColor = setting("ListColor", new Color((new Color(100, 61, 255, 200)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 61, 255, 200));
/* 26 */   Setting<Boolean> listRainbowRoll = setting("ListRainbowRoll", false).des("Rolling list rainbow").only(v -> ((Color)this.listColor.getValue()).getRainbow());
/* 27 */   Setting<Float> listRainbowRollSize = setting("ListRainbowRollSize", 1.0F, 0.1F, 2.0F).whenTrue(this.listRainbowRoll).only(v -> ((Color)this.listColor.getValue()).getRainbow());
/*    */   
/*    */   public ActiveModuleList() {
/* 30 */     this.asyncRenderer = new AsyncRenderer()
/*    */       {
/*    */         public void onUpdate(ScaledResolution resolution, int mouseX, int mouseY) {
/* 33 */           int startX = ActiveModuleList.this.x;
/* 34 */           int startY = ActiveModuleList.this.y;
/*    */           
/* 36 */           if (Module.mc.field_71439_g.func_70651_bq().size() > 0 && ((ActiveModuleList.ListPos)ActiveModuleList.this.listPos.getValue()).equals(ActiveModuleList.ListPos.RightTop) && ((Boolean)ActiveModuleList.this.potionMove.getValue()).booleanValue()) {
/* 37 */             startY += 26;
/*    */           }
/*    */           
/* 40 */           int index = 0;
/*    */           
/* 42 */           List<Module> moduleList = (List<Module>)BaseCenter.MODULE_BUS.getModules().stream().sorted(Comparator.comparing(it -> Integer.valueOf(-FontManager.getWidthHUD(it.getHudSuffix())))).collect(Collectors.toList());
/*    */           
/* 44 */           for (Module module : moduleList) {
/* 45 */             int color; if (!((Visibility)module.visibleSetting.getValue()).getVisible())
/*    */               continue; 
/* 47 */             if (((Color)ActiveModuleList.this.listColor.getValue()).getRainbow() && ((Boolean)ActiveModuleList.this.listRainbowRoll.getValue()).booleanValue()) {
/* 48 */               color = ColorUtil.rainbow(index * 100, ((Color)ActiveModuleList.this.listColor.getValue()).getRainbowSpeed(), ((Float)ActiveModuleList.this.listRainbowRollSize.getValue()).floatValue(), ((Color)ActiveModuleList.this.listColor.getValue()).getRainbowSaturation(), ((Color)ActiveModuleList.this.listColor.getValue()).getRainbowBrightness());
/*    */             } else {
/*    */               
/* 51 */               color = ((Color)ActiveModuleList.this.listColor.getValue()).getColor();
/*    */             } 
/*    */             
/* 54 */             index++;
/* 55 */             String information = module.getHudSuffix();
/* 56 */             switch ((ActiveModuleList.ListPos)ActiveModuleList.this.listPos.getValue()) {
/*    */               case RightDown:
/* 58 */                 drawAsyncString(information, (startX - FontManager.getWidthHUD(information) + ActiveModuleList.this.width), (startY - FontManager.getHeightHUD() * index + ActiveModuleList.this.height), color, ((Boolean)ActiveModuleList.this.shadow.getValue()).booleanValue());
/*    */                 continue;
/*    */               
/*    */               case LeftTop:
/* 62 */                 drawAsyncString(information, startX, (startY + 3 + FontManager.getHeightHUD() * (index - 1)), color, ((Boolean)ActiveModuleList.this.shadow.getValue()).booleanValue());
/*    */                 continue;
/*    */               
/*    */               case LeftDown:
/* 66 */                 drawAsyncString(information, startX, (startY - FontManager.getHeightHUD() * index + ActiveModuleList.this.height), color, ((Boolean)ActiveModuleList.this.shadow.getValue()).booleanValue());
/*    */                 continue;
/*    */             } 
/*    */             
/* 70 */             drawAsyncString(information, (startX - FontManager.getWidthHUD(information) + ActiveModuleList.this.width), (startY + 3 + FontManager.getHeightHUD() * (index - 1)), color, ((Boolean)ActiveModuleList.this.shadow.getValue()).booleanValue());
/*    */           } 
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onHUDRender(ScaledResolution resolution) {
/* 81 */     this.asyncRenderer.onRender();
/*    */   }
/*    */   
/*    */   public enum ListPos {
/* 85 */     RightTop, RightDown, LeftTop, LeftDown;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\hud\huds\ActiveModuleList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */