package de.raidcraft.tips.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mdoering
 */
@Data
@EqualsAndHashCode(of = "name")
public abstract class AbstractTipDisplay<T> implements TipDisplay<T> {

    private final String name;
    private final Class<T> type;

    public AbstractTipDisplay(String name, Class<T> type) {

        this.name = name;
        this.type = type;
    }
}
