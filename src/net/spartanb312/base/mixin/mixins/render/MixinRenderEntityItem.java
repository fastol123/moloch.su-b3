/*     */ package net.spartanb312.base.mixin.mixins.render;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.module.modules.visuals.Chams;
/*     */ import me.thediamondsword5.moloch.module.modules.visuals.ESP;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.entity.RenderEntityItem;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.spartanb312.base.client.ModuleManager;
/*     */ import net.spartanb312.base.command.Command;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ 
/*     */ @Mixin({RenderEntityItem.class})
/*     */ public class MixinRenderEntityItem {
/*     */   @Inject(method = {"doRender"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderItem;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V")})
/*     */   public void doRenderHook(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
/*  26 */     ItemStack itemstack = entity.func_92059_d();
/*  27 */     IBakedModel ibakedmodel = (Command.mc.func_175597_ag()).field_178112_h.func_184393_a(itemstack, entity.field_70170_p, null);
/*     */     
/*  29 */     if ((ModuleManager.getModule(Chams.class).isEnabled() && ((Boolean)Chams.instance.items.getValue()).booleanValue() && (!((Boolean)Chams.instance.itemsRangeLimit.getValue()).booleanValue() || Command.mc.field_71439_g.func_70032_d((Entity)entity) <= ((Float)Chams.instance.itemsRange.getValue()).floatValue())) || (((Boolean)ESP.INSTANCE.espTargetItems.getValue()).booleanValue() && ESP.INSTANCE.espModeItems.getValue() == ESP.ModeItems.Wireframe && ModuleManager.getModule(ESP.class).isEnabled() && (!((Boolean)ESP.INSTANCE.espRangeLimitItems.getValue()).booleanValue() || Command.mc.field_71439_g.func_70032_d((Entity)entity) <= ((Float)ESP.INSTANCE.espRangeItems.getValue()).floatValue()))) {
/*  30 */       renderItem(itemstack, ibakedmodel, (((Boolean)ESP.INSTANCE.espTargetItems.getValue()).booleanValue() && ESP.INSTANCE.espModeItems.getValue() == ESP.ModeItems.Wireframe && ModuleManager.getModule(ESP.class).isEnabled() && (!((Boolean)ESP.INSTANCE.espRangeLimitItems.getValue()).booleanValue() || Command.mc.field_71439_g.func_70032_d((Entity)entity) <= ((Float)ESP.INSTANCE.espRangeItems.getValue()).floatValue())), (ModuleManager.getModule(Chams.class).isEnabled() && ((Boolean)Chams.instance.items.getValue()).booleanValue() && (!((Boolean)Chams.instance.itemsRangeLimit.getValue()).booleanValue() || Command.mc.field_71439_g.func_70032_d((Entity)entity) <= ((Float)Chams.instance.itemsRange.getValue()).floatValue())));
/*     */     }
/*     */   }
/*     */   
/*     */   private void newRenderModel(IBakedModel model, ItemStack stack, boolean lines, boolean chams) {
/*  35 */     GL11.glEnable(2881);
/*  36 */     GL11.glEnable(2848);
/*  37 */     GlStateManager.func_179118_c();
/*  38 */     GlStateManager.func_179090_x();
/*  39 */     GlStateManager.func_179097_i();
/*  40 */     GlStateManager.func_179147_l();
/*  41 */     if (((Boolean)Chams.instance.itemBlend.getValue()).booleanValue() && chams) { GL11.glBlendFunc(770, 32772); }
/*  42 */     else { GL11.glBlendFunc(770, 771); }
/*     */     
/*  44 */     if (chams) {
/*  45 */       if (((Boolean)Chams.instance.itemTexture.getValue()).booleanValue()) GlStateManager.func_179098_w(); 
/*  46 */       if (!((Boolean)Chams.instance.itemLighting.getValue()).booleanValue()) GlStateManager.func_179140_f(); 
/*  47 */       GL11.glPolygonMode(1032, 6914);
/*  48 */       renderModel(model, ((Color)Chams.instance.itemColor.getValue()).getColor(), stack);
/*  49 */       if (((Boolean)Chams.instance.itemTexture.getValue()).booleanValue()) GlStateManager.func_179090_x();
/*     */     
/*     */     } 
/*     */     
/*  53 */     if (lines) {
/*  54 */       GlStateManager.func_179140_f();
/*  55 */       GL11.glLineWidth(((Float)ESP.INSTANCE.espItemWidth.getValue()).floatValue());
/*  56 */       GL11.glPolygonMode(1032, 6913);
/*  57 */       renderModel(model, ((Color)ESP.INSTANCE.espColorItems.getValue()).getColor(), stack);
/*     */     } 
/*     */     
/*  60 */     GL11.glPolygonMode(1032, 6914);
/*  61 */     GlStateManager.func_179089_o();
/*  62 */     GlStateManager.func_179098_w();
/*  63 */     GlStateManager.func_179126_j();
/*  64 */     GlStateManager.func_179141_d();
/*  65 */     GL11.glEnable(2896);
/*  66 */     GL11.glBlendFunc(770, 771);
/*  67 */     GlStateManager.func_179147_l();
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderModel(IBakedModel model, int color, ItemStack stack) {
/*  72 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  73 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*  74 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_176599_b);
/*     */     
/*  76 */     for (EnumFacing enumfacing : EnumFacing.values())
/*     */     {
/*  78 */       Command.mc.func_175599_af().func_191970_a(bufferbuilder, model.func_188616_a(null, enumfacing, 0L), color, stack);
/*     */     }
/*     */     
/*  81 */     Command.mc.func_175599_af().func_191970_a(bufferbuilder, model.func_188616_a(null, null, 0L), color, stack);
/*  82 */     tessellator.func_78381_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderItem(ItemStack stack, IBakedModel model, boolean lines, boolean chams) {
/*  87 */     if (!stack.func_190926_b()) {
/*     */       
/*  89 */       GlStateManager.func_179094_E();
/*  90 */       GlStateManager.func_179109_b(-0.5F, -0.5F, -0.5F);
/*     */       
/*  92 */       if (model.func_188618_c()) {
/*     */         
/*  94 */         GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*  95 */         GlStateManager.func_179091_B();
/*  96 */         stack.func_77973_b().getTileEntityItemStackRenderer().func_179022_a(stack);
/*     */       }
/*     */       else {
/*     */         
/* 100 */         newRenderModel(model, stack, lines, chams);
/*     */       } 
/*     */       
/* 103 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinRenderEntityItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */