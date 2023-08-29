package lee.code.permissions.listeners;

import lee.code.permissions.Permissions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class QuitListener implements Listener {
  private final Permissions permissions;

  public QuitListener(Permissions permissions) {
    this.permissions = permissions;
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent e) {
    final UUID uuid = e.getPlayer().getUniqueId();
    permissions.getStaffChatManager().removeChatter(uuid);
    permissions.getPermissionManager().deleteCommandData(uuid);
  }
}
