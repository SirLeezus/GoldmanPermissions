package lee.code.permissions.listeners;

import lee.code.permissions.Permissions;
import lee.code.permissions.database.CacheManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    private final Permissions permissions;

    public JoinListener(Permissions permissions) {
        this.permissions = permissions;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final CacheManager cacheManager = permissions.getCacheManager();
        final UUID uuid = e.getPlayer().getUniqueId();
        if (!cacheManager.getCachePlayers().hasPlayerData(uuid)) cacheManager.getCachePlayers().createPlayerData(uuid);
        permissions.getPermissionManager().registerPermissions(e.getPlayer());
    }
}
