package lee.code.permissions.managers;

import lee.code.permissions.Permissions;
import lee.code.permissions.lang.Lang;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class StaffChatManager {
  private final Permissions permissions;
  private final Pattern displayNamePattern = Pattern.compile("\\{display-name\\}");
  private final Pattern messagePattern = Pattern.compile("\\{message\\}");
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

  public boolean isStaff(UUID uuid) {
    return permissions.getCacheManager().getCachePlayers().getStaffData().isStaff(uuid);
  }

  private Component parseChatVariables(Player player, Component chatFormat, Component message) {
    Component targetMessage = chatFormat;
    targetMessage = targetMessage.replaceText(createTextReplacementConfig(displayNamePattern, player.displayName()));
    targetMessage = targetMessage.replaceText(createTextReplacementConfig(messagePattern, message));
    return targetMessage;
  }

  private TextReplacementConfig createTextReplacementConfig(Pattern pattern, Component message) {
    return TextReplacementConfig.builder().match(pattern).replacement(message).build();
  }

  public void sendMessage(Player player, Component message) {
    final Component targetMessage = parseChatVariables(player, Lang.STAFF_CHAT.getComponent(null), message);
    for (UUID staff : permissions.getCacheManager().getCachePlayers().getStaffData().getStaff()) {
      final OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(staff);
      if (!offlineTarget.isOnline()) continue;
      final Player onlineTarget = offlineTarget.getPlayer();
      if (onlineTarget == null) continue;
      onlineTarget.sendMessage(targetMessage);
    }
  }
}
