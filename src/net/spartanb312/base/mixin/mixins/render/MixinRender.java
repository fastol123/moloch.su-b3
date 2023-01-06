/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ 
/*    */ import me.thediamondsword5.moloch.client.EnemyManager;
/*    */ import me.thediamondsword5.moloch.core.common.Color;
/*    */ import me.thediamondsword5.moloch.module.modules.visuals.ESP;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.spartanb312.base.client.FriendManager;
/*    */ import net.spartanb312.base.utils.ItemUtils;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({Render.class})
/*    */ public abstract class MixinRender<T extends Entity>
/*    */ {
/*    */   @Inject(method = {"getTeamColor"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getTeamColorHook(T entityIn, CallbackInfoReturnable<Integer> ci) {
/* 31 */     if ((((Boolean)ESP.INSTANCE.espTargetSelf.getValue()).booleanValue() && ESP.INSTANCE.espModeSelf.getValue() == ESP.ModeSelf.Glow) || (((Boolean)ESP.INSTANCE.espTargetPlayers.getValue()).booleanValue() && ESP.INSTANCE.espModePlayers.getValue() == ESP.Mode.Glow) || (((Boolean)ESP.INSTANCE.espTargetMobs.getValue()).booleanValue() && ESP.INSTANCE.espModeMobs.getValue() == ESP.Mode.Glow) || (((Boolean)ESP.INSTANCE.espTargetAnimals.getValue()).booleanValue() && ESP.INSTANCE.espModeAnimals.getValue() == ESP.Mode.Glow) || (((Boolean)ESP.INSTANCE.espTargetCrystals.getValue()).booleanValue() && ESP.INSTANCE.espModeCrystals.getValue() == ESP.Mode.Glow) || (((Boolean)ESP.INSTANCE.espTargetItems.getValue()).booleanValue() && ESP.INSTANCE.espModeItems.getValue() == ESP.ModeItems.Glow)) {
/*    */       int color;
/* 33 */       if (entityIn instanceof net.minecraft.entity.player.EntityPlayer && ((Boolean)ESP.INSTANCE.espTargetPlayers.getValue()).booleanValue() && entityIn != ItemUtils.mc.field_71439_g)
/* 34 */       { if (FriendManager.isFriend((Entity)entityIn)) { color = ((Color)ESP.INSTANCE.espColorPlayersFriend.getValue()).getColor(); }
/* 35 */         else if (EnemyManager.isEnemy((Entity)entityIn)) { color = ((Color)ESP.INSTANCE.espColorPlayersEnemy.getValue()).getColor(); }
/* 36 */         else { color = ((Color)ESP.INSTANCE.espColorPlayers.getValue()).getColor(); }
/*    */          }
/* 38 */       else if (entityIn == ItemUtils.mc.field_71439_g && ((Boolean)ESP.INSTANCE.espTargetSelf.getValue()).booleanValue()) { color = ((Color)ESP.INSTANCE.espColorSelf.getValue()).getColor(); }
/* 39 */       else if ((entityIn instanceof net.minecraft.entity.monster.EntityMob || entityIn instanceof net.minecraft.entity.monster.EntitySlime || entityIn instanceof net.minecraft.entity.monster.EntityGhast || entityIn instanceof net.minecraft.entity.boss.EntityDragon) && ((Boolean)ESP.INSTANCE.espTargetMobs.getValue()).booleanValue()) { color = ((Color)ESP.INSTANCE.espColorMobs.getValue()).getColor(); }
/* 40 */       else if ((entityIn instanceof net.minecraft.entity.passive.EntityAnimal || entityIn instanceof net.minecraft.entity.passive.EntitySquid) && ((Boolean)ESP.INSTANCE.espTargetAnimals.getValue()).booleanValue()) { color = ((Color)ESP.INSTANCE.espColorAnimals.getValue()).getColor(); }
/* 41 */       else if (entityIn instanceof net.minecraft.entity.item.EntityEnderCrystal && ((Boolean)ESP.INSTANCE.espTargetCrystals.getValue()).booleanValue()) { color = ((Color)ESP.INSTANCE.espColorCrystals.getValue()).getColor(); }
/* 42 */       else if (entityIn instanceof net.minecraft.entity.IProjectile || entityIn instanceof net.minecraft.entity.projectile.EntityShulkerBullet || entityIn instanceof net.minecraft.entity.projectile.EntityFireball || entityIn instanceof net.minecraft.entity.item.EntityEnderEye) { color = ((Color)ESP.INSTANCE.espColorProjectiles.getValue()).getColor(); }
/* 43 */       else { color = ((Color)ESP.INSTANCE.espColorItems.getValue()).getColor(); }
/* 44 */        ci.setReturnValue(Integer.valueOf(color));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */