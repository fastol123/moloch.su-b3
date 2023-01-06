package net.spartanb312.base.mixin.mixins.accessor;

import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({RenderManager.class})
public interface AccessorRenderManager {
  @Accessor("renderPosX")
  double getRenderPosX();
  
  @Accessor("renderPosY")
  double getRenderPosY();
  
  @Accessor("renderPosZ")
  double getRenderPosZ();
  
  @Accessor("renderOutlines")
  boolean getRenderOutlines();
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\accessor\AccessorRenderManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */