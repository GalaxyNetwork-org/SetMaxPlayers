package xyz.lncvrt.setmaxplayers;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static org.bukkit.Bukkit.getServer;

public class SetMaxPlayersCommand implements CommandExecutor {
    private final SetMaxPlayers plugin;

    public SetMaxPlayersCommand(SetMaxPlayers plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length != 1) {
            commandSender.sendMessage(ChatColor.RED + "Correct usage: /setmaxplayers <amount>");
            return true;
        }
        int newMaxPlayers;
        try {
            newMaxPlayers = Integer.parseInt(strings[0]);
        } catch (NumberFormatException e) {
            commandSender.sendMessage(ChatColor.RED + "Failed to parse value! Correct usage: /setmaxplayers <amount>");
            return true;
        }
        plugin.getServer().setMaxPlayers(newMaxPlayers);

        Properties props = new Properties();
        Path path = Paths.get("server.properties");

        try (var input = Files.newInputStream(path)) {
            props.load(input);
        } catch (IOException e) {
            plugin.getLogger().info(ChatColor.RED + "Failed to load server.properties file");
            getServer().getPluginManager().disablePlugin(plugin);
            throw new RuntimeException(e);
        }

        props.setProperty("max-players", String.valueOf(newMaxPlayers));

        try (var output = new FileOutputStream(path.toFile())) {
            props.store(output, null);
        } catch (IOException e) {
            plugin.getLogger().info(ChatColor.RED + "Failed to write to server.properties file");
            getServer().getPluginManager().disablePlugin(plugin);
            throw new RuntimeException(e);
        }

        commandSender.sendMessage(ChatColor.GREEN + "The server is now capped to %s players!".formatted(newMaxPlayers));
        return true;
    }
}