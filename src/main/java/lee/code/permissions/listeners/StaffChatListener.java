package lee.code.permissions.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import lee.code.permissions.Permissions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class StaffChatListener implements Listener {
  private final Permissions permissions;

  public StaffChatListener(Permissions permissions) {
    this.permissions = permissions;
  }

  @EventHandler(priority = EventPriority.NORMAL)
  public void onAsyncStaffChatListener(AsyncChatEvent e) {
    if (e.isCancelled()) return;
    if (permissions.getStaffChatManager().isChatting(e.getPlayer().getUniqueId())) {
      e.setCancelled(true);
      System.out.println("Sending Chat Message");
      permissions.getStaffChatManager().sendMessage(e.getPlayer(), e.message());
    }
  }
}
