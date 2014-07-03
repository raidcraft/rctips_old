package de.raidcraft.tips.displays;

import de.raidcraft.tips.api.AbstractTipDisplay;
import de.raidcraft.tips.api.TipTemplate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author mdoering
 */
public class ChatDisplay extends AbstractTipDisplay<Player> {

    public ChatDisplay() {

        super("chat");
    }

    @Override
    public Class<Player> getType() {

        return Player.class;
    }

    @Override
    public void display(TipTemplate tip, Player entity) {

        entity.sendMessage(ChatColor.AQUA + tip.getName() + ": "
                + ChatColor.RESET + ChatColor.YELLOW + tip.getDescription());
    }
}
