package net.spartanb312.base.mixin.mixins.accessor;

import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({CPacketPlayer.class})
public interface AccessorCPacketPlayer {
  @Accessor("x")
  void setX(double paramDouble);
  
  @Accessor("y")
  void setY(double paramDouble);
  
  @Accessor("z")
  void setZ(double paramDouble);
  
  @Accessor("yaw")
  void setYaw(float paramFloat);
  
  @Accessor("pitch")
  void setPitch(float paramFloat);
  
  @Accessor("onGround")
  void setOnGround(boolean paramBoolean);
  
  @Accessor("moving")
  boolean getMoving();
  
  @Accessor("rotating")
  boolean getRotating();
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\accessor\AccessorCPacketPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */