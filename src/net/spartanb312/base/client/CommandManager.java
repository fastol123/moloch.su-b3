/*    */ package net.spartanb312.base.client;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Set;
/*    */ import me.thediamondsword5.moloch.command.commands.Enemy;
/*    */ import me.thediamondsword5.moloch.module.modules.client.ClientInfo;
/*    */ import net.spartanb312.base.BaseCenter;
/*    */ import net.spartanb312.base.command.Command;
/*    */ import net.spartanb312.base.command.commands.Bind;
/*    */ import net.spartanb312.base.command.commands.Config;
/*    */ import net.spartanb312.base.command.commands.Help;
/*    */ import net.spartanb312.base.command.commands.Prefix;
/*    */ import net.spartanb312.base.command.commands.Send;
/*    */ import net.spartanb312.base.command.commands.TP;
/*    */ import net.spartanb312.base.command.commands.Toggle;
/*    */ import net.spartanb312.base.core.event.Listener;
/*    */ import net.spartanb312.base.event.events.client.ChatEvent;
/*    */ 
/*    */ public class CommandManager {
/* 19 */   public List<Command> commands = new ArrayList<>();
/* 20 */   private final Set<Class<? extends Command>> classes = new HashSet<>(); private static CommandManager instance;
/*    */   
/*    */   public static void init() {
/* 23 */     instance = new CommandManager();
/* 24 */     instance.commands.clear();
/*    */     
/* 26 */     register((Class)Bind.class);
/* 27 */     register((Class)Commands.class);
/* 28 */     register((Class)Config.class);
/* 29 */     register((Class)Friend.class);
/* 30 */     register((Class)Help.class);
/* 31 */     register((Class)Prefix.class);
/* 32 */     register((Class)Send.class);
/* 33 */     register((Class)Toggle.class);
/* 34 */     register((Class)TP.class);
/*    */     
/* 36 */     register((Class)Enemy.class);
/*    */     
/* 38 */     instance.loadCommands();
/* 39 */     BaseCenter.EVENT_BUS.register(instance);
/*    */   }
/*    */   
/*    */   private static void register(Class<? extends Command> clazz) {
/* 43 */     instance.classes.add(clazz);
/*    */   }
/*    */   
/*    */   private void loadCommands() {
/* 47 */     this.classes.stream().sorted(Comparator.comparing(Class::getSimpleName)).forEach(clazz -> {
/*    */           try {
/*    */             Command command = clazz.newInstance();
/*    */             this.commands.add(command);
/* 51 */           } catch (Exception e) {
/*    */             e.printStackTrace();
/*    */             System.err.println("Couldn't initiate Command " + clazz.getSimpleName() + "! Err: " + e.getClass().getSimpleName() + ", message: " + e.getMessage());
/*    */           } 
/*    */         });
/*    */   }
/*    */   
/*    */   @Listener(priority = 2147483647)
/*    */   public void onChat(ChatEvent event) {
/* 60 */     if (event.getMessage().startsWith((String)ClientInfo.INSTANCE.clientPrefix.getValue())) {
/* 61 */       ConcurrentTaskManager.launch(() -> runCommands(event.getMessage()));
/* 62 */       event.cancel();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void runCommands(String s) {
/* 67 */     String readString = s.trim().substring(((String)ClientInfo.INSTANCE.clientPrefix.getValue()).length()).trim();
/* 68 */     boolean commandResolved = false;
/* 69 */     boolean hasArgs = readString.trim().contains(" ");
/* 70 */     String commandName = hasArgs ? readString.split(" ")[0] : readString.trim();
/* 71 */     String[] args = hasArgs ? readString.substring(commandName.length()).trim().split(" ") : new String[0];
/*    */     
/* 73 */     for (Command command : this.commands) {
/* 74 */       if (command.getCommand().trim().equalsIgnoreCase(commandName.trim().toLowerCase())) {
/* 75 */         command.onCall(readString, args);
/* 76 */         commandResolved = true;
/*    */         break;
/*    */       } 
/*    */     } 
/* 80 */     if (!commandResolved) {
/* 81 */       ChatUtil.sendNoSpamErrorMessage("Unknown command. try 'help' for a list of commands.");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static CommandManager getInstance() {
/* 88 */     if (instance == null) instance = new CommandManager(); 
/* 89 */     return instance;
/*    */   }
/*    */ }


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\base\client\CommandManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */