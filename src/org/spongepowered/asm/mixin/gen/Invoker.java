package org.spongepowered.asm.mixin.gen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Invoker {
  String value() default "";
  
  boolean remap() default true;
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\org\spongepowered\asm\mixin\gen\Invoker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */