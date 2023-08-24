package lee.code.permissions.database;

import lee.code.permissions.Permissions;
import lee.code.permissions.database.cache.CachePlayers;
import lombok.Getter;

public class CacheManager {

    private final Permissions permissions;
    @Getter private final CachePlayers cachePlayers;

    public CacheManager(Permissions permissions, DatabaseManager databaseManager) {
        this.permissions = permissions;
        this.cachePlayers = new CachePlayers(databaseManager);
    }
}
