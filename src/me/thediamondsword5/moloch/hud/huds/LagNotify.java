/*     */ package me.thediamondsword5.moloch.hud.huds;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*     */ import net.minecraft.util.math.Vec2f;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.spartanb312.base.client.FontManager;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.engine.AsyncRenderer;
/*     */ import net.spartanb312.base.event.events.network.PacketEvent;
/*     */ import net.spartanb312.base.hud.HUDModule;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.utils.ColorUtil;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ 
/*     */ @ModuleInfo(name = "LagNotify", category = Category.HUD, description = "Alerts you when your internet is down or when the server is lagging")
/*     */ public class LagNotify
/*     */   extends HUDModule {
/*  24 */   Setting<Integer> timeout = setting("Timeout", 500, 1, 1500).des("Amount of milliseconds passed from last packet sent before you are considered to be lagging");
/*  25 */   Setting<Boolean> rubberband = setting("Rubberband", false).des("Detects if you have recently rubberbanded");
/*  26 */   Setting<Integer> rubberbandTimeout = setting("RubberbandTimeout", 1500, 1, 10000).des("Amount of milliseconds passed from last rubberband to stop showing warning").whenTrue(this.rubberband);
/*  27 */   Setting<Boolean> textShadow = setting("TextShadow", true).des("Draws shadow under text");
/*  28 */   Setting<Boolean> fade = setting("Fade", true).des("Warnings fade in and out");
/*  29 */   Setting<Float> fadeInSpeed = setting("FadeInSpeed", 1.5F, 0.1F, 3.0F).des("Fade speed when rendering warning in").whenTrue(this.fade);
/*  30 */   Setting<Float> fadeOutSpeed = setting("FadeOutSpeed", 0.7F, 0.1F, 3.0F).des("Fade speed on stopping rendering waring").whenTrue(this.fade);
/*  31 */   Setting<Color> color = setting("Color", new Color((new Color(255, 100, 100, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 100, 255));
/*     */   
/*  33 */   private final Timer internetTimer = new Timer();
/*  34 */   private final Timer packetTimer = new Timer();
/*  35 */   private final Timer rubberbandTimer = new Timer();
/*  36 */   private final Timer alphaTimer = new Timer();
/*  37 */   private float alphaFactor = 5.0F;
/*     */   private boolean isInternetDown = false;
/*  39 */   private String lagStr = "";
/*     */   
/*     */   public LagNotify() {
/*  42 */     this.asyncRenderer = new AsyncRenderer()
/*     */       {
/*     */         public void onUpdate(ScaledResolution resolution, int mouseX, int mouseY) {
/*  45 */           LagNotify.this.width = FontManager.getWidthHUD("Server hasn't responded for 9999999 ms");
/*  46 */           LagNotify.this.height = FontManager.getHeight();
/*     */           
/*  48 */           if ((((Boolean)LagNotify.this.fade.getValue()).booleanValue() && LagNotify.this.alphaFactor > 5.0F) || LagNotify.this.isInternetDown || LagNotify.this.packetTimer.passed(((Integer)LagNotify.this.timeout.getValue()).intValue()) || (((Boolean)LagNotify.this.rubberband.getValue()).booleanValue() && !LagNotify.this.rubberbandTimer.passed(((Integer)LagNotify.this.rubberbandTimeout.getValue()).intValue()))) {
/*  49 */             drawAsyncCenteredString(LagNotify.this.lagStr, LagNotify.this.x + LagNotify.this.width * 0.5F, LagNotify.this.y, (new Color(((Color)LagNotify.this.color.getValue()).getColorColor().getRed(), ((Color)LagNotify.this.color.getValue()).getColorColor().getGreen(), ((Color)LagNotify.this.color.getValue()).getColorColor().getBlue(), (int)(((Color)LagNotify.this.color.getValue()).getAlpha() * LagNotify.this.alphaFactor / 300.0F))).getRGB(), ((Boolean)LagNotify.this.textShadow.getValue()).booleanValue());
/*     */           }
/*     */           
/*  52 */           if (LagNotify.this.isInternetDown) {
/*  53 */             Color color1 = new Color(((Color)LagNotify.this.color.getValue()).getColorColor().getRed(), ((Color)LagNotify.this.color.getValue()).getColorColor().getGreen(), ((Color)LagNotify.this.color.getValue()).getColorColor().getBlue(), (int)(((Color)LagNotify.this.color.getValue()).getAlpha() * LagNotify.this.alphaFactor / 300.0F));
/*  54 */             Color color2 = ColorUtil.colorHSBChange(color1, 0.5F, ColorUtil.ColorHSBMode.Brightness);
/*     */             
/*  56 */             drawAsyncIcon("*", LagNotify.this.x + LagNotify.this.width * 0.5F - FontManager.getWidthHUD(LagNotify.this.lagStr) * 0.5F - FontManager.iconFont.getStringWidth("*") - 5.0F, LagNotify.this.y, ColorUtil.colorShift(color1, color2, 150.0F + (float)(150.0D * Math.sin(System.currentTimeMillis() / 2.0D % 300.0D * 0.020943951023931952D))).getRGB());
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/*  64 */     this.packetTimer.reset();
/*     */ 
/*     */     
/*  67 */     if (((Boolean)this.rubberband.getValue()).booleanValue() && event.getPacket() instanceof SPacketPlayerPosLook && mc.field_71439_g.field_70173_aa >= 20.0F) {
/*  68 */       SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
/*  69 */       double rubberbandDist = (new Vec3d(packet.field_148940_a, packet.field_148938_b, packet.field_148939_c)).func_178788_d(mc.field_71439_g.func_174791_d()).func_72433_c();
/*  70 */       Vec2f rubberbandRotate = new Vec2f(packet.field_148936_d - mc.field_71439_g.field_70177_z, packet.field_148937_e - mc.field_71439_g.field_70125_A);
/*     */       
/*  72 */       if (rubberbandDist > 0.5D || Math.sqrt((rubberbandRotate.field_189982_i * rubberbandRotate.field_189982_i + rubberbandRotate.field_189983_j * rubberbandRotate.field_189983_j)) > 1.0D) {
/*  73 */         this.rubberbandTimer.reset();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  80 */     if (((Boolean)this.fade.getValue()).booleanValue()) {
/*  81 */       int passedms = (int)this.alphaTimer.hasPassed();
/*  82 */       this.alphaTimer.reset();
/*  83 */       if (passedms < 1000) {
/*  84 */         if (this.isInternetDown || this.packetTimer.passed(((Integer)this.timeout.getValue()).intValue()) || (((Boolean)this.rubberband.getValue()).booleanValue() && !this.rubberbandTimer.passed(((Integer)this.rubberbandTimeout.getValue()).intValue()))) {
/*  85 */           this.alphaFactor += passedms * ((Float)this.fadeInSpeed.getValue()).floatValue();
/*  86 */           if (this.alphaFactor > 300.0F) {
/*  87 */             this.alphaFactor = 300.0F;
/*     */           }
/*     */         } else {
/*     */           
/*  91 */           this.alphaFactor -= passedms * ((Float)this.fadeOutSpeed.getValue()).floatValue();
/*  92 */           if (this.alphaFactor < 5.0F) {
/*  93 */             this.alphaFactor = 5.0F;
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  99 */     if (this.internetTimer.passed(250.0D)) {
/* 100 */       updateInternet();
/* 101 */       this.internetTimer.reset();
/*     */     } 
/*     */     
/* 104 */     if (((Boolean)this.rubberband.getValue()).booleanValue() && !this.rubberbandTimer.passed(((Integer)this.rubberbandTimeout.getValue()).intValue())) {
/* 105 */       this.lagStr = "Rubberbanded " + this.rubberbandTimer.hasPassed() + " ms ago";
/*     */     }
/*     */     
/* 108 */     if (this.isInternetDown) {
/* 109 */       this.lagStr = "Ur internet's fucked";
/*     */     }
/* 111 */     else if (this.packetTimer.passed(((Integer)this.timeout.getValue()).intValue())) {
/* 112 */       this.lagStr = "Server hasn't responded for " + this.packetTimer.hasPassed() + " ms";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onHUDRender(ScaledResolution resolution) {
/* 118 */     this.asyncRenderer.onRender();
/*     */   }
/*     */   
/*     */   private void updateInternet() {
/*     */     try {
/* 123 */       Socket socket = new Socket();
/* 124 */       socket.connect(new InetSocketAddress("1.1.1.1", 80), 100);
/* 125 */       socket.close();
/* 126 */       this.isInternetDown = false;
/* 127 */     } catch (Exception e) {
/* 128 */       this.isInternetDown = true;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\hud\huds\LagNotify.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */