package de.raidcraft.tips.api;

/**
 * @author mdoering
 */
public interface TipDisplay<T> {

    String getName();

    Class<T> getType();

    void display(TipTemplate tip, T entity);
}
