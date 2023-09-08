package lee.code.permissions.commands.cmds;

import lee.code.colors.ColorAPI;
import lee.code.permissions.Permissions;
import lee.code.permissions.commands.CustomCommand;
import lee.code.permissions.database.CacheManager;
import lee.code.permissions.enums.Rank;
import lee.code.permissions.enums.RankData;
import lee.code.permissions.lang.Lang;
import lee.code.permissions.utils.CoreUtil;
import lee.code.playerdata.PlayerDataAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SetRankCMD extends CustomCommand {
  private final Permissions permissions;

  public SetRankCMD(Permissions permissions) {
    this.permissions = permissions;
  }

  @Override
  public String getName() {
    return "setrank";
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
    performSender(player, args, command);
  }

  @Override
  public void performConsole(CommandSender console, String[] args, Command command) {
    performSender(console, args, command);
  }

  @Override
  public void performSender(CommandSender sender, String[] args, Command command) {
    if (args.length < 2) {
      sender.sendMessage(Lang.USAGE.getComponent(new String[]{command.getUsage()}));
      return;
    }
    final CacheManager cacheManager = permissions.getCacheManager();
    final String targetString = args[0];
    final UUID targetID = PlayerDataAPI.getUniqueId(targetString);
    if (targetID == null) {
      sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NO_PLAYER_DATA.getComponent(new String[]{targetString})));
      return;
    }
    final String rankString = args[1].toUpperCase();
    if (!permissions.getData().getRanks().contains(rankString)) {
      sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.ERROR_NOT_RANK.getComponent(new String[]{rankString})));
      return;
    }
    final OfflinePlayer offlineTarget = Bukkit.getOfflinePlayer(targetID);
    final Rank rank = Rank.valueOf(rankString);
    final RankData rankData = RankData.valueOf(rankString);
    cacheManager.getCachePlayers().setRank(targetID, rank);
    ColorAPI.setPlayerColorData(offlineTarget, rankData.getPrefix(), rankData.getSuffix(), rankData.getPriority(), rankData.getColor());
    if (offlineTarget.isOnline()) {
      final Player onlineTarget = offlineTarget.getPlayer();
      if (onlineTarget != null) permissions.getPermissionManager().registerPermissions(onlineTarget);
    }
    sender.sendMessage(Lang.PREFIX.getComponent(null).append(Lang.COMMAND_SET_RANK_SUCCESSFUL.getComponent(new String[]{ColorAPI.getNameColor(targetID, offlineTarget.getName()), CoreUtil.capitalize(rankString)})));
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    if (args.length == 1) return StringUtil.copyPartialMatches(args[0], CoreUtil.getOnlinePlayers(), new ArrayList<>());
    else if (args.length == 2)
      return StringUtil.copyPartialMatches(args[1], permissions.getData().getRanks(), new ArrayList<>());
    else return new ArrayList<>();
  }
}
