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

        super("chat", Player.class);
    }

    @Override
    public void display(TipTemplate tip, Player entity) {

        String[] lines = tip.getDescription().split("\\|");
        if (lines.length > 0) {
            entity.sendMessage(ChatColor.AQUA + tip.getName() + ": " + ChatColor.RESET + ChatColor.YELLOW + lines[0]);
            if (lines.length > 1) {
                for (int i = 1; i < lines.length; i++) {
                    entity.sendRawMessage(ChatColor.YELLOW + lines[1]);
                }
            }
        }

    }
}
