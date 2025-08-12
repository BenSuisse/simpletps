package ch.bensuisse.simpletps;

import org.bukkit.plugin.java.JavaPlugin;

public final class Simpletps extends JavaPlugin {

    private TPSManager tpsManager;

    @Override
    public void onEnable() {
        getLogger().info("Simpletps wurde aktiviert!");
        this.tpsManager = new TPSManager(this);
        this.getCommand("tps").setExecutor(new TPSCommand(this.tpsManager));
    }

    @Override
    public void onDisable() {
        getLogger().info("Simpletps wurde deaktiviert!");
    }
}