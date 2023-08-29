package lee.code.permissions.commands.cmds;

import lee.code.colors.ColorAPI;
import lee.code.permissions.Permissions;
import lee.code.permissions.commands.CustomCommand;
import lee.code.permissions.database.CacheManager;
import lee.code.permissions.lang.Lang;
import lee.code.permissions.utils.CoreUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PermissionCMD extends CustomCommand {
  private final Permissions permissions;

  public PermissionCMD(Permissions permissions) {
    this.permissions = permissions;
  }

  @Override
  public String getName() {
    return "permission";
  }

  @Override
  public boolean performAsync() {
    return true;
  }

  @Override
  public boolean performAsyncSynchronized() {
    return true;
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    permissions.getCommandManager().perform(sender, args, this, command);
    return true;
  }

  @Override
  public void perform(Player player, String[] args, Command command) {
    performSender(player, args, command);
  }

  @Override
  public void performConsole(CommandSender console, String[] args, Command command) {
    performSender(console, args, command);
  }

  @Override
  public void performSender(CommandSender sender, String[] args, Command command) {
    if (args.length < 3) {
      sender.sendMessage(Lang.USAGE.getComponent(new String[]{command.getUsage()}));
      return;
    }
    final CacheManager cacheManager = permissions.getCacheManager();
    final String targetString = args[0];
    final OfflinePlayer offlineTarget = Bukkit.getOfflinePlayerIfCached(targetString);
    if (offlineTarget == null) {
      sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PLAYER_NOT_FOUND.getComponent(new String[]{targetString})));
      return;
    }
    final UUID targetID = offlineTarget.getUniqueId();
    if (!cacheManager.getCachePlayers().hasPlayerData(targetID)) {
      sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NO_PLAYER_DATA.getComponent(new String[]{targetString})));
      return;
    }
    final String option = args[1].toLowerCase();
    final String permission = args[2];
    switch (option) {
      case "add" -> {
        if (cacheManager.hasGlobalPermission(targetID, permission)) {
          sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PERMISSION_ADD_ALREADY_OWNED.getComponent(new String[]{ColorAPI.getNameColor(targetID, targetString), permission})));
          return;
        }
        cacheManager.getCachePlayers().getPermissionData().addPermission(targetID, permission);
        sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_PERMISSION_ADD_SUCCESSFUL.getComponent(new String[]{permission, ColorAPI.getNameColor(targetID, targetString)})));
      }
      case "remove" -> {
        if (!cacheManager.getCachePlayers().getPermissionData().hasPermission(targetID, permission)) {
          sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_PERMISSION_REMOVE_NOT_OWNED.getComponent(new String[]{ColorAPI.getNameColor(targetID, targetString), permission})));
          return;
        }
        cacheManager.getCachePlayers().getPermissionData().removePermission(targetID, permission);
        sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_PERMISSION_REMOVE_SUCCESSFUL.getComponent(new String[]{permission, ColorAPI.getNameColor(targetID, targetString)})));
      }
      case "check" -> {
        final String result = cacheManager.hasGlobalPermission(targetID, permission) ? Lang.TRUE.getString() : Lang.FALSE.getString();
        sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_PERMISSION_CHECK_SUCCESSFUL.getComponent(new String[]{ColorAPI.getNameColor(targetID, targetString), permission, result})));
      }
      default -> sender.sendMessage(Lang.USAGE.getComponent(new String[]{command.getUsage()}));
    }
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    if (args.length == 1) return StringUtil.copyPartialMatches(args[0], CoreUtil.getOnlinePlayers(), new ArrayList<>());
    else return new ArrayList<>();
  }
}
