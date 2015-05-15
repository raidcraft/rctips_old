package de.raidcraft.tips.templates;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * @author mdoering
 */
public class PlayerTipTemplate extends YamlTipTemplate<Player> {

    public PlayerTipTemplate(String identifier, ConfigurationSection config) {

        super(identifier, config);
    }

    @Override
    public Class<Player> getTriggerEntityType() {

        return Player.class;
    }
}
