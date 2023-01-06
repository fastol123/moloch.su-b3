package net.spartanb312.base.common.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {
  String command();
  
  String description() default "";
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\common\annotations\CommandInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */