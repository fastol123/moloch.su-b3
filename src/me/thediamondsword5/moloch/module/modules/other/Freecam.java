/*     */ package me.thediamondsword5.moloch.module.modules.other;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import me.thediamondsword5.moloch.event.events.entity.TurnEvent;
/*     */ import me.thediamondsword5.moloch.event.events.player.PlayerAttackEvent;
/*     */ import me.thediamondsword5.moloch.event.events.render.RenderViewEntityGuiEvent;
/*     */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.util.MovementInput;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.InputUpdateEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.network.FMLNetworkEvent;
/*     */ import net.spartanb312.base.client.ModuleManager;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.event.Listener;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.network.PacketEvent;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.RotationUtil;
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "Freecam", category = Category.OTHER, description = "Move camera off of player body and through walls")
/*     */ public class Freecam
/*     */   extends Module {
/*     */   public static Freecam INSTANCE;
/*  34 */   public EntityPlayer camera = null;
/*  35 */   private float prevYaw = 0.0F;
/*  36 */   private float prevPitch = 0.0F;
/*     */   private boolean rotationFlag = false;
/*  38 */   private final int cameraEntityID = 6666666;
/*     */ 
/*     */ 
/*     */   
/*  42 */   Setting<Float> horizontalSpeed = setting("HorizontalSpeed", 15.0F, 1.0F, 50.0F).des("Speed going right or left or forward or backward");
/*  43 */   Setting<Float> verticalSpeed = setting("VerticalSpeed", 15.0F, 1.0F, 50.0F).des("Speed going up or down");
/*  44 */   Setting<Boolean> rotate = setting("Rotate", false).des("Automatically rotate your player to face where you are hitting in freecam");
/*     */   
/*     */   public Freecam() {
/*  47 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  52 */     MinecraftForge.EVENT_BUS.register(this);
/*  53 */     this.moduleEnableFlag = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  58 */     MinecraftForge.EVENT_BUS.unregister(this);
/*  59 */     resetFreecam();
/*  60 */     this.moduleDisableFlag = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPacketSend(PacketEvent.Send event) {
/*  65 */     if (event.getPacket() instanceof CPacketUseEntity && ((CPacketUseEntity)event.getPacket()).func_149564_a((World)mc.field_71441_e) == mc.field_71439_g) {
/*  66 */       event.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   public void onTick() {
/*  71 */     if (mc.field_71439_g.field_70725_aQ > 0.0D) {
/*  72 */       MinecraftForge.EVENT_BUS.unregister(this);
/*  73 */       resetFreecam();
/*  74 */       ModuleManager.getModule(Freecam.class).disable();
/*     */     }
/*  76 */     else if (this.camera == null && mc.field_71439_g.field_70173_aa > 5) {
/*  77 */       this.camera = (EntityPlayer)new CameraEntity((World)mc.field_71441_e, mc.field_71449_j.func_148256_e());
/*  78 */       this.camera.func_82149_j((Entity)mc.field_71439_g);
/*  79 */       mc.field_71441_e.func_73027_a(6666666, (Entity)this.camera);
/*  80 */       resetMovement(mc.field_71439_g.field_71158_b);
/*  81 */       mc.field_175622_Z = (Entity)this.camera;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderTick() {
/*  87 */     if (((Boolean)this.rotate.getValue()).booleanValue() && this.camera != null && mc.field_71439_g.field_70173_aa > 5 && mc.field_71476_x != null && mc.field_71476_x.field_72313_a != RayTraceResult.Type.MISS && mc.field_71476_x.field_72307_f != null) {
/*     */       
/*  89 */       if (!this.rotationFlag) {
/*  90 */         this.prevYaw = mc.field_71439_g.field_70759_as;
/*  91 */         this.prevPitch = mc.field_71439_g.field_70125_A;
/*  92 */         this.rotationFlag = true;
/*     */       } 
/*     */       
/*  95 */       float[] r = RotationUtil.getRotations(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), mc.field_71476_x.field_72307_f);
/*  96 */       mc.field_71439_g.field_70177_z = r[0];
/*  97 */       mc.field_71439_g.field_70125_A = r[1];
/*     */     } 
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void onRenderGuiForRenderViewEntity(RenderViewEntityGuiEvent event) {
/* 103 */     if (this.camera != null && mc.field_71439_g.field_70173_aa > 5)
/* 104 */       event.entityPlayer = (EntityPlayer)mc.field_71439_g; 
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void onTurn(TurnEvent event) {
/* 109 */     if (event.entity == mc.field_71439_g) {
/* 110 */       this.camera.func_70082_c(event.yaw, event.pitch);
/* 111 */       event.cancel();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Listener
/*     */   public void onPlayerAttackPre(PlayerAttackEvent event) {
/* 117 */     if (event.target == mc.field_71439_g)
/* 118 */       event.cancel(); 
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onInput(InputUpdateEvent event) {
/* 123 */     resetMovement(event.getMovementInput());
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
/* 128 */     MinecraftForge.EVENT_BUS.unregister(this);
/* 129 */     resetFreecam();
/* 130 */     ModuleManager.getModule(Freecam.class).disable();
/*     */   }
/*     */   
/*     */   private void resetMovement(MovementInput movementInput) {
/* 134 */     if (movementInput instanceof net.minecraft.util.MovementInputFromOptions) {
/* 135 */       movementInput.field_192832_b = 0.0F;
/* 136 */       movementInput.field_78902_a = 0.0F;
/* 137 */       movementInput.field_187255_c = false;
/* 138 */       movementInput.field_187256_d = false;
/* 139 */       movementInput.field_187257_e = false;
/* 140 */       movementInput.field_187258_f = false;
/* 141 */       movementInput.field_78901_c = false;
/* 142 */       movementInput.field_78899_d = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void resetFreecam() {
/* 147 */     mc.field_175622_Z = (Entity)mc.field_71439_g;
/* 148 */     mc.field_71441_e.func_73028_b(6666666);
/* 149 */     this.camera = null;
/*     */     
/* 151 */     if (((Boolean)this.rotate.getValue()).booleanValue()) {
/* 152 */       mc.field_71439_g.field_70759_as = this.prevYaw;
/* 153 */       mc.field_71439_g.field_70177_z = this.prevYaw;
/* 154 */       mc.field_71439_g.field_70125_A = this.prevPitch;
/* 155 */       this.rotationFlag = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private class CameraEntity extends EntityOtherPlayerMP {
/*     */     public CameraEntity(World worldIn, GameProfile gameProfileIn) {
/* 161 */       super(worldIn, gameProfileIn);
/*     */     }
/*     */     
/*     */     public CameraEntity(World worldIn) {
/* 165 */       this(worldIn, Module.mc.field_71439_g.func_146103_bH());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void func_70636_d() {
/* 171 */       this.field_71071_by.func_70455_b(Module.mc.field_71439_g.field_71071_by);
/* 172 */       func_70626_be();
/*     */       
/* 174 */       if (Module.mc.field_71474_y.field_74351_w.func_151470_d()) { this.field_191988_bg = 1.0F; }
/* 175 */       else if (Module.mc.field_71474_y.field_74368_y.func_151470_d()) { this.field_191988_bg = -1.0F; }
/* 176 */       else { this.field_191988_bg = 0.0F; }
/*     */       
/* 178 */       if (Module.mc.field_71474_y.field_74366_z.func_151470_d()) { this.field_70702_br = 1.0F; }
/* 179 */       else if (Module.mc.field_71474_y.field_74370_x.func_151470_d()) { this.field_70702_br = -1.0F; }
/* 180 */       else { this.field_70702_br = 0.0F; }
/*     */       
/* 182 */       if (Module.mc.field_71474_y.field_74314_A.func_151470_d()) { this.field_70701_bs = 1.0F; }
/* 183 */       else if (Module.mc.field_71474_y.field_74311_E.func_151470_d()) { this.field_70701_bs = -1.0F; }
/* 184 */       else { this.field_70701_bs = 0.0F; }
/*     */       
/* 186 */       float yaw = RotationUtil.normalizeAngle((float)(Math.atan2(this.field_191988_bg, this.field_70702_br) * 57.29577951308232D) - 90.0F);
/* 187 */       double yawRadian = (this.field_70177_z - yaw) * 0.017453292519943295D;
/* 188 */       float speed = ((Float)Freecam.this.horizontalSpeed.getValue()).floatValue() / 20.0F * Math.min(Math.abs(this.field_191988_bg) + Math.abs(this.field_70702_br), 1.0F);
/*     */       
/* 190 */       this.field_70159_w = -Math.sin(yawRadian) * speed;
/* 191 */       this.field_70181_x = (this.field_70701_bs * ((Float)Freecam.this.verticalSpeed.getValue()).floatValue() / 20.0F);
/* 192 */       this.field_70179_y = Math.cos(yawRadian) * speed;
/*     */       
/* 194 */       if (Module.mc.field_71474_y.field_151444_V.func_151470_d()) {
/* 195 */         this.field_70159_w *= 1.5D;
/* 196 */         this.field_70181_x *= 1.5D;
/* 197 */         this.field_70179_y *= 1.5D;
/*     */       } 
/*     */       
/* 200 */       func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
/*     */     }
/*     */ 
/*     */     
/*     */     public float func_70047_e() {
/* 205 */       return 1.65F;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_175149_v() {
/* 210 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_82150_aj() {
/* 215 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_98034_c(EntityPlayer player) {
/* 220 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\other\Freecam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */