package lee.code.permissions.database.cache;

import lee.code.permissions.database.tables.PlayerTable;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PermissionData {
  private final ConcurrentHashMap<UUID, Set<String>> permissionsCache = new ConcurrentHashMap<>();

  private final CachePlayers cachePlayers;

  public PermissionData(CachePlayers cachePlayers) {
    this.cachePlayers = cachePlayers;
  }

  private void setPermissionsCache(UUID uuid, String permission) {
    if (permissionsCache.containsKey(uuid)) {
      permissionsCache.get(uuid).add(permission);
    } else {
      final Set<String> permissions = ConcurrentHashMap.newKeySet();
      permissions.add(permission);
      permissionsCache.put(uuid, permissions);
    }
  }

  private void removePermissionsCache(UUID uuid, String permission) {
    permissionsCache.get(uuid).remove(permission);
    if (permissionsCache.get(uuid).isEmpty()) permissionsCache.remove(uuid);
  }

  public boolean hasPermissions(UUID uuid) {
    return permissionsCache.containsKey(uuid);
  }

  public boolean hasPermission(UUID uuid, String permission) {
    if (!permissionsCache.containsKey(uuid)) return false;
    return permissionsCache.get(uuid).contains(permission);
  }

  public void cachePermissions(PlayerTable playerTable) {
    if (playerTable.getPermissions() == null) return;
    final String[] permission = playerTable.getPermissions().split(",");
    for (String player : permission) setPermissionsCache(playerTable.getUniqueId(), player);
  }

  public void deletePermissionsCache(UUID uuid) {
    permissionsCache.remove(uuid);
  }

  public void addPermission(UUID uuid, String permission) {
    final PlayerTable playerTable = cachePlayers.getPlayerTable(uuid);
    if (playerTable.getPermissions() == null) playerTable.setPermissions(permission);
    else playerTable.setPermissions(playerTable.getPermissions() + "," + permission);
    setPermissionsCache(uuid, permission);
    cachePlayers.updatePlayerDatabase(playerTable);
  }

  public void removePermission(UUID uuid, String permission) {
    final PlayerTable playerTable = cachePlayers.getPlayerTable(uuid);
    final Set<String> permissions = Collections.synchronizedSet(new HashSet<>(List.of(playerTable.getPermissions().split(","))));
    permissions.remove(permission);
    if (permissions.isEmpty()) playerTable.setPermissions(null);
    else playerTable.setPermissions(StringUtils.join(permissions, ","));
    removePermissionsCache(uuid, permission);
    cachePlayers.updatePlayerDatabase(playerTable);
  }

  public Set<String> getPermissionsList(UUID uuid) {
    if (!hasPermissions(uuid)) return ConcurrentHashMap.newKeySet();
    return permissionsCache.get(uuid);
  }
}
