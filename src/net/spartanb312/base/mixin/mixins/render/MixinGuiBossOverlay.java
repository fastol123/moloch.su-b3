/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.client.gui.BossInfoClient;
/*    */ import net.minecraft.client.gui.GuiBossOverlay;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.world.BossInfo;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.module.modules.visuals.NoRender;
/*    */ import net.spartanb312.base.utils.ItemUtils;
/*    */ import net.spartanb312.base.utils.math.Pair;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({GuiBossOverlay.class})
/*    */ public class MixinGuiBossOverlay
/*    */ {
/*    */   @Inject(method = {"renderBossHealth"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void renderBossHealthHookPre(CallbackInfo ci) {
/* 25 */     if (ModuleManager.getModule(NoRender.class).isEnabled() && NoRender.INSTANCE.bossBar.getValue() == NoRender.BossBarMode.Stack) {
/*    */       
/* 27 */       Map<UUID, BossInfoClient> map = (ItemUtils.mc.field_71456_v.func_184046_j()).field_184060_g;
/* 28 */       ScaledResolution scaledResolution = new ScaledResolution(ItemUtils.mc);
/* 29 */       int scaledWidth = scaledResolution.func_78326_a();
/* 30 */       int width = (int)(scaledWidth / ((Float)NoRender.INSTANCE.bossBarSize.getValue()).floatValue() / 2.0F - 91.0F);
/* 31 */       int i = 12;
/*    */       
/* 33 */       if (NoRender.INSTANCE.bossBar.getValue() == NoRender.BossBarMode.Stack) {
/* 34 */         HashMap<String, Pair<BossInfoClient, Integer>> map2 = new HashMap<>();
/*    */         
/* 36 */         for (Map.Entry<UUID, BossInfoClient> entry : map.entrySet()) {
/*    */ 
/*    */           
/* 39 */           String s = ((BossInfoClient)entry.getValue()).func_186744_e().func_150254_d();
/* 40 */           if (map2.containsKey(s)) {
/* 41 */             Pair<BossInfoClient, Integer> pair1 = map2.get(s);
/* 42 */             pair1 = new Pair(pair1.a, Integer.valueOf(((Integer)pair1.b).intValue() + 1));
/* 43 */             map2.put(s, pair1);
/*    */             continue;
/*    */           } 
/* 46 */           Pair<BossInfoClient, Integer> pair = new Pair(entry.getValue(), Integer.valueOf(1));
/* 47 */           map2.put(s, pair);
/*    */         } 
/*    */ 
/*    */         
/* 51 */         for (Map.Entry<String, Pair<BossInfoClient, Integer>> entry : map2.entrySet()) {
/*    */           
/* 53 */           String text = entry.getKey();
/* 54 */           BossInfoClient info = (BossInfoClient)((Pair)entry.getValue()).a;
/* 55 */           int i3 = ((Integer)((Pair)entry.getValue()).b).intValue();
/* 56 */           text = text + " x" + i3;
/*    */           
/* 58 */           GL11.glScalef(((Float)NoRender.INSTANCE.bossBarSize.getValue()).floatValue(), ((Float)NoRender.INSTANCE.bossBarSize.getValue()).floatValue(), 1.0F);
/*    */           
/* 60 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 61 */           ItemUtils.mc.func_110434_K().func_110577_a(GuiBossOverlay.field_184058_a);
/* 62 */           ItemUtils.mc.field_71456_v.func_184046_j().func_184052_a(width, i, (BossInfo)info);
/* 63 */           ItemUtils.mc.field_71466_p.func_175063_a(text, scaledWidth / ((Float)NoRender.INSTANCE.bossBarSize.getValue()).floatValue() / 2.0F - ItemUtils.mc.field_71466_p.func_78256_a(text) / 2.0F, (i - 9), 16777215);
/*    */           
/* 65 */           GL11.glScalef(1.0F / ((Float)NoRender.INSTANCE.bossBarSize.getValue()).floatValue(), 1.0F / ((Float)NoRender.INSTANCE.bossBarSize.getValue()).floatValue(), 1.0F);
/*    */           
/* 67 */           i += 10 + ItemUtils.mc.field_71466_p.field_78288_b;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 73 */     if (ModuleManager.getModule(NoRender.class).isEnabled() && NoRender.INSTANCE.bossBar.getValue() != NoRender.BossBarMode.None)
/* 74 */       ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinGuiBossOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */