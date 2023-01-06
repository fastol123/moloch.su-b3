/*    */ package net.spartanb312.base.mixin.mixins.render;
/*    */ 
/*    */ import me.thediamondsword5.moloch.event.events.render.RenderEntityPreEvent;
/*    */ import me.thediamondsword5.moloch.module.modules.visuals.Chams;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.utils.EntityUtil;
/*    */ import net.spartanb312.base.utils.ItemUtils;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({RenderManager.class})
/*    */ public class MixinRenderManager
/*    */ {
/*    */   @Inject(method = {"renderEntity"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderEntity(Entity entity, double x, double y, double z, float yaw, float partialTicks, boolean debug, CallbackInfo ci) {
/* 28 */     RenderEntityPreEvent event = new RenderEntityPreEvent(entity);
/* 29 */     BaseCenter.EVENT_BUS.post(event);
/* 30 */     if (event.isCancelled())
/* 31 */       ci.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderEntity"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/Render;doRender(Lnet/minecraft/entity/Entity;DDDFF)V", shift = At.Shift.BEFORE)}, cancellable = true)
/*    */   public void renderEntity1(Entity entity, double x, double y, double z, float yaw, float partialTicks, boolean debug, CallbackInfo ci) {
/* 36 */     if (ModuleManager.getModule(Chams.class).isEnabled() && (!((Boolean)Chams.instance.ignoreInvisible.getValue()).booleanValue() || !entity.func_82150_aj()) && ((entity instanceof net.minecraft.entity.player.EntityPlayer && ((entity != ItemUtils.mc.field_71439_g && ((Boolean)Chams.instance.otherPlayers.getValue()).booleanValue()) || (entity == ItemUtils.mc.field_71439_g && ((Boolean)Chams.instance.self.getValue()).booleanValue())) && ((Boolean)Chams.instance.players.getValue()).booleanValue() && (((Boolean)Chams.instance.fixPlayerOutlineESP.getValue()).booleanValue() || (((Boolean)Chams.instance.playerWall.getValue()).booleanValue() && !((Boolean)Chams.instance.playerBypassArmor.getValue()).booleanValue() && !((Boolean)Chams.instance.playerWallEffect.getValue()).booleanValue()))) || (entity instanceof net.minecraft.entity.monster.EntityMob && ((Boolean)Chams.instance.mobs.getValue()).booleanValue() && (((Boolean)Chams.instance.fixMobOutlineESP.getValue()).booleanValue() || (((Boolean)Chams.instance.mobWall.getValue()).booleanValue() && !((Boolean)Chams.instance.mobBypassArmor.getValue()).booleanValue() && !((Boolean)Chams.instance.mobWallEffect.getValue()).booleanValue()))) || (EntityUtil.isEntityAnimal(entity) && ((Boolean)Chams.instance.animals.getValue()).booleanValue() && ((Boolean)Chams.instance.fixAnimalOutlineESP.getValue()).booleanValue() && !((Boolean)Chams.instance.animalWallEffect.getValue()).booleanValue()) || (entity instanceof net.minecraft.entity.item.EntityEnderCrystal && ((Boolean)Chams.instance.crystals.getValue()).booleanValue() && ((Boolean)Chams.instance.fixCrystalOutlineESP.getValue()).booleanValue() && !((Boolean)Chams.instance.crystalWallEffect.getValue()).booleanValue())))
/* 37 */       GL11.glDepthRange(0.0D, 0.01D); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"renderEntity"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/Render;doRender(Lnet/minecraft/entity/Entity;DDDFF)V", shift = At.Shift.AFTER)})
/*    */   public void renderEntity2(Entity entity, double x, double y, double z, float yaw, float partialTicks, boolean debug, CallbackInfo ci) {
/*    */     // Byte code:
/*    */     //   0: ldc me/thediamondsword5/moloch/module/modules/visuals/Chams
/*    */     //   2: invokestatic getModule : (Ljava/lang/Class;)Lnet/spartanb312/base/module/Module;
/*    */     //   5: invokevirtual isEnabled : ()Z
/*    */     //   8: ifeq -> 189
/*    */     //   11: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   14: getfield ignoreInvisible : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   17: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   20: checkcast java/lang/Boolean
/*    */     //   23: invokevirtual booleanValue : ()Z
/*    */     //   26: ifeq -> 36
/*    */     //   29: aload_1
/*    */     //   30: invokevirtual func_82150_aj : ()Z
/*    */     //   33: ifne -> 189
/*    */     //   36: aload_1
/*    */     //   37: instanceof net/minecraft/entity/player/EntityPlayer
/*    */     //   40: ifeq -> 135
/*    */     //   43: aload_1
/*    */     //   44: getstatic net/spartanb312/base/utils/ItemUtils.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   47: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*    */     //   50: if_acmpeq -> 71
/*    */     //   53: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   56: getfield otherPlayers : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   59: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   62: checkcast java/lang/Boolean
/*    */     //   65: invokevirtual booleanValue : ()Z
/*    */     //   68: ifne -> 99
/*    */     //   71: aload_1
/*    */     //   72: getstatic net/spartanb312/base/utils/ItemUtils.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   75: getfield field_71439_g : Lnet/minecraft/client/entity/EntityPlayerSP;
/*    */     //   78: if_acmpne -> 135
/*    */     //   81: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   84: getfield self : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   87: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   90: checkcast java/lang/Boolean
/*    */     //   93: invokevirtual booleanValue : ()Z
/*    */     //   96: ifeq -> 135
/*    */     //   99: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   102: getfield players : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   105: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   108: checkcast java/lang/Boolean
/*    */     //   111: invokevirtual booleanValue : ()Z
/*    */     //   114: ifeq -> 135
/*    */     //   117: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   120: getfield fixPlayerOutlineESP : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   123: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   126: checkcast java/lang/Boolean
/*    */     //   129: invokevirtual booleanValue : ()Z
/*    */     //   132: ifne -> 408
/*    */     //   135: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   138: getfield playerWall : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   141: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   144: checkcast java/lang/Boolean
/*    */     //   147: invokevirtual booleanValue : ()Z
/*    */     //   150: ifeq -> 189
/*    */     //   153: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   156: getfield playerBypassArmor : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   159: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   162: checkcast java/lang/Boolean
/*    */     //   165: invokevirtual booleanValue : ()Z
/*    */     //   168: ifne -> 189
/*    */     //   171: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   174: getfield playerWallEffect : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   177: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   180: checkcast java/lang/Boolean
/*    */     //   183: invokevirtual booleanValue : ()Z
/*    */     //   186: ifeq -> 408
/*    */     //   189: aload_1
/*    */     //   190: instanceof net/minecraft/entity/monster/EntityMob
/*    */     //   193: ifeq -> 286
/*    */     //   196: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   199: getfield mobs : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   202: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   205: checkcast java/lang/Boolean
/*    */     //   208: invokevirtual booleanValue : ()Z
/*    */     //   211: ifeq -> 286
/*    */     //   214: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   217: getfield fixMobOutlineESP : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   220: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   223: checkcast java/lang/Boolean
/*    */     //   226: invokevirtual booleanValue : ()Z
/*    */     //   229: ifne -> 408
/*    */     //   232: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   235: getfield mobWall : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   238: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   241: checkcast java/lang/Boolean
/*    */     //   244: invokevirtual booleanValue : ()Z
/*    */     //   247: ifeq -> 286
/*    */     //   250: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   253: getfield mobBypassArmor : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   256: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   259: checkcast java/lang/Boolean
/*    */     //   262: invokevirtual booleanValue : ()Z
/*    */     //   265: ifne -> 286
/*    */     //   268: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   271: getfield mobWallEffect : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   274: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   277: checkcast java/lang/Boolean
/*    */     //   280: invokevirtual booleanValue : ()Z
/*    */     //   283: ifeq -> 408
/*    */     //   286: aload_1
/*    */     //   287: invokestatic isEntityAnimal : (Lnet/minecraft/entity/Entity;)Z
/*    */     //   290: ifeq -> 347
/*    */     //   293: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   296: getfield animals : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   299: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   302: checkcast java/lang/Boolean
/*    */     //   305: invokevirtual booleanValue : ()Z
/*    */     //   308: ifeq -> 347
/*    */     //   311: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   314: getfield fixAnimalOutlineESP : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   317: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   320: checkcast java/lang/Boolean
/*    */     //   323: invokevirtual booleanValue : ()Z
/*    */     //   326: ifeq -> 347
/*    */     //   329: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   332: getfield animalWallEffect : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   335: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   338: checkcast java/lang/Boolean
/*    */     //   341: invokevirtual booleanValue : ()Z
/*    */     //   344: ifeq -> 408
/*    */     //   347: aload_1
/*    */     //   348: instanceof net/minecraft/entity/item/EntityEnderCrystal
/*    */     //   351: ifeq -> 413
/*    */     //   354: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   357: getfield crystals : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   360: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   363: checkcast java/lang/Boolean
/*    */     //   366: invokevirtual booleanValue : ()Z
/*    */     //   369: ifeq -> 413
/*    */     //   372: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   375: getfield fixCrystalOutlineESP : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   378: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   381: checkcast java/lang/Boolean
/*    */     //   384: invokevirtual booleanValue : ()Z
/*    */     //   387: ifeq -> 413
/*    */     //   390: getstatic me/thediamondsword5/moloch/module/modules/visuals/Chams.instance : Lme/thediamondsword5/moloch/module/modules/visuals/Chams;
/*    */     //   393: getfield crystalWallEffect : Lnet/spartanb312/base/core/setting/Setting;
/*    */     //   396: invokevirtual getValue : ()Ljava/lang/Object;
/*    */     //   399: checkcast java/lang/Boolean
/*    */     //   402: invokevirtual booleanValue : ()Z
/*    */     //   405: ifne -> 413
/*    */     //   408: dconst_0
/*    */     //   409: dconst_1
/*    */     //   410: invokestatic glDepthRange : (DD)V
/*    */     //   413: return
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #42	-> 0
/*    */     //   #43	-> 408
/*    */     //   #44	-> 413
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	414	0	this	Lnet/spartanb312/base/mixin/mixins/render/MixinRenderManager;
/*    */     //   0	414	1	entity	Lnet/minecraft/entity/Entity;
/*    */     //   0	414	2	x	D
/*    */     //   0	414	4	y	D
/*    */     //   0	414	6	z	D
/*    */     //   0	414	8	yaw	F
/*    */     //   0	414	9	partialTicks	F
/*    */     //   0	414	10	debug	Z
/*    */     //   0	414	11	ci	Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\render\MixinRenderManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */