/*    */ package net.spartanb312.base.command;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.spartanb312.base.common.annotations.CommandInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Command
/*    */ {
/* 11 */   public static final Minecraft mc = Minecraft.func_71410_x();
/*    */ 
/*    */   
/* 14 */   private final String command = getAnnotation().command();
/* 15 */   private final String description = getAnnotation().description();
/*    */ 
/*    */   
/*    */   private CommandInfo getAnnotation() {
/* 19 */     if (getClass().isAnnotationPresent((Class)CommandInfo.class)) {
/* 20 */       return getClass().<CommandInfo>getAnnotation(CommandInfo.class);
/*    */     }
/* 22 */     throw new IllegalStateException("No Annotation on class " + getClass().getCanonicalName() + "!");
/*    */   }
/*    */   
/*    */   public abstract void onCall(String paramString, String... paramVarArgs);
/*    */   
/*    */   public abstract String getSyntax();
/*    */   
/*    */   public String getDescription() {
/* 30 */     return this.description;
/*    */   }
/*    */   
/*    */   public String getCommand() {
/* 34 */     return this.command;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\command\Command.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */