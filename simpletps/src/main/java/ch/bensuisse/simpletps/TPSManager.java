package ch.bensuisse.simpletps;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TPSManager {

    private final Simpletps plugin;
    private final Set<UUID> toggledPlayers = new HashSet<>();

    public TPSManager(Simpletps plugin) {
        this.plugin = plugin;
        startTpsTask();
    }

    public void togglePlayer(Player player) {
        UUID playerId = player.getUniqueId();
        if (toggledPlayers.contains(playerId)) {
            toggledPlayers.remove(playerId);
            player.sendMessage(Component.text("TPS-Anzeige ", NamedTextColor.GRAY)
                    .append(Component.text("deaktiviert", NamedTextColor.RED))
                    .append(Component.text(".", NamedTextColor.GRAY)));
        } else {
            toggledPlayers.add(playerId);
            player.sendMessage(Component.text("TPS-Anzeige ", NamedTextColor.GRAY)
                    .append(Component.text("aktiviert", NamedTextColor.GREEN))
                    .append(Component.text(".", NamedTextColor.GRAY)));
        }
    }

    private void startTpsTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (toggledPlayers.isEmpty()) {
                    return;
                }

                double tps = Bukkit.getServer().getTPS()[0];
                double mspt = Bukkit.getServer().getAverageTickTime();

                Component tpsMessage = Component.text("TPS: ", NamedTextColor.GRAY)
                        .append(formatTps(tps))
                        .append(Component.text(" | ", NamedTextColor.DARK_GRAY))
                        .append(Component.text("MSPT: ", NamedTextColor.GRAY))
                        .append(formatMspt(mspt));

                for (UUID playerId : toggledPlayers) {
                    Player player = Bukkit.getPlayer(playerId);
                    if (player != null && player.isOnline()) {
                        player.sendActionBar(tpsMessage);
                    }
                }
            }
        }.runTaskTimerAsynchronously(plugin, 20L, 20L);
    }

    private Component formatTps(double tps) {
        NamedTextColor color = (tps >= 18.0) ? NamedTextColor.GREEN : (tps >= 15.0) ? NamedTextColor.YELLOW : NamedTextColor.RED;
        return Component.text(String.format("%.2f", Math.min(tps, 20.0)), color);
    }

    private Component formatMspt(double mspt) {
        NamedTextColor color = (mspt <= 40.0) ? NamedTextColor.GREEN : (mspt <= 50.0) ? NamedTextColor.YELLOW : NamedTextColor.RED;
        return Component.text(String.format("%.2f By BenSuisse", mspt), color);
    }
}