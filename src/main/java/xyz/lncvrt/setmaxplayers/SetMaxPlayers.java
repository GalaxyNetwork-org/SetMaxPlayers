package xyz.lncvrt.setmaxplayers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class SetMaxPlayers extends JavaPlugin {
    public FileConfiguration config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        getCommand("setmaxplayers").setExecutor(new SetMaxPlayersCommand(this));
    }
}
