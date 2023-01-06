/*    */ package me.thediamondsword5.moloch.client;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ public class EnemyManager
/*    */ {
/* 10 */   public List<String> enemies = new ArrayList<>();
/*    */   
/*    */   public static void init() {
/* 13 */     instance = new EnemyManager();
/* 14 */     instance.enemies.clear();
/*    */   }
/*    */   private static EnemyManager instance;
/*    */   public static boolean isEnemy(Entity entity) {
/* 18 */     return isEnemy(entity.func_70005_c_());
/*    */   }
/*    */   
/*    */   public static boolean isEnemy(String name) {
/* 22 */     return (getInstance()).enemies.contains(name);
/*    */   }
/*    */   
/*    */   public static void add(String name) {
/* 26 */     if (!(getInstance()).enemies.contains(name)) (getInstance()).enemies.add(name); 
/*    */   }
/*    */   
/*    */   public static void add(Entity entity) {
/* 30 */     if (!(getInstance()).enemies.contains(entity.func_70005_c_())) (getInstance()).enemies.add(entity.func_70005_c_()); 
/*    */   }
/*    */   
/*    */   public static void remove(String name) {
/* 34 */     (getInstance()).enemies.remove(name);
/*    */   }
/*    */   
/*    */   public static void remove(Entity entity) {
/* 38 */     (getInstance()).enemies.remove(entity.func_70005_c_());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static EnemyManager getInstance() {
/* 44 */     if (instance == null) instance = new EnemyManager(); 
/* 45 */     return instance;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\client\EnemyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */