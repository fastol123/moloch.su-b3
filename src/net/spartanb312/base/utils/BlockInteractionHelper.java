/*    */ package net.spartanb312.base.utils;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ public class BlockInteractionHelper
/*    */ {
/*    */   public static List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
/* 11 */     List<BlockPos> circleBlocks = new ArrayList<>();
/* 12 */     int cx = loc.func_177958_n();
/* 13 */     int cy = loc.func_177956_o();
/* 14 */     int cz = loc.func_177952_p();
/* 15 */     for (int x = cx - (int)r; x <= cx + r; x++) {
/* 16 */       for (int z = cz - (int)r; z <= cz + r; ) {
/* 17 */         int y = sphere ? (cy - (int)r) : cy; for (;; z++) { if (y < (sphere ? (cy + r) : (cy + h))) {
/* 18 */             double dist = ((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0));
/* 19 */             if (dist < (r * r) && (!hollow || dist >= ((r - 1.0F) * (r - 1.0F)))) {
/* 20 */               BlockPos l = new BlockPos(x, y + plus_y, z);
/* 21 */               circleBlocks.add(l);
/*    */             }  y++; continue;
/*    */           }  }
/*    */       
/*    */       } 
/* 26 */     }  return circleBlocks;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\BlockInteractionHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */