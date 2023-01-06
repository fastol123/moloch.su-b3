package net.spartanb312.base.mixin.mixins.accessor;

import net.minecraft.network.play.client.CPacketUseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({CPacketUseEntity.class})
public interface AccessorCPacketUseEntity {
  @Accessor("entityId")
  int getId();
  
  @Accessor("entityId")
  void setId(int paramInt);
  
  @Accessor("action")
  void setAction(CPacketUseEntity.Action paramAction);
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\accessor\AccessorCPacketUseEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */