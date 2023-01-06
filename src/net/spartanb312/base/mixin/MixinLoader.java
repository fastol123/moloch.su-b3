/*    */ package net.spartanb312.base.mixin;
/*    */ import java.util.Map;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.spongepowered.asm.launch.MixinBootstrap;
/*    */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*    */ import org.spongepowered.asm.mixin.Mixins;
/*    */ 
/*    */ @MCVersion("1.12.2")
/*    */ @Name("Moloch")
/*    */ @SortingIndex(1001)
/*    */ public class MixinLoader implements IFMLLoadingPlugin {
/* 17 */   public static final Logger log = LogManager.getLogger("MIXIN");
/*    */   
/*    */   public MixinLoader() {
/* 20 */     log.info("Mixins initialized");
/* 21 */     MixinBootstrap.init();
/* 22 */     Mixins.addConfigurations(new String[] { "mixins.mixer.json" });
/* 23 */     MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
/* 24 */     log.info(MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getASMTransformerClass() {
/* 29 */     return new String[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getModContainerClass() {
/* 34 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSetupClass() {
/* 39 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void injectData(Map<String, Object> data) {}
/*    */ 
/*    */   
/*    */   public String getAccessTransformerClass() {
/* 47 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\MixinLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */