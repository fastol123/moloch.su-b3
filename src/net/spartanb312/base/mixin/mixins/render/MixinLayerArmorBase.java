/*     */ package net.spartanb312.base.mixin.mixins.render;
/*     */ 
/*     */ import me.thediamondsword5.moloch.module.modules.visuals.Chams;
/*     */ import me.thediamondsword5.moloch.module.modules.visuals.ESP;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.spartanb312.base.client.ModuleManager;
/*     */ import net.spartanb312.base.module.modules.visuals.NoRender;
/*     */ import net.spartanb312.base.utils.ItemUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Mutable;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ @Mixin({LayerArmorBase.class})
/*     */ public abstract class MixinLayerArmorBase<T extends ModelBase>
/*     */   implements LayerRenderer<EntityLivingBase> {
/*     */   @Shadow
/*     */   private boolean field_177193_i;
/*     */   private float newAlpha;
/*     */   @Mutable
/*     */   @Final
/*     */   @Shadow
/*     */   private final RenderLivingBase<?> field_177190_a;
/*     */   
/*     */   @Shadow
/*     */   public abstract T func_188360_a(EntityEquipmentSlot paramEntityEquipmentSlot);
/*     */   
/*     */   @Shadow
/*     */   protected abstract T getArmorModelHook(EntityLivingBase paramEntityLivingBase, ItemStack paramItemStack, EntityEquipmentSlot paramEntityEquipmentSlot, T paramT);
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_188359_a(T paramT, EntityEquipmentSlot paramEntityEquipmentSlot);
/*     */   
/*     */   @Shadow
/*     */   public abstract ResourceLocation getArmorResource(Entity paramEntity, ItemStack paramItemStack, EntityEquipmentSlot paramEntityEquipmentSlot, String paramString);
/*     */   
/*     */   public MixinLayerArmorBase(RenderLivingBase<?> rendererIn) {
/*  52 */     this.field_177190_a = rendererIn;
/*     */   }
/*     */   
/*     */   @Inject(method = {"renderEnchantedGlint"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private static void renderEnchantedGlintHook(RenderLivingBase<?> p_188364_0_, EntityLivingBase p_188364_1_, ModelBase model, float p_188364_3_, float p_188364_4_, float p_188364_5_, float p_188364_6_, float p_188364_7_, float p_188364_8_, float p_188364_9_, CallbackInfo ci) {
/*  57 */     if (ModuleManager.getModule(ESP.class).isEnabled() && ESP.INSTANCE.renderOutlineFlag) {
/*  58 */       ci.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"renderArmorLayer"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void renderArmorLayerHookPre(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn, CallbackInfo ci) {
/*  64 */     if (ModuleManager.getModule(Chams.class).isEnabled() && ((((Boolean)Chams.instance.playerChangeArmorAlpha.getValue()).booleanValue() && ((Boolean)Chams.instance.self.getValue()).booleanValue() && ((Boolean)Chams.instance.selfChangeArmorAlpha.getValue()).booleanValue() && entityLivingBaseIn == ItemUtils.mc.field_71439_g) || (((Boolean)Chams.instance.playerChangeArmorAlpha.getValue()).booleanValue() && ((Boolean)Chams.instance.otherPlayers.getValue()).booleanValue() && entityLivingBaseIn instanceof net.minecraft.entity.player.EntityPlayer && entityLivingBaseIn != ItemUtils.mc.field_71439_g) || (((Boolean)Chams.instance.mobChangeArmorAlpha.getValue()).booleanValue() && ((Boolean)Chams.instance.mobs.getValue()).booleanValue() && entityLivingBaseIn instanceof net.minecraft.entity.monster.EntityMob))) {
/*  65 */       GlStateManager.func_179147_l();
/*  66 */       renderArmorLayer(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, slotIn);
/*  67 */       GlStateManager.func_179084_k();
/*  68 */       ci.cancel();
/*     */     } 
/*     */     
/*  71 */     if (ModuleManager.getModule(NoRender.class).isEnabled() && ((Boolean)NoRender.INSTANCE.armor.getValue()).booleanValue()) {
/*  72 */       ci.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   private void renderArmorLayer(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn) {
/*  77 */     if (((Boolean)Chams.instance.playerChangeArmorAlpha.getValue()).booleanValue() && entityLivingBaseIn instanceof net.minecraft.entity.player.EntityPlayer) this.newAlpha = ((Integer)Chams.instance.playerArmorAlpha.getValue()).intValue() / 255.0F; 
/*  78 */     if (((Boolean)Chams.instance.mobChangeArmorAlpha.getValue()).booleanValue() && entityLivingBaseIn instanceof net.minecraft.entity.monster.EntityMob) this.newAlpha = ((Integer)Chams.instance.mobArmorAlpha.getValue()).intValue() / 255.0F;
/*     */     
/*  80 */     ItemStack itemstack = entityLivingBaseIn.func_184582_a(slotIn);
/*     */     
/*  82 */     if (itemstack.func_77973_b() instanceof ItemArmor) {
/*     */       
/*  84 */       ItemArmor itemarmor = (ItemArmor)itemstack.func_77973_b();
/*     */       
/*  86 */       if (itemarmor.func_185083_B_() == slotIn) {
/*     */         
/*  88 */         T t = func_188360_a(slotIn);
/*  89 */         t = getArmorModelHook(entityLivingBaseIn, itemstack, slotIn, t);
/*  90 */         t.func_178686_a(this.field_177190_a.func_177087_b());
/*  91 */         t.func_78086_a(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
/*  92 */         func_188359_a(t, slotIn);
/*  93 */         this.field_177190_a.func_110776_a(getArmorResource((Entity)entityLivingBaseIn, itemstack, slotIn, null));
/*     */ 
/*     */         
/*  96 */         if (itemarmor.hasOverlay(itemstack)) {
/*     */           
/*  98 */           int i = itemarmor.func_82814_b(itemstack);
/*  99 */           float f = (i >> 16 & 0xFF) / 255.0F;
/* 100 */           float f1 = (i >> 8 & 0xFF) / 255.0F;
/* 101 */           float f2 = (i & 0xFF) / 255.0F;
/* 102 */           GlStateManager.func_179131_c(f, f1, f2, this.newAlpha);
/* 103 */           t.func_78088_a((Entity)entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/* 104 */           this.field_177190_a.func_110776_a(getArmorResource((Entity)entityLivingBaseIn, itemstack, slotIn, "overlay"));
/*     */         } 
/*     */         
/* 107 */         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, this.newAlpha);
/* 108 */         t.func_78088_a((Entity)entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*     */         
/* 110 */         if (!this.field_177193_i && itemstack.func_77962_s()) {
/*     */           
/* 112 */           LayerArmorBase.func_188364_a(this.field_177190_a, entityLivingBaseIn, (ModelBase)t, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
/* 113 */           GL11.glBlendFunc(770, 771);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinLayerArmorBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */