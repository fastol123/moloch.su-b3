package net.spartanb312.base.mixin.mixins.accessor;

import net.minecraft.network.play.client.CPacketChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({CPacketChatMessage.class})
public interface AccessorCPacketChatMessage {
  @Accessor("message")
  void setMessage(String paramString);
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\accessor\AccessorCPacketChatMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */