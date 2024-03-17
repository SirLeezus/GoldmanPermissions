package lee.code.permissions;

import lee.code.permissions.enums.Rank;
import lombok.Getter;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Data {
  @Getter private final Set<String> ranks = ConcurrentHashMap.newKeySet();
  private final ConcurrentHashMap<Rank, Set<String>> rankPermissions = new ConcurrentHashMap<>();

  public Data() {
    storeRankPermissions();
    storeData();
  }

  public Set<String> getRankPermissions(Rank rank) {
    return rankPermissions.get(rank);
  }

  private void setRankPermission(Rank rank, String permission) {
    if (rankPermissions.containsKey(rank)) {
      rankPermissions.get(rank).add(permission);
    } else {
      final Set<String> permissions = ConcurrentHashMap.newKeySet();
      permissions.add(permission);
      rankPermissions.put(rank, permissions);
    }
  }

  private void setRankBulkPermission(Rank rank, Set<String> perms) {
    if (rankPermissions.containsKey(rank)) {
      rankPermissions.get(rank).addAll(perms);
    } else {
      final Set<String> permissions = ConcurrentHashMap.newKeySet();
      permissions.addAll(perms);
      rankPermissions.put(rank, permissions);
    }
  }

  public void storeRankPermissions() {
    for (Rank rank : Rank.values()) {
      switch (rank) {
        case OWNER -> setRankBulkPermission(rank, getRankPermissions(Rank.ADMIN));
        case ADMIN -> {
          setRankPermission(rank, "central.command.money");
          setRankPermission(rank, "central.command.gamemode");
          setRankPermission(rank, "central.command.flyspeed");
          setRankPermission(rank, "central.command.smite");
          setRankPermission(rank, "central.command.enchant");
          setRankPermission(rank, "central.command.give");
          setRankPermission(rank, "central.command.spawner");
          setRankPermission(rank, "central.command.head");
          setRankPermission(rank, "central.command.heal");
          setRankPermission(rank, "central.command.feed");
          setRankPermission(rank, "central.command.clear");
          setRankPermission(rank, "central.command.time");
          setRankPermission(rank, "central.command.world");
          setRankPermission(rank, "central.command.summon");
          setRankBulkPermission(rank, getRankPermissions(Rank.MOD));
        }
        case MOD -> {
          setRankPermission(rank, "central.command.god");
          setRankPermission(rank, "central.command.fly");
          setRankPermission(rank, "central.command.weather");
          setRankPermission(rank, "central.command.enderchest");
          setRankPermission(rank, "central.command.teleporthere");
          setRankPermission(rank, "central.command.vanish");
          setRankPermission(rank, "central.command.teleport");
          setRankPermission(rank, "central.command.teleportpos");
          setRankPermission(rank, "central.command.patrol");
          setRankPermission(rank, "towns.command.admin");
          setRankPermission(rank, "permissions.command.permission");
          setRankPermission(rank, "permissions.command.staffchat");
          setRankPermission(rank, "locks.command.bypass");
          setRankPermission(rank, "punishments.command.ban");
          setRankPermission(rank, "punishments.command.unban");
          setRankPermission(rank, "punishments.command.tempban");
          setRankPermission(rank, "punishments.command.kick");
          setRankPermission(rank, "punishments.command.mute");
          setRankPermission(rank, "punishments.command.tempmute");
          setRankPermission(rank, "punishments.command.unmute");
          setRankPermission(rank, "punishments.command.punishment");
          setRankPermission(rank, "punishments.command.punishments");
          setRankPermission(rank, "punishments.command.cuff");
          setRankPermission(rank, "punishments.command.uncuff");
          setRankPermission(rank, "vulcan.alerts");
          setRankPermission(rank, "vulcan.violations");
          setRankPermission(rank, "coreprotect.inspect");
          setRankPermission(rank, "coreprotect.lookup");
          setRankPermission(rank, "coreprotect.lookup.*");
          setRankPermission(rank, "coreprotect.rollback");
          setRankPermission(rank, "coreprotect.restore");
          setRankPermission(rank, "coreprotect.help");
          setRankBulkPermission(rank, getRankPermissions(Rank.ELITE));
        }
        case ELITE -> {
          setRankPermission(rank, "vault.pages.10");
          setRankPermission(rank, "pets.max.28");
          setRankPermission(rank, "central.homes.30");
          setRankPermission(rank, "trails.particle.redstone_rainbow");
          setRankPermission(rank, "trails.particle.block_crack");
          setRankPermission(rank, "trails.particle.end_rod");
          setRankPermission(rank, "trails.particle.soul_fire_flame");
          setRankPermission(rank, "trails.particle.cherry_leaves");
          setRankPermission(rank, "trails.particle.crit_magic");
          setRankPermission(rank, "trails.particle.dragon_breath");
          setRankPermission(rank, "trails.particle.dripping_obsidian_tear");
          setRankPermission(rank, "trails.particle.end_rod");
          setRankPermission(rank, "trails.style.cube");
          setRankPermission(rank, "trails.style.halo");
          setRankBulkPermission(rank, getRankPermissions(Rank.LEGEND));
        }
        case LEGEND -> {
          setRankPermission(rank, "vault.pages.5");
          setRankPermission(rank, "pets.max.15");
          setRankPermission(rank, "central.homes.15");
          setRankPermission(rank, "central.command.glow");
          setRankPermission(rank, "central.command.color");
          setRankPermission(rank, "central.command.hat");
          setRankPermission(rank, "central.command.sort");
          setRankPermission(rank, "towns.command.fly");
          setRankPermission(rank, "trails.particle.redstone");
          setRankPermission(rank, "trails.particle.potion_effect");
          setRankPermission(rank, "trails.particle.potion_effect_rainbow");
          setRankPermission(rank, "trails.particle.falling_dust");
          setRankPermission(rank, "trails.particle.happy_villager");
          setRankPermission(rank, "trails.particle.villager_angry");
          setRankPermission(rank, "trails.particle.flame");
          setRankPermission(rank, "trails.particle.totem");
          setRankPermission(rank, "trails.particle.wax_on");
          setRankPermission(rank, "trails.particle.wax_off");
          setRankPermission(rank, "trails.particle.heart");
          setRankPermission(rank, "trails.particle.damage_indicator");
          setRankPermission(rank, "trails.particle.ash");
          setRankPermission(rank, "trails.particle.electric_spark");
          setRankPermission(rank, "trails.particle.bubble_pop");
          setRankPermission(rank, "trails.particle.campfire_cosy_smoke");
          setRankPermission(rank, "trails.particle.drip_lava");
          setRankPermission(rank, "trails.particle.drip_water");
          setRankPermission(rank, "trails.particle.rain");
          setRankPermission(rank, "trails.particle.water_drop");
          setRankPermission(rank, "trails.particle.crit");
          setRankPermission(rank, "trails.particle.falling_spore_blossom");
          setRankPermission(rank, "trails.particle.enchantment_table");
          setRankPermission(rank, "trails.particle.fireworks_spark");
          setRankPermission(rank, "trails.particle.scrape");
          setRankPermission(rank, "trails.particle.glow");
          setRankPermission(rank, "trails.particle.glow_squid_ink");
          setRankPermission(rank, "trails.particle.squid_ink");
          setRankPermission(rank, "trails.particle.nautilus");
          setRankPermission(rank, "trails.particle.note");
          setRankPermission(rank, "trails.particle.portal");
          setRankPermission(rank, "trails.particle.slime");
          setRankPermission(rank, "trails.particle.sneeze");
          setRankPermission(rank, "trails.particle.spell_witch");
          setRankPermission(rank, "trails.particle.snowflake");
          setRankPermission(rank, "trails.particle.spit");
          setRankPermission(rank, "trails.particle.cloud");
          setRankPermission(rank, "trails.style.normal");
          setRankPermission(rank, "trails.style.heart");
          setRankPermission(rank, "trails.style.thick");
          setRankPermission(rank, "trails.style.helix");
          setRankPermission(rank, "trails.style.ground_spiral");
          setRankPermission(rank, "trails.style.normal");
          setRankPermission(rank, "trails.style.force_field");
          setRankPermission(rank, "trails.style.orbit");
          setRankPermission(rank, "trails.style.pulse");
          setRankPermission(rank, "trails.style.sphere");
          setRankPermission(rank, "trails.style.damage");
          setRankPermission(rank, "trails.style.projectile");
          setRankPermission(rank, "trails.style.block");
          setRankBulkPermission(rank, getRankPermissions(Rank.DEFAULT));
        }
        case DEFAULT -> {
          setRankPermission(rank, "pets.max.3");
          setRankPermission(rank, "vault.pages.1");
          setRankPermission(rank, "central.homes.5");
          setRankPermission(rank, "vault.command.vault");
          setRankPermission(rank, "resourceworld.command.resourceworld");
          setRankPermission(rank, "pets.command.pets");
          setRankPermission(rank, "trails.command.trails");
          setRankPermission(rank, "shops.command.shop");
          setRankPermission(rank, "shops.command.help");
          setRankPermission(rank, "shops.command.notifications");
          setRankPermission(rank, "shops.command.playershops");
          setRankPermission(rank, "shops.command.removespawn");
          setRankPermission(rank, "shops.command.setspawn");
          setRankPermission(rank, "shops.command.signhelp");
          setRankPermission(rank, "shops.command.spawn");
          setRankPermission(rank, "central.command.balance");
          setRankPermission(rank, "central.command.seen");
          setRankPermission(rank, "central.command.colors");
          setRankPermission(rank, "central.command.balance");
          setRankPermission(rank, "central.command.worth");
          setRankPermission(rank, "central.command.sell");
          setRankPermission(rank, "central.command.sellall");
          setRankPermission(rank, "central.command.pay");
          setRankPermission(rank, "central.command.spawn");
          setRankPermission(rank, "central.command.balancetop");
          setRankPermission(rank, "central.command.stats");
          setRankPermission(rank, "central.command.stattop");
          setRankPermission(rank, "central.command.message");
          setRankPermission(rank, "central.command.reply");
          setRankPermission(rank, "central.command.teleportask");
          setRankPermission(rank, "central.command.randomteleport");
          setRankPermission(rank, "central.command.mail");
          setRankPermission(rank, "central.command.mailbox");
          setRankPermission(rank, "central.command.home");
          setRankPermission(rank, "central.command.homes");
          setRankPermission(rank, "central.command.back");
          setRankPermission(rank, "central.command.playtime");
          setRankPermission(rank, "central.command.ping");
          setRankPermission(rank, "central.command.warp");
          setRankPermission(rank, "central.command.rules");
          setRankPermission(rank, "central.command.help");
          setRankPermission(rank, "central.command.guide");
          setRankPermission(rank, "towns.command.towns");
          setRankPermission(rank, "towns.command.abandon");
          setRankPermission(rank, "towns.command.create");
          setRankPermission(rank, "towns.command.autoclaim");
          setRankPermission(rank, "towns.command.automap");
          setRankPermission(rank, "towns.command.bank");
          setRankPermission(rank, "towns.command.banner");
          setRankPermission(rank, "towns.command.bonusclaim");
          setRankPermission(rank, "towns.command.border");
          setRankPermission(rank, "towns.command.chat");
          setRankPermission(rank, "towns.command.chunkinfo");
          setRankPermission(rank, "towns.command.claim");
          setRankPermission(rank, "towns.command.create");
          setRankPermission(rank, "towns.command.flagmanager");
          setRankPermission(rank, "towns.command.help");
          setRankPermission(rank, "towns.command.info");
          setRankPermission(rank, "towns.command.invite");
          setRankPermission(rank, "towns.command.kick");
          setRankPermission(rank, "towns.command.leave");
          setRankPermission(rank, "towns.command.map");
          setRankPermission(rank, "towns.command.profit");
          setRankPermission(rank, "towns.command.public");
          setRankPermission(rank, "towns.command.rent");
          setRankPermission(rank, "towns.command.role");
          setRankPermission(rank, "towns.command.setbanner");
          setRankPermission(rank, "towns.command.setspawn");
          setRankPermission(rank, "towns.command.spawn");
          setRankPermission(rank, "towns.command.taxes");
          setRankPermission(rank, "towns.command.teleport");
          setRankPermission(rank, "towns.command.top");
          setRankPermission(rank, "towns.command.unclaim");
          setRankPermission(rank, "towns.command.tc");
          setRankPermission(rank, "locks.command.lock");
          setRankPermission(rank, "locks.command.add");
          setRankPermission(rank, "locks.command.help");
          setRankPermission(rank, "locks.command.remove");
          setRankPermission(rank, "locks.command.signhelp");
          setRankPermission(rank, "central.command.buy");
          setRankPermission(rank, "central.command.motd");
          setRankPermission(rank, "votes.command.vote");
          setRankPermission(rank, "votes.command.websites");
          setRankPermission(rank, "votes.command.total");
          setRankPermission(rank, "votes.command.top");
          setRankPermission(rank, "votes.command.help");
        }
      }
    }
  }

  public void storeData() {
    for (Rank rank : Rank.values()) ranks.add(rank.name());
  }
}
