/*    */ package net.spartanb312.base.utils;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListUtil
/*    */ {
/*    */   @SafeVarargs
/*    */   public static <T> List<T> listOf(T... elements) {
/* 12 */     return Arrays.asList(elements);
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\ListUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */