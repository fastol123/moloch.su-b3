/*      */ package net.spartanb312.base.utils.graphics;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import me.thediamondsword5.moloch.client.EnemyManager;
/*      */ import me.thediamondsword5.moloch.module.modules.visuals.ESP;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*      */ import net.minecraft.client.model.ModelBiped;
/*      */ import net.minecraft.client.model.ModelPlayer;
/*      */ import net.minecraft.client.model.ModelRenderer;
/*      */ import net.minecraft.client.renderer.BufferBuilder;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.World;
/*      */ import net.spartanb312.base.client.FriendManager;
/*      */ import net.spartanb312.base.command.Command;
/*      */ import net.spartanb312.base.utils.ColorUtil;
/*      */ import net.spartanb312.base.utils.EntityUtil;
/*      */ import net.spartanb312.base.utils.MathUtilFuckYou;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SpartanTessellator
/*      */   extends Tessellator
/*      */ {
/*   38 */   public static SpartanTessellator INSTANCE = new SpartanTessellator();
/*      */   
/*      */   public SpartanTessellator() {
/*   41 */     super(2097152);
/*      */   }
/*      */   
/*      */   public static void prepareGL() {
/*   45 */     GL11.glBlendFunc(770, 771);
/*   46 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*   47 */     GL11.glDisable(3553);
/*   48 */     GlStateManager.func_179132_a(false);
/*   49 */     GL11.glEnable(3042);
/*   50 */     GlStateManager.func_179097_i();
/*   51 */     GL11.glDisable(3008);
/*   52 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public static void releaseGL() {
/*   56 */     GlStateManager.func_179132_a(true);
/*   57 */     GL11.glEnable(3553);
/*   58 */     GL11.glEnable(3042);
/*   59 */     GlStateManager.func_179126_j();
/*   60 */     GL11.glEnable(3008);
/*   61 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public static void begin(int mode) {
/*   65 */     INSTANCE.func_178180_c().func_181668_a(mode, DefaultVertexFormats.field_181706_f);
/*      */   }
/*      */   
/*      */   public static void render() {
/*   69 */     INSTANCE.func_78381_a();
/*      */   }
/*      */   
/*      */   public static void drawFlatFullBox(Vec3d pos, boolean useDepth, int color) {
/*   73 */     int a = color >>> 24 & 0xFF;
/*   74 */     int r = color >>> 16 & 0xFF;
/*   75 */     int g = color >>> 8 & 0xFF;
/*   76 */     int b = color & 0xFF;
/*   77 */     GL11.glDisable(2884);
/*   78 */     drawFlatFilledBox(INSTANCE.func_178180_c(), useDepth, (float)pos.field_72450_a, (float)pos.field_72448_b, (float)pos.field_72449_c, 1.0F, 1.0F, r, g, b, a);
/*   79 */     GL11.glEnable(2884);
/*      */   }
/*      */   
/*      */   public static void drawFlatLineBox(Vec3d pos, boolean useDepth, float width, int color) {
/*   83 */     int a = color >>> 24 & 0xFF;
/*   84 */     int r = color >>> 16 & 0xFF;
/*   85 */     int g = color >>> 8 & 0xFF;
/*   86 */     int b = color & 0xFF;
/*   87 */     GL11.glLineWidth(width);
/*   88 */     drawFlatLineBox(INSTANCE.func_178180_c(), useDepth, (float)pos.field_72450_a, (float)pos.field_72448_b, (float)pos.field_72449_c, 1.0F, 1.0F, r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawBBFullBox(Entity entity, int color) {
/*   92 */     int a = color >>> 24 & 0xFF;
/*   93 */     int r = color >>> 16 & 0xFF;
/*   94 */     int g = color >>> 8 & 0xFF;
/*   95 */     int b = color & 0xFF;
/*   96 */     AxisAlignedBB bb = entity.func_174813_aQ();
/*   97 */     Vec3d entityPos = EntityUtil.getInterpolatedEntityPos(entity, EntityUtil.mc.func_184121_ak());
/*   98 */     drawFilledBox(INSTANCE.func_178180_c(), false, (float)(entityPos.field_72450_a - (bb.field_72336_d - bb.field_72340_a + 0.05D) / 2.0D), (float)entityPos.field_72448_b, (float)(entityPos.field_72449_c - (bb.field_72334_f - bb.field_72339_c + 0.05D) / 2.0D), (float)(bb.field_72336_d - bb.field_72340_a + 0.05D), (float)(bb.field_72337_e - bb.field_72338_b), (float)(bb.field_72334_f - bb.field_72339_c + 0.05D), r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawBBLineBox(Entity entity, float width, int color) {
/*  102 */     int a = color >>> 24 & 0xFF;
/*  103 */     int r = color >>> 16 & 0xFF;
/*  104 */     int g = color >>> 8 & 0xFF;
/*  105 */     int b = color & 0xFF;
/*  106 */     AxisAlignedBB bb = entity.func_174813_aQ();
/*  107 */     Vec3d entityPos = EntityUtil.getInterpolatedEntityPos(entity, EntityUtil.mc.func_184121_ak());
/*  108 */     GL11.glLineWidth(width);
/*  109 */     drawLineBox(INSTANCE.func_178180_c(), false, (float)(entityPos.field_72450_a - (bb.field_72336_d - bb.field_72340_a + 0.05D) / 2.0D), (float)entityPos.field_72448_b, (float)(entityPos.field_72449_c - (bb.field_72334_f - bb.field_72339_c + 0.05D) / 2.0D), (float)(bb.field_72336_d - bb.field_72340_a + 0.05D), (float)(bb.field_72337_e - bb.field_72338_b), (float)(bb.field_72334_f - bb.field_72339_c + 0.05D), r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawBlockBBFullBox(BlockPos blockPos, float scale, int color) {
/*  113 */     int a = color >>> 24 & 0xFF;
/*  114 */     int r = color >>> 16 & 0xFF;
/*  115 */     int g = color >>> 8 & 0xFF;
/*  116 */     int b = color & 0xFF;
/*  117 */     AxisAlignedBB bb = getBoundingFromPos(blockPos);
/*  118 */     drawBetterBoundingBoxFilled(INSTANCE.func_178180_c(), bb, new Vec3d((blockPos.field_177962_a + 0.5F), (blockPos.field_177960_b + 0.5F), (blockPos.field_177961_c + 0.5F)), scale, r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawBlockBBLineBox(BlockPos blockPos, float scale, float width, int color) {
/*  122 */     int a = color >>> 24 & 0xFF;
/*  123 */     int r = color >>> 16 & 0xFF;
/*  124 */     int g = color >>> 8 & 0xFF;
/*  125 */     int b = color & 0xFF;
/*  126 */     AxisAlignedBB bb = getBoundingFromPos(blockPos);
/*  127 */     GL11.glLineWidth(width);
/*  128 */     drawBetterBoundingBoxLines(INSTANCE.func_178180_c(), bb, new Vec3d((blockPos.field_177962_a + 0.5F), (blockPos.field_177960_b + 0.5F), (blockPos.field_177961_c + 0.5F)), scale, r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawBlockFullBox(Vec3d vec, boolean useDepth, float height, int color) {
/*  132 */     int a = color >>> 24 & 0xFF;
/*  133 */     int r = color >>> 16 & 0xFF;
/*  134 */     int g = color >>> 8 & 0xFF;
/*  135 */     int b = color & 0xFF;
/*  136 */     drawFilledBox(INSTANCE.func_178180_c(), useDepth, (float)vec.field_72450_a, (float)vec.field_72448_b, (float)vec.field_72449_c, 1.0F, height, 1.0F, r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawGradientBlockFullBox(Vec3d vec, boolean useDepth, boolean sidesOnly, float height, int color1, int color2) {
/*  140 */     int a1 = color1 >>> 24 & 0xFF;
/*  141 */     int r1 = color1 >>> 16 & 0xFF;
/*  142 */     int g1 = color1 >>> 8 & 0xFF;
/*  143 */     int b1 = color1 & 0xFF;
/*      */     
/*  145 */     int a2 = color2 >>> 24 & 0xFF;
/*  146 */     int r2 = color2 >>> 16 & 0xFF;
/*  147 */     int g2 = color2 >>> 8 & 0xFF;
/*  148 */     int b2 = color2 & 0xFF;
/*      */     
/*  150 */     drawGradientFilledBox(INSTANCE.func_178180_c(), useDepth, sidesOnly, (float)vec.field_72450_a, (float)vec.field_72448_b, (float)vec.field_72449_c, 1.0F, height, 1.0F, r1, g1, b1, a1, r2, g2, b2, a2);
/*      */   }
/*      */   
/*      */   public static void drawBlockLineBox(Vec3d vec, boolean useDepth, float height, float lineWidth, int color) {
/*  154 */     int a = color >>> 24 & 0xFF;
/*  155 */     int r = color >>> 16 & 0xFF;
/*  156 */     int g = color >>> 8 & 0xFF;
/*  157 */     int b = color & 0xFF;
/*  158 */     GL11.glLineWidth(lineWidth);
/*  159 */     drawLineBox(INSTANCE.func_178180_c(), useDepth, (float)vec.field_72450_a, (float)vec.field_72448_b, (float)vec.field_72449_c, 1.0F, height, 1.0F, r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawGradientBlockLineBox(Vec3d vec, boolean useDepth, float height, float lineWidth, int color1, int color2) {
/*  163 */     int a1 = color1 >>> 24 & 0xFF;
/*  164 */     int r1 = color1 >>> 16 & 0xFF;
/*  165 */     int g1 = color1 >>> 8 & 0xFF;
/*  166 */     int b1 = color1 & 0xFF;
/*      */     
/*  168 */     int a2 = color2 >>> 24 & 0xFF;
/*  169 */     int r2 = color2 >>> 16 & 0xFF;
/*  170 */     int g2 = color2 >>> 8 & 0xFF;
/*  171 */     int b2 = color2 & 0xFF;
/*      */     
/*  173 */     GL11.glLineWidth(lineWidth);
/*  174 */     drawGradientLineBox(INSTANCE.func_178180_c(), useDepth, (float)vec.field_72450_a, (float)vec.field_72448_b, (float)vec.field_72449_c, 1.0F, height, 1.0F, r1, g1, b1, a1, r2, g2, b2, a2);
/*      */   }
/*      */   
/*      */   public static void drawDoubleBlockFullBox(Vec3d vec1, Vec3d vec2, boolean useDepth, int color) {
/*  178 */     int a = color >>> 24 & 0xFF;
/*  179 */     int r = color >>> 16 & 0xFF;
/*  180 */     int g = color >>> 8 & 0xFF;
/*  181 */     int b = color & 0xFF;
/*  182 */     drawTwoPointFilledBox(INSTANCE.func_178180_c(), useDepth, (float)(vec1.field_72450_a + 0.5D), (float)vec1.field_72448_b, (float)(vec1.field_72449_c + 0.5D), (float)(vec2.field_72450_a + 0.5D), (float)vec2.field_72448_b, (float)(vec2.field_72449_c + 0.5D), r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawDoubleBlockFullPyramid(Vec3d vec1, Vec3d vec2, boolean useDepth, boolean flagx, boolean flagz, int color) {
/*  186 */     int a = color >>> 24 & 0xFF;
/*  187 */     int r = color >>> 16 & 0xFF;
/*  188 */     int g = color >>> 8 & 0xFF;
/*  189 */     int b = color & 0xFF;
/*  190 */     drawTwoPointFilledPyramid(INSTANCE.func_178180_c(), useDepth, flagx, flagz, (float)(vec1.field_72450_a + 0.5D), (float)vec1.field_72448_b, (float)(vec1.field_72449_c + 0.5D), (float)(vec2.field_72450_a + 0.5D), (float)vec2.field_72448_b, (float)(vec2.field_72449_c + 0.5D), r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawGradientDoubleBlockFullPyramid(Vec3d vec1, Vec3d vec2, boolean useDepth, boolean flagx, boolean flagz, int color1, int color2) {
/*  194 */     int a1 = color1 >>> 24 & 0xFF;
/*  195 */     int r1 = color1 >>> 16 & 0xFF;
/*  196 */     int g1 = color1 >>> 8 & 0xFF;
/*  197 */     int b1 = color1 & 0xFF;
/*      */     
/*  199 */     int a2 = color2 >>> 24 & 0xFF;
/*  200 */     int r2 = color2 >>> 16 & 0xFF;
/*  201 */     int g2 = color2 >>> 8 & 0xFF;
/*  202 */     int b2 = color2 & 0xFF;
/*      */     
/*  204 */     drawGradientTwoPointFilledPyramid(INSTANCE.func_178180_c(), useDepth, flagx, flagz, (float)(vec1.field_72450_a + 0.5D), (float)vec1.field_72448_b, (float)(vec1.field_72449_c + 0.5D), (float)(vec2.field_72450_a + 0.5D), (float)vec2.field_72448_b, (float)(vec2.field_72449_c + 0.5D), r1, g1, b1, a1, r2, g2, b2, a2);
/*      */   }
/*      */   
/*      */   public static void drawGradientDoubleBlockFullBox(Vec3d vec1, Vec3d vec2, boolean useDepth, boolean sidesOnly, int color1, int color2) {
/*  208 */     int a1 = color1 >>> 24 & 0xFF;
/*  209 */     int r1 = color1 >>> 16 & 0xFF;
/*  210 */     int g1 = color1 >>> 8 & 0xFF;
/*  211 */     int b1 = color1 & 0xFF;
/*      */     
/*  213 */     int a2 = color2 >>> 24 & 0xFF;
/*  214 */     int r2 = color2 >>> 16 & 0xFF;
/*  215 */     int g2 = color2 >>> 8 & 0xFF;
/*  216 */     int b2 = color2 & 0xFF;
/*      */     
/*  218 */     drawGradientTwoPointFilledBox(INSTANCE.func_178180_c(), useDepth, sidesOnly, (float)(vec1.field_72450_a + 0.5D), (float)vec1.field_72448_b, (float)(vec1.field_72449_c + 0.5D), (float)(vec2.field_72450_a + 0.5D), (float)vec2.field_72448_b, (float)(vec2.field_72449_c + 0.5D), r1, g1, b1, a1, r2, g2, b2, a2);
/*      */   }
/*      */   
/*      */   public static void drawDoubleBlockLineBox(Vec3d vec1, Vec3d vec2, boolean useDepth, float lineWidth, int color) {
/*  222 */     int a = color >>> 24 & 0xFF;
/*  223 */     int r = color >>> 16 & 0xFF;
/*  224 */     int g = color >>> 8 & 0xFF;
/*  225 */     int b = color & 0xFF;
/*  226 */     GL11.glLineWidth(lineWidth);
/*  227 */     drawTwoPointLineBox(INSTANCE.func_178180_c(), useDepth, (float)(vec1.field_72450_a + 0.5D), (float)vec1.field_72448_b, (float)(vec1.field_72449_c + 0.5D), (float)(vec2.field_72450_a + 0.5D), (float)vec2.field_72448_b, (float)(vec2.field_72449_c + 0.5D), r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawDoubleBlockLinePyramid(Vec3d vec1, Vec3d vec2, boolean useDepth, float lineWidth, boolean flagx, boolean flagz, int color) {
/*  231 */     int a = color >>> 24 & 0xFF;
/*  232 */     int r = color >>> 16 & 0xFF;
/*  233 */     int g = color >>> 8 & 0xFF;
/*  234 */     int b = color & 0xFF;
/*  235 */     GL11.glLineWidth(lineWidth);
/*  236 */     drawTwoPointLinePyramid(INSTANCE.func_178180_c(), useDepth, flagx, flagz, (float)(vec1.field_72450_a + 0.5D), (float)vec1.field_72448_b, (float)(vec1.field_72449_c + 0.5D), (float)(vec2.field_72450_a + 0.5D), (float)vec2.field_72448_b, (float)(vec2.field_72449_c + 0.5D), r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawGradientDoubleBlockLinePyramid(Vec3d vec1, Vec3d vec2, boolean useDepth, float lineWidth, boolean flagx, boolean flagz, int color1, int color2) {
/*  240 */     int a1 = color1 >>> 24 & 0xFF;
/*  241 */     int r1 = color1 >>> 16 & 0xFF;
/*  242 */     int g1 = color1 >>> 8 & 0xFF;
/*  243 */     int b1 = color1 & 0xFF;
/*      */     
/*  245 */     int a2 = color2 >>> 24 & 0xFF;
/*  246 */     int r2 = color2 >>> 16 & 0xFF;
/*  247 */     int g2 = color2 >>> 8 & 0xFF;
/*  248 */     int b2 = color2 & 0xFF;
/*      */     
/*  250 */     GL11.glLineWidth(lineWidth);
/*  251 */     drawGradientTwoPointLinePyramid(INSTANCE.func_178180_c(), useDepth, flagx, flagz, (float)(vec1.field_72450_a + 0.5D), (float)vec1.field_72448_b, (float)(vec1.field_72449_c + 0.5D), (float)(vec2.field_72450_a + 0.5D), (float)vec2.field_72448_b, (float)(vec2.field_72449_c + 0.5D), r1, g1, b1, a1, r2, g2, b2, a2);
/*      */   }
/*      */   
/*      */   public static void drawGradientDoubleBlockLineBox(Vec3d vec1, Vec3d vec2, boolean useDepth, float lineWidth, int color1, int color2) {
/*  255 */     int a1 = color1 >>> 24 & 0xFF;
/*  256 */     int r1 = color1 >>> 16 & 0xFF;
/*  257 */     int g1 = color1 >>> 8 & 0xFF;
/*  258 */     int b1 = color1 & 0xFF;
/*      */     
/*  260 */     int a2 = color2 >>> 24 & 0xFF;
/*  261 */     int r2 = color2 >>> 16 & 0xFF;
/*  262 */     int g2 = color2 >>> 8 & 0xFF;
/*  263 */     int b2 = color2 & 0xFF;
/*      */     
/*  265 */     GL11.glLineWidth(lineWidth);
/*  266 */     drawGradientTwoPointLineBox(INSTANCE.func_178180_c(), useDepth, (float)(vec1.field_72450_a + 0.5D), (float)vec1.field_72448_b, (float)(vec1.field_72449_c + 0.5D), (float)(vec2.field_72450_a + 0.5D), (float)vec2.field_72448_b, (float)(vec2.field_72449_c + 0.5D), r1, g1, b1, a1, r2, g2, b2, a2);
/*      */   }
/*      */   
/*      */   public static void drawDoubleBlockFlatFullBox(Vec3d vec1, Vec3d vec2, boolean useDepth, int color) {
/*  270 */     int a = color >>> 24 & 0xFF;
/*  271 */     int r = color >>> 16 & 0xFF;
/*  272 */     int g = color >>> 8 & 0xFF;
/*  273 */     int b = color & 0xFF;
/*  274 */     GL11.glDisable(2884);
/*  275 */     drawDoublePointFlatFilledBox(INSTANCE.func_178180_c(), useDepth, (float)(vec1.field_72450_a + 0.5D), (float)vec1.field_72448_b, (float)(vec1.field_72449_c + 0.5D), (float)(vec2.field_72450_a + 0.5D), (float)(vec2.field_72449_c + 0.5D), r, g, b, a);
/*  276 */     GL11.glEnable(2884);
/*      */   }
/*      */   
/*      */   public static void drawDoubleBlockFlatLineBox(Vec3d vec1, Vec3d vec2, boolean useDepth, float lineWidth, int color) {
/*  280 */     int a = color >>> 24 & 0xFF;
/*  281 */     int r = color >>> 16 & 0xFF;
/*  282 */     int g = color >>> 8 & 0xFF;
/*  283 */     int b = color & 0xFF;
/*  284 */     GL11.glLineWidth(lineWidth);
/*  285 */     drawDoublePointFlatLineBox(INSTANCE.func_178180_c(), useDepth, (float)(vec1.field_72450_a + 0.5D), (float)vec1.field_72448_b, (float)(vec1.field_72449_c + 0.5D), (float)(vec2.field_72450_a + 0.5D), (float)(vec2.field_72449_c + 0.5D), r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawXCross(Vec3d vec, float height, float lineWidth, int color) {
/*  289 */     int a = color >>> 24 & 0xFF;
/*  290 */     int r = color >>> 16 & 0xFF;
/*  291 */     int g = color >>> 8 & 0xFF;
/*  292 */     int b = color & 0xFF;
/*  293 */     GL11.glLineWidth(lineWidth);
/*  294 */     drawXCross(INSTANCE.func_178180_c(), (float)vec.field_72450_a, (float)vec.field_72448_b, (float)vec.field_72449_c, 1.0F, height, 1.0F, r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawGradientXCross(Vec3d vec, float height, float lineWidth, int color1, int color2) {
/*  298 */     int a1 = color1 >>> 24 & 0xFF;
/*  299 */     int r1 = color1 >>> 16 & 0xFF;
/*  300 */     int g1 = color1 >>> 8 & 0xFF;
/*  301 */     int b1 = color1 & 0xFF;
/*      */     
/*  303 */     int a2 = color2 >>> 24 & 0xFF;
/*  304 */     int r2 = color2 >>> 16 & 0xFF;
/*  305 */     int g2 = color2 >>> 8 & 0xFF;
/*  306 */     int b2 = color2 & 0xFF;
/*      */     
/*  308 */     GL11.glLineWidth(lineWidth);
/*  309 */     drawGradientXCross(INSTANCE.func_178180_c(), (float)vec.field_72450_a, (float)vec.field_72448_b, (float)vec.field_72449_c, 1.0F, height, 1.0F, r1, g1, b1, a1, r2, g2, b2, a2);
/*      */   }
/*      */   
/*      */   public static void drawFlatXCross(Vec3d vec, float lineWidth, int color) {
/*  313 */     int a = color >>> 24 & 0xFF;
/*  314 */     int r = color >>> 16 & 0xFF;
/*  315 */     int g = color >>> 8 & 0xFF;
/*  316 */     int b = color & 0xFF;
/*  317 */     GL11.glLineWidth(lineWidth);
/*  318 */     drawFlatXCross(INSTANCE.func_178180_c(), (float)vec.field_72450_a, (float)vec.field_72448_b, (float)vec.field_72449_c, 1.0F, 1.0F, r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawDoublePointXCross(Vec3d vec1, Vec3d vec2, float lineWidth, int color) {
/*  322 */     int a = color >>> 24 & 0xFF;
/*  323 */     int r = color >>> 16 & 0xFF;
/*  324 */     int g = color >>> 8 & 0xFF;
/*  325 */     int b = color & 0xFF;
/*  326 */     GL11.glLineWidth(lineWidth);
/*  327 */     drawDoublePointXCross(INSTANCE.func_178180_c(), (float)(vec1.field_72450_a + 0.5D), (float)vec1.field_72448_b, (float)(vec1.field_72449_c + 0.5D), (float)(vec2.field_72450_a + 0.5D), (float)vec2.field_72448_b, (float)(vec2.field_72449_c + 0.5D), r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawGradientDoublePointXCross(Vec3d vec1, Vec3d vec2, float lineWidth, int color1, int color2) {
/*  331 */     int a1 = color1 >>> 24 & 0xFF;
/*  332 */     int r1 = color1 >>> 16 & 0xFF;
/*  333 */     int g1 = color1 >>> 8 & 0xFF;
/*  334 */     int b1 = color1 & 0xFF;
/*      */     
/*  336 */     int a2 = color2 >>> 24 & 0xFF;
/*  337 */     int r2 = color2 >>> 16 & 0xFF;
/*  338 */     int g2 = color2 >>> 8 & 0xFF;
/*  339 */     int b2 = color2 & 0xFF;
/*      */     
/*  341 */     GL11.glLineWidth(lineWidth);
/*  342 */     drawGradientDoublePointXCross(INSTANCE.func_178180_c(), (float)(vec1.field_72450_a + 0.5D), (float)vec1.field_72448_b, (float)(vec1.field_72449_c + 0.5D), (float)(vec2.field_72450_a + 0.5D), (float)vec2.field_72448_b, (float)(vec2.field_72449_c + 0.5D), r1, g1, b1, a1, r2, g2, b2, a2);
/*      */   }
/*      */   
/*      */   public static void drawDoublePointFlatXCross(Vec3d vec1, Vec3d vec2, float lineWidth, int color) {
/*  346 */     int a = color >>> 24 & 0xFF;
/*  347 */     int r = color >>> 16 & 0xFF;
/*  348 */     int g = color >>> 8 & 0xFF;
/*  349 */     int b = color & 0xFF;
/*  350 */     GL11.glLineWidth(lineWidth);
/*  351 */     drawDoublePointFlatXCross(INSTANCE.func_178180_c(), (float)(vec1.field_72450_a + 0.5D), (float)vec1.field_72448_b, (float)(vec1.field_72449_c + 0.5D), (float)(vec2.field_72450_a + 0.5D), (float)(vec2.field_72449_c + 0.5D), r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawPyramidFullBox(Vec3d vec, boolean useDepth, float height, int color) {
/*  355 */     int a = color >>> 24 & 0xFF;
/*  356 */     int r = color >>> 16 & 0xFF;
/*  357 */     int g = color >>> 8 & 0xFF;
/*  358 */     int b = color & 0xFF;
/*  359 */     drawFilledPyramid(INSTANCE.func_178180_c(), useDepth, (float)vec.field_72450_a, (float)vec.field_72448_b, (float)vec.field_72449_c, 1.0F, height, 1.0F, r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawGradientPyramidFullBox(Vec3d vec, boolean useDepth, float height, int color1, int color2) {
/*  363 */     int a1 = color1 >>> 24 & 0xFF;
/*  364 */     int r1 = color1 >>> 16 & 0xFF;
/*  365 */     int g1 = color1 >>> 8 & 0xFF;
/*  366 */     int b1 = color1 & 0xFF;
/*      */     
/*  368 */     int a2 = color2 >>> 24 & 0xFF;
/*  369 */     int r2 = color2 >>> 16 & 0xFF;
/*  370 */     int g2 = color2 >>> 8 & 0xFF;
/*  371 */     int b2 = color2 & 0xFF;
/*      */     
/*  373 */     drawGradientFilledPyramid(INSTANCE.func_178180_c(), useDepth, (float)vec.field_72450_a, (float)vec.field_72448_b, (float)vec.field_72449_c, 1.0F, height, 1.0F, r1, g1, b1, a1, r2, g2, b2, a2);
/*      */   }
/*      */   
/*      */   public static void drawPyramidLineBox(Vec3d vec, boolean useDepth, float height, float lineWidth, int color) {
/*  377 */     int a = color >>> 24 & 0xFF;
/*  378 */     int r = color >>> 16 & 0xFF;
/*  379 */     int g = color >>> 8 & 0xFF;
/*  380 */     int b = color & 0xFF;
/*  381 */     GL11.glLineWidth(lineWidth);
/*  382 */     drawLinePyramid(INSTANCE.func_178180_c(), useDepth, (float)vec.field_72450_a, (float)vec.field_72448_b, (float)vec.field_72449_c, 1.0F, height, 1.0F, r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawGradientPyramidLineBox(Vec3d vec, boolean useDepth, float height, float lineWidth, int color1, int color2) {
/*  386 */     int a1 = color1 >>> 24 & 0xFF;
/*  387 */     int r1 = color1 >>> 16 & 0xFF;
/*  388 */     int g1 = color1 >>> 8 & 0xFF;
/*  389 */     int b1 = color1 & 0xFF;
/*      */     
/*  391 */     int a2 = color2 >>> 24 & 0xFF;
/*  392 */     int r2 = color2 >>> 16 & 0xFF;
/*  393 */     int g2 = color2 >>> 8 & 0xFF;
/*  394 */     int b2 = color2 & 0xFF;
/*      */     
/*  396 */     GL11.glLineWidth(lineWidth);
/*  397 */     drawGradientLinePyramid(INSTANCE.func_178180_c(), useDepth, (float)vec.field_72450_a, (float)vec.field_72448_b, (float)vec.field_72449_c, 1.0F, height, 1.0F, r1, g1, b1, a1, r2, g2, b2, a2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static float[] getRotations(ModelRenderer model) {
/*  402 */     return new float[] { model.field_78795_f, model.field_78796_g, model.field_78808_h };
/*      */   }
/*      */   
/*      */   public static float[][] getRotationsFromModel(ModelBiped modelBiped) {
/*  406 */     float[][] rotations = new float[5][3];
/*  407 */     rotations[0] = getRotations(modelBiped.field_78116_c);
/*  408 */     rotations[1] = getRotations(modelBiped.field_178723_h);
/*  409 */     rotations[2] = getRotations(modelBiped.field_178724_i);
/*  410 */     rotations[3] = getRotations(modelBiped.field_178721_j);
/*  411 */     rotations[4] = getRotations(modelBiped.field_178722_k);
/*  412 */     return rotations;
/*      */   }
/*      */   
/*      */   public static void drawSkeleton(EntityPlayer entity, float[][] rotations, float width, boolean fadeLimbs, boolean rollingColor, Color rollColor1, Color rollColor2, int color) {
/*  416 */     int a = color >>> 24 & 0xFF;
/*  417 */     int r = color >>> 16 & 0xFF;
/*  418 */     int g = color >>> 8 & 0xFF;
/*  419 */     int b = color & 0xFF;
/*  420 */     BufferBuilder buffer = INSTANCE.func_178180_c();
/*  421 */     float xOffset = entity.field_70760_ar + (entity.field_70761_aq - entity.field_70760_ar) * EntityUtil.mc.func_184121_ak();
/*  422 */     float yOffset = entity.func_70093_af() ? 0.6F : 0.75F;
/*  423 */     float yOffset2 = entity.func_70093_af() ? 0.45F : 0.75F;
/*  424 */     Vec3d entityPos = EntityUtil.getInterpolatedEntityPos((Entity)entity, EntityUtil.mc.func_184121_ak());
/*      */     
/*  426 */     if (((Boolean)ESP.INSTANCE.espSkeletonDeathFade.getValue()).booleanValue()) {
/*  427 */       if (entity.field_70725_aQ > 0 && ESP.INSTANCE.skeletonFadeData.containsKey(entity)) {
/*  428 */         float f = ((Float)ESP.INSTANCE.skeletonFadeData.get(entity)).floatValue();
/*  429 */         f += ((Float)ESP.INSTANCE.espSkeletonDeathFadeFactor.getValue()).floatValue() / 10.0F;
/*  430 */         if (f >= 300.0F) {
/*  431 */           f = 300.0F;
/*      */         }
/*  433 */         a = (int)(a / f);
/*  434 */         ESP.INSTANCE.skeletonFadeData.put(entity, Float.valueOf(f));
/*      */       } else {
/*      */         
/*  437 */         ESP.INSTANCE.skeletonFadeData.put(entity, Float.valueOf(0.1F));
/*  438 */         a = color >>> 24 & 0xFF;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  444 */     double[] ld1 = MathUtilFuckYou.rotationAroundAxis3d(-0.125D, yOffset, entity.func_70093_af() ? -0.235D : 0.0D, -xOffset * 0.017453292519943295D, "y");
/*  445 */     double[] ld2 = MathUtilFuckYou.rotationAroundAxis3d(0.125D, yOffset, entity.func_70093_af() ? -0.235D : 0.0D, -xOffset * 0.017453292519943295D, "y");
/*      */ 
/*      */     
/*  448 */     double[] ld6 = MathUtilFuckYou.rotationAroundAxis3d(-0.125D, -yOffset, entity.func_70093_af() ? -0.235D : 0.0D, rotations[3][0], "x");
/*  449 */     double[] ld9 = MathUtilFuckYou.rotationAroundAxis3d(ld6[0], ld6[1], ld6[2], -xOffset * 0.017453292519943295D, "y");
/*  450 */     double[] ld7 = MathUtilFuckYou.rotationAroundAxis3d(0.125D, -yOffset, entity.func_70093_af() ? -0.235D : 0.0D, rotations[4][0], "x");
/*  451 */     double[] ld8 = MathUtilFuckYou.rotationAroundAxis3d(ld7[0], ld7[1], ld7[2], -xOffset * 0.017453292519943295D, "y");
/*      */ 
/*      */     
/*  454 */     double[] td1 = MathUtilFuckYou.rotationAroundAxis3d(0.0D, yOffset, entity.func_70093_af() ? -0.235D : 0.0D, -xOffset * 0.017453292519943295D, "y");
/*  455 */     double[] td2 = MathUtilFuckYou.rotationAroundAxis3d(0.0D, yOffset2 + 0.55D + (entity.func_70093_af() ? -0.05D : 0.0D), entity.func_70093_af() ? -0.0025D : 0.0D, -xOffset * 0.017453292519943295D, "y");
/*  456 */     double[] td3 = MathUtilFuckYou.rotationAroundAxis3d(0.0D, 0.3D, entity.func_70093_af() ? -0.0035D : 0.0D, rotations[0][0], "x");
/*  457 */     double[] td4 = MathUtilFuckYou.rotationAroundAxis3d(td3[0], td3[1], td3[2], -(entity.field_70758_at + (entity.field_70759_as - entity.field_70758_at) * EntityUtil.mc.func_184121_ak()) * 0.017453292519943295D, "y");
/*      */ 
/*      */ 
/*      */     
/*  461 */     double[] ad1 = MathUtilFuckYou.rotationAroundAxis3d(-0.375D, yOffset2 + 0.55D + (entity.func_70093_af() ? -0.05D : 0.0D), entity.func_70093_af() ? -0.0025D : 0.0D, -xOffset * 0.017453292519943295D, "y");
/*  462 */     double[] ad2 = MathUtilFuckYou.rotationAroundAxis3d(0.375D, yOffset2 + 0.55D + (entity.func_70093_af() ? -0.05D : 0.0D), entity.func_70093_af() ? -0.0025D : 0.0D, -xOffset * 0.017453292519943295D, "y");
/*      */ 
/*      */ 
/*      */     
/*  466 */     double[] ad3 = MathUtilFuckYou.rotationAroundAxis3d(0.0D, -0.55D, 0.0D, rotations[1][0], "x");
/*  467 */     double[] ad31 = MathUtilFuckYou.rotationAroundAxis3d(ad3[0], ad3[1], ad3[2], -rotations[1][1], "y");
/*  468 */     double[] ad5 = MathUtilFuckYou.rotationAroundAxis3d(ad31[0], ad31[1], ad31[2], -rotations[1][2], "z");
/*  469 */     double[] ad6 = MathUtilFuckYou.rotationAroundAxis3d(ad5[0] - 0.375D, ad5[1] + yOffset2 + 0.550000011920929D, ad5[2] + (entity.func_70093_af() ? 0.02D : 0.0D), -xOffset * 0.017453292519943295D, "y");
/*      */     
/*  471 */     double[] ad7 = MathUtilFuckYou.rotationAroundAxis3d(0.0D, -0.55D, 0.0D, rotations[2][0], "x");
/*  472 */     double[] ad71 = MathUtilFuckYou.rotationAroundAxis3d(ad7[0], ad7[1], ad7[2], -rotations[2][1], "y");
/*  473 */     double[] ad8 = MathUtilFuckYou.rotationAroundAxis3d(ad71[0], ad71[1], ad71[2], -rotations[2][2], "z");
/*  474 */     double[] ad9 = MathUtilFuckYou.rotationAroundAxis3d(ad8[0] + 0.375D, ad8[1] + yOffset2 + 0.550000011920929D, ad8[2] + (entity.func_70093_af() ? 0.02D : 0.0D), -xOffset * 0.017453292519943295D, "y");
/*      */     
/*  476 */     GL11.glEnable(2848);
/*  477 */     GL11.glLineWidth(width);
/*  478 */     begin(3);
/*      */     
/*  480 */     if (rollingColor) {
/*  481 */       Color rollColor = ColorUtil.rolledColor(rollColor1, rollColor2, 0, ((Float)ESP.INSTANCE.espSkeletonRollingColorSpeed.getValue()).floatValue(), 0.1F);
/*  482 */       if (fadeLimbs) { buffer.func_181662_b(entityPos.field_72450_a + ld9[0], entityPos.field_72448_b + yOffset + ld9[1], entityPos.field_72449_c + ld9[2]).func_181666_a(0.0F, 0.0F, 0.0F, 0.0F).func_181675_d(); }
/*  483 */       else { buffer.func_181662_b(entityPos.field_72450_a + ld9[0], entityPos.field_72448_b + yOffset + ld9[1], entityPos.field_72449_c + ld9[2]).func_181669_b(rollColor.getRed(), rollColor.getGreen(), rollColor.getBlue(), a).func_181675_d(); }
/*      */       
/*  485 */       rollColor = ColorUtil.rolledColor(rollColor1, rollColor2, 1000, ((Float)ESP.INSTANCE.espSkeletonRollingColorSpeed.getValue()).floatValue(), 0.1F);
/*  486 */       buffer.func_181662_b(entityPos.field_72450_a + ld1[0], entityPos.field_72448_b + ld1[1], entityPos.field_72449_c + ld1[2]).func_181669_b(rollColor.getRed(), rollColor.getGreen(), rollColor.getBlue(), a).func_181675_d();
/*      */       
/*  488 */       rollColor = ColorUtil.rolledColor(rollColor1, rollColor2, 1000, ((Float)ESP.INSTANCE.espSkeletonRollingColorSpeed.getValue()).floatValue(), 0.1F);
/*  489 */       buffer.func_181662_b(entityPos.field_72450_a + ld2[0], entityPos.field_72448_b + ld2[1], entityPos.field_72449_c + ld2[2]).func_181669_b(rollColor.getRed(), rollColor.getGreen(), rollColor.getBlue(), a).func_181675_d();
/*      */       
/*  491 */       rollColor = ColorUtil.rolledColor(rollColor1, rollColor2, 0, ((Float)ESP.INSTANCE.espSkeletonRollingColorSpeed.getValue()).floatValue(), 0.1F);
/*  492 */       if (fadeLimbs) { buffer.func_181662_b(entityPos.field_72450_a + ld8[0], entityPos.field_72448_b + yOffset + ld8[1], entityPos.field_72449_c + ld8[2]).func_181666_a(0.0F, 0.0F, 0.0F, 0.0F).func_181675_d(); }
/*  493 */       else { buffer.func_181662_b(entityPos.field_72450_a + ld8[0], entityPos.field_72448_b + yOffset + ld8[1], entityPos.field_72449_c + ld8[2]).func_181669_b(rollColor.getRed(), rollColor.getGreen(), rollColor.getBlue(), a).func_181675_d(); }
/*      */       
/*  495 */       render();
/*  496 */       begin(3);
/*      */       
/*  498 */       rollColor = ColorUtil.rolledColor(rollColor1, rollColor2, 1000, ((Float)ESP.INSTANCE.espSkeletonRollingColorSpeed.getValue()).floatValue(), 0.1F);
/*  499 */       buffer.func_181662_b(entityPos.field_72450_a + td1[0], entityPos.field_72448_b + td1[1], entityPos.field_72449_c + td1[2]).func_181669_b(rollColor.getRed(), rollColor.getGreen(), rollColor.getBlue(), a).func_181675_d();
/*  500 */       rollColor = ColorUtil.rolledColor(rollColor1, rollColor2, 2000, ((Float)ESP.INSTANCE.espSkeletonRollingColorSpeed.getValue()).floatValue(), 0.1F);
/*  501 */       buffer.func_181662_b(entityPos.field_72450_a + td2[0], entityPos.field_72448_b + td2[1], entityPos.field_72449_c + td2[2]).func_181669_b(rollColor.getRed(), rollColor.getGreen(), rollColor.getBlue(), a).func_181675_d();
/*  502 */       rollColor = ColorUtil.rolledColor(rollColor1, rollColor2, 3000, ((Float)ESP.INSTANCE.espSkeletonRollingColorSpeed.getValue()).floatValue(), 0.1F);
/*  503 */       if (fadeLimbs) { buffer.func_181662_b(entityPos.field_72450_a + td4[0], entityPos.field_72448_b + yOffset2 + 0.550000011920929D + (entity.func_70093_af() ? -0.05D : 0.0D) + td4[1], entityPos.field_72449_c + td4[2]).func_181666_a(0.0F, 0.0F, 0.0F, 0.0F).func_181675_d(); }
/*  504 */       else { buffer.func_181662_b(entityPos.field_72450_a + td4[0], entityPos.field_72448_b + yOffset2 + 0.550000011920929D + (entity.func_70093_af() ? -0.05D : 0.0D) + td4[1], entityPos.field_72449_c + td4[2]).func_181669_b(rollColor.getRed(), rollColor.getGreen(), rollColor.getBlue(), a).func_181675_d(); }
/*      */       
/*  506 */       render();
/*  507 */       begin(3);
/*      */       
/*  509 */       rollColor = ColorUtil.rolledColor(rollColor1, rollColor2, 1000, ((Float)ESP.INSTANCE.espSkeletonRollingColorSpeed.getValue()).floatValue(), 0.1F);
/*  510 */       if (fadeLimbs) { buffer.func_181662_b(entityPos.field_72450_a + ad6[0], entityPos.field_72448_b + ad6[1], entityPos.field_72449_c + ad6[2]).func_181666_a(0.0F, 0.0F, 0.0F, 0.0F).func_181675_d(); }
/*  511 */       else { buffer.func_181662_b(entityPos.field_72450_a + ad6[0], entityPos.field_72448_b + ad6[1], entityPos.field_72449_c + ad6[2]).func_181669_b(rollColor.getRed(), rollColor.getGreen(), rollColor.getBlue(), a).func_181675_d(); }
/*      */       
/*  513 */       rollColor = ColorUtil.rolledColor(rollColor1, rollColor2, 2000, ((Float)ESP.INSTANCE.espSkeletonRollingColorSpeed.getValue()).floatValue(), 0.1F);
/*  514 */       buffer.func_181662_b(entityPos.field_72450_a + ad1[0], entityPos.field_72448_b + ad1[1], entityPos.field_72449_c + ad1[2]).func_181669_b(rollColor.getRed(), rollColor.getGreen(), rollColor.getBlue(), a).func_181675_d();
/*  515 */       rollColor = ColorUtil.rolledColor(rollColor1, rollColor2, 2000, ((Float)ESP.INSTANCE.espSkeletonRollingColorSpeed.getValue()).floatValue(), 0.1F);
/*  516 */       buffer.func_181662_b(entityPos.field_72450_a + ad2[0], entityPos.field_72448_b + ad2[1], entityPos.field_72449_c + ad2[2]).func_181669_b(rollColor.getRed(), rollColor.getGreen(), rollColor.getBlue(), a).func_181675_d();
/*      */       
/*  518 */       rollColor = ColorUtil.rolledColor(rollColor1, rollColor2, 1000, ((Float)ESP.INSTANCE.espSkeletonRollingColorSpeed.getValue()).floatValue(), 0.1F);
/*  519 */       if (fadeLimbs) { buffer.func_181662_b(entityPos.field_72450_a + ad9[0], entityPos.field_72448_b + ad9[1], entityPos.field_72449_c + ad9[2]).func_181666_a(0.0F, 0.0F, 0.0F, 0.0F).func_181675_d(); }
/*  520 */       else { buffer.func_181662_b(entityPos.field_72450_a + ad9[0], entityPos.field_72448_b + ad9[1], entityPos.field_72449_c + ad9[2]).func_181669_b(rollColor.getRed(), rollColor.getGreen(), rollColor.getBlue(), a).func_181675_d(); }
/*      */     
/*      */     } else {
/*  523 */       if (fadeLimbs) { buffer.func_181662_b(entityPos.field_72450_a + ld9[0], entityPos.field_72448_b + yOffset + ld9[1], entityPos.field_72449_c + ld9[2]).func_181666_a(0.0F, 0.0F, 0.0F, 0.0F).func_181675_d(); }
/*  524 */       else { buffer.func_181662_b(entityPos.field_72450_a + ld9[0], entityPos.field_72448_b + yOffset + ld9[1], entityPos.field_72449_c + ld9[2]).func_181669_b(r, g, b, a).func_181675_d(); }
/*  525 */        buffer.func_181662_b(entityPos.field_72450_a + ld1[0], entityPos.field_72448_b + ld1[1], entityPos.field_72449_c + ld1[2]).func_181669_b(r, g, b, a).func_181675_d();
/*      */       
/*  527 */       buffer.func_181662_b(entityPos.field_72450_a + ld2[0], entityPos.field_72448_b + ld2[1], entityPos.field_72449_c + ld2[2]).func_181669_b(r, g, b, a).func_181675_d();
/*  528 */       if (fadeLimbs) { buffer.func_181662_b(entityPos.field_72450_a + ld8[0], entityPos.field_72448_b + yOffset + ld8[1], entityPos.field_72449_c + ld8[2]).func_181666_a(0.0F, 0.0F, 0.0F, 0.0F).func_181675_d(); }
/*  529 */       else { buffer.func_181662_b(entityPos.field_72450_a + ld8[0], entityPos.field_72448_b + yOffset + ld8[1], entityPos.field_72449_c + ld8[2]).func_181669_b(r, g, b, a).func_181675_d(); }
/*      */       
/*  531 */       render();
/*  532 */       begin(3);
/*      */       
/*  534 */       buffer.func_181662_b(entityPos.field_72450_a + td1[0], entityPos.field_72448_b + td1[1], entityPos.field_72449_c + td1[2]).func_181669_b(r, g, b, a).func_181675_d();
/*  535 */       buffer.func_181662_b(entityPos.field_72450_a + td2[0], entityPos.field_72448_b + td2[1], entityPos.field_72449_c + td2[2]).func_181669_b(r, g, b, a).func_181675_d();
/*  536 */       if (fadeLimbs) { buffer.func_181662_b(entityPos.field_72450_a + td4[0], entityPos.field_72448_b + yOffset2 + 0.550000011920929D + (entity.func_70093_af() ? -0.05D : 0.0D) + td4[1], entityPos.field_72449_c + td4[2]).func_181666_a(0.0F, 0.0F, 0.0F, 0.0F).func_181675_d(); }
/*  537 */       else { buffer.func_181662_b(entityPos.field_72450_a + td4[0], entityPos.field_72448_b + yOffset2 + 0.550000011920929D + (entity.func_70093_af() ? -0.05D : 0.0D) + td4[1], entityPos.field_72449_c + td4[2]).func_181669_b(r, g, b, a).func_181675_d(); }
/*      */       
/*  539 */       render();
/*  540 */       begin(3);
/*      */       
/*  542 */       if (fadeLimbs) { buffer.func_181662_b(entityPos.field_72450_a + ad6[0], entityPos.field_72448_b + ad6[1], entityPos.field_72449_c + ad6[2]).func_181666_a(0.0F, 0.0F, 0.0F, 0.0F).func_181675_d(); }
/*  543 */       else { buffer.func_181662_b(entityPos.field_72450_a + ad6[0], entityPos.field_72448_b + ad6[1], entityPos.field_72449_c + ad6[2]).func_181669_b(r, g, b, a).func_181675_d(); }
/*  544 */        buffer.func_181662_b(entityPos.field_72450_a + ad1[0], entityPos.field_72448_b + ad1[1], entityPos.field_72449_c + ad1[2]).func_181669_b(r, g, b, a).func_181675_d();
/*  545 */       buffer.func_181662_b(entityPos.field_72450_a + ad2[0], entityPos.field_72448_b + ad2[1], entityPos.field_72449_c + ad2[2]).func_181669_b(r, g, b, a).func_181675_d();
/*  546 */       if (fadeLimbs) { buffer.func_181662_b(entityPos.field_72450_a + ad9[0], entityPos.field_72448_b + ad9[1], entityPos.field_72449_c + ad9[2]).func_181666_a(0.0F, 0.0F, 0.0F, 0.0F).func_181675_d(); }
/*  547 */       else { buffer.func_181662_b(entityPos.field_72450_a + ad9[0], entityPos.field_72448_b + ad9[1], entityPos.field_72449_c + ad9[2]).func_181669_b(r, g, b, a).func_181675_d(); }
/*      */     
/*  549 */     }  render();
/*      */     
/*  551 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawXCross(BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a) {
/*  555 */     GL11.glEnable(2848);
/*  556 */     begin(1);
/*  557 */     buffer.func_181662_b(x, y, z).func_181669_b(r, g, b, a).func_181675_d();
/*  558 */     buffer.func_181662_b((x + w), (y + h), (z + d)).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/*  560 */     buffer.func_181662_b(x, y, (z + d)).func_181669_b(r, g, b, a).func_181675_d();
/*  561 */     buffer.func_181662_b((x + w), (y + h), z).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/*  563 */     buffer.func_181662_b((x + w), y, z).func_181669_b(r, g, b, a).func_181675_d();
/*  564 */     buffer.func_181662_b(x, (y + h), (z + d)).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/*  566 */     buffer.func_181662_b(x, (y + h), z).func_181669_b(r, g, b, a).func_181675_d();
/*  567 */     buffer.func_181662_b((x + w), y, (z + d)).func_181669_b(r, g, b, a).func_181675_d();
/*  568 */     render();
/*  569 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawGradientXCross(BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r1, int g1, int b1, int a1, int r2, int g2, int b2, int a2) {
/*  573 */     GL11.glEnable(2848);
/*      */     
/*  575 */     begin(1);
/*  576 */     buffer.func_181662_b(x, y, z).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  577 */     buffer.func_181662_b((x + w), (y + h), (z + d)).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*      */     
/*  579 */     buffer.func_181662_b(x, y, (z + d)).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  580 */     buffer.func_181662_b((x + w), (y + h), z).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*      */     
/*  582 */     buffer.func_181662_b((x + w), y, z).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  583 */     buffer.func_181662_b(x, (y + h), (z + d)).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*      */     
/*  585 */     buffer.func_181662_b((x + w), y, (z + d)).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  586 */     buffer.func_181662_b(x, (y + h), z).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  587 */     render();
/*  588 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawFlatXCross(BufferBuilder buffer, float x, float y, float z, float w, float d, int r, int g, int b, int a) {
/*  592 */     GL11.glEnable(2848);
/*  593 */     begin(1);
/*  594 */     buffer.func_181662_b(x, y, z).func_181669_b(r, g, b, a).func_181675_d();
/*  595 */     buffer.func_181662_b((x + w), y, (z + d)).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/*  597 */     buffer.func_181662_b(x, y, (z + d)).func_181669_b(r, g, b, a).func_181675_d();
/*  598 */     buffer.func_181662_b((x + w), y, z).func_181669_b(r, g, b, a).func_181675_d();
/*  599 */     render();
/*  600 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawDoublePointXCross(BufferBuilder buffer, float x1, float y1, float z1, float x2, float y2, float z2, int r, int g, int b, int a) {
/*  604 */     GL11.glEnable(2848);
/*  605 */     begin(1);
/*  606 */     buffer.func_181662_b(x1, y1, z1).func_181669_b(r, g, b, a).func_181675_d();
/*  607 */     buffer.func_181662_b(x2, y2, z2).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/*  609 */     buffer.func_181662_b(x1, y1, z2).func_181669_b(r, g, b, a).func_181675_d();
/*  610 */     buffer.func_181662_b(x2, y2, z1).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/*  612 */     buffer.func_181662_b(x2, y1, z1).func_181669_b(r, g, b, a).func_181675_d();
/*  613 */     buffer.func_181662_b(x1, y2, z2).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/*  615 */     buffer.func_181662_b(x1, y2, z1).func_181669_b(r, g, b, a).func_181675_d();
/*  616 */     buffer.func_181662_b(x2, y1, z2).func_181669_b(r, g, b, a).func_181675_d();
/*  617 */     render();
/*  618 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawGradientDoublePointXCross(BufferBuilder buffer, float x1, float y1, float z1, float x2, float y2, float z2, int r1, int g1, int b1, int a1, int r2, int g2, int b2, int a2) {
/*  622 */     GL11.glEnable(2848);
/*  623 */     begin(1);
/*  624 */     buffer.func_181662_b(x1, y1, z1).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  625 */     buffer.func_181662_b(x2, y2, z2).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*      */     
/*  627 */     buffer.func_181662_b(x1, y1, z2).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  628 */     buffer.func_181662_b(x2, y2, z1).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*      */     
/*  630 */     buffer.func_181662_b(x2, y1, z1).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  631 */     buffer.func_181662_b(x1, y2, z2).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*      */     
/*  633 */     buffer.func_181662_b(x2, y1, z2).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  634 */     buffer.func_181662_b(x1, y2, z1).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  635 */     render();
/*  636 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawDoublePointFlatXCross(BufferBuilder buffer, float x1, float y, float z1, float x2, float z2, int r, int g, int b, int a) {
/*  640 */     GL11.glEnable(2848);
/*  641 */     begin(1);
/*  642 */     buffer.func_181662_b(x1, y, z1).func_181669_b(r, g, b, a).func_181675_d();
/*  643 */     buffer.func_181662_b(x2, y, z2).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/*  645 */     buffer.func_181662_b(x1, y, z2).func_181669_b(r, g, b, a).func_181675_d();
/*  646 */     buffer.func_181662_b(x2, y, z1).func_181669_b(r, g, b, a).func_181675_d();
/*  647 */     render();
/*  648 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawFlatLineBox(BufferBuilder buffer, boolean useDepth, float x, float y, float z, float w, float d, int r, int g, int b, int a) {
/*  652 */     double offset = useDepth ? 0.003D : 0.0D;
/*  653 */     GL11.glEnable(2848);
/*  654 */     begin(3);
/*  655 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  656 */     buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  657 */     buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  658 */     buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  659 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  660 */     render();
/*  661 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawFlatFilledBox(BufferBuilder buffer, boolean useDepth, float x, float y, float z, float w, float d, int r, int g, int b, int a) {
/*  665 */     double offset = useDepth ? 0.003D : 0.0D;
/*  666 */     begin(7);
/*  667 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  668 */     buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  669 */     buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  670 */     buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  671 */     render();
/*      */   }
/*      */   
/*      */   public static void drawDoublePointFlatLineBox(BufferBuilder buffer, boolean useDepth, float x1, float y, float z1, float x2, float z2, int r, int g, int b, int a) {
/*  675 */     double offset = useDepth ? 0.003D : 0.0D;
/*  676 */     GL11.glEnable(2848);
/*  677 */     begin(3);
/*  678 */     buffer.func_181662_b(x1 + offset, y + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  679 */     buffer.func_181662_b(x2 - offset, y + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  680 */     buffer.func_181662_b(x2 - offset, y + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  681 */     buffer.func_181662_b(x1 + offset, y + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  682 */     buffer.func_181662_b(x1 + offset, y + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  683 */     render();
/*  684 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawDoublePointFlatFilledBox(BufferBuilder buffer, boolean useDepth, float x1, float y, float z1, float x2, float z2, int r, int g, int b, int a) {
/*  688 */     double offset = useDepth ? 0.003D : 0.0D;
/*  689 */     begin(7);
/*  690 */     buffer.func_181662_b(x1 + offset, y + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  691 */     buffer.func_181662_b(x2 - offset, y + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  692 */     buffer.func_181662_b(x2 - offset, y + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  693 */     buffer.func_181662_b(x1 + offset, y + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  694 */     render();
/*      */   }
/*      */   
/*      */   public static void drawLineBox(BufferBuilder buffer, boolean useDepth, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a) {
/*  698 */     double offset = useDepth ? 0.003D : 0.0D;
/*  699 */     GL11.glEnable(2848);
/*  700 */     begin(3);
/*  701 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  702 */     buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  703 */     buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  704 */     buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  705 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  706 */     buffer.func_181662_b(x + offset, (y + h), z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  707 */     buffer.func_181662_b((x + w) - offset, (y + h), z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  708 */     buffer.func_181662_b((x + w) - offset, (y + h), (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  709 */     buffer.func_181662_b(x + offset, (y + h), (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  710 */     buffer.func_181662_b(x + offset, (y + h), z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  711 */     render();
/*      */     
/*  713 */     begin(1);
/*  714 */     buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  715 */     buffer.func_181662_b((x + w) - offset, (y + h), z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  716 */     buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  717 */     buffer.func_181662_b((x + w) - offset, (y + h), (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  718 */     buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  719 */     buffer.func_181662_b(x + offset, (y + h), (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  720 */     render();
/*  721 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawLinePyramid(BufferBuilder buffer, boolean useDepth, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a) {
/*  725 */     double offset = useDepth ? 0.003D : 0.0D;
/*  726 */     GL11.glEnable(2848);
/*  727 */     begin(3);
/*  728 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  729 */     buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  730 */     buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  731 */     buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  732 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/*  734 */     buffer.func_181662_b((x + w * 0.5F), (y + h), (z + d * 0.5F)).func_181669_b(r, g, b, a).func_181675_d();
/*  735 */     buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  736 */     render();
/*      */     
/*  738 */     begin(3);
/*  739 */     buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  740 */     buffer.func_181662_b((x + w * 0.5F), (y + h), (z + d * 0.5F)).func_181669_b(r, g, b, a).func_181675_d();
/*  741 */     buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  742 */     render();
/*  743 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawGradientLinePyramid(BufferBuilder buffer, boolean useDepth, float x, float y, float z, float w, float h, float d, int r1, int g1, int b1, int a1, int r2, int g2, int b2, int a2) {
/*  747 */     double offset = useDepth ? 0.003D : 0.0D;
/*  748 */     GL11.glEnable(2848);
/*  749 */     begin(3);
/*  750 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  751 */     buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  752 */     buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  753 */     buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  754 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*      */     
/*  756 */     buffer.func_181662_b((x + w * 0.5F), (y + h), (z + d * 0.5F)).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  757 */     buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  758 */     render();
/*      */     
/*  760 */     begin(3);
/*  761 */     buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  762 */     buffer.func_181662_b((x + w * 0.5F), (y + h), (z + d * 0.5F)).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  763 */     buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  764 */     render();
/*  765 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawGradientLineBox(BufferBuilder buffer, boolean useDepth, float x, float y, float z, float w, float h, float d, int r1, int g1, int b1, int a1, int r2, int g2, int b2, int a2) {
/*  769 */     double offset = useDepth ? 0.003D : 0.0D;
/*  770 */     GL11.glEnable(2848);
/*  771 */     begin(3);
/*  772 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  773 */     buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  774 */     buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  775 */     buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  776 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  777 */     buffer.func_181662_b(x + offset, (y + h), z + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  778 */     buffer.func_181662_b((x + w) - offset, (y + h), z + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  779 */     buffer.func_181662_b((x + w) - offset, (y + h), (z + d) - offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  780 */     buffer.func_181662_b(x + offset, (y + h), (z + d) - offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  781 */     buffer.func_181662_b(x + offset, (y + h), z + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  782 */     render();
/*      */     
/*  784 */     begin(1);
/*  785 */     buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  786 */     buffer.func_181662_b((x + w) - offset, (y + h), z + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  787 */     buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  788 */     buffer.func_181662_b((x + w) - offset, (y + h), (z + d) - offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  789 */     buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  790 */     buffer.func_181662_b(x + offset, (y + h), (z + d) - offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  791 */     render();
/*  792 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawFilledBox(BufferBuilder buffer, boolean useDepth, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a) {
/*  796 */     double offset = useDepth ? 0.003D : 0.0D;
/*  797 */     begin(8);
/*  798 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  799 */     buffer.func_181662_b(x + offset, (y + h), z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/*  801 */     buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  802 */     buffer.func_181662_b((x + w) - offset, (y + h), z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/*  804 */     buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  805 */     buffer.func_181662_b((x + w) - offset, (y + h), (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/*  807 */     buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  808 */     buffer.func_181662_b(x + offset, (y + h), (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/*  810 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  811 */     buffer.func_181662_b(x + offset, (y + h), z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  812 */     render();
/*      */     
/*  814 */     begin(7);
/*  815 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  816 */     buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  817 */     buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  818 */     buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/*  820 */     buffer.func_181662_b(x + offset, (y + h), (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  821 */     buffer.func_181662_b((x + w) - offset, (y + h), (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  822 */     buffer.func_181662_b((x + w) - offset, (y + h), z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  823 */     buffer.func_181662_b(x + offset, (y + h), z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  824 */     render();
/*      */   }
/*      */   
/*      */   public static void drawFilledPyramid(BufferBuilder buffer, boolean useDepth, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a) {
/*  828 */     double offset = useDepth ? 0.003D : 0.0D;
/*  829 */     GL11.glFrontFace(2304);
/*  830 */     begin(6);
/*  831 */     buffer.func_181662_b((x + w * 0.5F), (y + h), (z + d * 0.5F)).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/*  833 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  834 */     buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  835 */     buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  836 */     buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  837 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  838 */     render();
/*  839 */     GL11.glFrontFace(2305);
/*      */     
/*  841 */     begin(7);
/*  842 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  843 */     buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  844 */     buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  845 */     buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  846 */     render();
/*      */   }
/*      */   
/*      */   public static void drawGradientFilledPyramid(BufferBuilder buffer, boolean useDepth, float x, float y, float z, float w, float h, float d, int r1, int g1, int b1, int a1, int r2, int g2, int b2, int a2) {
/*  850 */     double offset = useDepth ? 0.003D : 0.0D;
/*  851 */     GL11.glDisable(2884);
/*  852 */     begin(6);
/*  853 */     buffer.func_181662_b((x + w * 0.5F), (y + h), (z + d * 0.5F)).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*      */     
/*  855 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  856 */     buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  857 */     buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  858 */     buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  859 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  860 */     render();
/*      */     
/*  862 */     begin(7);
/*  863 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  864 */     buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  865 */     buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  866 */     buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  867 */     render();
/*  868 */     GL11.glEnable(2884);
/*      */   }
/*      */   
/*      */   public static void drawGradientFilledBox(BufferBuilder buffer, boolean useDepth, boolean sidesOnly, float x, float y, float z, float w, float h, float d, int r1, int g1, int b1, int a1, int r2, int g2, int b2, int a2) {
/*  872 */     double offset = useDepth ? 0.003D : 0.0D;
/*  873 */     GL11.glDisable(2884);
/*  874 */     begin(8);
/*  875 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  876 */     buffer.func_181662_b(x + offset, (y + h), z + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*      */     
/*  878 */     buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  879 */     buffer.func_181662_b((x + w) - offset, (y + h), z + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*      */     
/*  881 */     buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  882 */     buffer.func_181662_b((x + w) - offset, (y + h), (z + d) - offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*      */     
/*  884 */     buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  885 */     buffer.func_181662_b(x + offset, (y + h), (z + d) - offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*      */     
/*  887 */     buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  888 */     buffer.func_181662_b(x + offset, (y + h), z + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  889 */     render();
/*      */     
/*  891 */     if (!sidesOnly) {
/*  892 */       begin(7);
/*  893 */       buffer.func_181662_b(x + offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  894 */       buffer.func_181662_b((x + w) - offset, y + offset, z + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  895 */       buffer.func_181662_b((x + w) - offset, y + offset, (z + d) - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  896 */       buffer.func_181662_b(x + offset, y + offset, (z + d) - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*      */       
/*  898 */       buffer.func_181662_b(x + offset, (y + h), (z + d) - offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  899 */       buffer.func_181662_b((x + w) - offset, (y + h), (z + d) - offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  900 */       buffer.func_181662_b((x + w) - offset, (y + h), z + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  901 */       buffer.func_181662_b(x + offset, (y + h), z + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  902 */       render();
/*      */     } 
/*  904 */     GL11.glEnable(2884);
/*      */   }
/*      */   
/*      */   public static void drawTwoPointLineBox(BufferBuilder buffer, boolean useDepth, float x1, float y1, float z1, float x2, float y2, float z2, int r, int g, int b, int a) {
/*  908 */     double offset = useDepth ? 0.003D : 0.0D;
/*  909 */     GL11.glEnable(2848);
/*  910 */     begin(3);
/*  911 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  912 */     buffer.func_181662_b(x2 - offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  913 */     buffer.func_181662_b(x2 - offset, y1 + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  914 */     buffer.func_181662_b(x1 + offset, y1 + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  915 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  916 */     buffer.func_181662_b(x1 + offset, y2, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  917 */     buffer.func_181662_b(x2 - offset, y2, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  918 */     buffer.func_181662_b(x2 - offset, y2, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  919 */     buffer.func_181662_b(x1 + offset, y2, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  920 */     buffer.func_181662_b(x1 + offset, y2, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  921 */     render();
/*      */     
/*  923 */     begin(1);
/*  924 */     buffer.func_181662_b(x1 + offset, y2, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  925 */     buffer.func_181662_b(x1 + offset, y1 + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  926 */     buffer.func_181662_b(x2 - offset, y2, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  927 */     buffer.func_181662_b(x2 - offset, y1 + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  928 */     buffer.func_181662_b(x2 - offset, y2, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  929 */     buffer.func_181662_b(x2 - offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  930 */     render();
/*  931 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawTwoPointLinePyramid(BufferBuilder buffer, boolean useDepth, boolean flagx, boolean flagz, float x1, float y1, float z1, float x2, float y2, float z2, int r, int g, int b, int a) {
/*  935 */     double offset = useDepth ? 0.003D : 0.0D;
/*  936 */     float w = Math.abs(x1 - x2) * 0.5F;
/*  937 */     float d = Math.abs(z1 - z2) * 0.5F;
/*      */     
/*  939 */     if (flagx) {
/*  940 */       w *= -1.0F;
/*      */     }
/*      */     
/*  943 */     if (flagz) {
/*  944 */       d *= -1.0F;
/*      */     }
/*      */     
/*  947 */     GL11.glEnable(2848);
/*  948 */     begin(3);
/*  949 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  950 */     buffer.func_181662_b(x2 - offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  951 */     buffer.func_181662_b(x2 - offset, y1 + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  952 */     buffer.func_181662_b(x1 + offset, y1 + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  953 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/*  955 */     buffer.func_181662_b((x1 + w), y2, (z1 + d)).func_181669_b(r, g, b, a).func_181675_d();
/*  956 */     buffer.func_181662_b(x2 - offset, y1 + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  957 */     render();
/*      */     
/*  959 */     begin(3);
/*  960 */     buffer.func_181662_b(x2 - offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*  961 */     buffer.func_181662_b((x1 + w), y2, (z1 + d)).func_181669_b(r, g, b, a).func_181675_d();
/*  962 */     buffer.func_181662_b(x1 + offset, y1 + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*  963 */     render();
/*  964 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawGradientTwoPointLinePyramid(BufferBuilder buffer, boolean useDepth, boolean flagx, boolean flagz, float x1, float y1, float z1, float x2, float y2, float z2, int r1, int g1, int b1, int a1, int r2, int g2, int b2, int a2) {
/*  968 */     double offset = useDepth ? 0.003D : 0.0D;
/*  969 */     float w = Math.abs(x1 - x2) * 0.5F;
/*  970 */     float d = Math.abs(z1 - z2) * 0.5F;
/*      */     
/*  972 */     if (flagx) {
/*  973 */       w *= -1.0F;
/*      */     }
/*      */     
/*  976 */     if (flagz) {
/*  977 */       d *= -1.0F;
/*      */     }
/*      */     
/*  980 */     GL11.glEnable(2848);
/*  981 */     begin(3);
/*  982 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  983 */     buffer.func_181662_b(x2 - offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  984 */     buffer.func_181662_b(x2 - offset, y1 + offset, z2 - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  985 */     buffer.func_181662_b(x1 + offset, y1 + offset, z2 - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  986 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*      */     
/*  988 */     buffer.func_181662_b((x1 + w), y2, (z1 + d)).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  989 */     buffer.func_181662_b(x2 - offset, y1 + offset, z2 - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  990 */     render();
/*      */     
/*  992 */     begin(3);
/*  993 */     buffer.func_181662_b(x2 - offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  994 */     buffer.func_181662_b((x1 + w), y2, (z1 + d)).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*  995 */     buffer.func_181662_b(x1 + offset, y1 + offset, z2 - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*  996 */     render();
/*  997 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawGradientTwoPointLineBox(BufferBuilder buffer, boolean useDepth, float x1, float y1, float z1, float x2, float y2, float z2, int r1, int g1, int b1, int a1, int r2, int g2, int b2, int a2) {
/* 1001 */     double offset = useDepth ? 0.003D : 0.0D;
/* 1002 */     GL11.glEnable(2848);
/* 1003 */     begin(3);
/* 1004 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1005 */     buffer.func_181662_b(x2 - offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1006 */     buffer.func_181662_b(x2 - offset, y1 + offset, z2 - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1007 */     buffer.func_181662_b(x1 + offset, y1 + offset, z2 - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1008 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1009 */     buffer.func_181662_b(x1 + offset, y2, z1 + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/* 1010 */     buffer.func_181662_b(x2 - offset, y2, z1 + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/* 1011 */     buffer.func_181662_b(x2 - offset, y2, z2 - offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/* 1012 */     buffer.func_181662_b(x1 + offset, y2, z2 - offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/* 1013 */     buffer.func_181662_b(x1 + offset, y2, z1 + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/* 1014 */     render();
/*      */     
/* 1016 */     begin(1);
/* 1017 */     buffer.func_181662_b(x1 + offset, y2, z2 - offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/* 1018 */     buffer.func_181662_b(x1 + offset, y1 + offset, z2 - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1019 */     buffer.func_181662_b(x2 - offset, y2, z2 - offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/* 1020 */     buffer.func_181662_b(x2 - offset, y1 + offset, z2 - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1021 */     buffer.func_181662_b(x2 - offset, y2, z1 + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/* 1022 */     buffer.func_181662_b(x2 - offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1023 */     render();
/* 1024 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawTwoPointFilledBox(BufferBuilder buffer, boolean useDepth, float x1, float y1, float z1, float x2, float y2, float z2, int r, int g, int b, int a) {
/* 1028 */     double offset = useDepth ? 0.003D : 0.0D;
/* 1029 */     begin(8);
/* 1030 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1031 */     buffer.func_181662_b(x1 + offset, y2, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/* 1033 */     buffer.func_181662_b(x2 - offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1034 */     buffer.func_181662_b(x2 - offset, y2, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/* 1036 */     buffer.func_181662_b(x2 - offset, y1 + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1037 */     buffer.func_181662_b(x2 - offset, y2, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/* 1039 */     buffer.func_181662_b(x1 + offset, y1 + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1040 */     buffer.func_181662_b(x1 + offset, y2, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/* 1042 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1043 */     buffer.func_181662_b(x1 + offset, y2, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1044 */     render();
/*      */     
/* 1046 */     begin(7);
/* 1047 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1048 */     buffer.func_181662_b(x2 - offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1049 */     buffer.func_181662_b(x2 - offset, y1 + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1050 */     buffer.func_181662_b(x1 + offset, y1 + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/* 1052 */     buffer.func_181662_b(x1 + offset, y2, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1053 */     buffer.func_181662_b(x2 - offset, y2, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1054 */     buffer.func_181662_b(x2 - offset, y2, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1055 */     buffer.func_181662_b(x1 + offset, y2, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1056 */     render();
/*      */   }
/*      */   
/*      */   public static void drawTwoPointFilledPyramid(BufferBuilder buffer, boolean useDepth, boolean flagx, boolean flagz, float x1, float y1, float z1, float x2, float y2, float z2, int r, int g, int b, int a) {
/* 1060 */     double offset = useDepth ? 0.003D : 0.0D;
/* 1061 */     float w = Math.abs(x1 - x2) * 0.5F;
/* 1062 */     float d = Math.abs(z1 - z2) * 0.5F;
/*      */     
/* 1064 */     if (flagx) {
/* 1065 */       w *= -1.0F;
/*      */     }
/*      */     
/* 1068 */     if (flagz) {
/* 1069 */       d *= -1.0F;
/*      */     }
/*      */ 
/*      */     
/* 1073 */     GL11.glFrontFace(2304);
/* 1074 */     begin(6);
/* 1075 */     buffer.func_181662_b((x1 + w), y2, (z1 + d)).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/* 1077 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1078 */     buffer.func_181662_b(x2 - offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1079 */     buffer.func_181662_b(x2 - offset, y1 + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1080 */     buffer.func_181662_b(x1 + offset, y1 + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1081 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1082 */     render();
/* 1083 */     GL11.glFrontFace(2305);
/*      */     
/* 1085 */     begin(7);
/* 1086 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1087 */     buffer.func_181662_b(x2 - offset, y1 + offset, z1 + offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1088 */     buffer.func_181662_b(x2 - offset, y1 + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1089 */     buffer.func_181662_b(x1 + offset, y1 + offset, z2 - offset).func_181669_b(r, g, b, a).func_181675_d();
/* 1090 */     render();
/*      */   }
/*      */   
/*      */   public static void drawGradientTwoPointFilledPyramid(BufferBuilder buffer, boolean useDepth, boolean flagx, boolean flagz, float x1, float y1, float z1, float x2, float y2, float z2, int r1, int g1, int b1, int a1, int r2, int g2, int b2, int a2) {
/* 1094 */     double offset = useDepth ? 0.003D : 0.0D;
/* 1095 */     float w = Math.abs(x1 - x2) * 0.5F;
/* 1096 */     float d = Math.abs(z1 - z2) * 0.5F;
/*      */     
/* 1098 */     if (flagx) {
/* 1099 */       w *= -1.0F;
/*      */     }
/*      */     
/* 1102 */     if (flagz) {
/* 1103 */       d *= -1.0F;
/*      */     }
/*      */ 
/*      */     
/* 1107 */     GL11.glDisable(2884);
/* 1108 */     begin(6);
/* 1109 */     buffer.func_181662_b((x1 + w), y2, (z1 + d)).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*      */     
/* 1111 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1112 */     buffer.func_181662_b(x2 - offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1113 */     buffer.func_181662_b(x2 - offset, y1 + offset, z2 - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1114 */     buffer.func_181662_b(x1 + offset, y1 + offset, z2 - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1115 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1116 */     render();
/*      */     
/* 1118 */     begin(7);
/* 1119 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1120 */     buffer.func_181662_b(x2 - offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1121 */     buffer.func_181662_b(x2 - offset, y1 + offset, z2 - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1122 */     buffer.func_181662_b(x1 + offset, y1 + offset, z2 - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1123 */     render();
/* 1124 */     GL11.glEnable(2884);
/*      */   }
/*      */   
/*      */   public static void drawGradientTwoPointFilledBox(BufferBuilder buffer, boolean useDepth, boolean sidesOnly, float x1, float y1, float z1, float x2, float y2, float z2, int r1, int g1, int b1, int a1, int r2, int g2, int b2, int a2) {
/* 1128 */     double offset = useDepth ? 0.003D : 0.0D;
/* 1129 */     GL11.glDisable(2884);
/* 1130 */     begin(8);
/* 1131 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1132 */     buffer.func_181662_b(x1 + offset, y2, z1 + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*      */     
/* 1134 */     buffer.func_181662_b(x2 - offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1135 */     buffer.func_181662_b(x2 - offset, y2, z1 + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*      */     
/* 1137 */     buffer.func_181662_b(x2 - offset, y1 + offset, z2 - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1138 */     buffer.func_181662_b(x2 - offset, y2, z2 - offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*      */     
/* 1140 */     buffer.func_181662_b(x1 + offset, y1 + offset, z2 - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1141 */     buffer.func_181662_b(x1 + offset, y2, z2 - offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/*      */     
/* 1143 */     buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1144 */     buffer.func_181662_b(x1 + offset, y2, z1 + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/* 1145 */     render();
/*      */     
/* 1147 */     if (!sidesOnly) {
/* 1148 */       begin(7);
/* 1149 */       buffer.func_181662_b(x1 + offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1150 */       buffer.func_181662_b(x2 - offset, y1 + offset, z1 + offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1151 */       buffer.func_181662_b(x2 - offset, y1 + offset, z2 - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/* 1152 */       buffer.func_181662_b(x1 + offset, y1 + offset, z2 - offset).func_181669_b(r1, g1, b1, a1).func_181675_d();
/*      */       
/* 1154 */       buffer.func_181662_b(x1 + offset, y2, z2 - offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/* 1155 */       buffer.func_181662_b(x2 - offset, y2, z2 - offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/* 1156 */       buffer.func_181662_b(x2 - offset, y2, z1 + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/* 1157 */       buffer.func_181662_b(x1 + offset, y2, z1 + offset).func_181669_b(r2, g2, b2, a2).func_181675_d();
/* 1158 */       render();
/*      */     } 
/* 1160 */     GL11.glEnable(2884);
/*      */   }
/*      */   
/*      */   public static void drawBetterBoundingBoxLines(BufferBuilder buffer, AxisAlignedBB boundingBox, Vec3d vec, float scale, int r, int g, int b, int a) {
/* 1164 */     boundingBox = EntityUtil.scaleBB(vec, boundingBox, scale);
/*      */     
/* 1166 */     GL11.glEnable(2848);
/* 1167 */     begin(3);
/* 1168 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1169 */     buffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1170 */     buffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
/* 1171 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
/* 1172 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1173 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1174 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
/* 1175 */     buffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
/* 1176 */     buffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1177 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1178 */     render();
/*      */     
/* 1180 */     begin(1);
/* 1181 */     buffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1182 */     buffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1183 */     buffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
/* 1184 */     buffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
/* 1185 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
/* 1186 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
/* 1187 */     render();
/* 1188 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawBetterBoundingBoxFilled(BufferBuilder buffer, AxisAlignedBB boundingBox, Vec3d vec, float scale, int r, int g, int b, int a) {
/* 1192 */     boundingBox = EntityUtil.scaleBB(vec, boundingBox, scale);
/*      */     
/* 1194 */     begin(8);
/* 1195 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1196 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/* 1198 */     buffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1199 */     buffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/* 1201 */     buffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
/* 1202 */     buffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/* 1204 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
/* 1205 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/* 1207 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1208 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1209 */     render();
/*      */     
/* 1211 */     begin(7);
/* 1212 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1213 */     buffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1214 */     buffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
/* 1215 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/* 1217 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
/* 1218 */     buffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181669_b(r, g, b, a).func_181675_d();
/* 1219 */     buffer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1220 */     buffer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181669_b(r, g, b, a).func_181675_d();
/*      */     
/* 1222 */     render();
/*      */   }
/*      */   
/*      */   public static AxisAlignedBB getBoundingFromPos(BlockPos pos) {
/* 1226 */     IBlockState iBlockState = EntityUtil.mc.field_71441_e.func_180495_p(pos);
/* 1227 */     return iBlockState.func_185918_c((World)EntityUtil.mc.field_71441_e, pos).func_72321_a(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D);
/*      */   }
/*      */   
/*      */   public static Vec3d[] verticesFromBlockFace(BlockPos pos, EnumFacing face) {
/* 1231 */     AxisAlignedBB bb = getBoundingFromPos(pos);
/*      */     
/* 1233 */     switch (face) {
/*      */       case UP:
/* 1235 */         return new Vec3d[] { new Vec3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c), new Vec3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c), new Vec3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f), new Vec3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case DOWN:
/* 1244 */         return new Vec3d[] { new Vec3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c), new Vec3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c), new Vec3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f), new Vec3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case NORTH:
/* 1253 */         return new Vec3d[] { new Vec3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c), new Vec3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c), new Vec3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c), new Vec3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case SOUTH:
/* 1262 */         return new Vec3d[] { new Vec3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f), new Vec3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f), new Vec3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f), new Vec3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case EAST:
/* 1271 */         return new Vec3d[] { new Vec3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c), new Vec3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f), new Vec3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f), new Vec3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c) };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case WEST:
/* 1280 */         return new Vec3d[] { new Vec3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c), new Vec3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f), new Vec3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f), new Vec3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c) };
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1289 */     return new Vec3d[] { new Vec3d(0.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, 0.0D) };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawBlockFaceFilledBB(BlockPos pos, EnumFacing face, int color) {
/* 1296 */     int a = color >>> 24 & 0xFF;
/* 1297 */     int r = color >>> 16 & 0xFF;
/* 1298 */     int g = color >>> 8 & 0xFF;
/* 1299 */     int b = color & 0xFF;
/* 1300 */     GL11.glDisable(2884);
/* 1301 */     drawBlockFaceFilledBB(INSTANCE.func_178180_c(), pos, face, r, g, b, a);
/* 1302 */     GL11.glEnable(2884);
/*      */   }
/*      */   
/*      */   public static void drawBlockFaceFilledBB(BufferBuilder buffer, BlockPos pos, EnumFacing face, int r, int g, int b, int a) {
/* 1306 */     Vec3d[] vertices = verticesFromBlockFace(pos, face);
/*      */     
/* 1308 */     begin(7);
/* 1309 */     buffer.func_181662_b((vertices[0]).field_72450_a, (vertices[0]).field_72448_b, (vertices[0]).field_72449_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1310 */     buffer.func_181662_b((vertices[1]).field_72450_a, (vertices[1]).field_72448_b, (vertices[1]).field_72449_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1311 */     buffer.func_181662_b((vertices[2]).field_72450_a, (vertices[2]).field_72448_b, (vertices[2]).field_72449_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1312 */     buffer.func_181662_b((vertices[3]).field_72450_a, (vertices[3]).field_72448_b, (vertices[3]).field_72449_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1313 */     render();
/*      */   }
/*      */   
/*      */   public static void drawBlockFaceLinesBB(BlockPos pos, EnumFacing face, float lineWidth, int color) {
/* 1317 */     int a = color >>> 24 & 0xFF;
/* 1318 */     int r = color >>> 16 & 0xFF;
/* 1319 */     int g = color >>> 8 & 0xFF;
/* 1320 */     int b = color & 0xFF;
/* 1321 */     GL11.glLineWidth(lineWidth);
/* 1322 */     drawBlockFaceLinesBB(INSTANCE.func_178180_c(), pos, face, r, g, b, a);
/*      */   }
/*      */   
/*      */   public static void drawBlockFaceLinesBB(BufferBuilder buffer, BlockPos pos, EnumFacing face, int r, int g, int b, int a) {
/* 1326 */     Vec3d[] vertices = verticesFromBlockFace(pos, face);
/*      */     
/* 1328 */     begin(3);
/* 1329 */     buffer.func_181662_b((vertices[0]).field_72450_a, (vertices[0]).field_72448_b, (vertices[0]).field_72449_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1330 */     buffer.func_181662_b((vertices[1]).field_72450_a, (vertices[1]).field_72448_b, (vertices[1]).field_72449_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1331 */     buffer.func_181662_b((vertices[2]).field_72450_a, (vertices[2]).field_72448_b, (vertices[2]).field_72449_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1332 */     buffer.func_181662_b((vertices[3]).field_72450_a, (vertices[3]).field_72448_b, (vertices[3]).field_72449_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1333 */     buffer.func_181662_b((vertices[0]).field_72450_a, (vertices[0]).field_72448_b, (vertices[0]).field_72449_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1334 */     render();
/*      */   }
/*      */   
/*      */   public static void drawLineToVec(Vec3d vec1, Vec3d vec2, float lineWidth, int color) {
/* 1338 */     int a = color >>> 24 & 0xFF;
/* 1339 */     int r = color >>> 16 & 0xFF;
/* 1340 */     int g = color >>> 8 & 0xFF;
/* 1341 */     int b = color & 0xFF;
/* 1342 */     BufferBuilder buffer = INSTANCE.func_178180_c();
/*      */     
/* 1344 */     GL11.glLineWidth(lineWidth);
/* 1345 */     GL11.glEnable(2848);
/*      */     
/* 1347 */     begin(1);
/* 1348 */     buffer.func_181662_b(vec1.field_72450_a, vec1.field_72448_b, vec1.field_72449_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1349 */     buffer.func_181662_b(vec2.field_72450_a, vec2.field_72448_b, vec2.field_72449_c).func_181669_b(r, g, b, a).func_181675_d();
/* 1350 */     render();
/*      */     
/* 1352 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawTracer(Entity entity, float lineWidth, boolean spine, int color) {
/* 1356 */     int a = color >>> 24 & 0xFF;
/* 1357 */     int r = color >>> 16 & 0xFF;
/* 1358 */     int g = color >>> 8 & 0xFF;
/* 1359 */     int b = color & 0xFF;
/* 1360 */     Vec3d entityPos = EntityUtil.interpolateEntityRender(entity, EntityUtil.mc.func_184121_ak());
/* 1361 */     assert EntityUtil.mc.field_175622_Z != null;
/* 1362 */     Vec3d selfPos = EntityUtil.interpolateEntityRender(EntityUtil.mc.field_175622_Z, EntityUtil.mc.func_184121_ak());
/* 1363 */     double[] rotations = MathUtilFuckYou.rotationAroundAxis3d(0.0D, 0.0D, 1.0D, (EntityUtil.mc.field_175622_Z.field_70125_A * 0.017453292F), "x");
/* 1364 */     rotations = MathUtilFuckYou.rotationAroundAxis3d(rotations[0], rotations[1], rotations[2], (-EntityUtil.mc.field_175622_Z.field_70177_z * 0.017453292F), "y");
/* 1365 */     selfPos = new Vec3d(selfPos.field_72450_a + rotations[0], selfPos.field_72448_b + EntityUtil.mc.field_175622_Z.func_70047_e() + rotations[1], selfPos.field_72449_c + rotations[2]);
/*      */     
/* 1367 */     GL11.glLineWidth(lineWidth);
/* 1368 */     GL11.glEnable(2848);
/* 1369 */     GL11.glColor4f(r / 255.0F, g / 255.0F, b / 255.0F, a / 255.0F);
/*      */     
/* 1371 */     if (EntityUtil.mc.field_71474_y.field_74336_f) {
/* 1372 */       GL11.glLoadIdentity();
/* 1373 */       EntityUtil.mc.field_71460_t.func_78467_g(EntityUtil.mc.func_184121_ak());
/*      */     } 
/*      */ 
/*      */     
/* 1377 */     GL11.glBegin(1);
/* 1378 */     if (spine) {
/* 1379 */       GL11.glVertex3d(entityPos.field_72450_a, entityPos.field_72448_b, entityPos.field_72449_c);
/* 1380 */       GL11.glVertex3d(entityPos.field_72450_a, entityPos.field_72448_b + entity.field_70131_O, entityPos.field_72449_c);
/*      */     } 
/*      */     
/* 1383 */     GL11.glVertex3d(entityPos.field_72450_a, entityPos.field_72448_b, entityPos.field_72449_c);
/* 1384 */     GL11.glVertex3d(selfPos.field_72450_a, selfPos.field_72448_b, selfPos.field_72449_c);
/*      */     
/* 1386 */     GL11.glEnd();
/* 1387 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */     
/* 1389 */     GL11.glDisable(2848);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawPlayer(EntityOtherPlayerMP entityPlayer, ModelPlayer model, float limbSwing, float limbSwingAmount, float headYaw, float headPitch, boolean solid, boolean lines, boolean points, float lineWidth, float pointSize, float alphaFactor, boolean texture, float swingProgress, int solidColorFriend, int lineColorFriend, int pointColorFriend, int solidColorEnemy, int lineColorEnemy, int pointColorEnemy, int solidColorSelf, int lineColorSelf, int pointColorSelf, int solidColor, int lineColor, int pointColor) {
/*      */     int sr, sg, sb, sa, lr, lg, lb, la, pr, pg, pb, pa;
/* 1399 */     if (entityPlayer.func_70005_c_().equals(EntityUtil.mc.field_71439_g.func_70005_c_())) {
/* 1400 */       sa = (int)((solidColorSelf >>> 24 & 0xFF) * alphaFactor / 300.0F);
/* 1401 */       sr = solidColorSelf >>> 16 & 0xFF;
/* 1402 */       sg = solidColorSelf >>> 8 & 0xFF;
/* 1403 */       sb = solidColorSelf & 0xFF;
/*      */       
/* 1405 */       la = (int)((lineColorSelf >>> 24 & 0xFF) * alphaFactor / 300.0F);
/* 1406 */       lr = lineColorSelf >>> 16 & 0xFF;
/* 1407 */       lg = lineColorSelf >>> 8 & 0xFF;
/* 1408 */       lb = lineColorSelf & 0xFF;
/*      */       
/* 1410 */       pa = (int)((pointColorSelf >>> 24 & 0xFF) * alphaFactor / 300.0F);
/* 1411 */       pr = pointColorSelf >>> 16 & 0xFF;
/* 1412 */       pg = pointColorSelf >>> 8 & 0xFF;
/* 1413 */       pb = pointColorSelf & 0xFF;
/*      */     
/*      */     }
/* 1416 */     else if (FriendManager.isFriend((Entity)entityPlayer)) {
/* 1417 */       sa = (int)((solidColorFriend >>> 24 & 0xFF) * alphaFactor / 300.0F);
/* 1418 */       sr = solidColorFriend >>> 16 & 0xFF;
/* 1419 */       sg = solidColorFriend >>> 8 & 0xFF;
/* 1420 */       sb = solidColorFriend & 0xFF;
/*      */       
/* 1422 */       la = (int)((lineColorFriend >>> 24 & 0xFF) * alphaFactor / 300.0F);
/* 1423 */       lr = lineColorFriend >>> 16 & 0xFF;
/* 1424 */       lg = lineColorFriend >>> 8 & 0xFF;
/* 1425 */       lb = lineColorFriend & 0xFF;
/*      */       
/* 1427 */       pa = (int)((pointColorFriend >>> 24 & 0xFF) * alphaFactor / 300.0F);
/* 1428 */       pr = pointColorFriend >>> 16 & 0xFF;
/* 1429 */       pg = pointColorFriend >>> 8 & 0xFF;
/* 1430 */       pb = pointColorFriend & 0xFF;
/* 1431 */     } else if (EnemyManager.isEnemy((Entity)entityPlayer)) {
/* 1432 */       sa = (int)((solidColorEnemy >>> 24 & 0xFF) * alphaFactor / 300.0F);
/* 1433 */       sr = solidColorEnemy >>> 16 & 0xFF;
/* 1434 */       sg = solidColorEnemy >>> 8 & 0xFF;
/* 1435 */       sb = solidColorEnemy & 0xFF;
/*      */       
/* 1437 */       la = (int)((lineColorEnemy >>> 24 & 0xFF) * alphaFactor / 300.0F);
/* 1438 */       lr = lineColorEnemy >>> 16 & 0xFF;
/* 1439 */       lg = lineColorEnemy >>> 8 & 0xFF;
/* 1440 */       lb = lineColorEnemy & 0xFF;
/*      */       
/* 1442 */       pa = (int)((pointColorEnemy >>> 24 & 0xFF) * alphaFactor / 300.0F);
/* 1443 */       pr = pointColorEnemy >>> 16 & 0xFF;
/* 1444 */       pg = pointColorEnemy >>> 8 & 0xFF;
/* 1445 */       pb = pointColorEnemy & 0xFF;
/*      */     } else {
/* 1447 */       sa = (int)((solidColor >>> 24 & 0xFF) * alphaFactor / 300.0F);
/* 1448 */       sr = solidColor >>> 16 & 0xFF;
/* 1449 */       sg = solidColor >>> 8 & 0xFF;
/* 1450 */       sb = solidColor & 0xFF;
/*      */       
/* 1452 */       la = (int)((lineColor >>> 24 & 0xFF) * alphaFactor / 300.0F);
/* 1453 */       lr = lineColor >>> 16 & 0xFF;
/* 1454 */       lg = lineColor >>> 8 & 0xFF;
/* 1455 */       lb = lineColor & 0xFF;
/*      */       
/* 1457 */       pa = (int)((pointColor >>> 24 & 0xFF) * alphaFactor / 300.0F);
/* 1458 */       pr = pointColor >>> 16 & 0xFF;
/* 1459 */       pg = pointColor >>> 8 & 0xFF;
/* 1460 */       pb = pointColor & 0xFF;
/*      */     } 
/*      */ 
/*      */     
/* 1464 */     GL11.glPushMatrix();
/* 1465 */     GL11.glTranslated((EntityUtil.interpolateEntityRender((Entity)entityPlayer, EntityUtil.mc.func_184121_ak())).field_72450_a, 
/* 1466 */         (EntityUtil.interpolateEntityRender((Entity)entityPlayer, EntityUtil.mc.func_184121_ak())).field_72448_b, 
/* 1467 */         (EntityUtil.interpolateEntityRender((Entity)entityPlayer, EntityUtil.mc.func_184121_ak())).field_72449_c);
/* 1468 */     GlStateManager.func_179091_B();
/* 1469 */     GL11.glRotatef(180.0F - entityPlayer.field_70177_z, 0.0F, 1.0F, 0.0F);
/* 1470 */     GL11.glScalef(-1.0F, -1.0F, 1.0F);
/* 1471 */     GlStateManager.func_179152_a(0.9375F, 0.9375F, 0.9375F);
/* 1472 */     GL11.glTranslatef(0.0F, -1.501F, 0.0F);
/*      */     
/* 1474 */     if (solid) {
/* 1475 */       if (texture) {
/* 1476 */         GlStateManager.func_179126_j();
/* 1477 */         GlStateManager.func_179132_a(true);
/* 1478 */         GL11.glDepthRange(0.0D, 0.01D);
/* 1479 */         GL11.glEnable(3553);
/* 1480 */         Command.mc.func_110434_K().func_110577_a(entityPlayer.func_110306_p());
/*      */       } 
/* 1482 */       GL11.glPolygonMode(1032, 6914);
/* 1483 */       GL11.glColor4f(sr / 255.0F, sg / 255.0F, sb / 255.0F, sa / 255.0F);
/* 1484 */       model.func_78088_a((Entity)entityPlayer, limbSwing, limbSwingAmount, entityPlayer.field_70173_aa, headYaw, headPitch, 0.0625F);
/* 1485 */       if (texture) {
/* 1486 */         GlStateManager.func_179097_i();
/* 1487 */         GlStateManager.func_179132_a(false);
/* 1488 */         GL11.glDepthRange(0.0D, 1.0D);
/* 1489 */         GL11.glDisable(3553);
/*      */       } 
/*      */     } 
/*      */     
/* 1493 */     if (lines) {
/* 1494 */       GL11.glPolygonMode(1032, 6913);
/* 1495 */       GL11.glLineWidth(lineWidth);
/* 1496 */       GL11.glEnable(2848);
/* 1497 */       GL11.glColor4f(lr / 255.0F, lg / 255.0F, lb / 255.0F, la / 255.0F);
/* 1498 */       model.func_78088_a((Entity)entityPlayer, limbSwing, limbSwingAmount, entityPlayer.field_70173_aa, headYaw, headPitch, 0.0625F);
/* 1499 */       GL11.glDisable(2848);
/*      */     } 
/*      */     
/* 1502 */     if (points) {
/* 1503 */       GL11.glPolygonMode(1032, 6912);
/* 1504 */       GL11.glPointSize(pointSize);
/* 1505 */       GL11.glEnable(2832);
/* 1506 */       GL11.glColor4f(pr / 255.0F, pg / 255.0F, pb / 255.0F, pa / 255.0F);
/* 1507 */       model.func_78088_a((Entity)entityPlayer, limbSwing, limbSwingAmount, entityPlayer.field_70173_aa, headYaw, headPitch, 0.0625F);
/* 1508 */       GL11.glDisable(2832);
/*      */     } 
/*      */     
/* 1511 */     GL11.glPolygonMode(1032, 6914);
/* 1512 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1513 */     GL11.glPopMatrix();
/*      */   }
/*      */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\graphics\SpartanTessellator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */