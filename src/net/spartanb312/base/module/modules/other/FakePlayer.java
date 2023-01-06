/*    */ package net.spartanb312.base.module.modules.other;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.potion.PotionEffect;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraftforge.common.MinecraftForge;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.common.network.FMLNetworkEvent;
/*    */ import net.spartanb312.base.client.ModuleManager;
/*    */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*    */ import net.spartanb312.base.common.annotations.Parallel;
/*    */ import net.spartanb312.base.core.setting.Setting;
/*    */ import net.spartanb312.base.module.Category;
/*    */ import net.spartanb312.base.module.Module;
/*    */ 
/*    */ @Parallel
/*    */ @ModuleInfo(name = "FakePlayer", category = Category.OTHER, description = "Spawn a fake player entity in client side")
/*    */ public class FakePlayer extends Module {
/* 22 */   Setting<Integer> health = setting("Health", 10, 0, 36).des("Health of fakeplayer");
/* 23 */   Setting<Boolean> sneak = setting("Sneak", false).des("Makes fakeplayer crouch ");
/* 24 */   Setting<String> playerName = setting("Name", "B_312").des("Name of fakeplayer");
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 28 */     MinecraftForge.EVENT_BUS.register(this);
/* 29 */     if (mc.field_71439_g == null || mc.field_71441_e == null)
/* 30 */       return;  EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP((World)mc.field_71441_e, new GameProfile(UUID.fromString("60569353-f22b-42da-b84b-d706a65c5ddf"), (String)this.playerName.getValue()));
/* 31 */     fakePlayer.func_82149_j((Entity)mc.field_71439_g);
/* 32 */     for (PotionEffect potionEffect : mc.field_71439_g.func_70651_bq()) {
/* 33 */       fakePlayer.func_70690_d(potionEffect);
/*    */     }
/* 35 */     fakePlayer.func_70606_j(((Integer)this.health.getValue()).intValue());
/* 36 */     fakePlayer.field_71071_by.func_70455_b(mc.field_71439_g.field_71071_by);
/* 37 */     fakePlayer.field_70759_as = mc.field_71439_g.field_70759_as;
/* 38 */     if (((Boolean)this.sneak.getValue()).booleanValue()) fakePlayer.func_70095_a(true); 
/* 39 */     mc.field_71441_e.func_73027_a(-666, (Entity)fakePlayer);
/* 40 */     this.moduleEnableFlag = true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 46 */     MinecraftForge.EVENT_BUS.unregister(this);
/* 47 */     mc.field_71441_e.func_73028_b(-666);
/* 48 */     this.moduleDisableFlag = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 53 */     if (mc.field_71439_g.field_70725_aQ > 0) {
/* 54 */       MinecraftForge.EVENT_BUS.unregister(this);
/* 55 */       ModuleManager.getModule(FakePlayer.class).disable();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getModuleInfo() {
/* 61 */     return (String)this.playerName.getValue();
/*    */   }
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
/* 66 */     MinecraftForge.EVENT_BUS.unregister(this);
/* 67 */     ModuleManager.getModule(FakePlayer.class).disable();
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\module\modules\other\FakePlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */