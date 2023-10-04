package lee.code.permissions;

import lee.code.permissions.enums.Rank;
import lombok.Getter;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Data {
  @Getter private final Set<String> ranks = ConcurrentHashMap.newKeySet();
  private final ConcurrentHashMap<Rank, Set<String>> rankPermissions = new ConcurrentHashMap<>();

  public Data() {
    storeRankPermissions();
    storeData();
  }

  public Set<String> getRankPermissions(Rank rank) {
    return rankPermissions.get(rank);
  }

  private void setRankPermission(Rank rank, String permission) {
    if (rankPermissions.containsKey(rank)) {
      rankPermissions.get(rank).add(permission);
    } else {
      final Set<String> permissions = ConcurrentHashMap.newKeySet();
      permissions.add(permission);
      rankPermissions.put(rank, permissions);
    }
  }

  private void setRankBulkPermission(Rank rank, Set<String> perms) {
    if (rankPermissions.containsKey(rank)) {
      rankPermissions.get(rank).addAll(perms);
    } else {
      final Set<String> permissions = ConcurrentHashMap.newKeySet();
      permissions.addAll(perms);
      rankPermissions.put(rank, permissions);
    }
  }

  public void storeRankPermissions() {
    for (Rank rank : Rank.values()) {
      switch (rank) {
        case OWNER -> {
          setRankPermission(rank, "central.command.llll");
          setRankBulkPermission(rank, getRankPermissions(Rank.ADMIN));
        }
        case ADMIN -> {
          setRankPermission(rank, "central.command.ggg");
          setRankBulkPermission(rank, getRankPermissions(Rank.MOD));
        }
        case MOD -> {
          setRankPermission(rank, "central.command.aaaa");
          setRankBulkPermission(rank, getRankPermissions(Rank.GOD));
        }
        case GOD -> {
          setRankPermission(rank, "central.command.ssss");
          setRankBulkPermission(rank, getRankPermissions(Rank.ELITE));
        }
        case ELITE -> {
          setRankPermission(rank, "central.command.something");
          setRankPermission(rank, "vault.pages.20");
          setRankPermission(rank, "pets.max.20");

          setRankBulkPermission(rank, getRankPermissions(Rank.LEGEND));
        }
        case LEGEND -> {
          setRankPermission(rank, "central.command.color");
          setRankPermission(rank, "vault.pages.10");
          setRankPermission(rank, "pets.max.10");

          setRankBulkPermission(rank, getRankPermissions(Rank.DEFAULT));
        }
        case DEFAULT -> {
          setRankPermission(rank, "pets.max.3");
          setRankPermission(rank, "vault.pages.2");
          setRankPermission(rank, "vault.command.vault");
          setRankPermission(rank, "central.command.balance");
          setRankPermission(rank, "towns.command.use");
          setRankPermission(rank, "towns.command.help");
          setRankPermission(rank, "towns.command.create");
        }
      }
    }
  }

  public void storeData() {
    for (Rank rank : Rank.values()) ranks.add(rank.name());
  }
}
