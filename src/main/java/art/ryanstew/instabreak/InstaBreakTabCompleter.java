package art.ryanstew.instabreak;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;


public class InstaBreakTabCompleter implements TabCompleter
{

    /*
        /instabreak and /instabreak help
        /instabreak <toggle/enable/disable>
        /instabreak <toggledrops/enabledrops/disabledrops>
        /instabreak reload
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args)
    {
        List<String> tabOptions = new java.util.ArrayList<>(List.of("help"));

        if (sender instanceof Player)
        {
            tabOptions.addAll(Arrays.asList("toggle", "enable", "disable"));
            tabOptions.addAll(Arrays.asList("toggledrops", "enabledrops", "disabledrops"));
        }

        if (sender.hasPermission(InstaBreak.RELOAD_PERMISSION))
            tabOptions.add("reload");

        return tabOptions;
    }
}
