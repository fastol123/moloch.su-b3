/*     */ package net.spartanb312.base.gui.components;
/*     */ 
/*     */ import me.thediamondsword5.moloch.module.modules.client.CustomFont;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.spartanb312.base.client.FontManager;
/*     */ import net.spartanb312.base.client.GUIManager;
/*     */ import net.spartanb312.base.core.concurrent.task.VoidTask;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.gui.Component;
/*     */ import net.spartanb312.base.gui.Panel;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ActionButton
/*     */   extends Component
/*     */ {
/*     */   Setting<VoidTask> setting;
/*     */   String moduleName;
/*     */   
/*     */   public ActionButton(Setting<VoidTask> setting, int width, int height, Panel father, Module module) {
/*  21 */     this.moduleName = module.name;
/*  22 */     this.setting = setting;
/*  23 */     this.width = width;
/*  24 */     this.height = height;
/*  25 */     this.father = father;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(int mouseX, int mouseY, float translateDelta, float partialTicks) {
/*  31 */     if (CustomFont.instance.font.getValue() == CustomFont.FontMode.Minecraft) {
/*  32 */       GL11.glEnable(3553);
/*  33 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/*  34 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/*  36 */       this.mc.field_71466_p.func_175065_a(this.setting.getName(), (this.x + 5), (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F), GUIManager.getColor3I(), ((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue());
/*     */       
/*  38 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*  39 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, (int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*  40 */       GL11.glDisable(3553);
/*     */     
/*     */     }
/*  43 */     else if (((Boolean)CustomFont.instance.textShadow.getValue()).booleanValue()) {
/*  44 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/*  45 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/*  47 */       FontManager.drawShadow(this.setting.getName(), (this.x + 5), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), GUIManager.getColor3I());
/*     */       
/*  49 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*  50 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */     } else {
/*     */       
/*  53 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()), 0.0F);
/*  54 */       GL11.glScalef(((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*     */       
/*  56 */       FontManager.draw(this.setting.getName(), (this.x + 5), ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3), GUIManager.getColor3I());
/*     */       
/*  58 */       GL11.glScalef(1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue(), 1.0F / ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue());
/*  59 */       GL11.glTranslatef((this.x + 5) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, ((int)((this.y + this.height / 2) - this.font.getHeight() / 2.0F) + 3) * (1.0F - ((Float)CustomFont.instance.componentTextScale.getValue()).floatValue()) * -1.0F, 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bottomRender(int mouseX, int mouseY, boolean lastSetting, boolean firstSetting, float partialTicks) {
/*  67 */     GlStateManager.func_179118_c();
/*  68 */     drawSettingRects(lastSetting, false);
/*     */     
/*  70 */     drawExtendedGradient(lastSetting, false);
/*  71 */     drawExtendedLine(lastSetting);
/*     */     
/*  73 */     renderHoverRect(this.moduleName + this.setting.getName(), mouseX, mouseY, 2.0F, -1.0F, false);
/*     */     
/*  75 */     GlStateManager.func_179141_d();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHovered(int mouseX, int mouseY) {
/*  80 */     if (!anyExpanded) {
/*  81 */       return (mouseX >= Math.min(this.x, this.x + this.width) && mouseX <= Math.max(this.x, this.x + this.width) && mouseY >= Math.min(this.y, this.y + this.height) && mouseY <= Math.max(this.y, this.y + this.height));
/*     */     }
/*     */     
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/*  91 */     if (!isHovered(mouseX, mouseY)) {
/*  92 */       return false;
/*     */     }
/*  94 */     if (mouseButton == 0) {
/*  95 */       ((VoidTask)this.setting.getValue()).invoke();
/*     */     }
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 103 */     return this.setting.getDescription();
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\gui\components\ActionButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */