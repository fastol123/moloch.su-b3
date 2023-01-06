/*    */ package net.spartanb312.base.mixin.mixins.client;
/*    */ 
/*    */ import me.thediamondsword5.moloch.module.modules.other.NameSpoof;
/*    */ import net.minecraft.client.gui.GuiPlayerTabOverlay;
/*    */ import net.minecraft.client.network.NetworkPlayerInfo;
/*    */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*    */ import net.minecraft.scoreboard.Team;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.utils.CrystalUtil;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({GuiPlayerTabOverlay.class})
/*    */ public class MixinGuiPlayerTabOverlay
/*    */ {
/*    */   @Inject(method = {"getPlayerName"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getPlayerNameHook(NetworkPlayerInfo networkPlayerInfoIn, CallbackInfoReturnable<String> cir) {
/* 20 */     if (ModuleManager.getModule(NameSpoof.class).isEnabled())
/* 21 */       cir.setReturnValue(((networkPlayerInfoIn.func_178854_k() != null) ? networkPlayerInfoIn.func_178854_k().func_150254_d() : ScorePlayerTeam.func_96667_a((Team)networkPlayerInfoIn.func_178850_i(), networkPlayerInfoIn.func_178845_a().getName()))
/* 22 */           .replaceAll(CrystalUtil.mc.field_71439_g.func_70005_c_(), (String)NameSpoof.INSTANCE.name.getValue())); 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\client\MixinGuiPlayerTabOverlay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */