/*     */ package net.spartanb312.base.client;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import me.thediamondsword5.moloch.client.EnemyManager;
/*     */ import net.spartanb312.base.BaseCenter;
/*     */ import net.spartanb312.base.gui.ClickGUIFinal;
/*     */ import net.spartanb312.base.gui.Panel;
/*     */ import net.spartanb312.base.gui.renderers.ClickGUIRenderer;
/*     */ import net.spartanb312.base.gui.renderers.HUDEditorRenderer;
/*     */ import net.spartanb312.base.module.Module;
/*     */ 
/*     */ public class ConfigManager {
/*     */   public String skidName;
/*  24 */   private static final Gson gsonPretty = (new GsonBuilder()).setPrettyPrinting().create(); public static final String CONFIG_PATH = "moloch.su/config/";
/*  25 */   private static final JsonParser jsonParser = new JsonParser();
/*     */   
/*  27 */   private final File CLIENT_FILE = new File("moloch.su/config/moloch_Client_Stuff.json");
/*  28 */   private final File GUI_FILE = new File("moloch.su/config/moloch_GUI_Stuff.json");
/*     */   
/*  30 */   private final List<File> configList = ListUtil.listOf((Object[])new File[] { this.CLIENT_FILE, this.GUI_FILE });
/*     */   boolean shouldSave = false;
/*     */   private static ConfigManager instance;
/*     */   
/*     */   public void shouldSave() {
/*  35 */     this.shouldSave = true;
/*     */   }
/*     */   
/*     */   public void onInit() {
/*  39 */     this.configList.forEach(it -> {
/*     */           if (!it.exists()) {
/*     */             shouldSave();
/*     */           }
/*     */         });
/*  44 */     if (this.shouldSave) saveAll(); 
/*     */   }
/*     */   
/*     */   public void saveGUI() {
/*     */     try {
/*  49 */       if (!this.GUI_FILE.exists()) {
/*  50 */         this.GUI_FILE.getParentFile().mkdirs();
/*     */         try {
/*  52 */           this.GUI_FILE.createNewFile();
/*  53 */         } catch (Exception exception) {}
/*     */       } 
/*     */       
/*  56 */       JsonObject father = new JsonObject();
/*  57 */       List<Panel> panels = new ArrayList<>(ClickGUIRenderer.instance.panels);
/*  58 */       panels.addAll(HUDEditorRenderer.instance.panels);
/*  59 */       for (Panel panel : panels) {
/*  60 */         JsonObject jsonGui = new JsonObject();
/*  61 */         jsonGui.addProperty("X", Integer.valueOf(panel.x));
/*  62 */         jsonGui.addProperty("Y", Integer.valueOf(panel.y));
/*  63 */         jsonGui.addProperty("Extended", Boolean.valueOf(panel.extended));
/*  64 */         father.add(panel.category.categoryName, (JsonElement)jsonGui);
/*     */       } 
/*  66 */       JsonObject jsonDesGui = new JsonObject();
/*  67 */       jsonDesGui.addProperty("X", Integer.valueOf(ClickGUIFinal.descriptionHubX));
/*  68 */       jsonDesGui.addProperty("Y", Integer.valueOf(ClickGUIFinal.descriptionHubY));
/*  69 */       father.add("DescriptionHub", (JsonElement)jsonDesGui);
/*     */       
/*  71 */       PrintWriter saveJSon = new PrintWriter(new FileWriter(this.GUI_FILE));
/*  72 */       saveJSon.println(gsonPretty.toJson((JsonElement)father));
/*  73 */       saveJSon.close();
/*  74 */     } catch (Exception e) {
/*  75 */       BaseCenter.log.error("Error while saving GUI config!");
/*  76 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void loadGUI() {
/*  81 */     if (this.GUI_FILE.exists()) {
/*     */       try {
/*  83 */         BufferedReader loadJson = new BufferedReader(new FileReader(this.GUI_FILE));
/*  84 */         JsonObject guiJson = (JsonObject)jsonParser.parse(loadJson);
/*  85 */         loadJson.close();
/*  86 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)guiJson.entrySet()) {
/*  87 */           Panel panel = ClickGUIRenderer.instance.getPanelByName(entry.getKey());
/*  88 */           if (panel == null) panel = HUDEditorRenderer.instance.getPanelByName(entry.getKey()); 
/*  89 */           JsonObject jsonGui = (JsonObject)entry.getValue();
/*  90 */           if (panel != null) {
/*  91 */             panel.x = jsonGui.get("X").getAsInt();
/*  92 */             panel.y = jsonGui.get("Y").getAsInt();
/*  93 */             panel.extended = jsonGui.get("Extended").getAsBoolean();
/*     */           } 
/*  95 */           if (Objects.equals(entry.getKey(), "DescriptionHub")) {
/*  96 */             ClickGUIFinal.descriptionHubX = jsonGui.get("X").getAsInt();
/*  97 */             ClickGUIFinal.descriptionHubY = jsonGui.get("Y").getAsInt();
/*     */           } 
/*     */         } 
/* 100 */       } catch (IOException e) {
/* 101 */         BaseCenter.log.error("Error while loading GUI config!");
/* 102 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveClient() {
/*     */     try {
/* 109 */       if (!this.CLIENT_FILE.exists()) {
/* 110 */         this.CLIENT_FILE.getParentFile().mkdirs();
/*     */         try {
/* 112 */           this.CLIENT_FILE.createNewFile();
/* 113 */         } catch (Exception exception) {}
/*     */       } 
/*     */ 
/*     */       
/* 117 */       JsonObject father = new JsonObject();
/*     */       
/* 119 */       saveFriend(father);
/* 120 */       saveEnemy(father);
/*     */       
/* 122 */       PrintWriter saveJSon = new PrintWriter(new FileWriter(this.CLIENT_FILE));
/* 123 */       saveJSon.println(gsonPretty.toJson((JsonElement)father));
/* 124 */       saveJSon.close();
/* 125 */     } catch (Exception e) {
/* 126 */       BaseCenter.log.error("Error while saving client stuff!");
/* 127 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadClient() {
/* 132 */     if (this.CLIENT_FILE.exists()) {
/*     */       try {
/* 134 */         BufferedReader loadJson = new BufferedReader(new FileReader(this.CLIENT_FILE));
/* 135 */         JsonObject guiJason = (JsonObject)jsonParser.parse(loadJson);
/* 136 */         loadJson.close();
/* 137 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)guiJason.entrySet()) {
/* 138 */           if (((String)entry.getKey()).equals("Friends")) {
/* 139 */             JsonArray array = (JsonArray)entry.getValue();
/* 140 */             array.forEach(it -> (FriendManager.getInstance()).friends.add(it.getAsString())); continue;
/* 141 */           }  if (((String)entry.getKey()).equals("Enemies")) {
/* 142 */             JsonArray array = (JsonArray)entry.getValue();
/* 143 */             array.forEach(it -> (EnemyManager.getInstance()).enemies.add(it.getAsString()));
/*     */           }
/*     */         
/*     */         } 
/* 147 */       } catch (IOException e) {
/* 148 */         BaseCenter.log.error("Error while loading client stuff!");
/* 149 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void saveFriend(JsonObject father) {
/* 155 */     JsonArray array = new JsonArray();
/* 156 */     (FriendManager.getInstance()).friends.forEach(array::add);
/* 157 */     father.add("Friends", (JsonElement)array);
/*     */   }
/*     */   private void saveEnemy(JsonObject father) {
/* 160 */     JsonArray array = new JsonArray();
/* 161 */     (EnemyManager.getInstance()).enemies.forEach(array::add);
/* 162 */     father.add("Enemies", (JsonElement)array);
/*     */   }
/*     */   
/*     */   private void loadModule() {
/* 166 */     ModuleManager.getModules().forEach(Module::onLoad);
/*     */   }
/*     */   
/*     */   private void saveModule() {
/* 170 */     ModuleManager.getModules().forEach(Module::onSave);
/*     */   }
/*     */   
/*     */   public static void loadAll() {
/* 174 */     getInstance().loadClient();
/* 175 */     getInstance().loadGUI();
/* 176 */     getInstance().loadModule();
/*     */   }
/*     */   
/*     */   public static void saveAll() {
/* 180 */     getInstance().saveClient();
/* 181 */     getInstance().saveGUI();
/* 182 */     getInstance().saveModule();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConfigManager getInstance() {
/* 188 */     if (instance == null) instance = new ConfigManager(); 
/* 189 */     return instance;
/*     */   }
/*     */   
/*     */   public static void init() {
/* 193 */     instance = new ConfigManager();
/* 194 */     instance.onInit();
/* 195 */     loadAll();
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\client\ConfigManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */