package xyz.lncvrt.setmaxplayers;

import org.bukkit.plugin.java.JavaPlugin;

public final class SetMaxPlayers extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("setmaxplayers").setExecutor(new SetMaxPlayersCommand(this));
    }
}
