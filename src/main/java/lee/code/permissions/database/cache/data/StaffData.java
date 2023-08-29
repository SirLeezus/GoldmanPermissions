package lee.code.permissions.database.cache.data;

import lee.code.permissions.database.tables.PlayerTable;
import lee.code.permissions.enums.RankData;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class StaffData {
  private final Set<UUID> staffCache = ConcurrentHashMap.newKeySet();

  public void cacheStaff(PlayerTable playerTable) {
    if (RankData.valueOf(playerTable.getRank()).isStaffRank()) {
      staffCache.add(playerTable.getUniqueId());
    } else staffCache.remove(playerTable.getUniqueId());
  }

  public Set<UUID> getStaff() {
    return staffCache;
  }

  public boolean isStaff(UUID uuid) {
    return staffCache.contains(uuid);
  }
}
