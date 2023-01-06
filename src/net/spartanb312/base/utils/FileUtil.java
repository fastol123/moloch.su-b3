/*    */ package net.spartanb312.base.utils;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.OpenOption;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.Paths;
/*    */ import java.nio.file.StandardOpenOption;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ public class FileUtil {
/*    */   public static List<String> readTextFileAllLines(String file) {
/*    */     try {
/* 16 */       Path path = Paths.get(file, new String[0]);
/* 17 */       return Files.readAllLines(path, StandardCharsets.UTF_8);
/*    */     }
/* 19 */     catch (IOException e) {
/* 20 */       System.out.println("WARNING: Unable to read file, creating new file: " + file);
/* 21 */       appendTextFile("", file);
/* 22 */       return Collections.emptyList();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static boolean appendTextFile(String data, String file) {
/*    */     try {
/* 28 */       Path path = Paths.get(file, new String[0]);
/* 29 */       Files.write(path, Collections.singletonList(data), StandardCharsets.UTF_8, new OpenOption[] { Files.exists(path, new java.nio.file.LinkOption[0]) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE });
/*    */     }
/* 31 */     catch (IOException e) {
/* 32 */       System.out.println("WARNING: Unable to write file: " + file);
/* 33 */       return false;
/*    */     } 
/* 35 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\FileUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */