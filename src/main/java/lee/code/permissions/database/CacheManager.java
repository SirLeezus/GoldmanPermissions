package lee.code.permissions.database;

import lee.code.permissions.Permissions;
import lee.code.permissions.database.cache.CachePlayers;
import lee.code.permissions.enums.Rank;
import lombok.Getter;

import java.util.UUID;

public class CacheManager {

    private final Permissions permissions;
    @Getter private final CachePlayers cachePlayers;

    public CacheManager(Permissions permissions, DatabaseManager databaseManager) {
        this.permissions = permissions;
        this.cachePlayers = new CachePlayers(databaseManager);
    }

    public boolean hasGlobalPermission(UUID uuid, String permission) {
        boolean hasPermission = cachePlayers.getPermissionData().hasPermission(uuid, permission);
        final Rank rank = cachePlayers.getRank(uuid);
        if (permissions.getData().getRankPermissions(rank).contains(permission) && !hasPermission) hasPermission = true;
        return hasPermission;
    }
}
