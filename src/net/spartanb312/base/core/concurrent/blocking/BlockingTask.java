package net.spartanb312.base.core.concurrent.blocking;

import net.spartanb312.base.core.concurrent.task.Task;

public interface BlockingTask extends Task<BlockingContent> {
  void invoke(BlockingContent paramBlockingContent);
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\concurrent\blocking\BlockingTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */