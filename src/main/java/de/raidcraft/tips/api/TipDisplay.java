package de.raidcraft.tips.api;

/**
 * @author mdoering
 */
public interface TipDisplay<T> {

    String getName();

    void display(TipTemplate tip, T entity);
}
