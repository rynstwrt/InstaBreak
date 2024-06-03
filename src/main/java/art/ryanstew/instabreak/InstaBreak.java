package art.ryanstew.instabreak;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public final class InstaBreak extends JavaPlugin
{

    private List<Player> enabledPlayers = new ArrayList<>();


    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();

        getServer().getPluginManager().registerEvents(new InstaBreakEventHandler(this), this);

        Objects.requireNonNull(getCommand("instabreak")).setExecutor(new InstaBreakCommand(this));
    }


    @Override
    public void onDisable() { }


    public void sendFormattedMessage(CommandSender sender, String message)
    {
        message = String.format("%s %s", getConfig().getString("prefix"), message);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }


    public boolean playerHasEnabled(Player player)
    {
        return enabledPlayers.contains(player);
    }


    public void setPlayerEnabled(Player player, boolean enable)
    {
        if (enable)
            enabledPlayers.add(player);
        else
            enabledPlayers.remove(player);
    }
}
