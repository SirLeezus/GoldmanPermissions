package lee.code.permissions.lang;

import lee.code.permissions.utils.CoreUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;

@AllArgsConstructor
public enum Lang {
  PREFIX("&#1ECCFF&lPermissions &6➔ "),
  USAGE("&6&lUsage: &e{0}"),
  TRUE("&2&lTrue"),
  FALSE("&c&lFalse"),
  ON("&2&lON"),
  OFF("&c&lOFF"),
  STAFF_CHAT("&e[&cSC&e] {display-name}&7: &#F97C7C{message}"),
  COMMAND_STAFF_CHAT_SUCCESSFUL("&aSuccessfully toggled staff chat {0}&a!"),
  COMMAND_SET_RANK_SUCCESSFUL("&aSuccessfully set &6{0}'s &arank to &3{1}&a!"),
  COMMAND_PERMISSION_ADD_SUCCESSFUL("&aThe permission &3{0} &ahas successfully been added to the player &6{1}&a!"),
  COMMAND_PERMISSION_REMOVE_SUCCESSFUL("&aThe permission &3{0} &ahas successfully been removed from the player &6{1}&a!"),
  COMMAND_PERMISSION_CHECK_SUCCESSFUL("&aPermission Check&7: &6{0}&a &7| &aTarget Permission&7: &3{1} &7| &aResult&7: &3{2}"),
  ERROR_ONE_COMMAND_AT_A_TIME("&cYou're currently processing another command, please wait for it to finish."),
  ERROR_PLAYER_NOT_FOUND("&cThe player &6{0} &ccould not be found."),
  ERROR_NO_PLAYER_DATA("&cCould not find any player data for &6{0}&c."),
  ERROR_NOT_RANK("&cThe input &3{0} &cis not a rank."),
  ERROR_PERMISSION_REMOVE_NOT_OWNED("&cThe player &6{0} &cdoes not have the permission &3{1}&c."),
  ERROR_PERMISSION_ADD_ALREADY_OWNED("&cThe player &6{0} &calready has the permission &3{1}&c."),
  ERROR_NOT_CONSOLE_COMMAND("&cThis command does not work in console."),
  ERROR_NOT_STAFF_MEMBER("&cYou do not have a staff rank so you can't use this command."),
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
