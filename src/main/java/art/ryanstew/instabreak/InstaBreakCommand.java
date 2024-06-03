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
            Pattern pattern = Pattern.compile("(^(?:&\\w)+).+");
            Matcher matcher = pattern.matcher(prefix);

            if (matcher.matches())
                colorCode = matcher.group(1);
        }

        boolean isPlayer = sender instanceof Player;

        StringBuilder stringBuilder = new StringBuilder();
        if (!isPlayer)
            stringBuilder.append("&r\n");

        stringBuilder
                .append("&8[------- ").append(colorCode).append("InstaBreak").append("&8 -------]")
                .append("\n&8- &7/instabreak help");

        if (isPlayer)
        {
            stringBuilder
                    .append("\n&8- &7/instabreak <toggle/enable/disable>")
                    .append("\n&8- &7/instabreak <toggledrops/enabledrops/disabledrops>");
        }

        if (sender.hasPermission(InstaBreak.RELOAD_PERMISSION))
            stringBuilder.append("\n&8- &7/instabreak reload");

        if (isPlayer)
        {
            Player player = (Player) sender;

            boolean isEnabled = plugin.playerHasEnabled(player);
            boolean dropsEnabled = plugin.playerHasDropsEnabled(player);
            stringBuilder
                    .append("\n&r\n   &7&lStatus: [").append(isEnabled ? "&a" : "&c").append("&lInstaBreak&7&l] ")
                    .append("&7&l[").append(dropsEnabled ? "&a" : "&c").append("&lDrops&7&l]");
        }

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
        /instabreak <toggle/enable/disable>
        /instabreak <toggledrops/enabledrops/disabledrops>
        /instabreak reload
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args)
    {
        // /instabreak [help]
        if (args.length == 0 || args[0].equalsIgnoreCase("help"))
        {
            plugin.sendFormattedMessage(sender, getCommandHelpMessage(sender), false);
            return true;
        }

        // /instabreak reload
        if (args[0].equalsIgnoreCase("reload") && sender.hasPermission(InstaBreak.RELOAD_PERMISSION))
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

        // /instabreak toggle
        if (args[0].equalsIgnoreCase("toggle"))
        {
            boolean isEnabled = plugin.playerHasEnabled(player);
            plugin.setPlayerEnabled(player, !isEnabled);
            sendSetEnabledMessage(player, !isEnabled);
            return true;
        }

        // /instabreak enable
        if (args[0].equalsIgnoreCase("enable"))
        {
            plugin.setPlayerEnabled(player, true);
            sendSetEnabledMessage(player, true);
            return true;
        }

        // /instabreak disable
        if (args[0].equalsIgnoreCase("disable"))
        {
            plugin.setPlayerEnabled(player, false);
            sendSetEnabledMessage(player, false);
            return true;
        }

        // /instabreak toggledrops
        if (args[0].equalsIgnoreCase("toggledrops"))
        {
            boolean dropsEnabled = plugin.playerHasDropsEnabled(player);
            plugin.setPlayerDropsEnabled(player, !dropsEnabled);
            sendSetDropsEnabledMessage(player, !dropsEnabled);
            return true;
        }

        // /instabreak enabledrops
        if (args[0].equalsIgnoreCase("enabledrops"))
        {
            plugin.setPlayerDropsEnabled(player, true);
            sendSetDropsEnabledMessage(player, true);
            return true;
        }

        // /instabreak disabledrops
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
