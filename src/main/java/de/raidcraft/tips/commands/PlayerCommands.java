package de.raidcraft.tips.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import de.raidcraft.tips.TipsPlugin;
import de.raidcraft.tips.tables.TTipPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author mdoering
 */
public class PlayerCommands {

    private final TipsPlugin plugin;

    public PlayerCommands(TipsPlugin plugin) {

        this.plugin = plugin;
    }

    @Command(
            aliases = {"toggle"},
            desc = "Toggles the tip display on and off."
    )
    public void toggle(CommandContext args, CommandSender sender) {

        TTipPlayer player = plugin.getTipManager().loadDatabasePlayer((Player) sender);
        player.setEnabled(!player.isEnabled());
        plugin.getRcDatabase().update(player);
        if (player.isEnabled()) {
            plugin.getTranslationProvider().msg(sender, "tips.enabled", ChatColor.GREEN + "Dir werden nun Tips angezeigt.");
        } else {
            plugin.getTranslationProvider().msg(sender, "tips.disabled", ChatColor.RED + "Dir werden keine Tips mehr angezeigt.");
        }
    }
}
