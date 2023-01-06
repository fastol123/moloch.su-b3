/*    */ package me.thediamondsword5.moloch.module.modules.client;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.render.DrawScreenEvent;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.event.Listener;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ import net.spartanb312.base.utils.Timer;
/*    */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "Blur", category = Category.CLIENT, description = "!! TURN OFF FAST RENDER IN OPTIFINE !! Blurs background of GUI")
/*    */ public class Blur
/*    */   extends Module
/*    */ {
/*    */   public static Blur INSTANCE;
/* 26 */   private final Timer timer = new Timer();
/* 27 */   public int blurThreader = 0;
/*    */   
/* 29 */   public Setting<Boolean> blurClickGUI = setting("BlurClickGUI", true);
/* 30 */   public Setting<Float> blurFactor = setting("BlurFactor", 1.0F, 0.0F, 2.0F).des("Blur Intensity");
/* 31 */   Setting<Boolean> blurChat = setting("BlurChat", false).des("Blurs chat background when chat is open");
/* 32 */   Setting<Boolean> blurOtherGUI = setting("BlurMiscGUI", false).des("Blur When Opened Inventory Or Containers");
/* 33 */   Setting<Float> blurGUISpeed = setting("BlurGUISpeed", 1.0F, 0.1F, 5.0F).des("GUI Blur Speed").whenTrue(this.blurOtherGUI);
/*    */   
/*    */   public Blur() {
/* 36 */     INSTANCE = this;
/*    */   }
/*    */   
/*    */   @Listener
/*    */   public void onDrawScreenOther(DrawScreenEvent.Layer1 event) {
/* 41 */     if (!mc.field_71456_v.func_146158_b().func_146241_e() && 
/* 42 */       Particles.isOtherGUIOpen() && ((Boolean)this.blurOtherGUI.getValue()).booleanValue()) {
/* 43 */       RenderUtils2D.drawBlurAreaPre(((Float)this.blurFactor.getValue()).floatValue() * this.blurThreader / 300.0F, mc.func_184121_ak());
/* 44 */       RenderUtils2D.drawBlurRect(Tessellator.func_178181_a(), Tessellator.func_178181_a().func_178180_c(), 0.0F, 0.0F, mc.field_71443_c, mc.field_71440_d);
/* 45 */       RenderUtils2D.drawBlurAreaPost();
/*    */       
/* 47 */       GL11.glEnable(2929);
/* 48 */       GL11.glEnable(3042);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Listener
/*    */   public void onDrawScreenChat(DrawScreenEvent.Chat event) {
/* 55 */     if (mc.field_71456_v.func_146158_b().func_146241_e() && ((Boolean)this.blurChat.getValue()).booleanValue()) {
/* 56 */       GL11.glPushMatrix();
/* 57 */       RenderUtils2D.drawBlurAreaPre(((Float)this.blurFactor.getValue()).floatValue() * this.blurThreader / 300.0F, mc.func_184121_ak());
/* 58 */       RenderUtils2D.drawBlurRect(Tessellator.func_178181_a(), Tessellator.func_178181_a().func_178180_c(), 0.0F, 0.0F, mc.field_71443_c, mc.field_71440_d);
/* 59 */       RenderUtils2D.drawBlurAreaPost();
/* 60 */       GL11.glPopMatrix();
/* 61 */       ScaledResolution scale = new ScaledResolution(mc);
/* 62 */       GL11.glScalef(scale.func_78325_e(), scale.func_78325_e(), 1.0F);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 68 */     if (!Particles.isOtherGUIOpen() && !mc.field_71456_v.func_146158_b().func_146241_e() && this.blurThreader != 0) {
/* 69 */       this.blurThreader = 0;
/*    */     }
/*    */     
/* 72 */     if ((mc.field_71456_v.func_146158_b().func_146241_e() && ((Boolean)this.blurChat.getValue()).booleanValue()) || (!mc.field_71456_v.func_146158_b().func_146241_e() && Particles.isOtherGUIOpen() && ((Boolean)this.blurOtherGUI.getValue()).booleanValue())) {
/* 73 */       int passedms = (int)this.timer.hasPassed();
/* 74 */       this.timer.reset();
/* 75 */       if (passedms < 1000) {
/* 76 */         this.blurThreader = (int)(this.blurThreader + ((Float)this.blurGUISpeed.getValue()).floatValue() / 10.0F * passedms);
/* 77 */         if (this.blurThreader > 300)
/* 78 */           this.blurThreader = 300; 
/* 79 */         if (this.blurThreader < 0)
/* 80 */           this.blurThreader = 0; 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\client\Blur.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */