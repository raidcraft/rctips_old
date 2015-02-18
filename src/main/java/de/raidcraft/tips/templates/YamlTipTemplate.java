package de.raidcraft.tips.templates;

import de.raidcraft.RaidCraft;
import de.raidcraft.api.action.requirement.RequirementException;
import de.raidcraft.api.action.requirement.RequirementFactory;
import de.raidcraft.api.action.trigger.TriggerManager;
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
        try {
            setRequirements(RequirementFactory.getInstance().createRequirements(getListenerId(), config.getConfigurationSection("requirements")));
        } catch (RequirementException e) {
            RaidCraft.LOGGER.warning(e.getMessage() + " in " + getIdentifier());
        }
        setTrigger(TriggerManager.getInstance().createTriggerFactories(config.getConfigurationSection("trigger")));
        TipManager manager = RaidCraft.getComponent(TipManager.class);
        setDisplays(config.getStringList("displays").stream()
                .map(manager::getTipDisplay)
                .filter(display -> display.getType().isAssignableFrom(getTriggerEntityType()))
                .map(display -> (TipDisplay<T>) display)
                .collect(Collectors.toSet()));
    }

    @Override
    public Tip<T> createTip(T entity) {

        return RaidCraft.getComponent(TipManager.class).createTip(this, entity);
    }
}
