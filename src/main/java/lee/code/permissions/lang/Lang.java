package lee.code.permissions.lang;

import lee.code.permissions.utils.CoreUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;

@AllArgsConstructor
public enum Lang {
    PREFIX("&9&lPermissions &6➔ "),
    USAGE("&6&lUsage: &e{0}"),
    ERROR_ONE_COMMAND_AT_A_TIME("&cYou're currently processing another town command, please wait for it to finish."),
    ERROR_PLAYER_NOT_FOUND("&cThe player &6{0} &ccould not be found."),
    ERROR_NO_PLAYER_DATA("&cCould not find any player data for &6{0}&c."),
    ERROR_NOT_RANK("&cThe input &3{0} &cis not a rank."),
    COMMAND_SET_RANK_SUCCESSFUL("&aYou successfully set &6{0}'s &arank to &3{1}&a!"),

    ;

    @Getter private final String string;

    public String getString(String[] variables) {
        String value = string;
        if (variables == null || variables.length == 0) return value;
        for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
        return value;
    }

    public Component getComponent(String[] variables) {
        String value = string;
        if (variables == null || variables.length == 0) return CoreUtil.parseColorComponent(value);
        for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
        return CoreUtil.parseColorComponent(value);
    }
}
