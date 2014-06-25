package de.raidcraft.tips.displays;

import de.raidcraft.tips.api.TipDisplay;
import de.raidcraft.tips.api.TipTemplate;
import org.bukkit.entity.Player;

/**
 * @author mdoering
 */
public class ChatDisplay implements TipDisplay<Player> {

    @Override
    public Class<Player> getType() {

        return Player.class;
    }

    @Override
    public void display(TipTemplate tip, Player entity) {


    }
}
