package art.ryanstew.instabreak;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public final class InstaBreak extends JavaPlugin
{

    public static final String RELOAD_PERMISSION = "instabreak.reload";

    private final List<Player> enabledPlayers = new ArrayList<>();
    private final List<Player> dropsDisabledPlayers = new ArrayList<>();


    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();

        getServer().getPluginManager().registerEvents(new InstaBreakEventHandler(this), this);

        PluginCommand command = Objects.requireNonNull(getCommand("instabreak"));
        command.setExecutor(new InstaBreakCommand(this));
        command.setTabCompleter(new InstaBreakTabCompleter());
    }


    @Override
    public void onDisable() { }


    public void sendFormattedMessage(CommandSender sender, String message, boolean prefixed)
    {
        if (prefixed)
            message = String.format("%s %s", getConfig().getString("prefix"), message);

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }


    public boolean playerHasEnabled(Player player)
    {
        return enabledPlayers.contains(player);
    }


    public boolean playerHasDropsEnabled(Player player)
    {
        return !dropsDisabledPlayers.contains(player);
    }


    public void setPlayerEnabled(Player player, boolean enable)
    {
        if (enable && !enabledPlayers.contains(player)) enabledPlayers.add(player);
        else if (!enable) enabledPlayers.remove(player);
    }


    public void setPlayerDropsEnabled(Player player, boolean enable)
    {
        if (!enable && !dropsDisabledPlayers.contains(player)) dropsDisabledPlayers.add(player);
        else if (enable) dropsDisabledPlayers.remove(player);
    }
}
