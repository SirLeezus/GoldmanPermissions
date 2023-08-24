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
                    setRankBulkPermission(rank, getRankPermissions(Rank.LEGEND));
                }
                case LEGEND -> {
                    setRankPermission(rank, "central.command.color");
                    setRankBulkPermission(rank, getRankPermissions(Rank.DEFAULT));
                }
                case DEFAULT -> {
                    setRankPermission(rank, "central.command.balance");
                }
            }
        }
    }

    public void storeData() {
        for (Rank rank : Rank.values()) ranks.add(rank.name());
    }
}
