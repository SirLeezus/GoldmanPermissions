package lee.code.permissions.managers;

import lee.code.permissions.Permissions;
import lee.code.permissions.database.CacheManager;
import lee.code.permissions.enums.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PermissionManager {
  private final Permissions permissions;
  private final ConcurrentHashMap<UUID, Set<String>> playerCommands = new ConcurrentHashMap<>();

  public PermissionManager(Permissions permissions) {
    this.permissions = permissions;
  }

  public void registerPermissions(Player player) {
    if (player.isOp()) return;
    final CacheManager cacheManager = permissions.getCacheManager();
    final UUID uuid = player.getUniqueId();
    final Rank rank = cacheManager.getCachePlayers().getRank(uuid);
    final Set<String> playerPermissions = cacheManager.getCachePlayers().getPermissionData().getPermissionsList(uuid);
    final Set<String> rankPermissions = permissions.getData().getRankPermissions(rank);

    final PermissionAttachment attachment = player.addAttachment(permissions);
    attachment.getPermissions().clear();

    for (PermissionAttachmentInfo perm : player.getEffectivePermissions()) attachment.setPermission(perm.getPermission(), false);
    for (String perm : playerPermissions) attachment.setPermission(perm, true);
    for (String perm : rankPermissions) attachment.setPermission(perm, true);
    player.recalculatePermissions();
    player.updateCommands();
    storeCommands(player);
  }

  private void storeCommands(Player player) {
    final Set<String> commands = ConcurrentHashMap.newKeySet();
    final CommandMap commandMap = permissions.getServer().getCommandMap();
    for (Map.Entry<String, Command> targetCommand : commandMap.getKnownCommands().entrySet()) {
      final String perm = targetCommand.getValue().getPermission();
      if (perm != null && player.hasPermission(perm)) {
        final Command command = targetCommand.getValue();
        final List<String> aliases = command.getAliases();
        commands.addAll(aliases);
        commands.add(command.getName());
      }
    }
    playerCommands.put(player.getUniqueId(), commands);
  }

  public Set<String> getCommands(UUID uuid) {
    return playerCommands.get(uuid);
  }

  public boolean hasCommands(UUID uuid) {
    return playerCommands.containsKey(uuid);
  }

  public void deleteCommandData(UUID uuid) {
    playerCommands.remove(uuid);
  }
}
