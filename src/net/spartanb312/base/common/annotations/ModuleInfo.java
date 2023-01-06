package net.spartanb312.base.common.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import net.spartanb312.base.module.Category;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
  String name();
  
  int keyCode() default 0;
  
  Category category();
  
  String description() default "";
  
  boolean visible() default true;
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\common\annotations\ModuleInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */