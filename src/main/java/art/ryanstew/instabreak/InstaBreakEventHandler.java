package art.ryanstew.instabreak;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;


public class InstaBreakEventHandler implements Listener
{

    private final InstaBreak plugin;


    public InstaBreakEventHandler(InstaBreak plugin)
    {
        this.plugin = plugin;
    }


    @EventHandler
    private void onBlockDamage(BlockDamageEvent event)
    {
        if (plugin.playerHasEnabled(event.getPlayer()))
            event.setInstaBreak(true);
    }


    @EventHandler
    private void onBlockBreak(BlockBreakEvent event)
    {
        if (!plugin.playerHasEnabled(event.getPlayer()))
            return;

        boolean shouldDropItems = plugin.getConfig().getBoolean("should-drop-items");
        if (!shouldDropItems)
            event.setDropItems(false);
    }
}
