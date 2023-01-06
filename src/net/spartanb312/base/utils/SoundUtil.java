/*    */ package net.spartanb312.base.utils;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.audio.ISound;
/*    */ import net.minecraft.client.audio.PositionedSoundRecord;
/*    */ import net.minecraft.init.SoundEvents;
/*    */ 
/*    */ public class SoundUtil {
/*    */   public static void playButtonClick() {
/* 10 */     Minecraft.func_71410_x().func_147118_V()
/* 11 */       .func_147682_a(
/* 12 */         (ISound)PositionedSoundRecord.func_184371_a(SoundEvents.field_187909_gi, 1.0F));
/*    */   }
/*    */   
/*    */   public static void playAnvilHit() {
/* 16 */     Minecraft.func_71410_x().func_147118_V()
/* 17 */       .func_147682_a(
/* 18 */         (ISound)PositionedSoundRecord.func_184371_a(SoundEvents.field_187686_e, 1.0F));
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\SoundUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */