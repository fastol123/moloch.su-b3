package net.spartanb312.base.event.events.client;

import net.spartanb312.base.event.EventCenter;

public class InitializationEvent extends EventCenter {
  public static class PreInitialize extends InitializationEvent {}
  
  public static class Initialize extends InitializationEvent {}
  
  public static class PostInitialize extends InitializationEvent {}
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\event\events\client\InitializationEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */