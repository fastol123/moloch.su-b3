package net.spartanb312.base.mixin.mixins.accessor;

import java.util.List;
import me.thediamondsword5.moloch.mixinotherstuff.AccessorInterfaceShaderGroup;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ShaderGroup.class})
public abstract class AccessorShaderGroup implements AccessorInterfaceShaderGroup {
  @Accessor("listFramebuffers")
  public abstract List<Framebuffer> getListFramebuffers();
  
  @Accessor("listShaders")
  public abstract List<Shader> getListShaders();
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\mixin\mixins\accessor\AccessorShaderGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */