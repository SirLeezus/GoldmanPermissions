package lee.code.permissions.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;

@SuppressWarnings("deprecation")
@AllArgsConstructor
public enum RankData {
  DEFAULT(null, null, "z", ChatColor.YELLOW, false),
  LEGEND("&e[&6&lLegend&e]", null, "y", ChatColor.GOLD, false),
  ELITE("&d[&5&lElite&d]", null, "x", ChatColor.DARK_PURPLE, false),
  GOD("&a[&2&lGod&a]", null, "w", ChatColor.DARK_GREEN, false),
  MOD("&d[&5&lMod&d]", null, "v", ChatColor.DARK_PURPLE, true),
  ADMIN("&a[&2&lAdmin&a]", null, "u", ChatColor.DARK_GREEN, true),
  OWNER("&4[&c&lOwner&4]", null, "t", ChatColor.RED, true);

  @Getter private final String prefix;
  @Getter private final String suffix;
  @Getter private final String priority;
  @Getter private final ChatColor color;
  @Getter private final boolean isStaffRank;
}
