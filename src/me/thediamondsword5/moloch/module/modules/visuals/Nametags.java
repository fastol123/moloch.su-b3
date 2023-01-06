/*     */ package me.thediamondsword5.moloch.module.modules.visuals;
/*     */ import java.awt.Color;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import me.thediamondsword5.moloch.client.EnemyManager;
/*     */ import me.thediamondsword5.moloch.client.PopManager;
/*     */ import me.thediamondsword5.moloch.core.common.Color;
/*     */ import me.thediamondsword5.moloch.hud.huds.ArmorDisplay;
/*     */ import me.thediamondsword5.moloch.module.modules.client.CustomFont;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.spartanb312.base.client.FontManager;
/*     */ import net.spartanb312.base.client.FriendManager;
/*     */ import net.spartanb312.base.common.annotations.ModuleInfo;
/*     */ import net.spartanb312.base.common.annotations.Parallel;
/*     */ import net.spartanb312.base.core.setting.Setting;
/*     */ import net.spartanb312.base.event.events.render.RenderEvent;
/*     */ import net.spartanb312.base.module.Category;
/*     */ import net.spartanb312.base.module.Module;
/*     */ import net.spartanb312.base.utils.ColorUtil;
/*     */ import net.spartanb312.base.utils.CrystalUtil;
/*     */ import net.spartanb312.base.utils.EntityUtil;
/*     */ import net.spartanb312.base.utils.MathUtilFuckYou;
/*     */ import net.spartanb312.base.utils.Timer;
/*     */ import net.spartanb312.base.utils.graphics.RenderHelper;
/*     */ import net.spartanb312.base.utils.graphics.RenderUtils2D;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @Parallel
/*     */ @ModuleInfo(name = "Nametags", category = Category.VISUALS, description = "Draw nametags with information on top of entities")
/*     */ public class Nametags extends Module {
/*  42 */   private final HashMap<Entity, Float> prevHealthMap = new HashMap<>(); public static Nametags INSTANCE;
/*  43 */   private final HashMap<Entity, Integer> interpProgressMap = new HashMap<>();
/*  44 */   private final HashMap<Entity, Float> prevHealthAbsorptionMap = new HashMap<>();
/*  45 */   private final HashMap<Entity, Integer> interpProgressAbsorptionMap = new HashMap<>();
/*  46 */   private final Timer timer = new Timer();
/*     */   
/*  48 */   Setting<Float> size = setting("GlobalSize", 0.4F, 0.1F, 3.0F).des("Scales everything in the nametag at once");
/*  49 */   Setting<Page> page = setting("Page", Page.Distance);
/*     */   
/*  51 */   Setting<Float> range = setting("Range", 256.0F, 1.0F, 256.0F).des("Distance to render nametags in on entities").whenAtMode(this.page, Page.Distance);
/*  52 */   float theFuckingScaleIllFixThisLator = 0.188F;
/*     */   
/*  54 */   Setting<Float> innerLockRange = setting("InnerLockRange", 2.5F, 0.0F, 10.0F).des("Stops scaling nametags down when you reach within this distance of that nametag").whenAtMode(this.page, Page.Distance);
/*  55 */   Setting<Float> outerLockRange = setting("OuterLockRange", 25.5F, 0.0F, 50.0F).des("Stops scaling nametags up when you reach beyond this distance of that nametag").whenAtMode(this.page, Page.Distance);
/*     */   
/*  57 */   Setting<Float> yOffset = setting("YOffset", 0.8F, 0.0F, 5.0F).whenAtMode(this.page, Page.Rect);
/*  58 */   Setting<Float> rectHeight = setting("RectHeight", 13.3F, 0.1F, 50.0F).des("Height of BG rect").whenAtMode(this.page, Page.Rect);
/*  59 */   Setting<Float> rectWidth = setting("RectWidth", 4.9F, -20.0F, 20.0F).des("Extra width from the ends of the nametag text").whenAtMode(this.page, Page.Rect);
/*  60 */   Setting<Boolean> roundedRect = setting("RoundedRect", false).des("Rounded BG rect").whenAtMode(this.page, Page.Rect);
/*  61 */   Setting<Float> roundedRectRadius = setting("RoundedRectRadius", 0.5F, 0.0F, 1.0F).des("Radius of rounded BG rect corners").whenTrue(this.roundedRect).whenAtMode(this.page, Page.Rect);
/*  62 */   Setting<Boolean> roundedRectTopRight = setting("RoundedTopRight", true).whenTrue(this.roundedRect).whenAtMode(this.page, Page.Rect);
/*  63 */   Setting<Boolean> roundedRectTopLeft = setting("RoundedTopLeft", true).whenTrue(this.roundedRect).whenAtMode(this.page, Page.Rect);
/*  64 */   Setting<Boolean> roundedRectDownRight = setting("RoundedDownRight", true).whenTrue(this.roundedRect).whenAtMode(this.page, Page.Rect);
/*  65 */   Setting<Boolean> roundedRectDownLeft = setting("RoundedDownLeft", true).whenTrue(this.roundedRect).whenAtMode(this.page, Page.Rect);
/*  66 */   Setting<Boolean> rectBorder = setting("RectBorder", false).des("Draw a border around the BG rect").whenAtMode(this.page, Page.Rect);
/*  67 */   Setting<Float> rectBorderWidth = setting("RectBorderWidth", 1.0F, 1.0F, 2.0F).des("BG rect border thickness").whenTrue(this.rectBorder).whenAtMode(this.page, Page.Rect);
/*  68 */   Setting<Float> rectBorderOffset = setting("RectBorderOffset", 2.0F, 0.0F, 5.0F).des("BG rect border offset from rect").whenTrue(this.rectBorder).whenAtMode(this.page, Page.Rect);
/*     */   
/*  70 */   Setting<Boolean> healthBar = setting("HealthBar", true).des("Draws a rect showing how much health the nametagged entity has").whenAtMode(this.page, Page.HealthBar);
/*  71 */   Setting<Float> healthBarThickness = setting("HealthBarThickness", 1.5F, 0.1F, 4.0F).des("Thickness of health bar").whenTrue(this.healthBar).whenAtMode(this.page, Page.HealthBar);
/*  72 */   Setting<Float> healthBarOffset = setting("HealthBarYOffset", 0.0F, -20.0F, 20.0F).des("Y to offset the health bar").whenTrue(this.healthBar).whenAtMode(this.page, Page.HealthBar);
/*  73 */   Setting<Float> healthBarAbsorptionOffset = setting("HBAbsorptionYOffset", -1.1F, -20.0F, 20.0F).des("Y to offset the absorption bar from the health bar").whenTrue(this.healthBar).whenAtMode(this.page, Page.HealthBar);
/*  74 */   Setting<Boolean> healthBarColorShift = setting("HealthBarColorShift", true).des("Change health bar color as health goes down").whenTrue(this.healthBar).whenAtMode(this.page, Page.HealthBar);
/*  75 */   Setting<Boolean> healthBarAbsorptionColorShift = setting("HBAbsorptionColorShift", false).des("Change absorption bar color as absorption goes down").whenTrue(this.healthBar).whenAtMode(this.page, Page.HealthBar);
/*  76 */   Setting<Boolean> healthBarInterp = setting("HealthBarSmooth", true).des("Interpolates the health bar width as health changes instead of having the bar instantly snap to a new width when health changes").whenTrue(this.healthBar).whenAtMode(this.page, Page.HealthBar);
/*  77 */   Setting<Float> healthBarInterpFactor = setting("HBSmoothFactor", 20.0F, 0.1F, 30.0F).des("Speed of interpolation of health bar width").whenTrue(this.healthBarInterp).whenTrue(this.healthBar).whenAtMode(this.page, Page.HealthBar);
/*  78 */   Setting<Boolean> roundedHealthBar = setting("RoundedHB", false).des("Rounded health bar").whenTrue(this.healthBar).whenAtMode(this.page, Page.HealthBar);
/*  79 */   Setting<Float> roundedHealthBarRadius = setting("RoundedHBRadius", 1.0F, 0.0F, 1.0F).des("Radius of rounded health bar corners").whenTrue(this.healthBar).whenTrue(this.roundedHealthBar).whenAtMode(this.page, Page.HealthBar);
/*  80 */   Setting<Boolean> roundedHealthBarTopRight = setting("RoundedHBTopRight", true).whenTrue(this.healthBar).whenTrue(this.roundedHealthBar).whenAtMode(this.page, Page.HealthBar);
/*  81 */   Setting<Boolean> roundedHealthBarTopLeft = setting("RoundedHBTopLeft", true).whenTrue(this.healthBar).whenTrue(this.roundedHealthBar).whenAtMode(this.page, Page.HealthBar);
/*  82 */   Setting<Boolean> roundedHealthBarDownRight = setting("RoundedHBDownRight", true).whenTrue(this.healthBar).whenTrue(this.roundedHealthBar).whenAtMode(this.page, Page.HealthBar);
/*  83 */   Setting<Boolean> roundedHealthBarDownLeft = setting("RoundedHBDownLeft", true).whenTrue(this.healthBar).whenTrue(this.roundedHealthBar).whenAtMode(this.page, Page.HealthBar);
/*  84 */   Setting<Boolean> roundedHealthBarAbsorption = setting("RoundedHBA", false).des("Rounded absorption bar").whenTrue(this.healthBar).whenAtMode(this.page, Page.HealthBar);
/*  85 */   Setting<Float> roundedHealthBarAbsorptionRadius = setting("RoundedHBARadius", 1.0F, 0.0F, 1.0F).des("Radius of rounded absorption bar corners").whenTrue(this.healthBar).whenTrue(this.roundedHealthBarAbsorption).whenAtMode(this.page, Page.HealthBar);
/*  86 */   Setting<Boolean> roundedHealthBarAbsorptionTopRight = setting("RoundedHBATopRight", true).whenTrue(this.healthBar).whenTrue(this.roundedHealthBarAbsorption).whenAtMode(this.page, Page.HealthBar);
/*  87 */   Setting<Boolean> roundedHealthBarAbsorptionTopLeft = setting("RoundedHBATopLeft", true).whenTrue(this.healthBar).whenTrue(this.roundedHealthBarAbsorption).whenAtMode(this.page, Page.HealthBar);
/*  88 */   Setting<Boolean> roundedHealthBarAbsorptionDownRight = setting("RoundedHBADownRight", true).whenTrue(this.healthBar).whenTrue(this.roundedHealthBarAbsorption).whenAtMode(this.page, Page.HealthBar);
/*  89 */   Setting<Boolean> roundedHealthBarAbsorptionDownLeft = setting("RoundedHBADownLeft", true).whenTrue(this.healthBar).whenTrue(this.roundedHealthBarAbsorption).whenAtMode(this.page, Page.HealthBar);
/*  90 */   Setting<Boolean> healthBarBorder = setting("HBBorder", false).des("Draw a border around the health bar").whenTrue(this.healthBar).whenAtMode(this.page, Page.HealthBar);
/*  91 */   Setting<Float> healthBarBorderWidth = setting("HBBorderWidth", 1.0F, 1.0F, 2.0F).des("BG health bar thickness").whenTrue(this.healthBar).whenTrue(this.healthBarBorder).whenAtMode(this.page, Page.HealthBar);
/*  92 */   Setting<Float> healthBarBorderOffset = setting("HBBorderOffset", 2.0F, 0.0F, 5.0F).des("BG health bar offset from rect").whenTrue(this.healthBar).whenTrue(this.healthBarBorder).whenAtMode(this.page, Page.HealthBar);
/*     */   
/*  94 */   Setting<Boolean> shadow = setting("RectShadow", true).des("Gradient BG rect shadow").whenAtMode(this.page, Page.RectEffects);
/*  95 */   Setting<Boolean> shadowCenterRect = setting("RectShadowCenterRect", false).des("Fill in the center of the rect shadow with a rect to match the outside gradient").whenTrue(this.shadow).whenAtMode(this.page, Page.RectEffects);
/*  96 */   Setting<Float> shadowSize = setting("RectShadowSize", 0.18F, 0.0F, 1.0F).des("Size of outside shadow gradient").whenTrue(this.shadow).whenAtMode(this.page, Page.RectEffects);
/*  97 */   Setting<Integer> shadowAlpha = setting("RectShadowAlpha", 33, 0, 255).des("Alpha of BG rect shadow").whenTrue(this.shadow).whenAtMode(this.page, Page.RectEffects);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   Setting<Boolean> players = setting("Players", true).des("Draw nametags on players").whenAtMode(this.page, Page.Entities);
/* 103 */   Setting<Boolean> mobs = setting("Mobs", false).des("Draw nametags on mobs").whenAtMode(this.page, Page.Entities);
/* 104 */   Setting<Boolean> animals = setting("Animals", false).des("Draw nametags on animals").whenAtMode(this.page, Page.Entities);
/* 105 */   Setting<Boolean> items = setting("Items", false).des("Draw nametags on dropped items").whenAtMode(this.page, Page.Entities);
/*     */   
/* 107 */   Setting<CustomFont.FontMode> font = setting("Font", (Enum)CustomFont.FontMode.Comfortaa).whenAtMode(this.page, Page.Text);
/*     */   
/* 109 */   Setting<Float> textYOffset = setting("TextYOffset", 1.3F, 0.0F, 20.0F).des("Amount to offset the text vertically").whenAtMode(this.page, Page.Text);
/* 110 */   Setting<Float> textSpace = setting("TextSpace", 6.5F, 0.0F, 10.0F).des("Spaces between text").whenAtMode(this.page, Page.Text);
/* 111 */   Setting<Boolean> textShadow = setting("TextShadow", true).des("Draw shadow below text").whenAtMode(this.page, Page.Text);
/* 112 */   Setting<TextMode> ping = setting("Ping", TextMode.Left).des("Display ping on nametags").whenAtMode(this.page, Page.Text);
/* 113 */   Setting<TextMode> health = setting("Health", TextMode.Right).des("Display health on nametags").whenAtMode(this.page, Page.Text);
/* 114 */   public Setting<TextMode> popCount = setting("PopCount", TextMode.Right).des("Display recently popped totem count on nametags").whenAtMode(this.page, Page.Text);
/* 115 */   Setting<TextMode> itemCount = setting("ItemCount", TextMode.Left).whenTrue(this.items).whenAtMode(this.page, Page.Text);
/*     */   
/* 117 */   Setting<Integer> itemsOffsetY = setting("ItemsY", 33, 0, 50).des("Y offset of items").whenAtMode(this.page, Page.Items);
/* 118 */   Setting<Float> itemsScale = setting("ItemsScale", 0.6F, 0.1F, 2.0F).des("Scale of items").whenAtMode(this.page, Page.Items);
/* 119 */   Setting<Integer> separationDistItems = setting("ItemsSeparationDist", 20, 0, 50).des("Distance between each items").whenAtMode(this.page, Page.Items);
/* 120 */   Setting<Boolean> duraBar = setting("DurabilityBar", true).des("Draws a bar showing remaining durability for an item").whenAtMode(this.page, Page.Items);
/* 121 */   Setting<Float> duraBarOffsetY = setting("DuraBarY", -16.2F, -20.0F, 20.0F).des("Durability bar y offset").whenTrue(this.duraBar).whenAtMode(this.page, Page.Items);
/* 122 */   Setting<Boolean> duraColorShift = setting("DuraColorShift", true).des("Changes durability bar color as durability of item goes down").whenTrue(this.duraBar).whenAtMode(this.page, Page.Items);
/* 123 */   Setting<Integer> duraBarHeight = setting("DuraBarHeight", 1, 1, 20).whenTrue(this.duraBar).whenAtMode(this.page, Page.Items);
/* 124 */   Setting<Boolean> duraBarRounded = setting("DuraBarRounded", false).des("Rounded rect for durability bar").whenTrue(this.duraBar).whenAtMode(this.page, Page.Items);
/* 125 */   Setting<Float> duraBarRoundedRadius = setting("DuraBarRoundedRadius", 0.6F, 0.0F, 1.0F).des("Radius of rounded durability bar").whenTrue(this.duraBarRounded).whenTrue(this.duraBar).whenAtMode(this.page, Page.Items);
/* 126 */   Setting<Boolean> duraBarRoundedTopRight = setting("DuraBarRoundedTopRight", true).des("Rounded corner for rounded durability bar top right").whenTrue(this.duraBarRounded).whenTrue(this.duraBar).whenAtMode(this.page, Page.Items);
/* 127 */   Setting<Boolean> duraBarRoundedTopLeft = setting("DuraBarRoundedTopLeft", true).des("Rounded corner for rounded durability bar top left").whenTrue(this.duraBarRounded).whenTrue(this.duraBar).whenAtMode(this.page, Page.Items);
/* 128 */   Setting<Boolean> duraBarRoundedDownRight = setting("DuraBarRoundedDownRight", true).des("Rounded corner for rounded durability bar bottom right").whenTrue(this.duraBarRounded).whenTrue(this.duraBar).whenAtMode(this.page, Page.Items);
/* 129 */   Setting<Boolean> duraBarRoundedDownLeft = setting("DuraBarRoundedDownLeft", true).des("Rounded corner for rounded durability bar bottom left").whenTrue(this.duraBarRounded).whenTrue(this.duraBar).whenAtMode(this.page, Page.Items);
/* 130 */   Setting<Boolean> duraBarBordered = setting("DuraBarBordered", false).des("Bordered rect for durability bar").whenTrue(this.duraBar).whenAtMode(this.page, Page.Items);
/* 131 */   Setting<Float> duraBarBorderOffset = setting("DuraBarBorderOffset", 2.0F, 0.0F, 5.0F).des("Bordered durability bar outline offset").whenTrue(this.duraBarBordered).whenTrue(this.duraBar).whenAtMode(this.page, Page.Items);
/* 132 */   Setting<Float> duraBarBorderWidth = setting("DuraBarBorderWidth", 1.2F, 1.0F, 2.0F).des("Bordered durability bar outline width").whenTrue(this.duraBarBordered).whenTrue(this.duraBar).whenAtMode(this.page, Page.Items);
/* 133 */   Setting<ArmorDisplay.RenderMode> armorMode = setting("ArmorMode", (Enum)ArmorDisplay.RenderMode.Image).des("Ways to render armor on nametags").whenAtMode(this.page, Page.Items);
/* 134 */   Setting<Integer> itemRectsWidth = setting("ItemRectsWidth", 16, 1, 30).des("Width of simplified rect render and damage bar for items").only(v -> (this.armorMode.getValue() != ArmorDisplay.RenderMode.None || ((Boolean)this.duraBar.getValue()).booleanValue())).whenAtMode(this.page, Page.Items);
/* 135 */   Setting<Integer> armorRectHeight = setting("ArmorRectHeight", 7, 1, 20).whenAtMode(this.armorMode, (Enum)ArmorDisplay.RenderMode.Simplified).whenAtMode(this.page, Page.Items);
/* 136 */   Setting<Boolean> armorRoundedRect = setting("ArmorRoundedRect", false).des("Rounded rect for non image render mode").whenAtMode(this.armorMode, (Enum)ArmorDisplay.RenderMode.Simplified).whenAtMode(this.page, Page.Items);
/* 137 */   Setting<Float> armorRoundedRectRadius = setting("ArmorRoundedRectRadius", 0.6F, 0.0F, 1.0F).des("Radius of rounded rect").whenTrue(this.armorRoundedRect).whenAtMode(this.armorMode, (Enum)ArmorDisplay.RenderMode.Simplified).whenAtMode(this.page, Page.Items);
/* 138 */   Setting<Boolean> armorRoundedRectTopRight = setting("ArmorRoundedRectTopRight", true).des("Rounded corner for rounded rect top right").whenTrue(this.armorRoundedRect).whenAtMode(this.armorMode, (Enum)ArmorDisplay.RenderMode.Simplified).whenAtMode(this.page, Page.Items);
/* 139 */   Setting<Boolean> armorRoundedRectTopLeft = setting("ArmorRoundedRectTopLeft", true).des("Rounded corner for rounded rect top left").whenTrue(this.armorRoundedRect).whenAtMode(this.armorMode, (Enum)ArmorDisplay.RenderMode.Simplified).whenAtMode(this.page, Page.Items);
/* 140 */   Setting<Boolean> armorRoundedRectDownRight = setting("ArmorRoundedRectDownRight", true).des("Rounded corner for rounded rect bottom right").whenTrue(this.armorRoundedRect).whenAtMode(this.armorMode, (Enum)ArmorDisplay.RenderMode.Simplified).whenAtMode(this.page, Page.Items);
/* 141 */   Setting<Boolean> armorRoundedRectDownLeft = setting("ArmorRoundedRectDownLeft", true).des("Rounded corner for rounded rect bottom left").whenTrue(this.armorRoundedRect).whenAtMode(this.armorMode, (Enum)ArmorDisplay.RenderMode.Simplified).whenAtMode(this.page, Page.Items);
/* 142 */   Setting<Boolean> armorBorderedRect = setting("ArmorBorderedRect", true).des("Bordered rect for non image render mode").whenAtMode(this.armorMode, (Enum)ArmorDisplay.RenderMode.Simplified).whenAtMode(this.page, Page.Items);
/* 143 */   Setting<Float> armorBorderedRectOffset = setting("ArmorBorderedRectOffset", 2.0F, 0.0F, 5.0F).des("Bordered rect outline offset").whenTrue(this.armorBorderedRect).whenAtMode(this.armorMode, (Enum)ArmorDisplay.RenderMode.Simplified).whenAtMode(this.page, Page.Items);
/* 144 */   Setting<Float> armorBorderedRectWidth = setting("ArmorBorderedRectWidth", 1.2F, 1.0F, 2.0F).des("Bordered rect outline width").whenTrue(this.armorBorderedRect).whenAtMode(this.armorMode, (Enum)ArmorDisplay.RenderMode.Simplified).whenAtMode(this.page, Page.Items);
/* 145 */   Setting<Boolean> heldItems = setting("HeldItems", true).des("Renders the items that the entity is holding").whenAtMode(this.page, Page.Items);
/* 146 */   Setting<Boolean> flipHeldItems = setting("FlipHeldItems", false).des("Makes offhand and mainhand render on opposite sides").whenTrue(this.heldItems).whenAtMode(this.page, Page.Items);
/* 147 */   Setting<Float> heldItemsOffsetX = setting("HeldItemsX", -0.75F, -20.0F, 20.0F).des("X offset of held items").whenTrue(this.heldItems).whenAtMode(this.page, Page.Items);
/* 148 */   Setting<Float> heldItemsOffsetY = setting("HeldItemsY", 0.75F, -40.0F, 40.0F).des("Y offset of held items").whenTrue(this.heldItems).whenAtMode(this.page, Page.Items);
/* 149 */   Setting<Boolean> heldItemsStackSize = setting("HeldItemsAmt", true).des("Shows the amount of items in the currently held stack").whenTrue(this.heldItems).whenAtMode(this.page, Page.Items);
/* 150 */   Setting<Float> heldItemsStackSizeOffsetX = setting("HeldItemsAmtX", 0.0F, -20.0F, 20.0F).des("X offset of held items stack size").whenTrue(this.heldItemsStackSize).whenTrue(this.heldItems).whenAtMode(this.page, Page.Items);
/* 151 */   Setting<Float> heldItemsStackSizeOffsetY = setting("HeldItemsAmtY", 0.0F, -40.0F, 40.0F).des("Y offset of held items stack size").whenTrue(this.heldItemsStackSize).whenTrue(this.heldItems).whenAtMode(this.page, Page.Items);
/* 152 */   Setting<Boolean> duraPercent = setting("DuraPercent", false).des("Renders the remaining durability of items as a percent").whenAtMode(this.page, Page.Items);
/* 153 */   Setting<Float> duraPercentOffsetX = setting("DuraPercentX", 4.5F, -20.0F, 20.0F).des("X offset of durability percentage").whenTrue(this.duraPercent).whenAtMode(this.page, Page.Items);
/* 154 */   Setting<Float> duraPercentOffsetY = setting("DuraPercentY", -4.5F, -20.0F, 20.0F).des("Y offset of durability percentage").whenTrue(this.duraPercent).whenAtMode(this.page, Page.Items);
/* 155 */   Setting<Float> duraPercentScale = setting("DuraPercentScale", 0.7F, 0.1F, 2.0F).des("Scale of durability percentage").whenTrue(this.duraPercent).whenAtMode(this.page, Page.Items);
/* 156 */   Setting<Boolean> enchants = setting("Enchantments", true).des("Renders all enchantments on each item").whenAtMode(this.page, Page.Items);
/* 157 */   Setting<Boolean> enchantRenderUp = setting("EnchantRenderUp", true).des("Renders multiple enchantments above each other").whenTrue(this.enchants).whenAtMode(this.page, Page.Items);
/* 158 */   Setting<Float> enchantSeparationOffset = setting("EnchantSeparationOffset", 7.6F, 0.1F, 20.0F).des("Distance between each enchantment").whenTrue(this.enchants).whenAtMode(this.page, Page.Items);
/* 159 */   Setting<Float> enchantScale = setting("EnchantScale", 0.78F, 0.1F, 2.0F).des("Size of item enchantments").whenTrue(this.enchants).whenAtMode(this.page, Page.Items);
/* 160 */   Setting<Float> enchantOffsetX = setting("EnchantmentsX", 1.9F, -20.0F, 20.0F).des("X offset of item enchantments").whenTrue(this.enchants).whenAtMode(this.page, Page.Items);
/* 161 */   Setting<Float> enchantOffsetY = setting("EnchantmentsY", 3.4F, -20.0F, 20.0F).des("Y offset of item enchantments").whenTrue(this.enchants).whenAtMode(this.page, Page.Items);
/* 162 */   Setting<Boolean> heldItemName = setting("HeldItemName", true).des("Draw the name of held item as a string").whenAtMode(this.page, Page.Items);
/* 163 */   Setting<Float> heldItemNameOffsetX = setting("HeldItemNameX", 1.9F, -50.0F, 50.0F).des("X offset of held item name").whenTrue(this.heldItemName).whenAtMode(this.page, Page.Items);
/* 164 */   Setting<Float> heldItemNameOffsetY = setting("HeldItemNameY", -22.6F, -40.0F, 40.0F).des("Y offset of held item name").whenTrue(this.heldItemName).whenAtMode(this.page, Page.Items);
/* 165 */   Setting<Float> heldItemNameScale = setting("HeldItemNameScale", 0.8F, 0.1F, 2.0F).des("Scale of held item name").whenTrue(this.heldItemName).whenAtMode(this.page, Page.Items);
/* 166 */   Setting<Boolean> itemsTextShadow = setting("ItemsTextShadow", true).des("Draw text shadow on items text").only(v -> (((Boolean)this.enchants.getValue()).booleanValue() || ((Boolean)this.duraPercent.getValue()).booleanValue() || ((Boolean)this.heldItemName.getValue()).booleanValue())).whenAtMode(this.page, Page.Items);
/*     */   
/* 168 */   Setting<Color> bgRectColor = setting("BGRectColor", new Color((new Color(0, 0, 0, 50)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 0, 0, 0, 50)).whenAtMode(this.page, Page.Colors);
/* 169 */   Setting<Color> bgRectBorderColor = setting("BGRectBorderColor", new Color((new Color(20, 20, 150, 100)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 20, 20, 150, 100)).whenTrue(this.rectBorder).whenAtMode(this.page, Page.Colors);
/* 170 */   Setting<Color> nameColor = setting("NameColor", new Color((new Color(255, 255, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 255)).whenAtMode(this.page, Page.Colors);
/* 171 */   Setting<Color> friendColor = setting("FriendColor", new Color((new Color(50, 255, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 255, 255)).whenAtMode(this.page, Page.Colors);
/* 172 */   Setting<Color> enemyColor = setting("EnemyColor", new Color((new Color(255, 50, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 255)).whenAtMode(this.page, Page.Colors);
/* 173 */   Setting<Color> crouchColor = setting("CrouchColor", new Color((new Color(255, 150, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 150, 50, 255)).whenAtMode(this.page, Page.Colors);
/* 174 */   Setting<Color> pingColorGood = setting("PingColorGood", new Color((new Color(50, 255, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 255)).only(v -> (this.ping.getValue() != TextMode.None)).whenAtMode(this.page, Page.Colors);
/* 175 */   Setting<Color> pingColorBad = setting("PingColorBad", new Color((new Color(255, 50, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 255)).only(v -> (this.ping.getValue() != TextMode.None)).whenAtMode(this.page, Page.Colors);
/* 176 */   Setting<Color> healthColorMax = setting("HealthColorMax", new Color((new Color(50, 255, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 255)).only(v -> (this.health.getValue() != TextMode.None)).whenAtMode(this.page, Page.Colors);
/* 177 */   Setting<Color> healthColorDead = setting("HealthColorDead", new Color((new Color(255, 50, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 255)).only(v -> (this.health.getValue() != TextMode.None)).whenAtMode(this.page, Page.Colors);
/* 178 */   Setting<Color> popColor = setting("PopColor", new Color((new Color(175, 80, 80, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 175, 80, 80, 255)).only(v -> (this.popCount.getValue() != TextMode.None)).whenAtMode(this.page, Page.Colors);
/* 179 */   Setting<Color> itemCountColor = setting("ItemCountColor", new Color((new Color(255, 255, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 255)).whenTrue(this.items).only(v -> (this.itemCount.getValue() != TextMode.None)).whenAtMode(this.page, Page.Colors);
/* 180 */   Setting<Color> healthBarColor = setting("HealthBarColor", new Color((new Color(50, 255, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 255)).whenTrue(this.healthBar).whenAtMode(this.page, Page.Colors);
/* 181 */   Setting<Color> healthBar2Color = setting("HealthBar2Color", new Color((new Color(255, 50, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 255)).whenTrue(this.healthBarColorShift).whenTrue(this.healthBar).whenAtMode(this.page, Page.Colors);
/* 182 */   Setting<Color> healthBarAbsorptionColor = setting("HBAbsorptionColor", new Color((new Color(255, 255, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 50, 255)).whenTrue(this.healthBar).whenAtMode(this.page, Page.Colors);
/* 183 */   Setting<Color> healthBar2AbsorptionColor = setting("HBAbsorption2Color", new Color((new Color(100, 100, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 100, 100, 50, 255)).whenTrue(this.healthBarAbsorptionColorShift).whenTrue(this.healthBar).whenAtMode(this.page, Page.Colors);
/* 184 */   Setting<Color> duraColor = setting("DuraColor", new Color((new Color(50, 255, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 50, 255, 50, 255)).only(v -> (((Boolean)this.duraBar.getValue()).booleanValue() || ((Boolean)this.duraPercent.getValue()).booleanValue())).whenAtMode(this.page, Page.Colors);
/* 185 */   Setting<Color> duraColor2 = setting("DuraColor2", new Color((new Color(255, 50, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 255)).only(v -> (((Boolean)this.duraBar.getValue()).booleanValue() || ((Boolean)this.duraPercent.getValue()).booleanValue())).whenTrue(this.duraColorShift).whenAtMode(this.page, Page.Colors);
/* 186 */   Setting<Color> duraBarBGColor = setting("DuraBarBGColor", new Color((new Color(20, 20, 20, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 20, 20, 20, 255)).whenTrue(this.duraBar).whenAtMode(this.page, Page.Colors);
/* 187 */   Setting<Color> heldItemNameColor = setting("HeldItemNameColor", new Color((new Color(255, 255, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 255)).whenTrue(this.heldItemName).whenAtMode(this.page, Page.Colors);
/* 188 */   Setting<Color> enchantmentColor = setting("EnchantmentTextColor", new Color((new Color(255, 255, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 255)).whenTrue(this.enchants).whenAtMode(this.page, Page.Colors);
/* 189 */   Setting<Color> enchantmentCurseColor = setting("EnchantmentCurseTextColor", new Color((new Color(255, 50, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 50, 50, 255)).whenTrue(this.enchants).whenAtMode(this.page, Page.Colors);
/* 190 */   Setting<Color> heldItemStackSizeColor = setting("HeldItemAmtColor", new Color((new Color(255, 255, 255, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 255, 255, 255, 255)).whenTrue(this.heldItems).whenTrue(this.heldItemsStackSize).whenAtMode(this.page, Page.Colors);
/*     */   
/* 192 */   Setting<Color> elytraColor = setting("ElytraColor", new Color((new Color(150, 100, 150, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 150, 100, 150, 255)).whenAtMode(this.armorMode, (Enum)ArmorDisplay.RenderMode.Simplified).whenAtMode(this.page, Page.Colors);
/* 193 */   Setting<Color> diamondColor = setting("DiamondColor", new Color((new Color(53, 200, 200, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 53, 175, 175, 255)).whenAtMode(this.armorMode, (Enum)ArmorDisplay.RenderMode.Simplified).whenAtMode(this.page, Page.Colors);
/* 194 */   Setting<Color> ironColor = setting("IronColor", new Color((new Color(200, 200, 200, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 200, 200, 200, 255)).whenAtMode(this.armorMode, (Enum)ArmorDisplay.RenderMode.Simplified).whenAtMode(this.page, Page.Colors);
/* 195 */   Setting<Color> goldColor = setting("GoldColor", new Color((new Color(200, 200, 50, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 200, 200, 50, 255)).whenAtMode(this.armorMode, (Enum)ArmorDisplay.RenderMode.Simplified).whenAtMode(this.page, Page.Colors);
/* 196 */   Setting<Color> chainMailColor = setting("ChainmailColor", new Color((new Color(150, 150, 150, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 150, 150, 150, 255)).whenAtMode(this.armorMode, (Enum)ArmorDisplay.RenderMode.Simplified).whenAtMode(this.page, Page.Colors);
/* 197 */   Setting<Color> leatherColor = setting("LeatherColor", new Color((new Color(110, 75, 0, 255)).getRGB(), false, false, 1.0F, 0.75F, 0.9F, 110, 75, 0, 255)).whenAtMode(this.armorMode, (Enum)ArmorDisplay.RenderMode.Simplified).whenAtMode(this.page, Page.Colors);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Nametags() {
/* 203 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRenderWorld(RenderEvent event) {
/* 208 */     EntityUtil.entitiesListFlag = true;
/* 209 */     mc.field_71441_e.field_72996_f.stream()
/* 210 */       .filter(e -> (e != mc.field_71439_g && !e.func_70005_c_().equals(mc.field_71439_g.func_70005_c_())))
/* 211 */       .filter(e -> (EntityUtil.getInterpDistance(mc.func_184121_ak(), (Entity)mc.field_71439_g, e) <= ((Float)this.range.getValue()).floatValue()))
/* 212 */       .filter(e -> !e.field_70128_L)
/* 213 */       .filter(e -> RenderHelper.isInViewFrustrum(e.func_174813_aQ().func_72317_d(0.0D, ((Float)this.yOffset.getValue()).floatValue(), 0.0D)))
/* 214 */       .sorted(Comparator.comparing(entity -> Float.valueOf(-mc.func_175606_aa().func_70032_d(entity))))
/* 215 */       .forEach(entity -> {
/*     */           if (((Boolean)this.healthBar.getValue()).booleanValue() && ((Boolean)this.healthBarInterp.getValue()).booleanValue() && (float)EntityUtil.getInterpDistance(mc.func_184121_ak(), (Entity)mc.field_71439_g, entity) > ((Float)this.range.getValue()).floatValue()) {
/*     */             this.prevHealthMap.remove(entity);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             this.interpProgressMap.remove(entity);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             this.prevHealthAbsorptionMap.remove(entity);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             this.interpProgressAbsorptionMap.remove(entity);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*     */           if (entity instanceof EntityPlayer && ((Boolean)this.players.getValue()).booleanValue()) {
/*     */             Integer pops = (((EntityPlayer)entity).func_110143_aJ() <= 0.0F) ? (Integer)PopManager.deathPopMap.get(entity) : (Integer)PopManager.popMap.get(entity);
/*     */ 
/*     */ 
/*     */             
/*     */             drawNametag(entity, entity.func_70005_c_(), (mc.func_147114_u().func_175102_a(entity.func_110124_au()) == null) ? 0 : mc.func_147114_u().func_175102_a(entity.func_110124_au()).func_178853_c(), ((EntityLivingBase)entity).func_110143_aJ(), (pops == null) ? 0 : pops.intValue(), ((EntityPlayer)entity).func_110139_bj(), false, false);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/*     */           if ((EntityUtil.isEntityMob(entity) && ((Boolean)this.mobs.getValue()).booleanValue()) || (EntityUtil.isEntityAnimal(entity) && ((Boolean)this.animals.getValue()).booleanValue())) {
/*     */             drawNametag(entity, entity.func_70005_c_(), 0, ((EntityLivingBase)entity).func_110143_aJ(), 0, 0.0F, true, false);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           if (entity instanceof EntityItem && ((Boolean)this.items.getValue()).booleanValue()) {
/*     */             drawNametag(entity, entity.func_70005_c_(), 0, 0.0F, 0, 0.0F, false, true);
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 260 */     EntityUtil.entitiesListFlag = false;
/*     */     
/* 262 */     GL11.glDisable(3553);
/* 263 */     GL11.glDisable(3008);
/*     */     
/* 265 */     if (((Boolean)this.healthBar.getValue()).booleanValue() && ((Boolean)this.healthBarInterp.getValue()).booleanValue() && !this.interpProgressMap.isEmpty()) {
/* 266 */       int passedms = (int)this.timer.hasPassed();
/* 267 */       if (passedms < 1000) {
/* 268 */         for (Map.Entry<Entity, Integer> entry : this.interpProgressMap.entrySet()) {
/* 269 */           int i = ((Integer)entry.getValue()).intValue();
/* 270 */           i = (int)(i + ((Float)this.healthBarInterpFactor.getValue()).floatValue() / 10.0F * passedms);
/*     */           
/* 272 */           if (i > 300) {
/* 273 */             i = 300;
/*     */           }
/*     */           
/* 276 */           this.interpProgressMap.put(entry.getKey(), Integer.valueOf(i));
/*     */         } 
/*     */         
/* 279 */         for (Map.Entry<Entity, Integer> entry : this.interpProgressAbsorptionMap.entrySet()) {
/* 280 */           int i = ((Integer)entry.getValue()).intValue();
/* 281 */           i = (int)(i + ((Float)this.healthBarInterpFactor.getValue()).floatValue() / 10.0F * passedms);
/*     */           
/* 283 */           if (i > 300) {
/* 284 */             i = 300;
/*     */           }
/*     */           
/* 287 */           this.interpProgressAbsorptionMap.put(entry.getKey(), Integer.valueOf(i));
/*     */         } 
/*     */       } 
/* 290 */       this.timer.reset();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawNametag(Entity entity, String name, int ping, float health, int pops, float absorption, boolean isNonPlayerLiving, boolean isItem) {
/* 295 */     Vec3d entityVec = EntityUtil.getInterpolatedEntityPos(entity, mc.func_184121_ak());
/* 296 */     float dist = (float)EntityUtil.getInterpDistance(mc.func_184121_ak(), mc.field_175622_Z, entity);
/* 297 */     float scale = 0.0018F + ((Float)this.size.getValue()).floatValue() + dist * this.theFuckingScaleIllFixThisLator;
/* 298 */     float roundedHealth = (float)(Math.ceil(((health + absorption) * 2.0F)) / 2.0D);
/*     */     
/* 300 */     if (dist < ((Float)this.innerLockRange.getValue()).floatValue()) {
/* 301 */       scale = 0.0018F + ((Float)this.size.getValue()).floatValue() + ((Float)this.innerLockRange.getValue()).floatValue() * this.theFuckingScaleIllFixThisLator;
/*     */     }
/* 303 */     else if (dist > ((Float)this.outerLockRange.getValue()).floatValue()) {
/* 304 */       scale = 0.0018F + ((Float)this.size.getValue()).floatValue() + ((Float)this.outerLockRange.getValue()).floatValue() * this.theFuckingScaleIllFixThisLator;
/*     */     } 
/*     */     
/* 307 */     float spaceIndex = 0.0F;
/*     */     
/* 309 */     if (this.ping.getValue() != TextMode.None) spaceIndex++; 
/* 310 */     if (this.health.getValue() != TextMode.None) spaceIndex++; 
/* 311 */     if (this.popCount.getValue() != TextMode.None) spaceIndex++;
/*     */     
/* 313 */     float width = spaceIndex * ((Float)this.textSpace.getValue()).floatValue() + getWidthNametags(name + ((this.ping.getValue() != TextMode.None) ? (ping + " ms") : "") + ((this.health.getValue() != TextMode.None) ? (roundedHealth + "") : "") + ((this.popCount.getValue() != TextMode.None) ? ("[" + pops + "]") : ""));
/*     */     
/* 315 */     if (isNonPlayerLiving) {
/* 316 */       width = ((this.health.getValue() != TextMode.None) ? ((Float)this.textSpace.getValue()).floatValue() : 0.0F) + getWidthNametags(name + ((this.health.getValue() != TextMode.None) ? (roundedHealth + "") : ""));
/*     */     }
/*     */     
/* 319 */     if (isItem) {
/* 320 */       width = ((this.itemCount.getValue() != TextMode.None) ? ((Float)this.textSpace.getValue()).floatValue() : 0.0F) + getWidthNametags(((EntityItem)entity).func_92059_d().func_82833_r() + ((this.itemCount.getValue() != TextMode.None) ? ("x" + (((EntityItem)entity).func_92059_d()).field_77994_a) : ""));
/*     */     }
/*     */     
/* 323 */     float xOffsetRect = -width / 2.0F;
/*     */     
/* 325 */     GL11.glPushMatrix();
/* 326 */     CrystalUtil.glBillboard((float)entityVec.field_72450_a, (float)((entity.func_174813_aQ()).field_72337_e - (entity.func_174813_aQ()).field_72338_b + entityVec.field_72448_b + ((Float)this.yOffset.getValue()).floatValue()), (float)entityVec.field_72449_c);
/* 327 */     GL11.glTranslatef(0.0F, ((Float)this.rectHeight.getValue()).floatValue() * (1.0F - scale), 0.0F);
/* 328 */     GL11.glScalef(scale, scale, scale);
/*     */     
/* 330 */     RenderUtils2D.prepareGl();
/* 331 */     if (((Boolean)this.shadow.getValue()).booleanValue()) {
/* 332 */       RenderUtils2D.drawBetterRoundRectFade(xOffsetRect - ((Float)this.rectWidth.getValue()).floatValue(), 0.0F, xOffsetRect + width + ((Float)this.rectWidth.getValue()).floatValue(), ((Float)this.rectHeight.getValue()).floatValue(), ((Float)this.shadowSize.getValue()).floatValue(), 30.0F, false, ((Boolean)this.shadowCenterRect.getValue()).booleanValue(), false, (new Color(0, 0, 0, ((Integer)this.shadowAlpha.getValue()).intValue())).getRGB());
/*     */     }
/*     */     
/* 335 */     drawRects(entity, xOffsetRect, width, ping, health, pops, absorption);
/* 336 */     RenderUtils2D.releaseGl();
/*     */     
/* 338 */     Color color = FriendManager.isFriend(entity) ? ((Color)this.friendColor.getValue()).getColorColor() : (EnemyManager.isEnemy(entity) ? ((Color)this.enemyColor.getValue()).getColorColor() : ((Color)this.nameColor.getValue()).getColorColor());
/*     */     
/* 340 */     if (entity.func_70093_af()) {
/* 341 */       color = ((Color)this.crouchColor.getValue()).getColorColor();
/*     */     }
/*     */     
/* 344 */     float yOffset = ((Float)this.rectHeight.getValue()).floatValue() / 2.0F - ((Float)this.textYOffset.getValue()).floatValue();
/* 345 */     float xOffset = ((this.popCount.getValue() == TextMode.Left && !isNonPlayerLiving && !isItem) ? (getWidthNametags("[" + pops + "]") + ((Float)this.textSpace.getValue()).floatValue()) : 0.0F) + ((this.health.getValue() == TextMode.Left && !isItem) ? (getWidthNametags(roundedHealth + "") + ((Float)this.textSpace.getValue()).floatValue()) : 0.0F) + ((this.ping.getValue() == TextMode.Left && !isNonPlayerLiving && !isItem) ? (getWidthNametags(ping + " ms") + ((Float)this.textSpace.getValue()).floatValue()) : 0.0F) + ((this.itemCount.getValue() == TextMode.Left && isItem) ? (getWidthNametags("x" + (((EntityItem)entity).func_92059_d()).field_77994_a) + ((Float)this.textSpace.getValue()).floatValue()) : 0.0F);
/* 346 */     xOffset -= ((isNonPlayerLiving ? ((this.health.getValue() != TextMode.None) ? ((Float)this.textSpace.getValue()).floatValue() : 0.0F) : (isItem ? ((this.itemCount.getValue() != TextMode.None) ? ((Float)this.textSpace.getValue()).floatValue() : 0.0F) : (spaceIndex * ((Float)this.textSpace.getValue()).floatValue()))) + getWidthNametags((isItem ? ((EntityItem)entity).func_92059_d().func_82833_r() : name) + ((this.ping.getValue() != TextMode.None && !isNonPlayerLiving && !isItem) ? (ping + " ms") : "") + ((this.health.getValue() != TextMode.None && !isItem) ? (roundedHealth + "") : "") + ((this.popCount.getValue() != TextMode.None && !isNonPlayerLiving && !isItem) ? ("[" + pops + "]") : "") + ((this.itemCount.getValue() != TextMode.None && isItem) ? ("x" + (((EntityItem)entity).func_92059_d()).field_77994_a) : ""))) / 2.0F;
/*     */     
/* 348 */     drawItems(entity, isItem);
/*     */     
/* 350 */     if (this.itemCount.getValue() == TextMode.Left && isItem) drawString("x" + (((EntityItem)entity).func_92059_d()).field_77994_a, xOffset - ((Float)this.textSpace.getValue()).floatValue() - getWidthNametags("x" + (((EntityItem)entity).func_92059_d()).field_77994_a), yOffset, ((Boolean)this.textShadow.getValue()).booleanValue(), ((Color)this.itemCountColor.getValue()).getColor()); 
/* 351 */     if (this.popCount.getValue() == TextMode.Left && !isNonPlayerLiving && !isItem) drawString("[" + pops + "]", xOffset + ((this.health.getValue() == TextMode.Left) ? (-getWidthNametags(roundedHealth + "") - ((Float)this.textSpace.getValue()).floatValue()) : 0.0F) - ((this.ping.getValue() == TextMode.Left) ? (getWidthNametags(ping + " ms") + ((Float)this.textSpace.getValue()).floatValue()) : 0.0F) - ((Float)this.textSpace.getValue()).floatValue() - getWidthNametags("[" + pops + "]"), yOffset, ((Boolean)this.textShadow.getValue()).booleanValue(), ((Color)this.popColor.getValue()).getColor()); 
/* 352 */     if (this.health.getValue() == TextMode.Left && !isItem) drawString(roundedHealth + "", xOffset + ((this.ping.getValue() == TextMode.Left) ? (-getWidthNametags(ping + " ms") - ((Float)this.textSpace.getValue()).floatValue()) : 0.0F) - ((Float)this.textSpace.getValue()).floatValue() - getWidthNametags(roundedHealth + ""), yOffset, ((Boolean)this.textShadow.getValue()).booleanValue(), ColorUtil.colorShift(((Color)this.healthColorDead.getValue()).getColorColor(), ((Color)this.healthColorMax.getValue()).getColorColor(), health / ((EntityLivingBase)entity).func_110138_aP() * 300.0F).getRGB()); 
/* 353 */     if (this.ping.getValue() == TextMode.Left && !isNonPlayerLiving && !isItem) drawString(ping + " ms", xOffset - ((Float)this.textSpace.getValue()).floatValue() - getWidthNametags(ping + " ms"), yOffset, ((Boolean)this.textShadow.getValue()).booleanValue(), ColorUtil.colorShift(((Color)this.pingColorGood.getValue()).getColorColor(), ((Color)this.pingColorBad.getValue()).getColorColor(), MathUtilFuckYou.clamp(ping / 250.0F, 0.0F, 1.0F) * 300.0F).getRGB()); 
/* 354 */     drawString(isItem ? ((EntityItem)entity).func_92059_d().func_82833_r() : name, xOffset, yOffset, ((Boolean)this.textShadow.getValue()).booleanValue(), color.getRGB());
/* 355 */     if (this.ping.getValue() == TextMode.Right && !isNonPlayerLiving && !isItem) drawString(ping + " ms", xOffset + getWidthNametags(name) + ((Float)this.textSpace.getValue()).floatValue(), yOffset, ((Boolean)this.textShadow.getValue()).booleanValue(), ColorUtil.colorShift(((Color)this.pingColorGood.getValue()).getColorColor(), ((Color)this.pingColorBad.getValue()).getColorColor(), MathUtilFuckYou.clamp(ping / 250.0F, 0.0F, 1.0F) * 300.0F).getRGB()); 
/* 356 */     if (this.health.getValue() == TextMode.Right && !isItem) drawString(roundedHealth + "", xOffset + ((this.ping.getValue() == TextMode.Right) ? (getWidthNametags(ping + " ms") + ((Float)this.textSpace.getValue()).floatValue()) : 0.0F) + getWidthNametags(name) + ((Float)this.textSpace.getValue()).floatValue(), yOffset, ((Boolean)this.textShadow.getValue()).booleanValue(), ColorUtil.colorShift(((Color)this.healthColorDead.getValue()).getColorColor(), ((Color)this.healthColorMax.getValue()).getColorColor(), health / ((EntityLivingBase)entity).func_110138_aP() * 300.0F).getRGB()); 
/* 357 */     if (this.popCount.getValue() == TextMode.Right && !isNonPlayerLiving && !isItem) drawString("[" + pops + "]", xOffset + ((this.health.getValue() == TextMode.Right) ? (getWidthNametags(roundedHealth + "") + ((Float)this.textSpace.getValue()).floatValue()) : 0.0F) + ((this.ping.getValue() == TextMode.Right) ? (getWidthNametags(ping + " ms") + ((Float)this.textSpace.getValue()).floatValue()) : 0.0F) + getWidthNametags(name) + ((Float)this.textSpace.getValue()).floatValue(), yOffset, ((Boolean)this.textShadow.getValue()).booleanValue(), ((Color)this.popColor.getValue()).getColor()); 
/* 358 */     if (this.itemCount.getValue() == TextMode.Right && isItem) drawString("x" + (((EntityItem)entity).func_92059_d()).field_77994_a, xOffset + getWidthNametags(((EntityItem)entity).func_92059_d().func_82833_r()) + ((Float)this.textSpace.getValue()).floatValue(), yOffset, ((Boolean)this.textShadow.getValue()).booleanValue(), ((Color)this.itemCountColor.getValue()).getColor());
/*     */     
/* 360 */     GL11.glScalef(1.0F / scale, 1.0F / scale, 1.0F / scale);
/* 361 */     GL11.glTranslatef(0.0F, ((Float)this.rectHeight.getValue()).floatValue() * -(1.0F - scale), 0.0F);
/* 362 */     GL11.glPopMatrix();
/* 363 */     GlStateManager.func_179147_l();
/*     */   }
/*     */   
/*     */   private void drawRects(Entity entity, float startX, float width, float ping, float health, int pops, float absorption) {
/* 367 */     drawRect(((Boolean)this.roundedRect.getValue()).booleanValue(), ((Boolean)this.roundedRectTopRight.getValue()).booleanValue(), ((Boolean)this.roundedRectTopLeft.getValue()).booleanValue(), ((Boolean)this.roundedRectDownRight.getValue()).booleanValue(), ((Boolean)this.roundedRectDownLeft.getValue()).booleanValue(), ((Float)this.roundedRectRadius.getValue()).floatValue(), ((Boolean)this.rectBorder
/* 368 */         .getValue()).booleanValue(), ((Float)this.rectBorderWidth.getValue()).floatValue(), ((Float)this.rectBorderOffset.getValue()).floatValue(), startX - ((Float)this.rectWidth
/* 369 */         .getValue()).floatValue(), 0.0F, width + ((Float)this.rectWidth.getValue()).floatValue() * 2.0F, ((Float)this.rectHeight.getValue()).floatValue(), ((Color)this.bgRectColor.getValue()).getColor(), ((Color)this.bgRectBorderColor.getValue()).getColor());
/*     */     
/* 371 */     if (((Boolean)this.healthBar.getValue()).booleanValue() && entity instanceof EntityLivingBase) {
/* 372 */       Color hbColor = ((Color)this.healthBarColor.getValue()).getColorColor();
/* 373 */       Color hbAbsorptionColor = ((Color)this.healthBarAbsorptionColor.getValue()).getColorColor();
/* 374 */       float healthBarFactor = health / ((EntityLivingBase)entity).func_110138_aP();
/* 375 */       float absorptionBarFactor = MathUtilFuckYou.clamp(absorption / 16.0F, 0.0F, 1.0F);
/*     */       
/* 377 */       if (((Boolean)this.healthBarInterp.getValue()).booleanValue()) {
/* 378 */         if (this.interpProgressMap.get(entity) != null && this.prevHealthMap.get(entity) != null) {
/* 379 */           healthBarFactor = MathUtilFuckYou.clamp(((Float)this.prevHealthMap.get(entity)).floatValue() + ((Integer)this.interpProgressMap.get(entity)).intValue() * (healthBarFactor - ((Float)this.prevHealthMap.get(entity)).floatValue()) / 300.0F, 0.0F, 1.0F);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 384 */         if (this.interpProgressAbsorptionMap.get(entity) != null && this.prevHealthAbsorptionMap.get(entity) != null) {
/* 385 */           absorptionBarFactor = MathUtilFuckYou.clamp(((Float)this.prevHealthAbsorptionMap.get(entity)).floatValue() + ((Integer)this.interpProgressAbsorptionMap.get(entity)).intValue() * (absorptionBarFactor - ((Float)this.prevHealthAbsorptionMap.get(entity)).floatValue()) / 300.0F, 0.0F, 1.0F);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 390 */         if (healthBarFactor * (width + ((Float)this.rectWidth.getValue()).floatValue() * 2.0F) != ((this.prevHealthMap.get(entity) == null) ? 99999.0F : ((Float)this.prevHealthMap.get(entity)).floatValue())) {
/* 391 */           this.interpProgressMap.put(entity, Integer.valueOf(0));
/* 392 */           this.prevHealthMap.put(entity, Float.valueOf(healthBarFactor));
/*     */         } 
/*     */         
/* 395 */         if (absorptionBarFactor * (width + ((Float)this.rectWidth.getValue()).floatValue() * 2.0F) != ((this.prevHealthAbsorptionMap.get(entity) == null) ? 99999.0F : ((Float)this.prevHealthAbsorptionMap.get(entity)).floatValue())) {
/* 396 */           this.interpProgressAbsorptionMap.put(entity, Integer.valueOf(0));
/* 397 */           this.prevHealthAbsorptionMap.put(entity, Float.valueOf(absorptionBarFactor));
/*     */         } 
/*     */       } 
/*     */       
/* 401 */       float healthBarWidth = healthBarFactor * (width + ((Float)this.rectWidth.getValue()).floatValue() * 2.0F);
/* 402 */       float absorptionBarWidth = absorptionBarFactor * (width + ((Float)this.rectWidth.getValue()).floatValue() * 2.0F);
/*     */       
/* 404 */       if (((Boolean)this.healthBarColorShift.getValue()).booleanValue()) {
/* 405 */         hbColor = ColorUtil.colorShift(((Color)this.healthBar2Color.getValue()).getColorColor(), hbColor, healthBarFactor * 300.0F);
/*     */       }
/*     */       
/* 408 */       if (((Boolean)this.healthBarAbsorptionColorShift.getValue()).booleanValue()) {
/* 409 */         hbAbsorptionColor = ColorUtil.colorShift(((Color)this.healthBar2AbsorptionColor.getValue()).getColorColor(), hbAbsorptionColor, absorptionBarFactor * 300.0F);
/*     */       }
/*     */       
/* 412 */       if (absorptionBarWidth > 0.0F) {
/* 413 */         drawRect(((Boolean)this.roundedHealthBarAbsorption.getValue()).booleanValue(), ((Boolean)this.roundedHealthBarAbsorptionTopRight.getValue()).booleanValue(), ((Boolean)this.roundedHealthBarAbsorptionTopLeft.getValue()).booleanValue(), ((Boolean)this.roundedHealthBarAbsorptionDownRight.getValue()).booleanValue(), ((Boolean)this.roundedHealthBarAbsorptionDownLeft.getValue()).booleanValue(), ((Float)this.roundedHealthBarAbsorptionRadius.getValue()).floatValue(), ((Boolean)this.healthBarBorder.getValue()).booleanValue(), ((Float)this.healthBarBorderWidth.getValue()).floatValue(), ((Float)this.healthBarBorderOffset.getValue()).floatValue(), startX - ((Float)this.rectWidth.getValue()).floatValue(), ((Float)this.rectHeight.getValue()).floatValue() - ((Float)this.healthBarOffset.getValue()).floatValue() - ((Float)this.healthBarAbsorptionOffset.getValue()).floatValue(), absorptionBarWidth, ((Float)this.healthBarThickness.getValue()).floatValue(), hbAbsorptionColor.getRGB(), hbAbsorptionColor.getRGB());
/*     */       }
/* 415 */       drawRect(((Boolean)this.roundedHealthBar.getValue()).booleanValue(), ((Boolean)this.roundedHealthBarTopRight.getValue()).booleanValue(), ((Boolean)this.roundedHealthBarTopLeft.getValue()).booleanValue(), ((Boolean)this.roundedHealthBarDownRight.getValue()).booleanValue(), ((Boolean)this.roundedHealthBarDownLeft.getValue()).booleanValue(), ((Float)this.roundedHealthBarRadius.getValue()).floatValue(), ((Boolean)this.healthBarBorder.getValue()).booleanValue(), ((Float)this.healthBarBorderWidth.getValue()).floatValue(), ((Float)this.healthBarBorderOffset.getValue()).floatValue(), startX - ((Float)this.rectWidth.getValue()).floatValue(), ((Float)this.rectHeight.getValue()).floatValue() - ((Float)this.healthBarOffset.getValue()).floatValue(), healthBarWidth, ((Float)this.healthBarThickness.getValue()).floatValue(), hbColor.getRGB(), hbColor.getRGB());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawRect(boolean rounded, boolean arcTopRight, boolean arcTopLeft, boolean arcDownRight, boolean arcDownLeft, float roundedRadiusFactor, boolean bordered, float borderWidth, float borderOffset, float x, float y, float width, float height, int color, int borderColor) {
/* 422 */     if (bordered) {
/* 423 */       if (rounded) {
/* 424 */         RenderUtils2D.drawCustomRoundedRectOutline(x, y, x + width, y + height, roundedRadiusFactor, borderWidth, arcTopRight, arcTopLeft, arcDownRight, arcDownLeft, false, false, borderColor);
/* 425 */         RenderUtils2D.drawRoundedRect(x + borderOffset / 2.0F, y + borderOffset / 2.0F, roundedRadiusFactor, x + width - borderOffset / 2.0F, y + height - borderOffset / 2.0F, false, arcTopRight, arcTopLeft, arcDownRight, arcDownLeft, color);
/*     */       } else {
/*     */         
/* 428 */         RenderUtils2D.drawRectOutline(x, y, x + width, y + height, borderWidth, borderColor, false, false);
/* 429 */         RenderUtils2D.drawRect(x + borderOffset / 2.0F, y + borderOffset / 2.0F, x + width - borderOffset / 2.0F, y + height - borderOffset / 2.0F, color);
/*     */       }
/*     */     
/*     */     }
/* 433 */     else if (rounded) {
/* 434 */       RenderUtils2D.drawRoundedRect(x, y, roundedRadiusFactor, x + width, y + height, false, arcTopRight, arcTopLeft, arcDownRight, arcDownLeft, color);
/*     */     } else {
/*     */       
/* 437 */       RenderUtils2D.drawRect(x, y, x + width, y + height, color);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawString(String str, float x, float y, boolean shadow, int color) {
/* 443 */     if (this.font.getValue() == CustomFont.FontMode.Minecraft) {
/* 444 */       GlStateManager.func_179147_l();
/* 445 */       GlStateManager.func_179141_d();
/* 446 */       GL11.glEnable(3553);
/* 447 */       mc.field_71466_p.func_175065_a(str, x, y, color, shadow);
/* 448 */       GL11.glDisable(3553);
/*     */     
/*     */     }
/* 451 */     else if (shadow) {
/* 452 */       switch ((CustomFont.FontMode)this.font.getValue()) {
/*     */         case Image:
/* 454 */           FontManager.fontObjectivityRenderer.drawStringWithShadow(str, x, y, color);
/*     */           break;
/*     */ 
/*     */         
/*     */         case Simplified:
/* 459 */           FontManager.fontRenderer.drawStringWithShadow(str, x, y, color);
/*     */           break;
/*     */ 
/*     */         
/*     */         case null:
/* 464 */           FontManager.fontArialRenderer.drawStringWithShadow(str, x, (y - 2.0F), color);
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } else {
/* 470 */       switch ((CustomFont.FontMode)this.font.getValue()) {
/*     */         case Image:
/* 472 */           FontManager.fontObjectivityRenderer.drawString(str, x, y, color);
/*     */           break;
/*     */ 
/*     */         
/*     */         case Simplified:
/* 477 */           FontManager.fontRenderer.drawString(str, x, y, color);
/*     */           break;
/*     */ 
/*     */         
/*     */         case null:
/* 482 */           FontManager.fontArialRenderer.drawString(str, x, y - 2.0F, color);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawItems(Entity entity, boolean isItem) {
/* 491 */     if (!(entity instanceof EntityLivingBase) || isItem || (this.armorMode.getValue() == ArmorDisplay.RenderMode.None && !((Boolean)this.heldItems.getValue()).booleanValue() && !((Boolean)this.enchants.getValue()).booleanValue() && !((Boolean)this.duraPercent.getValue()).booleanValue() && !((Boolean)this.duraBar.getValue()).booleanValue())) {
/*     */       return;
/*     */     }
/* 494 */     GlStateManager.func_179132_a(true);
/* 495 */     GL11.glScalef(((Float)this.itemsScale.getValue()).floatValue(), ((Float)this.itemsScale.getValue()).floatValue(), ((Float)this.itemsScale.getValue()).floatValue());
/*     */     
/* 497 */     GL11.glTranslatef(-((Integer)this.separationDistItems.getValue()).intValue() * 2.0F, 0.0F, 0.0F);
/* 498 */     int x = ((Integer)this.separationDistItems.getValue()).intValue() * 3;
/* 499 */     for (ItemStack armorItem : entity.func_184193_aE()) {
/* 500 */       if (armorItem.func_77973_b() != Items.field_190931_a) {
/* 501 */         Color color; switch ((ArmorDisplay.RenderMode)this.armorMode.getValue()) {
/*     */           case Image:
/* 503 */             GL11.glEnable(3553);
/* 504 */             GlStateManager.func_179126_j();
/* 505 */             GL11.glTranslatef(0.0F, 0.0F, -150.5F);
/* 506 */             GL11.glDepthRange(0.0D, 0.01D);
/* 507 */             mc.func_175599_af().func_180450_b(armorItem, x, -((Integer)this.itemsOffsetY.getValue()).intValue());
/* 508 */             GL11.glDepthRange(0.0D, 1.0D);
/* 509 */             GL11.glTranslatef(0.0F, 0.0F, 150.5F);
/* 510 */             GlStateManager.func_179097_i();
/* 511 */             GL11.glDisable(3553);
/*     */             break;
/*     */ 
/*     */           
/*     */           case Simplified:
/* 516 */             color = ArmorDisplay.INSTANCE.getSimplifiedArmorColor(armorItem.func_77973_b(), (Color)this.leatherColor.getValue(), (Color)this.chainMailColor.getValue(), (Color)this.goldColor.getValue(), (Color)this.ironColor.getValue(), (Color)this.diamondColor.getValue(), (Color)this.elytraColor.getValue());
/* 517 */             GL11.glDisable(3553);
/*     */             
/* 519 */             RenderUtils2D.prepareGl();
/* 520 */             drawRect(((Boolean)this.armorRoundedRect.getValue()).booleanValue(), ((Boolean)this.armorRoundedRectTopRight.getValue()).booleanValue(), ((Boolean)this.armorRoundedRectTopLeft.getValue()).booleanValue(), ((Boolean)this.armorRoundedRectDownRight.getValue()).booleanValue(), ((Boolean)this.armorRoundedRectDownLeft.getValue()).booleanValue(), ((Float)this.armorRoundedRectRadius.getValue()).floatValue(), ((Boolean)this.armorBorderedRect.getValue()).booleanValue(), ((Float)this.armorBorderedRectWidth.getValue()).floatValue(), ((Float)this.armorBorderedRectOffset.getValue()).floatValue(), x, 
/* 521 */                 -((Integer)this.itemsOffsetY.getValue()).intValue(), ((Integer)this.itemRectsWidth.getValue()).intValue(), ((Integer)this.armorRectHeight.getValue()).intValue(), color.getRGB(), color.getRGB());
/* 522 */             RenderUtils2D.releaseGl();
/*     */             break;
/*     */         } 
/*     */ 
/*     */         
/* 527 */         if (((Boolean)this.duraBar.getValue()).booleanValue()) {
/* 528 */           renderDMGBar(armorItem, x, -((Float)this.duraBarOffsetY.getValue()).floatValue() - ((Integer)this.itemsOffsetY.getValue()).intValue());
/*     */         }
/*     */         
/* 531 */         if (((Boolean)this.duraPercent.getValue()).booleanValue()) {
/* 532 */           renderDMGPercentage(armorItem, x + ((Float)this.duraPercentOffsetX.getValue()).floatValue(), -((Integer)this.itemsOffsetY.getValue()).intValue() - ((Float)this.duraPercentOffsetY.getValue()).floatValue());
/*     */         }
/*     */         
/* 535 */         if (((Boolean)this.enchants.getValue()).booleanValue()) {
/* 536 */           renderEnchantments(armorItem, x + ((Float)this.enchantOffsetX.getValue()).floatValue(), -((Integer)this.itemsOffsetY.getValue()).intValue() - ((Float)this.enchantOffsetY.getValue()).floatValue(), ((Boolean)this.enchantRenderUp.getValue()).booleanValue());
/*     */         }
/*     */       } 
/* 539 */       x -= ((Integer)this.separationDistItems.getValue()).intValue();
/*     */     } 
/*     */     
/* 542 */     if (((Boolean)this.heldItems.getValue()).booleanValue()) {
/* 543 */       ItemStack mainItem = ((EntityLivingBase)entity).func_184614_ca();
/* 544 */       ItemStack offItem = ((EntityLivingBase)entity).func_184592_cb();
/*     */       
/* 546 */       GL11.glEnable(3553);
/* 547 */       RenderHelper.func_74519_b();
/* 548 */       GlStateManager.func_179126_j();
/* 549 */       (mc.func_175599_af()).field_77023_b = -150.5F;
/* 550 */       GL11.glDepthRange(0.0D, 0.01D);
/* 551 */       if (((Boolean)this.flipHeldItems.getValue()).booleanValue()) {
/* 552 */         mc.func_175599_af().func_180450_b(mainItem, (int)((((Integer)this.separationDistItems.getValue()).intValue() * 4) + ((Float)this.heldItemsOffsetX.getValue()).floatValue()), (int)(-((Integer)this.itemsOffsetY.getValue()).intValue() - ((Float)this.heldItemsOffsetY.getValue()).floatValue()));
/* 553 */         mc.func_175599_af().func_180450_b(offItem, (int)(x - ((Float)this.heldItemsOffsetX.getValue()).floatValue()), (int)(-((Integer)this.itemsOffsetY.getValue()).intValue() - ((Float)this.heldItemsOffsetY.getValue()).floatValue()));
/*     */       } else {
/*     */         
/* 556 */         mc.func_175599_af().func_180450_b(mainItem, (int)(x - ((Float)this.heldItemsOffsetX.getValue()).floatValue()), (int)(-((Integer)this.itemsOffsetY.getValue()).intValue() - ((Float)this.heldItemsOffsetY.getValue()).floatValue()));
/* 557 */         mc.func_175599_af().func_180450_b(offItem, (int)((((Integer)this.separationDistItems.getValue()).intValue() * 4) + ((Float)this.heldItemsOffsetX.getValue()).floatValue()), (int)(-((Integer)this.itemsOffsetY.getValue()).intValue() - ((Float)this.heldItemsOffsetY.getValue()).floatValue()));
/*     */       } 
/* 559 */       GL11.glDepthRange(0.0D, 1.0D);
/* 560 */       (mc.func_175599_af()).field_77023_b = 0.0F;
/* 561 */       GlStateManager.func_179097_i();
/* 562 */       RenderHelper.func_74518_a();
/* 563 */       GL11.glDisable(3553);
/*     */       
/* 565 */       if (((Boolean)this.duraBar.getValue()).booleanValue()) {
/* 566 */         if (mainItem.func_77984_f()) {
/* 567 */           renderDMGBar(mainItem, ((Boolean)this.flipHeldItems.getValue()).booleanValue() ? ((((Integer)this.separationDistItems.getValue()).intValue() * 4) + ((Float)this.heldItemsOffsetX.getValue()).floatValue()) : (x - ((Float)this.heldItemsOffsetX.getValue()).floatValue()), -((Float)this.duraBarOffsetY.getValue()).floatValue() - ((Integer)this.itemsOffsetY.getValue()).intValue() - ((Float)this.heldItemsOffsetY.getValue()).floatValue());
/*     */         }
/*     */         
/* 570 */         if (offItem.func_77984_f()) {
/* 571 */           renderDMGBar(offItem, ((Boolean)this.flipHeldItems.getValue()).booleanValue() ? (x - ((Float)this.heldItemsOffsetX.getValue()).floatValue()) : ((((Integer)this.separationDistItems.getValue()).intValue() * 4) + ((Float)this.heldItemsOffsetX.getValue()).floatValue()), -((Float)this.duraBarOffsetY.getValue()).floatValue() - ((Integer)this.itemsOffsetY.getValue()).intValue() - ((Float)this.heldItemsOffsetY.getValue()).floatValue());
/*     */         }
/*     */       } 
/*     */       
/* 575 */       if (((Boolean)this.duraPercent.getValue()).booleanValue()) {
/* 576 */         if (mainItem.func_77984_f()) {
/* 577 */           renderDMGPercentage(mainItem, (((Boolean)this.flipHeldItems.getValue()).booleanValue() ? ((((Integer)this.separationDistItems.getValue()).intValue() * 4) + ((Float)this.heldItemsOffsetX.getValue()).floatValue()) : (x - ((Float)this.heldItemsOffsetX.getValue()).floatValue())) + ((Float)this.duraPercentOffsetX.getValue()).floatValue(), -((Integer)this.itemsOffsetY.getValue()).intValue() - ((Float)this.duraPercentOffsetY.getValue()).floatValue() - ((Float)this.heldItemsOffsetY.getValue()).floatValue());
/*     */         }
/*     */         
/* 580 */         if (offItem.func_77984_f()) {
/* 581 */           renderDMGPercentage(offItem, (((Boolean)this.flipHeldItems.getValue()).booleanValue() ? (x - ((Float)this.heldItemsOffsetX.getValue()).floatValue()) : ((((Integer)this.separationDistItems.getValue()).intValue() * 4) + ((Float)this.heldItemsOffsetX.getValue()).floatValue())) + ((Float)this.duraPercentOffsetX.getValue()).floatValue(), -((Integer)this.itemsOffsetY.getValue()).intValue() - ((Float)this.duraPercentOffsetY.getValue()).floatValue() - ((Float)this.heldItemsOffsetY.getValue()).floatValue());
/*     */         }
/*     */       } 
/*     */       
/* 585 */       if (((Boolean)this.enchants.getValue()).booleanValue()) {
/* 586 */         renderEnchantments(mainItem, (((Boolean)this.flipHeldItems.getValue()).booleanValue() ? ((((Integer)this.separationDistItems.getValue()).intValue() * 4) + ((Float)this.heldItemsOffsetX.getValue()).floatValue()) : (x - ((Float)this.heldItemsOffsetX.getValue()).floatValue())) + ((Float)this.enchantOffsetX.getValue()).floatValue(), -((Integer)this.itemsOffsetY.getValue()).intValue() - ((Float)this.enchantOffsetY.getValue()).floatValue() - ((Float)this.heldItemsOffsetY.getValue()).floatValue(), ((Boolean)this.enchantRenderUp.getValue()).booleanValue());
/* 587 */         renderEnchantments(offItem, (((Boolean)this.flipHeldItems.getValue()).booleanValue() ? (x - ((Float)this.heldItemsOffsetX.getValue()).floatValue()) : ((((Integer)this.separationDistItems.getValue()).intValue() * 4) + ((Float)this.heldItemsOffsetX.getValue()).floatValue())) + ((Float)this.enchantOffsetX.getValue()).floatValue(), -((Integer)this.itemsOffsetY.getValue()).intValue() - ((Float)this.enchantOffsetY.getValue()).floatValue() - ((Float)this.heldItemsOffsetY.getValue()).floatValue(), ((Boolean)this.enchantRenderUp.getValue()).booleanValue());
/*     */       } 
/*     */       
/* 590 */       if (((Boolean)this.heldItemsStackSize.getValue()).booleanValue()) {
/* 591 */         if (mainItem.field_77994_a > 1 && mainItem.func_77973_b() != Items.field_190931_a) {
/* 592 */           drawString(mainItem.field_77994_a + "", (((Boolean)this.flipHeldItems.getValue()).booleanValue() ? ((((Integer)this.separationDistItems.getValue()).intValue() * 4) + ((Float)this.heldItemsOffsetX.getValue()).floatValue()) : (x - ((Float)this.heldItemsOffsetX.getValue()).floatValue())) + ((Float)this.heldItemsStackSizeOffsetX.getValue()).floatValue(), -((Integer)this.itemsOffsetY.getValue()).intValue() - ((Float)this.heldItemsStackSizeOffsetY.getValue()).floatValue(), ((Boolean)this.itemsTextShadow.getValue()).booleanValue(), ((Color)this.heldItemStackSizeColor.getValue()).getColor());
/*     */         }
/*     */         
/* 595 */         if (offItem.field_77994_a > 1 && offItem.func_77973_b() != Items.field_190931_a) {
/* 596 */           drawString(offItem.field_77994_a + "", (((Boolean)this.flipHeldItems.getValue()).booleanValue() ? (x - ((Float)this.heldItemsOffsetX.getValue()).floatValue()) : ((((Integer)this.separationDistItems.getValue()).intValue() * 4) + ((Float)this.heldItemsOffsetX.getValue()).floatValue())) + ((Float)this.heldItemsStackSizeOffsetX.getValue()).floatValue(), -((Integer)this.itemsOffsetY.getValue()).intValue() - ((Float)this.heldItemsStackSizeOffsetY.getValue()).floatValue(), ((Boolean)this.itemsTextShadow.getValue()).booleanValue(), ((Color)this.heldItemStackSizeColor.getValue()).getColor());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 601 */     GL11.glTranslatef(((Integer)this.separationDistItems.getValue()).intValue() * 2.0F, 0.0F, 0.0F);
/*     */     
/* 603 */     if (((Boolean)this.heldItemName.getValue()).booleanValue()) {
/* 604 */       GL11.glTranslatef((((Float)this.heldItemNameOffsetX.getValue()).floatValue() - getWidthNametags(((EntityLivingBase)entity).func_184614_ca().func_82833_r()) * ((Float)this.heldItemNameScale.getValue()).floatValue() / 2.0F) * (1.0F - ((Float)this.heldItemNameScale.getValue()).floatValue()), (-((Integer)this.itemsOffsetY.getValue()).intValue() - ((Float)this.heldItemNameOffsetY.getValue()).floatValue()) * (1.0F - ((Float)this.heldItemNameScale.getValue()).floatValue()), 0.0F);
/* 605 */       GL11.glScalef(((Float)this.heldItemNameScale.getValue()).floatValue(), ((Float)this.heldItemNameScale.getValue()).floatValue(), ((Float)this.heldItemNameScale.getValue()).floatValue());
/* 606 */       drawString((((EntityLivingBase)entity).func_184614_ca().func_77973_b() != Items.field_190931_a) ? ((EntityLivingBase)entity).func_184614_ca().func_82833_r() : "", ((Float)this.heldItemNameOffsetX.getValue()).floatValue() - getWidthNametags(((EntityLivingBase)entity).func_184614_ca().func_82833_r()) * ((Float)this.heldItemNameScale.getValue()).floatValue() / 2.0F, -((Integer)this.itemsOffsetY.getValue()).intValue() - ((Float)this.heldItemNameOffsetY.getValue()).floatValue(), ((Boolean)this.itemsTextShadow.getValue()).booleanValue(), ((Color)this.heldItemNameColor.getValue()).getColor());
/* 607 */       GL11.glScalef(1.0F / ((Float)this.heldItemNameScale.getValue()).floatValue(), 1.0F / ((Float)this.heldItemNameScale.getValue()).floatValue(), 1.0F / ((Float)this.heldItemNameScale.getValue()).floatValue());
/* 608 */       GL11.glTranslatef((((Float)this.heldItemNameOffsetX.getValue()).floatValue() - getWidthNametags(((EntityLivingBase)entity).func_184614_ca().func_82833_r()) * ((Float)this.heldItemNameScale.getValue()).floatValue() / 2.0F) * -(1.0F - ((Float)this.heldItemNameScale.getValue()).floatValue()), (-((Integer)this.itemsOffsetY.getValue()).intValue() - ((Float)this.heldItemNameOffsetY.getValue()).floatValue()) * -(1.0F - ((Float)this.heldItemNameScale.getValue()).floatValue()), 0.0F);
/*     */     } 
/*     */     
/* 611 */     GL11.glScalef(1.0F / ((Float)this.itemsScale.getValue()).floatValue(), 1.0F / ((Float)this.itemsScale.getValue()).floatValue(), 1.0F / ((Float)this.itemsScale.getValue()).floatValue());
/* 612 */     GlStateManager.func_179132_a(false);
/*     */   }
/*     */   
/*     */   private void renderDMGPercentage(ItemStack itemStack, float x, float y) {
/* 616 */     Color dBColor = ((Color)this.duraColor.getValue()).getColorColor();
/* 617 */     if (((Boolean)this.duraColorShift.getValue()).booleanValue()) {
/* 618 */       dBColor = ColorUtil.colorShift(((Color)this.duraColor2.getValue()).getColorColor(), ((Color)this.duraColor.getValue()).getColorColor(), ArmorDisplay.INSTANCE.getItemDMG(itemStack) * 300.0F);
/*     */     }
/*     */     
/* 621 */     GL11.glTranslatef(x * (1.0F - ((Float)this.duraPercentScale.getValue()).floatValue()), y * (1.0F - ((Float)this.duraPercentScale.getValue()).floatValue()), 0.0F);
/* 622 */     GL11.glScalef(((Float)this.duraPercentScale.getValue()).floatValue(), ((Float)this.duraPercentScale.getValue()).floatValue(), ((Float)this.duraPercentScale.getValue()).floatValue());
/* 623 */     drawString((int)(ArmorDisplay.INSTANCE.getItemDMG(itemStack) * 100.0F) + "", x, y, ((Boolean)this.itemsTextShadow.getValue()).booleanValue(), dBColor.getRGB());
/* 624 */     GL11.glScalef(1.0F / ((Float)this.duraPercentScale.getValue()).floatValue(), 1.0F / ((Float)this.duraPercentScale.getValue()).floatValue(), 1.0F / ((Float)this.duraPercentScale.getValue()).floatValue());
/* 625 */     GL11.glTranslatef(x * -(1.0F - ((Float)this.duraPercentScale.getValue()).floatValue()), y * -(1.0F - ((Float)this.duraPercentScale.getValue()).floatValue()), 0.0F);
/*     */   }
/*     */   
/*     */   private void renderDMGBar(ItemStack itemStack, float x, float y) {
/* 629 */     Color dBColor = ((Color)this.duraColor.getValue()).getColorColor();
/* 630 */     float damageFactor = ArmorDisplay.INSTANCE.getItemDMG(itemStack) * ((Integer)this.itemRectsWidth.getValue()).intValue();
/* 631 */     GL11.glDisable(3553);
/* 632 */     RenderUtils2D.prepareGl();
/*     */     
/* 634 */     if (((Boolean)this.duraColorShift.getValue()).booleanValue()) {
/* 635 */       dBColor = ColorUtil.colorShift(((Color)this.duraColor2.getValue()).getColorColor(), ((Color)this.duraColor.getValue()).getColorColor(), MathUtilFuckYou.clamp(ArmorDisplay.INSTANCE.getItemDMG(itemStack) * 300.0F, 0.0F, 300.0F));
/*     */     }
/*     */     
/* 638 */     if (((Boolean)this.duraBarBordered.getValue()).booleanValue()) {
/* 639 */       if (((Boolean)this.duraBarRounded.getValue()).booleanValue()) {
/* 640 */         RenderUtils2D.drawRoundedRect(x + ((Float)this.duraBarBorderOffset.getValue()).floatValue() / 2.0F, y + ((Float)this.duraBarBorderOffset.getValue()).floatValue() / 2.0F, ((Float)this.duraBarRoundedRadius.getValue()).floatValue(), x + ((Integer)this.itemRectsWidth.getValue()).intValue() - ((Float)this.duraBarBorderOffset.getValue()).floatValue() / 2.0F, y + ((Integer)this.duraBarHeight.getValue()).intValue() - ((Float)this.duraBarBorderOffset.getValue()).floatValue() / 2.0F, false, ((Boolean)this.duraBarRoundedTopRight.getValue()).booleanValue(), ((Boolean)this.duraBarRoundedTopLeft.getValue()).booleanValue(), ((Boolean)this.duraBarRoundedDownRight.getValue()).booleanValue(), ((Boolean)this.duraBarRoundedDownLeft.getValue()).booleanValue(), ((Color)this.duraBarBGColor.getValue()).getColor());
/*     */       } else {
/*     */         
/* 643 */         RenderUtils2D.drawRect(x + ((Float)this.duraBarBorderOffset.getValue()).floatValue() / 2.0F, y + ((Float)this.duraBarBorderOffset.getValue()).floatValue() / 2.0F, x + ((Integer)this.itemRectsWidth.getValue()).intValue() - ((Float)this.duraBarBorderOffset.getValue()).floatValue() / 2.0F, y + ((Integer)this.duraBarHeight.getValue()).intValue() - ((Float)this.duraBarBorderOffset.getValue()).floatValue() / 2.0F, ((Color)this.duraBarBGColor.getValue()).getColor());
/*     */       }
/*     */     
/*     */     }
/* 647 */     else if (((Boolean)this.duraBarRounded.getValue()).booleanValue()) {
/* 648 */       RenderUtils2D.drawRoundedRect(x, y, ((Float)this.duraBarRoundedRadius.getValue()).floatValue(), x + ((Integer)this.itemRectsWidth.getValue()).intValue(), y + ((Integer)this.duraBarHeight.getValue()).intValue(), false, ((Boolean)this.duraBarRoundedTopRight.getValue()).booleanValue(), ((Boolean)this.duraBarRoundedTopLeft.getValue()).booleanValue(), ((Boolean)this.duraBarRoundedDownRight.getValue()).booleanValue(), ((Boolean)this.duraBarRoundedDownLeft.getValue()).booleanValue(), ((Color)this.duraBarBGColor.getValue()).getColor());
/*     */     } else {
/*     */       
/* 651 */       RenderUtils2D.drawRect(x, y, x + ((Integer)this.itemRectsWidth.getValue()).intValue(), y + ((Integer)this.duraBarHeight.getValue()).intValue(), ((Color)this.duraBarBGColor.getValue()).getColor());
/*     */     } 
/*     */ 
/*     */     
/* 655 */     if (((Boolean)this.duraBarBordered.getValue()).booleanValue()) {
/* 656 */       if (((Boolean)this.duraBarRounded.getValue()).booleanValue()) {
/* 657 */         RenderUtils2D.drawCustomRoundedRectOutline(x, y, x + ((Integer)this.itemRectsWidth.getValue()).intValue(), y + ((Integer)this.duraBarHeight.getValue()).intValue(), ((Float)this.duraBarRoundedRadius.getValue()).floatValue(), ((Float)this.duraBarBorderWidth.getValue()).floatValue(), ((Boolean)this.duraBarRoundedTopRight.getValue()).booleanValue(), ((Boolean)this.duraBarRoundedTopLeft.getValue()).booleanValue(), ((Boolean)this.duraBarRoundedDownRight.getValue()).booleanValue(), ((Boolean)this.duraBarRoundedDownLeft.getValue()).booleanValue(), false, false, dBColor.getRGB());
/* 658 */         RenderUtils2D.drawRoundedRect(x + ((Float)this.duraBarBorderOffset.getValue()).floatValue() / 2.0F, y + ((Float)this.duraBarBorderOffset.getValue()).floatValue() / 2.0F, ((Float)this.duraBarRoundedRadius.getValue()).floatValue(), x + damageFactor - ((Float)this.duraBarBorderOffset.getValue()).floatValue() / 2.0F, y + ((Integer)this.duraBarHeight.getValue()).intValue() - ((Float)this.duraBarBorderOffset.getValue()).floatValue() / 2.0F, false, ((Boolean)this.duraBarRoundedTopRight.getValue()).booleanValue(), ((Boolean)this.duraBarRoundedTopLeft.getValue()).booleanValue(), ((Boolean)this.duraBarRoundedDownRight.getValue()).booleanValue(), ((Boolean)this.duraBarRoundedDownLeft.getValue()).booleanValue(), dBColor.getRGB());
/*     */       } else {
/*     */         
/* 661 */         RenderUtils2D.drawRectOutline(x, y, x + ((Integer)this.itemRectsWidth.getValue()).intValue(), y + ((Integer)this.duraBarHeight.getValue()).intValue(), ((Float)this.duraBarBorderWidth.getValue()).floatValue(), dBColor.getRGB(), false, false);
/* 662 */         RenderUtils2D.drawRect(x + ((Float)this.duraBarBorderOffset.getValue()).floatValue() / 2.0F, y + ((Float)this.duraBarBorderOffset.getValue()).floatValue() / 2.0F, x + damageFactor - ((Float)this.duraBarBorderOffset.getValue()).floatValue() / 2.0F, y + ((Integer)this.duraBarHeight.getValue()).intValue() - ((Float)this.duraBarBorderOffset.getValue()).floatValue() / 2.0F, dBColor.getRGB());
/*     */       }
/*     */     
/*     */     }
/* 666 */     else if (((Boolean)this.duraBarRounded.getValue()).booleanValue()) {
/* 667 */       RenderUtils2D.drawRoundedRect(x, y, ((Float)this.duraBarRoundedRadius.getValue()).floatValue(), x + damageFactor, y + ((Integer)this.duraBarHeight.getValue()).intValue(), false, ((Boolean)this.duraBarRoundedTopRight.getValue()).booleanValue(), ((Boolean)this.duraBarRoundedTopLeft.getValue()).booleanValue(), ((Boolean)this.duraBarRoundedDownRight.getValue()).booleanValue(), ((Boolean)this.duraBarRoundedDownLeft.getValue()).booleanValue(), dBColor.getRGB());
/*     */     } else {
/*     */       
/* 670 */       RenderUtils2D.drawRect(x, y, x + damageFactor, y + ((Integer)this.duraBarHeight.getValue()).intValue(), dBColor.getRGB());
/*     */     } 
/*     */     
/* 673 */     RenderUtils2D.releaseGl();
/*     */   }
/*     */   
/*     */   private void renderEnchantments(ItemStack itemStack, float x, float y, boolean drawUp) {
/* 677 */     float tempY = y;
/*     */     
/* 679 */     GL11.glTranslatef(x * (1.0F - ((Float)this.enchantScale.getValue()).floatValue()), y * (1.0F - ((Float)this.enchantScale.getValue()).floatValue()), 0.0F);
/* 680 */     GL11.glScalef(((Float)this.enchantScale.getValue()).floatValue(), ((Float)this.enchantScale.getValue()).floatValue(), ((Float)this.enchantScale.getValue()).floatValue());
/*     */     
/* 682 */     NBTTagList enchants = itemStack.func_77986_q();
/* 683 */     for (int i = 0; i < enchants.func_74745_c(); i++) {
/* 684 */       Enchantment enchantment = Enchantment.func_185262_c(enchants.func_150305_b(i).func_74765_d("id"));
/* 685 */       short lvl = enchants.func_150305_b(i).func_74765_d("lvl");
/*     */       
/* 687 */       if (enchantment != null) {
/* 688 */         drawString((enchantment.func_190936_d() ? enchantment.func_77316_c(lvl).substring(11).substring(0, 2) : enchantment
/* 689 */             .func_77316_c(lvl).substring(0, 2)) + lvl, x, tempY, ((Boolean)this.itemsTextShadow
/* 690 */             .getValue()).booleanValue(), enchantment.func_190936_d() ? ((Color)this.enchantmentCurseColor.getValue()).getColor() : ((Color)this.enchantmentColor.getValue()).getColor());
/*     */         
/* 692 */         tempY -= drawUp ? ((Float)this.enchantSeparationOffset.getValue()).floatValue() : -((Float)this.enchantSeparationOffset.getValue()).floatValue();
/*     */       } 
/*     */     } 
/*     */     
/* 696 */     GL11.glScalef(1.0F / ((Float)this.enchantScale.getValue()).floatValue(), 1.0F / ((Float)this.enchantScale.getValue()).floatValue(), 1.0F / ((Float)this.enchantScale.getValue()).floatValue());
/* 697 */     GL11.glTranslatef(x * -(1.0F - ((Float)this.enchantScale.getValue()).floatValue()), y * -(1.0F - ((Float)this.enchantScale.getValue()).floatValue()), 0.0F);
/*     */   }
/*     */   
/*     */   private int getWidthNametags(String str) {
/* 701 */     if (this.font.getValue() == CustomFont.FontMode.Comfortaa) {
/* 702 */       return FontManager.fontRenderer.getStringWidth(str);
/*     */     }
/* 704 */     if (this.font.getValue() == CustomFont.FontMode.Arial) {
/* 705 */       return FontManager.fontArialRenderer.getStringWidth(str);
/*     */     }
/* 707 */     if (this.font.getValue() == CustomFont.FontMode.Objectivity) {
/* 708 */       return FontManager.fontObjectivityRenderer.getStringWidth(str);
/*     */     }
/*     */     
/* 711 */     return mc.field_71466_p.func_78256_a(str);
/*     */   }
/*     */   
/*     */   enum Page {
/* 715 */     Distance,
/* 716 */     Rect,
/* 717 */     RectEffects,
/* 718 */     HealthBar,
/* 719 */     Items,
/* 720 */     Text,
/* 721 */     Entities,
/* 722 */     Colors;
/*     */   }
/*     */   
/*     */   public enum TextMode {
/* 726 */     None,
/* 727 */     Left,
/* 728 */     Right;
/*     */   }
/*     */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\me\thediamondsword5\moloch\module\modules\visuals\Nametags.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */