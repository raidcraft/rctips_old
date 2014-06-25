package de.raidcraft.tips.api;

/**
 * @author mdoering
 */
public interface TipDisplay<T> {

    public Class<T> getType();

    public default boolean matchesType(T entity) {

        return getType().isAssignableFrom(entity.getClass());
    }

    public void display(TipTemplate tip, T entity);
}
