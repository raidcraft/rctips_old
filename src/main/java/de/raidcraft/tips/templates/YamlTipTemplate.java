package de.raidcraft.tips.templates;

import de.raidcraft.RaidCraft;
import de.raidcraft.api.action.ActionAPI;
import de.raidcraft.tips.TipManager;
import de.raidcraft.tips.api.AbstractTipTemplate;
import de.raidcraft.tips.api.Tip;
import de.raidcraft.tips.api.TipDisplay;
import de.raidcraft.util.TimeUtil;
import org.bukkit.configuration.ConfigurationSection;

import java.util.stream.Collectors;

/**
 * @author mdoering
 */
public abstract class YamlTipTemplate<T> extends AbstractTipTemplate<T> {

    @SuppressWarnings("unchecked")
    public YamlTipTemplate(String identifier, ConfigurationSection config) {

        super(identifier);
        setName(config.getString("name", getIdentifier()));
        setDescription(config.getString("desc", "undefined"));
        setEnabled(config.getBoolean("enabled", true));
        setCooldown(TimeUtil.secondsToMillis(config.getDouble("cooldown", 0.0)));
        setRequirements(ActionAPI.createRequirements(getListenerId(), config.getConfigurationSection("requirements"), getTriggerEntityType()));
        setTrigger(ActionAPI.createTrigger(config.getConfigurationSection("trigger")));
        TipManager manager = RaidCraft.getComponent(TipManager.class);
        setDisplays(config.getStringList("displays").stream()
                .map(manager::getTipDisplay)
                .filter(display -> getTriggerEntityType().isAssignableFrom(display.getType()))
                .map(display -> (TipDisplay<T>) display)
                .collect(Collectors.toList()));
    }

    @Override
    public Tip<T> createTip(T entity) {

        return RaidCraft.getComponent(TipManager.class).createTip(this, entity);
    }
}
