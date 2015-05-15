package de.raidcraft.tips.api;

import de.raidcraft.api.action.requirement.RequirementHolder;
import de.raidcraft.api.action.trigger.TriggerListener;

import java.util.Collection;

/**
 * @author mdoering
 */
public interface TipTemplate<T> extends TriggerListener<T>, RequirementHolder {

    String getIdentifier();

    String getName();

    String getDescription();

    boolean isEnabled();

    boolean isRepeating();

    long getCooldown();

    Collection<TipDisplay<T>> getDisplays();

    Tip<T> display(T entity);

    Tip<T> createTip(T entity);
}
