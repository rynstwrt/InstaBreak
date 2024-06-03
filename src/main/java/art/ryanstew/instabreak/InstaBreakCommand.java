package art.ryanstew.instabreak;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class InstaBreakCommand implements CommandExecutor
{

    private static final String RELOAD_PERMISSION = "instabreak.reload";

    private final InstaBreak plugin;


    public InstaBreakCommand(InstaBreak plugin)
    {
        this.plugin = plugin;
    }


    private String getCommandHelpMessage(CommandSender sender)
    {
        String prefix = plugin.getConfig().getString("prefix");
        String colorCode = "&c";

        if (prefix != null)
        {
            Pattern pattern = Pattern.compile("^(?:&\\w)+");
            Matcher matcher = pattern.matcher(prefix);

            if (matcher.matches())
                colorCode = matcher.group(0);
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("&8[------- ").append(colorCode).append("InstaBreak").append("&8 -------]")
                .append("\n").append("&8- ").append("&7/instabreak help");

        if (sender instanceof Player)
        {
            stringBuilder
                    .append("\n").append("&8- ").append("&7/instabreak toggle")
                    .append("\n").append("&8- ").append("&7/instabreak <on/enable>")
                    .append("\n").append("&8- ").append("&7/instabreak <off/disable>")
                    .append("\n")
                    .append("\n").append("&8- ").append("&7/instabreak toggledrops")
                    .append("\n").append("&8- ").append("&7/instabreak enabledrops")
                    .append("\n").append("&8- ").append("&7/instabreak disabledrops");
        }

        if (sender.hasPermission(RELOAD_PERMISSION))
            stringBuilder.append("\n").append("&8- ").append("&7/instabreak reload");

        return stringBuilder.toString();
    }


    private void sendSetEnabledMessage(Player player, boolean enabled)
    {
        plugin.sendFormattedMessage(player, String.format("&7Successfully turned instabreak %s&7.", (enabled ? "&aON" : "&cOFF")), true);
    }


    private void sendSetDropsEnabledMessage(Player player, boolean enabled)
    {
        plugin.sendFormattedMessage(player, String.format("&7Successfully turned block drops %s&7.", (enabled ? "&aON" : "&cOFF")), true);
    }


    /*
        /instabreak and /instabreak help
        /instabreak toggle
        /instabreak <on/enable>
        /instabreak <off/disable>
        /instabreak toggledrops
        /instabreak enabledrops
        /instabraek disabledrops
        /instabreak reload
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args)
    {
        if (args.length == 0 || args[0].equalsIgnoreCase("help"))
        {
            plugin.sendFormattedMessage(sender, getCommandHelpMessage(sender), false);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload") && sender.hasPermission(RELOAD_PERMISSION))
        {
            plugin.reloadConfig();
            plugin.sendFormattedMessage(sender, "&aSuccessfully reloaded the config!", true);
            return true;
        }

        if (!(sender instanceof Player player))
        {
            plugin.sendFormattedMessage(sender, "&cOnly players can run this command!", true);
            return true;
        }

        if (args[0].equalsIgnoreCase("toggle"))
        {
            boolean isEnabled = plugin.playerHasEnabled(player);
            plugin.setPlayerEnabled(player, !isEnabled);
            sendSetEnabledMessage(player, !isEnabled);
            return true;
        }

        if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("enable"))
        {
            plugin.setPlayerEnabled(player, true);
            sendSetEnabledMessage(player, true);
            return true;
        }

        if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("disable"))
        {
            plugin.setPlayerEnabled(player, false);
            sendSetEnabledMessage(player, false);
            return true;
        }

        if (args[0].equalsIgnoreCase("toggledrops"))
        {
            boolean dropsEnabled = plugin.playerHasDropsEnabled(player);
            plugin.setPlayerDropsEnabled(player, !dropsEnabled);
            sendSetDropsEnabledMessage(player, !dropsEnabled);
            return true;
        }

        if (args[0].equalsIgnoreCase("enabledrops"))
        {
            plugin.setPlayerDropsEnabled(player, true);
            sendSetDropsEnabledMessage(player, true);
            return true;
        }

        if (args[0].equalsIgnoreCase("disabledrops"))
        {
            plugin.setPlayerDropsEnabled(player, false);
            sendSetDropsEnabledMessage(player, false);
            return true;
        }

        plugin.sendFormattedMessage(sender, getCommandHelpMessage(sender), false);
        return true;
    }
}
