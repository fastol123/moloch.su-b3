package net.spartanb312.base.mixin.mixins.accessor;

import net.minecraft.network.play.server.SPacketEntityVelocity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({SPacketEntityVelocity.class})
public interface AccessorSPacketEntityVelocity {
  @Accessor("motionX")
  void setMotionX(int paramInt);
  
  @Accessor("motionY")
  void setMotionY(int paramInt);
  
  @Accessor("motionZ")
  void setMotionZ(int paramInt);
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\accessor\AccessorSPacketEntityVelocity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */