package lee.code.permissions.listeners;

import lee.code.permissions.Permissions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.UUID;

public class TabCompleteListener implements Listener {
  private final Permissions permissions;

  public TabCompleteListener(Permissions permissions) {
    this.permissions = permissions;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onCommandTabShow(PlayerCommandSendEvent e) {
    if (e.getPlayer().isOp()) return;
    e.getCommands().clear();
    final UUID uuid = e.getPlayer().getUniqueId();
    if (permissions.getPermissionManager().hasCommands(uuid)) {
      e.getCommands().addAll(permissions.getPermissionManager().getCommands(uuid));
    }
  }
}
