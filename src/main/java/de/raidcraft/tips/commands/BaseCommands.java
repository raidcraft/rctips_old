package de.raidcraft.tips.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.NestedCommand;
import de.raidcraft.tips.TipsPlugin;
import org.bukkit.command.CommandSender;

/**
 * @author Silthus
 */
public class BaseCommands {

    private final TipsPlugin plugin;

    public BaseCommands(TipsPlugin plugin) {

        this.plugin = plugin;
    }

    @Command(
            aliases = {"rcta"},
            desc = "Base admin command for tips"
    )
    @NestedCommand(AdminCommands.class)
    public void tips(CommandContext args, CommandSender sender) throws CommandException {


    }

    @Command(
            aliases = {"tips", "tip", "rct"},
            desc = "Base player command for tips"
    )
    @NestedCommand(PlayerCommands.class)
    public void players(CommandContext args, CommandSender sender) {


    }
}