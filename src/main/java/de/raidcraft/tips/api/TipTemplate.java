package de.raidcraft.tips.api;

import de.raidcraft.api.action.requirement.RequirementHolder;
import de.raidcraft.api.action.trigger.TriggerListener;

import java.util.Collection;

/**
 * @author mdoering
 */
public interface TipTemplate extends TriggerListener, RequirementHolder {

    public String getIdentifier();

    public String getName();

    public String getDescription();

    public boolean isPersistant();

    public boolean isRepeating();

    public long getCooldown();

    public <T> Collection<TipDisplay<T>> getDisplays();

    public <T> Tip<T> display(T entity);

    public <T> Tip<T> createTip(T entity);
}
