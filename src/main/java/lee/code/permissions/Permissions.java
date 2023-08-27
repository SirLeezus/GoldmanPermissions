package lee.code.permissions;

import com.mojang.brigadier.tree.LiteralCommandNode;
import lee.code.permissions.commands.CommandManager;
import lee.code.permissions.commands.CustomCommand;
import lee.code.permissions.commands.TabCompletion;
import lee.code.permissions.database.CacheManager;
import lee.code.permissions.database.DatabaseManager;
import lee.code.permissions.listeners.JoinListener;
import lee.code.permissions.listeners.QuitListener;
import lee.code.permissions.listeners.TabCompleteListener;
import lee.code.permissions.managers.PermissionManager;
import lombok.Getter;
import me.lucko.commodore.CommodoreProvider;
import me.lucko.commodore.file.CommodoreFileReader;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Permissions extends JavaPlugin {
  @Getter private PermissionManager permissionManager;
  @Getter private CacheManager cacheManager;
  @Getter private Data data;
  @Getter private CommandManager commandManager;
  private DatabaseManager databaseManager;

  @Override
  public void onEnable() {
    this.data = new Data();
    this.permissionManager = new PermissionManager(this);
    this.databaseManager = new DatabaseManager(this);
    this.cacheManager = new CacheManager(this, databaseManager);
    this.commandManager = new CommandManager(this);
    databaseManager.initialize(false);
    registerListeners();
    registerCommands();
  }

  @Override
  public void onDisable() {
    databaseManager.closeConnection();
  }

  private void registerListeners() {
    getServer().getPluginManager().registerEvents(new JoinListener(this), this);
    getServer().getPluginManager().registerEvents(new TabCompleteListener(this), this);
    getServer().getPluginManager().registerEvents(new QuitListener(this), this);
  }

  private void registerCommands() {
    for (CustomCommand command : commandManager.getCommands()) {
      getCommand(command.getName()).setExecutor(command);
      getCommand(command.getName()).setTabCompleter(new TabCompletion(command));
      loadCommodoreData(getCommand(command.getName()));
    }
  }

  private void loadCommodoreData(Command command) {
    try {
      final LiteralCommandNode<?> targetCommand = CommodoreFileReader.INSTANCE.parse(getResource("commodore/" + command.getName() + ".commodore"));
      CommodoreProvider.getCommodore(this).register(command, targetCommand);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
