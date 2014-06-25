package de.raidcraft.tips.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import de.raidcraft.api.config.SimpleConfiguration;
import de.raidcraft.tips.TipsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * @author mdoering
 */
public class AdminCommands {

    private final TipsPlugin plugin;

    public AdminCommands(TipsPlugin plugin) {

        this.plugin = plugin;
    }

    @Command(
            aliases = {"reload"},
            desc = "Reloads all tips from disk."
    )
    @CommandPermissions("rctips.reload")
    public void reload(CommandContext args, CommandSender sender) {

        plugin.reload();
        sender.sendMessage(ChatColor.GREEN + "Tips were reloaded. See console for details...");
    }

    @Command(
            aliases = {"create"},
            desc = "Creates a tip at the given location.",
            min = 3,
            flags = "ec:r:"
    )
    @CommandPermissions("rctips.create")
    public void create(CommandContext args, CommandSender sender) throws CommandException {

        if (!(sender instanceof Player)) {
            throw new CommandException("Must be executed as player");
        }
        File dir = new File(plugin.getDataFolder(), "tips");
        SimpleConfiguration<TipsPlugin> config = new SimpleConfiguration<>(plugin, new File(dir, args.getString(0) + ".yml"));
        config.set("name", args.getString(1));
        config.set("desc", args.getJoinedStrings(2));
        config.set("enabled", args.hasFlag('e'));
        config.set("cooldown", args.getFlagInteger('c', 0));
        config.set("trigger.0.type", "player.move");
        Location location = ((Player) sender).getLocation();
        config.set("trigger.0.world", location.getWorld().getName());
        config.set("trigger.0.x", location.getBlockX());
        config.set("trigger.0.y", location.getBlockY());
        config.set("trigger.0.z", location.getBlockZ());
        config.set("trigger.0.radius", args.getFlagInteger('r', 0));
        config.set("meta.creator", sender.getName());
        config.set("meta.location.world", location.getWorld().getName());
        config.set("meta.location.x", location.getBlockX());
        config.set("meta.location.y", location.getBlockY());
        config.set("meta.location.z", location.getBlockZ());
        config.save();

        sender.sendMessage(ChatColor.GREEN + "Created tip config in " + dir.getPath());
        if (args.hasFlag('e')) sender.sendMessage(ChatColor.RED + "The tip will be loaded on the next /rcta reload!");
    }

}
