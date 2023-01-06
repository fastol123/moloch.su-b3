/*    */ package net.spartanb312.base.mixin.mixins.world;
/*    */ 
/*    */ import me.thediamondsword5.moloch.module.modules.movement.NoSlow;
/*    */ import net.minecraft.block.BlockWeb;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({BlockWeb.class})
/*    */ public class MixinBlockWeb {
/*    */   @Inject(method = {"onEntityCollision"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn, CallbackInfo info) {
/* 19 */     if (ModuleManager.getModule(NoSlow.class).isEnabled() && ((Boolean)NoSlow.instance.cobweb.getValue()).booleanValue() && !((Boolean)NoSlow.instance.cobwebTimer.getValue()).booleanValue()) info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\world\MixinBlockWeb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */