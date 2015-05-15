package de.raidcraft.tips.api;

/**
 * @author mdoering
 */
public interface Tip<T> {

    TipTemplate getTemplate();

    T getEntity();

    boolean isEntityEnabled();

    boolean isDisplayed();

    boolean isOnCooldown();

    void display();

    void save();
}
