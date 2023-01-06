/*    */ package me.thediamondsword5.moloch.event.events.player;
/*    */ 
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.spartanb312.base.event.EventCenter;
/*    */ 
/*    */ public class DamageBlockEvent extends EventCenter {
/*    */   public BlockPos blockPos;
/*    */   public EnumFacing face;
/*    */   
/*    */   public DamageBlockEvent(BlockPos blockPos, EnumFacing face) {
/* 12 */     this.blockPos = blockPos;
/* 13 */     this.face = face;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\event\events\player\DamageBlockEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */