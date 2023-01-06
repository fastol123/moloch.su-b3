package net.spartanb312.base.mixin.mixins.accessor;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({PlayerControllerMP.class})
public interface AccessorPlayerControllerMP {
  @Accessor("blockHitDelay")
  int getBlockHitDelay();
  
  @Accessor("blockHitDelay")
  void setBlockHitDelay(int paramInt);
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\accessor\AccessorPlayerControllerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */