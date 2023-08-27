package lee.code.permissions.database.cache;

import lee.code.permissions.database.DatabaseManager;
import lee.code.permissions.database.handlers.DatabaseHandler;
import lee.code.permissions.database.tables.PlayerTable;
import lee.code.permissions.enums.Rank;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CachePlayers extends DatabaseHandler {
  @Getter private final PermissionData permissionData;
  private final ConcurrentHashMap<UUID, PlayerTable> playersCache = new ConcurrentHashMap<>();

  public CachePlayers(DatabaseManager databaseManager) {
    super(databaseManager);
    permissionData = new PermissionData(this);
  }

  public void setPlayerTable(PlayerTable playerTable) {
    playersCache.put(playerTable.getUniqueId(), playerTable);
    permissionData.cachePermissions(playerTable);
  }

  public PlayerTable getPlayerTable(UUID uuid) {
    return playersCache.get(uuid);
  }

  public boolean hasPlayerData(UUID uuid) {
    return playersCache.containsKey(uuid);
  }

  public void createPlayerData(UUID uuid) {
    final PlayerTable playerTable = new PlayerTable(uuid);
    setPlayerTable(playerTable);
    createPlayerDatabase(playerTable);
  }

  public Rank getRank(UUID uuid) {
    return Rank.valueOf(getPlayerTable(uuid).getRank());
  }

  public void setRank(UUID uuid, Rank rank) {
    final PlayerTable playerTable = getPlayerTable(uuid);
    playerTable.setRank(rank.name());
    updatePlayerDatabase(playerTable);
  }
}
