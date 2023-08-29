package lee.code.permissions.managers;

import lee.code.colors.ColorAPI;
import lee.code.permissions.Permissions;
import lee.code.permissions.lang.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class StaffChatManager {
  private final Permissions permissions;
  private final Set<UUID> staffChatters = ConcurrentHashMap.newKeySet();

  public StaffChatManager(Permissions permissions) {
    this.permissions = permissions;
  }

  public void addChatter(UUID uuid) {
    staffChatters.add(uuid);
  }

  public void removeChatter(UUID uuid) {
    staffChatters.remove(uuid);
  }

  public boolean isChatting(UUID uuid) {
    return staffChatters.contains(uuid);
  }

  public void sendMessage(Player player, Component message) {
    final Component targetMessage = Lang.STAFF_CHAT.getComponent(new String[] {ColorAPI.getNameColor(player.getUniqueId(), player.getName())}).append(message.color(NamedTextColor.GOLD));
    for (UUID staff : permissions.getCacheManager().getCachePlayers().getStaffData().getStaff()) {
      final OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(staff);
      if (!offlineTarget.isOnline()) continue;
      final Player onlineTarget = offlineTarget.getPlayer();
      if (onlineTarget == null) continue;
      onlineTarget.sendMessage(targetMessage);
    }
  }
}
