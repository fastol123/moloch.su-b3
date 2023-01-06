/*    */ package net.spartanb312.base.client;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ public class FriendManager
/*    */ {
/* 10 */   public List<String> friends = new ArrayList<>();
/*    */   
/*    */   public static void init() {
/* 13 */     instance = new FriendManager();
/* 14 */     instance.friends.clear();
/*    */   }
/*    */   private static FriendManager instance;
/*    */   public static boolean isFriend(Entity entity) {
/* 18 */     return isFriend(entity.func_70005_c_());
/*    */   }
/*    */   
/*    */   public static boolean isFriend(String name) {
/* 22 */     return (getInstance()).friends.contains(name);
/*    */   }
/*    */   
/*    */   public static void add(String name) {
/* 26 */     if (!(getInstance()).friends.contains(name)) (getInstance()).friends.add(name); 
/*    */   }
/*    */   
/*    */   public static void add(Entity entity) {
/* 30 */     if (!(getInstance()).friends.contains(entity.func_70005_c_())) (getInstance()).friends.add(entity.func_70005_c_()); 
/*    */   }
/*    */   
/*    */   public static void remove(String name) {
/* 34 */     (getInstance()).friends.remove(name);
/*    */   }
/*    */   
/*    */   public static void remove(Entity entity) {
/* 38 */     (getInstance()).friends.remove(entity.func_70005_c_());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static FriendManager getInstance() {
/* 44 */     if (instance == null) instance = new FriendManager(); 
/* 45 */     return instance;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\client\FriendManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */