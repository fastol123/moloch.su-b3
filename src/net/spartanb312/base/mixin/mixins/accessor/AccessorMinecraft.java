package net.spartanb312.base.mixin.mixins.accessor;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({Minecraft.class})
public interface AccessorMinecraft {
  @Accessor("rightClickDelayTimer")
  int getRightClickDelayTimer();
  
  @Accessor("rightClickDelayTimer")
  void setRightClickDelayTimer(int paramInt);
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\accessor\AccessorMinecraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */