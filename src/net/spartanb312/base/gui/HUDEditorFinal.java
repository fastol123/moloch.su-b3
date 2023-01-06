/*     */ package net.spartanb312.base.gui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.module.modules.client.Blur;
/*     */ import me.thediamondsword5.moloch.module.modules.client.MoreClickGUI;
/*     */ import me.thediamondsword5.moloch.module.modules.client.Particles;
/*     */ import me.thediamondsword5.moloch.utils.graphics.ParticleUtil;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraftforge.client.event.GuiScreenEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.spartanb312.base.client.FontManager;
/*     */ import net.spartanb312.base.client.GUIManager;
/*     */ import net.spartanb312.base.client.ModuleManager;
/*     */ import net.spartanb312.base.gui.renderers.HUDEditorRenderer;
/*     */ import net.spartanb312.base.module.modules.client.ClickGUI;
/*     */ import net.spartanb312.base.module.modules.client.HUDEditor;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*     */ import net.spartanb312.base.utils.math.Vec2I;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HUDEditorFinal
/*     */   extends GuiScreen
/*     */ {
/*  35 */   public int flag = 0;
/*     */   
/*     */   public static HUDEditorFinal instance;
/*     */   static Panel panel;
/*  39 */   Timer guiAnimateTimer = new Timer();
/*  40 */   float delta = 0.0F;
/*     */ 
/*     */   
/*     */   public HUDEditorFinal() {
/*  44 */     instance = this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean func_73868_f() {
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_146281_b() {
/*  57 */     if (this.field_146297_k.field_71460_t.func_147706_e() != null) {
/*  58 */       this.field_146297_k.field_71460_t.func_147706_e().func_148021_a();
/*     */     }
/*  60 */     if (ModuleManager.getModule(HUDEditor.class).isEnabled()) {
/*  61 */       ModuleManager.getModule(HUDEditor.class).disable();
/*     */     }
/*     */   }
/*     */   
/*     */   private float alphaFactor() {
/*  66 */     float f = this.delta / this.field_146297_k.field_71462_r.field_146295_m;
/*  67 */     if (f > 1.0F) f = 1.0F; 
/*  68 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/*  74 */     if (ModuleManager.getModule(Blur.class).isEnabled() && ((Boolean)Blur.INSTANCE.blurClickGUI.getValue()).booleanValue()) {
/*     */       
/*  76 */       RenderUtils2D.drawBlurAreaPre(((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue() ? (((Float)Blur.INSTANCE.blurFactor.getValue()).floatValue() * alphaFactor()) : ((Float)Blur.INSTANCE.blurFactor.getValue()).floatValue(), partialTicks);
/*  77 */       RenderUtils2D.drawBlurRect(Tessellator.func_178181_a(), Tessellator.func_178181_a().func_178180_c(), 0.0F, 0.0F, this.field_146297_k.field_71443_c, this.field_146297_k.field_71440_d);
/*  78 */       RenderUtils2D.drawBlurAreaPost();
/*     */     } 
/*     */     
/*  81 */     RenderUtils2D.prepareGl();
/*  82 */     if (((Boolean)ClickGUI.instance.backgroundColor.getValue()).booleanValue()) {
/*     */       
/*  84 */       if (((Boolean)ClickGUI.instance.gradient.getValue()).booleanValue()) {
/*     */         
/*  86 */         GlStateManager.func_179118_c();
/*  87 */         Color trColor = ((Color)ClickGUI.instance.trColor.getValue()).getColorColor();
/*  88 */         Color tlColor = ((Color)ClickGUI.instance.tlColor.getValue()).getColorColor();
/*  89 */         Color brColor = ((Color)ClickGUI.instance.brColor.getValue()).getColorColor();
/*  90 */         Color blColor = ((Color)ClickGUI.instance.blColor.getValue()).getColorColor();
/*  91 */         RenderUtils2D.drawCustomRect(0.0F, 0.0F, this.field_146294_l, this.field_146295_m, (new Color(trColor.getRed(), trColor.getGreen(), trColor.getBlue(), ((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.trColor.getValue()).getAlpha() * alphaFactor()) : ((Color)ClickGUI.instance.trColor.getValue()).getAlpha())).getRGB(), (new Color(tlColor.getRed(), tlColor.getGreen(), tlColor.getBlue(), ((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.tlColor.getValue()).getAlpha() * alphaFactor()) : ((Color)ClickGUI.instance.tlColor.getValue()).getAlpha())).getRGB(), (new Color(blColor.getRed(), blColor.getGreen(), blColor.getBlue(), ((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.blColor.getValue()).getAlpha() * alphaFactor()) : ((Color)ClickGUI.instance.blColor.getValue()).getAlpha())).getRGB(), (new Color(brColor.getRed(), brColor.getGreen(), brColor.getBlue(), ((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.brColor.getValue()).getAlpha() * alphaFactor()) : ((Color)ClickGUI.instance.brColor.getValue()).getAlpha())).getRGB());
/*  92 */         GlStateManager.func_179141_d();
/*     */       }
/*     */       else {
/*     */         
/*  96 */         Color bgColor = ((Color)ClickGUI.instance.bgColor.getValue()).getColorColor();
/*  97 */         RenderUtils2D.drawRect(0.0F, 0.0F, this.field_146294_l, this.field_146295_m, (new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), ((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue() ? (int)(((Color)ClickGUI.instance.bgColor.getValue()).getAlpha() * alphaFactor()) : ((Color)ClickGUI.instance.bgColor.getValue()).getAlpha())).getRGB());
/*     */       } 
/*  99 */       MinecraftForge.EVENT_BUS.post((Event)new GuiScreenEvent.BackgroundDrawnEvent(this));
/*     */     } 
/*     */     
/* 102 */     ClickGUIFinal.description = null;
/*     */     
/* 104 */     if (GUIManager.isParticle()) {
/*     */       
/* 106 */       ParticleUtil.render();
/* 107 */       RenderUtils2D.prepareGl();
/* 108 */       GL11.glEnable(3042);
/*     */     } 
/*     */     
/* 111 */     HUDEditorRenderer.instance.drawHUDElements(mouseX, mouseY, partialTicks);
/*     */     
/* 113 */     if (((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue()) {
/*     */ 
/*     */       
/* 116 */       if (ModuleManager.getModule(HUDEditor.class).isDisabled()) {
/*     */         
/* 118 */         if (this.guiAnimateTimer.passed(1.0D)) {
/*     */           
/* 120 */           this.delta -= (this.field_146297_k.field_71462_r.field_146295_m - this.delta + 1.0F) * ((Float)ClickGUI.instance.guiMoveSpeed.getValue()).floatValue();
/* 121 */           this.guiAnimateTimer.reset();
/*     */         } 
/*     */         
/* 124 */         if (this.delta <= 0.0F) {
/* 125 */           this.delta = 0.0F;
/*     */         }
/*     */       } else {
/*     */         
/* 129 */         if (this.guiAnimateTimer.passed(1.0D)) {
/*     */           
/* 131 */           this.delta += (this.field_146297_k.field_71462_r.field_146295_m - this.delta) / 5.0F * ((Float)ClickGUI.instance.guiMoveSpeed.getValue()).floatValue();
/* 132 */           this.guiAnimateTimer.reset();
/*     */         } 
/*     */         
/* 135 */         if (this.delta >= this.field_146297_k.field_71462_r.field_146295_m)
/*     */         {
/* 137 */           this.delta = this.field_146297_k.field_71462_r.field_146295_m;
/*     */         }
/*     */       } 
/*     */       
/* 141 */       GL11.glTranslatef(0.0F, this.field_146297_k.field_71462_r.field_146295_m - this.delta, 0.0F);
/*     */     } 
/*     */     
/* 144 */     RenderUtils2D.prepareGl();
/* 145 */     HUDEditorRenderer.instance.drawScreen(mouseX, mouseY, this.delta, partialTicks);
/*     */     
/* 147 */     this.flag = 1;
/*     */     
/* 149 */     if (ClickGUIFinal.description != null)
/*     */     {
/* 151 */       if (MoreClickGUI.instance.descriptionMode.getValue() == MoreClickGUI.DescriptionMode.MouseTag) {
/*     */         
/* 153 */         RenderUtils2D.drawRect((((Vec2I)ClickGUIFinal.description.b).x + 10), ((Vec2I)ClickGUIFinal.description.b).y, (((Vec2I)ClickGUIFinal.description.b).x + 12 + FontManager.getWidth((String)ClickGUIFinal.description.a)), (((Vec2I)ClickGUIFinal.description.b).y + FontManager.getHeight() + 4), -2063597568);
/* 154 */         RenderUtils2D.drawRectOutline((((Vec2I)ClickGUIFinal.description.b).x + 10), ((Vec2I)ClickGUIFinal.description.b).y, (((Vec2I)ClickGUIFinal.description.b).x + 12 + FontManager.getWidth((String)ClickGUIFinal.description.a)), (((Vec2I)ClickGUIFinal.description.b).y + FontManager.getHeight() + 4), GUIManager.getColor4I(), false, false);
/* 155 */         FontManager.draw((String)ClickGUIFinal.description.a, (((Vec2I)ClickGUIFinal.description.b).x + 11), (((Vec2I)ClickGUIFinal.description.b).y + 4), ClickGUIFinal.white);
/*     */       } 
/*     */     }
/*     */     
/* 159 */     if (MoreClickGUI.instance.descriptionMode.getValue() == MoreClickGUI.DescriptionMode.Hub) {
/* 160 */       ClickGUIFinal.drawDescriptionHub(mouseX, mouseY, ClickGUIFinal.descriptionHubDragging);
/*     */     }
/* 162 */     if (GUIManager.isParticle()) {
/* 163 */       GL11.glDisable(3042);
/*     */     }
/* 165 */     if (ModuleManager.getModule(HUDEditor.class).isDisabled() && ((Boolean)ClickGUI.instance.guiMove.getValue()).booleanValue())
/*     */     {
/* 167 */       if (this.field_146297_k.field_71462_r instanceof HUDEditorFinal && this.delta <= 0.0F) {
/*     */         
/* 169 */         if (Particles.INSTANCE.isEnabled()) {
/* 170 */           ParticleUtil.clearParticles();
/*     */         }
/* 172 */         this.field_146297_k.func_147108_a(null);
/*     */       } 
/*     */     }
/* 175 */     RenderUtils2D.releaseGl();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73864_a(int mouseX, int mouseY, int mouseButton) {
/* 181 */     HUDEditorRenderer.instance.mouseClicked(mouseX, mouseY, mouseButton);
/* 182 */     if (mouseX >= Math.min(ClickGUIFinal.descriptionHubX, ClickGUIFinal.descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubLength.getValue()).intValue()) && mouseX <= Math.max(ClickGUIFinal.descriptionHubX, ClickGUIFinal.descriptionHubX + ((Integer)MoreClickGUI.instance.descriptionModeHubLength.getValue()).intValue()) && mouseY >= Math.min(ClickGUIFinal.descriptionHubY, ClickGUIFinal.descriptionHubY + ClickGUIFinal.descriptionHubHeight) && mouseY <= Math.max(ClickGUIFinal.descriptionHubY, ClickGUIFinal.descriptionHubY + ClickGUIFinal.descriptionHubHeight)) {
/*     */       
/* 184 */       ClickGUIFinal.descriptionHubDragging = true;
/* 185 */       ClickGUIFinal.descriptionHubX2 = ClickGUIFinal.descriptionHubX - mouseX;
/* 186 */       ClickGUIFinal.descriptionHubY2 = ClickGUIFinal.descriptionHubY - mouseY;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_73869_a(char typedChar, int keyCode) {
/* 193 */     HUDEditorRenderer.instance.keyTyped(typedChar, keyCode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_146286_b(int mouseX, int mouseY, int state) {
/* 199 */     HUDEditorRenderer.instance.mouseReleased(mouseX, mouseY, state);
/* 200 */     ClickGUIFinal.descriptionHubDragging = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\gui\HUDEditorFinal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */