package de.raidcraft.tips.api;

/**
 * @author mdoering
 */
public interface TipDisplay<T> {

    public String getName();

    public Class<T> getType();

    public default boolean matchesType(Object entity) {

        return getType().isAssignableFrom(entity.getClass());
    }

    public void display(TipTemplate tip, T entity);
}
