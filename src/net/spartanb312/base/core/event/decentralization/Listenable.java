package net.spartanb312.base.core.event.decentralization;

import java.util.concurrent.ConcurrentHashMap;
import net.spartanb312.base.core.concurrent.task.Task;

public interface Listenable {
  ConcurrentHashMap<DecentralizedEvent<? extends EventData>, Task<? extends EventData>> listenerMap();
  
  void subscribe();
  
  void unsubscribe();
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\core\event\decentralization\Listenable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */