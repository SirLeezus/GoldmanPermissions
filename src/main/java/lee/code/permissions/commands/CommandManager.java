package lee.code.permissions.commands;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import lee.code.permissions.Permissions;
import lee.code.permissions.commands.cmds.PermissionCMD;
import lee.code.permissions.commands.cmds.SetRankCMD;
import lee.code.permissions.commands.cmds.StaffChatCMD;
import lee.code.permissions.lang.Lang;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {
  private final Permissions permissions;
  @Getter private final Set<CustomCommand> commands = ConcurrentHashMap.newKeySet();
  private final ConcurrentHashMap<UUID, ScheduledTask> asyncTasks = new ConcurrentHashMap<>();
  private final Object synchronizedThreadLock = new Object();

  public CommandManager(Permissions permissions) {
    this.permissions = permissions;
    storeCommands();
  }

  private void storeCommands() {
    commands.add(new SetRankCMD(permissions));
    commands.add(new PermissionCMD(permissions));
    commands.add(new StaffChatCMD(permissions));
  }

  public void perform(CommandSender sender, String[] args, CustomCommand customCommand, Command command) {
    if (sender instanceof Player player) {
      final UUID uuid = player.getUniqueId();
      if (asyncTasks.containsKey(uuid)) {
        player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_ONE_COMMAND_AT_A_TIME.getComponent(null)));
        return;
      }
      if (customCommand.performAsync()) {
        if (customCommand.performAsyncSynchronized()) {
          synchronized (synchronizedThreadLock) {
            performCommandAsync(player, uuid, args, customCommand, command);
          }
        } else {
          performCommandAsync(player, uuid, args, customCommand, command);
        }
      } else {
        customCommand.perform(player, args, command);
      }
    } else if (sender instanceof ConsoleCommandSender console) {
      customCommand.performConsole(console, args, command);
    }
  }

  private void performCommandAsync(Player player, UUID uuid, String[] args, CustomCommand customCommand, Command command) {
    asyncTasks.put(uuid, Bukkit.getAsyncScheduler().runNow(permissions, scheduledTask -> {
      try {
        customCommand.perform(player, args, command);
      } finally {
        asyncTasks.remove(uuid);
      }
    }));
  }
}
