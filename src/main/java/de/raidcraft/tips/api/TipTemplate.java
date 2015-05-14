package de.raidcraft.tips.api;

import de.raidcraft.api.action.requirement.RequirementHolder;
import de.raidcraft.api.action.trigger.TriggerListener;

import java.util.Collection;

/**
 * @author mdoering
 */
public interface TipTemplate<T> extends TriggerListener<T>, RequirementHolder {

    public String getIdentifier();

    public String getName();

    public String getDescription();

    public boolean isEnabled();

    public boolean isRepeating();

    public long getCooldown();

    public Collection<TipDisplay<T>> getDisplays();

    public Tip<T> display(T entity);

    public Tip<T> createTip(T entity);
}
