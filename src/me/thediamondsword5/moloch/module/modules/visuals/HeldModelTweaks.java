/*     */ package me.thediamondsword5.moloch.module.modules.visuals;
/*     */ 
/*     */ import me.thediamondsword5.moloch.event.events.render.ItemModelEvent;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.event.Listener;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.MathUtilFuckYou;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "HeldModelTweaks", category = Category.VISUALS, description = "Changes stuff about your held items in your viewmodel")
/*     */ public class HeldModelTweaks
/*     */   extends Module {
/*  18 */   Setting<Page> page = setting("Page", Page.ItemModel);
/*     */   
/*  20 */   Setting<ItemModelPage> itemModelPage = setting("ItemModelPage", ItemModelPage.Main);
/*  21 */   Setting<Boolean> hitProgressMain = setting("HitProgressMain", false).des("Modifies hit progress of mainhand item").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*  22 */   Setting<Float> hitProgressMainOffset = setting("HitProgressMainOffset", 0.3F, 0.0F, 1.0F).des("Hit offset of mainhand item").whenTrue(this.hitProgressMain).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*  23 */   Setting<Float> mainX = setting("MainX", 0.0F, -5.0F, 5.0F).des("X offset of mainhand item").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*  24 */   Setting<Float> mainY = setting("MainY", 0.0F, -5.0F, 5.0F).des("Y offset of mainhand item").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*  25 */   Setting<Float> mainZ = setting("MainZ", 0.0F, -5.0F, 5.0F).des("Z offset of mainhand item").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*     */   
/*  27 */   Setting<Float> mainRotateX = setting("MainRotateX", 0.0F, -180.0F, 180.0F).des("Rotation of mainhand item on X axis").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*  28 */   Setting<Float> mainRotateY = setting("MainRotateY", 0.0F, -180.0F, 180.0F).des("Rotation of mainhand item on Y axis").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*  29 */   Setting<Float> mainRotateZ = setting("MainRotateZ", 0.0F, -180.0F, 180.0F).des("Rotation of mainhand item on Z axis").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*     */   
/*  31 */   Setting<Float> mainScaleX = setting("MainScaleX", 1.0F, 0.1F, 4.0F).des("Scale of mainhand item on X axis").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*  32 */   Setting<Float> mainScaleY = setting("MainScaleY", 1.0F, 0.1F, 4.0F).des("Scale of mainhand item on Y axis").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*  33 */   Setting<Float> mainScaleZ = setting("MainScaleZ", 1.0F, 0.1F, 4.0F).des("Scale of mainhand item on Z axis").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*     */   
/*  35 */   Setting<Boolean> eatingModifyMain = setting("EatingModifyMain", false).des("Move mainhand item while you are eating from it").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*     */   
/*  37 */   Setting<Float> mainEatingX = setting("MainEatingX", 0.0F, -5.0F, 5.0F).des("X offset of mainhand item while eating").whenTrue(this.eatingModifyMain).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*  38 */   Setting<Float> mainEatingY = setting("MainEatingY", 0.0F, -5.0F, 5.0F).des("Y offset of mainhand item while eating").whenTrue(this.eatingModifyMain).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*  39 */   Setting<Float> mainEatingZ = setting("MainEatingZ", 0.0F, -5.0F, 5.0F).des("Z offset of mainhand item while eating").whenTrue(this.eatingModifyMain).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*     */   
/*  41 */   Setting<Float> mainEatingRotateX = setting("MainEatingRotateX", 0.0F, -180.0F, 180.0F).des("Rotation of mainhand item on X axis while eating").whenTrue(this.eatingModifyMain).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*  42 */   Setting<Float> mainEatingRotateY = setting("MainEatingRotateY", 0.0F, -180.0F, 180.0F).des("Rotation of mainhand item on Y axis while eating").whenTrue(this.eatingModifyMain).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*  43 */   Setting<Float> mainEatingRotateZ = setting("MainEatingRotateZ", 0.0F, -180.0F, 180.0F).des("Rotation of mainhand item on Z axis while eating").whenTrue(this.eatingModifyMain).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*     */   
/*  45 */   Setting<Float> mainEatingScaleX = setting("MainEatingScaleX", 1.0F, 0.1F, 4.0F).des("Scale of mainhand item on X axis while eating").whenTrue(this.eatingModifyMain).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*  46 */   Setting<Float> mainEatingScaleY = setting("MainEatingScaleY", 1.0F, 0.1F, 4.0F).des("Scale of mainhand item on Y axis while eating").whenTrue(this.eatingModifyMain).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*  47 */   Setting<Float> mainEatingScaleZ = setting("MainEatingScaleZ", 1.0F, 0.1F, 4.0F).des("Scale of mainhand item on Z axis while eating").whenTrue(this.eatingModifyMain).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Main);
/*     */ 
/*     */ 
/*     */   
/*  51 */   Setting<Boolean> hitProgressOff = setting("HitProgressOff", false).des("Modifies hit progress of offhand item").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*  52 */   Setting<Float> hitProgressOffOffset = setting("HitProgressOffOffset", 0.7F, 0.0F, 1.0F).des("Hit offset of offhand item").whenTrue(this.hitProgressOff).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*  53 */   Setting<Float> offX = setting("OffX", 0.0F, -5.0F, 5.0F).des("X offset of offhand item").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*  54 */   Setting<Float> offY = setting("OffY", 0.0F, -5.0F, 5.0F).des("Y offset of offhand item").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*  55 */   Setting<Float> offZ = setting("OffZ", 0.0F, -5.0F, 5.0F).des("Z offset of offhand item").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*     */   
/*  57 */   Setting<Float> offRotateX = setting("OffRotateX", 0.0F, -180.0F, 180.0F).des("Rotation of offhand item on X axis").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*  58 */   Setting<Float> offRotateY = setting("OffRotateY", 0.0F, -180.0F, 180.0F).des("Rotation of offhand item on Y axis").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*  59 */   Setting<Float> offRotateZ = setting("OffRotateZ", 0.0F, -180.0F, 180.0F).des("Rotation of offhand item on Z axis").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*     */   
/*  61 */   Setting<Float> offScaleX = setting("OffScaleX", 1.0F, 0.1F, 4.0F).des("Scale of offhand item on X axis").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*  62 */   Setting<Float> offScaleY = setting("OffScaleY", 1.0F, 0.1F, 4.0F).des("Scale of offhand item on Y axis").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*  63 */   Setting<Float> offScaleZ = setting("OffScaleZ", 1.0F, 0.1F, 4.0F).des("Scale of offhand item on Z axis").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*     */   
/*  65 */   Setting<Boolean> eatingModifyOff = setting("EatingModifyOff", false).des("Move offhand while you are eating from it").whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*     */   
/*  67 */   Setting<Float> offEatingX = setting("OffEatingX", 0.0F, -5.0F, 5.0F).des("X offset of offhand item while eating").whenTrue(this.eatingModifyOff).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*  68 */   Setting<Float> offEatingY = setting("OffEatingY", 0.0F, -5.0F, 5.0F).des("Y offset of offhand item while eating").whenTrue(this.eatingModifyOff).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*  69 */   Setting<Float> offEatingZ = setting("OffEatingZ", 0.0F, -5.0F, 5.0F).des("Z offset of offhand item while eating").whenTrue(this.eatingModifyOff).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*     */   
/*  71 */   Setting<Float> offEatingRotateX = setting("OffEatingRotateX", 0.0F, -180.0F, 180.0F).des("Rotation of offhand item on X axis while eating").whenTrue(this.eatingModifyOff).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*  72 */   Setting<Float> offEatingRotateY = setting("OffEatingRotateY", 0.0F, -180.0F, 180.0F).des("Rotation of offhand item on Y axis while eating").whenTrue(this.eatingModifyOff).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*  73 */   Setting<Float> offEatingRotateZ = setting("OffEatingRotateZ", 0.0F, -180.0F, 180.0F).des("Rotation of offhand item on Z axis while eating").whenTrue(this.eatingModifyOff).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*     */   
/*  75 */   Setting<Float> offEatingScaleX = setting("OffEatingScaleX", 1.0F, 0.1F, 4.0F).des("Scale of offhand item on X axis while eating").whenTrue(this.eatingModifyOff).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*  76 */   Setting<Float> offEatingScaleY = setting("OffEatingScaleY", 1.0F, 0.1F, 4.0F).des("Scale of offhand item on Y axis while eating").whenTrue(this.eatingModifyOff).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*  77 */   Setting<Float> offEatingScaleZ = setting("OffEatingScaleZ", 1.0F, 0.1F, 4.0F).des("Scale of offhand item on Z axis while eating").whenTrue(this.eatingModifyOff).whenAtMode(this.page, Page.ItemModel).whenAtMode(this.itemModelPage, ItemModelPage.Off);
/*     */ 
/*     */   
/*  80 */   Setting<Float> switchAnimationThreshold = setting("SwitchAnimationThreshold", 0.0F, 0.0F, 1.0F).des("Switch progress to start stop switch animation from progressing").whenAtMode(this.page, Page.Animations);
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  84 */     mc.field_71460_t.field_78516_c.field_187469_f = MathUtilFuckYou.clamp(mc.field_71460_t.field_78516_c.field_187469_f, ((Float)this.switchAnimationThreshold.getValue()).floatValue(), 1.0F);
/*  85 */     mc.field_71460_t.field_78516_c.field_187471_h = MathUtilFuckYou.clamp(mc.field_71460_t.field_78516_c.field_187471_h, ((Float)this.switchAnimationThreshold.getValue()).floatValue(), 1.0F);
/*     */     
/*  87 */     if (mc.field_71460_t.field_78516_c.field_187469_f == ((Float)this.switchAnimationThreshold.getValue()).floatValue() && mc.field_71460_t.field_78516_c.field_187467_d != mc.field_71439_g.func_184614_ca()) {
/*  88 */       mc.field_71460_t.field_78516_c.field_187467_d = mc.field_71439_g.func_184614_ca();
/*     */     }
/*     */     
/*  91 */     if (mc.field_71460_t.field_78516_c.field_187471_h == ((Float)this.switchAnimationThreshold.getValue()).floatValue() && mc.field_71460_t.field_78516_c.field_187468_e != mc.field_71439_g.func_184592_cb()) {
/*  92 */       mc.field_71460_t.field_78516_c.field_187468_e = mc.field_71439_g.func_184592_cb();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Listener
/*     */   public void renderModelEquipProgressSet(ItemModelEvent.EquipProgress event) {
/*  99 */     event.modifyMain = ((Boolean)this.hitProgressMain.getValue()).booleanValue();
/* 100 */     if (((Boolean)this.hitProgressMain.getValue()).booleanValue()) {
/* 101 */       event.offsetMain = ((Float)this.hitProgressMainOffset.getValue()).floatValue();
/*     */     }
/*     */     
/* 104 */     event.modifyOff = ((Boolean)this.hitProgressOff.getValue()).booleanValue();
/* 105 */     if (((Boolean)this.hitProgressOff.getValue()).booleanValue()) {
/* 106 */       event.offsetOff = ((Float)this.hitProgressOffOffset.getValue()).floatValue();
/*     */     }
/*     */     
/* 109 */     if (((Boolean)this.hitProgressMain.getValue()).booleanValue() || ((Boolean)this.hitProgressOff.getValue()).booleanValue()) {
/* 110 */       event.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void modifyModelTransMatrix(ItemModelEvent.Normal event) {
/* 116 */     if (event.hand == EnumHand.MAIN_HAND) {
/* 117 */       if (mc.field_71439_g.func_184600_cs() == EnumHand.MAIN_HAND && ((Boolean)this.eatingModifyMain.getValue()).booleanValue() && mc.field_71439_g.func_184587_cr()) {
/* 118 */         GL11.glTranslatef(((Float)this.mainEatingX.getValue()).floatValue(), ((Float)this.mainEatingY.getValue()).floatValue(), -((Float)this.mainEatingZ.getValue()).floatValue());
/* 119 */         GL11.glRotatef(((Float)this.mainEatingRotateX.getValue()).floatValue(), 1.0F, 0.0F, 0.0F);
/* 120 */         GL11.glRotatef(((Float)this.mainEatingRotateY.getValue()).floatValue(), 0.0F, 1.0F, 0.0F);
/* 121 */         GL11.glRotatef(((Float)this.mainEatingRotateZ.getValue()).floatValue(), 0.0F, 0.0F, 1.0F);
/* 122 */         GL11.glScalef(((Float)this.mainEatingScaleX.getValue()).floatValue(), ((Float)this.mainEatingScaleY.getValue()).floatValue(), ((Float)this.mainEatingScaleZ.getValue()).floatValue());
/*     */       } else {
/*     */         
/* 125 */         GL11.glTranslatef(((Float)this.mainX.getValue()).floatValue(), ((Float)this.mainY.getValue()).floatValue(), -((Float)this.mainZ.getValue()).floatValue());
/* 126 */         GL11.glRotatef(((Float)this.mainRotateX.getValue()).floatValue(), 1.0F, 0.0F, 0.0F);
/* 127 */         GL11.glRotatef(((Float)this.mainRotateY.getValue()).floatValue(), 0.0F, 1.0F, 0.0F);
/* 128 */         GL11.glRotatef(((Float)this.mainRotateZ.getValue()).floatValue(), 0.0F, 0.0F, 1.0F);
/* 129 */         GL11.glScalef(((Float)this.mainScaleX.getValue()).floatValue(), ((Float)this.mainScaleY.getValue()).floatValue(), ((Float)this.mainScaleZ.getValue()).floatValue());
/*     */       } 
/*     */     }
/*     */     
/* 133 */     if (event.hand == EnumHand.OFF_HAND)
/* 134 */       if (mc.field_71439_g.func_184600_cs() == EnumHand.OFF_HAND && ((Boolean)this.eatingModifyOff.getValue()).booleanValue() && mc.field_71439_g.func_184587_cr()) {
/* 135 */         GL11.glTranslatef(-((Float)this.offEatingX.getValue()).floatValue(), ((Float)this.offEatingY.getValue()).floatValue(), -((Float)this.offEatingZ.getValue()).floatValue());
/* 136 */         GL11.glRotatef(((Float)this.offEatingRotateX.getValue()).floatValue(), 1.0F, 0.0F, 0.0F);
/* 137 */         GL11.glRotatef(-((Float)this.offEatingRotateY.getValue()).floatValue(), 0.0F, 1.0F, 0.0F);
/* 138 */         GL11.glRotatef(-((Float)this.offEatingRotateZ.getValue()).floatValue(), 0.0F, 0.0F, 1.0F);
/* 139 */         GL11.glScalef(((Float)this.offEatingScaleX.getValue()).floatValue(), ((Float)this.offEatingScaleY.getValue()).floatValue(), ((Float)this.offEatingScaleZ.getValue()).floatValue());
/*     */       } else {
/*     */         
/* 142 */         GL11.glTranslatef(-((Float)this.offX.getValue()).floatValue(), ((Float)this.offY.getValue()).floatValue(), -((Float)this.offZ.getValue()).floatValue());
/* 143 */         GL11.glRotatef(((Float)this.offRotateX.getValue()).floatValue(), 1.0F, 0.0F, 0.0F);
/* 144 */         GL11.glRotatef(-((Float)this.offRotateY.getValue()).floatValue(), 0.0F, 1.0F, 0.0F);
/* 145 */         GL11.glRotatef(-((Float)this.offRotateZ.getValue()).floatValue(), 0.0F, 0.0F, 1.0F);
/* 146 */         GL11.glScalef(((Float)this.offScaleX.getValue()).floatValue(), ((Float)this.offScaleY.getValue()).floatValue(), ((Float)this.offScaleZ.getValue()).floatValue());
/*     */       }  
/*     */   }
/*     */   
/*     */   enum Page
/*     */   {
/* 152 */     ItemModel,
/* 153 */     Animations;
/*     */   }
/*     */   
/*     */   enum ItemModelPage
/*     */   {
/* 158 */     Main,
/* 159 */     Off;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\visuals\HeldModelTweaks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */