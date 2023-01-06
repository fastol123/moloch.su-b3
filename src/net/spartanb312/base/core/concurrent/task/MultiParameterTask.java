package net.spartanb312.base.core.concurrent.task;

import java.util.List;

public interface MultiParameterTask<T> {
  void invoke(List<T> paramList);
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\concurrent\task\MultiParameterTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */