package de.raidcraft.tips.api;

import de.raidcraft.api.action.GenericType;

/**
 * @author mdoering
 */
public interface TipDisplay<T> extends GenericType<T> {

    public String getName();

    public void display(TipTemplate tip, T entity);
}
