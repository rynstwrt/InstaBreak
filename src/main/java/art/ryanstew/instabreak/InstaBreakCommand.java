package art.ryanstew.instabreak;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class InstaBreakCommand implements CommandExecutor
{

    private static final String RELOAD_PERMISSION = "instabreak.reload";

    private final InstaBreak plugin;


    public InstaBreakCommand(InstaBreak plugin)
    {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args)
    {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload") && sender.hasPermission(RELOAD_PERMISSION))
        {
            plugin.reloadConfig();
            plugin.sendFormattedMessage(sender, "&aSuccessfully reloaded the config!");
            return true;
        }

        if (!(sender instanceof Player player))
        {
            plugin.sendFormattedMessage(sender, "&cOnly players can run this command!");
            return true;
        }

        boolean isEnabled = plugin.playerHasEnabled(player);
        plugin.setPlayerEnabled(player, !isEnabled);

        plugin.sendFormattedMessage(player, String.format("&7Successfully turned instabreak %s&7.", (isEnabled ? "&cOFF" : "&aON")));
        return true;
    }
}
