package ch.bensuisse.simpletps;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TPSCommand implements CommandExecutor {

    private final TPSManager tpsManager;

    public TPSCommand(TPSManager tpsManager) {
        this.tpsManager = tpsManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
            return true;
        }

        Player player = (Player) sender;
        tpsManager.togglePlayer(player);
        return true;
    }
}