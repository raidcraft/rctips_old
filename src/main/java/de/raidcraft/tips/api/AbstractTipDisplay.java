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

    public AbstractTipDisplay(String name) {

        this.name = name;
    }
}
