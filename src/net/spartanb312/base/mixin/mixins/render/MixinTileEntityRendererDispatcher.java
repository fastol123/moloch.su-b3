/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ 
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.module.modules.visuals.NoRender;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({TileEntityRendererDispatcher.class})
/*    */ public class MixinTileEntityRendererDispatcher {
/*    */   @Inject(method = {"render(Lnet/minecraft/tileentity/TileEntity;FI)V"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderHookPre(TileEntity tileEntity, float partialTicks, int destroyStage, CallbackInfo ci) {
/* 17 */     if (ModuleManager.getModule(NoRender.class).isEnabled()) {
/* 18 */       if (((Boolean)NoRender.INSTANCE.chests.getValue()).booleanValue() && (tileEntity.func_145838_q() == Blocks.field_150486_ae || tileEntity.func_145838_q() == Blocks.field_150447_bR)) {
/* 19 */         ci.cancel();
/*    */       }
/* 21 */       if (((Boolean)NoRender.INSTANCE.enderChests.getValue()).booleanValue() && tileEntity.func_145838_q() == Blocks.field_150477_bB) {
/* 22 */         ci.cancel();
/*    */       }
/* 24 */       if (((Boolean)NoRender.INSTANCE.enchantingTableBook.getValue()).booleanValue() && tileEntity.func_145838_q() == Blocks.field_150381_bn)
/* 25 */         ci.cancel(); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinTileEntityRendererDispatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */