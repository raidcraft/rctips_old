package de.raidcraft.tips.templates;

import de.raidcraft.RaidCraft;
import de.raidcraft.api.action.requirement.RequirementFactory;
import de.raidcraft.api.action.trigger.TriggerManager;
import de.raidcraft.tips.TipManager;
import de.raidcraft.tips.api.AbstractTipTemplate;
import de.raidcraft.tips.api.Tip;
import de.raidcraft.util.TimeUtil;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author mdoering
 */
public abstract class YamlTipTemplate<T> extends AbstractTipTemplate<T> {

    public YamlTipTemplate(String identifier, ConfigurationSection config) {

        super(identifier);
        setName(config.getString("name", getIdentifier()));
        setDescription(config.getString("desc", "undefined"));
        setEnabled(config.getBoolean("enabled", true));
        setCooldown(TimeUtil.secondsToMillis(config.getDouble("cooldown", 0.0)));
        setRequirements(RequirementFactory.getInstance().createRequirements(config.getConfigurationSection("requirements")));
        setTrigger(TriggerManager.getInstance().createTriggerFactories(config.getConfigurationSection("trigger")));
    }

    @Override
    public Tip<T> createTip(T entity) {

        return RaidCraft.getComponent(TipManager.class).createTip(this, entity);
    }
}
