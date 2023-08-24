package lee.code.permissions.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;

@SuppressWarnings("deprecation")
@AllArgsConstructor
public enum RankData {
    DEFAULT(null, null, "z", ChatColor.YELLOW),
    LEGEND("&e[&6&lLegend&e]", null, "y", ChatColor.GOLD),
    ELITE("&d[&5&lElite&d]", null, "x", ChatColor.DARK_PURPLE),
    GOD("&a[&2&lGod&a]", null, "w", ChatColor.DARK_GREEN),
    MOD("&d[&5&lMod&d]", null, "v", ChatColor.DARK_PURPLE),
    ADMIN("&a[&2&lAdmin&a]", null, "u", ChatColor.DARK_GREEN),
    OWNER("&4[&c&lOwner&4]", null, "t", ChatColor.RED)
    ;

    @Getter private final String prefix;
    @Getter private final String suffix;
    @Getter private final String priority;
    @Getter private final ChatColor color;
}
