/*     */ package net.spartanb312.base.module.modules.other;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketChatMessage;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.concurrent.ConcurrentTaskManager;
/*     */ import net.spartanb312.base.core.concurrent.repeat.RepeatUnit;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.FileUtil;
/*     */ 
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "Spammer", category = Category.OTHER, description = "Automatically spam")
/*     */ public class Spammer
/*     */   extends Module
/*     */ {
/*  25 */   Setting<Integer> delay = setting("DelayS", 10, 1, 100);
/*  26 */   Setting<Boolean> randomSuffix = setting("Random", false);
/*  27 */   Setting<Boolean> greenText = setting("GreenText", false);
/*     */   
/*     */   private static final String fileName = "moloch.su/spammer/Spammer.txt";
/*     */   private static final String defaultMessage = "hello world";
/*  31 */   private static final List<String> spamMessages = new ArrayList<>();
/*  32 */   private static final Random rnd = new Random();
/*     */   
/*  34 */   RepeatUnit fileChangeListener = new RepeatUnit(1000, this::readSpamFile);
/*     */   public Spammer() {
/*  36 */     this.runner = new RepeatUnit(() -> ((Integer)this.delay.getValue()).intValue() * 1000, () -> {
/*     */           if (mc.field_71439_g == null) {
/*     */             disable();
/*     */           } else if (spamMessages.size() > 0) {
/*     */             String messageOut;
/*     */             
/*     */             if (((Boolean)this.randomSuffix.getValue()).booleanValue()) {
/*     */               int index = rnd.nextInt(spamMessages.size());
/*     */               
/*     */               messageOut = spamMessages.get(index);
/*     */               spamMessages.remove(index);
/*     */             } else {
/*     */               messageOut = spamMessages.get(0);
/*     */               spamMessages.remove(0);
/*     */             } 
/*     */             spamMessages.add(messageOut);
/*     */             if (((Boolean)this.greenText.getValue()).booleanValue()) {
/*     */               messageOut = "> " + messageOut;
/*     */             }
/*     */             mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketChatMessage(messageOut.replaceAll("§", "")));
/*     */           } 
/*     */         });
/*  58 */     this.runner.suspend();
/*  59 */     ConcurrentTaskManager.runRepeat(this.runner);
/*  60 */     ConcurrentTaskManager.runRepeat(this.fileChangeListener);
/*     */   }
/*     */   RepeatUnit runner;
/*     */   
/*     */   public void onEnable() {
/*  65 */     if (mc.field_71439_g == null) {
/*  66 */       disable();
/*     */       return;
/*     */     } 
/*  69 */     this.runner.resume();
/*  70 */     readSpamFile();
/*  71 */     this.moduleEnableFlag = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  77 */     spamMessages.clear();
/*  78 */     this.runner.suspend();
/*  79 */     this.moduleDisableFlag = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readSpamFile() {
/*  85 */     File file = new File("moloch.su/spammer/Spammer.txt");
/*  86 */     if (!file.exists()) {
/*  87 */       file.getParentFile().mkdirs();
/*     */       try {
/*  89 */         file.createNewFile();
/*  90 */       } catch (Exception exception) {}
/*     */     } 
/*     */     
/*  93 */     List<String> fileInput = FileUtil.readTextFileAllLines("moloch.su/spammer/Spammer.txt");
/*  94 */     Iterator<String> i = fileInput.iterator();
/*  95 */     spamMessages.clear();
/*  96 */     while (i.hasNext()) {
/*  97 */       String s = i.next();
/*  98 */       if (s.replaceAll("\\s", "").isEmpty())
/*  99 */         continue;  spamMessages.add(s);
/*     */     } 
/*     */     
/* 102 */     if (spamMessages.size() == 0)
/* 103 */       spamMessages.add("hello world"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\module\modules\other\Spammer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */