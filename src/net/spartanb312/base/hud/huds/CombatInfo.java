/*     */ package net.spartanb312.base.hud.huds;
/*     */ 
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.item.Item;
/*     */ import net.spartanb312.base.client.FontManager;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.engine.AsyncRenderer;
/*     */ import net.spartanb312.base.hud.HUDModule;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.ChatUtil;
/*     */ import net.spartanb312.base.utils.ItemUtils;
/*     */ 
/*     */ @ModuleInfo(name = "CombatInfo", category = Category.HUD)
/*     */ public class CombatInfo extends HUDModule {
/*  20 */   public Setting<Boolean> shadow = setting("Shadow", true).des("Draw Shadow Under Combat Info");
/*     */   
/*  22 */   Setting<Boolean> items = setting("Items", true);
/*  23 */   Setting<Boolean> goldenApple = setting("GoldenApple", true).whenTrue(this.items);
/*  24 */   Setting<Boolean> expBottle = setting("ExpBottle", true).whenTrue(this.items);
/*  25 */   Setting<Boolean> crystal = setting("Crystal", true).whenTrue(this.items);
/*  26 */   Setting<Boolean> totem = setting("Totem", true).whenTrue(this.items);
/*  27 */   Setting<Boolean> obsidian = setting("Obsidian", true).whenTrue(this.items);
/*     */   
/*  29 */   Setting<Boolean> strength = setting("Strength", false);
/*  30 */   Setting<Boolean> weakness = setting("Weakness", false);
/*     */   
/*     */   public CombatInfo() {
/*  33 */     this.asyncRenderer = new AsyncRenderer()
/*     */       {
/*     */         public void onUpdate(ScaledResolution resolution, int mouseX, int mouseY) {
/*  36 */           int maxWidth = 10;
/*  37 */           int startY = CombatInfo.this.y;
/*  38 */           if (((Boolean)CombatInfo.this.items.getValue()).booleanValue()) {
/*  39 */             if (((Boolean)CombatInfo.this.goldenApple.getValue()).booleanValue()) {
/*  40 */               String text = "GAP " + CombatInfo.this.getColoredCount(ItemUtils.getItemCount(Items.field_151153_ao), 64);
/*  41 */               maxWidth = Math.max(maxWidth, FontManager.getWidthHUD(text));
/*  42 */               drawAsyncString(text, CombatInfo.this.x, startY, ((Boolean)CombatInfo.this.shadow.getValue()).booleanValue());
/*  43 */               startY += FontManager.getHeight();
/*     */             } 
/*  45 */             if (((Boolean)CombatInfo.this.expBottle.getValue()).booleanValue()) {
/*  46 */               String text = "EXP " + CombatInfo.this.getColoredCount(ItemUtils.getItemCount(Items.field_151062_by), 64);
/*  47 */               maxWidth = Math.max(maxWidth, FontManager.getWidthHUD(text));
/*  48 */               drawAsyncString(text, CombatInfo.this.x, startY, ((Boolean)CombatInfo.this.shadow.getValue()).booleanValue());
/*  49 */               startY += FontManager.getHeight();
/*     */             } 
/*  51 */             if (((Boolean)CombatInfo.this.crystal.getValue()).booleanValue()) {
/*  52 */               String text = "CRY " + CombatInfo.this.getColoredCount(ItemUtils.getItemCount(Items.field_185158_cP), 64);
/*  53 */               maxWidth = Math.max(maxWidth, FontManager.getWidthHUD(text));
/*  54 */               drawAsyncString(text, CombatInfo.this.x, startY, ((Boolean)CombatInfo.this.shadow.getValue()).booleanValue());
/*  55 */               startY += FontManager.getHeight();
/*     */             } 
/*  57 */             if (((Boolean)CombatInfo.this.totem.getValue()).booleanValue()) {
/*  58 */               String text = "TOU " + CombatInfo.this.getColoredCount(ItemUtils.getItemCount(Items.field_190929_cY), 1);
/*  59 */               maxWidth = Math.max(maxWidth, FontManager.getWidthHUD(text));
/*  60 */               drawAsyncString(text, CombatInfo.this.x, startY, ((Boolean)CombatInfo.this.shadow.getValue()).booleanValue());
/*  61 */               startY += FontManager.getHeight();
/*     */             } 
/*  63 */             if (((Boolean)CombatInfo.this.obsidian.getValue()).booleanValue()) {
/*  64 */               String text = "OBS " + CombatInfo.this.getColoredCount(ItemUtils.getItemCount(Item.func_150898_a(Blocks.field_150343_Z)), 64);
/*  65 */               maxWidth = Math.max(maxWidth, FontManager.getWidthHUD(text));
/*  66 */               drawAsyncString(text, CombatInfo.this.x, startY, ((Boolean)CombatInfo.this.shadow.getValue()).booleanValue());
/*  67 */               startY += FontManager.getHeight();
/*     */             } 
/*     */           } 
/*     */           
/*  71 */           if (startY != CombatInfo.this.y) startY += FontManager.getHeight();
/*     */           
/*  73 */           if (((Boolean)CombatInfo.this.strength.getValue()).booleanValue()) {
/*  74 */             String text = (Module.mc.field_71439_g.func_70644_a(MobEffects.field_76420_g) ? ChatUtil.colored("a") : ChatUtil.colored("f")) + "STR";
/*  75 */             maxWidth = Math.max(maxWidth, FontManager.getWidthHUD(text));
/*  76 */             drawAsyncString(text, CombatInfo.this.x, startY, ((Boolean)CombatInfo.this.shadow.getValue()).booleanValue());
/*  77 */             startY += FontManager.getHeight();
/*     */           } 
/*  79 */           if (((Boolean)CombatInfo.this.weakness.getValue()).booleanValue()) {
/*  80 */             String text = (Module.mc.field_71439_g.func_70644_a(MobEffects.field_76437_t) ? ChatUtil.colored("c") : ChatUtil.colored("f")) + "WEK";
/*  81 */             maxWidth = Math.max(maxWidth, FontManager.getWidthHUD(text));
/*  82 */             drawAsyncString(text, CombatInfo.this.x, startY, ((Boolean)CombatInfo.this.shadow.getValue()).booleanValue());
/*  83 */             startY += FontManager.getHeight();
/*     */           } 
/*     */           
/*  86 */           if (startY == CombatInfo.this.y) startY += FontManager.getHeight();
/*     */           
/*  88 */           CombatInfo.this.width = maxWidth;
/*  89 */           CombatInfo.this.height = startY - CombatInfo.this.y;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private String getColoredCount(int count, int stackSize) {
/*  95 */     if (count < stackSize) return ChatUtil.colored("c") + count; 
/*  96 */     if (count < stackSize * 2) return ChatUtil.colored("e") + count; 
/*  97 */     return ChatUtil.colored("a") + count;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onHUDRender(ScaledResolution resolution) {
/* 102 */     this.asyncRenderer.onRender();
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\hud\huds\CombatInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */