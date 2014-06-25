package de.raidcraft.tips.api;

/**
 * @author mdoering
 */
public interface Tip<T> {

    public TipTemplate getTemplate();

    public T getEntity();

    public boolean isDisplayed();

    public boolean isOnCooldown();

    public void display();
}
