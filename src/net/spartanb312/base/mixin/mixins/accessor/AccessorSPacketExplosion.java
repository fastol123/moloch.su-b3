package net.spartanb312.base.mixin.mixins.accessor;

import net.minecraft.network.play.server.SPacketExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({SPacketExplosion.class})
public interface AccessorSPacketExplosion {
  @Accessor("motionX")
  void setMotionX(float paramFloat);
  
  @Accessor("motionY")
  void setMotionY(float paramFloat);
  
  @Accessor("motionZ")
  void setMotionZ(float paramFloat);
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\accessor\AccessorSPacketExplosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */