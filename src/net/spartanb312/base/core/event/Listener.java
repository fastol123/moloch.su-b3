package net.spartanb312.base.core.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Listener {
  int priority() default 1000;
  
  boolean parallel() default false;
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\event\Listener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */