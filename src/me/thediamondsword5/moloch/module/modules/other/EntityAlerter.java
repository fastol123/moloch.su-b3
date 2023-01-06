/*     */ package me.thediamondsword5.moloch.module.modules.other;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.awt.Color;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.module.modules.client.ChatSettings;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.spartanb312.base.BaseCenter;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.core.setting.settings.StringSetting;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.ChatUtil;
/*     */ import net.spartanb312.base.utils.EntityUtil;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "EntityAlerter", category = Category.OTHER, description = "Alerts you when an entity comes into or leaves render distance")
/*     */ public class EntityAlerter extends Module {
/*  32 */   Setting<Boolean> anyPlayers = setting("AnyPlayers", true).des("Do notify on any player entering visual range");
/*  33 */   Setting<Boolean> notificationsMarked = setting("MessageMarked", true).des("Put client name in front of chat alert text");
/*  34 */   Setting<ChatSettings.StringColorsNoRainbow> enterMessageColor = setting("EnterMessageColor", (Enum)ChatSettings.StringColorsNoRainbow.Green).des("Color of chat alert text on enter entity");
/*  35 */   Setting<ChatSettings.StringColorsNoRainbow> exitMessageColor = setting("ExitMessageColor", (Enum)ChatSettings.StringColorsNoRainbow.Red).des("Color of chat alert text on exit entity");
/*  36 */   Setting<ChatSettings.Effects> messageEffect = setting("MessageEffect", (Enum)ChatSettings.Effects.Bold).des("Effects for chat alert text");
/*  37 */   Setting<Boolean> tracerPulse = setting("TracerPulse", true).des("Renders a temporary tracer to the entity");
/*  38 */   Setting<Float> tracerWidth = setting("TracerWidth", 1.0F, 1.0F, 5.0F).des("Thickness of tracer line").whenTrue(this.tracerPulse);
/*  39 */   Setting<Boolean> tracerSpine = setting("TracerSpine", true).des("Draw a line going up the entity's bounding box").whenTrue(this.tracerPulse);
/*  40 */   Setting<Float> tracerPulseFactor = setting("TracerPulseFactor", 1.0F, 0.1F, 10.0F).des("Speed of how fast tracer pulse fades away").whenTrue(this.tracerPulse);
/*  41 */   Setting<Color> tracerEnterColor = setting("TracerEnterColor", new Color((new Color(50, 255, 50, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 175)).whenTrue(this.tracerPulse);
/*  42 */   Setting<Color> tracerExitColor = setting("TracerExitColor", new Color((new Color(255, 50, 50, 175)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 175)).whenTrue(this.tracerPulse);
/*  43 */   Setting<String> entitiesToWhitelist = setting("WhitelistEntities", "").des("Type the name of entity to find");
/*  44 */   Setting<String> entitiesToRemoveFromWhitelist = setting("UnWhitelistEntities", "").des("Type the name entity to remove from whitelist");
/*     */ 
/*     */   
/*  47 */   private final List<String> cachedEntityWhitelist = new ArrayList<>();
/*  48 */   private final HashMap<Map.Entry<Boolean, Entity>, Float> tracerMap = new HashMap<>();
/*  49 */   private final List<Entity> localEntityList = new ArrayList<>();
/*  50 */   private final Timer tracerTimer = new Timer();
/*  51 */   private final File WHITELIST_FILE = new File("moloch.su/config/moloch_EntityAlerter_Whitelist.json");
/*     */   
/*     */   public EntityAlerter() {
/*  54 */     if (this.WHITELIST_FILE.exists()) {
/*     */       try {
/*  56 */         BufferedReader loadJson = new BufferedReader(new FileReader(this.WHITELIST_FILE));
/*  57 */         JsonObject json = (JsonObject)(new JsonParser()).parse(loadJson);
/*  58 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)json.entrySet()) {
/*  59 */           this.cachedEntityWhitelist.add(entry.getKey());
/*     */         }
/*     */       }
/*  62 */       catch (IOException e) {
/*  63 */         BaseCenter.log.error("Smt went wrong while loading entity whitelist");
/*  64 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  71 */     EntityUtil.entitiesListFlag = true;
/*  72 */     for (Entity entity : EntityUtil.entitiesList()) {
/*  73 */       if (!this.localEntityList.contains(entity)) {
/*  74 */         this.localEntityList.add(entity);
/*  75 */         onEntityAdd(entity);
/*     */       } 
/*     */     } 
/*  78 */     EntityUtil.entitiesListFlag = false;
/*     */     
/*  80 */     for (Entity entity : new ArrayList(this.localEntityList)) {
/*  81 */       if (!EntityUtil.entitiesList().contains(entity)) {
/*  82 */         this.localEntityList.remove(entity);
/*  83 */         onEntityRemoved(entity);
/*     */       } 
/*     */     } 
/*     */     
/*  87 */     if (!((StringSetting)this.entitiesToWhitelist).listening && !Objects.equals(this.entitiesToWhitelist.getValue(), "")) {
/*  88 */       writeToWhitelist((String)this.entitiesToWhitelist.getValue());
/*  89 */       this.entitiesToWhitelist.setValue("");
/*     */     } 
/*     */     
/*  92 */     if (!((StringSetting)this.entitiesToRemoveFromWhitelist).listening && !Objects.equals(this.entitiesToRemoveFromWhitelist.getValue(), "")) {
/*  93 */       removeFromWhiteList((String)this.entitiesToRemoveFromWhitelist.getValue());
/*  94 */       this.entitiesToRemoveFromWhitelist.setValue("");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderWorld(RenderEvent event) {
/* 100 */     if (((Boolean)this.tracerPulse.getValue()).booleanValue()) {
/*     */       
/* 102 */       int passedms = (int)this.tracerTimer.hasPassed();
/* 103 */       this.tracerTimer.reset();
/*     */       
/* 105 */       GL11.glPushMatrix();
/* 106 */       for (Map.Entry<Map.Entry<Boolean, Entity>, Float> entry : (new HashMap<>(this.tracerMap)).entrySet()) {
/* 107 */         float alphaThreader = MathUtilFuckYou.clamp(((Float)entry.getValue()).floatValue(), 0.0F, 300.0F);
/*     */         
/* 109 */         SpartanTessellator.drawTracer((Entity)((Map.Entry)entry.getKey()).getValue(), ((Float)this.tracerWidth.getValue()).floatValue(), ((Boolean)this.tracerSpine.getValue()).booleanValue(), 
/* 110 */             ((Boolean)((Map.Entry)entry.getKey()).getKey()).booleanValue() ? (new Color(((Color)this.tracerEnterColor.getValue()).getColorColor().getRed(), ((Color)this.tracerEnterColor.getValue()).getColorColor().getGreen(), ((Color)this.tracerEnterColor.getValue()).getColorColor().getBlue(), (int)(((Color)this.tracerEnterColor.getValue()).getAlpha() * alphaThreader / 300.0F))).getRGB() : (new Color(((Color)this.tracerExitColor
/* 111 */               .getValue()).getColorColor().getRed(), ((Color)this.tracerExitColor.getValue()).getColorColor().getGreen(), ((Color)this.tracerExitColor.getValue()).getColorColor().getBlue(), (int)(((Color)this.tracerExitColor.getValue()).getAlpha() * alphaThreader / 300.0F))).getRGB());
/*     */         
/* 113 */         if (passedms < 1000) {
/* 114 */           alphaThreader -= ((Float)this.tracerPulseFactor.getValue()).floatValue() / 10.0F * passedms;
/* 115 */           if (alphaThreader < 0.0F) {
/* 116 */             this.tracerMap.remove(entry.getKey());
/*     */             continue;
/*     */           } 
/* 119 */           this.tracerMap.put(entry.getKey(), Float.valueOf(alphaThreader));
/*     */         } 
/*     */       } 
/*     */       
/* 123 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onEntityAdd(Entity entity) {
/* 128 */     if (mc.field_175622_Z != null && entity != null && !entity.func_70005_c_().equals(mc.field_175622_Z.func_70005_c_()) && (this.cachedEntityWhitelist.contains(entity.func_70005_c_()) || (entity instanceof net.minecraft.entity.player.EntityPlayer && ((Boolean)this.anyPlayers.getValue()).booleanValue()))) {
/* 129 */       if (((Boolean)this.tracerPulse.getValue()).booleanValue()) this.tracerMap.put(new AbstractMap.SimpleEntry<>(Boolean.valueOf(true), entity), Float.valueOf(300.0F));
/*     */       
/* 131 */       if (((Boolean)this.notificationsMarked.getValue()).booleanValue()) {
/* 132 */         ChatUtil.printChatMessage(entity.func_70005_c_() + " " + ChatUtil.SECTIONSIGN + ChatSettings.INSTANCE.colorString(this.enterMessageColor) + ChatUtil.effectString(this.messageEffect) + "has entered visual range");
/*     */       } else {
/*     */         
/* 135 */         ChatUtil.printRawChatMessage(entity.func_70005_c_() + " " + ChatUtil.SECTIONSIGN + ChatSettings.INSTANCE.colorString(this.enterMessageColor) + ChatUtil.effectString(this.messageEffect) + "has entered visual range");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onEntityRemoved(Entity entity) {
/* 141 */     if (mc.field_175622_Z != null && entity != null && !entity.func_70005_c_().equals(mc.field_175622_Z.func_70005_c_()) && (this.cachedEntityWhitelist.contains(entity.func_70005_c_()) || (entity instanceof net.minecraft.entity.player.EntityPlayer && ((Boolean)this.anyPlayers.getValue()).booleanValue()))) {
/* 142 */       if (((Boolean)this.tracerPulse.getValue()).booleanValue()) this.tracerMap.put(new AbstractMap.SimpleEntry<>(Boolean.valueOf(false), entity), Float.valueOf(300.0F));
/*     */       
/* 144 */       if (((Boolean)this.notificationsMarked.getValue()).booleanValue()) {
/* 145 */         ChatUtil.printChatMessage(entity.func_70005_c_() + " " + ChatUtil.SECTIONSIGN + ChatSettings.INSTANCE.colorString(this.exitMessageColor) + ChatUtil.effectString(this.messageEffect) + "has left visual range");
/*     */       } else {
/*     */         
/* 148 */         ChatUtil.printRawChatMessage(entity.func_70005_c_() + " " + ChatUtil.SECTIONSIGN + ChatSettings.INSTANCE.colorString(this.exitMessageColor) + ChatUtil.effectString(this.messageEffect) + "has left visual range");
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateJSon() throws IOException {
/* 154 */     JsonObject json = new JsonObject();
/*     */     
/* 156 */     for (String str : this.cachedEntityWhitelist) {
/* 157 */       json.addProperty(str, "");
/*     */     }
/*     */     
/* 160 */     PrintWriter saveJSon = new PrintWriter(new FileWriter(this.WHITELIST_FILE));
/* 161 */     saveJSon.println((new GsonBuilder()).setPrettyPrinting().create().toJson((JsonElement)json));
/* 162 */     saveJSon.close();
/*     */   }
/*     */   
/*     */   private void writeToWhitelist(String entityName) {
/*     */     try {
/* 167 */       if (!this.WHITELIST_FILE.exists()) {
/* 168 */         this.WHITELIST_FILE.getParentFile().mkdirs();
/*     */         try {
/* 170 */           this.WHITELIST_FILE.createNewFile();
/* 171 */         } catch (Exception exception) {}
/*     */       } 
/*     */       
/* 174 */       this.cachedEntityWhitelist.add(entityName);
/* 175 */       updateJSon();
/*     */     }
/* 177 */     catch (Exception e) {
/* 178 */       BaseCenter.log.error("Smt went wrong while trying to save entity name to whitelist");
/* 179 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeFromWhiteList(String entityName) {
/*     */     try {
/* 185 */       if (!this.WHITELIST_FILE.exists()) {
/* 186 */         BaseCenter.log.error("Whitelist file doesn't exist");
/*     */       } else {
/*     */         
/* 189 */         this.cachedEntityWhitelist.remove(entityName);
/* 190 */         updateJSon();
/*     */       }
/*     */     
/* 193 */     } catch (Exception e) {
/* 194 */       BaseCenter.log.error("Smt went wrong while trying to save entity name to whitelist");
/* 195 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\other\EntityAlerter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */