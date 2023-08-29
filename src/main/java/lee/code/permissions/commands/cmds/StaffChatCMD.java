package lee.code.permissions.commands.cmds;

import lee.code.permissions.Permissions;
import lee.code.permissions.commands.CustomCommand;
import lee.code.permissions.lang.Lang;
import lee.code.permissions.managers.StaffChatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StaffChatCMD extends CustomCommand {
  private final Permissions permissions;

  public StaffChatCMD(Permissions permissions) {
    this.permissions = permissions;
  }

  @Override
  public String getName() {
    return "staffchat";
  }

  @Override
  public boolean performAsync() {
    return true;
  }

  @Override
  public boolean performAsyncSynchronized() {
    return true;
  }

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
    permissions.getCommandManager().perform(sender, args, this, command);
    return true;
  }

  @Override
  public void perform(Player player, String[] args, Command command) {
    final UUID uuid = player.getUniqueId();
    final StaffChatManager staffChatManager = permissions.getStaffChatManager();
    if (staffChatManager.isChatting(uuid)) {
      staffChatManager.removeChatter(uuid);
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_STAFF_CHAT_SUCCESSFUL.getComponent(new String[]{Lang.OFF.getString()})));
    } else {
      staffChatManager.addChatter(uuid);
      player.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_STAFF_CHAT_SUCCESSFUL.getComponent(new String[]{Lang.ON.getString()})));
    }
  }

  @Override
  public void performConsole(CommandSender console, String[] args, Command command) {
    console.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_CONSOLE_COMMAND.getComponent(null)));
  }

  @Override
  public void performSender(CommandSender sender, String[] args, Command command) {
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    return new ArrayList<>();
  }
}
