/*     */ package me.thediamondsword5.moloch.module.modules.visuals;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.awt.Color;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import me.thediamondsword5.moloch.client.EnemyManager;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*     */ import net.minecraft.client.model.ModelBiped;
/*     */ import net.minecraft.client.model.ModelPlayer;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.world.World;
/*     */ import net.spartanb312.base.client.FriendManager;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.network.PacketEvent;
/*     */ import net.spartanb312.base.event.events.render.RenderEvent;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import net.spartanb312.base.utils.graphics.RenderHelper;
/*     */ import net.spartanb312.base.utils.graphics.SpartanTessellator;
/*     */ import net.spartanb312.base.utils.math.Pair;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "PopRender", category = Category.VISUALS, description = "Renders their player model when someone pops a totem")
/*     */ public class PopRender extends Module {
/*  34 */   Setting<Page> page = setting("Page", Page.General);
/*  35 */   Setting<Boolean> self = setting("Self", false).des("Render yourself popping").whenAtMode(this.page, Page.General);
/*  36 */   Setting<Boolean> points = setting("Points", false).des("Renders vertices of model").whenAtMode(this.page, Page.General);
/*  37 */   Setting<Float> pointSize = setting("PointSize", 1.0F, 0.0F, 10.0F).des("Size of points on model").whenTrue(this.points).whenAtMode(this.page, Page.General);
/*  38 */   Setting<Boolean> solid = setting("Solid", true).des("Render filled model").whenAtMode(this.page, Page.General);
/*  39 */   Setting<Boolean> texture = setting("Texture", false).des("Renders player texture on filled model").whenTrue(this.solid).whenAtMode(this.page, Page.General);
/*  40 */   Setting<Boolean> lines = setting("Lines", true).des("Render wireframe model").whenAtMode(this.page, Page.General);
/*  41 */   Setting<Float> linesWidth = setting("LinesWidth", 1.0F, 1.0F, 5.0F).des("Width of wireframe model lines").whenTrue(this.lines).whenAtMode(this.page, Page.General);
/*  42 */   Setting<Boolean> skeleton = setting("Skeleton", false).des("Renders skeleton of player").whenAtMode(this.page, Page.General);
/*  43 */   Setting<Float> skeletonLinesWidth = setting("SkeletonLinesWidth", 1.0F, 1.0F, 5.0F).des("Width of lines in skeleton").whenTrue(this.skeleton).whenAtMode(this.page, Page.General);
/*  44 */   Setting<Boolean> skeletonFadeLimbs = setting("SkeletonFadeLimbs", false).des("Fade out the ends of skeleton's limbs").whenTrue(this.skeleton).whenAtMode(this.page, Page.General);
/*  45 */   Setting<Boolean> limbRotations = setting("LimbRotations", true).des("Records limb rotations when the player is popped onto pop render").whenAtMode(this.page, Page.General);
/*  46 */   Setting<Boolean> multiRender = setting("MultiRender", true).des("Allows client to render multiple models for one player when they chain pop").whenAtMode(this.page, Page.General);
/*  47 */   Setting<Movement> movement = setting("Movement", Movement.None).des("Movement of pop model").whenAtMode(this.page, Page.General);
/*  48 */   Setting<Float> moveSpeed = setting("MovementSpeed", 1.0F, 0.1F, 3.0F).only(v -> (this.movement.getValue() != Movement.None)).whenAtMode(this.page, Page.General);
/*  49 */   Setting<Float> fadeSpeed = setting("FadeSpeed", 2.0F, 0.1F, 3.0F).des("Speed of pop render fading away").whenAtMode(this.page, Page.General);
/*     */   
/*  51 */   Setting<Color> selfPointColor = setting("SelfPointColor", new Color((new Color(255, 255, 255, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 120)).whenTrue(this.points).whenTrue(this.self).whenAtMode(this.page, Page.Colors);
/*  52 */   Setting<Color> selfSolidColor = setting("SelfSolidColor", new Color((new Color(255, 255, 255, 40)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 40)).whenTrue(this.solid).whenTrue(this.self).whenAtMode(this.page, Page.Colors);
/*  53 */   Setting<Color> selfLinesColor = setting("SelfLinesColor", new Color((new Color(255, 255, 255, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 120)).whenTrue(this.lines).whenTrue(this.self).whenAtMode(this.page, Page.Colors);
/*  54 */   Setting<Color> selfSkeletonColor = setting("SelfSkeletonColor", new Color((new Color(255, 255, 255, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 120)).whenTrue(this.skeleton).whenTrue(this.self).whenAtMode(this.page, Page.Colors);
/*     */   
/*  56 */   Setting<Color> playerPointColor = setting("PlayerPointColor", new Color((new Color(180, 255, 180, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 180, 255, 180, 120)).whenTrue(this.points).whenAtMode(this.page, Page.Colors);
/*  57 */   Setting<Color> playerSolidColor = setting("PlayerSolidColor", new Color((new Color(180, 255, 180, 40)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 180, 255, 180, 40)).whenTrue(this.solid).whenAtMode(this.page, Page.Colors);
/*  58 */   Setting<Color> playerLinesColor = setting("PlayerLinesColor", new Color((new Color(180, 255, 180, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 180, 255, 180, 120)).whenTrue(this.lines).whenAtMode(this.page, Page.Colors);
/*  59 */   Setting<Color> playerSkeletonColor = setting("PlayerSkeletonColor", new Color((new Color(180, 255, 180, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 180, 255, 180, 120)).whenTrue(this.skeleton).whenAtMode(this.page, Page.Colors);
/*     */   
/*  61 */   Setting<Color> friendPointColor = setting("FriendPointColor", new Color((new Color(100, 255, 255, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 255, 255, 120)).whenTrue(this.points).whenAtMode(this.page, Page.Colors);
/*  62 */   Setting<Color> friendSolidColor = setting("FriendSolidColor", new Color((new Color(100, 255, 255, 40)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 255, 255, 40)).whenTrue(this.solid).whenAtMode(this.page, Page.Colors);
/*  63 */   Setting<Color> friendLinesColor = setting("FriendLinesColor", new Color((new Color(100, 255, 255, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 255, 255, 120)).whenTrue(this.lines).whenAtMode(this.page, Page.Colors);
/*  64 */   Setting<Color> friendSkeletonColor = setting("FriendSkeletonColor", new Color((new Color(100, 255, 255, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 255, 255, 120)).whenTrue(this.skeleton).whenAtMode(this.page, Page.Colors);
/*     */   
/*  66 */   Setting<Color> enemyPointColor = setting("EnemyPointColor", new Color((new Color(255, 100, 100, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 100, 120)).whenTrue(this.points).whenAtMode(this.page, Page.Colors);
/*  67 */   Setting<Color> enemySolidColor = setting("EnemySolidColor", new Color((new Color(255, 100, 100, 40)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 100, 40)).whenTrue(this.solid).whenAtMode(this.page, Page.Colors);
/*  68 */   Setting<Color> enemyLinesColor = setting("EnemyLinesColor", new Color((new Color(255, 100, 100, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 100, 120)).whenTrue(this.lines).whenAtMode(this.page, Page.Colors);
/*  69 */   Setting<Color> enemySkeletonColor = setting("EnemySkeletonColor", new Color((new Color(255, 100, 100, 120)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 100, 100, 120)).whenTrue(this.skeleton).whenAtMode(this.page, Page.Colors);
/*     */   
/*  71 */   private final ModelPlayer model = new ModelPlayer(0.0F, false);
/*  72 */   private final Timer timer = new Timer();
/*  73 */   private final HashMap<Integer, Pair<EntityOtherPlayerMP, Float>> popMapSingle = new HashMap<>();
/*  74 */   private final HashMap<Integer, Float> popMoveMapSingle = new HashMap<>();
/*  75 */   private final HashMap<EntityOtherPlayerMP, Float> popMapMulti = new HashMap<>();
/*  76 */   private final HashMap<EntityOtherPlayerMP, Float> popMoveMapMulti = new HashMap<>();
/*     */   
/*     */   public PopRender() {
/*  79 */     this.model.field_78091_s = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/*  84 */     if (mc.field_71441_e != null && mc.field_71439_g != null && event.getPacket() instanceof SPacketEntityStatus && ((SPacketEntityStatus)event.getPacket()).func_149160_c() == 35) {
/*  85 */       EntityPlayer player1 = (EntityPlayer)((SPacketEntityStatus)event.getPacket()).func_149161_a((World)mc.field_71441_e);
/*     */       
/*  87 */       if (player1 == mc.field_71439_g && !((Boolean)this.self.getValue()).booleanValue()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  92 */       EntityOtherPlayerMP player = new EntityOtherPlayerMP((World)mc.field_71441_e, new GameProfile(mc.field_71439_g.func_110124_au(), (player1.func_70005_c_() == null) ? "" : player1.func_70005_c_()));
/*  93 */       player.func_82149_j((Entity)player1);
/*     */       
/*  95 */       if (player1.func_70093_af()) player.func_70095_a(true); 
/*  96 */       player.field_70733_aJ = player1.field_70733_aJ;
/*  97 */       player.field_184619_aG = player1.field_184619_aG;
/*  98 */       player.field_70721_aZ = player1.field_70721_aZ;
/*     */       
/* 100 */       if (((Boolean)this.multiRender.getValue()).booleanValue()) {
/* 101 */         this.popMapMulti.put(player, Float.valueOf(300.0F));
/*     */       } else {
/*     */         
/* 104 */         this.popMapSingle.put(Integer.valueOf(player1.func_145782_y()), new Pair(player, Float.valueOf(300.0F)));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderWorld(RenderEvent event) {
/* 111 */     int passedms = (int)this.timer.hasPassed();
/* 112 */     this.timer.reset();
/*     */     
/* 114 */     if (((Boolean)this.multiRender.getValue()).booleanValue()) {
/* 115 */       for (Map.Entry<EntityOtherPlayerMP, Float> entry : (new HashMap<>(this.popMapMulti)).entrySet()) {
/* 116 */         if (((Float)entry.getValue()).floatValue() <= 0.0F) {
/* 117 */           this.popMapMulti.remove(entry.getKey());
/* 118 */           this.popMoveMapMulti.remove(entry.getKey());
/*     */           
/*     */           continue;
/*     */         } 
/* 122 */         render(entry.getKey(), ((Boolean)this.limbRotations.getValue()).booleanValue() ? ((EntityOtherPlayerMP)entry.getKey()).field_184619_aG : 0.0F, 
/* 123 */             ((Boolean)this.limbRotations.getValue()).booleanValue() ? ((EntityOtherPlayerMP)entry.getKey()).field_70721_aZ : 0.0F, 
/* 124 */             ((Boolean)this.limbRotations.getValue()).booleanValue() ? ((EntityOtherPlayerMP)entry.getKey()).field_70733_aJ : 0.0F, ((Float)entry
/* 125 */             .getValue()).floatValue(), 
/* 126 */             (this.popMoveMapMulti.get(entry.getKey()) == null) ? 0.0F : ((Float)this.popMoveMapMulti.get(entry.getKey())).floatValue());
/*     */         
/* 128 */         if (passedms < 1000) {
/* 129 */           this.popMapMulti.put(entry.getKey(), Float.valueOf(((Float)entry.getValue()).floatValue() - passedms * ((Float)this.fadeSpeed.getValue()).floatValue() / 5.0F));
/*     */           
/* 131 */           if (this.movement.getValue() != Movement.None) {
/* 132 */             this.popMoveMapMulti.put(entry.getKey(), Float.valueOf(((this.popMoveMapMulti.get(entry.getKey()) == null) ? 0.0F : ((Float)this.popMoveMapMulti.get(entry.getKey())).floatValue()) + passedms * ((Float)this.moveSpeed.getValue()).floatValue() / 1400.0F));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 138 */       for (Map.Entry<Integer, Pair<EntityOtherPlayerMP, Float>> entry : (new HashMap<>(this.popMapSingle)).entrySet()) {
/* 139 */         if (((Float)((Pair)entry.getValue()).b).floatValue() <= 0.0F) {
/* 140 */           this.popMapSingle.remove(entry.getKey());
/* 141 */           this.popMoveMapSingle.remove(entry.getKey());
/*     */           
/*     */           continue;
/*     */         } 
/* 145 */         render((EntityOtherPlayerMP)((Pair)entry.getValue()).a, ((Boolean)this.limbRotations.getValue()).booleanValue() ? ((EntityOtherPlayerMP)((Pair)entry.getValue()).a).field_184619_aG : 0.0F, 
/* 146 */             ((Boolean)this.limbRotations.getValue()).booleanValue() ? ((EntityOtherPlayerMP)((Pair)entry.getValue()).a).field_70721_aZ : 0.0F, 
/* 147 */             ((Boolean)this.limbRotations.getValue()).booleanValue() ? ((EntityOtherPlayerMP)((Pair)entry.getValue()).a).field_70733_aJ : 0.0F, (
/* 148 */             (Float)((Pair)entry.getValue()).b).floatValue(), 
/* 149 */             (this.popMoveMapSingle.get(entry.getKey()) == null) ? 0.0F : ((Float)this.popMoveMapSingle.get(entry.getKey())).floatValue());
/*     */         
/* 151 */         if (passedms < 1000) {
/* 152 */           this.popMapSingle.put(entry.getKey(), new Pair(((Pair)entry.getValue()).a, Float.valueOf(((Float)((Pair)entry.getValue()).b).floatValue() - passedms * ((Float)this.fadeSpeed.getValue()).floatValue() / 5.0F)));
/*     */           
/* 154 */           if (this.movement.getValue() != Movement.None) {
/* 155 */             this.popMoveMapSingle.put(entry.getKey(), Float.valueOf(((this.popMoveMapSingle.get(entry.getKey()) == null) ? 0.0F : ((Float)this.popMoveMapSingle.get(entry.getKey())).floatValue()) + passedms * ((Float)this.moveSpeed.getValue()).floatValue() / 1400.0F));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void render(EntityOtherPlayerMP entityPlayer, float limbSwing, float limbSwingAmount, float swingProgress, float alphaFactor, float moveFactor) {
/* 163 */     EntityOtherPlayerMP tempPlayer = new EntityOtherPlayerMP((World)mc.field_71441_e, new GameProfile(mc.field_71439_g.func_110124_au(), ""));
/* 164 */     tempPlayer.func_82149_j((Entity)entityPlayer);
/*     */     
/* 166 */     if (this.movement.getValue() != Movement.None) {
/* 167 */       tempPlayer.func_70012_b(entityPlayer.field_70165_t, entityPlayer.field_70163_u + (moveFactor * ((this.movement.getValue() == Movement.Rising) ? 1.0F : -1.0F)), entityPlayer.field_70161_v, entityPlayer.field_70177_z, entityPlayer.field_70125_A);
/*     */     }
/*     */     
/* 170 */     if (RenderHelper.isInViewFrustrum((Entity)tempPlayer)) {
/*     */       
/* 172 */       this.model.field_78116_c.field_78806_j = (((Boolean)this.solid.getValue()).booleanValue() && ((Boolean)this.texture.getValue()).booleanValue());
/* 173 */       this.model.field_78115_e.field_78806_j = (((Boolean)this.solid.getValue()).booleanValue() && ((Boolean)this.texture.getValue()).booleanValue());
/* 174 */       this.model.field_178734_a.field_78806_j = (((Boolean)this.solid.getValue()).booleanValue() && ((Boolean)this.texture.getValue()).booleanValue());
/* 175 */       this.model.field_178733_c.field_78806_j = (((Boolean)this.solid.getValue()).booleanValue() && ((Boolean)this.texture.getValue()).booleanValue());
/* 176 */       this.model.field_178732_b.field_78806_j = (((Boolean)this.solid.getValue()).booleanValue() && ((Boolean)this.texture.getValue()).booleanValue());
/* 177 */       this.model.field_178731_d.field_78806_j = (((Boolean)this.solid.getValue()).booleanValue() && ((Boolean)this.texture.getValue()).booleanValue());
/* 178 */       entityPlayer.field_184622_au = EnumHand.MAIN_HAND;
/* 179 */       entityPlayer.field_70761_aq = entityPlayer.field_70177_z;
/* 180 */       entityPlayer.field_70760_ar = entityPlayer.field_70177_z;
/* 181 */       this.model.field_78117_n = entityPlayer.func_70093_af();
/* 182 */       this.model.field_78095_p = swingProgress;
/*     */       
/* 184 */       if (this.movement.getValue() != Movement.None) {
/* 185 */         GL11.glTranslatef(0.0F, moveFactor * ((this.movement.getValue() == Movement.Rising) ? 1.0F : -1.0F), 0.0F);
/*     */       }
/*     */       
/* 188 */       if (((Boolean)this.solid.getValue()).booleanValue() || ((Boolean)this.lines.getValue()).booleanValue() || ((Boolean)this.points.getValue()).booleanValue()) {
/* 189 */         SpartanTessellator.drawPlayer(entityPlayer, this.model, limbSwing, limbSwingAmount, entityPlayer.field_70759_as, entityPlayer.field_70125_A, ((Boolean)this.solid
/* 190 */             .getValue()).booleanValue(), ((Boolean)this.lines.getValue()).booleanValue(), ((Boolean)this.points.getValue()).booleanValue(), ((Float)this.linesWidth.getValue()).floatValue(), ((Float)this.pointSize.getValue()).floatValue(), alphaFactor, ((Boolean)this.texture.getValue()).booleanValue(), swingProgress, ((Color)this.friendSolidColor
/* 191 */             .getValue()).getColor(), ((Color)this.friendLinesColor.getValue()).getColor(), ((Color)this.friendPointColor.getValue()).getColor(), ((Color)this.enemySolidColor
/* 192 */             .getValue()).getColor(), ((Color)this.enemyLinesColor.getValue()).getColor(), ((Color)this.enemyPointColor.getValue()).getColor(), ((Color)this.selfSolidColor
/* 193 */             .getValue()).getColor(), ((Color)this.selfLinesColor.getValue()).getColor(), ((Color)this.selfPointColor.getValue()).getColor(), ((Color)this.playerSolidColor
/* 194 */             .getValue()).getColor(), ((Color)this.playerLinesColor.getValue()).getColor(), ((Color)this.playerPointColor.getValue()).getColor());
/*     */       }
/*     */       
/* 197 */       if (((Boolean)this.skeleton.getValue()).booleanValue()) {
/* 198 */         this.model.func_78087_a(limbSwing, limbSwingAmount, 0.0F, entityPlayer.field_70759_as, entityPlayer.field_70125_A, 0.0625F, (Entity)entityPlayer);
/* 199 */         float[][] rotations = new float[5][3];
/* 200 */         rotations[0] = SpartanTessellator.getRotations(this.model.field_78116_c);
/* 201 */         int color = entityPlayer.func_70005_c_().equals(mc.field_71439_g.func_70005_c_()) ? ((Color)this.selfSkeletonColor.getValue()).getColor() : (FriendManager.isFriend((Entity)entityPlayer) ? ((Color)this.friendSkeletonColor.getValue()).getColor() : (EnemyManager.isEnemy((Entity)entityPlayer) ? ((Color)this.enemySkeletonColor.getValue()).getColor() : ((Color)this.playerSkeletonColor.getValue()).getColor()));
/*     */         
/* 203 */         SpartanTessellator.drawSkeleton((EntityPlayer)entityPlayer, ((Boolean)this.limbRotations.getValue()).booleanValue() ? SpartanTessellator.getRotationsFromModel((ModelBiped)this.model) : rotations, ((Float)this.skeletonLinesWidth.getValue()).floatValue(), ((Boolean)this.skeletonFadeLimbs.getValue()).booleanValue(), false, new Color(0), new Color(0), (new Color(color >>> 16 & 0xFF, color >>> 8 & 0xFF, color & 0xFF, (int)((color >>> 24 & 0xFF) * alphaFactor / 300.0F)))
/* 204 */             .getRGB());
/*     */       } 
/*     */ 
/*     */       
/* 208 */       if (this.movement.getValue() != Movement.None)
/* 209 */         GL11.glTranslatef(0.0F, moveFactor * ((this.movement.getValue() == Movement.Rising) ? -1.0F : 1.0F), 0.0F); 
/*     */     } 
/*     */   }
/*     */   
/*     */   enum Page
/*     */   {
/* 215 */     General,
/* 216 */     Colors;
/*     */   }
/*     */   
/*     */   enum Movement {
/* 220 */     Rising,
/* 221 */     Falling,
/* 222 */     None;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\visuals\PopRender.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */