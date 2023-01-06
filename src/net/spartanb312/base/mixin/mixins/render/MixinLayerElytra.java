/*     */ package net.spartanb312.base.mixin.mixins.render;
/*     */ 
/*     */ import me.thediamondsword5.moloch.module.modules.visuals.Chams;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelElytra;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerElytra;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.spartanb312.base.client.ModuleManager;
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
/*     */ 
/*     */ @Mixin({LayerElytra.class})
/*     */ public class MixinLayerElytra
/*     */ {
/*     */   @Mutable
/*     */   @Final
/*     */   @Shadow
/*     */   protected final RenderLivingBase<?> field_188356_b;
/*     */   @Final
/*     */   @Shadow
/*  39 */   private final ModelElytra field_188357_c = new ModelElytra();
/*     */ 
/*     */   
/*  42 */   private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
/*     */   private float newAlpha;
/*     */   
/*     */   public MixinLayerElytra(RenderLivingBase<?> p_i47185_1_) {
/*  46 */     this.field_188356_b = p_i47185_1_;
/*     */   }
/*     */   
/*     */   @Inject(method = {"doRenderLayer"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void doRenderLayerHook(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo ci) {
/*  51 */     if (ModuleManager.getModule(Chams.class).isEnabled() && ((((Boolean)Chams.instance.playerChangeArmorAlpha.getValue()).booleanValue() && ((Boolean)Chams.instance.self.getValue()).booleanValue() && ((Boolean)Chams.instance.selfChangeArmorAlpha.getValue()).booleanValue() && entitylivingbaseIn == ItemUtils.mc.field_71439_g) || (((Boolean)Chams.instance.playerChangeArmorAlpha.getValue()).booleanValue() && ((Boolean)Chams.instance.otherPlayers.getValue()).booleanValue() && entitylivingbaseIn instanceof net.minecraft.entity.player.EntityPlayer && entitylivingbaseIn != ItemUtils.mc.field_71439_g) || (((Boolean)Chams.instance.mobChangeArmorAlpha.getValue()).booleanValue() && ((Boolean)Chams.instance.mobs.getValue()).booleanValue() && entitylivingbaseIn instanceof net.minecraft.entity.monster.EntityMob))) {
/*  52 */       doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
/*  53 */       ci.cancel();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  59 */     if (((Boolean)Chams.instance.playerChangeArmorAlpha.getValue()).booleanValue() && entitylivingbaseIn instanceof net.minecraft.entity.player.EntityPlayer) this.newAlpha = ((Integer)Chams.instance.playerArmorAlpha.getValue()).intValue() / 255.0F; 
/*  60 */     if (((Boolean)Chams.instance.mobChangeArmorAlpha.getValue()).booleanValue() && entitylivingbaseIn instanceof net.minecraft.entity.monster.EntityMob) this.newAlpha = ((Integer)Chams.instance.mobArmorAlpha.getValue()).intValue() / 255.0F;
/*     */ 
/*     */     
/*  63 */     ItemStack itemstack = entitylivingbaseIn.func_184582_a(EntityEquipmentSlot.CHEST);
/*     */     
/*  65 */     if (itemstack.func_77973_b() == Items.field_185160_cR) {
/*     */       
/*  67 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, this.newAlpha);
/*  68 */       GlStateManager.func_179147_l();
/*  69 */       GL11.glBlendFunc(770, 771);
/*     */       
/*  71 */       if (entitylivingbaseIn instanceof AbstractClientPlayer) {
/*     */         
/*  73 */         AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)entitylivingbaseIn;
/*     */         
/*  75 */         if (abstractclientplayer.func_184833_s() && abstractclientplayer.func_184834_t() != null)
/*     */         {
/*  77 */           this.field_188356_b.func_110776_a(abstractclientplayer.func_184834_t());
/*     */         }
/*  79 */         else if (abstractclientplayer.func_152122_n() && abstractclientplayer.func_110303_q() != null && abstractclientplayer.func_175148_a(EnumPlayerModelParts.CAPE))
/*     */         {
/*  81 */           this.field_188356_b.func_110776_a(abstractclientplayer.func_110303_q());
/*     */         }
/*     */         else
/*     */         {
/*  85 */           this.field_188356_b.func_110776_a(TEXTURE_ELYTRA);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  90 */         this.field_188356_b.func_110776_a(TEXTURE_ELYTRA);
/*     */       } 
/*     */       
/*  93 */       GlStateManager.func_179094_E();
/*  94 */       GlStateManager.func_179109_b(0.0F, 0.0F, 0.125F);
/*  95 */       this.field_188357_c.func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, (Entity)entitylivingbaseIn);
/*  96 */       this.field_188357_c.func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*     */       
/*  98 */       if (itemstack.func_77948_v()) {
/*     */         
/* 100 */         LayerArmorBase.func_188364_a(this.field_188356_b, entitylivingbaseIn, (ModelBase)this.field_188357_c, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
/* 101 */         GL11.glBlendFunc(770, 771);
/*     */       } 
/*     */       
/* 104 */       GlStateManager.func_179084_k();
/* 105 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinLayerElytra.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */