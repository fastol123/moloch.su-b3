/*     */ package net.spartanb312.base.core.config;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Map;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.core.common.Visibility;
/*     */ import me.thediamondsword5.moloch.core.setting.settings.ColorSetting;
/*     */ import me.thediamondsword5.moloch.core.setting.settings.VisibilitySetting;
/*     */ import net.spartanb312.base.core.common.KeyBind;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.core.setting.settings.BindSetting;
/*     */ import net.spartanb312.base.core.setting.settings.BooleanSetting;
/*     */ import net.spartanb312.base.core.setting.settings.DoubleSetting;
/*     */ import net.spartanb312.base.core.setting.settings.EnumSetting;
/*     */ import net.spartanb312.base.core.setting.settings.FloatSetting;
/*     */ import net.spartanb312.base.core.setting.settings.IntSetting;
/*     */ import net.spartanb312.base.core.setting.settings.StringSetting;
/*     */ 
/*     */ public class ConfigContainer {
/*  21 */   private static final Gson gsonPretty = (new GsonBuilder()).setPrettyPrinting().create();
/*  22 */   private static final JsonParser jsonParser = new JsonParser();
/*     */   
/*  24 */   private final List<Setting<?>> settings = new ArrayList<>();
/*     */ 
/*     */   
/*     */   protected File configFile;
/*     */ 
/*     */   
/*     */   public ConfigContainer(String savePath, String saveName) {
/*  31 */     this.configFile = new File(savePath + saveName + ".json");
/*     */   }
/*     */   
/*     */   public List<Setting<?>> getSettings() {
/*  35 */     return this.settings;
/*     */   }
/*     */   
/*     */   public void saveConfig() {
/*  39 */     if (!this.configFile.exists()) {
/*     */       try {
/*  41 */         this.configFile.getParentFile().mkdirs();
/*  42 */         this.configFile.createNewFile();
/*  43 */       } catch (IOException e) {
/*  44 */         e.printStackTrace();
/*     */       } 
/*     */     } else {
/*  47 */       JsonObject jsonObject = new JsonObject();
/*  48 */       for (Setting<?> setting : this.settings) {
/*  49 */         if (setting instanceof BindSetting) {
/*  50 */           jsonObject.addProperty(setting.getName(), Integer.valueOf(((KeyBind)((BindSetting)setting).getValue()).getKeyCode()));
/*     */         }
/*  52 */         if (setting instanceof VisibilitySetting) {
/*  53 */           jsonObject.addProperty(setting.getName(), Boolean.valueOf(((Visibility)((VisibilitySetting)setting).getValue()).getVisible()));
/*     */         }
/*  55 */         if (setting instanceof ColorSetting) {
/*  56 */           jsonObject.addProperty(setting.getName(), Integer.valueOf(((Color)((ColorSetting)setting).getValue()).getColor()));
/*  57 */           jsonObject.addProperty(setting.getName() + "SyncGlobal", Boolean.valueOf(((Color)((ColorSetting)setting).getValue()).getSyncGlobal()));
/*  58 */           jsonObject.addProperty(setting.getName() + "Rainbow", Boolean.valueOf(((Color)((ColorSetting)setting).getValue()).getRainbow()));
/*  59 */           jsonObject.addProperty(setting.getName() + "RainbowSpeed", Float.valueOf(((Color)((ColorSetting)setting).getValue()).getRainbowSpeed()));
/*  60 */           jsonObject.addProperty(setting.getName() + "RainbowSaturation", Float.valueOf(((Color)((ColorSetting)setting).getValue()).getRainbowSaturation()));
/*  61 */           jsonObject.addProperty(setting.getName() + "RainbowBrightness", Float.valueOf(((Color)((ColorSetting)setting).getValue()).getRainbowBrightness()));
/*  62 */           jsonObject.addProperty(setting.getName() + "Red", Integer.valueOf(((Color)((ColorSetting)setting).getValue()).getRed()));
/*  63 */           jsonObject.addProperty(setting.getName() + "Green", Integer.valueOf(((Color)((ColorSetting)setting).getValue()).getGreen()));
/*  64 */           jsonObject.addProperty(setting.getName() + "Blue", Integer.valueOf(((Color)((ColorSetting)setting).getValue()).getBlue()));
/*  65 */           jsonObject.addProperty(setting.getName() + "Alpha", Integer.valueOf(((Color)((ColorSetting)setting).getValue()).getAlpha()));
/*     */         } 
/*  67 */         if (setting instanceof BooleanSetting) {
/*  68 */           jsonObject.addProperty(setting.getName(), (Boolean)((BooleanSetting)setting).getValue());
/*     */         }
/*  70 */         if (setting instanceof DoubleSetting) {
/*  71 */           jsonObject.addProperty(setting.getName(), (Number)((DoubleSetting)setting).getValue());
/*     */         }
/*  73 */         if (setting instanceof EnumSetting) {
/*  74 */           jsonObject.addProperty(setting.getName(), ((Enum)((EnumSetting)setting).getValue()).name());
/*     */         }
/*  76 */         if (setting instanceof FloatSetting) {
/*  77 */           jsonObject.addProperty(setting.getName(), (Number)((FloatSetting)setting).getValue());
/*     */         }
/*  79 */         if (setting instanceof IntSetting) {
/*  80 */           jsonObject.addProperty(setting.getName(), (Number)((IntSetting)setting).getValue());
/*     */         }
/*  82 */         if (setting instanceof StringSetting) {
/*  83 */           jsonObject.addProperty(setting.getName(), (String)((StringSetting)setting).getValue());
/*     */         }
/*     */       } 
/*     */       try {
/*  87 */         PrintWriter saveJSon = new PrintWriter(new FileWriter(this.configFile));
/*  88 */         saveJSon.println(gsonPretty.toJson((JsonElement)jsonObject));
/*  89 */         saveJSon.close();
/*  90 */       } catch (IOException e) {
/*  91 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void readConfig() {
/*  97 */     if (this.configFile.exists())
/*     */     { try {
/*  99 */         BufferedReader bufferedJson = new BufferedReader(new FileReader(this.configFile));
/* 100 */         JsonObject jsonObject = (JsonObject)jsonParser.parse(bufferedJson);
/* 101 */         bufferedJson.close();
/* 102 */         Map<String, JsonElement> map = new HashMap<>();
/* 103 */         jsonObject.entrySet().forEach(it -> (JsonElement)map.put(it.getKey(), it.getValue()));
/* 104 */         for (Setting<?> setting : this.settings) {
/* 105 */           JsonElement element = map.get(setting.getName());
/* 106 */           if (element != null) {
/* 107 */             if (setting instanceof BindSetting) {
/* 108 */               ((KeyBind)((BindSetting)setting).getValue()).setKeyCode(element.getAsInt()); continue;
/* 109 */             }  if (setting instanceof VisibilitySetting) {
/* 110 */               ((Visibility)((VisibilitySetting)setting).getValue()).setVisible(element.getAsBoolean()); continue;
/* 111 */             }  if (setting instanceof ColorSetting) {
/* 112 */               ((Color)((ColorSetting)setting).getValue()).setColor(element.getAsInt());
/* 113 */               ((Color)((ColorSetting)setting).getValue()).setSyncGlobal(((JsonElement)map.get(setting.getName() + "SyncGlobal")).getAsBoolean());
/* 114 */               ((Color)((ColorSetting)setting).getValue()).setRainbow(((JsonElement)map.get(setting.getName() + "Rainbow")).getAsBoolean());
/* 115 */               ((Color)((ColorSetting)setting).getValue()).setRainbowSpeed(((JsonElement)map.get(setting.getName() + "RainbowSpeed")).getAsFloat());
/* 116 */               ((Color)((ColorSetting)setting).getValue()).setRainbowSaturation(((JsonElement)map.get(setting.getName() + "RainbowSaturation")).getAsFloat());
/* 117 */               ((Color)((ColorSetting)setting).getValue()).setRainbowBrightness(((JsonElement)map.get(setting.getName() + "RainbowBrightness")).getAsFloat());
/* 118 */               ((Color)((ColorSetting)setting).getValue()).setRed(((JsonElement)map.get(setting.getName() + "Red")).getAsInt());
/* 119 */               ((Color)((ColorSetting)setting).getValue()).setGreen(((JsonElement)map.get(setting.getName() + "Green")).getAsInt());
/* 120 */               ((Color)((ColorSetting)setting).getValue()).setBlue(((JsonElement)map.get(setting.getName() + "Blue")).getAsInt());
/* 121 */               ((Color)((ColorSetting)setting).getValue()).setAlpha(((JsonElement)map.get(setting.getName() + "Alpha")).getAsInt()); continue;
/* 122 */             }  if (setting instanceof BooleanSetting) {
/* 123 */               ((BooleanSetting)setting).setValue(Boolean.valueOf(element.getAsBoolean())); continue;
/* 124 */             }  if (setting instanceof DoubleSetting) {
/* 125 */               ((DoubleSetting)setting).setValue(Double.valueOf(element.getAsDouble())); continue;
/* 126 */             }  if (setting instanceof EnumSetting) {
/* 127 */               ((EnumSetting)setting).setByName(element.getAsString()); continue;
/* 128 */             }  if (setting instanceof FloatSetting) {
/* 129 */               ((FloatSetting)setting).setValue(Float.valueOf(element.getAsFloat())); continue;
/* 130 */             }  if (setting instanceof IntSetting) {
/* 131 */               ((IntSetting)setting).setValue(Integer.valueOf(element.getAsInt())); continue;
/* 132 */             }  if (setting instanceof StringSetting) {
/* 133 */               ((StringSetting)setting).setValue(element.getAsString());
/*     */             }
/*     */           } 
/*     */         } 
/* 137 */       } catch (Exception exception) {
/* 138 */         exception.printStackTrace();
/*     */       }  }
/* 140 */     else { saveConfig(); }
/*     */   
/*     */   }
/*     */   public Setting<KeyBind> setting(String name, KeyBind defaultValue) {
/* 144 */     BindSetting setting = new BindSetting(name, defaultValue);
/* 145 */     this.settings.add(setting);
/* 146 */     return (Setting<KeyBind>)setting;
/*     */   }
/*     */   
/*     */   public Setting<Visibility> setting(String name, Visibility defaultValue) {
/* 150 */     VisibilitySetting setting = new VisibilitySetting(name, defaultValue);
/* 151 */     this.settings.add(setting);
/* 152 */     return (Setting<Visibility>)setting;
/*     */   }
/*     */   
/*     */   public Setting<Color> setting(String name, Color defaultValue) {
/* 156 */     ColorSetting setting = new ColorSetting(name, defaultValue);
/* 157 */     this.settings.add(setting);
/* 158 */     return (Setting<Color>)setting;
/*     */   }
/*     */   
/*     */   public Setting<Boolean> setting(String name, boolean defaultValue) {
/* 162 */     BooleanSetting setting = new BooleanSetting(name, defaultValue);
/* 163 */     this.settings.add(setting);
/* 164 */     return (Setting<Boolean>)setting;
/*     */   }
/*     */   
/*     */   public Setting<Double> setting(String name, double defaultValue, double minValue, double maxValue) {
/* 168 */     DoubleSetting setting = new DoubleSetting(name, defaultValue, minValue, maxValue);
/* 169 */     this.settings.add(setting);
/* 170 */     return (Setting<Double>)setting;
/*     */   }
/*     */ 
/*     */   
/*     */   public <E extends Enum<E>> Setting<E> setting(String name, E defaultValue) {
/* 175 */     EnumSetting<E> setting = new EnumSetting(name, (Enum)defaultValue);
/* 176 */     this.settings.add(setting);
/* 177 */     return (Setting<E>)setting;
/*     */   }
/*     */   
/*     */   public Setting<Float> setting(String name, float defaultValue, float minValue, float maxValue) {
/* 181 */     FloatSetting setting = new FloatSetting(name, defaultValue, minValue, maxValue);
/* 182 */     this.settings.add(setting);
/* 183 */     return (Setting<Float>)setting;
/*     */   }
/*     */   
/*     */   public Setting<Integer> setting(String name, int defaultValue, int minValue, int maxValue) {
/* 187 */     IntSetting setting = new IntSetting(name, defaultValue, minValue, maxValue);
/* 188 */     this.settings.add(setting);
/* 189 */     return (Setting<Integer>)setting;
/*     */   }
/*     */   
/*     */   public Setting<String> setting(String name, String defaultValue) {
/* 193 */     StringSetting setting = new StringSetting(name, defaultValue);
/* 194 */     this.settings.add(setting);
/* 195 */     return (Setting<String>)setting;
/*     */   }
/*     */   
/*     */   public ConfigContainer() {}
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\config\ConfigContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */