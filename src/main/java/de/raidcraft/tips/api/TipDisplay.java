package de.raidcraft.tips.api;

import de.raidcraft.api.action.GenericType;

/**
 * @author mdoering
 */
public interface TipDisplay<T> extends GenericType<T> {

    String getName();

    void display(TipTemplate tip, T entity);
}
