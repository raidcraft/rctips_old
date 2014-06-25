package de.raidcraft.tips.templates;

import de.raidcraft.RaidCraft;
import de.raidcraft.tips.TipManager;
import de.raidcraft.tips.api.AbstractTipTemplate;
import de.raidcraft.tips.api.Tip;
import de.raidcraft.util.TimeUtil;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author mdoering
 */
public class YamlTipTemplate extends AbstractTipTemplate {

    public YamlTipTemplate(String identifier, ConfigurationSection config) {

        super(identifier);
        setName(config.getString("name", getIdentifier()));
        setDescription(config.getString("description", "undefined"));
        setPersistant(config.getBoolean("persistant", true));
        setRepeating(config.getBoolean("repeating", false));
        setCooldown(TimeUtil.secondsToMillis(config.getDouble("cooldown", 300.0)));
    }

    @Override
    public <T> Tip<T> createTip(T entity) {

        return RaidCraft.getComponent(TipManager.class).createTip(this, entity);
    }
}
